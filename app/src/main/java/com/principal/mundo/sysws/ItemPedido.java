package com.principal.mundo.sysws;

import com.principal.mundo.ArticulosPedido;

import java.util.ArrayList;


public class ItemPedido {
	
	private long idItem;
	private String NPedido;
	private String NCaja;
	private long IdArticulo;
	private double Cantidad;
	private long ValorUnitario;
	private long TipoIva;
	private long ImpoConsumo;
	private long Total;
	private long CostoUnit;
	private String EnCocina;
	private String Mesero;
	private long idCategoria;
	private String NombreArticulo;
	private String UnidadArticulo;
	private String ImpresoraCategoria;
	private String XmlListaObservaciones;

	private long NObserArt;
	private ArrayList<Observacion> listaObservaciones;

	private String Observacion;
	private long TipoPrecio;

	private String EstadoAls;
	private String ObservacionAls;
    private String UbicacionArticulo;


	private double stock;
	private String codigo;



	public ItemPedido() {
		NObserArt=0;
		listaObservaciones=new ArrayList<Observacion>();
		ObservacionAls="";
		EstadoAls="C";
		Observacion="";
		idItem=0;
		NPedido="0";
		NCaja="0";

		 ImpoConsumo=0;
		Total=0;
		 CostoUnit=0;
		EnCocina="NO";
		 Mesero="";
		idCategoria=0;
		NombreArticulo="";
		UnidadArticulo="";
		ImpresoraCategoria="";

		NObserArt=0;
		Observacion="";
		EstadoAls="C";
		 ObservacionAls="";
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ItemPedido(long idarticulo) {
		super();
		NPedido = "0";
		NCaja = "0";
		IdArticulo = idarticulo;
		Cantidad = 2;
		ValorUnitario = 100;
		TipoIva = 100;
		ImpoConsumo = 100;
		Total = 100;
		CostoUnit = 100;
		EnCocina = "NO";
		Mesero = "JAVIER";
		this.idCategoria = 0;
		NombreArticulo = "T1";
		UnidadArticulo = "UN";
		ImpresoraCategoria = "";
		XmlListaObservaciones="";
		listaObservaciones=new ArrayList<Observacion>();

	}

 public ItemPedido(ArticulosPedido articulosPedido)
 {
		NPedido = "0";
		NCaja = "0";
		IdArticulo = articulosPedido.getIdArticulo();
		Cantidad = articulosPedido.getCantidad();
		ValorUnitario = articulosPedido.getValorUnitario();
		TipoIva = articulosPedido.getIva();
		ImpoConsumo = articulosPedido.getIpoConsumo();
		Total = articulosPedido.getValor();
		CostoUnit = 0;
		EnCocina = "NO";
		Mesero = "JAVIER";
		this.idCategoria = 0;
		NombreArticulo = "T1";
		UnidadArticulo = "UN";
		ImpresoraCategoria = "";
	    listaObservaciones=new ArrayList<Observacion>();
	    Observacion=articulosPedido.getObservacion();
		TipoPrecio=articulosPedido.getTipoPrecio();
 }

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 20;
	}
	public Object getProperty(int i) {
		switch(i)
		{
			case 0: return idItem;
			case 1: return NPedido;
			case 2: return NCaja;
			case 3: return IdArticulo;
			case 4: return Cantidad;
			case 5: return ValorUnitario;
			case 6: return TipoIva;
			case 7: return ImpoConsumo;
			case 8: return Total;
			case 9: return CostoUnit;
			case 10: return EnCocina;
			case 11: return Mesero;
			case 12: return idCategoria;
			case 13: return NombreArticulo;
			case 14: return UnidadArticulo;
			case 15: return ImpresoraCategoria;
			case 16:return getXmlObservaciones();
			case 17: return NObserArt;
			case 18: return Observacion;
			case 19: return TipoPrecio;
		}
		return null;
	}

	public String getPropertyName(int i) {
		switch(i)
		{
			case 0: return "idItem";
			case 1: return "NPedido";
			case 2: return "NCaja";
			case 3: return "IdArticulo";
			case 4: return "Cantidad";
			case 5: return "ValorUnitario";
			case 6: return "TipoIva";
			case 7: return "ImpoConsumo";
			case 8: return "Total";
			case 9: return "CostoUnit";
			case 10: return "EnCocina";
			case 11: return "Mesero";
			case 12: return "idCategoria";
			case 13: return "NombreArticulo";
			case 14: return "UnidadArticulo";
			case 15: return "ImpresoraCategoria";
			case 16: return "ListaObservaciones";
			case 17: return "NObserArt";
			case 18: return "Observacion";
			case 19: return "TipoPrecio";
		}
		return null;
	}
	public void setProperty(int i, String data) {

		switch(i)
		{
			case 0: idItem =Long.parseLong(data);break;
			case 1: NPedido =data;break;
			case 2:  NCaja =data;break;
			case 3:  IdArticulo =Long.parseLong(data);break;
			case 4:  Cantidad =Double.parseDouble(data);break;
			case 5:  ValorUnitario =Long.parseLong(data);break;
			case 6:  TipoIva =Long.parseLong(data);break;
			case 7:  ImpoConsumo =Long.parseLong(data);break;
			case 8:  Total =Long.parseLong(data);break;
			case 9:  CostoUnit =Long.parseLong(data);break;
			case 10:  EnCocina =data;break;
			case 11: Mesero=data;break;
			case 12:  idCategoria=Long.parseLong(data);break;
			case 13:  NombreArticulo=data;break;
			case 14:  UnidadArticulo=data;break;
			case 15:  ImpresoraCategoria=data;break;
			case 16:  XmlListaObservaciones=data;break;
			case 17:  NObserArt=Long.parseLong(data);break;
			case 18:  Observacion=data;break;
			case 19:  TipoPrecio=Long.parseLong(data);break;
		}
	}

	public int getPropertyCountAls() {
		// TODO Auto-generated method stub
		return 24;
	}
	public Object getPropertyAls(int i) {
		switch(i)
		{
			case 0: return idItem;
			case 1: return NPedido;
			case 2: return NCaja;
			case 3: return IdArticulo;
			case 4: return Cantidad;
			case 5: return ValorUnitario;
			case 6: return TipoIva;
			case 7: return ImpoConsumo;
			case 8: return Total;
			case 9: return CostoUnit;
			case 10: return EnCocina;
			case 11: return Mesero;
			case 12: return idCategoria;
			case 13: return NombreArticulo;
			case 14: return UnidadArticulo;
			case 15: return ImpresoraCategoria;
			case 16:return getXmlObservaciones();
			case 17: return NObserArt;
			case 18: return Observacion;
			case 19: return EstadoAls;
			case 20: return ObservacionAls;
            case 21: return UbicacionArticulo;
			case 22: return stock;
			case 23: return codigo;
		}
		return null;
	}

	public String getPropertyNameAls(int i) {
		switch(i)
		{
			case 0: return "idItem";
			case 1: return "NPedido";
			case 2: return "NCaja";
			case 3: return "IdArticulo";
			case 4: return "Cantidad";
			case 5: return "ValorUnitario";
			case 6: return "TipoIva";
			case 7: return "ImpoConsumo";
			case 8: return "Total";
			case 9: return "CostoUnit";
			case 10: return "EnCocina";
			case 11: return "Mesero";
			case 12: return "idCategoria";
			case 13: return "NombreArticulo";
			case 14: return "UnidadArticulo";
			case 15: return "ImpresoraCategoria";
			case 16: return "ListaObservaciones";
			case 17: return "NObserArt";
			case 18: return "Observacion";
			case 19: return "EstadoAls";
			case 20: return "ObservacionAls";
            case 21: return "UbicacionArticulo";
			case 22: return "stock";
			case 23: return "codigo";
		}
		return null;
	}
	public void setPropertyAls(int i, String data) {

		switch(i)
		{
			case 0: idItem =Long.parseLong(data);break;
			case 1: NPedido =data;break;
			case 2:  NCaja =data;break;
			case 3:  IdArticulo =Long.parseLong(data);break;
			case 4:  Cantidad =Double.parseDouble(data);break;
			case 5:  ValorUnitario =Long.parseLong(data);break;
			case 6:  TipoIva =Long.parseLong(data);break;
			case 7:  ImpoConsumo =Long.parseLong(data);break;
			case 8:  Total =Long.parseLong(data);break;
			case 9:  CostoUnit =Long.parseLong(data);break;
			case 10:  EnCocina =data;break;
			case 11: Mesero=data;break;
			case 12:  idCategoria=Long.parseLong(data);break;
			case 13:  NombreArticulo=data;break;
			case 14:  UnidadArticulo=data;break;
			case 15:  ImpresoraCategoria=data;break;
			case 16:  XmlListaObservaciones=data;break;
			case 17:  NObserArt=Long.parseLong(data);break;
			case 18:  Observacion=data;break;
			case 19:  EstadoAls=data;break;
			case 20:  ObservacionAls=data;break;
            case 21:  UbicacionArticulo=data;break;
			case 22:  stock =Double.parseDouble(data);break;
			case 23: codigo=data;break;
		}
	}
	public String getXmlObservaciones()
	{
		String xml="";
		for (int i = 0; i < listaObservaciones.size(); i++) {
			Observacion obs=listaObservaciones.get(i);
			xml +="<Observacion>\n";
				xml +="		<IdObservacion>"+obs.getIdObservacion()+"</IdObservacion>\n";
				xml +="		<Detalle>"+obs.getDetalle()+"</Detalle>\n";

			xml +="</Observacion>\n";
		}

		return xml;
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}


	public String getNPedido() {
		return NPedido;
	}



	public void setNPedido(String nPedido) {
		NPedido = nPedido;
	}



	public String getNCaja() {
		return NCaja;
	}



	public void setNCaja(String nCaja) {
		NCaja = nCaja;
	}



	public long getIdArticulo() {
		return IdArticulo;
	}

	public void setIdArticulo(long idArticulo) {
		IdArticulo = idArticulo;
	}

	public double getCantidad() {
		return Cantidad;
	}
	public long getValorCantidad()
	{
		return (long)Cantidad;
	}

	public void setCantidad(double cantidad) {
		Cantidad = cantidad;
	}

	public long getValorUnitario() {
		return ValorUnitario;
	}

	public void setValorUnitario(long valorUnitario) {
		ValorUnitario = valorUnitario;
	}

	public long getTipoIva() {
		return TipoIva;
	}

	public void setTipoIva(long tipoIva) {
		TipoIva = tipoIva;
	}

	public long getImpoConsumo() {
		return ImpoConsumo;
	}

	public void setImpoConsumo(long impoConsumo) {
		ImpoConsumo = impoConsumo;
	}

	public long getTotal() {
		double t=ValorUnitario*Cantidad;
		Total = (long)t;
		return Total;
	}

	public void setTotal(long total) {
		Total = total;
	}

	public long getCostoUnit() {
		return CostoUnit;
	}

	public void setCostoUnit(long costoUnit) {
		CostoUnit = costoUnit;
	}

	public String getEnCocina() {
		return EnCocina;
	}

	public void setEnCocina(String enCocina) {
		EnCocina = enCocina;
	}

	public String getMesero() {
		return Mesero;
	}

	public void setMesero(String mesero) {
		Mesero = mesero;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreArticulo() {
		return NombreArticulo;
	}
	public String getObservacionesArticulo()
	{
		String res="";
		for (int i =0; i<listaObservaciones.size();i++)
		{
			res=res+"; "+listaObservaciones.get(i).getDetalle();
		}
		return res;
	}

	public void setNombreArticulo(String nombreArticulo) {
		NombreArticulo = nombreArticulo;
	}

	public String getUnidadArticulo() {
		return UnidadArticulo;
	}

	public void setUnidadArticulo(String unidadArticulo) {
		UnidadArticulo = unidadArticulo;
	}

	public String getImpresoraCategoria() {
		return ImpresoraCategoria;
	}

	public void setImpresoraCategoria(String impresoraCategoria) {
		ImpresoraCategoria = impresoraCategoria;
	}

	public ArrayList<Observacion> getListaObservaciones() {
		return listaObservaciones;
	}

	public void setListaObservaciones(ArrayList<Observacion> listaObservaciones) {
		this.listaObservaciones = listaObservaciones;
	}

	public String getXmlListaObservaciones() {
		return XmlListaObservaciones;
	}

	public long getNObserArt() {
		return NObserArt;
	}

	public void setNObserArt(long NObserArt) {
		this.NObserArt = NObserArt;
	}

	public void setXmlListaObservaciones(String xmlListaObservaciones) {
		XmlListaObservaciones = xmlListaObservaciones;
	}

	public String getObservacion() {
		if(Observacion!=null)
		{
			if(Observacion.equals("?"))
			{
				return "";
			}
		}
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public String getEstadoAls() {
		return EstadoAls;
	}

	public void setEstadoAls(String estadoAls) {
		EstadoAls = estadoAls;
	}

	public String getObservacionAls() {
		if(ObservacionAls!=null)
		{
			if(ObservacionAls.equals("--"))
			{
				return "";
			}
		}
		return ObservacionAls;
	}

	public void setObservacionAls(String observacionAls) {
		ObservacionAls = observacionAls;
	}
    public String getUbicacionArticulo() {
        return UbicacionArticulo;
    }



    public void setUbicacionArticulo(String ubicacionArticulo) {
        UbicacionArticulo = ubicacionArticulo;
    }

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
