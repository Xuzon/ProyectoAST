package org.apache.ws.axis2;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.MessageContext;


public class Callback_Gohan implements AxisCallback {

	//Referencia al objeto principal.
	public Gohan g;
	//Método al que se a llamada a utilizar este callback.
	public int metodo;
	//Flag para indicar si el método externo a finalizado.
	public boolean fin=false;
	
	public Callback_Gohan(){}
	
	//Contructor própio para mantener una referencia al objeto y el tipo de método al que se a llamado.
	public Callback_Gohan(Gohan g, int metodo)
	 {
		this.g = g;
		this.metodo = metodo;
	 }
	
	
	@Override
	public void onComplete() {
		//Una vez completado ponemos la flag a true.
		System.out.println("Completado ok: "+metodo);
		fin=true;
	}

	@Override
	public void onError(Exception arg0) {
		arg0.printStackTrace();
	}

	@Override
	public void onFault(MessageContext arg0) {
		System.out.println(arg0);
	}

	@Override
	public void onMessage(MessageContext arg0) {
		//Dependiendo del método añadimos el valor retornado a su correspondiente parámetro del objeto principal.
		System.out.println("Mensaje ok: "+metodo);
		//Sacamos el cuerpo del mensaje completo.
		SOAPEnvelope env1 = arg0.getEnvelope();
		SOAPBody bod1 = env1.getBody();
		if(metodo==1) g.tipo_tarjeta = bod1.getFirstElement().getText();
		if(metodo==2) g.debito = Boolean.parseBoolean(bod1.getFirstElement().getFirstElement().getFirstElement().getText());
		if(metodo==3) g.largo = Boolean.parseBoolean(bod1.getFirstElement().getText());
		if(metodo==4) g.fecha = Boolean.parseBoolean(bod1.getFirstElement().getText());
		if(metodo==5) g.mod = Boolean.parseBoolean(bod1.getFirstElement().getText());
	}

}
