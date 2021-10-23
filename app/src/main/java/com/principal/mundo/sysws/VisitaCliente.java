package com.principal.mundo.sysws;

public class VisitaCliente {
    private String IdCliente;
    private String IdVendedor;
    private String Fecha;
    private String Fecha2;
    private String MotivoVisita;


    public VisitaCliente() {
        // TODO Auto-generated constructor stub
    }


    public VisitaCliente(String idCliente, String idVendedor, String fecha, String fecha2, String motivoVisita) {
        super();
        IdCliente = idCliente;
        IdVendedor = idVendedor;
        Fecha = fecha;
        Fecha2 = fecha2;
        MotivoVisita = motivoVisita;
    }

    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 5;
    }

    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return IdCliente;
            case 1:
                return IdVendedor;
            case 2:
                return Fecha;
            case 3:
                return Fecha2;
            case 4:
                return MotivoVisita;
        }
        return null;
    }

    public String getPropertyName(int i) {
        switch (i) {
            case 0:
                return "IdCliente";
            case 1:
                return "IdVendedor";
            case 2:
                return "Fecha";
            case 3:
                return "Fecha2";
            case 4:
                return "MotivoVisita";
        }
        return null;
    }

    public void setProperty(int i, String data) {

        switch (i) {
            case 0:
                IdCliente = data;
                break;
            case 1:
                IdVendedor = data;
                break;
            case 2:
                Fecha = data;
                break;
            case 3:
                Fecha2 = data;
                break;
            case 4:
                MotivoVisita = data;
                break;

        }
    }

    public String getIdCliente() {
        return IdCliente;
    }


    public void setIdCliente(String idCliente) {
        IdCliente = idCliente;
    }


    public String getIdVendedor() {
        return IdVendedor;
    }


    public void setIdVendedor(String idVendedor) {
        IdVendedor = idVendedor;
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


    public String getMotivoVisita() {
        return MotivoVisita;
    }


    public void setMotivoVisita(String motivoVisita) {
        MotivoVisita = motivoVisita;
    }
}