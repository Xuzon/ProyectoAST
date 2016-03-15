
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.xml.soap.SOAPHeaderElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.*;

public class LogHandler extends AbstractHandler{
	public static File log;
	public String cabeceraGuardar = "suatmm:Guardame";
	public String ficheroPath = "";
	@SuppressWarnings("rawtypes")
	@Override
	public InvocationResponse invoke(MessageContext arg0) throws AxisFault {
		try {
			SOAPEnvelope se = arg0.getEnvelope();
			SOAPHeader sh = se.getHeader();
			Iterator it = sh.examineAllHeaderBlocks();
			SOAPHeaderElement hel;
			while (it.hasNext()) {
				hel = (SOAPHeaderElement) it.next();
			    String headerName = hel.getNodeName();
			    if(headerName.equals(cabeceraGuardar)){
			    	guardarEnLog(arg0);
			    }
			   }
		} catch (Exception e) {
			throw new AxisFault("Failed to retrieve the SOAP Header or it's details properly.", e);
		}
		return InvocationResponse.CONTINUE;
	}
	void guardarEnLog(MessageContext msg){
		SOAPEnvelope se = msg.getEnvelope();
		String mensaje = se.toString();
		try{
			if(log == null)
				log = new File(ficheroPath);
			PrintWriter pw = new PrintWriter(new FileWriter(log,true));
			pw.println(mensaje);
			pw.flush();
			pw.close();
		}catch(IOException io){
			return;
		}
	}

}
