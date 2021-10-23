package com.principal.factura;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.principal.mundo.Parametros;
import com.principal.mundo.sysws.ItemPedido;
import com.principal.mundo.sysws.Vendedor;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 8/10/2018.
 */

public class ItemVendedorAdapter extends ArrayAdapter<Vendedor> {
    ArrayList<Vendedor> listaVendedor;
    Context context;
    LetraEstilo letraEstilo;
    Vendedor vendedor;


    public ItemVendedorAdapter(Context context, int resource , ArrayList<Vendedor> listaVendedor) {
        super(context, resource);
        this.listaVendedor=listaVendedor;
        this.context=context;
        letraEstilo=new LetraEstilo();

        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaVendedor.size();
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * metodo que se encarga de asignar valores a las etiquetas que describiran
     * el pedido en la lista
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item_vendedor_ruta, null);
        }

        TextView tvNombreRuta = (TextView) convertView.findViewById(R.id.tvNombreRuta);

        vendedor = listaVendedor.get(position);
        if (vendedor != null) {
            tvNombreRuta.setText("" + vendedor.getVendedor());
        }
        getEstilo(tvNombreRuta);




        return convertView;

    }
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item_vendedor_ruta, null);
        }

        TextView tvNombreRuta = (TextView) convertView.findViewById(R.id.tvNombreRuta);

        vendedor = listaVendedor.get(position);
        if (vendedor != null) {
            tvNombreRuta.setText("" + vendedor.getVendedor());
        }
        getEstilo(tvNombreRuta);
        return convertView;
    }


    /**
     * metodo que se encarga de dar estilo al texto de las etiquetas
     * @param tv
     */
    public void getEstilo(TextView tv)
    {
        tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
    }
}
