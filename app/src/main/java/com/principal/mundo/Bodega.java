package com.principal.mundo;

public class Bodega {
	private int IdBodega;
    private String Bodega;
    private String Direccion;
    private String Telefono;
    private String Responsable;
    private String Municipio;

	public Bodega() {
		// TODO Auto-generated constructor stub
	}

	public Bodega(int idBodega, String bodega, String direccion,
			String telefono, String responsable, String municipio) {
		super();
		IdBodega = idBodega;
		Bodega = bodega;
		Direccion = direccion;
		Telefono = telefono;
		Responsable = responsable;
		Municipio = municipio;
	}

	public int getIdBodega() {
		return IdBodega;
	}

	public void setIdBodega(int idBodega) {
		IdBodega = idBodega;
	}

	public String getBodega() {
		return Bodega;
	}

	public void setBodega(String bodega) {
		Bodega = bodega;
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

	public String getResponsable() {
		return Responsable;
	}

	public void setResponsable(String responsable) {
		Responsable = responsable;
	}

	public String getMunicipio() {
		return Municipio;
	}

	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}
	

}
