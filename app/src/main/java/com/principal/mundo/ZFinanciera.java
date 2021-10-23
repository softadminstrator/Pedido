package com.principal.mundo;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class ZFinanciera {
	
	public long NumZ;
	public long NCaja;
	public String Fecha;
	public String Hora;
	public long FacturaInicial;
	public long FacturaFinal;
	public long VentasExentas;
	public long Ventas16;
	public long Iva16;
	public long Total16;
	public long Ventas5;
	public long Iva5;
	public long Total5;
	public long Ventas8;
	public long Iva8;
	public long Total8;
	public long NTransacciones;
	public long Ventas;
	public long Iva;
	public long ImpoCmo;
	public long Descuento;
	public long Total;
	public long consulta;
	public long Ventas19;
	public long Iva19;
	public long Total19;
	
	public ArrayList<MediosDePago> listaMendiosDePago;
	
	public ZFinanciera() {
		listaMendiosDePago=new ArrayList<MediosDePago>();
	}
	public ZFinanciera(long NumZ, long NCaja) {
		
		  this.NumZ=NumZ;
		  this.NCaja=NCaja;
		  Date fecha=new Date();
          SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
          Fecha=sdf.format(fecha);            
          SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
          Hora = hora.format(fecha);		
		  listaMendiosDePago=new ArrayList<MediosDePago>();
		  consulta=0;
	}

	public ZFinanciera(long numZ, long nCaja, String fecha, String hora,
			long facturaInicial, long facturaFinal, long ventasExentas,
			long ventas16, long iva16, long total16, long ventas5, long total5,
			long ventas8, long iva8, long total8, long nTransacciones,
			long ventas, long iva, long impoCmo, long descuento, long total,
			ArrayList<MediosDePago> listaMendiosDePago) {
		NumZ = numZ;
		NCaja = nCaja;
		Fecha = fecha;
		Hora = hora;
		FacturaInicial = facturaInicial;
		FacturaFinal = facturaFinal;
		VentasExentas = ventasExentas;
		Ventas16 = ventas16;
		Iva16 = iva16;
		Total16 = total16;
		Ventas5 = ventas5;
		Total5 = total5;
		Ventas8 = ventas8;
		Iva8 = iva8;
		Total8 = total8;
		NTransacciones = nTransacciones;
		Ventas = ventas;
		Iva = iva;
		ImpoCmo = impoCmo;
		Descuento = descuento;
		Total = total;
		this.listaMendiosDePago = listaMendiosDePago;
	}

	public long getNumZ() {
		return NumZ;
	}

	public void setNumZ(long numZ) {
		NumZ = numZ;
	}

	public long getNCaja() {
		return NCaja;
	}

	public void setNCaja(long nCaja) {
		NCaja = nCaja;
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

	public long getFacturaInicial() {
		return FacturaInicial;
	}

	public void setFacturaInicial(long facturaInicial) {
		FacturaInicial = facturaInicial;
	}

	public long getFacturaFinal() {
		return FacturaFinal;
	}

	public void setFacturaFinal(long facturaFinal) {
		FacturaFinal = facturaFinal;
	}

	public long getVentasExentas() {
		return VentasExentas;
	}

	public void setVentasExentas(long ventasExentas) {
		VentasExentas = ventasExentas;
	}

	public long getVentas16() {
		return Ventas16;
	}

	public void setVentas16(long ventas16) {
		Ventas16 = ventas16;
	}

	public long getIva16() {
		return Iva16;
	}

	public void setIva16(long iva16) {
		Iva16 = iva16;
	}

	public long getTotal16() {
		return Total16;
	}

	public void setTotal16(long total16) {
		Total16 = total16;
	}

	public long getVentas5() {
		return Ventas5;
	}

	public void setVentas5(long ventas5) {
		Ventas5 = ventas5;
	}

	public long getTotal5() {
		return Total5;
	}

	public void setTotal5(long total5) {
		Total5 = total5;
	}

	public long getVentas8() {
		return Ventas8;
	}

	public void setVentas8(long ventas8) {
		Ventas8 = ventas8;
	}

	public long getIva8() {
		return Iva8;
	}

	public void setIva8(long iva8) {
		Iva8 = iva8;
	}

	public long getTotal8() {
		return Total8;
	}

	public void setTotal8(long total8) {
		Total8 = total8;
	}

	public long getNTransacciones() {
		return NTransacciones;
	}

	public void setNTransacciones(long nTransacciones) {
		NTransacciones = nTransacciones;
	}

	public long getVentas() {
		return Ventas;
	}

	public void setVentas(long ventas) {
		Ventas = ventas;
	}

	public long getIva() {
		return Iva;
	}

	public void setIva(long iva) {
		Iva = iva;
	}

	public long getImpoCmo() {
		return ImpoCmo;
	}

	public void setImpoCmo(long impoCmo) {
		ImpoCmo = impoCmo;
	}

	public long getDescuento() {
		return Descuento;
	}

	public void setDescuento(long descuento) {
		Descuento = descuento;
	}

	public long getTotal() {
		return Total;
	}

	public void setTotal(long total) {
		Total = total;
	}

	public ArrayList<MediosDePago> getListaMendiosDePago() {
		return listaMendiosDePago;
	}

	public void setListaMendiosDePago(ArrayList<MediosDePago> listaMendiosDePago) {
		this.listaMendiosDePago = listaMendiosDePago;
	}
	public long getConsulta() {
		return consulta;
	}
	public void setConsulta(long consulta) {
		this.consulta = consulta;
	}
	public long getTotalMedios()
	{
		long res=0;
		for (int i = 0; i < listaMendiosDePago.size(); i++) {
			res+=listaMendiosDePago.get(i).Valor;
		}
		return res;
	}
	public long getIva5() {
		return Iva5;
	}
	public void setIva5(long iva5) {
		Iva5 = iva5;
	}
	public long getVentas19() {
		return Ventas19;
	}
	public void setVentas19(long ventas19) {
		Ventas19 = ventas19;
	}
	public long getIva19() {
		return Iva19;
	}
	public void setIva19(long iva19) {
		Iva19 = iva19;
	}
	public long getTotal19() {
		return Total19;
	}
	public void setTotal19(long total19) {
		Total19 = total19;
	}

}
