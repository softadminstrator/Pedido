package com.principal.mundo;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.principal.mundo.Articulo;

/**
 * Clase que se encarga de obtener los clientes luego de invocar el servicio web del sistema PosStar
 * para luego ser almacenados en el telefono
 * @author Javier
 *
 */
public class LlamarStock {
	
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
		private static final String METHOD_NAME = "GetStock";	
		
		private String resultado="";
		
	
	/**
	 * @param ip
	 */
	public LlamarStock(String ip)
	{	
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	/**
	 * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
	 * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
	 * y los almacena en un arreglo
	 * @param cedula
	 * @return Lista de clientes
	 */
	public ArrayList<Articulo> getLlamarStock(String CodBarra, String NombreArticulo, long IdBodega )
	{
		ArrayList<Articulo>  l = new ArrayList<Articulo> ();
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 request.addProperty("CodBarra",CodBarra); 
		 request.addProperty("NombreArticulo",NombreArticulo); 
		 request.addProperty("IdBodega",IdBodega); 
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	SoapObject res = (SoapObject) envelope.getResponse();
		 	
		 	
		 	if (res != null) 
		 	{//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
		 		String [] temres= new String [res.getPropertyCount()];
		 		int ta = temres.length -1;
					for (int i = 0;i < ta ; i++) {

					Articulo articulo = new Articulo();						
					SoapObject ic = (SoapObject)res.getProperty(i);						
					articulo.setIdArticulo(Long.parseLong(ic.getProperty("IdArticulo").toString()));
					articulo.setStock(Double.parseDouble(ic.getProperty("Stock").toString()));
					articulo.setNombre(ic.getProperty("Articulo").toString());	
					articulo.setPrecio1(Long.parseLong(ic.getProperty("Precio1").toString()));
					articulo.setPrecio2(Long.parseLong(ic.getProperty("Precio2").toString()));
					articulo.setPrecio3(Long.parseLong(ic.getProperty("Precio3").toString()));
					articulo.setIdCodigo("Sin Codigo");
					articulo.setActivo(1);
					l.add(articulo);
					resultado="true";
					}				
		 	}
		 	else 
		 	{
		 		resultado="false";
		 		return null;
		 	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resultado="desc";
	    	 return null;
	     }	
		return l;
	}

	public String getRes() {
		return resultado;
	}

	public void setRes(String resultado) {
		this.resultado = resultado;
	}
	
	
	

}
