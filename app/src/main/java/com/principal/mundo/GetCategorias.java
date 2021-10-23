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
public class GetCategorias {
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
		private static final String METHOD_NAME = "GetCategorias";
		private String resultado="";
		private ArrayList<Categoria> listaCategorias;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public GetCategorias(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
		listaCategorias=new ArrayList<Categoria>();
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public String getCategorias()
	{
		
		try
		 {
			
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);	
		 request.addProperty("FechaA","201101011200");
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

				    Categoria categoria=new Categoria();				               
					SoapObject ic = (SoapObject)res.getProperty(i);
					
						if(ic!=null)
						{
							categoria.setIdCategoria(Integer.parseInt(ic.getPropertyAsString("IdCategoria")));
							categoria.setNombre(categoria.valspace(ic.getProperty("Nombre").toString()));
							categoria.setFechaAct(ic.getProperty("FechaAct").toString());
							categoria.setActivo(1);
							categoria.setHabilidada(0);
							listaCategorias.add(categoria);
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

	public ArrayList<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(ArrayList<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
}
