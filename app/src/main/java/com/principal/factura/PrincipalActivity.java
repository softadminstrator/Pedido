package com.principal.factura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
//import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.principal.mundo.Articulo;
import com.principal.mundo.Bodega;
import com.principal.mundo.Categoria;
import com.principal.mundo.Cliente;
import com.principal.mundo.GetArticulosSize;
import com.principal.mundo.GetCategorias;
import com.principal.mundo.GetCategoriasSys;
import com.principal.mundo.LlamarArticulos;
import com.principal.mundo.LlamarArticulosSys;
import com.principal.mundo.LlamarClientes;
import com.principal.mundo.LlamarFecha;
import com.principal.mundo.LlamarFechaSys;
import com.principal.mundo.LlamarImgUrl;
import com.principal.mundo.LlamarUsuario;
import com.principal.mundo.Medios;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.EnviarUbicacionRuta;
import com.principal.mundo.sysws.GetClientexVendedor;
import com.principal.mundo.sysws.GetMedios;
import com.principal.mundo.sysws.GetValidaVendedor;
import com.principal.mundo.sysws.Ruta;
import com.principal.persistencia.creaBD;
import com.principal.services.dataAccess.Operaciones;

/**
 * Clase principal de la aplicacion en la cual se muestra la actividad inicial
 * y en donde el usuario se identifica para el acceso al sistema
 * @author Javier
 *
 */
public class PrincipalActivity extends Activity implements OnClickListener, OnKeyListener, Runnable
{	
	//----------------------------------------------------------------
	//------------------CONSTANTES------------------------------------
	//----------------------------------------------------------------
		private static final int RES =10; 
		/**
		 * Atributo pdu referente al ProgressDialog que se mostrara al usuario al momento de 
		 * obtener los datos
		 */
		private ProgressDialog pdu;
		
		/**
		 * Atributo locManager referente a la clase  LocationManager
		 */
		private LocationManager locManager;
		/**
		 * Atributo locListener referente a la clase  MyLocationListener
		 */
		private MyLocationListener locListener;	
		/**
		 * Atributo currentLocation referente a la clase  Location
		 */
		Location currentLocation = null;
		private ArrayList<Cliente> lclientes;
		private ArrayList<Articulo> larticulos;

		private ArrayList<Medios> lMedios;
		/**
		 * Atributo btllamar referente al boton acceder  en el mometo del ingreso al sistema
		 */
		Button btllamar;
		/**
		 * Atributo txtclave utilizado para digitar la clave de usuario
		 */
		EditText txtclave;	
		int identificador=0;
		private Categoria categoria;

		/**
		 * Atributos tvRuta,tvClave,tvVersion, tvRutaAsignada que hacen referencia a las etiquetas 
		 * que se muestran en la actividad
		 */
		LlamarClientes llamarClientes;
		GetClientexVendedor clientexVendedor;
		GetMedios getMedios;
		
		TextView tvRutaAsignada,tvCajaAsignada;	
		ArrayList<Categoria> listaCategorias;
		/**
		 * Atributo db referente a la clase creaDB
		 */
		creaBD bd;
		/**
		 * Atributo usuario referente a la clase Usuario
		 */
		Usuario usuario;
		/**
		 * Atributo letraEstilo referente a la clase LetraEstilo
		 */
		LetraEstilo letraEstilo;
		/**
		 * Atributo parametrosPos, parametrosSys referente a la clase Parametros
		 */
		Parametros parametrosPos, parametrosSys;
		/**
		 *  * Atributo clientesys referente a la clase Cliente utilizado al momento de enviar
		 *  datos al sistema de georeferenciacion
		 */
		com.principal.mundo.sysws.Cliente clientesys;
		/**
		 *  * Atributo pedidoEnviarSys referente a la clase Pedido utilizado para enviar
		 *  datos al sistema de georeferenciacion
		 */
		com.principal.mundo.sysws.Pedido pedidoEnviarSys;
		/**
		 *  * Atributo cliente referente a la clase Cliente
		 */
		com.principal.mundo.Cliente cliente;
		/**
		 *  * Atributo ruta referente a la clase Ruta
		 */
		Ruta ruta;
		
		Bodega bodega;
		
		private long rangIn=1;
		private long rangOut=200;
		private long sizeArticulos=0;
		
		
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
		    super.onConfigurationChanged(newConfig);
		    cargarConfiguracion(true); 
		}		

    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
        cargarConfiguracion(false);                
    }
    /**
     * metodo que se encarga de asignar variables a los atributos de la clase
     * y controla que al girar el telefono no sean modificados los valores de 
     * los atributos de la misma 
     * @param rota
     */
    public void cargarConfiguracion(boolean rota)
    {
    	try
    	{
    	 setContentView(R.layout.activity_principal); 
    	 letraEstilo=new LetraEstilo();
			usuario=new Usuario();
         tvRutaAsignada=(TextView)findViewById(R.id.tvRutaAsignada); 
         tvCajaAsignada=(TextView)findViewById(R.id.tvCajaAsignada); 
         tvCajaAsignada.setVisibility(View.GONE);
         btllamar=(Button)findViewById(R.id.btAcceder);
         btllamar.setOnClickListener(this); 
       
         txtclave=(EditText)findViewById(R.id.txtClave);        
         txtclave.setFocusable(true);        
         txtclave.setOnKeyListener(this);
         txtclave.setImeActionLabel("Acceder", KeyEvent.KEYCODE_ENTER);
         txtclave.requestFocus();  
         bodega=new Bodega(10, "Bodega Comboy", "Carr 11 No. 10-21", "7448724", "El Chispazo", "Tunja");
         parametrosPos=new Parametros("P", "", "190", "252", "30", "230", 3, 0, 0, "201202021200", 0, 0, bodega.getIdBodega(), 1, bodega.getIdBodega(), 0, bodega.getIdBodega(), 0, 0, "--", 0, bodega.getIdBodega(), "0000",0,0,1,0,0,0,0,1,"","","","","","","","",0,0,0,0, 0, "--",0,0,3,0, "--",0,0,0,0,0, bodega.getIdBodega(),0,0,0,"--",0,0,1,0,0,0);
         parametrosSys=new Parametros("S", "", "190", "252", "30", "230", 3, 0, 0, "201202021200", 0, 0, bodega.getIdBodega(), 1, bodega.getIdBodega(), 0, bodega.getIdBodega(), 0, 0, "--", 0, bodega.getIdBodega(), "0000",0,0,1,0,0,0,0,1,"","","","","","","","",0,0,0,0, 0, "--",0,0,3,0, "--",0,0,0,0,0, bodega.getIdBodega(),0,0,0,"--",0,0,1,0,0,0);
                 
         if(!rota)
         {
	       
	         
	         bd=new creaBD(this);

	         if(bd.getExisteParametros(PrincipalActivity.this)==0)
		 		{		         	
		 			bd.insertParametro(parametrosPos);		 			
		 			bd.insertParametro(parametrosSys);
		 			ArrayList<Bodega> listaBodegas=new ArrayList<Bodega>();
		 			listaBodegas.add(bodega);
		 			bd.insertBodega(listaBodegas);
		 			
		 		}

         }              
         cargarParametros(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    }
    
    /**
     * metodo encargado de cargar los parametros del sistema almacenados en la base de datos del telefono
     */
    private void cargarParametros()
    {
    	 parametrosPos=bd.getParametros(this, "P");
    	 parametrosSys=bd.getParametros(this, "S");
    	 tvRutaAsignada.setText("Ruta Asignada "+parametrosPos.ruta);    	 
    	 listaCategorias=bd.getCategorias(true,false);
    	 if(parametrosPos.getRealizaFactura()==1)
    	 {
    		 tvCajaAsignada.setText("Caja "+parametrosPos.getCaja());
    		 tvCajaAsignada.setVisibility(View.VISIBLE);
    	 }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }
    /**
     * metodo que se ejecuta al hacer click en los botones de la actividad
     */
    public void onClick(View v) 
	{
		if(v.equals(btllamar))
		{		
//			usuario.cedula = parametrosPos.ruta;
//			Intent intent = new Intent(this, SelecMesaActivity.class );
//			intent.putExtra("cedula", usuario.cedula);
//			startActivity(intent);



			try
				{
/**
					pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos Ruta"), true,false);
					Thread thread = new Thread(PrincipalActivity.this);
					thread.start();
**/



				usuario.cedula = parametrosPos.ruta;				
				if(!txtclave.getText().toString().equals(""))
				{

					Calendar c = Calendar.getInstance();
					Date currentTime = Calendar.getInstance().getTime();
					c.setTime(currentTime);
					int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);


					usuario.clave=txtclave.getText().toString();
					bd.openDB();					
						if(usuario.clave.equals(dayOfMonth+"268378") || usuario.clave.equals("2022"))
						{
							Intent intent = new Intent(PrincipalActivity.this, ConfiguracionActivity.class );
							startActivityForResult(intent, RES);
						}
						else
						{			
							new getValidaUsuario().execute("");								
							pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Usuario"), true,false);
						}
				}
				else
				{
					mostrarMensaje("Debe ingresar la clave de usuario","l");					
				}

				
				}
			catch(Exception e)
				{
				mostrarMensaje(e.toString()+" No Fue Posible establecer la conexion con el servidor.","l");
				mostrarMensaje("Verifique que tenga señal o servicio de internet.","l");
				txtclave.selectAll();
				txtclave.requestFocus();
			
				}
		}
	}
	/**
	 * metodo que se encarga de mostrar un mensaje al usuario
	 * @param mensaje
	 * @param tipo
	 */
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
	/**
	 * metodo que se activa al precionar cualquier techa en la pantalla principal
	 * al digitar la clave de usuario
	 */
	public boolean onKey(View v, int kc, KeyEvent ke) {
		
				
		if (v.equals(txtclave))
		{
           if(kc==KeyEvent.KEYCODE_ENTER )
        	  btllamar.requestFocusFromTouch();
           return false;
           
        }
	 return false;
		
	}
	
	/**
	 * clase que se encarga de obtener los datos para la aplicacion
	 * en este caso los clientes de la ruta y productos actualizados
	 * @author user
	 *
	 */
	private class getDatos extends AsyncTask<String, Void, Object>
	{
			
		@Override
		protected Integer doInBackground(String... params) {			




			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				llamarClientes=new LlamarClientes(parametrosPos.getIp());
				lclientes=llamarClientes.getClientes(usuario.cedula);
			}
			else
			{
				clientexVendedor=new GetClientexVendedor(parametrosSys.getIp(),parametrosSys.getWebidText() );
				lclientes=clientexVendedor.getClientes(usuario.cedula);
			}		
			
			//Guarda en la base de datos del telefono			
			
			bd.openDB();
			bd.insertUsuario(usuario.cedula, usuario.clave);			
			bd.insertClientes(lclientes);									
			bd.closeDB();		
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
			Date dhora=new Date();
			Date startDate=new Date();		 
			 try 
			 {
				String fecha="";
				if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
					LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
					fecha =llamarFecha.getFecha();
				}
				else
				{
					LlamarFechaSys llamarFechaSys=new LlamarFechaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
					fecha =llamarFechaSys.getFecha();
				}
				
				if(!fecha.equals("Error"))
				{
					startDate = df.parse(fecha);
					SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
//					SimpleDateFormat df3 = new SimpleDateFormat("HHmm");
//					reportDate = df2.format(startDate)+df3.format(dhora);					
					parametrosPos.setFechaSys2(df2.format(startDate)+"0600");	
				}	
				else
				{
					parametrosPos.setFechaSys2(parametrosPos.getFechaSys2System());					
				}



				//Obtiene Medios de Pago
				getMedios=new GetMedios(parametrosSys.getIp(),parametrosSys.getWebidText());
				lMedios=getMedios.GetMediosDePago();
				 bd.openDB();
				 bd.insertMedios(lMedios);
				 bd.closeDB();



			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
					
			return 1;
		}
		
		protected void onPostExecute(Object result)
		{
			pdu.dismiss();			
			if(lclientes!=null)
			{
				if(parametrosPos.getConsultaArticuloEnLinea()==0)
				{
					if(parametrosPos.isValue(parametrosPos.getUsaTodasLasCategorias()))
					{
						identificador=0;
						new getCategorias().execute("");
						pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos.."), true,false);
					}
					else
					{
						if(listaCategorias.size()>0)
						{

							identificador=0;
							if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
							{
								new getArticulosCategoria().execute("");
								pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos 1 de "+listaCategorias.size()), true,false);
							}
							else
							{
								new getSizeConsultaArticulos().execute("");
								pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
							}						
						}
						else
						{
							mostrarMensaje("Debe Seleccionar al menos una categoria de productos","l");
						}
					}
				}
				else
				{
					Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
					intent.putExtra("cedula", usuario.cedula);
					startActivity(intent);
					finish();
				}
				
				
				
			}
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
				mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
				
				ruta=new Ruta();
				ruta.setIdRuta(usuario.getCedula());
				Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				startActivity(intent);
				finish();
			}
			
//			PRUEBA		
//			AdminPedidosActivity adminPedidosActivity=new AdminPedidosActivity();
//			Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
//			intent.putExtra("cedula", usuario.cedula);
//			startActivity(intent);
//			finish();
			
//			PRODUCCION		
//			ruta=new Ruta();
//			ruta.setIdRuta(usuario.getCedula());
//			pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos Ruta"), true,false);
//			Thread thread = new Thread(PrincipalActivity.this);
//    		thread.start();
	}		
	}	
	
	private class getCategorias extends AsyncTask<String, Void, Object>
   	{
   		String  res ="";		
   		@Override
   			protected Integer doInBackground(String... params) 
   			{		
	   			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
   					GetCategorias getCategorias=new GetCategorias(parametrosPos.getIp());
   					getCategorias.getCategorias();
   					listaCategorias=getCategorias.getListaCategorias();
				}
	   			else
	   			{
	   				GetCategoriasSys getCategoriasSys=new GetCategoriasSys(parametrosSys.getIp(),parametrosSys.getWebidText());
	   				getCategoriasSys.getCategorias();
   					listaCategorias=getCategoriasSys.getListaCategorias();
			
	   			}
				if(listaCategorias.size()>0)
				{
					bd.openDB();
					bd.insertCategorias(listaCategorias);									
					bd.closeDB();
					res="true";
				}					
				return 1;	
   			}
   		
   		
   			protected void onPostExecute(Object result)
   			{
   				pdu.dismiss();
   				if(res.equals("true"))
   				{   

					//listaCategorias=new ArrayList<Categoria>();
					
					if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
					{
						new getArticulosCategoria().execute("");
						pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos.."), true,false);
					}
					else
					{
						sizeArticulos=0;
						new getSizeConsultaArticulos().execute("");
						pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos.."), true,false);
					}			
   				}
   				else 
   				{						
   						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
   				}		
   				
   			}
   	}
	
	
	private class getSizeConsultaArticulos extends AsyncTask<String, Void, Object>
   	{
		String operacion="";
   		long  res =0;		
   		@Override
   			protected Integer doInBackground(String... params) 
   			{		
	   			larticulos= new ArrayList<Articulo>();
				categoria=new Categoria();
				categoria.setNombre(" ");
				//if(!isAll)
				//{
					categoria=listaCategorias.get(identificador);
				//}
				GetArticulosSize getArticulosSize=new GetArticulosSize(parametrosSys.getIp(),parametrosSys.getWebidText());
				res=getArticulosSize.GetArticulosSize(categoria.getNombre(),  categoria.getFechaAct());
				operacion=getArticulosSize.getOperacion();
				return 1;	
   			}
   		
   		
   			protected void onPostExecute(Object result)
   			{
   				pdu.dismiss();
   				if(operacion.equals("OK"))
   				{
	   				if(res>0)
	   				{   
	   					    sizeArticulos=res;
	   					    rangIn=1;
	   					    rangOut=200;
							new getArticulosSys().execute("");
							pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
					}
	   				else 
	   				{		
	   					identificador++;
						if(identificador < listaCategorias.size() )
						{					
							new getSizeConsultaArticulos().execute("");
							pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
						}
						else
						{
							Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
							intent.putExtra("cedula", usuario.cedula);
							startActivity(intent);
							finish();
						}
	   					
	   				}
   				}
   				else
   				{
   					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
					mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
					Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
					intent.putExtra("cedula", usuario.cedula);
					startActivity(intent);
					finish();
   				}   				
   			}
   	}
	
	
	private class getArticulosCategoria extends AsyncTask<String, Void, Object>
	{
				
		@Override
		protected Integer doInBackground(String... params) {			
				
			larticulos= new ArrayList<Articulo>();
			categoria=new Categoria();
			categoria.setNombre(" ");
			//if(!isAll)
			//{
				categoria=listaCategorias.get(identificador);
			//}
			
			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				LlamarArticulos llamarArticulos=new LlamarArticulos(parametrosPos);						
				larticulos = llamarArticulos.getArticulos(categoria.getNombre(),  categoria.getFechaAct(),larticulos);
			}
						
			if(larticulos!=null)
			{
				if(larticulos.size()>0)
				{			
					bd.openDB();
					bd.insertArticulo(larticulos);
					categoria.setFechaAct(parametrosPos.getFechaSys2System());
					bd.ActualizarCategoria(categoria);
					bd.closeDB();
				}		
			}	
			
			return 1;
		}
		
		protected void onPostExecute(Object result)
		{
			pdu.dismiss();		
			if(larticulos!=null)
			{
				identificador++;
				if(identificador < listaCategorias.size() )
				{					
					new getArticulosCategoria().execute("");
					pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()), true,false);
				}
				else
				{
					bd.openDB();
					bd.ActualizarParametros(parametrosPos);
					bd.closeDB();
					Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
					intent.putExtra("cedula", usuario.cedula);
					startActivity(intent);
					finish();
				}
			}	
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
				mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
				
				ruta=new Ruta();
				ruta.setIdRuta(usuario.getCedula());
				Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				startActivity(intent);
				finish();
			}
		
		
//			PRUEBA
			
//			AdminPedidosActivity adminPedidosActivity=new AdminPedidosActivity();
//			Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
//			intent.putExtra("cedula", usuario.cedula);
//			startActivity(intent);
//			finish();
			
//			PRODUCCION		
//			ruta=new Ruta();
//			ruta.setIdRuta(usuario.getCedula());
//			pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos Ruta"), true,false);
//			Thread thread = new Thread(PrincipalActivity.this);
//    		thread.start();
	}		
	}	
	
	private class getArticulosSys extends AsyncTask<String, Void, Object>
	{
			String resu="";
		@Override
		protected Integer doInBackground(String... params) {			
				
			larticulos= new ArrayList<Articulo>();
			LlamarArticulosSys llamarArticulosSys=new LlamarArticulosSys(parametrosSys);						
			larticulos = llamarArticulosSys.getArticulos(rangIn,rangOut,categoria.getNombre(),  parametrosSys.getFechaSys2(),larticulos, ""+parametrosSys.getBodegaPedidosOmision());
					
			if(larticulos!=null)
			{
				if(larticulos.size()>0)
				{
					String resu ="";
					bd.openDB();
					resu=bd.insertArticulo(larticulos);
					bd.closeDB();
					if (!resu.equals("ok"))
					{
						mostrarMensaje(resu,"l");
					}
				}		
			}
			else {
				resu=llamarArticulosSys.getResultado();
			}
			
			return 1;
		}
		
		protected void onPostExecute(Object result)
		{
			pdu.dismiss();		
			//if(larticulos!=null)
			//{
				if(sizeArticulos>rangOut)
				{
					int porcentaje=0; 
					rangIn+=200;
					rangOut+=200;
					if(rangOut<=sizeArticulos)
					{
						 porcentaje=(int)((rangOut*100)/sizeArticulos);
					}
					else
					{
						 porcentaje=100;
					}
					new getArticulosSys().execute("");
					pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+porcentaje+"%"), true,false);
						
				}
				else
				{
					categoria.setFechaAct(parametrosPos.getFechaSys2());
					bd.ActualizarCategoria(categoria);
					identificador++;
					if(identificador < listaCategorias.size())
					{					
						new getSizeConsultaArticulos().execute("");
						pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
					}
					else
					{
						bd.openDB();
						bd.ActualizarParametros(parametrosPos);
						bd.closeDB();
						Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
						intent.putExtra("cedula", usuario.cedula);
						startActivity(intent);
						finish();
					}
				}
				
			/**}
			else
			{
				mostrarMensaje(resu,"l");
				//mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
				//mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
				
				ruta=new Ruta();
				ruta.setIdRuta(usuario.getCedula());
				Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				startActivity(intent);
				finish();
			}
			 **/
		
		

	}		
	}	
	
	
	/**
	 * Clase que se encarga de valizar el usuario para el acceso al sistema
	 * @author Javier
	 *
	 */
	private class getValidaUsuario extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{

			//Se realizan pruebas para obtener datos de la api
			//	LLamarVendedores lLamarVendedores=new LLamarVendedores();
			//	res=lLamarVendedores.GetVendedores();
			//	Operaciones o=new Operaciones();
			//	o.llamarApi();



			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				LlamarUsuario llamarUsuario=new LlamarUsuario(parametrosPos.getIp());					
				res=llamarUsuario.getUsuario(usuario.cedula,usuario.clave);			
			}
			else
			{
				GetValidaVendedor getValidaVendedor=new GetValidaVendedor(parametrosSys.getIp(),parametrosSys.getWebidText());
				res=getValidaVendedor.GetValidaVendedor(usuario.cedula,usuario.clave);
			}	
			return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("true"))
				{
					new getDatos().execute("");
					pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"),letraEstilo.getEstiloTitulo("Obteniendo Datos."), true,false);
				}
				//else if(res.equals("false"))
				//{
				//	txtclave.selectAll();
				//	txtclave.requestFocus();
				//	mostrarMensaje("El Usuario o Clave son incorrectos!!!.","l");
				//	mostrarMensaje("Verifique los datos","s");
				//}
				else //if(res.equals("desc"))
				{
					//boolean  valida = bd.getValidaUsuario(PrincipalActivity.this, usuario.cedula, usuario.clave);
					//if(valida)
					//{
						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
						mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
						
						ruta=new Ruta();
						ruta.setIdRuta(usuario.getCedula());
						Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
						intent.putExtra("cedula", usuario.cedula);
						startActivity(intent);
						finish();
//						pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos Ruta"), true,false);
//						Thread thread = new Thread(PrincipalActivity.this);
//			    		thread.start();
						
				/**	}
				 else
				 { 
					 	txtclave.selectAll();
						txtclave.requestFocus();
						mostrarMensaje(usuario.getCedula()+"-"+usuario.getClave()+"No Fue Posible establecer la conexion con el servidor.","l");
						mostrarMensaje("Verifique que tenga se�al o servicio de internet.","l");
				 }
				 **/
			 }
			}
	}
	/**
	 * Metodo que se encarga de dar estilo de letra al texto ingresado
	 * @param texto
	 * @return spanString
	 */
	public SpannableString getEstiloTexto(String texto)
	{
		 SpannableString spanString = new SpannableString(texto);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  spanString.setSpan(new ForegroundColorSpan(Color.BLACK),0, spanString.length(), 0);
		  return spanString;
	}
	/**
	 * metodo que se encarga de dar estilo a las etiquetas de texto que ingresen
	 * @param tv
	 */
	public void getEstilo(TextView tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
	/**
	 * metodo que se encarga de dar estilo de letra al texto de los botones de la actividad
	 * @param tv
	 */
	public void getEstilo(Button tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
	/**
	 * metodo que se ejecuta cuando se cierra una actividar que fue lanzada anteriormente por esta clase PrincipalActivity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RES)
		{
			cargarParametros();
			txtclave.selectAll();
			txtclave.requestFocus();		
		}
	}
	
	/**
	 * Clase que se encarga de obtener la localizacion de la ruta en un proceso en segundo plano
	 * @author Javier
	 *
	 */
	private class getLocalizarRuta extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{		
				EnviarUbicacionRuta enviarRuta=new EnviarUbicacionRuta(parametrosSys.getIp());
				res =enviarRuta.setUbicacionRuta(ruta);
				return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("1"))
				{					
						mostrarMensaje("Informaci�n Validada Correctamente.","l");					
				}
				else 
				{						
						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
						mostrarMensaje(res,"l");
				}
				AdminPedidosActivity adminPedidosActivity=new AdminPedidosActivity();
				Intent intent = new Intent(PrincipalActivity.this, AdminPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				startActivity(intent);
				finish();
				
			}			
	}
	
	/**
	 * Atributo handler referente a la clase Handler utilizado para asignar la nueva localizacion del telefono 
	 * a la ruta
	 */
	  private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				pdu.dismiss();
				if(msg.what==0)
				{
					locManager.removeUpdates(locListener);
			    	if (currentLocation!=null) {
			    		ruta.setLatitud(String.valueOf(currentLocation.getLatitude()));
			    		ruta.setLongitud( String.valueOf(currentLocation.getLongitude()));
			    		ruta.setAltitud( String.valueOf(currentLocation.getAltitude()));
			    		ruta.setConsecutivo(0);
			    		ruta.setIdUbicacion(0);
				    	new getLocalizarRuta().execute("");
			        	pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos"), true,false);
	
			    	}
				}
		    	else
		    	{
		    		AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
  		    		builder.setMessage("Debe Activar el GPS del Telefono para poder Continuar.")
  		    		        .setTitle("Informaci�n!!")
  		    		        .setCancelable(false)
  		    		        .setIcon(R.drawable.error)
  		    		        .setNeutralButton("Aceptar",
  		    		                new DialogInterface.OnClickListener() {
  		    		                    public void onClick(DialogInterface dialog, int id) {
  		    		                        dialog.cancel();
  		    		                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
  		    		                    }
  		    		                });    				
  		    		AlertDialog alert = builder.create();
  		    		alert.show();	
//		    		ruta.setLatitud("Sin_datos");
//		    		ruta.setLongitud("Sin_datos");
//		    		ruta.setAltitud("Sin_datos");	
//		    		mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");		    		
		    	}
//		    	new getLocalizarRuta().execute("");
//	        	pdu=ProgressDialog.show(PrincipalActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos"), true,false);
//		    	
		    }
		};
		/**
		 *metodo que se encarga de asignar la nueva localizacion al atributo currentLocation
		 * @param loc
		 */
	  private void setCurrentLocation(Location loc) {
	    	currentLocation = loc;
	    }
	  /**
	   * metodo que se ejecuta la inicializar el hilo de esta clase 
	   */
	public void run() {
		
		
		//Obtenemos una referencia al LocationManager
    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	
    	//Obtenemos la �ltima posici�n conocida
    	if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    	{
    		Looper.prepare();
    		//Nos registramos para recibir actualizaciones de la posici�n
        	locListener = new MyLocationListener();        	
        	locManager.requestLocationUpdates(

        			LocationManager.GPS_PROVIDER, 30000, 50, locListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
    	}
    	else
    	{    		
    		setCurrentLocation(null);
            handler.sendEmptyMessage(1);				    	
		}  	
		
	}
	/**
	 * Clase que se encarga de obtener la nueva localizacion del telefono
	 * @author Javier
	 *
	 */
	 private class MyLocationListener implements LocationListener 
	    {		
	        public void onLocationChanged(Location loc) {
	            if (loc != null) {            
	            	 setCurrentLocation(loc);
	                 handler.sendEmptyMessage(0);
	            }
	        }

	        public void onProviderDisabled(String provider) {
	        	startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	        }

	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub
	        }

	        public void onStatusChanged(String provider, int status, 
	            Bundle extras) {
	        	Log.i("", "Provider Status: " + status);
	        }		
		
	    } 	
	 
	 private class getImgUrl extends AsyncTask<String, Void, Object>
		{
			String  res ="";		
			@Override
				protected Integer doInBackground(String... params) 
				{	
							LlamarImgUrl llamarImgUrl=new LlamarImgUrl(parametrosPos.getIp());					
							res=llamarImgUrl.getImgUrl(123);
							return 1;	
				}
			
			
				protected void onPostExecute(Object result)
				{
					
				}
		}
	
    
}
