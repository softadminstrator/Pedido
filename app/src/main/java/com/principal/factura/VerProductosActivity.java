package com.principal.factura;

import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.Bodega;
import com.principal.mundo.LlamarStock;
import com.principal.mundo.LlamarStockSys;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.PutStock;
import com.principal.mundo.sysws.ItemPedido;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintDigitaPos;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;

/**
 * Clase que se encarga de mostrar la descripcion de los articulos a la hora de consultarlos
 * @author Javier
 *
 */
public class VerProductosActivity extends Activity implements OnClickListener, OnItemSelectedListener, OnKeyListener {
	

	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int INVENTARIO = 4;
	private final static int CONSULTAARTICULO=5;
	private final static int CARTERA=7;

	private final static int REMISION=12;




	EditText etAlertValor;
	int pos=0;
	String nombreArticulo;

	ProgressDialog pdu;
	LlamarStock llamarStock;
	LlamarStockSys llamarStockSys;
	PutStock putStock;
	boolean existe =false;
	private boolean keyback=false;

	
	
	public final static int SUCCESS_RETURN_CODE = 1;
	/**
	 * Atributo btConsultarProductos referente al boton de buscar el producto en la base de datos del telefono
	 */
	Button btConsultarProductos, btPrecioVer, btVolverP, btAlertOk, btAlertCancelar,btSelecArticulos, btMenuVP;
	/**
	 * Atributo etNombreArticulo referente a la caja de texto en donde se ingresara el nombre del producto a buscar
	 */
	EditText etNombreArticulo;
	/**
	 * Atributo listaArticulos referente a la lista de articulos que se visualizaran despues de realizarce la busqueda del articulo
	 */
	ListView listaArticulos;
	/**
	 * Atributo listaArt referente al arreglo de Articulos que se obtuvieron despues de haber realizado el filtro
	 */
	ArrayList<Articulo> listaArt=new ArrayList<Articulo>();
	
	private Articulo articulo;
	/**
	 * Atributo tvTitulo referente a la etiqueta del titulo de la actividad
	 */
	TextView tvTituloBodega, tvCodigoTitulo,tvOperacionConsultaArticulo,tvCantPedir;
	
	private int operacion;
	private Bodega bodegaOmision;
	RelativeLayout rlVerProductos;	
	TextView tvIngrese;
	Parametros parametrosPos,parametrosSys;
	creaBD bd=new creaBD(this);
	private int precioSelect;
//	private Opciones [] opciones; 
	EditText etNuevoStock;
	private String precioCliente;
	private String IdCliente;



	public static IMyBinder binder;
	public static boolean ISCONNECT;

	/**
	 * Atributo letraEstilo referencia a la clase LetraEstilo
	 */
	
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 */
    @Override
    public void onCreate(Bundle savedInstanceState)
    
    {    	
        super.onCreate(savedInstanceState);



		//bindService connection
		ServiceConnection conn= new ServiceConnection() {

			public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
				//Bind successfully
				binder= (IMyBinder) iBinder;
				Log.e("binder","connected");
			}


			public void onServiceDisconnected(ComponentName componentName) {
				Log.e("disbinder","disconnected");
			}
		};
		//variables para impresora digital pos
		//bind service?get ImyBinder object
		Intent intent=new Intent(this, PosprinterService.class);
		bindService(intent, conn, BIND_AUTO_CREATE);






        setContentView(R.layout.activity_ver_productos);
        rlVerProductos=(RelativeLayout)findViewById(R.id.rlVerProductos);
        
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        operacion=obtenerDatos.getInt("operacion"); 
        precioCliente= obtenerDatos.getString("precioCliente");
		IdCliente= obtenerDatos.getString("idCliente");

      
        
        
        btConsultarProductos=(Button)findViewById(R.id.btConsultarProductos);
        btConsultarProductos.setOnClickListener(this);

		btMenuVP=(Button)findViewById(R.id.btMenuVP);
		btMenuVP.setOnClickListener(this);

        
        btPrecioVer=(Button)findViewById(R.id.btPrecioVer);
        btPrecioVer.setOnClickListener(this);
        
        btVolverP=(Button)findViewById(R.id.btVolverP);
        btVolverP.setOnClickListener(this);

		btSelecArticulos=(Button)findViewById(R.id.btSelecArticulos);
		btSelecArticulos.setOnClickListener(this);

        
        etNombreArticulo=(EditText)findViewById(R.id.etNombreArticulos);
        listaArticulos= (ListView)findViewById(R.id.lvArticulosVP);
        
        tvTituloBodega=(TextView)findViewById(R.id.tvTituloBodega);
        tvIngrese=(TextView)findViewById(R.id.tvIngreseNombre);
        tvCodigoTitulo=(TextView)findViewById(R.id.tvCodigoTitulo);
        tvOperacionConsultaArticulo=(TextView)findViewById(R.id.tvOperacionConsultaArticulo);
		tvCantPedir=(TextView)findViewById(R.id.tvCantPedir);

    	bd.openDB();
    	parametrosPos=bd.getParametros(this,"P");
    	parametrosSys=bd.getParametros(this,"S");   
		bd.close();	
		/*if(parametrosPos.getConsultaArticuloEnLinea()==1)
		{
        	tvCodigoTitulo.setText("STOCK");
		}
		 */
		if (parametrosPos.getUsaSelecMultipleArt()==0)
		{
			tvCantPedir.setVisibility(View.GONE);
			btSelecArticulos.setVisibility(View.GONE);
		}
		else {
			tvCantPedir.setVisibility(View.VISIBLE);
			btSelecArticulos.setVisibility(View.VISIBLE);
		}
        
        etNombreArticulo.setOnKeyListener(this); 
        cargarPrecio();

		if (parametrosPos.getUsaSelecMultipleArt()==1) {
			listaArticulos.setOnItemLongClickListener(new OnItemLongClickListener() {
				/**
				 * metodo que se ejecuta al realizar click sostenido sobre un elemento de la lista de articulos
				 * del pedido
				 */
				public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
					if (operacion == FACTURA || operacion == PEDIDO || operacion == TRANSLADO || operacion==REMISION) {
						articulo = listaArt.get((int) position);
						modificarArticulo(articulo, view);
					}
					return false;
				}

			});
		}
		else {
			listaArticulos.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
					if (operacion == FACTURA || operacion == PEDIDO || operacion == TRANSLADO || operacion==REMISION) {
						articulo = listaArt.get((int) position);
						Intent i = new Intent();
						Bundle b = new Bundle();
						b.putLong("idArticulo", articulo.idArticulo);
						b.putString("idCodigo", articulo.idCodigo);
						b.putString("nombre", articulo.nombre);
						b.putLong("precio1", articulo.precio1);
						b.putLong("precio2", articulo.precio2);
						b.putLong("precio3", articulo.precio3);
						b.putLong("precio4", articulo.precio1);
						b.putLong("precio5", articulo.precio1);
						b.putLong("precio6", articulo.precio1);
						b.putLong("activo", articulo.activo);
						b.putDouble("stock", articulo.stock);
						b.putLong("iva", articulo.iva);
						i.putExtras(b);
						setResult(SUCCESS_RETURN_CODE, i);
						finish();
					}

				}
			});
		}

        
        if(operacion==TRANSLADO)
        {       	
        	rlVerProductos.setBackgroundColor(0xFFDBDB74);
        	tvOperacionConsultaArticulo.setText("TRANSLADO");		
        	bodegaOmision=bd.getBodega( obtenerDatos.getInt("idBodegaOrigen"));
        }
        else if(operacion==FACTURA)
         {
        	 rlVerProductos.setBackgroundColor(0xFFE0E0E0);
        	 tvOperacionConsultaArticulo.setText("FACTURA"); 
        	 bodegaOmision=bd.getBodega( parametrosPos.getBodegaFacturaOmision());
         }
		else if(operacion==REMISION)
		{
			rlVerProductos.setBackgroundColor(0xFF5D9ECC);
			tvOperacionConsultaArticulo.setText("COTIZACION");
			bodegaOmision=bd.getBodega( parametrosPos.getBodegaRemisionOmision());
		}
         else if(operacion==PEDIDO)
         {	        	 
             tvOperacionConsultaArticulo.setText("PEDIDO");	
             bodegaOmision=bd.getBodega( parametrosPos.getBodegaPedidosOmision());
         }
         else if(operacion==CONSULTAARTICULO)
         {	        	 
             tvOperacionConsultaArticulo.setText("CONSULTA ARTICULO");	
             bodegaOmision=bd.getBodega( parametrosPos.getBodegaPedidosOmision());
         }
         else if(operacion==INVENTARIO)
         {	        	 
        	 rlVerProductos.setBackgroundColor(0xFFD8D8D8);
        	 tvOperacionConsultaArticulo.setText("INVENTARIO");
        	 bodegaOmision=bd.getBodega( parametrosPos.getBodegaAdmInvOmision());
        	 if(parametrosPos.getModificaStock()==1)
        	 {
        		 listaArticulos.setOnItemLongClickListener(new OnItemLongClickListener() {
                  	public boolean onItemLongClick(AdapterView<?> arg0, View v,
                  			int position, long id) {
                  		 pos=position;
                  		 articulo = listaArt.get((int)position);
                  		 LinearLayout llMensaje=new LinearLayout(VerProductosActivity.this);
                  		 llMensaje.setOrientation(LinearLayout.VERTICAL);
                  		 TextView tvNombre=new TextView(VerProductosActivity.this);
                  		 tvNombre.setText("Articulo");
                  		 tvNombre.setTextSize(16);        	
                  		 TextView tvTextoNombre=new TextView(VerProductosActivity.this);
                  		 tvTextoNombre.setText(articulo.getNombre());
                  		 tvTextoNombre.setTextSize(20); 
                  		 TextView tvStock=new TextView(VerProductosActivity.this);
                  		 tvStock.setText("Stock Actual");
                  		 tvStock.setTextSize(16);   
                  		 TextView tvTextoStock=new TextView(VerProductosActivity.this);
                  		 tvTextoStock.setText(Double.toString(articulo.getStock()));
                  		 tvTextoStock.setTextSize(20); 
                  		 TextView tvStocknuevo=new TextView(VerProductosActivity.this);
                  		 tvStocknuevo.setText("Ingrese el nuevo Stock");
                  		 tvStocknuevo.setTextSize(16); 
                  		 etNuevoStock=new EditText(VerProductosActivity.this);
                  		 etNuevoStock.setInputType(InputType.TYPE_CLASS_NUMBER);
                  		 etNuevoStock.setTextSize(20);
                  		 llMensaje.addView(tvNombre);
                  		 llMensaje.addView(tvTextoNombre);
                  		 llMensaje.addView(tvStock);
                  		 llMensaje.addView(tvTextoStock);
                  		 llMensaje.addView(tvStocknuevo);
                  		 llMensaje.addView(etNuevoStock);
                  		 
               			AlertDialog.Builder builder = 
               					new AlertDialog.Builder(VerProductosActivity.this);
               			builder.setTitle("Datos Articulo");     
               		
               			builder.setView(llMensaje);
               
               			// Set up the buttons
               			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
               			    public void onClick(DialogInterface dialog, int which) {
               			        String var = etNuevoStock.getText().toString();
               			        if(validaStock(var))
               			        {
               			        	// ajusta inventario local
									bd.openDB();
									bd.UpdateStockArticulo(articulo.idArticulo,Double.parseDouble(var));
									bd.close();
               			        	// ajusta inventario en linea
               			        	//new putStock().execute("");
               			        	//pdu=ProgressDialog.show(VerProductosActivity.this,"Por Favor Espere", "Enviando nuevo Stock", true,false);
               			   		} 
               			       dialog.cancel();
               			    }				
               			});
               			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               			    public void onClick(DialogInterface dialog, int which) {
               			        dialog.cancel();
               			    }
               			});
               
               			builder.show();
                  		 
                  		return false;
                  	}
          		});
        	 }
         }  
         else if (operacion==CARTERA)
         {
        	 //METODOS PARA CONSULTAR LA CARTERA DE LOS CLIENTES
         }         
        tvTituloBodega.setText(bodegaOmision.getBodega());
		//btPrecioVer.setEnabled(parametrosPos.isModificaPrecio());
		if(IdCliente!=null) {
			btPrecioVer.setEnabled(parametrosPos.isModificaPrecio() && !IdCliente.equals("2897"));
		}
	}


	/**
	 * metodo que se ejecuta al seleccionar una de las opciones de menu
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
			case R.id.menuPrintInventario:
				printInforme();
				return true;
			default:return super.onOptionsItemSelected(item);
		}
	}






	private void modificarArticulo(final Articulo  articulo, final View view2)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Modificar");
		builder.setMessage("Ingrese la cantidad del articulo \n "+articulo.getNombre() );

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder.create();

		// modifica interfaz grafica
		final LayoutInflater inflater = getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.activity_input_text_value, null);
		test.setView(dialogView);

		TextView tvInput=(TextView)dialogView.findViewById(R.id.TVTextoInput);
		tvInput.setVisibility(View.GONE);
		etAlertValor = (EditText) dialogView.findViewById(R.id.etValorInput);
		btAlertCancelar = (Button) dialogView.findViewById(R.id.btCancelarInput);
		btAlertOk = (Button) dialogView.findViewById(R.id.btAceptarInput);
		//----------------------------------------------------

		if(parametrosPos.getUsaCantDecimal()==1) {
			etAlertValor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			etAlertValor.setText(""+articulo.getCantPedir());
			etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		else
		{
			etAlertValor.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
			etAlertValor.setText(""+((long)articulo.getCantPedir()));
			etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		etAlertValor.selectAll();


//		        final AlertDialog test = builder.create();
		btAlertCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				test.cancel();
			}
		});
		btAlertOk.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				if(etAlertValor.getText().toString().length()>0)
				{
					try {
						double value = Double.parseDouble(etAlertValor.getText().toString());
						//if (value >= 0) {
						test.cancel();

						if (operacion==PEDIDO & parametrosPos.getPermiteStocken0EnPedido() == 0 & value > articulo.stock)
						{
							mostrarMensaje("El Articulo seleccionado esta Agotado", "l");
						}
						else if (parametrosPos.getConsultaArticuloEnLinea() == 1 & parametrosPos.getPermiteStocken0() == 0 & value > articulo.stock & operacion!=PEDIDO						) {
							mostrarMensaje("El Articulo seleccionado esta Agotado", "l");
						}
						else
						{


								articulo.setCantPedir(value);
								TextView tvCantPedir = (TextView) view2.findViewById(R.id.tvCantPedir);
								LinearLayout llVerArticulo = (LinearLayout) view2.findViewById(R.id.llVerArticulo);

								if (parametrosPos.getUsaCantDecimal() == 1) {
									tvCantPedir.setText("" + articulo.getCantPedir());
								} else {

									tvCantPedir.setText("" + ((long) articulo.getCantPedir()));
								}


								if (articulo.getCantPedir() > 0) {
									llVerArticulo.setBackgroundColor(Color.parseColor("#7F92FF"));

								} else {
									llVerArticulo.setBackgroundColor(Color.parseColor("#FFFFFF"));
								}
								etNombreArticulo.clearFocus();
								InputMethodManager iim = (InputMethodManager)
										VerProductosActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
								iim.hideSoftInputFromWindow(etAlertValor.getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
								iim.hideSoftInputFromWindow(etNombreArticulo.getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
						}

						//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						//imm.hideSoftInputFromWindow(etAlertValor.getWindowToken(), 0);
						//etNombreArticulo.requestFocus();
					} catch (Exception e)
					{
						mostrarMensaje("Formato numerico incorrecto", "l");
						etAlertValor.selectAll();
						etAlertValor.setFocusable(true);
					}

				}
				else
				{
					mostrarMensaje("Debe ingresar la nueva cantidad del articulo","l" );
					etAlertValor.selectAll();
					etAlertValor.setFocusable(true);

				}
			}
		});
		test.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etAlertValor, InputMethodManager.SHOW_IMPLICIT);
		etAlertValor.requestFocus();
		test.show();

	}
	private String getTextSpace(String text)
	{
		String res="";
		for (int i = 0; i < text.length(); i++) {
			res=res+text.charAt(i);
			if(i>32)
			{
				res=res+"\n";
			}
		}
		return res;
	}


    private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ver_productos, menu);
        return true;
    }
    /**
     * metodo que se ejecuta al hacer click en los botones de la actividad
     */
	public void onClick(View v)
	{
		if(v.equals(btConsultarProductos))
			{
				if(parametrosSys.getDescuentaStockEnPedido()==0) {
					if (validaNombre()) {
						String nombre = etNombreArticulo.getText().toString();
						if (parametrosPos.getConsultaArticuloEnLinea() == 0) {
							bd.openDB();
							listaArt = bd.getConsultaProducto(this, nombre);
							bd.close();
							mostrarArticulos();
						} else {
							new getStock().execute("");
							pdu = ProgressDialog.show(this, "Por Favor Espere", "Obteniendo datos", true, false);
						}

					}
				}
				else
				{
					String nombre = etNombreArticulo.getText().toString();
					bd.openDB();
					listaArt = bd.getConsultaProducto(this, nombre);
					bd.close();
					mostrarArticulos();
				}
			}
		else if(v.equals(btPrecioVer))
		{
			cambiarPrecio();
			if(listaArt!=null)
			{
				if(listaArt.size()>0)
				{
					mostrarArticulos();
				}	
			}
			
		    			    				
		}
		else if(v.equals(btMenuVP))
		{
			openOptionsMenu();
		}
		else if(v.equals(btVolverP))
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
				imm.hideSoftInputFromWindow(etNombreArticulo.getWindowToken(), 0);
			    keyback=true;
			}	
		}
		else if(v.equals(etNombreArticulo))
		{
		   keyback=false;
		}
		else if(v.equals(btSelecArticulos))
		{
			guardaArticulosSeleccionados();
		}

		
	}

	private void guardaArticulosSeleccionados()
	{
		if (operacion == FACTURA || operacion == PEDIDO || operacion == TRANSLADO || operacion==REMISION) {
			Intent i = new Intent();
			ArrayList<Articulo> listSelec =new ArrayList<Articulo>();
			for (int j = 0; j <listaArt.size() ; j++) {
				if(listaArt.get(j).cantPedir>0)
				{
					listaArt.get(j).setTipoPrecio(precioSelect );
					listSelec.add(listaArt.get(j));
				}
			}
			i.putParcelableArrayListExtra("listaSelec",listSelec);

			setResult(SUCCESS_RETURN_CODE, i);
			finish();
		}
	}

	/**
	 * metodo encargado de mostrar los articulos en la lista
	 */
	public void mostrarArticulos()
	{		
		listaArticulos.setAdapter(new VerProductosAdapter(this, 0,listaArt, precioSelect,parametrosPos.getConsultaArticuloEnLinea(),parametrosPos));
		etNombreArticulo.selectAll();
	    etNombreArticulo.requestFocus();
	}
	/**
	 * metodo que se encarga de mostrar mensaje al usuario
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
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub		
	}
	/**
	 * metodo que se ejecuta al hacer click en cualquiera de las teclas a la hora de ingresar el nombre del 
	 * articulo a buscar
	 */
	public boolean onKey(View v, int kc, KeyEvent ke) {

		
		if (v.equals(etNombreArticulo))
		{
           if(kc==ke.KEYCODE_ENTER)
           {
        	   if(existe)
				{
		        	
						if(validaNombre())
						{
								String nombre=etNombreArticulo.getText().toString();
								if(parametrosPos.getConsultaArticuloEnLinea()==0)
								{			    						
									bd.openDB();
									listaArt=bd.getConsultaProducto(this, nombre);
									bd.close();						            
									mostrarArticulos();
								}
								else
								{
									new getStock().execute("");
									pdu=ProgressDialog.show(this,"Por Favor Espere", "Obteniendo datos", true,false);
								   existe = false;
				        		   return true;
								}
					    		
						}  
				}
        	   else
        	   {
        		   existe = true;
        		   return true;
        	   }
           }   
                
        }
	return false;		
	}
	private void cargarPrecio()
	{
		if(precioCliente!=null)
		{
			btPrecioVer.setText("Precio "+precioCliente);
			precioSelect=Integer.parseInt(precioCliente);
		}
		else if(parametrosPos.isPrecio123())
		{
			btPrecioVer.setText("Precio 3");
			precioSelect=3;
		}
		else
		{
			btPrecioVer.setText("Precio 5");
			precioSelect=5;
		}
			
	}
	private void cambiarPrecio()
	{
		if(parametrosPos.isPrecio123())
		{
			if(parametrosPos.getConsultaCosto()==0) {
				if (btPrecioVer.getText().toString().equals("Precio 1")) {
					mostrarMensaje("Precio 3 Seleccionado", "s");
					btPrecioVer.setText("Precio 3");
					precioSelect = 3;

				} else if (btPrecioVer.getText().toString().equals("Precio 2")) {
					mostrarMensaje("Precio 1 Seleccionado  ", "s");
					btPrecioVer.setText("Precio 1");
					precioSelect = 1;
				} else if (btPrecioVer.getText().toString().equals("Precio 3")) {
					mostrarMensaje("Precio 2 Seleccionado  ", "s");
					btPrecioVer.setText("Precio 2");
					precioSelect = 2;
				}
			}
			else if(parametrosPos.getConsultaCosto()==1 )
			{
					if (btPrecioVer.getText().toString().equals("Precio 1")) {
						mostrarMensaje("Precio 3 Seleccionado", "s");
						btPrecioVer.setText("Precio 3");
						precioSelect = 3;
					} else if (btPrecioVer.getText().toString().equals("Precio 2")) {
						mostrarMensaje("Precio 1 Seleccionado  ", "s");
						btPrecioVer.setText("Precio 1");
						precioSelect = 1;
					}
					else if(operacion==CONSULTAARTICULO) {
					 if (btPrecioVer.getText().toString().equals("Precio 3")) {
							mostrarMensaje("Precio Costo Seleccionado  ", "s");
							btPrecioVer.setText("Costo");
							precioSelect = 0;
						} else if (btPrecioVer.getText().toString().equals("Costo")) {
							mostrarMensaje("Precio 2 Seleccionado  ", "s");
							btPrecioVer.setText("Precio 2");
							precioSelect = 2;
						}
					}
					else
					{
						 if (btPrecioVer.getText().toString().equals("Precio 3")) {
						mostrarMensaje("Precio 2 Seleccionado  ", "s");
						btPrecioVer.setText("Precio 2");
						precioSelect = 2;
					}
				}
			}

		}
		else
		{
			if(btPrecioVer.getText().toString().equals("Precio 1"))
			{
				mostrarMensaje("Precio 2 Seleccionado", "s");
				btPrecioVer.setText("Precio 2");
				precioSelect=2;
			}
			else if(btPrecioVer.getText().toString().equals("Precio 2"))
			{
				mostrarMensaje("Precio 3 Seleccionado  ", "s");
				btPrecioVer.setText("Precio 3");
				precioSelect=3;
			}
			else if(btPrecioVer.getText().toString().equals("Precio 3"))
			{
				mostrarMensaje("Precio 4 Seleccionado  ", "s");
				btPrecioVer.setText("Precio 4");
				precioSelect=4;
			}
			else if(btPrecioVer.getText().toString().equals("Precio 4"))
			{
				mostrarMensaje("Precio 5 Seleccionado", "s");
				btPrecioVer.setText("Precio 5");
				precioSelect=5;
			}
			else if(btPrecioVer.getText().toString().equals("Precio 5"))
			{
				mostrarMensaje("Precio 6 Seleccionado  ", "s");				
				btPrecioVer.setText("Precio 6");
				precioSelect=6;
			}

			else  if (btPrecioVer.getText().toString().equals("Precio 6")) {
                 if(parametrosPos.getConsultaCosto()==1 )
                 {
                     mostrarMensaje("Precio Costo Seleccionado  ", "s");
                     btPrecioVer.setText("Costo");
                     precioSelect = 0;
                 }
                 else {
                     mostrarMensaje("Precio 1 Seleccionado  ", "s");
                     btPrecioVer.setText("Precio 1");
                     precioSelect = 1;
                 }

            }
            else if (btPrecioVer.getText().toString().equals("Costo")) {
                mostrarMensaje("Precio 1 Seleccionado  ", "s");
                btPrecioVer.setText("Precio 1");
                precioSelect = 1;
            }
		}
	}
	private class getStock extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{	
			if(parametrosPos.isValue(parametrosPos.getUsaStarlapWS()))
			{
			
				llamarStock=new LlamarStock(parametrosSys.getIp(),parametrosSys);
				listaArt=llamarStock.getLlamarStock("nom", nombreArticulo, bodegaOmision.getIdBodega());
				res=llamarStock.getRes();
				return 1;	
			}
			else
			{
				llamarStockSys=new LlamarStockSys(parametrosSys);
				listaArt=llamarStockSys.getArticulos("nom", ""+bodegaOmision.getIdBodega(), nombreArticulo, listaArt);
				res=llamarStockSys.getResultado();
			}	
			return 1;
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("true"))
				{
					if(listaArt!=null)
					{
						mostrarArticulos();
					}
				}
				else if(res.equals("false"))
				{
					
					 mostrarMensaje("No existen articulos que contengan esa palabra","l");		        		   
				}
				else
				{
				
					mostrarMensaje("No fue posible establecer la conexion con el servidor", "l");
					
				}
			 }
			}
	
	private class putStock extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{						
				putStock=new PutStock(parametrosPos.getIp());
				res=putStock.putStock(articulo.getIdArticulo(),bodegaOmision.getIdBodega(),articulo.getStockint());
				return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("true"))
				{
					listaArt.get(pos).setStock(articulo.getStock());
					mostrarArticulos();
					mostrarMensaje("Stock enviado correctamente", "l");
					
				}
				else if(res.equals("false"))
				{
					mostrarMensaje("No fue posible enviar el nuevo stock del producto", "l");
					
				}
				else
				{
					mostrarMensaje("No fue posible establecer la conexion con el servidor", "l");
					
				}
			 }
			}

	private boolean validaNombre() {
								
				nombreArticulo=etNombreArticulo.getText().toString();
				return true;
				/**if(nombreArticulo.length()<3)
				{
					mostrarMensaje("Debe ingresar minimo 3 letras del nombre", "l");
					return false;					
				}
				else if(nombreArticulo.equals(""))
				{
					mostrarMensaje("Debe ingresar el nombre del articulo", "l");
					return false;
				}
				else
				{
					return true;
				}
				 **/
	}
	private boolean validaStock(String var) {
		if(var.equals(""))
		{
			mostrarMensaje("Debe ingresar el nuevo stock del producto", "l");
			return false;
		}
		else
		{
			try
			{
				articulo.setStock(Double.parseDouble(var));
				return true;
			}
			catch (Exception e) {
				mostrarMensaje("El valor de Stock es incorrecto", "l");	
				etNuevoStock.requestFocus();
				return false;
			}
		}	
	}
	private void printInforme()
	{
		try
		{
			if(listaArt.size()>0) {
				printDigitalPos810();
			}
			else
			{
				mostrarMensaje("No se encuentra informacion de articulos en la lista", "l");
			}
		}catch(Exception e){
			mostrarMensaje("No fue posible Enviar la impresion", "l");
			mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
		}
	}
	private void printDigitalPos810(){
		String bleAdrress=parametrosPos.getMacAddDigitalPos();
		binder.connectBtPort(bleAdrress, new UiExecute() {
			public void onsucess() {
				ISCONNECT = true;
				PrintDigitaPos printDigitaPos = new PrintDigitaPos();
				printDigitaPos.printInventario(binder,listaArt, parametrosPos);

				binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), new UiExecute() {

					public void onsucess() {
						binder.acceptdatafromprinter(new UiExecute() {

							public void onsucess() {
							}


							public void onfailed() {
								ISCONNECT=false;
							}
						});
					}


					public void onfailed() {
						ISCONNECT=false;
					}
				});


			}

			public void onfailed() {
				mostrarMensaje("desconectado impresora", "l");
			}
		});
	}
}
