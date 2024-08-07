package com.principal.factura;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.Print;
import com.principal.mundo.sysws.ClienteSucursal;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;
import com.principal.mundo.sysws.PutVisitasCliente;
import com.principal.mundo.sysws.VisitaCliente;
import com.principal.persistencia.creaBD;
import com.principal.mundo.*;
import com.principal.mundo.sysws.GetClientexVendedor;
import com.principal.print.PrintBixolon;
import com.principal.print.PrintDigitaPos;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;

/**
 * Clase en la que se muestran los clientes asignados para cada uno de los dias de la semana
 * y muestra los clientes dependiendo del dia de la semana que se seleccione
 * @author Javier
 *
 */
public class RuteroActivity extends Activity implements OnClickListener, OnItemSelectedListener, OnKeyListener, Runnable {
	protected static final int SUB_ACTIVITY_CONSULTA_CLIENTES = 400;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int PRESTAMOS=8;
	private final static int ABONOPRESTAMOS=9;
	private final static int REMISION=12;


	public static IMyBinder binder;
	public static boolean ISCONNECT;


	static final int SEND_TIMEOUT = 10 * 1000;
	private Print printer;
	static BixolonPrinter mBixolonPrinter;

	private String operacionBixolon;
	private String operacionDigitalPos;
	private Municipio municipio;

	PagoPrestamo pagoPrestamo=new PagoPrestamo();
	Prestamo prestamo =new Prestamo();
	Libro libro =new Libro();
	private View viewCliente;
	private ItemMunicipioAdapter itemMunicipioAdapter;
	ArrayList<Municipio> listaMunicipios;





	private boolean keyback=false;
	/**
	 * implementa radio buton para la busqueda por nombre o por nit
	 */
	private RadioButton  rbFiltroNombre,rbFiltroNit;



	/**
	 * Atributo tvEtiquetas que guarda las etiquetas que se muestran en la actividad
	 */
	TextView [] tvEtiquetas = new TextView[9];	
	/**
	 * Atributos btDias, btBuscar, btVerTodos que hace referencia a los botones que se encuentran 
	 * en la actividad
	 */
	Button  btBuscar, btVerTodos,btMenuR,btVolverR;
	/**
	 * Atributo listaCategorias hace referencia a la lista en donde se mostraran los clientes de visita asignados
	 */
	ListView listaCategorias;
	/**
	 * Atributo context hace referencia a la clase Context de la actividad
	 */
	Context context;
	/**
	 * Atributo etNombreCliente hace referencia a la caja de texto donde se digita el nombre del cliente a buscar
	 */
	EditText etNombreCliente;
	Spinner SpMunicipioCliente;
	/**
	 * Atributo bd referencia de la clase creaDB
	 */
	creaBD bd;
	/**
	 * Atributo clietnesys hace referencia a la clase Cliente utilizada para enviar informacion al sistema de georeferenciacion
	 */
	com.principal.mundo.sysws.Cliente clientesys;
	/**
	 * Atributo pdu referente al ProgressDialog que se mostrara al usuario en el momento de actualizar los clietnes del sistema
	 * o registrar la visita del cliente
	 */
	private ProgressDialog pdu;
	/**
	 * Atributos parametrosPos, parametrosSys referencia a la clase Parametros
	 */
	Parametros parametrosPos, parametrosSys;
	/**
	 * Atributos opciones,opcionesCliente,opcionesVisita referencia a la clase Opciones utilizados en las opciones de los dialogos
	 */
	Opciones [] opciones,opcionesCliente,opcionesVisita;
	/**
	 * Atributo locManager referencia a la clase Location Manager
	 */
	private LocationManager locManager;
	/**
	 * Atributo locListener referencia a la clase My LocationListener
	 */
	private MyLocationListener locListener;	
	/**
	 * Atributo currentLocation referente a la clase Location
	 */
	Location currentLocation = null;
	/**
	 * Atributo cal referente a la clase Calendar utilizado para obtener la fecha del sistema
	 */
	private Calendar cal = Calendar.getInstance();	
	/**
	 * Atributo sdfecha referente a la clase SimpleDateFormat utilizado para dar formato a la fecha del sistema
	 */
	private SimpleDateFormat sdfecha;
	/**
	 * Atributo fechaVisita utilizado para guardar la fecha actual del sistema
	 */
	String fechaVisita="";
	/**
	 * Atributo dia utilizado para guardar el numer de dia de la semana
	 */

	/**
	 * Atributo fecha utilizado para guardar la fecha actual del sistema
	 */
	Date fecha=new Date();
	/**
	 * Atributo cli arreglo donde se almacena la lista de clientes que se visualizara en el rutero
	 * dependiendo el dia seleccionado
	 */
	ArrayList<Cliente> cli=new ArrayList<Cliente>();
	/**
	 * Atributo letraEstilo referente a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	/**
	 * Atributo usuario referente a la clase Usuario	
	 */
	Usuario usuario;
	/**
	 * Atributo cliente referente a la clase Cliente
	 */
	Cliente cliente;


	ArrayList<ClienteSucursal> listaClienteSucursal=new ArrayList<ClienteSucursal>();

	ClienteSucursal clienteSucursal;


	
	private int operacion;
	
	private RelativeLayout rlRutero;
	LlamarClientes llamarClientes;
	GetClientexVendedor clientexVendedor;
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);		
		
	}
	/**
	 * metodo donde se asignan valores a los atributos de la clase
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	try {

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
			//bind service，get ImyBinder object
			Intent intent=new Intent(this, PosprinterService.class);
			bindService(intent, conn, BIND_AUTO_CREATE);



			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_rutero);
			letraEstilo = new LetraEstilo();
			usuario = new Usuario();
			cliente = new Cliente();

			opcionesVisita = new Opciones[8];
			listaMunicipios = new ArrayList<Municipio>();
			Bundle obtenerDatos = new Bundle();
			obtenerDatos = this.getIntent().getExtras();
			usuario.cedula = obtenerDatos.getString("cedula");
			operacion = obtenerDatos.getInt("operacion");
			rlRutero = (RelativeLayout) findViewById(R.id.rlRutero);


			btBuscar = (Button) findViewById(R.id.btConsultarCliente);
			btVerTodos = (Button) findViewById(R.id.btVerTodos);
			etNombreCliente = (EditText) findViewById(R.id.etNombreCliente);
			etNombreCliente.setOnClickListener(this);
			btMenuR = (Button) findViewById(R.id.btMenuR);
			btMenuR.setOnClickListener(this);
			btVolverR = (Button) findViewById(R.id.btVolverR);
			btVolverR.setOnClickListener(this);

			rbFiltroNombre = (RadioButton) findViewById(R.id.rbFiltroNombre);
			rbFiltroNombre.setOnClickListener(this);
			rbFiltroNit = (RadioButton) findViewById(R.id.rbFiltroNit);
			rbFiltroNit.setOnClickListener(this);

			SpMunicipioCliente = (Spinner) findViewById(R.id.SpMunicipioCliente);
			rbFiltroNombre.setChecked(true);

			btVerTodos.requestFocus();

			listaCategorias = (ListView) findViewById(R.id.lvCategorias);
			tvEtiquetas[0] = (TextView) findViewById(R.id.tvTotalCl);
			tvEtiquetas[1] = (TextView) findViewById(R.id.tvEfectivosCL);
			tvEtiquetas[2] = (TextView) findViewById(R.id.tvPendientesCL);
			tvEtiquetas[3] = (TextView) findViewById(R.id.tvTotalValor);
			tvEtiquetas[4] = (TextView) findViewById(R.id.tvEfectivosValor);
			tvEtiquetas[5] = (TextView) findViewById(R.id.tvPendientesValor);
			tvEtiquetas[6] = (TextView) findViewById(R.id.tvRutaR);
			tvEtiquetas[7] = (TextView) findViewById(R.id.tvRutaRN);
			tvEtiquetas[8] = (TextView) findViewById(R.id.tvOperacion);
			tvEtiquetas[7].setText(usuario.cedula);
			tvEtiquetas[8].setText("PEDIDO");
			if (operacion == FACTURA) {
				rlRutero.setBackgroundColor(0xFFE0E0E0);
				tvEtiquetas[8].setText("FACTURA");
			}

			if (operacion == REMISION) {
				rlRutero.setBackgroundColor(0xFF5D9ECC);
				tvEtiquetas[8].setText("COTIZACION");
			}

			opciones = new Opciones[7];
			opciones[0] = new Opciones("Domingo", getImg(R.drawable.clima), "Domingo");
			opciones[1] = new Opciones("Lunes", getImg(R.drawable.clima), "Lunes");
			opciones[2] = new Opciones("Martes", getImg(R.drawable.clima), "Martes");
			opciones[3] = new Opciones("Miercoles", getImg(R.drawable.clima), "Miercoles");
			opciones[4] = new Opciones("Jueves", getImg(R.drawable.clima), "Jueves");
			opciones[5] = new Opciones("Viernes", getImg(R.drawable.clima), "Viernes");
			opciones[6] = new Opciones("Sabado", getImg(R.drawable.clima), "Sabado");

			if (operacion == PRESTAMOS) {

				opcionesCliente = new Opciones[1];
				opcionesCliente[0] = new Opciones("!Nuevo Prestamo!", getImg(R.drawable.agregar), "!Nuevo Prestamo!");
				// opcionesCliente[3]=new Opciones("Registrar Autoventa", getImg(R.drawable.caja), "Ingresar Autoventa");

			} else {
				opcionesCliente = new Opciones[4];
				opcionesCliente[0] = new Opciones("Crear Pedido", getImg(R.drawable.pedidos), "Crear Pedido");
				// opcionesCliente[1]=new Opciones("Registrar Visita", getImg(R.drawable.bandera), "Ingresar Visita");
				opcionesCliente[1] = new Opciones("Ver Informacion", getImg(R.drawable.consultar), "Ver Informacion");
				opcionesCliente[2] = new Opciones("Registrar Autoventa", getImg(R.drawable.caja), "Ingresar Autoventa");
				opcionesCliente[3] = new Opciones("Crear Cliente", getImg(R.drawable.agregar), "Crear Cliente");

			}

			//opcionesCliente[0]=new Opciones("Crear Pedido", getImg(R.drawable.pedidos), "Crear Pedido");
			// opcionesCliente[1]=new Opciones("Registrar Visita", getImg(R.drawable.bandera), "Ingresar Visita");
			// opcionesCliente[1]=new Opciones("Ver Informacion", getImg(R.drawable.consultar), "Ver Informacion");
			// opcionesCliente[3]=new Opciones("Registrar Autoventa", getImg(R.drawable.caja), "Ingresar Autoventa");

			opcionesVisita[0] = new Opciones("Cerrado", getImg(R.drawable.check), "Cerrado");
			opcionesVisita[1] = new Opciones("No hay dinero", getImg(R.drawable.check), "No hay dinero");
			opcionesVisita[2] = new Opciones("No existe", getImg(R.drawable.check), "No existe");
			opcionesVisita[3] = new Opciones("Alto inventario", getImg(R.drawable.check), "Alto inventario");
			opcionesVisita[4] = new Opciones("El propietario no esta", getImg(R.drawable.check), "El propietario no esta");
			opcionesVisita[5] = new Opciones("Exclusivo", getImg(R.drawable.check), "Exclusivo");
			opcionesVisita[6] = new Opciones("No compro", getImg(R.drawable.check), "No compro");
			opcionesVisita[7] = new Opciones("Repetido", getImg(R.drawable.check), "Repetido");




			bd = new creaBD(this);
			bd.openDB();
			listaMunicipios = bd.getMunicipiosClientes(this, usuario.cedula);
			municipio=listaMunicipios.get(0);
			bd.close();
			cargarListaMunicipios();
			cargarClientes(municipio.getMunicipio(), false, rbFiltroNit.isChecked());

			btBuscar.setOnClickListener(this);
			btVerTodos.setOnClickListener(this);
			etNombreCliente.setOnKeyListener(this);

			listaCategorias.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View view01, int position, long id) {

					cliente = cli.get((int) position);
					if (operacion == PRESTAMOS) {
						viewCliente = view01;

						Intent intent = new Intent(RuteroActivity.this, AbonoPrestamos.class);
						intent.putExtra("nombreCliente", cliente.nombre);
						intent.putExtra("deudaAntFac", cliente.deudaAntFac);
						intent.putExtra("idCliente", cliente.idCliente);
						intent.putExtra("DiasGracia", "" + cliente.getDias());
						intent.putExtra("Nit", cliente.nit);
						intent.putExtra("cedula", usuario.cedula);
						intent.putExtra("operacion", ABONOPRESTAMOS);
						intent.putExtra("idClienteSucursal", 0);
						startActivityForResult(intent, SUB_ACTIVITY_CONSULTA_CLIENTES);
					} else {
						//obtiene sucursales del cliente ------------------------------------------
						listaClienteSucursal = new ArrayList<ClienteSucursal>();
						//Muestra dialogo para seleccionar sucursales en caso de que tenga mas de una sucursal
						listaClienteSucursal = bd.getClienteSucursales(RuteroActivity.this, cliente);
						if (listaClienteSucursal.size() > 1) {
							SeleccionaClienteSucursal();
						} else {
							cliente.idClienteSucursal = 0;
							//Guarda linea anterior forma de tomar pedidos
							Intent intent = new Intent(RuteroActivity.this, CrearPedidoActivity.class);
							//Intent intent = new Intent(RuteroActivity.this, ListaArticulosPedidoActivity.class);
							intent.putExtra("nombre", cliente.nombre);
							intent.putExtra("direccion", cliente.direccion);
							intent.putExtra("ordenVisita", cliente.ordenVisita);
							intent.putExtra("idCliente", cliente.idCliente);
							intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
							intent.putExtra("cedula", usuario.cedula);
							intent.putExtra("consulta", false);
							intent.putExtra("ubicado", cliente.ubicado);
							intent.putExtra("idCodigoExterno", 0);
							intent.putExtra("idCodigoInterno", 0);
							intent.putExtra("operacion", operacion);
							intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
							startActivity(intent);
							finish();
						}
						//------------------------------------------------------------------------


					}
				}
			});


			listaCategorias.setOnItemLongClickListener(new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView parent, View view01, final int position, long id) {

					ListAdapter listaOpciones = new OpcionesAdapter(RuteroActivity.this, opcionesCliente);

					AlertDialog.Builder builder = new AlertDialog.Builder(RuteroActivity.this);
					builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
					cliente = cli.get((int) position);
					viewCliente = view01;

					builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
								//  			      		builder.setSingleChoiceItems(opcionesEnviado, -1, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int item) {

									cliente = cli.get((int) position);

									if (item == 0) {
										if (operacion == PRESTAMOS) {


											Intent intent = new Intent(RuteroActivity.this, AbonoPrestamos.class);
											intent.putExtra("nombreCliente", cliente.nombre);
											intent.putExtra("deudaAntFac", cliente.deudaAntFac);
											intent.putExtra("idCliente", cliente.idCliente);
											intent.putExtra("cedula", usuario.cedula);
											intent.putExtra("operacion", PRESTAMOS);
											intent.putExtra("idClienteSucursal", 0);
											startActivityForResult(intent, SUB_ACTIVITY_CONSULTA_CLIENTES);

										} else {
											//obtiene sucursales del cliente ------------------------------------------
											listaClienteSucursal = new ArrayList<ClienteSucursal>();
											//Muestra dialogo para seleccionar sucursales en caso de que tenga mas de una sucursal
											listaClienteSucursal = bd.getClienteSucursales(RuteroActivity.this, cliente);
											if (listaClienteSucursal.size() > 1) {
												SeleccionaClienteSucursal();
											} else {
												cliente.idClienteSucursal = 0;
												//Guarda linea anterior forma de tomar pedidos
												Intent intent = new Intent(RuteroActivity.this, CrearPedidoActivity.class);
												//Intent intent = new Intent(RuteroActivity.this, ListaArticulosPedidoActivity.class);
												intent.putExtra("nombre", cliente.nombre);
												intent.putExtra("direccion", cliente.direccion);
												intent.putExtra("ordenVisita", cliente.ordenVisita);
												intent.putExtra("idCliente", cliente.idCliente);
												intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
												intent.putExtra("cedula", usuario.cedula);
												intent.putExtra("consulta", false);
												intent.putExtra("ubicado", cliente.ubicado);
												intent.putExtra("idCodigoExterno", 0);
												intent.putExtra("idCodigoInterno", 0);
												intent.putExtra("operacion", operacion);
												intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
												startActivity(intent);
												finish();
											}
											//------------------------------------------------------------------------
										}

									} else if (item == 1) {

										Intent intent = new Intent(RuteroActivity.this, DatosClienteActivity.class);
										intent.putExtra("idCliente", ""+cliente.idCliente);
										intent.putExtra("cedula", usuario.cedula);
										startActivity(intent);

									} else if (item == 2) {
										ListAdapter listaMotivos = new OpcionesAdapter(RuteroActivity.this, opcionesVisita);
										AlertDialog.Builder builderMotivo = new AlertDialog.Builder(RuteroActivity.this);
										builderMotivo.setTitle(letraEstilo.getEstiloTitulo("Motivo"));
										builderMotivo.setSingleChoiceItems(listaMotivos, -1, new DialogInterface.OnClickListener() {
													//
													public void onClick(DialogInterface dialogMotivo, int itemMotivo) {
														String observacion = "";
														switch (itemMotivo) {
															case 0:
																observacion = "Cerrado";
																break;
															case 1:
																observacion = "No hay dinero";
																break;
															case 2:
																observacion = "No existe";
																break;
															case 3:
																observacion = "Alto inventario";
																break;
															case 4:
																observacion = "El propietario no esta";
																break;
															case 5:
																observacion = "Exclusivo";
																break;
															case 6:
																observacion = "No compro";
																break;
															case 7:
																observacion = "Repetido";
																break;
															default:
																break;
														}
														dialogMotivo.cancel();

														cliente.setMotivoUltimaVisita(observacion);
														SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
														cliente.setFechaUltimaVisita(sdf.format(fecha));
														bd.ActualizarCliente(cliente, true, false);//
														cargarClientes(municipio.getMunicipio(), false, rbFiltroNit.isChecked());
													}
												}
										);
										AlertDialog alert = builderMotivo.create();
										alert.show();
									}
									else if (item == 3) {
										Intent intent = new Intent(RuteroActivity.this, DatosClienteActivity.class);
										intent.putExtra("idCliente", ""+0 );
										intent.putExtra("cedula", usuario.cedula);
										startActivity(intent);
									}

									dialog.cancel();
								}

							}
					);
					AlertDialog alert = builder.create();
					alert.show();

					return true;
				}
			});


			SpMunicipioCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					municipio=listaMunicipios.get(position);
					//Carga clientes del municipio
					cargarClientes(municipio.getMunicipio(), false, rbFiltroNit.isChecked());
				}

				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}
    	catch (Exception e)
		{
			mostrarMensaje(e.toString(), "l");
		}

        
    }

	private void cargarListaMunicipios()
	{

		itemMunicipioAdapter=new  ItemMunicipioAdapter(this, 0,listaMunicipios);
		SpMunicipioCliente .setAdapter(itemMunicipioAdapter);
		SpMunicipioCliente.setSelection(0);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_rutero, menu);         
        return true;      
    }
    
    //c�digo para cada opci�n de men�
    /**
     * metodo que se encarga de asignar acciones al momento de hacer click en la opcion de menu actualizar clientes
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		switch (item.getItemId()) {
			case R.id.menuActualizarClientes:
				new getActualizaClientes().execute("");
				pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Actualizando Clientes"), true, false);
				return true;
			case R.id.menuEnviarVisitas:
				new getEnviarVisitasClienteSys().execute("");
				pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Visitas"), true, false);
				return true;
			case R.id.menuCrearCliente:
				Intent intent = new Intent(RuteroActivity.this, DatosClienteActivity.class);
				intent.putExtra("idCliente", ""+0 );
				startActivity(intent);
				return true;

		}
	    return true;     
    }
    /**
     * metodo que se ejecuta al momento de hacer click en los botones de la actividad
     */
	public void onClick(View v) {
		
		 if(v.equals(btBuscar))
		{	
			cargarClientes(municipio.getMunicipio(), true,rbFiltroNit.isChecked());
		}
		else if(v.equals(btVerTodos))
		{
	    	cargarClientes(municipio.getMunicipio(),false,rbFiltroNit.isChecked());
		}
		else if(v.equals(btMenuR))
		{
	    	openOptionsMenu();		
		}
		else if(v.equals(btVolverR))
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
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			    keyback=true;
			}	
	    	
		}
		else if(v.equals(etNombreCliente))
		{
			keyback=false;
		}
		else if (v.equals(rbFiltroNombre))
		{
			rbFiltroNombre.setChecked(true);
			rbFiltroNit.setChecked(false);
		}
		else if (v.equals(rbFiltroNit))
		{
			rbFiltroNit.setChecked(true);
			rbFiltroNombre.setChecked(false);
		}
		
	}
	
	
	/**
	 * metodo que se ejecuta al hacer click en el momento de ingresar el nombre del cliente a buscar
	 */
	public boolean onKey(View v, int kc, KeyEvent ke) {

		
		if (v.equals(etNombreCliente))
		{
           if(kc==ke.KEYCODE_ENTER)
           {
        	   String nombre=etNombreCliente.getText().toString();
		    	
		    	if(nombre.equals(""))
		    	{
		    		mostrarMensaje("Debe Ingresar el nombre del Cliente", "l");		    		
		    	}
		    	else
		    	{
		    		cargarClientes(municipio.getMunicipio(), true,rbFiltroNit.isChecked());
					etNombreCliente.selectAll();
					etNombreCliente.requestFocus();
					return true;					
		    	}          	            
           }   
                
        }
	return false;		
	}
	/**
	 * metodo que carga los clientes a la lista para ser visualizados
	 * @param lista
	 */
	public void mostrar(ArrayList lista)
	{
		ArrayAdapter<String> aaClientes=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lista);
		listaCategorias.setAdapter(aaClientes);
	}
	
	/**
	 * metodo encargado de mostrar mensaje al usuario
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
	 * metodo que se encarga de cargar los clientes a la lista para visualizarlos
	 * y los demas parametros como la fecha, total de clientes, efectivos, faltantes

	 * @param busca
	 */
	public void cargarClientes(String  municipio , boolean busca, boolean buscaxnit)
	{



        bd=new creaBD(this);
        
        parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");
        
        //ESTADISTICAS
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual=sdf.format(c.getTime());
        
        long clientesTotales = bd.getNumeroClientes(this, municipio, usuario.cedula);
        long clientesEfectivos = bd.getNumeroClientesEfectivos(this, municipio, usuario.cedula, fechaActual);
        
        tvEtiquetas[3].setText(Long.toString(clientesTotales));
        tvEtiquetas[4].setText(Long.toString(clientesEfectivos));
        tvEtiquetas[5].setText(Long.toString(clientesTotales-clientesEfectivos));     
        try {

			if (busca) {
				String nombre = etNombreCliente.getText().toString();

				if (nombre.equals("")) {
					mostrarMensaje("Debe Ingresar el nombre del Cliente", "l");

				} else {
					cli = bd.getBuscarClientesPorDia(this, nombre, municipio, usuario.cedula,buscaxnit);
				}
			} else {
				cli = bd.getClientesPorDia(this, municipio, usuario.cedula);
			}
			context = getApplicationContext();
			listaCategorias.setAdapter(new ClienteAdapter(context, R.layout.activity_item_cliente, cli, operacion));
		} catch (Exception e)
		{
		 mostrarMensaje(""+e.toString(),"l");
		}
	}
	/**
	 * metodo encargado de retornar las primeras 3 letras del dia
	 * dependiendo del numero de dia que le ingrese
	 * @param dia
	 * @return
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
	/**
	 * metodo que retorna el nombre completo del dia dependiendo del numero del dia 
	 * que reciba
	 * @param dia
	 * @return
	 */
	public String getNombreDia(int dia)
	{
		String respuesta="";
		switch (dia)
		{
		case 0: respuesta="Domingo"; break;
		case 1: respuesta="Lunes"; break;
		case 2: respuesta="Martes"; break;
		case 3: respuesta="Miercoles"; break;
		case 4: respuesta="Jueves"; break;
		case 5: respuesta="Viernes"; break;
		case 6: respuesta="Sabado"; break;
		}
		return respuesta;
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
	{		
	}
	public void onNothingSelected(AdapterView<?> arg0) 
	{	
	}
	/**
	 * metodo que se encarga de dar estilo a la letra de las etiquetas de la actividad
	 * @param tv
	 */
//	public void getEstilo(TextView [] tv)
//	{
//		for (int i = 0; i < tv.length; i++) {
//			tv[i].setText(letraEstilo.getEstiloNegrilla(tv[i].getText().toString()));
//		}
//		
//	}
//	/**
//	 * metodo que se encarga de dar estilo a la letra de los botones de la actividad
//	 * @param tv
//	 */
//	public void getEstilo(Button tv)
//	{
//		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
//	}
	/**
	 * metodo que retorna la imagen que se visualizara en cada cliente 
	 * @param res
	 * @return
	 */
	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
	
	/**
	 * Clase que se encarga de actualizar los clientes del telefono 
	 * descargandolos del servidor en un proceso en segundo plano
	 * @author Javier
	 *
	 */
	private class getActualizaClientes extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{		
			ArrayList<Cliente> lclientes=null;
			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				llamarClientes=new LlamarClientes(parametrosPos.getIp());
				lclientes=llamarClientes.getClientes(usuario.cedula);
			}
			else
			{
				clientexVendedor=new GetClientexVendedor(parametrosSys.getIp(),parametrosSys.getWebidText());
				lclientes=clientexVendedor.getClientes(usuario.cedula);
			}	
			if(lclientes != null)
			{
				bd.openDB();
				bd.insertUsuario(usuario.cedula, usuario.clave);			
				bd.insertClientes(lclientes);									
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
						mostrarMensaje("Clientes Actualizados Correctamente.","l");
						cargarClientes(municipio.getMunicipio(),false,rbFiltroNit.isChecked());
						
						
				}
				else 
				{						
						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
						mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
				}		
				
			}
	}
	
	/**
	 * Clase que se encarga enviar la localizacion del cliente al sistema de georeferenciacion	 * 
	 * @author Javier
	 *
	 */
	private class getEnviarVisitasClienteSys extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{		

				PutVisitasCliente putVistitasCliente=new PutVisitasCliente(parametrosSys.getIp(),parametrosSys.getWebidText());
				res =putVistitasCliente.setVisitasCliente(parametrosSys.getRuta(),getXmlVisitas());
				return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("OK"))
				{						
						mostrarMensaje("Visita Enviadas Correctamente.","l");
				}
				else 
				{						
						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");	
					
				}
							
			}			
	}


	public String getXmlVisitas()
	{
		String fecha;
		Date fechaActual=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		fecha = sdf.format(fechaActual);
			String xml="";
			xml="<ListaVisitas>\n";
			for (int i = 0; i < cli.size(); i++) {
				Cliente cliente = cli.get(i);
				if (cliente.fechaUltimaVisita.equals(fecha) & !cliente.fechaUltimoPedido.equals(cliente.fechaUltimaVisita)) {
					VisitaCliente vc=new VisitaCliente();
					vc.setIdCliente(""+cliente.idCliente);
					vc.setIdVendedor(parametrosSys.getRuta());
					vc.setFecha(parametrosSys.getFechaSys());
					vc.setFecha2(parametrosSys.getFechaSys2());
					vc.setMotivoVisita(cliente.getMotivoUltimaVisita());
					xml +="<Visita>\n";
					for (int j = 0; j < vc.getPropertyCount(); j++) {
						xml +="		<"+vc.getPropertyName(j)+">"+vc.getProperty(j)+"</"+vc.getPropertyName(j)+">\n";
					}
					xml +="</Visita>\n";
				}

			}
			xml +="</ListaVisitas>";

		return xml;

	}


	/**
	 * 	 Atributo handler referencia de la clase Handler utilizada para asignar la ubicacion al cliente 
	 * se sera enviado al sistema de georeferenciacion 
	 */
	  private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				pdu.dismiss();
				if(msg.what==0)
				{
					locManager.removeUpdates(locListener);
			    	if (currentLocation!=null) {
			    		clientesys.setLatitud(String.valueOf(currentLocation.getLatitude()));
			    		clientesys.setLongitud( String.valueOf(currentLocation.getLongitude()));
			    		clientesys.setAltitud( String.valueOf(currentLocation.getAltitude()));
			    	//	new getLocalizarCliente().execute("");
			        //	pdu=ProgressDialog.show(RuteroActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Ubicaci�n del Cliente"), true,false);
			    	}
				}
		    	else
		    	{
		    		AlertDialog.Builder builder = new AlertDialog.Builder(RuteroActivity.this);
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
		    	}
		    }
		};
	/**
	 * metodo que se encarga de asignar la nueva localizacion al atributo currentLocation
	 * @param loc
	 */
  private void setCurrentLocation(Location loc) {
    	currentLocation = loc;
    }
	/**
	 * metodo que se ejecuta al mometo de inicializar el hilo de la chase
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
        	//locManager.requestLocationUpdates(
        	//		LocationManager.GPS_PROVIDER, 0, 0, locListener);
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
	 * Clase encargada de obtener la nueva localizacion del telefono
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
//	        	startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	        }

	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub
	        }

	        public void onStatusChanged(String provider, int status, 
	            Bundle extras) {
	        	Log.i("", "Provider Status: " + status);
	        }
	    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUB_ACTIVITY_CONSULTA_CLIENTES)
		{
			try
			{
				Bundle b = data.getExtras();
				boolean save=b.getBoolean("save");

				if(save)
				{
					long NSaldo =b.getLong("NSaldo");
					long inoperacion =b.getLong("operacion");
					long idLibro =b.getLong("idLibro");
					String fechaUltimaVenta=b.getString("fechaUltimaVenta");
					cliente.setDeudaAntFac(NSaldo+"");
					cliente.setFechaUltimaVenta(fechaUltimaVenta);
					refreshCliente();
					PrintDocumentIdLibro(idLibro);
				}


				/**
				if(save)
				{
					finish();
				}
				 **/
			}
			catch(Exception e)
			{
				//mostrarMensaje("No Selecciono ningun producto", "l");
			}
		}
	}

	private void PrintDocumentIdLibro(long IdLibro)
	{
		boolean valid=false;
		try
		{

				libro=bd.getLibro(this,IdLibro);
				valid=true;


		}
		catch (Exception e) {


		}

		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
			{
				try
				{
					operacionBixolon="libro";
					printBixolonsppr310();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					operacionDigitalPos="libro";
					printDigitalPos810();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}
		}
	}
	private void PrintDocumentIdPagoPrestamo(long IdPagoPrestamo)
	{
		boolean valid=false;
		try
		{

				pagoPrestamo=bd.getPagoPrestamo(this,IdPagoPrestamo);
				valid=true;
		}
		catch (Exception e) {
			mostrarMensaje("e1"+e.toString(),"l");
		}

		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1)
			{
				try
				{
					operacionBixolon="pagoPrestamo";
					printBixolonsppr310();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}

		}
	}
	private void PrintDocumentIdPrestamo(long IdPrestamo)
	{
		boolean valid=false;
		try
		{
				prestamo=bd.getPrestamo(this,IdPrestamo);
		}
		catch (Exception e) {


		}

		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1)
			{
				try
				{
					operacionBixolon="prestamo";
					printBixolonsppr310();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}

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
			mostrarMensaje("e2"+e.toString(),"l");
		}

		//mBixolonPrinter.findBluetoothPrinters();
	}
	private final Handler mHandler = new Handler(new Handler.Callback() {
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case BixolonPrinter.MESSAGE_STATE_CHANGE:
					if (msg.arg1 == BixolonPrinter.STATE_CONNECTED) {
						PrintBixolon printBixolon=new PrintBixolon();
						 if(operacionBixolon.equals("libro"))
						{
							printBixolon.printLibro(mBixolonPrinter,libro, parametrosSys);
						}



					}
					break;
				case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
					mBixolonPrinter.disconnect();
					break;
			}
			return true;
		}
	});






private void refreshCliente()
{
	if(viewCliente!=null) {
		LinearLayout llClientes = (LinearLayout) viewCliente.findViewById(R.id.llClientes);
		TextView tvOrden = (TextView) viewCliente.findViewById(R.id.tvOrden);
		TextView tvNombre = (TextView) viewCliente.findViewById(R.id.tvNombre);
		TextView tvDireccion = (TextView) viewCliente.findViewById(R.id.tvDireccion);
		TextView tvDiasGracia = (TextView) viewCliente.findViewById(R.id.tvDiasGracia);
		String fecha = "";

		TextView tvTelefono = (TextView) viewCliente.findViewById(R.id.tvTelefono);


		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		fecha = sdf.format(fechaActual);
		try {
			if (cliente.fechaUltimoPedido.equals(fecha)) {
				llClientes.setBackgroundColor(0xFF85E591);
			} else if (cliente.fechaUltimaVenta.equals(fecha)) {
				llClientes.setBackgroundColor(0xFF369DF7);
			} else if (cliente.fechaUltimaVisita.equals(fecha)) {
				llClientes.setBackgroundColor(0xFFFFE96D);
			} else {
			//	llClientes.setBackgroundColor(0xFFFFFFFF);
			}
			tvDiasGracia.setVisibility(View.GONE);
			tvDiasGracia.setVisibility(View.VISIBLE);
			tvDiasGracia.setText("" + cliente.getDias());
			if (cliente.getDias() > 2)
			//if(cliente.getDias()>cliente.getDiasGraciaCliente() )
			{
				llClientes.setBackgroundColor(0xFFD89393);
			}
			tvTelefono.setText(getEstiloTexto(cliente.deudaAntFac));

		} catch (Exception e) {
			tvNombre.setText(e.toString());
		}

	}
	}


	public SpannableString getEstiloTexto(String texto) {
		SpannableString spanString = new SpannableString(texto);
		spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0);
		return spanString;
	}



	private final void SeleccionaClienteSucursal()
	{




		final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("Sucursales disponibles");
		builder2.setMessage(""+cliente.nombre );

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder2.create();

		final LayoutInflater inflater = getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.activity_selec_sucursales_cliente , null);
		test.setView(dialogView);


		//hace referencia al objeto lista para mostrar sucursales
		final  ListView   lvSucursales = (ListView) dialogView.findViewById(R.id.lvSucursales);

		//Carga sucursales cliente
		lvSucursales.setAdapter(new ClienteSucursalAdapter(context, R.layout.activity_item_selec_sucursal_cliente, listaClienteSucursal));



		final Button btCancelarSucursal = (Button) dialogView.findViewById(R.id.btCancelarSucursal);
		btCancelarSucursal.setText("Cancelar");

		btCancelarSucursal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				test.cancel();
			}
		});



		lvSucursales.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view01, int position, long id) {
					clienteSucursal = listaClienteSucursal.get((int)position);
					cliente.idClienteSucursal=Long.parseLong(clienteSucursal.getIdClienteSucursal());
					cliente.direccion=clienteSucursal.getDireccion()+" - "+clienteSucursal.getMunicipio()+" - "+clienteSucursal.getTelefono();
							//Guarda linea anterior forma de tomar pedidos
							Intent intent = new Intent(RuteroActivity.this, CrearPedidoActivity.class);
							//Intent intent = new Intent(RuteroActivity.this, ListaArticulosPedidoActivity.class);
							intent.putExtra("nombre", cliente.nombre);
							intent.putExtra("direccion", cliente.direccion);
							intent.putExtra("ordenVisita", cliente.ordenVisita);
							intent.putExtra("idCliente", cliente.idCliente);
							intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
							intent.putExtra("cedula", usuario.cedula);
							intent.putExtra("consulta", false);
							intent.putExtra("ubicado", cliente.ubicado);
							intent.putExtra("idCodigoExterno", 0);
							intent.putExtra("idCodigoInterno", 0);
							intent.putExtra("operacion", operacion);
							intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
							startActivity(intent);
							finish();
					test.cancel();
				}

		});
		//		        final AlertDialog test = builder.create();
		test.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(lvSucursales, InputMethodManager.SHOW_IMPLICIT);
		lvSucursales.requestFocus();
		test.show();
	}

	private void printDigitalPos810(){
		String bleAdrress=parametrosPos.getMacAddDigitalPos();
		binder.connectBtPort(bleAdrress, new UiExecute() {
			public void onsucess() {
				ISCONNECT = true;
				PrintDigitaPos printDigitaPos = new PrintDigitaPos();

				if(operacionDigitalPos.equals("libro"))
				{
					printDigitaPos.printLibro(binder,libro, parametrosSys);
				}
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