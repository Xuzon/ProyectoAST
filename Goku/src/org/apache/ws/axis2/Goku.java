package org.apache.ws.axis2;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.util.XMLUtils;
import org.apache.log4j.varia.NullAppender;
import org.w3c.dom.NodeList;


public class Goku {
	
	//Nombre y localización del fichero para almacenar los importes de cada apuesta y datos de la tarjeta.
	private static final String fichero_imp_apuestas = "/home/bruno/AST/db/importe_apuestas.txt";
	
	/**
	 * Permite ver los datos de una apuesta en concreto (tipo, importe apostado, número de tarjeta, fecha caducidad de la tarjeta)
	 * @param id_a -> Identificador de la Apuesta
	 * @return devuelve un string con los diferentes datos separados por "//"
	 */
	private static String verApuesta(String id_a)
	 {
		String apuesta="";
		BufferedReader in = null;
		try
		 {
			in = new BufferedReader(new FileReader(fichero_imp_apuestas));
			String l_apuesta="";
			while((l_apuesta=in.readLine())!=null)
			 {
				if(l_apuesta.equals("")) continue;
				if(l_apuesta.split("-.-")[0].equals(id_a))
				 {
					apuesta = l_apuesta.split("-.-")[1];
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
				return null;
			 }
		 }
		return apuesta;
	 }
	
	/**
	 * Almacena los datos de una apuesta determinada. (tipo, importe apostado, número de tarjeta, fecha caducidad de la tarjeta)
	 * @param id_a -> Identificador de la apuesta
	 * @param datos -> Datos de la apuesta
	 */
	private static void guardarApuesta(int id_a, String datos)
	 {
		BufferedWriter out = null;
		try
		 {
			out = new BufferedWriter(new FileWriter(fichero_imp_apuestas, true));   
			out.write(id_a+"-.-"+datos+"\n");
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
	 * Permite realizar una apuesta por un partido. Realizará el cobro de la apuesta y en caso de ser correcto realizará
	 *  la apuesta y almacenará los datos de la misma para un posterior reenbolso en caso de ser necesario.
	 * @param id_p -> Identificador del partido en el que nos interesamos en apostar.
	 * @param goles_e1 -> Apuesta de goles por el equipo local.
	 * @param goles_e2 -> Apuesta de goles por el equipo visitante.
	 * @param importe -> Importe que deseamos apostar.
	 * @param tarjeta -> Número de la tarjeta por la que queremos realizar el pago y realizar el posterior cobro en caso de que proceda.
	 * @param f_cad -> Fecha de caducidad de la tarjeta proporcionada.
	 * @return Identificador de la apuesta que se ha realizado.
	 */
	public int apostarPartido(int id_p, int goles_e1, int goles_e2, double importe, String tarjeta, String f_cad)
	 {
		int id_a=0;
		importe = Math.rint(importe*100)/100;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio interno de pagos.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Gohan"));
			sc.setOptions(opts);
			OMElement res_pago = sc.sendReceive(realizarPago(tarjeta, importe+"", f_cad));
			
			//En caso de que ocurriese un error en el pago, cancelamos la apuesta.
			if(!res_pago.getFirstElement().getText().equals("1"))
			 {
				return -1;
			 }
			
			//Asignamos en las opciones la referencia al servicio interno de realización de apuestas.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goten"));
			sc.setOptions(opts);
			OMElement res_apuesta = sc.sendReceive(realizarApuestaPartido(id_p+"", goles_e1+"", goles_e2+""));
			
			//Obtengo del OMElement el id de apuesta.
			id_a = Integer.parseInt(res_apuesta.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return -1;
		 }
		
		//Si el id de apuesta es negativo es que ha sucedido algún error.
		if(id_a<0)
			return -1;
		
		//Almacenamos la apuesta en el fichero. Para poder identificar el tipo de apuesta, para apuesta de partido señalizamos con una "A".
		guardarApuesta(id_a, "A//"+importe+"//"+tarjeta+"//"+f_cad);
		
		return id_a;
	 }
	
	/**
	 *  Permite realizar una apuesta al pichichi del torneo. Realizará el cobro de la apuesta y en caso de ser correcto realizará
	 *  la apuesta y almacenará los datos de la misma para un posterior reenbolso en caso de ser necesario.
	 * @param jugador -> Nombre del jugador por el que se desea apostar.
	 * @param importe -> Importe que deseamos apostar.
	 * @param tarjeta -> Número de la tarjeta por la que queremos realizar el pago y realizar el posterior cobro en caso de que proceda.
	 * @param f_cad -> Fecha de caducidad de la tarjeta proporcionada.
	 * @return Identificador de la apuesta que se ha realizado.
	 */
	public int apostarPichichi(String jugador, double importe, String tarjeta, String f_cad)
	 {
		int id_a=0;
		importe = Math.rint(importe*100)/100;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio interno de pagos.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Gohan"));
			sc.setOptions(opts);
			OMElement res_pago = sc.sendReceive(realizarPago(tarjeta, importe+"", f_cad));
			
			//En caso de que ocurriese un error en el pago, cancelamos la apuesta.
			if(!res_pago.getFirstElement().getText().equals("1"))
			 {
				return -1;
			 }
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goten"));
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(realizarApuestaPichichi(jugador));
			
			//Obtengo del OMElement el id de apuesta.
			id_a = Integer.parseInt(res.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return -1;
		 }
		
		//Si el id de apuesta es negativo es que ha sucedido algún error.
		if(id_a<0)
			return -1;
		
		//Almacenamos la apuesta en el fichero. Para poder identificar el tipo de apuesta, para apuesta de pichichi señalizamos con una "B".
		guardarApuesta(id_a, "B//"+importe+"//"+tarjeta+"//"+f_cad);
		
		return id_a;
	 }
	
	
	/**
	 * En caso de que el resultado sea mayor que 1, abonará en la tarjeta correspondiente el importe resultante de lo apostado multiplicado por la cuota resultante.
	 * Este método solo estará disponible para el administrador del gestor de apuestas, que una vez finalizado un determinado evento, comprobará si existe alguna apuesta
	 * vinculada e invocará el mismo a través del identificador de la misma y la cuota resultante dependiendo del resultado del evento.
	 * Además se le informará a través de email al usuario del resultado de la misma, sea favorable o no.
	 * @param id_a -> Identificador de la apuesta.
	 * @param result -> Cuota correspondiente a la apuesta.
	 */
	public void apuestaFinalizada(int id_a, double result)
	 {
		String datos="", apostado_s="", tarjeta="", f_cad="";
		double apostado=0, importe_pagar=0;
		if(result>1)
		 {
			//Obtenemos los datos de la apueta que ha finalizado.
			datos = verApuesta(id_a+"");
			apostado_s = datos.split("//")[1];
			tarjeta = datos.split("//")[2];
			f_cad = datos.split("//")[3];
			
			try
			 {
				apostado = Double.parseDouble(apostado_s);
				
				//Calculo del importe que se pagará al afortunado, multiplicando lo apostado y la cuota resultante de la apuesta.
				importe_pagar = apostado*result;
				
				//POSTUREO, NO TOCAR.
				org.apache.log4j.BasicConfigurator.configure(new NullAppender());
				
				//Instanciamos el servicio de cliente y las opciones
				ServiceClient sc = new ServiceClient();
				Options opts = new Options();
				
				//Asignamos en las opciones la referencia al servicio interno de pagos.
				opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Gohan"));
				sc.setOptions(opts);
				OMElement res_pago = sc.sendReceive(abonarImporte(tarjeta, importe_pagar+"", f_cad));
				
				//En caso de que el pago no se llegase a realizar es necesario indicarlo
				//Falta implementar en el documento el cobro o no de la apuesta.
				System.out.println(res_pago.getFirstElement().getText());
				sc.cleanupTransport();
			 }
			catch(AxisFault af)
			 {
				af.printStackTrace();
				return;
			 }
			catch(Exception e)
			 {
				e.printStackTrace();
				return;
			 }
		 }
		
		return;
	 }
	 
	 
	/**
	 * Permite a un cliente comprobar el resultado de una terminada apuesta que ya ha finalizado.
	 * @param id_a -> Identificador de la apuesta.
	 * @return -> Importe ganado en la apuesta.
	 */
	public double comprobarApuesta(int id_a)
	 {
		String datos="", importe_s="";
		double cuota=0, importe=0;
		
		//Obtenemos los datos de la apueta
		datos = verApuesta(id_a+"");
		importe_s = datos.split("//")[1];
		
		//Llamaremos a un método u otro del servicio externo dependiendo del tipo de apuesta que sea.
		if(datos.split("//")[0].equals("A"))
		 {
			try
			 {
				//POSTUREO, NO TOCAR.
				org.apache.log4j.BasicConfigurator.configure(new NullAppender());
				
				//Instanciamos el servicio de cliente y las opciones
				ServiceClient sc = new ServiceClient();
				Options opts = new Options();
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goten"));
				sc.setOptions(opts);
				OMElement res = sc.sendReceive(comprobarApuestaPartido(id_a+""));
				
				//Obtengo del OMElement el id de apuesta.
				cuota = Double.parseDouble(res.getFirstElement().getText());
				
				sc.cleanupTransport();
				
				//Si la cuota es negativa es que ha sucedido algún error.
				if(cuota<0)
					return -2;
				
			 }
			catch(Exception e)
			 {
				e.printStackTrace();
				return -1;
			 }
		 }
		else if(datos.split("//")[0].equals("B"))
		 {
			try
			 {
				//POSTUREO, NO TOCAR.
				org.apache.log4j.BasicConfigurator.configure(new NullAppender());
				
				//Instanciamos el servicio de cliente y las opciones
				ServiceClient sc = new ServiceClient();
				Options opts = new Options();
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goten"));
				sc.setOptions(opts);
				OMElement res = sc.sendReceive(comprobarApuestaPichichi(id_a+""));
								
				//Obtengo del OMElement el id de apuesta.
				cuota = Double.parseDouble(res.getFirstElement().getText());
				
				sc.cleanupTransport();
				
				//Si la cuota es negativa es que ha sucedido algún error.
				if(cuota<0)
					return -2;
			 }
			catch(Exception e)
			 {
				e.printStackTrace();
				return -1;
			 }
		 }
		else
		 {
			return -1;
		 }
		
		try
		 {
			importe = Double.parseDouble(importe_s);
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return -3;
		 }
		
		//Retornamos la ganacia de la apuesta.
		
		return importe*cuota;
	 }
	
	
	/**
	 * Devuelve una lista de todos los equipos que participan el la competición.
	 * @return Listado de equipos que participan en la competición.
	 */
	public ArrayList<String> listarEquipos()
	 {
		ArrayList<String> lista = new ArrayList<String>();
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("http://footballpool.dataaccess.eu/data/info.wso"));
	
			//Llamamos al servicio GetCardType
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(teams());
			
			//Añadimos a la lista los Nombre de los equipos del XML que nos devuelve el servicio externo.
			NodeList nl = XMLUtils.toDOM(res).getElementsByTagName("m:sName");
			for(int i=0; i<nl.getLength(); i++)
			 {
				lista.add(nl.item(i).getTextContent());
			 }
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		return lista;
	 }
	
	
	/**
	 * Permite obtener un listado de los Jugadores de un determinado equipo convocados en la competición
	 * @param equipo -> Nombre del equipo al que petenecen los jugadores que queremos obtener. En caso de vacio obtendrá todos los jugadores de cualquier equipo.
	 * @return -> Listado de Jugadores.
	 */
	public ArrayList<String> listarJugadoresEquipo(String equipo)
	 {
		ArrayList<String> lista = new  ArrayList<String>();
		
		//En caso de que el equipo sea null o una cadena vacia, llamaremos a un servicio para obtener todos los jugadores de la competición
		//En otro caso llamaremos al servicio externo que nos proporciona toda la información de un equipo en concreto para extraer los nombre de los jugadores.
		if(equipo==null || equipo.equals(""))
		 {
			try
			 {
				//POSTUREO, NO TOCAR.
				org.apache.log4j.BasicConfigurator.configure(new NullAppender());
				
				//Instanciamos el servicio de cliente y las opciones
				ServiceClient sc = new ServiceClient();
				Options opts = new Options();
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference("http://footballpool.dataaccess.eu/data/info.wso"));
				
				//Añadimos a la lista los campos del XML obtenidos del servicio, que corresponden con el nombre de los jugadores.
				sc.setOptions(opts);
				OMElement res = sc.sendReceive(allPlayerNames());
				Iterator<OMElement> it = res.getFirstElement().getChildElements();
				while(it.hasNext())
				 {
					lista.add(((OMElement)it.next().getChildrenWithLocalName("sName").next()).getText());
				 }
				sc.cleanupTransport();
			 }
			catch(Exception e)
			 {
				e.printStackTrace();
			 }
		 }
		else
		 {
			try
			 {
				//POSTUREO, NO TOCAR.
				org.apache.log4j.BasicConfigurator.configure(new NullAppender());
				
				//Instanciamos el servicio de cliente y las opciones
				ServiceClient sc = new ServiceClient();
				Options opts = new Options();
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference("http://footballpool.dataaccess.eu/data/info.wso"));
				
				//Añadimos a la lista los campos del XML obtenido del servicio externo correspondientes con los nombres de los jugadores de cada equipo.
				sc.setOptions(opts);
				OMElement res = sc.sendReceive(fullTeamInfo(equipo));
				Iterator<OMElement> it1 = res.getFirstElement().getChildElements();
				Iterator<OMElement> it2;
				while(it1.hasNext())
				 {
					it2 = it1.next().getChildElements();
					while(it2.hasNext())
					 {
						lista.add(it2.next().getText());
					 }
					it2 = null;
				 }
				
				sc.cleanupTransport();
			
			 }
			catch(Exception e)
			 {
				e.printStackTrace();
			 }
		 }
		
		return lista;
	 }
	
	
	/**
	 * Listado de los partidos que va a jugar un equipo en concreto, o todos los partidos de la competición
	 * @param equipo -> Nombre del equipo del que queremos listar sus partidos. En caso de no pasarlo se listará todos los partidos de la competición.
	 * @return Se devuelve el identificador del partido además de una cadena con los equipos que juegan el partido.
	 */
	public Map<Integer, String> listarPartidos(String equipo)
	 {
		Map<Integer, String> mapa = new HashMap<Integer, String>();
		int id=0;
		String equipos="";
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("http://footballpool.dataaccess.eu/data/info.wso"));
			
			//Llamamos al servicio externo para obtener la información de todos los partidos del calendario en la competición.
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(allGames());
			Iterator<OMElement> it1 = res.getFirstElement().getChildElements();
			OMElement partido;
			
			//Recorremos el XML para obtener el identificador y los equipos que participan en el partido.
			while(it1.hasNext())
			 {
				partido = it1.next();
				id = Integer.parseInt(partido.getFirstElement().getText());
				equipos = ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team1").next()).getChildrenWithLocalName("sName").next()).getText();
				equipos += " - ";
				equipos += ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team2").next()).getChildrenWithLocalName("sName").next()).getText();
				
				if(equipo==null || equipos.toLowerCase().contains(equipo.toLowerCase()) || equipo.equals(""))
				 {
					mapa.put(id, equipos);
				 }
			 }
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		return mapa;
	 }
	
	
	
	//*************************************************************************************************************************//
	//				Métodos para contruir el mensaje de llamada a cada servicio tanto propio como externo					   //
	//*************************************************************************************************************************//
	
	
	private OMElement teams()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("Teams", omNs);
		return method;
	 }
	
	private OMElement fullTeamInfo(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("FullTeamInfo", omNs);
		OMElement value = fac.createOMElement("sTeamName", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private OMElement allGames()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("AllGames", omNs);
		return method;
	 }
	
	private OMElement allPlayerNames()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("AllPlayerNames", omNs);
		OMElement value = fac.createOMElement("bSelected", omNs);
		value.setText("");
		method.addChild(value);
		return method;
	 }
	
	private OMElement realizarApuestaPartido(String valor1, String valor2, String valor3)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("realizarApuestaPartido", omNs);
		OMElement value1 = fac.createOMElement("id_p", omNs);
		value1.setText(valor1);
		OMElement value2 = fac.createOMElement("goles_e1", omNs);
		value2.setText(valor2);
		OMElement value3 = fac.createOMElement("goles_e2", omNs);
		value3.setText(valor3);
		method.addChild(value1);
		method.addChild(value2);
		method.addChild(value3);
		return method;
	 }
	
	private OMElement realizarApuestaPichichi(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("realizarApuestaPichichi", omNs);
		OMElement value = fac.createOMElement("jugador", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private OMElement realizarPago(String valor1, String valor2, String valor3)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("realizarPago", omNs);
		OMElement value1 = fac.createOMElement("tarjeta", omNs);
		value1.setText(valor1);
		OMElement value2 = fac.createOMElement("importe", omNs);
		value2.setText(valor2);
		OMElement value3 = fac.createOMElement("f_cad", omNs);
		value3.setText(valor3);
		method.addChild(value1);
		method.addChild(value2);
		method.addChild(value3);
		return method;
	 }
	
	private OMElement abonarImporte(String valor1, String valor2, String valor3)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("abonarImporte", omNs);
		OMElement value1 = fac.createOMElement("tarjeta", omNs);
		value1.setText(valor1);
		OMElement value2 = fac.createOMElement("importe", omNs);
		value2.setText(valor2);
		OMElement value3 = fac.createOMElement("f_cad", omNs);
		value3.setText(valor3);
		method.addChild(value1);
		method.addChild(value2);
		method.addChild(value3);
		return method;
	 }
	 
	private OMElement comprobarApuestaPartido(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("comprobarApuestaPartido", omNs);
		OMElement value = fac.createOMElement("id_a", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	 
	private OMElement comprobarApuestaPichichi(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("comprobarApuestaPichichi", omNs);
		OMElement value = fac.createOMElement("id_a", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
}
