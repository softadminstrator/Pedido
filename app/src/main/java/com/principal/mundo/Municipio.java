package com.principal.mundo;

public class Municipio {

    private String IdDpto;
    private String IdMpio;
    private String Municipio;

    public Municipio(String idDpto, String idMpio, String municipio) {
        IdDpto = idDpto;
        IdMpio = idMpio;
        Municipio = municipio;
    }

    public Municipio() {

    }

    public String getIdDpto() {
        return IdDpto;
    }

    public void setIdDpto(String idDpto) {
        IdDpto = idDpto;
    }

    public String getIdMpio() {
        return IdMpio;
    }

    public void setIdMpio(String idMpio) {
        IdMpio = idMpio;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }
}
