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
public class GetValidaVendedor {
	
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
	    private static final String METHOD_NAME = "GetValidaVendedor";
		/**
		 * referencia para el resultado de la peticion al servicio web
		 */
		private String res;
	  
		

		/**
		 * metodo que se encarga de asignar valores a los atributos de la clase
		 * @param ip
		 */
	public GetValidaVendedor(String ip, String Webid)
	{

		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
		res="";
	}
	
	/**
	 * metodo que se encarga de realizar la llamada al servicio web del sistema de georeferenciacion
	 * y enviar la localizacion de la ruta
	 * @param ruta
	 * @return res
	 */
	public String GetValidaVendedor(String cedula, String clave){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prcedula = new PropertyInfo();
		 prcedula.name="Cedula";
		 prcedula.type=String.class;
		 prcedula.setValue(cedula);
		 request.addProperty(prcedula);	 			 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
	     SoapPrimitive response = (SoapPrimitive)envelope.getResponse();          
	     res= response.toString();	           
       }   
       catch (Exception e) {
    	   res = "desc";
       }	   
		return res;
	}

}
