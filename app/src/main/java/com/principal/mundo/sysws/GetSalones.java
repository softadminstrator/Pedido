package com.principal.mundo.sysws;

import com.principal.mundo.Medios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GetSalones {
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
    private static final String METHOD_NAME = "GetSalones";



    /**
     * @param ip
     */
    public GetSalones(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }


    public ArrayList<Salon> GetListaSalones()
    {

        ArrayList<Salon> l = new ArrayList<Salon>();
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
                    Salon salon=new Salon();	j++;
                    SoapObject ic = (SoapObject)res.get(i);		j++;
                    salon.IdSalon = ic.getProperty("idSalon").toString().trim();j++;
                    salon.Nombre = ic.getProperty("nombre").toString().trim();j++;
                    salon.Borrado = ic.getProperty("borrado").toString().trim();j++;
                    salon.PX = ic.getProperty("PX").toString().trim();j++;
                    salon.PY = ic.getProperty("PY").toString().trim();j++;

                    l.add(salon);

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
