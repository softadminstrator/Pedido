package com.principal.mundo.sysws;

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

public class PedidoMesaIn {

	private ArrayList<ItemPedido> listaArticulos;

	private String NMesa;
	public PedidoMesaIn() {
		listaArticulos=new ArrayList<ItemPedido>();	
	}

	
	public void getCargarArticulos(String xmlArticulos)
	{
		if(!xmlArticulos.equals(""))
		{
			listaArticulos = new ArrayList<ItemPedido>();
			try
			{		 
			 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			 DocumentBuilder db = dbf.newDocumentBuilder();
			 InputSource is = new InputSource();
			 is.setCharacterStream(new StringReader(xmlArticulos));
			 Document doc = db.parse(is);
			 NodeList nodes = doc.getElementsByTagName("Articulo");
			// iterate the employees
		        for (int i = 0; i < nodes.getLength(); i++) {
		           Element element = (Element) nodes.item(i);
		           
		           ItemPedido itemPedido=new ItemPedido();
		           for (int j = 0; j < itemPedido.getPropertyCount(); j++) {
					   if(itemPedido.getPropertyName(j).equals("ListaObservaciones"))
					   {
						   NodeList name = element.getElementsByTagName(itemPedido.getPropertyName(j));
						   for (int k = 0; k < name.getLength(); k++) {
							   Element elementCM = (Element) name.item(k);

							   NodeList nameCM = elementCM.getElementsByTagName("Observacion");

							   for (int l = 0; l < nameCM.getLength(); l++) {
								   Observacion observacion=new Observacion();

								   Element lineCM = (Element) nameCM.item(l);
								   NodeList nameCMI = lineCM.getElementsByTagName("IdObservacion");
								   NodeList nameCMCNT = lineCM.getElementsByTagName("Detalle");
								   if(lineCM!=null)
								   {
									   Element lineIAC = (Element) nameCMI.item(0);
									   Element lineCNTCOM= (Element) nameCMCNT.item(0);
									   if(nameCMI.item(0)!=null & nameCMCNT.item(0)!=null)
									   {
										   observacion.setDetalle(getCharacterDataFromElement(lineCNTCOM));
										   observacion.setIdObservacion(getCharacterDataFromElement(lineIAC));
										   itemPedido.getListaObservaciones().add(observacion);
									   }
								   }
							   }
						   }

					   }
					   else
					   {
						   NodeList name = element.getElementsByTagName(itemPedido.getPropertyName(j));
						   Element line = (Element) name.item(0);
						   itemPedido.setProperty(j,getCharacterDataFromElement(line));
					   }





		           }
		           listaArticulos.add(itemPedido);
		        }
			}
			catch(Exception e)
			{
				listaArticulos=null;
			}
		}		 
	}
	public long getTotalPedido() {
		long totalPedido=0;
		for (int i = 0; i < listaArticulos.size(); i++) {
			totalPedido+=listaArticulos.get(i).getTotal();
		}		
		return totalPedido;
	}
	
	public ArrayList<ItemPedido> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(ArrayList<ItemPedido> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	 public static String getCharacterDataFromElement(Element e) {
		    Node child = e.getFirstChild();
		    if (child instanceof CharacterData) {
		       CharacterData cd = (CharacterData) child;
		       return cd.getData();
		    }
		    return "?";
		  }
	 
	 public String getXmlArticulos()
		{
			String xml="";
			xml="<Pedido>\n";
			for (int i = 0; i < listaArticulos.size(); i++) {
				ItemPedido art=listaArticulos.get(i);
				art.setNObserArt(i);
				xml +="<Articulo>\n";
				for (int j = 0; j < art.getPropertyCount(); j++) {
					xml +="		<"+art.getPropertyName(j)+">"+art.getProperty(j)+"</"+art.getPropertyName(j)+">\n";				
				}
				xml +="</Articulo>\n";			
			}
			xml +="</Pedido>";
			return xml;
		}

	public String getNMesa() {
		return NMesa;
	}

	public void setNMesa(String NMesa) {
		this.NMesa = NMesa;
	}
}
