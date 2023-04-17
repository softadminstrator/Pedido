package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.principal.mundo.Factura_in;
import com.principal.mundo.Pago;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;

/**
 * Clase en la que se describe cada pedido que se mostrara en la lista
 * @author Javier
 *
 */
public class ListaDocumentosAdapterActivity extends ArrayAdapter<Pedido_in>
{
	/**
	 * Atributo pedidos hace referencia a el arreglo de Pedido_in que se mostraran en la lista
	 */
	ArrayList <Pedido_in> pedidos;
	
	ArrayList <Factura_in> facturas;

	ArrayList <Remision_in > remisiones;
	
	ArrayList <Traslado_in> traslados;
	
	ArrayList <Pago> pagos;

	ArrayList <Prestamo> prestamos;

	ArrayList <PagoPrestamo> pagoPrestamos;

	ArrayList <Libro> libros;



	/**
	 * Atributo context  referencia de la clase Context
	 */
	Context context;
	/**
	 * Atributo letraEstilo referencia de la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;

	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 * @param context
	 * @param pedidosNum
	 * @param pedidos
	 */
	public ListaDocumentosAdapterActivity(Context context, int pedidosNum, ArrayList<Pedido_in> pedidos, ArrayList<Factura_in> facturas, ArrayList<Traslado_in> traslados, ArrayList<Pago> pagos, ArrayList<Prestamo> prestamos, ArrayList<PagoPrestamo> pagoPrestamos, ArrayList<Libro> libros, ArrayList<Remision_in> remisiones )
	{
		super(context, pedidosNum);
		this.pedidos = pedidos;
		this.facturas=facturas;
		this.traslados=traslados;
		this.pagos=pagos;
		this.prestamos=prestamos;
		this.pagoPrestamos=pagoPrestamos;
		this.libros=libros;
		this.remisiones=remisiones;
	    this.context=context;
	    letraEstilo=new LetraEstilo();
	  
		
	}
	
	/**
	 * metodo que retorna el numero total de pedidos a enviar
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		if(pedidos!=null)
		{
			return pedidos.size();
		}
		else if(facturas!=null)
		{
			return facturas.size();
		}
		else if(pagos!=null)
		{
			return pagos.size();
		}
		else if(prestamos!=null)
		{
			return prestamos.size();
		}
		else if(pagoPrestamos!=null)
		{
			return pagoPrestamos.size();
		}
		else if(remisiones!=null)
		{
			return remisiones.size();
		}
		else if(libros!=null)
		{
			return libros.size();
		}
		else 
		{
			return traslados.size();
		}
		
	}

	/**
	 * metodo que retorna 0 en caso de querer obtener el valor del item especifico
	 */
	public long getItemId(int arg0) {
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
			convertView = inflater.inflate(R.layout.activity_item_pedido, null);
		}
		TextView tvCodigoExterno = (TextView) convertView.findViewById(R.id.tvCodigoExterno);
		TextView tvNombreClientep = (TextView) convertView.findViewById(R.id.tvNombreClienteP);
		TextView tvHora = (TextView) convertView.findViewById(R.id.tvHoraPedido);
		TextView tvPrecioTPedido = (TextView) convertView.findViewById(R.id.tvPrecioTPedido);
		LinearLayout linea =(LinearLayout) convertView.findViewById(R.id.LLPedido);
		TextView tvTipoDocumento = (TextView) convertView.findViewById(R.id.tvTipoDocumento);
		TextView tvFormaPagoPedido = (TextView) convertView.findViewById(R.id.tvFormaPagoPedido);
		tvTipoDocumento.setText("");
		tvFormaPagoPedido.setText("");
		if(pedidos!=null)
		{
				Pedido_in ped = pedidos.get(position);
				if (ped != null) {
					
					//if(ped.idCodigoExterno== 0)
					int intbkColor=0x00000000;
					if(ped.getEnvio()== 0)
					{
						intbkColor=0xFFFF8787;
						//linea.setBackgroundColor(0xFFFF8787);
					}
					else if(ped.getEstado().equals("1"))
					{
						intbkColor=0xFF2D8DDD;
						//linea.setBackgroundColor(0xFF2D8DDD);
					}
					else if(ped.getEstado().equals("3"))
					{
						intbkColor=0xFFC4D328;
						//linea.setBackgroundColor(0xFFC4D328);
					}

					linea.setBackgroundColor(intbkColor);
					tvCodigoExterno.setBackgroundColor(intbkColor);
					if(ped.getTipoPedido().equals("E"))
					{
						tvCodigoExterno.setBackgroundColor(0xFF2D8DDD);
					}





					tvCodigoExterno.setText(Long.toString(ped.idCodigoInterno ));
					tvTipoDocumento.setText(ped.getTipoDocumento());
					tvFormaPagoPedido.setText(ped.getTipoFormaPago());

					//tvCodigoExterno.setText(Long.toString(ped.idCodigoInterno));
					tvNombreClientep.setText(ped.nombreCliente);
					tvHora.setText(ped.hora);			
		//-----------------------Valor Unitario----------------------------------------------
					DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
					tvPrecioTPedido.setText(decimalFormat.format(ped.valor));			
				}
		}
		else if(facturas!=null)
		{
				Factura_in fac = facturas.get(position);
				if (fac != null) {
					
					if(fac.idCodigoExterno== 0)
					{
						linea.setBackgroundColor(0xFFFF8787);				
					}
					else
					{
						linea.setBackgroundColor(0x00000000);				
					}
					if(fac.getAnulada().equals("1"))
					{
						linea.setBackgroundColor(0xFFC4D328);
					}
										//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
					tvCodigoExterno.setText(Long.toString(fac.NFactura));
					tvNombreClientep.setText(fac.nombreCliente);
					tvHora.setText(fac.hora);			
		//-----------------------Valor Unitario----------------------------------------------
					DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
					tvPrecioTPedido.setText(decimalFormat.format(fac.valor));			
				}
		}
		else if(remisiones!=null)
		{
			Remision_in  fac = remisiones.get(position);
			if (fac != null) {

				if(fac.idCodigoExterno== 0)
				{
					linea.setBackgroundColor(0xFFFF8787);
				}
				else
				{
					linea.setBackgroundColor(0x00000000);
				}
				//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
				//tvCodigoExterno.setText(Long.toString(fac.NRemision));
				tvCodigoExterno.setText(Long.toString(fac.idCodigoInterno));
				tvNombreClientep.setText(fac.nombreCliente);
				tvHora.setText(fac.hora);
				//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioTPedido.setText(decimalFormat.format(fac.valor));
			}
		}
		else if(pagos!=null)
		{
			Pago pago = pagos.get(position);
			if (pago != null) {

				if(pago.getEnviado()== 0)
				{
					linea.setBackgroundColor(0xFFFF8787);
				}
				else
				{
					linea.setBackgroundColor(0x00000000);
				}
				//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
				tvCodigoExterno.setText(Long.toString(pago.getIdPago()));
				tvNombreClientep.setText(pago.getNombreCliente());
				tvHora.setText(pago.getFecha());
//					tvHora.setText("r " +pago.getEnviado());
				//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioTPedido.setText(decimalFormat.format(pago.getValor()));
			}
		}
		else if(prestamos!=null)
		{
			Prestamo prestamo = prestamos.get(position);
			if (prestamo != null) {

				if(prestamo.getEnviado()== 0)
				{
					linea.setBackgroundColor(0xFFFF8787);
				}
				else
				{
					linea.setBackgroundColor(0x00000000);
				}
				//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
				tvCodigoExterno.setText(prestamo.getIdPrestamo());
				tvNombreClientep.setText(prestamo.getNombreCliente());
				tvHora.setText(prestamo.getFecha());
//					tvHora.setText("r " +pago.getEnviado());
				//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioTPedido.setText(decimalFormat.format(prestamo.getValorPrestamo()));
			}
		}
		else if(pagoPrestamos!=null)
		{
			PagoPrestamo pagoPrestamo = pagoPrestamos.get(position);
			if (pagoPrestamo != null) {

				if(pagoPrestamo.getEnviado()== 0)
				{
					linea.setBackgroundColor(0xFFFF8787);
				}
				else
				{
					linea.setBackgroundColor(0x00000000);
				}
				//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
				tvCodigoExterno.setText(pagoPrestamo.getIdPagoPrestamo());
				tvNombreClientep.setText(pagoPrestamo.getNombreCliente());
				tvHora.setText(pagoPrestamo.getFecha());
//					tvHora.setText("r " +pago.getEnviado());
				//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioTPedido.setText(decimalFormat.format(pagoPrestamo.getValor()));
			}
		}
		else if(libros!=null)
		{
			Libro libro = libros.get(position);
			if (libro != null) {

				if(libro.getEnviado()== 0)
				{
					linea.setBackgroundColor(0xFFD89393);
				}
				else
				{
					linea.setBackgroundColor(0x00000000);
				}
				//tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
				tvCodigoExterno.setText(libro.getIdLibro());
				tvNombreClientep.setText(libro.getNombreCliente());
				tvHora.setText(libro.getFecha());
//					tvHora.setText("r " +pago.getEnviado());
				//-----------------------Valor Unitario----------------------------------------------
				if(libro.getMovDedito()>0)
				{
					DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
					tvPrecioTPedido.setText(decimalFormat.format(libro.getMovDedito()));
					tvTipoDocumento.setText("P");
				}
				else
				{
					DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
					tvPrecioTPedido.setText(decimalFormat.format(libro.getMovCredito()));
					tvTipoDocumento.setText("A");
				}

			}
		}
		else 	
		{
			Traslado_in tra = traslados.get(position);
			if (tra != null) {
				
				if(tra.idCodigoExterno== 0)
				{
					linea.setBackgroundColor(0xFFFF8787);				
				}
				else
				{
					linea.setBackgroundColor(0x00000000);				
				}		
				tvCodigoExterno.setText(Long.toString(tra.idCodigoExterno));
				tvNombreClientep.setText("De "+tra.getBodegaOrigen().getBodega()+" a "+tra.getBodegaDestino().getBodega());
				tvHora.setText(tra.hora);			
	//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioTPedido.setText(decimalFormat.format(tra.totalTraslado));			
			}
	}

		getEstilo(tvCodigoExterno);
		getEstilo(tvNombreClientep);
		getEstilo(tvHora);
		getEstilo(tvPrecioTPedido);
		getEstilo(tvFormaPagoPedido);
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
