package com.principal.mundo;

import android.graphics.drawable.Drawable;
/**
 * Clase se encarga de la descripcion de las opciones de los dialogos de seleccion
 * @author Javier
 *
 */
public class Opciones {
	/**
	 * nombre o texto que ira en la opcion de menu
	 */
	private String   _name;
	private int   _identificador;
	/**
	 * imagen que se asignara en la opcion de menu
	 */
	private Drawable _img;
	/**
	 * identificador asignado a la opcion de menu
	 */
	private String   _val;

	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param name
	 * @param img
	 * @param val
	 */
	public Opciones( int identificador,String name, Drawable img, String val ) {
		_name = name;
		_img = img;
		_val = val;
		_identificador=identificador;
		}
	
	public Opciones( String name, Drawable img, String val ) {
		_name = name;
		_img = img;
		_val = val;
	
		}

		public int get_identificador() {
			return _identificador;
		}

		public void set_identificador(int _identificador) {
			this._identificador = _identificador;
		}
	

	/**
	 * metodo que retorna el nombre de la opcion
	 * @return _name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * metodo la image que se mostrara en las opciones de menu del dialogo
	 * @return _img
	 */
	public Drawable getImg() {
		return _img;
	}

	/**
	 * metodo que retorna el identificador  de la opcion de menu
	 * @return _val
	 */
	public String getVal() {
		return _val;
	}

	@Override
	public String toString() {
		return _name;
	}
}
