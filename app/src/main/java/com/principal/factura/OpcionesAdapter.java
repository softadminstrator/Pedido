package com.principal.factura;
import android.annotation.SuppressLint;
//import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.principal.mundo.Opciones;
/**
 * Clase en que se describira cada uno de los elementos de las opciones de dialogo
 * @author Javier
 *
 */
public class OpcionesAdapter extends ArrayAdapter<Opciones>
{
	//-------------------------------------------------------------------
	//------------------------CONSTANTES---------------------------------
	//-------------------------------------------------------------------
	private static final int RESOURCE = R.layout.activity_modificar_pedido2;
	/**
	 * Atributo inflater referente a la clase LayoutInflater
	 */
	private LayoutInflater inflater;
	/**
	 * Atributo letraEstilo referente a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	
    static class ViewHolder {
        TextView nameTxVw;
        RadioButton checkedTextView;
    }
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param context
     * @param options
     */
	public OpcionesAdapter(Context context, Opciones [] options)
	{
		super(context, RESOURCE, options);
		inflater = LayoutInflater.from(context);
		 letraEstilo=new LetraEstilo();
		
	}
	/**
	 * metodo que se encarga de describir cada uno de los elementos de las opciones de dialogo
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if ( convertView == null ) {
			// inflate a new view and setup the view holder for future use
			holder = new ViewHolder();
			
				convertView = inflater.inflate( RESOURCE, null );
				holder.nameTxVw =(TextView) convertView.findViewById(R.id.tvItemOpciones);
						
			convertView.setTag( holder );
		}  else {
			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();
		}

		Opciones op = getItem( position );
		if ( op!=null ) {
			
				holder.nameTxVw.setText( op.getName() );
				holder.nameTxVw.setCompoundDrawables(null , null, op.getImg(), null );
		}
	

		return convertView;
	}
	/**
	 * metodo que se encarga de dar estilo a las entiquetas en las opciones de dialogo
	 * @param tv
	 */
	public void getEstilo(TextView tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
}

