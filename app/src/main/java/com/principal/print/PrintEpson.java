package com.principal.print;
import android.app.Activity;
import android.app.AlertDialog;


import com.epson.eposprint.*;

import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.DemoSleeper;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by acer on 1/08/2018.
 */

public class PrintEpson {

    private static final int ALIGN_LEFT = 1;
    private static final int ALIGN_CENTER = 2;
    private static final int ALIGN_RIGHT = 3;
    private final static int FACTURA = 1;
    private final static int PEDIDO = 2;
    private final static int TRANSLADO = 3;
    private final static int REMISION=12;

    static final int SEND_TIMEOUT = 10 * 1000;
    static final int SIZEWIDTH_MAX = 8;
    static final int SIZEHEIGHT_MAX = 8;

    private  Print printer ;
    private Parametros parametrosPos;
    private Activity activity;
    private Factura_in factura_in;
    private Remision_in remision_in;
    private Pedido_in pedido_in;
    private  Builder builder;
    ArrayList<Traslado_in> listaTraslados;
    ArrayList<Factura_in> listaFacturas;
    ArrayList<Pedido_in> listaPedidos;
    ArrayList<Articulo> listaArticulos;
    ArrayList<Remision_in> listaRemisiones;
    private ArrayList<ArticulosPedido> listaAPedido;
    ArrayList<String> datos;
    private int operacion=0;
    private boolean printArticulos=false;
    private Pago pago;

    public PrintEpson() {

    }

    public Builder printFactura(Builder builder, Factura_in factura_in) {
        this.factura_in=factura_in;
        this.builder=builder;

            try { // Header
                asignaValor(getFillText(ALIGN_CENTER, 46, factura_in.getRazonSocial()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(getFillText(ALIGN_CENTER, 46, factura_in.getRepresentante()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(getFillText(ALIGN_CENTER, 46, factura_in.getRegimenNit()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor( getFillText(ALIGN_CENTER, 46, factura_in.getDireccionTel()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor( " Factura: " + getFillText(ALIGN_LEFT, 4, factura_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + factura_in.NFactura) + "   Caja: " + getFillText(ALIGN_LEFT, 3, "" + factura_in.getNCaja()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
                asignaValor( "   Fecha: " + getFillText(ALIGN_LEFT, 10, factura_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, factura_in.getHora()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
                asignaValor( " Cliente: " + getFillText(ALIGN_LEFT, 35, factura_in.getNombreCliente()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
                asignaValor( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, factura_in.getNitCliente()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);

                // BODY
                asignaValor("CANT          ARTICULO                   IVA    VR-UNT     TOTAL",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
                for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
                    ArticulosFactura ap=factura_in.getListaArticulos().get(i);
                    asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 36, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

                }
                //FOOTER
                asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+factura_in.getValorText()),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
                asignaValor("    Paga: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getCambio())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO"),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(" Efectivo: "+getFillText(ALIGN_LEFT, 10, factura_in.getValorText()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 42, "RELACION DE IMPUESTOS"),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(" Excludido: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase0())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" Base 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase5()))+"   Iva 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva5())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" Base 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase19()))+"  Iva 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva19())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" ImpoComo: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getImpoCmo())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, factura_in.getNombreVendedor()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, factura_in.getResDian()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, factura_in.getRango()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
                asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            } catch (Exception e) {
                return null;

            }

        return this.builder;
    }
    public Builder printRemision(Builder builder, Remision_in remision_in) {
        this.factura_in=factura_in;
        this.builder=builder;

        try { // Header
            asignaValor(getFillText(ALIGN_CENTER, 46, remision_in.getRazonSocial()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
            asignaValor(getFillText(ALIGN_CENTER, 46, remision_in.getRepresentante()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
          //  asignaValor(getFillText(ALIGN_CENTER, 46, remision_in.getRegimenNit()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
           // asignaValor( getFillText(ALIGN_CENTER, 46, remision_in.getDireccionTel()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
            asignaValor( " Cotizacion: " + getFillText(ALIGN_LEFT, 3, remision_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + remision_in.NRemision) ,Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
            asignaValor( "   Fecha: " + getFillText(ALIGN_LEFT, 10, remision_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, remision_in.getHora()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
            asignaValor( " Cliente: " + getFillText(ALIGN_LEFT, 35, remision_in.getNombreCliente()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);
            asignaValor( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, remision_in.getNitCliente()),Builder.FONT_B, Builder.FALSE,Builder.ALIGN_LEFT);

            // BODY
            asignaValor("CANT          ARTICULO                   IVA    VR-UNT     TOTAL",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
            for (int i = 0; i < remision_in.getListaArticulos().size(); i++) {
                ArticulosRemision ap=remision_in.getListaArticulos().get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 36, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

            }
            //FOOTER
            asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+remision_in.getValorText()),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
            asignaValor("    Paga: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getCambio())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO"),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_CENTER);
            asignaValor(" Efectivo: "+getFillText(ALIGN_LEFT, 10, remision_in.getValorText()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, remision_in.getNombreVendedor()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, remision_in.getResDian()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, remision_in.getRango()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
            asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        } catch (Exception e) {
            return null;

        }

        return this.builder;
    }
    private void asignaValor(String text, int font, int bold,int align )
    {
        try {

        builder.addTextFont(font);
        builder.addTextAlign(align);
        builder.addTextLineSpace(getBuilderLineSpace(text));
        builder.addTextLang(Builder.LANG_EN) ;
        builder.addTextSize(1,1);
        builder.addTextStyle(Builder.FALSE, Builder.FALSE, bold , Builder.COLOR_1);
        builder.addTextPosition(0);
        builder.addText(text);
        builder.addFeedUnit(30);
        } catch (Exception e) {

        }
    }

    public Builder printDocumentosRealizados(Builder builder,int operacion, boolean printArticulos, ArrayList<String> datos, ArrayList<Pedido_in> listaPedidos, ArrayList<Factura_in> listaFacturas, ArrayList<Traslado_in> listaTraslados, ArrayList<Articulo> listaArticulos, ArrayList<Remision_in> listaRemisiones)
    {
        this.operacion=operacion;
        this.printArticulos=printArticulos;
        this.datos=datos;
        this.listaFacturas=listaFacturas;
        this.listaTraslados=listaTraslados;
        this.listaArticulos=listaArticulos;
        this.listaPedidos=listaPedidos;
        this.listaRemisiones=listaRemisiones;
        boolean res=false;
        this.builder=builder;

        asignaValor(getFillText(ALIGN_CENTER, 46, datos.get(0)),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
        asignaValor(" Generado: "+getFillText(ALIGN_LEFT, 10, datos.get(1))+"    Hora: "+getFillText(ALIGN_LEFT, 5, datos.get(2)),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        asignaValor("    Desde: "+getFillText(ALIGN_LEFT, 10, datos.get(3))+"   Hasta: "+getFillText(ALIGN_LEFT, 10, datos.get(4)),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        if(printArticulos)
        {
            asignaValor("CANT             ARTICULO                                  TOTAL",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
            for (int i = 0; i < listaArticulos.size(); i++) {
                Articulo a=listaArticulos.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 43, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 13, getDecTxt(a.getValorVentas())),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

            }
        }
        else if(operacion==FACTURA &  !printArticulos)
        {
            asignaValor(" No.             CLIENTE            FECHA Y HORA          TOTAL ",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
          for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f =listaFacturas.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 30, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

            }
        }
        else if(operacion==REMISION &  !printArticulos)
        {
            asignaValor(" No.             CLIENTE            FECHA Y HORA          TOTAL ",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
            for (int i = 0; i < listaRemisiones.size(); i++) {
                Remision_in  f =listaRemisiones.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 30, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

            }
        }

        else if(operacion==PEDIDO & !printArticulos)
        {
            asignaValor(" No.             CLIENTE            FECHA Y HORA          TOTAL ",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido_in p =listaPedidos.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+p.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 30, ""+p.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, p.getFecha()+" "+p.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(p.valor)),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);


            }
        }

        if(printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalArticulos())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        }
        else if(operacion==FACTURA & !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalFacturas())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        }
        else if(operacion==REMISION & !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalRemisiones())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        }
        else if(operacion==PEDIDO& !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalPedidos())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        }
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

        return this.builder;
    }

    public Builder printPedido(Builder builder,Pedido_in pedido_in, ArrayList<ArticulosPedido> listaAPedido, Parametros parametrosPos)
    {
        this.listaAPedido=listaAPedido;
        this.builder=builder;
        this.pedido_in=pedido_in;
        asignaValor(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRazonSocial()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
        asignaValor(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRepresentante()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
        asignaValor("  Orden de pedido: "+getFillText(ALIGN_LEFT, 10,  ""+pedido_in.idCodigoInterno),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        asignaValor("            Fecha: "+getFillText(ALIGN_LEFT, 10, pedido_in.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pedido_in.getHora()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        asignaValor("          Cliente: "+getFillText(ALIGN_LEFT, 42, pedido_in.getNombreCliente()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);

        asignaValor("CANT          ARTICULO                          VR-UNT     TOTAL",Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        for (int i = 0; i < listaAPedido.size(); i++) {
            ArticulosPedido ap=listaAPedido.get(i);
            asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 36, ""+ap.getNombre())+"    "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

        }
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "SUBTOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getSubTotal())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "DESCUENTO: "+pedido_in.getFormatoDecimal(pedido_in.getDescuentoTotal())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getValor())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor()),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);



        return builder;
    }

    public Builder printPago(Builder builder,Pago pago, Parametros parametros)
    {
        this.builder=builder;
        this.pago=pago;
        this.parametrosPos=parametros;

        asignaValor(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRazonSocial()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
        asignaValor(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRepresentante()),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);
        asignaValor(getFillText(ALIGN_CENTER, 46,   "COMPROBANTE DE PAGO"),Builder.FONT_A, Builder.TRUE,Builder.ALIGN_CENTER);

        asignaValor("No. Pago: "+getFillText(ALIGN_CENTER, 11, pago.getIdPago()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        asignaValor("   Fecha: "+getFillText(ALIGN_LEFT, 10, pago.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, parametrosPos.getHora()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
        asignaValor(" Cliente: "+getFillText(ALIGN_LEFT, 35, pago.getNombreCliente()),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);

        asignaValor(getFillText(ALIGN_CENTER, 46,   "DESCRIPCION"),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);


        if(pago.getListaPagosFactura()!=null)
        {
            for (int i = 0; i < pago.getListaPagosFactura().size(); i++) {
                PagosFactura pagosFactura=pago.getListaPagosFactura().get(i);
                asignaValor(getFillText(ALIGN_CENTER, 46,   "______________________________________________"),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(getFillText(ALIGN_CENTER, 46,   " Factura: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNFactura()+"")+"     Caja: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNCaja()+"")),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(getFillText(ALIGN_CENTER, 46,   "               Saldo Anterior: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldoAnterior()))),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_CENTER);
                asignaValor(getFillText(ALIGN_CENTER, 46,   " FORMA DE PAGO         VALOR        "),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
                if(pagosFactura.getListaPagoFac()!=null)
                {
                    for (int j = 0; j < pagosFactura.getListaPagoFac().size(); j++) {
                        ItemPagoFac itemPagoFac=pagosFactura.getListaPagoFac().get(j);
                        asignaValor(getFillText(ALIGN_CENTER, 46, " "+getFillText(ALIGN_LEFT, 20, ""+itemPagoFac.getFormaPago())+" "+getFillText(ALIGN_LEFT, 30, getDecTxt(itemPagoFac.getValor()))),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);
                    }
                }
                asignaValor(getFillText(ALIGN_CENTER, 46, "                    Descuento: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getDescuento()))),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);

                asignaValor(getFillText(ALIGN_CENTER, 46, "                  Nuevo Saldo: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldo()))),Builder.FONT_B, Builder.TRUE,Builder.ALIGN_LEFT);


            }
        }
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, " _____________________________________________"),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_CENTER );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_LEFT, 45, "TOTAL: "+getDecTxt(pago.getValor()))),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_LEFT, 45, "SALDO A LA FECHA: "+getDecTxt(pago.getDeudaCliente()))),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);

        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_CENTER, 66, "FIRMA AUTORIZADA:____________________________________________")),Builder.FONT_A,Builder.TRUE,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "),Builder.FONT_B,Builder.FALSE,Builder.ALIGN_CENTER);





         return this.builder;
    }



      private long getTotalArticulos()
    {
        long res=0;
        for (int i = 0; i < listaArticulos.size(); i++) {
            Articulo a=listaArticulos.get(i);
            res+=a.getValorVentas();
        }
        return res;
    }
    private long getTotalTraslados()
    {
        long res=0;
        for (int i = 0; i < listaTraslados.size(); i++) {
            Traslado_in t=listaTraslados.get(i);
            res+=t.getTotalTraslado();
        }
        return res;
    }
    private long getTotalPedidos()
    {
        long res=0;
        for (int i = 0; i < listaPedidos.size(); i++) {
            Pedido_in t=listaPedidos.get(i);
            res+=t.getValor();
        }
        return res;
    }
    private long getTotalFacturas()
    {
        long res=0;
        for (int i = 0; i < listaFacturas.size(); i++) {
            Factura_in f=listaFacturas.get(i);
            res+=f.getValor();
        }
        return res;
    }
    private long getTotalRemisiones()
    {
        long res=0;
        for (int i = 0; i < listaRemisiones.size(); i++) {
            Remision_in  f=listaRemisiones.get(i);
            res+=f.getValor();
        }
        return res;
    }


    private String getFillText(int align, int length,String text)
    {
        String res="";
        if(text.length()>=length)
        {
            res=text.substring(0, length);
        }
        else
        {
            if(align==ALIGN_CENTER)
            {
                int esp=length-text.length();
                for (int i = 0; i < (esp/2); i++) {
                    res+=" ";
                }
                res+=text+res;
            }
            else if(align==ALIGN_LEFT)
            {
                int esp=length-text.length();
                for (int i = 0; i < esp; i++) {
                    res+=" ";
                }
                res=text+res;
            }
            else if((align==ALIGN_RIGHT))
            {
                int esp=length-text.length();
                for (int i = 0; i < esp; i++) {
                    res+=" ";
                }
                res+=text;
            }
        }

        return res;
    }
    public String getDecTxt(long value) {
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return decimalFormat.format(value);
    }


    private int getBuilderLineSpace(String text) {
        try{
            return Integer.parseInt(text.toString());
        }catch(Exception e){
            return 0;
        }
    }




}
