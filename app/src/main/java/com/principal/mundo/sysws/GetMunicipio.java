package com.principal.mundo.sysws;

import com.principal.mundo.Departamento;
import com.principal.mundo.Municipio;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class GetMunicipio {

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
        private static final String METHOD_NAME = "GetMunicipio";
        /**
         * referencia para el resultado de la peticion al servicio web
         */
        private String res;
        /**
         * metodo que se encarga de asignar valores a los atributos de la clase
         * @param ip
         */
        public GetMunicipio(String ip, String Webid)
        {
            URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
            res="";
        }

        /**
         * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys

         * @return res
         */
        public ArrayList<Municipio> GetMunicipios(String IdDpto){

            ArrayList<Municipio> listaMunicipio =new ArrayList<Municipio>();
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                PropertyInfo prPedido = new PropertyInfo();
                prPedido.name="key";
                prPedido.type=String.class;
                prPedido.setValue("C5HKSIqrZ4x5PZw");
                request.addProperty(prPedido);

                PropertyInfo prIdDpto = new PropertyInfo();
                prIdDpto.name="idDpto";
                prIdDpto.type=String.class;
                prIdDpto.setValue(IdDpto);
                request.addProperty(prIdDpto);




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

                    for (int i = 0;i < ta ; i++) {
                        Municipio municipio=new Municipio();
                        SoapObject ic = (SoapObject)res.get(i);
                        municipio.setIdMpio(ic.getProperty("idMpio").toString().trim());
                        municipio.setIdDpto(ic.getProperty("idDpto").toString().trim());
                        municipio.setMunicipio(ic.getProperty("municipio").toString().trim());
                        listaMunicipio.add(municipio);

                    }
                }

            }
            catch (Exception e)
            {

                res=e.toString();
            }
            return listaMunicipio;
        }
}
