package com.principal.factura;

import java.util.ArrayList;

import com.principal.mundo.Bodega;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.persistencia.creaBD;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelecTrasladoBodegaActivity extends Activity implements OnClickListener{
	
	private final static int TRASLADO= 3;
	protected static final int SUB_ACTIVITY_SELECTBODEGA = 300;
	TextView tvOperacionTrasladoBodega,tvRutaTraslado;
	EditText etBodegaOrigen,etBodegaDestino;
	Opciones [] opciones;
	Bodega bodegaOrigen, bodegaDestino;
	creaBD bd;
	ArrayList<Bodega> listaBodegas;
	private int mbodega=0;
	private int operacion;
	Parametros parametrosPos;
	Button btAgregarProductos;
	Usuario usuario;
	RelativeLayout rlTrasladoBodega;
	private boolean selecInforme;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selec_traslado_bodega);
		bd=new creaBD(this); 
		 usuario=new Usuario();
		 
		listaBodegas=bd.getBodegas(this);
	    parametrosPos=bd.getParametros(this,"P");
		etBodegaOrigen=(EditText)findViewById(R.id.etBodegaOrigen);
		etBodegaDestino=(EditText)findViewById(R.id.etBodegaDestino);
		btAgregarProductos=(Button)findViewById(R.id.btAgregarProductos);
		rlTrasladoBodega=(RelativeLayout)findViewById(R.id.rlTrasladoBodega);
		rlTrasladoBodega.setBackgroundColor(0xFFE0E0E0);
		btAgregarProductos.setOnClickListener(this); 
		etBodegaOrigen.setOnClickListener(this); 
		etBodegaOrigen.setKeyListener(null);
		etBodegaDestino.setOnClickListener(this); 
		etBodegaDestino.setKeyListener(null);
		tvRutaTraslado=(TextView)findViewById(R.id.tvRutaTraslado);
		tvOperacionTrasladoBodega=(TextView)findViewById(R.id.tvOperacionTrasladoBodega);
	    Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        operacion=obtenerDatos.getInt("operacion");
        usuario.cedula=obtenerDatos.getString("cedula");
        selecInforme=obtenerDatos.getBoolean("selecInforme");
        tvRutaTraslado.setText(usuario.cedula);
        if(operacion==TRASLADO)
        {
        	tvOperacionTrasladoBodega.setText("TRASLADO");
        }         
        bodegaOrigen=bd.getBodega(parametrosPos.getBodegaTransladosOmision());
        etBodegaOrigen.setText(bodegaOrigen.getBodega());
        if(selecInforme)
        {
        	btAgregarProductos.setText("Ver Informe");
        	bodegaOrigen=new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
        	bodegaDestino=new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
        	listaBodegas.add(bodegaOrigen);
        	etBodegaOrigen.setText(bodegaOrigen.getBodega());
        	etBodegaDestino.setText(bodegaDestino.getBodega());
        }     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selec_traslado_bodega, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.equals(etBodegaOrigen))
		{
			mbodega=1;
			seleccionarBodega();
		}
		else if(v.equals(etBodegaDestino))
		{
			mbodega=2;
			seleccionarBodega();
		}
		else if(v.equals(btAgregarProductos))
		{
			if(validaDatos())
			{
				if(selecInforme)
				{
					Intent i = new Intent();
					Bundle b = new Bundle();
			    	b.putInt("idBodegaOrigen", bodegaOrigen.getIdBodega());
			    	b.putInt("idBodegaDestino", bodegaDestino.getIdBodega());			    	
			    	i.putExtras(b);			  
			        setResult(SUB_ACTIVITY_SELECTBODEGA, i );              
			        finish();
				}
				else
				{
					Intent intent = new Intent(SelecTrasladoBodegaActivity.this, CrearPedidoActivity.class );
					intent.putExtra("cedula", usuario.cedula);
					intent.putExtra("operacion", operacion);
					intent.putExtra("PrecioDefecto", "1");
				    intent.putExtra("idBodegaOrigen",bodegaOrigen.getIdBodega());
					intent.putExtra("idBodegaDestino",bodegaDestino.getIdBodega());				
					startActivity(intent);
					finish();
				}				
			}
		}
		
	}
	private boolean validaDatos()
	{
		if(bodegaDestino!=null &bodegaOrigen!=null)
		{
			if(bodegaDestino.getIdBodega()==0&bodegaOrigen.getIdBodega()==0)
			{
				return true;
			}
			else
			{
				if(bodegaDestino.getIdBodega()!=bodegaOrigen.getIdBodega())
				{
					return true;
				}
				else
				{
					mostrarMensaje("La bodega destino debe ser diferente a la bodega origen", "l");
					return false;
				}
			}
		}
		else
		{
			mostrarMensaje("Debe seleccionar la bodega origen y la bodega destino", "l");
			return false;
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
	private void seleccionarBodega()
	{
		opciones=new Opciones[listaBodegas.size()];
		for (int i = 0; i < listaBodegas.size(); i++) {
			Bodega b=listaBodegas.get(i);
			opciones[i]=new Opciones(b.getIdBodega(),b.getBodega() , getImg(R.drawable.pedidos), b.getBodega());
		}
		ListAdapter listaMotivos = new OpcionesAdapter(SelecTrasladoBodegaActivity.this, opciones);  				        		  		
        AlertDialog.Builder builderMotivo = new AlertDialog.Builder(SelecTrasladoBodegaActivity.this);
        builderMotivo.setTitle("Seleccione la Bodega");
        builderMotivo.setSingleChoiceItems(listaMotivos, -1, new DialogInterface.OnClickListener() {
//
  			    public void onClick(DialogInterface dialogMotivo, int itemMotivo) {	      			    
  			    	if(mbodega==1)
  			    	{
  			    		bodegaOrigen=listaBodegas.get(itemMotivo);
  			    		etBodegaOrigen.setText(bodegaOrigen.getBodega());
  			    	}
  			    	else if(mbodega==2)
  			    	{
  			    		bodegaDestino=listaBodegas.get(itemMotivo);
  			    		etBodegaDestino.setText(bodegaDestino.getBodega());
  			    	}  			    
  			    	mbodega=0;
  			    	dialogMotivo.cancel();
  			    }
  		}
        );
  	AlertDialog alert = builderMotivo.create();
  	alert.show();
	}
	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}

}
