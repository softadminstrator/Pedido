package com.principal.factura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.principal.mundo.sysws.Libro;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by acer on 22/04/2019.
 */

public class ListaLibroAdapter extends ArrayAdapter<Libro> {

    /**
     * Atributo pedidos hace referencia a el arreglo de Pedido_in que se mostraran en la lista
     */

    ArrayList <Libro> libros;



    /**
     * Atributo context  referencia de la clase Context
     */
    Context context;
    /**
     * Atributo letraEstilo referencia de la clase LetraEstilo
     */
    LetraEstilo letraEstilo;


    public ListaLibroAdapter(Context context, int pedidosNum,ArrayList<Libro> libros )
    {
        super(context, pedidosNum);
        this.libros=libros;
        this.context=context;
        letraEstilo=new LetraEstilo();


    }

    /**
     * metodo que retorna el numero total de pedidos a enviar
     */
    public int getCount() {
        // TODO Auto-generated method stub
       if(libros!=null)
        {
            return libros.size();
        }
        else
        {
            return 0;
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
            convertView = inflater.inflate(R.layout.activity_item_libro, null);
        }
        TextView tvLibroNComprobante = (TextView) convertView.findViewById(R.id.tvLibroNComprobante);
        TextView tvLibroFechaHora = (TextView) convertView.findViewById(R.id.tvLibroFechaHora);
        TextView tvLibroDebito = (TextView) convertView.findViewById(R.id.tvLibroDebito);
        TextView tvLibroCredito = (TextView) convertView.findViewById(R.id.tvLibroCredito);
        LinearLayout LLLibro =(LinearLayout) convertView.findViewById(R.id.LLLibro);
        TextView tvLibroSaldo = (TextView) convertView.findViewById(R.id.tvLibroSaldo);

        if(libros!=null)
        {
            Libro libro = libros.get(position);
            if (libro != null) {

                if(libro.getEnviado()== 0)
                {
                    LLLibro.setBackgroundColor(0xFFD89393);
                }
                else
                {
                    LLLibro.setBackgroundColor(0x00000000);
                }
                //tvCodigoExterno.setText(Long.toString(fac.idCodigoExterno));
                tvLibroNComprobante.setText(libro.getIdLibro());
                tvLibroFechaHora.setText(libro.getFecha()+" "+libro.getHora());

//					tvHora.setText("r " +pago.getEnviado());
                //-----------------------Valor Unitario----------------------------------------------
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                tvLibroDebito.setText(decimalFormat.format(libro.getMovDedito()));
                tvLibroCredito.setText(decimalFormat.format(libro.getMovCredito()));
                tvLibroSaldo.setText(decimalFormat.format(libro.getSaldo()));


            }
        }
        getEstilo(tvLibroNComprobante);
        getEstilo(tvLibroFechaHora);
        getEstilo(tvLibroDebito);
        getEstilo(tvLibroCredito);
        getEstilo(tvLibroSaldo);
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
