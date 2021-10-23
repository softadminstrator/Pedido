package com.principal.mundo;

import java.util.ArrayList;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * Clase que se encarga de actualizar los articulos de la aplicacion, atraves de la llamada al 
 * servicio web del sistema PosStar
 * @author Javier
 *
 */
public class GetCatalogoArticulos {
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
		private static final String METHOD_NAME = "GetArtxCatalogo";
		private String resultado="";
		private ArrayList<Articulo> listaArticulos;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public GetCatalogoArticulos(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
		listaArticulos=new ArrayList<Articulo>();
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public String getCatalogoArticulos(String catalogo)
	{
		
		try
		 {
			
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 request.addProperty("Catalogo",catalogo);
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

					Articulo articulo=new Articulo();				               
					SoapObject ic = (SoapObject)res.getProperty(i);
					
						if(ic!=null)
						{
							articulo.idArticulo=Long.parseLong(ic.getProperty("IdArticulo").toString());
							articulo.nombre=ic.getProperty("Articulo").toString();
							articulo.precio1=Long.parseLong(ic.getProperty("Precio1").toString());
							articulo.precio2=Long.parseLong(ic.getProperty("Precio2").toString());
							articulo.precio3=Long.parseLong(ic.getProperty("Precio3").toString());
							articulo.urlImagen=ic.getProperty("UrlImagen").toString();
							listaArticulos.add(articulo);
						}
						resultado="true";
					}				
		 	}
		 	else 
		 	{
		 		resultado= "false";
		 	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resultado ="desc";
	     }	
		return resultado;
	}

	public ArrayList<Articulo> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos(ArrayList<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	

	
}
