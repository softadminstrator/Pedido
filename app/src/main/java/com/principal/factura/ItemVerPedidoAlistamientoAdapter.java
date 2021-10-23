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

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 27/09/2018.
 */

public class ItemVerPedidoAlistamientoAdapter extends ArrayAdapter<ItemPedido> {
    ArrayList<ItemPedido> listaItemPedido;
    Context context;
    LetraEstilo letraEstilo;
    ItemPedido itemPedido;
    private Parametros parametrosPos;

    public ItemVerPedidoAlistamientoAdapter(Context context, int resource , ArrayList<ItemPedido> listaItemPedido,Parametros parametrosPos) {
        super(context, resource);
        this.listaItemPedido=listaItemPedido;
        this.context=context;
        letraEstilo=new LetraEstilo();
        this.parametrosPos=parametrosPos;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaItemPedido.size();
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
            convertView = inflater.inflate(R.layout.activity_item_ver_pedido_alistamiento, null);
        }
        LinearLayout LLItemVerPedido = (LinearLayout) convertView.findViewById(R.id.LLItemVerPedido);
        TextView tvCantidadPedidoAls = (TextView) convertView.findViewById(R.id.tvCantidadPedidoAls);
        TextView tvNombreArticuloPedidoAls = (TextView) convertView.findViewById(R.id.tvNombreArticuloPedidoAls);
        TextView tvObservacionItemPedidoAls = (TextView) convertView.findViewById(R.id.tvObservacionItemPedidoAls);
        TextView tvObservacionAlistamientoPedidoAls = (TextView) convertView.findViewById(R.id.tvObservacionAlistamientoPedidoAls);
        TextView tvValorUnitarioPedidoAls = (TextView) convertView.findViewById(R.id.tvValorUnitarioPedidoAls);
        TextView tvTTotalPedidoAls = (TextView) convertView.findViewById(R.id.tvTTotalPedidoAls);
        TextView tvCodigoPedidoAls = (TextView) convertView.findViewById(R.id.tvCodigoPedidoAls);
        TextView tvStockPedidoAls = (TextView) convertView.findViewById(R.id.tvStockPedidoAls);



        itemPedido = listaItemPedido.get(position);
        if (itemPedido != null) {
            if(parametrosPos.getUsaCantDecimal()==1) {
                tvCantidadPedidoAls.setText("" + itemPedido.getCantidad());
            }
            else
            {
                tvCantidadPedidoAls.setText("" + ((long)itemPedido.getCantidad()));
            }
            tvNombreArticuloPedidoAls.setText(""+itemPedido.getNombreArticulo());

            tvCodigoPedidoAls.setText(""+itemPedido.getCodigo());
            tvStockPedidoAls.setText(""+itemPedido.getStock());

            if(itemPedido.getObservacion().equals(""))
            {
                tvObservacionItemPedidoAls.setVisibility(View.GONE);
            }
            else
            {
                tvObservacionItemPedidoAls.setVisibility(View.VISIBLE);
            }
            if(itemPedido.getObservacionAls().equals(""))
            {
                tvObservacionAlistamientoPedidoAls.setVisibility(View.GONE);
            }
            else
            {
                tvObservacionAlistamientoPedidoAls.setVisibility(View.VISIBLE);
            }
            tvObservacionItemPedidoAls.setText(""+itemPedido.getObservacion());
            tvObservacionAlistamientoPedidoAls.setText(""+itemPedido.getObservacionAls());
            try {

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tvValorUnitarioPedidoAls.setText(decimalFormat.format(itemPedido.getValorUnitario()));
                tvTTotalPedidoAls.setText(decimalFormat.format((long)itemPedido.getTotal()));
                tvCodigoPedidoAls.setText(""+itemPedido.getCodigo());
                tvStockPedidoAls.setText(""+itemPedido.getStock());

            }
            catch (Exception e )
            {
                tvValorUnitarioPedidoAls.setText("0");
                tvTTotalPedidoAls.setText("0");
                tvCodigoPedidoAls.setVisibility(View.GONE);
                tvStockPedidoAls.setVisibility(View.GONE);

            }

            if(itemPedido.getEstadoAls().equals("S"))
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#C6CFFF"));

            }
            else if(itemPedido.getEstadoAls().equals("A"))
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#C68787"));

            }
            else if(itemPedido.getEstadoAls().equals("P"))
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#FFEDA8"));

            }
            else if(itemPedido.getEstadoAls().equals("E"))
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#72C182"));

            }
            else if(itemPedido.getEstadoAls().equals("R"))
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#82509B"));

            }
            else
            {
                LLItemVerPedido.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }
        getEstilo(tvCantidadPedidoAls);
        getEstilo(tvNombreArticuloPedidoAls);
       // getEstilo(tvObservacionItemPedidoAls);
        //getEstilo(tvObservacionAlistamientoPedidoAls);
        getEstilo(tvValorUnitarioPedidoAls);
        getEstilo(tvTTotalPedidoAls);


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

