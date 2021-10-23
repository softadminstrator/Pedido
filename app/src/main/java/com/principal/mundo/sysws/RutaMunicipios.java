package com.principal.mundo.sysws;

import java.util.ArrayList;
import com.principal.mundo.Municipio;

/**
 * Created by acer on 8/10/2018.
 */

public class RutaMunicipios {

    private String IdRuta;
    private String NombreRuta;
    private String cadenaMunicipios;

    private ArrayList<Municipio> listaMunicipios;

    public RutaMunicipios() {
        cadenaMunicipios="";
        listaMunicipios=new ArrayList<Municipio>();
        // TODO Auto-generated constructor stub
    }

    public String getIdRuta() {
        return IdRuta;
    }

    public void setIdRuta(String idRuta) {
        IdRuta = idRuta;
    }

    public String getNombreRuta() {
        return NombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        NombreRuta = nombreRuta;
    }

    public String getCadenaMunicipios() {
        return cadenaMunicipios;
    }

    public void setCadenaMunicipios(String cadenaMunicipios) {
        this.cadenaMunicipios = cadenaMunicipios;
    }

    public ArrayList<Municipio> getListaMunicipios() {

        listaMunicipios=new ArrayList<Municipio>();
        listaMunicipios.add(new Municipio("0","0","TODOS"));
        String [] parts =cadenaMunicipios.split("\\}");
        for (int i=0; i<parts.length; i++)
        {
            if (!parts[i].equals(""))
            {
                String [] dataMunicipio=parts[i].split("\\;") ;
                if (!dataMunicipio[0].equals(""))
                {
                    Municipio mun=new Municipio();
                    mun.setIdDpto(dataMunicipio[0]);
                    mun.setIdMpio(dataMunicipio[1]);
                    mun.setMunicipio(dataMunicipio[2]);
                    listaMunicipios.add(mun);
                }
            }
        }
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<Municipio> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }
}
