/**
 * GohanSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;

/**
 *  GohanSkeleton java skeleton for the axisService
 */
public class GohanSkeleton {
    /**
     * Auto generated method signature
     *
     * @param realizarPago
     * @return realizarPagoResponse
     */
    public org.apache.ws.axis2.RealizarPagoResponse realizarPago(
        org.apache.ws.axis2.RealizarPago realizarPago) {
    	
    	//Creamos el objecto que debemos retornar.
    	int salida = 0;
    	RealizarPagoResponse ret = new RealizarPagoResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	String tarjeta = realizarPago.getTarjeta();
    	double importe = realizarPago.getImporte();
    	String f_cad = realizarPago.getF_cad();
    	
    	//Creamos un objecto de la clase del servicio.
    	Gohan g = new Gohan();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.realizarPago(tarjeta, importe, f_cad);
    	
    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.set_return(salida);
    	
    	return ret;
    	
    }

    /**
     * Auto generated method signature
     *
     * @param abonarImporte
     * @return abonarImporteResponse
     */
    public org.apache.ws.axis2.AbonarImporteResponse abonarImporte(
        org.apache.ws.axis2.AbonarImporte abonarImporte) {

    	//Creamos el objecto que debemos retornar.
    	int salida = 0;
    	AbonarImporteResponse ret = new AbonarImporteResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	String tarjeta = abonarImporte.getTarjeta();
    	double importe = abonarImporte.getImporte();
    	String f_cad = abonarImporte.getF_cad();
    	
    	//Creamos un objecto de la clase del servicio.
    	Gohan g = new Gohan();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.abonarImporte(tarjeta, importe, f_cad);
    	
    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.set_return(salida);
    	
    	return ret;

    }
}

