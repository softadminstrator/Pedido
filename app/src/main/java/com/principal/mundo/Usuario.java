package com.principal.mundo;

/**
 * Clase en la que se describe al usuario de la aplicacion
 * @author Javier
 *
 */
public class Usuario 
{
	/**
	 * cedula del usuario o numero de ruta
	 */
	public String cedula;
	/**
	 * clave de acceso al sistema  del usuario
	 */
	public String clave;

	/**
	 * constructo 
	 */
	public Usuario()
	{
		cedula="";
		clave="";
	}

	/**
	 * metodo que retorna la cedula del usuario del sistema
	 * @return cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * metodo que asigna el nuevo valor de la cedula del usuario
	 * @param cedula
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * metodo que retorna la clave de usuario
	 * @return clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * metodo que asigna el nuevo valor de la clave de acceso del usuario
	 * @param clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
