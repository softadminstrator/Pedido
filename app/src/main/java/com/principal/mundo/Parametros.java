package com.principal.mundo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase en la que se describen los parametros utilizados por la aplicacion
 * para la configuracion y envio de informacion a los servicios web determinados
 * @author Javier
 *
 */
public class Parametros {
	
	/**
	 *  referente al nombre del servicio web
	 */
	public String ws;
	/**
	 * direccion ip publica completa en donde se encuentra el servicio web donde se 
	 * enviara informacion
	 */
	public String ip;	
	/**
	 * representa rango de numeros hasta el primer punto(.) ###.255.255.255
	 */
	public String ip1;	
	/**
	 * representa rango de numeros del primer punto(.) hasta el segundo punto(.) 255.###.255.255
	 */
	public String ip2;	
	/**
	 * representa rango de numeros del segundo punto(.) hasta el tercer punto(.) 255.255.###.255
	 */
	public String ip3;
	/**
	 * representa rango de numeros del tercer punto(.) en adelante 255.255.255.###
	 */
	public String ip4;
	
	/**
	 * identificador que determina si el usuario puede modificar el valor de los productos
	 * a la hora de realizar el pedido
	 */
    public long modificaPrecio;
    private long usaCatalogo;
	private long consultaArticuloEnLinea;	
	public String fecha;//dd/mm/yyyy
	private long administraInventario;
	private long modificaStock;
	private long bodegaAdmInvOmision;
	private long realizaPedidos;
	private long bodegaPedidosOmision;
	private long realizaFactura;
	private long bodegaFacturaOmision;	
	public  long caja;
	private long usaImpresoraZebra;
	private String macAdd;
	private long realizaTranslados;
	private long bodegaTransladosOmision;
	public String ruta;
	private long generaCierre;
	private long consultaZ;
	private long usaWSCash;	
	private long realizaPedidosMesa;
	private long usaTodasLasCategorias;
	private long permiteStocken0;
	private long precioLibre;
	private long FacturaOnLine;
	//------------------------------------------------------------
	//------------------DATOS CAJA--------------------------------
	//------------------------------------------------------------
	
	private String RazonSocial;
	private String Representante;
	private String RegimenNit;
	private String DireccionTel;
	private String ResDian;
	private String Rango;
	private String NombreVendedor;
	private String Prefijo;

	private long UsaObservMasMenos;
	private long DescuentoPedido;
	private long ImprimePedido;

	private long descuentaStockEnPedido;

	private long  ConsultaCosto;
	
	private String fechaSys;//yyyy-mm-dd
	private String fechaSys2;//yyyymmddhhmi
	private String hora;


	private long usaPrintEpson;
	private String macAddEpson;

	private long usaPrintBixolon;
	private String macAddBixolon;

	private long usaPrintDigitalPos;
	private String macAddDigitalPos;

	private long CarteraOnLine;

	private long ControlaPrecioLibre;
	private long SelectDocumentoPedido;

	private long RealizaAlistamiento;
	private long SelectFormaPagoPedido;

	private long UsaPrestamos;

	private long realizaRemision;
	private long bodegaRemisionOmision;

	
	//------------------------------------------------------------
	// implementacion de paramentros 24/08/2018
	private long usaCantDecimal;
	private long usaSelecMultipleArt;
	private long precioMinimo;
	//------------------------------------------------------------

	// implementacion modifica valor total 17/08/2020
	private long ModificaValorTotal;
	private long Webid;




	private long usaTipoPedido;



	private long permiteStocken0EnPedido;


	private long MuestraEstablecimientoCliente;

	private long UsaStarlapWS;

	private long EnviaUbicacionPedido;


	/**
	 * metodo que asigna valores predeterminados a los atributos del sistema
	 */
	public Parametros()
	{
		ws="";
		ip="";
		ip1="";
		ip2="";
		ip3="";
		ip4="";
		ruta="";
		modificaPrecio = 0;
		RealizaAlistamiento=0;
		SelectFormaPagoPedido=0;
		fecha="";
		
		 Date fechaSystem=new Date();
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         fecha=sdf.format(fechaSystem); 
         
      
         SimpleDateFormat sdfsys=new SimpleDateFormat("dd/MM/yyyy"); 
         fechaSys=sdf.format(fechaSystem);
         
         
         SimpleDateFormat shora=new SimpleDateFormat("HH:mm"); 
         hora = shora.format(fechaSystem);
	}


	public Parametros(String ws, String ip, String ip1, String ip2, String ip3,
			String ip4, long modificaPrecio, long usaCatalogo,
			long consultaArticuloEnLinea, String fecha,
			long administraInventario, long modificaStock,
			long bodegaAdmInvOmision, long realizaPedidos,
			long bodegaPedidosOmision, long realizaFactura,
			long bodegaFacturaOmision, long caja, long usaImpresoraZebra,
			String macAdd, long realizaTranslados,
			long bodegaTransladosOmision, String ruta, long generaCierre,
			long consultaZ, long usaWSCash, long realizaPedidosMesa,
			long usaTodasLasCategorias, long permiteStocken0, long precioLibre,
			long facturaOnLine, String razonSocial, String representante,
			String regimenNit, String direccionTel, String resDian,
			String rango, String nombreVendedor, String prefijo, long UsaObservMasMenos,long DescuentoPedido, long ImprimePedido ,long ConsultaCosto
			, long usaPrintEpson ,String macAddEpson, long usaCantDecimal, long usaSelecMultipleArt, long precioMinimo,long usaPrintBixolon,String macAddBixolon, long CarteraOnLine, long ControlaPrecioLibre
	, long SelectDocumentoPedido,long RealizaAlistamiento, long realizaRemision, long bodegaRemisionOmision, long ModificaValorTotal, long Webid,long usaPrintDigitalPos,String macAddDigitalPos, long descuentaStockEnPedido, long usaTipoPedido ,
					  long permiteStocken0EnPedido, long MuestraEstablecimientoCliente, long UsaStarlapWS, long EnviaUbicacionPedido) {
		super();
		this.ws = ws;
		this.ip = ip;
		this.ip1 = ip1;
		this.ip2 = ip2;
		this.ip3 = ip3;
		this.ip4 = ip4;
		this.modificaPrecio = modificaPrecio;
		this.usaCatalogo = usaCatalogo;
		this.consultaArticuloEnLinea = consultaArticuloEnLinea;
		this.fecha = fecha;
		this.administraInventario = administraInventario;
		this.modificaStock = modificaStock;
		this.bodegaAdmInvOmision = bodegaAdmInvOmision;
		this.realizaPedidos = realizaPedidos;
		this.bodegaPedidosOmision = bodegaPedidosOmision;
		this.realizaFactura = realizaFactura;
		this.bodegaFacturaOmision = bodegaFacturaOmision;
		this.caja = caja;
		this.usaImpresoraZebra = usaImpresoraZebra;
		this.macAdd = macAdd;
		this.realizaTranslados = realizaTranslados;
		this.bodegaTransladosOmision = bodegaTransladosOmision;
		this.ruta = ruta;
		this.generaCierre = generaCierre;
		this.consultaZ = consultaZ;
		this.usaWSCash = usaWSCash;
		this.realizaPedidosMesa = realizaPedidosMesa;
		this.usaTodasLasCategorias = usaTodasLasCategorias;
		this.permiteStocken0 = permiteStocken0;
		this.precioLibre = precioLibre;
		FacturaOnLine = facturaOnLine;
		RazonSocial = razonSocial;
		Representante = representante;
		RegimenNit = regimenNit;
		DireccionTel = direccionTel;
		ResDian = resDian;
		Rango = rango;
		NombreVendedor = nombreVendedor;
		Prefijo = prefijo;
		fechaSys = fecha;
		fechaSys2 = fecha;
		this.UsaObservMasMenos = UsaObservMasMenos;
		this.DescuentoPedido = DescuentoPedido;
		this.ImprimePedido = ImprimePedido;
		this.ConsultaCosto = ConsultaCosto;
		this.usaPrintEpson = usaPrintEpson;
		this.macAddEpson=macAddEpson;
		this.usaCantDecimal=usaCantDecimal;
		this.usaSelecMultipleArt=usaSelecMultipleArt;
		this.precioMinimo=precioMinimo;
		this.usaPrintBixolon=usaPrintBixolon;
		this.macAddBixolon=macAddBixolon;
		this.CarteraOnLine=CarteraOnLine;
		this.ControlaPrecioLibre=ControlaPrecioLibre;
		this.SelectDocumentoPedido=SelectDocumentoPedido;
		this.RealizaAlistamiento=RealizaAlistamiento;
		this.realizaRemision=realizaRemision;
		this.bodegaRemisionOmision=bodegaRemisionOmision;
		this.ModificaValorTotal=ModificaValorTotal;
		this.Webid=Webid;
		this.usaPrintDigitalPos=usaPrintDigitalPos;
		this.macAddDigitalPos=macAddDigitalPos;
		this.descuentaStockEnPedido=descuentaStockEnPedido;
		this.usaTipoPedido=usaTipoPedido;
		this.permiteStocken0EnPedido=permiteStocken0EnPedido;
		this.MuestraEstablecimientoCliente = MuestraEstablecimientoCliente;
		this.UsaStarlapWS=UsaStarlapWS;
		this.EnviaUbicacionPedido=EnviaUbicacionPedido;
	}


	/**
	 * metodo que retorna la direccion ip publica armada
	 * @return
	 */
	public String getIp() {
		return ip1+"."+ip2+"."+ip3+"."+ip4;
	}
	public String getIp(String prueba) {
		return ip;
	}

	public long getCaja() {
		return caja;
	}
	public long getCajaRem() {
		return caja+20;
	}

	public void setCaja(long caja) {
		this.caja = caja;
	}


	/**
	 * metodo que retorna el parametro de modifica presio
	 * returna Si en caso de que el usuario este autorizado para 
	 * modificar precios de los productos al momento de ralizar el pedido
	 * y No en caso contrario
	 * @return modificaPrecio
	 */
	public long getModificaPrecio() {
		return modificaPrecio;
	}
	
	public boolean isModificaPrecio()
	{
		if(modificaPrecio==1||modificaPrecio==2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isPrecio123()
	{
		if(modificaPrecio==3||modificaPrecio==2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isValue(long value)
	{
		if(value==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/**
	 * @param modificaPrecio
	 */
	public void setModificaPrecio(long modificaPrecio) {
		this.modificaPrecio = modificaPrecio;
	}


	/**
	 * metodo que asigna el nuevo valor de la direccion ip publica
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}


	/**
	 * metodo que retorna el valor de el primer segnento de la direccion ip
	 * @return ip1
	 */
	public String getIp1() {
		return ip1;
	}

	/**
	 * metodo que asigna el nuevo valor al primer segmento de la direcion ip publica
	 * @param ip1
	 */
	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	/**
	 * metodo que retorna el valor de el segundo segnento de la direccion ip
	 * @return ip2
	 */
	public String getIp2() {
		return ip2;
	}

	/**
	 * metodo que asigna el nuevo valor al segundo segmento de la direcion ip publica
	 * @param ip2
	 */
	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	/**
	 * metodo que retorna el valor de el tercer segnento de la direccion ip
	 * @return ip3
	 */
	public String getIp3() {
		return ip3;
	}

	/**
	 * metodo que asigna el nuevo valor al tercer segmento de la direcion ip publica
	 * @param ip3
	 */
	public void setIp3(String ip3) {
		this.ip3 = ip3;
	}

	/**
	 * metodo que retorna el valor de el cuarto segnento de la direccion ip
	 * @return ip4
	 */
	public String getIp4() {
		return ip4;
	}

	/**
	 * metodo que asigna el nuevo valor al cuarto segmento de la direcion ip publica
	 * @param ip4
	 */
	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}

	/**
	 * metodo que retorna el numero ruta o cedula de vendedor 
	 * @return ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * metodo que asigna el nuevo valor de la ruta asignada por omision
	 * a la aplicacion
	 * @param ruta
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * metodo que retorna el nombre del web service 
	 * @return ws
	 */
	public String getWs() {
		return ws;
	}
	/**
	 * metodo que asigna el nuevo nombre del web service 
	 * @param ws
	 */
	public void setWs(String ws) {
		this.ws = ws;
	}


	public String getFecha() {
		
		 final Calendar c = Calendar.getInstance();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 try
			 {
				 c.setTime(dateFormat.parse(fechaSys));
				 fecha=validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);
			 }
			 catch (Exception e) {
				// TODO: handle exception
			}
			
		return fecha;
		
	
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public long getBodegaPedidosOmision() {
		return bodegaPedidosOmision;
	}


	public void setBodegaPedidosOmision(long bodegaPedidosOmision) {
		this.bodegaPedidosOmision = bodegaPedidosOmision;
	}


	public long getBodegaFacturaOmision() {
		return bodegaFacturaOmision;
	}


	public void setBodegaFacturaOmision(long bodegaFacturaOmision) {
		this.bodegaFacturaOmision = bodegaFacturaOmision;
	}


	public long getAdministraInventario() {
		return administraInventario;
	}


	public void setAdministraInventario(long administraInventario) {
		this.administraInventario = administraInventario;
	}


	public long getConsultaArticuloEnLinea() {
		return consultaArticuloEnLinea;
	}


	public void setConsultaArticuloEnLinea(long consultaArticuloEnLinea) {
		this.consultaArticuloEnLinea = consultaArticuloEnLinea;
	}


	public long getRealizaFactura() {
		return realizaFactura;
	}


	public void setRealizaFactura(long realizaFactura) {
		this.realizaFactura = realizaFactura;
	}


	public long getRealizaPedidos() {
		return realizaPedidos;
	}


	public void setRealizaPedidos(long realizaPedidos) {
		this.realizaPedidos = realizaPedidos;
	}


	public long getBodegaAdmInvOmision() {
		return bodegaAdmInvOmision;
	}


	public void setBodegaAdmInvOmision(long bodegaAdmInvOmision) {
		this.bodegaAdmInvOmision = bodegaAdmInvOmision;
	}


	public long getUsaCatalogo() {
		return usaCatalogo;
	}


	public void setUsaCatalogo(long usaCatalogo) {
		this.usaCatalogo = usaCatalogo;
	}


	public long getModificaStock() {
		return modificaStock;
	}


	public void setModificaStock(long modificaStock) {
		this.modificaStock = modificaStock;
	}


	public long getUsaImpresoraZebra() {
		return usaImpresoraZebra;
	}


	public void setUsaImpresoraZebra(long usaImpresoraZebra) {
		this.usaImpresoraZebra = usaImpresoraZebra;
	}


	public String getMacAdd() {
		return macAdd;
	}


	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}


	public long getRealizaTranslados() {
		return realizaTranslados;
	}


	public void setRealizaTranslados(long realizaTranslados) {
		this.realizaTranslados = realizaTranslados;
	}


	public long getBodegaTransladosOmision() {
		return bodegaTransladosOmision;
	}


	public void setBodegaTransladosOmision(long bodegaTransladosOmision) {
		this.bodegaTransladosOmision = bodegaTransladosOmision;
	}	
	public long getConsultaZ() {
		return consultaZ;
	}
	public long getGeneraCierre() {
		return generaCierre;
	}
	public void setConsultaZ(long consultaZ) {
		this.consultaZ = consultaZ;
	}
	public void setGeneraCierre(long generaCierre) {
		this.generaCierre = generaCierre;
	}


	public long getUsaWSCash() {
		return usaWSCash;
	}


	public void setUsaWSCash(long usaWSCash) {
		this.usaWSCash = usaWSCash;
	}


	public long getRealizaPedidosMesa() {
		return realizaPedidosMesa;
	}


	public void setRealizaPedidosMesa(long realizaPedidosMesa) {
		this.realizaPedidosMesa = realizaPedidosMesa;
	}


	public long getUsaTodasLasCategorias() {
		return usaTodasLasCategorias;
	}
	public boolean getUsaCategorias() {
		if(usaTodasLasCategorias==0)
		{
			return true;
		}
		return false;
		
	}


	public void setUsaTodasLasCategorias(long usaTodasLasCategorias) {
		this.usaTodasLasCategorias = usaTodasLasCategorias;
	}


	public long getPermiteStocken0() {
		return permiteStocken0;
	}


	public void setPermiteStocken0(long permiteStocken0) {
		this.permiteStocken0 = permiteStocken0;
	}


	public long getPrecioLibre() {
		return precioLibre;
	}


	public void setPrecioLibre(long precioLibre) {
		this.precioLibre = precioLibre;
	}


	public long getFacturaOnLine() {
		return FacturaOnLine;
	}


	public void setFacturaOnLine(long facturaOnLine) {
		FacturaOnLine = facturaOnLine;
	}


	public String getRazonSocial() {
		return RazonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}


	public String getRepresentante() {
		return Representante;
	}


	public void setRepresentante(String representante) {
		Representante = representante;
	}


	public String getRegimenNit() {
		return RegimenNit;
	}


	public void setRegimenNit(String regimenNit) {
		RegimenNit = regimenNit;
	}


	public String getDireccionTel() {
		return DireccionTel;
	}


	public void setDireccionTel(String direccionTel) {
		DireccionTel = direccionTel;
	}


	public String getResDian() {
		return ResDian;
	}


	public void setResDian(String resDian) {
		ResDian = resDian;
	}


	public String getRango() {
		return Rango;
	}


	public void setRango(String rango) {
		Rango = rango;
	}


	public String getNombreVendedor() {
		return NombreVendedor;
	}


	public void setNombreVendedor(String nombreVendedor) {
		NombreVendedor = nombreVendedor;
	}
	
	
	public String getFechaSysSystem() {
		 final Calendar c = Calendar.getInstance();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 try
			 {
				 c.setTime(dateFormat.parse(fecha));
				 fechaSys=c.get(Calendar.YEAR)+"-"+validNumberDate((c.get(Calendar.MONTH)+1))+"-"+validNumberDate(c.get(Calendar.DAY_OF_MONTH));
			 }
			 catch (Exception e) {
				// TODO: handle exception
			}
			
		return fechaSys;
	}
	public String getFechaSysSystem2() {
		final Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			c.setTime(dateFormat.parse(fecha));
			fechaSys=c.get(Calendar.YEAR)+""+validNumberDate((c.get(Calendar.MONTH)+1))+""+validNumberDate(c.get(Calendar.DAY_OF_MONTH));
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return fechaSys;
	}



	public void setFechaSys(String fechaSys) { 
		this.fechaSys = fechaSys;
	}


	public String getFechaSys2System() {
		 final Calendar c = Calendar.getInstance();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 try
			 {
				 c.setTime(dateFormat.parse(fecha));
				 fechaSys2=c.get(Calendar.YEAR)+validNumberDate((c.get(Calendar.MONTH)+1))+validNumberDate(c.get(Calendar.DAY_OF_MONTH));
			 }
			 catch (Exception e) {
				// TODO: handle exception
			}
			
		return fechaSys2;
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


	public void setFechaSys2(String fechaSys2) {
		this.fechaSys2 = fechaSys2;
	}
	public String getFechaSys2() {
		return fechaSys2;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getPrefijo() {
		return Prefijo;
	}


	public void setPrefijo(String prefijo) {
		Prefijo = prefijo;
	}

	public long getUsaObservMasMenos() {
		return UsaObservMasMenos;
	}

	public void setUsaObservMasMenos(long usaObservMasMenos) {
		UsaObservMasMenos = usaObservMasMenos;
	}

	public long getDescuentoPedido() {
		return DescuentoPedido;
	}

	public void setDescuentoPedido(long descuentoPedido) {
		DescuentoPedido = descuentoPedido;
	}

	public long getImprimePedido() {
		return ImprimePedido;
	}

	public void setImprimePedido(long imprimePedido) {
		ImprimePedido = imprimePedido;
	}

	public long getDescuentaStockEnPedido() {
		return descuentaStockEnPedido;
	}

	public void setDescuentaStockEnPedido(long descuentaStockEnPedido) {
		this.descuentaStockEnPedido = descuentaStockEnPedido;
	}

	public long getConsultaCosto() {
		return ConsultaCosto;
	}

	public void setConsultaCosto(long consultaCosto) {
		ConsultaCosto = consultaCosto;
	}

	public String getFechaSys() {
		return fechaSys;
	}

	public long getUsaPrintEpson() {
		return usaPrintEpson;
	}

	public void setUsaPrintEpson(long usaPrintEpson) {
		this.usaPrintEpson = usaPrintEpson;
	}

	public String getMacAddEpson() {
		return macAddEpson;
	}

	public void setMacAddEpson(String macAddEpson) {
		this.macAddEpson = macAddEpson;
	}
	public long getUsaCantDecimal() {
		return usaCantDecimal;
	}

	public void setUsaCantDecimal(long usaCantDecimal) {
		this.usaCantDecimal = usaCantDecimal;
	}

	public long getUsaSelecMultipleArt() {
		return usaSelecMultipleArt;
	}

	public void setUsaSelecMultipleArt(long usaSelecMultipleArt) {
		this.usaSelecMultipleArt = usaSelecMultipleArt;
	}

	public long getPrecioMinimo() {
		return precioMinimo;
	}

	public void setPrecioMinimo(long precioMinimo) {
		this.precioMinimo = precioMinimo;
	}


	public long getUsaPrintBixolon() {
		return usaPrintBixolon;
	}

	public void setUsaPrintBixolon(long usaPrintBixolon) {
		this.usaPrintBixolon = usaPrintBixolon;
	}



	public String getMacAddBixolon() {
		return macAddBixolon;
	}

	public void setMacAddBixolon(String macAddBixolon) {
		this.macAddBixolon = macAddBixolon;
	}

	public long getUsaPrintDigitalPos() {
		return usaPrintDigitalPos;
	}

	public void setUsaPrintDigitalPos(long usaPrintDigitalPos) {
		this.usaPrintDigitalPos = usaPrintDigitalPos;
	}

	public String getMacAddDigitalPos() {
		return macAddDigitalPos;
	}

	public void setMacAddDigitalPos(String macAddDigitalPos) {
		this.macAddDigitalPos = macAddDigitalPos;
	}

	public long getWebid() {
		return Webid;
	}

	public long getCarteraOnLine() {
		return CarteraOnLine;
	}

	public long getControlaPrecioLibre() {
		return ControlaPrecioLibre;
	}

	public void setControlaPrecioLibre(long controlaPrecioLibre) {
		ControlaPrecioLibre = controlaPrecioLibre;
	}

	public void setCarteraOnLine(long carteraOnLine) {
		CarteraOnLine = carteraOnLine;
	}

	public long getSelectDocumentoPedido() {
		return SelectDocumentoPedido;
	}

	public void setSelectDocumentoPedido(long selectDocumentoPedido) {
		SelectDocumentoPedido = selectDocumentoPedido;
	}

	public long getRealizaAlistamiento() {
		return RealizaAlistamiento;
	}

	public void setRealizaAlistamiento(long realizaAlistamiento) {
		RealizaAlistamiento = realizaAlistamiento;
	}

	public long getSelectFormaPagoPedido() {
		return SelectFormaPagoPedido;
	}

	public void setSelectFormaPagoPedido(long selectFormaPagoPedido) {
		SelectFormaPagoPedido = selectFormaPagoPedido;
	}

	public long getUsaPrestamos() {
		return UsaPrestamos;
	}

	public void setUsaPrestamos(long usaPrestamos) {
		UsaPrestamos = usaPrestamos;
	}

	public long getRealizaRemision() {
		return realizaRemision;
	}

	public void setRealizaRemision(long realizaRemision) {
		this.realizaRemision = realizaRemision;
	}

	public long getBodegaRemisionOmision() {
		return bodegaRemisionOmision;
	}

	public void setBodegaRemisionOmision(long bodegaRemisionOmision) {
		this.bodegaRemisionOmision = bodegaRemisionOmision;
	}

	public void setModificaValorTotal(long modificaValorTotal) {
		ModificaValorTotal = modificaValorTotal;
	}

	public long getModificaValorTotal() {
		return ModificaValorTotal;
	}

	public long getWebid2() {
		return Webid;
	}

	public void setWebid(long webid) {
		Webid = webid;
	}

	public String getWebidText()
	{
		if (Webid==0)
		{
			return  "";
		}
		return  ""+Webid;
	}
	public long getUsaTipoPedido() {
		return usaTipoPedido;
	}

	public void setUsaTipoPedido(long usaTipoPedido) {
		this.usaTipoPedido = usaTipoPedido;
	}
	public long getPermiteStocken0EnPedido() {
		return permiteStocken0EnPedido;
	}

	public void setPermiteStocken0EnPedido(long permiteStocken0EnPedido) {
		this.permiteStocken0EnPedido = permiteStocken0EnPedido;
	}

	public long getMuestraEstablecimientoCliente() {
		return MuestraEstablecimientoCliente;
	}

	public void setMuestraEstablecimientoCliente(long muestraEstablecimientoCliente) {
		MuestraEstablecimientoCliente = muestraEstablecimientoCliente;
	}

	public long getUsaStarlapWS() {
		return UsaStarlapWS;
	}

	public void setUsaStarlapWS(long usaStarlapWS) {
		UsaStarlapWS = usaStarlapWS;
	}


	public long getEnviaUbicacionPedido() {
		return EnviaUbicacionPedido;
	}

	public void setEnviaUbicacionPedido(long enviaUbicacionPedido) {
		EnviaUbicacionPedido = enviaUbicacionPedido;
	}
}
