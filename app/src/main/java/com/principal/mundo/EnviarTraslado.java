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
public class EnviarTraslado {
	
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";
//		private static String URL="http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
		private static final String METHOD_NAME = "PutTraslado";
//		private static final String SOAP_ACTION ="http://www.elchispazo.com.co/PutPedido";
		
	
	/**
	 * metodo que se encargar de asignar valores a los atributos de la clase
	 * asigna direccion ip del servidor web del sistema PosStar
	 * @param ip
	 */
	public EnviarTraslado(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	
//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
	/**
	 * metodo que se encarga de enviar pedido al sistema PosStar
	 * @param factura
	 * @return 1 si fue enviado correctamente o 0 en caso contrario
	 */
	public Traslado_in getEnviarTraslado(Traslado traslado, Traslado_in traslado_in )
	{
		
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 PropertyInfo prPedido=new PropertyInfo();
		 prPedido.setName("Traslado");
		 prPedido.setValue(traslado);
		 prPedido.setType(Traslado.class);		 
		 request.addProperty(prPedido);		
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.implicitTypes = true;
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 		 
		 envelope.addMapping(NAMESPACE+METHOD_NAME, "Traslado", new Traslado().getClass());
		
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 SoapObject res = (SoapObject) envelope.getResponse();
		 if(res!=null)
		 	{		 		
			 	traslado_in.setRazonSocial(res.getProperty("RazonSocial").toString());
			 	traslado_in.setRepresentante(res.getProperty("Representante").toString());
			 	traslado_in.setRegimenNit(res.getProperty("RegimenNit").toString());
			 	traslado_in.setDireccionTel(res.getProperty("DireccionTel").toString());
			 	traslado_in.setIdCodigoExterno(Long.parseLong(res.getProperty("NTraslado").toString()));
				traslado_in.setNCaja(Long.parseLong(res.getProperty("NCaja").toString()));
				traslado_in.setFecha(res.getProperty("Fecha").toString());
				traslado_in.setHora(res.getProperty("Hora").toString());
				traslado_in.setBase0(Long.parseLong(res.getProperty("Base0").toString()));
				traslado_in.setBase5(Long.parseLong(res.getProperty("Base5").toString()));
				traslado_in.setBase10(Long.parseLong(res.getProperty("Base10").toString()));
				traslado_in.setBase14(Long.parseLong(res.getProperty("Base14").toString()));
				traslado_in.setBase16(Long.parseLong(res.getProperty("Base16").toString()));
				traslado_in.setIva5(Long.parseLong(res.getProperty("Iva5").toString()));
				traslado_in.setIva10(Long.parseLong(res.getProperty("Iva10").toString()));
				traslado_in.setIva14(Long.parseLong(res.getProperty("Iva14").toString()));
				traslado_in.setIva16(Long.parseLong(res.getProperty("Iva16").toString()));
				traslado_in.setImpoCmo(Long.parseLong(res.getProperty("ImpoCmo").toString()));
				traslado_in.setTotalTraslado(Long.parseLong(res.getProperty("TotalTraslado").toString()));
				traslado_in.bodegaOrigen.setIdBodega(Integer.parseInt(res.getProperty("IdBodegaOrigen").toString()));
				traslado_in.bodegaDestino.setIdBodega(Integer.parseInt(res.getProperty("IdBodegaDestino").toString()));
//				traslado_in.setNombreVendedor(res.getProperty("NombreVendedor").toString());
//				traslado_in.setTelefonoVendedor(res.getProperty("TelefonoVendedor").toString());
		  	}
		 else
			{
			 	traslado_in.setIdCodigoExterno(0);
//			 	traslado_in.cargarFacturaPrueba();
			}
	     }
	     catch (Exception e)
	     {
	    	 traslado_in.setIdCodigoExterno(0);
//	    	 traslado_in.setRazonSocial(e.toString());
	    	
	     }	
		return traslado_in;
	}
	
	
	
	
}
