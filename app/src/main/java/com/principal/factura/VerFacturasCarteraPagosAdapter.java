package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.principal.mundo.ItemPagoFac;

public class VerFacturasCarteraPagosAdapter extends ArrayAdapter<ItemPagoFac>{
   ArrayList<ItemPagoFac> listaItemPagosFacturas;
	/**
	 * Atributo context  referencia de la clase Context
	 */
	Context context;
	/**
	 * Atributo letraEstilo referencia de la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	ItemPagoFac itemPagoFac;


	public VerFacturasCarteraPagosAdapter(Context context, int resource , ArrayList<ItemPagoFac> listaItemPagosFacturas) {
		super(context, resource);
		this.listaItemPagosFacturas=listaItemPagosFacturas;
		this.context=context;
	    letraEstilo=new LetraEstilo();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaItemPagosFacturas.size();
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
			convertView = inflater.inflate(R.layout.activity_item_facturas_cartera_pagos, null);
		}		
		TextView tvItemPagoValor = (TextView) convertView.findViewById(R.id.tvItemPagoValor);
		TextView tvItemPagoForma = (TextView) convertView.findViewById(R.id.tvItemPagoForma);
		itemPagoFac = listaItemPagosFacturas.get(position);
		if (itemPagoFac != null) {
			
			DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
			tvItemPagoValor.setText(decimalFormat.format(itemPagoFac.getValor()));			
			tvItemPagoForma.setText(itemPagoFac.getFormaPago());
		}
		getEstilo(tvItemPagoValor);
		getEstilo(tvItemPagoForma);	
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
