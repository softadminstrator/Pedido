package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
/**
 * Clase encargada de enviar la anulacion de pedidos al servicio web del sistema PosStar
 * @author Javier
 *
 */
public class EnviarAnulacion {
		
	    /**
	     * nombre del dominio al que sera enviada la anulacion del pedido
	     */
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";
		/**
		 * nombre del metodo del servicio que sera llamado
		 */
		private static final String METHOD_NAME = "BorraPedido";
		
		
	
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * asigna el numero de ip en la que se encuentra el servicio web
	 * @param ip
	 */
	public EnviarAnulacion(String ip)
	{	
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	/**
	 * metodo que se encarga de enviar la anulacion del pedido al servicio web del Sistema PosStar
	 * @param idCodigoExterno
	 * @param cedula
	 * @return true si fue eliminado y false si no fue anularlo
	 */
	public Boolean getAnularPedido(long idCodigoExterno, String cedula)
	{
		Boolean resultado = false;		
		try
		 {	
   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 request.addProperty("NumPedido",idCodigoExterno);
		 request.addProperty("CedulaVendedor",cedula);
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre		
	
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
	    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	if(res!=null)
		 	{
		 		String resp =res.toString();
		 		if(resp.equals("true"))
		 		{
		 			resultado=true;
		 		}
		  	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resultado = false;
	     }	
		return resultado;
	}
	
	
	

}
