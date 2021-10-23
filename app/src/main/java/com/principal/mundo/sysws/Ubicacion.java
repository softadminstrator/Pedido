package com.principal.mundo.sysws;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * Clase en la que se describen las caracteristicas de la ubicacion del telefono
 * @author Javier
 *
 */
public class Ubicacion implements KvmSerializable{
	
	/**
	 * identificador de la ubicacion
	 */
	private int idUbicacion;
	/**
	 * latitud de la ubicacion
	 */
	private String latitud;
	/**
	 * longitud de la ubicacion
	 */
	private String longitud;
	/**
	 * altitud de la ubicacion
	 */
	private String altitud;
	/**
	 * fecha de la obtencion de la ubicacion
	 */
	private String fecha;
	/**
	 * hora de la obtencion de la ubicacion
	 */
	private String hora;
	/**
	 * consecutivo de la ubicacion
	 */
	private int consecutivo;
	/**
	 * referencia de la clase Calendar para obtener la fecha actual del sistema
	 */
	Calendar cal = Calendar.getInstance();	
	/**
	 * referencia de la clase SimpleDateFormatpara obtener el formato de la hora del sistema
	 */
	SimpleDateFormat sdfHora;
	/**
	 * referencia de la clase SimpleDateFormatpara obtener el formato de la fecha del sistema
	 */
	SimpleDateFormat sdfFecha;
	

	/**
	 * constructor vacio
	 */
	public Ubicacion() {
		idUbicacion=0;
		latitud="";
		longitud="";
		altitud="";
		fecha="";
		hora="";
		consecutivo=0;
	}
	
	/**
	 * metodo que se encarga de asignar valores a los atributos del sistema
	 * @param idUbicacion
	 * @param latitud
	 * @param longitud
	 * @param altitud
	 * @param fecha
	 * @param hora
	 * @param consecutivo
	 */
	public Ubicacion(int idUbicacion,String latitud, String longitud, String altitud,String fecha, String hora, int consecutivo)
	{
		this.idUbicacion=idUbicacion;
		this.latitud= latitud;
		this.longitud=longitud;
		this.altitud=altitud;
		this.fecha=fecha;
		this.hora=hora;
		this.consecutivo=consecutivo;
	}	

	/**
	 * metodo que retorna el identificador de la ubicacion
	 * @return idUbicacion
	 */
	public int getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * metodo que asigna el nuevo identificador de la ubicacion del sistema
	 * @param idUbicacion
	 */
	public void setIdUbicacion(int idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	/**
	 * metodo que retorna la latitud de la ubicacion del telefono
	 * @return latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * metodo que asigna la nueva latitud de la ubicacion del telefono
	 * @param latitud
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/**
	 * metodo que retorna la longitud de ubicacion del telefono
	 * @return longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * metodo que asigna el nuevo valor de la longitud del telefono
	 * @param longitud
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	/**
	 * metodo que retorna la altitud de ubicacion del telefono
	 * @return altitud
	 */
	public String getAltitud() {
		return altitud;
	}

	/**
	 * metodo que asigna el nuevo valor de altitud de ubicacion del telefono
	 * @param altitud
	 */
	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}

	/**
	 * metodo que retorna la fecha actual del sistema
	 * @return fecha
	 */
	public String getFecha() {
		sdfFecha=new SimpleDateFormat("dd/MM/yyyy"); 
		fecha=sdfFecha.format(cal.getTime());
		return fecha;
	}

	/**
	 * metodo que asigna el nuevo valor de fecha del sistema
	 * @param fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * metodo que retorna la hora actual del sistema
	 * @return hora
	 */
	public String getHora() {
		sdfHora = new SimpleDateFormat("HH:mm");
		hora=sdfHora.format(cal.getTime());
		return hora;
	}

	/**
	 * metodo que asigna la nueva hora del sistema
	 * @param hora
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * metodo que retorna el consecutivo de ubicacion
	 * @return consecutivo
	 */
	public int getConsecutivo() {
		return consecutivo;
	}

	/**
	 * metodo que asigna el nuevo valor del consecutivo de ubicacion
	 * @param consecutivo
	 */
	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Object getProperty(int i) {
		switch(i)
		{
		case 0:return idUbicacion;		
		case 1:return latitud;
		case 2:return longitud;
		case 3:return altitud;
		case 4:return fecha;	
		case 5:return hora;	
		case 6:return consecutivo;	
		}
		return null;
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
		case 0:
	        propertyInfo.name = "idUbicacion";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
	    case 1:
	        propertyInfo.name = "latitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 2:
	        propertyInfo.name = "longitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "altitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;        
            break;
	    case 4:
	        propertyInfo.name = "fecha";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 5:
	        propertyInfo.name = "hora";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 6:
	        propertyInfo.name = "consecutivo";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
	  
	     default: break;
		 }
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}