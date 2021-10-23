package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LlamarImgUrl {
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
		private static final String METHOD_NAME = "GetUrlImagenArt";
			
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public LlamarImgUrl(String ip)
	{	
		//http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	/**
	 * metodo que se encarga de realizar la llamada al servicio web del sistema PosStar
	 * para realizar la validacion del usuario
	 * @param cedula
	 * @param clave
	 * @return true si la identificacion fue exitosa, en caso contrario retorna false
	 */
	public String getImgUrl(long idArticulo)
	{
		boolean conn=false;
		String resultado = "false";	
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 request.addProperty("IdArticulo",idArticulo);
		 
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 	 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre		
	
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 	conn=true;
	    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	if(res!=null)
		 	{
		 		String resp =res.toString();
		 		resultado=resp;		 		
		  	}
		 			 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 if(!conn)
	    	 {
	    		 resultado = "desc";
	    	 }    	 
	     }	
		return resultado;
	}

}
