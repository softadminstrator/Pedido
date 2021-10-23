package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.principal.mundo.ArticulosPedido;

/**
 * Clase en la que se envian pedidos al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class PutPedidoSys {
	
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
    private static final String METHOD_NAME = "PutPedido";
	/**
	 * referencia para el resultado de la peticion al servicio web
	 */
	private long res;
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public PutPedidoSys(String ip, String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
		res=0;
	}
	
	/**
	 * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys
	 * @param pedido
	 * @param cliente
	 * @return res
	 */
	public long setPedido(String xmlPedido, String xmlArticulos){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prPedido = new PropertyInfo();
		 prPedido.name="xmlPedido";
		 prPedido.type=String.class;
		 prPedido.setValue(xmlPedido);
		 request.addProperty(prPedido);
		 
		 PropertyInfo prArticulos = new PropertyInfo();
		 prArticulos.name="xmlArticulos";
		 prArticulos.type=String.class;
		 prArticulos.setValue(xmlArticulos);
		 request.addProperty(prArticulos);
		 
			 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
         SoapPrimitive response = (SoapPrimitive)envelope.getResponse();         
         String resp= response.toString();
         if(resp!=null)
		 	{		 		
        	 res =Long.parseLong(resp.toString());		 		
		  	}		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	
	    	 res=0;
	     }	
		return res;
	}
}
