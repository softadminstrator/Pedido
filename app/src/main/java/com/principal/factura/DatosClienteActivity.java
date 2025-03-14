package com.principal.factura;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Bodega;
import com.principal.mundo.Cliente;
import com.principal.mundo.Departamento;
import com.principal.mundo.Municipio;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.GetCliente;
import com.principal.mundo.sysws.GetDepartamento;
import com.principal.mundo.sysws.GetMunicipio;
import com.principal.mundo.sysws.PutCliente;
import com.principal.mundo.sysws.PutVisitasCliente;
import com.principal.mundo.sysws.VisitaCliente;
import com.principal.persistencia.creaBD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase en la que se puede visualizar la imformacion completa del cliente
 * como el nombre, representante, direccion, relefono, barrio. 
 * @author Javier
 *
 */
public class DatosClienteActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
	/**
	 * Atributo cliente hace referencia a la clase Cliente
	 */
	Cliente cliente;
	Usuario usuario;
	Opciones[] opcionesDep,opcionesMun ;
	int IdDptoSelec, IdMpioSelec;

	ArrayList<Departamento> listaDepartamentos;
	ArrayList<Municipio> listaMunicipio;
	/**
	 * Atributo textViews arreglo que contendra las etiquetas de la actividad
	 */

	/**
	 * Atributo letraEstilo referencia a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	/**
	 * Atributo btvolver referente al boton volver para regresar al rutero de clientes
	 */
	Button btVolver, btGuardar;

	EditText etPrimerApellido, etSegundoApellido,etPrimerNombre,etSegundoNombre,etRazonSocial,etDireccion,etTelefono,etEMail, etRepresentante,etTipoCanal,etNit, etTipoPrecio;

	TextView tvPrimerApellido, tvSegundoApellido,tvPrimerNombre,tvSegundoNombre,tvRazonSocial,tvDireccion,tvTelefono,tvEMail, tvRepresentante, tvNit, tvNombreData;

	Spinner spTipoPersona,spNoTipoDocumento,spDepartamento, spMunicipio;

    creaBD bd;
	Parametros parametrosPos, parametrosSys;
	private ArrayAdapter<String> dataAdapter, dataAdapterTipoDoc;

	private ProgressDialog pdu;
	
	/**
	 * metodo que se asigna valores a los atributos de la actividad
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);

		etPrimerApellido=(EditText)findViewById(R.id.etPrimerApellido);
		etSegundoApellido=(EditText)findViewById(R.id.etSegundoApellido);
		etPrimerNombre=(EditText)findViewById(R.id.etPrimerNombre);
		etSegundoNombre=(EditText)findViewById(R.id.etSegundoNombre);
		etRazonSocial=(EditText)findViewById(R.id.etRazonSocial);
		etDireccion=(EditText)findViewById(R.id.etDireccion);
		etTelefono=(EditText)findViewById(R.id.etTelefono);
		etEMail=(EditText)findViewById(R.id.etEMail);
		etRepresentante=(EditText)findViewById(R.id.etRepresentante);
		etTipoCanal=(EditText)findViewById(R.id.etTipoCanal);
		etNit=(EditText)findViewById(R.id.etNit);
		etTipoPrecio=(EditText)findViewById(R.id.etTipoPrecio);


		tvPrimerApellido=(TextView) findViewById(R.id.tvPrimerApellido);
		tvSegundoApellido=(TextView) findViewById(R.id.tvSegundoApellido);
		tvPrimerNombre=(TextView) findViewById(R.id.tvPrimerNombre);
		tvSegundoNombre=(TextView) findViewById(R.id.tvSegundoNombre);
		tvRazonSocial=(TextView) findViewById(R.id.tvRazonSocial);
		tvDireccion=(TextView) findViewById(R.id.tvDireccion);
		tvTelefono=(TextView) findViewById(R.id.tvTelefono);
		tvEMail=(TextView) findViewById(R.id.tvEMail);
		tvRepresentante=(TextView) findViewById(R.id.tvRepresentante);
		tvNit=(TextView) findViewById(R.id.tvNit);
		tvNombreData=(TextView) findViewById(R.id.tvNombreData);

		spTipoPersona=(Spinner) findViewById(R.id.spTipoPersona);
		spNoTipoDocumento=(Spinner) findViewById(R.id.spNoTipoDocumento);
		spDepartamento=(Spinner) findViewById(R.id.spDepartamento);
		spMunicipio=(Spinner) findViewById(R.id.spMunicipio);

		btVolver=(Button)findViewById(R.id.btVolver);
		btVolver.setOnClickListener(this);
		btGuardar=(Button)findViewById(R.id.btGuardar);
		btGuardar.setOnClickListener(this);

		spTipoPersona.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		List<String> listaprecios = new ArrayList<String>();
		listaprecios.add("NATURAL");
		listaprecios.add("JURIDICA");

		// Spinner Drop down elements
		List<String> listaTipoDocumento = new ArrayList<String>();
		listaTipoDocumento.add("NIT");
		listaTipoDocumento.add("Cedula de Ciudadania");






		// Creating adapter for spinner
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaprecios);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spTipoPersona.setAdapter(dataAdapter);

		spDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(IdDptoSelec!=position)
				{
					IdDptoSelec=position;
					new getMunicipiosSys().execute("");
					pdu = ProgressDialog.show(DatosClienteActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo datos.."), true, false);

				}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(IdMpioSelec!=position)
				{
					IdMpioSelec=position;

				}
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}
		});




		dataAdapterTipoDoc= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTipoDocumento);
		dataAdapterTipoDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spNoTipoDocumento.setAdapter(dataAdapterTipoDoc);


        cliente=new Cliente();
		usuario=new Usuario();
		listaDepartamentos=new ArrayList<Departamento>();
		listaMunicipio=new ArrayList<Municipio>();

        letraEstilo=new LetraEstilo();
        bd=new creaBD(this);

		parametrosPos=bd.getParametros(this,"P");
		parametrosSys=bd.getParametros(this,"S");

        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
		//Obtiene todos los datos del cliente de la base de datos
		cliente.setIdCliente(Long.parseLong(obtenerDatos.getString("idCliente")));
		usuario.cedula=obtenerDatos.getString("cedula");

			new getDatosSys().execute("");
			pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo datos.."), true, false);




	}
    /**
     * metodo encargado de asignar valores a las etiquetas de la actividad
     */
    public void cargarDatos()
    {

		validaTipoPersona(cliente.TipoPersona);
		etPrimerApellido.setText(vt(cliente.getPrimerApellido()));
		etSegundoApellido.setText(vt(cliente.getSegundoApellido()));
		etPrimerNombre.setText(vt(cliente.getPrimerNombre()));
		etSegundoNombre.setText(vt(cliente.getSegundoNombre()));
		etRazonSocial.setText(vt(cliente.getRazonSocial()));
		etDireccion.setText(vt(cliente.getDireccion()));
		etTelefono.setText(vt(cliente.getTelefono()));
		etEMail.setText(vt(cliente.getMail()));
		etNit.setText(cliente.getNit());
		tvNombreData.setText(vt(cliente.getNombre()));
		etRepresentante.setText(vt(cliente.getRepresentante()));
		etTipoCanal.setText(vt(cliente.getTipoCanal()));
		etTipoPrecio.setText(vt(cliente.getPrecioDefecto()));
		spNoTipoDocumento.setSelection(0);
		if(cliente.getNoTipoDocumento().equals("13"))
		{
			spNoTipoDocumento.setSelection(1);
		}


		cargaDepartamentos();
		cargaMunicipios();

		//Asigna valor departamento
			int pos=0;
			for (int i=0;i<opcionesDep.length; i++)
			{
				if(opcionesDep[i].get_identificador()==Integer.parseInt(cliente.getIdDpto()))
				{
					pos=i;
				}
			}
			spDepartamento.setSelection(pos);
		IdDptoSelec=pos;
		//Asigna valor Municipio
		int posmun=0;
		for (int i=0;i<opcionesMun.length; i++)
		{
			if(opcionesMun[i].get_identificador()==Integer.parseInt(cliente.getIdMpio()))
			{
				posmun=i;
			}
		}
		spMunicipio.setSelection(posmun);
		IdMpioSelec=posmun;



    }
	public void cargaDepartamentos()
	{

		opcionesDep = new Opciones[listaDepartamentos.size()+1];
		opcionesDep[0] = new Opciones(0, "Selec..", getImg(R.drawable.pedidos),"Selec..");
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			Departamento dp = listaDepartamentos.get(i);
			opcionesDep[i+1] = new Opciones(Integer.parseInt(dp.getIdDpto()), dp.getDepartamento(), getImg(R.drawable.pedidos),dp.getDepartamento());
		}
		OpcionesAdapter op=new OpcionesAdapter(DatosClienteActivity.this, opcionesDep);
		op.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDepartamento.setAdapter(op);

	}
	private Drawable getImg(int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
	public void cargaMunicipios()
	{
		try {
			opcionesMun = new Opciones[listaMunicipio.size() + 1];
			opcionesMun[0] = new Opciones(0, "Selec..", getImg(R.drawable.pedidos), "Selec..");
			for (int i = 0; i < listaMunicipio.size(); i++) {
				Municipio dp = listaMunicipio.get(i);
				opcionesMun[i + 1] = new Opciones(Integer.parseInt(dp.getIdMpio()), dp.getMunicipio(), getImg(R.drawable.pedidos), dp.getMunicipio());
			}
			OpcionesAdapter op = new OpcionesAdapter(DatosClienteActivity.this, opcionesMun);
			op.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spMunicipio.setAdapter(op);
		}
		catch (Exception e )
		{
			e.toString();
		}
	}
	private String vt(String text)
	{
		if (text.equals("anyType{}"))
		{
			return "";
		}
		return text;
	}
	private void validaTipoPersona(String tipoPersona)
	{
		boolean isNatural=tipoPersona.toUpperCase().equals("NATURAL");
		if(tipoPersona.toUpperCase().equals("NATURAL"))
		{
			etPrimerApellido.setVisibility(View.VISIBLE);
			etSegundoApellido.setVisibility(View.VISIBLE);
			etPrimerNombre.setVisibility(View.VISIBLE);
			etSegundoNombre.setVisibility(View.VISIBLE);
			etRazonSocial.setVisibility(View.GONE);

			tvPrimerApellido.setVisibility(View.VISIBLE);
			tvSegundoApellido.setVisibility(View.VISIBLE);
			tvPrimerNombre.setVisibility(View.VISIBLE);
			tvSegundoNombre.setVisibility(View.VISIBLE);
			tvRazonSocial.setVisibility(View.GONE);
		}
		else {
			etPrimerApellido.setVisibility(View.GONE);
			etSegundoApellido.setVisibility(View.GONE);
			etPrimerNombre.setVisibility(View.GONE);
			etSegundoNombre.setVisibility(View.GONE);
			etRazonSocial.setVisibility(View.VISIBLE);

			tvPrimerApellido.setVisibility(View.GONE);
			tvSegundoApellido.setVisibility(View.GONE);
			tvPrimerNombre.setVisibility(View.GONE);
			tvSegundoNombre.setVisibility(View.GONE);
			tvRazonSocial.setVisibility(View.VISIBLE);

		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_datos_cliente, menu);
        return true;
    }
    
    /**
     * metodo que se encarga de dar estilo en letra de etiquetas
     * @param tv
     */
    public void getEstilo(TextView [] tv)
	{
		for (int i = 0; i < tv.length; i++) {
			if(i<5)
			{
				tv[i].setText(letraEstilo.getEstiloSencillo(tv[i].getText().toString()));
			}
			else
			{
				tv[i].setText(letraEstilo.getEstiloNegrilla(tv[i].getText().toString()));
			}
		}
		
	}
    /**
     * metodo que se encarga de dar estilo de letra al texto del boton
     * @param tv
     */
    public void getEstilo(Button tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}

	public void onClick(View v) {
		if(v.equals(btVolver))
		{
			finish();
		}
		if(v.equals(btGuardar))
		{
			if(validaDatos())
			{
				new getPutClienteSys().execute("");
				pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando datos.."), true, false);

			}
			else {
				mostrarMensaje("Debe ingresar todos los datos de cliente.","l");
			}

		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		validaTipoPersona(spTipoPersona.getSelectedItem().toString());
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	private class getPutClienteSys extends AsyncTask<String, Void, Object>
	{
		String  res ="";
		@Override
		protected Integer doInBackground(String... params)
		{

			PutCliente putCliente=new PutCliente(parametrosSys.getIp(),parametrosSys.getWebidText());
			res =putCliente.setDatosClienteCliente(cliente.getXml());
			return 1;
		}


		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(res.equals("OK"))
			{
				mostrarMensaje("Cliente Actualizado Correctamente.","l");
				//Actualizar datos cliente
				finish();
			}
			else if(res.equals("NE"))
			{
				mostrarMensaje("El numero de documento ya existe, pruebe con otro!!","l");
			}
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

			}

		}
	}

	private class getDatosSys extends AsyncTask<String, Void, Object>
	{
		String  res ="";
		@Override
		protected Integer doInBackground(String... params)
		{
			if(cliente.getIdCliente()>0) {
				GetCliente getCliente = new GetCliente(parametrosSys.getIp(), parametrosSys.getWebidText());
				cliente = getCliente.GetDatosCliente(cliente.getIdCliente() + "");
			}


			GetDepartamento getDepartamento=new GetDepartamento(parametrosSys.getIp(),parametrosSys.getWebidText());
			listaDepartamentos=getDepartamento.GetDepartamentos();


			if(cliente!=null)
			{
				GetMunicipio getMunicipio=new GetMunicipio(parametrosSys.getIp(),parametrosSys.getWebidText());
				listaMunicipio=getMunicipio.GetMunicipios(cliente.getIdDpto());
			}
			res="OK";
			return 1;
		}


		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(res.equals("OK"))
			{
				//mostrarMensaje("Cliente Actualizado Correctamente.","l");
				//Actualizar datos cliente
				if (cliente!=null & listaDepartamentos!=null)
				{
					cargarDatos();
				}

			}

			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

			}

		}
	}

	private class getMunicipiosSys extends AsyncTask<String, Void, Object>
	{
		String  res ="";
		@Override
		protected Integer doInBackground(String... params)
		{


				GetMunicipio getMunicipio=new GetMunicipio(parametrosSys.getIp(),parametrosSys.getWebidText());
				listaMunicipio=getMunicipio.GetMunicipios(""+opcionesDep[IdDptoSelec].get_identificador());

			res="OK";
			return 1;
		}


		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(res.equals("OK"))
			{
				cargaMunicipios();

			}
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

			}

		}
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

	private Boolean validaDatos()
	{
	boolean valNombre =false;

		if(spTipoPersona.getSelectedItem().toString().equals("NATURAL"))
		{
			if(valText(etPrimerApellido.getText().toString()+etSegundoApellido.getText().toString()+etPrimerNombre.getText().toString()+(etSegundoNombre.getText().toString())))
			{
				valNombre=true;
			}
		}
		else {
			if(valText(etRazonSocial.getText().toString()))
			{
				valNombre=true;
			}
		}

		if(valNombre
				&& valText(etNit.getText().toString())
				&& valText(etDireccion.getText().toString())
				&& valText(etTelefono.getText().toString())
				&& valText(etRepresentante.getText().toString())
				&& valText(etEMail.getText().toString())
				&& valText(etTipoCanal.getText().toString())
				&& IdDptoSelec>0
				&& IdMpioSelec>0
				) {
			cliente.setNit(etNit.getText().toString());
			cliente.setPrimerApellido(etPrimerApellido.getText().toString());
			cliente.setSegundoApellido(etSegundoApellido.getText().toString());
			cliente.setPrimerNombre(etPrimerNombre.getText().toString());
			cliente.setSegundoNombre(etSegundoNombre.getText().toString());
			cliente.setRazonSocial(etRazonSocial.getText().toString());
			cliente.setDireccion(etDireccion.getText().toString());
			cliente.setTelefono(etTelefono.getText().toString());
			cliente.setRepresentante(etRepresentante.getText().toString());
			cliente.setMail(etEMail.getText().toString());
			cliente.setTipoPersona(spTipoPersona.getSelectedItem().toString());
			cliente.setTipoCanal(etTipoCanal.getText().toString());
			cliente.setPrecioDefecto(etTipoPrecio.getText().toString());
			cliente.setFechaAct("");
			cliente.setIdVendedor(usuario.cedula);

			if (spTipoPersona.getSelectedItem().toString().equals("NATURAL")) {
				cliente.setNombre(cliente.getPrimerApellido() + " " + cliente.getSegundoApellido() + " " + cliente.getPrimerNombre() + " " + cliente.getSegundoNombre());
			} else {
				cliente.setNombre(cliente.getRazonSocial());
			}
			cliente.setNoTipoDocumento("31");
			if (!spNoTipoDocumento.getSelectedItem().toString().equals("NIT"))
			{
				cliente.setNoTipoDocumento("13");
			}
			cliente.setIdDpto(opcionesDep[IdDptoSelec].get_identificador()+"");
			cliente.setIdMpio(opcionesMun[IdMpioSelec].get_identificador()+"");

			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean valText (String text)
	{
		if (text.length()>0)
		{
			return true;
		}
		return false;
	}


}
