package com.principal.mundo;

import com.principal.mundo.sysws.ClienteSucursal;
import com.principal.mundo.sysws.ItemPedido;
import com.principal.mundo.sysws.Observacion;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * clase en la que se encuentra la informacion que describe al cliente
 * @author Javier
 *
 */
public class Cliente 
{
	/**
	 * identificador asignado al cliente
	 */
	public long idCliente;
	/**
	 * cedula para identificar al vendedor al que pertenece
	 */
	public String cedulaVendedor;
	/**
	 * nombre del cliente
	 */
	public String nombre;
	/**
	 * representante legal del cliente
	 */
	public String representante;
	/**
	 * nit o cedula del cliente
	 */
	public String nit;
	/**
	 * direccion de negocio del cliente
	 */
	public String direccion;
	/**
	 * telefono del cliente
	 */
	public String telefono;
	/**
	 * municipio al que pertenece el cliente
	 */
	public String municipio;
	/**
	 * limite de credito al que puede llegar el cliente
	 */
	public long limiteCredito;
	/**
	 * barrio en el que se encuentra el negocio del cliente
	 */
	public String barrio;
	/**
	 * tipo de canal del negocio del cliente puede ser "tienda, supermercado, caseta, etc"
	 */
	public String tipoCanal;
	/**
	 * identificador del dia de visita al cliente para el dia Lunes
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String lun;
	/**
	 * identificador del dia de visita al cliente para el dia Martes
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String mar;
	/**
	 * identificador del dia de visita al cliente para el dia Miercoles
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String mie;
	/**
	 * identificador del dia de visita al cliente para el dia Jueves
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String jue;
	/**
	 * identificador del dia de visita al cliente para el dia Viernes
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String vie;
	/**
	 * identificador del dia de visita al cliente para el dia Sabado
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String sab;
	/**
	 * identificador del dia de visita al cliente para el dia Domingo
	 * "true" si esta asignada la visita para ese dia
	 * "false"si no esta asignada la visita para ese dia
	 */
	public String dom;
	/**
	 * Orden de visita del cliente
	 */
	public long ordenVisita;
	/**
	  * Orden de visita del cliente para el dia Lunes
	 */
	public long OrdenVisitaLUN;
	/**
	 * Orden de visita del cliente para el dia Martes
	 */
	public long OrdenVisitaMAR;
	/**
	 * Orden de visita del cliente para el dia Miercoles
	 */
	public long OrdenVisitaMIE;
	/**
	 * Orden de visita del cliente para el dia Jueves
	 */
	public long OrdenVisitaJUE;
	/**
	 * Orden de visita del cliente para el dia Viernes
	 */
	public long OrdenVisitaVIE;
	/**
	 * Orden de visita del cliente para el dia Sabado
	 */
	public long OrdenVisitaSAB;
	/**
	 * Orden de visita del cliente para el dia Domingo
	 */
	public long OrdenVisitaDOM;
	/**
	 * fecha del ultimo pedido realizado al cliente
	 */
	public String fechaUltimoPedido;
	/**
	 * estado del cliente en el sistema 
	 */
	public long activo;
	/**
	 * fecha ultima visita realizada al cliente
	 */
	public String fechaUltimaVisita;
	/**
	 * fecha ultima venta realizada al cliente
	 */
	public String fechaUltimaVenta;
	/**
	 * Presio asignado por defecto al cliente
	 */
	public String PrecioDefecto;
	/**
	 * identificador que determina si el cliente ya esta localizado en el sistema
	 */
	public String ubicado;
	
	
	public long deudaActual;

	public String deudaAntFac;

	public String deudaTotal;

	public String MotivoUltimaVisita;


	public String fechaUltimoPago;

	public String DiasGracia;

	private String xmlClientesSucursales;

	private ArrayList<ClienteSucursal> listaClienteSucursal;

	public long idClienteSucursal;


	public String TipoPersona;
	public String PrimerApellido;
	public String SegundoApellido;
	public String PrimerNombre;
	public String SegundoNombre;
	public String RazonSocial;



	/**
	 *metodo que se encarga de asignar valores predeterminados a los atributos de la clase
	 */
	public Cliente()
	{
		idCliente=0;
		cedulaVendedor="";
		nombre="";
		representante="";
		nit="";
		direccion="";
		telefono="";
		municipio="";
		limiteCredito=0;
		barrio="";
		tipoCanal="";
		lun="";
		mar="";
		mie="";
		jue="";
		vie="";
		sab="";
		dom="";
		ordenVisita=0;
		OrdenVisitaLUN=0;
		OrdenVisitaMAR=0;
		OrdenVisitaMIE=0;
		OrdenVisitaJUE=0;
		OrdenVisitaVIE=0;
		OrdenVisitaSAB=0;
		OrdenVisitaDOM=0;		
		fechaUltimoPedido="";
		fechaUltimaVisita="";
		activo=0;
		PrecioDefecto="";
		ubicado="";
		deudaAntFac="0";
		deudaTotal="";

		TipoPersona="";
		PrimerApellido="";
		SegundoApellido="";
		PrimerNombre="";
		SegundoNombre="";
		RazonSocial="";

	}

	public String getDiasGracia() {
		return DiasGracia;
	}
	public long getDiasGraciaCliente() {
		return Long.parseLong(DiasGracia);
	}

	public void setDiasGracia(String diasGracia) {
		DiasGracia = diasGracia;
	}

	/**
	 * metodo que se encarga de asignar valores predeterminados a los atributos de la clase
	 * @param Prueba
	 */
	public Cliente(String Prueba)
	{
		idCliente=-1;
		cedulaVendedor="1006";
		nombre="Pedido de Depuraci�n";
		representante="ninguno";
		nit="12213";
		direccion="carrera 1";
		telefono="121312";
		municipio="sogamoso";
		limiteCredito=0;
		barrio="juan";
		tipoCanal="tienda";
		lun="false";
		mar="false";
		mie="false";
		jue="false";
		vie="false";
		sab="false";
		dom="false";
		ordenVisita=0;
		OrdenVisitaLUN=0;
		OrdenVisitaMAR=0;
		OrdenVisitaMIE=0;
		OrdenVisitaJUE=0;
		OrdenVisitaVIE=0;
		OrdenVisitaSAB=0;
		OrdenVisitaDOM=0;		
		fechaUltimoPedido="";
		fechaUltimaVisita="";
		activo=0;
		PrecioDefecto="";
		ubicado="";
	}

	
	/**
	 * metodo que retorna el estado del cliente en el sistema
	 * @return activo
	 */
	public long getActivo() {
		return activo;
	}


	/**
	 * metodo que retorna el precio por omision al momento de facturarle al cliente
	 * @return PrecioDefecto
	 */
	public String getPrecioDefecto() {
		return PrecioDefecto;
	}
	public String getTextoPrecioDefecto() {
		return "Precio "+PrecioDefecto;
	}

	/**
	 * metodo que se encarga de asignar el nuevo precio por omision de facturacion al cliente
	 * 	 * @param precioDefecto
	 */
	public void setPrecioDefecto(String precioDefecto) {
		PrecioDefecto = precioDefecto;
	}

	/**
	 * metodo que retorna la ultima fecha de visita realizada al cliente
	 * @return
	 */
	public String getFechaUltimaVisita() {
		return fechaUltimaVisita;
	}

	/**
	 * metodo que asigna la ultima fecha de visita al cliente
	 * @param fechaUltimaVisita
	 */
	public void setFechaUltimaVisita(String fechaUltimaVisita) {
		this.fechaUltimaVisita = fechaUltimaVisita;
	}

	/**
	 * metodo que asigna el nuevo estado del cliente en el sistema
	 * @param activo
	 */
	public void setActivo(long activo) {
		this.activo = activo;
	}


	/**
	 * metodo que retorna el orden de visita al cliente para el dia Lunes
	 * @returnOrdenVisitaLUN
	 */
	public long getOrdenVisitaLUN() {
		return OrdenVisitaLUN;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Lunes
	 * @param ordenVisitaLUN
	 */
	public void setOrdenVisitaLUN(long ordenVisitaLUN) {
		OrdenVisitaLUN = ordenVisitaLUN;
	}

	/**
	 *  metodo que retorna el orden de visita al cliente para el dia Martes
	 * @return OrdenVisitaMAR
	 */
	public long getOrdenVisitaMAR() {
		return OrdenVisitaMAR;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Martes
	 * @param ordenVisitaMAR
	 */
	public void setOrdenVisitaMAR(long ordenVisitaMAR) {
		OrdenVisitaMAR = ordenVisitaMAR;
	}

	/**
	 * * metodo que retorna el orden de visita al cliente para el dia Miercoles
	 * @return OrdenVisitaMIE
	 */
	public long getOrdenVisitaMIE() {
		return OrdenVisitaMIE;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Miercoles
	 * @param ordenVisitaMIE
	 */
	public void setOrdenVisitaMIE(long ordenVisitaMIE) {
		OrdenVisitaMIE = ordenVisitaMIE;
	}

	/**
	 * * metodo que retorna el orden de visita al cliente para el dia Jueves
	 * @return OrdenVisitaJUE
	 */
	public long getOrdenVisitaJUE() {
		return OrdenVisitaJUE;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Jueves
	 * @param ordenVisitaJUE
	 */
	public void setOrdenVisitaJUE(long ordenVisitaJUE) {
		OrdenVisitaJUE = ordenVisitaJUE;
	}

	/**
	 * * metodo que retorna el orden de visita al cliente para el dia Viernes
	 * @return OrdenVisitaVIE
	 */
	public long getOrdenVisitaVIE() {
		return OrdenVisitaVIE;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Viernes
	 * @param ordenVisitaVIE
	 */
	public void setOrdenVisitaVIE(long ordenVisitaVIE) {
		OrdenVisitaVIE = ordenVisitaVIE;
	}

	/**
	 * * metodo que retorna el orden de visita al cliente para el dia Sabado
	 * @return OrdenVisitaSAB
	 */
	public long getOrdenVisitaSAB() {
		return OrdenVisitaSAB;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Sabado
	 * @param ordenVisitaSAB
	 */
	public void setOrdenVisitaSAB(long ordenVisitaSAB) {
		OrdenVisitaSAB = ordenVisitaSAB;
	}

	/**
	 * * metodo que retorna el orden de visita al cliente para el dia Domingo
	 * @return OrdenVisitaDOM
	 */
	public long getOrdenVisitaDOM() {
		return OrdenVisitaDOM;
	}

	/**
	 * metodo que asigna  al cliente el nuevo orden de visita para el dia Domingo
	 * @param ordenVisitaDOM
	 */
	public void setOrdenVisitaDOM(long ordenVisitaDOM) {
		OrdenVisitaDOM = ordenVisitaDOM;
	}

	/**
	 * metodo que retorna la fecha del ultimo pedido realizado al cliente
	 * @return
	 */
	public String getFechaUltimoPedido() {
		return fechaUltimoPedido;
	}

	/**
	 * metodo que asigna la nueva fecha del ultimo pedido realizado al cliente
	 * @param fechaUltimoPedido
	 */
	public void setFechaUltimoPedido(String fechaUltimoPedido) {
		this.fechaUltimoPedido = fechaUltimoPedido;
	}

	/**
	 * metodo que retorna el nit o cedula del cliente
	 * @return nit
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * metodo que asigna el nuevo nit o cedula del cliente
	 * @param nit
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}

	/**
	 * metodo que retorna el identificador del cliente
	 * @return idcliente
	 */
	public long getIdCliente() {
		return idCliente;
	}

	/**
	 * metodo que asigna el nuevo valor del identificador del cliente
	 * @param idCliente
	 */
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * metodo que retorna la cedula del vendedor al que pertenece el cliente
	 * @return cedulaVendedor
	 */
	public String getCedulaVendedor() {
		return cedulaVendedor;
	}

	/**
	 * metodo que asigna el nuevo valor de la cedula del vendedor al que pertenece el cliente
	 * @param cedulaVendedor
	 */
	public void setCedulaVendedor(String cedulaVendedor) {
		this.cedulaVendedor = cedulaVendedor;
	}

	/**
	 * metodo que retorna el nombre del cliente
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo que asigna el nuevo nombre del cliente
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * metodo retorna el representante legal del cliente
	 * @return representante
	 */
	public String getRepresentante() {
		if(representante.equals("anyType{}"))
		{
			representante="--";
		}	
		return representante;
	}

	/**
	 * metodo que asigna el nuevo representante legal del cliente
	 * @param representante
	 */
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	/**
	 * metodo que retorna la direccion del negocio del cliente
	 * @return direccion
	 */
	public String getDireccion() {
		if(direccion.equals("anyType{}"))
		{
			direccion="--";
		}	
		return direccion;
	}

	/**
	 * metodo que asigna la nueva direccion del negocio del cliente
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * metodo que retorna el telefono del cliente
	 * @return telefono
	 */
	public String getTelefono() {
		if(telefono.equals("anyType{}"))
		{
			telefono="--";
		}
		return telefono;
	}

	/**
	 * metodo que asigna el nuevo telefono del cliente
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * metodo que retorna el municipio al que pertenece el cliente
	 * @return municipio
	 */
	public String getMunicipio() {
		if(municipio.equals("anyType{}"))
		{
			municipio="--";
		}	
		return municipio;
	}

	/**
	 * metodo que asigna el nuevo municipio al que pertenece el cliente
	 * @param municipio
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * metodo que retorna el limite de credito del cliente
	 * @return limiteCredito
	 */
	public long getLimiteCredito() {
		return limiteCredito;
	}

	/**
	 * metodo que asigna el nuevo limite de credito del cliente
	 * @param limiteCredito
	 */
	public void setLimiteCredito(long limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	/**
	 * metodo que retorna el barrio al que pertenece negocio del cliente
	 * @return
	 */
	public String getBarrio() {
		if(barrio.equals("anyType{}"))
		{
			barrio="--";
		}	
		return barrio;
	}

	/**
	 * metodo que asigna el nuevo barrio al que pertenece el negocio del cliente
	 * @param barrio
	 */
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	/**
	 * metodo que retorna el tipo de canal del negocio del cliente
	 * @return tipoCanal
	 */
	public String getTipoCanal() {
		if(tipoCanal.equals("anyType{}"))
		{
			tipoCanal="--";
		}	
		return tipoCanal;
	}

	/**
	 * metodo que asigna el nuevo tipo de canal del negocio del cliente
	 * @param tipoCanal
	 */
	public void setTipoCanal(String tipoCanal) {
		this.tipoCanal = tipoCanal;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Lunes
	 * @return lun
	 */
	public String getLun() {
		return lun;
	}

	/**
	 * metodo que asigna el nuevo identificador de visita para el dia Lunes
	 * @param lun
	 */
	public void setLun(String lun) {
		
		this.lun = lun;
	}
	public String getValorDia(String text)
	{
		if(text.equals("0"))
		{
			text="false";
		}
		else if(text.equals("1"))
		{
			text="true";
		}		
		return text;
		
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Martes
	 * @return lun
	 */
	public String getMar() {
		return mar;
	}

	/**
	 * metodo que asigna el nuevo identificador de visita para el dia Martes
	 * @param mar
	 */
	public void setMar(String mar) {
		this.mar =mar;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Miercoles
	 * @return lun
	 */
	public String getMie() {
		return mie;
	}

	/**
	 *  * metodo que asigna el nuevo identificador de visita para el dia Miercoles
	 * @param mie
	 */
	public void setMie(String mie) {
		this.mie = mie;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Jueves
	 * @return lun
	 */
	public String getJue() {
		return jue;
	}

	/**
	 *  * metodo que asigna el nuevo identificador de visita para el dia Jueves
	 * @param jue
	 */
	public void setJue(String jue) {
		this.jue = jue;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Viernes
	 * @return lun
	 */
	public String getVie() {
		return vie;
	}

	/**
	 *  * metodo que asigna el nuevo identificador de visita para el dia Viernes
	 * @param vie
	 */
	public void setVie(String vie) {
		this.vie = vie;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Sabado
	 * @return lun
	 */
	public String getSab() {
		return sab;
	}

	/**
	 *  * metodo que asigna el nuevo identificador de visita para el dia Sabado
	 * @param sab
	 */
	public void setSab(String sab) {
		this.sab = sab;
	}

	/**
	 * metodo que retorna el identificador de visita para el dia Domingo
	 * @return lun
	 */
	public String getDom() {
		return dom;
	}

	/**
	 *  * metodo que asigna el nuevo identificador de visita para el dia Domingo
	 * @param dom
	 */
	public void setDom(String dom) {
		this.dom = dom;
	}

	/**
	 * metodo que retorna el orden de visita del cliente
	 * @return ordenVisita
	 */
	public long getOrdenVisita() {
		return ordenVisita;
	}

	/**
	 * metodo que asigna el nuevo orden de visita del cliente
	 * @param ordenVisita
	 */
	public void setOrdenVisita(long ordenVisita) {
		this.ordenVisita = ordenVisita;
	}

	/**
	 * metodo que retorna el identificador para la ubicacion del cliente
	 * @return ubicado
	 */
	public String getUbicado() {
		return ubicado;
	}

	/**
	 * metodo que retorna el nuevo identificador para la localizacion del cliente
	 * @param ubicado
	 */
	public void setUbicado(String ubicado) {
		this.ubicado = ubicado;
	}

	public String getFechaUltimaVenta() {
		return fechaUltimaVenta;
	}

	public void setFechaUltimaVenta(String fechaUltimaVenta) {
		this.fechaUltimaVenta = fechaUltimaVenta;
	}

	public long getDeudaActual() {
		return deudaActual;
	}

	public void setDeudaActual(long deudaActual) {
		this.deudaActual = deudaActual;
	}

	public String getDeudaAntFac() {
		return deudaAntFac;
	}

	public void setDeudaAntFac(String deudaAntFac) {
		this.deudaAntFac = deudaAntFac;
	}

	public String getDeudaTotal() {
		return deudaTotal;
	}

	public void setDeudaTotal(String deudaTotal) {
		this.deudaTotal = deudaTotal;
	}




	public String getFechaUltimoPago() {
		return fechaUltimoPago;
	}

	public void setFechaUltimoPago(String fechaUltimoPago) {
		this.fechaUltimoPago = fechaUltimoPago;
	}

	public int getDias()
	{
		int dia=0;
		String fecha="";
		try {

		Date fechaActual=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		fecha = sdf.format(fechaActual);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date fi = df.parse(fechaUltimaVenta);
			Date ff = df.parse(fecha);
			dia=(int) ((ff.getTime()-fi.getTime())/86400000);
		}
		catch (Exception e)
		{
        return 0;
		}
		return dia;
	}

	public String getMotivoUltimaVisita() {
		return MotivoUltimaVisita;
	}

	public void setMotivoUltimaVisita(String motivoUltimaVisita) {
		MotivoUltimaVisita = motivoUltimaVisita;
	}

	public String getXmlClientesSucursales() {
		return xmlClientesSucursales;
	}

	public void setXmlClientesSucursales(String xmlClientesSucursales) {
		this.xmlClientesSucursales = xmlClientesSucursales;
	}

	public ArrayList<ClienteSucursal> getListaClienteSucursal() {
		return listaClienteSucursal;
	}

	public void setListaClienteSucursal(ArrayList<ClienteSucursal> listaClienteSucursal) {
		this.listaClienteSucursal = listaClienteSucursal;
	}


	public  void  getCargarSucursales( )
	{
		if(!xmlClientesSucursales.equals(""))
		{
			listaClienteSucursal = new ArrayList<ClienteSucursal>();
			try
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlClientesSucursales));
				Document doc = db.parse(is);
				NodeList nodes = doc.getElementsByTagName("Sucursal");
				// iterate the employees
				for (int i = 0; i < nodes.getLength(); i++) {
					Element element = (Element) nodes.item(i);

					ClienteSucursal clienteSucursal=new ClienteSucursal();
					for (int j = 0; j < clienteSucursal.getPropertyCount() ; j++) {


							NodeList name = element.getElementsByTagName(clienteSucursal.getPropertyName(j));
							Element line = (Element) name.item(0);
						clienteSucursal.setProperty(j,getCharacterDataFromElement(line));

					}
					listaClienteSucursal.add(clienteSucursal);
				}

			}
			catch(Exception e)
			{
				listaClienteSucursal=null;
			}
		}

	}
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

	public long getIdClienteSucursal() {
		return idClienteSucursal;
	}

	public void setIdClienteSucursal(long idClienteSucursal) {
		this.idClienteSucursal = idClienteSucursal;
	}

	public String getTipoPersona() {
		return TipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		TipoPersona = tipoPersona;
	}

	public String getPrimerApellido() {
		return PrimerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		PrimerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return SegundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		SegundoApellido = segundoApellido;
	}

	public String getPrimerNombre() {
		return PrimerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		PrimerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return SegundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		SegundoNombre = segundoNombre;
	}

	public String getRazonSocial() {
		return RazonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
}
