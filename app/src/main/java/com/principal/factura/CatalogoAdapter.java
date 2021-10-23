package com.principal.factura;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.principal.mundo.Catalogo;
import com.principal.persistencia.creaBD;

public class CatalogoAdapter extends ArrayAdapter<Catalogo> {	
	/**
	 * Atributo clientes hace referencia a la lista de clientes a visualizar
	 */
	ArrayList <Catalogo> listaCatalogo;
	/**
	 * Atributo context hace referencia a la clase Context de la actividad
	 */
	Catalogo catalogo;
	Context context;
	/**
	 * Atributo fecha en el que se almacentara la fecha actual de sistema
	 */
	String fecha="";
	creaBD bd;
	/**
	 * metodo que recibe los parametros que seran asignados a los atributos de la clase
	 * @param context
	 * @param idCliente
	 * @param cliente
	 */
	 public CatalogoAdapter(Context context ,int idCatalogo , ArrayList<Catalogo> listaCatalogo) 
	 {
		 super(context,idCatalogo);
         this.listaCatalogo = listaCatalogo;
         this.context=context;
         bd=new creaBD(context);
	 }
	 /**
	  * metodo que retorna el numero total de clientes a visualizar
	  */
	public int getCount() {
		// TODO Auto-generated method stub
		return listaCatalogo.size();
	}
	/**
	 * metodo que retorna el cliente que se encuentre en una pocision especifica del arreglo
	 */
	public Catalogo getItem(int i) {
		// TODO Auto-generated method stub
		return listaCatalogo.get(i);
	}
	/**
	 * metodo que retornara 0 en caso de que se invoque el valor del identificador del item
	 */
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Metodo que se encarga de crear las cajas de texto en donde se plasmara la informacion del 
	 * cliente en la lista del rutero
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_item_catalogo, null);
		}
		catalogo= listaCatalogo.get(position);
		if (catalogo != null) {
			TextView tvItemCatalogoNombre = (TextView) convertView.findViewById(R.id.tvItemCatalogoNombre);
			tvItemCatalogoNombre.setText(getEstiloTexto(catalogo.getNombre()));						
		}
		return convertView;
	}
	/**
	 * metodo se encarga dar estilo a el texto que ira en cada una de las cajas de texto
	 * @param texto
	 * @return
	 */
	public SpannableString getEstiloTexto(String texto)
	{
		 SpannableString spanString = new SpannableString(texto);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  spanString.setSpan(new ForegroundColorSpan(Color.BLACK),0, spanString.length(), 0);
		  return spanString;
	}
}