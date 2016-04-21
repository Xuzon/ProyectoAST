package handlers;

import java.util.Iterator;

import javax.xml.soap.SOAPHeaderElement;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;

public class HashHandler extends AbstractHandler{
	private String cabeceraHash = "";
	private String hash = "";
	private String funcionAAplicar = "";
	private static boolean enabled = true;
	
	public void main() throws AxisFault
	 {
		MessageContext x = new MessageContext();
		invoke(x);
	 }
	
	@Override
	public InvocationResponse invoke(MessageContext arg0) throws AxisFault {
		if(!enabled)//si lo desactivo por parametros siempre voy a devolver continuar
			return InvocationResponse.CONTINUE;
		String temp = (String) this.getHandlerDesc().getParameter("enable").getValue();//cojo el valor de enable
		if(temp.equals("FALSE")){//si enable vale FALSE se desactiva este handler
			enabled = false;
			return InvocationResponse.CONTINUE;
		}
		boolean pasar = comprobarCabecera(arg0);//compruebo la cabecera
		if(pasar)//y segun la comprobacion devuelvo un continue o un aborto
			return InvocationResponse.CONTINUE;
		else
			return InvocationResponse.ABORT;
	}
	@SuppressWarnings("rawtypes")
	boolean comprobarCabecera(MessageContext msg){
		try {
			hash = (String) this.getHandlerDesc().getParameter("hash").getValue();//consigo el hash
			cabeceraHash = (String) this.getHandlerDesc().getParameter("nombreCabeceraHash").getValue();//consigo la cabecera hash
			funcionAAplicar = (String) this.getHandlerDesc().getParameter("funcionAAplicar").getValue();//consigo la funcion a aplicar
			SOAPEnvelope se = msg.getEnvelope();
			SOAPHeader sh = se.getHeader();
			Iterator it = sh.examineAllHeaderBlocks();
			OMElement hel;
			boolean aplicar = false;//no se aplica para esta funcion
			if(funcionAAplicar.equals("ALL"))//si directamente es a todas las funciones
				aplicar = true;//aplico a true
			if(msg.getEnvelope().getBody().toString().contains(funcionAAplicar))//si el mensaje contiene la funcion
				aplicar = true;//aplico
			if(!aplicar)//si no se aplica el hash porque no es esa funcion
				return true;//devuelve true
			while (it.hasNext()) {
				hel = (OMElement) it.next();
				String headerName = hel.getLocalName();
			    if(headerName.equals(cabeceraHash)){//si la cabecera es la cabeceraHash
				    if(hel.getText().equals(hash))//consigo el valor del hash y si vale el valor
				    	return true;//devuelvo true
			    }
			}
			return false;
		} catch (Exception e) {
			System.out.println("HUBO UNA EXCEPCION::" + e.toString());
			return false;
		}
	}
}
