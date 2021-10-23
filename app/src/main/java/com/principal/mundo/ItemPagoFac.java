package com.principal.mundo;

public class ItemPagoFac {
	
	private long IdItemPagoFac;
	private long NPagoFac;
	private long Valor;
	private String FormaPago;
	private String NCheque;
	private String Tarjeta;
	

	public ItemPagoFac() {
		IdItemPagoFac=0;
		NPagoFac=0;
		Valor=0;
		FormaPago="";
		NCheque=" ";
		Tarjeta=" ";
	}
	public Object getProperty(int i) {
		switch(i)
		{
		case 0: return NPagoFac;	
		case 1: return Valor;		
		case 2: return FormaPago;	
		case 3: return NCheque;
		case 4: return Tarjeta;		
		}		
		return null;
	}
	
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "NPagoFac";	
		case 1: return "Valor";		
		case 2: return "FormaPago";	
		case 3: return "NCheque";
		case 4: return "Tarjeta";		
		}
		return null;
	}

	public void setProperty(int i, String data) {
		
		switch(i)
		{			
		case 0: NPagoFac=Long.parseLong(data);break;	
		case 1: Valor=Long.parseLong(data);break;			
		case 2: FormaPago=data;break;
		case 3: NCheque=data;break;
		case 4: Tarjeta=data;break;			
		}	
}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public long getNPagoFac() {
		return NPagoFac;
	}


	public void setNPagoFac(long nPagoFac) {
		NPagoFac = nPagoFac;
	}


	public long getValor() {
		return Valor;
	}


	public void setValor(long valor) {
		Valor = valor;
	}


	public String getFormaPago() {
		return FormaPago;
	}


	public void setFormaPago(String formaPago) {
		FormaPago = formaPago;
	}


	public String getNCheque() {
		return NCheque;
	}


	public void setNCheque(String nCheque) {
		NCheque = nCheque;
	}


	public String getTarjeta() {
		return Tarjeta;
	}


	public void setTarjeta(String tarjeta) {
		Tarjeta = tarjeta;
	}
	public long getIdItemPagoFac() {
		return IdItemPagoFac;
	}
	public void setIdItemPagoFac(long idItemPagoFac) {
		IdItemPagoFac = idItemPagoFac;
	}

}