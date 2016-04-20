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
			pw.println(mensaje);
			pw.println();//Añadimos una linea en blanco para la ayudar a la lectura de los mensajes en el log.
			pw.flush();
			pw.close();
		}catch(IOException io){
			return;
		}
	}

}
