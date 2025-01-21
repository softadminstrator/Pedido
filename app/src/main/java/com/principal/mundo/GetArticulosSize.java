package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de obtener la fecha de sistema que sera tenida en cuenta en el momento de 
 * enviar pedidos al sistema
 * @author Javier
 *
 */
public class GetArticulosSize {

		 /**
	     * nombre del dominio al que sera enviada la anulacion del pedido
	     */
	    private static final String NAMESPACE = "http://operator.wscashserver.com";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";
	    private String operacion="";
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */		
		private static final String METHOD_NAME = "GetArticulosSize";
	
		/**
		 * metodo que se encarga de asignar valores a los atributos de la clase
		 * @param ip
		 */
		public GetArticulosSize(String ip, String Webid)
		{
			URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
			//URL="http://"+ip+":8083/WSCashServer2/services/OperatorClass?wsdl";
		}
		
		/**
		 * metodo que se encarga de realizar la llamada al servicio web del sistema PosStar
		 * y obtener la fecha actual de dicho sistema
		 * @return Fecha
		 */
		public long GetArticulosSize(String categoria,String fecha)
		{
			long resultado=0;
			
			try
			 {	
	   	 
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			 
			 PropertyInfo prcategoria = new PropertyInfo();
			 prcategoria.name="Categoria";
			 prcategoria.type=String.class;
			 prcategoria.setValue(categoria);
			 request.addProperty(prcategoria);
			 
			 PropertyInfo prfecha = new PropertyInfo();
			 prfecha.name="FechaA";
			 prfecha.type=String.class;
			 prfecha.setValue(fecha);
			 request.addProperty(prfecha);	
			 
			 
	   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
			 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
			 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,1200000);
			 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre			
			 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		    	SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
			 	if(res!=null)
			 	{
			 		operacion="OK";
			 		resultado =Long.parseLong(res.toString());		 		
			  	}
			 		       	         	         
		     }
		     catch (Exception e)
		     {
		    	 operacion=e.toString();
		    	 resultado =0;
		     }	
			return resultado;
		}
		public String getOperacion() {
			return operacion;
		}

}
