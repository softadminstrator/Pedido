package com.principal.mundo.sysws;

/**
 * Created by acer on 3/10/2018.
 */

public class ObservacionPedido {

    private String IdObservacionPedido;
    private String NombreObservacion;
    private String Cantidad;

    public ObservacionPedido() {
        Cantidad="0";
        // TODO Auto-generated constructor stub
    }

    public ObservacionPedido(String idObservacionPedido, String nombreObservacion) {
        super();
        IdObservacionPedido = idObservacionPedido;
        NombreObservacion = nombreObservacion;
    }

    public String getIdObservacionPedido() {
        return IdObservacionPedido;
    }

    public void setIdObservacionPedido(String idObservacionPedido) {
        IdObservacionPedido = idObservacionPedido;
    }

    public String getNombreObservacion() {
        return NombreObservacion;
    }

    public void setNombreObservacion(String nombreObservacion) {
        NombreObservacion = nombreObservacion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}