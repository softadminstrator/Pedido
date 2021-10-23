package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de enviar pededido al sistema PosStar
 * @author Javier
 *
 */
public class GenerarCierre {
	
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";
		private static final String METHOD_NAME = "GeneraCierre";
	
		
	
	/**
	 * metodo que se encargar de asignar valores a los atributos de la clase
	 * asigna direccion ip del servidor web del sistema PosStar
	 * @param ip
	 */
	public GenerarCierre(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	
//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
	/**
	 * metodo que se encarga de enviar pedido al sistema PosStar
	 * @param pedido
	 * @return 1 si fue enviado correctamente o 0 en caso contrario
	 */
	public ZFinanciera getGenerarCierre(long NCaja)
	{
		ZFinanciera zFinanciera=null;
		
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 request.addProperty("NCaja",NCaja);	
		 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
			
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
		 if(res!=null)
		 	{		 		
			 zFinanciera=new ZFinanciera(Long.parseLong(res.toString()),NCaja);		 		
		  	}		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 String error = e.toString();
	     }	
		return zFinanciera;
	}	
}
