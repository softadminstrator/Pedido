package com.principal.factura;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Clase que se encarga de obtener la nueva localizacion del telefono
 * @author Javier
 *
 */
public class MyLocationListener implements LocationListener 
{
	/**
	 * Atributo latitud se guarda la latitud de la nueva localizacion
	 */
	public String latitud="";
	/**
	 * Atributo longuitud se guarda la longuitud de la nueva localizacion
	 */
	public String longuitud="";
	
   
    public void onLocationChanged(Location loc) {
        if (loc != null) {
        	mostrarPosicion(loc);
        }
    }  
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

   
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

  
    public void onStatusChanged(String provider, int status, 
        Bundle extras) {
        // TODO Auto-generated method stub
    } 
    /**
     * metodo que se encarga de obtener la latitud y longitud de la localizacion y guardarlas
     * en los atributos de la clase
     * @param loc
     */
    private void mostrarPosicion(Location loc) {
	    if(loc != null)
	    {
	    	latitud =String.valueOf(loc.getLatitude());
	        longuitud=String.valueOf(loc.getLongitude());
	      
	    }
	    else
	    {
	    	latitud ="Sin_Datos";
	    	longuitud="Sin_Datos";
	    }	  
	}
    /**
     * metodo que se encarga de retornar la latitud de la localizacion
     * @return latitud
     */
	public String getLatitud() {
		return latitud;
	}
	/**
	 * metodo que se encarga de asignar la nueva latitud al atributo
	 * @param latitud
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	/**
	 * metodo que se encarga de retornar la longitud de la localizacion
	 * @return longuitud
	 */
	public String getLonguitud() {
		return longuitud;
	}
	/**
	 * metodo que se encarga de asignar la nueva longitud al atributo
	 * @param longuitud
	 */
	public void setLonguitud(String longuitud) {
		this.longuitud = longuitud;
	}
    
    


}