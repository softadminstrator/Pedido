package com.principal.mundo;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
/**
 * Clase que representa el pedido que se enviara al servicio web del sistema PosStar
 * @author Javier
 *
 */
public class Pedido implements KvmSerializable
{
	 /**
	 * identificador del pedido en el sistema antes de enviarlo
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
     * referencia de la clase ClsArtsPedido en la que se encuentran representados los articulos del pedido
     */
    public ClsArtsPedido  Articulo;
     /**
     * observacion del pedido a enviar
     */
    public String Observaciones;
     /**
     * latitud de donde fue tomado el pedido
     */
    public String Latitud;
     /**
     * longitud de donde fue tomado el pedido
     */
    public String Longitud;

	public long IdClienteSucursal;
	
	
	/**
	 * metodo que se encarga de asignar valores a los atributos del sistema
	 * @param NPedido
	 * @param IdCliente
	 * @param CedulaVendedor
	 * @param Articulo
	 * @param Observaciones
	 * @param Latitud
	 * @param Longitud
	 */
	public Pedido(long NPedido, long IdCliente, String CedulaVendedor, ClsArtsPedido  Articulo, String Observaciones, String Latitud, String Longitud) 
	{
	this.NPedido=NPedido;
	this.IdCliente=IdCliente;
	this.CedulaVendedor=CedulaVendedor;
	this.Articulo=Articulo;
	this.Observaciones=Observaciones;
	this.Latitud=Latitud;
	this.Longitud=Longitud;
	}
	
	/**
	 * constructor vacio de la claes
	 */
	public Pedido()
	{
		
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 */
	public Object getProperty(int i) {
		// TODO Auto-generated method stub
		switch(i)
		{
		case 0: return NPedido;
		case 1: return IdCliente;
		case 2: return CedulaVendedor;
		case 3: return Articulo;
		case 4: return Observaciones;
		case 5: return Latitud;
		case 6: return Longitud;
		case 7: return IdClienteSucursal;
		}
		
		return null;
	}
	

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
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
	        propertyInfo.type = ClsArtsPedido.class;        
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
		 case 7:
			 propertyInfo.name = "IdClienteSucursal";
			 propertyInfo.type = PropertyInfo.LONG_CLASS;
			 break;

	        
	    default: break;
		 }
		
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
