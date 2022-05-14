package com.principal.mundo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DecimalFormat;

/**
 * Clase que representa el pedido que fue enviado al sistema PosStar exitosamente
 * para luego ser almacenado en el telefono
 * @author Javier
 *
 */
public class Pedido_in
{
	/**
	 * identificador del codigo interno de pedido
	 */
	public long idCodigoInterno;
	/**
	 * identificador del codigo externo del pedido
	 */
	public long idCodigoExterno;
	/**
	 * identificador del cliente al que se le realizo el pediod
	 */
	public long idCliente;
	/**
	 * nombre del cliente al que se le realizo el pedido
	 */
	public String nombreCliente;
	/**
	 * fecha en la que se realizo el pedido
	 */
	private String fecha;
	/**
	 * cedula del usuario o numero de ruta de quien realizo el pedido
	 */
	public String cedulaUsuario;
	/**
	 * observacion del pedido
	 */
	public String observaciones;
	/**
	 * latitud de donde se realizo el pedido
	 */
	public String latitud;
	/**
	 * longitud de donde se realizo el pedido
	 */
	public String longitud;
	/**
	 * hora en la que se realizo el pedido
	 */
	public String hora;
	/**
	 * valor total del pedido
	 */
	public long valor;

	public long SubTotal;

	public long DescuentoTotal;

	/**
	 * numero de mesa del pedido
	 */
	public String mesa;
	/**
	 * lista de articulos del pedido
	 */
	public ArrayList articulos;
	
	
	private String fechaSqlite;
	
	private String nombreVendedor;
	
	private long envio;

	private String Documento;

	private String FormaPago;

	public long idClienteSucursal;

	private String Estado;


	/**
	 * metodo que se encarga de asignar valores a los atributos del pedido
	 */
	public Pedido_in()
	{
		envio=0;
		idCodigoExterno=0;
		idCodigoInterno=0;
		idCliente=0;
		fecha="";
		valor=0;
		articulos=null;
		nombreCliente="";
		hora="";
		observaciones="";
		SubTotal=0;
		DescuentoTotal=0;
		Documento="FAC";
		FormaPago="1";
		idClienteSucursal=0;

	}

	/**
	 * metodo que retorna la hora en la que fue realizado el pedido
	 * @return hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * metodo que asigna el nuevo valor de hora del pedido
	 * @param hora
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * metodo que retorna el nombre del cliente a quien fue realizado el pedido
	 * @return nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}

	/**
	 * metodo que asigna el nuevo nombre del cliente a quien se le realiza el pedido
	 * @param nombreCliente
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	/**
	 * metodo que retorna el identificador interno del pedido
	 * @return idCodigoInterno
	 */
	public long getIdCodigoInterno() {
		return idCodigoInterno;
	}

	/**
	 * metodo que asigna el nuevo valor del identificador interno del pedido
	 * @param idCodigoInterno
	 */
	public void setIdCodigoInterno(long idCodigoInterno) {
		this.idCodigoInterno = idCodigoInterno;
	}

	/**
	 * metodo que retorna el codigo externo del pedido
	 * @return idCodigoExterno
	 */
	public long getIdCodigoExterno() {
		return idCodigoExterno;
	}

	/**
	 * metodo que asigna el nuevo valor del codigo esterno del pedido
	 * @param idCodigoExterno
	 */
	public void setIdCodigoExterno(long idCodigoExterno) {
		this.idCodigoExterno = idCodigoExterno;
	}

	/**
	 * metodo que retorna el identificador del cliente 
	 * @return
	 */
	public long getIdCliente() {
		return idCliente;
	}

	/**
	 * metodo que asigna el nuevo valor al identificador del cliente
	 * @param idCliente
	 */
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * metodo que retorna la fecha en la cual fue realizado el pedido
	 * @return fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * metodo que asigna la nueva fecha del pedido
	 * @param fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * metodo que retorna el valor total del pedido
	 * @return
	 */
	public long getValor() {
		return valor;
	}

	/**
	 * metodo que asigna el nuevo valor total del pedido
	 * @param valor
	 */
	public void setValor(long valor) {
		this.valor = valor;
	}

	/**
	 * metodo que retorna la lista de articulos del pedido
	 * @return articulos
	 */
	public ArrayList getArticulos() {
		return articulos;
	}

	/**
	 * metodo que asigna la nueva lista de articulos del pedido
	 * @param articulos
	 */
	public void setArticulos(ArrayList articulos) {
		this.articulos = articulos;
	}
	/**
	 * metodo que retorna la observacion del pedido
	 * @return observaciones
	 */
	public String getObservaciones() {
		if(observaciones==null)
		{
			return " ";
		}
		return observaciones;
	}

	/**
	 * metodo que asigna el nuevo valor de la observacion del pedido
	 * @param observaciones
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * metodo que retorna la latitud de donde fue enviado al pedido
	 * @return latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * mettodo qu asigna el nuevo valor de la latitud donde fue enviado el pedido
	 * @param latitud
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/**
	 * medodo que retorna la longitud donde fue enviado el pedido
	 * @return longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * metodo que asigna el nuevo valor de la longitud donde fue enviado el pedido
	 * @param longitud
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	/**
	 * metodo que retorna la cedula del usuario que realizo el pedido
	 * @return cedulaUsuario
	 */
	public String getCedulaUsuario() {
		return cedulaUsuario;
	}

	/**
	 * metodo que asigna el nuevo valor a la cedula del usuario que realizo el pedido
	 * @param cedulaUsuario
	 */
	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}
	public String getFechaSqlite() {
		
		 final Calendar c = Calendar.getInstance();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 try
		 {
			 c.setTime(dateFormat.parse(fecha));	 
			 fechaSqlite=c.get(Calendar.YEAR)+"-"+ validNumberDate((c.get(Calendar.MONTH)+1))+"-"+ validNumberDate(c.get(Calendar.DAY_OF_MONTH));
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
			 fecha= validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+ validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);
	    }
		 catch (Exception e) {
			// TODO: handle exception
		}		
	}

	private String validNumberDate(int value)
	{
		String res=""+value;
		if(value<10)
		{
			res="0"+value;
		}
		return res;
	}


	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public long getEnvio() {
		return envio;
	}

	public void setEnvio(long envio) {
		this.envio = envio;
	}

	public long getSubTotal() {
		return SubTotal;
	}

	public void setSubTotal(long subTotal) {
		SubTotal = subTotal;
	}

	public long getDescuentoTotal() {
		return DescuentoTotal;
	}

	public void setDescuentoTotal(long descuentoTotal) {
		DescuentoTotal = descuentoTotal;
	}
	public String getFormatoDecimal(long numero) {
		DecimalFormat	decimalFormat=new DecimalFormat("###,###,###");
		return "$ "+decimalFormat.format(numero);
	}

	public String getDocumento() {
		return Documento;
	}

	public void setDocumento(String documento) {
		Documento = documento;
	}

	public String getTipoDocumento()
	{
		try
		{
			if(Documento.equals("REM"))
			{
				return "R";
			}
			else
			{
				return "F";
			}
		}
		catch (Exception e)
		{
			return "F";
		}
	}

	public String getFormaPago() {
		return FormaPago;
	}

	public void setFormaPago(String formaPago) {
		FormaPago = formaPago;
	}
	public String getTipoFormaPago()
	{
		if(FormaPago.equals("1"))
		{
			return "Credito";
		}
		return "Contado";
	}

	public long getIdClienteSucursal() {
		return idClienteSucursal;
	}

	public void setIdClienteSucursal(long idClienteSucursal) {
		this.idClienteSucursal = idClienteSucursal;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
}
