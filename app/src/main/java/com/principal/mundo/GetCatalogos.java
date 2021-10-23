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
public class GetCatalogos {
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
		private static final String METHOD_NAME = "GetCatalogo";
		private String resultado="";
		private ArrayList<Catalogo> listaCatalogos;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public GetCatalogos(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
		listaCatalogos=new ArrayList<Catalogo>();
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public String getCatalogos()
	{
		
		try
		 {
			
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);	
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

				    Catalogo catalogo=new Catalogo();				               
					SoapObject ic = (SoapObject)res.getProperty(i);
					
						if(ic!=null)
						{
							catalogo.setIdCatalogo(Integer.parseInt(ic.getPropertyAsString("IdCatalogo")));
							catalogo.setNombre(catalogo.valspace(ic.getProperty("Nombre").toString()));
							catalogo.setActivo(1);
							listaCatalogos.add(catalogo);
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

	public ArrayList<Catalogo> getListaCatalogos() {
		return listaCatalogos;
	}

	public void setListaCatalogos(ArrayList<Catalogo> listaCatalogos) {
		this.listaCatalogos = listaCatalogos;
	}

	
}
