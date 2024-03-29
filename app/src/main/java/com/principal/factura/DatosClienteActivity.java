package com.principal.factura;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Cliente;
import com.principal.mundo.Parametros;
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

	EditText etPrimerApellido, etSegundoApellido,etPrimerNombre,etSegundoNombre,etRazonSocial,etDireccion,etTelefono,etEMail, etRepresentante,etTipoCanal;

	TextView tvPrimerApellido, tvSegundoApellido,tvPrimerNombre,tvSegundoNombre,tvRazonSocial,tvDireccion,tvTelefono,tvEMail, tvRepresentante, tvNit, tvNombreData;

	Spinner spTipoPersona;

    creaBD bd;
	Parametros parametrosPos, parametrosSys;
	private ArrayAdapter<String> dataAdapter;

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

		btVolver=(Button)findViewById(R.id.btVolver);
		btVolver.setOnClickListener(this);
		btGuardar=(Button)findViewById(R.id.btGuardar);
		btGuardar.setOnClickListener(this);

		spTipoPersona.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		List<String> listaprecios = new ArrayList<String>();
		listaprecios.add("NATURAL");
		listaprecios.add("JURIDICA");


		// Creating adapter for spinner
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaprecios);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spTipoPersona.setAdapter(dataAdapter);




        cliente=new Cliente();

        letraEstilo=new LetraEstilo();
        bd=new creaBD(this);

		parametrosPos=bd.getParametros(this,"P");
		parametrosSys=bd.getParametros(this,"S");

        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
		//Obtiene todos los datos del cliente de la base de datos
        cliente=bd.getCliente(this,""+obtenerDatos.getString("idCliente"));
		 cargarDatos();


    }
    /**
     * metodo encargado de asignar valores a las etiquetas de la actividad
     */
    public void cargarDatos()
    {

		validaTipoPersona(cliente.TipoPersona);
		etPrimerApellido.setText(cliente.getPrimerApellido());
		etSegundoApellido.setText(cliente.getSegundoApellido());
		etPrimerNombre.setText(cliente.getPrimerNombre());
		etSegundoNombre.setText(cliente.getSegundoNombre());
		etRazonSocial.setText(cliente.getRazonSocial());
		etDireccion.setText(cliente.getDireccion());
		etTelefono.setText(cliente.getTelefono());
		etEMail.setText(cliente.getMail());
		tvNit.setText(cliente.getNit());
		tvNombreData.setText(cliente.getNombre());
		etRepresentante.setText(cliente.getRepresentante());
		etTipoCanal.setText(cliente.getTipoCanal());

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
				pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Visitas"), true, false);

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
			res =putCliente.setDatosClienteCliente(getXmlDatosCliente());
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
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

			}

		}
	}
	public String getXmlDatosCliente()
	{
		String fecha;
		Date fechaActual=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		fecha = sdf.format(fechaActual);
		String xml="";
		xml="<DatosCliente>\n";
		xml +="<Datos>\n";
		xml +="<IdCliente>\n"+cliente.getIdCliente()+"</IdCliente>\n";
		xml +="<TipoPersona>\n"+cliente.getTipoPersona()+"</TipoPersona>\n";
		xml +="<PrimerApellido>\n"+cliente.getPrimerApellido()+"</PrimerApellido>\n";
		xml +="<SegundoApellido>\n"+cliente.getSegundoApellido()+"</SegundoApellido>\n";
		xml +="<PrimerNombre>\n"+cliente.getPrimerNombre()+"</PrimerNombre>\n";
		xml +="<SegundoNombre>\n"+cliente.getSegundoNombre()+"</SegundoNombre>\n";
		xml +="<RazonSocial>\n"+cliente.getRazonSocial()+"</RazonSocial>\n";
		xml +="<NombreCliente>\n"+cliente.getNombre()+"</NombreCliente>\n";
		xml +="<Mail>\n"+cliente.getMail()+"</Mail>\n";
		xml +="<Direccion>\n"+cliente.getDireccion()+"</Direccion>\n";
		xml +="<Telefono>\n"+cliente.getTelefono()+"</Telefono>\n";
		xml +="<Representante>\n"+cliente.getRepresentante()+"</Representante>\n";
		xml +="<TipoCanal>\n"+cliente.getTipoCanal()+"</TipoCanal>\n";

		xml +="</Datos>\n";
		xml +="</DatosCliente>";

		return xml;

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


		if(valText(etPrimerApellido.getText().toString())
		&& valText(etSegundoApellido.getText().toString())
				&& valText(etPrimerNombre.getText().toString())
				&& valText(etSegundoNombre.getText().toString())
				&& valText(etRazonSocial.getText().toString())
				&& valText(etDireccion.getText().toString())
				&& valText(etTelefono.getText().toString())
				&& valText(etRepresentante.getText().toString())
				&& valText(etEMail.getText().toString())
				&& valText(etTipoCanal.getText().toString())
				) {

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

			if (spTipoPersona.getSelectedItem().toString().equals("NATURAL")) {
				cliente.setNombre(cliente.getPrimerApellido() + " " + cliente.getSegundoApellido() + " " + cliente.getPrimerNombre() + " " + cliente.getSegundoNombre());
			} else {
				cliente.setNombre(cliente.getRazonSocial());
			}

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
