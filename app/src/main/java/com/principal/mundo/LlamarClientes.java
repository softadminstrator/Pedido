package com.principal.mundo;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de obtener los clientes luego de invocar el servicio web del sistema PosStar
 * para luego ser almacenados en el telefono
 * @author Javier
 *
 */
public class LlamarClientes {
	private String error="";
	
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";	
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */		
		private static final String METHOD_NAME = "GetClientexVendedor";	
		Cliente cliente;
		
	
	/**
	 * @param ip
	 */
	public LlamarClientes(String ip)
	{	
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
	}
	
	/**
	 * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
	 * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
	 * y los almacena en un arreglo
	 * @param cedula
	 * @return Lista de clientes
	 */
	public ArrayList<Cliente> getClientes(String cedula)
	{
	
		ArrayList<Cliente> l = new ArrayList<Cliente>();
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 request.addProperty("Cedula",cedula); 
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
		
		
	
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	SoapObject res = (SoapObject) envelope.getResponse();
		 	
		 	
		 	if (res != null) 
		 	{//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
		 		String [] temres= new String [res.getPropertyCount()];
		 		int ta = temres.length -1;
					for (int i = 0;i < ta ; i++) {

					cliente=new Cliente();				               
					SoapObject ic = (SoapObject)res.getProperty(i);					
					cliente.idCliente = Long.parseLong(ic.getProperty("IdCliente").toString());
					cliente.nombre = ic.getProperty("NombreCliente").toString();
					cliente.representante = ic.getProperty("Representante").toString();
					cliente.nit = ic.getProperty("Nit").toString();
					cliente.direccion = ic.getProperty("Direccion").toString();
					cliente.telefono = ic.getProperty("Telefono").toString();
					cliente.municipio = ic.getProperty("Municipio").toString();
					cliente.limiteCredito = Long.parseLong(ic.getProperty("LimiteCredito").toString());
					cliente.barrio = ic.getProperty("Barrio").toString();
					cliente.tipoCanal = ic.getProperty("TipoCanal").toString();
					cliente.lun = ic.getProperty("LUN").toString();
					cliente.mar = ic.getProperty("MAR").toString();
					cliente.mie = ic.getProperty("MIE").toString();
					cliente.jue = ic.getProperty("JUE").toString();
					cliente.vie = ic.getProperty("VIE").toString();
					cliente.sab = ic.getProperty("SAB").toString();
					cliente.dom = ic.getProperty("DOM").toString();
					cliente.ordenVisita = Long.parseLong(ic.getProperty("OrdenVisita").toString());
					cliente.OrdenVisitaLUN = Long.parseLong(ic.getProperty("OrdenVisitaLUN").toString());
					cliente.OrdenVisitaMAR = Long.parseLong(ic.getProperty("OrdenVisitaMAR").toString());
					cliente.OrdenVisitaMIE = Long.parseLong(ic.getProperty("OrdenVisitaMIE").toString());
					cliente.OrdenVisitaJUE = Long.parseLong(ic.getProperty("OrdenVisitaJUE").toString());
					cliente.OrdenVisitaVIE = Long.parseLong(ic.getProperty("OrdenVisitaVIE").toString());
					cliente.OrdenVisitaSAB = Long.parseLong(ic.getProperty("OrdenVisitaSAB").toString());
					cliente.OrdenVisitaDOM = Long.parseLong(ic.getProperty("OrdenVisitaDOM").toString());	
					cliente.PrecioDefecto = ic.getProperty("PrecioDefecto").toString();
					cliente.cedulaVendedor=cedula;
					cliente.fechaUltimoPedido="01/01/2012";
					cliente.fechaUltimaVisita="01/01/2012";
					cliente.fechaUltimaVenta="01/01/2012";
					cliente.ubicado="NO";
					cliente.activo=1;				
					l.add(cliente);
			                        
					}
					
					
		 	}
		 	else 
		 	{
		 		return null;
		 	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 error=" " +e.toString();
	    	 return null;
	     }	
		return l;
	}
	
	public String getError() {
		return error;
	}

}
