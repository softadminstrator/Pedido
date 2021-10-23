package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by acer on 23/04/2019.
 */

public class PutLibro {
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
    private static final String METHOD_NAME = "PutLibro";
    /**
     * referencia para el resultado de la peticion al servicio web
     */
    private long res;
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param ip
     */
    public PutLibro(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        res=0;
    }


    public long setLibro(String xmlLibro){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo prLibro = new PropertyInfo();
            prLibro.name="xmlLibro";
            prLibro.type=String.class;
            prLibro.setValue(xmlLibro);
            request.addProperty(prLibro);




            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            String resp= response.toString();
            if(resp!=null)
            {
                res =Long.parseLong(resp.toString());
            }
        }
        catch (Exception e)
        {

            res=0;
        }
        return res;
    }
}
