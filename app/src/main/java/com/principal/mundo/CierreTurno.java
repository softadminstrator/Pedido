package com.principal.mundo;

import java.text.DecimalFormat;

public class CierreTurno {
    private long IdCierreTurno;
    private String NFacturaInicial;
    private String NFacturaFinal;
    private String NCaja;
    private String NCierre;
    private String Fecha2;
    private String Fecha;
    private String Hora;
    private String Valor;
    private String Transacciones;
    private String Vendedor;

    public CierreTurno(long idCierreTurno, String NFacturaInicial, String NFacturaFinal, String NCaja, String NCierre, String fecha2, String fecha, String hora, String valor, String transacciones, String vendedor) {
        IdCierreTurno = idCierreTurno;
        this.NFacturaInicial = NFacturaInicial;
        this.NFacturaFinal = NFacturaFinal;
        this.NCaja = NCaja;
        this.NCierre = NCierre;
        Fecha2 = fecha2;
        Fecha = fecha;
        Hora = hora;
        Valor = valor;
        Transacciones = transacciones;
        Vendedor = vendedor;
    }

    public CierreTurno() {
    }

    public long getIdCierreTurno() {
        return IdCierreTurno;
    }

    public void setIdCierreTurno(long idCierreTurno) {
        IdCierreTurno = idCierreTurno;
    }

    public String getNFacturaInicial() {
        return NFacturaInicial;
    }

    public void setNFacturaInicial(String NFacturaInicial) {
        this.NFacturaInicial = NFacturaInicial;
    }

    public String getNFacturaFinal() {
        return NFacturaFinal;
    }



    public void setNFacturaFinal(String NFacturaFinal) {
        this.NFacturaFinal = NFacturaFinal;
    }

    public String getNCaja() {
        return NCaja;
    }

    public void setNCaja(String NCaja) {
        this.NCaja = NCaja;
    }

    public String getNCierre() {
        return NCierre;
    }

    public void setNCierre(String NCierre) {
        this.NCierre = NCierre;
    }

    public String getFecha2() {
        return Fecha2;
    }

    public void setFecha2(String fecha2) {
        Fecha2 = fecha2;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getValor() {
        return Valor;
    }
    public String getValorDecimal()
    {
        String res = "";
        try {

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            res = decimalFormat.format(Long.parseLong(Valor));
        }
        catch (Exception e)
        {
            res="0";
        }
        return res;

    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getTransacciones() {
        return Transacciones;
    }

    public void setTransacciones(String transacciones) {
        Transacciones = transacciones;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }
    public String getSiguienteNCierre()
    {
        try
        {
            // Genera nuevo cierre
            long NNuevoCierre=Long.parseLong(NCierre);
            NNuevoCierre++;
            return ""+NNuevoCierre;
        }
        catch (Exception e)
        {
           return "1";
        }
    }

    public String getNFacturaInicialNuevoCierreTurno() {
        try
        {
            // Genera nuevo cierre
            long NFacturaInicialCierre=Long.parseLong(NFacturaFinal);
            NFacturaInicialCierre++;
            return ""+NFacturaInicialCierre;
        }
        catch (Exception e)
        {
            return "1";
        }

    }
}
