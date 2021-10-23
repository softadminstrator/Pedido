package com.principal.mundo.sysws;

public class Municipio {
    private String idMunicipio;
    private String Nombre;

    public Municipio() {
    }

    public Municipio(String idMunicipio, String nombre) {
        this.idMunicipio = idMunicipio;
        Nombre = nombre;
    }

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
