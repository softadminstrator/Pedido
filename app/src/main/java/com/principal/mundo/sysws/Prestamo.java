package com.principal.mundo.sysws;

/**
 * Created by acer on 16/04/2019.
 */

public class Prestamo {
    private String idPrestamo;
    private String idCliente;
    private String fecha;
    private String fecha2;
    private String hora;
    private String objeto;

    private long valorPrestamo;
    private long saldoAnterior;
    private long nuevoSaldo;
    private long enviado;

    private String nombreCliente;

    public Prestamo() {
    }

    public Prestamo(String idPrestamo, String idCliente, String fecha, String fecha2, String hora, String objeto, long valorPrestamo, long saldoAnterior, long nuevoSaldo, long enviado, String nombreCliente) {
        this.idPrestamo = idPrestamo;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.fecha2 = fecha2;
        this.hora = hora;
        this.objeto = objeto;
        this.valorPrestamo = valorPrestamo;
        this.saldoAnterior = saldoAnterior;
        this.nuevoSaldo = nuevoSaldo;
        this.enviado = enviado;
        this.nombreCliente = nombreCliente;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public long getValorPrestamo() {
        return valorPrestamo;
    }

    public void setValorPrestamo(long valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public long getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(long saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public long getNuevoSaldo() {
        return nuevoSaldo;
    }

    public void setNuevoSaldo(long nuevoSaldo) {
        this.nuevoSaldo = nuevoSaldo;
    }

    public long getEnviado() {
        return enviado;
    }

    public void setEnviado(long enviado) {
        this.enviado = enviado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
