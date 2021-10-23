package com.principal.mundo.sysws;

import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosRemision;

public class ItemRemision {
    private long IdArticulo;
    private double Cantidad;
    private long ValorUnitario;
    private long TipoIva;
    private long ImpoConsumo;
    private long Costo;

    public ItemRemision() {
        // TODO Auto-generated constructor stub
    }




    public ItemRemision(long idArticulo, double cantidad, long valorUnitario,
                       long tipoIva, long impoConsumo, long costo) {
        super();
        IdArticulo = idArticulo;
        Cantidad = cantidad;
        ValorUnitario = valorUnitario;
        TipoIva = tipoIva;
        ImpoConsumo = impoConsumo;
        Costo = costo;
    }

    public ItemRemision(ArticulosRemision articuloRemision) {
        super();
        IdArticulo = articuloRemision.getIdArticulo();
        Cantidad = articuloRemision.getCantidad();
        ValorUnitario = articuloRemision.getValorUnitario();
        TipoIva = articuloRemision.getIva();
        ImpoConsumo = articuloRemision.getIpoConsumo();
        Costo = articuloRemision.getValorUnitario();
    }




    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 6;
    }
    public Object getProperty(int i) {
        switch(i)
        {
            case 0: return IdArticulo;
            case 1: return Cantidad;
            case 2: return ValorUnitario;
            case 3: return TipoIva;
            case 4: return ImpoConsumo;
            case 5: return Costo;
        }
        return null;
    }

    public String getPropertyName(int i) {
        switch(i)
        {
            case 0: return "IdArticulo";
            case 1: return "Cantidad";
            case 2: return "ValorUnitario";
            case 3: return "TipoIva";
            case 4: return "ImpoConsumo";
            case 5: return "Costo";
        }
        return null;
    }
    public void setProperty(int i, String data) {

        switch(i)
        {
            case 0: IdArticulo =Long.parseLong(data);break;
            case 1: Cantidad =Long.parseLong(data);break;
            case 2:  ValorUnitario =Long.parseLong(data);break;
            case 3:  TipoIva =Long.parseLong(data);break;
            case 4:  ImpoConsumo =Long.parseLong(data);break;
            case 5:  Costo=Long.parseLong(data);break;
        }
    }



    public long getIdArticulo() {
        return IdArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        IdArticulo = idArticulo;
    }

    public double getCantidad() {
        return Cantidad;
    }
    public double getTotal() {
        return Cantidad*ValorUnitario;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public long getValorUnitario() {
        return ValorUnitario;
    }

    public void setValorUnitario(long valorUnitario) {
        ValorUnitario = valorUnitario;
    }



    public long getTipoIva() {
        return TipoIva;
    }



    public void setTipoIva(long tipoIva) {
        TipoIva = tipoIva;
    }



    public long getImpoConsumo() {
        return ImpoConsumo;
    }



    public void setImpoConsumo(long impoConsumo) {
        ImpoConsumo = impoConsumo;
    }

    public long getCosto() {
        return Costo;
    }
    public void setCosto(long costo) {
        Costo = costo;
    }
}
