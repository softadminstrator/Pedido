package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.principal.mundo.Factura_in;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VerFacturasCarteraAdapter extends ArrayAdapter<Factura_in>{
	
	ArrayList<Factura_in> listaFacturas;
	
	private boolean isPagosTotales;
	/**
	 * Atributo context  referencia de la clase Context
	 */
	Context context;
	/**
	 * Atributo letraEstilo referencia de la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	Factura_in fac;


	public VerFacturasCarteraAdapter(Context context, int resource , ArrayList<Factura_in> listaFacturas, boolean isPagosTotales) {
		super(context, resource);
		this.listaFacturas=listaFacturas;
		this.isPagosTotales=isPagosTotales;

		this.context=context;
	    letraEstilo=new LetraEstilo();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaFacturas.size();
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
			convertView = inflater.inflate(R.layout.activity_item_facturas_cartera, null);
		}
		
		CheckBox cbPago= (CheckBox) convertView.findViewById(R.id.cbPago);		
		TextView tvNFacturaC = (TextView) convertView.findViewById(R.id.tvNFacturaC);
		TextView tvCajaC = (TextView) convertView.findViewById(R.id.tvCajaC);
		TextView tvFechaC = (TextView) convertView.findViewById(R.id.tvFechaC);
		TextView tvTotalFacturaC = (TextView) convertView.findViewById(R.id.tvTotalFacturaC);
		TextView tvSaldoFacturaC = (TextView) convertView.findViewById(R.id.tvSaldoFacturaC);


		fac = listaFacturas.get(position);
		if (fac != null) {
			//VALIDA PAGOS TOTALES DE FACTURA
			cbPago.setFocusable(false);
			int visibility=View.VISIBLE;
			if(!isPagosTotales)
			{
				visibility=View.GONE;
			}
			cbPago.setVisibility(visibility);
//			cbPago.setSelected(fac.isPagada);
			cbPago.setChecked(fac.isPagada);
			cbPago.setEnabled(false);
			
//			cbPago.setOnCheckedChangeListener(
//					new OnCheckedChangeListener() {
//				
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					fac.isPagada=isChecked;				
//				}
//			});
			
			//DATOS DE LA FACTURA
			tvNFacturaC.setText(""+fac.getNFactura());
			tvCajaC.setText(""+fac.getNCaja());
//			tvCajaC.setText(""+fac.getPagada());
			tvFechaC.setText(fac.getFecha());
			//VALOR TOTAL Y SALDO FACTURA
			DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
			tvTotalFacturaC.setText(decimalFormat.format(fac.totalFactura));			
			tvSaldoFacturaC.setText(decimalFormat.format(fac.getSaldoFactura()));
		}
		getEstilo(tvNFacturaC);
		getEstilo(tvCajaC);
		getEstilo(tvFechaC);
		getEstilo(tvTotalFacturaC);	
		getEstilo(tvSaldoFacturaC);	
		
		
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
