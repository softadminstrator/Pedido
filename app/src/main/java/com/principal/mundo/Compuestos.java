package com.principal.mundo;

import org.ksoap2.serialization.KvmSerializable;

/**
 * Created by acer on 17/07/2017.
 */

public class Compuestos  {
    private long IdArticulo;

    private long IdArtComponente;

    private long Cantidad;

    public Compuestos()
    {

    }

    public Compuestos(long idArticulo) {
        IdArticulo = idArticulo;
    }

    public Compuestos(long idArticulo, long idArtComponente, long cantidad) {
        IdArticulo = idArticulo;
        IdArtComponente = idArtComponente;
        Cantidad = cantidad;
    }

    public long getIdArticulo() {
        return IdArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        IdArticulo = idArticulo;
    }

    public long getIdArtComponente() {
        return IdArtComponente;
    }

    public void setIdArtComponente(long idArtComponente) {
        IdArtComponente = idArtComponente;
    }

    public long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(long cantidad) {
        Cantidad = cantidad;
    }
}
