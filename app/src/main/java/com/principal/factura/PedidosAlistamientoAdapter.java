package com.principal.factura;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.principal.mundo.Factura_in;
import com.principal.mundo.sysws.Pedido;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 25/09/2018.
 */

public class PedidosAlistamientoAdapter extends ArrayAdapter<Pedido> {
    ArrayList<Pedido> listaPedidos;
    Context context;
    LetraEstilo letraEstilo;
    Pedido pedido;


    public PedidosAlistamientoAdapter(Context context, int resource , ArrayList<Pedido> listaPedidos) {
        super(context, resource);
        this.listaPedidos=listaPedidos;
        this.context=context;
        letraEstilo=new LetraEstilo();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaPedidos.size();
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
            convertView = inflater.inflate(R.layout.activity_item_lista_pedidos_alistamiento, null);
        }
        LinearLayout  LLItemPedidoAlistamiento = (LinearLayout) convertView.findViewById(R.id.LLItemPedidoAlistamiento);
        TextView tvPedidoAls = (TextView) convertView.findViewById(R.id.tvPedidoAls);
        TextView tvResponsableAls = (TextView) convertView.findViewById(R.id.tvResponsableAls);
        TextView tvNombreClienteAls = (TextView) convertView.findViewById(R.id.tvNombreClienteAls);
        TextView tvItemsTotal = (TextView) convertView.findViewById(R.id.tvItemsTotal);
        TextView tvMunicipioClienteAls = (TextView) convertView.findViewById(R.id.tvMunicipioClienteAls);
        TextView tvTotalPedidoAls = (TextView) convertView.findViewById(R.id.tvTotalPedidoAls);
        TextView tvNombreSeparadorAls = (TextView) convertView.findViewById(R.id.tvNombreSeparadorAls);
        TextView tvNombreRevisadorAls = (TextView) convertView.findViewById(R.id.tvNombreRevisadorAls);

        TextView tvItemsCrudos = (TextView) convertView.findViewById(R.id.tvItemsCrudos);
        TextView tvItemsSeparados = (TextView) convertView.findViewById(R.id.tvItemsSeparados);
        TextView tvItemsPendientes = (TextView) convertView.findViewById(R.id.tvItemsPendientes);
        TextView tvItemsAgotado = (TextView) convertView.findViewById(R.id.tvItemsAgotado);






        pedido = listaPedidos.get(position);
        if (pedido != null) {

            tvPedidoAls.setText(""+pedido.getNPedido()+" "+pedido.getEstado());
            tvResponsableAls.setText(""+pedido.getResponsable());
            tvNombreClienteAls.setText(""+pedido.getNombreCliente());
            tvItemsTotal.setText("Items:"+pedido.getItems());
            tvMunicipioClienteAls.setText(""+pedido.getMunicipioCliente());
            tvNombreSeparadorAls.setText(""+pedido.getNombreSeparador());
            tvNombreRevisadorAls.setText(""+pedido.getNombreRevisor());

            tvItemsCrudos.setText(""+pedido.getItemscrudos());
            tvItemsSeparados.setText(""+pedido.getItemsseparados());
            tvItemsPendientes.setText(""+pedido.getItemspendientes());
            tvItemsAgotado.setText(""+pedido.getItemsagotados());

try {

    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    tvTotalPedidoAls.setText(decimalFormat.format(Long.parseLong(pedido.getTotalPedido())));
}
catch (Exception e )
{
    tvTotalPedidoAls.setText("");
}

            if(pedido.getEstado().equals("SEPARADO"))
            {
                LLItemPedidoAlistamiento.setBackgroundColor(Color.parseColor("#CCCCCC"));

            }
            else if(pedido.getEstado().equals("REVISADO"))
            {
                LLItemPedidoAlistamiento.setBackgroundColor(Color.parseColor("#94CC9D"));

            }
            else if(pedido.getEstado().equals("DEPURADO"))
            {
                LLItemPedidoAlistamiento.setBackgroundColor(Color.parseColor("#AAB7FF"));

            }
            else
            {
                LLItemPedidoAlistamiento.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }
        getEstilo(tvPedidoAls);
        getEstilo(tvResponsableAls);
        getEstilo(tvNombreClienteAls);
        getEstilo(tvItemsTotal);
        getEstilo(tvMunicipioClienteAls);
        getEstilo(tvTotalPedidoAls);
        getEstilo(tvNombreSeparadorAls);
        getEstilo(tvNombreRevisadorAls);


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
