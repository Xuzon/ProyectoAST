package org.apache.ws.axis2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.ServiceLifeCycle;
import org.apache.log4j.varia.NullAppender;

public class Goten implements ServiceLifeCycle {
	
	//Nombre del fichero que almacena las apuestas.
	private static final String fichero_apuestas="apuestas.txt";
	private static final String directorio_apuestas= System.getProperty("user.home")+"/AST/db";
	
	//Nombre y localización del fichero de logs para este programa.
	private static final String fichero_log = "goten_log.txt";
	private static final String directorio_log = System.getProperty("user.home")+"/AST/log";
	
	//Nombres de los servicios en UDDI
	@SuppressWarnings("unused")
	private static final String name_service_goku = "Goku";
	private static final String name_service_mundial = "Mundial";
	
	
	//Variables para realizar el registro en UDDI
	private static String dir_uddi_publish;
	private static String dir_uddi_security;
	private static final String uddi_user = "games";
	private static final String uddi_pass = "games";
	private static final String service_name = "Goten";
	private static final String service_key = "uddi:localhost:servicios-propios-"+service_name.toLowerCase();
	private static final String business_key = "uddi:localhost:key";
	
	//Contraseña para función hash
	private static final String pass = "password";
	
	private static final String email_cliente = "cliente.apuesta@gmail.com";
	
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
			arg1.engageModule(new AxisModule("Log"));

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
		try{
		    Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		    for (; n.hasMoreElements();){
		        NetworkInterface e = n.nextElement();
		        Enumeration<InetAddress> a = e.getInetAddresses();
		        for (; a.hasMoreElements();){
		            InetAddress addr = a.nextElement();
		            String ipTemp = addr.getHostAddress();
		            if(ipTemp.contains("192.") || ipTemp.contains("10.")){
		            	ip = ipTemp;
		            	break;
		            }
		        }
		    }
		 }catch(Exception e){
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
			//uddi:localhost:key
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
	 * Permite obtener los datos de una determinada apuesta separados por "//".
	 *     - Para apuesta a partidos: Identificador de partido, goles del equipo local, goles del equipo visitante, cuota que se paga por la apuesta.
	 *     - Para apuesta a pichichi: Nombre jugador, cuota que se paga por la apuesta.
	 * En caso de pasar como identificador 0, retornará el identificador mayor actual incrementado en una unidad.
	 * @param id_a -> Idenificador de la apuesta.
	 * @return Datos de la apuesta.
	 */
	private static String verApuesta(String id_a)
	 {
		String apuesta="-1";
		ArrayList<Integer> id = new ArrayList<Integer>();
		int mayor=0;
		
		BufferedReader in = null;
		
		File f = new File(directorio_apuestas+"/"+fichero_apuestas);
		if(f.exists())
		 {
			try
			 {
				in = new BufferedReader(new FileReader(f));
				String l_apuesta="";
				while((l_apuesta=in.readLine())!=null)
				 {
					if(l_apuesta.equals("")) continue;
					if(id_a.equals("0"))
					 {
						id.add(Integer.parseInt(l_apuesta.split("-.-")[0]));
					 }
					else if(l_apuesta.split("-.-")[0].equals(id_a))
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
		
			if(id_a.equals("0"))
			 {
				for(int i=0; i<id.size(); i++)
				 {
					if(id.get(i)>mayor) mayor=id.get(i);
				 }
				return (mayor+1)+"";
			 }
		 }
		else
		 {
			if(id_a.equals("0"))
				apuesta = "1";
		 }
		return apuesta;
	 }
	
	
	/**
	 * Almacena los datos de una determinada apuesta separado por "//";
	 *     - Para apuesta a partidos: Identificador de partido, goles del equipo local, goles del equipo visitante, cuota que se paga por la apuesta.
	 *     - Para apuesta a pichichi: Nombre jugador, cuota que se paga por la apuesta.
	 * @param id_a -> Identificador de la apuesta.
	 * @param datos -> Datos de la apuesta separado por "//";
	 */
	private static void guardarApuesta(int id_a, String datos)
	 {
		BufferedWriter out = null;
		
		File dir = new File(directorio_apuestas);
		dir.mkdirs();
		
		try
		 {
			out = new BufferedWriter(new FileWriter(dir+"/"+fichero_apuestas, true));   
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
			out.write(fecha+":"+" Goten -- "+datos+"\n");
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
	 * Permite comprobar el resultado de una apuesta a un partido.
	 * @param id_a -> Identificador de la apuesta.
	 * @return Cuota por la que se paga en caso de haber acertado.
	 */
	public double comprobarApuestaPartido(int id_a)
	 {
		OMElement res = null;
		String apostado="", resultado="", goles1="", goles2="";
		double cuota=0;
		
		//Obtenemos los datos de la apuesta.
		apostado = verApuesta(id_a+"");
		
		if(apostado.equals("-1"))
			return -1;
		
		String[] datos = apostado.split("//");
		
		//Creamos el objeto servicio que nos permitirá obtener el endPoint.
		Servicio mundial = new Servicio(name_service_mundial);
		if(mundial.getError()==-100 || mundial.getError()==-101)
		 {
			return mundial.getError()-30;
		 }
		
		try
		 {
			cuota = Double.parseDouble(datos[4]);
			
			
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			opts.setAction("gameInfo");
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(mundial.getEndpoint()));
	
			//Llamamos al servicio GetCardType
			sc.setOptions(opts);
			res = sc.sendReceive(gameInfo(datos[1]));
		
			//Obtenemos los resultados del partido que corresponde a la apuesta.
			//Separamos los goles del visitante y del local.
			resultado = ((OMElement)res.getFirstElement().getChildrenWithLocalName("sScore").next()).getText();
			goles1 = resultado.split("-")[0];
			goles2 = resultado.split("-")[1];
			sc.cleanupTransport();
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -2;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -3;
		 }
		
		//En caso de que la apuesta haya sido acertada retornamos la cuota de la apuesta.
		//En otro caso retornamos 0.
		if(goles1.equals(datos[2]) && goles2.equals(datos[3]))
		 {
			return cuota;
		 }
		else
		 {
			return 0;
		 }
	 }
	
	
	/**
	 * Permite comprobar el resultado de una apuesta a un pichichi.
	 * @param id_a -> Identificador de la apuesta.
	 * @return Cuota por la que se paga en caso de haber acertado.
	 */
	public double comprobarApuestaPichichi(int id_a)
	 {
		ServiceClient sc = null;
		OMElement res = null;
		String apostado="";
		double cuota=0;
		
		//Obtenemos los datos de la apuesta
		apostado = verApuesta(id_a+"");
		if(apostado.equals("-1"))
			return -4;
		
		ArrayList<String> goleadores = new ArrayList<String>();
		String[] datos = apostado.split("//");
		
		//Creamos el objeto servicio que nos permitirá obtener el endPoint.
		Servicio mundial = new Servicio(name_service_mundial);
		if(mundial.getError()==-100 || mundial.getError()==-101)
		 {
			return mundial.getError()-30;
		 }
		
		try
		 {
			
			cuota = Double.parseDouble(datos[2]);
			
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());

			//Instanciamos el servicio de cliente y las opciones
			sc = new ServiceClient();
			Options opts = new Options();

			opts.setAction("topGoalScores");
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference(mundial.getEndpoint()));		
			
			//Llamamos al servicio GetCardType
			sc.setOptions(opts);
			
			res = sc.sendReceive(topGoalScores());
			
			//Obtenemos el nombre de los 3 máximos goleadores de la competición.
			@SuppressWarnings("unchecked")
			Iterator<OMElement> it = res.getFirstElement().getChildElements();
			while(it.hasNext())
			 {
				goleadores.add(((OMElement)it.next().getChildrenWithLocalName("sName").next()).getText());
			 }

			sc.cleanupTransport();
		 }
		catch(AxisFault af)
		 {
			log(af.toString());
			return -5;
		 }
		catch(NumberFormatException ne)
		 {
			log(ne.toString());
			return -6;
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -7;
		 }
	
		//Si el jugador por el que se a apostado se sitúa de primero en la lista de goleadores se devuelve la cuota integra, en caso de estar en segundo lugar
		//se devuelve la mitad de la cuota y en caso de estar de tercero la cuarta parte de la cuota, en otro caso 0.
		if(datos[1].equals(goleadores.get(0)))
		 {
			return cuota;
		 }
		else if(datos[1].equals(goleadores.get(1)))
		 {
			cuota = cuota/2;
			cuota = Math.rint(cuota*100)/100;
			return cuota;
		 }
		else if(datos[1].equals(goleadores.get(2)))
		 {
			cuota = cuota/4;
			cuota = Math.rint(cuota*100)/100;
			return cuota;
		 }
		else
			return 0.0;
	 }
	
	
	/**
	 * Permite realizar una apuesta a un partido.
	 * @param id_p -> Identificado del partido por el que se quiere apostar.
	 * @param goles_e1 -> Goles del equipo local.
	 * @param goles_e2 -> Goles del equipo visitante.
	 * @return -> Retorna el identificador con el que se a almacenado la apuesta.
	 */
	public int realizarApuestaPartido(int id_p, int goles_e1, int goles_e2)
	 {
		double cuota=0;
		
		//Obtenemos el nuevo identificador de la apuesta.
		String sid_a = verApuesta("0");
		int id_a=0;
		try
		 {
			id_a = Integer.parseInt(sid_a);
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -1;
		 }
		
		//Se calcula una cuota aleatoria
		//Este proceso se repite en caso de dar el valor 1
		while((cuota=Math.random()*25+1)==1);
		//Redondeamos la cuota a 2 decimales
		cuota = Math.rint(cuota*100)/100;
		
		//Almacenamos la apuesta.
		guardarApuesta(id_a, "A//"+id_p+"//"+goles_e1+"//"+goles_e2+"//"+cuota+"//"+email_cliente);
		
		return id_a;
	 }
	
	
	/**
	 * Permite realizar una apuesta a un jugador.
	 * @param jugador -> Nombre del jugador por el que se quiere apostar.
	 * @return -> Retorna el identificador con el que se a almacenado la apuesta.
	 */
	public int realizarApuestaPichichi(String jugador)
	 {
		double cuota=0;
		
		//Obtenemos el nuevo identificador de la apuesta.
		String sid_a = verApuesta("0");
		int id_a=0;
		try
		 {
			id_a = Integer.parseInt(sid_a);
		 }
		catch(Exception e)
		 {
			log(e.toString());
			return -1;
		 }
		
		if(jugador==null) return -1;
		
		//Se calcula una cuota aleatoria
		//Este proceso se repite en caso de dar el valor 1
		while((cuota=Math.random()*25+1)==1);
		//Redondeamos la cuota a 2 decimales
		cuota = Math.rint(cuota*100)/100;
		
		//Almacenamos los datos de la apuesta.
		guardarApuesta(id_a, "B//"+jugador+"//"+cuota+"//"+email_cliente);
		
		return id_a;
	}
	
	
	/**
	 * Este método es llamado exclusivamente por el administrador para indicar que a finalizado un partido.
	 * Comprobará si existe alguna apuesta referente a este partido y llamará a la función apuestaFinalizada del servidor principal
	 * que llevará a cabo el pago del mismo.
	 * @param id_p identificador del partido
	 */
	public void partidoFinalizado(int id_p)
	 {
		log("estas en partido finalizado");
		BufferedReader in = null;
		String apuesta;
		String cuota;
		int id_partido_listado;
		ArrayList <Integer> id_apuesta_partido= new ArrayList <Integer>();
		ArrayList <String> correos = new ArrayList<String>();
		File f = new File(directorio_apuestas+"/"+fichero_apuestas);
		Servicio goku= new Servicio("Goku");
		if(f.exists())
		{
			
			try
			{
				ServiceClient sc= new ServiceClient();
				Options opt=null;
				opt = new Options();
				opt.setTo(new EndpointReference(goku.getEndpoint()));
				opt.setAction("apuestaFinalizada");
				
				//Insertamos la cabecera con el hash para autentificarnos en el sistema.
				OMFactory fac = OMAbstractFactory.getOMFactory();
				OMNamespace omNs = fac.createOMNamespace("", "");
				OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
				cabeceraHash.setText(pass.hashCode()+"");
				sc.addHeader(cabeceraHash);
				
				in = new BufferedReader(new FileReader(f));
				while((apuesta=in.readLine())!=null)
				{
					if(apuesta.equals("")) continue;
					String [] datos=apuesta.split("-.-",2);
					String id_a=datos[0];
					String datos_apuesta=datos[1];
					if(datos_apuesta.startsWith("A"))
					{
						id_partido_listado = Integer.parseInt(datos_apuesta.split("//")[1]);
						if(id_p == id_partido_listado){
							id_apuesta_partido.add(Integer.parseInt(id_a));
							correos.add(datos_apuesta.split("//")[5]);//para el WS-ADDRESSING
						}
					}
				}
				in.close();

				for(int i=0;i<id_apuesta_partido.size();i++)
				{	
					cuota=String.valueOf(comprobarApuestaPartido(id_apuesta_partido.get(i)));
					opt.setReplyTo(new EndpointReference(correos.get(i)));//para el WS-ADDRESSING
					sc.setOptions(opt);//se vuelven a aplicar las opciones al serviceclient
					sc.fireAndForget(apuestaFinalizada(id_apuesta_partido.get(i).toString(),cuota));
					sc.cleanupTransport();
				}
			}
			catch (Exception e)
			{
				log(e.toString());
			}
		}
	 }
	
	
	/**
	 * Este método es llamado exclusivamente por el administrador para indicar que a finalizado la competición.
	 * Llamará a la función apuestaFinalizada del servidor principal por cada apuesta, que llevará a cabo el pago.
	 */
	public void competicionFinalizada()
	 {
		log("estas en competicion finalizada");
		BufferedReader in = null;
		String apuesta;
		String cuota;
		ArrayList <Integer> id_apuesta_partido= new ArrayList <Integer>();
		ArrayList <Integer> id_apuesta_jugador= new ArrayList <Integer>();
		ArrayList <String> correosPartidos = new ArrayList<String>();
		ArrayList <String> correosJugadores = new ArrayList<String>();
		File f = new File(directorio_apuestas+"/"+fichero_apuestas);
		Servicio goku= new Servicio("Goku");
		if(f.exists())
		{
			try
			{
				ServiceClient sc= new ServiceClient();
				Options opt=new Options();
				opt.setTo(new EndpointReference(goku.getEndpoint()));
				opt.setAction("apuestaFinalizada");
				//sc.setOptions(opt);
								
				//Insertamos la cabecera con el hash para autentificarnos en el sistema.
				OMFactory fac = OMAbstractFactory.getOMFactory();
				OMNamespace omNs = fac.createOMNamespace("", "");
				OMElement cabeceraHash = fac.createOMElement("cabeceraHash", omNs);
				cabeceraHash.setText(pass.hashCode()+"");
				sc.addHeader(cabeceraHash);
				
				in = new BufferedReader(new FileReader(f));
				while((apuesta=in.readLine())!=null)
				{
					if(apuesta.equals("")) continue;
					String [] datos=apuesta.split("-.-",2);
					String id_a=datos[0];
					String datos_apuesta=datos[1];
					if(datos_apuesta.startsWith("A"))
					{
						id_apuesta_partido.add(Integer.parseInt(id_a));
						correosPartidos.add(datos_apuesta.split("//")[5]);
					}
					else
					{
						id_apuesta_jugador.add(Integer.parseInt(id_a));
						correosJugadores.add(datos_apuesta.split("//")[3]);
					}
				}
				in.close();
				for(int j=0;j<id_apuesta_jugador.size();j++)
				{					
					cuota=String.valueOf(comprobarApuestaPichichi(id_apuesta_jugador.get(j)));
					opt.setReplyTo(new EndpointReference(correosJugadores.get(j)));
					sc.setOptions(opt);
					sc.fireAndForget(apuestaFinalizada(id_apuesta_jugador.get(j).toString(),cuota));
					sc.cleanupTransport();
				}
				for(int j=0;j<id_apuesta_partido.size();j++)
				{					
					cuota=String.valueOf(comprobarApuestaPartido(id_apuesta_partido.get(j)));
					opt.setReplyTo(new EndpointReference(correosPartidos.get(j)));
					sc.setOptions(opt);
					sc.fireAndForget(apuestaFinalizada(id_apuesta_partido.get(j).toString(),cuota));
					sc.cleanupTransport();
				}
			}
			catch (Exception e)
			{
				log(e.toString());
			}
		}
		
	 }
	
	
	
	//*************************************************************************************************************************//
	//							Métodos para contruir el mensaje de llamada a cada servicio externo							   //
	//*************************************************************************************************************************//

	private OMElement apuestaFinalizada(String id_a,String cuota)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("apuestaFinalizada", omNs);
		OMElement value= fac.createOMElement("id_apuesta",omNs);
		OMElement value1= fac.createOMElement("cuota_resultante",omNs);
		value.setText(id_a);
		value1.setText(cuota);
		method.addChild(value);
		method.addChild(value1);
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
	
	private OMElement topGoalScores()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://footballpool.dataaccess.eu", "");
		OMElement method = fac.createOMElement("TopGoalScorers", omNs);
		OMElement value = fac.createOMElement("iTopN", omNs);
		value.setText("3");
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