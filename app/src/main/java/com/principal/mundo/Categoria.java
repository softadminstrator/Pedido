package com.principal.mundo;

public class Categoria {
	private long IdCategoria;
	private String Nombre;
	private String FechaAct;
	private long activo;	
	private long habilidada;
	private long valor;
	private long unidades;
	private String cantidad;
	
	
	public Categoria(long idCategoria, String nombre, String fechaAct,
			long activo, long habilidada, long valor, long unidades) {
		super();
		IdCategoria = idCategoria;
		Nombre = nombre;
		FechaAct = fechaAct;
		this.activo = activo;
		this.habilidada = habilidada;
		this.valor = valor;
		this.unidades = unidades;
	}

	public Categoria() {
		super();
	}
	
	public int getPropertyCount()
	{
		return 5;
	}
	public String getPropertyName(int i) {
		switch(i)
		{
		case 0: return "IdCategoria";
		case 1: return "Nombre";
		case 2: return "FechaAct";
		case 3: return "activo";
		case 4: return "habilidada";	
		}
		return null;
	}
	public void setProperty(int i, String text)
	{
		switch(i)
		{
		case 0: IdCategoria=Long.parseLong(text);break;
		case 1: Nombre=text;break;
		case 2: FechaAct=text;break;
		case 3: activo=Long.parseLong(text);break;
		case 4: habilidada=Long.parseLong(text);break;
		}
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

	public long getUnidades() {
		return unidades;
	}

	public void setUnidades(long unidades) {
		this.unidades = unidades;
	}

	public long getIdCategoria() {
		return IdCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		IdCategoria = idCategoria;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getFechaAct() {
		return FechaAct;
	}
	public void setFechaAct(String fechaAct) {
		FechaAct = fechaAct;
	}
	public long getActivo() {
		return activo;
	}
	public void setActivo(long activo) {
		this.activo = activo;
	}
	public long getHabilidada() {
		return habilidada;
	}
	public void setHabilidada(long habilidada) {
		this.habilidada = habilidada;
	}
	public void setHabilidada() {
		if(habilidada==0)
		{
			habilidada=1;
		}
		else
		{
			habilidada=0;
		}
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

	//-------------------------------------------------------------------------------
	//ALISTAMEINTO CONSOLIDADO ARTICULOS
	//------------------------------------------------------------------------------
	public int getPropertyCountAlsConsolidado() {
		// TODO Auto-generated method stub
		return 3;
	}
	public Object getPropertyAlsConsolidado(int i) {
		switch(i)
		{
			case 0: return IdCategoria;
			case 1: return Nombre;
			case 2: return cantidad;
		}
		return null;
	}

	public String getPropertyNameAlsConsolidado(int i) {
		switch(i)
		{
			case 0: return "IdCategoria";
			case 1: return "Nombre";
			case 2: return "cantidad";
		}
		return null;
	}
	public void setPropertyAlsConsolidado(int i, String data) {

		switch(i)
		{
			case 0:  IdCategoria =Long.parseLong(data);break;
			case 1:  Nombre =data;break;
			case 2:  cantidad=data;break;
		}
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
}
