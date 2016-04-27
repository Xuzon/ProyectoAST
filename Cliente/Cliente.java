import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Iterator;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.varia.NullAppender;
import java.io.*;


@SuppressWarnings("serial")
public class Cliente extends HttpServlet {
	
	private static final String name_service_goku = "Goku";
	public Servicio Goku;
	
	/**
	 * Cada petición get se administra dependiendo de los parámetros recibidos.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	 {
		//Obtenemos el servicio de uddi para acceder al endpoint.
		Goku = new Servicio(name_service_goku);
		
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();

		out.println("<html>");

		cabezaHTML(out);

		String apuesta = req.getParameter("apuesta");
		String fase = req.getParameter("fase");
		out.println("<body>");
		out.println("<div id='contido'>");
		out.println("<div id='contido-int'>");
		out.println("<h1>SHUT UP & TAKE MY MONEY</h1>");

		if(Goku.getError()==-100)
			out.println("<h3>Error al intentar acceder al servidor de JUDDI</h3>");
		else if(Goku.getError()==-101)
			out.println("<h3>El servicio solicitado no se encuentra listado</h3>");
		else
		 {
			//Segun el tipo de apuesta y la fase de la misma, mostramos unas opciones u otras.
			
			//Página principal, imprimimos las 3 opciones del menú.
			if(apuesta==null && fase==null)
			 {
				out.println("<h2>MENÚ PRINCIPAL</h2>");
				out.println("<form action='' method='post' name='x'>");
				out.println("<h3><a href='?apuesta=1'>Apostar a un partido</a></h3>");
				out.println("<h3><a href='?apuesta=2'>Apostar por el pichichi del mundial</a></h3>");
				out.println("<h3><a href='?apuesta=3'>Comprobar apuesta</a></h3>");
				out.println("</form>");
			 }
			//Mostramos las 3 opciones de apostar a un partido.
			else if(apuesta.equals("1") && fase==null)
			 {
				out.println("<h2>APOSTAR A UN PARTIDO</h2>");
				out.println("<h3><a href='?apuesta=1&fase=1'>Utilizar el identificador de partido</a></h3>");
				out.println("<h3><a href='?apuesta=1&fase=2'>Listar primero los equipos que participan en la competición</a></h3>");
				out.println("<h3><a href='?apuesta=1&fase=3'>Listar primero todos los partidos de la competición</a></h3>");
				out.println("<div id='botons'>");
			    out.println("<a href='?'>Inicio</a>");
			    out.println("</div>");
			 }
			//Mostramos las 3 opciones de apostar a pichichi.
			else if(apuesta.equals("2") && fase==null)
			 {
				out.println("<h2>APOSTAR POR EL PICHICHI DEL MUNDIAL</h2>");
				out.println("<h3><a href='?apuesta=2&fase=1'>Utilizar el numbre de jugador</a></h3>");
				out.println("<h3><a href='?apuesta=2&fase=2'>Listar primero los equipos que participan en la competición</a></h3>");
				out.println("<h3><a href='?apuesta=2&fase=3'>Listar todos los jugadores de la competición</a></h3>");
				out.println("<div id='botons'>");
			    out.println("<a href='?'>Inicio</a>");
			    out.println("</div>");
			 }
			else if(apuesta.equals("3"))
			 {
				apuesta3(req, out);
			 }
			//Fase intermedia
			else if(fase!=null)
			 {
				if(apuesta.equals("1") && fase.equals("0"))
				 {
					apuesta1(req, out);
				 }
				else if(apuesta.equals("2") && fase.equals("0"))
				 {
					apuesta2(req, out);
				 }
				else if(apuesta.equals("1") && fase.equals("1"))
				 {
					apuesta11(req, out);
				 }
				else if(apuesta.equals("2") && fase.equals("1"))
				 {
					apuesta21(req, out);
				 }		
				else if(fase.equals("2"))
				 {
					listarEquipos(req, out);
				 }
				else if(apuesta.equals("1") && fase.equals("3"))
				 {
					listarPartidos(req, out);
				 }
				else if(apuesta.equals("2") && fase.equals("3"))
				 {
					listarJugadores(req, out);
				 }
			 }
		 }
		pieHTML(out);
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	 }
	
	
	/**
	 * Las peticiones POST son de tres tipos
	 *    - Si el parámetro es el identificador de partido, se realiza una apuesta de partido.
	 *    - Si el parámetro es el nombre de jugador, se realiza una apuesta a pichichi.
	 *    - Si el paraámetro es el identificador de una apuesta, se comprueba el resultado de la misma.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	 {
		//Obtenemos el servicio de uddi para acceder al endpoint.
		Goku = new Servicio(name_service_goku);
		
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		
		out.println("<html>");

		cabezaHTML(out);

		out.println("<body>");
		out.println("<div id='contido'>");
		out.println("<div id='contido-int'>");
		out.println("<h1>SHUT UP AND TAKE MY MONEY</h1>");
		String id_p_s = req.getParameter("id_p");
		String jugador = req.getParameter("jugador");
		String id_a_comp = req.getParameter("id_a");
		//En caso de realizar una apuesta a un partido.

		if(id_p_s!=null)
		 {
			int id_a=0;
			try
			 {
				int id_p = Integer.parseInt(id_p_s);
				int goles_e1 = Integer.parseInt(req.getParameter("goles_e1"));
				int goles_e2 = Integer.parseInt(req.getParameter("goles_e2"));
				double importe = Double.parseDouble(req.getParameter("importe"));
				String tarjeta = req.getParameter("tarjeta");
				String f_cad = req.getParameter("f_cad1")+req.getParameter("f_cad2");
				id_a = apostarPartido(id_p, goles_e1, goles_e2, importe, tarjeta, f_cad);
				if(id_a>0)
					out.println("Apuesta realizada correctamente: "+ id_a);
				else
				 {
					out.println("<div id='error'>");
					
					switch(id_a)
					 {
					 	case -1: out.println("Error en el pago de la apuesta: Solo se admiten tarjeta VISA y MASTERCARD.");
					 		break;
					 	case -2: out.println("Error en el pago de la apuesta: La longitud del número de la tarjeta no es correcta.");
					 		break;
					 	case -3: out.println("Error en el pago de la apuesta: La fecha de caducidad de la tarjeta no es correcta o está caducada.");
					 		break;
					 	case -4: out.println("Error en el pago de la apuesta: Número de tarjeta incorrecto.");
					 		break;
					 	case -5: out.println("Error en el pago de la apuesta: No tiene saldo suficiente en la tarjeta.");
					 		break;
					 	case -6: case -7: case -10: case -11: out.println("Error en el pago de la apuesta.");
					 		break;
					 	case -13: case -14: out.println("Error al almacenar la apuesta.");
					 		break;
					 	case -12: out.println("Error: El servicio de apuestas no responde");
					 		break;
					 	case -110: case -120: case -130: case -140: out.println("Error: el servicio no encuentra el servidor juddi");
					 		break;
					 	case -111: out.println("Error: no se encuentra listado el servicio Gohan en el servidor uddi");
				 			break;
					 	case -121: out.println("Error: no se encuentra listado el servicio Goten en el servidor uddi");
			 				break;
					 	case -131: out.println("Error: no se encuentra listado el servicio Mundial en el servidor uddi");
			 				break;
					 	case -141: out.println("Error: no se encuentra listado el servicio CreditCardValidatos en el servidor uddi");
			 				break;			 			
					 	default: out.println("Error no conocido: "+id_a);
					 		break;
					 }
					
					out.println("</div>");
				 }
			 }
			catch(Exception e)
			 {
				out.println(e.toString());
				e.printStackTrace();
			 }
		 }
		//En caso de realizar una apuesta a un jugador.
		else if(jugador!=null)
		 {
			int id_a = 0;
			try
			 {
				double importe = Double.parseDouble(req.getParameter("importe"));
				String tarjeta = req.getParameter("tarjeta");
				String f_cad = req.getParameter("f_cad1")+req.getParameter("f_cad2");
				id_a = apostarPichichi(jugador, importe, tarjeta, f_cad);
				if(id_a>0)
					out.println("Apuesta realizada correctamente: "+ id_a);
				else
				 {
					out.println("<div id='error'>");
					
					switch(id_a)
					 {
					 	case -1: out.println("Error en el pago de la apuesta: Solo se admiten tarjeta VISA y MASTERCARD.");
					 		break;
					 	case -2: out.println("Error en el pago de la apuesta: La longitud del número de la tarjeta no es correcta.");
					 		break;
					 	case -3: out.println("Error en el pago de la apuesta: La fecha de caducidad de la tarjeta no es correcta o está caducada.");
					 		break;
					 	case -4: out.println("Error en el pago de la apuesta: Número de tarjeta incorrecto.");
					 		break;
					 	case -5: out.println("Error en el pago de la apuesta: No tiene saldo suficiente en la tarjeta.");
					 		break;
					 	case -6: case -7: case -10: case -11: out.println("Error en el pago de la apuesta.");
					 		break;
					 	case -13: case -14: out.println("Error al almacenar la apuesta.");
					 		break;
					 	case -12: out.println("Error: El servicio de apuestas no responde");
				 			break;
					 	case -110: case -120: case -130: case -140: out.println("Error: el servicio no encuentra el servidor juddi");
				 			break;
					 	case -111: out.println("Error: no se encuentra listado el servicio Gohan en el servidor uddi");
			 				break;
					 	case -121: out.println("Error: no se encuentra listado el servicio Goten en el servidor uddi");
			 				break;
					 	case -131: out.println("Error: no se encuentra listado el servicio Mundial en el servidor uddi");
			 				break;
					 	case -141: out.println("Error: no se encuentra listado el servicio CreditCardValidatos en el servidor uddi");
			 				break;
					 	default: out.println("Error no conocido: "+id_a);
					 		break;
					 }
					
					out.println("</div>");
				 }
				
			 }
			catch(Exception e)
			 {
				//out.println(e.toString());
			 }
		 }
		//Comprueba una apuesta en concreto.
		else if(id_a_comp!=null)
		 {
			try
			 {
				double ganancia=0;
				int idA_comp = Integer.parseInt(id_a_comp);
				ganancia = comprobarApuesta(idA_comp);
				
				if(ganancia==0)
				 {
					out.println("<h2>LA APUESTA "+idA_comp+" NO HA SALIDO BENEFICIADA</h2>");
				 }
				else if(ganancia>0)
				 {
					out.println("<h2>FELICIDADES!!! HA OBTENIDO UNAS GANACIAS DE "+ganancia+"€</h2>");
				 }
				else
				 {
					out.println("<div id='error'>");
					
					switch((int)Math.floor(ganancia))
					 {
					 	case -1: case -4: case -10: out.println("No se encuentra el identificador de la apuesta en la base de datos");
					 		break;
					 	case -2: case -3: case -12: out.println("Error con el identificador de la apuesta al partido");
					 		break;
					 	case -11:  out.println("Error: El servicio de apuestas no responde");
				 			break;
					 	case -5: case -6: case -7: case -14: out.println("Error con el identificador de la apuesta al pichichi");
					 		break;
					 	case -13:  out.println("Error: El servicio de apuestas no responde");
					 		break;
					 	case -15: out.println("No se reconoce el tipo de apuesta.");
					 		break;
					 	case -16: out.println("Error de importe apostado.");
					 		break;
					 	case -110: case -120: case -130: case -140: out.println("Error: el servicio no encuentra el servidor juddi");
					 		break;
					 	case -111: out.println("Error: no se encuentra listado el servicio Gohan en el servidor uddi");
				 			break;
					 	case -121: out.println("Error: no se encuentra listado el servicio Goten en el servidor uddi");
			 				break;
					 	case -131: out.println("Error: no se encuentra listado el servicio Mundial en el servidor uddi");
			 				break;
					 	case -141: out.println("Error: no se encuentra listado el servicio CreditCardValidatos en el servidor uddi");
			 				break;
					 	default: out.println("Error no conocido: "+ganancia);
					 		break;
					 }
					
					out.println("</div>");
				 }
			 }
			catch(Exception e)
			 {
				//out.println(e.toString());
				e.printStackTrace();
			 }
		 }
		else
		 {
			out.println("<div id='error'>Ha ocurrido un error. Por favor vuelva a intentarlos más tarde.</div>");
		 }
		out.println("<div id='botons'>");
	    out.println("<a href='?'>Inicio</a>");
	    out.println("</div>");
		pieHTML(out);
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	 }
	
	
	/**
	 * Muestra en pantalla el formulario para realizar una apuesta a un partido en concreto.
	 */
	public void apuesta1(HttpServletRequest req, PrintWriter out)
	 {
		String id_partido = req.getParameter("id_partido");
		String equipo1 = req.getParameter("equipo1");
		String equipo2 = req.getParameter("equipo2");
		if(equipo1!=null && equipo2!=null && id_partido!=null)
		 {
			out.println("<h3>Realizar apuesta a un partido</h3>");
			out.println(equipo1+" ----------------------------------- "+equipo2);
			out.println("<form action='?' method='post'>");
			out.println("<div><input type='number' min='0' name='goles_e1' required/>");
			out.println("<input type='number' min='0' name='goles_e2' required/></div>");
			out.println("<div>Nº tarjeta: <input type='text' name='tarjeta' required>&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("f. cad: <input type='text' name='f_cad1' maxlength='2' size='1' required>");
			out.println("/<input type='text' name='f_cad2' maxlength='2' size='1' required></div>");
			out.println("<div>Importe que desea apostar: <input type='number' step='any' min='0.01' name='importe' required>€</div>");
			out.println("<div><input type='submit' value='APOSTAR'><input type='reset' value='RESET'></div>");
			out.println("<input type='hidden' name='id_p' value='"+id_partido+"'>");
			out.println("</form>");
			out.println("<div id='botons'>");
		    out.println("<a href='javascript:history.back()'>Volver</a>");
		    out.println("<a href='?'>Inicio</a>");
		    out.println("</div>");
		 }
	 }
	
	
	/**
	 * Muestra en pantalla el formulario para realizar una apuesta a un pichichi.
	 */
	public void apuesta2(HttpServletRequest req, PrintWriter out)
	 {
		String jugador = req.getParameter("jugador");
		if(jugador!=null)
		 {
			out.println("<h3>Realizar apuesta al pichichi</h3>");
			out.println(jugador);
			out.println("<form action='?' method='post'>");
			out.println("<div>Nº tarjeta: <input type='text' name='tarjeta' required>&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type='hidden' name='jugador' value='"+jugador+"'>");
			out.println("f. cad: <input type='text' name='f_cad1' maxlength='2' size='1' required>");
			out.println("/<input type='text' name='f_cad2' maxlength='2' size='1' required></div>");
			out.println("<div>Importe que desea apostar: <input type='number' step='any' min='0.01' name='importe' required>€</div>");
			out.println("<div><input type='submit' value='APOSTAR'><input type='reset' value='RESET'></div>");
			out.println("</form>");
			out.println("<div id='botons'>");
		    out.println("<a href='javascript:history.back()'>Volver</a>");
		    out.println("<a href='?'>Inicio</a>");
		    out.println("</div>");
		 }
	 }
	
	/**
	 * Muestra en pantalla un campo para insertar un identificador de apuesta y comprobar el resultado de la misma.
	 */
	public void apuesta3(HttpServletRequest req, PrintWriter out)
	 {
		out.println("<h2>COMPROBAR APUESTA</h2>");
		out.println("<form action='?' method='post'>");
		out.println("<div>Identificador de la apuesta: <input type='number' min='1' name='id_a' required></div>");
		out.println("<div><input type='submit' value='ENVIAR'></div>");
		out.println("</form>");
		out.println("<div id='botons'>");
		out.println("<a href='?'>Inicio</a>");
	    out.println("</div>");
	 }
	
	
	/**
	 * Muestra un campo para insertar el identificador de un partido.
	 */
	public void apuesta11(HttpServletRequest req, PrintWriter out)
	 {
		String id_p = req.getParameter("id_p");
		if(id_p==null)
		 {
			out.println("<h3>Realizar apuesta a un partido</h3>");
			out.println("<form action='' method='get'>");
			out.println("<input type='hidden' name='apuesta' value='1'>");
			out.println("<input type='hidden' name='fase' value='1'>");
			out.println("<div>Identificador del partido: <input type='number' min='1' name='id_p' required></div>");
			out.println("<div><input type='submit' value='ENVIAR'></div>");
			out.println("</form>");
			out.println("<div id='botons'>");
		    out.println("<a href='javascript:history.back()'>Volver</a>");
		    out.println("<a href='?'>Inicio</a>");
		    out.println("</div>");
		 }
		//En caso de que llege por parámetros el id de partido, generaremos un formulario con los datos del mismo
		//y se enviará automaticamente mediante get para que el usuario rellene el resto de los datos.
		else
		 {
			Partido p = getPartido(Integer.parseInt(id_p));
			if(p==null)
			 {
				out.println("<div id='error'>No se han encontrado partidos con este identificador.</div>");
				out.println("<div id='botons'>");
				out.println("<a href='javascript:history.back()'>Volver</a>");
			    out.println("<a href='?'>Inicio</a>");
			    out.println("</div>");
			 }
			else if(p.getId_partido()<0)
			 {
				switch(p.getId_partido())
				 {
					 case -1: out.println(" Error al acceder al servicio de Mundial (gameInfo)");
				 		break;
					 case -2: out.println("Error al interpretar los datos recividos por el servidor Mundial (gameInfo)");
				 		break;
					 case -3: out.println("Error");
				 		break;
					case -110: out.println("Error: el servicio no encuentra el servidor juddi");
				 		break;
				 	case -131: out.println("Error: no se encuentra listado el servicio Mundial en el servidor uddi");
		 				break;
				 	default: out.println("Error no conocido: "+p.getId_partido());
				 		break;
				 }
				out.println("<div id='botons'>");
				out.println("<a href='javascript:history.back()'>Volver</a>");
			    out.println("<a href='?'>Inicio</a>");
			    out.println("</div>");
			 }
			else
			 {
				out.println("<form action='' method='get' name='datos'>");
				out.println("<input type='hidden' name='apuesta' value='1'>");
				out.println("<input type='hidden' name='fase' value='0'>");
				out.println("<input type='hidden' name='id_partido' value='"+p.getId_partido()+"'>");
				out.println("<input type='hidden' name='equipo1' value='"+p.getEquipo_local()+"'>");
				out.println("<input type='hidden' name='equipo2' value='"+p.getEquipo_visitante()+"'>");
				out.println("</form>");
				out.println("<script>document.datos.submit();</script>");
			 }
		 }
	 }
	
	
	/**
	 * Muestra en pantalla un campo para rellenar el nombre del jugador.
	 */
	public void apuesta21(HttpServletRequest req, PrintWriter out)
	 {
		out.println("<h3>Apuesta pichichi</h3>");
		out.println("<form action='' method='get'>");
		out.println("<input type='hidden' name='apuesta' value='2'>");
		out.println("<input type='hidden' name='fase' value='0'>");
		out.println("<div>Nombre del jugador: <input type='text' name='jugador' required></div>");
		out.println("<div><input type='submit' value='ENVIAR'></div>");
		out.println("</form>");
		out.println("<div id='botons'>");
	    out.println("<a href='javascript:history.back()'>Volver</a>");
	    out.println("<a href='?'>Inicio</a>");
	    out.println("</div>");
	 }
	
	
	/**
	 * Muestra un listado de todos los equipos de la competición.
	 */
	public void listarEquipos(HttpServletRequest req, PrintWriter out)
	 {
		String apuesta = req.getParameter("apuesta");
		String equipo = req.getParameter("equipo");
		if(apuesta != null & equipo==null)
		 {
			ArrayList<String> equipos = new ArrayList<String>();
			equipos = listadoEquipos();
			if(equipos==null)
				out.println("<div id='error'>No se han encontrado equipos.</div>");

			else if(equipos.get(0).contains("Error: -1"))
				out.println("<div id='error'>Error al acceder al servicio (teams) de Mundial</div>");
			
			else if(equipos.get(0).contains("Error: -2"))
				out.println("<div id='error'>Error al interpretar los datos recividos por el servidor Mundial (teams)</div>");

			else if(equipos.get(0).contains("Error: -3"))
				out.println("<div id='error'>Error</div>");
			
			else if(equipos.get(0).contains("Error: -130"))
				out.println("<div id='error'>Error al acceder al servicio juddi.</div>");
			
			else if(equipos.get(0).contains("Error: -131"))
				out.println("<div id='error'>Error: No se encuentra listado el servicio Mundial en el servidor uddi.</div>");

			else
			 {
				out.println("<b>Listado de equipos participantes:</b>");
				out.println("<ul>");
				for(int i=0; i<equipos.size(); i++)
					out.println("<li><a href='?apuesta="+apuesta+"&fase=3&equipo="+equipos.get(i)+"'>"+equipos.get(i)+"</a></li>");
				out.println("</ul>");
			 }
			out.println("<div id='botons'>");
		    out.println("<a href='javascript:history.back()'>Volver</a>");
		    out.println("<a href='?'>Inicio</a>");
		    out.println("</div>");
		 }
	 }
	
	/**
	 * Muestra una lista de jugadores. En caso de venir por parámetro el nombre del equipo solo mostrará los jugadores de este
	 * en otro caso mostrará todos los de la competición.
	 */
	public void listarJugadores(HttpServletRequest req, PrintWriter out)
	 {
		String apuesta = req.getParameter("apuesta");
		String equipo = "";
		equipo = req.getParameter("equipo");
		if(apuesta != null)
		 {
			ArrayList<String> jugadores = new ArrayList<String>();
			jugadores = listadoJugadores(equipo);
			if(jugadores==null)
				out.println("<div id='error'>No se han encontrado jugadores.</div>");
			
			else if(jugadores.get(0).contains("Error: -1"))
				out.println("<div id='error'>Error al acceder al servicio (allPlayerNames) de Mundial</div>");
			
			else if(jugadores.get(0).contains("Error: -2"))
				out.println("<div id='error'>Error al interpretar los datos recividos por el servidor Mundial (allPlayerNames)</div>");

			else if(jugadores.get(0).contains("Error: -3"))
				out.println("<div id='error'>Error</div>");
			
			else if(jugadores.get(0).contains("Error: -4"))
				out.println("<div id='error'>Error al acceder al servicio (fullTeaminfo) de Mundial</div>");
			
			else if(jugadores.get(0).contains("Error: -5"))
				out.println("<div id='error'>Error al interpretar los datos recividos por el servidor Mundial (fullTeaminfo)</div>");

			else if(jugadores.get(0).contains("Error: -6"))
				out.println("<div id='error'>Error</div>");
			
			else if(jugadores.get(0).contains("Error: -130"))
				out.println("<div id='error'>Error al acceder al servicio juddi.</div>");
			
			else if(jugadores.get(0).contains("Error: -131"))
				out.println("<div id='error'>Error: No se encuentra listado el servicio Mundial en el servidor uddi.</div>");
			
			else
			 {
				out.println("<b>Listado de jugadores participantes:</b>");
				out.println("<ul>");
				for(int i=0; i<jugadores.size(); i++)
					out.println("<li><a href='?apuesta=2&fase=0&jugador="+jugadores.get(i)+"'>"+jugadores.get(i)+"</a></li>");
				out.println("</ul>");
			 }
			out.println("<div id='botons'>");
		    out.println("<a href='javascript:history.back()'>Volver</a>");
		    out.println("<a href='?'>Inicio</a>");
		    out.println("</div>");
		 }
	 }
	
	
	/**
	 * Muestra una lista de partidos. En caso de venir por parámetro el nombre del equipo solo mostrará los partidos que este disputará
	 * en otro caso los mostrará todos.
	 */
	public void listarPartidos(HttpServletRequest req, PrintWriter out)
	 {
		String equipo = "";
		equipo = req.getParameter("equipo");
		ArrayList<Partido> partidos = new ArrayList<Partido>();
		partidos = listadoPartidos(equipo);
		if(partidos==null)
			out.println("<div id='error'>No se han encontrado partidos.</div>");
		
		else if(partidos.get(0).getId_partido()==-1)
			out.println("<div id='error'>Error al acceder al servicio (allGames) de Mundial</div>");
		
		else if(partidos.get(0).getId_partido()==-2)
			out.println("<div id='error'>Error al interpretar los datos recividos por el servidor Mundial (allGames)</div>");

		else if(partidos.get(0).getId_partido()==-3)
			out.println("<div id='error'>Error</div>");
		
		else if(partidos.get(0).getId_partido()==-130)
			out.println("<div id='error'>Error al acceder al servicio juddi.</div>");
		
		else if(partidos.get(0).getId_partido()==-131)
			out.println("<div id='error'>Error: No se encuentra listado el servicio Mundial en el servidor uddi.</div>");
		
		else
		 {
			out.println("<b>Listado de los partidos:</b>");
			out.println("<ul>");
			for(int i=0; i<partidos.size(); i++)
				out.println("<li><a href='?apuesta=1&fase=0&id_partido="+partidos.get(i).getId_partido()+"&equipo1="+partidos.get(i).getEquipo_local()+"&equipo2="+partidos.get(i).getEquipo_visitante()+"'>"+partidos.get(i).getParticipantes()+"</a></li>");
			out.println("</ul>");
		 }
		out.println("<div id='botons'>");
	    out.println("<a href='javascript:history.back()'>Volver</a>");
	    out.println("<a href='?'>Inicio</a>");
	    out.println("</div>");
	 }
	

	
	/**
	 * Cabecera html
	 * @param out
	 */
	public void cabezaHTML(PrintWriter out)
    {
           out.println("<head>");
           out.println("<meta charset='UTF-8'>");
           out.println("<link rel='stylesheet' href='estilos/estilo.css'>");
           out.println("<title>ShutUp&TakeMyMoney</title>");
           out.println("</head>");
	return;
    }

	/**
	 * Pie html
	 * @param out
	 */
	public void pieHTML(PrintWriter out)
	{
		out.println("<div id='pe'>");
		out.println("<div id='esq'>&#169; 2016 Compuglobalhipermeganet, S.L.</div>");
		out.println("<div id='der'>Diseñado por: ast_c3-2</div>");
		out.println("</div>");
		return;
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
	public int apostarPichichi(String jugador, double importe, String tarjeta, String f_cad)
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
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
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
	public int apostarPartido(int id_p, int goles_e1, int goles_e2, double importe, String tarjeta, String f_cad)
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
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
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
	public double comprobarApuesta(int id_a)
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
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
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
	public ArrayList<Partido> listadoPartidos(String equipo)
	 {
		ArrayList<Partido> listado = new ArrayList<Partido>();
		String equipo1="", equipo2="";
		int id_p = 0;
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarPartidos(equipo));

			@SuppressWarnings("unchecked")
			Iterator<OMElement> it = res.getChildElements();
			OMElement partido;

			//Añadimos al listado cada uno de los partidos, creado el objeco a partir del id y los participantes obtenidos.
			while(it.hasNext())
			 {
				partido = it.next();
				id_p = Integer.parseInt(((OMElement)partido.getChildrenWithLocalName("id_partido").next()).getText());
				equipo1 = ((OMElement)partido.getChildrenWithLocalName("equipo_local").next()).getText();
				equipo2 = ((OMElement)partido.getChildrenWithLocalName("equipo_visitante").next()).getText();
				listado.add(new Partido(id_p, equipo1, equipo2));
			 }
			sc.cleanupTransport();
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return null;
		 }
		
		return listado;
	 }
	
	
	/**
	 * Obtiene la información de un partido en concreto
	 * @param id_p -> Identificador del partido del que queremos obtener la información.
	 * @return Retorna un objeto partido con la información de este.
	 */
	public Partido getPartido(int id_p)
	 {
		String equipo1="", equipo2="";
		int id=0;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_getPartido(id_p+""));
			
			equipo1 = ((OMElement)res.getFirstElement().getChildrenWithLocalName("equipo_local").next()).getText();
			equipo2 = ((OMElement)res.getFirstElement().getChildrenWithLocalName("equipo_visitante").next()).getText();
			
			sc.cleanupTransport();
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
			return null;
		 }
		if(equipo1.contains("Error: "))
			id = Integer.parseInt(equipo1.substring(7));
		else
			id = id_p;
		
		return new Partido(id, equipo1, equipo2);
	 }
	
	
	/**
	 * Listado de los jugadores de un equipo. En caso de pasar una cadena vacia se listan todos los jugadores de la competición.
	 * @param equipo -> Nombre del equipo.
	 * @return Devuelve un ArrayList de String con los nombres de los jugadores.
	 */
	public ArrayList<String> listadoJugadores(String equipo)
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
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarJugadoresEquipo(equipo));
			
			@SuppressWarnings("unchecked")
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
			return null;
		 }
		
		return listado;
	 }
	
	
	
	/**
	 * Listado de Equipos que participan en la competición.
	 * @return Devuelve un ArrayList de String con todos los equipos de la competición.
	 */
	public ArrayList<String> listadoEquipos()
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
			opts.setTo(new EndpointReference(Goku.getEndpoint()));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(m_listarEquipos());
			
			@SuppressWarnings("unchecked")
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
			return null;
		 }

		return listado;
	 }
	
	
	
	
	
	//*************************************************************************************************************************//
	//							Métodos para contruir el mensaje de llamada a cada servicio propio							   //
	//*************************************************************************************************************************//
	
	
	private OMElement m_apostarPartido(String valor1, String valor2, String valor3, String valor4, String valor5, String valor6)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("apostarPartido", omNs);
		OMElement value1 = fac.createOMElement("id_partido", omNs);
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
	
	private OMElement m_apostarPichichi(String valor1, String valor2, String valor3, String valor4)
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
	
	private OMElement m_comprobarApuesta(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("comprobarApuesta", omNs);
		OMElement value = fac.createOMElement("id_apuesta", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private OMElement m_listarEquipos()
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("listarEquipos", omNs);
		return method;
	 }
	
	private OMElement m_listarJugadoresEquipo(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "", "");
		OMElement method = fac.createOMElement("listarJugadoresEquipo", omNs);
		OMElement value = fac.createOMElement("equipo", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private OMElement m_listarPartidos(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("listarPartidos", omNs);
		OMElement value = fac.createOMElement("equipo", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private OMElement m_getPartido(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("", "");
		OMElement method = fac.createOMElement("getPartido", omNs);
		OMElement value = fac.createOMElement("id_partido", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
}
