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
import org.apache.axis2.util.XMLUtils;
import org.apache.log4j.varia.NullAppender;
import org.w3c.dom.NodeList;



public class Servicio {
	private String endpoint;
	private static String uddi;
	
	public Servicio(String nombre) {
		String url="";
		
		//Asignamos un valor por defecto de la localización del servidor uddi.
		uddi = "http://localhost:8080/juddiv3/services/inquiry";
		
		//Buscamos en la web la dirección del servidor uddi real.
		try
		 {
			URL web = new URL("http://brudi.es/ast/uddi.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
			if((uddi = in.readLine()) != null);
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
			opts.setTo(new EndpointReference(uddi));
			
			sc.setOptions(opts);
			OMElement res = sc.sendReceive(find_service(nombre));
						
			Iterator <OMElement> x = res.getChildrenWithLocalName("serviceInfos");
			if(x.hasNext())
			 {
				url=x.next().getFirstElement().getAttributeValue(new QName("serviceKey"));			
			 }

			sc.cleanupTransport();
			
			opts.setAction("get_serviceDetail");
			
			OMElement res1 = sc.sendReceive(get_serviceDetail(url));
			NodeList nl=XMLUtils.toDOM(res1).getElementsByTagName("ns2:bindingTemplate");
			for(int i=0;i<nl.getLength();i++)
			{
				if(nl.item(i).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(1).getTextContent().contains("Soap"))
				{
					endpoint=nl.item(i).getChildNodes().item(1).getTextContent();
				}
			}
			
			sc.cleanupTransport();
		}
		catch(Exception e)
		 {
			e.printStackTrace();
		 }

	}
	
	
	public String getEndpoint()
	{
		return endpoint;
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