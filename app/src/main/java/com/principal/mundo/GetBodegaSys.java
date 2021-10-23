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
public class GetBodegaSys {
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://operator.wscashserver.com";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";	
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */
		private static final String METHOD_NAME = "GetBodega";
		private String resultado="";
		private ArrayList<Bodega> listaBodegas;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public GetBodegaSys(String ip, String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
		listaBodegas=new ArrayList<Bodega>();
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
		 	//SoapObject res = (SoapObject) envelope.getResponse();
	
		 
		 try
		 {
				 java.util.Vector<SoapObject> res = (java.util.Vector<SoapObject>) envelope.getResponse();
				
				 	if (res != null) 
				 	{//comprobamos que la respuesta no esté vacía
		                //recorremos el objeto
				 		String [] temres= new String [res.size()];
				 		int ta = temres.length ;
							for (int i = 0;i < ta ; i++) {
								Bodega bodega=new Bodega();	
								SoapObject ic = (SoapObject)res.get(i);	
								bodega.setIdBodega(Integer.parseInt(ic.getProperty("idBodega").toString()));
								bodega.setBodega(ic.getProperty("bodega").toString());
								bodega.setDireccion(ic.getProperty("direccion").toString());
								bodega.setTelefono(ic.getProperty("telefono").toString());
								bodega.setResponsable(ic.getProperty("responsable").toString());
								bodega.setMunicipio(ic.getProperty("municipio").toString());
								listaBodegas.add(bodega);
					                        
							}		
							resultado= "true";
				 	}	 	
				 	else 
				 	{
				 		resultado= "false";
				 	}
		 }
		 catch (Exception e)
	     {
			SoapObject res = (SoapObject) envelope.getResponse();
				
			 	if (res != null) 
			 	{
			 		Bodega bodega=new Bodega();	
					bodega.setIdBodega(Integer.parseInt(res.getProperty("idBodega").toString()));
					bodega.setBodega(res.getProperty("bodega").toString());
					bodega.setDireccion(res.getProperty("direccion").toString());
					bodega.setTelefono(res.getProperty("telefono").toString());
					bodega.setResponsable(res.getProperty("responsable").toString());
					bodega.setMunicipio(res.getProperty("municipio").toString());
					listaBodegas.add(bodega); 
					resultado= "true";
			 	}	 	
			 	else 
			 	{
			 		resultado= "false";
			 	}
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
