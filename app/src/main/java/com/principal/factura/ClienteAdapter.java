package com.principal.factura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.principal.mundo.Cliente;
/**
 * Clase que determina la forma de visualizar un cliente en la lista del rutero
 * @author Javier
 *
 */
public class ClienteAdapter extends ArrayAdapter<Cliente> {
	private final static int PRESTAMOS=8;
	/**
	 * Atributo clientes hace referencia a la lista de clientes a visualizar
	 */
	ArrayList <Cliente> clientes;
	/**
	 * Atributo context hace referencia a la clase Context de la actividad
	 */
	Context context;
	/**
	 * Atributo fecha en el que se almacentara la fecha actual de sistema
	 */
	String fecha="";
	int operacion=0;
	/**
	 * metodo que recibe los parametros que seran asignados a los atributos de la clase
	 * @param context
	 * @param idCliente
	 * @param cliente
	 */
	 public ClienteAdapter(Context context ,int idCliente , ArrayList<Cliente> cliente, int operacion)
	 {
		 super(context,idCliente);
         this.clientes = cliente;
         this.context=context;
		 this.operacion=operacion;
         
	 }
	 /**
	  * metodo que retorna el numero total de clientes a visualizar
	  */
	public int getCount() {
		// TODO Auto-generated method stub
		return clientes.size();
	}
	/**
	 * metodo que retorna el cliente que se encuentre en una pocision especifica del arreglo
	 */
	public Cliente getItem(int i) {
		// TODO Auto-generated method stub
		return clientes.get(i);
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
			convertView = inflater.inflate(R.layout.activity_item_cliente, null);
		}
		Cliente cliente =new Cliente();
		cliente = clientes.get(position);
		if (cliente != null) {
			LinearLayout llClientes=(LinearLayout)convertView.findViewById(R.id.llClientes);
			TextView tvOrden = (TextView) convertView.findViewById(R.id.tvOrden);
			TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
				TextView tvDireccion = (TextView) convertView.findViewById(R.id.tvDireccion);
			TextView tvDiasGracia = (TextView) convertView.findViewById(R.id.tvDiasGracia);


			TextView tvTelefono = (TextView) convertView.findViewById(R.id.tvTelefono);

			TextView tvMotivoVisita = (TextView) convertView.findViewById(R.id.tvMotivoVisita);

			
			 Date fechaActual=new Date();
	         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
	         fecha = sdf.format(fechaActual);
			try {
				tvMotivoVisita.setVisibility(View.GONE);
				if (cliente.fechaUltimoPedido.equals(fecha)) {
					llClientes.setBackgroundColor(0xFF85E591); // verde
				} else if (cliente.fechaUltimaVenta.equals(fecha)) {
					llClientes.setBackgroundColor(0xFF369DF7); // azul
				} else if (cliente.fechaUltimaVisita.equals(fecha) & !cliente.fechaUltimaVisita.equals(cliente.fechaUltimoPedido)) {

					llClientes.setBackgroundColor(0xFFFFE96D); // amarillo
					tvMotivoVisita.setVisibility(View.VISIBLE);
					tvMotivoVisita.setText(cliente.getMotivoUltimaVisita());
				} else {
					llClientes.setBackgroundColor(0x0FFFFFFFF);
				}
				tvDiasGracia.setVisibility(View.GONE);
				if (operacion == PRESTAMOS) {
					tvDiasGracia.setVisibility(View.VISIBLE);
					tvDiasGracia.setText(""+cliente.getDias());
					if (cliente.getDias() > 2)
					//if(cliente.getDias()>cliente.getDiasGraciaCliente() )
					{
						llClientes.setBackgroundColor(0xFFD89393); // rojo
					}
				}

				tvOrden.setText(getEstiloTexto(Long.toString(cliente.ordenVisita)));
				tvNombre.setText(getEstiloTexto(cliente.nombre));
				tvDireccion.setText(getEstiloTexto(cliente.direccion));
				tvMotivoVisita.setVisibility(View.VISIBLE);
				tvMotivoVisita.setText(cliente.telefono);
				if (operacion == PRESTAMOS) {
					tvTelefono.setText(getEstiloTexto(cliente.deudaAntFac));
					tvDireccion.setText(getEstiloTexto(cliente.direccion + " " + cliente.telefono));
				} else {
					tvTelefono.setText(getEstiloTexto(cliente.getNit() + " " + cliente.getRepresentante()));
				}


			}
			catch (Exception e)
			{
				tvNombre.setText(e.toString());
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
