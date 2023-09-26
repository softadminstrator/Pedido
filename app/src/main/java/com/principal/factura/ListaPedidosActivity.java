package com.principal.factura;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.BatteryStatusChangeEventListener;
import com.epson.eposprint.Builder;
import com.epson.eposprint.Print;
import com.epson.eposprint.StatusChangeEventListener;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.ArticulosTraslado;
import com.principal.mundo.Bodega;
import com.principal.mundo.Cliente;
import com.principal.mundo.ClsArtFactura;
import com.principal.mundo.ClsArtPedido;
import com.principal.mundo.ClsArtRemision;
import com.principal.mundo.ClsArtsFactura;
import com.principal.mundo.ClsArtsPedido;
import com.principal.mundo.ClsArtsRemision;
import com.principal.mundo.EnviarAnulacion;
import com.principal.mundo.EnviarFactura;
import com.principal.mundo.EnviarPedido;
import com.principal.mundo.EnviarRemision;
import com.principal.mundo.EnviarTraslado;
import com.principal.mundo.Factura;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.LlamarFecha;
import com.principal.mundo.LlamarFechaSys;
import com.principal.mundo.Medios;
import com.principal.mundo.Opciones;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.FacturaEnviarSys;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;
import com.principal.mundo.sysws.PutFacturaSys;
import com.principal.mundo.sysws.PutLibro;
import com.principal.mundo.sysws.PutPagosFacturaSys;
import com.principal.mundo.sysws.PutPedidoSys;
import com.principal.mundo.sysws.PutRemisionSys;
import com.principal.mundo.sysws.RemisionEnviarSys;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintBixolon;
import com.principal.print.PrintDigitaPos;
import com.principal.print.PrintEpson;
import com.principal.print.PrintFactura;
import com.principal.print.PrintZebra;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;

/**
 * 
 * 
 * Clase en que se visualizan los pedidos relaizados desde el telefono  
 * @author Javier
 *
 */
@SuppressLint("SimpleDateFormat")
public class ListaPedidosActivity extends  Activity implements OnClickListener,StatusChangeEventListener, BatteryStatusChangeEventListener {
	//-------------------------------------------------------
	//--------------------CONSTANTES-------------------------
	//-------------------------------------------------------
	static final int DATE_DIALOG_ID   = 1;
	protected static final int SUB_ACTIVITY_SELECTFECHA = 200;	
	protected static final int SUB_ACTIVITY_SELECTBODEGA = 300;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int CARTERA=7;
	private final static int PRESTAMOS=8;
	private final static int ABONOPRESTAMOS=9;
	private final static int VERLIBRO=10;
	private final static int REMISION=12;
	private ArrayList<String> datos=new ArrayList<String>();

	static final int SEND_TIMEOUT = 10 * 1000;
	private Print printer;
	static BixolonPrinter mBixolonPrinter;

	private String operacionBixolon;
	private String operacionDigitalPos;

	//Variables para impresora digital pos
	//IMyBinder interface，All methods that can be invoked to connect and send data are encapsulated within this interface
	public static IMyBinder binder;
	public static boolean ISCONNECT;








	String ressul="..";
	/**
	 * Atributo pedidos referente al arrego que contiene la lista de pedidos realizados
	 */
	ArrayList<Pedido_in> listaPedidos=new ArrayList<Pedido_in>();	
	ArrayList<Pedido_in> listaPedidosRepresados=new ArrayList<Pedido_in>();
	

	ArrayList<Factura_in> listaFacturas=new ArrayList<Factura_in>();	
	ArrayList<Factura_in> listaFacturasRepresadas=new ArrayList<Factura_in>();

	ArrayList<Remision_in> listaRemisiones=new ArrayList<Remision_in>();
	ArrayList<Remision_in> listaRemisionesRepresadas=new ArrayList<Remision_in>();
	
	
	ArrayList<Traslado_in> listaTraslados=new ArrayList<Traslado_in>();	
	ArrayList<Traslado_in> listaTrasladosrepresados=new ArrayList<Traslado_in>();
	
	ArrayList<Pago> listaPagos=new ArrayList<Pago>();
	ArrayList<Pago> listaPagosRepresados=new ArrayList<Pago>();

	ArrayList<Prestamo> listaPrestamos=new ArrayList<Prestamo>();
	ArrayList<Prestamo> listaPrestamosRepresados=new ArrayList<Prestamo>();

	ArrayList<PagoPrestamo> listaPagoPrestamos=new ArrayList<PagoPrestamo>();
	ArrayList<PagoPrestamo> listaPagoPrestamosRepresados=new ArrayList<PagoPrestamo>();

	ArrayList<Libro> listaLibros=new ArrayList<Libro>();
	ArrayList<Libro> listaLibrosRepresados=new ArrayList<Libro>();

	private PrintFactura printFactura;
	FacturaEnviarSys facturaEnviarsys=new FacturaEnviarSys();

	RemisionEnviarSys remisionEnviarsys=new RemisionEnviarSys();
	/**
	 * Atributo pedidos referente al arrego que contiene la lista de pedidos realizados
	 */

	/**
	 * Atributo context referente a la clase Context de la actividad
	 */
	Context context;
	/**
	 * Atributo listaPedidos referente a la lista grafica donde se mostraran los pedidos realizados
	 */
	ListView listView;
	/**
	 * Atributo tvTituloP referente a la caja de texto del titulo de la actividad
	 */
	TextView tvOperacionListaPedidos;
	/**
	 * Atributo tvVentas referente a la caja de texto del valor total en pedidos realizados
	 */
	
	/**
	 * Atributo btFecha referente al boton que muestra la fecha actual del sistema
	 */
	Button btFecha,btBodegas, btMenuP, btVolverP;
	/**
	 * Atributo btRuteroP referente boton rutero para visualizar los clientes asignados
	 */
	Button btRuteroP;
	/**
	 * Atributo opcionesEnviado referente a la clase Opciones que se mostraran el dialogo al hacer click sostendido 
	 * sobre un pedido de la lista
	 */
	Opciones [] opcionesEnviado;    
	/**
	 * Atributo opcionesNoEnviado referente a la clase Opciones que se mostraran el dialogo al hacer click sostendido 
	 * sobre un pedido de la lista
	 */
    Opciones [] opcionesNoEnviado;
    /**
	 * Atributo represados contiene el numero de pedidos que no han podido ser enviados al servidor
	 */
    private int represados=0;
    /**
	 * Atributo idlistaRepresado utilizada para guardar el identificador de la lista de represados
	 */
    private int idlistaRepresado=0; 
    
    private int idlistaPagos=0;
    /**
	 * Atributo locManager referente de la clase LocationManager
	 */
    private LocationManager locManager;
    /**
	 * Atributo locListener referente de la clase LocationListener
	 */
	private LocationListener locListener;
	/**
	 * Atributo bd referente a la clase creaBD
	 */
	creaBD bd;
	/**
	 * Atributo usuario referente de la clase Usuario
	 */
	Usuario usuario=new Usuario();
	/**
	 * Atributo pedselect referente de la clase Pedido_in
	 */
	Pedido_in pedido=new Pedido_in();
	PagosFactura pagosFactura =new PagosFactura();
	
	Factura_in factura=new Factura_in();

	Remision_in remision=new Remision_in();

	Traslado_in traslado=new Traslado_in();
	
	Factura facturaEnviar=new Factura();
	Remision remisionEnviar=new Remision();

	Traslado trasladoEnviar=new Traslado();
	
	Pago pago=new Pago();

	Prestamo prestamo =new Prestamo();

	PagoPrestamo pagoPrestamo=new PagoPrestamo();

	Libro libro =new Libro();
	
	Pedido pedidoEnviar=new Pedido();
	com.principal.mundo.sysws.Pedido pedidoEnviarSys=new com.principal.mundo.sysws.Pedido();
	
	private RelativeLayout rlListaPedidos;
	/**
	 * Atributo pedselect referente de la clase Pedido_in
	 */
	
	/**
	 * Atributo fecha donde se almacena la fecha actual del sistema
	 */
	String fechaDesde="";
	String fechaHasta="";
	
	private boolean changeDate;
	
	
	String fechaBotonDesde="";
	String fechaBotonHasta="";
	/**
	 * Atributo dia donde se almacena el dia actual del sistema
	 */
	int dia=1;
	/**
	 * Atributo mes donde se almacena el mes actual del sistema
	 */
	int mes=1;
	/**
	 * Atributo ano donde se almacena el ano actual del sistema
	 */
	int ano=2012;
	
	
	String ventas="";
	/**
	 * Atributo pedidoEnviar referente de la clase Pedido
	 */
	
	/**
	 * Atributo listaAP Arreglo que hacer referencia a la lista de pedidos realizados
	 */
	ArrayList<ArticulosPedido> listaAPedido;
	ArrayList<ArticulosFactura> listaAFactura;
	ArrayList<ArticulosTraslado> listaATraslado;
	ArrayList<ArticulosRemision> listaARemision;
	
	
	/**
	 * Atributo letraEstilo referente de la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	/**
	 * Atributo parametros referente de la clase Parametros
	 */
	Parametros parametrosPos, parametrosSys;
	ArrayList<Medios> listaMedios;
	
	private ProgressDialog pdu;
	
	String etapa="";
	
	private boolean isanulado=false;
	
	private com.principal.mundo.sysws.Cliente	clientesys=new com.principal.mundo.sysws.Cliente();
	/**
	 * Atributo cliente referencia de la clase Cliente
	 */
	Cliente cliente=new Cliente();
	private int operacion;
	private boolean print=false;
	/**
	 * Atributo pedidoin referencia de la clase Pedido_in
	 */
	
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);



    	
    	try
    	{

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





        letraEstilo=new LetraEstilo();
        setContentView(R.layout.activity_lista_pedidos);
        listView=(ListView)findViewById(R.id.lvPedidos);        
        tvOperacionListaPedidos=(TextView)findViewById(R.id.tvOperacionListaPedidos);
//        getEstilo(tvTituloP);        
        btFecha=(Button)findViewById(R.id.btFecha);        
        btRuteroP=(Button)findViewById(R.id.btRuteroP);
        btBodegas=(Button)findViewById(R.id.btBodegas);
        
        btVolverP=(Button)findViewById(R.id.btVolverP);
        btVolverP.setOnClickListener(this);
        btMenuP=(Button)findViewById(R.id.btMenuP);
        btMenuP.setOnClickListener(this);
        
        rlListaPedidos=(RelativeLayout)findViewById(R.id.rlListaPedidos);
//        getEstilo(btRuteroP);        
        btBodegas.setOnClickListener(this);
        bd=new creaBD(this);        
        parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");
		listaMedios=bd.GetMedios();
      
        Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);     
      
        fechaDesde=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);
        fechaHasta=ano+"-"+validNumberDate(mes)+"-"+validNumberDate(dia);
        
         
        
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        usuario.cedula=obtenerDatos.getString("cedula");
        operacion=obtenerDatos.getInt("operacion");
        changeDate=obtenerDatos.getBoolean("changeDate"); 
        print=obtenerDatos.getBoolean("print");
        
        
        fechaBotonDesde=validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);
        fechaBotonHasta=validNumberDate(c.get(Calendar.DAY_OF_MONTH))+"/"+validNumberDate((c.get(Calendar.MONTH)+1))+"/"+c.get(Calendar.YEAR);    
       
         btFecha.setText(fechaBotonDesde+" <> "+fechaBotonHasta); 
         btBodegas.setVisibility(View.GONE);
			btRuteroP.setVisibility(View.VISIBLE);


			if(operacion==VERLIBRO)
			{
				rlListaPedidos.setBackgroundColor(0xFFC6D9E8);

				cliente=new Cliente();
				cliente.setNombre(obtenerDatos.getString("nombreCliente"));
				cliente.setDeudaAntFac(obtenerDatos.getString("deudaAntFac"));
				cliente.setIdCliente(obtenerDatos.getLong("idCliente"));
				tvOperacionListaPedidos.setText("LIBRO: "+cliente.getNombre());
				cargarMovimientosXCliente(fechaDesde, fechaHasta,cliente);
				btRuteroP.setVisibility(View.GONE);

			}
		//ACCIONES PRESTAMOS
			else if(operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
			{

				opcionesEnviado=new Opciones[1];
				opcionesNoEnviado=new Opciones[2];

//             opcionesEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
				opcionesEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");

//             opcionesNoEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
				opcionesNoEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");
				opcionesNoEnviado[1]=new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");

				rlListaPedidos.setBackgroundColor(0xFFC6D9E8);
				tvOperacionListaPedidos.setText("MOVIMIENTOS REALIZADOS");
				//fechaDesde=ano+""+validNumberDate(mes)+""+validNumberDate(dia);
				//fechaHasta=ano+""+validNumberDate(mes)+""+validNumberDate(dia);
				cargarMovimientos(fechaDesde, fechaHasta);
				btRuteroP.setVisibility(View.GONE);

				listView.setOnItemLongClickListener (new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id)
					{
						ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);
						AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
						builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));


						libro = listaLibros.get((int)position);

						if( libro.getEnviado()!=0)
						{
							builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int item) {
											if(item==0)
											{
												//IMPRIME PAGO
												PrintDocumentIdLibro(Long.parseLong(libro.getIdLibro()));
											}
											dialog.cancel();
										}
									}
							);
						}
						else
						{

							ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
							builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int item) {
											if(item==0)
											{
												PrintDocumentIdLibro( Long.parseLong(libro.getIdLibro()));

											}
											else if(item==1)
											{
												//ENVIAR LIBRO REPRESADO
												represados=0;
												new enviarLibro().execute("");
												pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando movimiento"), true,false);

											}
											dialog.cancel();
										}
									}
							);
						}

						AlertDialog alert = builder.create();
						alert.show();

						return false;
					}
				});

				if(print)
				{

					PrintDocumentIdLibro(obtenerDatos.getLong("idLibro"));
				}

			}
			//ACCIONES PAGO PRESTAMOS
         else if(operacion==PRESTAMOS)
		 {

			 opcionesEnviado=new Opciones[1];
			 opcionesNoEnviado=new Opciones[2];

//             opcionesEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
			 opcionesEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");

//             opcionesNoEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
			 opcionesNoEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");
			 opcionesNoEnviado[1]=new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");

			 rlListaPedidos.setBackgroundColor(0xFFCDE5C5);
			 tvOperacionListaPedidos.setText("PRESTAMOS REALIZADOS");
			// fechaDesde=ano+""+validNumberDate(mes)+""+validNumberDate(dia);
			// fechaHasta=ano+""+validNumberDate(mes)+""+validNumberDate(dia);
			 cargarPrestamos(fechaDesde, fechaHasta);

			 listView.setOnItemLongClickListener (new OnItemLongClickListener() {
				 public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id)
				 {
					 ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);
					 AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
					 builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));


					 prestamo = listaPrestamos.get((int)position);

					 if( prestamo.getEnviado()!=0)
					 {
						 builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
									 public void onClick(DialogInterface dialog, int item) {
										 if(item==0)
										 {
											 //IMPRIME PAGO
											 PrintDocumentIdPrestamo(Long.parseLong(prestamo.getIdPrestamo()));
										 }
										 dialog.cancel();
									 }
								 }
						 );
					 }
					 else
					 {

						 ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
						 builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
									 public void onClick(DialogInterface dialog, int item) {
										 if(item==0)
										 {
											 PrintDocumentIdPrestamo( Long.parseLong(prestamo.getIdPrestamo()));

										 }
										 else if(item==1)
										 {
											 //ENVIAR PAGO REPRESADO
											 pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(ListaPedidosActivity.this, pago.getIdPago()));
											 idlistaPagos=0;
											 enviarPago();
										 }
										 dialog.cancel();
									 }
								 }
						 );
					 }

					 AlertDialog alert = builder.create();
					 alert.show();

					 return false;
				 }
			 });

			 if(print)
			 {

				 PrintDocumentIdPrestamo(obtenerDatos.getLong("idPrestamo"));
			 }

		 }
         //Acciones Cartera
         else if(operacion==CARTERA)
         {
        	 
        	 opcionesEnviado=new Opciones[1];
             opcionesNoEnviado=new Opciones[2]; 
             
//             opcionesEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
             opcionesEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");
            
//             opcionesNoEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
             opcionesNoEnviado[0]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");
             opcionesNoEnviado[1]=new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");
             
             rlListaPedidos.setBackgroundColor(0xFFFFEEBB);
             tvOperacionListaPedidos.setText("PAGOS REALIZADOS");
	         cargarPagos(fechaDesde, fechaHasta); 
	        	
	        	listView.setOnItemLongClickListener (new OnItemLongClickListener() {
	            	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
	            	  {      		  			
	            		  		ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);      		  		
	            		        AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
	            		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
	            		   	    
	            		   	    	
	            		   	    pago = listaPagos.get((int)position);     
	            		   	    
	      		    		   	    if( pago.getEnviado()!=0)
	      		    		   	     {      		  
	      		    		   	    	builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
	      						      			    	if(item==0)
	      						      			    	{
	      						      			    	//IMPRIME PAGO
	      						      			    	 PrintDocumentIdPago( pago.getIdPago());
	      						      			    	}	  						      			    	
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    }
	      		    		   	    else
	      		    		   	    {
	      					      		   	    
	      		    		   	               ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
	      		    		   	               builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
	      						      			    	if(item==0)
	      						      			    	{
	      						      			    	 PrintDocumentIdPago( pago.getIdPago());
	      						      			    			
	      						      			    	}				      			    	
	      						      			    	else if(item==1)
	      						      			    	{
	      						      			    		//ENVIAR PAGO REPRESADO
	      						      						pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(ListaPedidosActivity.this, pago.getIdPago()));
	      						      			    		idlistaPagos=0;
	      						      			    		enviarPago();
	      						      			    	}	      						      			   
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    } 	
	            		   	    
	            		  	AlertDialog alert = builder.create();
	            			alert.show();
	            		  
	            		  return false;
	            		  }
	            		}); 
	        	
	        if(print)
           	 {
           		              	
           		// PrintDocumentIdPago(obtenerDatos.getLong("idPago"));
           	 }     	
             
         }    
         
         else if(operacion==PEDIDO)
        {
        					tvOperacionListaPedidos.setText("PEDIDOS REALIZADOS");
				        	cargarPedidos(fechaDesde,fechaHasta);

							if(parametrosPos.isValue(parametrosPos.getImprimePedido())) {
								opcionesEnviado = new Opciones[4];
								opcionesNoEnviado = new Opciones[5];
								opcionesEnviado[0] = new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
								opcionesEnviado[1] = new Opciones("Editar", getImg(R.drawable.pedidos), "Editar");
								opcionesEnviado[2] = new Opciones("Anular", getImg(R.drawable.anular), "Anular");
								opcionesEnviado[3] = new Opciones("Imprimir Copia", getImg(R.drawable.print), "Imprimir Copia");



								opcionesNoEnviado[0] = new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
								opcionesNoEnviado[1] = new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");
								opcionesNoEnviado[2] = new Opciones("Editar", getImg(R.drawable.pedidos), "Editar");
								opcionesNoEnviado[3] = new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");
								opcionesNoEnviado[4] = new Opciones("Imprimir Copia", getImg(R.drawable.print), "Imprimir Copia");opcionesNoEnviado[4] = new Opciones("Imprimir Copia", getImg(R.drawable.print), "Imprimir Copia");

							}
							else
							{
								opcionesEnviado = new Opciones[3];
								opcionesNoEnviado = new Opciones[4];
								opcionesEnviado[0] = new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
								opcionesEnviado[1] = new Opciones("Editar", getImg(R.drawable.pedidos), "Editar");
								opcionesEnviado[2] = new Opciones("Anular", getImg(R.drawable.anular), "Anular");

								opcionesNoEnviado[0] = new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
								opcionesNoEnviado[1] = new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");
								opcionesNoEnviado[2] = new Opciones("Editar", getImg(R.drawable.pedidos), "Editar");
								opcionesNoEnviado[3] = new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");
							}

			listView.setOnItemLongClickListener (new OnItemLongClickListener() {
				            	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
				            	  {      		  			
				            		  		ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);      		  		
				            		        AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
				            		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
				            		   	    
				            		   	    	
				      		      		   	  pedido = listaPedidos.get((int)position);      		   	    
				      		    		   	    if(pedido.idCodigoExterno!=0)
				      		    		   	     {      		  
				      		    		   	    	builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
				      						      			    public void onClick(DialogInterface dialog, int item) {
				      						      			    	if(item==0)
				      						      			    	{				      			    		
				      						      					  Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
				      						      						intent.putExtra("nombre", pedido.nombreCliente);
				      						      						intent.putExtra("ordenVisita", pedido.idCodigoInterno);
				      						      						intent.putExtra("idCliente", pedido.idCliente);
																		intent.putExtra("idClienteSucursal", pedido.idClienteSucursal);
				      						      						intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
				      						      						intent.putExtra("cedula", usuario.cedula);
				      						      						intent.putExtra("ubicado",cliente.ubicado);
				      						      						intent.putExtra("consulta", true);			      			
				      				  				      			    intent.putExtra("idCodigoExterno",pedido.idCodigoExterno);
				      				  				      			    intent.putExtra("idCodigoInterno",pedido.idCodigoInterno);
				      				  				      			    intent.putExtra("operacion", operacion);		      					  				      				
				      						      						startActivity(intent);
				      						      			    	}				      						      			    		      			    	
//				      						      			    	
				      						      			    	else if(item==1)
				      						      			    	{
				      						      			    		//Valida que el pedido no haya sido enviado para factura electronica
																		if(pedido.getEstado().equals("1"))
																		{
																			mostrarMensaje("No se puede editar el pedido, debido a que ya desconto del inventario", "l");
																		}
																		else {
																			Intent intent = new Intent(ListaPedidosActivity.this, CrearPedidoActivity.class);
																			cliente = bd.getBuscarClientesin(pedido.idCliente, cliente);
																			intent.putExtra("nombre", cliente.nombre);
																			intent.putExtra("ordenVisita", cliente.ordenVisita);
																			intent.putExtra("idCliente", cliente.idCliente);
																			intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
																			intent.putExtra("consulta", false);
																			intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
																			intent.putExtra("ubicado", cliente.ubicado);
																			intent.putExtra("cedula", usuario.cedula);
																			intent.putExtra("idCodigoExterno", pedido.idCodigoExterno);
																			intent.putExtra("idCodigoInterno", pedido.idCodigoInterno);
																			intent.putExtra("operacion", operacion);
																			startActivity(intent);
																			finish();
																		}
				      							      				}
																	else if (item == 2) {//
																		if(pedido.getEstado().equals("3"))
																		{
																			mostrarMensaje("El pedido seleccionado ya fue anulada anteriormente!!", "l");
																		}
																		else if(pedido.getEstado().equals("0"))
																		{
																			mostrarMensaje("No es posible anular un pedido normal debido a que no ha afectado inventario!!", "l");
																		}
																		else {
																			new enviarAnulacionPedido().execute("");
																			pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Anulacion"), true, false);
																		}
																	}
																	if(parametrosPos.isValue(parametrosPos.getImprimePedido())) {

																		if(item==3) {
																			bd.openDB();
																			listaAPedido = bd.getArticulosPedido(pedido.idCodigoInterno);
																			bd.closeDB();
																			 if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0) {
																				pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
																				printFactura = new PrintFactura();
																				printFactura.printPedido(pedido, listaAPedido, parametrosPos);
																				pdu.dismiss();
																				mostrarMensaje(printFactura.getMensaje(), "l");

																			}
																			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
																			{
																				mostrarMensaje("Enviando datos digital pos", "l");
																				try
																				{
																					operacionBixolon="pedido";
																					printBixolonsppr310();
																				}catch(Exception e){
																					mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
																					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																				}
																			}
																			 else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
																			 {
																				 mostrarMensaje("Enviando datos digital pos", "l");
																				 try
																				 {
																					 operacionDigitalPos="pedido";
																					 printDigitalPos810();
																				 }catch(Exception e){
																					 mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
																					 mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																				 }
																			 }
																			 else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0){

																				Context context = ListaPedidosActivity.this;
																				int deviceType = Print.DEVTYPE_BLUETOOTH;
																				int enabled = Print.FALSE;
																				int interval = 1000;

																				try {
																					//open
																					if (printer == null) {
																						printer = new Print(getApplicationContext());
																					}


																					if (printer != null) {
																						try {
																							if (printer != null) {
																								printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																								printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																							}
																							printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
																						} catch (Exception e) {
																							mostrarMensaje("No fue posible Enviar la impresion", "l");
																							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																							//mostrarMensaje("3. " + e.toString(), "l");
																						}

																						Builder builder = null;
																						try {
																							builder = new Builder("TM-P80", Builder.MODEL_ANK, getApplicationContext());
																							PrintEpson printEpson = new PrintEpson();
																							builder = printEpson.printPedido(builder, pedido, listaAPedido, parametrosPos);
																							int[] status = new int[1];
																							int[] battery = new int[1];
																							printer.sendData(builder, SEND_TIMEOUT, status, battery);


																							if (builder != null) {
																								try {
																									builder.clearCommandBuffer();
																									builder = null;
																								} catch (Exception e) {
																									builder = null;
																								}
																							}

																							printer.closePrinter();

																						} catch (Exception e) {

																							mostrarMensaje("No fue posible Enviar la impresion", "l");
																							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																						}
																					} else {

																					}

																					//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
																				} catch (Exception e) {
																					mostrarMensaje("No fue posible Enviar la impresion", "l");
																					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


																				}

																			} else {
																				pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo"), true, false);
																				PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
																				if (pz.printPedido(pedido, listaAPedido, parametrosPos)) {
																					pdu.dismiss();
																					mostrarMensaje("Impresion enviada Correctamente..", "l");
																				} else {
																					pdu.dismiss();
																					mostrarMensaje("No fue posible Enviar la impresion", "l");
																				}
																			}
																		}


																	}
				      						      			    	dialog.cancel();			        
				      						      			    }
				      						      			}
				      						      			);
				      		    		   	    }
				      		    		   	    else
				      		    		   	    {
				      					      		   	    
				      		    		   	               ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
				      		    		   	               builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
				      						      			    public void onClick(DialogInterface dialog, int item) {
				      						      			    	if(item==0)
				      						      			    	{
				      						      					    Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
				      						      						intent.putExtra("nombre", pedido.nombreCliente);
				      						      						intent.putExtra("ordenVisita", pedido.idCodigoInterno);
				      						      						intent.putExtra("idCliente", pedido.idCliente);
																		intent.putExtra("idClienteSucursal", pedido.idClienteSucursal);
				      						      						intent.putExtra("idPedido", pedido.idCodigoInterno);				      						
				      						      						intent.putExtra("cedula", usuario.cedula);
				      						      						intent.putExtra("consulta", true);			      			
				      				  				      				intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
				      				  				      				intent.putExtra("modifica","NO");
				      				  				      			    intent.putExtra("ubicado",cliente.ubicado);				  				      		
				      				  				      			    intent.putExtra("idCodigoExterno",pedido.idCodigoExterno);
				      				  				      			    intent.putExtra("idCodigoInterno",pedido.idCodigoInterno);
				      				  				      			    intent.putExtra("operacion", operacion);
				      				  				      			    startActivity(intent);
				      						      			    	}				      			    	
				      						      			    	else if(item==1)
				      						      			    	{
//				      						      			    		comenzarLocalizacion();
//				      						      						locManager.removeUpdates(locListener);
				      						      			    	    represados=0;

																		if (pedido.getTipoPedido().equals("E"))
																		{
																			new enviarPedidoInventario().execute("");
																			pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
																		}
																		else {
																			new enviarPedido().execute("");
																			pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
																		}

				      						      						//new enviarPedido().execute("");
				      						      						//pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true,false);
				      						      							      			    		
    					          
				      						      			    	}
				      						      			      	else if(item==2)
				      						      			    	{
																		if(pedido.getEstado().equals("1"))
																		{
																			mostrarMensaje("No se puede editar el pedido, debido a que ya desconto del inventario", "l");
																		}
																		else {
																			CrearPedidoActivity crearPedidoActivity = new CrearPedidoActivity();
																			Intent intent = new Intent(ListaPedidosActivity.this, CrearPedidoActivity.class);
																			cliente = bd.getBuscarClientesin(pedido.idCliente, cliente);
																			cliente = bd.getBuscarClientesin(pedido.idCliente, cliente);
																			intent.putExtra("nombre", cliente.nombre);
																			intent.putExtra("ordenVisita", cliente.ordenVisita);
																			intent.putExtra("idCliente", cliente.idCliente);
																			intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
																			intent.putExtra("PrecioDefecto", cliente.PrecioDefecto);
																			intent.putExtra("cedula", usuario.cedula);
																			intent.putExtra("modifica", "NO");
																			intent.putExtra("ubicado", cliente.ubicado);
																			intent.putExtra("consulta", false);
																			intent.putExtra("idCodigoExterno", pedido.idCodigoExterno);
																			intent.putExtra("idCodigoInterno", pedido.idCodigoInterno);
																			intent.putExtra("operacion", operacion);
																			startActivity(intent);
																		}
				      				  				      								      			    		
				      							      				}
					      						      			    else if(item==3)
				      						      			    	{						      			    		
					      						      			    borrarPedido();
					      						    	 	    	mostrarMensaje("Pedido Eliminado Correctamente", "l");
					      							    			cargarPedidos(fechaDesde,fechaHasta);
				      						      			    	}
																	if(parametrosPos.isValue(parametrosPos.getImprimePedido())) {

																	 if(item==4) {
																		 bd.openDB();
																		 listaAPedido = bd.getArticulosPedido(pedido.idCodigoInterno);
																		 bd.closeDB();
																		 if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0) {
																			 pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
																			 printFactura = new PrintFactura();
																			 printFactura.printPedido(pedido, listaAPedido, parametrosPos);
																			 pdu.dismiss();
																			 mostrarMensaje(printFactura.getMensaje(), "l");

																		 }
																		 else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
																		 {
																			 mostrarMensaje("Enviando datos digital pos", "l");
																			 try
																			 {
																				 operacionBixolon="pedido";
																				 printBixolonsppr310();
																			 }catch(Exception e){
																				 mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
																				 mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																			 }
																		 }
																		 else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
																		 {
																			 mostrarMensaje("Enviando datos digital pos", "l");
																			 try
																			 {
																				 operacionDigitalPos="pedido";
																				 printDigitalPos810();

																			 }catch(Exception e){
																				 mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
																				 mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																			 }
																		 }
																		 else  if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0) {

																			 Context context = ListaPedidosActivity.this;
																			 int deviceType = Print.DEVTYPE_BLUETOOTH;
																			 int enabled = Print.FALSE;
																			 int interval = 1000;

																			 try {
																				 //open
																				 if (printer == null) {
																					 printer = new Print(getApplicationContext());
																				 }


																				 if (printer != null) {
																					 try {
																						 if (printer != null) {
																							 printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																							 printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																						 }
																						 printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
																					 } catch (Exception e) {
																						 mostrarMensaje("No fue posible Enviar la impresion", "l");
																						 mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																					 }

																					 Builder builder = null;
																					 try {
																						 builder = new Builder("TM-P80", Builder.MODEL_ANK, getApplicationContext());
																						 PrintEpson printEpson = new PrintEpson();
																						 builder = printEpson.printPedido(builder, pedido, listaAPedido, parametrosPos);
																						 int[] status = new int[1];
																						 int[] battery = new int[1];
																						 printer.sendData(builder, SEND_TIMEOUT, status, battery);


																						 if (builder != null) {
																							 try {
																								 builder.clearCommandBuffer();
																								 builder = null;
																							 } catch (Exception e) {
																								 builder = null;
																							 }
																						 }

																						 printer.closePrinter();

																					 } catch (Exception e) {
																						 mostrarMensaje("No fue posible Enviar la impresion", "l");
																						// mostrarMensaje("1. " + e.toString(), "l");
																					 }
																				 } else {

																				 }

																				 //printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
																			 } catch (Exception e) {
																				 mostrarMensaje("No fue posible Enviar la impresion", "l");
																				// mostrarMensaje("2. " + e.toString(), "l");

																			 }

																		 }
																		 else {
																			 pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo"), true, false);
																			 PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
																			 if (pz.printPedido(pedido, listaAPedido, parametrosPos)) {
																				 pdu.dismiss();
																				 mostrarMensaje("Impresion enviada Correctamente..", "l");
																			 } else {
																				 pdu.dismiss();
																				 mostrarMensaje("No fue posible Enviar la impresion", "l");
																			 }
																		 }
																	 }

																	}



				      						      			    	dialog.cancel();			        
				      						      			    }
				      						      			}
				      						      			);
				      		    		   	    } 	
				            		   	    
				            		  	AlertDialog alert = builder.create();
				            			alert.show();
				            		  
				            		  return false;
				            		  }
				            		}); 
            
        }
        else
        {
            etapa="2";
        	 opcionesEnviado=new Opciones[3];
             
             opcionesEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
             opcionesEnviado[1]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");
			 opcionesEnviado[2]=new Opciones("Anular factura", getImg(R.drawable.anular), "Anular factura");

			opcionesNoEnviado=new Opciones[3];
             opcionesNoEnviado[0]=new Opciones("Ver Detalle", getImg(R.drawable.consultar), "Ver Detalle");
             opcionesNoEnviado[1]=new Opciones("Enviar", getImg(R.drawable.enviar), "Enviar");
             opcionesNoEnviado[2]=new Opciones("Imprimir Copia", getImg(R.drawable.pedidos), "Imprimir Copia");

             
             if(operacion==FACTURA)
             {
            	 rlListaPedidos.setBackgroundColor(0xFFE0E0E0);
            	 tvOperacionListaPedidos.setText("FACTURAS REALIZADAS");
	        	 cargarFacturas(fechaDesde, fechaHasta); 
	        	
	        	listView.setOnItemLongClickListener (new OnItemLongClickListener() {
	            	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
	            	  {      		  			
	            		  		ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);      		  		
	            		        AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
	            		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
	            		   	    
	            		   	    	
	            		   	    factura = listaFacturas.get((int)position);     
	            		   	    
	      		    		   	    if(factura.idCodigoExterno!=0)
	      		    		   	     {      		  
	      		    		   	    	builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
	      						      			    	if(item==0)
	      						      			    	{				      			    		
	      						      					  Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
	      						      					  	intent.putExtra("operacion", operacion);
	      						      					  	intent.putExtra("cedula", usuario.cedula);
	      						      					  	intent.putExtra("consulta", true);
	      						      						intent.putExtra("nombre", factura.nombreCliente);
	      						      						intent.putExtra("ordenVisita", factura.idCodigoInterno);
	      						      						intent.putExtra("idCliente", factura.idCliente);	      						      									      			
	      				  				      				intent.putExtra("idCodigoExterno",factura.idCodigoExterno);
	      				  				      			    intent.putExtra("idCodigoInterno",factura.idCodigoInterno);	      				  				      			   
	      						      						startActivity(intent);
	      						      			    	}	
	      						      			    	if(item==1)
	      						      			    	{
															if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)

															{
																pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
																printFactura=new PrintFactura();
																printFactura.printFactura(factura);
																pdu.dismiss();
																mostrarMensaje(printFactura.getMensaje(), "l");

															}
															else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
															{
																try
																{
																	operacionBixolon="factura";
																	printBixolonsppr310();
																}catch(Exception e){
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																}
															}
															else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
															{
																try
																{
																	operacionDigitalPos="factura";
																	printDigitalPos810();
																}catch(Exception e){
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																}
															}
															else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0)
															{
																//Context context =this;
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
																				printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																				printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																			}
																			printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
																		}
																		catch (Exception e)
																		{
																			mostrarMensaje("No fue posible Enviar la impresion", "l");
																			mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																		}

																		Builder builder = null;
																		try {
																			builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
																			PrintEpson printEpson=new PrintEpson();
																			builder =printEpson.printFactura(builder, factura);
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
																			mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																		}
																	}
																	else
																	{

																	}

																	//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
																}catch(Exception e){
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


																}
															}
															else {


																PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
																try {
																	pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

																	boolean resPrint = false;
																	if (operacion == FACTURA) {
																		resPrint = pz.printFactura(factura);
																	}

																	if (resPrint) {
																		pdu.dismiss();
																		mostrarMensaje("Impresion enviada Correctamente..", "l");
																	} else {
																		pdu.dismiss();
																		mostrarMensaje("No fue posible Enviar la impresion", "l");
																	}


																} catch (Exception e) {
																	pdu.dismiss();
																	mostrarMensaje("Imp22 " + e.toString(), "l");
																}
															}//
														}
														else if (item == 2) {//
																if(factura.getAnulada().equals("1"))
																{
																	mostrarMensaje("La factura seleccionada ya fue anulada anteriormente!!", "l");
																}
																else {
																	new enviarAnulacionFactura().execute("");
																	pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Anulacion"), true, false);
																}
														}
	      						      			    	
	      						      			    	
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    }
	      		    		   	    else
	      		    		   	    {
	      					      		   	    
	      		    		   	               ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
	      		    		   	               builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
														if (item == 0) {
															Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class);
															intent.putExtra("operacion", operacion);
															intent.putExtra("cedula", usuario.cedula);
															intent.putExtra("consulta", true);
															intent.putExtra("nombre", factura.nombreCliente);
															intent.putExtra("ordenVisita", factura.idCodigoInterno);
															intent.putExtra("idCliente", factura.idCliente);
															intent.putExtra("idCodigoExterno", factura.idCodigoExterno);
															intent.putExtra("idCodigoInterno", factura.idCodigoInterno);
															startActivity(intent);
														} else if (item == 1) {
//	      						      			    		comenzarLocalizacion();
//	      						      						locManager.removeUpdates(locListener);

//	      						      			    	generarFacturaSys();
															represados = 0;
															new enviarFactura().execute("");
															pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Factura"), true, false);
														}
														if (item == 2)
														{
															if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 0& parametrosPos.getUsaPrintDigitalPos()==0)

															{
																pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
																printFactura = new PrintFactura();
																printFactura.printFactura(factura);
																pdu.dismiss();
																mostrarMensaje(printFactura.getMensaje(), "l");

															} else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 1& parametrosPos.getUsaPrintDigitalPos() == 0) {
																try {
																	operacionBixolon = "factura";
																	printBixolonsppr310();
																} catch (Exception e) {
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																}
															} else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 0& parametrosPos.getUsaPrintDigitalPos() == 1) {
																try {
																	operacionDigitalPos = "factura";
																	printDigitalPos810();
																} catch (Exception e) {
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
																}
															}  else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 1 & parametrosPos.getUsaPrintBixolon() == 0) {
																//Context context =this;
																int deviceType = Print.DEVTYPE_BLUETOOTH;
																int enabled = Print.FALSE;
																int interval = 1000;

																try {
																	//open
																	if (printer == null) {
																		printer = new Print(getApplicationContext());
																	}


																	if (printer != null) {
																		try {
																			if (printer != null) {
																				printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																				printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																			}
																			printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
																		} catch (Exception e) {
																			mostrarMensaje("No fue posible Enviar la impresion", "l");
																			mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																		}

																		Builder builder = null;
																		try {
																			builder = new Builder("TM-P80", Builder.MODEL_ANK, getApplicationContext());
																			PrintEpson printEpson = new PrintEpson();
																			builder = printEpson.printFactura(builder, factura);
																			int[] status = new int[1];
																			int[] battery = new int[1];
																			printer.sendData(builder, SEND_TIMEOUT, status, battery);


																			if (builder != null) {
																				try {
																					builder.clearCommandBuffer();
																					builder = null;
																				} catch (Exception e) {
																					builder = null;
																				}
																			}

																			printer.closePrinter();

																		} catch (Exception e) {

																			mostrarMensaje("No fue posible Enviar la impresion", "l");
																			mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

																		}
																	} else {

																	}

																	//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
																} catch (Exception e) {
																	mostrarMensaje("No fue posible Enviar la impresion", "l");
																	mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


																}
															} else {


																PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
																try {
																	pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

																	boolean resPrint = false;
																	if (operacion == FACTURA) {
																		resPrint = pz.printFactura(factura);
																	}

																	if (resPrint) {
																		pdu.dismiss();
																		mostrarMensaje("Impresion enviada Correctamente..", "l");
																	} else {
																		pdu.dismiss();
																		mostrarMensaje("No fue posible Enviar la impresion", "l");
																	}


																} catch (Exception e) {
																	pdu.dismiss();
																	mostrarMensaje("Imp22 " + e.toString(), "l");
																}
															}
													}
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    } 	
	            		   	    
	            		  	AlertDialog alert = builder.create();
	            			alert.show();
	            		  
	            		  return false;
	            		  }
	            		}); 
             }
             else if(operacion==REMISION)
			{
				rlListaPedidos.setBackgroundColor(0xFF5D9ECC);
				tvOperacionListaPedidos.setText("COTIZACIONES REALIZADOS");
				cargarRemisiones(fechaDesde, fechaHasta);

				listView.setOnItemLongClickListener (new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id)
					{
						ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);
						AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
						builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));


						remision = listaRemisiones.get((int)position);

						if(remision.idCodigoExterno!=0)
						{
							builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int item) {
											if(item==0)
											{
												Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
												intent.putExtra("operacion", operacion);
												intent.putExtra("cedula", usuario.cedula);
												intent.putExtra("consulta", true);
												intent.putExtra("nombre", remision.nombreCliente);
												intent.putExtra("ordenVisita", remision.idCodigoInterno);
												intent.putExtra("idCliente", remision.idCliente);
												intent.putExtra("idCodigoExterno",remision.idCodigoExterno);
												intent.putExtra("idCodigoInterno",remision.idCodigoInterno);
												startActivity(intent);
											}
											if(item==1)
											{
												if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)

												{
													pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
													printFactura=new PrintFactura();
													printFactura.printRemision(remision);
													pdu.dismiss();
													mostrarMensaje(printFactura.getMensaje(), "l");

												}
												else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
												{
													try
													{
														operacionBixolon="remision";
														printBixolonsppr310();
													}catch(Exception e){
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
													}
												}
												else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
												{
													try
													{
														operacionDigitalPos="remision";
														printDigitalPos810();
													}catch(Exception e){
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
													}
												}
												else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0)
												{
													//Context context =this;
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
																	printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																	printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																}
																printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
															}
															catch (Exception e)
															{
																mostrarMensaje("No fue posible Enviar la impresion", "l");
																mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

															}

															Builder builder = null;
															try {
																builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
																PrintEpson printEpson=new PrintEpson();
																builder =printEpson.printRemision(builder, remision);
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
																mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

															}
														}
														else
														{

														}

														//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
													}catch(Exception e){
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


													}
												}
												else {


													PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
													try {
														pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

														boolean resPrint = false;
														if (operacion == REMISION) {
															resPrint = pz.printRemision(remision);
														}

														if (resPrint) {
															pdu.dismiss();
															mostrarMensaje("Impresion enviada Correctamente..", "l");
														} else {
															pdu.dismiss();
															mostrarMensaje("No fue posible Enviar la impresion", "l");
														}


													} catch (Exception e) {
														pdu.dismiss();
														mostrarMensaje("Imp22 " + e.toString(), "l");
													}
												}//
											}


											dialog.cancel();
										}
									}
							);
						}
						else
						{

							ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
							builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int item) {
											if (item == 0) {
												Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class);
												intent.putExtra("operacion", operacion);
												intent.putExtra("cedula", usuario.cedula);
												intent.putExtra("consulta", true);
												intent.putExtra("nombre", remision.nombreCliente);
												intent.putExtra("ordenVisita", remision.idCodigoInterno);
												intent.putExtra("idCliente", remision.idCliente);
												intent.putExtra("idCodigoExterno", remision.idCodigoExterno);
												intent.putExtra("idCodigoInterno", remision.idCodigoInterno);
												startActivity(intent);
											} else if (item == 1) {
//	      						      			    		comenzarLocalizacion();
//	      						      						locManager.removeUpdates(locListener);

//	      						      			    	generarremisionSys();
												represados = 0;
												new enviarRemision().execute("");
												pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizacion"), true, false);
											}
											if (item == 2)
											{
												if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 0& parametrosPos.getUsaPrintDigitalPos()==0)

												{
													pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
													printFactura = new PrintFactura();
													printFactura.printRemision(remision);
													pdu.dismiss();
													mostrarMensaje(printFactura.getMensaje(), "l");

												} else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 1& parametrosPos.getUsaPrintDigitalPos() == 0) {
													try {
														operacionBixolon = "remision";
														printBixolonsppr310();
													} catch (Exception e) {
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
													}
												}else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 0 & parametrosPos.getUsaPrintBixolon() == 0& parametrosPos.getUsaPrintDigitalPos() == 1) {
													try {
														operacionDigitalPos = "remision";
														printDigitalPos810();
													} catch (Exception e) {
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
													}
												}  else if (parametrosPos.getUsaImpresoraZebra() == 0 & parametrosPos.getUsaPrintEpson() == 1 & parametrosPos.getUsaPrintBixolon() == 0) {
													//Context context =this;
													int deviceType = Print.DEVTYPE_BLUETOOTH;
													int enabled = Print.FALSE;
													int interval = 1000;

													try {
														//open
														if (printer == null) {
															printer = new Print(getApplicationContext());
														}


														if (printer != null) {
															try {
																if (printer != null) {
																	printer.setStatusChangeEventCallback(ListaPedidosActivity.this);
																	printer.setBatteryStatusChangeEventCallback(ListaPedidosActivity.this);
																}
																printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
															} catch (Exception e) {
																mostrarMensaje("No fue posible Enviar la impresion", "l");
																mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

															}

															Builder builder = null;
															try {
																builder = new Builder("TM-P80", Builder.MODEL_ANK, getApplicationContext());
																PrintEpson printEpson = new PrintEpson();
																builder = printEpson.printRemision(builder, remision);
																int[] status = new int[1];
																int[] battery = new int[1];
																printer.sendData(builder, SEND_TIMEOUT, status, battery);


																if (builder != null) {
																	try {
																		builder.clearCommandBuffer();
																		builder = null;
																	} catch (Exception e) {
																		builder = null;
																	}
																}

																printer.closePrinter();

															} catch (Exception e) {

																mostrarMensaje("No fue posible Enviar la impresion", "l");
																mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

															}
														} else {

														}

														//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
													} catch (Exception e) {
														mostrarMensaje("No fue posible Enviar la impresion", "l");
														mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


													}
												} else {


													PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
													try {
														pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

														boolean resPrint = false;
														if (operacion == REMISION) {
															resPrint = pz.printRemision(remision);
														}

														if (resPrint) {
															pdu.dismiss();
															mostrarMensaje("Impresion enviada Correctamente..", "l");
														} else {
															pdu.dismiss();
															mostrarMensaje("No fue posible Enviar la impresion", "l");
														}


													} catch (Exception e) {
														pdu.dismiss();
														mostrarMensaje("Imp22 " + e.toString(), "l");
													}
												}
											}
											dialog.cancel();
										}
									}
							);
						}

						AlertDialog alert = builder.create();
						alert.show();

						return false;
					}
				});
			}

	        else if(operacion==TRANSLADO)
	        {
	        	btBodegas.setVisibility(View.VISIBLE);
	        	rlListaPedidos.setBackgroundColor(0xFFE0E0E0);
	        	btRuteroP.setText("Traslado");
	        	tvOperacionListaPedidos.setText("TRASLADOS REALIZADOS");
	        	traslado.bodegaOrigen = new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
				traslado.bodegaDestino = new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
				cargarTraslados(fechaDesde, fechaHasta);  
				btBodegas.setText(traslado.bodegaOrigen.getBodega()+" a "+traslado.bodegaDestino.getBodega());
	        	listView.setOnItemLongClickListener (new OnItemLongClickListener() {
	            	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
	            	  {      		  			
	            		  		ListAdapter listaOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesEnviado);      		  		
	            		        AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidosActivity.this);
	            		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
	            		   	    
	            		   	    	
	            		   	    traslado = listaTraslados.get((int)position);      		   	    
	      		    		   	    if(traslado.idCodigoExterno!=0)
	      		    		   	     {      		  
	      		    		   	    	builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
	      						      			    	if(item==0)
	      						      			    	{				      			    		
	      						      					  Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
	      						      					  	intent.putExtra("operacion", operacion);
	      						      					  	intent.putExtra("cedula", usuario.cedula);
	      						      					  	intent.putExtra("consulta", true);
	      						      						intent.putExtra("idBodegaOrigen", traslado.bodegaOrigen.getIdBodega());
	      						      						intent.putExtra("idBodegaDestino", traslado.bodegaDestino.getIdBodega());
	      						      					    intent.putExtra("idCodigoExterno",traslado.idCodigoExterno);
	      				  				      			    intent.putExtra("idCodigoInterno",traslado.idCodigoInterno);	      				  				      			   
	      						      						startActivity(intent);
	      						      			    	}	
	      						      			    	if(item==1)
	      						      			    	{
			      						      			    	if(parametrosPos.getUsaImpresoraZebra()==0)
		      						      			    		{
			      						      			    	 	pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
			      													printFactura=new PrintFactura();	
			      												    printFactura.printTraslado(traslado);
			      													pdu.dismiss();	
			      													mostrarMensaje(printFactura.getMensaje(), "l");
		      						      			    		}
		      						      			    		else
		      						      			    		{
			      						      			    		pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo"), true,false);
									      			    			 PrintZebra pz=new PrintZebra(parametrosPos.getMacAdd());		         	   
									     			                if(pz.printTraslado(traslado))
									     			                {
									     			                	pdu.dismiss();
									     			                	mostrarMensaje("Impresion enviada Correctamente..", "l");
									     			                }
									     			                else
									     			                {
									     			                	pdu.dismiss();
									     			                	mostrarMensaje("No fue posible Enviar la impresion", "l");
									     			                }
		      						      			    		}
	      						      			    		
	      						      			    	}
	      						      			    	
	      						      			    	
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    }
	      		    		   	    else
	      		    		   	    {
	      					      		   	    
	      		    		   	               ListAdapter listaNoOpciones = new OpcionesAdapter(ListaPedidosActivity.this, opcionesNoEnviado);
	      		    		   	               builder.setSingleChoiceItems(listaNoOpciones, -1, new DialogInterface.OnClickListener() {
	      						      			    public void onClick(DialogInterface dialog, int item) {
	      						      			    	if(item==0)
	      						      			    	{
	      						      			    		Intent intent = new Intent(ListaPedidosActivity.this, ListaArticulosPedidoActivity.class );
	      						      					  	intent.putExtra("operacion", operacion);
	      						      					  	intent.putExtra("cedula", usuario.cedula);
	      						      					  	intent.putExtra("consulta", true);
	      						      						intent.putExtra("idBodegaOrigen", traslado.bodegaOrigen.getIdBodega());
	      						      						intent.putExtra("idBodegaDestino", traslado.bodegaDestino.getIdBodega());	      						      									      			
	      				  				      				intent.putExtra("idCodigoExterno",traslado.idCodigoExterno);
	      				  				      			    intent.putExtra("idCodigoInterno",traslado.idCodigoInterno);	      				  				      			   
	      						      						startActivity(intent);
	      						      			    	}				      			    	
	      						      			    	else if(item==1)
	      						      			    	{
	      						      			    	represados=0;
     						      						new enviarTraslado().execute("");
	      						      						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true,false);
	      						      			    	}		      			    	
	      						      			    	dialog.cancel();			        
	      						      			    }
	      						      			}
	      						      			);
	      		    		   	    } 	
	            		   	    
	            		  	AlertDialog alert = builder.create();
	            			alert.show();
	            		  
	            		  return false;
	            		  }
	            		}); 
	        }
             etapa="3";
             
             if(print & operacion==FACTURA)
             {          	 
            		 etapa="4";
                	if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
         			{
                	//	 PrintDocument(obtenerDatos.getLong("idCodigoExterno"));
         			}
                	else
                	{
                	//	PrintDocumentNFactura(obtenerDatos.getLong("NFactura"));
                	} 
            	  
             }
			else if(print & operacion==REMISION)
			{
				etapa="4";
				if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
				{
					//PrintDocument(obtenerDatos.getLong("idCodigoExterno"));
				}
				else
				{
					//PrintDocumentNRemision(obtenerDatos.getLong("NRemision"));
				}

			}
			else if(print & operacion==PEDIDO)
			{
				etapa="4";
				if(parametrosPos.isValue(parametrosPos.getImprimePedido()))
				{
				//	PrintDocumentNPedido(obtenerDatos.getLong("idCodigoInterno"));
				}


			}
        }
        
        btFecha.setOnClickListener(new OnClickListener() {		
        	public void onClick(View v) {       	
        		Intent intent = new Intent(ListaPedidosActivity.this, SelecFechasActivity.class );
    	    	intent.putExtra("cedula", usuario.cedula);
    	    	intent.putExtra("operacion", operacion);
    	    	intent.putExtra("fechaBotonDesde", fechaBotonDesde);
    	    	intent.putExtra("fechaBotonHasta", fechaBotonHasta);
				intent.putExtra("fechaDesde", fechaDesde);
				intent.putExtra("fechaHasta", fechaHasta);
				startActivityForResult(intent,SUB_ACTIVITY_SELECTFECHA );
			}			
		});
        btRuteroP.setOnClickListener(new OnClickListener() {    		
        	public void onClick(View v) { 
        		if(operacion==TRANSLADO)
        		{
        			Intent intent = new Intent(ListaPedidosActivity.this, SelecTrasladoBodegaActivity.class );
        			intent.putExtra("cedula", usuario.cedula);
        			intent.putExtra("operacion", operacion);
        			startActivity(intent);
        			finish();  
        		}
        		else
        		{
        			Intent intent = new Intent(ListaPedidosActivity.this, RuteroActivity.class);
        			intent.putExtra("cedula", usuario.cedula);
        			intent.putExtra("operacion", operacion);
        			startActivity(intent);
        			finish();  
        		}
        		  			
			}			
		});       
     
    	}
    	catch (Exception e) {
			mostrarMensaje(etapa+" tres "+e.toString(), "l");
		}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    
        getMenuInflater().inflate(R.menu.activity_lista_pedidos, menu);
        MenuItem bedMenuItem = menu.findItem(R.id.menuEnviarRepresados);
		MenuItem bedMenuItemArqueo = menu.findItem(R.id.menuPrintArqueo);
		bedMenuItemArqueo.setVisible(false);

        if(operacion==TRANSLADO)
		{
        	bedMenuItem.setTitle("Enviar Traslados Represadas");
		}
		else if(operacion==FACTURA)
		{
			bedMenuItem.setTitle("Enviar Facturas Represados");
		}
		else if(operacion==REMISION)
		{
			bedMenuItem.setTitle("Enviar Cotizaciones Represadas");
		}
		else if(operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
		{
			bedMenuItem.setTitle("Enviar movimientos Represados");
		}
		else if(operacion==VERLIBRO)
		{
			MenuItem bedMenuItem2 = menu.findItem(R.id.menuResultado);
			bedMenuItem2.setVisible(false);
			bedMenuItem.setVisible(false);
		}
		else if(operacion==CARTERA)
		{
			 MenuItem bedMenuItem2 = menu.findItem(R.id.menuResultado);
			 MenuItem bedMenuItem3 = menu.findItem(R.id.menuPrintDocument);
			 bedMenuItem2.setVisible(false);
			 //bedMenuItem3.setVisible(false);
			 bedMenuItemArqueo.setVisible(true);
			 bedMenuItem.setTitle("Enviar Pagos Represados");
		}
        return true;
    }
    /**
     * metodo que se ejecuta al seleccionar una de las opciones de menu
     */
    public boolean onOptionsItemSelected(MenuItem item) 
    {    
	switch (item.getItemId()) {
		case R.id.menuResultado:		
				Intent intent = new Intent(ListaPedidosActivity.this, VerResultadosActivity.class );
		    	intent.putExtra("cedula", usuario.cedula);
		    	intent.putExtra("operacion", operacion);
		    	intent.putExtra("fechaDesde", fechaDesde);
		    	intent.putExtra("fechaHasta", fechaHasta);
		    	intent.putExtra("fechaBotonDesde", fechaBotonDesde);
		    	intent.putExtra("fechaBotonHasta", fechaBotonHasta);
		    	if(operacion==TRANSLADO)
		    	{
			    	intent.putExtra("bodegaOrigen", traslado.bodegaOrigen.getBodega());
			    	intent.putExtra("bodegaDestino", traslado.bodegaDestino.getBodega());
		    	}
		    	startActivity(intent);
		    return true;
		case R.id.menuPrintDocument:		
			printInforme();
	    return true;
		case R.id.menuEnviarRepresados:
			idlistaRepresado=0;
			represados=0;
			if(operacion==PEDIDO)
 			{
				enviarPedidosRepresados();	
			}
			else if(operacion==FACTURA)
			{
				enviarFacturasRepresadas();
			}
			else if(operacion==REMISION)
			{
				enviarRemisionesRepresadas();
			}
			else if(operacion==CARTERA)
			{
				enviarPagosRepresadas();
			}
			else if(operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
			{
				enviarMovimientosRepresados();
			}
			else
			{
				enviarTrasladosRepresados();
			}		
			return true;
		case R.id.menuPrintArqueo:
			printInformeArqueo();
			return true;
		default:return super.onOptionsItemSelected(item);    	
    	}  	  
    }  
    
    public void enviarPagosRepresadas()
	 {
    	represados=bd.getNumeroPagosRepresados(fechaDesde,fechaHasta);
		if(represados> 0)
		{
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);       	 
       	dialog.setMessage("Actualmente existen "+represados+" Pagos represadas � Esta seguro que desea enviarlos ?");
       	dialog.setCancelable(false);
       	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
       	
       	  public void onClick(DialogInterface dialog, int which) {
       		 
       		//Obtiene lista de pagos represados  
       		listaPagosRepresados=bd.getPagosPorFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta, true);
       		pago=listaPagosRepresados.get(idlistaRepresado);
       		pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(ListaPedidosActivity.this, pago.getIdPago()));
       		idlistaPagos=0;
       		enviarPago();
       			
       	  }
       	});
       	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
       	 
       	
       	   public void onClick(DialogInterface dialog, int which) {
       	      dialog.cancel();
       	   }
       	});
       	dialog.show();			
		
		}
		else
		{
			 AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			 
			 dialog.setTitle("Informacion");
			 dialog.setMessage("No existen Pagos represadas.");
			 dialog.setIcon(R.drawable.error);
			 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
		        	
		        	  public void onClick(DialogInterface dialog, int which) {
		        		  dialog.cancel(); 
		        	  }
		        	});
			 dialog.show();
		}
		
	 }  
    public void enviarPago()
    {
    	//Obtiene lista de PagosFactura del Pago
    	
    	
    	if(pago.getListaPagosFactura().size()>0)
    	{
    		pagosFactura=pago.getListaPagosFactura().get(idlistaPagos);
    		if(bd.getValidaFacturaEnviada(pagosFactura.getNFactura(),pagosFactura.getNCaja()))// || bd.getValidaRemisionEnviada(pagosFactura.getNFactura(),pagosFactura.getNCaja()))
    		{
    			pagosFactura.setListaPagoFac(bd.getAllItemPagosFacturaPorNFactura(this,pagosFactura.getIdPagosFactura()));

    			new enviarPagoSys().execute("");
				pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pagos "+(idlistaRepresado+1)+" de "+represados), true,false);
		
    		}
    		else
    		{
    			 AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    			 
    			 dialog.setTitle("Informacion");
    			 dialog.setMessage("Para poder enviar el pago "+pago.getIdPago()+" debe previamente enviar la factura No."+pagosFactura.getNFactura()+"!!");
    			 dialog.setIcon(R.drawable.error);
    			 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
    		        	
    		        	  public void onClick(DialogInterface dialog, int which) {
    		        		  dialog.cancel(); 
    		        	  }
    		        	});
    			 dialog.show();
    		}
    		
    	}
    	
    }
    
    /**
     * metodo que se encarga de enviar todos los pedidos represados
     */
    public void enviarFacturasRepresadas()
	 {
    	represados=bd.getNumeroFacturasRepresados(fechaDesde,fechaHasta);
		if(represados> 0)
		{
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);       	 
        	dialog.setMessage("Actualmente existen "+represados+" facturas represadas , Esta seguro que desea enviarlos ?");
        	dialog.setCancelable(false);
        	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
        	
        	  public void onClick(DialogInterface dialog, int which) {
        			listaFacturasRepresadas=bd.getFacturasPorFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta, true);
        			factura = listaFacturasRepresadas.get(idlistaRepresado);
        			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
					{
	        			new enviarFactura().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
					}
        			else
        			{
        				new enviarFacturaSys().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando .Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
				
        			}
        	  }
        	});
        	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
        	 
        	
        	   public void onClick(DialogInterface dialog, int which) {
        	      dialog.cancel();
        	   }
        	});
        	dialog.show();			
		
		}
		else
		{
			 AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			 
			 dialog.setTitle("Informacion");
			 dialog.setMessage("No existen facturas represadas.");
			 dialog.setIcon(R.drawable.error);
			 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
		        	
		        	  public void onClick(DialogInterface dialog, int which) {
		        		  dialog.cancel(); 
		        	  }
		        	});
			 dialog.show();
		}
		
	 }
	public void enviarRemisionesRepresadas()
	{
		represados=bd.getNumeroRemisionesRepresadas(fechaDesde,fechaHasta);
		if(represados> 0)
		{

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("Actualmente existen "+represados+" cotizaciones represadas, Esta seguro que desea enviarlas ?");
			dialog.setCancelable(false);
			dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					listaRemisionesRepresadas=bd.getRemisionesPorFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta, true);
					remision = listaRemisionesRepresadas.get(idlistaRepresado);
					if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
					{
						new enviarRemision().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizaciones "+(idlistaRepresado+1)+" de "+represados), true,false);
					}
					else
					{
						new enviarRemisionSys().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizaciones "+(idlistaRepresado+1)+" de "+represados), true,false);

					}
				}
			});
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {


				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();

		}
		else
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Informacion");
			dialog.setMessage("No existen Cotizaciones represadas.");
			dialog.setIcon(R.drawable.error);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();
		}

	}
	public void  enviarPedidosRepresados()
	{
		represados=bd.getNumeroPedidosRepresados(fechaDesde,fechaHasta);
		if(represados> 0)
		{

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setMessage("Actualmente existen "+represados+" pedidos represados � Esta seguro que desea enviarlos ?");
			dialog.setCancelable(false);
			dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					listaPedidosRepresados=bd.getPedidosPorFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta, true);
					pedido = listaPedidosRepresados.get(idlistaRepresado);
					if (pedido.getTipoPedido().equals("E"))
					{
						new enviarPedidoInventario().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
					}
					else {
						new enviarPedido().execute("");
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
					}
				}
			});
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {


				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();

		}
		else
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Informacion");
			dialog.setMessage("No existen pedidos represados.");
			dialog.setIcon(R.drawable.error);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();
		}

	}
	public void  enviarMovimientosRepresados()
  	 {
      	represados=bd.getNumeroLibrosRepresados(fechaDesde,fechaHasta);
  		if(represados> 0)
  		{
  			
  			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
         	 
          	dialog.setMessage("Actualmente existen "+represados+" movimientos represados . Esta seguro que desea enviarlos ?");
          	dialog.setCancelable(false);
          	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
          	
          	  public void onClick(DialogInterface dialog, int which) {
          			listaLibrosRepresados=bd.getMovimientosXFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta, true);
          			libro = listaLibrosRepresados.get(idlistaRepresado);
          			new enviarLibro().execute("");
  					pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando movimientos "+(idlistaRepresado+1)+" de "+represados), true,false);
          	  }
          	});
          	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
          	 
          	
          	   public void onClick(DialogInterface dialog, int which) {
          	      dialog.cancel();
          	   }
          	});
          	dialog.show();			
  		
  		}
  		else
  		{
  			 AlertDialog.Builder dialog = new AlertDialog.Builder(this);
  			 
  			 dialog.setTitle("Informacion");
  			 dialog.setMessage("No existen movimientos represados.");
  			 dialog.setIcon(R.drawable.error);
  			 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
  		        	
  		        	  public void onClick(DialogInterface dialog, int which) {
  		        		  dialog.cancel(); 
  		        	  }
  		        	});
  			 dialog.show();
  		}
  		
  	 }  
    public void  enviarTrasladosRepresados()
 	 {
     	represados=bd.getNumeroTrasladosRepresados(fechaDesde,fechaHasta);
 		if(represados> 0)
 		{
 			
 			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        	 
         	dialog.setMessage("Actualmente existen "+represados+" traslados represados � Esta seguro que desea enviarlos ?");
         	dialog.setCancelable(false);
         	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
         	
         	  public void onClick(DialogInterface dialog, int which) {
         			listaTrasladosrepresados=bd.getTrasladosPorFecha(ListaPedidosActivity.this, fechaDesde,fechaHasta,true,traslado.bodegaOrigen.getIdBodega(),traslado.bodegaDestino.getIdBodega());
         			traslado = listaTrasladosrepresados.get(idlistaRepresado);
         			new enviarTraslado().execute("");
 					pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando traslados "+(idlistaRepresado+1)+" de "+represados), true,false);						
         	  }
         	});
         	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
         	 
         	
         	   public void onClick(DialogInterface dialog, int which) {
         	      dialog.cancel();
         	   }
         	});
         	dialog.show();			
 		
 		}
 		else
 		{
 			 AlertDialog.Builder dialog = new AlertDialog.Builder(this);
 			 
 			 dialog.setTitle("Informacion");
 			 dialog.setMessage("No existen traslados represados.");
 			 dialog.setIcon(R.drawable.error);
 			 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
 		        	
 		        	  public void onClick(DialogInterface dialog, int which) {
 		        		  dialog.cancel(); 
 		        	  }
 		        	});
 			 dialog.show();
 		}
 		
 	 }
	public void cargarPagoPrestamos(String fechaDesde, String fechaHasta)
	{
		listaPagoPrestamos=bd.getPagosPrestamoXFecha(this, fechaDesde,fechaHasta, false);
		listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,null,null,null,listaPagoPrestamos,null,null,parametrosPos));
	}
	public void cargarMovimientos(String fechaDesde, String fechaHasta)
	{
		listaLibros=bd.getMovimientosXFecha(this, fechaDesde,fechaHasta, false);
		listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,null,null,null,null,listaLibros,null,parametrosPos));
	}

	public void cargarMovimientosXCliente(String fechaDesde, String fechaHasta,Cliente cliente)
	{
		listaLibros=bd.getMovimientosXFechaXCliente(this, fechaDesde,fechaHasta, cliente);
		listView.setAdapter(new ListaLibroAdapter(this,R.layout.activity_item_libro,listaLibros));
	}

	public void cargarPrestamos(String fechaDesde, String fechaHasta)
	{
		listaPrestamos=bd.getPrestamosXFecha(this, fechaDesde,fechaHasta, false);
		listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,null,null,listaPrestamos,null,null,null,parametrosPos));
	}
    
    public void cargarPagos(String fechaDesde, String fechaHasta)
  	{   	
  		  listaPagos=bd.getPagosPorFecha(this, fechaDesde,fechaHasta, false);
          listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,null,listaPagos,null,null,null,null,parametrosPos));
  	}
      
    public void cargarPedidos(String fechaDesde, String fechaHasta)
	{   	
		listaPedidos=bd.getPedidosPorFecha(this, fechaDesde,fechaHasta, false);
        listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,listaPedidos,null,null,null,null,null,null,null,parametrosPos));
	}
    
    public void cargarFacturas(String fechaDesde, String fechaHasta)
	{   	
    	listaFacturas=bd.getFacturasPorFecha(this, fechaDesde,fechaHasta, false);
        listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,listaFacturas,null,null,null,null,null,null,parametrosPos));
	}
	public void cargarRemisiones(String fechaDesde, String fechaHasta)
	{
		listaRemisiones=bd.getRemisionesPorFecha(this, fechaDesde,fechaHasta, false) ;
		listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,null,null,null,null,null,listaRemisiones,parametrosPos));
	}
    
    public void cargarTraslados(String fechaDesde, String fechaHasta)
	{   	
		listaTraslados=bd.getTrasladosPorFecha(this, fechaDesde,fechaHasta,false,traslado.bodegaOrigen.getIdBodega(),traslado.bodegaDestino.getIdBodega());
        listView.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,null,listaTraslados,null,null,null,null,null,parametrosPos));
	}
    
    public boolean borrarPedido()
	{
		
		if(bd.eliminarPedido(pedido.idCodigoInterno, pedido.idCodigoExterno))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
    /**
     * metodo que muestra un mensaje al cliente
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
	 * metodo que borrra un pedido de la lista
	 * @return
	 */
	
	
	/**
	 * metodo que genera el pedido para enviar al servidor
	 */
	public void generarPedido()
	{
		
		listaAPedido=bd.getArticulosPedido(pedido.idCodigoInterno);
		//pedidoEnviar.NPedido=0;
		pedidoEnviar.NPedido=pedido.idCodigoInterno;
		pedidoEnviar.IdCliente=pedido.idCliente;
		pedidoEnviar.IdClienteSucursal=pedido.idClienteSucursal;
		pedidoEnviar.CedulaVendedor=usuario.cedula;		
		ClsArtsPedido ls=new ClsArtsPedido ();		
		for (int i = 0; i < listaAPedido.size(); i++) 
		{
			 ArticulosPedido ap=new ArticulosPedido();
			 ap=listaAPedido.get(i);
			 ClsArtPedido cap=new ClsArtPedido();
			 cap.IdArticulo=ap.idArticulo;
			 cap.Cantidad=ap.cantidad;
			 cap.ValorUnitario=ap.valorUnitario;
			 ls.add(cap);
		}
		pedidoEnviar.Articulo=ls;	
		pedidoEnviar.Observaciones=pedido.observaciones;
		pedidoEnviar.Latitud="0";
		pedidoEnviar.Longitud="0";
	
		
	}
	/**
	 * metodo que guarda el pedido enviado en la base de datos del telefono
	 */
	public void guardarPedido()
	{
		
		creaBD bd=new creaBD(this);
		bd.openDB();
		if(pedido.getEnvio()!=0)
		{
			bd.ActualizarPedido(pedido);
			cargarPedidos(fechaDesde,fechaHasta);
			if(represados !=0)
			{
			
				enviarSiguientePedido();
			}
			else
			{
				mostrarMensaje("Pedidos Enviados Correctamente.", "s");				
			}
		}
		else
		{
			mostrarMensaje("No Fue Posible enviar el pedido, Fue almacenado en el telefono temporalmente ","l");
			cargarPedidos(fechaDesde,fechaHasta);
		}
		bd.closeDB();
		
		
		
	}
	public void guardarLibro()
	{

		creaBD bd=new creaBD(this);
		bd.openDB();
		if(libro.getEnviado()!=0)
		{
			bd.ActualizarLibro(libro);
			cargarMovimientos(fechaDesde,fechaHasta);
			if(represados !=0)
			{

				enviarSiguienteLibro();
			}
			else
			{
				mostrarMensaje("Movimientos Enviados Correctamente.", "s");
			}
		}
		else
		{
			mostrarMensaje("No Fue Posible enviar el libro, Fue almacenado en el telefono temporalmente ","l");
			cargarMovimientos(fechaDesde,fechaHasta);
		}
		bd.closeDB();



	}

	
	/**
	 * Atributo dateSetListener referente a la clase DatePickerDialog.OnDateSetListener  utilizado para seleccionar
	 * una fecha determinada
	 */
//	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//	   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
//	   {
//			     Calendar c = Calendar.getInstance();
//		         c.set(year, monthOfYear, dayOfMonth);
//		         fecha=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;		         
//		         SimpleDateFormat sdfBoton=new SimpleDateFormat("dd/MM/yyyy"); 
//		         fechaBoton=sdfBoton.format(c.getTime());
//		         btFecha.setText(fechaBoton);
//		         if(operacion==PEDIDO)
//		         {
//		        	 cargarPedidos(fecha);
//		         }
//		         else if(operacion==FACTURA)
//		         {
//		        	 cargarFacturas(fecha);
//		         }
//		         else
//		         {
//		        	 cargarTraslados(fecha);
//		         }		         		
//		}
//	};
	/**
	 * metodo que crea el dialogo para seleccionar la una fecha de terminada
	 */
//	protected Dialog onCreateDialog(int id)
//	{
//		switch(id)
//		{
//		case DATE_DIALOG_ID : return new DatePickerDialog(this, dateSetListener, ano, (mes-1), dia);
//		}
//		return null;
//		
//	}	
	


	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
	
	/**
	 * metodo que obtiene la locaciozacion del telefono
	 */
	 private void comenzarLocalizacion()
	    {
	    	//Obtenemos una referencia al LocationManager
	    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	
	    	//Obtenemos la �ltima posici�n conocida
	    	//Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	//
	    	
	    	//Mostramos la �ltima posici�n conocida
	    	//mostrarPosicion(loc);
	    	
	    	//Nos registramos para recibir actualizaciones de la posici�n
	    	locListener = new LocationListener() {
		    	public void onLocationChanged(Location location) {
		    		mostrarPosicion(location);
		    	}
		    	public void onProviderDisabled(String provider){
		    		
		    	}
		    	public void onProviderEnabled(String provider){
		    		
		    	}
		    	public void onStatusChanged(String provider, int status, Bundle extras){
		    		Log.i("", "Provider Status: " + status);
		    		
		    	}
	    	};
	    	
	    	//locManager.requestLocationUpdates(			//LocationManager.GPS_PROVIDER, 30000, 0, locListener);
	    }
	  
	  /**
	   * metodo que guarda la nueva localizacion obtenida del telefono en el pedido que se enviara
	   * @param loc
	   */
	  private void mostrarPosicion(Location loc) {
	    	if(loc != null)
	    	{
	    		pedidoEnviar.Latitud= String.valueOf(loc.getLatitude());
				pedidoEnviar.Longitud= String.valueOf(loc.getLongitude());
	    	}
	    	else
	    	{
	    		pedidoEnviar.Latitud = "sin_datos";
	    		pedidoEnviar.Longitud = "sin_datos";
	    		
	    	}
	  }

	private class enviarLibro extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params)
		{

				PutLibro putLibro=new PutLibro(parametrosSys.getIp(),parametrosSys.getWebidText());
//					pedido.idCodigoExterno=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
				long idLibro=putLibro.setLibro(getXmlLibro());
				libro.setEnviado(0);
				if(idLibro>0)
				{
					libro.setEnviado(1);
				}
			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			guardarLibro();
		}

	}
	  /**
	   * Clase que se encarga de enviar el pedido al servidor en un proceso en segundo plano
	   * @author user
	   *
	   */
	  private class enviarPedido extends AsyncTask<String, Void, Object>
		{

			@Override
			protected Integer doInBackground(String... params) 
			{
				if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
					generarPedido();			
					EnviarPedido e=new EnviarPedido(parametrosPos.getIp());
//					pedido.idCodigoExterno=e.getEnviarPedido(pedidoEnviar);	
					
					long npedido=e.getEnviarPedido(pedidoEnviar);	
					pedido.setEnvio(0);
					if(npedido>0)
					{
						pedido.idCodigoExterno=npedido;
						pedido.setEnvio(1);
					}		
					
				}
				else
				{
					generarPedidoSys();
					PutPedidoSys putPedidoSys=new PutPedidoSys(parametrosSys.getIp(),parametrosSys.getWebidText());
//					pedido.idCodigoExterno=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
					long npedido=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
					pedido.setEnvio(0);
					pedido.setTipoPedido("N");
					if(npedido>0)
					{
						pedido.idCodigoExterno=npedido;
						pedido.setEnvio(1);
					}		
					
					LlamarFechaSys llamarFecha=new LlamarFechaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
					String fechaSys=llamarFecha.getFecha();
					if(!fechaSys.equals("Error"))
					{
						//pedido.setFecha(fechaSys);
					}
				}
				return 1;
			}
			
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();	
				guardarPedido();
			}
			
		}

	private class enviarPedidoInventario extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params)
		{
			generarPedidoSys();
			PutPedidoSys putPedidoSys=new PutPedidoSys(parametrosSys.getIp(),parametrosSys.getWebidText());

			//pedido.idCodigoExterno=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
			//activa parametro envio del pedido
			long npedido=putPedidoSys.setPedidoInventario(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
			pedido.setEnvio(0);
			pedido.setEstado("1");
			pedido.setTipoPedido("E");
			if(npedido>0)
			{
				pedido.idCodigoExterno=npedido;
				pedido.setEnvio(1);
			}

			LlamarFechaSys llamarFecha=new LlamarFechaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
			String fechaSys=llamarFecha.getFecha();
			if(!fechaSys.equals("Error"))
			{
				pedido.setFecha(fechaSys);
			}




			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			guardarPedido();
		}

	}
	  
	  public void generarPedidoSys()
		{
			
		 listaAPedido=bd.getArticulosPedido(pedido.idCodigoInterno);
			//pedidoEnviarSys.setNPedido(""+pedido.getIdCodigoExterno());
			pedidoEnviarSys.setNPedido(""+pedido.idCodigoInterno);
			pedidoEnviarSys.setIdCliente(""+pedido.idCliente);
			pedidoEnviarSys.setIdClienteSucursal(""+pedido.idClienteSucursal);
			pedidoEnviarSys.setIdVendedor(usuario.cedula);
			pedidoEnviarSys.setNMesa("0");
			pedidoEnviarSys.setTotalPedido(""+pedido.getValor());
			pedidoEnviarSys.setListaArticulos(listaAPedido);
			pedidoEnviarSys.setNCaja(""+parametrosPos.getCaja());
			try
			{	pedidoEnviarSys.setObservaciones("Ninguna");}
			catch(Exception e)
			{  pedidoEnviarSys.setObservaciones("Ninguna"); }
			pedidoEnviarSys.setObservaciones(pedido.observaciones);
			pedidoEnviarSys.setSubTotal(pedido.getSubTotal()+"");
			pedidoEnviarSys.setDescuentoTotal(pedido.getDescuentoTotal()+"");
			pedidoEnviarSys.setDocumento(pedido.getDocumento());
			pedidoEnviarSys.setFormaPago(pedido.getFormaPago());
			pedidoEnviarSys.setFecha(pedido.getFecha());
			pedidoEnviarSys.setHora(pedido.getHora());
			pedidoEnviarSys.setLatitud(pedido.getLatitud());
			pedidoEnviarSys.setLongitud(pedido.getLongitud());
		}
	  
	  
	  
	  public String getXmlPedido()
		{
		 
			String xml="";
			xml="<PedidoMesa>\n";		
			xml +="<Datos>\n";
			for (int j = 0; j < pedidoEnviarSys.getPropertyCount(); j++) {
				xml +="		<"+pedidoEnviarSys.getPropertyName(j)+">"+pedidoEnviarSys.getProperty(j)+"</"+pedidoEnviarSys.getPropertyName(j)+">\n";				
			}
			xml +="</Datos>\n";			
			
			xml +="</PedidoMesa>";
			return xml;
		}
	public String getXmlLibro()
	{
		libro.setIdVendedor(usuario.cedula);
		String xml="";
		xml="<PedidoMesa>\n";
		xml +="<Datos>\n";
		for (int j = 0; j < libro.getPropertyCount(); j++) {
			xml +="		<"+libro.getPropertyName(j)+">"+libro.getProperty(j)+"</"+libro.getPropertyName(j)+">\n";
		}
		xml +="</Datos>\n";

		xml +="</PedidoMesa>";
		return xml;
	}
	  
	  /**
	   * metodo que se encarga de enviar el siguiente pedido de la lista de pedidos represados
	   */
	  private void enviarSiguientePedido()
	  {
		  idlistaRepresado++;
		  if(idlistaRepresado<listaPedidosRepresados.size())
		  {			
			  pedido = listaPedidosRepresados.get(idlistaRepresado);
			  if (pedido.getTipoPedido().equals("E"))
			  {
				  new enviarPedidoInventario().execute("");
				  pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
			  }
			  else {
				  new enviarPedido().execute("");
				  pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
			  }
			 // new enviarPedido().execute("");
			 // pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedidos "+(idlistaRepresado+1)+" de "+represados), true,false);
		  }
		  else
		  {
			  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				 
				 dialog.setTitle("Informacion");
				 dialog.setMessage("Pedidos Enviados Correctamente.");
				 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
			        	
			        	  public void onClick(DialogInterface dialog, int which) {
			        		  dialog.cancel(); 
			        	  }
			        	});
				 dialog.show();
				 cargarPedidos(fechaDesde,fechaHasta);
		  }
				
	  }
	private void enviarSiguienteLibro()
	{
		idlistaRepresado++;
		if(idlistaRepresado<listaLibrosRepresados.size())
		{
			libro = listaLibrosRepresados.get(idlistaRepresado);
			new enviarLibro().execute("");
			pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Movimientos "+(idlistaRepresado+1)+" de "+represados), true,false);
		}
		else
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Informacion");
			dialog.setMessage("Movimientos Enviados Correctamente.");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();
			cargarMovimientos(fechaDesde,fechaHasta);
		}

	}
	  
	  private void enviarSiguienteFactura()
	  {
		  idlistaRepresado++;
		  if(idlistaRepresado<listaFacturasRepresadas.size())
		  {			
			  factura = listaFacturasRepresadas.get(idlistaRepresado);
			  if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
      				new enviarFactura().execute("");
					pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
				}
	  			else
	  			{
	  				new enviarFacturaSys().execute("");
					pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando .Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
				
	  			}
//			  new enviarFactura().execute("");
//			  pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
		  }
		  else
		  {
			  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				 
				 dialog.setTitle("Informacion");
				 dialog.setMessage("Facturas Enviados Correctamente.");
				 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
			        	
			        	  public void onClick(DialogInterface dialog, int which) {
			        		  dialog.cancel(); 
			        	  }
			        	});
				 dialog.show();
				 cargarFacturas(fechaDesde,fechaHasta);
		  }
				
	  }
	private void enviarSiguienteRemision()
	{
		idlistaRepresado++;
		if(idlistaRepresado<listaRemisionesRepresadas.size())
		{
			remision = listaRemisionesRepresadas.get(idlistaRepresado);
			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				new enviarRemision().execute("");
				pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizaciones "+(idlistaRepresado+1)+" de "+represados), true,false);
			}
			else
			{
				new enviarRemisionSys().execute("");
				pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizaciones "+(idlistaRepresado+1)+" de "+represados), true,false);

			}
//			  new enviarFactura().execute("");
//			  pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Facturas "+(idlistaRepresado+1)+" de "+represados), true,false);
		}
		else
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);

			dialog.setTitle("Informacion");
			dialog.setMessage("Cotizaciones Enviadas Correctamente.");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();
			cargarRemisiones(fechaDesde,fechaHasta);
		}

	}
	  private void enviarSiguientePago()
	  {
		  idlistaRepresado++;
		  if(idlistaRepresado<listaPagosRepresados.size())
		  {		
			 
			  pago=listaPagosRepresados.get(idlistaRepresado);
	       	  idlistaPagos=0;
	       	  pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(this, pago.getIdPago()));
	       	  enviarPago();
		  }
		  else
		  {
			  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				 
				 dialog.setTitle("Informacion");
				 dialog.setMessage("Pagos Enviados Correctamente.");
				 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
			        	
			        	  public void onClick(DialogInterface dialog, int which) {
			        		  dialog.cancel(); 
			        	  }
			        	});
				 dialog.show();
				 cargarPagos(fechaDesde,fechaHasta);
		  }
				
	  }
	  
	  
	  private void enviarSiguienteTraslado()
	  {
		  idlistaRepresado++;
		  if(idlistaRepresado<listaTrasladosrepresados.size())
		  {			
			  traslado = listaTrasladosrepresados.get(idlistaRepresado);
			  new enviarTraslado().execute("");
			  pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Traslados "+(idlistaRepresado+1)+" de "+represados), true,false);
		  }
		  else
		  {
			  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				 
				 dialog.setTitle("Informacion");
				 dialog.setMessage("Traslados Enviados Correctamente.");
				 dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  	 
			        	
			        	  public void onClick(DialogInterface dialog, int which) {
			        		  dialog.cancel(); 
			        	  }
			        	});
				 dialog.show();
				 cargarTraslados(fechaDesde,fechaHasta);
		  }
				
	  }
	  
	  /**
		  * Clase en la que se envia el pedido al sistema de georeferenciacion en un proceso en segundo plano
		  * @author Javier
		  *
		  */
		 private class setAnularPedido extends AsyncTask<String, Void, Object>
			{
				String  res ="";		
				@Override
					protected Integer doInBackground(String... params) 
					{	
						EnviarAnulacion enviar=new EnviarAnulacion(parametrosPos.getIp());
						isanulado=enviar.getAnularPedido(pedido.idCodigoExterno, usuario.cedula);
		    	 	   	return 1;	
					}
				
				
					protected void onPostExecute(Object result)
					{
						pdu.dismiss();
						 if(isanulado)
				    	    {
			    	 	    	borrarPedido();
			    	 	    	mostrarMensaje("Pedido Anulado Correctamente", "l");
				    			cargarPedidos(fechaDesde,fechaHasta);
				    			
				    		}
				    		else
				    		{
				    			mostrarMensaje("El Pedido No fue Anulado", "l");
				    			cargarPedidos(fechaDesde,fechaHasta);
				    		}									
					}			
			}
		 
		 private class enviarFactura extends AsyncTask<String, Void, Object>
			{
				String rest="";

				@Override
				protected Integer doInBackground(String... params) 
				{
					if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
					{
						generarFactura();			
						EnviarFactura e= new EnviarFactura(parametrosPos.getIp());
						factura=e.getEnviarFactura(facturaEnviar,factura);
						LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
						String fechaSys=llamarFecha.getFecha();
						if(!fechaSys.equals("Error"))
						{
							factura.setFecha(fechaSys);
						}
					}
					else
					{
						generarFacturaSys();						 
						PutFacturaSys putFacturaSys=new PutFacturaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
						factura=putFacturaSys.setFactura(getXmlFactura(), facturaEnviarsys.getXmlArticulos(),factura);
						rest=putFacturaSys.getRes();
					}
					return 1;
				}
				
				protected void onPostExecute(Object result)
				{
					pdu.dismiss();
					//mostrarMensaje("9* "+rest,"l");
				    guardarFactura();			
				}
				
			}

	private class enviarAnulacionFactura extends AsyncTask<String, Void, Object>
	{
		String rest="";

		@Override
		protected Integer doInBackground(String... params)
		{

				PutFacturaSys putFacturaSys=new PutFacturaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
				factura=putFacturaSys.setAnularFactura(factura);
				rest=putFacturaSys.getRes();

			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			guardarAnulacionFactura();
		}

	}

	private class enviarAnulacionPedido extends AsyncTask<String, Void, Object>
	{
		String rest="";

		@Override
		protected Integer doInBackground(String... params)
		{

			PutPedidoSys putPedidoSys=new PutPedidoSys(parametrosSys.getIp(),parametrosSys.getWebidText());
			long res=putPedidoSys.setAnularPedido(""+parametrosPos.getCaja(),""+pedido.idCodigoInterno,""+ parametrosSys.getBodegaPedidosOmision());
			if (res>0) {
				pedido.setEstado("3");
			}

			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			guardarAnulacionPedido();
		}

	}
	private class enviarRemision extends AsyncTask<String, Void, Object>
	{
		String rest="";

		@Override
		protected Integer doInBackground(String... params)
		{
			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				generarRemision();
				EnviarRemision e= new EnviarRemision(parametrosPos.getIp());
				remision=e.getEnviarRemision(remisionEnviar,remision);
				LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
				String fechaSys=llamarFecha.getFecha();
				if(!fechaSys.equals("Error"))
				{
					remision.setFecha(fechaSys);
				}
			}
			else
			{
				generarRemisionSys();
				PutRemisionSys putRemisionSys=new PutRemisionSys(parametrosSys.getIp(),parametrosSys.getWebidText());
				remision=putRemisionSys.setRemision(getXmlRemision() , remisionEnviarsys.getXmlArticulos(),remision);
				rest=putRemisionSys.getRes();
			}
			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			//mostrarMensaje("9* "+rest,"l");
			guardarRemision();
		}

	}
		 private class enviarPagoSys extends AsyncTask<String, Void, Object>
			{
			 

				@Override
				protected Integer doInBackground(String... params) 
				{
						
						PutPagosFacturaSys putPagosFacturaSys=new PutPagosFacturaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
						pagosFactura=putPagosFacturaSys.setPagosFactura(getXmlDatosFac(), pagosFactura);

					return 1;
				}
				
				protected void onPostExecute(Object result)
				{
					pdu.dismiss();
					guardarPagoFactura();							
				}
				
			}
		 
		 private class enviarFacturaSys extends AsyncTask<String, Void, Object>
			{
			 	String rest="";

				@Override
				protected Integer doInBackground(String... params) 
				{
						generarFacturaSys();
						PutFacturaSys putFacturaSys=new PutFacturaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
						factura=putFacturaSys.setFactura(getXmlFactura(), facturaEnviarsys.getXmlArticulos(),factura);
					rest=putFacturaSys.getRes();

					return 1;
				}
				
				protected void onPostExecute(Object result)
				{
					pdu.dismiss();
					//mostrarMensaje(rest,"l");
					guardarFactura();							
				}
				
			}
	private class enviarRemisionSys extends AsyncTask<String, Void, Object>
	{
		String rest="";

		@Override
		protected Integer doInBackground(String... params)
		{
			generarRemisionSys();
			PutRemisionSys putRemisionSys=new PutRemisionSys(parametrosSys.getIp(),parametrosSys.getWebidText());
			remision=putRemisionSys.setRemision(getXmlRemision(), remisionEnviarsys.getXmlArticulos(),remision);
			rest=putRemisionSys.getRes();

			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			//mostrarMensaje(rest,"l");
			guardarRemision();
		}

	}
		 public String getXmlFactura()
	{

		String xml="";
		xml="<Factura>\n";
		xml +="<Datos>\n";
		for (int j = 0; j < facturaEnviarsys.getPropertyCount(); j++) {
			xml +="		<"+facturaEnviarsys.getPropertyName(j)+">"+facturaEnviarsys.getProperty(j)+"</"+facturaEnviarsys.getPropertyName(j)+">\n";
		}
		xml +="</Datos>\n";

		xml +="</Factura>";
		return xml;
	}

	public String getXmlRemision()
	{

		String xml="";
		xml="<Remision>\n";
		xml +="<Datos>\n";
		for (int j = 0; j < remisionEnviarsys.getPropertyCount(); j++) {
			xml +="		<"+remisionEnviarsys.getPropertyName(j)+">"+remisionEnviarsys.getProperty(j)+"</"+remisionEnviarsys.getPropertyName(j)+">\n";
		}
		xml +="</Datos>\n";

		xml +="</Remision>";
		return xml;
	}
	public boolean validaPagoFactura()
	{
		boolean res=false;


		return res;
	}

	public String getXmlDatosFac()
	{


		String xml="";

		xml="<Posstar7>\n";
		xml +="<DatosPagoFactura>\n";
		for (int i=0;i<pago.getListaPagosFactura().size();i++){
			pagosFactura=pago.getListaPagosFactura().get(i);

			//Asigna numero de caja asignada al vendedor
			pagosFactura.setNCajaQRecibe(parametrosSys.getCaja());
			pagosFactura.setIdVendedor(usuario.cedula);
			pagosFactura.setCuenta(1001);

			xml +="<Datos>\n";
			for (int j = 0; j < pagosFactura.getPropertyCount(); j++) {
				if(pagosFactura.getPropertyName(j).equals("listaPagoFac"))
				{
					xml += "		<" + pagosFactura.getPropertyName(j) + ">\n";
					for (int m = 0; m < pagosFactura.getListaPagoFac().size(); m++) {
						ItemPagoFac ipf= pagosFactura.getListaPagoFac().get(m);
						xml +="<Pago>\n";
						for (int n = 0; n < ipf.getPropertyCount(); n++) {
							xml +="		<"+ipf.getPropertyName(n)+">"+ipf.getProperty(n)+"</"+ipf.getPropertyName(n)+">\n";
						}
						xml +="</Pago>\n";
					}
					xml += "	    </" + pagosFactura.getPropertyName(j) + ">\n";
				}
				else {
					xml += "		<" + pagosFactura.getPropertyName(j) + ">" + pagosFactura.getProperty(j) + "</" + pagosFactura.getPropertyName(j) + ">\n";
				}
			}
			xml +="</Datos>\n";
		}

		xml +="</DatosPagoFactura>\n";

		xml +="</Posstar7>";


		return xml;
	}
		 
		 public void generarFacturaSys()
			{
			 try
			 {
			 	listaAFactura=bd.getArticulosFactura(factura.idCodigoInterno);
				facturaEnviarsys.setNCaja(""+factura.getNCaja());	
				facturaEnviarsys.setIdCliente(""+factura.idCliente);
				 facturaEnviarsys.setIdClienteSucursal(""+factura.idClienteSucursal);
				facturaEnviarsys.setCedulaVendedor(usuario.cedula);	
				facturaEnviarsys.setLatitud("sin_datos");
				facturaEnviarsys.setLongitud("sin_datos");
				facturaEnviarsys.setVentaCredito(""+factura.VentaCredito);
				facturaEnviarsys.setFecha(factura.getFecha());
				facturaEnviarsys.setFecha2(factura.getFechaServer());
				facturaEnviarsys.setHora(factura.getHora());
				facturaEnviarsys.setListaArticulos(listaAFactura);
				facturaEnviarsys.setObservaciones(factura.observaciones);
				facturaEnviarsys.setMedioDePago(factura.MedioDePago);
				
				if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
			 	{
					facturaEnviarsys.setNFactura("0");
				}
			 	else
			 	{
			 		facturaEnviarsys.setNFactura(""+factura.NFactura);
			 	}			
			 }
			 catch (Exception e) {
				 pdu.dismiss();	
				mostrarMensaje(e.toString(), "l");
			}
				
			
		}

	public void generarRemisionSys()
	{
		try
		{
			listaARemision=bd.getArticulosRemision(remision.idCodigoInterno);
			remisionEnviarsys.setNCaja(""+parametrosPos.getCajaRem());
			remisionEnviarsys.setIdCliente(""+remision.idCliente);
			remisionEnviarsys.setIdClienteSucursal(""+remision.idClienteSucursal);
			remisionEnviarsys.setCedulaVendedor(usuario.cedula);
			remisionEnviarsys.setLatitud("sin_datos");
			remisionEnviarsys.setLongitud("sin_datos");
			remisionEnviarsys.setVentaCredito(""+remision.VentaCredito);
			remisionEnviarsys.setFecha(remision.getFecha());
			remisionEnviarsys.setFecha2(remision.getFechaServer());
			remisionEnviarsys.setHora(remision.getHora());
			remisionEnviarsys.setListaArticulos(listaARemision);
			remisionEnviarsys.setObservaciones(remision.observaciones);
			remisionEnviarsys.setMedioDePago(remision.MedioDePago);

			if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
			{
				remisionEnviarsys.setNRemision("0");
			}
			else
			{
				remisionEnviarsys.setNRemision(""+remision.NRemision);
			}
		}
		catch (Exception e) {
			pdu.dismiss();
			mostrarMensaje(e.toString(), "l");
		}


	}
		 public void generarFactura()
			{
				listaAFactura=bd.getArticulosFactura(factura.idCodigoInterno);
				facturaEnviar.NCaja=parametrosPos.getCaja();	
				facturaEnviar.IdCliente=factura.idCliente;
				facturaEnviar.IdClienteSucursal=factura.idClienteSucursal;
				facturaEnviar.CedulaVendedor=usuario.cedula;	
				ArrayList<ArticulosFactura> listaAPTem=new ArrayList<ArticulosFactura>();
				listaAPTem.add(listaAFactura.get(0));
				for (int i = 0; i < listaAFactura.size(); i++) {
					listaAPTem.add(listaAFactura.get(i));
				}
				ClsArtsFactura ls=new ClsArtsFactura ();
				for (int i = 0; i < listaAPTem.size(); i++) 
				{
					 ArticulosFactura ap=new ArticulosFactura();
					 ap=listaAPTem.get(i);
					 ClsArtFactura cap=new ClsArtFactura();
					 cap.IdArticulo=ap.idArticulo;
					 cap.Cantidad=ap.cantidad;
					 cap.ValorUnitario=ap.valorUnitario;
					 ls.add(cap);
				}
				facturaEnviar.Articulo=ls;
				facturaEnviar.Latitud="sin_datos";
				facturaEnviar.Longitud="sin_datos";
				
				try
				{	facturaEnviar.Observaciones="";}
				catch(Exception e)
				{  facturaEnviar.Observaciones="Ninguna"; }
			}

	public void generarRemision()
	{
		listaARemision=bd.getArticulosRemision(remision.idCodigoInterno);
		remisionEnviar.NCaja=parametrosPos.getCajaRem();
		remisionEnviar.IdCliente=remision.idCliente;
		remisionEnviar.IdClienteSucursal=remision.idClienteSucursal;
		remisionEnviar.CedulaVendedor=usuario.cedula;
		ArrayList<ArticulosRemision> listaAPTem=new ArrayList<ArticulosRemision>();
		listaAPTem.add(listaARemision.get(0));
		for (int i = 0; i < listaARemision.size(); i++) {
			listaAPTem.add(listaARemision.get(i));
		}
		ClsArtsRemision ls=new ClsArtsRemision ();
		for (int i = 0; i < listaAPTem.size(); i++)
		{
			ArticulosRemision ap=new ArticulosRemision();
			ap=listaAPTem.get(i);
			ClsArtRemision cap=new ClsArtRemision();
			cap.IdArticulo=ap.idArticulo;
			cap.Cantidad=ap.cantidad;
			cap.ValorUnitario=ap.valorUnitario;
			ls.add(cap);
		}
		remisionEnviar.Articulo=ls;
		remisionEnviar.Latitud="sin_datos";
		remisionEnviar.Longitud="sin_datos";

		try
		{	remisionEnviar.Observaciones="";}
		catch(Exception e)
		{  remisionEnviar.Observaciones="Ninguna"; }
	}
		 public void guardarPagoFactura()
			{	
			 try
				{
				if(pagosFactura.getNPagoFac()!=0)
				{
					// ACTUALIZA PAGO FACTURA
					bd.ActualizarPagoFactura(pagosFactura);
					idlistaPagos++;
					//valida si hay mas pagos
					if(idlistaPagos<pago.getListaPagosFactura().size())
					{
						enviarPago();
					}
					else
					{
						
						pago.setEnviado(1);
						
						bd.ActualizarPago(pago);
						cargarPagos(fechaDesde,fechaHasta);
						if(represados !=0)
						{
				
							enviarSiguientePago();
						}
						else
						{
							mostrarMensaje("Pagos Enviadas Correctamente.", "s");				
						}
						
					}				
					
				}
				else
				{
					mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ","l");
					cargarPagos(fechaDesde,fechaHasta);
				}
				}
				catch (Exception e) {
					mostrarMensaje(e.toString(), "l");
				}
			}
		 
		 public void guardarAnulacionFactura()
		 {
			 if(factura!=null) {

					 bd.ActualizarAnulacionFactura(factura);
					 mostrarMensaje("Facturas Anulada Correctamente.", "s");
				 cargarFacturas(fechaDesde, fechaHasta);
			 }
			 else {
				 mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ", "l");
				 mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ", "l");
				 cargarFacturas(fechaDesde, fechaHasta);
			 }
		 }
	public void guardarAnulacionPedido()
	{
		if(pedido.getEstado().equals("3")) {

			bd.ActualizarAnulacionPedido(pedido);
			mostrarMensaje("Pedido Anulado Correctamente.", "s");
			cargarPedidos(fechaDesde, fechaHasta);
		}
		else {
			mostrarMensaje("No Fue Posible anular el pedido, Fue almacenado en el telefono temporalmente ", "l");
			mostrarMensaje("No Fue Posible anular el pedido, Fue almacenado en el telefono temporalmente ", "l");
			cargarPedidos(fechaDesde, fechaHasta);
		}
	}
		 public void guardarFactura()
			{
                if(factura!=null) {

                	//Asigna valor de la caja
					factura.setNCaja(parametrosPos.getCaja());

                    factura.setIdCodigoExterno(factura.getNFactura());
                    if (factura.idCodigoExterno != 0) {
                        if (!parametrosPos.isValue(parametrosPos.getUsaWSCash())) {
                            factura.setRazonSocial(parametrosPos.getRazonSocial());
                            factura.setRepresentante(parametrosPos.getRepresentante());
                            factura.setDireccionTel(parametrosPos.getDireccionTel());
                            factura.setRegimenNit(parametrosPos.getRegimenNit());
                            factura.setRango(parametrosPos.getRango());
                            factura.setResDian(parametrosPos.getResDian());
                            factura.setPrefijo(parametrosPos.getPrefijo());

                        }
                        bd.ActualizarFactura(factura);
                        cargarFacturas(fechaDesde, fechaHasta);
                        if (represados != 0) {

                            enviarSiguienteFactura();
                        } else {
                            mostrarMensaje("Facturas Enviadas Correctamente.", "s");
                        }
                    } else {
                        mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ", "l");
                        cargarFacturas(fechaDesde, fechaHasta);
                    }
                }
                else {
                    mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ", "l");
                    mostrarMensaje("No Fue Posible enviar la factura, Fue almacenado en el telefono temporalmente ", "l");
                    cargarFacturas(fechaDesde, fechaHasta);
                }
			}
	public void guardarRemision()
	{
		if(remision!=null) {
			remision.setIdCodigoExterno(remision.getNRemision());
			if (remision.idCodigoExterno != 0) {
				if (!parametrosPos.isValue(parametrosPos.getUsaWSCash())) {
					remision.setRazonSocial(parametrosPos.getRazonSocial());
					remision.setRepresentante(parametrosPos.getRepresentante());
					remision.setDireccionTel(parametrosPos.getDireccionTel());
					remision.setRegimenNit(parametrosPos.getRegimenNit());
					remision.setRango(parametrosPos.getRango());
					remision.setResDian(parametrosPos.getResDian());
					remision.setPrefijo(parametrosPos.getPrefijo());

				}
				bd.ActualizarRemision(remision);
				cargarRemisiones(fechaDesde, fechaHasta);
				if (represados != 0) {
					enviarSiguienteRemision();
				} else {
					mostrarMensaje("Cotizaciones Enviadas Correctamente.", "s");
				}
			} else {
				mostrarMensaje("No Fue Posible enviar la cotizacion, Fue almacenado en el telefono temporalmente ", "l");
				cargarRemisiones(fechaDesde, fechaHasta);
			}
		}
		else {
			mostrarMensaje("No Fue Posible enviar la Cotizacion, Fue almacenado en el telefono temporalmente ", "l");
			mostrarMensaje("No Fue Posible enviar la Cotizacion, Fue almacenado en el telefono temporalmente ", "l");
			cargarRemisiones(fechaDesde, fechaHasta);
		}
	}
		 
		 
		 private class enviarTraslado extends AsyncTask<String, Void, Object>
			{

				@Override
				protected Integer doInBackground(String... params) 
				{
					generarTraslado();			
					EnviarTraslado e= new EnviarTraslado(parametrosPos.getIp());
					traslado=e.getEnviarTraslado(trasladoEnviar, traslado);
					LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
					String fechaSys=llamarFecha.getFecha();
					if(!fechaSys.equals("Error"))
					{
						traslado.setFecha(fechaSys);
					}	
					return 1;
				}
				
				protected void onPostExecute(Object result)
				{
					pdu.dismiss();					
				    guardarTraslado();			
				}
				
			}
		 public void generarTraslado()
			{
			 	listaATraslado=bd.getArticulosTraslado(traslado.idCodigoInterno);
				trasladoEnviar.NCaja=parametrosPos.getCaja();	
				trasladoEnviar.IdCliente=13108;
				trasladoEnviar.CedulaVendedor=usuario.cedula;	
				ArrayList<ArticulosTraslado> listaAPTem=new ArrayList<ArticulosTraslado>();
				listaAPTem.add(listaATraslado.get(0));
				for (int i = 0; i < listaATraslado.size(); i++) {
					listaAPTem.add(listaATraslado.get(i));
				}
				ClsArtsFactura ls=new ClsArtsFactura ();
				for (int i = 0; i < listaAPTem.size(); i++) 
				{
					 ArticulosTraslado ap=listaAPTem.get(i);
					 ClsArtFactura cap=new ClsArtFactura();
					 cap.IdArticulo=ap.idArticulo;
					 cap.Cantidad=ap.cantidad;
					 cap.ValorUnitario=ap.valorUnitario;
					 ls.add(cap);
				}
				trasladoEnviar.Articulo=ls;
				trasladoEnviar.Latitud="sin_datos";
				trasladoEnviar.Longitud="sin_datos";
				trasladoEnviar.IdBodegaOrigen=traslado.bodegaOrigen.getIdBodega();
				trasladoEnviar.IdBodegaDestino=traslado.bodegaDestino.getIdBodega();
				trasladoEnviar.BodegaOrigen=traslado.bodegaOrigen.getBodega();
				trasladoEnviar.BodegaDestino=traslado.bodegaDestino.getBodega();
				
				try
				{	trasladoEnviar.Observaciones="";}
				catch(Exception e)
				{  trasladoEnviar.Observaciones="Ninguna"; }
			}
		 public void guardarTraslado()
			{
			 if(traslado.idCodigoExterno!=0)
				{
					bd.ActualizarTraslado(traslado);
					cargarTraslados(fechaDesde,fechaHasta);
					if(represados !=0)
					{			
						enviarSiguienteTraslado();
					}
					else
					{
						mostrarMensaje("Traslados Enviados Correctamente.", "s");				
					}
				}
				else
				{
					mostrarMensaje("No Fue Posible enviar el traslado, Fue almacenado en el telefono temporalmente ","l");
					cargarTraslados(fechaDesde,fechaHasta);
				}			
			}
		 
		 
		 private void PrintDocument(long idCodigoExterno)
		    {
		    	boolean valid=false;
		    	if(operacion==FACTURA)
		    	{
		    		 etapa="5 "+idCodigoExterno;
		    		for (int i = 0; i < listaFacturas.size(); i++) {
						if(idCodigoExterno==listaFacturas.get(i).idCodigoExterno)
						{
							factura=listaFacturas.get(i);
							valid=true;
						}
					}
		    		etapa="6 "+ listaFacturas.size()+factura.nombreCliente;
		    	}
		    	else if(operacion==REMISION)
				{
					etapa="5 "+idCodigoExterno;
					for (int i = 0; i < listaRemisiones.size(); i++) {
						if(idCodigoExterno==listaRemisiones.get(i).idCodigoExterno)
						{
							remision=listaRemisiones.get(i);
							valid=true;
						}
					}
					etapa="6 "+ listaRemisiones.size()+remision.nombreCliente;
				}
		    	else 
		    	{
		    		for (int i = 0; i < listaTraslados.size(); i++) {
						if(idCodigoExterno==listaTraslados.get(i).idCodigoExterno)
						{
							traslado=listaTraslados.get(i);
							valid=true;
						}
					}	
		    	}
		    	
		    	if(valid)
		    	{
		    		if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
				{
					try
					{
						operacionBixolon = "factura";
						if(operacion==REMISION) {
							operacionBixolon = "remision";
						}
						printBixolonsppr310();
					}catch(Exception e){
						mostrarMensaje("No fue posible Enviar la impresion", "l");
						mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
					}
				}
				else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
				{
					try
					{
						operacionDigitalPos = "factura";
						if(operacion==REMISION) {
							operacionDigitalPos = "remision";
						}
						printDigitalPos810();
					}catch(Exception e){
						mostrarMensaje("No fue posible Enviar la impresion", "l");
						mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
					}
				}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0)
					{
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
						printFactura=new PrintFactura();
						if(operacion==REMISION)
						{
							printFactura.printRemision(remision) ;
						}
						else {
							printFactura.printFactura(factura);
						}
						pdu.dismiss();
						mostrarMensaje(printFactura.getMensaje(), "l");

					}
					else if(parametrosPos.getUsaPrintEpson()==1)
					{
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
									printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
								}
								catch (Exception e)
								{
									mostrarMensaje("No fue posible Enviar la impresion", "l");
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}

								Builder builder = null;
								try {
									builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
									PrintEpson printEpson=new PrintEpson();

									if(operacion==REMISION)
									{
										builder = printEpson.printRemision(builder, remision) ;
									}
									else {
										builder = printEpson.printFactura(builder, factura);
									}
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
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}
							}
							else
							{

							}

							//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


						}
					}
					else {

						PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
						try {
							pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

							boolean resPrint = false;
							if (operacion == FACTURA) {
								resPrint = pz.printFactura(factura);
							}
							else  if (operacion == REMISION) {
								resPrint = pz.printRemision(remision);
							}
							else {
								resPrint = pz.printTraslado(traslado);
							}

							if (resPrint) {
								pdu.dismiss();
								mostrarMensaje("Impresion enviada Correctamente..", "l");
							} else {
								pdu.dismiss();
								mostrarMensaje("No fue posible Enviar la impresion", "l");
							}
						} catch (Exception e) {
							pdu.dismiss();
							mostrarMensaje("Imp " + e.toString(), "l");
						}//
					}
		    	}
		    }
		 
		 private void PrintDocumentNFactura(long NFactura)
		    {
		    	boolean valid=false;
		    	if(operacion==FACTURA)
		    	{
		    		 etapa="5 "+NFactura;
		    		for (int i = 0; i < listaFacturas.size(); i++) {
						if(NFactura==listaFacturas.get(i).NFactura)
						{
							factura=listaFacturas.get(i);
							valid=true;
						}
					}
		    		etapa="6 "+ listaFacturas.size()+factura.nombreCliente;
		    	}
		    	
	            	
		    	
		    	if(valid)
		    	{
					 if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)

					{
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
						printFactura=new PrintFactura();
						printFactura.printFactura(factura);
						pdu.dismiss();
						mostrarMensaje(printFactura.getMensaje(), "l");

					}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
					{
						try
						{
							operacionBixolon="factura";
							printBixolonsppr310();
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						}
					}
					 else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
					 {
						 try
						 {
							 operacionDigitalPos="factura";
							 //printDigitalPos810();
						 }catch(Exception e){
							 mostrarMensaje(factura.getNFactura()+e.toString()+"No fue posible Enviar la impresion", "l");
							 mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						 }
					 }
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0)
					{
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
									printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
								}
								catch (Exception e)
								{
									mostrarMensaje("No fue posible Enviar la impresion", "l");
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}

								Builder builder = null;
								try {
									builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
									PrintEpson printEpson=new PrintEpson();
									builder =printEpson.printFactura(builder, factura);
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
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}
							}
							else
							{

							}

							//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


						}
					}
					else {


						PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
						try {
							pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

							boolean resPrint = false;
							if (operacion == FACTURA) {
								resPrint = pz.printFactura(factura);
							}

							if (resPrint) {
								pdu.dismiss();
								mostrarMensaje("Impresion enviada Correctamente..", "l");
							} else {
								pdu.dismiss();
								mostrarMensaje("No fue posible Enviar la impresion", "l");
							}


						} catch (Exception e) {
							pdu.dismiss();
							mostrarMensaje("Imp22 " + e.toString(), "l");
						}
					}//
		    	}
		    }
	private void PrintDocumentNRemision(long NRemision)
	{
		boolean valid=false;
		if(operacion==REMISION)
		{
			etapa="5 "+NRemision;
			for (int i = 0; i < listaRemisiones .size(); i++) {
				if(NRemision==listaRemisiones.get(i).NRemision)
				{
					remision=listaRemisiones.get(i);
					valid=true;
				}
			}
			etapa="6 "+ listaRemisiones.size()+remision.nombreCliente;
		}



		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)

			{
				pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				printFactura=new PrintFactura();
				printFactura.printRemision(remision);
				pdu.dismiss();
				mostrarMensaje(printFactura.getMensaje(), "l");

			}
			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
			{
				try
				{
					operacionBixolon="remision";
					printBixolonsppr310();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}
			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					operacionDigitalPos="remision";
					printDigitalPos810();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}
			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0)
			{
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
							printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
						}
						catch (Exception e)
						{
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

						}

						Builder builder = null;
						try {
							builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
							PrintEpson printEpson=new PrintEpson();
							builder =printEpson.printRemision(builder, remision);
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
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

						}
					}
					else
					{

					}

					//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


				}
			}
			else {


				PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
				try {
					pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

					boolean resPrint = false;
					if (operacion == REMISION) {
						resPrint = pz.printRemision(remision) ;
					}

					if (resPrint) {
						pdu.dismiss();
						mostrarMensaje("Impresion enviada Correctamente..", "l");
					} else {
						pdu.dismiss();
						mostrarMensaje("No fue posible Enviar la impresion", "l");
					}


				} catch (Exception e) {
					pdu.dismiss();
					mostrarMensaje("Imp22 " + e.toString(), "l");
				}
			}//
		}
	}
	private void PrintDocumentNPedido(long NPedido)
	{
		boolean valid=false;
		if(operacion==PEDIDO)
		{
			etapa="5 "+NPedido;
			for (int i = 0; i < listaPedidos.size(); i++) {
				if(NPedido==listaPedidos.get(i).idCodigoInterno)
				{
					pedido=listaPedidos.get(i);
					valid=true;
				}
			}
			//etapa="6 "+ listaPedidos.size()+factura.nombreCliente;
		}



		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)
			{
				pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				printFactura=new PrintFactura();

				ArrayList<ArticulosPedido> listaAPedido=null;
				bd.openDB();
				listaAPedido=bd.getArticulosPedido( pedido.idCodigoInterno);
				bd.closeDB();


				printFactura.printPedido(pedido,listaAPedido,parametrosSys);
				pdu.dismiss();
				mostrarMensaje(printFactura.getMensaje(), "l");

			}
			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
			{
				try
				{
					operacionBixolon="pedido";
					printBixolonsppr310();
				}catch(Exception e){
					mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}

			else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					operacionDigitalPos="pedido";
					printDigitalPos810();
				}catch(Exception e){
					mostrarMensaje(e.toString()+"No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}

			else {


				PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
				try {
					pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

					boolean resPrint = false;
					if (operacion == PEDIDO) {
						resPrint = pz.printPedido(pedido,listaAPedido,parametrosSys);
					}

					if (resPrint) {
						pdu.dismiss();
						mostrarMensaje("Impresion enviada Correctamente..", "l");
					} else {
						pdu.dismiss();
						mostrarMensaje("No fue posible Enviar la impresion", "l");
					}


				} catch (Exception e) {
					pdu.dismiss();
					mostrarMensaje("Imp22 " + e.toString(), "l");
				}
			}//
		}
	}
		 private void PrintDocumentIdPagoPrestamo(long IdPagoPrestamo)
	{
		boolean valid=false;
		try
		{

			if(operacion==ABONOPRESTAMOS)
			{

				etapa="5 "+IdPagoPrestamo;
				for (int i = 0; i < listaPagoPrestamos.size(); i++) {
					if(IdPagoPrestamo==Long.parseLong(listaPagoPrestamos.get(i).getIdPagoPrestamo()))
					{
						pagoPrestamo=listaPagoPrestamos.get(i);
						valid=true;
					}
				}
//		    		etapa="6 "+ listaPagos.size()+factura.nombreCliente;
			}



		}
		catch (Exception e) {


		}

		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
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
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0 & parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					operacionDigitalPos="pagoPrestamo";
					printDigitalPos810();
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

			if(operacion==PRESTAMOS)
			{

				etapa="5 "+IdPrestamo;
				for (int i = 0; i < listaPrestamos.size(); i++) {
					if(IdPrestamo==Long.parseLong(listaPrestamos.get(i).getIdPrestamo()))
					{
						prestamo=listaPrestamos.get(i);
						valid=true;
					}
				}
//		    		etapa="6 "+ listaPagos.size()+factura.nombreCliente;
			}



		}
		catch (Exception e) {


		}

		if(valid)
		{
			 if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
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
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					operacionDigitalPos="prestamo";
					conectprintDigitalPOS();
					printDigitalPos810();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}

		}
	}


	private void conectprintDigitalPOS()
	{
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
	}

	private void PrintDocumentIdLibro(long IdLibro)
	{
		boolean valid=false;
		try
		{

			if(operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
			{

				etapa="5 "+IdLibro;
				for (int i = 0; i < listaLibros.size(); i++) {
					if(IdLibro==Long.parseLong(listaLibros.get(i).getIdLibro()))
					{
						libro=listaLibros.get(i);
						valid=true;
					}
				}
//		    		etapa="6 "+ listaPagos.size()+factura.nombreCliente;
			}



		}
		catch (Exception e) {


		}

		if(valid)
		{
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1 & parametrosPos.getUsaPrintDigitalPos()==0)
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
			if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0 & parametrosPos.getUsaPrintDigitalPos()==1)
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

		 private void PrintDocumentIdPago(long IdPago)
		    {
			 boolean valid=false;
			 try
			 {
		    	
		    	if(operacion==CARTERA)
		    	{
		    		
		    		 etapa="5 "+IdPago;
		    		for (int i = 0; i < listaPagos.size(); i++) {
						if(IdPago==listaPagos.get(i).getIdPago())
						{
							pago=listaPagos.get(i);
							pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(this, pago.getIdPago()));
							pago.setDeudaCliente(bd.getDeudaCliente(this, pago.getIdCliente()));
							valid=true;
						}
					}
//		    		etapa="6 "+ listaPagos.size()+factura.nombreCliente;
		    	}
		    	
		    	
		     
			 }
			 catch (Exception e) {
				
		    	
			}
		    	
		    	if(valid)
		    	{
					if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)
					{
						pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
						printFactura=new PrintFactura();
						if (operacion == CARTERA) {
							printFactura.printPago(pago, parametrosSys);
						}
						pdu.dismiss();
						mostrarMensaje(printFactura.getMensaje(), "l");

					}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
					{
						try
						{
							operacionBixolon="pago";
							printBixolonsppr310();
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						}
					}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
					{
						try
						{
							operacionDigitalPos="pago";
							printDigitalPos810();
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						}
					}

					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0)
					{
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
									printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
								}
								catch (Exception e)
								{
									mostrarMensaje("No fue posible Enviar la impresion", "l");
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}

								Builder builder = null;
								try {
									builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
									PrintEpson printEpson=new PrintEpson();
									builder =printEpson.printPago(builder,pago, parametrosSys);
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
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}
							}
							else
							{

							}

							//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


						}
					}
					else {

						PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
						try {
							pdu = ProgressDialog.show(ListaPedidosActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);

							boolean resPrint = false;
							if (operacion == CARTERA) {
								resPrint = pz.printPago(pago, parametrosSys);
							}

							if (resPrint) {
								pdu.dismiss();
								mostrarMensaje("Impresion enviada Correctamente..", "l");
							} else {
								pdu.dismiss();
								mostrarMensaje("No fue posible Enviar la impresion", "l");
							}
						} catch (Exception e) {
							pdu.dismiss();
							mostrarMensaje("Imp22 " + e.toString(), "l");
						}//
					}
		    	}
		    }
		 
		 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == SUB_ACTIVITY_SELECTFECHA)
			{
				 try
					{
					  Bundle b = data.getExtras();				  
	                  fechaDesde = b.getString("fechaDesde");
	                  fechaHasta = b.getString("fechaHasta");
	                  fechaBotonDesde=b.getString("fechaBotonDesde");
	                  fechaBotonHasta =b.getString("fechaBotonHasta");
	                  btFecha.setText(fechaBotonDesde+" <> "+fechaBotonHasta) ;
	                  if(operacion==TRANSLADO)
	                  {
	                	  cargarTraslados(fechaDesde, fechaHasta);
	                  }
	                  else if(operacion==FACTURA)
	                  {
	                	  cargarFacturas(fechaDesde, fechaHasta);
	                  }
					  else if(operacion==REMISION)
					  {
						  cargarRemisiones(fechaDesde, fechaHasta);
					  }
	                  else if(operacion==CARTERA)
	                  {
	                	  cargarPagos(fechaDesde, fechaHasta);
	                  }
					  else if(operacion==PRESTAMOS||operacion==ABONOPRESTAMOS)
					  {
						  cargarMovimientos(fechaDesde, fechaHasta);
					  }
					  else if(operacion==VERLIBRO)
					  {
						  cargarMovimientosXCliente(fechaDesde, fechaHasta,cliente);
					  }
				      else
	                  {
	                	  cargarPedidos(fechaDesde, fechaHasta);
	                  }	                
					}
				 catch (Exception e) {
					 mostrarMensaje("No modifico las fechas", "l");
					}
			}
			else if(requestCode == SUB_ACTIVITY_SELECTBODEGA)
			{
				 try
					{
					  Bundle b = data.getExtras();	
					  traslado=new Traslado_in();
					  traslado.bodegaOrigen.setIdBodega(b.getInt("idBodegaOrigen"));
					  traslado.bodegaDestino.setIdBodega(b.getInt("idBodegaDestino"));
					  if( traslado.bodegaOrigen.getIdBodega()==0)
					  {
						  traslado.bodegaOrigen = new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
					  }
					  else
					  {
						  traslado.bodegaOrigen=bd.getBodega(traslado.bodegaOrigen.getIdBodega());
					  }
					  if( traslado.bodegaDestino.getIdBodega()==0)
					  {
						  traslado.bodegaDestino = new Bodega(0, "Todas", "Carre 3", "3124993390", "Javier", "Duitama");
					  }
					  else
					  {
						  traslado.bodegaDestino=bd.getBodega(traslado.bodegaDestino.getIdBodega());
					  }
					                  
	                  btBodegas.setText(traslado.bodegaOrigen.getBodega()+" <> "+traslado.bodegaDestino.getBodega()) ;	                
	              	  cargarTraslados(fechaDesde, fechaHasta);	                              
					}
				 catch (Exception e) {
					 mostrarMensaje("No modifico las Bodegas", "l");
					}
			}
			
		}
		 
		 private void printInforme()
			{
				
		    		try
		    		{
		    		PrintZebra pz=new PrintZebra(parametrosPos.getMacAdd());	
		    		pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
		    		datos=new ArrayList<String>();
		  			boolean resPrint=false;
		  			if(operacion==TRANSLADO)
			    	{
		  				datos.add("INFORME DE TRASLADOS");
		  			}
		  			else if(operacion==FACTURA)
			    	{
		  				datos.add("INFORME DE FACTURAS");
		  			}
					else if(operacion==REMISION)
					{
						datos.add("INFORME DE COTIZACIONES");
					}
		  			else if (operacion==PEDIDO)
		  			{
		  				datos.add("INFORME DE PEDIDOS");
			    	}
					else if (operacion==ABONOPRESTAMOS||operacion==PRESTAMOS)
					{
						datos.add("INFORME DE MOVIMIENTOS");
					}
					else if (operacion==VERLIBRO)
					{
						datos.add("INFORME DE MOVIMIENTOS");
					}
					else if (operacion==CARTERA)
					{
						datos.add("INFORME DE PAGOS");
					}
		  			Date fecha=new Date();
		            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
		            datos.add(sdf.format(fecha));            
		            SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
		            datos.add(hora.format(fecha));	
		            datos.add(fechaBotonDesde);
		            datos.add(fechaBotonHasta);
		            if(operacion==TRANSLADO)
			    	{
		  				datos.add(traslado.bodegaOrigen.getBodega());
		  				datos.add(traslado.bodegaDestino.getBodega());
		  			}

		  			if(parametrosPos.getUsaImpresoraZebra()==1 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0) {

						resPrint = pz.printDocumentosRealizados(operacion, false, datos, listaPedidos, listaFacturas, listaTraslados, null, listaRemisiones);

						if (resPrint) {
							pdu.dismiss();
							mostrarMensaje("Impresion enviada Correctamente..", "l");
						} else {
							pdu.dismiss();
							mostrarMensaje("No fue posible Enviar la impresion", "l");
						}
					}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
					{
						try
						{
							pdu.dismiss();
							operacionBixolon="documentos";
							printBixolonsppr310();
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						}
					}
					else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
					{
						try
						{
							pdu.dismiss();
							operacionDigitalPos="documentos";
							printDigitalPos810();
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
						}
					}

					else
					{
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
									printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
								}
								catch (Exception e)
								{
									mostrarMensaje("No fue posible Enviar la impresion", "l");
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}

								Builder builder = null;
								try {
									builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
									PrintEpson printEpson=new PrintEpson();
									builder =printEpson.printDocumentosRealizados(builder,operacion, false, datos, listaPedidos, listaFacturas, listaTraslados, null,listaRemisiones);
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
									mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");

								}
							}
							else
							{

							}

							//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
						}catch(Exception e){
							mostrarMensaje("No fue posible Enviar la impresion", "l");
							mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");


						}
					}
		    		}
		    		catch (Exception e) {
		    			pdu.dismiss();
		             	mostrarMensaje("Imp "+e.toString(), "l");
					}//	
			}
	private void printInformeArqueo()
	{

		try
		{
			PrintZebra pz=new PrintZebra(parametrosPos.getMacAdd());
			pdu=ProgressDialog.show(ListaPedidosActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
			datos=new ArrayList<String>();
			boolean resPrint=false;
			datos.add("ARQUEO DE VENTAS");

			Date fecha=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			datos.add(sdf.format(fecha));
			SimpleDateFormat hora=new SimpleDateFormat("HH:mm");
			datos.add(hora.format(fecha));
			datos.add(fechaBotonDesde);
			datos.add(fechaBotonHasta);

			//UNICAMENTE HABILITADO PARA IMPRESORAS DIGITALPOS
				if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==1)
			{
				try
				{
					pdu.dismiss();
					operacionDigitalPos="Arqueo";
					printDigitalPos810();
				}catch(Exception e){
					mostrarMensaje("No fue posible Enviar la impresion", "l");
					mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
				}
			}


		}
		catch (Exception e) {
			pdu.dismiss();
			mostrarMensaje("Imp "+e.toString(), "l");
		}//
	}
		public void onClick(View v) {
			if(v.equals(btBodegas))
			{
				Intent intent = new Intent(this, SelecTrasladoBodegaActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("operacion", TRANSLADO);
				intent.putExtra("selecInforme", true);
				startActivityForResult(intent, SUB_ACTIVITY_SELECTBODEGA);
			}
			else if(v.equals(btMenuP))
			{
		    	openOptionsMenu();		
			}
			else if(v.equals(btVolverP))
			{
					onBackPressed();
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

	private boolean openPrinter() {

		Context context =this;
		int deviceType = Print.DEVTYPE_BLUETOOTH;
		int enabled = Print.FALSE;
		int interval = 1000;

		try{
			//open
			if (printer==null) {
				printer =
						new Print(getApplicationContext());
			}

			mostrarMensaje(parametrosPos.getMacAddEpson(),"l");
			if(printer != null){
				mostrarMensaje("Objeto Creado ok", "l");
				//printer.setStatusChangeEventCallback(this);
				//printer.setBatteryStatusChangeEventCallback(this);
			}
			String text1 = "HOLA MUNDO";
			String text2 = "HOLA MUNDO PEQUENO";
			if (printer!=null) {
				try {
					if(printer != null){
						printer.setStatusChangeEventCallback(this);
						printer.setBatteryStatusChangeEventCallback(this);
					}

					printer.openPrinter(deviceType, parametrosPos.getMacAddEpson());
				}
				catch (Exception e)
				{
					text2=e.toString();
					mostrarMensaje("3. "+e.toString(), "l");
				}

				Builder builder = null;
				try {
					builder = new Builder("TM-P80", Builder.MODEL_ANK,getApplicationContext());
					builder.addTextFont(Builder.FONT_A);
					builder.addTextAlign(Builder.ALIGN_CENTER);
					builder.addTextLineSpace(getBuilderLineSpace(text1));
					builder.addTextLang(Builder.LANG_EN) ;
					builder.addTextSize(1,1);
					builder.addTextStyle(Builder.FALSE, Builder.FALSE, Builder.FALSE, Builder.COLOR_1);
					builder.addTextPosition(0);
					builder.addText(text1);
					builder.addFeedUnit(30);

					builder.addTextFont(Builder.FONT_A);
					builder.addTextAlign(Builder.ALIGN_CENTER);
					builder.addTextLineSpace(getBuilderLineSpace(text1));
					builder.addTextLang(Builder.LANG_EN) ;
					builder.addTextSize(1,1);
					builder.addTextStyle(Builder.FALSE, Builder.FALSE, Builder.FALSE, Builder.COLOR_1);
					builder.addTextPosition(0);
					builder.addText(text2);
					builder.addFeedUnit(30);


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

					mostrarMensaje("1. "+e.toString(), "l");
				}
			}
			else
			{

			}

			//printer.openPrinter(deviceType, parametrosPos.getMacAddEpson() , enabled, interval);
		}catch(Exception e){
			mostrarMensaje("2. "+e.toString(), "l");
			return false;
		}
		return true;
	}

	private int getBuilderLineSpace(String text) {
		try{
			return Integer.parseInt(text.toString());
		}catch(Exception e){
			return 0;
		}
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
						PrintBixolon printBixolon=new PrintBixolon();
						if(operacionBixolon.equals("pagoPrestamo"))
						{
							printBixolon.printPagoPrestamo(mBixolonPrinter,pagoPrestamo, parametrosSys);
						}
						else if(operacionBixolon.equals("libro"))
						{
							printBixolon.printLibro(mBixolonPrinter,libro, parametrosSys);
						}
						else if(operacionBixolon.equals("prestamo"))
						{
							printBixolon.printPrestamo(mBixolonPrinter,prestamo, parametrosSys);
						}
						else if(operacionBixolon.equals("pago"))
						{
							printBixolon.printPago(mBixolonPrinter,pago, parametrosSys);
						}
						else if (operacionBixolon.equals("pedido"))
						{
							printBixolon.printPedido(mBixolonPrinter,pedido,listaAPedido,parametrosSys);
						}
						else if (operacionBixolon.equals("factura"))
						{
							printBixolon.printFactura(mBixolonPrinter, factura);
							//printBixolon.printFacturaSimplificado(mBixolonPrinter, factura);
						}
						else if (operacionBixolon.equals("remision"))
						{
							printBixolon.printRemision(mBixolonPrinter, remision);
							//printBixolon.printFacturaSimplificado(mBixolonPrinter, factura);
						}
						else if (operacionBixolon.equals("documentos"))
						{
							printBixolon.printDocumentosRealizados(mBixolonPrinter,operacion, false, datos, listaPedidos, listaFacturas, listaTraslados, null,listaLibros, listaRemisiones);
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



	private void printDigitalPos810(){
		String bleAdrress=parametrosPos.getMacAddDigitalPos();
				binder.connectBtPort(bleAdrress, new UiExecute() {
					public void onsucess() {
						ISCONNECT = true;
						PrintDigitaPos printDigitaPos = new PrintDigitaPos();

						 if(operacionDigitalPos.equals("pago"))
						{
							printDigitaPos.printPago(binder,pago, parametrosSys);
						}
						else if (operacionDigitalPos.equals("pedido"))
						{
							printDigitaPos.printPedido(binder,pedido,listaAPedido,parametrosSys);
						}
						else if (operacionDigitalPos.equals("factura"))
						{
							printDigitaPos.printFactura(binder, factura);
							//printBixolon.printFacturaSimplificado(mBixolonPrinter, factura);
						}
						else if (operacionDigitalPos.equals("remision"))
						{
							printDigitaPos.printRemision(binder, remision);
							//printBixolon.printFacturaSimplificado(mBixolonPrinter, factura);
						}
						else if (operacionDigitalPos.equals("documentos"))
						{
							printDigitaPos.printDocumentosRealizados(binder,operacion, false, datos, listaPedidos, listaFacturas, listaTraslados, null,listaLibros, listaRemisiones, parametrosPos,listaMedios,listaPagos);
						}
						else if(operacionDigitalPos.equals("pagoPrestamo"))
						{
							printDigitaPos.printPagoPrestamo(binder,pagoPrestamo, parametrosPos);
						}
						else if(operacionDigitalPos.equals("libro"))
						{
							printDigitaPos.printLibro(binder,libro, parametrosPos);
						}
						else if(operacionDigitalPos.equals("prestamo"))
						{
							printDigitaPos.printPrestamo(binder,prestamo, parametrosPos);
						}
						 else if (operacionDigitalPos.equals("Arqueo"))
						 {
							 //Obtiene datos de las listas
							 listaPedidos=bd.getPedidosPorFecha(getBaseContext(), fechaDesde,fechaHasta, false);
							 listaFacturas=bd.getFacturasPorFecha(getBaseContext(), fechaDesde,fechaHasta, false);
							 listaRemisiones=bd.getRemisionesPorFecha(getBaseContext(), fechaDesde,fechaHasta, false);
							 listaMedios=bd.GetMedios();
							 listaPagos=bd.getPagosPorFecha(getBaseContext(), fechaDesde,fechaHasta, false);
							 printDigitaPos.printArqueo(binder,datos,listaPedidos,listaFacturas,listaRemisiones,parametrosPos,listaMedios,listaPagos);
						 }





						binder.write(DataForSendToPrinterPos80.openOrCloseAutoReturnPrintState(0x1f), new UiExecute() {

							public void onsucess() {
								binder.acceptdatafromprinter(new UiExecute() {

									public void onsucess() {
										ISCONNECT=false;
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
