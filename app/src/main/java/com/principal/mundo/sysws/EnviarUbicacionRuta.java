package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase en la que se envia la ubicacion de la ruta en terreno 
 * al sistema de georeferenciacion  SYS
 * @author Javier
 *
 */
public class EnviarUbicacionRuta {
	
	 /**
		 * nombre del dominio al que sera enviada la anulacion del pedido
		 */
	    private static final String NAMESPACE = "http://wscoordenadas.principal.com/";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */	
	    private static final String METHOD_NAME = "setUbicacion";
		/**
		 * referencia para el resultado de la peticion al servicio web
		 */
		private String res;
	  
		

		/**
		 * metodo que se encarga de asignar valores a los atributos de la clase
		 * @param ip
		 */
	public EnviarUbicacionRuta(String ip) {
		res="";
		URL="http://"+ip+":8081/WSCoordenadas/SYSGPS?wsdl";
	}
	
	/**
	 * metodo que se encarga de realizar la llamada al servicio web del sistema de georeferenciacion
	 * y enviar la localizacion de la ruta
	 * @param ruta
	 * @return res
	 */
	public String setUbicacionRuta(Ruta ruta){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prCliente=new PropertyInfo();
		 prCliente.setName("ruta");
		 prCliente.setValue(ruta);
		 prCliente.setType(Ruta.class);		 
		 request.addProperty(prCliente);
		 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
	     SoapPrimitive response = (SoapPrimitive)envelope.getResponse();         
	     res= response.toString();	           
       }   
       catch (Exception e) {
       		res = "Err. "+e.toString();
       }	   
		return res;
	}

}
