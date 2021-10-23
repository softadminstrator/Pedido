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
import android.widget.ImageView;
import android.widget.TextView;

import com.principal.mundo.Categoria;
import com.principal.persistencia.creaBD;

/**
 * Clase en que se describira cada uno de los elementos de las opciones de dialogo
 * @author Javier
 *
 */
public class CategoriaSpinnerAdapter extends ArrayAdapter<Categoria> {
    /**
     * Atributo clientes hace referencia a la lista de clientes a visualizar
     */
    ArrayList <Categoria> listaCategoria;
    /**
     * Atributo context hace referencia a la clase Context de la actividad
     */
    Categoria categoria;
    Context context;
    /**
     * Atributo fecha en el que se almacentara la fecha actual de sistema
     */
    String fecha="";
    creaBD bd;
    /**
     * metodo que recibe los parametros que seran asignados a los atributos de la clase
     * @param context

     */
    public CategoriaSpinnerAdapter(Context context ,int idCategoria , ArrayList<Categoria> listaCategoria)
    {
        super(context,idCategoria);
        this.listaCategoria = listaCategoria;
        this.context=context;
        bd=new creaBD(context);
    }
    /**
     * metodo que retorna el numero total de clientes a visualizar
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return listaCategoria.size();
    }
    /**
     * metodo que retorna el cliente que se encuentre en una pocision especifica del arreglo
     */
    public Categoria getItem(int i) {
        // TODO Auto-generated method stub
        return listaCategoria.get(i);
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
            convertView = inflater.inflate(R.layout.activity_item_categoria_spinner , null);
        }
        categoria= listaCategoria.get(position);
        if (categoria != null) {
//			LinearLayout linear=(LinearLayout)convertView.findViewById(R.id.llCategoria);
            TextView tvCategoriaItemSpinner = (TextView) convertView.findViewById(R.id.tvCategoriaItemSpinner);
            tvCategoriaItemSpinner.setText(getEstiloTexto(categoria.getNombre()));
        }
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item_categoria_spinner , null);
        }
        categoria= listaCategoria.get(position);
        if (categoria != null) {
//			LinearLayout linear=(LinearLayout)convertView.findViewById(R.id.llCategoria);
            TextView tvCategoriaItemSpinner = (TextView) convertView.findViewById(R.id.tvCategoriaItemSpinner);
            tvCategoriaItemSpinner.setText(getEstiloTexto(categoria.getNombre()));
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