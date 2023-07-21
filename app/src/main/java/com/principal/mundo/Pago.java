package com.principal.mundo;

import java.util.ArrayList;

public class Pago {
	
	private long idPago;
	private long Valor;
	private String Descripcion;
	private long idCliente;
	private long NPagosFacNoEnviados;	
	private String fecha;
	private String fecha2;
	private long Enviado;
	private String nombreCliente;
	private long deudaCliente;

	private String representanteCliente;
	
	private ArrayList<PagosFactura> listaPagosFactura;

	public Pago(long idPago, long valor, String descripcion, long idCliente,
			long nPagosFacNoEnviados, String fecha, String fecha2,
			long enviado, String nombreCliente,
			ArrayList<PagosFactura> listaPagosFactura) {
		super();
		this.idPago = idPago;
		Valor = valor;
		Descripcion = descripcion;
		this.idCliente = idCliente;
		NPagosFacNoEnviados = nPagosFacNoEnviados;
		this.fecha = fecha;
		this.fecha2 = fecha2;
		Enviado = enviado;
		this.nombreCliente = nombreCliente;
		this.listaPagosFactura = listaPagosFactura;
	}


	public Pago() {
		idPago=0;
		Valor=0;
		Descripcion="";
		idCliente=0;
		NPagosFacNoEnviados=0;		
		listaPagosFactura=new ArrayList<PagosFactura>();
	}


	public long getIdPago() {
		return idPago;
	}


	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}


	public long getValorListaPagos() {
		Valor=0;
		for (int i = 0; i < listaPagosFactura.size(); i++) {
			Valor+=listaPagosFactura.get(i).getTotal();
		}		
		return Valor;
	}
public long getValor() {
	return Valor;
}

	public void setValor(long valor) {
		Valor = valor;
	}


	public String getDescripcion() {
		return Descripcion;
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}


	public long getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}	
	public ArrayList<PagosFactura> getListaPagosFactura() {
		return listaPagosFactura;
	}


	public void setListaPagosFactura(ArrayList<PagosFactura> listaPagosFactura) {
		this.listaPagosFactura = listaPagosFactura;
	}

	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getFecha2() {
		return fecha2;
	}


	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}


	public long getNPagosFacNoEnviados() {
		return NPagosFacNoEnviados;
	}


	public void setNPagosFacNoEnviados(long nPagosFacNoEnviados) {
		NPagosFacNoEnviados = nPagosFacNoEnviados;
	}


	public long getEnviado() {
		return Enviado;
	}


	public void setEnviado(long enviado) {
		Enviado = enviado;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public long getDeudaCliente() {
		return deudaCliente;
	}

	public void setDeudaCliente(long deudaCliente) {
		this.deudaCliente = deudaCliente;
	}

	public String getRepresentanteCliente() {
		return representanteCliente;
	}

	public void setRepresentanteCliente(String representanteCliente) {
		this.representanteCliente = representanteCliente;
	}
}
