package handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.*;

public class LogHandler extends AbstractHandler{
	private static File log;
	private String ficheroPath = "";
	@Override
	public InvocationResponse invoke(MessageContext arg0) throws AxisFault {
		try {
			guardarEnLog(arg0);
		} catch (Exception e) {
			throw new AxisFault("Failed to retrieve the SOAP Header or it's details properly.", e);
		}
		return InvocationResponse.CONTINUE;
	}
	void guardarEnLog(MessageContext msg){
		SOAPEnvelope se = msg.getEnvelope();
		String mensaje = se.toString();
		ficheroPath = (String) this.getHandlerDesc().getParameter("nombreLog").getValue();
		try{
			Path currentRelative = Paths.get("");
			String absoluto = currentRelative.toAbsolutePath().toString();
			if(log == null)
				log = new File(absoluto + "/" + ficheroPath);
			PrintWriter pw = new PrintWriter(new FileWriter(log,true));
			pw.println(formatXML(mensaje));
			pw.println();//Añadimos una linea en blanco para la ayudar a la lectura de los mensajes en el log.
			pw.flush();
			pw.close();
		}catch(IOException io){
			return;
		}
	}
	
	//Método para formatear el mensaje XML
	public String formatXML(String input)
	 {
	    try
	     {
	        Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
	        StreamResult result = new StreamResult(new StringWriter());
	        DOMSource source = new DOMSource(parseXml(input));
	        transformer.transform(source, result);
	        return result.getWriter().toString();
	     }
	    catch (Exception e)
	     {
	        e.printStackTrace();
	        return input;
	     }
	 }
	 
	//Método para parsear el mensaje xml
	private Document parseXml(String in)
	 {
	    try
	     {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource(new StringReader(in));
	        return db.parse(is);
	     }
	    catch (Exception e)
	     {
	        throw new RuntimeException(e);
	     }
	 }
}
