package com.principal.mundo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de enviar pededido al sistema PosStar
 * @author Javier
 *
 */
public class EnviarRemision {

    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
    private String URL="";
    //		private static String URL="http://190.252.30.230/servicioarticulos/srvarticulos.asmx";
    private static final String METHOD_NAME = "PutFacturaRemision";
//		private static final String SOAP_ACTION ="http://www.elchispazo.com.co/PutPedido";


    /**
     * metodo que se encargar de asignar valores a los atributos de la clase
     * asigna direccion ip del servidor web del sistema PosStar
     * @param ip
     */
    public EnviarRemision(String ip)
    {
        URL="http://"+ip+"/servicioarticulos/srvarticulos.asmx";
    }


//	public long getEnviarPedido(ArrayList<ArticulosPedido> articulo_pedido, long idPedido, long idCliente, String idUsuario, String observaciones,String latitud, String longitud )
    /**
     * metodo que se encarga de enviar pedido al sistema PosStar
     * @param remision
     * @return 1 si fue enviado correctamente o 0 en caso contrario
     */
    public Remision_in getEnviarRemision(Remision remision, Remision_in remision_in )
    {

        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo prPedido=new PropertyInfo();
            prPedido.setName("Remision");
            prPedido.setValue(remision);
            prPedido.setType(Factura.class);
            request.addProperty(prPedido);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado

            envelope.addMapping(NAMESPACE+METHOD_NAME, "Remision", new Remision().getClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre
            androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
            SoapObject res = (SoapObject) envelope.getResponse();
            if(res!=null)
            {
                remision_in.setRazonSocial(res.getProperty("RazonSocial").toString());
                remision_in.setRepresentante(res.getProperty("Representante").toString());
                remision_in.setRegimenNit(res.getProperty("RegimenNit").toString());
                remision_in.setDireccionTel(res.getProperty("DireccionTel").toString());
                remision_in.setIdCodigoExterno(Long.parseLong(res.getProperty("NRemision").toString()));
                remision_in.setNCaja(Long.parseLong(res.getProperty("NCaja").toString()));
                remision_in.setPrefijo(res.getProperty("Prefijo").toString());
                remision_in.setFecha(res.getProperty("Fecha").toString());
                remision_in.setHora(res.getProperty("Hora").toString());
                remision_in.setBase0(Long.parseLong(res.getProperty("Base0").toString()));
                remision_in.setBase5(Long.parseLong(res.getProperty("Base5").toString()));
                remision_in.setBase10(Long.parseLong(res.getProperty("Base10").toString()));
                remision_in.setBase14(Long.parseLong(res.getProperty("Base14").toString()));
                remision_in.setBase16(Long.parseLong(res.getProperty("Base16").toString()));
                remision_in.setIva5(Long.parseLong(res.getProperty("Iva5").toString()));
                remision_in.setIva10(Long.parseLong(res.getProperty("Iva10").toString()));
                remision_in.setIva14(Long.parseLong(res.getProperty("Iva14").toString()));
                remision_in.setIva16(Long.parseLong(res.getProperty("Iva16").toString()));
                remision_in.setImpoCmo(Long.parseLong(res.getProperty("ImpoCmo").toString()));
                remision_in.setTotalRemision(Long.parseLong(res.getProperty("TotalRemision").toString()));
                remision_in.setResDian(res.getProperty("ResDian").toString());
                remision_in.setRango(res.getProperty("Rango").toString());
                remision_in.setIdBodega(Long.parseLong(res.getProperty("IdBodega").toString()));
                remision_in.setNombreVendedor(res.getProperty("NombreVendedor").toString());
                remision_in.setTelefonoVendedor(res.getProperty("TelefonoVendedor").toString());
            }
            else
            {
                remision_in.setIdCodigoExterno(0);
//			 	remision_in.cargarFacturaPrueba();
            }
        }
        catch (Exception e)
        {
            remision_in.setIdCodigoExterno(0);
//	    	 remision_in.setRazonSocial(e.toString());

        }
        return remision_in;
    }




}
