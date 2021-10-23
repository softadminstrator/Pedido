package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

/**
 * Created by acer on 22/09/2018.
 */

public class GetRevisores {
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
    private static final String METHOD_NAME = "GetRevisores";


    private Vendedor vendedor;
    /**
     * @param ip
     */
    public GetRevisores(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public ArrayList<Vendedor> GetRevisores()
    {

        ArrayList<Vendedor> l = new ArrayList<Vendedor>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);

            //vendedor=new Vendedor();
            //vendedor.setIdVendedor("999");
            //vendedor.setVendedor("SELEC..");
            //l.add(vendedor);

            vendedor=new Vendedor();
            vendedor.setIdVendedor("0");
            vendedor.setVendedor("TODOS");

            l.add(vendedor);
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
                        SoapObject ic = (SoapObject)res.get(i);		j++;
                        vendedor=new Vendedor();
                        vendedor.setIdVendedor(ic.getProperty("idVendedor").toString().trim());
                        vendedor.setVendedor(ic.getProperty("vendedor").toString().trim());
                        vendedor.setDocumento(ic.getProperty("documento").toString().trim());
                        vendedor.setTipo(ic.getProperty("tipo").toString().trim());
                        l.add(vendedor);
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


                    vendedor=new Vendedor();
                    vendedor.setIdVendedor(ic.getProperty("idVendedor").toString().trim());
                    vendedor.setVendedor(ic.getProperty("vendedor").toString().trim());
                    vendedor.setDocumento(ic.getProperty("documento").toString().trim());
                    vendedor.setTipo(ic.getProperty("tipo").toString().trim());
                    l.add(vendedor);
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
