package com.principal.mundo.sysws;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
/**
 * Clase en la que se describe el usuario que sera enviado al sistema de georeferenciacion
 * @author Javier
 *
 */
/**
 * @author user
 *
 */
public class Cliente extends Ubicacion implements KvmSerializable
{
	/**
	 * identificador del cliente
	 */
	private String idCliente;
	/**
	 * nombre del cliente
	 */
	private String nombreCliente;
	/**
	 * direcccion del cliente
	 */
	private String direccion;
	/**
	 * telefono del cliente
	 */
	private String telefono;
	/**
	 * barrio en el que se encuentra el negocio del cliente
	 */
	private String barrio;
	/**
	 * ruta a la que pertenece el cliente
	 */
	private String idRuta;
	/**
	 * latitud de ubicacion del cliente
	 */
	private String latitud;
	/**
	 * longitud de ubicacion del cliente
	 */
	private String longitud;
	/**
	 * altitud de ubicacion del cliente
	 */
	private String altitud;	
	/**
	 * nombre del negocio del cliente
	 */
	private String nombreNegocio;
	/**
	 * cedula o nit del cliente
	 */
	private String cedulaNit;
	/**
	 * municipio al que pertenece el cliente
	 */
	private String municipio;
	/**
	 * limite de credito del cliente
	 */
	private long limiteCredito;
	/**
	 * tipo del canal del cliente
	 */
	private String tipoCanal;
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
	public long ordenVisitaLUN;
	/**
	 * Orden de visita del cliente para el dia Martes
	 */
	public long ordenVisitaMAR;
	/**
	 * Orden de visita del cliente para el dia Miercoles
	 */
	public long ordenVisitaMIE;
	/**
	 * Orden de visita del cliente para el dia Jueves
	 */
	public long ordenVisitaJUE;
	/**
	 * Orden de visita del cliente para el dia Viernes
	 */
	public long ordenVisitaVIE;
	/**
	 * Orden de visita del cliente para el dia Sabado
	 */
	public long ordenVisitaSAB;
	/**
	 * Orden de visita del cliente para el dia Domingo
	 */
	public long ordenVisitaDOM;
	/**
	 * fecha del ultimo pedido realizado al cliente
	 */
	private String fechaUltimoPedido;
	/**
	 * estado del cliente en el sistema
	 */
	private long activo;
	/**
	 * observacion de la visita realizada al cliente
	 */
	private String observacionVisita;
	/**
	 * fecha de ultima visita realizada al cliente
	 */
	private String fechaUltimaVisita;
	/**
	 * fecha de ultima visita realizada al cliente
	 */
	private String fechaUltimaVenta;
	/**
	 * resultado de la ultima visita realizada
	 */
	private String efectivo;	
	/**
	 * identificador de la ubicacion del cliente
	 */
	private int idUbicacion;
	/**
	 * consecutivo asignado al cliente
	 */
	private int consecutivo;
	
//	private String PrecioDefecto;
	
	/**
	 * metodo que asigna valores por omision a los atributos de la clase
	 */
	public Cliente() {
		idCliente="";
		nombreCliente="";
		direccion="";
		telefono="";
		barrio="";
		idRuta="";
		latitud="";
		longitud="";
		altitud="";	
		 nombreNegocio="";
		 cedulaNit="";
		 municipio="";
		 tipoCanal="";
		 lun="";
		 mar="";
		 mie="";
		 jue="";
		 vie="";
		 sab="";
		 dom="";
		 fechaUltimoPedido="";
		 fechaUltimaVisita="";
		 limiteCredito=0;
		 ordenVisitaLUN=0;
		 ordenVisitaMAR=0;
		 ordenVisitaMIE=0;
		 ordenVisitaJUE=0;
		 ordenVisitaVIE=0;
		 ordenVisitaSAB=0;
		 ordenVisitaDOM=0;
		 activo=0;
		 observacionVisita="";
		 fechaUltimaVisita="";
		 efectivo="";		 
		 idUbicacion=0;
		 consecutivo=0;
//		 PrecioDefecto="";
				
	}
	/**
	 * metodo que asigna valores de prueba a los atributos de la clase
	 * @param pru
	 */
	public Cliente(String pru) {
		idCliente="123432";
		nombreCliente="juancarlos";
		direccion="carrera 4";
		telefono="323342";
		barrio="san juan";
		idRuta="1002";
		latitud="022222";
		longitud="033333";
		altitud="200";	
		 nombreNegocio="doña";
		 cedulaNit="123";
		 municipio="paipa";
		 tipoCanal="tienda";
		 lun="true";
		 mar="true";
		 mie="false";
		 jue="false";
		 vie="true";
		 sab="false";
		 dom="true";
		 fechaUltimoPedido="0";
		 limiteCredito=0;
		 ordenVisitaLUN=0;
		 ordenVisitaMAR=0;
		 ordenVisitaMIE=0;
		 ordenVisitaJUE=0;
		 ordenVisitaVIE=0;
		 ordenVisitaSAB=0;
		 ordenVisitaDOM=0;
		 activo=0;
		 observacionVisita="Ninguna";
		 fechaUltimaVisita="0";
		 efectivo="";
		 idUbicacion=0;
		 consecutivo=0;
//		 PrecioDefecto="";
				
	}
	
	/**
	 * metodo que asigna valores a los atributos de la clase
	 * @param idRuta
	 * @param idCliente
	 * @param nombreCliente
	 * @param direccion
	 * @param telefono
	 * @param barrio
	 * @param idUbicacion
	 * @param latitud
	 * @param longitud
	 * @param altitud
	 * @param fecha
	 * @param hora
	 * @param consecutivo
	 */
	public Cliente(String idRuta,String idCliente, String nombreCliente, String direccion, String telefono, String barrio, int idUbicacion,String latitud, String longitud, String altitud, String fecha,String hora,int consecutivo)
	{	
		super(idUbicacion,latitud,longitud,altitud,fecha,hora,consecutivo);
		this.idCliente=idCliente;
		this.nombreCliente=nombreCliente;
		this.direccion=direccion;
		this.telefono=telefono;
		this.barrio=barrio;
		this.idRuta=idRuta;
		this.latitud=latitud;
		this.longitud=longitud;
		this.altitud=altitud;
	}
	
	
	/**
	 * metodo que retorna estado de la ultima visita relaizada al cliente
	 * @return
	 */
	public String getEfectivo() {
		return efectivo;
	}
	/**
	 * metodo que asigna el nuevo valor de efectivo del vliente
	 * @param efectivo
	 */
	public void setEfectivo(String efectivo) {
		this.efectivo = efectivo;
	}
	
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getIdUbicacion()
	 */
	public int getIdUbicacion() {
		return idUbicacion;
	}
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#setIdUbicacion(int)
	 */
	public void setIdUbicacion(int idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getConsecutivo()
	 */
	public int getConsecutivo() {
		return consecutivo;
	}
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#setConsecutivo(int)
	 */
	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}
	/**
	 * retorna la ultima fecha de visita del cliente
	 * @return
	 */
	public String getFechaUltimaVisita() {
		return fechaUltimaVisita;
	}
	/**
	 * asigna la fecha de ultima visita al cliente
	 * @param fechaUltimaVisita
	 */
	public void setFechaUltimaVisita(String fechaUltimaVisita) {
		this.fechaUltimaVisita = fechaUltimaVisita;
	}
	/**
	 * retorna la observacion de la visita del cliente
	 * @return observacionVisita
	 */
	public String getObservacionVisita() {
		return observacionVisita;
	}
	/**
	 * @param observacionVisita
	 */
	public void setObservacionVisita(String observacionVisita) {
		this.observacionVisita = observacionVisita;
	}
	/**
	 * retorna el nombre del cliente
	 * @return nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}

	/**
	 * asigna el nuevo nombre del cliente
	 * @param nombreCliente
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	/**
	 * retorna el identificador del cliente
	 * @return
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * asigna el nuevo identificador del cliente
	 * @param idCliente
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	/**
	 * retorna la direccion del cliente
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * asigna el valor de la nueva direccion del cliente
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * retorna el telefono del cliente
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * asigna el nuevo valor del telefono del cliente
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * retorna el barrio del cliente
	 * @return
	 */
	public String getBarrio() {
		return barrio;
	}

	/**
	 * asigna el nuevo valor del barrio del cliente
	 * @param barrio
	 */
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	/**
	 * retorna el identificador de la ruta a la que pertenece
	 * @return idruta
	 */
	public String getIdRuta() {
		return idRuta;
	}

	/**
	 * asigna el nuevo valor del identificador de la ruta a la que pertenece
	 * @param idRuta
	 */
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getLatitud()
	 */
	public String getLatitud() {
		return latitud;
	}

	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#setLatitud(java.lang.String)
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getLongitud()
	 */
	public String getLongitud() {
		return longitud;
	}

	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#setLongitud(java.lang.String)
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getAltitud()
	 */
	public String getAltitud() {
		return altitud;
	}
	

	/**
	 * metodo que retorna el nombre del negocio del clietne
	 * @return nombreNegocio
	 */
	public String getNombreNegocio() {
		return nombreNegocio;
	}

	/**
	 * asignar el nuevo nombre del negocio del cliente
	 * @param nombreNegocio
	 */
	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}

	/**
	 * metodo que retorna la cedula o nit del cliente
	 * @return cedulaNit
	 */
	public String getCedulaNit() {
		return cedulaNit;
	}

	/**
	 * metodo que asigna el valor del nuevo nit o cedula del cliente
	 * @param cedulaNit
	 */
	public void setCedulaNit(String cedulaNit) {
		this.cedulaNit = cedulaNit;
	}

	/**
	 * metodo que retorna el municipio del cliente
	 * @return municipio
	 */
	public String getMunicipio() {
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
	 * @return
	 */
	public long getOrdenVisitaLUN() {
		return ordenVisitaLUN;
	}

	/**
	 * @param ordenVisitaLUN
	 */
	public void setOrdenVisitaLUN(long ordenVisitaLUN) {
		this.ordenVisitaLUN = ordenVisitaLUN;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaMAR() {
		return ordenVisitaMAR;
	}

	/**
	 * @param ordenVisitaMAR
	 */
	public void setOrdenVisitaMAR(long ordenVisitaMAR) {
		this.ordenVisitaMAR = ordenVisitaMAR;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaMIE() {
		return ordenVisitaMIE;
	}

	/**
	 * @param ordenVisitaMIE
	 */
	public void setOrdenVisitaMIE(long ordenVisitaMIE) {
		this.ordenVisitaMIE = ordenVisitaMIE;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaJUE() {
		return ordenVisitaJUE;
	}

	/**
	 * @param ordenVisitaJUE
	 */
	public void setOrdenVisitaJUE(long ordenVisitaJUE) {
		this.ordenVisitaJUE = ordenVisitaJUE;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaVIE() {
		return ordenVisitaVIE;
	}

	/**
	 * @param ordenVisitaVIE
	 */
	public void setOrdenVisitaVIE(long ordenVisitaVIE) {
		this.ordenVisitaVIE = ordenVisitaVIE;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaSAB() {
		return ordenVisitaSAB;
	}

	/**
	 * @param ordenVisitaSAB
	 */
	public void setOrdenVisitaSAB(long ordenVisitaSAB) {
		this.ordenVisitaSAB = ordenVisitaSAB;
	}

	/**
	 * @return
	 */
	public long getOrdenVisitaDOM() {
		return ordenVisitaDOM;
	}

	/**
	 * @param ordenVisitaDOM
	 */
	public void setOrdenVisitaDOM(long ordenVisitaDOM) {
		this.ordenVisitaDOM = ordenVisitaDOM;
	}

	/**
	 * @return
	 */
	public String getFechaUltimoPedido() {
		return fechaUltimoPedido;
	}

	/**
	 * @param fechaUltimoPedido
	 */
	public void setFechaUltimoPedido(String fechaUltimoPedido) {
		this.fechaUltimoPedido = fechaUltimoPedido;
	}
	/**
	 * @return
	 */
	public long getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 */
	public void setActivo(long activo) {
		this.activo = activo;
	}

	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#setAltitud(java.lang.String)
	 */
	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}
	public String getFechaUltimaVenta() {
		return fechaUltimaVenta;
	}

	public void setFechaUltimaVenta(String fechaUltimaVenta) {
		this.fechaUltimaVenta = fechaUltimaVenta;
	}
		
//	public String getPrecioDefecto() {
//		return PrecioDefecto;
//	}
//	public void setPrecioDefecto(String precioDefecto) {
//		PrecioDefecto = precioDefecto;
//	}
	public void setOrdenVisita(long ordenVisita) {
		this.ordenVisita = ordenVisita;
	}
	/**
	 * @return
	 */
	public Ruta getRuta()
	{
		Ruta ruta =new Ruta();
		ruta.setIdRuta(idRuta);
		ruta.setLatitud(this.getLatitud());
		ruta.setLongitud(this.getLongitud());
		ruta.setAltitud(this.getAltitud());
		return ruta;
	}

	/* (non-Javadoc)
	 * @see com.principal.mundo.sysws.Ubicacion#getProperty(int)
	 */
	public Object getProperty(int i) {
		switch(i)
		{
		case 0:return idCliente;
	    case 1:return nombreCliente;
	    case 2:return direccion;
	    case 3:return telefono;
	    case 4:return barrio;
	    case 5:return idRuta;
	    case 6:return latitud;
	    case 7:return longitud;
	    case 8:return altitud;
	    case 9:return nombreNegocio;
	    case 10:return cedulaNit;
	    case 11:return municipio;	    
	    case 12:return limiteCredito;
	    case 13:return tipoCanal;	  
	    case 14:return lun;
	    case 15:return mar;
	    case 16:return mie;
	    case 17:return jue;
	    case 18:return vie;
	    case 19:return sab;
	    case 20:return dom;
	    case 21:return ordenVisitaLUN;
	    case 22:return ordenVisitaMAR;
	    case 23:return ordenVisitaMIE;
	    case 24:return ordenVisitaJUE;
	    case 25:return ordenVisitaVIE;
	    case 26:return ordenVisitaSAB;
	    case 27:return ordenVisitaDOM;
	    case 28:return fechaUltimoPedido;
	    case 29:return activo;
	    case 30:return observacionVisita;
	    case 31:return fechaUltimaVisita;
	    case 32:return idUbicacion;
	    case 33:return consecutivo;
	    case 34:return efectivo;
//	    case 35:return PrecioDefecto;
		}
		return null;
		
	}

	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 35;
	}

	public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
		 switch(i)
		 {
	    case 0:
	        propertyInfo.name = "idCliente";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 1:
	        propertyInfo.name = "nombreCliente";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 2:
	        propertyInfo.name = "direccion";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 3:
	        propertyInfo.name = "telefono";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;        
            break;
	    case 4:
	        propertyInfo.name = "barrio";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 5:
	        propertyInfo.name = "idRuta";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 6:
	        propertyInfo.name = "latitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;        
            break;
	    case 7:
	        propertyInfo.name = "longitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 8:
	        propertyInfo.name = "altitud";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 9:
	        propertyInfo.name = "nombreNegocio";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 10:
	        propertyInfo.name = "cedulaNit";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 11:
	        propertyInfo.name = "municipio";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 12:
	        propertyInfo.name = "limiteCredito";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 13:
	        propertyInfo.name = "tipoCanal";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	  
	    case 14:
	        propertyInfo.name = "lun";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 15:
	        propertyInfo.name = "mar";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 16:
	        propertyInfo.name = "mie";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 17:
	        propertyInfo.name = "jue";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 18:
	        propertyInfo.name = "vie";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 19:
	        propertyInfo.name = "sab";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 20:
	        propertyInfo.name = "dom";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 21:
	        propertyInfo.name = "ordenVisitaLUN";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 22:
	        propertyInfo.name = "ordenVisitaMAR";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 23:
	        propertyInfo.name = "ordenVisitaMIE";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 24:
	        propertyInfo.name = "ordenVisitaJUE";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 25:
	        propertyInfo.name = "ordenVisitaVIE";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 26:
	        propertyInfo.name = "ordenVisitaSAB";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 27:
	        propertyInfo.name = "ordenVisitaDOM";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;
	    case 28:
	        propertyInfo.name = "fechaUltimoPedido";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;
	    case 29:
	        propertyInfo.name = "activo";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;	  
	    case 30:
	        propertyInfo.name = "observacionVisita";
	        propertyInfo.type = PropertyInfo.LONG_CLASS;
	        break;	
	    case 31:
	        propertyInfo.name = "fechaUltimaVisita";
	        propertyInfo.type = PropertyInfo.STRING_CLASS;
	        break;	        
	    case 32:
	        propertyInfo.name = "idUbicacion";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;	
	    case 33:
	        propertyInfo.name = "consecutivo";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
	    case 34:
	        propertyInfo.name = "efectivo";
	        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
	        break;
//	    case 35:
//	        propertyInfo.name = "PrecioDefecto";
//	        propertyInfo.type = PropertyInfo.STRING_CLASS;
//	        break;
	     default: break;
		 }
	}

	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}