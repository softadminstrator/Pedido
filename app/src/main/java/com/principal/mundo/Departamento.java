package com.principal.mundo;

public class Departamento {
    private String IdDpto;
    private String Departamento;

    public Departamento(String idDpto, String departamento) {
        IdDpto = idDpto;
        Departamento = departamento;
    }

    public Departamento() {
    }

    public String getIdDpto() {
        return IdDpto;
    }

    public void setIdDpto(String idDpto) {
        IdDpto = idDpto;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }
}
