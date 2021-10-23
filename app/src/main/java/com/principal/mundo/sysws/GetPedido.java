package com.principal.mundo.sysws;

import com.principal.mundo.Factura_in;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

/**
 * Created by acer on 22/09/2018.
 */

public class GetPedido {
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
    private static final String METHOD_NAME = "GetPedido";


    private Pedido pedido;
    /**
     * @param ip
     */
    public GetPedido(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public Pedido GetPedido(String NCaja, String NPedido)
    {


        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pridVendedor = new PropertyInfo();
            pridVendedor.name="NCaja";
            pridVendedor.type=String.class;
            pridVendedor.setValue(NCaja);

            PropertyInfo prTipo = new PropertyInfo();
            prTipo.name="NPedido";
            prTipo.type=String.class;
            prTipo.setValue(NPedido);

            request.addProperty(pridVendedor);
            request.addProperty(prTipo);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL);
            ht.call(NAMESPACE + METHOD_NAME, envelope);
             SoapObject ic = (SoapObject) envelope.getResponse();
                if (ic != null) {//comprobamos que la respuesta no esté vacía
                    //recorremos el objeto

                    pedido = new Pedido();
                    j++;
                    pedido.setItems(ic.getProperty("items").toString().trim());
                    pedido.setNPedido(ic.getProperty("NPedido").toString().trim());
                    pedido.setNCaja(ic.getProperty("NCaja").toString().trim());
                    pedido.setIdVendedor(ic.getProperty("idVendedor").toString().trim());
                    pedido.setIdCliente(ic.getProperty("idCliente").toString().trim());
                    pedido.setObservaciones(ic.getProperty("observaciones").toString().trim());
                    pedido.setEstado(ic.getProperty("estado").toString().trim());
                    pedido.setFecha(ic.getProperty("fecha").toString().trim());
                    pedido.setHora(ic.getProperty("hora").toString().trim());
                    pedido.setTotalPedido(ic.getProperty("totalPedido").toString().trim());
                    pedido.setNMesa(ic.getProperty("NMesa").toString().trim());
                    pedido.setDescuentoTotal(ic.getProperty("descuentoTotal").toString().trim());
                    pedido.setSubTotal(ic.getProperty("subTotal").toString().trim());
                    pedido.setNombreCliente(ic.getProperty("nombreCliente").toString().trim());
                    pedido.setNombreSeparador(ic.getProperty("nombreSeparador").toString().trim());
                    pedido.setNombreRevisor(ic.getProperty("nombreRevisor").toString().trim());
                    pedido.setNombreUsuario(ic.getProperty("nombreUsuario").toString().trim());
                    pedido.setNombreVendedor(ic.getProperty("nombreVendedor").toString().trim());
                    pedido.setIdSeparador(ic.getProperty("idSeparador").toString().trim());
                    pedido.setIdRevisor(ic.getProperty("idRevisor").toString().trim());
                    pedido.setObservacionAls(ic.getProperty("observacionAls").toString().trim());
                    pedido.setMunicipioCliente(ic.getProperty("municipioCliente").toString().trim());
                    pedido.setXmlArticulos(ic.getProperty("xmlArticulos").toString().trim());
                    pedido.setXmlObservacionesPedido(ic.getProperty("xmlObservacionesPedido").toString().trim());
                    pedido.setPrecioDefecto(ic.getProperty("precioDefecto").toString().trim());


                  /**  pedido.setItemsagotados(ic.getProperty("itemsagotados").toString().trim());
                    pedido.setItemscrudos(ic.getProperty("itemscrudos").toString().trim());
                    pedido.setItemspendientes(ic.getProperty("itemspendientes").toString().trim());
                    pedido.setItemsseparados(ic.getProperty("itemsseparados").toString().trim());
                    pedido.setNitCliente(ic.getProperty("nitCliente").toString().trim());
                   **/
                    pedido.getCargarArticulosAls();
                    pedido.setListaObservacionesPedido();

                }
                else
                {
                    pedido=null;
                }
//		 	SoapObject res = (SoapObject) envelope.getResponse();




        }
        catch (Exception e)
        {
            error=i+" " +j+" "+e.toString();
            return null;
        }
        return pedido;
    }

    public String getValorDia(String text)
    {
        if(text.equals("0"))
        {
            text="false";
        }
        else if(text.equals("1"))
        {
            text="true";
        }
        return text;

    }

    public String getError() {
        return error;
    }
}
