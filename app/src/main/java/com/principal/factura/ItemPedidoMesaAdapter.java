package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.principal.mundo.sysws.ItemPedido;

public class ItemPedidoMesaAdapter extends ArrayAdapter<ItemPedido>{
    
	final ArrayList <ItemPedido>  listaArticulos;
	Context context;
	LetraEstilo letraEstilo;
	
	
	public ItemPedidoMesaAdapter(Context context, int resource, ArrayList <ItemPedido> listaArticulos) {
		super(context, resource);
		this.listaArticulos=listaArticulos;
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	public int getCount() {
		return listaArticulos.size();
	}
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try
		{
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_item_pedido_mesa, null);
		}
		final TextView tvCantidad = (TextView) convertView.findViewById(R.id.tvCantidadPM);
		final TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombreaPM);
		final TextView tvPrecioU = (TextView) convertView.findViewById(R.id.tvPrecioUPM);
		final TextView tvPrecioT = (TextView) convertView.findViewById(R.id.tvPrecioTPM);
		final ImageView ivPrint = (ImageView) convertView.findViewById(R.id.ivPrint);
		final TextView tvObserArticulo = (TextView) convertView.findViewById(R.id.tvObserArticulo);
		final TextView tvObserArticuloItem = (TextView) convertView.findViewById(R.id.tvObserArticuloItem);

		
		
		//LinearLayout llItemPedidoMesa=(LinearLayout)convertView.findViewById(R.id.llItemPedidoMesa);
				
		if(listaArticulos!=null)
		{
			ItemPedido itemPedido = listaArticulos.get(position);
			if (itemPedido != null) {			
				tvCantidad.setText(Double.toString(itemPedido.getCantidad()));
				tvNombre.setText(itemPedido.getNombreArticulo());
	//-----------------------Observaciones Articulo-------------------------------------
				tvObserArticulo.setText(itemPedido.getObservacionesArticulo());
	//-----------------------Observaciones Articulo-------------------------------------
				tvObserArticuloItem.setText(itemPedido.getObservacionAls());
	//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioU.setText(decimalFormat.format(itemPedido.getValorUnitario()));
	//-----------------------Valor total----------------------------------------------
				tvPrecioT.setText(decimalFormat.format(itemPedido.getTotal()));	
				if(itemPedido.getEnCocina().equals("NO"))	         
				{
					ivPrint.setVisibility(View.GONE);
//					llItemPedidoMesa.setBackgroundColor(0xFF85E591);			  		
				}
				else
				{
					ivPrint.setVisibility(View.VISIBLE);
				}
			}
			  
		}	
		
		getEstilo(tvCantidad);
		getEstilo(tvNombre);
		getEstilo(tvPrecioU);
		getEstilo(tvPrecioT);
		}
		catch (Exception e) {
			System.out.println("Error "+e.toString());
		}
		return convertView;
	}
	/**
	 * metodo que se encarga de dar estilo a cada uno de las cajas de texto de la vista
	 * @param tv
	 */
	
	public void getEstilo(TextView tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}

}
