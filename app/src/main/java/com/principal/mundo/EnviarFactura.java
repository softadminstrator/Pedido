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
public class EnviarFactura {
	
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";
//		private static String URL="http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
		private static final String METHOD_NAME = "PutFacturaCredito";
//		private static final String SOAP_ACTION ="http://www.elchispazo.com.co/PutPedido";
		
	
	/**
	 * metodo que se encargar de asignar valores a los atributos de la clase
	 * asigna direccion ip del servidor web del sistema PosStar
	 * @param ip
	 */
	public EnviarFactura(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	
//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
	/**
	 * metodo que se encarga de enviar pedido al sistema PosStar
	 * @param factura
	 * @return 1 si fue enviado correctamente o 0 en caso contrario
	 */
	public Factura_in getEnviarFactura(Factura factura, Factura_in factura_in )
	{
		
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 PropertyInfo prPedido=new PropertyInfo();
		 prPedido.setName("Factura");
		 prPedido.setValue(factura);
		 prPedido.setType(Factura.class);		 
		 request.addProperty(prPedido);		
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.implicitTypes = true;
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 		 
		 envelope.addMapping(NAMESPACE+METHOD_NAME, "Factura", new Factura().getClass());
		
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 SoapObject res = (SoapObject) envelope.getResponse();
		 if(res!=null)
		 	{		 		
			 	factura_in.setRazonSocial(res.getProperty("RazonSocial").toString());
			 	factura_in.setRepresentante(res.getProperty("Representante").toString());
			 	factura_in.setRegimenNit(res.getProperty("RegimenNit").toString());
			 	factura_in.setDireccionTel(res.getProperty("DireccionTel").toString());
			 	factura_in.setIdCodigoExterno(Long.parseLong(res.getProperty("NFactura").toString()));
				factura_in.setNCaja(Long.parseLong(res.getProperty("NCaja").toString()));
				factura_in.setPrefijo(res.getProperty("Prefijo").toString());
				factura_in.setFecha(res.getProperty("Fecha").toString());
				factura_in.setHora(res.getProperty("Hora").toString());
				factura_in.setBase0(Long.parseLong(res.getProperty("Base0").toString()));
				factura_in.setBase5(Long.parseLong(res.getProperty("Base5").toString()));
				factura_in.setBase10(Long.parseLong(res.getProperty("Base10").toString()));
				factura_in.setBase14(Long.parseLong(res.getProperty("Base14").toString()));
				factura_in.setBase16(Long.parseLong(res.getProperty("Base16").toString()));
				factura_in.setIva5(Long.parseLong(res.getProperty("Iva5").toString()));
				factura_in.setIva10(Long.parseLong(res.getProperty("Iva10").toString()));
				factura_in.setIva14(Long.parseLong(res.getProperty("Iva14").toString()));
				factura_in.setIva16(Long.parseLong(res.getProperty("Iva16").toString()));
				factura_in.setImpoCmo(Long.parseLong(res.getProperty("ImpoCmo").toString()));
				factura_in.setTotalFactura(Long.parseLong(res.getProperty("TotalFactura").toString()));
				factura_in.setResDian(res.getProperty("ResDian").toString());
				factura_in.setRango(res.getProperty("Rango").toString());
				factura_in.setIdBodega(Long.parseLong(res.getProperty("IdBodega").toString()));
				factura_in.setNombreVendedor(res.getProperty("NombreVendedor").toString());
				factura_in.setTelefonoVendedor(res.getProperty("TelefonoVendedor").toString());
		  	}
		 else
			{
			 	factura_in.setIdCodigoExterno(0);
//			 	factura_in.cargarFacturaPrueba();
			}
	     }
	     catch (Exception e)
	     {
	    	 factura_in.setIdCodigoExterno(0);
//	    	 factura_in.setRazonSocial(e.toString());
	    	
	     }	
		return factura_in;
	}
	
	
	
	
}
