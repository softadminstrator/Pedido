package com.principal.mundo;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.principal.mundo.Bodega;

/**
 * Clase que se encarga de actualizar los articulos de la aplicacion, atraves de la llamada al 
 * servicio web del sistema PosStar
 * @author Javier
 *
 */
public class GetBodega {
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
		private static final String METHOD_NAME = "GetBodega";
		private String resultado="";
		ArrayList<Bodega> listaBodegas;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public GetBodega(String ip)
	{		
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public String getBodega()
	{
		
		listaBodegas = new ArrayList<Bodega>();
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

					Bodega bodega=new Bodega();				               
					SoapObject ic = (SoapObject)res.getProperty(i);
					
						if(ic!=null)
						{
							bodega.setIdBodega(Integer.parseInt(ic.getProperty("IdBodega").toString()));
							bodega.setBodega(ic.getProperty("Bodega").toString());
							bodega.setDireccion(ic.getProperty("Direccion").toString());
							bodega.setTelefono(ic.getProperty("Telefono").toString());
							bodega.setResponsable(ic.getProperty("Responsable").toString());
							bodega.setMunicipio(ic.getProperty("Municipio").toString());
							listaBodegas.add(bodega);
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

	public ArrayList<Bodega> getListaBodegas() {
		return listaBodegas;
	}

	public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
		this.listaBodegas = listaBodegas;
	}


}
