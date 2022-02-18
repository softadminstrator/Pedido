package com.principal.print;

import android.app.Activity;

import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.Builder;
import com.epson.eposprint.Print;
import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.CierreTurno;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 7/09/2018.
 */

public class PrintBixolon {
    static BixolonPrinter mBixolonPrinter;
    private static final int ALIGN_LEFT = 1;
    private static final int ALIGN_CENTER = 2;
    private static final int ALIGN_RIGHT = 3;
    private final static int FACTURA = 1;
    private final static int PEDIDO = 2;
    private final static int TRANSLADO = 3;
    private final static int PRESTAMOS=8;
    private final static int ABONOPRESTAMOS=9;
    private final static int VERLIBRO=10;
    private final static int REMISION=12;

    static final int SEND_TIMEOUT = 10 * 1000;
    static final int SIZEWIDTH_MAX = 8;
    static final int SIZEHEIGHT_MAX = 8;

    private Print printer ;
    private Parametros parametrosPos;
    private Activity activity;
    private Factura_in factura_in;
    private Remision_in remision_in;

    private Pedido_in pedido_in;
    private Builder builder;
    ArrayList<Traslado_in> listaTraslados;
    ArrayList<Factura_in> listaFacturas;
    ArrayList<Pedido_in> listaPedidos;
    ArrayList<Articulo> listaArticulos;
    private ArrayList<ArticulosPedido> listaAPedido;
    ArrayList<Libro> listaLibros;
    ArrayList<Remision_in> listaRemisiones;

    private CierreTurno cierreTurno;



    ArrayList<String> datos;
    private int operacion=0;
    private boolean printArticulos=false;
    private Pago pago;
    private Prestamo prestamo;
    private PagoPrestamo pagoPrestamo;
    private Libro libro;

    public PrintBixolon()
    {

    }

    public Builder printFactura(BixolonPrinter mBixolonPrinter, Factura_in factura_in) {
        this.factura_in=factura_in;
        this.mBixolonPrinter=mBixolonPrinter;

        try { // Header
            asignaValorBixolon(getFillText(ALIGN_CENTER, 46, factura_in.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 46, factura_in.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 46, factura_in.getRegimenNit()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon( getFillText(ALIGN_CENTER, 46, factura_in.getDireccionTel()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon( "D.E / P.O.S. : " + getFillText(ALIGN_LEFT, 4, factura_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + factura_in.NFactura) + "   Caja: " + getFillText(ALIGN_LEFT, 3, "" + factura_in.getNCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "   Fecha: " + getFillText(ALIGN_LEFT, 10, factura_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, factura_in.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( " Cliente: " + getFillText(ALIGN_LEFT, 35, factura_in.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, factura_in.getNitCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            // se agrega linea para estaciones de trabajo
            //asignaValorBixolon( "   Placa:"+getFillText(ALIGN_LEFT, 46, factura_in.getObservaciones()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            if(factura_in.getObservaciones().length()>0) {
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46, "Placa: " + factura_in.getObservaciones()), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, BixolonPrinter.ALIGNMENT_CENTER);
            }

            // BODY
            asignaValorBixolon("CANT          ARTICULO                  IVA    VR-UNT     TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
                ArticulosFactura ap=factura_in.getListaArticulos().get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 6, ""+ap.getCantidadText())+" "+getFillText(ALIGN_LEFT, 32, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 10, ap.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
            //FOOTER
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+factura_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
            asignaValorBixolon("    Paga: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getCambio())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" Efectivo: "+getFillText(ALIGN_LEFT, 10, factura_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 42, "RELACION DE IMPUESTOS"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" Excludido: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase0())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            if (factura_in.getBase5()>0) {
                asignaValorBixolon(" Base 5%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase5())) + "   Iva 5%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva5())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);
            }
            if (factura_in.getBase19()>0) {
                asignaValorBixolon(" Base 19%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase19())) + "  Iva 19%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva19())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);
            }
            //asignaValorBixolon(" ImpoComo: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getImpoCmo())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
           // asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, factura_in.getNombreVendedor()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, factura_in.getResDian()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, factura_in.getRango()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
          // asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            } catch (Exception e) {
            return null;

        }

        return this.builder;
    }

    public Builder printRemision(BixolonPrinter mBixolonPrinter, Remision_in remision_in) {
        this.remision_in=remision_in;
        this.mBixolonPrinter=mBixolonPrinter;

        try { // Header
            asignaValorBixolon(getFillText(ALIGN_CENTER, 46, remision_in.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 46, remision_in.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
           // asignaValorBixolon(getFillText(ALIGN_CENTER, 46, remision_in.getRegimenNit()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
           // asignaValorBixolon( getFillText(ALIGN_CENTER, 46, remision_in.getDireccionTel()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon( " Cotización: " + getFillText(ALIGN_LEFT, 3, remision_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + remision_in.NRemision) ,BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "   Fecha: " + getFillText(ALIGN_LEFT, 10, remision_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, remision_in.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( " Cliente: " + getFillText(ALIGN_LEFT, 35, remision_in.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, remision_in.getNitCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);

            // BODY
            asignaValorBixolon("CANT          ARTICULO                  IVA    VR-UNT     TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < remision_in.getListaArticulos().size(); i++) {
                ArticulosRemision ap=remision_in.getListaArticulos().get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 34, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 10, ap.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
            //FOOTER
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+remision_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
            asignaValorBixolon("    Paga: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getCambio())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" Efectivo: "+getFillText(ALIGN_LEFT, 10, remision_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, remision_in.getNombreVendedor()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            // imprime observaciones de la cotizacion
            asignaValorBixolon(" "+getFillText(ALIGN_LEFT, 60, remision_in.getObservaciones()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);


        } catch (Exception e) {
            return null;

        }

        return this.builder;
    }

    public Builder printFacturaSimplificado(BixolonPrinter mBixolonPrinter, Factura_in factura_in) {
        this.factura_in=factura_in;
        this.mBixolonPrinter=mBixolonPrinter;

        try { // Header
            asignaValorBixolon(getFillText(ALIGN_CENTER, 29, factura_in.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
             asignaValorBixolon( " Factura: " + getFillText(ALIGN_LEFT, 3, factura_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + factura_in.NFactura) + "   Caja: " + getFillText(ALIGN_LEFT, 3, "" + factura_in.getNCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "   Fecha: " + getFillText(ALIGN_LEFT, 10, factura_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, factura_in.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( " Cliente: " + getFillText(ALIGN_LEFT, 29, factura_in.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( "C.C./Nit: " + getFillText(ALIGN_LEFT, 29, factura_in.getNitCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);

            // BODY
            asignaValorBixolon("CANT    ARTICULO      VR-UNT  TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
                ArticulosFactura ap=factura_in.getListaArticulos().get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 2, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 27, ""+ap.getNombre()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
                asignaValorBixolon(getFillText(ALIGN_RIGHT, 10, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 10, ap.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
            //FOOTER
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, "TOTAL: "+factura_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
            asignaValorBixolon("    Paga: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getDineroRecibido())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A,Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon("    Cambio: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getCambio())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A,Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
          //  asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "MEDIOS DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
           // asignaValorBixolon(" Efectivo: "+getFillText(ALIGN_LEFT, 10, factura_in.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 42, "RELACION DE IMPUESTOS"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" Excludido: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase0())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" Base 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase5()))+"   Iva 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva5())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" Base 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase19()))+"  Iva 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva19())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            //asignaValorBixolon(" ImpoComo: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getImpoCmo())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, factura_in.getNombreVendedor()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "Observaciones: "+factura_in.getObservaciones()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A,Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 29, factura_in.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 29, factura_in.getRegimenNit()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);



            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "RECIBIDO:______________________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_LEFT);
            asignaValorBixolon( getFillText(ALIGN_CENTER, 29, factura_in.getDireccionTel()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);

            asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
        } catch (Exception e) {
            return null;

        }

        return this.builder;
    }



    private void asignaValorBixolon(String text, int font, int bold,int align )
    {
        try {
            mBixolonPrinter.printText(text+"\n ",align , font|
                            bold,
                    BixolonPrinter.TEXT_SIZE_HORIZONTAL1 |
                            BixolonPrinter.TEXT_SIZE_VERTICAL1, true);
        } catch (Exception e) {

        }
    }

    public Builder printDocumentosRealizados(BixolonPrinter mBixolonPrinter,int operacion, boolean printArticulos, ArrayList<String> datos, ArrayList<Pedido_in> listaPedidos, ArrayList<Factura_in> listaFacturas, ArrayList<Traslado_in> listaTraslados, ArrayList<Articulo> listaArticulos, ArrayList <Libro> listaLibros, ArrayList<Remision_in> listaRemisiones)
    {
        this.operacion=operacion;
        this.printArticulos=printArticulos;
        this.datos=datos;
        this.listaFacturas=listaFacturas;
        this.listaTraslados=listaTraslados;
        this.listaArticulos=listaArticulos;
        this.listaPedidos=listaPedidos;
        this.listaLibros=listaLibros;
        this.listaRemisiones=listaRemisiones;
        boolean res=false;
        this.mBixolonPrinter=mBixolonPrinter;

        asignaValorBixolon(getFillText(ALIGN_CENTER, 28, datos.get(0)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" Generado: "+getFillText(ALIGN_LEFT, 10, datos.get(1))+"    Hora: "+getFillText(ALIGN_LEFT, 5, datos.get(2)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("    Desde: "+getFillText(ALIGN_LEFT, 10, datos.get(3))+"   Hasta: "+getFillText(ALIGN_LEFT, 10, datos.get(4)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);

        if(printArticulos)
        {
           if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS)
            {
                asignaValorBixolon("CANT       MOVIMIENTO          TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
                for (int i = 0; i < listaArticulos.size(); i++) {
                    Articulo a=listaArticulos.get(i);
                    asignaValorBixolon(""+getFillText(ALIGN_LEFT, 4, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 16, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(a.getValorVentas())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

                }
            }
            else
            {
                asignaValorBixolon("CANT             ARTICULO                                  TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
                for (int i = 0; i < listaArticulos.size(); i++) {
                    Articulo a=listaArticulos.get(i);
                    asignaValorBixolon(""+getFillText(ALIGN_LEFT, 6, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 41, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 13, getDecTxt(a.getValorVentas())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

                }
            }

        }
        else if(operacion==FACTURA &  !printArticulos)
        {
            asignaValorBixolon(" No.             CLIENTE            FECHA Y HORA          TOTAL ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f =listaFacturas.get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 7, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 26, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
        }
        else if(operacion==REMISION &  !printArticulos)
        {
            asignaValorBixolon(" No.             CLIENTE            FECHA Y HORA          TOTAL ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaRemisiones.size(); i++) {
                Remision_in f =listaRemisiones.get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 4, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 29, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
        }
        else if(operacion==PEDIDO & !printArticulos)
        {
            asignaValorBixolon(" No.             CLIENTE            FECHA Y HORA          TOTAL ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido_in p =listaPedidos.get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 4, ""+p.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 29, ""+p.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, p.getFecha()+" "+p.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(p.valor)),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);


            }
        }
        else if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS & !printArticulos)
        {
            asignaValorBixolon(" No.   CLIENTE    FECHA      TOTAL ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaLibros.size(); i++) {
                Libro p =listaLibros.get(i);
                if(p.getMovDedito()>0) {
                    asignaValorBixolon("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro()) + "P " + getFillText(ALIGN_LEFT, 13, "" + p.getNombreCliente()) + " " + getFillText(ALIGN_RIGHT, 10, p.getFecha()  ) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(p.getMovDedito())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);
                }
                else
                {
                    asignaValorBixolon("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro()) + "A " + getFillText(ALIGN_LEFT, 13, "" + p.getNombreCliente()) + " " + getFillText(ALIGN_RIGHT, 10, p.getFecha() ) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(p.getMovCredito())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);
                }

            }
        }
        else if(operacion==VERLIBRO & !printArticulos)
        {
            if(listaLibros.size()>0) {
                asignaValorBixolon(" Cliente: " + getFillText(ALIGN_LEFT, 29,listaLibros.get(0).getNombreCliente() ), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, BixolonPrinter.ALIGNMENT_LEFT);
            }
            asignaValorBixolon(" No.   Fecha MovDebito MovCredito Total ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaLibros.size(); i++) {
                Libro p =listaLibros.get(i);
                    asignaValorBixolon("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro())  + getFillText(ALIGN_RIGHT, 5, p.getFecha() ) + " " + getFillText(ALIGN_RIGHT, 9, getDecTxt(p.getMovDedito()))+ " " + getFillText(ALIGN_RIGHT, 9, getDecTxt(p.getMovCredito()))+ " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(p.getSaldo())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);


            }
        }

        if(printArticulos)
        {
            if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS )
            {
                asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, "TOTAL: "+getDecTxt(getTotalArticulos())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
            }
            else
            {
               asignaValorBixolon(" " + getFillText(ALIGN_RIGHT, 40, "TOTAL: " + getDecTxt(getTotalArticulos())), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
           }
        }
        else if(operacion==FACTURA & !printArticulos)
        {
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalFacturas())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        }
        else if(operacion==REMISION & !printArticulos)
        {
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalRemisiones())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        }
        else if(operacion==PEDIDO& !printArticulos)
        {
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalPedidos())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        }
        else if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS & !printArticulos)
        {
            asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 20, "TOTAL: "+getDecTxt(getTotalMovimientos())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        }
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

        return this.builder;
    }


    public Builder printCierreTurno(BixolonPrinter mBixolonPrinter, ArrayList<Factura_in> listaFacturas, CierreTurno cierreTurno, boolean printArticulos,ArrayList<Articulo> listaArticulos)
    {

        this.listaFacturas=listaFacturas;
        this.cierreTurno=cierreTurno;
        boolean res=false;
        this.mBixolonPrinter=mBixolonPrinter;

        asignaValorBixolon(getFillText(ALIGN_CENTER, 28, "Cierre Turno"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon("      No. Cierre: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getNCierre())+"      No. Caja: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getNCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("           Fecha: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getFecha())+"          Hora: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon(" Factura Inicial: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getNFacturaInicial())+" Factura Final: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getNFacturaFinal()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);

        if(printArticulos)
        {
            asignaValorBixolon(getFillText(ALIGN_CENTER, 28, "LISTA DE ARTICULOS FACTURADOS "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);

            asignaValorBixolon("CANT             ARTICULO                               TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaArticulos.size(); i++) {
                Articulo a=listaArticulos.get(i);
                asignaValorBixolon(""+getFillText(ALIGN_LEFT, 7, ""+a.getCantidadVentasDouble())+" "+getFillText(ALIGN_LEFT, 38, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 13, getDecTxt(a.getValorVentas())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

            }
        }
        else {
            asignaValorBixolon(getFillText(ALIGN_CENTER, 28, "LISTA DE FACTURAS REALIZADAS"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);


            asignaValorBixolon(" No.             CLIENTE            FECHA Y HORA          TOTAL ", BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, BixolonPrinter.ALIGNMENT_LEFT);
            for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f = listaFacturas.get(i);
                asignaValorBixolon("" + getFillText(ALIGN_LEFT, 7, "" + f.NFactura) + " " + getFillText(ALIGN_LEFT, 26, "" + f.nombreCliente) + " " + getFillText(ALIGN_RIGHT, 16, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)), BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, Builder.FALSE, BixolonPrinter.ALIGNMENT_CENTER);

            }
        }
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalFacturas())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

        return this.builder;
    }










    public Builder printPedido(BixolonPrinter mBixolonPrinter,Pedido_in pedido_in, ArrayList<ArticulosPedido> listaAPedido, Parametros parametrosPos)
    {
        this.listaAPedido=listaAPedido;
        this.mBixolonPrinter=mBixolonPrinter;
        this.pedido_in=pedido_in;
        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon("  Orden de pedido: "+getFillText(ALIGN_LEFT, 10,  ""+pedido_in.idCodigoInterno),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("            Fecha: "+getFillText(ALIGN_LEFT, 10, pedido_in.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pedido_in.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("          Cliente: "+getFillText(ALIGN_LEFT, 42, pedido_in.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("             Nota: "+getFillText(ALIGN_LEFT, 42, pedido_in.getObservaciones()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT );

        asignaValorBixolon("CANT          ARTICULO                        VR-UNT     TOTAL",BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        for (int i = 0; i < listaAPedido.size(); i++) {
            ArticulosPedido ap=listaAPedido.get(i);
            asignaValorBixolon(""+getFillText(ALIGN_LEFT, 6, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 32, ""+ap.getNombre())+"    "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

        }
       // asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "SUBTOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getSubTotal())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "DESCUENTO: "+pedido_in.getFormatoDecimal(pedido_in.getDescuentoTotal())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL.: "+pedido_in.getFormatoDecimal(pedido_in.getValor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, "RECIBIDO:______________________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);


        return builder;
    }

    public Builder printPago(BixolonPrinter mBixolonPrinter,Pago pago, Parametros parametros)
    {
        this.mBixolonPrinter=mBixolonPrinter;
        this.pago=pago;
        this.parametrosPos=parametros;


        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,  parametrosPos.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   "COMPROBANTE DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);

        asignaValorBixolon("No. Pago: "+getFillText(ALIGN_CENTER, 11, pago.getIdPago()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("   Fecha: "+getFillText(ALIGN_LEFT, 10, pago.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, parametrosPos.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon(" Cliente: "+getFillText(ALIGN_LEFT, 35, pago.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);

        asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   "DESCRIPCION"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);


        if(pago.getListaPagosFactura()!=null)
        {
            for (int i = 0; i < pago.getListaPagosFactura().size(); i++) {
                PagosFactura pagosFactura=pago.getListaPagosFactura().get(i);
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   "______________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   " Factura: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNFactura()+"")+"     Caja: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNCaja()+"")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   "               Saldo Anterior: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldoAnterior()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46,   " FORMA DE PAGO         VALOR        "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
                if(pagosFactura.getListaPagoFac()!=null)
                {
                    for (int j = 0; j < pagosFactura.getListaPagoFac().size(); j++) {
                        ItemPagoFac itemPagoFac=pagosFactura.getListaPagoFac().get(j);
                        asignaValorBixolon(getFillText(ALIGN_CENTER, 46, " "+getFillText(ALIGN_LEFT, 20, ""+itemPagoFac.getFormaPago())+" "+getFillText(ALIGN_LEFT, 30, getDecTxt(itemPagoFac.getValor()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
                    }
                }
                asignaValorBixolon(getFillText(ALIGN_CENTER, 46, "                    Descuento: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getDescuento()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);

                asignaValorBixolon(getFillText(ALIGN_CENTER, 46, "                  Nuevo Saldo: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldo()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);


            }
        }
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, " _____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_LEFT, 45, "TOTAL: "+getDecTxt(pago.getValor()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
      //  asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_LEFT, 45, "SALDO A LA FECHA: "+getDecTxt(pago.getDeudaCliente()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, "LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_CENTER, 66, "FIRMA AUTORIZADA:____________________________________________")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 64, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);





        return this.builder;
    }


    public Builder printPagoPrestamo(BixolonPrinter mBixolonPrinter, PagoPrestamo pagoPrestamo, Parametros parametros)
    {
        this.mBixolonPrinter=mBixolonPrinter;
        this.pagoPrestamo=pagoPrestamo;
        this.parametrosPos=parametros;


      //  asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
      //  asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
     //   asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(getFillText(ALIGN_CENTER, 30,   "COMPROBANTE DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
       // asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        asignaValorBixolon("No. Pago: "+getFillText(ALIGN_CENTER, 11, pagoPrestamo.getIdPagoPrestamo()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("   Fecha: "+getFillText(ALIGN_LEFT, 10, pagoPrestamo.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pagoPrestamo.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon(" CLIENTE ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(""+getFillText(ALIGN_CENTER, 29, pagoPrestamo.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

       asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getSaldoAnterior()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   VALOR ABONO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getValor()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getNuevoSaldo()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "LO ATENDIO "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29,  getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "FIRMA RECIBIDO:")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 30, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);






        return this.builder;
    }
    public Builder printPrestamo(BixolonPrinter mBixolonPrinter, Prestamo prestamo, Parametros parametros)
    {
        this.mBixolonPrinter=mBixolonPrinter;
        this.prestamo=prestamo;
        this.parametrosPos=parametros;


        //asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        //asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE PRESTAMO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        asignaValorBixolon("No. Prestamo: "+getFillText(ALIGN_CENTER, 11, prestamo.getIdPrestamo()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("   Fecha: "+getFillText(ALIGN_LEFT, 10, prestamo.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, prestamo.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon(" CLIENTE ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(""+getFillText(ALIGN_CENTER, 29, prestamo.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(getFillText(ALIGN_CENTER, 26,   "CONCEPTO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(getFillText(ALIGN_CENTER, 26,   prestamo.getObjeto()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getSaldoAnterior()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "VALOR PRESTAMO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getValorPrestamo()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getNuevoSaldo()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "LO ATENDIO "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29,  getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

       //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "FIRMA RECIBIDO:")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 30, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);





        return this.builder;
    }

    public Builder printLibro(BixolonPrinter mBixolonPrinter, Libro libro, Parametros parametros)
    {
        this.mBixolonPrinter=mBixolonPrinter;
        this.libro=libro;
        this.parametrosPos=parametros;


       // asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRazonSocial()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        //asignaValorBixolon(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRepresentante()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        if(libro.getMovDedito()>0)
        {
            asignaValorBixolon(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE DE VENTA"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        }
        else
        {
            asignaValorBixolon(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE DE PAGO"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A , BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        }


        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        asignaValorBixolon("No. Comprabante: "+getFillText(ALIGN_CENTER, 11, libro.getIdLibro()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon("   Fecha: "+getFillText(ALIGN_LEFT, 10, libro.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, libro.getHora()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_LEFT);
        asignaValorBixolon(" CLIENTE ",BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(""+getFillText(ALIGN_CENTER, 29, libro.getNombreCliente()),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

            asignaValorBixolon(getFillText(ALIGN_CENTER, 26, "CONCEPTO"), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, BixolonPrinter.ALIGNMENT_CENTER);
            asignaValorBixolon(getFillText(ALIGN_CENTER, 26, libro.getConcepto()), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, BixolonPrinter.ALIGNMENT_CENTER);

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(libro.getSaldoAnterior()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        if(libro.getMovDedito()>0) {
            asignaValorBixolon(" " + getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "VALOR VENTA: ") + getFillText(ALIGN_RIGHT, 12, getDecTxt(libro.getMovDedito()))), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
          //  asignaValorBixolon(" " + getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
        }
        else
        {
            asignaValorBixolon(" " + getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   VALOR ABONO: ") + getFillText(ALIGN_RIGHT, 12, getDecTxt(libro.getMovCredito()))), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
            //asignaValorBixolon(" " + getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
        }
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(libro.getSaldo()))),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,BixolonPrinter.ALIGNMENT_CENTER );

        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29, "LO ATENDIO "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 29,  getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        //asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_LEFT, 26, "FIRMA RECIBIDO:")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValorBixolon(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 30, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);
        asignaValorBixolon(" "+getFillText(ALIGN_CENTER, 30, " "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B,Builder.FALSE,BixolonPrinter.ALIGNMENT_CENTER);





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
    private long getTotalMovimientos()
    {
        long res=0;
        for (int i = 0; i < listaLibros.size(); i++) {
            Libro t=listaLibros.get(i);
            res+=t.getMovCredito()+t.getMovDedito();
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
