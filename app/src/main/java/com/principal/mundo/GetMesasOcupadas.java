package com.principal.mundo;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Clase que se encarga de obtener la fecha de sistema que sera tenida en cuenta en el momento de 
 * enviar pedidos al sistema
 * @author Javier
 *
 */
public class GetMesasOcupadas {

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
		private static final String METHOD_NAME = "GetMesasOcupadas";
	
		/**
		 * metodo que se encarga de asignar valores a los atributos de la clase
		 * @param ip
		 */
		public GetMesasOcupadas(String ip, String Webid)
		{
			URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
		}
		
		/**
		 * metodo que se encarga de realizar la llamada al servicio web del sistema PosStar
		 * y obtener la fecha actual de dicho sistema
		 * @return Fecha
		 */
		public ArrayList<Long> GetMesasOcupadas()
		{
			ArrayList<Long> listaMesasOcupadas = new ArrayList<Long>();
			try
			 {	
	   	 
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
			 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
			 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre			
			 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
			 	if(res!=null)
			 	{
			 		listaMesasOcupadas =getMesasOcupadas(res.toString());		 		
			  	}			 		       	         	         
		     }
		     catch (Exception e)
		     {
		    	 listaMesasOcupadas =null;
		     }	
			return listaMesasOcupadas;
		}
		
		private ArrayList<Long> getMesasOcupadas(String xmlMesas)
		{
			if(!xmlMesas.equals(""))
			{
				ArrayList<Long> listaMesasOcupadas = new ArrayList<Long>();
				try
				{		 
				 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				 DocumentBuilder db = dbf.newDocumentBuilder();
				 InputSource is = new InputSource();
				 is.setCharacterStream(new StringReader(xmlMesas));
				 Document doc = db.parse(is);
				 NodeList nodes = doc.getElementsByTagName("NMesa");
				// iterate the employees
				 if(nodes!=null)
				 {
					 for (int i = 0; i < nodes.getLength(); i++) {
		        	  Element line = (Element) nodes.item(i);
		        	  listaMesasOcupadas.add(Long.parseLong(getCharacterDataFromElement(line)));
					 	}	       
			        return listaMesasOcupadas; 
				 }			
				else
				{
					listaMesasOcupadas=null;
				}
				}
				catch(Exception e)
				{
					listaMesasOcupadas=null;
					System.out.println(e.toString());
				}
			}
			return null; 
		}
		 public static String getCharacterDataFromElement(Element e)
		 {
			    Node child = e.getFirstChild();
			    if (child instanceof CharacterData) {
			       CharacterData cd = (CharacterData) child;
			       if(cd.getData().equals("BARRA"))
			       {
			    	   return "0";
			       }
			       return cd.getData();
			    }
			    return "?";
		 }

}
