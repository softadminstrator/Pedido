package com.principal.mundo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que representa el pedido que fue enviado al sistema PosStar exitosamente
 * para luego ser almacenado en el telefono
 * @author Javier
 *
 */
public class Remision_in
{
    /**
     * identificador del codigo interno de pedido
     */
    public long idCodigoInterno;
    public long idCodigoExterno;
    /**
     * identificador del cliente al que se le realizo el pediod
     */
    public long idCliente;
    public long NRemision;
    /**
     * nombre del cliente al que se le realizo el pedido
     */
    public String nombreCliente;

    public String nitCliente;
    public String dvCliente;
    /**
     * fecha en la que se realizo el pedido
     */
    private String fecha;
    /**
     * cedula del usuario o numero de ruta de quien realizo el pedido
     */
    public String cedulaUsuario;
    /**
     * observacion del pedido
     */
    public String observaciones;
    /**
     * latitud de donde se realizo el pedido
     */
    public String latitud;
    /**
     * longitud de donde se realizo el pedido
     */
    public String longitud;
    /**
     * hora en la que se realizo el pedido
     */
    public String hora;
    /**
     * valor total del pedido
     */
    public long valor;
    /**
     * lista de articulos del pedido
     */
    public ArrayList<ArticulosRemision> listaArticulos;

    public String razonSocial;
    public String representante;
    public String regimenNit;
    public String direccionTel;
    public long NCaja;
    public String prefijo;
    public long base0;
    public long base5;
    public long base10;
    public long base14;
    public long base16;
    public long base19;
    public long iva5;
    public long iva10;
    public long iva14;
    public long iva16;
    public long iva19;
    public long impoCmo;
    public long totalRemision;
    public String resDian;
    public String rango;
    public long idBodega;
    public long dineroRecibido;
    public String NombreVendedor;
    public String TelefonoVendedor;
    public long VentaCredito;

    public String Pagada;
    public long ValorPagado;
    public long Devolucion;

    public boolean isPagada;


    private String fechaSqlite;
    private String fechaServer;


    public ArrayList<String> variablesHeader;

    public long idClienteSucursal;




    /**
     * metodo que se encarga de asignar valores a los atributos del pedido
     */


    /**
     * metodo que retorna la hora en la que fue realizado el pedido
     * @return hora
     */
    public String getHora() {
        return hora;
    }

    public Remision_in() {
        listaArticulos=new ArrayList<ArticulosRemision>();
        isPagada=false;
    }



    public ArrayList<ArticulosRemision> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList<ArticulosRemision> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
    public Remision_in(long nFacturaIn, long nFacturaOut, long idCliente,
                      String nombreCliente, String fecha, String cedulaUsuario,
                      String observaciones, String latitud, String longitud, String hora,
                      long valor, ArrayList<ArticulosRemision> listaArticulos,
                      String razonSocial, String representante, String regimenNit,
                      String direccionTel, long nCaja, String prefijo, long base0,
                      long base5, long base10, long base14, long base16, long iva5,
                      long iva10, long iva14, long iva16, long impoCmo,
                      long totalFactura, String resDian, String rango, long idBodega,
                      long dineroRecibido, String nombreVendedor,
                      String telefonoVendedor, ArrayList<String> variablesHeader) {
        super();
        idCodigoInterno = nFacturaIn;
        idCodigoExterno = nFacturaOut;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.cedulaUsuario = cedulaUsuario;
        this.observaciones = observaciones;
        this.latitud = latitud;
        this.longitud = longitud;
        this.hora = hora;
        this.valor = valor;
        this.listaArticulos = listaArticulos;
        this.razonSocial = razonSocial;
        this.representante = representante;
        this.regimenNit = regimenNit;
        this.direccionTel = direccionTel;
        NCaja = nCaja;
        this.prefijo = prefijo;
        this.base0 = base0;
        this.base5 = base5;
        this.base10 = base10;
        this.base14 = base14;
        this.base16 = base16;
        this.iva5 = iva5;
        this.iva10 = iva10;
        this.iva14 = iva14;
        this.iva16 = iva16;
        this.impoCmo = impoCmo;
        this.totalRemision = totalFactura;
        this.resDian = resDian;
        this.rango = rango;
        this.idBodega = idBodega;
        this.dineroRecibido = dineroRecibido;
        NombreVendedor = nombreVendedor;
        TelefonoVendedor = telefonoVendedor;
        this.variablesHeader = variablesHeader;
    }

    public String getNombreVendedor() {
        if (NombreVendedor==null)
        {
            NombreVendedor="";
        }
        return NombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        NombreVendedor = nombreVendedor;
    }

    public String getTelefonoVendedor() {
        if(TelefonoVendedor.equals("anyType{}"))
        {
            TelefonoVendedor="--";
        }
        return TelefonoVendedor;
    }


    public void setTelefonoVendedor(String telefonoVendedor) {
        TelefonoVendedor = telefonoVendedor;
    }

    public long getDineroRecibido() {
        return dineroRecibido;
    }

    public long getCambio() {
        if (valor>dineroRecibido)
        {
            return 0;
        }
        return dineroRecibido-valor;
    }
    public void setDineroRecibido(long dineroRecibido) {
        this.dineroRecibido = dineroRecibido;
    }

    /**
     * metodo que asigna el nuevo valor de hora del pedido
     * @param hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }




    /**
     * metodo que retorna el nombre del cliente a quien fue realizado el pedido
     * @return nombreCliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * metodo que asigna el nuevo nombre del cliente a quien se le realiza el pedido
     * @param nombreCliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String  valspace(String value)
    {
        String res="";
        int val=0;
        for (int i = 0; i < value.length(); i++) {
            if(val>1)
            {
                res=value.substring(0, i-2);
                return res;
            }
            else if(value.charAt(i)==' ')
            {
                val++;
            }
            else
            {
                val=0;
            }
        }
        return res;
    }

    /**
     * metodo que retorna el identificador del cliente
     * @return
     */
    public long getIdCliente() {
        return idCliente;
    }

    /**
     * metodo que asigna el nuevo valor al identificador del cliente
     * @param idCliente
     */
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * metodo que retorna la fecha en la cual fue realizado el pedido
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * metodo que asigna la nueva fecha del pedido
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * metodo que retorna el valor total del pedido
     * @return
     */
    public long getValor() {
        return valor;
    }
    public String getValorText() {
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return "$ "+decimalFormat.format(valor);
    }

    /**
     * metodo que asigna el nuevo valor total del pedido
     * @param valor
     */
    public void setValor(long valor) {
        this.valor = valor;
    }



    /**
     * metodo que retorna la observacion del pedido
     * @return observaciones
     */
    public String getObservaciones() {
        if (observaciones==null)
        {
            return " ";
        }
        return observaciones;
    }
    public  ArrayList<String> getObservacion(String text)
    {
        String [] value=null;
        int tinicial=0;
        int variable=66;
        ArrayList<String> lis=new ArrayList<String>();

        if(text!=null)
        {
            if (text.length() > 0) {
                double res=text.length()/variable;//2.5
                for (int i=0; i<res; i++)
                {
                    lis.add(text.substring(tinicial,variable));
                    text=text.substring(variable,text.length());
                }
                if (text.length() > 0)
                {
                    lis.add(text.substring(0,text.length()));
                }
            }
        }
        return lis;
    }

    /**
     * metodo que asigna el nuevo valor de la observacion del pedido
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * metodo que retorna la latitud de donde fue enviado al pedido
     * @return latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * mettodo qu asigna el nuevo valor de la latitud donde fue enviado el pedido
     * @param latitud
     */
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    /**
     * medodo que retorna la longitud donde fue enviado el pedido
     * @return longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * metodo que asigna el nuevo valor de la longitud donde fue enviado el pedido
     * @param longitud
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * metodo que retorna la cedula del usuario que realizo el pedido
     * @return cedulaUsuario
     */
    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    /**
     * metodo que asigna el nuevo valor a la cedula del usuario que realizo el pedido
     * @param cedulaUsuario
     */
    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }



    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getRegimenNit() {
        return regimenNit;
    }

    public void setRegimenNit(String regimenNit) {
        this.regimenNit = regimenNit;
    }

    public String getDireccionTel() {
        return direccionTel;
    }

    public void setDireccionTel(String direccionTel) {
        this.direccionTel = direccionTel;
    }

    public long getNCaja() {
        return NCaja;
    }

    public void setNCaja(long nCaja) {
        NCaja = nCaja;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public long getBase0() {
        return base0;
    }
    public String getFormatoDecimal(long numero) {
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return "$ "+decimalFormat.format(numero);
    }

    public void setBase0(long base0) {
        this.base0 = base0;
    }

    public long getBase5() {
        return base5;
    }

    public void setBase5(long base5) {
        this.base5 = base5;
    }

    public long getBase10() {
        return base10;
    }

    public void setBase10(long base10) {
        this.base10 = base10;
    }

    public long getBase14() {
        return base14;
    }

    public void setBase14(long base14) {
        this.base14 = base14;
    }

    public long getBase16() {
        return base16;
    }

    public void setBase16(long base16) {
        this.base16 = base16;
    }






    public long getIva5() {
        return iva5;
    }

    public void setIva5(long iva5) {
        this.iva5 = iva5;
    }

    public long getIva10() {
        return iva10;
    }

    public void setIva10(long iva10) {
        this.iva10 = iva10;
    }

    public long getIva14() {
        return iva14;
    }

    public void setIva14(long iva14) {
        this.iva14 = iva14;
    }

    public long getIva16() {
        return iva16;
    }

    public void setIva16(long iva16) {
        this.iva16 = iva16;
    }

    public long getImpoCmo() {
        return impoCmo;
    }

    public void setImpoCmo(long impoCmo) {
        this.impoCmo = impoCmo;
    }

    public long getTotalRemision() {
        return totalRemision;
    }

    public void setTotalRemision(long totalRemision) {
        this.totalRemision = totalRemision;
    }

    public String getResDian() {
        return resDian;
    }
    public void setIdCodigoExterno(long idCodigoExterno) {
        this.idCodigoExterno = idCodigoExterno;
    }
    public void setIdCodigoInterno(long idCodigoInterno) {
        this.idCodigoInterno = idCodigoInterno;
    }
    public long getIdCodigoExterno() {
        return idCodigoExterno;
    }
    public long getIdCodigoInterno() {
        return idCodigoInterno;
    }

    public void setResDian(String resDian) {
        this.resDian = resDian;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public long getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(long idBodega) {
        this.idBodega = idBodega;
    }




    public long getBase19() {
        return base19;
    }

    public void setBase19(long base19) {
        this.base19 = base19;
    }

    public long getIva19() {
        return iva19;
    }

    public void setIva19(long iva19) {
        this.iva19 = iva19;
    }

    public void cargarFacturaPrueba() {

        razonSocial="DISTRIBUIDORA EL CHISPAZO";
        representante="MARIO ERNESTO HERNANDEZ GALLO";
        regimenNit="NIT 6.761.024-3 REGIMEN COMUN";
        direccionTel="CRA 11 No. 10 - 24 TEL 7448724 TUNJA";
        NCaja=10;
        prefijo="BT1";
        base0=0;
        base5=0;
        base10=3456;
        base14=0;
        base16=1724;
        base19=0;
        iva5=0;
        iva10=0;
        iva14=0;
        iva16=276;
        iva19=0;
        impoCmo=10845;
        totalRemision=5980;
        dineroRecibido=100000;
        valor=50000;
        resDian="RES DIAN No. 200000034159 de 2012/09/14 HABILITA";
        rango="Rango desde 162602 hasta 1000000";
        NombreVendedor="Darwin Quintero Rojas";
        idBodega=10;
        idCodigoExterno=215709;
        fecha="01/01/2014";
        hora="24:13";
        ArticulosRemision ar=new ArticulosRemision();
        ar.setCantidad(10);
        ar.setNombre("Cigarrilo Mustang azul decena * 10");
        ar.setValorUnitario(1000);
        ar.setValor(10000);
        listaArticulos.add(ar);
        listaArticulos.add(ar);
        listaArticulos.add(ar);

    }
    public String getFechaSqlite() {

        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            c.setTime(dateFormat.parse(fecha));
            fechaSqlite=c.get(Calendar.YEAR)+"-"+validNumberDate((c.get(Calendar.MONTH)+1))+"-"+validNumberDate(c.get(Calendar.DAY_OF_MONTH));
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return fechaSqlite;
    }
    public void setFechaSqlite(String fechaSqlite) {
        this.fechaSqlite = fechaSqlite;
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            c.setTime(dateFormat.parse(fechaSqlite));
            fecha=validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);
            fechaServer=c.get(Calendar.YEAR)+validNumberDate((c.get(Calendar.MONTH)+1))+validNumberDate(c.get(Calendar.DAY_OF_MONTH));
        }
        catch (Exception e) {
            // TODO: handle exception
        }
    }

    private String validNumberDate(int value)
    {
        String res=""+value;
        if(value<10)
        {
            res="0"+value;
        }
        return res;
    }

    public long getVentaCredito() {
        return VentaCredito;
    }
    public void setVentaCredito(long ventaCredito) {
        VentaCredito = ventaCredito;
    }


    public void setProperty(int i, String data) {

        switch(i)
        {
            case 0:  NRemision=Long.parseLong(data);break;
            case 1:  NCaja=Long.parseLong(data);break;
            case 2:  fecha=data;break;
            case 3:  hora=data;break;
            case 4:  base0=Long.parseLong(data);break;
            case 5:  base5=Long.parseLong(data);break;
            case 6:  base10=Long.parseLong(data);break;
            case 7:  base14=Long.parseLong(data);break;
            case 8:  base16=Long.parseLong(data);break;
            case 9:  iva5=Long.parseLong(data);break;
            case 10:  iva10=Long.parseLong(data);break;
            case 11:  iva14=Long.parseLong(data);break;
            case 12:  iva16=Long.parseLong(data);break;
            case 13:  impoCmo=Long.parseLong(data);break;
            case 14:  totalRemision=Long.parseLong(data);break;
            case 15:  idBodega=Long.parseLong(data);break;
            case 16:  NombreVendedor=data;break;
            case 17:  TelefonoVendedor=data;break;
            case 18:  base19=Long.parseLong(data);break;
            case 19:  iva19=Long.parseLong(data);break;

//		case 0:  razonSocial=data;break;
//		case 1:  representante=data;break;
//		case 2:  regimenNit=data;break;
//		case 3:  direccionTel=data;break;
//		case 6:  prefijo=data;break;
//		case 20:  resDian=data;break;
//		case 21:  rango=data;break;
        }

    }

    public String getPropertyNameDatosRemision(int i) {
        switch(i)
        {
            case 0: return "NRemision";
            case 1: return "NCaja";
            case 2: return "Fecha";
            case 3: return "Hora";
            case 4: return "Base0";
            case 5: return "Base5";
            case 6: return "Base10";
            case 7: return "Base8";
            case 8: return "Base16";
            case 9: return "Iva5";
            case 10: return "Iva10";
            case 11: return "Iva8";
            case 12: return "Iva16";
            case 13: return "ImpoConsumo";
            case 14: return "TotalRemision";
            case 15: return "IdBodega";
            case 16: return "NombreVendedor";
            case 17: return "TelefonoVendedor";
            case 18: return "Base19";
            case 19: return "Iva19";
//		case 0: return "RazonSocial";
//		case 1: return "Representante";
//		case 2: return "RegimenNit";
//		case 3: return "DireccionTel";
//		case 6: return "Prefijo";
//		case 20: return "ResDian";
//		case 21: return "Rango";
        }
        return null;
    }
    public int getPropertyCountDatosFactura() {
        // TODO Auto-generated method stub
        return 20;
    }

    public long getNRemision() {
        return NRemision;
    }

    public void setNRemision(long nRemision) {
        NRemision = nRemision;
    }

    public void CalcularBasesIvas()
    {
        for (int i = 0; i < listaArticulos.size(); i++) {
            ArticulosRemision  item=listaArticulos.get(i);
            impoCmo+=(item.getIpoConsumo()*item.getCantidad());
            switch ((int)item.getIva()) {
                case 0: base0= (long)((base0+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;
                case 10: base10= (long)((base10+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;
                case 16: base16= (long)((base16+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;

                case 19: base19= (long)((base19+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;

                case 5: base5= (long)((base5+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;
                case 8: base14= (long)((base14+(item.getCantidad()*item.getValorUnitario()))-(item.getIpoConsumo()*item.getCantidad()));
                    break;
            }
        }
        iva10=(long)((base10/1.1)*0.1);
        base10=(long)(base10/1.1);
        iva16=(long)((base16/1.16)*0.16);
        base16=(long)(base16/1.16);

        iva19=(long)((base19/1.19)*0.19);
        base19=(long)(base19/1.19);

        iva5=(long)((base5/1.05)*0.05);
        base5=(long)(base5/1.05);
        iva14=(long)((base14/1.08)*0.08);
        base14=(long)(base14/1.08);

    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getPagada() {
        return Pagada;
    }

    public void setPagada(String pagada) {
        Pagada = pagada;
    }

    public long getValorPagado() {
        return ValorPagado;
    }

    public void setValorPagado(long valorPagado) {
        ValorPagado = valorPagado;
    }

    public long getSaldoFactura()
    {
        return totalRemision-ValorPagado-Devolucion;
    }

    public String getFechaServer() {
        return fechaServer;
    }

    public void setFechaServer(String fechaServer) {


    }

    public long getDevolucion() {
        return Devolucion;
    }

    public void setDevolucion(long devolucion) {
        Devolucion = devolucion;
    }


    public long getIdClienteSucursal() {
        return idClienteSucursal;
    }

    public void setIdClienteSucursal(long idClienteSucursal) {
        this.idClienteSucursal = idClienteSucursal;
    }
}
