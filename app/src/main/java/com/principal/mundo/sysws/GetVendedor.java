package com.principal.mundo.sysws;

import com.principal.mundo.Factura_in;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

/**
 * Created by acer on 22/09/2018.
 */

public class GetVendedor {
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
    private static final String METHOD_NAME = "GetVendedor";


    private Vendedor vendedor;
    /**
     * @param ip
     */
    public GetVendedor(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public Vendedor getVendedor(String Documento)
    {
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pridCliente = new PropertyInfo();
            pridCliente.name="Documento";
            pridCliente.type=String.class;
            pridCliente.setValue(Documento);

            request.addProperty(pridCliente);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);



                SoapObject ic = (SoapObject) envelope.getResponse();
                if (ic != null)
                {//comprobamos que la respuesta no esté vacía
                    //recorremos el objeto

                    vendedor=new Vendedor();
                    vendedor.setIdVendedor(ic.getProperty("idVendedor").toString().trim());
                    vendedor.setVendedor(ic.getProperty("vendedor").toString().trim());
                    vendedor.setDocumento(ic.getProperty("documento").toString().trim());
                    vendedor.setTipo(ic.getProperty("tipo").toString().trim());                }
                else
                {
                    vendedor=null;
                }



        }
        catch (Exception e)
        {
            return null;
        }
        return vendedor;
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
