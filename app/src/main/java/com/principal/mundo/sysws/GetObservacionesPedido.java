package com.principal.mundo.sysws;

import com.principal.mundo.Categoria;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by acer on 3/10/2018.
 */

public class GetObservacionesPedido {
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
    private static final String METHOD_NAME = "GetObservacionesPedido";
    private String resultado="";
    private ArrayList<ObservacionPedido> listaObservacionPedido;
    private ObservacionPedido observacionPedido;


    /**
     * metodo que se encarga asignar valores a los atributos de la clase
     * @param ip
     */
    public GetObservacionesPedido(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        listaObservacionPedido=new ArrayList<ObservacionPedido>();
    }


    public ArrayList<ObservacionPedido> getObservacionesSistema()
    {

        ArrayList<ObservacionPedido> l = new ArrayList<ObservacionPedido>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

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
                        observacionPedido=new ObservacionPedido();	j++;
                        SoapObject ic = (SoapObject)res.get(i);		j++;
                        observacionPedido.setIdObservacionPedido(ic.getProperty("idObservacionPedido").toString().trim());
                        observacionPedido.setNombreObservacion(ic.getProperty("nombreObservacion").toString().trim());
                        l.add(observacionPedido);
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

                    observacionPedido=new ObservacionPedido();	j++;
                    observacionPedido.setIdObservacionPedido(ic.getProperty("idObservacionPedido").toString().trim());
                    observacionPedido.setNombreObservacion(ic.getProperty("nombreObservacion").toString().trim());
                    l.add(observacionPedido);
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
            resultado=i+" " +j+" "+e.toString();
            return null;
        }
        return l;
    }


    public String getResultado() {
        return resultado;
    }
}
