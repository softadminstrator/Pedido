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

public class GetPedidosVendedor {
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
    // se actualiza servidor para obtener pedidos por municipio
    private static final String METHOD_NAME = "GetPedidosVendedor3";
    //private static final String METHOD_NAME = "GetPedidosVendedor";
   // private static final String METHOD_NAME = "GetPedidosVendedor2";


    private Pedido pedido;
    /**
     * @param ip
     */
    public GetPedidosVendedor(String ip, String Webid)
    {
        URL="http://"+ip+":8083/WSCashServer" +Webid+"/services/OperatorClass?wsdl";
       // URL="http://"+ip+":8083/WSCashServer2/services/OperatorClass?wsdl";
    }

    /**
     * Metodo que se encarga de realizar el llamado al servicio web del sistema PosStar para obtener
     * los clientes actualizados del vendedor al que pertenece la cedula que le ingresa como parametro
     * y los almacena en un arreglo

     */
    public ArrayList<Pedido> GetPedidosVendedor(String idVendedor, String Tipo, String idRuta, String IdDpto, String IdMpio)
    {

        ArrayList<Pedido> l = new ArrayList<Pedido>();
        int i=0;
        int j=0;
        try
        {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pridVendedor = new PropertyInfo();
            pridVendedor.name="idVendedor";
            pridVendedor.type=String.class;
            pridVendedor.setValue(idVendedor);

            PropertyInfo prTipo = new PropertyInfo();
            prTipo.name="Tipo";
            prTipo.type=String.class;
            prTipo.setValue(Tipo);

            PropertyInfo pridRuta= new PropertyInfo();
            pridRuta.name="idRuta";
            pridRuta.type=String.class;
            pridRuta.setValue(idRuta);

            PropertyInfo prIdDpto= new PropertyInfo();
            prIdDpto.name="IdDpto";
            prIdDpto.type=String.class;
            prIdDpto.setValue(IdDpto);

            PropertyInfo prIdMpio= new PropertyInfo();
            prIdMpio.name="IdMpio";
            prIdMpio.type=String.class;
            prIdMpio.setValue(IdMpio);


            request.addProperty(pridVendedor);
            request.addProperty(prTipo);
            request.addProperty(pridRuta);
            request.addProperty(prIdDpto);
            request.addProperty(prIdMpio);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            org.ksoap2.transport.HttpTransportSE ht = new org.ksoap2.transport.HttpTransportSE(URL,60000);
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
                        pedido=new Pedido();	j++;
                        SoapObject ic = (SoapObject)res.get(i);		j++;
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
                        if (i==0) {
                            pedido.setXmlArticulos(ic.getProperty("xmlArticulos").toString().trim());
                            pedido.setXmlCategorias(ic.getProperty("xmlCategorias").toString().trim());
                        }
                        pedido.setItemsagotados(ic.getProperty("itemsagotados").toString().trim());
                        pedido.setItemscrudos(ic.getProperty("itemscrudos").toString().trim());
                        pedido.setItemspendientes(ic.getProperty("itemspendientes").toString().trim());
                        pedido.setItemsseparados(ic.getProperty("itemsseparados").toString().trim());
                        pedido.setNitCliente(ic.getProperty("nitCliente").toString().trim());


                        l.add(pedido);
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

                    pedido=new Pedido();	j++;
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

                    pedido.setItemsagotados(ic.getProperty("itemsagotados").toString().trim());
                    pedido.setItemscrudos(ic.getProperty("itemscrudos").toString().trim());
                    pedido.setItemspendientes(ic.getProperty("itemspendientes").toString().trim());
                    pedido.setItemsseparados(ic.getProperty("itemsseparados").toString().trim());
                    pedido.setNitCliente(ic.getProperty("nitCliente").toString().trim());

                    pedido.setXmlArticulos(ic.getProperty("xmlArticulos").toString().trim());
                    pedido.setXmlCategorias(ic.getProperty("xmlCategorias").toString().trim());

                    l.add(pedido);
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
