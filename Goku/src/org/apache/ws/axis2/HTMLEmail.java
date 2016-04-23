package org.apache.ws.axis2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class HTMLEmail {
	      String sTo = "xuzon69@gmail.com";
	      String sFrom = "info@compuglobalhipermeganet.com";
	      String sAsunto = "Asunto de prueba";
	      String sText = "<h1>Texto cambiame</h1>";
	      Properties properties = System.getProperties();
	  	  private static final String fichero_log = "mailLog.txt";
	  	  private static final String directorio_log = System.getProperty("user.home")+"/AST/log";
	      public HTMLEmail(){
	    	  send();
	      }
	      
	      public HTMLEmail(String to, String from, String subject,String text){
	    	  sTo = to;
	    	  sFrom = from;
	    	  sAsunto = subject;
	    	  sText = text;
	    	  send();
	      }
	      void send(){
	      properties.put("mail.smtp.auth", "true");
		  properties.put("mail.smtp.starttls.enable", "true");
		  properties.put("mail.smtp.host", "smtp.uvigo.es");
		  properties.put("mail.smtp.port", "587");

			Session session = Session.getInstance(properties,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("cgarrido@alumnos.uvigo.es", "051111nha");
				}
			  });
	      try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(sFrom));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(sTo));
	         message.setSubject(sAsunto);
	         message.setContent(sText, "text/html" );
	         Transport.send(message);
	         log("Correo enviado!!");
	      }catch (MessagingException mex) {
	    	 log(mex.toString());
	      }
	   }
	      
	      /**
	  	 * Método para almacenar una linea de error en el fichero de log del programa.
	  	 * Se añade la fecha y hora del error, así como el nombre del programa desde el que se ha generado.
	  	 * @param datos -> Información del error.
	  	 */
	  	private static void log(String datos){
	  		BufferedWriter out = null;
	  		File dir = new File(directorio_log);
	  		dir.mkdirs();
	  		try{
	  			Date fecha = new Date();
	  			out = new BufferedWriter(new FileWriter(dir+"/"+fichero_log, true));   
	  			out.write(fecha+":"+" Goten -- "+datos+"\n");
	  		}catch(Exception e1){
	  			e1.printStackTrace();
	  		}finally{
	  			try{
	  				if( null != out ){
	  					out.close();
	  				 }
	  			 }catch (Exception e2){
	  				e2.printStackTrace();
	  			 }
	  		 }
	  	 }
	  	
}
