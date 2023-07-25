package com.principal.mundo.sysws;

import com.principal.mundo.Cliente;
import com.principal.mundo.Medios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GetMedios {
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
    private static final String METHOD_NAME = "GetMediosDePago";



    /**
     * @param ip
     */
    public GetMedios(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        //URL="http://"+ip+":8083/WSCashServer2/services/OperatorClass?wsdl";
    }


    public ArrayList<Medios> GetMediosDePago()
    {

        ArrayList<Medios> l = new ArrayList<Medios>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);

            //SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
            java.util.Vector<SoapObject> res = (java.util.Vector<SoapObject>) envelope.getResponse();
//		 	SoapObject res = (SoapObject) envelope.getResponse();


            if (res != null)
            {//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
                String [] temres= new String [res.size()];
                int ta = temres.length ;

                for (i = 0;i < ta ; i++) {
                    j=0;
                    Medios medios=new Medios();	j++;
                    SoapObject ic = (SoapObject)res.get(i);		j++;
                    medios.IdMediosDePago = Long.parseLong(ic.getProperty("idMediosDePago").toString().trim());j++;
                    medios.Nombre = ic.getProperty("nombre").toString().trim();j++;
                    medios.Borrado = ic.getProperty("borrado").toString().trim();j++;
                    medios.VisibleEnPantalla = ic.getProperty("visibleEnPantalla").toString().trim();j++;
                    medios.TeclaAccesoDirecto = ic.getProperty("teclaAccesoDirecto").toString().trim();j++;
                    medios.PermiteModificar = ic.getProperty("permiteModificar").toString().trim();j++;

                    l.add(medios);

                }


            }
            else
            {
                return l;
            }

        }
        catch (Exception e)
        {
            error=i+" " +j+" "+e.toString();
            return null;
        }
        return l;
    }


    public String getError() {
        return error;
    }
}
