


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.varia.NullAppender;


public class Gohan {

	static final String fichero_saldos="/home/bruno/AST/db/saldos.txt";
	
	//Nombres de los servicios en UDDI
	private static final String name_service_card = "CreditCardValidator ";
	
	//Nombre y localización del fichero de logs para este programa.
	private static final String fichero_log = "/home/bruno/AST/db/gohan_log.txt";
	
	ServiceClient sc1;
	ServiceClient sc2;
	ServiceClient sc3;
	ServiceClient sc4;
	ServiceClient sc5;
	
	
	//Comprobaciones de la tarjeta que modificará el Callback una vez responda el servicio externo.
	String tipo_tarjeta="";
	boolean debito=false;
	boolean largo=false;
	boolean fecha=false;
	boolean mod=false;
	
	/**
	 * Permite alterar el saldo de una tarjeta determinada, pasandole un importe tanto negativo como positivo.
	 * Esta función permite realizar una simulación de un banco.
	 * En caso de no existir la tarjeta en la lista, el saldo de esta se inicia a 100.
	 * @param tarjeta -> Número de la tarjeta a realizar la modificación de saldo.
	 * @param importe -> Importe que se le va a sumar (restar en caso de ser negativo) al saldo de la tarjeta correspondiente.
	 * @return Devuelve el saldo resultante de la operación.
	 */
	private static double setSaldo(String tarjeta, double importe)
	 {	
				
		BufferedReader in = null;
		BufferedWriter out = null;
		
		//Mapa de tarjeta - saldo.
		HashMap<String, Double> saldos = new HashMap<String, Double>();
		
		//Lectura de todos los saldos de tarjeta y carga en el mapa.
		try
		 {
			in = new BufferedReader(new FileReader(fichero_saldos));
			String l_saldo="";
			
			while((l_saldo=in.readLine())!=null)
			 {
				if(l_saldo.equals("")) continue;
				saldos.put(l_saldo.split("-.-")[0], Double.parseDouble(l_saldo.split("-.-")[1]));
			 }
		 }
		catch(IOException ioe)
		 {
			log(ioe.toString());
			return -1;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -1;
		 }
		finally
		 {
			try
			 {
				if( null != in )
				 {
					in.close();
				 }
			 }
			catch (Exception e2)
			 {
				log(e2.toString());
				return -1;
			 }
		 }
		
		//Modificación del saldo correspondiente.
		double saldo=0;
		if(saldos.containsKey(tarjeta))
		 {
			saldo = saldos.get(tarjeta);
			if((saldo+importe)>=0)
			 {
				saldo = saldo+importe;
				saldos.put(tarjeta, saldo);
			 }
			else
				return -1;
		 }
		else
		 {
			saldo = 100+importe;
			if(saldo<0) return -1;
			saldos.put(tarjeta, saldo);
		 }
		
		//Almacenamiento de los saldos en el fichero.
		try
		 {
			out = new BufferedWriter(new FileWriter(fichero_saldos));
			for(Entry<String, Double> entry: saldos.entrySet())
			 {
				out.write(entry.getKey()+"-.-"+entry.getValue()+"\n");
			 }
		 }
		catch(IOException io)
		 {
			log(io.toString());
			return -1;
		 }
		finally
		 {
			try
			 {
				if( null != out )
				 {
					out.close();
				 }
			 }
			catch (Exception e2)
			 {
				log(e2.toString());
				return -1;
			 }
		 }
		return saldo;
	 }

	
	/**
	 * Método para almacenar una linea de error en el fichero de log del programa.
	 * Se añade la fecha y hora del error, así como el nombre del programa desde el que se ha generado.
	 * @param datos -> Información del error.
	 */
	public static void log(String datos)
	 {
		BufferedWriter out = null;
		try
		 {
			Date fecha = new Date();
			out = new BufferedWriter(new FileWriter(fichero_log, true));   
			out.write(fecha+":"+" Gohan -- "+datos+"\n");
		 }
		catch(Exception e1)
		 {
			e1.printStackTrace();
		 }
		finally
		 {
			try
				 {
				if( null != out )
				 {
					out.close();
				 }
			 }
			catch (Exception e2)
			 {
				e2.printStackTrace();
			 }
		 }
	 }
	
	
	/**
	 * Permite abonar un importe a una tarjeta, siempre y cuando los datos de esta sean correctos.
	 * @param tarjeta -> Número de la tarjeta de crédito.
	 * @param importe -> Importe (positivo) que se le añadirá a la tarjeta 
	 * @param f_cad -> Fecha de caducidad de la tarjeta proporcionada
	 * @return -> Devuelve un 1 en caso de no haber ocurrido un error.
	 */
	public int abonarImporte(String tarjeta, double importe, String f_cad)
	 {
		//En caso de importe negativo retornamos con un código de error.
		if(importe<0)
			return -7;
		
		//Comentamos para realizar pruebas iniciales, debido al excesivo consumo de tiempo que supone realizar todas las comprobaciones (Estaba hasta os huevos de esperar por esto)
		if(comprobar_tarjeta(tarjeta, f_cad)<0)
			return -6;
		//Debido a que está comentado sustituimos por unos parámetro correctos.
		/*tipo_tarjeta="VISA";
		largo=true;
		fecha=true;
		mod=true;*/
		
		//Imprimimos solo para la realización de pruebas.
		/*System.out.println("Resultado de las comprobaciones:");
		System.out.println("1. "+tipo_tarjeta);
		System.out.println("2. "+debito);
		System.out.println("3. "+largo);
		System.out.println("4. "+fecha);
		System.out.println("5. "+mod);*/
		
		
		//Dependiendo del problema de la tarjeta retornamos con un código de error u otro.
		//Las tarjetas solo pueden ser Visa o Mastercard.
		if(!(tipo_tarjeta.toLowerCase().equals("visa") || tipo_tarjeta.toLowerCase().equals("mastercard")))
			return -1;
		
		if(!largo)
			return -2;
		
		if(!fecha)
			return -3;
		
		if(!mod)
			return -4;
		
		//Si todo a sido correcto añadimos el importe a la tarjeta.
		setSaldo(tarjeta, importe);
		
		return 1;
	 }

	
	/**
	 * Permite realizar un pago desde una tarjeta, siempre y cuando los datos de esta sean correctos.
	 * @param tarjeta -> Número de la tarjeta de crédito.
	 * @param importe -> Importe (positivo) que se le añadirá a la tarjeta 
	 * @param f_cad -> Fecha de caducidad de la tarjeta proporcionada
	 * @return -> Devuelve un 1 en caso de no haber ocurrido un error.
	 */
	public int realizarPago(String tarjeta, double importe, String f_cad)
	 {
		//En caso de importe negativo retornamos con un código de error.
		if(importe<0)
			return -7;

		double saldo_final=0;
		
		//Comentamos para realizar pruebas iniciales, debido al excesivo consumo de tiempo que supone realizar todas las comprobaciones (Estaba hasta os huevos de esperar por esto)
		if(comprobar_tarjeta(tarjeta, f_cad)<0)
			return -6;
		//Debido a que está comentado sustituimos por unos parámetro correctos.
		/*tipo_tarjeta="VISA";
		largo=true;
		fecha=true;
		mod=true;*/
		
		//Imprimimos solo para la realización de pruebas.
		/*System.out.println("1. "+tipo_tarjeta);
		System.out.println("2. "+debito);
		System.out.println("3. "+largo);
		System.out.println("4. "+fecha);
		System.out.println("5. "+mod);*/
		
		//Dependiendo del problema de la tarjeta retornamos con un código de error u otro.
		
		if(!largo)
			return -2;
		
		//Las tarjetas solo pueden ser Visa o Mastercard.
		if(!(tipo_tarjeta.toLowerCase().equals("visa") || tipo_tarjeta.toLowerCase().equals("mastercard")))
			return -1;
		
		if(!fecha)
			return -3;
		
		if(!mod)
			return -4;
		
		//Si todo a sido correcto retiramos el importe de la tarjeta
		saldo_final = setSaldo(tarjeta, -(importe));
		
		//En caso de que los fondos en la tarjeta sean insuficientes para realizar el pago, retornamos con un error.
		if(saldo_final<0)
			return -5;
				
		return 1;
	 }
	
	/**
	 * Realiza las comprobaciones pertinentes de una tarjeta utilizando los servicios externos.
	 *      - Entidad emisora de la tarjeta (Visa, Mastercard, AmericanExpres, ...).
	 *      - Tarjeta de débito o crédito.
	 *      - Longitud válida del número de tarjeta.
	 *      - Fecha de caducidad de la tarjeta correcta.
	 *      - Comprobación de que el número de tarjeta respeta la regla del mod10.
	 * @param tarjeta -> Número de la tarjeta que queremos comprobar.
	 * @param f_cad -> Fecha de caducidad de la tarjeta.
	 * @return
	 */
	private int comprobar_tarjeta(String tarjeta, String f_cad)
	 {
		Servicio Ccard = new Servicio(name_service_card);
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			sc1 = new ServiceClient();
			sc2 = new ServiceClient();
			sc3 = new ServiceClient();
			sc4 = new ServiceClient();
			sc5 = new ServiceClient();
			Options opts= new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(Ccard.getEndpoint()));
			
			//Llamamos al servicio GetCardType
			opts.setAction("http://localhost/SmartPayments/GetCardType");
			sc1.setOptions(opts);
			Callback_Gohan call1 = new Callback_Gohan(this, 1);
			sc1.sendReceiveNonBlocking(getCardType(tarjeta), call1);
			
			
			//Llamamos al servicio IsDebitCard
			opts.setAction("http://localhost/SmartPayments/IsDebitCard");
			sc2.setOptions(opts);
			Callback_Gohan call2 = new Callback_Gohan(this, 2);
			sc2.sendReceiveNonBlocking(isDebitCard(tarjeta), call2);

			
			//Llamamos al servicio ValidCardLength
			opts.setAction("http://localhost/SmartPayments/ValidCardLength");
			sc3.setOptions(opts);
			Callback_Gohan call3 = new Callback_Gohan(this, 3);
			sc3.sendReceiveNonBlocking(validCardLength(tarjeta), call3);

			
			//Llamamos al servicio ValidExpDate
			opts.setAction("http://localhost/SmartPayments/ValidExpDate");
			sc4.setOptions(opts);
			Callback_Gohan call4 = new Callback_Gohan(this, 4);
			sc4.sendReceiveNonBlocking(validExpDate(f_cad), call4);
			
			
			//Llamamos al servicio ValidMod10
			opts.setAction("http://localhost/SmartPayments/ValidMod10");
			sc5.setOptions(opts);
			Callback_Gohan call5 = new Callback_Gohan(this, 5);
			sc5.sendReceiveNonBlocking(validMod10(tarjeta), call5);			
			
			
			//En caso de las llamadas asíncronas esperamos a que las flags de cada callback se pongan a true.
			while(!(call1.fin && call2.fin && call3.fin && call4.fin && call5.fin))
			 {
				Thread.sleep(1000);
			 }

			
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -1;
		 }		
		return 1;
	 }
		
	
	
	//*************************************************************************************************************************//
	//							Métodos para contruir el mensaje de llamada a cada servicio externo							   //
	//*************************************************************************************************************************//
	
	private OMElement getCardType(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("GetCardType", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private OMElement isDebitCard(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("IsDebitCard", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private OMElement validCardLength(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidCardLength", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private OMElement validExpDate(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidExpDate", omNs);
		OMElement value = fac.createOMElement("ExpDate", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private OMElement validMod10(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidMod10", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	


}
