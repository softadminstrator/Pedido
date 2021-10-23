package com.principal.mundo;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
/**
 * Clase que representa el pedido que se enviara al servicio web del sistema PosStar
 * @author Javier
 *
 */
public class Factura implements KvmSerializable
{
	 /**
		 * identificador del pedido en el sistema antes de enviarlo
		 */
		public long NCaja;
	 /**
	 * identificador del pedido en el sistema antes de enviarlo
	 */
	public long NFactura;
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
    public ClsArtsFactura  Articulo;
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
    
	
    public long VentaCredito;


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
	
	
	/**
	 * constructor vacio de la claes
	 */
	public Factura()
	{
		
	}



	public Factura(long nCaja, long nFactura, long idCliente,
			String cedulaVendedor, ClsArtsFactura articulo,
			String observaciones, String latitud, String longitud,
			long ventaCredito) {
		super();
		NCaja = nCaja;
		NFactura = nFactura;
		IdCliente = idCliente;
		CedulaVendedor = cedulaVendedor;
		Articulo = articulo;
		Observaciones = observaciones;
		Latitud = latitud;
		Longitud = longitud;
		VentaCredito = ventaCredito;
	}



	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 */
	public Object getProperty(int i) {
		// TODO Auto-generated method stub
		switch(i)
		{
		case 0: return NCaja;
		case 1: return IdCliente;
		case 2: return CedulaVendedor;
		case 3: return Articulo;
		case 4: return Observaciones;
		case 5: return Latitud;
		case 6: return Longitud;
		case 7: return VentaCredito;
		case 8: return IdClienteSucursal;
		}
		
		return null;
	}
	

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
	    case 0:
	        propertyInfo.name = "NCaja";
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
	        propertyInfo.type = ClsArtsFactura.class;        
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
	        propertyInfo.name = "VentaCredito";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
		case 8:
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
