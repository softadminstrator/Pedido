package com.principal.factura;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
/**
 * Clase que se encarga de dar estilos de letra a los textos que reciba
 * en los metodos que contiene
 * @author Javier
 *
 */
public class LetraEstilo 
{

	public LetraEstilo()
	{		
	}
	/**
	 * Metodo que se encarga de dar estilo de letra con negrilla
	 * @param texto
	 * @return Texto modificado con estilo
	 */
	public SpannableString getEstiloNegrilla(String texto)
	{
		 SpannableString spanString = new SpannableString(texto);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  spanString.setSpan(new ForegroundColorSpan(Color.BLACK),0, spanString.length(), 0);
		  return spanString;
	}
	/**
	 * Metodo que se encarga de dar estilo de letra con estilo sensillo
	 * @param texto
	 * @return Texto modificado con estilo
	 */
	public SpannableString getEstiloSencillo(String texto)
	{
		 SpannableString spanString = new SpannableString(texto);
		  spanString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  spanString.setSpan(new ForegroundColorSpan(Color.BLACK),0, spanString.length(), 0);
		  return spanString;
	}
	
	/**
	 * Metodo que se encarga de dar estilo de letra con estilo negrilla pero texto de color blanco
	 * para los titulos de los mensajes mostrados al cliente
	 * @param texto
	 * @return Texto modificado con estilo
	 */
	public SpannableString getEstiloTitulo(String texto)
	{
		 SpannableString spanString = new SpannableString(texto);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  spanString.setSpan(new ForegroundColorSpan(Color.WHITE),0, spanString.length(), 0);
		  return spanString;
	}

}
