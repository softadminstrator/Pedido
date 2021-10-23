package com.principal.mundo;

import java.text.DecimalFormat;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * clase que se encarga de guardar el detalle de los articulos del pedido
 * @author Javier
 *
 */
public class ArticulosTraslado implements KvmSerializable
{
	/**
	 * identificador asignado al articulo
	 */
	public long idArticulo;
	/**
	 * identificador asignado al pedido
	 */
	public long idTraslado;
	/**
	 * cantidad asignada al articulo en el pedido
	 */
	public double cantidad;
	/**
	 * nombre del articulo
	 */
	public String nombre;
	/**
	 * valor unitiario del articulo
	 */
	public long valorUnitario;
	/**
	 * valor total del articulo en el pedido
	 * es igual al valorUnitario x cantidad
	 */
	public long valor;
	/**
	 * codigo usado para la busqueda del articulo
	 */
	public String codigo;	
	/**
	 * unidad del articulo
	 */
	public String unidad;	
	/**
	 * precio1 asignado al articulo
	 */
	public long precio1;
	/**
	 * precio2 asignado al articulo
	 */
	public long precio2;
	/**
	 * preci3 asignado al articulo
	 */
	public long precio3;
	/**
	 * precio1 asignado al articulo
	 */
	public long precio4;
	/**
	 * precio2 asignado al articulo
	 */
	public long precio5;
	/**
	 * preci3 asignado al articulo
	 */
	public long precio6;	
	/**
	 * Valor de impo consumo asignado al articulo
	 */
	public long ipoConsumo;
	/**
	 * porcentaje de iva al que pertenece el articulo
	 */
	public long iva;
	/**
	 * existencias del articulo en la bodega
	 */
	public double stock;
	/**
	 * estado en el que se encuentra el articulo en el sistem
	 */
	public long activo;
	/**
	 * orden de ingreso del articulo al pedido
	 */
	public long orden;
	
	public String categoria;
	
	/**
	 * metodo que asigna valores a los atributos de la clase
	 * @param idArticulo
	 * @param idPedido
	 * @param cantidad
	 * @param nombre
	 * @param valorUnitario
	 * @param valor
	 * @param codigo
	 * @param unidad
	 * @param precio1
	 * @param precio2
	 * @param precio3
	 * @param ipoConsumo
	 * @param iva
	 * @param stock
	 * @param activo
	 * @param orden
	 */
	public ArticulosTraslado(long idArticulo, long idPedido, int cantidad, String nombre, long valorUnitario, long valor, String codigo,
			String unidad, long precio1, long precio2, long precio3, long ipoConsumo, long iva, long stock, long activo, long orden)
	{
		this.idArticulo=idArticulo;
		this.idTraslado=idPedido;
		this.cantidad=cantidad;
		this.nombre=nombre;
		this.valorUnitario=valorUnitario;
		this.valor=valor;
		this.codigo=codigo;
		this.unidad=unidad;
		this.precio1=precio1;
		this.precio2=precio2;
		this.precio3=precio3;
		this.ipoConsumo=ipoConsumo;
		this.iva=iva;
		this.stock=stock;
		this.activo=activo;
		this.orden=orden;
	
	}
	
	
	/**
	 * metodo que asigna valores predeterminados a los atributos de la clase
	 */
	public ArticulosTraslado()
	{
		idArticulo=0;
		cantidad=0;
		nombre="";
		valor=0;
		valorUnitario=0;
		categoria="";
	}
	/**
	 * metodo que retorna el codigo de busqueda del articulo
	 * @return codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * metodo que retorna el identificador del pedido 
	 * @return idpedido
	 */
	public long getIdPedido() {
		return idTraslado;
	}

	/**
	 * metodo que asigna el nuevo identificador del pedido
	 * @param idPedido
	 */
	public void setIdPedido(long idPedido) {
		this.idTraslado = idPedido;
	}
	/**
	 * metodo que asigna el nuevo codigo de busqueda del articulo
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * metodo que retorna el identificador el articulo
	 * @return idarticulo
	 */
	public long getIdArticulo() {
		return idArticulo;
	}

	/**
	 * metodo que asigna el nueo valor al identificador del articulo
	 * @param idArticulo
	 */
	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}

	/**
	 * metodo que retorna la cantidad del articulo en el pedido
	 * @return cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * metodo que asigna la nueva cantidad del articulo en el pedido
	 * @param cantidad
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * metodo que retorna el nombre del articulo
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo que asigna el nuevo nombre del articulo
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * metodo que retorna el valor unitario del articulo
	 * @return valorUnitario
	 */
	public long getValorUnitario() {
		return valorUnitario;
	}

	/**
	 * metodo que asigna el nuevo valor unitario del articulo
	 * @param valorUnitario
	 */
	public void setValorUnitario(long valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	/**
	 * metodo que retorna el valor total del articulo en el pedido
	 * @return valor
	 */
	public long getValor() {
		return valor;
	}

	/**
	 * metodo que asigna el nuevo valor total del articulo en el pedido
	 * @param valor
	 */
	public void setValor(long valor) {
		this.valor = valor;
	}
	
	
	
	/**
	 * metodo que retorna el valor de unidad del articulo
	 * @return unidad
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
	 * metodo que retorna el precio1 del articulo
	 * @return precio1
	 */
	public long getPrecio1() {
		return precio1;
	}
	/**
	 * metodo que asigna el nuevo valor de precio1 del articulo 
	 * @param precio1
	 */
	public void setPrecio1(long precio1) {
		this.precio1 = precio1;
	}
	/**
	 * metodo que retorna el precio2 del articulo
	 * @return precio2
	 */
	public long getPrecio2() {
		return precio2;
	}
	/**
	 * metodo que asigna el nuevo valor del precio2 del articulo
	 * @param precio2
	 */
	public void setPrecio2(long precio2) {
		this.precio2 = precio2;
	}
	/**
	 * metodo que retorna el precio 3 del articulo
	 * @return precio3
	 */
	public long getPrecio3() {
		return precio3;
	}
	/**
	 * metodo que asigna el nuevo precio3 del articulo
	 * @param precio3
	 */
	public void setPrecio3(long precio3) {
		this.precio3 = precio3;
	}
	/**
	 * metodo que retorna el valor de impoconsumo del articulo
	 * @return impoConsumo
	 */
	public long getIpoConsumo() {
		return ipoConsumo;
	}
	/**
	 * metodo que asigna el nuevo valor de impoconsumo del articulo
	 * @param ipoConsumo
	 */
	public void setIpoConsumo(long ipoConsumo) {
		this.ipoConsumo = ipoConsumo;
	}
	/**
	 * metodo que retorna el porcentaje de iva del articulo
	 * @return iva
	 */
	public long getIva() {
		return iva;
	}
	/**
	 * metodo que asigna el nuevo valor del porcentaje de inva del articulo
	 * @param iva
	 */
	public void setIva(long iva) {
		this.iva = iva;
	}
	/**
	 * metodo que retorna la existencia del producto en bodega del articulo
	 * @return stock
	 */
	public double getStock() {
		return stock;
	}
	/**
	 * metodo que asigna la nueva cantidad de existencia en bodega del articulo
	 * @param stock
	 */
	public void setStock(double stock) {
		this.stock = stock;
	}
	/**
	 * metodo que retorna el estado del articulo en el sistema
	 * @return activo
	 */
	public long getActivo() {
		return activo;
	}
	/**
	 * asigna el nuevo estado del articulo en el sistema
	 * @param activo
	 */
	public void setActivo(long activo) {
		this.activo = activo;
	}
	/**
	 * retorna el orden en que fue ingresado el articulo al pedido
	 * @return orden
	 */
	public long getOrden() {
		return orden;
	}
	/**
	 * @param orden
	 */
	public void setOrden(long orden) {
		this.orden = orden;
	}
	
	
	public long getPrecio4() {
		return precio4;
	}


	public void setPrecio4(long precio4) {
		this.precio4 = precio4;
	}


	public long getPrecio5() {
		return precio5;
	}


	public void setPrecio5(long precio5) {
		this.precio5 = precio5;
	}


	public long getPrecio6() {
		return precio6;
	}


	public void setPrecio6(long precio6) {
		this.precio6 = precio6;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getPropertyCount()
	 */
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 17;
	}
	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 */
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return Long.toString(idArticulo);
		case 1: return codigo;
		case 2: return nombre;
		case 3: return unidad;
		case 4: return precio1;
		case 5: return precio2;
		case 6: return precio3;
		case 7: return ipoConsumo;
		case 8: return iva;
		case 9: return stock;
		case 10: return activo;
		case 11: return cantidad;
		case 12: return orden;
		case 13: return categoria;
		case 14: return precio4;
		case 15: return precio5;
		case 16: return precio6;
		}
		return null;
	}
	
	/**
	 * @param i
	 * @return
	 */
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "idArticulo";
		case 1: return "codigo";
		case 2: return "nombre";
		case 3: return "unidad";
		case 4: return "precio1";
		case 5: return "precio2";
		case 6: return "precio3";
		case 7: return "ipoConsumo";
		case 8: return "iva";
		case 9: return "stock";
		case 10: return "activo";
		case 11: return "cantidad";
		case 12: return "orden";
		case 13: return "categoria";
		case 14: return "precio4";
		case 15: return "precio5";
		case 16: return "precio6";
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getPropertyInfo(int, java.util.Hashtable, org.ksoap2.serialization.PropertyInfo)
	 */
	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
	    case 0:
	        propertyInfo.name = "idArticulo";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 1:
	        propertyInfo.name = "codigo";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 2:
	        propertyInfo.name = "nombre";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "unidad";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 4:
	        propertyInfo.name = "precio1";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 5:
	        propertyInfo.name = "precio2";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 6:
	        propertyInfo.name = "precio3";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 7:
	        propertyInfo.name = "ipoConsumo";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 8:
	        propertyInfo.name = "iva";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 9:
	        propertyInfo.name = "stock";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 10:
	        propertyInfo.name = "activo";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 11:
	        propertyInfo.name = "cantidad";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 12:
	        propertyInfo.name = "orden";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 13:
	        propertyInfo.name = "categoria";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 14:
	        propertyInfo.name = "precio4";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 15:
	        propertyInfo.name = "precio5";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 16:
	        propertyInfo.name = "precio6";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
		 }			 
	}
	public String getValorText() {
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		return decimalFormat.format(valor);
	}
	public String getValorUnitarioText() {
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		return decimalFormat.format(valorUnitario);
	}


	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
