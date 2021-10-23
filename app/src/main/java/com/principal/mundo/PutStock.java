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
public class PutStock {
	
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";
		private static final String METHOD_NAME = "PutStock";
		
		
	
	/**
	 * metodo que se encargar de asignar valores a los atributos de la clase
	 * asigna direccion ip del servidor web del sistema PosStar
	 * @param ip
	 */
	public PutStock(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	
//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
	/**
	 * metodo que se encarga de enviar pedido al sistema PosStar
	 * @param pedido
	 * @return 1 si fue enviado correctamente o 0 en caso contrario
	 */
	public String putStock(long IdArticulo, int IdBodega, int Stock )
	{
		
		String resultado="";		
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);	 
		 request.addProperty("IdArticulo",IdArticulo); 
		 request.addProperty("IdBodega",IdBodega);
		 request.addProperty("Stock",Stock); 
		 
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
		 			resultado=resp;
		 		}
		 		else
		 		{
		 			resultado="false";
		 		}
		  	}
		 }
	     catch (Exception e)
	     {
	    	 resultado="desc";
	     }	
		return resultado;
	}
	
}
