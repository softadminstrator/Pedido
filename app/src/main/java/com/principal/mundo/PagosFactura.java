package com.principal.mundo;

import java.util.ArrayList;

import com.principal.mundo.sysws.ItemFactura;

public class PagosFactura {
	
	private long idPagosFactura;
	private long NPago;
	private long NPagoFac;
	private long NCajaQRecibe;
	private long NFactura;
	private long NCaja;
	private long IdCliente;
	private long Cuenta;
	private long NComprobante;
	private String Fecha;
	private String Hora;
	private String Fecha2;
	private long Descuento;
	private long RteFuente;
	private long Total;
	private long SaldoAnterior;
	private long Saldo;
	private String Observaciones;
	private long IdUsuario;
	private String FacturasQPaga;
	private long RteIca;
	private long RteIva;
	private long Devolucion;
	private ArrayList<ItemPagoFac> listaPagoFac;
	private String IdVendedor;

	public PagosFactura() {
		  idPagosFactura=0;
		  NPagoFac=0;
		  NPago=0;
		  NCajaQRecibe=0;
		  NFactura=0;
		  NCaja=0;
		  IdCliente=0;
		  Cuenta=0;
		  NComprobante=0;
		  Fecha="";
		  Hora="";
		  Fecha2="";
		  Descuento=0;
		  RteFuente=0;
		  Total=0;
		  SaldoAnterior=0;
		  Saldo=0;
		  Observaciones=" ";
		  IdUsuario=0;
		  FacturasQPaga=" ";
		  RteIca=0;
		  RteIva=0;
		  Devolucion=0;
		  listaPagoFac=new ArrayList<ItemPagoFac>();
	}
	
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return NPagoFac;	
		case 1: return NCajaQRecibe;		
		case 2: return NFactura;	
		case 3: return NCaja;
		case 4: return IdCliente;
		case 5: return Cuenta;
		case 6: return NComprobante;
		case 7: return Fecha;
		case 8: return Hora;
		case 9: return Fecha2;
		case 10: return Descuento;
		case 11: return RteFuente;
		case 12: return Total;
		case 13: return SaldoAnterior;
		case 14: return Saldo;
		case 15: return Observaciones;
		case 16: return IdUsuario;
		case 17: return FacturasQPaga;
		case 18: return RteIca;
		case 19: return RteIva;
		case 20: return Devolucion;
		case 21: return Devolucion;
		case 22: return IdVendedor;
		}		
		return null;
	}
	
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "NPagoFac";	
		case 1: return "NCajaQRecibe";		
		case 2: return "NFactura";	
		case 3: return "NCaja";
		case 4: return "IdCliente";
		case 5: return "Cuenta";
		case 6: return "NComprobante";
		case 7: return "Fecha";
		case 8: return "Hora";
		case 9: return "Fecha2";
		case 10: return "Descuento";
		case 11: return "RteFuente";
		case 12: return "Total";
		case 13: return "SaldoAnterior";
		case 14: return "Saldo";
		case 15: return "Observaciones";
		case 16: return "IdUsuario";
		case 17: return "FacturasQPaga";
		case 18: return "RteIca";
		case 19: return "RteIva";
		case 20: return "Devolucion";
		case 21: return "listaPagoFac";
		case 22: return "IdVendedor";
		}
		return null;
	}

	public void setProperty(int i, String data) {
		
		switch(i)
		{			
		case 0: NPagoFac=Long.parseLong(data);break;	
		case 1: NCajaQRecibe=Long.parseLong(data);break;		
		case 2: NFactura=Long.parseLong(data);break;	
		case 3: NCaja=Long.parseLong(data);break;
		case 4: IdCliente=Long.parseLong(data);break;
		case 5: Cuenta=Long.parseLong(data);break;
		case 6: NComprobante=Long.parseLong(data);break;
		case 7: Fecha=data;break;
		case 8: Hora=data;break;
		case 9: Fecha2=data;break;
		case 10: Descuento=Long.parseLong(data);break;
		case 11: RteFuente=Long.parseLong(data);break;
		case 12: Total=Long.parseLong(data);break;
		case 13: SaldoAnterior=Long.parseLong(data);break;
		case 14: Saldo=Long.parseLong(data);break;
		case 15: Observaciones=data;break;
		case 16: IdUsuario=Long.parseLong(data);break;
		case 17: FacturasQPaga=data;break;
		case 18: RteIca=Long.parseLong(data);break;
		case 19: RteIva=Long.parseLong(data);break;
		case 20: Devolucion=Long.parseLong(data);break;
		case 21: Devolucion=Long.parseLong(data);break;
		case 22: IdVendedor =data;break;
		}	
}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 23;
	}


	public long getNPagoFac() {
		return NPagoFac;
	}


	public void setNPagoFac(long nPagoFac) {
		NPagoFac = nPagoFac;
	}


	public long getNCajaQRecibe() {
		return NCajaQRecibe;
	}


	public void setNCajaQRecibe(long nCajaQRecibe) {
		NCajaQRecibe = nCajaQRecibe;
	}


	public long getNFactura() {
		return NFactura;
	}


	public void setNFactura(long nFactura) {
		NFactura = nFactura;
	}


	public long getNCaja() {
		return NCaja;
	}


	public void setNCaja(long nCaja) {
		NCaja = nCaja;
	}


	public long getIdCliente() {
		return IdCliente;
	}


	public void setIdCliente(long idCliente) {
		IdCliente = idCliente;
	}


	public long getCuenta() {
		return Cuenta;
	}


	public void setCuenta(long cuenta) {
		Cuenta = cuenta;
	}


	public long getNComprobante() {
		return NComprobante;
	}


	public void setNComprobante(long nComprobante) {
		NComprobante = nComprobante;
	}


	public String getFecha() {
		return Fecha;
	}


	public void setFecha(String fecha) {
		Fecha = fecha;
	}


	public String getHora() {
		return Hora;
	}


	public void setHora(String hora) {
		Hora = hora;
	}


	public String getFecha2() {
		return Fecha2;
	}


	public void setFecha2(String fecha2) {
		Fecha2 = fecha2;
	}


	public long getDescuento() {
		return Descuento;
	}


	public void setDescuento(long descuento) {
		Descuento = descuento;
	}


	public long getRteFuente() {
		return RteFuente;
	}


	public void setRteFuente(long rteFuente) {
		RteFuente = rteFuente;
	}


	public long getTotal() {
		return Total;
	}


	public void setTotal(long total) {
		Total = total;
	}


	public long getSaldoAnterior() {
		return SaldoAnterior;
	}


	public void setSaldoAnterior(long saldoAnterior) {
		SaldoAnterior = saldoAnterior;
	}


	public long getSaldo() {
		return Saldo;
	}


	public void setSaldo(long saldo) {
		Saldo = saldo;
	}


	public String getObservaciones() {
		return Observaciones;
	}


	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}


	public long getIdUsuario() {
		return IdUsuario;
	}


	public void setIdUsuario(long idUsuario) {
		IdUsuario = idUsuario;
	}


	public String getFacturasQPaga() {
		return FacturasQPaga;
	}


	public void setFacturasQPaga(String facturasQPaga) {
		FacturasQPaga = facturasQPaga;
	}


	public long getRteIca() {
		return RteIca;
	}


	public void setRteIca(long rteIca) {
		RteIca = rteIca;
	}


	public long getRteIva() {
		return RteIva;
	}


	public void setRteIva(long rteIva) {
		RteIva = rteIva;
	}


	public long getDevolucion() {
		return Devolucion;
	}


	public void setDevolucion(long devolucion) {
		Devolucion = devolucion;
	}


	public ArrayList<ItemPagoFac> getListaPagoFac() {
		return listaPagoFac;
	}


	public void setListaPagoFac(ArrayList<ItemPagoFac> listaPagoFac) {
		this.listaPagoFac = listaPagoFac;
	}

	public long getIdPagosFactura() {
		return idPagosFactura;
	}

	public void setIdPagosFactura(long idPagosFactura) {
		this.idPagosFactura = idPagosFactura;
	}

	public long getNPago() {
		return NPago;
	}

	public void setNPago(long nPago) {
		NPago = nPago;
	}

	public long getValorAbonosFactura()
	{
		long abonos=0;
		for (int i = 0; i < listaPagoFac.size(); i++) {
			abonos+=listaPagoFac.get(i).getValor();
		}
		return abonos;
	}
	public String getXmlAbonosFactura()
	{
		String xml="";
		xml="<Posstar7>\n";
		for (int i = 0; i < listaPagoFac.size(); i++) {
			ItemPagoFac ipf= listaPagoFac.get(i);
			xml +="<Pago>\n";
			for (int j = 0; j < ipf.getPropertyCount(); j++) {
				xml +="		<"+ipf.getPropertyName(j)+">"+ipf.getProperty(j)+"</"+ipf.getPropertyName(j)+">\n";				
			}
			xml +="</Pago>\n";			
		}
		xml +="</Posstar7>";
		return xml;
	}

	public String getIdVendedor() {
		return IdVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		IdVendedor = idVendedor;
	}
}