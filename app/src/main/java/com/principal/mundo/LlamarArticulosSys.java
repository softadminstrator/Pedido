package com.principal.mundo;

import com.principal.mundo.sysws.Observacion;

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
public class LlamarArticulosSys {
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
		private static final String METHOD_NAME = "GetArtxCategoriaFecha";
		private static final String METHOD_NAME2 = "GetArticulos";
		
		
		private Parametros parametros;
		
		private String resultado;
		
		
	
	/**
	 * metodo que se encarga asignar valores a los atributos de la clase
	 * @param ip
	 */
	public LlamarArticulosSys(Parametros parametros)
	{	
		this.parametros=parametros;
		resultado="";
		URL="http://"+parametros.getIp()+":8083/WSCashServer"+parametros.getWebidText()+ "/services/OperatorClass?wsdl";

		
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public ArrayList<Articulo> getArticulos(boolean isAll,long rangin, long rangout, String categoria,String fecha, ArrayList<Articulo> listaArticulos, String idBodega)
	{
		
		ArrayList<Articulo> l = listaArticulos;
		try
		 {
		SoapObject request=null;
		 if(isAll)
		 {	 
			 request = new SoapObject(NAMESPACE, METHOD_NAME2);
			 
			 PropertyInfo prRangIn = new PropertyInfo();
			 prRangIn.name="rangin";
			 prRangIn.type=String.class;
			 prRangIn.setValue(rangin);
			 request.addProperty(prRangIn);	
			 
			 PropertyInfo prRangOut = new PropertyInfo();
			 prRangOut.name="rangout";
			 prRangOut.type=String.class;
			 prRangOut.setValue(rangout);
			 request.addProperty(prRangOut);	
			 
			 PropertyInfo prfecha = new PropertyInfo();
			 prfecha.name="FechaA";
			 prfecha.type=String.class;
			 prfecha.setValue(fecha);
			 request.addProperty(prfecha);

			 PropertyInfo pridBodega = new PropertyInfo();
			 pridBodega.name="idBodega";
			 pridBodega.type=String.class;
			 pridBodega.setValue(idBodega);
			 request.addProperty(pridBodega);
		 }
		 else
		 {
			 request = new SoapObject(NAMESPACE, METHOD_NAME);
			 
			 PropertyInfo prRangIn = new PropertyInfo();
			 prRangIn.name="rangin";
			 prRangIn.type=String.class;
			 prRangIn.setValue(rangin);
			 request.addProperty(prRangIn);	
			 
			 PropertyInfo prRangOut = new PropertyInfo();
			 prRangOut.name="rangout";
			 prRangOut.type=String.class;
			 prRangOut.setValue(rangout);
			 request.addProperty(prRangOut);	
			 
			 PropertyInfo prcategoria = new PropertyInfo();
			 prcategoria.name="Categoria";
			 prcategoria.type=String.class;
			 prcategoria.setValue(categoria);
			 request.addProperty(prcategoria);
			 
			 PropertyInfo prfecha = new PropertyInfo();
			 prfecha.name="FechaA";
			 prfecha.type=String.class;
			 prfecha.setValue(fecha);
			 request.addProperty(prfecha);

			 PropertyInfo pridBodega = new PropertyInfo();
			 pridBodega.name="idBodega";
			 pridBodega.type=String.class;
			 pridBodega.setValue(idBodega);
			 request.addProperty(pridBodega);
		 }
		 
//		 request.addProperty("Categoria","ABARROTES");
//		 request.addProperty("FechaAct","201309160600");	
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //a�adimos a la conexi�n el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos as� de que funcione siempre		
		
		 if(isAll)
		 {
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME2, envelope);
		 }
		 else
		 {
			 androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 }
		 	
	    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
//		 	SoapObject res = (SoapObject) envelope.getResponse();
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
	    	 resultado=resultado+e.toString();


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
						try {
							Articulo articulo = new Articulo();
							for (int j = 0; j < articulo.getPropertyCount(); j++) {
								NodeList name = element.getElementsByTagName(articulo.getPropertyName(j));
								if (articulo.getPropertyName(j).equals("CodBarra")) {
									for (int k = 0; k < name.getLength(); k++) {
										Element elementCB = (Element) name.item(k);
										NodeList nameCB = elementCB.getElementsByTagName("string");

										for (int l = 0; l < nameCB.getLength(); l++) {
											Element lineCB = (Element) nameCB.item(l);
											if (lineCB != null) {
												articulo.getCodigos().add(getCharacterDataFromElement(lineCB));
											}
										}
									}
								} else if (articulo.getPropertyName(j).equals("Compuesto")) {
									for (int k = 0; k < name.getLength(); k++) {
										Element elementCM = (Element) name.item(k);

										NodeList nameCM = elementCM.getElementsByTagName("ClsCompuesto");

										for (int l = 0; l < nameCM.getLength(); l++) {
											Compuestos compuestos = new Compuestos();

											Element lineCM = (Element) nameCM.item(l);
											NodeList nameCMI = lineCM.getElementsByTagName("IdArtComponente");
											NodeList nameCMCNT = lineCM.getElementsByTagName("Cantidad");
											if (lineCM != null) {
												Element lineIAC = (Element) nameCMI.item(0);
												Element lineCNTCOM = (Element) nameCMCNT.item(0);
												if (nameCMI.item(0) != null & nameCMCNT.item(0) != null) {
													compuestos.setCantidad(Long.parseLong(getCharacterDataFromElement(lineCNTCOM)));
													compuestos.setIdArtComponente(Long.parseLong(getCharacterDataFromElement(lineIAC)));
													articulo.getListaCompuestos().add(compuestos);
												}
											}
										}
									}
								} else if (articulo.getPropertyName(j).equals("Observacion")) {
									for (int k = 0; k < name.getLength(); k++) {
										Element elementCM = (Element) name.item(k);

										NodeList nameCM = elementCM.getElementsByTagName("ClsObservacion");

										for (int l = 0; l < nameCM.getLength(); l++) {
											Observacion observacion = new Observacion();

											Element lineCM = (Element) nameCM.item(l);
											NodeList nameCMI = lineCM.getElementsByTagName("IdObservacion");
											NodeList nameCMCNT = lineCM.getElementsByTagName("Detalle");
											if (lineCM != null) {
												Element lineIAC = (Element) nameCMI.item(0);
												Element lineCNTCOM = (Element) nameCMCNT.item(0);
												if (nameCMI.item(0) != null & nameCMCNT.item(0) != null) {
													observacion.setDetalle(getCharacterDataFromElement(lineCNTCOM));
													observacion.setIdObservacion(getCharacterDataFromElement(lineIAC));
													articulo.getListaObservaciones().add(observacion);
												}
											}
										}
									}
								} else {
									if (name.item(0) != null) {
										Element line = (Element) name.item(0);
										articulo.setProperty(j, getCharacterDataFromElement(line));
									}
								}
							}
							listaArticulos.add(articulo);
						}
						catch (Exception e)
						{
							resultado=e.toString();
						}
		        }
		        return listaArticulos; 
			 }			
			else
			{
				listaArticulos=null;
			}
			}
			catch(Exception e)
			{
				listaArticulos=null;
				resultado=e.toString();
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
		    return "?";
		  }
	

}
