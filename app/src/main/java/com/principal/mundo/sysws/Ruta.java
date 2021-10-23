package com.principal.mundo.sysws;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;



/**
 * Clase en la que se describen las caracteristicas de la ruta
 * @author Javier
 *
 */
public class Ruta  implements KvmSerializable {
	
	/**
	 * identificador de la ruta
	 */
	private String idRuta;
	/**
	 * identificador de la ubicacion 
	 */
	private long idUbicacion;
	/**
	 * latitud de la ubicacion de la ruta
	 */
	private String latitud;
	/**
	 * longitud de la ubicacion de la ruta
	 */
	private String longitud;
	/**
	 * altitud de la ubicacion ruta
	 */
	private String altitud;
	/**
	 * consecutivo de ubicacion de la ruta
	 */
	private long consecutivo;

	/**
	 * metodo que asigna valores a los atributos de la clase
	 * @param idRuta
	 * @param idUbicacion
	 * @param latitud
	 * @param longitud
	 * @param altitude
	 * @param consecutivo
	 */
	public Ruta(String idRuta,long idUbicacion, String latitud,String longitud, String altitude, long consecutivo) {		
		
		this.idRuta=idRuta;
		this.idUbicacion=idUbicacion;
		this.latitud=latitud;
		this.longitud=longitud;
		this.altitud=altitude;
		this.consecutivo=consecutivo;
	
	}
	/**
	 *constructor vacio 
	 */
	public Ruta() {		
	}
	/**
	 * metodo que retorna el valor del los atribudos de la clase dependiendo del identificador entrante
	 */
	public Object getProperty(int i) {
		switch(i)
		{
		case 0:return idRuta;		
		case 1:return idUbicacion;
		case 2:return latitud;
		case 3:return longitud;
		case 4:return altitud;	
		case 5:return consecutivo;
		}
		return null;
	}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}
	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
		case 0:
	        propertyInfo.name = "idRuta";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 1:
	        propertyInfo.name = "idUbicacion";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
	    case 2:
	        propertyInfo.name = "latitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "longitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;        
            break;
	    case 4:
	        propertyInfo.name = "altitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 5:
	        propertyInfo.name = "consecutivo";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
	  
	     default: break;
		 }
		
	}
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * metodo que retorna el identificador de la ruta
	 * @return idruta
	 */
	public String getIdRuta() {
		return idRuta;
	}
	/**
	 * metodo que asigna el nuevo valor del identificador de la ruta
	 * @param idRuta
	 */
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	/**
	 * metodo que retorna el identificador de  ubicacion de la ruta
	 * @return
	 */
	public long getIdUbicacion() {
		return idUbicacion;
	}
	/**
	 * metodo que asigna el nuevo identificador de ubicacion de la ruta
	 * @param idUbicacion
	 */
	public void setIdUbicacion(long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	/**
	 * retorna la latitud del ubicacion de la ruta
	 * @return latitud
	 */
	public String getLatitud() {
		return latitud;
	}
	/**
	 * asigna el nuevo valor de la latitud de ubicacion de la ruta
	 * @param latitud
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	/**
	 * metodo que retorna la longitud de la ubicacion de la ruta
	 * @return longitud
	 */
	public String getLongitud() {
		return longitud;
	}
	/**
	 * metodo que asigna la nueva longitud de ubicacion de la ruta
	 * @param longitud
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	/**
	 * metodo que retorna la altitid de ubicacion de la ruta
	 * @return altitud
	 */
	public String getAltitud() {
		return altitud;
	}
	/**
	 * metodo que asigna el nuevo valor de altitud de la ruta
	 * @param altitud
	 */
	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}
	/**
	 * metodo que retorna el consecutivo de la ruta
	 * @return consecutivo
	 */
	public long getConsecutivo() {
		return consecutivo;
	}
	/**
	 * metodo que asigna el nuevo consecutivo de la ruta
	 * @param consecutivo
	 */
	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}
	
	

}
