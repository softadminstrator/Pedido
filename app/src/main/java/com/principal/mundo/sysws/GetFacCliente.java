package com.principal.mundo.sysws;

import com.principal.mundo.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

/**
 * Created by acer on 7/09/2018.
 */

public class GetFacCliente {
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
    private static final String METHOD_NAME = "getFacCliente";


    private  Factura_in factura_in;
    /**
     * @param ip
     */
    public GetFacCliente(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public ArrayList<Factura_in> getClientes(String idCliente)
    {

        ArrayList<Factura_in> l = new ArrayList<Factura_in>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pridCliente = new PropertyInfo();
            pridCliente.name="idCliente";
            pridCliente.type=String.class;
            pridCliente.setValue(idCliente);

            request.addProperty(pridCliente);

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
                        factura_in=new Factura_in();	j++;
                        SoapObject ic = (SoapObject)res.get(i);		j++;
                        factura_in.setNFactura(Long.parseLong(ic.getProperty("NFactura").toString().trim()));j++;
                        factura_in.setNCaja(Long.parseLong(ic.getProperty("NCaja").toString().trim()));j++;
                        factura_in.setFecha(ic.getProperty("fecha").toString().trim());j++;
                        factura_in.setDevolucion(Long.parseLong(ic.getProperty("devolucion").toString().trim()));j++;
                        factura_in.setTotalFactura(Long.parseLong(ic.getProperty("totalFactura").toString().trim()));j++;
                        factura_in.setValorPagado(Long.parseLong(ic.getProperty("valorPagado").toString().trim()));j++;
                        factura_in.setIdCliente(Long.parseLong(idCliente));
                        factura_in.isPagada=false;
                        l.add(factura_in);
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

                    factura_in=new Factura_in();	j++;
                    factura_in.setNFactura(Long.parseLong(ic.getProperty("NFactura").toString().trim()));j++;
                    factura_in.setNCaja(Long.parseLong(ic.getProperty("NCaja").toString().trim()));j++;
                    factura_in.setFecha(ic.getProperty("fecha").toString().trim());j++;
                    factura_in.setDevolucion(Long.parseLong(ic.getProperty("devolucion").toString().trim()));j++;
                    factura_in.setTotalFactura(Long.parseLong(ic.getProperty("totalFactura").toString().trim()));j++;
                    factura_in.setValorPagado(Long.parseLong(ic.getProperty("valorPagado").toString().trim()));j++;
                    factura_in.setIdCliente(Long.parseLong(idCliente));
                    factura_in.isPagada=false;
                    l.add(factura_in);
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
