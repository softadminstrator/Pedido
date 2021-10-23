package com.principal.factura;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.principal.mundo.Articulo;
import com.principal.mundo.Cliente;
import com.principal.mundo.Parametros;

/**
 * Clase que se encarga de mostrar el detale cada uno de los productos de la lista
 * @author Javier
 *
 */
public class VerProductosAdapter extends ArrayAdapter<Cliente> {
	
	/**
	 * Atributo articulos arreglo que guarda los articulos a visualizar en la lista
	 */
	ArrayList <Articulo> articulos;
	/**
	 * Atributo letraEstilo referencia a la clase Letra estilo
	 */
	LetraEstilo letraEstilo;
	private int precioDefecto;
	private long buscaArticuloOnline;
	private Parametros parametrosPos;
	/**
	 * Atributo context referencia a la clase Context de la clase
	 */
	Context context;
	/**
	 * metodo que se encarga de asignar valores a los atributos de la actividad
	 * @param context
	 * @param idPedido
	 * @param articulos
	 */
	 public VerProductosAdapter(Context context, int idPedido  , ArrayList<Articulo> articulos, int precioDefecto, long buscaArticuloOnline,Parametros parametrosPos)
	 {
		 super(context,idPedido);
         this.articulos = articulos;
         this.context=context;
         letraEstilo=new LetraEstilo();
         this.precioDefecto=precioDefecto;
         this.buscaArticuloOnline=buscaArticuloOnline;
 		this.parametrosPos=parametrosPos;
	 }

	 /**
	  * metodo que retorna el numero total de articulos a visualizar
	  */
	public int getCount() {
		// TODO Auto-generated method stub
		return articulos.size();
	}

	/**
	 * metodo que retorna 0 en caso de llamar el valor del item del identificador
	 */
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * metodo que se encarga de asignar en etiquetas los atributos del articulo a mostrar en la lista
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_item_ver_productos, null);
		}
		Articulo art = articulos.get(position);
		if (art != null) {
			TextView tvCantPedir = (TextView) convertView.findViewById(R.id.tvCantPedir);
			TextView tvCodigoVA = (TextView) convertView.findViewById(R.id.tvCodigoVA);
			TextView tvNombreVA = (TextView) convertView.findViewById(R.id.tvNombreVA);
			TextView tvPrecioVA = (TextView) convertView.findViewById(R.id.tvPrecioVA);
			TextView tvStockVA= (TextView) convertView.findViewById(R.id.tvStockVA);
			LinearLayout llVerArticulo= (LinearLayout) convertView.findViewById(R.id.llVerArticulo);


			tvCodigoVA.setText(art.codigo);

			if(art.getStock() < 0)
			{
				tvStockVA.setText("0");
			}
			else
			{
				tvStockVA.setText(""+art.stock);
			}
			tvNombreVA.setText(art.nombre);

			if(parametrosPos.getUsaCantDecimal()==1)
			{
				tvCantPedir.setText(""+art.cantPedir);
			}
			else
			{
				tvCantPedir.setText(""+((long)art.cantPedir));
			}


			if(art.getCantPedir()>0)
			{
				llVerArticulo.setBackgroundColor(Color.parseColor("#7F92FF"));

			}
			else
			{
				llVerArticulo.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}

			
//-----------------------Valor Unitario----------------------------------------------
			DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
			art.setPrecioUnitario(art.getPrecio(precioDefecto));
			tvPrecioVA.setText(decimalFormat.format(art.getPrecio(precioDefecto)));
			
			//getEstilo(tvCodigoVA);
			getEstilo(tvNombreVA);
			getEstilo(tvPrecioVA);		
		}
		return convertView;
	}	
	/**
	 * metodo que se encarga de dar estilo al texto de las etiquetas de la actividad
	 * @param tv
	 */
	public void getEstilo(TextView tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
	
	
}

