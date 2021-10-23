package com.principal.mundo.sysws;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * @author user
 *
 */
public class Articulo implements KvmSerializable
{
	/**
	 * identificador asignado al articulo
	 */
	public long idArticulo;
	/**
	 * codigo de acceso adignado al articulo
	 */
	public String  codigo;	
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
	 * impoconsuma asignado al articulo
	 */
	public long ipoConsumo;
	/**
	 * iva al que corresponde el articulo
	 */
	public long iva;
	/**
	 * existencia del articulo en bodega
	 */
	public long stock;
	/**
	 *estado del articulo en el sistema "SI" o "NO"
	 */
	public long activo;
	/**
	 * cantidad de articulo en el pedido
	 */
	private long cantidad;
	/**
	 * orden de ingreso al pedido del articulo
	 */
	private long orden;
	
	/**
	 * metodo constructor vacio de la clase
	 */
	public Articulo() {
		this.idArticulo=0;
		this.nombre="";
		this.unidad="";
		this.precio1=0;
		this.precio2=0;
		this.precio3=0;
		this.activo=0;		
	}
	
	/**
	 * metodo que asigna valores a los atributos la clase
	 * @param idArticulo
	 * @param codigo
	 * @param nombre
	 * @param unidad
	 * @param precio1
	 * @param precio2
	 * @param precio3
	 * @param ipoConsumo
	 * @param iva
	 */
	public Articulo(long idArticulo,String codigo, String nombre, String unidad, long precio1, long precio2, long precio3, long ipoConsumo, long iva) {
		this.idArticulo=idArticulo;
		this.codigo=codigo;
		this.nombre=nombre;
		this.unidad=unidad;
		this.precio1=precio1;
		this.precio2=precio2;
		this.precio3=precio3;
		this.ipoConsumo=ipoConsumo;
	}


	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 13;
	}
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return idArticulo;
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
		}
		return null;
	}
	
	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
	    case 0:
	        propertyInfo.name = "idArticulo";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
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
		 }			 
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
