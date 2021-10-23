package com.principal.mundo.sysws;

import java.io.StringReader;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.xml.sax.InputSource;



public class GetPedidoMesa {
	private String error="";
	
	 /**
    * nombre del dominio al que sera enviada la anulacion del pedido
    */
	    private static final String NAMESPACE = "http://operator.wscashserver.com";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";
	    private String mensaje="";
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */		
		private static final String METHOD_NAME = "GetPedidoMesa";
		com.principal.mundo.sysws.Pedido pedido;
		
	
	/**
	 * @param ip
	 */
	public GetPedidoMesa(String ip, String Webid)
	{
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
	}
	
	/**
	 * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
	 * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
	 * y los almacena en un arreglo
	 *
	 * @return Lista de clientes
	 */
	public com.principal.mundo.sysws.Pedido GetPedidoM(String NMesa)
	{
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 //PropertyInfo prcedula = new PropertyInfo();
		 //prcedula.name="NMesa";
		 //prcedula.type=String.class;
		 //prcedula.setValue(NMesa);
			// request.addProperty(prcedula);
		 request.addProperty("NMesa",NMesa);

		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
	    
///	     SoapObject res = (SoapObject) envelope.getResponse();
//		 	SoapObject res = (SoapObject) envelope.getResponse();
//			 SoapObject res=null;
			 SoapPrimitive res =(SoapPrimitive)envelope.getResponse();

	     pedido=new com.principal.mundo.sysws.Pedido();
			 pedido=getPedido(res.toString());
			// return pedido;
	     //pedido.setNPedido("0");
	     //pedido.setObservaciones("ok");
		 //	if (pedido != null)
		 //	{
			//	return pedido;
				/*
		 		pedido.setNPedido(res.getProperty("NPedido").toString());
		 		if(pedido.getNPedido().equals("0"))
		 		{
		 			return pedido;
		 		}
	 			pedido.setNCaja(res.getProperty("NCaja").toString());
				pedido.setNMesa(res.getProperty("NMesa").toString());
				System.out.println("Error3 "+pedido.getNMesa());
				pedido.setEstado(res.getProperty("estado").toString());
				pedido.setFecha(res.getProperty("fecha").toString());
				pedido.setHora(res.getProperty("hora").toString());
				pedido.setIdCliente(res.getProperty("idCliente").toString());
				pedido.setIdVendedor(res.getProperty("idVendedor").toString());
				pedido.setObservaciones(res.getProperty("observaciones").toString());
				pedido.setTotalPedido(res.getProperty("totalPedido").toString());
				pedido.setXmlArticulos(res.getProperty("xmlArticulos").toString());
				*/
			
			//}
		 	//else
		 	//{
		 	//	return null;
		 	//}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 mensaje=e.toString();
	    	 return null;
	     }	
		return pedido;
	}

	public  Pedido getPedido(String xmlPedido )
	{
		if(!xmlPedido.equals(""))
		{
			Pedido ped=new Pedido();
			try
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlPedido));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Datos");
				// iterate the employees
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);


					for (int j = 0; j < ped.getPropertyCount(); j++) {
						NodeList name = element.getElementsByTagName(ped.getPropertyName(j));
						Element line = (Element) name.item(0);
						if(j==10)
						{
							ped.setProperty(j,getCharacterDataFromElement2(line));
						}
						else
						{
							ped.setProperty(j, getCharacterDataFromElement(line));
						}
					}

				}
				return ped;
			}
			catch(Exception e)
			{
				ped=null;
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
	public static String getCharacterDataFromElement2(Element e) {
		Node child = e.getFirstChild();
		try {
			StringWriter writer = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(child), new StreamResult(writer));
			return writer.getBuffer().toString();
		}
		catch (Exception e3)
		{

		}
		return "?";
	}




	public String getError() {
		return error;
	}
	public String getMensaje() {
		return mensaje;
	}
}
