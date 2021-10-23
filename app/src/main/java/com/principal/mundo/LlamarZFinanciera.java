package com.principal.mundo;

import java.util.ArrayList;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de actualizar los articulos de la aplicacion, atraves de la llamada al 
 * servicio web del sistema PosStar
 * @author Javier
 *
 */
public class LlamarZFinanciera {
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    private String URL="";	
	    private static final String METHOD_NAME = "GetZ";
		private String resultado;
	
	public LlamarZFinanciera(String ip)
	{			
		resultado="";
		URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";		
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public ZFinanciera getZFinanciera(long NumZ, long NCaja)
	{
		ZFinanciera zFinanciera=new ZFinanciera();	
	try
		 {	
   	 
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);		 
		 request.addProperty("NumZ",NumZ);
		 request.addProperty("NumCaja",NCaja);
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre		
		
	
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	SoapObject res = (SoapObject) envelope.getResponse();
		 	resultado="ok";		 	
		 	if (res != null) 
		 	{
		 		zFinanciera.NumZ=Long.parseLong(res.getProperty("NumZ").toString());
				zFinanciera.NCaja=Long.parseLong(res.getProperty("NCaja").toString());
				zFinanciera.Fecha=res.getProperty("Fecha").toString();
				zFinanciera.Hora=res.getProperty("Hora").toString();
				zFinanciera.FacturaInicial=Long.parseLong(res.getProperty("FacturaInicial").toString());
				zFinanciera.FacturaFinal=Long.parseLong(res.getProperty("FacturaFinal").toString());
				zFinanciera.VentasExentas=Long.parseLong(res.getProperty("VentasExentas").toString());
				zFinanciera.Ventas16=Long.parseLong(res.getProperty("Ventas16").toString());
				zFinanciera.Iva16=Long.parseLong(res.getProperty("Iva16").toString());
				zFinanciera.Total16=Long.parseLong(res.getProperty("Total16").toString());
				zFinanciera.Ventas5=Long.parseLong(res.getProperty("Ventas5").toString());
				zFinanciera.Iva5=Long.parseLong(res.getProperty("Iva5").toString());
				zFinanciera.Total5=Long.parseLong(res.getProperty("Total5").toString());
				zFinanciera.Ventas8=Long.parseLong(res.getProperty("Ventas8").toString());
				zFinanciera.Iva8=Long.parseLong(res.getProperty("Iva8").toString());
				zFinanciera.Total8=Long.parseLong(res.getProperty("Total8").toString());
				zFinanciera.NTransacciones=Long.parseLong(res.getProperty("NTransacciones").toString());
				zFinanciera.Ventas=Long.parseLong(res.getProperty("Ventas").toString());
				zFinanciera.Iva=Long.parseLong(res.getProperty("Iva").toString());
				zFinanciera.ImpoCmo=Long.parseLong(res.getProperty("ImpoCmo").toString());
				zFinanciera.Descuento=Long.parseLong(res.getProperty("Descuento").toString());
				zFinanciera.Total=Long.parseLong(res.getProperty("Total").toString());
				zFinanciera.consulta=1;		
				try
				{
					SoapObject mc = (SoapObject)res.getProperty(22);
						
						String [] temres= new String [mc.getPropertyCount()];
				 		int ta = temres.length -1;
							for (int i = 0;i < ta ; i++) {

						    MediosDePago mediosDePago=new MediosDePago();				               
							SoapObject mcdetalle = (SoapObject)mc.getProperty(i);
							if(mcdetalle!=null)
								{
									mediosDePago.MedioPago=mcdetalle.getProperty("MedioPago").toString();
									mediosDePago.Valor=Long.parseLong(mcdetalle.getProperty("Valor").toString());
									mediosDePago.Cuenta=Long.parseLong(mcdetalle.getProperty("Cuenta").toString());									
									zFinanciera.listaMendiosDePago.add(mediosDePago);
								}
								resultado="true";
							}	
				}
				catch(Exception e3)
				{
					zFinanciera.NumZ=0;
				}								   
						
		 	}
		 	else
		 	{
		 		zFinanciera.NumZ=0;
		 	}
	     }
	     catch (Exception e)
	     {
	    	 resultado=e.toString();
	    	 zFinanciera=null;
	     }	
		return zFinanciera;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	

}
