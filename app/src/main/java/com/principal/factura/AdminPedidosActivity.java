package com.principal.factura;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.ToggleButton;
import com.bixolon.printer.BixolonPrinter;
import android.os.Handler;
import android.bluetooth.BluetoothDevice;
import java.util.Set;


import com.epson.eposprint.*;

import com.principal.mundo.Articulo;
import com.principal.mundo.Catalogo;
import com.principal.mundo.Categoria;
import com.principal.mundo.GetArticulosSize;
import com.principal.mundo.GetCatalogos;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintEpson;


/**
 * clase que hace referencia al menu principal de la aplicacion 
 * la cual se muestra despues de que el usuario se halla identificado
 * @author Javier
 *
 */
public class AdminPedidosActivity extends Activity  implements OnClickListener, StatusChangeEventListener, BatteryStatusChangeEventListener {
	
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO= 3;
	private final static int INVENTARIO = 4;
	private final static int CONSULTAARTICULO=5;
	private final static int PEDIDOMESA = 6;
	private final static int CARTERA=7;
	private final static int PRESTAMOS=8;
	private final static int ABONOPRESTAMOS=9;
	private final static int REMISION=12;


	static final int SEND_TIMEOUT = 10 * 1000;
	private Print printer;
	static BixolonPrinter mBixolonPrinter;
	private boolean conn=false;
	
	/**
	 * Atributo btVerArticulo referente a la opcion de consultar precios del sistema
	 */
	private Button btFactura,btFacturasRealizadas,btPedido,
					btPedidosRealizados,btVerListaArticulos, btAdministraInventario,
					btZFinanciera,btTraslados,btTrasladosRealizados, btPedidoMesa,btCartera, btPagosRealizados,btAlistamiento,
	btPrestamos,btPrestamosRealizados, btRemision, btRemisionesRealizadas;
	
	
	/**
	 * Arreglo de TextView para hacer referencia a cada una de las cajas de texto de la actividad
	 */
	TextView [] tvTextViews;
	/**
	 * Atributo letraEstilo hace referencia a la clase Letra estilo
	 */
	LetraEstilo letraEstilo;
	/** 
	 * Atributo usuario hace referencia a la clase Usuario 
	 */
	Usuario usuario;
	
	creaBD bd;
	Parametros parametrosPos;
	//private  Print printer;
	
	private ProgressDialog pdu;
	/**
	 *Metodo que se encarga de inicalizar la actividad y asignarles valores a los atributos de la clase
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pedidos);
        usuario=new Usuario();
        bd=new creaBD(this);      
        parametrosPos=bd.getParametros(this,"P");
        letraEstilo=new LetraEstilo();
        
        tvTextViews=new TextView[3];        
        tvTextViews[0]=(TextView)findViewById(R.id.tvTituloMenu);
        tvTextViews[1]=(TextView)findViewById(R.id.tvRutaA);
        tvTextViews[2]=(TextView)findViewById(R.id.tvRutaAN);   
        
        btFactura=(Button)findViewById(R.id.btFactura);
        btFacturasRealizadas=(Button)findViewById(R.id.btFacturasRealizadas);
        btPedido=(Button)findViewById(R.id.btPedido);
        btPedidosRealizados=(Button)findViewById(R.id.btPedidosRealizados);
        btVerListaArticulos=(Button)findViewById(R.id.btVerListaArticulos);
        btAdministraInventario=(Button)findViewById(R.id.btAdministraInventario);
        btZFinanciera=(Button)findViewById(R.id.btZFinanciera); 
        btTraslados=(Button)findViewById(R.id.btTraslados); 
        btTrasladosRealizados=(Button)findViewById(R.id.btTrasladosRealizados);         
        btPedidoMesa=(Button)findViewById(R.id.btPedidoMesa);
		btAlistamiento=(Button)findViewById(R.id.btAlistamiento);
        btCartera=(Button)findViewById(R.id.btCartera); 
        btPagosRealizados=(Button)findViewById(R.id.btPagosRealizados);

		btPrestamos=(Button)findViewById(R.id.btPrestamos);
		btPrestamosRealizados=(Button)findViewById(R.id.btPrestamosRealizados);


		btRemision=(Button)findViewById(R.id.btRemision);
		btRemisionesRealizadas=(Button)findViewById(R.id.btRemisionesRealizadas);




      
        btFactura.setOnClickListener(this);
        btFacturasRealizadas.setOnClickListener(this);
        btPedido.setOnClickListener(this);
        btPedidosRealizados.setOnClickListener(this);
        btVerListaArticulos.setOnClickListener(this);
        btAdministraInventario.setOnClickListener(this);
        btZFinanciera.setOnClickListener(this); 
        btTraslados.setOnClickListener(this); 
        btTrasladosRealizados.setOnClickListener(this); 
        btPedidoMesa.setOnClickListener(this);
		btAlistamiento.setOnClickListener(this);
        btCartera.setOnClickListener(this); 
        btPagosRealizados.setOnClickListener(this);
		btPrestamos.setOnClickListener(this);
		btPrestamosRealizados.setOnClickListener(this);

		btRemision.setOnClickListener(this);
		btRemisionesRealizadas.setOnClickListener(this);

       
        
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        usuario.cedula=obtenerDatos.getString("cedula");        
        tvTextViews[2].setText( usuario.cedula); 
        
        
        validCheckAdmInv(parametrosPos.getRealizaFactura(),btFactura);
        validCheckAdmInv(parametrosPos.getRealizaFactura(),btFacturasRealizadas);
        validCheckAdmInv(parametrosPos.getRealizaPedidos(),btPedido);
        validCheckAdmInv(parametrosPos.getRealizaPedidos(),btPedidosRealizados);
        validCheckAdmInv(parametrosPos.getRealizaTranslados(),btTraslados);
        validCheckAdmInv(parametrosPos.getRealizaTranslados(),btTrasladosRealizados);
        validCheckAdmInv(parametrosPos.getAdministraInventario(), btAdministraInventario);
        validCheckAdmInv(parametrosPos.getConsultaZ(),btZFinanciera);        
        validCheckAdmInv(parametrosPos.getRealizaPedidosMesa(),btPedidoMesa); 
        validCheckAdmInv(parametrosPos.getUsaCatalogo(),btCartera);
        validCheckAdmInv(parametrosPos.getUsaCatalogo(),btPagosRealizados);
		validCheckAdmInv(parametrosPos.getRealizaAlistamiento(),btAlistamiento);
		validCheckAdmInv(parametrosPos.getUsaPrestamos(),btPrestamos);
		validCheckAdmInv(parametrosPos.getUsaPrestamos(),btPrestamosRealizados);

		validCheckAdmInv(parametrosPos.getRealizaRemision(),btRemision);
		validCheckAdmInv(parametrosPos.getRealizaRemision(),btRemisionesRealizadas);





    }
    
 
    private void validCheckAdmInv(long value, View view)
	{
		int visibility=View.VISIBLE;
		if(value==0)
		{
			visibility=View.GONE;
		}
		view.setVisibility(visibility);			
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_pedidos, menu);
        return true;
    }
	/**
	 * Metodo en el que se determinan las acciones a realizar en el evento click de cada uno de los botones
	 */
	public void onClick(View v) 
	{
		if(v.equals(btFactura))
		{
			Intent intent = new Intent(this, RuteroActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", FACTURA);
			startActivity(intent);
		}	
		if(v.equals(btFacturasRealizadas))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", FACTURA);
			intent.putExtra("print",false);
			intent.putExtra("idCodigoExterno",0 );			
			startActivity(intent);
		}
		if(v.equals(btPedido))
		{
			Intent intent = new Intent(this, RuteroActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", PEDIDO);		
			startActivity(intent);
		}
		if(v.equals(btPedidosRealizados))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", PEDIDO);
			intent.putExtra("print",false);
			intent.putExtra("idCodigoExterno",0 );			
			startActivity(intent);
		}
		if(v.equals(btRemision))
		{
			Intent intent = new Intent(this, RuteroActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", REMISION);
			startActivity(intent);
		}
		if(v.equals(btRemisionesRealizadas))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", REMISION);
			intent.putExtra("print",false);
			intent.putExtra("idCodigoExterno",0 );
			startActivity(intent);
		}

		if(v.equals(btTraslados))
		{
			Intent intent = new Intent(this, SelecTrasladoBodegaActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", TRANSLADO);
			startActivity(intent);
		}
		if(v.equals(btTrasladosRealizados))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", TRANSLADO);
			intent.putExtra("print",false);
			intent.putExtra("idCodigoExterno",0 );				
			startActivity(intent);
		}
	    if(v.equals(btVerListaArticulos))
		{			
			Intent intent = new Intent(this, VerProductosActivity.class );
			intent.putExtra("operacion",CONSULTAARTICULO);
			intent.putExtra("precioCliente",parametrosPos.getPrecioMinimo());
			startActivity(intent);
			try {
				//printBixolonsppr310();
				//new getOPEN().execute("");
				//openPrinter2();
				//openPrinter();
				//PrintEpson  pz = new PrintEpson(parametrosPos);
				//String mensaje =pz.printFactura();
				//pdu = ProgressDialog.show(AdminPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
				//String mensaje=pz.printFactura();
				//pdu.dismiss();
				//mostrarMensaje(mensaje, "l");

			} catch (Exception e) {
				//pdu.dismiss();
				mostrarMensaje("Imp " + e.toString(), "l");
			}//


		}	
	    if(v.equals(btAdministraInventario))
		{	
	    	//if(parametrosPos.getConsultaArticuloEnLinea()==1)
			//{
				Intent intent = new Intent(this, VerProductosActivity.class );
				intent.putExtra("operacion",INVENTARIO);
				startActivity(intent);
			//}
	    	//else
	    	//{
	    	//	mostrarMensaje("Debe habilitar la opcion Consulta Articulo En linea", "");
	    	//}
		}			
		if(v.equals(btZFinanciera))
		{
			Intent intent = new Intent(this, ZFinancieraActivity.class );			
			startActivity(intent);
		}
		
		if(v.equals(btPedidoMesa))
		{
			Intent intent = new Intent(this, SelecMesaActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			startActivity(intent);
		}

		if(v.equals(btAlistamiento))
		{
			Intent intent = new Intent(this, ListaPedidosAlistamiento.class );
			intent.putExtra("cedula", usuario.cedula);
			startActivity(intent);
		}
		
		if(v.equals(btCartera))
	{
		Intent intent = new Intent(this, ConsultaClientesActivity.class );
		intent.putExtra("cedula", usuario.cedula);
		intent.putExtra("operacion", CARTERA);
		startActivity(intent);
	}
		if(v.equals(btPagosRealizados))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", CARTERA);
			intent.putExtra("print",false);
			startActivity(intent);
		}

		if(v.equals(btPrestamos))
		{

			Intent intent = new Intent(this, RuteroActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", PRESTAMOS);
			startActivity(intent);


			//Intent intent = new Intent(this, ConsultaClientesActivity.class );
			//intent.putExtra("cedula", usuario.cedula);
			//intent.putExtra("operacion", PRESTAMOS);
			//startActivity(intent);
		}
		if(v.equals(btPrestamosRealizados))
		{
			Intent intent = new Intent(this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", PRESTAMOS);
			intent.putExtra("print",false);
			startActivity(intent);
		}



	}

	private void printBixolonsppr310()
	{
		try {
			mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
			mBixolonPrinter.connect(parametrosPos.getMacAddBixolon());

		}
		catch (Exception e)
		{
			mostrarMensaje("e44"+e.toString(),"l");
		}

		//mBixolonPrinter.findBluetoothPrinters();
	}
	private final Handler mHandler = new Handler(new Handler.Callback() {
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case BixolonPrinter.MESSAGE_STATE_CHANGE:
					if (msg.arg1 == BixolonPrinter.STATE_CONNECTED) {
						mBixolonPrinter.printText("HOLA MUNDO \n ", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.TEXT_ATTRIBUTE_FONT_A |
										BixolonPrinter.TEXT_ATTRIBUTE_UNDERLINE1,
								BixolonPrinter.TEXT_SIZE_HORIZONTAL1 |
										BixolonPrinter.TEXT_SIZE_VERTICAL1, true);

					}
					break;
				case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
					mBixolonPrinter.disconnect();
					break;
			}
			return true;
		}
	});

	private void openPrinter2() {


		int deviceType = Print.DEVTYPE_BLUETOOTH;

		int enabled = Print.FALSE;

		int interval = 0;


		//open
		Print printer = new Print(getApplicationContext());

		if(printer != null){
			printer.setStatusChangeEventCallback(this);
			printer.setBatteryStatusChangeEventCallback(this);
			mostrarMensaje("OPEN","l");
		}


		try{
			printer.openPrinter(deviceType, parametrosPos.getMacAddEpson(), enabled, interval);
		}catch(Exception e){
			printer = null;
			mostrarMensaje("OPEN","l");
			return;
		}


	}



	private int getBuilderLineSpace(String text) {
		try{
			return Integer.parseInt(text.toString());
		}catch(Exception e){
			return 0;
		}
	}

	  private class getCatalogos extends AsyncTask<String, Void, Object>
	   	{
	   		String  res ="";		
	   		@Override
	   			protected Integer doInBackground(String... params) 
	   			{		
	   					GetCatalogos getCatalogos=new GetCatalogos(parametrosPos.getIp());
	   					getCatalogos.getCatalogos();
	   					ArrayList<Catalogo> listaCatalogos=getCatalogos.getListaCatalogos();
	   					if(listaCatalogos.size()>0)
	   					{
	   						bd.openDB();
	   						bd.insertCatalogos(listaCatalogos);									
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
	   					  Intent intent = new Intent(AdminPedidosActivity.this, CatalogoActivity.class );
	   	      			  startActivity(intent); 					
	   					
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
	  
	  /** Called when the user touches the button */
	  public void sendPedidoPesaMovil(View view) {
		  /**
		   * QUEDA PENDIENTE POR IMPLEMENTAR PEDIDOS DE MESA DESDE DISPOSITIVOS MOVILES
		   */	
		  mostrarMensaje("En proceso de implementacion. \n Consulte con su proveedor de software", "l");
	  }


	private class getOPEN extends AsyncTask<String, Void, Object>
	{
		String operacion="";
		long  res =0;

		protected Integer doInBackground(String... params)
		{
			int deviceType = Print.DEVTYPE_BLUETOOTH;
			int enabled = Print.FALSE;
			int interval = 1000;
			try{
				//open
				Print printer = new Print();
				operacion="ok";
				if(printer != null){
					printer.setStatusChangeEventCallback(AdminPedidosActivity.this);
					printer.setBatteryStatusChangeEventCallback(AdminPedidosActivity.this);
				}
				printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
			}catch(Exception e){
				//printer = null;

				return 0;
			}
			return 1;
		}


		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(operacion.equals("OK")) {
			}
		}
	}

	public void onStatusChangeEvent(final String deviceName, final int status) {
		;
	}
	public void onBatteryStatusChangeEvent(final String deviceName, final int battery) {
		;
	}


}
