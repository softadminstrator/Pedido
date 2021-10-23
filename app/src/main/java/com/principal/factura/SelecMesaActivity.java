package com.principal.factura;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.principal.mundo.GetMesasOcupadas;
import com.principal.mundo.GetNMesas;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.persistencia.creaBD;

public class SelecMesaActivity extends Activity implements OnClickListener {

	private long NMesa=0;	
	
	Usuario usuario;
	private ProgressDialog pdu;
	LetraEstilo letraEstilo;
	private ArrayList<Long> listaMesasOcupadas;
	private long NMesasPos;
	private LinearLayout llMesas;
	Parametros parametrosPos, parametrosSys;
	private Button btActualizarMesas;
	creaBD bd;
	private boolean isAct=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selec_mesa);
		usuario=new Usuario();
		letraEstilo=new LetraEstilo();
		bd=new creaBD(this);		
		parametrosPos=bd.getParametros(this, "P");
		parametrosSys=bd.getParametros(this, "S");
		Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        usuario.cedula=obtenerDatos.getString("cedula");  
        llMesas=(LinearLayout)findViewById(R.id.llMesas);	
        btActualizarMesas=(Button)findViewById(R.id.btActualizarMesas);
        btActualizarMesas.setOnClickListener(this);
        
		new getMesas().execute("");								
		pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Consultando Mesa.."), true,false);
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selec_mesa, menu);
		return true;
	}

	
	 /** Called when the user touches the button */
	  public void sendPedidoPesaTablet(String mesa) {
//		  if(validaNMesa())
//		  {
			   Intent intent = new Intent(SelecMesaActivity.this, CrearPedioMesaActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("NMesa", mesa);
				startActivity(intent);
				finish();
//		  }
	}
	  
	 
//	  private boolean validaNMesa()
//	  {
//		 boolean valid=false;
//		 if(!etNumeroMesa.getText().toString().equals(""))
//		 {
//			try
//			{
//				NMesa=Long.parseLong(etNumeroMesa.getText().toString());
//				valid=true;
//			}
//			catch (Exception e) {
//				NMesa=0;
//			}
//		 }
//		 else
//		 {
//			 NMesa=0;
//			 mostrarMensaje("Debe Seleccionar el Numero de mesa", "l");
//		 }
//		 return valid;		 
//	  }
	  
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
	  
	  private class getMesas extends AsyncTask<String, Void, Object>
		{
			@Override
				protected Integer doInBackground(String... params) 
				{	
				GetNMesas getNMesas=new GetNMesas(parametrosSys.getIp(),parametrosSys.getWebidText());
				NMesasPos=Long.parseLong(getNMesas.GetNMesas())+1;
				
				GetMesasOcupadas getMesasOcupadas=new GetMesasOcupadas(parametrosSys.getIp(),parametrosSys.getWebidText());
				listaMesasOcupadas=getMesasOcupadas.GetMesasOcupadas();
				return 1;	
				}
			
			
				protected void onPostExecute(Object result)
				{							
					pdu.dismiss();	
					isAct=false;
					if(NMesasPos>0 & listaMesasOcupadas!=null)
					{
					cargarMesas();
					}
				}
		}
	  private void cargarMesas()
		{
		  llMesas.removeAllViews();
			try
			{			
			 int col=0;
			 int numcolBt=0;
			 final Animation anim_scale_mesa = AnimationUtils.loadAnimation(this, R.anim.anim_scale_mesa);
				
			 LinearLayout llinea =null;
			 if(NMesasPos>0)
			 {
				 // detallado por numero de mesas la fogata sutamarchan
					for (int i = 1; i < NMesasPos; i++) { //Original
					//	 for (int i = 1; i < 27; i++) { //restaurante esquina sutamarchan
							// for (int i = 100; i < 139; i++) { //restaurante esquina 2 sutamarchan
						
						 NMesa=i;
						 final long mesa=i;
						 Button btMesa = new Button(this);
						 btMesa.setOnClickListener(new OnClickListener() {
							
							public void onClick(View v) {
							    v.startAnimation(anim_scale_mesa);
								sendPedidoPesaTablet(""+mesa);								
							}
						});			
						 btMesa.setText(""+NMesa);
//						 btMesa.setBackgroundResource(R.drawable.mesa);
						 btMesa.setTextColor(Color.WHITE);
						 btMesa.setTypeface(null, Typeface.BOLD);
						 btMesa.setTextSize(18-4);
						 
						 
						 
						 col++;
						 
						 if( buscaMesaOcupada(NMesa))
						 {
							 btMesa.setBackgroundResource(R.drawable.blue_button);
//							 btMesa.setBackgroundColor(0xFF347BE5);//Ocupada 
						 }
						 else
						 {
							 btMesa.setBackgroundResource(R.drawable.red_button);
//							 btMesa.setBackgroundColor(0xFFF4EC00);//Disponible
						 }
						 Drawable img =SelecMesaActivity.this.getResources().getDrawable( R.drawable.mesa2 );
						 img.setBounds( 0, 0, 40, 40 );
						 btMesa.setCompoundDrawables( img, null, null, null );
					
					     if(col==4)
						 {
							 col=0; 
						 }
					     LinearLayout ll = new LinearLayout(this);
					     ll.setOrientation(LinearLayout.VERTICAL);		
					     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);		
					     layoutParams.setMargins(20, 20, 0, 0);
					     layoutParams.height=80-20;
					     layoutParams.width=120-30;
					     ll.addView(btMesa, layoutParams);
					     
					     if(numcolBt==0)
					     {
					    	 LinearLayout.LayoutParams layoutParamsHor = new LinearLayout.LayoutParams(
							 LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					    	 llinea=new LinearLayout(this);
					    	 llinea.setOrientation(LinearLayout.HORIZONTAL);
					    	 llinea.setLayoutParams(layoutParamsHor);
					     }
					     numcolBt++;				     
					     llinea.addView(ll);
					     
					     if(numcolBt==4)
					     {
					    	 llMesas.addView(llinea);
					    	 numcolBt=0;
					     }			    
					}
					 if(numcolBt!=0)
					 {
						 llMesas.addView(llinea);
				    	 numcolBt=0;
					 }
			 }
			}
			catch (Exception e) {
				System.out.println("Err22 "+e.toString());
			}
		}
	  private boolean buscaMesaOcupada(long mesa)
	  {
		  for (int i = 0; i < listaMesasOcupadas.size(); i++) {
			if(listaMesasOcupadas.get(i)==mesa)
			{
				return true;
			}
		}
		  return false;
	  }

	public void onClick(View v) {
		if(v.equals(btActualizarMesas))
		{
			if(!isAct)
			{
				isAct=true;
				new getMesas().execute("");								
				pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Consultando Mesas.."), true,false);
			}
			
		}
		
	}
		
}
