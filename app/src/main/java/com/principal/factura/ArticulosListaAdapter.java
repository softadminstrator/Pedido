package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.ArticulosTraslado;
import com.principal.mundo.Cliente;
import com.principal.mundo.Parametros;
import com.principal.mundo.sysws.ItemPedido;
/**
 * Clase que se encarga de dibujar cada uno de los elementos que se mostraran en el objeto ListView 
 * a la hora de mostrar los articulos del pedido
 * @author Javier
 * 
 */
public class ArticulosListaAdapter extends ArrayAdapter<Cliente> {
	
	/**
	 * Atributo articulosPedidosque hace referencia a la clase ArticulosPedido donde se encuentran los detalles
	 * de los articulos seleccionados 
	 */
	ArrayList <ArticulosPedido> articulosPedidos;
	ArrayList <ArticulosFactura> articulosFactura;
	ArrayList <ArticulosTraslado> articulosTraslado;
	ArrayList <ItemPedido> articulosPedidoMesa;
	ArrayList <ArticulosRemision> articulosRemision;

	private Parametros parametrosPos;
	/**
	 * Atributo articulosPedidosque hace referencia a la clase ArticulosPedido donde se encuentran los detalles
	 * de los articulos seleccionados 
	 */
	/**
	 * Atributo context hace referencia  la clase abstracta Context de la actividad
	 */
	Context context;
	/**
	 * Atributo letraEstilo hace referencia a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	

	
	/**
	 * metodo que se encarga de inicializar y asignar los parametros recibidos a los atributos de la clase y super clase
	 * @param context
	 * @param idPedido
	 * @param articulosPedidos
	 */
	 public ArticulosListaAdapter(Context context, int idPedido  , ArrayList<ArticulosPedido> articulosPedidos,
			 ArrayList<ArticulosFactura> articulosFactura,ArrayList<ArticulosTraslado> articulosTraslado,Parametros parametrosPos,ArrayList<ArticulosRemision> articulosRemision)//, ArrayList<ItemPedido> articulosPedidoMesa)
	 {
		 super(context,idPedido);
         this.articulosPedidos = articulosPedidos;
         this.articulosFactura=articulosFactura;
         this.articulosTraslado=articulosTraslado;
         this.articulosPedidoMesa=articulosPedidoMesa;
		 this.articulosRemision=articulosRemision;
         this.context=context;
         letraEstilo=new LetraEstilo();
		 this.parametrosPos=parametrosPos;
        
	 }

	/**
	 * Metodo que se encarga de retornar el numero de articulos del pedido
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		if(articulosPedidos!=null)
		{
			return articulosPedidos.size();
		}
		else if(articulosFactura!=null)
		{
			return articulosFactura.size();
		}
		else if(articulosPedidoMesa!=null)
		{
			return articulosPedidoMesa.size();
		}
		else if(articulosRemision!=null)
		{
			return articulosRemision.size();
		}
		else 
		{
			return articulosTraslado.size();
		}
		
		
	}

	/**
	 * Metodo que retorna 0 en caso de invocar el item del identificador
	 */
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * metodo que retorna una vista para cada uno de los articulos del pedido y la forma en que se visualizara
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_item_articulo_pedido, null);
		}
		TextView tvCantidad = (TextView) convertView.findViewById(R.id.tvCantidad);
		TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombreAP);
		TextView tvPrecioU = (TextView) convertView.findViewById(R.id.tvPrecioU);
		TextView tvPrecioT = (TextView) convertView.findViewById(R.id.tvPrecioT);
		
		if(articulosPedidos!=null)
		{
			ArticulosPedido arPedido = articulosPedidos.get(position);
			if (arPedido != null) {
				DecimalFormat decimalFormat2=new DecimalFormat("###,###,###.###");
				tvCantidad.setText(decimalFormat2.format(arPedido.cantidad));
				//tvCantidad.setText(Double.toString(arPedido.cantidad));
				tvNombre.setText(arPedido.nombre+"--"+ arPedido.getObservacion());
	//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioU.setText(decimalFormat.format(arPedido.valorUnitario));
	//-----------------------Valor Unitario----------------------------------------------	
				tvPrecioT.setText(decimalFormat.format(arPedido.valor));

				if(parametrosPos.getUsaCantDecimal()==1)
				{
					DecimalFormat decimalFormat22=new DecimalFormat("###,###,###.###");
					tvCantidad.setText(decimalFormat22.format(arPedido.cantidad));
					//tvCantidad.setText(""+arPedido.cantidad);
				}
				else
				{
					DecimalFormat decimalFormat22=new DecimalFormat("###,###,###.###");
					tvCantidad.setText(decimalFormat22.format(arPedido.cantidad));

					//tvCantidad.setText(""+(arPedido.cantidad));
				}


			}
		}
		else if(articulosFactura!=null)
		{
			ArticulosFactura arFactura = articulosFactura.get(position);
			if (arFactura != null) {
				DecimalFormat decimalFormat2=new DecimalFormat("###,###,###.###");
				tvCantidad.setText(decimalFormat2.format(arFactura.cantidad));
				tvNombre.setText(arFactura.nombre);
	//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioU.setText(decimalFormat.format(arFactura.valorUnitario));
	//-----------------------Valor Unitario----------------------------------------------	
				tvPrecioT.setText(decimalFormat.format(arFactura.valor));			
			}
		}
		else if(articulosRemision!=null)
		{
			ArticulosRemision arRemision = articulosRemision.get(position);
			if (arRemision != null) {
				DecimalFormat decimalFormat2=new DecimalFormat("###,###,###.###");
				tvCantidad.setText(decimalFormat2.format(arRemision.cantidad));
				//tvCantidad.setText(Long.toString(arRemision.cantidad));
				tvNombre.setText(arRemision.nombre);
				//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioU.setText(decimalFormat.format(arRemision.valorUnitario));
				//-----------------------Valor Unitario----------------------------------------------
				tvPrecioT.setText(decimalFormat.format(arRemision.valor));
			}
		}
		else if(articulosTraslado!=null)
		{
			ArticulosTraslado arTraslado = articulosTraslado.get(position);
			if (arTraslado != null) {
				DecimalFormat decimalFormat2=new DecimalFormat("###,###,###.###");
				tvCantidad.setText(decimalFormat2.format(arTraslado.cantidad));
				//tvCantidad.setText(Long.toString(arTraslado.cantidad));
				tvNombre.setText(arTraslado.nombre);
	//-----------------------Valor Unitario----------------------------------------------
				DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				tvPrecioU.setText(decimalFormat.format(arTraslado.valorUnitario));
	//-----------------------Valor Unitario----------------------------------------------	
				tvPrecioT.setText(decimalFormat.format(arTraslado.valor));			
			}
		}
		
		
		//getEstilo(tvCantidad);
		getEstilo(tvNombre);
		//getEstilo(tvPrecioU);
		getEstilo(tvPrecioT);	
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
