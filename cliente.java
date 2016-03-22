import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.varia.NullAppender;


public class cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String opcion;
		Scanner scan = new Scanner(System.in);
		
		//listadoEquipos();
		//listadoJugadores("Spain");
		//System.out.println(listadoPartidos("Spain").size());
		//System.out.println(comprobarApuesta(78));
		//System.out.println(apostarPartido(2, 5, 3, 10.55, "656565654654", "0817"));
		//System.out.println(apostarPichichi("James Rodríguez", 4.54, "589898986540", "0819"));
		while(true)
		 {
			System.out.println("\n\n\n");
			System.out.println("##############################");
			System.out.println("#       MENÚ PRINCIPAL       #");
			System.out.println("##############################");
			System.out.println("    1) Apostar a un partido");
			System.out.println("    2) Apostar al máximo goleador");
			System.out.println("[Q/q]) Salir");
			System.out.print("\nElije una opción: ");
			opcion="";
			opcion = scan.nextLine();
			scan.reset();
			if(opcion.equals("1"))
				apuesta1(scan);
			else if(opcion.equals("2"))
				apuesta2(scan);
			else if(opcion.toLowerCase().equals("q"))
				break;
		 }
			
	}

	public static void apuesta1(Scanner scan)
	 {
		String opcion;
		while(true)
		 {
			System.out.println("\n\n\n");
			System.out.println("##############################");
			System.out.println("#    APOSTAR A UN PARTIDO    #");
			System.out.println("##############################");
			System.out.println("    1) A través de su ID");
			System.out.println("    2) Listar primero los equipos que participan en la competición");
			System.out.println("    3) Listar primero todos los partidos de la competición");
			System.out.println("[Q/q]) Salir");
			System.out.print("\nElije una opción: ");
			opcion="";
			opcion = scan.nextLine();
			scan.reset();
			if(opcion.equals("1"))
			 {
				
				
				
				
				
			 }
			else if(opcion.equals("2"))
			 {
				
				ArrayList<String> equipos = new ArrayList<String>();
				equipos = listadoEquipos();
				System.out.println("\n\n\n");
				System.out.println("Listado de los equipos participantes:");
				for(int i=0; i<equipos.size(); i++)
					System.out.println(" -> "+(i+1)+" - "+equipos.get(i));
				
				System.out.print("\n\nEscriba el identificador del equipo para listar todos los partidos del mismo (0 para listar todos los partidos): ");
				opcion="";
				opcion = scan.nextLine();
				scan.reset();
				
				String equipo="";
				try {
					int num = Integer.parseInt(opcion)-1;
					if(num>=0 && num<equipos.size()) equipo = equipos.get(num);
				 }
				catch(Exception e){
					
				 }
				
				
				ArrayList<Partido> partidos = new ArrayList<Partido>();
				partidos = listadoPartidos(equipo);
				System.out.println("\n\n\n");
				System.out.println("Listado de los partidos ("+equipo+"):");
				for(int j=0; j<partidos.size(); j++)
					System.out.println(partidos.get(j).toString());
				
				System.out.println("\n");
				System.out.println("##############################");
				System.out.println("#    APOSTAR A UN PARTIDO    #");
				System.out.println("##############################");
				System.out.print("Id del partido: ");
				String id_p = scan.nextLine();
				scan.reset();
				System.out.print("Goles del equipo local: ");
				String goles_e1 = scan.nextLine();
				scan.reset();
				System.out.print("Goles del equipo visitante: ");
				String goles_e2 = scan.nextLine();
				scan.reset();
				
				int id_a = apostarPartido(Integer.parseInt(id_p), Integer.parseInt(goles_e1), Integer.parseInt(goles_e2), 54, "", "");
				
				if(id_a>0) System.out.println("\n\nApuesta realizada con id: "+id_a);
				else System.out.println("\n\nHa ocurrido algún problema");
				
			 }
			else if(opcion.equals("3"))
			 {
				
			 }
			else if(opcion.toLowerCase().equals("q"))
				break;
		 }
	 }
	
	public static void apuesta2(Scanner scan)
	 {
		
	 }
	
	
	
	
	//*************************************************************************************************************************//
	//								Métodos de llamada al servicio de interfaz principal									   //
	//*************************************************************************************************************************//
	
	
	/**
	 * Realiza una apuesta por un jugador en concreto.
	 * @param jugador -> Nombre del jugador.
	 * @param importe -> Importe que se desea apostar.
	 * @param tarjeta -> Número de la tarjeta.
	 * @param f_cad -> Fecha de caducidad de la tarjeta.
	 * @return Devuelve el identificador de la apuesta.
	 */
	public static int apostarPichichi(String jugador, double importe, String tarjeta, String f_cad)
	 {
		int id_a=0;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_apostarPichichi(jugador, importe+"", tarjeta, f_cad));
			
			id_a = Integer.parseInt(res.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		return id_a;
	 }
	
	
	
	
	
	/**
	 * Realiza una nueva apuesta a un partido pendiente de realizar.
	 * @param id_p -> Identificador del partido por el que se va a apostar.
	 * @param goles_e1 -> Goles del equipo local.
	 * @param goles_e2 -> Goles del equipo visitante.
	 * @param importe -> Importe que se desea apostar.
	 * @param tarjeta -> Número de la tarjeta.
	 * @param f_cad -> Fecha de caducidad de la tarjeta.
	 * @return Devuelve el identificador de la apuesta.
	 */
	public static int apostarPartido(int id_p, int goles_e1, int goles_e2, double importe, String tarjeta, String f_cad)
	 {
		int id_a=0;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_apostarPartido(id_p+"", goles_e1+"", goles_e2+"", importe+"", tarjeta, f_cad));
			
			id_a = Integer.parseInt(res.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }

		return id_a;
	 }
	
	
	/**
	 * Permite consultar la ganancia de una determinada apuesta (partido o pichichi).
	 * @param id_a -> Identificador de la apuesta.
	 * @return Retorna el beneficio de la apuesta.
	 */
	public static double comprobarApuesta(int id_a)
	 {
		double salida=0;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_comprobarApuesta(id_a+""));
			
			salida = Double.parseDouble(res.getFirstElement().getText());
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		
		return salida;
	 }
	
	
	
	/**
	 * Listado de partidos que jugará un equipo en concreto o todos.
	 * @param equipo -> Nombre del equipo.
	 * @return Devuelve un array de partidos
	 */
	public static ArrayList<Partido> listadoPartidos(String equipo)
	 {
		
		ArrayList<Partido> listado = new ArrayList<Partido>();
		String sid_p="", participantes="";
		int id_p = 0;
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarPartidos(equipo));
			
			Iterator<OMElement> it1 = res.getFirstElement().getChildElements();
			Iterator<OMElement> it2 = null;
			
			//Añadimos al listado cada uno de los partidos, creado el objeco a partir del id y los participantes obtenidos.
			while(it1.hasNext())
			 {
				it2 = it1.next().getChildElements();
				sid_p = it2.next().getText();
				participantes = it2.next().getText();
				
				id_p = Integer.parseInt(sid_p);
				listado.add(new Partido(id_p, participantes));
			 }
			sc.cleanupTransport();
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		return listado;
	 }
	
	/**
	 * Listado de los jugadores de un equipo. En caso de pasar una cadena vacia se listan todos los jugadores de la competición.
	 * @param equipo -> Nombre del equipo.
	 * @return Devuelve un ArrayList de String con los nombres de los jugadores.
	 */
	public static ArrayList<String> listadoJugadores(String equipo)
	 {
		ArrayList<String> listado = new ArrayList<String>();
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarJugadoresEquipo(equipo));
			
			Iterator<OMElement> it = res.getChildElements();
			while(it.hasNext())
			 {
				listado.add(it.next().getText());
			 }
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		return listado;
	 }
	
	
	
	
	/**
	 * Listado de Equipos que participan en la competición.
	 * @return Devuelve un ArrayList de String con todos los equipos de la competición.
	 */
	public static ArrayList<String> listadoEquipos()
	 {
		ArrayList<String> listado = new ArrayList<String>();
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference("http://localhost:8080/axis2/services/Goku"));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarEquipos());
			
			Iterator<OMElement> it = res.getChildElements();
			while(it.hasNext())
			 {
				listado.add(it.next().getText());
			 }
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		return listado;
	 }
	
	
	
	
	
	//*************************************************************************************************************************//
	//							Métodos para contruir el mensaje de llamada a cada servicio propio							   //
	//*************************************************************************************************************************//
	
	
	private static OMElement m_apostarPartido(String valor1, String valor2, String valor3, String valor4, String valor5, String valor6)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("apostarPartido", omNs);
		OMElement value1 = fac.createOMElement("id_p", omNs);
		OMElement value2 = fac.createOMElement("goles_e1", omNs);
		OMElement value3 = fac.createOMElement("goles_e2", omNs);
		OMElement value4 = fac.createOMElement("importe", omNs);
		OMElement value5 = fac.createOMElement("tarjeta", omNs);
		OMElement value6 = fac.createOMElement("f_cad", omNs);
		value1.setText(valor1);
		value2.setText(valor2);
		value3.setText(valor3);
		value4.setText(valor4);
		value5.setText(valor5);
		value6.setText(valor6);
		method.addChild(value1);
		method.addChild(value2);
		method.addChild(value3);
		method.addChild(value4);
		method.addChild(value5);
		method.addChild(value6);
		return method;
	 }
	
	private static OMElement m_apostarPichichi(String valor1, String valor2, String valor3, String valor4)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("apostarPichichi", omNs);
		OMElement value1 = fac.createOMElement("jugador", omNs);
		OMElement value2 = fac.createOMElement("importe", omNs);
		OMElement value3 = fac.createOMElement("tarjeta", omNs);
		OMElement value4 = fac.createOMElement("f_cad", omNs);
		value1.setText(valor1);
		value2.setText(valor2);
		value3.setText(valor3);
		value4.setText(valor4);
		method.addChild(value1);
		method.addChild(value2);
		method.addChild(value3);
		method.addChild(value4);
		return method;
	 }
	
	private static OMElement m_comprobarApuesta(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("comprobarApuesta", omNs);
		OMElement value = fac.createOMElement("id_a", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private static OMElement m_listarEquipos()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("listarEquipos", omNs);
		return method;
	 }
	
	private static OMElement m_listarJugadoresEquipo(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("listarJugadoresEquipo", omNs);
		OMElement value = fac.createOMElement("equipo", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private static OMElement m_listarPartidos(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("listarPartidos", omNs);
		OMElement value = fac.createOMElement("equipo", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
}
