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
import com.principal.mundo.sysws.ClienteSucursal;

/**
 * Clase que determina la forma de visualizar un cliente en la lista del rutero
 * @author Javier
 *
 */
public class ClienteSucursalAdapter extends ArrayAdapter<ClienteSucursal> {
    private final static int PRESTAMOS=8;
    /**
     * Atributo clientes hace referencia a la lista de clientes a visualizar
     */
    ArrayList <ClienteSucursal> listaClienteSucursales;
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
     * @param listaClienteSucursales
     */
    public ClienteSucursalAdapter(Context context ,int idCliente , ArrayList<ClienteSucursal> listaClienteSucursales)
    {
        super(context,idCliente);
        this.listaClienteSucursales = listaClienteSucursales;
        this.context=context;
        this.operacion=operacion;

    }
    /**
     * metodo que retorna el numero total de clientes a visualizar
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return listaClienteSucursales.size();
    }
    /**
     * metodo que retorna el cliente que se encuentre en una pocision especifica del arreglo
     */
    public ClienteSucursal getItem(int i) {
        // TODO Auto-generated method stub
        return listaClienteSucursales.get(i);
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
            convertView = inflater.inflate(R.layout.activity_item_selec_sucursal_cliente, null);
        }
        ClienteSucursal clienteSucursal =new ClienteSucursal();
        clienteSucursal = listaClienteSucursales.get(position);
        if (clienteSucursal != null) {
            TextView tvTextDireccionSucursal = (TextView) convertView.findViewById(R.id.tvTextDireccionSucursal);
            TextView tvTextEstablecimientoSucursal = (TextView) convertView.findViewById(R.id.tvTextEstablecimientoSucursal);
            TextView tvTextoMunicipioSucursal = (TextView) convertView.findViewById(R.id.tvTextoMunicipioSucursal);
            TextView tvTextTelefonoSucursal = (TextView) convertView.findViewById(R.id.tvTextTelefonoSucursal);

            Date fechaActual=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            fecha = sdf.format(fechaActual);
            try {

                tvTextDireccionSucursal.setText(getEstiloTexto(clienteSucursal.getDireccion()));
                tvTextEstablecimientoSucursal.setText(getEstiloTexto(clienteSucursal.getEstablecimiento()));
                tvTextoMunicipioSucursal.setText(getEstiloTexto(clienteSucursal.getMunicipio()));
                tvTextTelefonoSucursal.setText(getEstiloTexto(clienteSucursal.getTelefono()));            }
            catch (Exception e)
            {
                tvTextDireccionSucursal.setText(e.toString());
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
