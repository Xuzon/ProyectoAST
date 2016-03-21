/**
 * GokuSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ws.namespaces.axis2.map.Entry1;
import org.apache.ws.namespaces.axis2.map.Map1;

/**
 *  GokuSkeleton java skeleton for the axisService
 */
public class GokuSkeleton {
    /**
     * Auto generated method signature
     *
     * @param comprobarApuesta
     * @return comprobarApuestaResponse
     */
    public org.apache.ws.axis2.ComprobarApuestaResponse comprobarApuesta(
        org.apache.ws.axis2.ComprobarApuesta comprobarApuesta) {
        
    	//Creamos el objecto que debemos retornar.
    	double salida = 0;
    	ComprobarApuestaResponse ret = new ComprobarApuestaResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	int id_a = comprobarApuesta.getId_a();
    	
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.comprobarApuesta(id_a);
    	
    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.setImporteRecuperado(salida);
    	
    	return ret;
    }

    /**
     * Auto generated method signature
     *
     * @param apuestaFinalizada
     * @return
     */
    public void apuestaFinalizada(
        org.apache.ws.axis2.ApuestaFinalizada apuestaFinalizada) {
    	    	
    	//Recuperamos los argumentos de entrada.
    	int id_a = apuestaFinalizada.getId_apuesta();
    	double cuota_resultante = apuestaFinalizada.getCuota_resultante();
    	
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	g.apuestaFinalizada(id_a, cuota_resultante);
    	
    }

    /**
     * Auto generated method signature
     *
     * @param listarPartidos
     * @return listarPartidosResponse
     */
    public org.apache.ws.axis2.ListarPartidosResponse listarPartidos(
        org.apache.ws.axis2.ListarPartidos listarPartidos) {
    	
    	//Creamos el objecto que debemos retornar.
    	Map<Integer, String> salida = null;
    	
    	ListarPartidosResponse ret = new ListarPartidosResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	String equipo = listarPartidos.getEquipo();
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.listarPartidos(equipo);
    	
    	//Es necesario combertir el HashMap de salida de la función a un Mapa definido para el mensaje SOAP.
    	Map1 salida1 = new Map1();
    	Entry1 entry1 = null;

    	for(Map.Entry<Integer, String> entry : salida.entrySet())
    	 {
    		entry1 = new Entry1();
    		entry1.setId_partido(entry.getKey());
    		entry1.setParticipantes(entry.getValue());
    		salida1.addPartido(entry1);
    	 }
    	
    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.setPartidos(salida1);
    	
    	return ret;
    }

    /**
     * Auto generated method signature
     *
     * @param apostarPartido
     * @return apostarPartidoResponse
     */
    public org.apache.ws.axis2.ApostarPartidoResponse apostarPartido(
        org.apache.ws.axis2.ApostarPartido apostarPartido) {
       
    	//Creamos el objecto que debemos retornar.
    	int salida = 0;
    	
    	ApostarPartidoResponse ret = new ApostarPartidoResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	int id_p = apostarPartido.getId_p();
    	int goles_e1 = apostarPartido.getGoles_e1();
    	int goles_e2 = apostarPartido.getGoles_e2();
    	double importe = apostarPartido.getImporte();
    	String tarjeta = apostarPartido.getTarjeta();
    	String f_cad = apostarPartido.getF_cad();
    	
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.apostarPartido(id_p, goles_e1, goles_e2, importe, tarjeta, f_cad);


    	//Añadimos al objeto que retornamos la salida de la función.   	
    	ret.setId_apuesta(salida);
    	
    	return ret;
    	
    }

    /**
     * Auto generated method signature
     *
     * @param apostarPichichi
     * @return apostarPichichiResponse
     */
    public org.apache.ws.axis2.ApostarPichichiResponse apostarPichichi(
        org.apache.ws.axis2.ApostarPichichi apostarPichichi) {
        
     	//Creamos el objecto que debemos retornar.
     	int salida = 0;
     	
     	ApostarPichichiResponse ret = new ApostarPichichiResponse();
     	
     	//Recuperamos los argumentos de entrada.
     	String jugador = apostarPichichi.getJugador();
     	double importe = apostarPichichi.getImporte();
     	String tarjeta = apostarPichichi.getTarjeta();
     	String f_cad = apostarPichichi.getF_cad();
     	
     	//Creamos un objecto de la clase del servicio.
     	Goku g = new Goku();
     	
     	//Llamamos a la función pasandole los parámetros obtenidos
     	salida = g.apostarPichichi(jugador, importe, tarjeta, f_cad);


     	//Añadimos al objeto que retornamos la salida de la función.   	
     	ret.setId_apuesta(salida);
     	
     	return ret;
    }

    /**
     * Auto generated method signature
     *
     * @param listarEquipos
     * @return listarEquiposResponse
     */
    public org.apache.ws.axis2.ListarEquiposResponse listarEquipos(
        org.apache.ws.axis2.ListarEquipos listarEquipos) {
    	
    	//Creamos el objecto que debemos retornar.
    	ArrayList<String> salida = null;
    	ListarEquiposResponse ret = new ListarEquiposResponse();
    	
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();
    	
    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.listarEquipos();
    	
    	//Necesario combertir el ArrayList de salida a un array de string para el servicio SOAP
    	String[] salida1 = new String[salida.size()];

    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.setEquipos(salida.toArray(salida1));
    	
    	return ret;
    }

    /**
     * Auto generated method signature
     *
     * @param listarJugadoresEquipo
     * @return listarJugadoresEquipoResponse
     */
    public org.apache.ws.axis2.ListarJugadoresEquipoResponse listarJugadoresEquipo(
        org.apache.ws.axis2.ListarJugadoresEquipo listarJugadoresEquipo) {
    	
    	//Creamos el objecto que debemos retornar.
    	ArrayList<String> salida = null;
    	ListarJugadoresEquipoResponse ret = new ListarJugadoresEquipoResponse();
    	
    	//Recuperamos los argumentos de entrada.
    	String equipo = listarJugadoresEquipo.getEquipo();
    	
    	//Creamos un objecto de la clase del servicio.
    	Goku g = new Goku();

    	//Llamamos a la función pasandole los parámetros obtenidos
    	salida = g.listarJugadoresEquipo(equipo);
    	
    	//Necesario combertir el ArrayList de salida a un array de string para el servicio SOAP
    	String[] salida1 = new String[salida.size()];
    	
    	//Añadimos al objeto que retornamos la salida de la función.
    	ret.setJugadores(salida.toArray(salida1));
    	
    	return ret;
    }
}
