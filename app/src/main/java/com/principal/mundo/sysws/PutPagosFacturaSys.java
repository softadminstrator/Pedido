package com.principal.mundo.sysws;

import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import com.principal.mundo.Factura_in;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;

/**
 * Clase en la que se envian pedidos al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class PutPagosFacturaSys {
	
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
    private static final String METHOD_NAME = "PutPagosFactura";
	/**
	 * referencia para el resultado de la peticion al servicio web
	 */
	private String res;
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public PutPagosFacturaSys(String ip, String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
	}
	

	public PagosFactura setPagosFactura(String xmlDatosFac,PagosFactura pagosFactura){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prFactura = new PropertyInfo();
		 prFactura.name="xmlDatosFac";
		 prFactura.type=String.class;
		 prFactura.setValue(xmlDatosFac);
		 request.addProperty(prFactura);

			 /**
		 PropertyInfo prArticulos = new PropertyInfo();
		 prArticulos.name="xmlPagosFac";
		 prArticulos.type=String.class;
		 prArticulos.setValue(xmlPagosFac);
		 request.addProperty(prArticulos);
			  **/
		 
		 
		 	 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
         SoapPrimitive response = (SoapPrimitive)envelope.getResponse();         
         String resp= response.toString();
         if(resp!=null)
		 	{
				 //obtiene Nuevo saldo
				String[] parts = resp.split(";");
        	 pagosFactura.setNPagoFac(Long.parseLong(parts[0]));
			 pagosFactura.setSaldo(Long.parseLong(parts[1]));
		  	}	
         else
         {
        	 pagosFactura.setNPagoFac(0);
         }
	     }
	     catch (Exception e)
	     {
	    	 res="Error "+e.toString();
	    	 pagosFactura.setNPagoFac(0);
	     }	
		return pagosFactura;
//		 return null;
	}
	public String getRes() {
		return res;
	}
	
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?";
	  }
}
