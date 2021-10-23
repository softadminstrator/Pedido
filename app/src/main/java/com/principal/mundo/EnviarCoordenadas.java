package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
/**
 * Clase encargada de enviar coordenadas de la localizacion del telefono al servicio Web del Sistema PosStar
 * @author Javier
 *
 */
public class EnviarCoordenadas {
		
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
		private static String URL="http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
		private static final String METHOD_NAME = "GetValidaVendedor";
		private static final String SOAP_ACTION ="http://www.elchispazo.com.co/GetValidaVendedor";
		
	/**
	 * constructor vacio de la clase
	 */
	public EnviarCoordenadas()
	{		
	}
	
	/**
	 * Metodo que se encarga de enviar coordenadas al sistema Posstar
	 * @param cedula
	 * @param clave
	 * @return Respuesta del servicio
	 */
	public String getUsuario(String cedula, String clave)
	{
		String resultado = "false";
		
		
		try
		 {	
			
		
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 request.addProperty("Cedula",cedula);
		 request.addProperty("Clave",clave);
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		
		
	
		 	androidHttpTransport.call(SOAP_ACTION, envelope);
	    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	if(res!=null)
		 	{
		 		String resp =res.toString();
		 		if(resp.equals("true"))
		 		{
		 			resultado=resp;
		 		}
		  	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resultado = "desc";
	     }	
		return resultado;
	}
	
	
	

}

