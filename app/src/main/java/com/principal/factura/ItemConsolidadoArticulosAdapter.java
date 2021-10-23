package com.principal.factura;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.principal.mundo.Articulo;
import com.principal.persistencia.creaBD;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 26/09/2018.
 */

public class ItemConsolidadoArticulosAdapter extends ArrayAdapter<Articulo> {
    /**
     * Atributo clientes hace referencia a la lista de clientes a visualizar
     */
    ArrayList<Articulo> listaArticulos;
    /**
     * Atributo context hace referencia a la clase Context de la actividad
     */
    Articulo articulo;
    Context context;
    /**
     * Atributo fecha en el que se almacentara la fecha actual de sistema
     */
    String fecha="";
    creaBD bd;


    public ItemConsolidadoArticulosAdapter(Context context ,int idCategoria , ArrayList<Articulo> listaArticulos)
    {
        super(context,idCategoria);
        this.listaArticulos = listaArticulos;
        this.context=context;
        bd=new creaBD(context);
    }
    /**
     * metodo que retorna el numero total de clientes a visualizar
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return listaArticulos.size();
    }
    /**
     * metodo que retorna el cliente que se encuentre en una pocision especifica del arreglo
     */
    public Articulo getItem(int i) {
        // TODO Auto-generated method stub
        return listaArticulos.get(i);
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
            convertView = inflater.inflate(R.layout.activity_item_consolidado_alistamiento, null);
        }
        articulo= listaArticulos.get(position);
        if (articulo != null) {
            TextView tvArticuloAls = (TextView) convertView.findViewById(R.id.tvArticuloAls);
            TextView tvCantidadAls = (TextView) convertView.findViewById(R.id.tvCantidadAls);
            LinearLayout llCategoriaAls= (LinearLayout) convertView.findViewById(R.id.llCategoriaAls);

            tvArticuloAls.setText(getEstiloTexto(articulo.getNombre()));
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            tvCantidadAls.setText(getEstiloTexto(decimalFormat.format(articulo.getCantidadVentas())));

            if(articulo.getEstadoAls().equals("S"))
            {
                llCategoriaAls.setBackgroundColor(Color.parseColor("#C6CFFF"));

            }
            else
            {
                llCategoriaAls.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

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

