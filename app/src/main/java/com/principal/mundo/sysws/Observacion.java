package com.principal.mundo.sysws;

/**
 * Created by acer on 8/08/2017.
 */

public class Observacion {
    private String IdObservacion;
    private String Detalle;
    private boolean isSelected;
    private boolean isSelectedMas;
    private boolean isSelectedMenos;
    public Observacion(String idObservacion) {
        IdObservacion = idObservacion;
    }

    public Observacion() {
    }

    public String getIdObservacion() {
        return IdObservacion;
    }

    public void setIdObservacion(String idObservacion) {
        IdObservacion = idObservacion;
    }

    public String getDetalle() {
        String res="";
        if(isSelectedMas)
        {
            res="+ ";
        }
        if(isSelectedMenos)
        {
            res="- ";
        }
        return res+Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelectedMas() {
        return isSelectedMas;
    }

    public void setSelectedMas(boolean selectedMas) {
        isSelectedMas = selectedMas;
    }

    public boolean isSelectedMenos() {
        return isSelectedMenos;
    }

    public void setSelectedMenos(boolean selectedMenos) {
        isSelectedMenos = selectedMenos;
    }
}
