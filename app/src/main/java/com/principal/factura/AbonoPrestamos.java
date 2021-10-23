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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Cliente;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;
import com.principal.persistencia.creaBD;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class AbonoPrestamos extends Activity
        implements OnClickListener {

    private final static int PRESTAMOS=8;
    private final static int ABONOPRESTAMOS=9;
    private final static int VERLIBRO=10;

    protected static final int SUB_ACTIVITY_CONSULTA_CLIENTES_PRESTAMO = 700;

    private Usuario usuario;
    private Cliente cliente;
    private int operacionConsulta;

    private Button btGenerarAbonoPrestamo,btVerLibro;
    private EditText etValorAbonoPrestamo,etConceptoPrestamo;
    private TextView tvTituloAbono,tvTClientePrestamoText,tvTNitClientePrestamoText,tvDeudaClientePrestamoText,tvValorNuevoAbono,tvObjetoPrestamo,tvDiasGraciaClientePrestamoText;
    private ListView lvAbonosPreatamo;
    ArrayList<Libro> listaLibros=new ArrayList<Libro>();

    creaBD bd;
    Parametros parametrosPos, parametrosSys;

    String fechaDesde="";
    String fechaHasta="";
    int dia=1;
    /**
     * Atributo mes donde se almacena el mes actual del sistema
     */
    int mes=1;
    /**
     * Atributo ano donde se almacena el ano actual del sistema
     */
    int ano=2012;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abono_prestamos);

        bd=new creaBD(this);
        parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");

        Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);

        fechaDesde=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);
        fechaHasta=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);

        usuario=new Usuario();
        cliente=new Cliente();

        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
        usuario.cedula=obtenerDatos.getString("cedula");
        cliente.setNombre(obtenerDatos.getString("nombreCliente"));
        cliente.setDeudaAntFac(obtenerDatos.getString("deudaAntFac"));
        cliente.setIdCliente(obtenerDatos.getLong("idCliente"));
        cliente.setDiasGracia(obtenerDatos.getString("DiasGracia"));
        cliente.setNit(obtenerDatos.getString("Nit"));

        operacionConsulta=obtenerDatos.getInt("operacion");

        btGenerarAbonoPrestamo=(Button)findViewById(R.id.btGenerarAbonoPrestamo);
        btVerLibro=(Button)findViewById(R.id.btVerLibro);
        etValorAbonoPrestamo=(EditText) findViewById(R.id.etValorAbonoPrestamo);
        etConceptoPrestamo=(EditText) findViewById(R.id.etConceptoPrestamo);
        tvTituloAbono=(TextView) findViewById(R.id.tvTituloAbono);
        tvTClientePrestamoText=(TextView)findViewById(R.id.tvTClientePrestamoText);
        tvTNitClientePrestamoText=(TextView)findViewById(R.id.tvTNitClientePrestamoText);
        tvDeudaClientePrestamoText=(TextView)findViewById(R.id.tvDeudaClientePrestamoText);
        tvValorNuevoAbono=(TextView)findViewById(R.id.tvValorNuevoAbono);
        tvObjetoPrestamo=(TextView)findViewById(R.id.tvObjetoPrestamo);
        tvDiasGraciaClientePrestamoText=(TextView)findViewById(R.id.tvDiasGraciaClientePrestamoText);
        lvAbonosPreatamo=(ListView) findViewById(R.id.lvAbonosPreatamo);

        btGenerarAbonoPrestamo.setOnClickListener(this);
        btVerLibro.setOnClickListener(this);
        cargarDatosCliente();
        cargarMovimientosXCliente(fechaDesde,fechaHasta,cliente);

    }
    private String validNumberDate(int value)
    {
        String res=""+value;
        if(value<10)
        {
            res="0"+value;
        }
        return res;
    }
    public void cargarMovimientosXCliente(String fechaDesde, String fechaHasta,Cliente cliente)
    {
        listaLibros=bd.getUltimosMovimientosXFechaXCliente(this, fechaDesde,fechaHasta, cliente);
        lvAbonosPreatamo.setAdapter(new ListaLibroAdapter(this,R.layout.activity_item_libro,listaLibros));
    }
    private void cargarDatosCliente()
    {
        if(operacionConsulta!=PRESTAMOS)
        {
            tvTituloAbono.setText("ABONO A PRESTAMO");
            btGenerarAbonoPrestamo.setText("Generar Pago");
            tvValorNuevoAbono.setText("Ingrese el valor del abono");
            etConceptoPrestamo.setVisibility(View.GONE);
            tvObjetoPrestamo.setVisibility(View.GONE);


        }
        else
        {
            tvTituloAbono.setText("¡NUEVO PRESTAMO!");
            btGenerarAbonoPrestamo.setText("Generar Prestamo");
            tvValorNuevoAbono.setText("Ingrese el valor del prestamo");
            etConceptoPrestamo.setVisibility(View.VISIBLE);
            tvObjetoPrestamo.setVisibility(View.VISIBLE);

        }
        etValorAbonoPrestamo.setText("");
        etConceptoPrestamo.setText("");
        tvTClientePrestamoText.setText(cliente.getNombre());
        tvTNitClientePrestamoText.setText(cliente.getNit());
        tvDiasGraciaClientePrestamoText.setText(cliente.getDiasGracia());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvDeudaClientePrestamoText.setText(decimalFormat.format(Long.parseLong(cliente.getDeudaAntFac())));

    }


    public void onClick(View v) {
      if(v.equals(btGenerarAbonoPrestamo))
      {
            if(validaDatos())
            {
            long idLibro=bd.obtenerUltimoidLibro(this)+1;
            int NLibro=bd.obtenerUltimoidNLibroCliente(this,""+cliente.getIdCliente())+1;

                Libro libro=new Libro();


             if(operacionConsulta==PRESTAMOS)
             {
                 int idPrestamo=bd.obtenerUltimoidPrestamo(this)+1;
                 Prestamo prestamo=new Prestamo();
                 prestamo.setIdPrestamo(""+idPrestamo);
                 prestamo.setValorPrestamo(Long.parseLong(etValorAbonoPrestamo.getText().toString()));
                 prestamo.setEnviado(0);
                 prestamo.setFecha(parametrosSys.getFecha());
                 prestamo.setFecha2(parametrosSys.getFechaSysSystem());
                 prestamo.setNombreCliente(" ");
                 prestamo.setObjeto(etConceptoPrestamo.getText().toString());
                 prestamo.setHora(parametrosSys.getHora());
                 prestamo.setSaldoAnterior(Long.parseLong(cliente.getDeudaAntFac()));
                 prestamo.setIdCliente(""+cliente.getIdCliente());
                 prestamo.setNuevoSaldo(prestamo.getValorPrestamo()+prestamo.getSaldoAnterior());

                 libro.setIdCliente(prestamo.getIdCliente());
                 libro.setEnviado(0);
                 libro.setConcepto(prestamo.getObjeto());
                 libro.setFecha(prestamo.getFecha());
                 libro.setFecha2(prestamo.getFecha2());
                 libro.setIdLibro(""+idLibro);
                 libro.setNLibro(NLibro);
                 libro.setMovCredito(0);
                 libro.setMovDedito(prestamo.getValorPrestamo());
                 libro.setSaldo(prestamo.getNuevoSaldo());
                 libro.setSaldoAnterior(prestamo.getSaldoAnterior());
                 libro.setHora(prestamo.getHora());
                 if(bd.insertPrestamo(prestamo));
                 {
                     bd.insertLibro(libro);
                     bd.ActualizaDeudaAntFacCliente(prestamo.getIdCliente(),prestamo.getNuevoSaldo(),libro.getFecha2());
                     mostrarMensaje("Prestamo Ingresado Correctamente", "l");
                     //----------------------------------------------------
                     //PARAMETRIZAR RESULTADOS ACTIVIDAD CONSULTAR CLIENTES
                     //----------------------------------------------------
                     Intent i = new Intent();
                     Bundle b = new Bundle();
                     b.putBoolean("save", true);
                     b.putLong("NSaldo",prestamo.getNuevoSaldo());
                     b.putLong("operacion",PRESTAMOS);
                     b.putString("fechaUltimaVenta",libro.getFecha2());
                     b.putLong("idLibro",idLibro);
                     i.putExtras(b);
                     setResult( SUB_ACTIVITY_CONSULTA_CLIENTES_PRESTAMO, i );

                     finish();
                     //----------------------------------------------------
                    /* Intent intent = new Intent(AbonoPrestamos.this, ListaPedidosActivity.class );
                     intent.putExtra("cedula", usuario.cedula);
                     intent.putExtra("operacion", PRESTAMOS);
                     intent.putExtra("idLibro", idLibro);
                     intent.putExtra("print",true);
                     startActivity(intent);

                     */
                 }

             }
             else
             {
                 int idPagoPrestamo=bd.obtenerUltimoidPagoPrestamo(this)+1;
                 PagoPrestamo pagoPrestamo=new PagoPrestamo();
                 pagoPrestamo.setIdPagoPrestamo(""+idPagoPrestamo);
                 pagoPrestamo.setValor(Long.parseLong(etValorAbonoPrestamo.getText().toString()));
                 pagoPrestamo.setEnviado(0);
                 pagoPrestamo.setFecha(parametrosSys.getFecha());
                 pagoPrestamo.setFecha2(parametrosSys.getFechaSysSystem());
                 pagoPrestamo.setNombreCliente(" ");
                 pagoPrestamo.setHora(parametrosSys.getHora());
                 pagoPrestamo.setSaldoAnterior(Long.parseLong(cliente.getDeudaAntFac()));
                 pagoPrestamo.setIdCliente(""+cliente.getIdCliente());
                 pagoPrestamo.setNuevoSaldo(pagoPrestamo.getSaldoAnterior()-pagoPrestamo.getValor());

                 libro.setIdCliente(pagoPrestamo.getIdCliente());
                 libro.setEnviado(0);
                 libro.setConcepto("Abono");
                 libro.setFecha(pagoPrestamo.getFecha());
                 libro.setFecha2(pagoPrestamo.getFecha2());
                 libro.setIdLibro(""+idLibro);
                 libro.setNLibro(NLibro);
                 libro.setMovCredito(pagoPrestamo.getValor());
                 libro.setMovDedito(0);
                 libro.setSaldo(pagoPrestamo.getNuevoSaldo());
                 libro.setSaldoAnterior(pagoPrestamo.getSaldoAnterior());
                 libro.setHora(pagoPrestamo.getHora());
                 if(bd.insertPagoPrestamo(pagoPrestamo));
                 {
                     bd.insertLibro(libro);
                     bd.ActualizaDeudaAntFacCliente(pagoPrestamo.getIdCliente(),pagoPrestamo.getNuevoSaldo(),pagoPrestamo.getFecha2());
                     mostrarMensaje("Prestamo Ingresado Correctamente", "l");
                     //----------------------------------------------------
                     //PARAMETRIZAR RESULTADOS ACTIVIDAD CONSULTAR CLIENTES
                     //----------------------------------------------------
                     Intent i = new Intent();
                     Bundle b = new Bundle();
                     b.putBoolean("save", true);
                     b.putLong("NSaldo",pagoPrestamo.getNuevoSaldo());
                     b.putString("fechaUltimaVenta",pagoPrestamo.getFecha2());
                     b.putLong("operacion",ABONOPRESTAMOS);
                     b.putLong("idLibro",idLibro);
                     i.putExtras(b);
                     setResult( SUB_ACTIVITY_CONSULTA_CLIENTES_PRESTAMO, i );

                     finish();                   //----------------------------------------------------
                     /**
                     Intent intent = new Intent(AbonoPrestamos.this, ListaPedidosActivity.class );
                     intent.putExtra("cedula", usuario.cedula);
                     intent.putExtra("operacion", ABONOPRESTAMOS);
                     intent.putExtra("idLibro", idLibro);
                     intent.putExtra("print",true);
                     startActivity(intent);
                     finish();
                      **/
                 }
             }





            }
      }
      else if (v.equals(btVerLibro))
      {
          //----------------------------------------------------
          Intent intent = new Intent(AbonoPrestamos.this, ListaPedidosActivity.class );
          intent.putExtra("cedula", usuario.cedula);
          intent.putExtra("operacion", VERLIBRO);
          intent.putExtra("idCliente", cliente.getIdCliente());
          intent.putExtra("nombreCliente", cliente.getNombre());
          intent.putExtra("deudaAntFac", cliente.getDeudaAntFac());
          startActivity(intent);
          finish();
      }
    }


    private boolean validaDatos()
    {
        long valor=0;
        if(etValorAbonoPrestamo.getText().length()>0)
        {
         try
         {
             valor = Long.parseLong(etValorAbonoPrestamo.getText().toString());
             if(operacionConsulta==PRESTAMOS)
             {
                 if(etConceptoPrestamo.getText().length()>0)
                 {
                     return true;
                 }
                 else
                 {
                     mostrarMensaje("Debe ingresar el concepto del nuevo prestamo", "l");
                     return false;
                 }

             }
             else {
                 if(valor>Long.parseLong(cliente.getDeudaAntFac()))
                 {
                     mostrarMensaje("El valor del abono debe ser menor o igual a la deuda actual del cliente", "l");
                     return false;
                 }
             }
             return true;
         }
         catch (Exception e)
         {
             mostrarMensaje("El valor debe ser en formato numerico"+e.toString(), "l");
         }

        }
        else
        {
            if(operacionConsulta==PRESTAMOS)
            {
                mostrarMensaje("Debe ingresar el valor del nuevo prestamo", "l");
            }
            else
            {
                mostrarMensaje("Debe ingresar el valor del abono", "l");
            }

        }
        return false;
    }
    public void mostrarMensaje(String mensaje, String tipo)
    {
        if(mensaje=="l")
        {
            Toast.makeText(getBaseContext(),mensaje,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),mensaje,Toast.LENGTH_SHORT).show();
        }


    }

}

