package com.principal.mundo;

public class Medios {
    public long IdMediosDePago;
    public String Nombre;
    public String Borrado;
    public String VisibleEnPantalla;
    public String TeclaAccesoDirecto;
    public String PermiteModificar;


    public Medios()
    {

    }





    public String getNombre() {
        return Nombre;
    }


    public void setNombre(String nombre) {
        Nombre = nombre;
    }


    public String getBorrado() {
        return Borrado;
    }


    public void setBorrado(String borrado) {
        Borrado = borrado;
    }


    public String getVisibleEnPantalla() {
        return VisibleEnPantalla;
    }


    public void setVisibleEnPantalla(String visibleEnPantalla) {
        VisibleEnPantalla = visibleEnPantalla;
    }


    public String getTeclaAccesoDirecto() {
        return TeclaAccesoDirecto;
    }


    public void setTeclaAccesoDirecto(String teclaAccesoDirecto) {
        TeclaAccesoDirecto = teclaAccesoDirecto;
    }


    public String getPermiteModificar() {
        return PermiteModificar;
    }


    public void setPermiteModificar(String permiteModificar) {
        PermiteModificar = permiteModificar;
    }

    public long getIdMediosDePago() {
        return IdMediosDePago;
    }

    public void setIdMediosDePago(long idMediosDePago) {
        IdMediosDePago = idMediosDePago;
    }
}
