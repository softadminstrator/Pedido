package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de enviar pededido al sistema PosStar
 * @author Javier
 *
 */
public class EnviarPedido {
	
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";
//		private static String URL="http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
		private static final String METHOD_NAME = "PutPedido";
	    private static final String METHOD_NAME_INVENTARIO = "PutPedidoInventario";
		private static final String SOAP_ACTION ="http://www.elchispazo.com.co/PutPedido";
		
	
	/**
	 * metodo que se encargar de asignar valores a los atributos de la clase
	 * asigna direccion ip del servidor web del sistema PosStar
	 * @param ip
	 */
	public EnviarPedido(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	
//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
	/**
	 * metodo que se encarga de enviar pedido al sistema PosStar
	 * @param pedido
	 * @return 1 si fue enviado correctamente o 0 en caso contrario
	 */
	public long getEnviarPedido(Pedido pedido )
	{
		
		long resp=0;		
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 
		 PropertyInfo prPedido=new PropertyInfo();
		 prPedido.setName("Pedido");
		 prPedido.setValue(pedido);
		 prPedido.setType(Pedido.class);		 
		 request.addProperty(prPedido);		
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.implicitTypes = true;
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 		 
		 envelope.addMapping(SOAP_ACTION, "Pedido", new Pedido().getClass());
		
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 androidHttpTransport.call(SOAP_ACTION, envelope);
		 SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
		 if(res!=null)
		 	{		 		
		 		resp =Long.parseLong(res.toString());		 		
		  	}		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resp=0;
	     }	
		return resp;
	}

	public long getEnviarPedidoInventario(Pedido pedido )
	{

		long resp=0;
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_INVENTARIO);

			PropertyInfo prPedido=new PropertyInfo();
			prPedido.setName("Pedido");
			prPedido.setValue(pedido);
			prPedido.setType(Pedido.class);
			request.addProperty(prPedido);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado

			envelope.addMapping(SOAP_ACTION, "Pedido", new Pedido().getClass());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
			if(res!=null)
			{
				resp =Long.parseLong(res.toString());
			}
		}
		catch (Exception e)
		{
			resp=0;
		}
		return resp;
	}
}
