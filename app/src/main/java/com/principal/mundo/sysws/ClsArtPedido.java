package com.principal.mundo.sysws;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.principal.mundo.ArticulosPedido;

/**
 * Clase que representa al vector de los articulos del pedido
 * @author Javier
 *
 */
public class ClsArtPedido extends Vector<ArticulosPedido> implements KvmSerializable{

	   private static final long serialVersionUID = -1166006770093411055L;
	
	/**
	 * 
	 */
	public ClsArtPedido() {
		
	}

	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return this.get(arg0);
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		  return this.size();
	}

	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		 arg2.name = "articulo";
         arg2.type = ArticulosPedido.class;
		
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
