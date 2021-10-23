package com.principal.mundo;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;


/**
 * @author user
 *
 */
public class SobrePedido implements KvmSerializable {
	
	/**
	 * identificador del pedido antes de enviarlo
	 */
	public long NPedido;
    /**
     * identificador del cliente del pedido
     */
    public long IdCliente;
    /**
     * identificador del usuario que realizo el pedido
     */
    public String CedulaVendedor;
    /**
     * referencia de la clase SobreArticulos
     */
    public SobreArticulos sobreArticulos;
    /**
     * observacion del pedido
     */
    public String Observaciones;
    /**
     * latitud de donde fue enviado el pedido
     */
    public String Latitud;
    /**
     * longitud de donde fue enviado el pedido
     */
    public String Longitud;

	/**
	 * 
	 */
	public SobrePedido() {
		// TODO Auto-generated constructor stub
	}

	public Object getProperty(int i) {
		// TODO Auto-generated method stub
		switch(i)
		{
		case 0: return NPedido;
		case 1: return IdCliente;
		case 2: return CedulaVendedor;
		case 3: return sobreArticulos;
		case 4: return Observaciones;
		case 5: return Latitud;
		case 6: return Longitud;
		}
		
		return null;
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		// TODO Auto-generated method stub
		 switch(i)
		 {
	    case 0:
	        propertyInfo.name = "NPedido";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 1:
	        propertyInfo.name = "IdCliente";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 2:
	        propertyInfo.name = "CedulaVendedor";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "Articulo";
	        propertyInfo.type = sobreArticulos.getClass();
	        break;
	    case 4:
	        propertyInfo.name = "Observaciones";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 5:
	        propertyInfo.name = "Latitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 6:
	        propertyInfo.name = "Longitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	        
	    default: break;
		 }
		
		
	}
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}	
}
