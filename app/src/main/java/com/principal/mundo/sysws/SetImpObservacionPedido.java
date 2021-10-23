package com.principal.mundo.sysws;

import com.principal.mundo.Pedido_in;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by acer on 9/10/2018.
 */

public class SetImpObservacionPedido {

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
    private static final String METHOD_NAME = "SetImpObservacionPedido";
    /**
     * referencia para el resultado de la peticion al servicio web
     */
    private String res;
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param ip
     */
    public SetImpObservacionPedido(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        res="";
    }


    public String SetImpObservacionPedido(String NPedido, String NCaja){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo prNPedido = new PropertyInfo();
            prNPedido.name="NPedido";
            prNPedido.type=String.class;
            prNPedido.setValue(NPedido);
            request.addProperty(prNPedido);

            PropertyInfo prNCaja = new PropertyInfo();
            prNCaja.name="NCaja";
            prNCaja.type=String.class;
            prNCaja.setValue(NCaja);
            request.addProperty(prNCaja);




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
