package com.principal.factura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.principal.mundo.Cliente;
import com.principal.mundo.LlamarClientes;
import com.principal.mundo.LlamarFecha;
import com.principal.mundo.LlamarFechaSys;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.GetClientexVendedor;
import com.principal.mundo.sysws.GetDeudaClientexVendedor;
import com.principal.mundo.sysws.Ruta;
import com.principal.persistencia.creaBD;


public class ConsultaClientesActivity extends Activity implements OnClickListener {
	
	
	protected static final int SUB_ACTIVITY_CONSULTA_CLIENTES = 400;
	private final static int CARTERA=7;
	private final static int PRESTAMOS=8;
	private final static int ABONOPRESTAMOS=9;
	Usuario usuario;	
	creaBD bd;
	Parametros parametrosPos,parametrosSys;
	
	private Button btVolverCT, btBuscarCli,btBuscarCliAll;
	private RadioButton rbNombre,rbCedula;
	private EditText etNombreCliente;
	private ListView lvClientes;
	private boolean keyback=false;
	Context context;
	Cliente cliente;
	ArrayList<Cliente> listaClientes=new ArrayList<Cliente>();
	private ProgressDialog pdu;
	private String text;

	private String operacion;
	private int operacionConsulta;
	LetraEstilo letraEstilo;

	private GetDeudaClientexVendedor getDeudaClientexVendedor;

	Opciones[] opciones;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_clientes);
		usuario=new Usuario();
		cliente=new Cliente();
        bd=new creaBD(this);      
        parametrosPos=bd.getParametros(this,"P");
		parametrosSys=bd.getParametros(this,"S");

		letraEstilo=new LetraEstilo();
        
        btVolverCT=(Button)findViewById(R.id.btVolverCT);
        btBuscarCli=(Button)findViewById(R.id.btBuscarCli);
        btBuscarCliAll=(Button)findViewById(R.id.btBuscarCliAll);
        etNombreCliente=(EditText)findViewById(R.id.etNombreCliente);
        lvClientes=(ListView)findViewById(R.id.lvClientes);

		rbNombre=(RadioButton)findViewById(R.id.rbNombre);
		rbCedula=(RadioButton)findViewById(R.id.rbCedula);


		rbNombre.setChecked(true);
		rbCedula.setChecked(false);
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        usuario.cedula=obtenerDatos.getString("cedula");
		operacionConsulta=obtenerDatos.getInt("operacion");
        
        btVolverCT.setOnClickListener(this);
        btBuscarCli.setOnClickListener(this);
        btBuscarCliAll.setOnClickListener(this);
        etNombreCliente.setOnClickListener(this);
		rbNombre.setOnClickListener(this);
		rbCedula.setOnClickListener(this);

		if(parametrosPos.getCarteraOnLine()==1)
		{
			btBuscarCliAll.setVisibility(View.GONE);
		}
		opciones=new Opciones[1];
		opciones[0]=new Opciones("!Nuevo Prestamo!", getImg(R.drawable.agregar), "!Nuevo Prestamo!");
		if(operacionConsulta==PRESTAMOS) {
			lvClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
					ListAdapter listaOpciones = new OpcionesAdapter(ConsultaClientesActivity.this, opciones);
					AlertDialog.Builder builder = new AlertDialog.Builder(ConsultaClientesActivity.this);
					builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
					builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {
									cliente = listaClientes.get((int) position);
									if (item == 0) {
										Intent intent = new Intent(ConsultaClientesActivity.this, AbonoPrestamos.class);
										intent.putExtra("nombreCliente", cliente.nombre);
										intent.putExtra("deudaAntFac", cliente.deudaAntFac);
										intent.putExtra("idCliente", cliente.idCliente);
										intent.putExtra("cedula", usuario.cedula);
										intent.putExtra("operacion", PRESTAMOS);
										startActivityForResult(intent, SUB_ACTIVITY_CONSULTA_CLIENTES);
									}
									dialog.cancel();
								}
							}
					);


					AlertDialog alert = builder.create();
					alert.show();

					return true;
				}
			});
		}
        
        lvClientes.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			lvClientes.setEnabled(false);
			cliente= listaClientes.get((int)position);

			if(operacionConsulta==PRESTAMOS)
			{
				Intent intent = new Intent(ConsultaClientesActivity.this, AbonoPrestamos.class);
				intent.putExtra("nombreCliente", cliente.nombre);
				intent.putExtra("deudaAntFac", cliente.deudaAntFac);
				intent.putExtra("idCliente", cliente.idCliente);
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("operacion",ABONOPRESTAMOS );
				startActivityForResult(intent, SUB_ACTIVITY_CONSULTA_CLIENTES);
			}
			else {
				Intent intent = new Intent(ConsultaClientesActivity.this, CarteraClienteActivity.class);
				intent.putExtra("nombreCliente", cliente.nombre);
				intent.putExtra("deudaActual", cliente.deudaActual);
				intent.putExtra("idCliente", cliente.idCliente);
				intent.putExtra("cedula", usuario.cedula);
				startActivityForResult(intent, SUB_ACTIVITY_CONSULTA_CLIENTES);
			}
			
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta_clientes, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.equals(btBuscarCli))
		{

			if(rbCedula.isChecked())
			{
				operacion="nit";
			}
			else {
				operacion="nombre";
			}
			if(parametrosPos.getCarteraOnLine()==1)
			{
				if(validaTexto()) {
					new getDatos().execute("");
					pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniedo datos"), true, false);
				}
			}
			else
			{
				cargarClientes(true);
			}


		}
		if(v.equals(btBuscarCliAll))
		{
			cargarClientes(false);	
		}
		
		else if(v.equals(btVolverCT))
		{
			if(keyback)
			{
				onBackPressed();
			}
			else
			{
				InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
//			    imm.hideSoftInputFromWindow(etNombreCliente.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etNombreCliente.getWindowToken(), 0);
			    keyback=true;
			}	
		}
		else if(v.equals(etNombreCliente))
		{
		   keyback=false;
		}
		else if(v.equals(rbCedula)) {

			if (rbCedula.isChecked())
			{
				rbNombre.setChecked(false);
			}

		}
		else if(v.equals(rbNombre)) {

			if (rbNombre.isChecked())
			{
				rbCedula.setChecked(false);
			}
		}
		
	}

	public boolean validaTexto()
	{
		text=etNombreCliente.getText().toString();
		if(text.length()>0)
		{
			return true;
		}
		else
		{
			mostrarMensaje("Debe Ingresar el "+operacion+" del Cliente", "l");
			return false;
		}
	}

	public void cargarClientesOnline()
	{
		context = getApplicationContext();
		lvClientes.setAdapter(new VerConsultaClientesAdapter(context,R.layout.activity_item_consulta_clientes,listaClientes,CARTERA));
	}
	
	public void cargarClientes(boolean busca)
	{
	   bd=new creaBD(this);
        
       if(busca)
        {
        	String nombre=etNombreCliente.getText().toString();	    	
	    	if(nombre.equals(""))
	    	{
	    		mostrarMensaje("Debe Ingresar el nombre del Cliente", "l");
	    		
	    	}
	    	else
	    	{
	    		listaClientes=bd.getBuscarClientesPorNombre(this, nombre, usuario.cedula);
	    	}
        }
        else
        {
        	listaClientes=bd.getBuscarClientesPorNombre(this, "", usuario.cedula);
        }
        context = getApplicationContext();
        lvClientes.setAdapter(new VerConsultaClientesAdapter(context,R.layout.activity_item_consulta_clientes,listaClientes,operacionConsulta));
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

	private class getDatos extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params) {


			getDeudaClientexVendedor=new GetDeudaClientexVendedor(parametrosSys.getIp(),parametrosSys.getWebidText());
			listaClientes=getDeudaClientexVendedor.getClientes(operacion,usuario.cedula,text);
			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(listaClientes!=null)
			{
				if(listaClientes.size()>0) {
					cargarClientesOnline();
				}
				else {
					mostrarMensaje("No existen clientes con la información ingresada","l");
				}
			}
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

			}

		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUB_ACTIVITY_CONSULTA_CLIENTES)
		{
			lvClientes.setEnabled(true);
		 try
			{			
			  Bundle b = data.getExtras();	
			  boolean save=b.getBoolean("save");
			  if(save)
			  {
				  finish();
			  }
			  else
			  {
				  if(rbCedula.isChecked())
				  {
					  operacion="nit";
				  }
				  else {
					  operacion="nombre";
				  }
				  if(parametrosPos.getCarteraOnLine()==1)
				  {
					  if(validaTexto()) {
						  new getDatos().execute("");
						  pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniedo datos"), true, false);
					  }
				  }
				  else
				  {

						  cargarClientes(true);


				  }
			  }
			 
			}
			catch(Exception e)
			{
				//mostrarMensaje("No Selecciono ningun producto", "l");
			}
		}
	}

	private Drawable getImg(int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
}
