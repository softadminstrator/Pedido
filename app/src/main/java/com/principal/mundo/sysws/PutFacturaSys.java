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

/**
 * Clase en la que se envian pedidos al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class PutFacturaSys {
	
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
    private static final String METHOD_NAME = "PutFactura";

	private static final String METHOD_NAME_ANULAR = "PutAnularFactura";
	/**
	 * referencia para el resultado de la peticion al servicio web
	 */
	private String res;

	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param ip
	 */
	public PutFacturaSys(String ip , String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
	}
	
	/**
	 * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys
	 * @param pedido
	 * @param cliente
	 * @return res
	 */
	public Factura_in setFactura(String xmlFactura, String xmlArticulos,Factura_in factura_in){
		
		 try {
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prFactura = new PropertyInfo();
		 prFactura.name="xmlFactura";
		 prFactura.type=String.class;
		 prFactura.setValue(xmlFactura);
		 request.addProperty(prFactura);
		 
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
        	 factura_in =getFacturaXml(resp.toString(),factura_in);		 		
		  	}	
         else
         {
        	 factura_in.setNFactura(0);
         }
	     }
	     catch (Exception e)
	     {
	    	 res="Error "+e.toString();
	    	 factura_in.setNFactura(0);
	     }	
		return factura_in;
//		 return null;
	}
	public String getRes() {
		return res;
	}
	
	public Factura_in getFacturaXml(String xmlFactura,Factura_in factura_in)
	{
		if(!xmlFactura.equals(""))
		{
			
			try
			{		 
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 DocumentBuilder db = dbf.newDocumentBuilder();
			 InputSource is = new InputSource();
			 is.setCharacterStream(new StringReader(xmlFactura));
			 Document doc = db.parse(is);
			 NodeList nodes = doc.getElementsByTagName("DatosFactura");
			// iterate the employees
		        for (int i = 0; i < nodes.getLength(); i++) {
		           Element element = (Element) nodes.item(i);
		           
		          
		           for (int j = 0; j < factura_in.getPropertyCountDatosFactura(); j++) {
		        	   NodeList name = element.getElementsByTagName(factura_in.getPropertyNameDatosFactura(j));
			           Element line = (Element) name.item(0);
			           factura_in.setProperty(j,getCharacterDataFromElement(line));		         		
		           }
		        
		        }
		        return factura_in; 
			}
			catch(Exception e)
			{
				 res="Error "+xmlFactura+e.toString();
				factura_in=null;
			}
		}
		return null; 
	}
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?";
	  }



	public Factura_in setAnularFactura(Factura_in factura_in){

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_ANULAR);

			PropertyInfo prFactura = new PropertyInfo();
			prFactura.name="NCaja";
			prFactura.type=String.class;
			prFactura.setValue(factura_in.getNCaja());
			request.addProperty(prFactura);

			PropertyInfo prArticulos = new PropertyInfo();
			prArticulos.name="NFactura";
			prArticulos.type=String.class;
			prArticulos.setValue(factura_in.getNFactura());
			request.addProperty(prArticulos);



			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);

			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.call(NAMESPACE + METHOD_NAME_ANULAR, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			String resp= response.toString();
			if(resp!=null)
			{
				factura_in.setAnulada("1");
			}
			else
			{
				factura_in.setAnulada("0");
			}
		}
		catch (Exception e)
		{
			res="Error "+e.toString();
			factura_in.setAnulada("0");
		}
		return factura_in;
//		 return null;
	}
}
