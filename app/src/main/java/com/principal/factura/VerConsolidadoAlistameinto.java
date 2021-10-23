package com.principal.factura;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.principal.mundo.Articulo;
import com.principal.mundo.sysws.Pedido;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



/**
 * A login screen that offers login via email/password.
 */
public class VerConsolidadoAlistameinto extends Activity implements OnClickListener {

    private Button btVolverConAls;
    private TextView tvNPedidosValorConAls,tvItemsValorConAls, tvTituloConAls, tvTValorConAls;
    private ListView lvArticulosConAls;

    private String xmlArticulos;
    private String xmlCategorias;
    private long  NPedidos,Items, TotalPedidos;
    private Pedido pedido;
    private String operacion;
    protected static final int SUB_ACTIVITY_VER_CONSOLIDADO_ARTICULOS = 900;

    ArrayList<Articulo> listaArticulosConsolidado;
    ArrayList<Articulo> listaCategoriasConsolidado;

    private Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_consolidado_alistameinto);



        tvTituloConAls=(TextView)findViewById(R.id.tvTituloConAls);
        tvNPedidosValorConAls=(TextView)findViewById(R.id.tvNPedidosValorConAls);
        tvItemsValorConAls=(TextView)findViewById(R.id.tvItemsValorConAls);
        tvTValorConAls=(TextView)findViewById(R.id.tvTValorConAls);

        btVolverConAls=(Button)findViewById(R.id.btVolverConAls);
        lvArticulosConAls=(ListView)findViewById(R.id.lvArticulosConAls);

        btVolverConAls.setOnClickListener(this);


        lvArticulosConAls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(operacion.equals("A"))
                {
                    articulo = listaArticulosConsolidado.get((int) position);
                }
                else
                {
                    articulo = listaCategoriasConsolidado.get((int) position);
                }
                if(articulo.getEstadoAls().equals("C"))
                {
                    articulo.setEstadoAls("S");
                }
                else
                {
                    articulo.setEstadoAls("C");
                }


                LinearLayout llCategoriaAls= (LinearLayout) view.findViewById(R.id.llCategoriaAls);
                if(articulo.getEstadoAls().equals("S"))
                {
                    llCategoriaAls.setBackgroundColor(Color.parseColor("#7F92FF"));

                }
                else
                {
                    llCategoriaAls.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }


            }
        });

        //clearCampos();
        cargarDatos();

    }
    private void cargarDatos()
    {
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
        operacion=obtenerDatos.getString("operacion");
        xmlArticulos=obtenerDatos.getString("xmlArticulos");
        xmlCategorias=obtenerDatos.getString("xmlCategorias");
        NPedidos=obtenerDatos.getLong("NPedidos");
        Items=obtenerDatos.getLong("Items");
        TotalPedidos=obtenerDatos.getLong("TotalPedidos");
        tvNPedidosValorConAls.setText(""+NPedidos);
        tvItemsValorConAls.setText(""+Items);

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvTValorConAls.setText(decimalFormat.format(TotalPedidos));

        listaArticulosConsolidado=new ArrayList<Articulo>();
        listaCategoriasConsolidado=new ArrayList<Articulo>();

        if(operacion.equals("A"))
        {
            setArticulosAlsConsolidado();
            tvTituloConAls.setText("CONSOLIDADO DE ARTICULOS");
            lvArticulosConAls.setAdapter(new ItemConsolidadoArticulosAdapter(this,R.layout.activity_item_consolidado_alistamiento,listaArticulosConsolidado));
        }
        else
        {
            setCategoriasAlsConsolidado();
            tvTituloConAls.setText("CONSOLIDADO DE CATEGORIAS");
            lvArticulosConAls.setAdapter(new ItemConsolidadoArticulosAdapter(this,R.layout.activity_item_consolidado_alistamiento,listaCategoriasConsolidado));
        }




    }




    private void clearCampos()
    {
        lvArticulosConAls.removeAllViews();
        tvNPedidosValorConAls.setText("0");
        tvItemsValorConAls.setText("0");
    }



    public void onClick(View v) {
        if(v.equals(btVolverConAls))
        {
            if(operacion.equals("A"))
            {
                guardaArticulosSeparados();
            }
            this.finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent  event)
    {
        if(operacion.equals("A"))
        {
            guardaArticulosSeparados();
        }
        return true;
    }

    private void guardaArticulosSeparados()
    {

            Intent i = new Intent();
            xmlArticulos=getXmlArticulosAlsConsolidado();
            i.putExtra("xmlArticulos",xmlArticulos);
            setResult(SUB_ACTIVITY_VER_CONSOLIDADO_ARTICULOS, i);


    }


    public String getXmlArticulosAlsConsolidado( )
    {

        String xmlArticulos="<Consolidado>\n";
        for (int i = 0; i < listaArticulosConsolidado.size(); i++) {
            Articulo itemPedido=listaArticulosConsolidado.get(i);
            xmlArticulos +="<Articulo>\n";
            for (int j = 0; j < itemPedido.getPropertyCountAlsConsolidado(); j++) {
                xmlArticulos +="		<"+itemPedido.getPropertyNameAlsConsolidado(j)+">"+itemPedido.getPropertyAlsConsolidado(j)+"</"+itemPedido.getPropertyNameAlsConsolidado(j)+">\n";
            }
            xmlArticulos +="</Articulo>\n";
        }
        xmlArticulos +="</Consolidado>";

        return xmlArticulos;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }

    public void setArticulosAlsConsolidado( ) {

        if (!xmlArticulos.equals("")) {

            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlArticulos));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("Articulo");
                // iterate the employees
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);

                    Articulo articulo = new Articulo();
                    for (int j = 0; j < articulo.getPropertyCountAlsConsolidado(); j++) {


                        NodeList name = element.getElementsByTagName(articulo.getPropertyNameAlsConsolidado(j));
                        Element line = (Element) name.item(0);
                        articulo.setPropertyAlsConsolidado(j, getCharacterDataFromElement(line));
                    }
                    listaArticulosConsolidado.add(articulo);
                }


            } catch (Exception e) {
                listaArticulosConsolidado = null;
            }
        }
        else
        {
            listaArticulosConsolidado = null;
        }



    }


    public void setCategoriasAlsConsolidado( ) {

        if (!xmlCategorias.equals("")) {

            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlCategorias));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("Articulo");
                // iterate the employees
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);

                    Articulo articulo = new Articulo();
                    for (int j = 0; j < articulo.getPropertyCountAlsConsolidado(); j++) {


                        NodeList name = element.getElementsByTagName(articulo.getPropertyNameAlsConsolidado(j));
                        Element line = (Element) name.item(0);
                        articulo.setPropertyAlsConsolidado(j, getCharacterDataFromElement(line));
                    }
                    listaCategoriasConsolidado.add(articulo);
                }


            } catch (Exception e) {
                listaCategoriasConsolidado = null;
            }
        }
        else
        {
            listaCategoriasConsolidado = null;
        }



    }

}

