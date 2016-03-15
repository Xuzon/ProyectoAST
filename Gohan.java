import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.varia.NullAppender;


public class Gohan {
	
	String tipo_tarjeta="";
	boolean debito=false;
	boolean largo=false;
	boolean fecha=false;
	boolean mod=false;

	
	public boolean abonarImporte(String tarjeta, double importe, String f_cad)
	 {
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("https://secure.ftipgw.com/ArgoFire/validate.asmx"));

			//Llamamos al servicio GetCardType
			opts.setAction("http://localhost/SmartPayments/GetCardType");
			sc.setOptions(opts);
			Callback_Gohan call1 = new Callback_Gohan(this, 1);
			sc.sendReceiveNonBlocking(getCardType(tarjeta), call1);

			//Llamamos al servicio IsDebitCard
			opts.setAction("http://localhost/SmartPayments/IsDebitCard");
			sc.setOptions(opts);
			Callback_Gohan call2 = new Callback_Gohan(this, 2);
			sc.sendReceiveNonBlocking(isDebitCard(tarjeta), call2);

			//Llamamos al servicio ValidCardLength
			opts.setAction("http://localhost/SmartPayments/ValidCardLength");
			sc.setOptions(opts);
			Callback_Gohan call3 = new Callback_Gohan(this, 3);
			sc.sendReceiveNonBlocking(validCardLength(tarjeta), call3);
			
			//Llamamos al servicio ValidExpDate
			opts.setAction("http://localhost/SmartPayments/ValidExpDate");
			sc.setOptions(opts);
			Callback_Gohan call4 = new Callback_Gohan(this, 4);
			sc.sendReceiveNonBlocking(validExpDate(f_cad), call4);
			
			//Llamamos al servicio ValidMod10
			opts.setAction("http://localhost/SmartPayments/ValidMod10");
			sc.setOptions(opts);
			Callback_Gohan call5 = new Callback_Gohan(this, 5);
			sc.sendReceiveNonBlocking(validMod10(tarjeta), call5);
			
			
			System.out.println("fufuf");
			while(!(call1.fin && call2.fin && call3.fin && call4.fin && call5.fin))
			 {
				Thread.sleep(1000);
				System.out.println("Esperando fin");
				System.out.println("1. "+call1.fin);
				System.out.println("2. "+call2.fin);
				System.out.println("3. "+call3.fin);
				System.out.println("4. "+call4.fin);
				System.out.println("5. "+call5.fin);
			 }
			System.out.println("fufu222f");
			System.out.println("1. "+tipo_tarjeta);
			System.out.println("2. "+debito);
			System.out.println("3. "+largo);
			System.out.println("4. "+fecha);
			System.out.println("5. "+mod);
			
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		return true;
	 }

	public boolean realizarPago(int tarjeta, double importe, String f_cad)
	 {
		String tipo_tarjeta="";
		boolean debito=false, largo=false, fecha=false, mod=false;
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
			
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			//Asignamos en las opciones la referencia al servicio externo.
			opts.setTo(new EndpointReference("https://secure.ftipgw.com/ArgoFire/validate.asmx"));
/*
			//Llamamos al servicio GetCardType
			opts.setAction("http://localhost/SmartPayments/GetCardType");
			sc.setOptions(opts);
			OMElement res_tipo = sc.sendReceive(getCardType(tarjeta));
			tipo_tarjeta = res_tipo.getFirstElement().getText();

			//Llamamos al servicio IsDebitCard
			opts.setAction("http://localhost/SmartPayments/IsDebitCard");
			sc.setOptions(opts);
			OMElement res_deb = sc.sendReceive(isDebitCard(tarjeta));
			debito = Boolean.parseBoolean(res_deb.getFirstElement().getFirstElement().getFirstElement().getText());
			
			//Llamamos al servicio ValidCardLength
			opts.setAction("http://localhost/SmartPayments/ValidCardLength");
			sc.setOptions(opts);
			OMElement res_long = sc.sendReceive(validCardLength(tarjeta));
			largo = Boolean.parseBoolean(res_long.getFirstElement().getText());
			
			//Llamamos al servicio ValidExpDate
			opts.setAction("http://localhost/SmartPayments/ValidExpDate");
			sc.setOptions(opts);
			OMElement res_fecha = sc.sendReceive(validExpDate(f_cad));
			fecha = Boolean.parseBoolean(res_fecha.getFirstElement().getText());
			
			//Llamamos al servicio ValidMod10
			opts.setAction("http://localhost/SmartPayments/ValidMod10");
			sc.setOptions(opts);
			OMElement res_mod = sc.sendReceive(validMod10(tarjeta));
			mod = Boolean.parseBoolean(res_mod.getFirstElement().getText());
 			*/
			
			
			

		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		return true;
	 }
	
	
	
	
	
	
	
	private static OMElement getCardType(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("GetCardType", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private static OMElement isDebitCard(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("IsDebitCard", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private static OMElement validCardLength(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidCardLength", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private static OMElement validExpDate(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidExpDate", omNs);
		OMElement value = fac.createOMElement("ExpDate", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	
	private static OMElement validMod10(String valor) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace( "http://localhost/SmartPayments/", "");
		OMElement method = fac.createOMElement("ValidMod10", omNs);
		OMElement value = fac.createOMElement("CardNumber", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	}
	


}
