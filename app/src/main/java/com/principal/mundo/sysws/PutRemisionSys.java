package com.principal.mundo.sysws;

import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import com.principal.mundo.Factura_in;
import com.principal.mundo.Remision;
import com.principal.mundo.Remision_in;

/**
 * Clase en la que se envian pedidos al sistema de georeferenciacion SYS
 * @author Javier
 *
 */
public class PutRemisionSys {

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
    private static final String METHOD_NAME = "PutRemision";
    /**
     * referencia para el resultado de la peticion al servicio web
     */
    private String res;
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param ip
     */
    public PutRemisionSys(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys

     * @return res
     */
    public Remision_in setRemision(String xmlRemision, String xmlArticulos, Remision_in remision_in){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo prFactura = new PropertyInfo();
            prFactura.name="xmlRemision";
            prFactura.type=String.class;
            prFactura.setValue(xmlRemision);
            request.addProperty(prFactura);

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
            String resp= response.toString();
            if(resp!=null)
            {
                remision_in =getRemisionXml(resp.toString(),remision_in);
            }
            else
            {
                remision_in.setNRemision(0);
            }
        }
        catch (Exception e)
        {
            res="Error "+e.toString();
            remision_in.setNRemision(0);
        }
        return remision_in;
//		 return null;
    }
    public String getRes() {
        return res;
    }

    public Remision_in getRemisionXml(String xmlRemision,Remision_in remision_in)
    {
        if(!xmlRemision.equals(""))
        {

            try
            {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlRemision));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("DatosRemision");
                // iterate the employees
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);


                    for (int j = 0; j < remision_in.getPropertyCountDatosFactura(); j++) {
                        NodeList name = element.getElementsByTagName(remision_in.getPropertyNameDatosRemision(j));
                        Element line = (Element) name.item(0);
                        remision_in.setProperty(j,getCharacterDataFromElement(line));
                    }

                }
                return remision_in;
            }
            catch(Exception e)
            {
                res="Error "+xmlRemision+e.toString();
                remision_in=null;
            }
        }
        return null;
    }
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}
