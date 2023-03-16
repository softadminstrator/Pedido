package com.principal.mundo.sysws;

import com.principal.mundo.ArticulosFactura;

public class ItemFactura {
	private long IdArticulo;
    private double Cantidad;
    private long ValorUnitario;
    private long TipoIva;
    private long ImpoConsumo;
    private long Costo;
	private long tipoPrecio;

	public ItemFactura() {
		// TODO Auto-generated constructor stub
	}

	

	
	public ItemFactura(long idArticulo, double cantidad, long valorUnitario,
			long tipoIva, long impoConsumo, long costo) {
		super();
		IdArticulo = idArticulo;
		Cantidad = cantidad;
		ValorUnitario = valorUnitario;
		TipoIva = tipoIva;
		ImpoConsumo = impoConsumo;
		Costo = costo;
	}
	
	public ItemFactura(ArticulosFactura articuloFactura) {
		super();
		IdArticulo = articuloFactura.getIdArticulo();
		Cantidad = articuloFactura.getCantidad();
		ValorUnitario = articuloFactura.getValorUnitario();
		TipoIva = articuloFactura.getIva();
		ImpoConsumo = articuloFactura.getIpoConsumo();
		Costo = articuloFactura.getValorUnitario();
		tipoPrecio = articuloFactura.getTipoPrecio();
	}




	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 7;
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
		case 6: return tipoPrecio;
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
		case 6: return "tipoPrecio";
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
		case 6:  tipoPrecio=Long.parseLong(data);break;
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

	public long getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(long tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
}
