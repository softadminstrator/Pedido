package com.principal.factura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.principal.mundo.Parametros;
import com.principal.mundo.sysws.RutaMunicipios;

import java.util.ArrayList;

/**
 * Created by acer on 8/10/2018.
 */

public class ItemRutaAdapter  extends ArrayAdapter<RutaMunicipios> {
    ArrayList<RutaMunicipios> listaRutaMunicipios;
    Context context;
    LetraEstilo letraEstilo;
    RutaMunicipios rutaMunicipios;


    public ItemRutaAdapter(Context context, int resource , ArrayList<RutaMunicipios> listaVendedor) {
        super(context, resource);
        this.listaRutaMunicipios =listaVendedor;
        this.context=context;
        letraEstilo=new LetraEstilo();

        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaRutaMunicipios.size();
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
try {
    TextView tvNombreRuta = (TextView) convertView.findViewById(R.id.tvNombreRuta);

    rutaMunicipios = listaRutaMunicipios.get(position);
    if (rutaMunicipios != null) {
        tvNombreRuta.setText("" + rutaMunicipios.getNombreRuta());
    }
    getEstilo(tvNombreRuta);


}
catch ( Exception e)
{

}

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

            rutaMunicipios = listaRutaMunicipios.get(position);
            if (rutaMunicipios != null) {
                tvNombreRuta.setText("" + rutaMunicipios.getNombreRuta());
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
