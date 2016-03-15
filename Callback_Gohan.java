import javax.xml.namespace.QName;

import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.MessageContext;


public class Callback_Gohan implements AxisCallback {

	public Gohan g;
	public int metodo;
	public boolean fin=false;
	
	
	
	public Callback_Gohan(){}
	
	public Callback_Gohan(Gohan g, int metodo)
	 {
		this.g = g;
		this.metodo = metodo;
	 }
	
	
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		fin=true;
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFault(MessageContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(MessageContext arg0) {
		// TODO Auto-generated method stub
		SOAPEnvelope env1 = arg0.getEnvelope();
		SOAPBody bod1 = env1.getBody();
		if(metodo==1) g.tipo_tarjeta = bod1.getFirstElement().getFirstElement().getText();
		if(metodo==2) g.debito = Boolean.parseBoolean(bod1.getFirstElement().getFirstElement().getFirstElement().getFirstElement().getText());
		if(metodo==3) g.largo = Boolean.parseBoolean(bod1.getFirstElement().getFirstElement().getText());
		if(metodo==4) g.fecha = Boolean.parseBoolean(bod1.getFirstElement().getFirstElement().getText());
		if(metodo==5) g.mod = Boolean.parseBoolean(bod1.getFirstElement().getFirstElement().getText());
	}

}
