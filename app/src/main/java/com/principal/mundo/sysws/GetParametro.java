package com.principal.mundo.sysws;

import com.principal.mundo.Medios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GetParametro {
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
    private static final String METHOD_NAME = "GetParametro";



    /**
     * @param ip
     */
    public GetParametro(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";

    }


    public String  GGetParametro()
    {

       String resParam="";
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);


		 	SoapObject res = (SoapObject) envelope.getResponse();


            if (res != null)
            {
                resParam=res.toString();
            }
            else
            {
                return "";
            }

        }
        catch (Exception e)
        {
            error=i+" " +j+" "+e.toString();
            return null;
        }
        return resParam;
    }


    public String getError() {
        return error;
    }
}
