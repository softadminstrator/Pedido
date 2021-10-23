package com.principal.mundo.sysws;

import com.principal.mundo.*;
import com.principal.mundo.HttpTransportSE;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.*;

import java.util.ArrayList;

/**
 * Created by acer on 6/09/2018.
 */

public class GetDeudaClientexVendedor {
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
   //private static final String METHOD_NAME = "getFilterClient";
//OJO consulta todos los clientes
    private static final String METHOD_NAME = "getFilterClient2";
    com.principal.mundo.Cliente cliente;


    /**
     * @param ip
     */
    public GetDeudaClientexVendedor(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public ArrayList<com.principal.mundo.Cliente> getClientes(String operacion, String documento,String text)
    {

        ArrayList<com.principal.mundo.Cliente> l = new ArrayList<com.principal.mundo.Cliente>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo properacion = new PropertyInfo();
            properacion.name="operacion";
            properacion.type=String.class;
            properacion.setValue(operacion);

            PropertyInfo prdocumento = new PropertyInfo();
            prdocumento.name="documento";
            prdocumento.type=String.class;
            prdocumento.setValue(documento);

            PropertyInfo prtext = new PropertyInfo();
            prtext.name="text";
            prtext.type=String.class;
            prtext.setValue(text);

            request.addProperty(properacion);
            request.addProperty(prdocumento);
            request.addProperty(prtext);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);


            //SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
            try {
                java.util.Vector<SoapObject> res = (java.util.Vector<SoapObject>) envelope.getResponse();
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
                        cliente.nit = ic.getProperty("nit").toString().trim();j++;
                        cliente.deudaAntFac = ic.getProperty("deudaAntFac").toString().trim();j++;
                        cliente.deudaTotal = ic.getProperty("deudaTotal").toString().trim();j++;
                        cliente.deudaActual=Long.parseLong(ic.getProperty("deudaAntFac").toString().trim())+Long.parseLong(ic.getProperty("deudaTotal").toString().trim());
                        l.add(cliente);
                    }


                }
                else
                {
                    return l;
                }
            }
            catch (Exception ext)
            {
                SoapObject ic = (SoapObject) envelope.getResponse();
                if (ic != null)
                {//comprobamos que la respuesta no esté vacía
                    //recorremos el objeto

                        cliente=new com.principal.mundo.Cliente();	j++;
                        cliente.idCliente = Long.parseLong(ic.getProperty("idCliente").toString().trim());j++;
                        cliente.nombre = ic.getProperty("nombreCliente").toString().trim();j++;
                        cliente.nit = ic.getProperty("nit").toString().trim();j++;
                        cliente.deudaAntFac = ic.getProperty("deudaAntFac").toString().trim();j++;
                        cliente.deudaTotal = ic.getProperty("deudaTotal").toString().trim();j++;
                        cliente.deudaActual=Long.parseLong(ic.getProperty("deudaAntFac").toString().trim())+Long.parseLong(ic.getProperty("deudaTotal").toString().trim());
                        l.add(cliente);
                }
                else
                {
                    return l;
                }
            }
//		 	SoapObject res = (SoapObject) envelope.getResponse();




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
