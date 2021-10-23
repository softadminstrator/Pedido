package com.principal.mundo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que representa el pedido que fue enviado al sistema PosStar exitosamente
 * para luego ser almacenado en el telefono
 * @author Javier
 *
 */
public class Traslado_in
{
	
	public  long idCodigoInterno;
	public long idCodigoExterno;
	public Bodega bodegaOrigen;
	public Bodega bodegaDestino;		
	private String observaciones;	
	private ArrayList<ArticulosTraslado> listaArticulos;
	
	
	private String fechaSqlite;
	
	public String razonSocial;
	public String representante;
	public String regimenNit;
	public String direccionTel;
	public long NCaja;
	private String fecha;	
	public String hora;
	public long base0;
	public long base5;
	public long base10;
	public long base14;
	public long base16;
	public long base19;
	public long iva5;
	public long iva10;
	public long iva14;
	public long iva16;
	public long iva19;
	public long impoCmo;
	public long totalTraslado;
	public long dineroRecibido;
	
	public Traslado_in() {
		listaArticulos=new ArrayList<ArticulosTraslado>();
		bodegaOrigen=new Bodega();
		bodegaDestino=new Bodega();
	}
	
	
	public Traslado_in(long idCodigoInterno, long idCodigoExterno,
			Bodega bodegaOrigen, Bodega bodegaDestino, String observaciones,
			ArrayList<ArticulosTraslado> listaArticulos, long valor,
			String razonSocial, String representante, String regimenNit,
			String direccionTel, long nCaja, String fecha, String hora,
			long base0, long base5, long base10, long base14, long base16,
			long iva5, long iva10, long iva14, long iva16, long impoCmo,
			long totalTraslado, long dineroRecibido) {
		super();
		this.idCodigoInterno = idCodigoInterno;
		this.idCodigoExterno = idCodigoExterno;
		this.bodegaOrigen = bodegaOrigen;
		this.bodegaDestino = bodegaDestino;
		this.observaciones = observaciones;
		this.listaArticulos = listaArticulos;	
		this.razonSocial = razonSocial;
		this.representante = representante;
		this.regimenNit = regimenNit;
		this.direccionTel = direccionTel;
		NCaja = nCaja;
		this.fecha = fecha;
		this.hora = hora;
		this.base0 = base0;
		this.base5 = base5;
		this.base10 = base10;
		this.base14 = base14;
		this.base16 = base16;
		this.iva5 = iva5;
		this.iva10 = iva10;
		this.iva14 = iva14;
		this.iva16 = iva16;
		this.impoCmo = impoCmo;
		this.totalTraslado = totalTraslado;
		this.dineroRecibido = dineroRecibido;
	
	}


	public long getIdCodigoInterno() {
		return idCodigoInterno;
	}


	public void setIdCodigoInterno(long idCodigoInterno) {
		this.idCodigoInterno = idCodigoInterno;
	}


	public long getIdCodigoExterno() {
		return idCodigoExterno;
	}


	public void setIdCodigoExterno(long idCodigoExterno) {
		this.idCodigoExterno = idCodigoExterno;
	}


	public Bodega getBodegaOrigen() {
		return bodegaOrigen;
	}


	public void setBodegaOrigen(Bodega bodegaOrigen) {
		this.bodegaOrigen = bodegaOrigen;
	}


	public Bodega getBodegaDestino() {
		return bodegaDestino;
	}


	public void setBodegaDestino(Bodega bodegaDestino) {
		this.bodegaDestino = bodegaDestino;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public ArrayList<ArticulosTraslado> getListaArticulos() {
		return listaArticulos;
	}


	public void setListaArticulos(ArrayList<ArticulosTraslado> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}




	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getRepresentante() {
		return representante;
	}


	public void setRepresentante(String representante) {
		this.representante = representante;
	}


	public String getRegimenNit() {
		return regimenNit;
	}


	public void setRegimenNit(String regimenNit) {
		this.regimenNit = regimenNit;
	}


	public String getDireccionTel() {
		return direccionTel;
	}


	public void setDireccionTel(String direccionTel) {
		this.direccionTel = direccionTel;
	}


	public long getNCaja() {
		return NCaja;
	}


	public void setNCaja(long nCaja) {
		NCaja = nCaja;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public long getBase0() {
		return base0;
	}


	public void setBase0(long base0) {
		this.base0 = base0;
	}


	public long getBase5() {
		return base5;
	}


	public void setBase5(long base5) {
		this.base5 = base5;
	}


	public long getBase10() {
		return base10;
	}


	public void setBase10(long base10) {
		this.base10 = base10;
	}


	public long getBase14() {
		return base14;
	}


	public void setBase14(long base14) {
		this.base14 = base14;
	}


	public long getBase16() {
		return base16;
	}


	public void setBase16(long base16) {
		this.base16 = base16;
	}


	public long getIva5() {
		return iva5;
	}


	public void setIva5(long iva5) {
		this.iva5 = iva5;
	}


	public long getIva10() {
		return iva10;
	}


	public void setIva10(long iva10) {
		this.iva10 = iva10;
	}


	public long getIva14() {
		return iva14;
	}


	public void setIva14(long iva14) {
		this.iva14 = iva14;
	}


	public long getIva16() {
		return iva16;
	}


	public void setIva16(long iva16) {
		this.iva16 = iva16;
	}


	public long getImpoCmo() {
		return impoCmo;
	}


	public void setImpoCmo(long impoCmo) {
		this.impoCmo = impoCmo;
	}


	public long getTotalTraslado() {
		return totalTraslado;
	}


	public void setTotalTraslado(long totalTraslado) {
		this.totalTraslado = totalTraslado;
	}


	public long getDineroRecibido() {
		return dineroRecibido;
	}


	public void setDineroRecibido(long dineroRecibido) {
		this.dineroRecibido = dineroRecibido;
	}




	public String getFormatoDecimal(long numero) {
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		return "$ "+decimalFormat.format(numero);
	}
	public String getValorText() {
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		return "$ "+decimalFormat.format(totalTraslado);
	}
	public long getCambio() {
		return dineroRecibido-totalTraslado;
	}
	
	public String getFechaSqlite() {
		
		 final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 try
		 {
			 c.setTime(dateFormat.parse(fecha));	 
			 fechaSqlite=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		return fechaSqlite;
	}
	public void setFechaSqlite(String fechaSqlite) {
		this.fechaSqlite = fechaSqlite;
		 final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 try
		 {
			 c.setTime(dateFormat.parse(fechaSqlite));
			 fecha=c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
	    }
		 catch (Exception e) {
			// TODO: handle exception
		}		
	}


	public long getBase19() {
		return base19;
	}


	public void setBase19(long base19) {
		this.base19 = base19;
	}


	public long getIva19() {
		return iva19;
	}


	public void setIva19(long iva19) {
		this.iva19 = iva19;
	}
	
}
