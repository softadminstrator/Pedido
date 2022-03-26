package com.principal.factura;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.sysws.GetObservacionesPedido;
import com.principal.mundo.sysws.GetPedido;
import com.principal.mundo.sysws.GetPedidosVendedor;
import com.principal.mundo.sysws.GetRevisores;
import com.principal.mundo.sysws.GetVendedor;
import com.principal.mundo.sysws.ItemPedido;
import com.principal.mundo.sysws.Observacion;
import com.principal.mundo.sysws.ObservacionPedido;
import com.principal.mundo.sysws.Pedido;
import com.principal.mundo.sysws.PutPedidoAls;
import com.principal.mundo.sysws.Vendedor;
import com.principal.persistencia.creaBD;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * A login screen that offers login via email/password.
 */
public class VerPedidoAlistamientoActivity extends Activity implements  OnClickListener {
    protected static final int SUB_ACTIVITY_VER_PEDIDO_ALISTAMIENTO = 901;
    protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;
    private final static int PEDIDO = 2;

    Parametros parametrosPos, parametrosSys;
    creaBD bd;
    private ProgressDialog pdu;
    private Pedido pedidoin;
    private Pedido pedido;
    private Vendedor vendedor;
    private ItemPedido itemPedido;
    private   ArrayList<ObservacionPedido> listaObservacionesPedidoSistema;

    private Boolean isLoading=false;



    private Button btGuardarPedidoAls, btConfirmarPedidoAls, btVolverPedidoAls, btAgregarArticuloAls, btObservacionesPedidoAls
            ,btAlertaOkObservaciones;
    private TextView tvTClientePedidoAls, tvTNitPedidoAls,tvTMunicipioPedidoAls,tvTPedidoAls,tvTObservacionPedidoAls, tvTCajaPedidoAls, tvTVendedorPedidoAls
            ,tvTTotPedidoAls;
    private ListView lvArticulosPedidoAls;

    private LinearLayout llObservaciones;



    private ItemVerPedidoAlistamientoAdapter itemVerPedidoAlistamientoAdapter;

    Opciones[] opciones;

    Opciones[] opcionesAdministrador;
    LetraEstilo letraEstilo;

    private boolean confirmapedido;
    private String estadoAnterior;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido_alistamiento);

        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
        pedido=(Pedido) obtenerDatos.get("pedido");
        vendedor=(Vendedor) obtenerDatos.get("rutaMunicipios");

        //linea para pruebas
        //rutaMunicipios.setTipo("4");


        bd=new creaBD(this);
        parametrosPos=bd.getParametros(this, "P");
        parametrosSys=bd.getParametros(this, "S");
        letraEstilo=new LetraEstilo();



        opciones=new Opciones[3];
        opciones[0]=new Opciones("Agotado", getImg(R.drawable.anular), "Agotado");
        opciones[1]=new Opciones("Pendiente", getImg(R.drawable.bandera), "Pendiente") ;
        opciones[2]=new Opciones("Agregar Observacion", getImg(R.drawable.consultar), "Agregar Observacion");

        opcionesAdministrador=new Opciones[5];
        opcionesAdministrador[0]=new Opciones("Modificar", getImg(R.drawable.modificar), "Modificar");
        opcionesAdministrador[1]=new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar") ;
        opcionesAdministrador[2]=new Opciones("Agotado", getImg(R.drawable.anular), "Agotado");
        opcionesAdministrador[3]=new Opciones("Pendiente", getImg(R.drawable.bandera), "Pendiente") ;
        opcionesAdministrador[4]=new Opciones("Agregar Observacion", getImg(R.drawable.consultar), "Agregar Observacion");

        btGuardarPedidoAls =(Button)findViewById(R.id.btGuardarPedidoAls);
        btConfirmarPedidoAls =(Button)findViewById(R.id.btConfirmarPedidoAls);
        btVolverPedidoAls =(Button)findViewById(R.id.btVolverPedidoAls);
        btAgregarArticuloAls =(Button)findViewById(R.id.btAgregarArticuloAls);
        btObservacionesPedidoAls =(Button)findViewById(R.id.btObservacionesPedidoAls);




        btGuardarPedidoAls.setOnClickListener(this);
        btConfirmarPedidoAls.setOnClickListener(this);
        btVolverPedidoAls.setOnClickListener(this);
        btAgregarArticuloAls.setOnClickListener(this);
        btObservacionesPedidoAls.setOnClickListener(this);



        tvTClientePedidoAls =(TextView)findViewById(R.id.tvTClientePedidoAls);
        tvTNitPedidoAls =(TextView)findViewById(R.id.tvTNitPedidoAls);
        tvTMunicipioPedidoAls =(TextView)findViewById(R.id.tvTMunicipioPedidoAls);
        tvTPedidoAls =(TextView)findViewById(R.id.tvTPedidoAls);
        tvTCajaPedidoAls =(TextView)findViewById(R.id.tvTCajaPedidoAls);
        tvTVendedorPedidoAls =(TextView)findViewById(R.id.tvTVendedorPedidoAls);
        tvTObservacionPedidoAls=(TextView)findViewById(R.id.tvTObservacionPedidoAls);
        tvTTotPedidoAls=(TextView)findViewById(R.id.tvTTotPedidoAls);



        lvArticulosPedidoAls=(ListView) findViewById(R.id.lvArticulosPedidoAls);



        lvArticulosPedidoAls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (vendedor.getTipo().equals("4")) {
                    ActualizaProducto(view, position, "E");
                }
                else if (vendedor.getTipo().equals("3")) {
                    ActualizaProducto(view, position, "R");
                }
                else
                {
                    ActualizaProducto(view, position, "S");
                }
            }
        });
        btConfirmarPedidoAls.setVisibility(View.VISIBLE);
        btAgregarArticuloAls.setVisibility(View.VISIBLE);
        //Habilita opcion de aregar articulos al pedido
        if (!vendedor.getTipo().equals("2")) {
            btAgregarArticuloAls.setVisibility(View.VISIBLE);
            btObservacionesPedidoAls.setVisibility(View.VISIBLE);
        }
        else
        {
            btAgregarArticuloAls.setVisibility(View.GONE);
            btObservacionesPedidoAls.setVisibility(View.GONE);

        }







        //Habilita opcion de aregar articulos al pedido
        if (vendedor.getTipo().equals("4")||vendedor.getTipo().equals("3")) {
            btAgregarArticuloAls.setVisibility(View.VISIBLE);

        }
        else
        {
            btAgregarArticuloAls.setVisibility(View.GONE);
        }
        //inactiva el boton de confirmar pedido en caso de que el tipo de usuario haya completado los requerimientos

        if((vendedor.getTipo().equals("4") &pedido.getEstado().equals("DEPURADO")) || (vendedor.getTipo().equals("3") &pedido.getEstado().equals("REVISADO")))
        {
            btConfirmarPedidoAls.setVisibility(View.GONE);
            btAgregarArticuloAls.setVisibility(View.GONE);
        }




        lvArticulosPedidoAls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final View viewIn = view;
                final int positionIn = position;
                if ((vendedor.getTipo().equals("4")||vendedor.getTipo().equals("3"))&!((vendedor.getTipo().equals("4") &pedido.getEstado().equals("DEPURADO")) || (vendedor.getTipo().equals("3") &pedido.getEstado().equals("REVISADO")))) {
                    final ListAdapter listaOpciones = new OpcionesAdapter(VerPedidoAlistamientoActivity.this, opcionesAdministrador);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(VerPedidoAlistamientoActivity.this);
                    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
                    builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                modificarArticuloCantidad(pedidoin.getListaArticulos().get((int) positionIn), viewIn);
                            } else if (item == 1) {
                                pedidoin.getListaArticulos().remove((int) positionIn);
                                itemVerPedidoAlistamientoAdapter.notifyDataSetChanged();
                                actualizaTotalPedido();
                            } else if (item == 2) {
                                ActualizaProducto(viewIn, positionIn, "A");
                            } else if (item == 3) {
                                ActualizaProducto(viewIn, positionIn, "P");

                            } else if (item == 4) {
                                modificarArticulo(pedidoin.getListaArticulos().get((int) positionIn), viewIn);

                            }
                            dialog.cancel();
                        }
                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    final ListAdapter listaOpciones = new OpcionesAdapter(VerPedidoAlistamientoActivity.this, opciones);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(VerPedidoAlistamientoActivity.this);
                    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
                    builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                ActualizaProducto(viewIn, positionIn, "A");
                            } else if (item == 1) {
                                ActualizaProducto(viewIn, positionIn, "P");

                            } else if (item == 2) {
                                modificarArticulo(pedidoin.getListaArticulos().get((int) positionIn), viewIn);

                            }

                            dialog.cancel();
                        }
                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }





                    return true;

            }
        });





        cargarDatos();
        new GetPedidoAls().execute("");
        pdu=ProgressDialog.show(this,"Por Favor Espere", "Obteniendo datos", true,false);


    }
    private void borraArticulo()
    {

    }

    private void ActualizaProducto(final View view, final int position, String EstadoSls)
    {
        itemPedido = pedidoin.getListaArticulos().get((int) position);


        if(itemPedido.getEstadoAls().equals(EstadoSls))
        {
            itemPedido.setEstadoAls("C");
        }
        else
        {
            itemPedido.setEstadoAls(EstadoSls);
        }
        LinearLayout LLItemVerPedido = (LinearLayout) view.findViewById(R.id.LLItemVerPedido);

        if(itemPedido.getEstadoAls().equals("S"))
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#C6CFFF"));

        }
        else if(itemPedido.getEstadoAls().equals("A"))
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#E0B3B3"));

        }
        else if(itemPedido.getEstadoAls().equals("P"))
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#FFEDA8"));

        }
        else if(itemPedido.getEstadoAls().equals("E"))
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#72C182"));

        }
        else if(itemPedido.getEstadoAls().equals("R"))
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#82509B"));

        }
        else
        {
            LLItemVerPedido.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    private Drawable getImg(int res )
    {
        Drawable img = getResources().getDrawable( res );
        img.setBounds( 0, 0, 45, 45 );
        return img;
    }
    private void cargarDatos()
    {
        tvTClientePedidoAls.setText(pedido.getNombreCliente());
        tvTNitPedidoAls.setText(pedido.getNitCliente());
        tvTMunicipioPedidoAls.setText(pedido.getMunicipioCliente());
        tvTPedidoAls.setText(pedido.getNPedido());
        tvTCajaPedidoAls.setText(pedido.getNCaja());
        tvTVendedorPedidoAls.setText(pedido.getNombreVendedor());
        tvTObservacionPedidoAls.setText(pedido.getObservaciones());


        try {

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvTTotPedidoAls.setText(decimalFormat.format(pedido.getTotalPedido()));


        }
        catch (Exception e )
        {
            tvTTotPedidoAls.setText("0");


        }




    }


    public void onClick(View v) {
        if(v.equals(btVolverPedidoAls))
        {
            closeActivity();
        }
        else if(v.equals(btObservacionesPedidoAls))
        {
            btObservacionesPedidoAls.setEnabled(false);
            new GetObservacionesSistema().execute("");
            pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos.."), true,false);

        }
        else  if(v.equals(btGuardarPedidoAls))
        {
            btGuardarPedidoAls.setEnabled(false);
            confirmapedido=false;
            confirmaPedido("guardar");
        }
        else  if(v.equals(btConfirmarPedidoAls))
        {
            btConfirmarPedidoAls.setEnabled(false);
            confirmapedido=true;
            confirmaPedido("confirmar");
        }
        else if(v.equals(btAgregarArticuloAls))
        {
            Intent intent = new Intent(VerPedidoAlistamientoActivity.this, VerProductosActivity.class );
            intent.putExtra("operacion",PEDIDO);
            intent.putExtra("precioCliente",pedidoin.getPrecioDefecto());
            startActivityForResult(intent,  SUB_ACTIVITY_REQUEST_CODE );
        }



    }

    private class GetObservacionesSistema extends AsyncTask<String, Void, Object>
    {
        boolean validaVendedor=false;
        @Override
        protected Integer doInBackground(String... params)
        {
            GetObservacionesPedido getObservacionesPedido=new GetObservacionesPedido(parametrosSys.getIp(),parametrosSys.getWebidText());
            listaObservacionesPedidoSistema=getObservacionesPedido.getObservacionesSistema();
            return 1;
        }


        protected void onPostExecute(Object result)
        {
            pdu.dismiss();
            btObservacionesPedidoAls.setEnabled(true);

            if(listaObservacionesPedidoSistema!=null)
            {
                if(listaObservacionesPedidoSistema.size()>0) {
                    cargarCantidadesObservacionPedido();
                    seleccionarObservacionesArticulo(listaObservacionesPedidoSistema);
                }
            }
            else
            {
                mostrarMensaje("No fue posible establecer la conexión con el servidor", "l");
                mostrarMensaje("Verifique que tenga señal o servicio de internet.","l");
            }

        }
    }
    private void cargarCantidadesObservacionPedido()
    {
        for (int i = 0; i < listaObservacionesPedidoSistema.size(); i++) {
            ObservacionPedido ops=listaObservacionesPedidoSistema.get(i);
            for (int j = 0; j < pedidoin.getListaObservacionesPedido().size(); j++) {
                ObservacionPedido op=pedidoin.getListaObservacionesPedido().get(j);
                if(ops.getIdObservacionPedido().equals(op.getIdObservacionPedido()))
                {
                    ops.setCantidad(op.getCantidad());
                }
            }
        }
    }
    private class GetPedidoAls extends AsyncTask<String, Void, Object>
    {
        boolean validaVendedor=false;
        @Override
        protected Integer doInBackground(String... params)
        {
           GetPedido getPedido = new GetPedido(parametrosSys.getIp(),parametrosSys.getWebidText());
            pedidoin = getPedido.GetPedido(pedido.getNCaja(), pedido.getNPedido());

            return 1;
        }


        protected void onPostExecute(Object result)
        {
            pdu.dismiss();
            if(pedidoin!=null)
            {
                try {

                    if (pedidoin.getListaArticulos() != null) {
                        if(pedidoin.getListaArticulos().size()>0)
                        {
                            cargarListaArticulos();
                        }

                    }

                }
                catch (Exception es)
                {
                    mostrarMensaje(es.toString(), "l");
                }
            }
            else
            {

                  mostrarMensaje("No fue posible establecer la conexión con el servidor", "l");
                setRespuesta(false);
                finish();
            }

        }
    }
    public void mostrarMensaje(String mensaje, String tipo)
    {
        if(tipo=="l")
        {
            Toast.makeText(getBaseContext(),mensaje,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),mensaje,Toast.LENGTH_SHORT).show();
        }


    }
    private void cargarListaArticulos()
    {
        itemVerPedidoAlistamientoAdapter=new ItemVerPedidoAlistamientoAdapter(this, 0,pedidoin.getListaArticulos(),parametrosSys);
        lvArticulosPedidoAls.setAdapter(itemVerPedidoAlistamientoAdapter);
        actualizaTotalPedido();
    }
    private void actualizaTotalPedido()
    {
        try {

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvTTotPedidoAls.setText(decimalFormat.format(pedidoin.getTotalPedidoModificado()));
        }
        catch (Exception e )
        {
            tvTTotPedidoAls.setText("0");

        }
    }
    private final void modificarArticulo(final ItemPedido itemPedido, final View view2)
    {
        final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("NOTA");
        builder2.setMessage("Ingrese la observacion para  \n "+itemPedido.getNombreArticulo() );

//		         // Use an EditText view to get user input.
        final AlertDialog test = builder2.create();
        final LinearLayout llVertical=new LinearLayout(this);
        llVertical.setOrientation(LinearLayout.VERTICAL);
        final EditText  etAlertValor = new EditText(this);
        etAlertValor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        llVertical.addView(etAlertValor);
        final LinearLayout llHorizontal=new LinearLayout(this);
        llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        etAlertValor.setText(""+itemPedido.getObservacionAls());
        etAlertValor.selectAll();
        etAlertValor.setTextSize(16);



        etAlertValor.setInputType(InputType.TYPE_CLASS_TEXT);


        final Button btAlertCancelar=new Button(this);
        //btAlertCancelar=(Button)findViewById(R.id.btAgregar);
        btAlertCancelar.setText("Cancelar");
        btAlertCancelar.setWidth(300);
        btAlertCancelar.setHeight(50);
        btAlertCancelar.setTextSize(15);
        final Button btAlertOk=new Button(this);
        //btAlertOk=(Button)findViewById(R.id.btVer);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
        btAlertOk.setText("Ok");
        btAlertOk.setWidth(300);
        btAlertOk.setHeight(50);
        btAlertOk.setTextSize(15);
        llHorizontal.addView(btAlertOk);
        llHorizontal.addView(btAlertCancelar);

        llVertical.addView(llHorizontal);

        test.setView(llVertical);
//		        final AlertDialog test = builder.create();
        btAlertCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                test.cancel();
            }
        });
        btAlertOk.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                if(etAlertValor.getText().toString().length()>0)
                {
                    try {
                        String value = etAlertValor.getText().toString();
                        //if (value >= 0) {
                        test.cancel();
                        itemPedido.setObservacionAls(value);
                        TextView tvObservacionAlistamientoPedidoAls = (TextView) view2.findViewById(R.id.tvObservacionAlistamientoPedidoAls);
                        tvObservacionAlistamientoPedidoAls.setText(""+itemPedido.getObservacionAls());
                        if(itemPedido.getObservacionAls().equals(""))
                        {
                            tvObservacionAlistamientoPedidoAls.setVisibility(View.GONE);
                        }
                        else
                        {
                            tvObservacionAlistamientoPedidoAls.setVisibility(View.VISIBLE);
                        }

                        actualizaTotalPedido();

                    } catch (Exception e)
                    {
                        mostrarMensaje("Formato texto incorrecto", "l");
                        etAlertValor.selectAll();
                        etAlertValor.setFocusable(true);
                    }

                }
                else
                {
                    mostrarMensaje("Debe ingresar la nueva observacion del articulo","l" );
                    etAlertValor.selectAll();
                    etAlertValor.setFocusable(true);

                }
            }
        });
        test.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAlertValor, InputMethodManager.SHOW_IMPLICIT);
        etAlertValor.requestFocus();
        test.show();




    }
    private final void modificarArticuloCantidad(final ItemPedido itemPedido, final View view2)
    {
        final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("NOTA");
        builder2.setMessage("Ingrese la Nueva cantidad para  \n "+itemPedido.getNombreArticulo() );

//		         // Use an EditText view to get user input.
        final AlertDialog test = builder2.create();
        final LinearLayout llVertical=new LinearLayout(this);
        llVertical.setOrientation(LinearLayout.VERTICAL);
        final EditText  etAlertValor = new EditText(this);
        etAlertValor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        llVertical.addView(etAlertValor);
        final LinearLayout llHorizontal=new LinearLayout(this);
        llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

        if(parametrosPos.getUsaCantDecimal()==1)
        {
            etAlertValor.setText(""+itemPedido.getCantidad());
        }
        else {

            etAlertValor.setText(""+((long)itemPedido.getCantidad()));
        }

        etAlertValor.selectAll();
        etAlertValor.setTextSize(16);



        if(parametrosPos.getUsaCantDecimal()==0)
        {
            etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else {
            etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }


        final Button btAlertCancelar=new Button(this);
        //btAlertCancelar=(Button)findViewById(R.id.btAgregar);
        btAlertCancelar.setText("Cancelar");
        btAlertCancelar.setWidth(500);
        btAlertCancelar.setHeight(50);
        btAlertCancelar.setTextSize(15);
        final Button btAlertOk=new Button(this);
        //btAlertOk=(Button)findViewById(R.id.btVer);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
        btAlertOk.setText("Ok");
        btAlertOk.setWidth(500);
        btAlertOk.setHeight(50);
        btAlertOk.setTextSize(15);
        llHorizontal.addView(btAlertOk);
        llHorizontal.addView(btAlertCancelar);

        llVertical.addView(llHorizontal);

        test.setView(llVertical);
//		        final AlertDialog test = builder.create();
        btAlertCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                test.cancel();
            }
        });
        btAlertOk.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                if(etAlertValor.getText().toString().length()>0)
                {
                    try {
                        double value = Long.parseLong(etAlertValor.getText().toString());
                        //if (value >= 0) {
                        test.cancel();
                        itemPedido.setCantidad(value);
                        TextView tvCantidadPedidoAls = (TextView) view2.findViewById(R.id.tvCantidadPedidoAls);
                        if(parametrosPos.getUsaCantDecimal()==1)
                        {
                            tvCantidadPedidoAls.setText(""+itemPedido.getCantidad());
                        }
                        else {

                            tvCantidadPedidoAls.setText(""+((long)itemPedido.getCantidad()));
                        }
                        //ACTUALIZA TOTAL PEDIDO
                        actualizaTotalPedido();

                    } catch (Exception e)
                    {
                        mostrarMensaje("Formato numerico incorrecto", "l");
                        etAlertValor.selectAll();
                        etAlertValor.setFocusable(true);
                    }

                }
                else
                {
                    mostrarMensaje("Debe ingresar la nueva cantidad del articulo","l" );
                    etAlertValor.selectAll();
                    etAlertValor.setFocusable(true);

                }
            }
        });
        test.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAlertValor, InputMethodManager.SHOW_IMPLICIT);
        etAlertValor.requestFocus();
        test.show();
    }


    private void seleccionarObservacionesArticulo(final ArrayList<ObservacionPedido> nuevaLista)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(VerPedidoAlistamientoActivity.this);
        builder.setTitle("Observaciónes");

        final AlertDialog test = builder.create();

        //VARIABLES LINEAR LAYOUT
        LinearLayout llVertical=new LinearLayout(VerPedidoAlistamientoActivity.this);
        llVertical.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsllVertical = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsllVertical.setMargins(10, 5, 0, 0);
        layoutParamsllVertical.height=800;
        layoutParamsllVertical.width=840;
        llVertical.setLayoutParams(layoutParamsllVertical);

        //LINEAR LAYOUT PARA EL BOTON OK
        LinearLayout llHorizontal=new LinearLayout(VerPedidoAlistamientoActivity.this);
        llHorizontal.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsllHorizontal = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsllHorizontal.setMargins(10, 5, 0, 0);
        layoutParamsllHorizontal.height=80;
        layoutParamsllHorizontal.width=800;
        llHorizontal.setLayoutParams(layoutParamsllHorizontal);

        //LIENAR LAYOUT PARA LAS DESCRIPCIIONES DE LAS OBSERVACIONES
        LinearLayout llHorizontal2=new LinearLayout(VerPedidoAlistamientoActivity.this);


        LinearLayout.LayoutParams layoutParamsllHorizontal2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsllHorizontal2.setMargins(5, 5, 0, 0);
        layoutParamsllHorizontal2.height=800;
        layoutParamsllHorizontal2.width=800;
        llHorizontal2.setLayoutParams(layoutParamsllHorizontal2);

        ScrollView scView = new ScrollView(VerPedidoAlistamientoActivity.this);


        LinearLayout.LayoutParams layoutParamsscView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsscView.setMargins(5, 5, 0, 0);
        layoutParamsscView.height=500;
        layoutParamsscView.width=800;

        scView.setLayoutParams(layoutParamsscView);

        llObservaciones=new LinearLayout(VerPedidoAlistamientoActivity.this);
        LinearLayout.LayoutParams layoutParamsllObservaciones = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llObservaciones.setLayoutParams(layoutParamsllObservaciones);
        llObservaciones.setOrientation(LinearLayout.VERTICAL);

        scView.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams parms3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btAlertaOkObservaciones=new Button(VerPedidoAlistamientoActivity.this);
        btAlertaOkObservaciones.setLayoutParams(parms3);
        btAlertaOkObservaciones.setText("OK");
        btAlertaOkObservaciones.setTextColor(Color.WHITE);
        btAlertaOkObservaciones.setTextSize(18);
        btAlertaOkObservaciones.setWidth(800);
        btAlertaOkObservaciones.setHeight(90);
        btAlertaOkObservaciones.setBackgroundResource(R.drawable.red_button);


        llHorizontal.addView(btAlertaOkObservaciones);
        scView.addView(llObservaciones);
        llHorizontal2.addView(scView);
        llVertical.addView(llHorizontal);
        llVertical.addView(llHorizontal2);
        test.setView(llVertical);

        try
        {
            int col=0;
            int numcolBt=0;
            LinearLayout llinea =null;
            llObservaciones.removeAllViews();
            if(nuevaLista!=null)
            {
                for (int i = 0; i < nuevaLista.size(); i++) {
                    final ObservacionPedido observacion=nuevaLista.get(i);
                    View view=null;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.activity_observacion_pedido, null);
                    }
                    final TextView tvNombreObservacion = (TextView) view.findViewById(R.id.tvNombreObservacion);
                    final TextView tvCantidadObservacion = (TextView) view.findViewById(R.id.tvCantidadObservacion);
                    final Button btMas = (Button) view.findViewById(R.id.btMas);
                    final Button btMenos = (Button) view.findViewById(R.id.btMenos);
                    LinearLayout LLItemObservacionPedido= (LinearLayout) view.findViewById(R.id.LLItemObservacionPedido);


                    tvNombreObservacion.setText(observacion.getNombreObservacion());
                    tvCantidadObservacion.setText(""+((long)Double.parseDouble(observacion.getCantidad())));

                    btMas.setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {

                            long cant=(long)Double.parseDouble(observacion.getCantidad());
                            cant++;
                            observacion.setCantidad(""+cant);
                            tvCantidadObservacion.setText(observacion.getCantidad());

                        }
                    });
                    // Boton menos
                    btMenos.setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {

                            long cant=(long)Double.parseDouble(observacion.getCantidad());
                            if(cant>0) {
                                cant--;
                            }
                            observacion.setCantidad(""+cant);
                            tvCantidadObservacion.setText(observacion.getCantidad());
                        }
                    });
                    llObservaciones.addView(view);

                }
            }
        }
        catch (Exception e) {
            System.out.println("Err22 "+e.toString());
        }

        btAlertaOkObservaciones.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
               pedidoin.setNuevaListaObservaciones(nuevaLista);
                test.cancel();
            }
        });
        test.show();
    }

    public String getXmlPedido()
    {
        String xml="";
        xml="<PedidoMesa>\n";
        xml +="<Datos>\n";
        for (int j = 0; j < pedidoin.getPropertyCountAls(); j++) {
            xml +="		<"+pedidoin.getPropertyNameAls(j)+">"+pedidoin.getPropertyAls(j)+"</"+pedidoin.getPropertyNameAls(j)+">\n";
        }
        xml +="</Datos>\n";

        xml +="</PedidoMesa>";
        return xml;
    }

    private class EnviarPedido extends AsyncTask<String, Void, Object>
    {

        String res="";
        @Override
        protected Integer doInBackground(String... params)
        {
            PutPedidoAls putPedidoAls=new PutPedidoAls(parametrosSys.getIp(),parametrosSys.getWebidText());
            res=putPedidoAls.setPedidoAls(getXmlPedido(),pedidoin.getXmlArticulosAls());
            return 1;
        }


        protected void onPostExecute(Object result)
        {
            try
            {
                pdu.dismiss();

                //Activa botones
                btGuardarPedidoAls.setEnabled(true);
                btConfirmarPedidoAls.setEnabled(true);



                if(res.equals(pedidoin.getNPedido()))
                {
                    mostrarMensaje("PEDIDO ENVIADO CORRECTAMENTE!!!.","l");
                    setRespuesta(true);
                    finish();

                }
                else
                {

                    if(confirmapedido)
                    {
                        pedidoin.setEstado(estadoAnterior);
                    }


					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
				 mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
                }
            }
            catch (Exception e) {
                System.out.println("jaja "+e.toString());
            }
            isLoading=false;

        }
    }

    private void setRespuesta(boolean value)
    {
        Intent i = new Intent();
        i.putExtra("envio",value);
        setResult(SUB_ACTIVITY_VER_PEDIDO_ALISTAMIENTO, i);
    }



    public boolean onKeyDown(int keyCode, KeyEvent  event)
    {
        closeActivity();
        return true;
    }
    public void closeActivity()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage("¿Desea guardar cambios? ");

        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (!isLoading) {
                    isLoading=true;
                    new EnviarPedido().execute("");
                    pdu = ProgressDialog.show(VerPedidoAlistamientoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido.."), true, false);
                }
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                setRespuesta(false);
                VerPedidoAlistamientoActivity.this.finish();

            }
        });
        dialog.show();
    }

    public void confirmaPedido(final String opcion)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String texteConfirmar="";
        if (opcion.equals("confirmar"))
        {
            texteConfirmar = "\n ¡Una vez confirmado no lo podra volver a modificarlo!";
        }


        dialog.setMessage("¿Esta seguro que desea "+opcion+" el pedido? "+texteConfirmar);

        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (opcion.equals("confirmar"))
                {
                    cambiaEstadoPedido();
                }
                if (!isLoading) {
                    isLoading = true;
                    new EnviarPedido().execute("");
                    pdu = ProgressDialog.show(VerPedidoAlistamientoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido.."), true, false);
                }
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if(opcion.equals("guardar"))
                {
                    btGuardarPedidoAls.setEnabled(true);

                }
                else  if(opcion.equals("confirmar"))
                {
                    btConfirmarPedidoAls.setEnabled(true);

                }



            }
        });
        dialog.show();
    }
    private String getSiguienteEstado(String Estado)
    {
        String nuevoEstado="";

        if(Estado.equals("CRUDO"))
        {
            nuevoEstado="SEPARADO";
        }
        else  if(Estado.equals("SEPARADO"))
        {
            nuevoEstado="REVISADO";
        }
        else  if(Estado.equals("REVISADO"))
        {
            nuevoEstado="DEPURADO";
        }
        else  if(Estado.equals("DEPURADO"))
        {
            nuevoEstado="DEPURADO";
        }

        return nuevoEstado;
    }
    private void cambiaEstadoPedido()
    {
        estadoAnterior=pedidoin.getEstado();
        pedidoin.setEstado(getSiguienteEstado(pedidoin.getEstado()));


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUB_ACTIVITY_REQUEST_CODE)
        {
            String etapa="0";
            try
            {
                ArrayList<Articulo> listaSelec=data.getParcelableArrayListExtra("listaSelec");
                agregaListaArticulos(listaSelec);

            }
            catch(Exception e)
            {
                mostrarMensaje("Etapa "+etapa+" No Selecciono ningun producto", "l");
            }
        }



        else
        {
            finish();
        }
    }
    private void agregaListaArticulos( ArrayList <Articulo> listaSelec)
    {
        try {
            for (int j = 0; j < listaSelec.size(); j++) {
                Articulo art = listaSelec.get(j);
                if (!ActualizaArticulo(art)) {
                    ItemPedido item = new ItemPedido();
                    item.setIdArticulo(art.idArticulo);
                    item.setCantidad(art.cantPedir);
                    item.setNombreArticulo(art.getNombre());
                    item.setValorUnitario(art.getPrecioUnitario());
                    item.setTotal((long) art.cantPedir * art.getPrecioUnitario());
                    item.setTipoIva(art.getIva());
                    item.setEstadoAls("C");
                    pedidoin.getListaArticulos().add(item);
                }

            }

            mostrarMensaje(listaSelec.size() + " Articulos Ingresados", "l");
            cargarListaArticulos();
        }
            catch(Exception e)
            {

            }
    }
    private boolean ActualizaArticulo(Articulo art)
    {
        //Verifica si existe articulo en la lista
        try {
        for (int i = 0; i < pedidoin.getListaArticulos().size(); i++) {
            ItemPedido ip=pedidoin.getListaArticulos().get(i);
            if(ip.getIdArticulo()==art.getIdArticulo())
            {
                ip.setCantidad(ip.getCantidad()+art.cantPedir);
                return true;

            }
        }
        }
        catch(Exception e)
        {

        }
        return false;
    }
}

