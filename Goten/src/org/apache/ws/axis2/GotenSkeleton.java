/**
 * GotenSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;

/**
 *  GotenSkeleton java skeleton for the axisService
 */
public class GotenSkeleton {
    /**
     * Auto generated method signature
     *
     * @param realizarApuestaPartido
     * @return realizarApuestaPartidoResponse
     */
    public org.apache.ws.axis2.RealizarApuestaPartidoResponse realizarApuestaPartido(
        org.apache.ws.axis2.RealizarApuestaPartido realizarApuestaPartido) {
        
    	//Creamos el objecto que debemos retornar.
    	int salida = 0;
    	RealizarApuestaPartidoResponse ret = new RealizarApuestaPartidoResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	int id_p = realizarApuestaPartido.getId_p();
    	int goles_e1 = realizarApuestaPartido.getGoles_e1();
    	int goles_e2 = realizarApuestaPartido.getGoles_e2();

    	//Creamos un objecto de la clase del servicio.
    	Goten g = new Goten();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos y almacenamos lo que nos devuelve.
    	salida = g.realizarApuestaPartido(id_p, goles_e1, goles_e2);
    	
    	//Modificamos el objeto que devemos devolver añadiendole lo que recuperamos de la función anterior.
    	ret.setId_apuesta(salida);
    	
    	return ret;
    	
    }

    /**
     * Auto generated method signature
     *
     * @param comprobarApuestaPichichi
     * @return comprobarApuestaPichichiResponse
     */
    public org.apache.ws.axis2.ComprobarApuestaPichichiResponse comprobarApuestaPichichi(
        org.apache.ws.axis2.ComprobarApuestaPichichi comprobarApuestaPichichi) {
        
    	//Creamos el objecto que debemos retornar.
    	double salida = 0;
    	ComprobarApuestaPichichiResponse ret = new ComprobarApuestaPichichiResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	int id_a = comprobarApuestaPichichi.getId_a();

    	//Creamos un objecto de la clase del servicio.
    	Goten g = new Goten();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos y almacenamos lo que nos devuelve.
    	salida = g.comprobarApuestaPichichi(id_a);
    	
    	//Modificamos el objeto que devemos devolver añadiendole lo que recuperamos de la función anterior.
    	ret.setResultado_cuota(salida);
    	
    	return ret;
    	
    	
    }

    /**
     * Auto generated method signature
     *
     * @param realizarApuestaPichichi
     * @return realizarApuestaPichichiResponse
     */
    public org.apache.ws.axis2.RealizarApuestaPichichiResponse realizarApuestaPichichi(
        org.apache.ws.axis2.RealizarApuestaPichichi realizarApuestaPichichi) {
    	
    	
    	//Creamos el objecto que debemos retornar.
    	int salida = 0;
    	RealizarApuestaPichichiResponse ret = new RealizarApuestaPichichiResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	String jugador = realizarApuestaPichichi.getJugador();

    	//Creamos un objecto de la clase del servicio.
    	Goten g = new Goten();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos y almacenamos lo que nos devuelve.
    	salida = g.realizarApuestaPichichi(jugador);
    	
    	//Modificamos el objeto que devemos devolver añadiendole lo que recuperamos de la función anterior.
    	ret.setId_apuesta(salida);
    	
    	return ret;
    }

    /**
     * Auto generated method signature
     *
     * @param comprobarApuestaPartido
     * @return comprobarApuestaPartidoResponse
     */
    public org.apache.ws.axis2.ComprobarApuestaPartidoResponse comprobarApuestaPartido(
        org.apache.ws.axis2.ComprobarApuestaPartido comprobarApuestaPartido) {
    	
    	
    	//Creamos el objecto que debemos retornar.
    	double salida = 0;
    	ComprobarApuestaPartidoResponse ret = new ComprobarApuestaPartidoResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	int id_a = comprobarApuestaPartido.getId_a();

    	//Creamos un objecto de la clase del servicio.
    	Goten g = new Goten();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos y almacenamos lo que nos devuelve.
    	salida = g.comprobarApuestaPartido(id_a);
    	
    	//Modificamos el objeto que devemos devolver añadiendole lo que recuperamos de la función anterior.
    	ret.setResultado_cuota(salida);
    	
    	return ret;
    }
}
