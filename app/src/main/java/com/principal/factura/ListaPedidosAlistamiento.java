package com.principal.factura;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;


import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.GetMesasOcupadas;
import com.principal.mundo.GetNMesas;
import com.principal.mundo.Municipio;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.GetPedidosVendedor;
import com.principal.mundo.sysws.GetRevisores;
import com.principal.mundo.sysws.GetRutas;
import com.principal.mundo.sysws.GetVendedor;
import com.principal.mundo.sysws.Pedido;
import com.principal.mundo.sysws.RutaMunicipios;
import com.principal.mundo.sysws.SetImpObservacionPedido;
import com.principal.mundo.sysws.Vendedor;
import com.principal.persistencia.creaBD;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A login screen that offers login via email/password.
 */
public class ListaPedidosAlistamiento extends Activity implements OnClickListener, View.OnKeyListener {


    protected static final int SUB_ACTIVITY_VER_CONSOLIDADO_ARTICULOS = 900;
    protected static final int SUB_ACTIVITY_VER_PEDIDO_ALISTAMIENTO = 901;

    Vendedor vendedor;
    Usuario usuario;
    private ProgressDialog pdu;
    LetraEstilo letraEstilo;
    Parametros parametrosPos, parametrosSys;
    creaBD bd;
    ArrayList<Vendedor> listaVendedores;
    ArrayList<Pedido> listaPedidos;
    ArrayList<RutaMunicipios> listaRutaMunicipios;
    ArrayList<Municipio> listaMunicipios;
    private String IdRuta;
    private Pedido pedido;
    private String IdVendedor;
    private String IdDpto;
    private String IdMpio;

    private Button btMenuALS, btActualizarAls, btVolverAls;
    private TextView tvRutaLPN, tvCargoT, tvResponsableT;
    private Spinner SpRevisores, SpRuta,SpMunicipio;
    private LinearLayout LLRevisor,LLRuta, LLMunicipio;
    private ListView lvPedidosPendientes;

    private ItemRutaAdapter itemRutaAdapter;
    private ItemMunicipioAdapter itemMunicipioAdapter;
    //private String xmlArticulosConsolidado;
    private boolean cargaRutas;
    private boolean cargaMunicipios;

    private Opciones[] opciones;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_alistamiento);
        usuario=new Usuario();
        letraEstilo=new LetraEstilo();
        bd=new creaBD(this);
        listaMunicipios=new ArrayList<Municipio>();

        parametrosPos=bd.getParametros(this, "P");
        parametrosSys=bd.getParametros(this, "S");
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
        usuario.cedula=obtenerDatos.getString("cedula");

        btMenuALS =(Button)findViewById(R.id.btMenuALS);
        btActualizarAls =(Button)findViewById(R.id.btActualizarAls);
        btVolverAls =(Button)findViewById(R.id.btVolverAls);
        tvRutaLPN =(TextView)findViewById(R.id.tvRutaALS);
        tvCargoT =(TextView)findViewById(R.id.tvCargoT);
        tvResponsableT =(TextView)findViewById(R.id.tvResponsableT);
        SpRevisores =(Spinner)findViewById(R.id.SpRevisores);
        LLRevisor =(LinearLayout)findViewById(R.id.LLRevisor);
        LLRuta=(LinearLayout)findViewById(R.id.LLRuta);
        LLMunicipio=(LinearLayout)findViewById(R.id.LLMunicipio);
        lvPedidosPendientes =(ListView)findViewById(R.id.lvPedidosPendientes);
        SpRuta =(Spinner)findViewById(R.id.SpRuta);
        SpRuta.setPrompt("Selec.. una ruta");
        SpMunicipio=(Spinner)findViewById(R.id.SpMunicipio);
        limpiaCampos();

        btMenuALS.setOnClickListener(this);
        btActualizarAls.setOnClickListener(this);
        btVolverAls.setOnClickListener(this);


        opciones=new Opciones[1];

        opciones[0]=new Opciones("Imprimir Observaciones", getImg(R.drawable.pedidos), "Imprimir Observaciones");

        lvPedidosPendientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (vendedor.getTipo().equals("4")||vendedor.getTipo().equals("3")) {
                    pedido = listaPedidos.get((int) position);
                    ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosAlistamiento.this, opciones);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosAlistamiento.this);
                    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));


                    builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    if (item == 0) {
                                        new EnviarImpPedidoObservacion().execute("");
                                        pdu = ProgressDialog.show(ListaPedidosAlistamiento.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Impresi?n.."), true, false);


                                    }
                                    dialog.cancel();
                                }
                            }
                    );

                    AlertDialog alert = builder.create();
                    alert.show();
                }

                return true;
            }
        });


        lvPedidosPendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedido= listaPedidos.get((int)position);
                Intent intent = new Intent(ListaPedidosAlistamiento.this, VerPedidoAlistamientoActivity.class );
                intent.putExtra("pedido", pedido);
                intent.putExtra("rutaMunicipios", vendedor);
                startActivityForResult(intent,SUB_ACTIVITY_VER_PEDIDO_ALISTAMIENTO);

            }
         });
        SpRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IdRuta.equals(listaRutaMunicipios.get(position).getIdRuta()))
                {
                    IdDpto="0";
                    IdMpio="0";
                    listaMunicipios.clear();
                    listaMunicipios.add(new Municipio("0","0","TODOS"));
                    SpMunicipio.setEnabled(false);
                }

                IdRuta=listaRutaMunicipios.get(position).getIdRuta();

                //Valida que el municipio que haya seleccionado sea diferente a 999 (Selec..) y 0 (Todos)
                if(!IdRuta.equals("999") & !IdRuta.equals("0")) {
                    SpMunicipio.setEnabled(true);
                    listaMunicipios=listaRutaMunicipios.get(position).getListaMunicipios();
                    cargaMunicipios=true;
                    cargarListaMunicipios();
                }

                //VALIDA QUE HAYA SELECCIONADO ALGUNA RUTA
                if(!IdRuta.equals("999")) {
                    new GetPedidosVendedorSys().execute("");
                    pdu = ProgressDialog.show(ListaPedidosAlistamiento.this, "Por Favor Espere", "Obteniendo datos", true, false);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IdDpto =listaMunicipios.get(position).getIdDpto();
                IdMpio  =listaMunicipios.get(position).getIdMpio();
                //VALIDA QUE HAYA SELECCIONADO ALGUNA RUTA
                if(!cargaMunicipios) {
                    new GetPedidosVendedorSys().execute("");
                    pdu = ProgressDialog.show(ListaPedidosAlistamiento.this, "Por Favor Espere", "Obteniendo datos", true, false);
                }
                cargaMunicipios=false;
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpRevisores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IdVendedor=listaVendedores.get(position).getIdVendedor();
                if(!cargaRutas) {
                    //VALIDA QUE HAYA SELECCIONADO ALGUNA RUTA
                    if(!IdRuta.equals("999")) {
                        new GetPedidosVendedorSys().execute("");
                        pdu = ProgressDialog.show(ListaPedidosAlistamiento.this, "Por Favor Espere", "Obteniendo datos", true, false);
                    }
                }
                cargaRutas=false;
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        new GetDatosVendedor().execute("");
        pdu=ProgressDialog.show(this,"Por Favor Espere", "Obteniendo datos", true,false);

    }
    private Drawable getImg(int res )
    {
        Drawable img = getResources().getDrawable( res );
        img.setBounds( 0, 0, 45, 45 );
        return img;
    }
    private void limpiaCampos()
    {
        tvRutaLPN.setText("");
        tvCargoT.setText("");
        tvResponsableT.setText("");
        LLRevisor.setVisibility(View.GONE);


    }
    private class EnviarImpPedidoObservacion extends AsyncTask<String, Void, Object>
    {
        String res="";
        @Override
        protected Integer doInBackground(String... params)
        {
            SetImpObservacionPedido setImpObservacionPedido=new SetImpObservacionPedido(parametrosPos.getIp(),parametrosSys.getWebidText());
            res=setImpObservacionPedido.SetImpObservacionPedido(pedido.getNPedido(), pedido.getNCaja());
            return 1;
        }


        protected void onPostExecute(Object result)
        {
            try
            {
                pdu.dismiss();

                if(res.equals("OK"))
                {
                    mostrarMensaje("PEDIDO IMPRESO CORRECTAMENTE!!!.","l");

                }
                else if(res.equals("NE"))
                {
                    mostrarMensaje("EL PEDIDO SELECCIONADO NO CONTIENE NINGUNA OBSERVACI�N!!.","l");

                }
                else
                {
                    mostrarMensaje(res,"l");
                }
            }
            catch (Exception e) {
                System.out.println("jaja "+e.toString());
            }

        }
    }

    private void cargarVendedor()
    {
        if(vendedor!=null)
        {
            tvRutaLPN.setText(""+vendedor.getDocumento());
            tvCargoT.setText(""+vendedor.getTipoVendedor());
            tvResponsableT.setText(""+vendedor.getVendedor());

            if(!vendedor.getTipo().equals("4"))
            {
                LLRevisor.setVisibility(View.GONE);
            }
            else
            {
                LLRevisor.setVisibility(View.VISIBLE);
            }

        }
        else
        {
            limpiaCampos();
        }
    }
    private void cargarListaPedidos()
    {
        lvPedidosPendientes.setAdapter(new PedidosAlistamientoAdapter(this, 0,listaPedidos));
    }
    private void cargarListaRevisores()
    {
        SpRevisores.setAdapter(new ItemVendedorAdapter(this, 0,listaVendedores));
        SpRevisores.setSelection(0);
    }
    private void cargarListaRutas()
    {
        itemRutaAdapter=new  ItemRutaAdapter(this, 0,listaRutaMunicipios);
        SpRuta.setAdapter(itemRutaAdapter);
        SpRuta.setSelection(0);
    }
    private void cargarListaMunicipios()
    {
        itemMunicipioAdapter=new  ItemMunicipioAdapter(this, 0,listaMunicipios);
        SpMunicipio .setAdapter(itemMunicipioAdapter);
        SpMunicipio.setSelection(0);
    }


    public void onClick(View v) {
        if (v.equals(btActualizarAls))
        {
            new GetDatosVendedor().execute("");
            pdu=ProgressDialog.show(this,"Por Favor Espere", "Obteniendo datos", true,false);
        }
        else if(v.equals(btMenuALS))
        {
            openOptionsMenu();
        }
        else if(v.equals(btVolverAls))
        {
            finish();
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetDatosVendedor extends AsyncTask<String, Void, Object>
    {
        boolean validaVendedor=false;
        @Override
        protected Integer doInBackground(String... params)
        {
            GetVendedor getVendedor=new GetVendedor(parametrosSys.getIp(),parametrosSys.getWebidText());
            vendedor=getVendedor.getVendedor(usuario.cedula);
            if (vendedor!=null) {
                IdVendedor=vendedor.getIdVendedor();
                GetRutas getRutas=new GetRutas(parametrosSys.getIp(),parametrosSys.getWebidText());
                listaRutaMunicipios=getRutas.getRutas();
                IdRuta=listaRutaMunicipios.get(0).getIdRuta();

                if (vendedor.getTipo().equals("4")) {
                    IdVendedor="0";
                    validaVendedor = true;
                    GetRevisores getRevisores = new GetRevisores(parametrosSys.getIp(),parametrosSys.getWebidText());
                    listaVendedores = getRevisores.GetRevisores();
                } else if (vendedor.getTipo().equals("2") || vendedor.getTipo().equals("3")) {
                    validaVendedor = true;
                   // GetPedidosVendedor getPedidosVendedor = new GetPedidosVendedor(parametrosSys.getIp());
                   // listaPedidos = getPedidosVendedor.GetPedidosVendedor(vendedor.getIdVendedor(), vendedor.getTipo(),"0");
                }
            }
            return 1;
        }


        protected void onPostExecute(Object result)
        {
            pdu.dismiss();
            if(validaVendedor & listaRutaMunicipios!=null)
            {
                try {
                    cargarVendedor();
                    cargarListaRutas();
                    IdDpto="0";
                    IdMpio="0";
                    listaMunicipios.clear();
                    listaMunicipios.add(new Municipio("0","0","TODOS"));
                    SpMunicipio.setEnabled(false);
                    cargaMunicipios=true;
                    cargarListaMunicipios();
                    cargaRutas=true;
                    if (vendedor.getTipo().equals("4")) {
                            if(listaVendedores!=null)
                            {
                                cargarListaRevisores();
                            }
                    }
                   // new GetPedidosVendedorSys().execute("");
                   // pdu=ProgressDialog.show(ListaPedidosAlistamiento.this,"Por Favor Espere", "Obteniendo datos", true,false);

                     /**   if (listaPedidos != null) {
                        if(listaPedidos.size()>0)
                        {
                           // xmlArticulosConsolidado=listaPedidos.get(0).getXmlArticulos();
                            listaPedidos.get(0).setArticulosAlsConsolidado();
                            listaPedidos.get(0).setCategoriasAlsConsolidado();

                        }
                        cargarListaPedidos();
                    }
                      **/
                   //else
                    //{
                    //     xmlArticulosConsolidado="";
                    //}
                }
                catch (Exception es)
                    {
                        mostrarMensaje(es.toString(), "l");
                    }
            }
            else
            {
                if (vendedor==null)
                {
                    mostrarMensaje("No fue posible Enviar la impresion", "l");
                    mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

                }
                else
                {
                    mostrarMensaje("El vendedor no se encuentra habilitado para el modulo de alistamiento", "l");

                }
                finish();

            }

        }
    }


    private class GetPedidosVendedorSys extends AsyncTask<String, Void, Object>
    {

        @Override
        protected Integer doInBackground(String... params)
        {

            if (vendedor!=null) {

                   GetPedidosVendedor getPedidosVendedor = new GetPedidosVendedor(parametrosSys.getIp(),parametrosSys.getWebidText());
                    listaPedidos = getPedidosVendedor.GetPedidosVendedor(IdVendedor, vendedor.getTipo(),IdRuta, IdDpto , IdMpio );

            }
            return 1;
        }


        protected void onPostExecute(Object result)
        {
            pdu.dismiss();
            if( listaPedidos!=null)
            {
                try {

                        if(listaPedidos.size()>0)
                        {
                            // xmlArticulosConsolidado=listaPedidos.get(0).getXmlArticulos();
                            listaPedidos.get(0).setArticulosAlsConsolidado();
                            listaPedidos.get(0).setCategoriasAlsConsolidado();

                        }
                        cargarListaPedidos();
                }
                catch (Exception es)
                {
                    mostrarMensaje(es.toString(), "l");
                }
            }
            else
            {
                if (vendedor==null)
                {
                    mostrarMensaje("No fue posible Enviar la impresion", "l");
                    mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

                }
                else
                {
                    mostrarMensaje("La ruta  no se encuentra habilitado para el modulo de alistamiento", "l");

                }
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_pedidos_alistamiento, menu);
        MenuItem bedMenuItem = menu.findItem(R.id.menuEnviarRepresados);
        MenuItem bedMenuItem2 = menu.findItem(R.id.menuResultadoCategoriasAls);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuResultadoAls:
                Intent intent = new Intent(ListaPedidosAlistamiento.this, VerConsolidadoAlistameinto.class);
                intent.putExtra("operacion", "A");
                intent.putExtra("xmlArticulos", listaPedidos.get(0).getXmlArticulosAlsConsolidado());
                intent.putExtra("xmlCategorias", listaPedidos.get(0).getXmlCategoriaAlsConsolidado());
                intent.putExtra("Items", getTotalItems());
                intent.putExtra("NPedidos",  getNPedidos());
                intent.putExtra("TotalPedidos",  getTotalListaPedidos());

                startActivityForResult(intent,SUB_ACTIVITY_VER_CONSOLIDADO_ARTICULOS);
                return true;
            case R.id.menuResultadoCategoriasAls:
                Intent intent2 = new Intent(ListaPedidosAlistamiento.this, VerConsolidadoAlistameinto.class);
                intent2.putExtra("operacion", "C");
                intent2.putExtra("xmlArticulos", listaPedidos.get(0).getXmlArticulosAlsConsolidado());
                intent2.putExtra("xmlCategorias", listaPedidos.get(0).getXmlCategoriaAlsConsolidado());
                intent2.putExtra("Items", getTotalItems());
                intent2.putExtra("NPedidos",  getNPedidos());
                intent2.putExtra("TotalPedidos",  getTotalListaPedidos());

                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public long getNPedidos()
    {
        if(listaPedidos!=null)

        {
            return listaPedidos.size();
        }
        return 0;
    }
    private long getTotalItems()
    {
        try {
            long res = 0;
            for (int i = 0; i < listaPedidos.size(); i++) {
                res += (Long.parseLong(listaPedidos.get(i).getItems()));
            }
            return res;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    private long getTotalListaPedidos()
    {
        try {
            long res = 0;
            for (int i = 0; i < listaPedidos.size(); i++) {
                res += (Long.parseLong(listaPedidos.get(i).getTotalPedido()));
            }
            return res;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUB_ACTIVITY_VER_CONSOLIDADO_ARTICULOS)
        {
            Bundle b = data.getExtras();
            listaPedidos.get(0).setXmlArticulos(b.getString("xmlArticulos"));
            listaPedidos.get(0).setArticulosAlsConsolidadoModificado();
        }
        if(requestCode == SUB_ACTIVITY_VER_PEDIDO_ALISTAMIENTO)
        {

            Bundle b = data.getExtras();
            if(b.getBoolean("envio"))
            {
               // new GetDatosVendedor().execute("");
                //VALIDA QUE HAYA SELECCIONADO ALGUNA RUTA
                if(!IdRuta.equals("999")) {
                    new GetPedidosVendedorSys().execute("");
                    pdu = ProgressDialog.show(this, "Por Favor Espere", "Obteniendo datos", true, false);
                }
            }


        }
    }


}

