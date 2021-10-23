package com.principal.mundo;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * Clase que representa el vector de articulos del pedido que sera enviado al sistema PosStar
 * @author user
 *
 */
public class ClsArtsFactura extends Vector<ClsArtFactura> implements KvmSerializable{

	
	   private static final long serialVersionUID = -1166006770093411055L;
	   
	/**
	 * constructor vacio de la clase
	 */
	public ClsArtsFactura() {
		
	}

	public Object getProperty(int arg0) {
		 return this.get(arg0);
	}

	public int getPropertyCount() {
		  return this.size();
	}

	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		 arg2.name = "ClsArtFactura";
         arg2.type = ClsArtFactura.class;		
	}

	public void setProperty(int arg0, Object arg1) {
		  // this.add(arg1.toString());
		
	}

}
