package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase en la que se envian pedidos al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class SetImpPrecuenta {

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
    private static final String METHOD_NAME = "SetImpPrecuenta";
	/**
	 * referencia para el resultado de la peticion al servicio web
	 */
	private String res;
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public SetImpPrecuenta(String ip, String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
		res="";
	}
	
	/**
	 * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys
	 * @param pedido
	 * @param cliente
	 * @return res
	 */
	public String SetImpPrecuenta(String NMesa){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prPedido = new PropertyInfo();
		 prPedido.name="NMesa";
		 prPedido.type=String.class;
		 prPedido.setValue(NMesa);
		 request.addProperty(prPedido);
		 

			 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
         SoapPrimitive response = (SoapPrimitive)envelope.getResponse();         
         res= response.toString();	           
       }   
       catch (Exception e) {
       	res = e.toString();
       }	   
		return res;
	}
}
