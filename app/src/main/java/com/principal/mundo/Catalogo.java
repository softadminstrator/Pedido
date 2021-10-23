package com.principal.mundo;

import java.util.ArrayList;

public class Catalogo {
	private long idCatalogo;
	private String nombre;
	private long activo;	
	private ArrayList<Articulo> listaArticulos;
	
	public Catalogo() {
		super();
		listaArticulos=new ArrayList<Articulo>();
	}
	
	
	
	public Catalogo(long idCatalogo, String nombre, long activo,
			ArrayList<Articulo> listaArticulos) {
		super();
		this.idCatalogo = idCatalogo;
		this.nombre = nombre;
		this.activo = activo;
		this.listaArticulos = listaArticulos;
	}



	public long getIdCatalogo() {
		return idCatalogo;
	}
	public void setIdCatalogo(long idCatalogo) {
		this.idCatalogo = idCatalogo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Articulo> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(ArrayList<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	public long getActivo() {
		return activo;
	}
	public void setActivo(long activo) {
		this.activo = activo;
	}
	public String  valspace(String value)
	{
		String res="";
		int val=0;
		for (int i = 0; i < value.length(); i++) {
			if(val>1)
			{
				res=value.substring(0, i-2);
				return res;
			}
			else if(value.charAt(i)==' ')
			{
				val++;	
			}
			else
			{
				val=0;
			}
		}
		return res;
	}
}
