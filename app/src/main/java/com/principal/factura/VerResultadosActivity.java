package com.principal.factura;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.BatteryStatusChangeEventListener;
import com.epson.eposprint.Builder;
import com.epson.eposprint.Print;
import com.epson.eposprint.StatusChangeEventListener;
import com.principal.mundo.Articulo;
import com.principal.mundo.Categoria;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.Libro;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintBixolon;
import com.principal.print.PrintEpson;
import com.principal.print.PrintZebra;
/**
 * Clase que se encarga de mostrar los resultado de ventas en una fecha deteminada
 * @author Javier
 *
 */
public class VerResultadosActivity extends Activity implements OnClickListener,StatusChangeEventListener, BatteryStatusChangeEventListener {
	
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int PRESTAMOS=8;
	private final static int ABONOPRESTAMOS=9;
	private final static int REMISION=12;

	static final int SEND_TIMEOUT = 10 * 1000;
	private Print printer;
	private ArrayList<String> datos=new ArrayList<String>();
	/**
	 * Atributo usuario referencia de la clase Usuario
	 */
	private Usuario usuario;
	/**
	 * Atributo btVolver referente al boton de volver utilizado para cerrar la actividad y regresar
	 * a la actividad de pedidos realizados
	 */
	private Button btVolver,btMenuVR;
	static BixolonPrinter mBixolonPrinter;
	/**
	 * Atributo bd referencia de la clase creaBD
	 */
	private creaBD bd;
	/**
	 * Atributo textViews arreglo que guarda las etiquetas de la clase
	 */
	private TextView [] textViews;
	
	private int operacion;
	
	private String fechaDesde,fechaBotonDesde,fechaBotonHasta;
	private String fechaHasta,bodegaOrigen, bodegaDestino;
	String printFechaDesde="";
	String printFechaHasta="";
	
	
	private RelativeLayout rlVerResultados;
	/**
	 * Atributo letraEstilo referencia a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	ArrayList<Articulo> listaArticulos;
	ArrayList<Libro> listaLibros;
	Libro libro;
	private ListView lvCategorias;

	private Spinner spinnerCategorias;
	ArrayList<Categoria> listaCategorias;

	/**
	 * 	Atributo fecha Referente a la clase Date en donde se almacenara la fecha actual del sistema
	 */
	Date fecha=new Date();
	
	TabHost tabHost;
	private Parametros parametros;
	private ProgressDialog pdu;
	
	/**
	 * metodo encargado de asignar valores a los atributos de la actividad
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultados);
		try {
			btVolver = (Button) findViewById(R.id.btVolverVR);
			btVolver.setOnClickListener(this);
			btMenuVR = (Button) findViewById(R.id.btMenuVR);
			btMenuVR.setOnClickListener(this);

			letraEstilo = new LetraEstilo();
			textViews = new TextView[7];
			lvCategorias = (ListView) findViewById(R.id.lvCategorias);

			spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
			//spinnerCategorias.setOnClickListener(this);

			bd = new creaBD(this);
			parametros = bd.getParametros(this, "P");

			textViews[0] = (TextView) findViewById(R.id.tvTotalClientesVR);
			textViews[1] = (TextView) findViewById(R.id.tvEfectivosValorVR);
			textViews[2] = (TextView) findViewById(R.id.tvPendientesValorVR);
			textViews[3] = (TextView) findViewById(R.id.tvFechaVR);
			textViews[4] = (TextView) findViewById(R.id.tvValorTotal);
			textViews[5] = (TextView) findViewById(R.id.tvUnidadesTotal);
			textViews[6] = (TextView) findViewById(R.id.tvCantidadTotal);
			rlVerResultados = (RelativeLayout) findViewById(R.id.rlVerResultados);

			cargardatos();
		}
		catch (Exception e)
		{
			mostrarMensaje(e.toString(),"l");
		}
    }
    
    /**
     * metodo encargado de cargar los datos en las etiquetas para mostrar al usuario
     * como  el valor total de pedidos, numero total de pacas, total de clientes 
     * visitads, faltantes y efectivos
     */
    private void cargardatos() 
    {
		try {
			usuario = new Usuario();
			bd = new creaBD(this);
			Bundle obtenerDatos = new Bundle();
			obtenerDatos = this.getIntent().getExtras();
			usuario.cedula = obtenerDatos.getString("cedula");
			fechaDesde = obtenerDatos.getString("fechaDesde");
			fechaHasta = obtenerDatos.getString("fechaHasta");
			operacion = obtenerDatos.getInt("operacion");
			fechaBotonDesde = obtenerDatos.getString("fechaBotonDesde");
			fechaBotonHasta = obtenerDatos.getString("fechaBotonHasta");
			if (operacion == TRANSLADO) {
				bodegaOrigen = obtenerDatos.getString("bodegaOrigen");
				bodegaDestino = obtenerDatos.getString("bodegaDestino");
				rlVerResultados.setBackgroundColor(0xFFE0E0E0);
				listaArticulos = bd.getVentasTrasladosporArticulo(fechaDesde, fechaHasta);
				lvCategorias.setAdapter(new CategoriaResAdapter(this, R.layout.activity_item_categoria_res, listaArticulos));

			} else if (operacion == FACTURA) {
				rlVerResultados.setBackgroundColor(0xFFE0E0E0);
				listaArticulos = bd.getVentasFacturasporArticulo(fechaDesde, fechaHasta, "0");
				lvCategorias.setAdapter(new CategoriaResAdapter(this, R.layout.activity_item_categoria_res, listaArticulos));

				listaCategorias = bd.getCategoriasVerResultados();
				spinnerCategorias.setAdapter(new CategoriaSpinnerAdapter(this, R.layout.activity_item_categoria_spinner, listaCategorias));
				spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						lvCategorias.setAdapter(null);
						listaArticulos = bd.getVentasFacturasporArticulo(fechaDesde, fechaHasta, "" + listaCategorias.get(position).getIdCategoria());
						lvCategorias.setAdapter(new CategoriaResAdapter(VerResultadosActivity.this, R.layout.activity_item_categoria_res, listaArticulos));
						cargarTotales();
					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			} else if (operacion == REMISION) {
				rlVerResultados.setBackgroundColor(0xFF5D9ECC);
				listaArticulos = bd.getVentasRemisionporArticulo(fechaDesde, fechaHasta, "0");
				lvCategorias.setAdapter(new CategoriaResAdapter(this, R.layout.activity_item_categoria_res, listaArticulos));

				listaCategorias = bd.getCategoriasVerResultados();
				spinnerCategorias.setAdapter(new CategoriaSpinnerAdapter(this, R.layout.activity_item_categoria_spinner, listaCategorias));
				spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						lvCategorias.setAdapter(null);
						listaArticulos = bd.getVentasRemisionporArticulo(fechaDesde, fechaHasta, "" + listaCategorias.get(position).getIdCategoria());
						lvCategorias.setAdapter(new CategoriaResAdapter(VerResultadosActivity.this, R.layout.activity_item_categoria_res, listaArticulos));
						cargarTotales();
					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

			} else if (operacion == ABONOPRESTAMOS || operacion == PRESTAMOS) {
				rlVerResultados.setBackgroundColor(0xFFE0E0E0);
				listaLibros = bd.getMovimientosXFecha(this, fechaDesde, fechaHasta, false);
				listaArticulos = new ArrayList<Articulo>();
				libro = new Libro();
				libro.setListalibros(listaLibros);
				Articulo articulo = new Articulo();
				articulo.setNombre("ABONOS");
				articulo.setCantidadVentas(libro.getNAbonos());
				articulo.setValorVentas(libro.getValorAbonos());
				listaArticulos.add(articulo);
				Articulo articulo2 = new Articulo();
				articulo2.setNombre("PRESTAMOS");
				articulo2.setCantidadVentas(libro.getNPrestamos());
				articulo2.setValorVentas(libro.getValorPrestamos());
				listaArticulos.add(articulo2);
				lvCategorias.setAdapter(new CategoriaResAdapter(this, R.layout.activity_item_categoria_res, listaArticulos));
			} else {
				listaArticulos = bd.getVentasPedidosporArticulo(fechaDesde, fechaHasta, "0");
				lvCategorias.setAdapter(new CategoriaResAdapter(this, R.layout.activity_item_categoria_res, listaArticulos));

				listaCategorias = bd.getCategoriasVerResultados();
				spinnerCategorias.setAdapter(new CategoriaSpinnerAdapter(this, R.layout.activity_item_categoria_spinner, listaCategorias));
				spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						lvCategorias.setAdapter(null);
						listaArticulos = bd.getVentasPedidosporArticulo(fechaDesde, fechaHasta, "" + listaCategorias.get(position).getIdCategoria());
						lvCategorias.setAdapter(new CategoriaResAdapter(VerResultadosActivity.this, R.layout.activity_item_categoria_res, listaArticulos));
						cargarTotales();
					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

			}
			long clientesTotales = bd.getNumeroClientes(this, getDia(this.fecha.getDay()), usuario.cedula);
			long clientesEfectivos = bd.getNumeroClientesEfectivos(this, getDia(this.fecha.getDay()), usuario.cedula, fechaDesde);
			textViews[0].setText(Long.toString(clientesTotales));
			textViews[1].setText(Long.toString(clientesEfectivos));
			textViews[2].setText(Long.toString(clientesTotales - clientesEfectivos));


			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();


			try {
				c.setTime(dateFormat.parse(fechaDesde));
				c2.setTime(dateFormat.parse(fechaHasta));
				printFechaDesde = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
				printFechaHasta = c2.get(Calendar.DAY_OF_MONTH) + "/" + (c2.get(Calendar.MONTH) + 1) + "/" + c2.get(Calendar.YEAR);
				//printFechaDesde=fechaDesde;
				//printFechaHasta=fechaHasta;
			} catch (Exception e) {
				// TODO: handle exception
			}
			textViews[3].setText("De " + printFechaDesde + " a " + printFechaHasta);
			cargarTotales();
		}
		catch (Exception e)
		{
			mostrarMensaje(e.toString(),"l");
		}
	}

	private void cargarTotales()
	{

		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		textViews[4].setText(decimalFormat.format(getValorTotal()));
		textViews[5].setText(decimalFormat.format(getUnidadesVendidas()));
		textViews[6].setText(decimalFormat.format(getUnidadesTotal()));
	}

    
    private long getValorTotal()
    {
    	long res=0;
    	for (int i = 0; i < listaArticulos.size(); i++) {
			res+=listaArticulos.get(i).getValorVentas();
		}
    	return res;
    }
	private long getUnidadesVendidas()
	{
		long res=0;
		for (int i = 0; i < listaArticulos.size(); i++) {
			res+=listaArticulos.get(i).getUnidadDeMedidaVendidas();
		}
		return res;
	}
    private long getUnidadesTotal()
    {
    	long res=0;
    	for (int i = 0; i < listaArticulos.size(); i++) {
			res+=listaArticulos.get(i).getCantidadVentas();
		}
    	return res;
    }
    /**
     * metodo que se encarga de retornar las primeras tres letras de los dias de la semana
     * @param dia
     * @return respuesta
     */
    public String getDia(int dia)
	{
		String respuesta="";
		switch (dia)
		{
		case 0: respuesta="dom"; break;
		case 1: respuesta="lun"; break;
		case 2: respuesta="mar"; break;
		case 3: respuesta="mie"; break;
		case 4: respuesta="jue"; break;
		case 5: respuesta="vie"; break;
		case 6: respuesta="sab"; break;
		}
		return respuesta;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        getMenuInflater().inflate(R.menu.activity_ver_resultados, menu);
        return true;
    }

	/**
	 * metodo que se ejecuta al hacer click en los botones de la actividad
	 */
	public void onClick(View v) 
	{
		if(v.equals(btVolver))
		{
			this.finish();
		}	
		if(v.equals(btMenuVR))
		{
			this.openOptionsMenu();
		}			
	}
	/**
	 * metodo que se encarga de dar estilo al texto de las entiquetas de la actividad
	 * @param tv
	 */
	 public void getEstilo(TextView [] tv)
		{
			for (int i = 0; i < tv.length; i++) {
				if(i<5 ||i>12)
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
	  * metodo que se encarga de dar estilo al texto de los botones de la actividad
	  * @param tv
	  */
	 public void getEstilo(Button tv)
		{
			tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
		}
		private Drawable getImg( int res )
		{
			Drawable img = getResources().getDrawable( res );
			img.setBounds( 0, 0, 45, 45 );
			return img;
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
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.itemPrintDocument:		
				printInforme();
			    return true;			
			default:return super.onOptionsItemSelected(item);    	
	    	}  	
		}
		@SuppressLint("SimpleDateFormat")
		private void printInforme()
		{
			
	    		try
	    		{
	    			 PrintZebra pz=new PrintZebra(parametros.getMacAdd());	
	    		 pdu=ProgressDialog.show(VerResultadosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
	    		 datos=new ArrayList<String>();
	  			boolean resPrint=false;
	  			if(operacion==TRANSLADO)
		    	{
	  				datos.add("INFORME DE ARTICULOS TRASLADADOS");
	  			}
	  			else if(operacion==FACTURA)
		    	{
	  				datos.add("INFORME DE ARTICULOS FACTURADOS");
	  			}
				else if(operacion==REMISION)
				{
					datos.add("INFORME DE ARTICULOS COTIZADOS");
				}
	  			else if (operacion==PEDIDO)
	  			{
	  				datos.add("INFORME DE ARTICULOS PEDIDOS");	  				
		    	}
				else if (operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
				{
					datos.add("INFORME DE MOVIMIENTOS");
				}
	  			Date fecha=new Date();
	            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
	            datos.add(sdf.format(fecha));            
	            SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
	            datos.add(hora.format(fecha));	
	            datos.add(printFechaDesde);
	            datos.add(printFechaHasta);
	  			
	            if(operacion==TRANSLADO)
		    	{
	  				datos.add(bodegaOrigen);
	  				datos.add(bodegaDestino);
	  			}


					if(parametros.getUsaImpresoraZebra()==1 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==0) {
					resPrint = pz.printDocumentosRealizados(operacion, true, datos, null, null, null, listaArticulos,null);
					if (resPrint) {
						pdu.dismiss();
						mostrarMensaje("Impresion enviada Correctamente..", "l");
					} else {
						pdu.dismiss();
						mostrarMensaje("No fue posible Enviar la impresion", "l");
					}
				}
				else if(parametros.getUsaImpresoraZebra()==0 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==1)
				{
					try
					{
						pdu.dismiss();
						printBixolonsppr310();
					}catch(Exception e){
						mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
						mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
					}
				}
				else {
					pdu.dismiss();
					Context context =this;
					int deviceType = Print.DEVTYPE_BLUETOOTH;
					int enabled = Print.FALSE;
					int interval = 1000;

					try{
						//open
						if (printer==null) {
							printer =new Print(getApplicationContext());
						}



						if (printer!=null) {
							try {
								if(printer != null){
									printer.setStatusChangeEventCallback(this);
									printer.setBatteryStatusChangeEventCallback(this);
								}
								printer.openPrinter(deviceType, parametros.getMacAddEpson());
							}
							catch (Exception e)
							{
								//mostrarMensaje("3. "+e.toString(), "l");
								mostrarMensaje("No fue posible Enviar la impresion", "l");
							}

							Builder builder = null;
							try {
								builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
								PrintEpson printEpson=new PrintEpson();
								builder =printEpson.printDocumentosRealizados(builder,operacion, true, datos, null, null, null, listaArticulos,null);
								int[] status = new int[1];
								int[] battery = new int[1];
								printer.sendData(builder, SEND_TIMEOUT, status, battery);


								if(builder != null){
									try{
										builder.clearCommandBuffer();
										builder = null;
									}catch(Exception e){
										builder = null;
									}
								}

								printer.closePrinter();

							} catch (Exception e) {
								mostrarMensaje("No fue posible Enviar la impresion", "l");
								//mostrarMensaje("1. "+e.toString(), "l");
							}
						}
						else
						{

						}

						//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
					}catch(Exception e){
						//mostrarMensaje("2. "+e.toString(), "l");
						mostrarMensaje("No fue posible Enviar la impresion", "l");
					}
				}



	    		}
	    		catch (Exception e) {
	    			pdu.dismiss();
	             	mostrarMensaje("Imp "+e.toString(), "l");
				}//	
		}
	public void onStatusChangeEvent(final String deviceName, final int status) {
		;
	}
	public void onBatteryStatusChangeEvent(final String deviceName, final int battery) {
		;
	}
	private void printBixolonsppr310()
	{
		try {
			mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
			mBixolonPrinter.connect(parametros.getMacAddBixolon());

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
						PrintBixolon printBixolon=new PrintBixolon();
						printBixolon.printDocumentosRealizados(mBixolonPrinter,operacion, true, datos, null, null, null, listaArticulos,null,null);




					}
					break;
				case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
					mBixolonPrinter.disconnect();
					break;
			}
			return true;
		}
	});
}
