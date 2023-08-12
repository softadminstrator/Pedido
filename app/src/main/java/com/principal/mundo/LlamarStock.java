package com.principal.mundo;

import java.io.StringReader;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.principal.mundo.Articulo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Clase que se encarga de obtener los clientes luego de invocar el servicio web del sistema PosStar
 * para luego ser almacenados en el telefono
 * @author Javier
 *
 */
public class LlamarStock {
	
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://www.starlap.co/";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";	
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */		
		private static final String METHOD_NAME = "GetFilterArti";
		
		private String resultado="";

	private Parametros parametros;
		
	
	/**
	 * @param ip
	 */
	public LlamarStock(String ip,Parametros parametros)
	{	
		//URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
		URL="http://"+ip+":81/ServicioArticulosMail/SrvArticulos.asmx";
		this.parametros=parametros;
	}
	
	/**
	 * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
	 * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
	 * y los almacena en un arreglo
	 * @param cedula
	 * @return Lista de clientes
	 */
	public ArrayList<Articulo> getLlamarStock(String operacion, String text, long IdBodega )
	{
		ArrayList<Articulo>  l = new ArrayList<Articulo> ();
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 request.addProperty("key","A3GzPviy4TDawSi");
		 request.addProperty("idEntity",parametros.getWebidText());
		 request.addProperty("operacion",operacion);
		 request.addProperty("idBodega",IdBodega);
		 request.addProperty("text",text);

			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	SoapObject res = (SoapObject) envelope.getResponse();



		 	if (res != null) 
		 	{//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
		 		String [] temres= new String [res.getPropertyCount()];
		 		int ta = temres.length -1;
					for (int i = 0;i < ta ; i++) {

					Articulo articulo = new Articulo();						
					SoapObject ic = (SoapObject)res.getProperty(i);

					for (int j = 0; j < articulo.getPropertyCount(); j++) {
						if(articulo.getPropertyName(j).equals("CodBarra"))
						{
							articulo.getCodigos().add(ic.getPropertySafelyAsString("CodBarra", ""));
						}
						else {
							articulo.setProperty(j, ic.getPropertySafelyAsString(articulo.getPropertyName(j), "").toString());
						}
					}
						/*
					articulo.setIdArticulo(Long.parseLong(ic.getProperty("IdArticulo").toString()));
					articulo.setStock(Double.parseDouble(ic.getProperty("Stock").toString()));
					articulo.setNombre(ic.getProperty("Articulo").toString());	
					articulo.setPrecio1(Long.parseLong(ic.getProperty("Precio1").toString()));
					articulo.setPrecio2(Long.parseLong(ic.getProperty("Precio2").toString()));
					articulo.setPrecio3(Long.parseLong(ic.getProperty("Precio3").toString()));
					articulo.setIdCodigo("Sin Codigo");
					articulo.setActivo(1);
						 */



					l.add(articulo);
					resultado="true";
					}				
		 	}
		 	else 
		 	{
		 		resultado="false";
		 		return null;
		 	}

		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 resultado="desc";
	    	 return null;
	     }	
		return l;
	}

	public String getRes() {
		return resultado;
	}

	public void setRes(String resultado) {
		this.resultado = resultado;
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
