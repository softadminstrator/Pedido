package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.principal.mundo.Cliente;
import com.principal.mundo.Parametros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VerConsultaClientesAdapter extends ArrayAdapter<Cliente> {

	private final static int PRESTAMOS=8;
	private ArrayList<Cliente> listaClientes;
	Context context;
	LetraEstilo letraEstilo;
	private Parametros parametros;
	private int operacion;

	
	
	public VerConsultaClientesAdapter(Context context, int resource, ArrayList<Cliente> listaClientes, int operacionConsulta) {
		super(context, resource);
		this.listaClientes=listaClientes;
		this.context=context;
		letraEstilo=new LetraEstilo();
		operacion=operacionConsulta;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_item_consulta_clientes, null);
		}
		Cliente cliente=listaClientes.get(position);
		if(cliente!=null)
		{
			TextView tvNombreClienteCt = (TextView) convertView.findViewById(R.id.tvNombreClienteCt);
			TextView tvDeudaCt = (TextView) convertView.findViewById(R.id.tvDeudaCt);
			TextView tvFechaUltimoPago = (TextView) convertView.findViewById(R.id.tvFechaUltimoPago);

			
			tvNombreClienteCt.setText(cliente.getNombre());
			tvFechaUltimoPago.setText("");
			if(operacion==PRESTAMOS)
			{
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvDeudaCt.setText(decimalFormat.format(Long.parseLong(cliente.getDeudaAntFac())));
				tvFechaUltimoPago.setText(cliente.getFechaUltimoPago());
			}
			else
			{
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvDeudaCt.setText(decimalFormat.format(cliente.getDeudaActual()));
			}

			
			getEstilo(tvNombreClienteCt);
			getEstilo(tvDeudaCt);
			
		}
		
		
		return convertView;
	}
	
	public void getEstilo(TextView tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
	
	@Override
	public int getCount() {
		return listaClientes.size();
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
