package com.principal.mundo;

import android.os.Parcel;
import android.os.Parcelable;

import com.principal.mundo.sysws.Observacion;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Comparator;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
/**
 * clase que contiene los datos de un articulo del negocio
 * @author user
 *
 */
public class Articulo implements Comparator<Articulo>, Parcelable
{
	
	/**
	 * identificador asignado al articulo
	 */
	public long idArticulo;
	/**
	 * codigo de acceso adignado al articulo
	 */
	public String  idCodigo;	
	/**
	 * nombre del articulo
	 */
	public String nombre;
	/**
	 * unidad de presentacion del articulo
	 */
	public String unidad;
	/**
	 * precio de venta 1 del articulo
	 */
	public long precio1;
	/**
	 * precio de venta 2 del articulo
	 */
	public long precio2;
	/**
	 * precio de venta 3 del articulo
	 */
	public long precio3;
	/**
	 * precio de venta 4 del articulo
	 */
	public long precio4;
	/**
	 * precio de venta 5 del articulo
	 */
	public long precio5;
	/**
	 *  precio de venta 6 del articulo
	 */
	public long precio6;
	/**
	 * impoconsuma asignado al articulo
	 */
	public long impoConsumo;
	/**
	 * iva al que corresponde el articulo
	 */
	public long iva;
	/**
	 * existencia del articulo en bodega
	 */
	public double stock;
	/**
	 *estado del articulo en el sistema "SI" o "NO"
	 */
	public long activo;
	/**
	 * nombre del la categoria
	 */
	public String categoria;
	
	public String urlImagen;
	
	public boolean isview=false;
	
	public boolean isviewMenu=false;
	
	
	private long cantidadVentas;
	private double cantidadVentasDouble;
	
	private long valorVentas;
	
	private long enCocina;
	
	/**
	 * lista de codigos del articulo
	 */
	public ArrayList<String> codigos;

	public ArrayList<Compuestos> listaCompuestos;

	public ArrayList <Observacion> listaObservaciones;

	
	/**
	 * marca de estado borrado del articulo
	 */
	public String borrado;
	public String codigo;

	public long costo;

	public double cantPedir;

	public long precioUnitario;

	private String EstadoAls;

	public  String gramaje;

	public String unidadDeMedida;

	public long TipoPrecio;


	
	/**
	 * metodo que retorna el codigo asignado al articulo
	 * @return idCodigo
	 */
	public String getIdCodigo() {
		return idCodigo;
	}

	/**
	 * metodo que asigna el nuevo valor al codigo del articulo
	 * @param idCodigo
	 */
	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}

	/**
	 * metodo que retorna la existencias del articulo en bodega
	 * @return stock
	 */
	public double getStock() {
		return stock;
	}

	/**
	 * metodo que asigna la nueva existencia del articulo en la bodega
	 * @param stock
	 */
	public void setStock(double stock) {
		this.stock = stock;
	}
	

	/**
	 * metodo que retorna el estado del producto en el sistema
	 * @return "SI" si el articulo esta activo o "NO" si el articulo fue eliminado
	 */
	public long getActivo() {
		return activo;
	}

	/**
	 * metodo que agrega el nuevo estado del articulo en el sistema
	 * @param activo
	 */
	public void setActivo(long activo) {
		this.activo = activo;
	}


	
	
	/**
	 * metodo que retorna la lista de codigos del articulo
	 * @return codigos
	 */
	public ArrayList<String> getCodigos() {
		return codigos;
	}

	public String getPrimerCodigo()
	{
		if(codigos!=null)
		{
			for (int i = 0; i < codigos.size(); i++) {
				return codigos.get(i).toString();
			}
		}
		return "Sin Codigo";

	}

	/**
	 * metodo que asigna la nueva lista de codigos al articulo
	 * @param codigos
	 */
	public void setCodigos(ArrayList <String> codigos) {
		this.codigos = codigos;
	}

	/**
	 * metodo que retorna el estado del articulo
	 * @return borrado
	 */
	public String getBorrado() {
		return borrado;
	}

	/**
	 * metodo que asigna el nuevo estado de borrado del articulo
	 * @param borrado
	 */
	public void setBorrado(String borrado) {
		this.borrado = borrado;
	}

	public double getCantidadVentasDouble() {
		return cantidadVentasDouble;
	}

	public void setCantidadVentasDouble(double cantidadVentasDouble) {
		this.cantidadVentasDouble = cantidadVentasDouble;
	}

	/**
	 * metodo que asigna valores a los atributos del articulo
	 * @param idArticulo
	 * @param idCategoria
	 * @param nombre
	 * @param unidad
	 * @param precio1
	 * @param precio2
	 * @param precio3
	 * @param precio4
	 * @param precio5
	 * @param precio6
	 * @param impoconsumo
	 * @param iva
	 */
	public Articulo(long idArticulo,String idCategoria, String nombre, String unidad, long precio1, long precio2, long precio3,long precio4, long precio5, long precio6,long impoconsumo, long iva, long costo) {
		this.idArticulo=idArticulo;
		this.idCodigo=idCategoria;
		this.nombre=nombre;
		this.unidad=unidad;
		this.precio1=precio1;
		this.precio2=precio2;
		this.precio3=precio3;
		this.precio4=precio1;
		this.precio5=precio2;
		this.precio6=precio3;
		this.impoConsumo=impoconsumo;
		this.costo=costo;
	}
	
	/**
	 * metodo que retorna el codigo del articulo
	 * @return idcodigo
	 */
	public String getIdCategoria() {
		return idCodigo;
	}

	/**
	 * metodo que retorna el precio 4 del articulo
	 * @return
	 */
	public long getPrecio4() {
		return precio4;
	}

	/**
	 * metodo que asigna el nuevo valor del precio 4 del articulo
	 * @param precio4
	 */
	public void setPrecio4(long precio4) {
		this.precio4 = precio4;
	}

	/**
	 * metodo que retorna el precio 5 del articulo
	 * @return precio 5
	 */
	public long getPrecio5() {
		return precio5;
	}

	/**
	 * metodo que asigna el precio 5 del articulo
	 * @param precio5
	 */
	public void setPrecio5(long precio5) {
		this.precio5 = precio5;
	}

	/**
	 * metodo que retorna el precio 6 del articulo
	 * @return precio6
	 */
	public long getPrecio6() {
		return precio6;
	}

	/**
	 * metodo que asigna el nuevo precio6 del articulo
	 * @param precio6
	 */
	public void setPrecio6(long precio6) {
		this.precio6 = precio6;
	}

	/**
	 * metodo que asigna el nuevo codigo del articulo
	 * @param idCategoria
	 */
	public void setIdCategoria(String idCategoria) {
		this.idCodigo = idCategoria;
	}

	/**
	 * metodo que retorna el impoconsumo del articulo
	 * @return impoconsumo
	 */
	public long getImpoConsumo() {
		return impoConsumo;
	}

	/**
	 * metodo que asigna el nuevo valor del impoconsumo del articulo
	 * @param impoConsumo
	 */
	public void setImpoConsumo(long impoConsumo) {
		this.impoConsumo = impoConsumo;
	}

	/**
	 * metodo que retorna el valor de iva del articulo
	 * @return
	 */
	public long getIva() {
		return iva;
	}

	/**
	 * metodo que asigna el nuevo valor del iva del articulo
	 * @param iva
	 */
	public void setIva(long iva) {
		this.iva = iva;
	}

	/**
	 * metodo que asigna valores predeterminados a las variables
	 */
	public Articulo() {
		this.idArticulo=0;
		this.nombre="";
		this.unidad="";
		this.precio1=0;
		this.precio2=0;
		this.precio3=0;
		this.costo=0;
		this.activo=0;
		this.cantPedir = 0;
		this.idCodigo = "Sin codigo";
		codigos=new ArrayList<String>();
		listaObservaciones=new ArrayList<Observacion>();
		EstadoAls="C";
	}

	/**
	 * metodo que retorna el id del articulo
	 * @return
	 */
	public long getIdArticulo() {
		return idArticulo;
	}

	/**
	 * metodo que asigna el nuevo valor del id del articulo
	 * @param idArticulo
	 */
	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}

	/**
	 * metodo que retorna el nombre del articulo
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo que asigna un nuevo nombre al articulo
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * metodo que retorna el valor de unidad del articulo 
	 * @return
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * metodo que asigna el nuevo valor de unidad del articulo
	 * @param unidad
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	/**
	 * metodo que retorna el precio1 asignado al articulo
	 * @return
	 */
	public long getPrecio1() {
		return precio1;
	}

	/**
	 * metodo que asigna el nuevo precio1 al articulo
	 * @param precio1
	 */
	public void setPrecio1(long precio1) {
		this.precio1 = precio1;
	}

	/**
	 * metodo que retorna el precio2 del articulo 
	 * @return precio
	 */
	public long getPrecio2() {
		return precio2;
	}

	/**
	 * metodo que asigna el nuevo precio 2 del articulo
	 * @param precio2
	 */
	public void setPrecio2(long precio2) {
		this.precio2 = precio2;
	}

	/**
	 * metodo que retorna el precio 3 del articulo
	 * @return
	 */
	public long getPrecio3() {
		return precio3;
	}

	/**
	 * metodo que asigna el nuevo precio 3 al articulo
	 * @param precio3
	 */
	public void setPrecio3(long precio3) {
		this.precio3 = precio3;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	//OBTIENE PROPIEDADES
	
	public int getPropertyCount()
	{
		return 20;
	}
	public long getPrecio(int precioDefecto)
	{
		switch(precioDefecto)
		{
			case 0:return costo;
		case 1: return precio1;
		case 2: return precio2;
		case 3: return precio3;
		case 4: return precio4;
		case 5: return precio5;
		case 6: return precio6;
		}
		return 0;
	}
	
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "IdArticulo";
		case 1: return "IdCategoria";
		case 2: return "NombreArticulo";
		case 3: return "Unidad";
		case 4: return "Iva";
		case 5: return "ImpoConsumo";
		case 6: return "Precio1";
		case 7: return "Precio2";
		case 8: return "Precio3";
		case 9: return "Precio4";
		case 10: return "Precio5";
		case 11: return "Precio6";
		case 12: return "Borrado";
		case 13: return "CodBarra";
		case 14: return "codigo";
		case 15: return "stock";
		case 16: return "Observacion";
			case 17: return "Costo";
			case 18: return "Gramaje";
			case 19: return "UnidadDeMedida";
		}
		return null;
	}
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return idArticulo;
		case 1: return idCodigo;
		case 2: return nombre;
		case 3: return unidad;
		case 4: return iva;
		case 5: return impoConsumo;
		case 6: return precio1;
		case 7: return precio2;
		case 8: return precio3;
		case 9: return precio4;
		case 10: return precio5;
		case 11: return precio6;
		case 12: return borrado;
		case 13: return codigos;
		case 14: return codigo;
		case 15: return stock;
		case 16: return listaObservaciones;
			case 17: return costo;
			case 18: return gramaje;
			case 19: return unidadDeMedida;
		}
		return null;
	}
	
	public void setProperty(int i, String text) {
		switch(i)
		{
		case 0: idArticulo=Long.parseLong(text);break;
		case 1: idCodigo=text;break;
		case 2: nombre=text;break;
		case 3: unidad=text;break;
		case 4: iva=Long.parseLong(text);break;
		case 5: impoConsumo=Long.parseLong(text);break;
		case 6: precio1=Long.parseLong(text);break;
		case 7: precio2=Long.parseLong(text);break;
		case 8: precio3=Long.parseLong(text);break;
		case 9: precio4=Long.parseLong(text);break;
		case 10:precio5=Long.parseLong(text);break;
		case 11:precio6=Long.parseLong(text);break;
		case 12:setBorradoXml(text);break;
		case 13: break;
		case 14: codigo=text;break;
		case 15: stock=Double.parseDouble(text);break;
		case 16: break;
		case 17:costo=Long.parseLong(text);break;
		case 18: gramaje=text;break;
		case 19: unidadDeMedida=text;break;
		}
	}
	private void setBorradoXml(String text)
	{
		borrado=text;
		if(borrado.equals("NO"))
		{
			activo = 1;
		}
		else
		{
			activo = 0;
		}
	}

	public double getUnidadDeMedidaVendidas()
    {
        double value=0;
        try
        {

            double unidad=Double.parseDouble(gramaje);
            value=unidad*cantidadVentas;

        }
        catch (Exception e)
        {
        }
       return value;
    }

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public int compare(Articulo e1, Articulo e2) {	
		return e1.getNombre().compareTo(e2.getNombre());
	}
	public static Comparator<Articulo> ArticuloNameComparator 
		    = new Comparator<Articulo>() {
		
		public int compare(Articulo ar1, Articulo ar2) {
		return ar1.getNombre().toUpperCase().compareTo(ar2.getNombre().toUpperCase());
		}
		};
	public boolean isIsview() {
		return isview;
	}

	public void setIsview(boolean isview) {
		this.isview = isview;
	}

	public boolean isIsviewMenu() {
		return isviewMenu;
	}

	public void setIsviewMenu(boolean isviewMenu) {
		this.isviewMenu = isviewMenu;
	}
	public int getStockint() {
		return (int)stock;
	}

	public long getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(long cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public long getValorVentas() {
		return valorVentas;
	}

	public void setValorVentas(long valorVentas) {
		this.valorVentas = valorVentas;
	}

	public String getCodigo() {
		if (codigo!=null)
		{
			return codigo;
		}
		else
		{
			return "";
		}
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ArrayList<Compuestos> getListaCompuestos() {
		return listaCompuestos;
	}

	public void setListaCompuestos(ArrayList<Compuestos> listaCompuestos) {
		this.listaCompuestos = listaCompuestos;
	}

	public ArrayList<Observacion> getListaObservaciones() {
		return listaObservaciones;
	}


	public void setListaObservaciones(ArrayList<Observacion> listaObservaciones) {
		this.listaObservaciones = listaObservaciones;
	}

	public ArrayList<Observacion> getListaObservacionesSelec()
	{
		ArrayList<Observacion> lista=new ArrayList<Observacion>();

		for(int i=0; i<listaObservaciones.size();i++)
		{
			if(listaObservaciones.get(i).isSelected())
			{
				Observacion o=new Observacion();
				o.setIdObservacion(listaObservaciones.get(i).getIdObservacion());
				o.setDetalle(listaObservaciones.get(i).getDetalle());
				lista.add(o);
			}
		}
		return lista;
	}
	public void setListaObservacionesDesSelect()
	{
		for(int i=0; i<listaObservaciones.size();i++)
		{
			listaObservaciones.get(i).setSelected(false);
		}

	}

	public long getCosto() {
		return costo;
	}

	public void setCosto(long costo) {
		this.costo = costo;
	}

	public double getCantPedir() {
		return cantPedir;
	}

	public void setCantPedir(double cantPedir) {
		this.cantPedir = cantPedir;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(idArticulo);
		dest.writeString(nombre);
		dest.writeLong(precioUnitario);
		dest.writeDouble(cantPedir);
		dest.writeString(codigo);
		dest.writeString(idCodigo);
		dest.writeLong(precio1);
		dest.writeLong(precio2);
		dest.writeLong(precio3);
		dest.writeLong(precio4);
		dest.writeLong(precio5);
		dest.writeLong(precio6);
		dest.writeLong(activo);
		dest.writeDouble(stock);
		dest.writeLong(iva);
		dest.writeLong(TipoPrecio);



	}

	public long getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Articulo(Parcel in)
	{
		//read in same order that you wrote in writeToParcel
		idArticulo = in.readLong();
		nombre    = in.readString();
		precioUnitario = in.readLong();
		cantPedir=in.readDouble();
		codigo=in.readString();
		idCodigo=in.readString();
		precio1= in.readLong();
		precio2= in.readLong();
		precio3= in.readLong();
		precio4= in.readLong();
		precio5= in.readLong();
		precio6= in.readLong();
		stock=in.readDouble();
		activo= in.readLong();
		iva= in.readLong();
		TipoPrecio= in.readLong();
//      reading in a list custom objects: in.readTypedList(someCustomObjectArrayList, someCustomObject.CREATOR )

	}

	//used when un-parceling our parcel (creating the object)
	public static final Parcelable.Creator<Articulo> CREATOR = new Parcelable.Creator<Articulo>(){


		public Articulo createFromParcel(Parcel in) {
			return new Articulo(in);
		}


		public Articulo[] newArray(int size) {
			return new Articulo[size];
		}
	};



	public int getPropertyCountAlsConsolidado() {
		// TODO Auto-generated method stub
		return 4;
	}
	public Object getPropertyAlsConsolidado(int i) {
		switch(i)
		{
			case 0: return idArticulo;
			case 1: return cantidadVentas;
			case 2: return nombre;
			case 3: return getEstadoAls();

		}
		return null;
	}

	public String getPropertyNameAlsConsolidado(int i) {
		switch(i)
		{
			case 0: return "IdArticulo";
			case 1: return "Cantidad";
			case 2: return "NombreArticulo";
			case 3: return "EstadoAls";
		}
		return null;
	}
	public void setPropertyAlsConsolidado(int i, String data) {

		switch(i)
		{
			case 0:  idArticulo =Long.parseLong(data);break;
			case 1:  cantidadVentas =(long)Double.parseDouble(data);break;
			case 2:  nombre=data;break;
			case 3:  EstadoAls=data;break;
		}
	}

	public String getEstadoAls() {
		if(EstadoAls==null)
		{
			return "C";
		}
		return EstadoAls;
	}

	public void setEstadoAls(String estadoAls) {
		EstadoAls = estadoAls;
	}

	public String getGramaje() {
		return gramaje;
	}

	public void setGramaje(String gramaje) {
		this.gramaje = gramaje;
	}

	public String getUnidadDeMedida() {
		if(unidadDeMedida==null)
		{
			return "U";
		}
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public long getTipoPrecio() {
		return TipoPrecio;
	}

	public void setTipoPrecio(long tipoPrecio) {
		TipoPrecio = tipoPrecio;
	}
}
