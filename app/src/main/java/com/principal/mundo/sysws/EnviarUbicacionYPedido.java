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
public class EnviarUbicacionYPedido {
	
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
    private static final String METHOD_NAME = "setPedido";
	/**
	 * referencia para el resultado de la peticion al servicio web
	 */
	private String res;
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public EnviarUbicacionYPedido(String ip) {
		res="";
		URL="http://"+ip+":8081/WSCoordenadas/SYSGPS?wsdl";
	}
	
	/**
	 * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys
	 * @param pedido
	 * @param cliente
	 * @return res
	 */
	public String setUbicacionYPedido(Pedido pedido, Cliente cliente){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prPedido=new PropertyInfo();
		 prPedido.setName("pedido");
		 prPedido.setValue(pedido);
		 prPedido.setType(Pedido.class);		 
		 request.addProperty(prPedido);
		 
		 PropertyInfo prCliente=new PropertyInfo();
		 prCliente.setName("cliente");
		 prCliente.setValue(cliente);
		 prCliente.setType(Cliente.class);		 
		 request.addProperty(prCliente);
		
		 
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
