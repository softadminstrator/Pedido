package com.principal.mundo;

public class MediosDePago {
	
	public String MedioPago;
	public long Valor;
	public long Cuenta;
	
	public MediosDePago() {
		// TODO Auto-generated constructor stub
	}

	public MediosDePago(String medioPago, long valor, long cuenta) {
		MedioPago = medioPago;
		Valor = valor;
		Cuenta = cuenta;
	}

	public String getMedioPago() {
		return MedioPago;
	}

	public void setMedioPago(String medioPago) {
		MedioPago = medioPago;
	}

	public long getValor() {
		return Valor;
	}

	public void setValor(long valor) {
		Valor = valor;
	}

	public long getCuenta() {
		return Cuenta;
	}

	public void setCuenta(long cuenta) {
		Cuenta = cuenta;
	}
	

}
