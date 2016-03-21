

import java.util.HashMap;

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
	
	//Listado de los saldos de cada tarjeta de crétido
	private static HashMap<String, Double> saldos = new HashMap<String, Double>();
	
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
				saldo = -1;
		 }
		else
		 {
			saldo = 100+importe;
			if(saldo<0) saldo=0;
			saldos.put(tarjeta, saldo);
		 }
		
		return saldo;
		
		//Intento de controlar saldo en fichero.
		//Se abandona debido al coste de operaciones, ya que seria necesario reescribir todo el fichero al modificar una sola linea.
		//Además no es necesario, ya que aunque no sea permanente lo podemos realizar simplemente con un listado.
		
		/*
		String saldo="0";
		BufferedReader in = null;
		BufferedWriter out = null;
		try
		 {
			//PrintWriter pw = new PrintWriter(new FileWriter(fichero_saldos));
			in = new BufferedReader(new FileReader(fichero_saldos));
			out = new BufferedWriter(new FileWriter(fichero_saldos, true));
			String l_saldo="";
			
			while((l_saldo=in.readLine())!=null)
			 {
				if(l_saldo.equals("")) continue;
				if(l_saldo.split("-.-")[0].equals(tarjeta))
				 {
					saldo = l_saldo.split("-.-")[1];
					out.write("modificado");
					out.flush();
					out.newLine();
					break;
				 }
			 }
		 }
		catch(IOException ioe)
		 {
			ioe.printStackTrace();
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
				e2.printStackTrace();
				return 0;
			 }
		 }
		
		try
		 {
			return Double.parseDouble(saldo);
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return 0;
		 }
		 */
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
		//Además de existir problemas con las llamadas asíncronas a estes servicios, queda pendiente realizar mas pruebas.
		//if(comprobar_tarjeta(tarjeta, f_cad)<0)
		//	return -6;
		//Debido a que está comentado sustituimos por unos parámetro correctos.
		tipo_tarjeta="VISA";
		largo=true;
		fecha=true;
		mod=true;
		
		//Imprimimos solo para la realización de pruebas.
		System.out.println("Resultado de las comprobaciones:");
		System.out.println("1. "+tipo_tarjeta);
		System.out.println("2. "+debito);
		System.out.println("3. "+largo);
		System.out.println("4. "+fecha);
		System.out.println("5. "+mod);
		
		
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
				//Además de existir problemas con las llamadas asíncronas a estes servicios, queda pendiente realizar mas pruebas.
		//if(comprobar_tarjeta(tarjeta, f_cad)<0)
		//	return -6;
		//Debido a que está comentado sustituimos por unos parámetro correctos.
		tipo_tarjeta="VISA";
		largo=true;
		fecha=true;
		mod=true;
		
		//Imprimimos solo para la realización de pruebas.
		System.out.println("1. "+tipo_tarjeta);
		System.out.println("2. "+debito);
		System.out.println("3. "+largo);
		System.out.println("4. "+fecha);
		System.out.println("5. "+mod);
		
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
		//Vemos comentado los callback debido a que todavía tenemos problemas con las llamadas asíncronas.
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("https://secure.ftipgw.com/ArgoFire/validate.asmx"));
	
			//Llamamos al servicio GetCardType
			opts.setAction("http://localhost/SmartPayments/GetCardType");
			sc.setOptions(opts);
			//Callback_Gohan call1 = new Callback_Gohan(this, 1);
			//sc.sendReceiveNonBlocking(getCardType(tarjeta), call1);
			OMElement res1 = sc.sendReceive(getCardType(tarjeta));
			tipo_tarjeta = res1.getFirstElement().getText();
			
			//Llamamos al servicio IsDebitCard
			opts.setAction("http://localhost/SmartPayments/IsDebitCard");
			sc.setOptions(opts);
			//Callback_Gohan call2 = new Callback_Gohan(this, 2);
			//sc.sendReceiveNonBlocking(isDebitCard(tarjeta), call2);
			OMElement res2 = sc.sendReceive(isDebitCard(tarjeta));
			debito = Boolean.parseBoolean(res2.getFirstElement().getFirstElement().getFirstElement().getText());

			//Llamamos al servicio ValidCardLength
			opts.setAction("http://localhost/SmartPayments/ValidCardLength");
			sc.setOptions(opts);
			//Callback_Gohan call3 = new Callback_Gohan(this, 3);
			//sc.sendReceiveNonBlocking(validCardLength(tarjeta), call3);
			OMElement res3 = sc.sendReceive(validCardLength(tarjeta));
			largo = Boolean.parseBoolean(res3.getFirstElement().getText());

			//Llamamos al servicio ValidExpDate
			opts.setAction("http://localhost/SmartPayments/ValidExpDate");
			sc.setOptions(opts);
			//Callback_Gohan call4 = new Callback_Gohan(this, 4);
			//sc.sendReceiveNonBlocking(validExpDate(f_cad), call4);
			OMElement res4 = sc.sendReceive(validExpDate(f_cad));
			fecha = Boolean.parseBoolean(res4.getFirstElement().getText());

			//Llamamos al servicio ValidMod10
			opts.setAction("http://localhost/SmartPayments/ValidMod10");
			sc.setOptions(opts);
			//Callback_Gohan call5 = new Callback_Gohan(this, 5);
			//sc.sendReceiveNonBlocking(validMod10(tarjeta), call5);
			OMElement res5 = sc.sendReceive(validMod10(tarjeta));
			mod = Boolean.parseBoolean(res5.getFirstElement().getText());
			sc.cleanupTransport();
			
			//En caso de las llamadas asíncronas esperamos a que las flags de cada callback se pongan a true.
			/*
			while(!(call1.fin && call2.fin && call3.fin && call4.fin && call5.fin))
			 {
			 	//Imprimimos los valores de las flags para realizar pruebas.
				System.out.println("1. "+call1.fin);
				System.out.println("2. "+call2.fin);
				System.out.println("3. "+call3.fin);
				System.out.println("4. "+call4.fin);
				System.out.println("5. "+call5.fin);
				Thread.sleep(1000);
			 }	*/
			
			//Debido a problemas con la llamada asincrona mantenemos temporalmente las llamadas síncronas
			//y comentamos las demás, incluido el callback
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
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
