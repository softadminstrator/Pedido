package com.principal.mundo;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
/**
 * Clase En dodne se representa la lista de articulos del pedido
 * @author Javier
 *
 */
public class SobreArticulos extends ArrayList<ArticulosPedido> implements KvmSerializable{

	
	 private static final long serialVersionUID = -1166006770093411055L;
	 
	 /**
	 * referencia de la clase ArticulosPedido que representa un articulo del pedio
	 */
	public ArticulosPedido articulosPedido;
	 
	 
	/**
	 * consturctor vacio
	 */
	public SobreArticulos() {
		// TODO Auto-generated constructor stub
	}

	public Object getProperty(int i) {
		return this.get(i);
	}

	public int getPropertyCount() {
		return this.size();
	}

	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo propertyInfo) {
		// TODO Auto-generated method stub
		propertyInfo.name = "ClsArtPedido";
		propertyInfo.type = articulosPedido.getClass();
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
