package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by acer on 3/10/2018.
 */

public class PutPedidoAls {
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
    private static final String METHOD_NAME = "PutPedidoAls";
    /**
     * referencia para el resultado de la peticion al servicio web
     */
    private String res;
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param ip
     */
    public PutPedidoAls(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        res="";
    }


    public String setPedidoAls(String xmlPedido, String xmlArticulos){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo prPedido = new PropertyInfo();
            prPedido.name="xmlPedido";
            prPedido.type=String.class;
            prPedido.setValue(xmlPedido);
            request.addProperty(prPedido);

            PropertyInfo prArticulos = new PropertyInfo();
            prArticulos.name="xmlArticulos";
            prArticulos.type=String.class;
            prArticulos.setValue(xmlArticulos);
            request.addProperty(prArticulos);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            res= response.toString();
        }
        catch (Exception e) {
            res = e.toString();
        }
        return res;
    }
}
