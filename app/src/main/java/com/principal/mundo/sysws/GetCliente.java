package com.principal.mundo.sysws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.principal.mundo.Cliente;

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
    private static final String METHOD_NAME = "GetCliente";
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
            prArticulos.name="IdCliente";
            prArticulos.type=String.class;
            prArticulos.setValue(IdCliente);
            request.addProperty(prArticulos);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);
            SoapObject ic = (SoapObject) envelope.getResponse();
            if (ic != null)
            {//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
                cliente.setIdCliente(Long.parseLong(ic.getProperty("IdCliente").toString().trim()));
                cliente.setNombre(ic.getProperty("NombreCliente").toString().trim());
                cliente.setRepresentante(ic.getProperty("Representante").toString().trim());
                cliente.setMail(ic.getProperty("Mail").toString().trim());
                cliente.setNit(ic.getProperty("Nit").toString().trim());
                cliente.setDireccion(ic.getProperty("Direccion").toString().trim());
                cliente.setTelefono(ic.getProperty("Telefono").toString().trim());
                cliente.setMunicipio(ic.getProperty("Municipio").toString().trim());
                cliente.setBarrio(ic.getProperty("Barrio").toString().trim());
                cliente.setTipoCanal(ic.getProperty("TipoCanal").toString().trim());
                cliente.setTipoPersona(ic.getProperty("TipoPersona").toString().trim());
                cliente.setPrimerApellido(ic.getProperty("PrimerApellido").toString().trim());
                cliente.setSegundoApellido(ic.getProperty("SegundoApellido").toString().trim());
                cliente.setPrimerNombre(ic.getProperty("PrimerNombre").toString().trim());
                cliente.setSegundoNombre(ic.getProperty("SegundoNombre").toString().trim());
                cliente.setRazonSocial(ic.getProperty("RazonSocial").toString().trim());
                cliente.setIdMpio(ic.getProperty("idMpio").toString().trim());
                cliente.setIdDpto(ic.getProperty("IdDpto").toString().trim());
                cliente.setIdVendedor(ic.getProperty("idVendedor").toString().trim());
                              }
            else
            {
                cliente=null;
            }
        }
        catch (Exception e)
        {

            res=e.toString();
        }
        return cliente;
    }
}
