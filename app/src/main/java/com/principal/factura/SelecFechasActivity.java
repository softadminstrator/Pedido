package com.principal.factura;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Usuario;

public class SelecFechasActivity extends Activity implements OnClickListener{
	
	static final int DATE_DIALOG_ID   = 1;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	protected static final int SUB_ACTIVITY_SELECTFECHA = 200;	
	private int selecFecha=0;
	private String fechaDesde="";
	private String fechaHasta="";
	private Date dateDesde;
	private Date dateHasta;
	private String fechaBotonDesde="";
	private String fechaBotonHasta="";
	int dia=1;
	int mes=1;	
	int ano=2012;
	private int operacion=0;	
	private Button btFechaDesde,btFechaHasta,btVerInforme;
	private RelativeLayout rlSelecFechas;
	private TextView tvOperacionFechas;
	private Usuario usuario;
	

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selec_fechas);
		dateDesde=new Date();
		dateHasta=new Date();
		usuario=new Usuario();
		Calendar c = Calendar.getInstance();
		dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);	
        fechaDesde=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);
        fechaHasta=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);

	    
		btFechaDesde=(Button)findViewById(R.id.btFechaDesde);
		btFechaHasta=(Button)findViewById(R.id.btFechaHasta);
		btVerInforme=(Button)findViewById(R.id.btVerInforme);
		rlSelecFechas=(RelativeLayout)findViewById(R.id.rlSelecFechas);
		tvOperacionFechas=(TextView)findViewById(R.id.tvOperacionFechas); 
		
		btFechaDesde.setOnClickListener(this);
		btFechaHasta.setOnClickListener(this);
		btVerInforme.setOnClickListener(this);
		
		tvOperacionFechas.setText("PEDIDO");
		
	
		
	  Bundle obtenerDatos=new Bundle();
	  obtenerDatos = this.getIntent().getExtras(); 
	  operacion=obtenerDatos.getInt("operacion");
	  usuario.cedula=obtenerDatos.getString("cedula");
	  fechaBotonDesde=obtenerDatos.getString("fechaBotonDesde");
	  fechaBotonHasta=obtenerDatos.getString("fechaBotonHasta");
	  try
	  {
		fechaDesde=obtenerDatos.getString("fechaDesde");
  		fechaHasta=obtenerDatos.getString("fechaHasta");
  		btFechaDesde.setText(fechaBotonDesde);
		btFechaHasta.setText(fechaBotonHasta);
 
	  }catch (Exception e) {
		// TODO: handle exception
	}
	  
	  
	  
	  if(operacion==TRANSLADO)
  		{       	
		  rlSelecFechas.setBackgroundColor(0xFFE0E0E0);
		  tvOperacionFechas.setText("TRASLADO");
  		}
	  else if(operacion==FACTURA)
	  	{
		  rlSelecFechas.setBackgroundColor(0xFFE0E0E0);
		  tvOperacionFechas.setText("FACTURA");
	  	}
	        
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selec_fechas, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.equals(btFechaDesde))
		{
			selecFecha=1;
			showDialog(DATE_DIALOG_ID);
		}
		else if(v.equals(btFechaHasta))
		{
			selecFecha=2;
			showDialog(DATE_DIALOG_ID);
		}
		else
		{		
			if(dateDesde.before(dateHasta)||dateDesde.equals(dateHasta))
			{
				Intent i = new Intent();
				Bundle b = new Bundle();
		    	b.putString("fechaDesde", fechaDesde);
		    	b.putString("fechaHasta", fechaHasta);
		    	b.putString("fechaBotonDesde", fechaBotonDesde);
		    	b.putString("fechaBotonHasta", fechaBotonHasta);
		    	i.putExtras(b);			  
		        setResult( SUB_ACTIVITY_SELECTFECHA, i );              
		        finish();
			}
			else
			{
				mostrarMensaje("La fecha Desde debe ser menor o igual a la fecha Hasta", "l");
			}
			
		}
		
	}
	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
		case DATE_DIALOG_ID : return new DatePickerDialog(this, dateSetListener, ano, (mes-1), dia);
		}
		return null;
		
	}	
	
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		   {
			   try
			   {
				     Calendar c = Calendar.getInstance();
			         c.set(year, monthOfYear, dayOfMonth);
			         SimpleDateFormat sdfQuery=new SimpleDateFormat("yyyy-MM-dd"); 
			 		 SimpleDateFormat sdfButton=new SimpleDateFormat("dd/MM/yyyy");
			 		 
			         if(selecFecha==1)
			         {
			        	 fechaBotonDesde=sdfButton.format(c.getTime());
			        	 fechaDesde=year+"-"+validNumberDate((monthOfYear+1))+"-"+validNumberDate(dayOfMonth);		
			        	 dateDesde=sdfQuery.parse(fechaDesde);
				         btFechaDesde.setText(fechaBotonDesde);
			         }
			         else
			         {
			        	 fechaBotonHasta=sdfButton.format(c.getTime());
			        	 fechaHasta=year+"-"+validNumberDate((monthOfYear+1))+"-"+validNumberDate(dayOfMonth);	
			        	 dateHasta=sdfQuery.parse(fechaHasta);
				         btFechaHasta.setText(fechaBotonHasta);
			         }	   
			   }
			   catch (Exception e) {
				// TODO: handle exception
			   }
			}
		};
		
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
		public String getFechaBoton(String fechaSqlite) {
			 final Calendar c = Calendar.getInstance();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 try
			 {
				 c.setTime(dateFormat.parse(fechaSqlite));
				 return validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);
		    }
			 catch (Exception e) {
				return "";
			}		
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
}
