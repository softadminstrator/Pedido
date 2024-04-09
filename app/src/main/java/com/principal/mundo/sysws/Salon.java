package com.principal.mundo.sysws;

public class Salon {
    public String IdSalon;
    public String Nombre;
    public String Borrado;
    public String PX;
    public String PY;

    public Salon() {
    }

    public Salon(String idSalon, String nombre, String borrado, String PX, String PY) {
        IdSalon = idSalon;
        Nombre = nombre;
        Borrado = borrado;
        this.PX = PX;
        this.PY = PY;
    }

    public String getIdSalon() {
        return IdSalon;
    }

    public void setIdSalon(String idSalon) {
        IdSalon = idSalon;
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

    public String getPX() {
        return PX;
    }

    public void setPX(String PX) {
        this.PX = PX;
    }

    public String getPY() {
        return PY;
    }

    public void setPY(String PY) {
        this.PY = PY;
    }
}
