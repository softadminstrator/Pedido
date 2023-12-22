package com.principal.mundo.sysws;

import java.util.ArrayList;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de obtener los clientes luego de invocar el servicio web del sistema PosStar
 * para luego ser almacenados en el telefono
 * @author Javier
 *
 */
public class GetClientexVendedor {
	private String error="";
	
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://operator.wscashserver.com";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";	
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */		
		private static final String METHOD_NAME = "GetClientexVendedor";	
		com.principal.mundo.Cliente cliente;
		
	
	/**
	 * @param ip
	 */
	public GetClientexVendedor(String ip, String Webid)
	{	
		URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        //URL="http://"+ip+":8083/WSCashServer2/services/OperatorClass?wsdl";
	}
	
	/**
	 * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
	 * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
	 * y los almacena en un arreglo
	 * @param cedula
	 * @return Lista de clientes
	 */
	public ArrayList<com.principal.mundo.Cliente> getClientes(String cedula)
	{
	
		ArrayList<com.principal.mundo.Cliente> l = new ArrayList<com.principal.mundo.Cliente>();
		int i=0;
		int j=0;
		try
		 {	   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 PropertyInfo prcedula = new PropertyInfo();
		 prcedula.name="Cedula";
		 prcedula.type=String.class;
		 prcedula.setValue(cedula);
		 request.addProperty(prcedula);
		 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.setOutputSoapObject(request);
	     
	     HttpTransportSE ht = new HttpTransportSE(URL);	     
	     ht.call(NAMESPACE + METHOD_NAME, envelope);
		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
	     java.util.Vector<SoapObject> res = (java.util.Vector<SoapObject>) envelope.getResponse();
//		 	SoapObject res = (SoapObject) envelope.getResponse();
		 	
		 	
		 	if (res != null) 
		 	{//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
		 		String [] temres= new String [res.size()];
		 		int ta = temres.length ;
		 		
					for (i = 0;i < ta ; i++) {
						j=0; 
					cliente=new com.principal.mundo.Cliente();	j++;			               
					SoapObject ic = (SoapObject)res.get(i);		j++;			
					cliente.idCliente = Long.parseLong(ic.getProperty("idCliente").toString().trim());j++;
					cliente.nombre = ic.getProperty("nombreCliente").toString().trim();j++;
					cliente.representante = ic.getProperty("representante").toString().trim();j++;
					cliente.nit = ic.getProperty("nit").toString().trim();j++;
					cliente.direccion = ic.getProperty("direccion").toString().trim();j++;
					cliente.telefono = ic.getProperty("telefono").toString().trim();j++;
					cliente.municipio = ic.getProperty("municipio").toString().trim();		j++;			
					cliente.limiteCredito = Long.parseLong(ic.getProperty("limiteCredito").toString().trim());j++;
					cliente.barrio = ic.getProperty("barrio").toString().trim();j++;
					cliente.tipoCanal = ic.getProperty("tipoCanal").toString().trim();j++;
					cliente.lun = getValorDia(ic.getProperty("LUN").toString().trim());j++;
					cliente.mar = getValorDia(ic.getProperty("MAR").toString().trim());j++;
					cliente.mie = getValorDia(ic.getProperty("MIE").toString().trim());j++;
					cliente.jue = getValorDia(ic.getProperty("JUE").toString().trim());j++;
					cliente.vie = getValorDia(ic.getProperty("VIE").toString().trim());j++;
					cliente.sab = getValorDia(ic.getProperty("SAB").toString().trim());j++;
					cliente.dom = getValorDia(ic.getProperty("DOM").toString().trim());j++;
					cliente.ordenVisita = Long.parseLong(ic.getProperty("ordenVisita").toString().trim());j++;
					cliente.OrdenVisitaLUN = Long.parseLong(ic.getProperty("ordenVisitaLUN").toString());j++;
					cliente.OrdenVisitaMAR = Long.parseLong(ic.getProperty("ordenVisitaMAR").toString());j++;
					cliente.OrdenVisitaMIE = Long.parseLong(ic.getProperty("ordenVisitaMIE").toString());j++;
					cliente.OrdenVisitaJUE = Long.parseLong(ic.getProperty("ordenVisitaJUE").toString());j++;
					cliente.OrdenVisitaVIE = Long.parseLong(ic.getProperty("ordenVisitaVIE").toString());j++;
					cliente.OrdenVisitaSAB = Long.parseLong(ic.getProperty("ordenVisitaSAB").toString());j++;
					cliente.OrdenVisitaDOM = Long.parseLong(ic.getProperty("ordenVisitaDOM").toString());j++;	
					cliente.PrecioDefecto = ic.getProperty("precioDefecto").toString().trim();j++;
                    cliente.DiasGracia=  ic.getProperty("diasGracia").toString().trim();j++;

					cliente.PrimerApellido= ic.getProperty("primerApellido").toString().trim();j++;
					cliente.SegundoApellido= ic.getProperty("segundoApellido").toString().trim();j++;
					cliente.PrimerNombre= ic.getProperty("primerNombre").toString().trim();j++;
					cliente.SegundoNombre= ic.getProperty("segundoNombre").toString().trim();j++;
					cliente.RazonSocial= ic.getProperty("razonSocial").toString().trim();j++;
					cliente.TipoPersona= ic.getProperty("tipoPersona").toString().trim();j++;
					cliente.Mail= ic.getProperty("mail").toString().trim();j++;



						try {
						cliente.deudaAntFac = ic.getProperty("deudaAntFac").toString().trim();
						j++;
					} catch (Exception e)
					{
						cliente.deudaAntFac="0";
					}

					//Obtiene sucursales
						try
						{
							cliente.setXmlClientesSucursales(ic.getProperty("xmlClientesSucursales").toString().trim());
							cliente.getCargarSucursales();
						}
						catch (Exception e)
						{
							cliente.setXmlClientesSucursales("");
						}





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
		 		return l;
		 	}
		 		       	         	         
	     }
	     catch (Exception e)
	     {
	    	 error=i+" " +j+" "+e.toString();
	    	 return null;
	     }	
		return l;
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
	
	public String getError() {
		return error;
	}

}
