package com.principal.mundo.sysws;

import java.util.Hashtable;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ClienteSucursal implements KvmSerializable  {

    private String IdClienteSucursal;
    private String IdMpio;
    private String IdDpto;
    private String Direccion;
    private String Telefono;
    private String Establecimiento;
    private String idCliente;
    private String Borrado;
    private String Municipio;
    private String Departamento;



    public ClienteSucursal() {
        // TODO Auto-generated constructor stub
    }




    public ClienteSucursal(String idClienteSucursal, String idMpio, String idDpto, String direccion, String telefono,
                           String establecimiento, String idCliente, String borrado, String municipio, String departamento) {
        super();
        IdClienteSucursal = idClienteSucursal;
        IdMpio = idMpio;
        IdDpto = idDpto;
        Direccion = direccion;
        Telefono = telefono;
        Establecimiento = establecimiento;
        this.idCliente = idCliente;
        Borrado = borrado;
        Municipio = municipio;
        Departamento = departamento;
    }




    public String getIdClienteSucursal() {
        return IdClienteSucursal;
    }



    public void setIdClienteSucursal(String idClienteSucursal) {
        IdClienteSucursal = idClienteSucursal;
    }



    public String getIdMpio() {
        return IdMpio;
    }



    public void setIdMpio(String idMpio) {
        IdMpio = idMpio;
    }



    public String getIdDpto() {
        return IdDpto;
    }



    public void setIdDpto(String idDpto) {
        IdDpto = idDpto;
    }



    public String getDireccion() {
        return Direccion;
    }



    public void setDireccion(String direccion) {
        Direccion = direccion;
    }



    public String getTelefono() {
        return Telefono;
    }



    public void setTelefono(String telefono) {
        Telefono = telefono;
    }



    public String getEstablecimiento() {
        return Establecimiento;
    }



    public void setEstablecimiento(String establecimiento) {
        Establecimiento = establecimiento;
    }



    public String getIdCliente() {
        return idCliente;
    }



    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }



    public String getBorrado() {
        return Borrado;
    }



    public void setBorrado(String borrado) {
        Borrado = borrado;
    }



    public String getMunicipio() {
        return Municipio;
    }



    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }



    public String getDepartamento() {
        return Departamento;
    }



    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }





    public Object getProperty(int arg0) {
        switch(arg0)
        {
            case 0: return IdClienteSucursal;
            case 1: return IdMpio;
            case 2: return IdDpto;
            case 3: return Direccion;
            case 4: return Telefono;
            case 5: return Establecimiento;
            case 6: return idCliente;
            case 7: return Borrado;
            case 8: return Municipio.trim();
            case 9: return Departamento.trim();
        }
        return null;
    }


    public String getPropertyName(int arg0) {
        switch(arg0)
        {
            case 0: return "IdClienteSucursal";
            case 1: return "IdMpio";
            case 2: return "IdDpto";
            case 3: return "Direccion";
            case 4: return "Telefono";
            case 5: return "Establecimiento";
            case 6: return "idCliente";
            case 7: return "Borrado";
            case 8: return "Municipio";
            case 9: return "Departamento";
        }
        return null;
    }




    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 10;
    }





    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        switch(arg0)
        {
            case 0:
                arg2.name = "IdClienteSucursal";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 1:
                arg2.name = "IdMpio";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 2:
                arg2.name = "IdDpto";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 3:
                arg2.name = "Direccion";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 4:
                arg2.name = "Telefono";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 5:
                arg2.name = "Establecimiento";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 6:
                arg2.name = "idCliente";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 7:
                arg2.name = "Borrado";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 8:
                arg2.name = "Municipio";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 9:
                arg2.name = "Departamento";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;


        }

    }





    public void setProperty(int arg0, Object arg1) {
        switch(arg0)
        {
            case 0:
                IdClienteSucursal=arg1.toString();
                break;
            case 1:
                IdMpio=arg1.toString();
                break;
            case 2:
                IdDpto=arg1.toString();
                break;
            case 3:
                Direccion=arg1.toString();
                break;
            case 4:
                Telefono=arg1.toString();
                break;
            case 5:
                Establecimiento=arg1.toString();
                break;
            case 6:
                idCliente=arg1.toString();
                break;
            case 7:
                Borrado=arg1.toString();
                break;
            case 8:
                Municipio=arg1.toString();
                break;
            case 9:
                Departamento=arg1.toString();
                break;

        }

    }


}
