package com.principal.mundo.sysws;

/**
 * Created by acer on 16/04/2019.
 */

public class PagoPrestamo {
    private String idPagoPrestamo;
    private String fecha;
    private String fecha2;
    private String idCliente;
    private String hora;
    private long valor;
    private long saldoAnterior;
    private long nuevoSaldo;
    private long enviado;


    private String nombreCliente;


    public PagoPrestamo() {
    }

    public PagoPrestamo(String idPagoPrestamo, String fecha, String fecha2, String idCliente, String hora, long valor, long saldoAnterior, long nuevoSaldo, long enviado) {
        this.idPagoPrestamo = idPagoPrestamo;
        this.fecha = fecha;
        this.fecha2 = fecha2;
        this.idCliente = idCliente;
        this.hora = hora;
        this.valor = valor;
        this.saldoAnterior = saldoAnterior;
        this.nuevoSaldo = nuevoSaldo;
        this.enviado = enviado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdPagoPrestamo() {
        return idPagoPrestamo;
    }

    public void setIdPagoPrestamo(String idPagoPrestamo) {
        this.idPagoPrestamo = idPagoPrestamo;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
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
}
