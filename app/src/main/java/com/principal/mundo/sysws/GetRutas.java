package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

/**
 * Created by acer on 8/10/2018.
 */

public class GetRutas {
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
    private static final String METHOD_NAME = "GetRutas";


    private RutaMunicipios rutaMunicipios;
    /**
     * @param ip
     */
    public GetRutas(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public ArrayList<RutaMunicipios> getRutas()
    {

        ArrayList<RutaMunicipios> l = new ArrayList<RutaMunicipios>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);

            rutaMunicipios=new RutaMunicipios();
            rutaMunicipios.setIdRuta("999");
            rutaMunicipios.setNombreRuta("SELEC..");
            l.add(rutaMunicipios);

            rutaMunicipios=new RutaMunicipios();
            rutaMunicipios.setIdRuta("0");
            rutaMunicipios.setNombreRuta("TODAS");
            l.add(rutaMunicipios);

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
                        rutaMunicipios=new RutaMunicipios();
                        rutaMunicipios.setIdRuta(ic.getProperty("idRuta").toString().trim());
                        rutaMunicipios.setNombreRuta(ic.getProperty("nombreRuta").toString().trim());
                       rutaMunicipios.setCadenaMunicipios(ic.getProperty("listaMunicipios").toString().trim());
                        l.add(rutaMunicipios);
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
                {
                    rutaMunicipios=new RutaMunicipios();
                    rutaMunicipios.setIdRuta(ic.getProperty("idRuta").toString().trim());
                    rutaMunicipios.setNombreRuta(ic.getProperty("nombreRuta").toString().trim());
                    rutaMunicipios.setCadenaMunicipios(ic.getProperty("listaMunicipios").toString().trim());
                    l.add(rutaMunicipios);
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



    public String getError() {
        return error;
    }

}
