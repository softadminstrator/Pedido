package com.principal.mundo.sysws;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import com.principal.mundo.Articulo;
import com.principal.mundo.*;



import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

/**
 * Clase en la que se describe el pedido que se enviara al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class Pedido implements KvmSerializable,  Parcelable {

	
    private String NCaja;
    private String NMesa;
    private String NPedido;
    private String estado;
    private String fecha;
    private String hora;
    private String idCliente;
    private String idVendedor;
    private String observaciones;
    private String totalPedido;
     
    private String xmlArticulos;
	private String xmlCategorias;
	private String xmlObservacionesPedido;


	private String SubTotal;
	private String DescuentoTotal;
	private String Documento;
    private String FormaPago;



	private String NombreCliente;
	private String NombreSeparador;
	private String NombreRevisor;
	private String NombreUsuario;
	private String NombreVendedor;
	private String idSeparador;
	private String idRevisor;
	private String ObservacionAls;
	private String MunicipioCliente;
	private String Items;
	private String itemsseparados;
	private String itemsagotados;
	private String itemspendientes;
	private String itemscrudos;
	private String nitCliente;
	private String precioDefecto;


	private String idClienteSucursal;


	ArrayList<ItemPedido> listaArticulos;
	ArrayList<Articulo> listaArticulosConsolidado;
	ArrayList<Articulo> listaCategoriasConsolidado;
	ArrayList<ObservacionPedido> listaObservacionesPedido;

	private String Latitud;
	private String Longitud;

	
	/**
	 * constructor vacio de la clase
	 */
	public Pedido() {
	
	    NCaja="0";
	    NMesa="10";
	    NPedido="0";
	    idCliente="0";
	    estado="CRUDO";
	    fecha="01/01/2012";
	    hora="8:00";
	    idVendedor="1";
	    observaciones="Ninguna";
	    totalPedido="100";	     
	    xmlArticulos="";
		SubTotal="0";
		DescuentoTotal="0";
		Documento="FAC";
        FormaPago="1";
		NombreCliente="";
		NombreSeparador="";
		NombreRevisor="";
		NombreUsuario="";
		NombreVendedor="";
		idSeparador="";
		idRevisor="";
		ObservacionAls="";
		MunicipioCliente="";
		Items="0";
		listaArticulosConsolidado=new ArrayList<Articulo>();
		listaCategoriasConsolidado=new ArrayList<Articulo>();
		listaObservacionesPedido=new ArrayList<ObservacionPedido>();

		idClienteSucursal="0";
	}

	protected Pedido(Parcel in) {
		NCaja = in.readString();
		NMesa = in.readString();
		NPedido = in.readString();
		estado = in.readString();
		fecha = in.readString();
		hora = in.readString();
		idCliente = in.readString();
		idVendedor = in.readString();
		observaciones = in.readString();
		totalPedido = in.readString();
		xmlArticulos = in.readString();
		SubTotal = in.readString();
		DescuentoTotal = in.readString();

		NombreCliente = in.readString();
		NombreSeparador = in.readString();
		NombreRevisor = in.readString();
		NombreUsuario = in.readString();
		NombreVendedor = in.readString();
		idSeparador = in.readString();
		idRevisor = in.readString();
		ObservacionAls = in.readString();
		MunicipioCliente = in.readString();
		Items = in.readString();
		itemsseparados = in.readString();
		itemsagotados = in.readString();
		itemspendientes = in.readString();
		itemscrudos = in.readString();
		nitCliente= in.readString();
        Documento = in.readString();
        FormaPago= in.readString();
		idClienteSucursal = in.readString();
	}

	public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
		public Pedido createFromParcel(Parcel in) {
			return new Pedido(in);
		}


		public Pedido[] newArray(int size) {
			return new Pedido[size];
		}
	};

	/**
	 * metodo utilizado para la serializacion del pedido al momento de enviarlo al servicio web
	 */
	
	
	
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return NCaja;	
		case 1: return NMesa;		
		case 2: return NPedido;	
		case 3: return estado;
		case 4: return fecha;
		case 5: return hora;
		case 6: return idCliente;
		case 7: return idVendedor;
	    case 8: return observaciones;
		case 9: return totalPedido;
		case 10: return "";
		case 11: return SubTotal;
		case 12: return DescuentoTotal;
		case 13: return Documento;
		case 14: return FormaPago;
		case 15: return idClienteSucursal;
		case 16: return Latitud;
		case 17: return Longitud;
		}
		
		return null;
	}

	
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "NCaja";
		case 1: return "NMesa";
		case 2: return "NPedido";
		case 3: return "estado";
		case 4: return "fecha";
		case 5: return "hora";
		case 6: return "idCliente";
		case 7: return "idVendedor";
		case 8: return "observaciones";
		case 9: return "totalPedido";
		case 10: return "xmlArticulos";
		case 11: return "SubTotal";
		case 12: return "DescuentoTotal";
		case 13: return "Documento";
		case 14: return "FormaPago";
		case 15: return "idClienteSucursal";
		case 16: return "Latitud";
		case 17: return "Longitud";
		}
		return null;
	}

	public void setProperty(int i, String data) {
		
		switch(i)
		{
		case 0: NCaja =data;break;
		case 1: NMesa =data;break;
		case 2:  NPedido =data;break;
		case 3:  estado =data;break;
		case 4:  fecha =data;break;
		case 5:  hora =data;break;
		case 6:  idCliente =data;break;
		case 7:  idVendedor =data;break;
		case 8:  observaciones =data;break;
		case 9:  totalPedido =data;break;
		case 10:  xmlArticulos =data;break;
		case 11:  SubTotal =data;break;
		case 12:  DescuentoTotal =data;break;
		case 13:  Documento =data;break;
		case 14:  FormaPago =data;break;
		case 15:  idClienteSucursal =data;break;
		case 16:  Latitud =data;break;
		case 17:  Longitud =data;break;
	}
}

	
	
	
	
	public Pedido(String nCaja, String nMesa, String nPedido, String estado,
			String fecha, String hora, String idCliente, String idVendedor,
			String observaciones, String totalPedido, String xmlArticulos
		) {
		super();
		NCaja = nCaja;
		NMesa = nMesa;
		NPedido = nPedido;
		this.estado = estado;
		this.fecha = fecha;
		this.hora = hora;
		this.idCliente = idCliente;
		this.idVendedor = idVendedor;
		this.observaciones = observaciones;
		this.totalPedido = totalPedido;
		this.xmlArticulos = xmlArticulos;
		
	}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 18;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
		case 0:
	        propertyInfo.name = "NCaja";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	  
	    case 1:
	        propertyInfo.name = "NMesa";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	   
	    case 2:
	        propertyInfo.name = "NPedido";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "estado";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 4:
	        propertyInfo.name = "fecha";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	 
	    case 5:
	        propertyInfo.name = "hora";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	
	    case 6:
	        propertyInfo.name = "idCliente";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	
	    case 7:
	        propertyInfo.name = "idVendedor";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	
	    case 8:
	        propertyInfo.name = "observaciones";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	
	    case 9:
	        propertyInfo.name = "totalPedido";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	
	    case 10:
	        propertyInfo.name = "xmlArticulos";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
		 case 11:
			 propertyInfo.name = "SubTotal";
			 propertyInfo.type = PropertyInfo.STRING_CLASS;
			 break;
		 case 12:
			 propertyInfo.name = "DescuentoTotal";
			 propertyInfo.type = PropertyInfo.STRING_CLASS;
			 break;
		 case 13:
				 propertyInfo.name = "idClienteSucursal";
				 propertyInfo.type = PropertyInfo.STRING_CLASS;
				 break;
			 default: break;
		 }
		
		
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * metodo que retorna el identificador del cliente
	 * @return idcliente
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * metodo que asigna el nuevo valor del identificador del cliente
	 * @param idCliente
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * metodo que retorna el nuevo valor del xml de articulos pertenecientes al pedido
	 * @return xmlArticulos
	 */
	public String getXmlArticulos() {
		return xmlArticulos;
	}
	

	
	

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getIdVendedor() {
		return idVendedor;
	}
	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setTotalPedido(String totalPedido) {
		this.totalPedido = totalPedido;
	}

	public void setXmlArticulos(String xmlArticulos) {
		this.xmlArticulos = xmlArticulos;
	}
	public void setListaArticulos(ArrayList<ArticulosPedido> listaArticulos )
	{
		String xml="";
		xml="<Pedido>\n";
		for (int i = 0; i < listaArticulos.size(); i++) {
			ItemPedido art= new ItemPedido(listaArticulos.get(i));
			xml +="<Articulo>\n";
			for (int j = 0; j < art.getPropertyCount(); j++) {
				xml +="		<"+art.getPropertyName(j)+">"+art.getProperty(j)+"</"+art.getPropertyName(j)+">\n";				
			}
			xml +="</Articulo>\n";			
		}
		xml +="</Pedido>";
		xmlArticulos=xml;
	}
	
	public String getNCaja() {
		return NCaja;
	}
	public void setNCaja(String nCaja) {
		NCaja = nCaja;
	}
	public String getNMesa() {
		return NMesa;
	}
	public void setNMesa(String nMesa) {
		NMesa = nMesa;
	}
	public String getNPedido() {
		return NPedido;
	}
	public void setNPedido(String nPedido) {
		NPedido = nPedido;
	}

public String getResponsable()
{
	if(getNombreVendedor().equals("--"))
	{
		return getNombreUsuario();
	}
	else
	{
		return getNombreVendedor();
	}
}
	public String getTotalPedido() {
		return totalPedido;
}
	public long getTotalPedidoModificado() {
		long tot=0;
		for (int i = 0; i <listaArticulos.size() ; i++) {
			tot=tot+((long)listaArticulos.get(i).getCantidad()*listaArticulos.get(i).getValorUnitario());
		}
		return tot;
	}

	public String getSubTotal() {
		return SubTotal;
	}

	public void setSubTotal(String subTotal) {
		SubTotal = subTotal;
	}

	public String getDescuentoTotal() {
		return DescuentoTotal;
	}

	public void setDescuentoTotal(String descuentoTotal) {
		DescuentoTotal = descuentoTotal;
	}

	public String getDocumento() {
		return Documento;
	}

	public void setDocumento(String documento) {
		Documento = documento;
	}

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String formaPago) {
        FormaPago = formaPago;
    }

    public String getNombreCliente() {
		return NombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		NombreCliente = nombreCliente;
	}

	public String getNombreSeparador() {
		return NombreSeparador;
	}

	public void setNombreSeparador(String nombreSeparador) {
		NombreSeparador = nombreSeparador;
	}

	public String getNombreRevisor() {
		return NombreRevisor;
	}

	public void setNombreRevisor(String nombreRevisor) {
		NombreRevisor = nombreRevisor;
	}

	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}

	public String getNombreVendedor() {
		return NombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		NombreVendedor = nombreVendedor;
	}

	public String getIdSeparador() {
		return idSeparador;
	}

	public void setIdSeparador(String idSeparador) {
		this.idSeparador = idSeparador;
	}

	public String getIdRevisor() {
		return idRevisor;
	}

	public void setIdRevisor(String idRevisor) {
		this.idRevisor = idRevisor;
	}

	public String getObservacionAls() {
		return ObservacionAls;
	}

	public void setObservacionAls(String observacionAls) {
		ObservacionAls = observacionAls;
	}

	public String getMunicipioCliente() {
		return MunicipioCliente;
	}

	public void setMunicipioCliente(String municipioCliente) {
		MunicipioCliente = municipioCliente;
	}

	public String getItems() {
		return Items;
	}

	public void setItems(String items) {
		Items = items;
	}

	public String getItemsseparados() {
		return getValorItem("S",itemsseparados);
	}

	public void setItemsseparados(String itemsseparados) {
		this.itemsseparados = itemsseparados;
	}

	public String getItemsagotados() {

		return getValorItem("A",itemsagotados);
	}

	public String getNitCliente() {
		return nitCliente;
	}

	public void setNitCliente(String nitCliente) {
		this.nitCliente = nitCliente;
	}

	public void setItemsagotados(String itemsagotados) {

		this.itemsagotados = itemsagotados;
	}

	public String getItemspendientes() {
		return getValorItem("P",itemspendientes);
	}

	public void setItemspendientes(String itemspendientes) {
		this.itemspendientes = itemspendientes;
	}
	private String getValorItem(String prefijo, String valor)
	{
		try
		{
			long tem=Long.parseLong(valor);
			if(tem>0)
			{
				return prefijo+":"+tem;
			}
		}
		catch (Exception e)	{}
		return "";
	}

	public String getItemscrudos() {
	if(itemscrudos.equals(Items))
	{
		return "";
	}
		return getValorItem("C",itemscrudos);
	}

	public void setItemscrudos(String itemscrudos) {
		this.itemscrudos = itemscrudos;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(NCaja);
		dest.writeString(NMesa);
		dest.writeString(NPedido);
		dest.writeString(estado);
		dest.writeString(fecha);
		dest.writeString(hora);
		dest.writeString(idCliente);
		dest.writeString(idVendedor);
		dest.writeString(observaciones);
		dest.writeString(totalPedido);

		dest.writeString(xmlArticulos);

		dest.writeString(SubTotal);
		dest.writeString(DescuentoTotal);

		dest.writeString(NombreCliente);
		dest.writeString(NombreSeparador);
		dest.writeString(NombreRevisor);
		dest.writeString(NombreUsuario);
		dest.writeString(NombreVendedor);
		dest.writeString(idSeparador);
		dest.writeString(idRevisor);
		dest.writeString(ObservacionAls);
		dest.writeString(MunicipioCliente);
		dest.writeString(Items);
		dest.writeString(itemsseparados);
		dest.writeString(itemsagotados);
		dest.writeString(itemspendientes);
		dest.writeString(itemscrudos);
		dest.writeString(nitCliente);
        dest.writeString(FormaPago);
		dest.writeString(Documento);
		dest.writeString(idClienteSucursal);
		dest.writeTypedList(listaArticulosConsolidado);


	}

	public  void  getCargarArticulosAls( )
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
					for (int j = 0; j < itemPedido.getPropertyCountAls(); j++) {

						if(itemPedido.getPropertyNameAls(j).equals("ListaObservaciones"))
						{
							NodeList name = element.getElementsByTagName(itemPedido.getPropertyNameAls(j));
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
							NodeList name = element.getElementsByTagName(itemPedido.getPropertyNameAls(j));
							Element line = (Element) name.item(0);
							itemPedido.setPropertyAls(j,getCharacterDataFromElement(line));
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
	public void setNuevaListaObservaciones(ArrayList<ObservacionPedido> newLista)
	{
		if(newLista!=null) {
			listaObservacionesPedido=new ArrayList<ObservacionPedido>();
			for (int i = 0; i < newLista.size(); i++) {
				long cant=(long)Double.parseDouble(newLista.get(i).getCantidad());
				if(cant>0)
				{
					listaObservacionesPedido.add(newLista.get(i));
				}
			}
		}
	}
	public void setListaObservacionesPedido()
	{
		if(!xmlObservacionesPedido.equals("") & !xmlObservacionesPedido.equals("anyType{}"))
		{
			listaObservacionesPedido=new ArrayList<ObservacionPedido>();
			try
			{
				xmlObservacionesPedido="<Lista>"+xmlObservacionesPedido+"</Lista>";
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlObservacionesPedido));
				Document doc = db.parse(is);
					 NodeList nameCM = doc.getElementsByTagName("Observacion");
					for (int l = 0; l < nameCM.getLength(); l++) {
										ObservacionPedido observacion=new ObservacionPedido();

										Element lineCM = (Element) nameCM.item(l);
										NodeList nameCMI = lineCM.getElementsByTagName("IdObservacionPedido");
										NodeList nameCMCNT = lineCM.getElementsByTagName("Cantidad");
										NodeList nameNombre= lineCM.getElementsByTagName("NombreObservacion");

										if(lineCM!=null)
										{
											Element lineIAC = (Element) nameCMI.item(0);
											Element lineCNTCOM= (Element) nameCMCNT.item(0);
											Element lineNombre= (Element) nameNombre.item(0);
											if(nameCMI.item(0)!=null & nameCMCNT.item(0)!=null & nameNombre.item(0)!=null)
											{
												observacion.setIdObservacionPedido(getCharacterDataFromElement(lineIAC));
												observacion.setCantidad(getCharacterDataFromElement(lineCNTCOM));
												observacion.setNombreObservacion(getCharacterDataFromElement(lineNombre));
												listaObservacionesPedido.add(observacion);
											}
										}
									}

			}
			catch(Exception e)
			{
				listaObservacionesPedido=null;
			}
		}

	}
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

	public void setArticulosAlsConsolidado( ) {

		if (!xmlArticulos.equals("")) {
			listaArticulosConsolidado=new ArrayList<Articulo>();
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlArticulos));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Articulo");
				// iterate the employees
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);

					Articulo articulo = new Articulo();
					for (int j = 0; j < articulo.getPropertyCountAlsConsolidado()-1; j++) {


						NodeList name = element.getElementsByTagName(articulo.getPropertyNameAlsConsolidado(j));
						Element line = (Element) name.item(0);
						articulo.setPropertyAlsConsolidado(j, getCharacterDataFromElement(line));
					}
					listaArticulosConsolidado.add(articulo);
				}


			} catch (Exception e) {
				listaArticulosConsolidado = null;
			}
		}
		else
		{
			listaArticulosConsolidado = null;
		}



	}

	public void setArticulosAlsConsolidadoModificado( ) {

		if (!xmlArticulos.equals("")) {
			listaArticulosConsolidado=new ArrayList<Articulo>();
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlArticulos));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Articulo");
				// iterate the employees
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);

					Articulo articulo = new Articulo();
					for (int j = 0; j < articulo.getPropertyCountAlsConsolidado(); j++) {


						NodeList name = element.getElementsByTagName(articulo.getPropertyNameAlsConsolidado(j));
						Element line = (Element) name.item(0);
						articulo.setPropertyAlsConsolidado(j, getCharacterDataFromElement(line));
					}
					listaArticulosConsolidado.add(articulo);
				}


			} catch (Exception e) {
				listaArticulosConsolidado = null;
			}
		}
		else
		{
			listaArticulosConsolidado = null;
		}



	}


	public void setCategoriasAlsConsolidado( ) {

		if (!xmlCategorias.equals("")) {
			listaCategoriasConsolidado=new ArrayList<Articulo>();
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlCategorias));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Categoria");
				// iterate the employees
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);

					Articulo articulo = new Articulo();
					for (int j = 0; j < articulo.getPropertyCountAlsConsolidado()-1; j++) {


						NodeList name = element.getElementsByTagName(articulo.getPropertyNameAlsConsolidado(j));
						Element line = (Element) name.item(0);
						articulo.setPropertyAlsConsolidado(j, getCharacterDataFromElement(line));
					}
					listaCategoriasConsolidado.add(articulo);
				}


			} catch (Exception e) {
				listaCategoriasConsolidado = null;
			}
		}
		else
		{
			listaCategoriasConsolidado = null;
		}



	}


	public ArrayList<ItemPedido> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos2(ArrayList<ItemPedido> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public ArrayList<Articulo> getListaArticulosConsolidado() {
		return listaArticulosConsolidado;
	}

	public ArrayList<Articulo> getListaCategoriasConsolidado() {
		return listaCategoriasConsolidado;
	}

	public void setListaCategoriasConsolidado(ArrayList<Articulo> listaCategoriasConsolidado) {
		this.listaCategoriasConsolidado = listaCategoriasConsolidado;
	}

	public void setListaArticulosConsolidado(ArrayList<Articulo> listaArticulosConsolidado) {
		this.listaArticulosConsolidado = listaArticulosConsolidado;
	}

	public String getXmlCategorias() {
		return xmlCategorias;
	}

	public void setXmlCategorias(String xmlCategorias) {
		this.xmlCategorias = xmlCategorias;
	}

	public String getXmlArticulosAlsConsolidado()
	{

		String xmlArticulos="<Consolidado>\n";
		for (int i = 0; i < listaArticulosConsolidado.size(); i++) {
			Articulo itemPedido=listaArticulosConsolidado.get(i);
			xmlArticulos +="<Articulo>\n";
			for (int j = 0; j < itemPedido.getPropertyCountAlsConsolidado(); j++) {
				xmlArticulos +="		<"+itemPedido.getPropertyNameAlsConsolidado(j)+">"+itemPedido.getPropertyAlsConsolidado(j)+"</"+itemPedido.getPropertyNameAlsConsolidado(j)+">\n";
			}
			xmlArticulos +="</Articulo>\n";
		}
		xmlArticulos +="</Consolidado>";

		return xmlArticulos;
	}
	public String getXmlCategoriaAlsConsolidado()
	{

		String xmlArticulos="<Consolidado>\n";
		for (int i = 0; i < listaCategoriasConsolidado.size(); i++) {
			Articulo itemPedido=listaCategoriasConsolidado.get(i);
			xmlArticulos +="<Articulo>\n";
			for (int j = 0; j < itemPedido.getPropertyCountAlsConsolidado(); j++) {
				xmlArticulos +="		<"+itemPedido.getPropertyNameAlsConsolidado(j)+">"+itemPedido.getPropertyAlsConsolidado(j)+"</"+itemPedido.getPropertyNameAlsConsolidado(j)+">\n";
			}
			xmlArticulos +="</Articulo>\n";
		}
		xmlArticulos +="</Consolidado>";

		return xmlArticulos;
	}
	public String getXmlObservaciones()
	{
		String xml="";
		for (int i = 0; i < listaObservacionesPedido.size(); i++) {
			ObservacionPedido obs=listaObservacionesPedido.get(i);
			xml +="<Observacion>\n";
			xml +="		<IdObservacionPedido>"+obs.getIdObservacionPedido()+"</IdObservacionPedido>\n";
			xml +="		<NombreObservacion>"+obs.getNombreObservacion()+"</NombreObservacion>\n";
			xml +="		<Cantidad>"+obs.getCantidad()+"</Cantidad>\n";

			xml +="</Observacion>\n";
		}

		return xml;
	}
	public void setXmlObservacionesPedido(ArrayList<ObservacionPedido> listaObservacionesPedidoin) {

		xmlObservacionesPedido="";
		this.listaObservacionesPedido=listaObservacionesPedidoin;
		for (int i = 0; i < listaObservacionesPedido.size(); i++) {
			ObservacionPedido obs=listaObservacionesPedido.get(i);
			xmlObservacionesPedido +="<Observacion>\n";
			xmlObservacionesPedido +="		<IdObservacionPedido>"+obs.getIdObservacionPedido()+"</IdObservacionPedido>\n";
			xmlObservacionesPedido +="		<NombreObservacion>"+obs.getNombreObservacion()+"</NombreObservacion>\n";
			xmlObservacionesPedido +="		<Cantidad>"+obs.getCantidad()+"</Cantidad>\n";

			xmlObservacionesPedido +="</Observacion>\n";
		}


	}

	public String getXmlObservacionesPedido() {
		return xmlObservacionesPedido;
	}

	public void setXmlObservacionesPedido(String xmlObservacionesPedido) {
		this.xmlObservacionesPedido = xmlObservacionesPedido;
	}

	public ArrayList<ObservacionPedido> getListaObservacionesPedido() {
		return listaObservacionesPedido;
	}

	public void setListaObservacionesPedido(ArrayList<ObservacionPedido> listaObservacionesPedido) {
		this.listaObservacionesPedido = listaObservacionesPedido;
	}
	//---------------------------------------------------------
	// PROPIEDADES PARA ALISTAMIENTO DE PEDIDOS
	//---------------------------------------------------------
	public Object getPropertyAls(int i) {
		switch(i)
		{
			case 0: return NCaja;
			case 1: return NMesa;
			case 2: return NPedido;
			case 3: return estado;
			case 4: return fecha;
			case 5: return hora;
			case 6: return idCliente;
			case 7: return idVendedor;
			case 8: return observaciones;
			case 9: return getTotalPedidoModificado();
			case 10: return xmlArticulos;
			case 11: return DescuentoTotal;
			case 12: return SubTotal;
			case 13: return Documento;
			case 14: return ObservacionAls;
			case 15: return getXmlObservaciones();
			case 16: return idClienteSucursal;



//		case 11: return CedulaVendedor;
		}

		return null;
	}

	public String getPropertyNameAls(int i) {
		switch(i)
		{
			case 0: return "NCaja";
			case 1: return "NMesa";
			case 2: return "NPedido";
			case 3: return "estado";
			case 4: return "fecha";
			case 5: return "hora";
			case 6: return "idCliente";
			case 7: return "idVendedor";
			case 8: return "observaciones";
			case 9: return "totalPedido";
			case 10: return "xmlArticulos";
			case 11: return "DescuentoTotal";
			case 12: return "SubTotal";
			case 13: return "Documento";
			case 14: return "ObservacionAls";
			case 15: return "ListaObservaciones";
			case 16: return "idClienteSucursal";
		}
		return null;
	}

	public void setPropertyAls(int i, String data) {

		switch(i)
		{
			case 0: NCaja =data;break;
			case 1: NMesa =data;break;
			case 2:  NPedido =data;break;
			case 3:  estado =data;break;
			case 4:  fecha =data;break;
			case 5:  hora =data;break;
			case 6:  idCliente =data;break;
			case 7:  idVendedor =data;break;
			case 8:  observaciones =data;break;
			case 9:  totalPedido =data;break;
			case 10: xmlArticulos =data;break;
			case 11: DescuentoTotal=data;break;
			case 12: SubTotal=data;break;
			case 13: Documento=data;break;
			case 14:  ObservacionAls=data;break;
			case 15:  xmlObservacionesPedido=data;break;
			case 16:  idClienteSucursal =data;break;

//		case 11:  CedulaVendedor =data;break;
		}
	}
	public int getPropertyCountAls() {
		// TODO Auto-generated method stub
		return 17;
	}

	public String getXmlArticulosAls( )
	{

		String xmlArticulos="<Pedido>\n";
		for (int i = 0; i < listaArticulos.size(); i++) {
			ItemPedido itemPedido=listaArticulos.get(i);
			xmlArticulos +="<Articulo>\n";
			for (int j = 0; j < itemPedido.getPropertyCountAls(); j++) {
				xmlArticulos +="		<"+itemPedido.getPropertyNameAls(j)+">"+itemPedido.getPropertyAls(j)+"</"+itemPedido.getPropertyNameAls(j)+">\n";
			}
			xmlArticulos +="</Articulo>\n";
		}
		xmlArticulos +="</Pedido>";

		return xmlArticulos;
	}

	public String getPrecioDefecto() {
		return precioDefecto;
	}

	public void setPrecioDefecto(String precioDefecto) {
		this.precioDefecto = precioDefecto;
	}

	public String getIdClienteSucursal() {
		return idClienteSucursal;
	}

	public void setIdClienteSucursal(String idClienteSucursal) {
		this.idClienteSucursal = idClienteSucursal;
	}

	public String getLatitud() {
		return Latitud;
	}

	public void setLatitud(String latitud) {
		Latitud = latitud;
	}

	public String getLongitud() {
		return Longitud;
	}

	public void setLongitud(String longitud) {
		Longitud = longitud;
	}
}
