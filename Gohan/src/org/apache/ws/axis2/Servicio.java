package org.apache.ws.axis2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.varia.NullAppender;


public class Servicio {
	private String endpoint;
	private int error=0;
	private static String dir_uddi;
	
	
	public Servicio(String nombre) {
		String service_key="";
		
		//Asignamos un valor por defecto de la localización del servidor uddi.
		//dir_uddi = "http://localhost:8082/juddiv3/services/inquiry";
		
		//Buscamos en la web la dirección del servidor uddi real.
		String ip_uddi = "";
		try
		 {
			URL web = new URL("http://brudi.es/ast/uddi.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			if((ip_uddi = in.readLine()) != null);
			dir_uddi = ip_uddi+"/juddiv3/services/inquiry";
		 }
		catch(Exception e)
		 {
			e.printStackTrace();
		 }
		
		try
		 {
			//POSTUREO, NO TOCAR.
			org.apache.log4j.BasicConfigurator.configure(new NullAppender());
		
			//Instanciamos el servicio de cliente y las opciones
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			
			opts.setAction("find_service");
			
			//Asignamos en las opciones la referencia al servicio propio.
			opts.setTo(new EndpointReference(dir_uddi));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(find_service(nombre));			
			
			if(!res.getFirstElement().getFirstElement().getText().equals("0"))
			 {
				@SuppressWarnings("unchecked")
				Iterator <OMElement> x = res.getChildrenWithLocalName("serviceInfos");
				if(x.hasNext())
				 {
					service_key=x.next().getFirstElement().getAttributeValue(new QName("serviceKey"));			
				 }
			
				sc.cleanupTransport();
				
				opts.setAction("get_serviceDetail");
				
				OMElement res1 = sc.sendReceive(get_serviceDetail(service_key));
	
				endpoint = ((OMElement)((OMElement)res1.getFirstElement().getChildrenWithLocalName("bindingTemplates").next()).getFirstElement().getChildrenWithLocalName("accessPoint").next()).getText();
				
				sc.cleanupTransport();
			 }
			else
			 {
				endpoint=null;
				error = -101;
			 }
		 }
		catch(Exception e)
		 {
		 	e.printStackTrace();
		 	error=-100;
		 }

	}
	
	
	public String getEndpoint()
	{
		return endpoint;
	}
	
	public int getError()
	 {
		return error;
	 }

	
	
	
	
	private static OMElement find_service(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("urn:uddi-org:api_v3", "");
		OMElement method = fac.createOMElement("find_service", omNs);
		OMElement value = fac.createOMElement("name", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	private static OMElement get_serviceDetail(String valor)
	 {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("urn:uddi-org:api_v3", "");
		OMElement method = fac.createOMElement("get_serviceDetail", omNs);
		OMElement value = fac.createOMElement("serviceKey", omNs);
		value.setText(valor);
		method.addChild(value);
		return method;
	 }
	
	
	
}
