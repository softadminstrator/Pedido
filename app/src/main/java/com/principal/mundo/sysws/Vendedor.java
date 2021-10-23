package com.principal.mundo.sysws;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 22/09/2018.
 */

public class Vendedor implements Parcelable {
    private String IdVendedor;
    private String Vendedor;
    private String Documento;
    private String Tipo;

    public Vendedor() {
        IdVendedor="";
        Vendedor="";
        Documento="";
        Tipo="";
        // TODO Auto-generated constructor stub
    }

    public String getIdVendedor() {
        return IdVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        IdVendedor = idVendedor;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getTipo() {
        return Tipo;
    }
    public String getTipoVendedor() {
        if(Tipo.equals("1"))
        {
            return "MOVIL";
        }
        else  if(Tipo.equals("2"))
        {
            return "SEPARADOR";
        }
        else  if(Tipo.equals("3"))
        {
            return "REVISOR";
        }
        else
        {
            return "ADMINISTRADOR";
        }

    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(IdVendedor);
        dest.writeString(Vendedor);
        dest.writeString(Documento);
        dest.writeString(Tipo);
    }
    public static final Creator<Vendedor> CREATOR = new Creator<Vendedor>() {
        public Vendedor createFromParcel(Parcel in) {
            return new Vendedor(in);
        }


        public Vendedor[] newArray(int size) {
            return new Vendedor[size];
        }
    };
    protected Vendedor(Parcel in) {
        IdVendedor = in.readString();
        Vendedor = in.readString();
        Documento = in.readString();
        Tipo = in.readString();
    }
}
