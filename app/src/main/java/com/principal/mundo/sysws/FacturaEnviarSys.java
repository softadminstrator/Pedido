package com.principal.mundo.sysws;

import java.util.ArrayList;

import com.principal.mundo.ArticulosFactura;

public class FacturaEnviarSys {
private String NCaja;	
private String NFactura;		
private String IdCliente;	
private String CedulaVendedor;
private String Observaciones;
private String Latitud;
private String Longitud;
private String VentaCredito;
private String Fecha;
private String Fecha2;
private String Hora;
private String xmlArticulos;
private String IdClienteSucursal;

	public FacturaEnviarSys() {
		// TODO Auto-generated constructor stub
	}


	public FacturaEnviarSys(String NCaja, String NFactura, String idCliente, String cedulaVendedor, String observaciones, String latitud, String longitud, String ventaCredito, String fecha, String fecha2, String hora, String xmlArticulos, String idClienteSucursal) {
		this.NCaja = NCaja;
		this.NFactura = NFactura;
		IdCliente = idCliente;
		CedulaVendedor = cedulaVendedor;
		Observaciones = observaciones;
		Latitud = latitud;
		Longitud = longitud;
		VentaCredito = ventaCredito;
		Fecha = fecha;
		Fecha2 = fecha2;
		Hora = hora;
		this.xmlArticulos = xmlArticulos;
		IdClienteSucursal = idClienteSucursal;
	}

	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return NCaja;	
		case 1: return NFactura;		
		case 2: return IdCliente;	
		case 3: return CedulaVendedor;
		case 4: return Observaciones;
		case 5: return Latitud;
		case 6: return Longitud;
		case 7: return VentaCredito;
		case 8: return Fecha;
		case 9: return Fecha2;
		case 10: return Hora;
		case 11: return IdClienteSucursal;
		}		
		return null;
	}
	
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "NCaja";	
		case 1: return "NFactura";		
		case 2: return "IdCliente";	
		case 3: return "CedulaVendedor";
		case 4: return "Observaciones";
		case 5: return "Latitud";
		case 6: return "Longitud";
		case 7: return "VentaCredito";
		case 8: return "Fecha";
		case 9: return "Fecha2";
		case 10: return "Hora";
		case 11: return "IdClienteSucursal";
		}
		return null;
	}

	public void setProperty(int i, String data) {
		
		switch(i)
		{		
		case 0: NCaja=data;break;	
		case 1: NFactura=data;break;		
		case 2: IdCliente=data;break;	
		case 3: CedulaVendedor=data;break;
		case 4: Observaciones=data;break;
		case 5: Latitud=data;break;
		case 6: Longitud=data;break;
		case 7: VentaCredito=data;break;
		case 8: Fecha=data;break;
		case 9: Fecha2=data;break;
		case 10: Hora=data;break;
		case 11: IdClienteSucursal=data;break;
		
		}	
}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 12;
	}
	


	public String getNCaja() {
		return NCaja;
	}


	public void setNCaja(String nCaja) {
		NCaja = nCaja;
	}


	public String getNFactura() {
		return NFactura;
	}


	public void setNFactura(String nFactura) {
		NFactura = nFactura;
	}


	public String getIdCliente() {
		return IdCliente;
	}


	public void setIdCliente(String idCliente) {
		IdCliente = idCliente;
	}


	public String getCedulaVendedor() {
		return CedulaVendedor;
	}


	public void setCedulaVendedor(String cedulaVendedor) {
		CedulaVendedor = cedulaVendedor;
	}


	public String getObservaciones() {
		return Observaciones;
	}


	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}


	public String getLatitud() {
		return Latitud;
	}


	public void setLatitud(String latitud) {
		Latitud = latitud;
	}


	public String getLongitud() {
		return Longitud;
	}


	public void setLongitud(String longitud) {
		Longitud = longitud;
	}


	public String getVentaCredito() {
		return VentaCredito;
	}


	public void setVentaCredito(String ventaCredito) {
		VentaCredito = ventaCredito;
	}


	public String getFecha() {
		return Fecha;
	}


	public void setFecha(String fecha) {
		Fecha = fecha;
	}


	public String getFecha2() {
		return Fecha2;
	}


	public void setFecha2(String fecha2) {
		Fecha2 = fecha2;
	}


	public String getHora() {
		return Hora;
	}


	public void setHora(String hora) {
		Hora = hora;
	}


	public String getXmlArticulos() {
		return xmlArticulos;
	}


	public void setXmlArticulos(String xmlArticulos) {
		this.xmlArticulos = xmlArticulos;
	}


	public void setListaArticulos(ArrayList<ArticulosFactura> listaArticulos )
	{
		String xml="";
		xml="<Factura>\n";
		for (int i = 0; i < listaArticulos.size(); i++) {
			ItemFactura art= new ItemFactura(listaArticulos.get(i));
			xml +="<Articulo>\n";
			for (int j = 0; j < art.getPropertyCount(); j++) {
				xml +="		<"+art.getPropertyName(j)+">"+art.getProperty(j)+"</"+art.getPropertyName(j)+">\n";				
			}
			xml +="</Articulo>\n";			
		}
		xml +="</Factura>";
		xmlArticulos=xml;
	}

	public String getIdClienteSucursal() {
		return IdClienteSucursal;
	}

	public void setIdClienteSucursal(String idClienteSucursal) {
		IdClienteSucursal = idClienteSucursal;
	}
}
