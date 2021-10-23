package com.principal.mundo;

import java.io.StringReader;

import java.util.ArrayList;

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
import org.xml.sax.InputSource;

/**
 * Clase que se encarga de actualizar los articulos de la aplicacion, atraves de la llamada al 
 * servicio web del sistema PosStar
 * @author Javier
 *
 */
public class LlamarStockSys {
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
		private static final String METHOD_NAME = "GetFilterArti";
	
		
		
		private Parametros parametros;
		
		private String resultado;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public LlamarStockSys(Parametros parametros)
	{	
		this.parametros=parametros;
		resultado="";
		URL="http://"+parametros.getIp()+":8083/WSCashServer" +parametros.getWebidText() +"/services/OperatorClass?wsdl";
		
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public ArrayList<Articulo> getArticulos(String operacion,String idBodega,String text, ArrayList<Articulo> listaArticulos)
	{
		
		ArrayList<Articulo> l = listaArticulos;
		try
		 {
		 SoapObject request=null;		
		 request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo properacion = new PropertyInfo();
		 properacion.name="operacion";
		 properacion.type=String.class;
		 properacion.setValue(operacion);
		 request.addProperty(properacion);
		 
		 PropertyInfo pridBodega = new PropertyInfo();
		 pridBodega.name="idBodega";
		 pridBodega.type=String.class;
		 pridBodega.setValue(idBodega);
		 request.addProperty(pridBodega);
		 
		 PropertyInfo prtext = new PropertyInfo();
		 prtext.name="text";
		 prtext.type=String.class;
		 prtext.setValue(text);
		 request.addProperty(prtext);

		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre		
		 
		 androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 resultado="ok";		 	
		 	if (res != null) 		 		
		 	{
		 		l=getListaArticulos(res.toString());	 	 					
		 	}
		 	else
		 	{
		 		return l;
		 	}
	     }
	     catch (Exception e)
	     {
	    	 resultado=e.toString();
	    	 System.out.println("error " +resultado.toString());
	     }	
		return l;
	}
	
	public  ArrayList<Articulo> getListaArticulos(String xmlArticulos )
	{
		if(!xmlArticulos.equals(""))
		{
			xmlArticulos.replaceAll("&","&amp;");
			 ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
			try
			{		 
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 DocumentBuilder db = dbf.newDocumentBuilder();
			 InputSource is = new InputSource();
			 is.setCharacterStream(new StringReader(xmlArticulos));
			 Document doc = db.parse(is);
			 NodeList nodes = doc.getElementsByTagName("Articulo");
			// iterate the employees
			 if(nodes!=null)
			 {
			        for (int i = 0; i < nodes.getLength(); i++) {
			        	 Element element = (Element) nodes.item(i);	           
				           Articulo articulo=new Articulo();
				           for (int j = 0; j < articulo.getPropertyCount(); j++) {
					        	NodeList name = element.getElementsByTagName(articulo.getPropertyName(j));
								if(articulo.getPropertyName(j).equals("CodBarra"))
				        	    {
				        		 for (int k = 0; k < name.getLength(); k++) {
				        			 Element elementCB = (Element) name.item(k);
				        			 NodeList nameCB = elementCB.getElementsByTagName("string");
				        			 Element lineCB = (Element) nameCB.item(0);
				        			 if(lineCB!=null)
				        			 {
				        				 articulo.getCodigos().add(getCharacterDataFromElement(lineCB));

				        			 }
				        		 }
				        	    }
				        	    else
				        	    {
				        	    	if(name.item(0)!=null)
				        	    	{
					        		   Element line = (Element) name.item(0);
							           articulo.setProperty(j,getCharacterDataFromElement(line));
				        	    	}
				        	    }
			           }
			           listaArticulos.add(articulo);
		        }
			     resultado="true";
		        return listaArticulos; 
		      
			 }			
			else
			{
				 resultado="false";
				listaArticulos=null;
			}
			}
			catch(Exception e)
			{
				listaArticulos=null;
				 resultado=e.toString();
				System.out.println(e.toString());
			}
		}
		return null; 		 
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	 public static String getCharacterDataFromElement(Element e) {
		    Node child = e.getFirstChild();
		    if (child instanceof CharacterData) {
		       CharacterData cd = (CharacterData) child;
		       return cd.getData();
		    }
		    return "Sin Codigo";
		  }
	

}
