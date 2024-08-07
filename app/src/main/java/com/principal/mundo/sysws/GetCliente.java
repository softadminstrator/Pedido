package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.principal.mundo.Cliente;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class GetCliente {

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
    private static final String METHOD_NAME = "GetCliente2";
    /**
     * referencia para el resultado de la peticion al servicio web
     */
    private String res;
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param ip
     */
    public GetCliente(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
        res="";
    }

    /**
     * metodo que se encarga de enviar el pedido al servicio web del sistema de georeferenciacion Sys

     * @return res
     */
    public Cliente GetDatosCliente(String IdCliente){
        Cliente cliente =new Cliente();
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo prPedido = new PropertyInfo();
            prPedido.name="key";
            prPedido.type=String.class;
            prPedido.setValue("C5HKSIqrZ4x5PZw");
            request.addProperty(prPedido);

            PropertyInfo prArticulos = new PropertyInfo();
            prArticulos.name="idCliente";
            prArticulos.type=String.class;
            prArticulos.setValue(IdCliente);
            request.addProperty(prArticulos);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);
            if (envelope.bodyIn instanceof SoapFault) {
                SoapFault soapFault = (SoapFault) envelope.bodyIn;
                String requestStr = ht.requestDump;

            } else {
                SoapObject soapObject = ((SoapObject) envelope.bodyIn);
                String requestStr = ht.requestDump;
            }
            SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
            if (res != null)
            {//comprobamos que la respuesta no esté vacía
                cliente=getCliente(res.toString());
            }
            else
            {
                cliente=new Cliente();
            }
        }
        catch (Exception e)
        {

            res=e.toString();
        }
        return cliente;
    }

    public  Cliente getCliente(String xml )
    {
        if(!xml.equals(""))
        {
            Cliente cli=new Cliente();
            try
            {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("Datos");
                // iterate the employees
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);


                    for (int j = 0; j < cli.getPropertyCountCliente(); j++) {
                        NodeList name = element.getElementsByTagName(cli.getPropertyNameCliente(j));
                        Element line = (Element) name.item(0);
                        if(j==10)
                        {
                            cli.setPropertyCliente(j,getCharacterDataFromElement2(line));
                        }
                        else
                        {
                            cli.setPropertyCliente(j, getCharacterDataFromElement(line));
                        }
                    }

                }
                return cli;
            }
            catch(Exception e)
            {
                cli=null;
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
        return "";
    }
    public static String getCharacterDataFromElement2(Element e) {
        Node child = e.getFirstChild();
        try {
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(child), new StreamResult(writer));
            return writer.getBuffer().toString();
        }
        catch (Exception e3)
        {

        }
        return "";
    }

}
