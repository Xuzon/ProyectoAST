package org.apache.ws.axis2;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.axis2.util.XMLUtils;
import org.apache.log4j.varia.NullAppender;
import org.w3c.dom.NodeList;


public class Goku implements ServiceLifeCycle {
	
	//Nombre y localización del fichero para almacenar los importes de cada apuesta y datos de la tarjeta.
	private static final String fichero_imp_apuestas = "importe_apuestas.txt";
	private static final String directorio_imp_apuestas = System.getProperty("user.home")+"/AST/db";

	
	//Nombre y localización del fichero de logs para este programa.
	private static final String fichero_log = "goku_log.txt";
	private static final String directorio_log = System.getProperty("user.home")+"/AST/log";
	
	//Nombres de los servicios en UDDI
	private static final String name_service_goten = "Goten";
	private static final String name_service_gohan = "Gohan";
	private static final String name_service_mundial = "Mundial";
	
	//Variables para realizar el registro en UDDI
	private static String dir_uddi_publish;
	private static String dir_uddi_security;
	private static final String uddi_user = "games";
	private static final String uddi_pass = "games";
	private static final String service_name = "Goku";
	private static final String service_key = "uddi:localhost:servicios-propios-"+service_name.toLowerCase();
	private static final String business_key = "uddi:localhost:key";
	
	//Contraseña para función hash
	private static final String pass = "password";
	
	/**
	 * Método que se ejecuta al iniciar el servicio en axis2.
	 */
	@Override
	public void startUp(ConfigurationContext arg0, AxisService arg1) {
		
		log("Se ha iniciado el servicio.");
		
		//Buscamos en la web la dirección del servidor uddi real.
		String ip_uddi = "";
		try
		 {
			URL web = new URL("http://brudi.es/ast/uddi.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			if((ip_uddi = in.readLine()) != null);
			dir_uddi_publish = ip_uddi+"/juddiv3/services/publish";
			dir_uddi_security = ip_uddi+"/juddiv3/services/security";
		 }
		catch(Exception e)
		 {
			log(e.toString());
		 }
		

		String ip = "";
		try
		 {
			NetworkInterface interfaz = NetworkInterface.getByName("wlan0");
			ip = interfaz.getInterfaceAddresses().get(3).getAddress().getHostAddress();
		 }
		catch(Exception e)
		 {
			log(e.toString());
		 }
		
		String access_point = "http://"+ip+":8080/axis2/services/"+service_name;
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(dir_uddi_security));
			
			opts.setAction("get_authToken");
				
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(get_authToken(uddi_user, uddi_pass));
			
			sc.cleanupTransport();
			
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(dir_uddi_publish));
			
			opts.setAction("save_service");

			sc.setOptions(opts);
			sc.sendRobust(save_service(res.getFirstElement().getText(), service_name, service_key, business_key, access_point));
			
			sc.cleanupTransport();
		 }
		catch(Exception e)
		 {
			log(e.toString());
		 }

	}
	
	
	/**
	 * Método que se ejecuta al cerrar el servicio en axis2.
	 */
	@Override
	public void shutDown(ConfigurationContext arg0, AxisService arg1) {
		
		//Buscamos en la web la dirección del servidor uddi real.
		String ip_uddi = "";
		try
		 {
			URL web = new URL("http://brudi.es/ast/uddi.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			if((ip_uddi = in.readLine()) != null);
			dir_uddi_publish = ip_uddi+"/juddiv3/services/publish";
			dir_uddi_security = ip_uddi+"/juddiv3/services/security";
		 }
		catch(Exception e)
		 {
			log(e.toString());
		 }
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(dir_uddi_security));
			
			opts.setAction("get_authToken");
				
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(get_authToken(uddi_user, uddi_pass));
			
			sc.cleanupTransport();
						
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(dir_uddi_publish));
			
			opts.setAction("delete_service");
			sc.setOptions(opts);
			sc.sendRobust(delete_service(res.getFirstElement().getText(), service_key));
			
			sc.cleanupTransport();
		 }
		catch(Exception e)
		 {
			log(e.toString());
		 }
		
		log("Se ha finalizado el servicio.");
	}
	
	
	/**
	 * Permite ver los datos de una apuesta en concreto (tipo, importe apostado, número de tarjeta, fecha caducidad de la tarjeta)
	 * @param id_a -> Identificador de la Apuesta
	 * @return devuelve un string con los diferentes datos separados por "//"
	 */
	private static String verApuesta(String id_a)
	 {
		String apuesta="-1";
		BufferedReader in = null;
		File f = new File(directorio_imp_apuestas+"/"+fichero_imp_apuestas);
		
		//Leemos el fichero solo si existe.
		if(f.exists())
		 {
			try
			 {
				in = new BufferedReader(new FileReader(f));
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
				log(ioe.toString());
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
					return null;
				 }
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
		
		File dir = new File(directorio_imp_apuestas);
		dir.mkdirs();
		try
		 {
			out = new BufferedWriter(new FileWriter(dir+"/"+fichero_imp_apuestas, true));   
			out.write(id_a+"-.-"+datos+"\n");
		 }
		catch(Exception e1)
		 {
			log(e1.toString());
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
			 }
		 }
	 }
	
	
	/**
	 * Método para almacenar una linea de error en el fichero de log del programa.
	 * Se añade la fecha y hora del error, así como el nombre del programa desde el que se ha generado.
	 * @param datos -> Información del error.
	 */
	private static void log(String datos)
	 {
		BufferedWriter out = null;
		File dir = new File(directorio_log);
		dir.mkdirs();
		try
		 {
			Date fecha = new Date();
			out = new BufferedWriter(new FileWriter(dir+"/"+fichero_log, true));   
			out.write(fecha+":"+" Goku -- "+datos+"\n");
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
		int id_a=0, error=0;
		importe = Math.rint(importe*100)/100;
		
		Servicio Gohan = new Servicio(name_service_gohan);
		if(Gohan.getError()==-100 || Gohan.getError()==-101)
			return Gohan.getError()-10;
		Servicio Goten = new Servicio(name_service_goten);
		if(Goten.getError()==-100 || Goten.getError()==-101)
			return Goten.getError()-20;
		
		OMElement res_pago = null;
		ServiceClient sc = null;
		Options opts = null;
				
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			sc = new ServiceClient();
			opts = new Options();
			
			opts.setAction("realizarPago");

			//Asignamos en las opciones la referencia al servicio interno de pagos.
			opts.setTo(new EndpointReference(Gohan.getEndpoint()));
			sc.setOptions(opts);

			res_pago = sc.sendReceive(realizarPago(tarjeta, importe+"", f_cad));

			error = Integer.parseInt(res_pago.getFirstElement().getText());
			sc.cleanupTransport();

		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -10;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -11;
		 }
		
		//En caso de que ocurriese un error en el pago, cancelamos la apuesta.
		if(error!=1)
		 {
			return error;
		 }
			
		try
		 {
			opts.setAction("realizarApuestaPartido");
			
			//Asignamos en las opciones la referencia al servicio interno de realización de apuestas.
			opts.setTo(new EndpointReference(Goten.getEndpoint()));
			sc.setOptions(opts);
			OMElement res_apuesta = sc.sendReceive(realizarApuestaPartido(id_p+"", goles_e1+"", goles_e2+""));
			
			
			//Insertamos la cabecera con el hash para autentificarnos en el sistema.
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("", "");
			OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
			cabeceraHash.setText(pass.hashCode()+"");
			sc.addHeader(cabeceraHash);
			
			//Obtengo del OMElement el id de apuesta.
			id_a = Integer.parseInt(res_apuesta.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -12;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -13;
		 }
		
		//Si el id de apuesta es negativo es que ha sucedido algún error.
		if(id_a<0)
			return -14;
		
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
		int id_a=0, error=0;
		importe = Math.rint(importe*100)/100;
		
		Servicio Gohan = new Servicio(name_service_gohan);
		if(Gohan.getError()==-100 || Gohan.getError()==-101)
			return Gohan.getError()-10;
		Servicio Goten = new Servicio(name_service_goten);
		if(Goten.getError()==-100 || Goten.getError()==-101)
			return Goten.getError()-20;
		
		ServiceClient sc = null;
		Options opts = null;
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			sc = new ServiceClient();
			opts = new Options();
				
			opts.setAction("realizarPago");
			
			//Asignamos en las opciones la referencia al servicio interno de pagos.
			opts.setTo(new EndpointReference(Gohan.getEndpoint()));
			sc.setOptions(opts);
			OMElement res_pago = sc.sendReceive(realizarPago(tarjeta, importe+"", f_cad));
			
			error = Integer.parseInt(res_pago.getFirstElement().getText());
			
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -10;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -11;
		 }
		
		//En caso de que ocurriese un error en el pago, cancelamos la apuesta.
		if(error!=0)
		 {
			return error;
		 }
		
		try
		 {
			
			opts.setAction("realizarApuestaPichichi");
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(Goten.getEndpoint()));
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(realizarApuestaPichichi(jugador));
			
			//Insertamos la cabecera con el hash para autentificarnos en el sistema.
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("", "");
			OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
			cabeceraHash.setText(pass.hashCode()+"");
			sc.addHeader(cabeceraHash);
			
			
			//Obtengo del OMElement el id de apuesta.
			id_a = Integer.parseInt(res.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -12;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -13;
		 }
		
		//Si el id de apuesta es negativo es que ha sucedido algún error.
		if(id_a<0)
			return -14;
		
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
		
		Servicio Gohan = new Servicio(name_service_gohan);
		
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
				
				opts.setAction("abonarImporte");
				
				//Asignamos en las opciones la referencia al servicio interno de pagos.
				opts.setTo(new EndpointReference(Gohan.getEndpoint()));
				sc.setOptions(opts);
				@SuppressWarnings("unused")
				OMElement res_pago = sc.sendReceive(abonarImporte(tarjeta, importe_pagar+"", f_cad));
				
				//En caso de que el pago no se llegase a realizar es necesario indicarlo
				//Falta implementar en el documento el cobro o no de la apuesta.
				sc.cleanupTransport();
			 }
			catch(AxisFault af)
			 {
				log(af.toString());
				return;
			 }
			catch(Exception e)
			 {
				log(e.toString());
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
		
		Servicio Goten = new Servicio(name_service_goten);
		if(Goten.getError()==-100 || Goten.getError()==-101)
			return Goten.getError()-20;
		
		//Obtenemos los datos de la apueta
		datos = verApuesta(id_a+"");
		
		if(datos.equals("-1"))
			return -10;
		
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
				
				opts.setAction("comprobarApuestaPartido");
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference(Goten.getEndpoint()));
				sc.setOptions(opts);
				
				//Insertamos la cabecera con el hash para autentificarnos en el sistema.
				OMFactory fac = OMAbstractFactory.getOMFactory();
				OMNamespace omNs = fac.createOMNamespace("", "");
				OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
				cabeceraHash.setText(pass.hashCode()+"");
				sc.addHeader(cabeceraHash);
				
				
				OMElement res = sc.sendReceive(comprobarApuestaPartido(id_a+""));
								
				//Obtengo del OMElement el id de apuesta.
				cuota = Double.parseDouble(res.getFirstElement().getText());
				
				sc.cleanupTransport();
				
				//Si la cuota es negativa es que ha sucedido algún error.
				if(cuota<0)
					return cuota;
				
			 }
			catch(AxisFault af)
			 {
				log(af.toString());
				return -11;
			 }
			catch(Exception e)
			 {
				log(e.toString());
				return -12;
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
				
				opts.setAction("comprobarApuestaPichichi");
				
				//Asignamos en las opciones la referencia al servicio externo.
				opts.setTo(new EndpointReference(Goten.getEndpoint()));
				sc.setOptions(opts);
				OMElement res = sc.sendReceive(comprobarApuestaPichichi(id_a+""));
				
				//Insertamos la cabecera con el hash para autentificarnos en el sistema.
				OMFactory fac = OMAbstractFactory.getOMFactory();
				OMNamespace omNs = fac.createOMNamespace("", "");
				OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
				cabeceraHash.setText(pass.hashCode()+"");
				sc.addHeader(cabeceraHash);
				
				
				//Obtengo del OMElement el id de apuesta.
				cuota = Double.parseDouble(res.getFirstElement().getText());
				
				sc.cleanupTransport();
								
				//Si la cuota es negativa es que ha sucedido algún error.
				if(cuota<0)
					return cuota;
			 }
			catch(AxisFault af)
			 {
				log(af.toString());
				return -13;
			 }
			catch(Exception e)
			 {
				log(e.toString());
				return -14;
			 }
		 }
		else
		 {
			return -15;
		 }
		
		try
		 {
			importe = Double.parseDouble(importe_s);
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -16;
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
		
		Servicio Mundial = new Servicio(name_service_mundial);
		if(Mundial.getError()==-100 || Mundial.getError()==-101)
		 {
			lista.add("Error: "+(Mundial.getError()-30));
			return lista;
		 }
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
						
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(Mundial.getEndpoint()));
	
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
		catch(AxisFault af)
		 {
			log(af.toString());
			lista.clear();
			lista.add("Error: -1");
		 }
		catch(OMException oe)
		 {
			log(oe.toString());
			lista.clear();
			lista.add("Error: -2");
		 }
		catch(Exception e)
		 {
			log(e.toString());
			lista.clear();
			lista.add("Error: -3");
		 }
		
		if(lista.size()==0) return null;
		return lista;
	 }
	
	
	/**
	 * Permite obtener un listado de los Jugadores de un determinado equipo convocados en la competición
	 * @param equipo -> Nombre del equipo al que petenecen los jugadores que queremos obtener. En caso de vacio obtendrá todos los jugadores de cualquier equipo.
	 * @return -> Listado de Jugadores.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> listarJugadoresEquipo(String equipo)
	 {
		ArrayList<String> lista = new  ArrayList<String>();
		
		Servicio Mundial = new Servicio(name_service_mundial);
		if(Mundial.getError()==-100 || Mundial.getError()==-101)
		 {
			lista.add("Error: "+(Mundial.getError()-30));
			return lista;
		 }
		
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
				opts.setTo(new EndpointReference(Mundial.getEndpoint()));
				
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
			catch(AxisFault af)
			 {
				log(af.toString());
				lista.clear();
				lista.add("Error: -1");
			 }
			catch(OMException oe)
			 {
				log(oe.toString());
				lista.clear();
				lista.add("Error: -2");
			 }
			catch(Exception e)
			 {
				log(e.toString());
				lista.clear();
				lista.add("Error: -3");
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
				opts.setTo(new EndpointReference(Mundial.getEndpoint()));
				
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
			catch(AxisFault af)
			 {
				log(af.toString());
				lista.clear();
				lista.add("Error: -4");
			 }
			catch(OMException oe)
			 {
				log(oe.toString());
				lista.clear();
				lista.add("Error: -5");
			 }
			catch(Exception e)
			 {
				log(e.toString());
				lista.clear();
				lista.add("Error: -6");
			 }
		 }
		
		if(lista.size()==0) return null;
		return lista;
	 }
	
	
	/**
	 * Listado de los partidos que va a jugar un equipo en concreto, o todos los partidos de la competición
	 * @param equipo -> Nombre del equipo del que queremos listar sus partidos. En caso de no pasarlo se listará todos los partidos de la competición.
	 * @return Devuelve un listado de objeto partidos con el identificador del partido además los equipos que juegan en el partido.
	 */
	public ArrayList<Partido> listarPartidos(String equipo)
	 {
		ArrayList<Partido> listado = new ArrayList<Partido>();
		int id=0;
		String local="", visitante="";
		
		Servicio Mundial = new Servicio(name_service_mundial);
		if(Mundial.getError()==-100 || Mundial.getError()==-101)
		 {
			listado.add(new Partido(Mundial.getError()-30, "Error: "+(Mundial.getError()-30), ""));
			return listado;
		 }
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(Mundial.getEndpoint()));
			
			//Llamamos al servicio externo para obtener la información de todos los partidos del calendario en la competición.
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(allGames());
			@SuppressWarnings("unchecked")
			Iterator<OMElement> it1 = res.getFirstElement().getChildElements();
			OMElement partido;
			
			//Recorremos el XML para obtener el identificador y los equipos que participan en el partido.
			while(it1.hasNext())
			 {
				partido = it1.next();
				id = Integer.parseInt(partido.getFirstElement().getText());
				local = ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team1").next()).getChildrenWithLocalName("sName").next()).getText();
				visitante = ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team2").next()).getChildrenWithLocalName("sName").next()).getText();
				
				if(equipo==null || local.toLowerCase().contains(equipo.toLowerCase()) || visitante.toLowerCase().contains(equipo.toLowerCase()) || equipo.equals(""))
				 {
					listado.add(new Partido(id, local, visitante));
				 }
			 }
			
			sc.cleanupTransport();
			
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			listado.clear();
			listado.add(new Partido(-1, "Error: -1", ""));
		 }
		catch(OMException oe)
		 {
			log(oe.toString());
			listado.clear();
			listado.add(new Partido(-2, "Error: -2", ""));
		 }
		catch(Exception e)
		 {
			log(e.toString());
			listado.clear();
			listado.add(new Partido(-3, "Error: -3", ""));
		 }
		
		if(listado.size()==0) return null;
		return listado;
	 }
	
	
	/**
	 * Devuelve un objeto partido a partir de un id de partido.
	 * @param id_p -> identificador del partido del que queremos obtener la información.
	 * @return Devuelve un objeto partido con el id y los participantes.
	 */
	public Partido getPartido(int id_p)
	 {
		int id=0;
		String local="", visitante="";
		
		Servicio Mundial = new Servicio(name_service_mundial);
		if(Mundial.getError()==-100 || Mundial.getError()==-101)
		 {
			return (new Partido(Mundial.getError()-30, "Error: "+(Mundial.getError()-30), ""));
		 }
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(Mundial.getEndpoint()));
			
			//Llamamos al servicio externo para obtener la información de todos los partidos del calendario en la competición.
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(gameInfo(id_p+""));
			OMElement partido = (OMElement)res.getFirstElement();
			
			id = Integer.parseInt(partido.getFirstElement().getText());
			local = ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team1").next()).getChildrenWithLocalName("sName").next()).getText();
			visitante = ((OMElement)((OMElement)partido.getChildrenWithLocalName("Team2").next()).getChildrenWithLocalName("sName").next()).getText();
						
			sc.cleanupTransport();
			
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			if(af.getMessage().contains("Game ID not found!"))
				return null;
			return (new Partido(-1, "Error: -1", ""));
		 }
		catch(OMException oe)
		 {
			log(oe.toString());
			return (new Partido(-2, "Error: -2", ""));
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return (new Partido(-3, "Error: -3", ""));
		 }
		if(id==0) return null;
		return new Partido(id, local, visitante);
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
	
	private OMElement gameInfo(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("GameInfo", omNs);
		OMElement value = fac.createOMElement("iGameId", omNs);
		value.setText(valor);
		method.addChild(value);
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
		OMElement value1 = fac.createOMElement("id_partido", omNs);
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
		OMElement value = fac.createOMElement("id_apuesta", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	 
	private OMElement comprobarApuestaPichichi(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("comprobarApuestaPichichi", omNs);
		OMElement value = fac.createOMElement("id_apuesta", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	
	
	//*************************************************************************************************************************//
	//					Métodos para contruir el mensaje de llamada a los servicios de publicación de UDDI 					   //
	//*************************************************************************************************************************//
	
	private static OMElement save_service(String authtoken, String nombre, String ser_key, String bus_key, String access_point)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("urn:uddi-org:api_v3", "");
		OMNamespace omNs1 = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("save_service", omNs);
		OMElement auth = fac.createOMElement("authInfo", omNs);
		OMElement business = fac.createOMElement("businessService", omNs);
		OMElement bts = fac.createOMElement("bindingTemplates", omNs);
		OMElement bt = fac.createOMElement("bindingTemplate", omNs);
		OMElement ap = fac.createOMElement("accessPoint", omNs);
		OMElement name = fac.createOMElement("name", omNs);
		
		auth.setText(authtoken);
	    method.addChild(auth);

		ap.setText(access_point);
		bt.addChild(ap);
		bts.addChild(bt);
		name.setText(nombre);
		business.addChild(name);
		business.addChild(bts);
		business.addAttribute("serviceKey", ser_key, omNs1);
		business.addAttribute("businessKey", bus_key, omNs1);
	    method.addChild(business);
	    
		return method;
	 }
	
	
	private static OMElement delete_service(String authtoken, String ser_key)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("urn:uddi-org:api_v3", "");
		OMElement method = fac.createOMElement("delete_service", omNs);
		OMElement auth = fac.createOMElement("authInfo", omNs);
		OMElement sk = fac.createOMElement("serviceKey", omNs);
		
		auth.setText(authtoken);
		sk.setText(ser_key);
		
		method.addChild(auth);
		method.addChild(sk);
		
		return method;
	 }
	
	
	private static OMElement get_authToken(String user, String pass)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("urn:uddi-org:api_v3", "");
		OMNamespace omNs1 = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("get_authToken", omNs);
		OMAttribute att1 = fac.createOMAttribute("userID", omNs1, user);
		OMAttribute att2 = fac.createOMAttribute("cred", omNs1, pass);
		method.addAttribute(att1);
		method.addAttribute(att2);
		return method;
	 }
	
}