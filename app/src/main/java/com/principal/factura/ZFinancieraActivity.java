package com.principal.factura;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.CierreTurno;
import com.principal.mundo.GenerarCierre;
import com.principal.mundo.LlamarZFinanciera;
import com.principal.mundo.Parametros;
import com.principal.mundo.ZFinanciera;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintFactura;
import com.principal.print.PrintZebra;

public class ZFinancieraActivity extends Activity implements OnClickListener {
	
	private Button btGeneraCierre, btConsultarZFinanciera;
	private TextView tvDatosZFinanciera, tvInfoUltZFinanciera;
	private creaBD bd;
	private Parametros parametros;
	private String textoFecha;
	private ProgressDialog pdu;
	private ZFinanciera zFinanciera;
	private EditText etAlertValor;
	private Button btAlertOk, btAlertCancelar, btVolverZ;
	private PrintFactura printFactura;
	private long NTransacciones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zfinanciera);
		zFinanciera=new ZFinanciera();
		bd=new creaBD(this);
		parametros=bd.getParametros(this, "P");
		
		btGeneraCierre=(Button)findViewById(R.id.btGeneraCierre);
		btConsultarZFinanciera=(Button)findViewById(R.id.btConsultarZFinanciera);

		btVolverZ=(Button)findViewById(R.id.btVolverZ);
		tvDatosZFinanciera=(TextView)findViewById(R.id.tvDatosZFinanciera);
		tvInfoUltZFinanciera=(TextView)findViewById(R.id.tvInfoUltZFinanciera);
		btVolverZ.setOnClickListener(this);
		btGeneraCierre.setOnClickListener(this);
		btConsultarZFinanciera.setOnClickListener(this);

		tvDatosZFinanciera.setText("");
		if(parametros.getGeneraCierre()==0)
		{
			btGeneraCierre.setVisibility(View.GONE);
		}
		cargarDatos();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zfinanciera, menu);
		return true;
	}
	private void cargarDatos()
	{
		tvInfoUltZFinanciera.setText("Caja "+parametros.getCaja()+" Ultimo Cierre "+bd.obtenerUltimaNCierreTurno(parametros.getCaja()));
	}

	public void onClick(View v) {
		if(v.equals(btGeneraCierre))
		{
			generarCierre();
		}
		else if(v.equals(btConsultarZFinanciera))
		{
			getZFinanciera();
		}
		else if(v.equals(btVolverZ))
		{
			onBackPressed();
		}

		
	}

	@SuppressLint("SimpleDateFormat")
	private void generarCierre()
	{
		Date fecha=new Date();
	    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
	    textoFecha=sdf.format(fecha);

	    //Valida que existan facturas para poder realizar el cierre
		bd.openDB();
		final CierreTurno ultimoCierre=bd.obtenerUltimoCierreTurno(""+parametros.getCaja());
		NTransacciones=bd.obtenerNumeroFacturasCierreTurno(""+parametros.getCaja(),ultimoCierre);
		bd.close();
		if (NTransacciones==0)
		{
			muestraMensajePantalla("No es posible realizar el cierre, debio a que no se encuentran transacciones retistradas");

			return;
		}


		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		String mensaje="Esta seguro que desea generar el cierre de la caja "+parametros.getCaja();

		//valida si existe algun cierre realizado con la misma fecha, en caso de que exista pregunta si desea realizar cierre nuevamnete
		// siempre y cuando hayan facturas ralizadas despues del ultimo cierre
    	if(bd.getValidaFechaCierreTurno(parametros.getFechaSys2System()))
    	{
    		mensaje="Acutualmente existe registrado un cierre con fecha "+textoFecha+". "+mensaje;
    	}
      	dialog.setMessage(mensaje);
      	dialog.setCancelable(false);
      	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
      	
      	  public void onClick(DialogInterface dialog, int which) {
      	  	  //Guarda cierre he imprime informe
			  //Crea objeto cierre
			  CierreTurno cierreTurno=new CierreTurno();
			  cierreTurno.setFecha(parametros.getFechaSys());
			  cierreTurno.setFecha2(parametros.getFechaSysSystem2());
			  cierreTurno.setHora(parametros.getHora());
			  cierreTurno.setNCaja(""+parametros.getCaja());
			  cierreTurno.setIdCierreTurno(0);

			  cierreTurno.setNCierre(""+ultimoCierre.getSiguienteNCierre());
			  bd.openDB();
			  cierreTurno.setTransacciones(""+bd.obtenerNumeroFacturasCierreTurno(""+parametros.getCaja(),ultimoCierre));
			  bd.obtenerDatosNuevoCierreTurno(cierreTurno,ultimoCierre);
			  bd.close();
			  cierreTurno.setVendedor(parametros.getRuta());

			  GuardaCierre(cierreTurno);
			  muestraMensajePantalla("Cierre turno registrado correctamente");
			  Intent intent = new Intent(ZFinancieraActivity.this, VerCierreTurno.class);
			  intent.putExtra("NCaja", cierreTurno.getNCaja());
			  intent.putExtra("NCierre", cierreTurno.getNCierre());
			  startActivity(intent);
			  finish();
			  //---------------------------------------------------------
      	  }
      	});
      	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
      	 
      	
      	   public void onClick(DialogInterface dialog, int which) {
      	      dialog.cancel();
      	   }
      	});
      	dialog.show();
	}
	private void GuardaCierre(CierreTurno cierreTurno)
	{

		//Ingresa turno en base de datos
		bd.openDB();
		bd.insertCierreTurno(cierreTurno);
		bd.close();



	}

	

	  private void getZFinanciera()
	  {
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Ciere Turno Caja "+parametros.getCaja());
	        builder.setMessage("Ingrese el No. de Cierre que desea consultar: ");
//		         // Use an EditText view to get user input.
	        final AlertDialog test = builder.create();

		  final LayoutInflater inflater = getLayoutInflater();
		  final View dialogView = inflater.inflate(R.layout.activity_input_text_value, null);
		  test.setView(dialogView);

		  	TextView tvInput=(TextView)dialogView.findViewById(R.id.TVTextoInput);
		  	tvInput.setVisibility(View.GONE);
		   	etAlertValor = (EditText) dialogView.findViewById(R.id.etValorInput);
	        etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);

	        btAlertCancelar = (Button) dialogView.findViewById(R.id.btCancelarInput);
		  	btAlertOk = (Button) dialogView.findViewById(R.id.btAceptarInput);

	        

//		        final AlertDialog test = builder.create();
	        btAlertCancelar.setOnClickListener(new OnClickListener() {									
				public void onClick(View v) {										
					test.cancel();
				}
			});
	        btAlertOk.setOnClickListener(new OnClickListener() {					
				@SuppressLint("NewApi")
				public void onClick(View v) {
					if(!etAlertValor.getText().toString().isEmpty())
		            	{
		            		//Valida si existe el numero de cierre
							CierreTurno cierreTurno=bd.obtenerCierreTurno(""+parametros.getCaja(),etAlertValor.getText().toString());
		            		if (cierreTurno.getNCierre().equals("0"))
							{
								muestraMensajePantalla("El numero de cierre ingresado no existe!!");
							}
		            		else
							{
								try {
									Intent intent = new Intent(ZFinancieraActivity.this, VerCierreTurno.class);
									intent.putExtra("NCaja", ""+parametros.getCaja());
									intent.putExtra("NCierre", etAlertValor.getText().toString());
									startActivity(intent);
									test.cancel();
									finish();
								}
								catch (Exception e)
								{
									mostrarMensaje(e.toString(),"l");
								}

							}

		            	}
		            	else
		            	{
		            		mostrarMensaje("Debe ingresar el dinero recibido","l" );
		            		etAlertValor.selectAll();
		            		etAlertValor.setFocusable(true);
		            	
		            	}
				}
			});
	        test.show();
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
		public void muestraMensajePantalla(String mensaje)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Información");
			alert.setMessage(mensaje);
			alert.setPositiveButton("OK",null);
			alert.show();
		}

}
