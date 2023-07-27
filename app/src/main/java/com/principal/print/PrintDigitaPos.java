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
import com.principal.mundo.Medios;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.DataForSendToPrinterPos80;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.Normalizer;
import java.util.Locale;

public class PrintDigitaPos {

    public static IMyBinder binder;
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
   
    ArrayList<Traslado_in> listaTraslados;
    ArrayList<Factura_in> listaFacturas;
    ArrayList<Pedido_in> listaPedidos;
    ArrayList<Articulo> listaArticulos;
    private ArrayList<ArticulosPedido> listaAPedido;
    ArrayList<Libro> listaLibros;
    ArrayList<Remision_in> listaRemisiones;

    ArrayList<Pago> listaPagos;

    private CierreTurno cierreTurno;



    ArrayList<String> datos;
    private int operacion=0;
    private boolean printArticulos=false;
    private Pago pago;
    private Prestamo prestamo;
    private PagoPrestamo pagoPrestamo;
    private Libro libro;

    //Imprimir pedido
    //-----------------------------------------------------------------

    public void printPedido(IMyBinder binder, Pedido_in pedido_in, ArrayList<ArticulosPedido> listaAPedido, Parametros parametrosPos)
    {
        this.binder=binder;
        //asignaValor("123456789012345678901234567890123456789012345678"); //48
       // asignaValor("JDJdsfszjksdfjkalkdiqerknmdKJKLOOO000056sadfaklf");//48

        asignaValor(getFillText(ALIGN_CENTER, 48,  parametrosPos.getRazonSocial()));
        asignaValor(getFillText(ALIGN_CENTER, 48,  parametrosPos.getRepresentante()));
        asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
        asignaValor("    Orden de pedido: "+getFillText(ALIGN_LEFT, 27,  ""+pedido_in.idCodigoInterno));
        asignaValor("    Fecha: "+getFillText(ALIGN_LEFT, 10, pedido_in.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pedido_in.getHora()));
        asignaValor("    Cliente: "+getFillText(ALIGN_LEFT, 35, pedido_in.getNombreCliente()));
        asignaValor("    Nota: "+getFillText(ALIGN_LEFT, 42, pedido_in.getObservaciones()));

        asignaValor(""+getFillText(ALIGN_LEFT, 48,"CANT ARTICULO                   VR-UNT     TOTAL"));
        for (int i = 0; i < listaAPedido.size(); i++) {
            ArticulosPedido ap=listaAPedido.get(i);
            asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 23, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()));

        }
        asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
        asignaValor(""+getFillText(ALIGN_RIGHT, 48, "TOTAL.: "+getTotalPedido(listaAPedido)));
        asignaValor(" "+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor()));

        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, "RECIBIDO:______________________________________"));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));

    }

    //Imprimir factura
    //-----------------------------------------------------------------------------

    public void printFactura(IMyBinder binder,Factura_in factura_in) {

        this.binder=binder;
        try { // Header
            asignaValor(getFillText(ALIGN_CENTER, 48, factura_in.getRazonSocial()));
            asignaValor(getFillText(ALIGN_CENTER, 48, factura_in.getRepresentante()));
            asignaValor(getFillText(ALIGN_CENTER, 48, factura_in.getRegimenNit()));
            asignaValor( getFillText(ALIGN_CENTER, 48, factura_in.getDireccionTel()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
            asignaValor( "D.E / P.O.S. : " + getFillText(ALIGN_LEFT, 4, factura_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + factura_in.NFactura) + "   Caja: " + getFillText(ALIGN_LEFT, 3, "" + factura_in.getNCaja()));
            asignaValor( "   Fecha: " + getFillText(ALIGN_LEFT, 10, factura_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, factura_in.getHora()));
            asignaValor( " Cliente: " + getFillText(ALIGN_LEFT, 35, factura_in.getNombreCliente()));
            asignaValor( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, factura_in.getNitCliente()));
            // se agrega linea para estaciones de trabajo
            //asignaValor( "   Placa:"+getFillText(ALIGN_LEFT, 46, factura_in.getObservaciones()));

            if(factura_in.getObservaciones().length()>0) {
                asignaValor(getFillText(ALIGN_CENTER, 48, "Nota: " + factura_in.getObservaciones()));
            }
            asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
            // BODY
            asignaValor(""+getFillText(ALIGN_LEFT, 48,"CANT ARTICULO                I    VR-UNT   TOTAL"));
            for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
                ArticulosFactura ap=factura_in.getListaArticulos().get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidadText())+" "+getFillText(ALIGN_LEFT, 22, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+""+getFillText(ALIGN_RIGHT, 8, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()));

            }
            //FOOTER
            asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
            asignaValor(""+getFillText(ALIGN_RIGHT, 48, "TOTAL: "+getTotalFactura(factura_in.getListaArticulos())));
            asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));
            asignaValor("    Paga: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getCambio())));
            asignaValor(""+getFillText(ALIGN_CENTER, 48, "MEDIOS DE PAGO"));
            asignaValor(" Efectivo: "+getFillText(ALIGN_LEFT, 10, factura_in.getValorText()));
            asignaValor(""+getFillText(ALIGN_CENTER, 48, "RELACION DE IMPUESTOS"));
            asignaValor(" Excludido: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase0())));

            if (factura_in.getBase5()>0) {
                asignaValor(" Base 5%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase5())) + "   Iva 5%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva5())));
            }
            if (factura_in.getBase19()>0) {
                asignaValor(" Base 19%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase19())) + "  Iva 19%: " + getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva19())));
            }

            asignaValor(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, factura_in.getNombreVendedor()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, factura_in.getResDian()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, factura_in.getRango()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, "RECIBIDO:______________________________________"));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));

        } catch (Exception e) {


        }
        
        
        //Imprime remision

     

        
    }

    public void printRemision(IMyBinder binder, Remision_in remision_in) {
        this.binder=binder;

        try { // Header
            asignaValor(getFillText(ALIGN_CENTER, 48, remision_in.getRazonSocial()));
            asignaValor(getFillText(ALIGN_CENTER, 48, remision_in.getRepresentante()));
            // asignaValor(getFillText(ALIGN_CENTER, 46, remision_in.getRegimenNit()));
            // asignaValor( getFillText(ALIGN_CENTER, 46, remision_in.getDireccionTel()));
            asignaValor( " Cotización: " + getFillText(ALIGN_LEFT, 3, remision_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + remision_in.NRemision) );
            asignaValor( "   Fecha: " + getFillText(ALIGN_LEFT, 10, remision_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, remision_in.getHora()));
            asignaValor( " Cliente: " + getFillText(ALIGN_LEFT, 35, remision_in.getNombreCliente()));
            asignaValor( "C.C./Nit: " + getFillText(ALIGN_LEFT, 35, remision_in.getNitCliente()));

            // BODY
            asignaValor(""+getFillText(ALIGN_LEFT, 48,"CANT ARTICULO                   VR-UNT     TOTAL"));
            for (int i = 0; i < remision_in.getListaArticulos().size(); i++) {
                ArticulosRemision ap=remision_in.getListaArticulos().get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 23, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText()));

            }
            //FOOTER
            asignaValor(""+getFillText(ALIGN_RIGHT, 48, "TOTAL: "+remision_in.getValorText()) );
            asignaValor("    Paga: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getCambio())));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, "MEDIOS DE PAGO"));
            asignaValor(" Efectivo: "+getFillText(ALIGN_LEFT, 10, remision_in.getValorText()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, remision_in.getNombreVendedor()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            // imprime observaciones de la cotizacion
            asignaValor(" "+getFillText(ALIGN_LEFT, 48, remision_in.getObservaciones()));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, "RECIBIDO:______________________________________"));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));



        } catch (Exception e) {


        }

    }


    public void printDocumentosRealizados(IMyBinder binder, int operacion, boolean printArticulos, ArrayList<String> datos, ArrayList<Pedido_in> listaPedidos, ArrayList<Factura_in> listaFacturas, ArrayList<Traslado_in> listaTraslados, ArrayList<Articulo> listaArticulos, ArrayList <Libro> listaLibros, ArrayList<Remision_in> listaRemisiones, Parametros parametrosPos, ArrayList<Medios> listaMedios)
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
        this.parametrosPos=parametrosPos;
        boolean res=false;
        this.binder=binder;

        asignaValor(getFillText(ALIGN_CENTER, 30, datos.get(0)));
        asignaValor(" Generado: "+getFillText(ALIGN_LEFT, 5, datos.get(1))+"  Hora: "+getFillText(ALIGN_LEFT, 5, datos.get(2)));
        asignaValor(" Desde: "+getFillText(ALIGN_LEFT, 5, datos.get(3))+"  Hasta: "+getFillText(ALIGN_LEFT, 5, datos.get(4)));

        if(printArticulos)
        {
            if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS)
            {
                asignaValor("CANT       MOVIMIENTO                  TOTAL    ");
                for (int i = 0; i < listaArticulos.size(); i++) {
                    Articulo a=listaArticulos.get(i);
                    asignaValor(""+getFillText(ALIGN_LEFT, 4, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 16, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(a.getValorVentas())));

                }
            }
            else
            {
                asignaValor(getFillText(ALIGN_CENTER, 48,"CANT        ARTICULO                 TOTAL "));
                for (int i = 0; i < listaArticulos.size(); i++) {
                    Articulo a=listaArticulos.get(i);
                    asignaValor(""+getFillText(ALIGN_LEFT, 6, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 28, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 12, getDecTxt(a.getValorVentas())));

                }
            }

        }
        else if(operacion==FACTURA &  !printArticulos)
        {
            //Divide facturas de credito y contado

            asignaValor(" No.      CLIENTE       FECHA Y HORA   TOTAL");



            // Remisiones de  contado
            // Detalla Remisiones x Medio de pago
            for (int i = 0; i < listaMedios.size(); i++) {
                Medios medio =listaMedios.get(i);
                long temTotMedio=0;
                for (int j = 0; j < listaFacturas.size(); j++) {
                    Factura_in f =listaFacturas.get(j);
                    if(f.getMedioDePago().equals("")) {
                        asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                        temTotMedio=temTotMedio+f.valor;
                    }
                    else if(f.getMedioDePago().equals(medio.getNombre())) {
                        asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                        temTotMedio=temTotMedio+f.valor;
                    }

                }
                if(temTotMedio>0)
                {
                    asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "Total "+medio.getNombre()+": " + getDecTxt(temTotMedio)));
                }


            }

/*
            // Facturas de  contado
            for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f =listaFacturas.get(i);
                if(f.getPagada().equals("SI")) {
                    asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                }
            }
*/
            asignaValor("____________________________________________");



            //Total facturas contado
            if(getTotalFacturasContado()>0) {
                asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "TOTAL FACTURAS CONTADO: " + getDecTxt(getTotalFacturasContado())));
            }

            // Facturas de  credito
            for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f =listaFacturas.get(i);
                if(f.getPagada().equals("NO")) {
                    asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                }
            }
            //Total facturas credito
            if(getTotalFacturasCredito()>0) {
                asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "TOTAL FACTURAS CREDITO: " + getDecTxt(getTotalFacturasCredito())));
            }









        }
        else if(operacion==REMISION &  !printArticulos)
        {
            asignaValor(" No.          CLIENTE    FECHA Y HORA    TOTAL");


            // Remisiones de  contado
            // Detalla Remisiones x Medio de pago
            for (int i = 0; i < listaMedios.size(); i++) {
                Medios medio =listaMedios.get(i);
                long temTotMedio=0;
                for (int j = 0; j < listaRemisiones.size(); j++) {
                    Remision_in f =listaRemisiones.get(j);
                    if(f.getMedioDePago().equals("")) {
                        asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                        temTotMedio=temTotMedio+f.valor;
                    }
                    else if(f.getMedioDePago().equals(medio.getNombre())) {
                        asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                        temTotMedio=temTotMedio+f.valor;
                    }

                }
                if(temTotMedio>0)
                {
                    asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "Total "+medio.getNombre()+": " + getDecTxt(temTotMedio)));
                }

            }


           /* for (int i = 0; i < listaRemisiones.size(); i++) {
                Remision_in f =listaRemisiones.get(i);
                if(f.getPagada().equals("SI")) {
                    asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                }
            }
            */

            //Total Remisiones contado
            if(getTotalRemisionesContado()>0) {
                asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "TOTAL COTIZACIONES CONTADO: " + getDecTxt(getTotalRemisionesContado())));
            }

            // Remisiones de  credito
            for (int i = 0; i < listaRemisiones.size(); i++) {
                Remision_in f =listaRemisiones.get(i);
                if(f.getPagada().equals("NO")) {
                    asignaValor("" + getFillText(ALIGN_LEFT, 8, "" + f.idCodigoExterno) + " " + getFillText(ALIGN_LEFT, 17, "" + f.getDatoCliente(parametrosPos)) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));
                }
            }
            //Total Remisiones credito
            if(getTotalRemisionesCredito()>0) {
                asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "TOTAL COTIZACIONES CREDITO: " + getDecTxt(getTotalRemisionesCredito())));
            }




        }
        else if(operacion==PEDIDO & !printArticulos)
        {
            asignaValor(" No.          CLIENTE    FECHA Y HORA    TOTAL ");
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido_in p =listaPedidos.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 5, ""+p.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 19, ""+p.getDatoCliente(parametrosPos))+" "+getFillText(ALIGN_RIGHT, 10, p.getFecha()+" "+p.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(p.valor)));


            }
        }
        else if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS & !printArticulos)
        {
            asignaValor(" No. CLIENTE    FECHA   TOTAL ");
            for (int i = 0; i < listaLibros.size(); i++) {
                Libro p =listaLibros.get(i);
                if(p.getMovDedito()>0) {
                    asignaValor("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro()) + "P " + getFillText(ALIGN_LEFT, 13, "" + p.getNombreCliente()) + " " + getFillText(ALIGN_LEFT, 5, p.getFecha()  ) + " - " + getFillText(ALIGN_LEFT, 9, getDecTxt(p.getMovDedito())));
                }
                else
                {
                    asignaValor("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro()) + "A " + getFillText(ALIGN_LEFT, 13, "" + p.getNombreCliente()) + " " + getFillText(ALIGN_LEFT, 5, p.getFecha() ) + " - " + getFillText(ALIGN_LEFT, 9, getDecTxt(p.getMovCredito())));
                }

            }
        }
        else if(operacion==VERLIBRO & !printArticulos)
        {
            if(listaLibros.size()>0) {
                asignaValor(" Cliente: " + getFillText(ALIGN_LEFT, 29,listaLibros.get(0).getNombreCliente() ));
            }
            asignaValor("No. Fecha MovDeb MovCre   Total");
            for (int i = 0; i < listaLibros.size(); i++) {
                Libro p =listaLibros.get(i);
                asignaValor("" + getFillText(ALIGN_LEFT, 4, "" + p.getIdLibro())  + getFillText(ALIGN_RIGHT, 5, p.getFecha() ) + "  " + getFillText(ALIGN_LEFT, 8, getDecTxt(p.getMovDedito()))+ " " + getFillText(ALIGN_LEFT, 8, getDecTxt(p.getMovCredito()))+ " " + getFillText(ALIGN_LEFT, 8, getDecTxt(p.getSaldo())));


            }
        }
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        if(printArticulos)
        {
            if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS )
            {
                asignaValor(" "+getFillText(ALIGN_RIGHT, 47, "TOTAL: "+getDecTxt(getTotalArticulos())) );
            }
            else
            {
                asignaValor(" " + getFillText(ALIGN_RIGHT, 47, "TOTAL: " + getDecTxt(getTotalArticulos())));
            }
        }
        else if(operacion==FACTURA & !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 47, "TOTAL: "+getDecTxt(getTotalFacturas())) );
        }
        else if(operacion==REMISION & !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 47, "TOTAL: "+getDecTxt(getTotalRemisiones())) );
        }
        else if(operacion==PEDIDO& !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 47, "TOTAL: "+getDecTxt(getTotalPedidos())) );
        }
        else if(operacion==ABONOPRESTAMOS ||operacion==PRESTAMOS & !printArticulos)
        {
            asignaValor(" "+getFillText(ALIGN_RIGHT, 20, "TOTAL: "+getDecTxt(getTotalMovimientos())) );
        }
        asignaValor(" "+getFillText(ALIGN_CENTER, 64, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));

        
    }

    public void printInventario(IMyBinder binder, ArrayList<Articulo> listaArticulos, Parametros parametrosPos)
    {

        this.listaArticulos=listaArticulos;
        if(listaArticulos!=null) {

            boolean res = false;
            this.binder = binder;

            asignaValor(getFillText(ALIGN_CENTER, 48, "INVENTARIO"));
            asignaValor(" Generado: " + getFillText(ALIGN_LEFT, 10, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())) + "    Hora: " + getFillText(ALIGN_LEFT, 5, new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())));

            asignaValor(getFillText(ALIGN_CENTER, 48, "CANT        ARTICULO               "));
            for (int i = 0; i < listaArticulos.size(); i++) {
                Articulo a = listaArticulos.get(i);
                asignaValor("" + getFillText(ALIGN_LEFT, 6, "" + a.stock) + " " + getFillText(ALIGN_LEFT, 38, "" + a.nombre) + " ");

            }

            asignaValor(" " + getFillText(ALIGN_CENTER, 64, " "));
            asignaValor(" " + getFillText(ALIGN_CENTER, 48, " "));
            asignaValor(" " + getFillText(ALIGN_CENTER, 48, " "));
        }

    }


    public void printCierreTurno(IMyBinder binder, ArrayList<Factura_in> listaFacturas, CierreTurno cierreTurno, boolean printArticulos, ArrayList<Articulo> listaArticulos)
    {

        this.listaFacturas=listaFacturas;
        this.cierreTurno=cierreTurno;
        boolean res=false;
        this.binder=binder;

        asignaValor(getFillText(ALIGN_CENTER, 28, "Cierre Turno"));
        asignaValor("      No. Cierre: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getNCierre())+"      No. Caja: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getNCaja()));
        asignaValor("           Fecha: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getFecha())+"          Hora: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getHora()));
        asignaValor(" Factura Inicial: "+getFillText(ALIGN_LEFT, 10, cierreTurno.getNFacturaInicial())+" Factura Final: "+getFillText(ALIGN_LEFT, 5, cierreTurno.getNFacturaFinal()));

        if(printArticulos)
        {
            asignaValor(getFillText(ALIGN_CENTER, 48, "LISTA DE ARTICULOS FACTURADOS "));

            asignaValor("CANT       ARTICULO                 TOTAL");
            for (int i = 0; i < listaArticulos.size(); i++) {
                Articulo a=listaArticulos.get(i);
                asignaValor(""+getFillText(ALIGN_LEFT, 7, ""+a.getCantidadVentasDouble())+" "+getFillText(ALIGN_LEFT, 38, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 13, getDecTxt(a.getValorVentas())));

            }
        }
        else {
            asignaValor(getFillText(ALIGN_CENTER, 48, "LISTA DE FACTURAS REALIZADAS"));


            asignaValor(" No.       CLIENTE     FECHA Y HORA     TOTAL ");
            for (int i = 0; i < listaFacturas.size(); i++) {
                Factura_in f = listaFacturas.get(i);
                asignaValor("" + getFillText(ALIGN_LEFT, 7, "" + f.NFactura) + " " + getFillText(ALIGN_LEFT, 20, "" + f.nombreCliente) + " " + getFillText(ALIGN_RIGHT, 10, f.getFecha() + " " + f.hora) + " " + getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor)));

            }
        }
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+getDecTxt(getTotalFacturas())) );
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, "RECIBIDO:______________________________________"));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));

        
    }


    public void printPago(IMyBinder binder,Pago pago, Parametros parametros)
    {
        this.binder=binder;
        this.pago=pago;
        this.parametrosPos=parametros;


        asignaValor(getFillText(ALIGN_CENTER, 48,  parametrosPos.getRazonSocial()));
        asignaValor(getFillText(ALIGN_CENTER, 48,  parametrosPos.getRepresentante()));
        asignaValor(getFillText(ALIGN_CENTER, 48,   "COMPROBANTE DE PAGO"));
        asignaValor(" "+getFillText(ALIGN_CENTER, 47, "_______________________________________________"));

        asignaValor("No. Pago: "+getFillText(ALIGN_CENTER, 11, pago.getIdPago()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()));
        asignaValor("   Fecha: "+getFillText(ALIGN_LEFT, 10, pago.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, parametrosPos.getHora()));
        asignaValor(" Cliente: "+getFillText(ALIGN_LEFT, 35, pago.getNombreCliente()));

        asignaValor(getFillText(ALIGN_CENTER, 48,   "DESCRIPCION"));


        if(pago.getListaPagosFactura()!=null)
        {
            for (int i = 0; i < pago.getListaPagosFactura().size(); i++) {
                PagosFactura pagosFactura=pago.getListaPagosFactura().get(i);
                asignaValor(getFillText(ALIGN_CENTER, 48,   "______________________________________________"));
                asignaValor(getFillText(ALIGN_CENTER, 48,   " Factura: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNFactura()+"")+"     Caja: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNCaja()+"")));
                asignaValor(getFillText(ALIGN_CENTER, 48,   "               Saldo Anterior: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldoAnterior()))));
                asignaValor(getFillText(ALIGN_CENTER, 48,   " FORMA DE PAGO         VALOR        "));
                if(pagosFactura.getListaPagoFac()!=null)
                {
                    for (int j = 0; j < pagosFactura.getListaPagoFac().size(); j++) {
                        ItemPagoFac itemPagoFac=pagosFactura.getListaPagoFac().get(j);
                        asignaValor(getFillText(ALIGN_CENTER, 48, " "+getFillText(ALIGN_LEFT, 20, ""+itemPagoFac.getFormaPago())+" "+getFillText(ALIGN_LEFT, 20, getDecTxt(itemPagoFac.getValor()))));
                    }
                }
                asignaValor(getFillText(ALIGN_CENTER, 48, "                    Descuento: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getDescuento()))));

                asignaValor(getFillText(ALIGN_CENTER, 48, "                  Nuevo Saldo: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldo()))));


            }
        }
        asignaValor(" "+getFillText(ALIGN_RIGHT, 47, " _____________________________________________") );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 47, getFillText(ALIGN_CENTER, 45, "TOTAL: "+getDecTxt(pago.getValor()))) );
        //  asignaValor(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_LEFT, 45, "SALDO A LA FECHA: "+getDecTxt(pago.getDeudaCliente()))) );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, "LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())) );
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 40, getFillText(ALIGN_CENTER, 48, "FIRMA AUTORIZADA:____________________________________________")) );

        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 48, " "));

    }
    
    
    
    
    //---------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------PENDIENTE DATOS DE PRESTAMOS                                   ---------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------

    public void printPagoPrestamo(IMyBinder binder, PagoPrestamo pagoPrestamo, Parametros parametros)
    {


        this.binder=binder;

        this.pagoPrestamo=pagoPrestamo;
        this.parametrosPos=parametros;


        asignaValor(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRazonSocial()));
        asignaValor(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRepresentante()));
        asignaValor(getFillText(ALIGN_CENTER, 30,   "COMPROBANTE DE PAGO"));
        // asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________"));

        asignaValor("No. Pago: "+getFillText(ALIGN_CENTER, 11, pagoPrestamo.getIdPagoPrestamo()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()));
        asignaValor("   Fecha: "+getFillText(ALIGN_LEFT, 10, pagoPrestamo.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pagoPrestamo.getHora()));
        asignaValor(" CLIENTE ");
        asignaValor(""+getFillText(ALIGN_CENTER, 29, pagoPrestamo.getNombreCliente()));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")));

        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getSaldoAnterior()))));
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   VALOR ABONO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getValor()))) );
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(pagoPrestamo.getNuevoSaldo()))));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );

        //asignaValor(" "+getFillText(ALIGN_CENTER, 29, "LO ATENDIO "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValor(" "+getFillText(ALIGN_CENTER, 29,  getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "FIRMA RECIBIDO:")));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")) );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")) );

        asignaValor(" "+getFillText(ALIGN_CENTER, 30, " "));






       
    }
    public void printPrestamo(IMyBinder binder, Prestamo prestamo, Parametros parametros)
    {
        this.binder=binder;
        this.prestamo=prestamo;
        this.parametrosPos=parametros;


        //asignaValor(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRazonSocial()));
        //asignaValor(getFillText(ALIGN_CENTER, 30,  parametrosPos.getRepresentante()));
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 40,  "123456789012345678901234567890123456789"),BixolonPrinter.TEXT_ATTRIBUTE_FONT_B ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValor(getFillText(ALIGN_CENTER, 26,  parametrosPos.getRazonSocial()));
        asignaValor(getFillText(ALIGN_CENTER, 26,  parametrosPos.getRepresentante()));
        asignaValor(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE PRESTAMO"));
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________"));

        asignaValor("No. Prestamo: "+getFillText(ALIGN_CENTER, 11, prestamo.getIdPrestamo()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()));
        asignaValor("   Fecha: "+getFillText(ALIGN_LEFT, 10, prestamo.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, prestamo.getHora()));
        asignaValor(" CLIENTE ");
        asignaValor(""+getFillText(ALIGN_CENTER, 29, prestamo.getNombreCliente()));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")));

        asignaValor(getFillText(ALIGN_CENTER, 26,   "CONCEPTO"));
        asignaValor(getFillText(ALIGN_CENTER, 26,   prestamo.getObjeto()));

        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getSaldoAnterior()))) );
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "VALOR PRESTAMO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getValorPrestamo()))));
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(prestamo.getNuevoSaldo()))) );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );

        //asignaValor(" "+getFillText(ALIGN_CENTER, 29, "LO ATENDIO "),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        //asignaValor(" "+getFillText(ALIGN_CENTER, 29,  getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );

        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "FIRMA RECIBIDO:")));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")) );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")) );

        asignaValor(" "+getFillText(ALIGN_CENTER, 30, " "));





    
    }

    public void printLibro(IMyBinder binder, Libro libro, Parametros parametros)
    {
        this.binder=binder;
        this.libro=libro;
        this.parametrosPos=parametros;
        asignaValor(getFillText(ALIGN_CENTER, 26,  parametrosPos.getRazonSocial()));
        asignaValor(getFillText(ALIGN_CENTER, 26,  parametrosPos.getRepresentante()));

        if(libro.getMovDedito()>0)
        {
            asignaValor(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE DE VENTA"));
        }
        else
        {
            asignaValor(getFillText(ALIGN_CENTER, 26,   "COMPROBANTE DE PAGO"));
        }


        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, " _____________________________________________") );

        asignaValor("No. Comprabante: "+getFillText(ALIGN_CENTER, 11, libro.getIdLibro()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosPos.getCaja()));
        asignaValor("   Fecha: "+getFillText(ALIGN_LEFT, 10, libro.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, libro.getHora()));
        asignaValor(" CLIENTE ");
        asignaValor(""+getFillText(ALIGN_CENTER, 29, libro.getNombreCliente()));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")) );

        asignaValor(getFillText(ALIGN_CENTER, 26, "CONCEPTO"));
        asignaValor(getFillText(ALIGN_CENTER, 26, libro.getConcepto()));

        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "SALDO ANTERIOR: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(libro.getSaldoAnterior()))));
        //asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")),BixolonPrinter.TEXT_ATTRIBUTE_FONT_A ,BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED,Builder.ALIGN_RIGHT );
        if(libro.getMovDedito()>0) {
            asignaValor(" " + getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "VALOR VENTA: ") + getFillText(ALIGN_RIGHT, 12, getDecTxt(libro.getMovDedito()))));
            //  asignaValor(" " + getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
        }
        else
        {
            asignaValor(" " + getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   VALOR ABONO: ") + getFillText(ALIGN_RIGHT, 12, getDecTxt(libro.getMovCredito()))));
            //asignaValor(" " + getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")), BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, BixolonPrinter.TEXT_ATTRIBUTE_EMPHASIZED, Builder.ALIGN_RIGHT);
        }
        asignaValor(" "+getFillText(ALIGN_RIGHT, 29, getFillText(ALIGN_LEFT, 16, "   NUEVO SALDO: ")+getFillText(ALIGN_RIGHT, 12,getDecTxt(libro.getSaldo()))) );
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, "_____________________________________________") );


        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_LEFT, 26, "FIRMA RECIBIDO:")));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 26, getFillText(ALIGN_CENTER, 26, "")));
        asignaValor(" "+getFillText(ALIGN_RIGHT, 30, getFillText(ALIGN_CENTER, 30, "_______________________________")) );

        asignaValor(" "+getFillText(ALIGN_CENTER, 30, " "));
        asignaValor(" "+getFillText(ALIGN_CENTER, 30, " "));






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

    private long getTotalFacturasContado()
    {
        long res=0;
        for (int i = 0; i < listaFacturas.size(); i++) {
            Factura_in f=listaFacturas.get(i);
            if(f.getPagada().equals("SI")){
            res+=f.getValor();
            }
        }
        return res;
    }

    private long getTotalFacturasCredito()
    {
        long res=0;
        for (int i = 0; i < listaFacturas.size(); i++) {
            Factura_in f=listaFacturas.get(i);
            if(f.getPagada().equals("NO")){
                res+=f.getValor();
            }
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

    private long getTotalRemisionesContado()
    {
        long res=0;
        for (int i = 0; i < listaRemisiones.size(); i++) {
            Remision_in f=listaRemisiones.get(i);
            if(f.getPagada().equals("SI")){
                res+=f.getValor();
            }
        }
        return res;
    }

    private long getTotalRemisionesCredito()
    {
        long res=0;
        for (int i = 0; i < listaRemisiones.size(); i++) {
            Remision_in f=listaRemisiones.get(i);
            if(f.getPagada().equals("NO")){
                res+=f.getValor();
            }
        }
        return res;
    }











    private void asignaValor( final String  text)
    {
        binder.writeDataByYouself(

        new UiExecute() {
                    public void onsucess() {

                    }
                    public void onfailed() {

                    }
                }, new ProcessData() {

                    public List<byte[]> processDataBeforeSend() {

                        List<byte[]> list=new ArrayList<byte[]>();
                        //creat a text ,and make it to byte[],
                        String str=text;
                        if (str.equals(null)||str.equals("")){
                        }else {
                            //initialize the printer
//                            list.add( DataForSendToPrinterPos58.initializePrinter());
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            byte[] data1= str.getBytes();
                            list.add(data1);
                            //should add the command of print and feed line,because print only when one line is complete, not one line, no print
                            list.add(DataForSendToPrinterPos80.printAndFeedLine());
                            //cut pager
                            list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66,1));
                            return list;
                        }
                        return null;
                    }
                });
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



    public String getTotalPedido(ArrayList<ArticulosPedido> listaAPedido)
    {
        double total=0;
        for (int i=0; i<listaAPedido.size();i++)
        {
            total=total+listaAPedido.get(i).valor;
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return decimalFormat.format(total);

    }
    public String getTotalFactura(ArrayList<ArticulosFactura> listaAFactura)
    {
        double total=0;
        for (int i=0; i<listaAFactura.size();i++)
        {
            total=total+listaAFactura.get(i).valor;
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return decimalFormat.format(total);

    }

}
