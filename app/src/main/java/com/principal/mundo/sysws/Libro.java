package com.principal.mundo.sysws;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by acer on 18/04/2019.
 */

public class Libro {

    private String idLibro;
    private long NLibro;
    private String IdCliente;
    private long MovCredito;
    private long MovDedito;
    private long Saldo;
    private long Enviado;
    private String Concepto;
    private String Fecha;
    private String Fecha2;
    private String hora;
    private long SaldoAnterior;
    private ArrayList<Libro> listalibros;

    private long NAbonos;
    private long NPrestamos;
    private long valorAbonos;
    private long valorPrestamos;
    private String IdVendedor;


    private String NombreCliente;

    public Libro() {

    }

    public Libro(String idLibro, long NLibro, String idCliente, long movCredito, long movDedito, long saldo, long enviado, String concepto, String fecha, String fecha2, String hora, long saldoAnterior, String nombreCliente) {
        this.idLibro = idLibro;
        this.NLibro = NLibro;
        IdCliente = idCliente;
        MovCredito = movCredito;
        MovDedito = movDedito;
        Saldo = saldo;
        Enviado = enviado;
        Concepto = concepto;
        Fecha = fecha;
        Fecha2 = fecha2;
        this.hora = hora;
        SaldoAnterior = saldoAnterior;
        NombreCliente = nombreCliente;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public long getNLibro() {
        return NLibro;
    }

    public void setNLibro(long NLibro) {
        this.NLibro = NLibro;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(String idCliente) {
        IdCliente = idCliente;
    }

    public long getMovCredito() {
        return MovCredito;
    }

    public void setMovCredito(long movCredito) {
        MovCredito = movCredito;
    }

    public long getMovDedito() {
        return MovDedito;
    }

    public void setMovDedito(long movDedito) {
        MovDedito = movDedito;
    }

    public long getSaldo() {
        return Saldo;
    }

    public void setSaldo(long saldo) {
        Saldo = saldo;
    }

    public long getEnviado() {
        return Enviado;
    }

    public void setEnviado(long enviado) {
        Enviado = enviado;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getFecha2() {
        return Fecha2;
    }

    public void setFecha2(String fecha2) {
        Fecha2 = fecha2;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public long getSaldoAnterior() {
        return SaldoAnterior;
    }

    public void setSaldoAnterior(long saldoAnterior) {
        SaldoAnterior = saldoAnterior;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public ArrayList<Libro> getListalibros() {
        return listalibros;
    }

    public void setListalibros(ArrayList<Libro> listalibros) {
        this.listalibros = listalibros;
        valorAbonos=0;
        valorPrestamos=0;
        NAbonos=0;
        NPrestamos=0;
        if(listalibros!=null)
        {
            for (int i=0;i<listalibros.size();i++)
            {
                if(listalibros.get(i).getMovDedito()>0)
                {
                    NPrestamos++;
                    valorPrestamos=valorPrestamos+ listalibros.get(i).getMovDedito();
                }
                else
                {
                    NAbonos++;
                    valorAbonos=valorAbonos+ listalibros.get(i).getMovCredito();
                }
            }
        }
    }

    public long getNAbonos() {
        return NAbonos;
    }

    public void setNAbonos(long NAbonos) {
        this.NAbonos = NAbonos;
    }

    public long getNPrestamos() {
        return NPrestamos;
    }

    public void setNPrestamos(long NPrestamos) {
        this.NPrestamos = NPrestamos;
    }

    public long getValorAbonos() {
        return valorAbonos;
    }

    public void setValorAbonos(long valorAbonos) {
        this.valorAbonos = valorAbonos;
    }

    public long getValorPrestamos() {
        return valorPrestamos;
    }

    public void setValorPrestamos(long valorPrestamos) {
        this.valorPrestamos = valorPrestamos;
    }

    public Object getProperty(int i) {
        switch(i)
        {
            case 0: return idLibro;
            case 1: return NLibro;
            case 2: return IdCliente;
            case 3: return MovCredito;
            case 4: return MovDedito;
            case 5: return Saldo;
            case 6: return Concepto;
            case 7: return Fecha;
            case 8: return Fecha2.replaceAll("-","");
            case 9: return hora;
            case 10: return SaldoAnterior;
            case 11: return IdVendedor;

        }

        return null;
    }

    public String getPropertyName(int i) {
        switch(i)
        {
            case 0: return "idLibro";
            case 1: return "NLibro";
            case 2: return "IdCliente";
            case 3: return "MovCredito";
            case 4: return "MovDedito";
            case 5: return "Saldo";
            case 6: return "Concepto";
            case 7: return "Fecha";
            case 8: return "Fecha2";
            case 9: return "hora";
            case 10: return "SaldoAnterior";
            case 11: return "IdVendedor";
        }
        return null;
    }

    public void setProperty(int i, String data) {

        switch(i)
        {
            case 0: idLibro =data;break;
            case 1: NLibro =Long.parseLong(data);break;
            case 2:  IdCliente =data;break;
            case 3:  MovCredito =Long.parseLong(data);break;
            case 4:  MovDedito =Long.parseLong(data);break;
            case 5:  Saldo =Long.parseLong(data);break;
            case 6:  Concepto =data;break;
            case 7:  Fecha =data;break;
            case 8:  Fecha2 =data;break;
            case 9:  hora =data;break;
            case 10: SaldoAnterior =Long.parseLong(data);break;
            case 11: IdVendedor=data;break;

        }
    }
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 12;
    }

    public String getIdVendedor() {
        return IdVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        IdVendedor = idVendedor;
    }
}
