package com.principal.factura;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.Bodega;
import com.principal.mundo.Categoria;
import com.principal.mundo.Cliente;
import com.principal.mundo.Factura_in;
import com.principal.mundo.GetArticulosSize;
import com.principal.mundo.LlamarArticulos;
import com.principal.mundo.LlamarArticulosSys;
import com.principal.mundo.LlamarFecha;
import com.principal.mundo.LlamarStock;
import com.principal.mundo.LlamarStockSys;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.Usuario;

import com.principal.mundo.sysws.Ruta;
import com.principal.persistencia.creaBD;
/**
 * Clase en la cual se registran los productos al pedido y se cambia la catidad del producto de 
 * ser necesario
 * @author Javier
 *
 */
@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class CrearPedidoActivity extends Activity implements OnClickListener , OnKeyListener, Runnable {
 
	//----------------------CONSTANTES-----------------------------------
	protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;	
	protected static final int SUB_ACTIVITY_VER_PEDIDOS = 200;	
	protected static final int SUB_ACTIVITY_TERMINAR = 300;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int REMISION=12;
	private boolean keyback=false;

	private long tipoPrecio=0;
	
	private int intFocus=0;

	LlamarStock llamarStock;
	
	LlamarStockSys llamarStockSys;
	
	String codigo="";
	/**
	 * Atributo pdu refencia de la clase ProgressDialog
	 */
	private ProgressDialog pdu;	
	private boolean actualizaFecha=true;
	/**
	 * Atributo textView referencia de la clase TextView
	 */
	TextView [] textView;

	TextView tvDatosCliente;
	/**
	 * Atributo etCodigo referente a la caja de texto del codigo del producto
	 */
	EditText etCodigo;
	/**
	 * Atributo etPrecio referente a la caja de texto del precio del producto
	 */
	EditText etPrecio;
	/**
	 * Atributo etCantidad referente a la caja de texto del cantidad del producto
	 */
	EditText etCantidad;
	/**
	 * Atributo btAgregar referente boton de agregar el producto al pedido
	 */
	Button btAgregar ,btMenuC, btVolverC ;
	/**
	 * Atributo btBuscar referente boton para buscar el producto por nombre
	 */
	Button btBuscar;
	/**
	 * Atributo btVer referente boton para visualizar los productos del pedido
	 */
	Button btVer;
	/**
	 * Atributo btPrecio referente boton para cambiar el precio del producto
	 */
	Button btPrecio;
	/**
	 * Atributo imUbicacionCliente referente a la imagen para conocer si el cliente ya esta 
	 * localizado en el sistema
	 */
	ImageView imUbicacionCliente;
	
	RelativeLayout rlCrearPedido;
		
	/**
	 * Atributo usuario referencia de la clase Usuario
	 */
	Usuario usuario=new Usuario();
	/**
	 * Atributo articulo referencia de la clase Articulo
	 */
	Articulo articulo;
	/**
	 * Atributo cliente referencia de la clase Cliente
	 */
	Cliente cliente;
	/**
	 * Atributo clientesys referencia de la clase Cliente utilizada al momento de 
	 * enviar informacion al sistema de georeferenciacion
	 */
	com.principal.mundo.sysws.Cliente clientesys;
	/**
	 * Atributo pedido referencia de la clase Pedido_in
	 */
	Pedido_in pedido;
	
	Factura_in factura;

	Remision_in remision;
	
	Traslado_in traslado;

	/**
	 * Atributo bd referencia de la clase creaBD
	 */
	creaBD bd;

	/**
	 * Atributo modificaCantidad de tipo boolean utilizado al momento de realizar 
	 * modificaciones a cantidades de productos en el pedido
	 */
	boolean modificaCantidad;
	private boolean isAll=false;
	/**
	 * Atributo modificaCantidad de tipo boolean utilizado al momento de agregar articulos 
	 * al pedido y verificacion del producto existente en el pedido
	 */
	boolean agrega, existe;	
	/**
	 * Atributo letraEstilo referencia de la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	/**
	 * Atributo parametrosPos y parametrosSys referencia de la clase 	Parametros
	 */
	Parametros parametrosPos, parametrosSys;
	/**
	 * Atributo locManager referente a la clase LocationManager 	
	 */
	private LocationManager locManager;
	/**
	 * Atributo locListener referente a la clase MyLocationListener 	
	 */
	private MyLocationListener locListener;	
	/**
	 * Atributo currentLocation referente a la clase Location 	
	 */
	Location currentLocation = null;
	private ArrayList<Categoria> listaCategorias;
	private ArrayList<Articulo> larticulos;
	private Categoria categoria;
	int identificador=0;
	String reportDate="";
	private int operacion;
	Bodega bodegaOmision;
	private long rangIn=1;
	private long rangOut=200;
	private long sizeArticulos=0;
	Ruta ruta;
	
	
	
	/**
	 * Metodo que se encarga de crear las opciones de menu de la actividad
	 */
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		   
		   	MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.activity_menu_crear_pedido, menu);
		    return true;
	    }
	 /**
	  * Metodo en el que se definen las acciones a realizar en el momento de hacer click en 
	  * alguna opcion de menu seleccionada
	  */
	    public boolean onOptionsItemSelected(MenuItem item) 
	    {   
	    	switch (item.getItemId()) {
	        case R.id.menuActualizaProductos:
	        	if(parametrosPos.getConsultaArticuloEnLinea()==0)
	        	{
					identificador=0;
		        	if(listaCategorias.size()>0)
					{
		        		if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
						{
							new getArticulosCategoria().execute("");
							pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos.."), true,false);
						}
						else
						{
							sizeArticulos=0;
							new getSizeConsultaArticulos().execute("");
							pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
						}		
					}
					else
					{
						mostrarMensaje("Debe Seleccionar al menos una categoria de productos","l");
						
					}
	        	}
			   return true;   
//	        case R.id.menuEnviarLocation:
//	        	clientesys=new com.principal.mundo.sysws.Cliente();
//	        	pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos"), true,false);
//				Thread thread = new Thread(this);
//	    		thread.start();
//	    		return true;
	        default: 
	            return super.onOptionsItemSelected(item);
	        }
	    	  
	    }
	/**
	 * Metodo que se ejecuta al momento de girar el telefono 
	 * y se controla que no cambie valores a parametros
	 */
	
	/**
	 * metodo que se ejecuta al abrir por primera vez la actividad
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        cargarConfiguracion();        
    }
    
    /**
     * metodo en el que se asignan valores a las valriables de la clase
     * ya sea al momento de llamar la actividad por primera vez o al girar el telefono

     */
    public void cargarConfiguracion()
    {
    	 letraEstilo=new LetraEstilo();
         textView=new TextView[12];        
         bd=new creaBD(this);
         parametrosPos=bd.getParametros(this,"P"); 
         parametrosSys=bd.getParametros(this,"S");        
     	 listaCategorias=bd.getCategorias(true);
     	
     	 rlCrearPedido=(RelativeLayout)findViewById(R.id.rlCrearPedido);     	
         textView[0]=(TextView)findViewById(R.id.tvReferenciaTR2);
         textView[1]=(TextView)findViewById(R.id.tvClienteN);      
         textView[2]=(TextView)findViewById(R.id.tvArticulo);        
         textView[3]=(TextView)findViewById(R.id.tvClientetx);        
         textView[4]=(TextView)findViewById(R.id.tvReferenciaLAP2);
         textView[5]=(TextView)findViewById(R.id.tvCantidad);
         textView[6]=(TextView)findViewById(R.id.tvCodigo);
         textView[7]=(TextView)findViewById(R.id.tvPrecio);
         textView[8]=(TextView)findViewById(R.id.tvRutaC);
         textView[9]=(TextView)findViewById(R.id.tvRutaCN);
         textView[10]=(TextView)findViewById(R.id.tvOperacionCrearPedido);
         textView[11]=(TextView)findViewById(R.id.tvStockArticulo);

		tvDatosCliente=(TextView)findViewById(R.id.tvDatosCliente);


         imUbicacionCliente=(ImageView)findViewById(R.id.imUbicacionCliente);
         
         textView[2].setText("");
         
         etCodigo=(EditText)findViewById(R.id.etCodigo);
         etPrecio=(EditText)findViewById(R.id.etPres);
         etCantidad=(EditText)findViewById(R.id.etCant);
         
         btPrecio=(Button)findViewById(R.id.btPrecio);
         btAgregar=(Button)findViewById(R.id.btAgregar);
         btBuscar=(Button)findViewById(R.id.btBuscar);
         btVer=(Button)findViewById(R.id.btVer);
         
         btMenuC=(Button)findViewById(R.id.btMenuC);         
         btMenuC.setOnClickListener(this);
         
         btVolverC=(Button)findViewById(R.id.btVolverC); 
         btVolverC.setOnClickListener(this);
                     
         btPrecio.setOnClickListener(this);
         btAgregar.setOnClickListener(this);
         btBuscar.setOnClickListener(this);
         btVer.setOnClickListener(this);
         etCantidad.setOnClickListener(this);
         etCodigo.setOnClickListener(this);
         etPrecio.setOnClickListener(this);
         etCodigo.setOnKeyListener(this);
         etCantidad.setOnKeyListener(this);
         
         
         if(!parametrosPos.isValue(parametrosPos.getPrecioLibre()))
         {
        	    etPrecio.setFocusable(false);
                etPrecio.setEnabled(false); 
         }         
         
         etCantidad.setFocusable(true);

		if(parametrosPos.getUsaCantDecimal()==0)
		{
			etCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		else {
			etCantidad.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}


         
         btAgregar.setFocusable(true);
         
         cliente=new Cliente();
         articulo=new Articulo();
         pedido=new Pedido_in();
         factura=new Factura_in();
         traslado=new Traslado_in();
		remision=new Remision_in();
      
         modificaCantidad=false;
         agrega=false;
         existe=true;
         
       
                 
         Bundle obtenerDatos=new Bundle();
         obtenerDatos = this.getIntent().getExtras();         
         operacion=obtenerDatos.getInt("operacion");
         cliente.PrecioDefecto=obtenerDatos.getString("PrecioDefecto");
         usuario.cedula=obtenerDatos.getString("cedula");
         textView[9].setText(usuario.cedula);
		 cliente.ordenVisita=0;
        
         if(operacion==TRANSLADO)
         {        	
        	 rlCrearPedido.setBackgroundColor(0xFFE0E0E0);
         	 textView[10].setText("TRANSLADO");
         	 traslado.bodegaOrigen=bd.getBodega( obtenerDatos.getInt("idBodegaOrigen"));
         	 traslado.bodegaDestino=bd.getBodega( obtenerDatos.getInt("idBodegaDestino")); 
         	 bodegaOmision=bd.getBodega(parametrosPos.getBodegaTransladosOmision());
         	 textView[3].setText("Bodega");
         	 textView[1].setText("Origen. " +traslado.bodegaOrigen.getBodega()+" Destino. "+traslado.bodegaDestino.getBodega());
			 tvDatosCliente.setText("");
         	 cargaDatosTraslado();         
         }
         else
         {
        	 cliente.nombre=obtenerDatos.getString("nombre");
			 cliente.direccion=obtenerDatos.getString("direccion");
             cliente.ordenVisita=obtenerDatos.getLong("ordenVisita");
             cliente.idCliente=obtenerDatos.getLong("idCliente");
			 cliente.idClienteSucursal=obtenerDatos.getLong("idClienteSucursal");
			 cliente.ubicado=obtenerDatos.getString("ubicado");
	         if(operacion==FACTURA)
	         {
	        	 rlCrearPedido.setBackgroundColor(0xFFE0E0E0);
	         	 textView[10].setText("FACTURA");
	         	 factura.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
	             factura.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
	             bodegaOmision=bd.getBodega( parametrosPos.getBodegaFacturaOmision());
	             cargaDatosFactura();
	         }
	         else if(operacion==PEDIDO)
	         {
	        	 pedido.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
	             pedido.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
		         textView[10].setText("PEDIDO");
		         bodegaOmision=bd.getBodega( parametrosPos.getBodegaPedidosOmision());
		         cargaDatosPedido();
	         }
			 else if(operacion==REMISION)
			 {
				 rlCrearPedido.setBackgroundColor(0xFF5D9ECC);

				 remision.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
				 remision.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
				 textView[10].setText("COTIZACION");
				 bodegaOmision=bd.getBodega( parametrosPos.getBodegaRemisionOmision());
				 cargaDatosRemision();
			 }



	         textView[1].setText(cliente.ordenVisita+". " +cliente.nombre);
			//agrega datos del cliente
			 tvDatosCliente.setText(cliente.direccion);



		     	if(cliente.getUbicado().equals("SI"))
				{
		     		imUbicacionCliente.setImageResource(R.drawable.localgreenhd);
				}
				else
				{
					imUbicacionCliente.setImageResource(R.drawable.localredhd);
				}	
         } 
         textView[11].setVisibility(View.GONE);
         btPrecio.setText(cliente.getTextoPrecioDefecto());
         tipoPrecio=Long.parseLong(cliente.getPrecioDefecto());
         etCodigo.requestFocus(); 
         etPrecio.setEnabled(parametrosPos.isModificaPrecio()&&cliente.idCliente!=2897);
      	 btPrecio.setEnabled(parametrosPos.isModificaPrecio()&&cliente.idCliente!=2897);
       
    }
    /**
     * metodo que se encarga de asinar acciones al momento de hacer click en los 
     * botones de la actividad
     */
    private void cambiarPrecio()
	{
		if(parametrosPos.isPrecio123())
		{
			if(btPrecio.getText().toString().equals("Precio 1"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio3));
				btPrecio.setText("Precio 3");
				tipoPrecio=3;
			}
			else if(btPrecio.getText().toString().equals("Precio 2"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio1));
				btPrecio.setText("Precio 1");
				tipoPrecio=1;
			}
			else if(btPrecio.getText().toString().equals("Precio 3"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio2));
				btPrecio.setText("Precio 2");
				tipoPrecio=2;
			}
		}
		else
		{
			if(btPrecio.getText().toString().equals("Precio 1"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio2));
				btPrecio.setText("Precio 2");
				tipoPrecio=2;
			}
			else if(btPrecio.getText().toString().equals("Precio 2"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio3));
				btPrecio.setText("Precio 3");
				tipoPrecio=3;
			}
			else if(btPrecio.getText().toString().equals("Precio 3"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio4));
				btPrecio.setText("Precio 4");
				tipoPrecio=4;
			}
			else if(btPrecio.getText().toString().equals("Precio 4"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio5));
				btPrecio.setText("Precio 5");
				tipoPrecio=5;
			}
			else if(btPrecio.getText().toString().equals("Precio 5"))
			{
				etPrecio.setText("");
				etPrecio.setText(Long.toString(articulo.precio6));
				btPrecio.setText("Precio 6");
				tipoPrecio=6;
			}
            else  if (btPrecio.getText().toString().equals("Precio 6")) {
                if(parametrosPos.getConsultaCosto()==1 )
                {
                    mostrarMensaje("Precio Costo Seleccionado  ", "s");
                    etPrecio.setText(Long.toString(articulo.costo));
                    btPrecio.setText("Costo");
					tipoPrecio=0;
                }
                else {
                    mostrarMensaje("Precio 1 Seleccionado  ", "s");
                    etPrecio.setText(Long.toString(articulo.precio1));
                    btPrecio.setText("Precio 1");
					tipoPrecio=1;
                }

            }
            else if (btPrecio.getText().toString().equals("Costo")) {
                mostrarMensaje("Precio 1 Seleccionado  ", "s");
                etPrecio.setText(Long.toString(articulo.precio1));
                btPrecio.setText("Precio 1");
				tipoPrecio=1;
            }
		}
	}
    
    public void onClick(View v) {
	
			if(v.equals(btPrecio))
			{
				try
				{
					if(!textView[2].getText().toString().equals(""))
					{
						cambiarPrecio();
					}
					else
					{
						mostrarMensaje("Debe Seleccionar el articulo", "l");
						existe=false;
						etCodigo.requestFocus();
					}
				}
				catch (Exception e) 
				{
					mostrarMensaje("Debe Seleccionar el articulo", "l");
					etCantidad.requestFocus();
				}
			}
			if(v.equals(etCodigo))
			{
				etCodigo.selectAll();
			}
			
			if(v.equals(btAgregar))
			{
				if(!textView[2].getText().toString().equals(""))
				{
					addArticulo();					
				}
				else
				{
					mostrarMensaje("Debe Seleccionar el articulo", "l");
					existe=false;
					etCodigo.requestFocus();
				}
				
			}
			if(v.equals(btVer))
			{
				
				try
				{
				    Intent intent = new Intent(CrearPedidoActivity.this, ListaArticulosPedidoActivity.class );
				    intent.putExtra("operacion", operacion);
				    intent.putExtra("consulta",false );
				    intent.putExtra("cedula", usuario.cedula);
				    if(operacion==TRANSLADO)
				    {
				    	intent.putExtra("idCodigoExterno", traslado.idCodigoExterno);
						intent.putExtra("idCodigoInterno", traslado.idCodigoInterno);
					    intent.putExtra("idBodegaOrigen",traslado.bodegaOrigen.getIdBodega());
						intent.putExtra("idBodegaDestino",traslado.bodegaDestino.getIdBodega());	
				    }
				    else
				    {
				    	    intent.putExtra("nombre", cliente.nombre);
						    intent.putExtra("direccion", cliente.direccion);
							intent.putExtra("ordenVisita", cliente.ordenVisita);
							intent.putExtra("idCliente", cliente.idCliente);
							intent.putExtra("idClienteSucursal", cliente.idClienteSucursal);
						if(operacion==FACTURA)
							{
								intent.putExtra("idCodigoExterno", factura.idCodigoExterno);
								intent.putExtra("idCodigoInterno", factura.idCodigoInterno);
							}
							else if(operacion==REMISION)
							{
								intent.putExtra("idCodigoExterno", remision.idCodigoExterno);
								intent.putExtra("idCodigoInterno", remision.idCodigoInterno);
							}
							else
							{
								intent.putExtra("idCodigoExterno", pedido.idCodigoExterno);
								intent.putExtra("idCodigoInterno", pedido.idCodigoInterno);
							}
				    }												
					startActivityForResult(intent,SUB_ACTIVITY_VER_PEDIDOS );
				}
				catch (Exception e4)
				{
					mostrarMensaje(e4.toString(), "l");
				}
				
			}
			
			if(v.equals(btBuscar))
			{
				   	Intent intent = new Intent(CrearPedidoActivity.this, VerProductosActivity.class );
					intent.putExtra("operacion",operacion);	
					intent.putExtra("precioCliente",cliente.PrecioDefecto);
					intent.putExtra("idCliente",""+cliente.idCliente);
				if(operacion==TRANSLADO)
					{
						intent.putExtra("idBodegaOrigen",traslado.bodegaOrigen.getIdBodega());		
					}
					startActivityForResult(intent,  SUB_ACTIVITY_REQUEST_CODE );
			}	
			
			else if(v.equals(btMenuC))
			{
		    	openOptionsMenu();		
			}
			else if(v.equals(btVolverC))
			{
				if(keyback)
				{
					closeActivity();
				}
				else
				{
					InputMethodManager imm = (InputMethodManager)getSystemService(
					      Context.INPUT_METHOD_SERVICE);
//				    imm.hideSoftInputFromWindow(etNombreCliente.getWindowToken(), 0);
					if(intFocus==0)
					{
						imm.hideSoftInputFromWindow(etCodigo.getWindowToken(), 0);
					}
					else if(intFocus==1)
					{
						imm.hideSoftInputFromWindow(etCantidad.getWindowToken(), 0);	
					}
					else if(intFocus==2)
					{
						imm.hideSoftInputFromWindow(etPrecio.getWindowToken(), 0);
					}
					keyback=true;
				}	
		    	
			}
			else if(v.equals(etCodigo))
			{
				intFocus=0;
				keyback=false;
			}
			else if(v.equals(etCantidad))
			{
				intFocus=1;
				keyback=false;
			}
			else if(v.equals(etPrecio))
			{
				intFocus=2;
				keyback=false;
			}
	}
    private boolean validaPrecio(long precio)
    {
    	if(precio>0)
    	{
    			if( precio>=articulo.getPrecio((int) parametrosPos.getPrecioMinimo()) || (parametrosPos.isValue(parametrosPos.getPrecioLibre()) & !parametrosPos.isValue(parametrosPos.getControlaPrecioLibre())))
					{
		    			return true;
					}
		    		else
		    		{
		    			mostrarMensaje("El precio del articulo debe ser mayor o igual al precio "+parametrosPos.getPrecioMinimo(), "l");
		    			return false;
		    		}

    	}
    	else
		{
			mostrarMensaje("El precio del articulo debe ser mayor que 0", "l");
			return false;
		}
    }
    private void addArticulo()
    {
    	try
		{
		 long precio=Long.parseLong(etPrecio.getText().toString());
		 double cantidad =Double.parseDouble(etCantidad.getText().toString());
		 		if(cantidad>0)
		 		{		 			
							if(validaPrecio(precio) )
							{
								if (operacion==PEDIDO & parametrosPos.getPermiteStocken0EnPedido() == 0 & cantidad > articulo.stock)
								{
									etCodigo.selectAll();
									etCodigo.requestFocus();
									existe = false;
									mostrarMensaje("El Articulo seleccionado esta Agotado","l");
								}
								else if(parametrosPos.getConsultaArticuloEnLinea()==1 &parametrosPos.getPermiteStocken0()==0& cantidad>articulo.stock & operacion!=PEDIDO)
				              	  {
									 etCodigo.selectAll();
					        		 etCodigo.requestFocus();	
					        		 existe = false;
					        		 mostrarMensaje("El Articulo seleccionado esta Agotado","l");				        		 
				              	  }
								else
								{
									if(parametrosPos.getConsultaArticuloEnLinea()==1)
									{
										ArrayList<Articulo> listaArticulos=new ArrayList<Articulo>();
										listaArticulos.add(articulo);
										bd.insertArticulo(listaArticulos);
									}	
											//ingresa
											if(operacion==PEDIDO)
											{
												long orden=bd.obtenerUltimoPedidoArticulo(this, pedido.idCodigoInterno);
												double cantidadExistente=bd.getCantidadSiExistePedido(this, pedido.idCodigoInterno, articulo.idArticulo);
												if(cantidadExistente > 0)
												{
													if(!modificaCantidad)
														{
															cantidad+=cantidadExistente;
														}
													if(bd.ActualizarPedidoArticulo(pedido.idCodigoInterno, articulo.idArticulo, cantidad, precio,(long) (cantidad*precio),tipoPrecio));
													{
														mostrarMensaje("Articulo Modificado Correctamente", "l");										
													}											
													modificaCantidad=false;									
												}
												else
												{


													if(bd.insertPedidoArticulos(pedido.idCodigoInterno, articulo.idArticulo, cantidad, precio,(long) (cantidad*precio),orden+1, articulo.idCodigo,articulo.getStockint(),"",tipoPrecio));
													{
														mostrarMensaje("Articulo Ingresado Correctamente", "l"); 										
													}									
												}	
											}
											else if(operacion==FACTURA)
											{
												long orden=bd.obtenerUltimoFacturaArticulo(this, factura.idCodigoInterno);
												double cantidadExistente=bd.getCantidadSiExisteFactura(this, factura.idCodigoInterno, articulo.idArticulo);
												if(cantidadExistente > 0)
												{
													if(!modificaCantidad)
													{
														cantidad+=cantidadExistente;
													}
													if(bd.ActualizarFacturaArticulo(factura.idCodigoInterno, articulo.idArticulo, cantidad, precio, (long) (cantidad*precio)));
													{
														mostrarMensaje("Articulo Modificado Correctamente", "l");
													}
													modificaCantidad=false;
												}
												else
												{
													if(bd.insertFacturaArticulos(factura.idCodigoInterno, articulo.idArticulo, cantidad, precio, (long) (cantidad*precio),orden+1, articulo.idCodigo,articulo.getStockint(),tipoPrecio ));
													{
														mostrarMensaje("Articulo Ingresado Correctamente", "l");
													}
												}
											}
											else if(operacion==REMISION)
											{
												long orden=bd.obtenerUltimoRemisionArticulo(this, remision.idCodigoInterno);
												int cantidadExistente=bd.getCantidadSiExisteRemision(this, remision.idCodigoInterno, articulo.idArticulo);
												if(cantidadExistente > 0)
												{
													if(!modificaCantidad)
													{
														cantidad+=cantidadExistente;
													}
													if(bd.ActualizarRemisionArticulo(remision.idCodigoInterno, articulo.idArticulo,cantidad, precio, (long) (cantidad*precio)));
													{
														mostrarMensaje("Articulo Modificado Correctamente", "l");
													}
													modificaCantidad=false;
												}
												else
												{
													if(bd.insertRemisionArticulos(remision.idCodigoInterno, articulo.idArticulo,  cantidad, precio, (long) (cantidad*precio),orden+1, articulo.idCodigo,articulo.getStockint()));
													{
														mostrarMensaje("Articulo Ingresado Correctamente", "l");
													}
												}
											}
											else if(operacion==TRANSLADO)
											{
												long orden=bd.obtenerUltimoTrasladoArticulo(this, traslado.idCodigoInterno);
												int cantidadExistente=bd.getCantidadSiExisteTraslado(this, traslado.idCodigoInterno, articulo.idArticulo);
												if(cantidadExistente > 0)
												{
													if(!modificaCantidad)
														{
															cantidad+=cantidadExistente;
														}
													if(bd.ActualizarTrasladoArticulo(traslado.idCodigoInterno, articulo.idArticulo, (long) cantidad, precio, (long) (cantidad*precio)));
													{
														mostrarMensaje("Articulo Modificado Correctamente", "l");										
													}											
													modificaCantidad=false;									
												}
												else
												{													
													if(bd.insertTrasladoArticulos(traslado.idCodigoInterno, articulo.idArticulo, (long) cantidad, precio, (long) (cantidad*precio),orden+1, articulo.idCodigo,articulo.getStockint()));
													{
														mostrarMensaje("Articulo Ingresado Correctamente", "l"); 										
													}									
												}	
											}
									limpiarCampos();
								}
								
							}
		 		}
				else
				{
					mostrarMensaje("La cantidad del articulo debe ser mayor que 0", "l");
					etCantidad.requestFocus();
							
				}
				
		}
	catch (Exception e) 
		{
			mostrarMensaje("Debe Ingresar el precio y cantidad del articulo", "l");
			etCantidad.requestFocus();
		}
    }
    private void agregaListaArticulos( ArrayList <Articulo> listaSelec)
	{
		for (int j = 0; j <listaSelec.size() ; j++) {
		Articulo art=listaSelec.get(j);

			if(parametrosPos.getConsultaArticuloEnLinea()==1)
			{
				ArrayList<Articulo> listaArticulos=new ArrayList<Articulo>();
				listaArticulos.add(art);
				bd.insertArticulo(listaArticulos);
			}
			//ingresa
			if(operacion==PEDIDO)
			{
				long orden=bd.obtenerUltimoPedidoArticulo(this, pedido.idCodigoInterno);
				double cantidadExistente=bd.getCantidadSiExistePedido(this, pedido.idCodigoInterno, art.idArticulo);
				if(cantidadExistente > 0)
				{
					if(!modificaCantidad)
					{
						art.cantPedir+=cantidadExistente;
					}
					if(bd.ActualizarPedidoArticulo(pedido.idCodigoInterno, art.idArticulo, art.cantPedir, art.getPrecioUnitario(),(long) (art.cantPedir*art.getPrecioUnitario()),art.getTipoPrecio()));
					{
						//mostrarMensaje("Articulo Modificado Correctamente", "l");
					}
					modificaCantidad=false;
				}
				else
				{


					if(bd.insertPedidoArticulos(pedido.idCodigoInterno, art.idArticulo, art.cantPedir, art.getPrecioUnitario(),(long) (art.cantPedir*art.getPrecioUnitario()),orden+1, art.idCodigo,art.getStockint(),"",art.getTipoPrecio()));
					{
						//mostrarMensaje("Articulo Ingresado Correctamente", "l");
					}
				}
			}
			else if(operacion==FACTURA)
			{
				long orden=bd.obtenerUltimoFacturaArticulo(this, factura.idCodigoInterno);
				double cantidadExistente=bd.getCantidadSiExisteFactura(this, factura.idCodigoInterno, art.idArticulo);
				if(cantidadExistente > 0)
				{
					if(!modificaCantidad)
					{
						art.cantPedir+=cantidadExistente;
					}
					if(bd.ActualizarFacturaArticulo(factura.idCodigoInterno, art.idArticulo,(long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario())));
					{
						//mostrarMensaje("Articulo Modificado Correctamente", "l");
					}
					modificaCantidad=false;
				}
				else
				{
					if(bd.insertFacturaArticulos(factura.idCodigoInterno, art.idArticulo, (long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario()),orden+1, art.idCodigo,art.getStockint(),art.getTipoPrecio()));
					{
						//mostrarMensaje("Articulo Ingresado Correctamente", "l");
					}
				}
			}
			else if(operacion==REMISION)
			{
				long orden=bd.obtenerUltimoRemisionArticulo(this, remision.idCodigoInterno);
				int cantidadExistente=bd.getCantidadSiExisteRemision(this, remision.idCodigoInterno, art.idArticulo) ;
				if(cantidadExistente > 0)
				{
					if(!modificaCantidad)
					{
						art.cantPedir+=cantidadExistente;
					}
					if(bd.ActualizarRemisionArticulo(remision.idCodigoInterno, art.idArticulo,(long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario())));
					{
						//mostrarMensaje("Articulo Modificado Correctamente", "l");
					}
					modificaCantidad=false;
				}
				else
				{
					if(bd.insertRemisionArticulos(remision.idCodigoInterno, art.idArticulo, (long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario()),orden+1, art.idCodigo,art.getStockint()));
					{
						//mostrarMensaje("Articulo Ingresado Correctamente", "l");
					}
				}
			}
			else if(operacion==TRANSLADO)
			{
				long orden=bd.obtenerUltimoTrasladoArticulo(this, traslado.idCodigoInterno);
				int cantidadExistente=bd.getCantidadSiExisteTraslado(this, traslado.idCodigoInterno, art.idArticulo);
				if(cantidadExistente > 0)
				{
					if(!modificaCantidad)
					{
						art.cantPedir+=cantidadExistente;
					}
					if(bd.ActualizarTrasladoArticulo(traslado.idCodigoInterno, art.idArticulo, (long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario())));
					{
						//mostrarMensaje("Articulo Modificado Correctamente", "l");
					}
					modificaCantidad=false;
				}
				else
				{
					if(bd.insertTrasladoArticulos(traslado.idCodigoInterno, art.idArticulo, (long) art.cantPedir, art.getPrecioUnitario(), (long) (art.cantPedir*art.getPrecioUnitario()),orden+1, art.idCodigo,art.getStockint()));
					{
						//mostrarMensaje("Articulo Ingresado Correctamente", "l");
					}
				}
			}
			limpiarCampos();
		}

        mostrarMensaje(listaSelec.size() +" Articulos Ingresados", "l");
	}

    /**
     * metodo que se encarga de validar si el codigo que se ingreso pertenece a 
     * algun producto almacenado en la base de datos	
     * @param codigo
     * @return true si existe y de lo contrario false
     */
	public Boolean buscarArticulo(String codigo)
	{	
		creaBD bd=new creaBD(this);
        bd.openDB();
        articulo=bd.getArticuloPorCodigo(this, codigo);
        bd.closeDB();
		
        if(articulo!=null)
        { 
        		textView[2].setText(articulo.nombre);
        	
            	etPrecio.setText(Long.toString(getPrecioCliente())); 
            	btPrecio.setText(cliente.getTextoPrecioDefecto());
				textView[11].setText(" Stock. "+articulo.getStockint());
				textView[11].setVisibility(View.VISIBLE);
				textView[11].setTextColor(Color.parseColor("#0C5716"));


        	return true;
        }
        else
        {	
        	return false;
        }
	}
	/**
	 * metodo que se encarga de retornar el precio del producto para determinado cliente
	 * @return el valor del precio asignado al cliente
	 */
	public long getPrecioCliente()
	{		
		long respuesta=0;
		switch (Integer.parseInt(cliente.PrecioDefecto))
		{
		case 1: respuesta=articulo.precio1; break;
		case 2: respuesta=articulo.precio2; break;
		case 3: respuesta=articulo.precio3; break;
		case 4: respuesta=articulo.precio4; break;
		case 5: respuesta=articulo.precio5; break;
		case 6: respuesta=articulo.precio6; break;
	
		}
		return respuesta;
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
	/**
	 * metodo que se encarga de borrar articulos almacenados temporalmente 
	 * de algun pedido que no fue enviado anteriormente

	 */
	public void cargaDatosPedido()
	{
		
		 if(pedido.idCodigoInterno==0)
	        {
		        bd=new creaBD(this);
		        bd.openDB();
		        pedido.idCodigoInterno=bd.obtenerUltimoIdPedido(this);
		        if(pedido.idCodigoInterno==0)
		        {
		        	pedido.idCodigoInterno=12000;
		        }
		        else
		        {
		        	pedido.idCodigoInterno++;
		        }
		        bd.eliminarArticulosDePedido(pedido.idCodigoInterno);		        
			    textView[0].setText(Long.toString(pedido.idCodigoInterno));
		        bd.closeDB();
	        }
		 else
		 {
			 if(pedido.idCodigoExterno==0)
			 {
				 textView[0].setText(Long.toString(pedido.idCodigoInterno));
			 }
			 else
			 {
				 textView[0].setText("env "+Long.toString(pedido.idCodigoExterno));
			 }			  
		 }		
	}
	public void cargaDatosFactura()
	{
		textView[4].setText("No. Factura");
		 if(factura.idCodigoInterno==0)
	        {
		        bd=new creaBD(this);
		        bd.openDB();
		        factura.idCodigoInterno=bd.obtenerUltimoIdFactura(this);
		        if(factura.idCodigoInterno==0)
		        {
		        	factura.idCodigoInterno=1;
		        }
		        else
		        {
		        	factura.idCodigoInterno++;
		        }
		        bd.eliminarArticulosDeFactura(factura.idCodigoInterno);		        
			    textView[0].setText(Long.toString(factura.idCodigoInterno));
		        bd.closeDB();
	        }
		 else
		 {
			 if(factura.idCodigoExterno==0)
			 {
				 textView[0].setText(Long.toString(factura.idCodigoInterno));
			 }
			 else
			 {
				 textView[0].setText("env "+Long.toString(factura.idCodigoExterno));
			 }			  
		 }		
	}
	public void cargaDatosRemision()
	{
		textView[4].setText("No. Cotización");
		if(remision.idCodigoInterno==0)
		{
			bd=new creaBD(this);
			bd.openDB();
			remision.idCodigoInterno=bd.obtenerUltimoIdRemision(this);
			if(remision.idCodigoInterno==0)
			{
				remision.idCodigoInterno=1;
			}
			else
			{
				remision.idCodigoInterno++;
			}
			bd.eliminarArticulosDeRemision(remision.idCodigoInterno);
			textView[0].setText(Long.toString(remision.idCodigoInterno));
			bd.closeDB();
		}
		else
		{
			if(remision.idCodigoExterno==0)
			{
				textView[0].setText(Long.toString(remision.idCodigoInterno));
			}
			else
			{
				textView[0].setText("env "+Long.toString(remision.idCodigoExterno));
			}
		}
	}
	public void cargaDatosTraslado()
	{
		textView[4].setText("No. Traslado");
		 if(traslado.getIdCodigoInterno()==0)
	        {
		        bd=new creaBD(this);
		        bd.openDB();
		        traslado.setIdCodigoInterno(bd.obtenerUltimoIdTraslado(this));
		        if(traslado.getIdCodigoInterno()==0)
		        {
		        	traslado.setIdCodigoInterno(1);
		        }
		        else
		        {
		        	traslado.setIdCodigoInterno(traslado.getIdCodigoInterno()+1);
		        }
		        bd.eliminarArticulosDeTraslado(traslado.getIdCodigoInterno());		        
			    textView[0].setText(Long.toString(traslado.getIdCodigoInterno()));
		        bd.closeDB();
	        }
		 else
		 {
			 if(traslado.getIdCodigoExterno()==0)
			 {
				 textView[0].setText(Long.toString(traslado.getIdCodigoInterno()));
			 }
			 else
			 {
				 textView[0].setText("env "+Long.toString(traslado.getIdCodigoExterno()));
			 }			  
		 }		
	}
	/**
	 * metodo que se encarga de limpiar las cajas de texto y etiquetas de la actividad
	 */
	public void limpiarCampos()
	{
		etCodigo.setText("");
		etCantidad.setText("");
		textView[2].setText("");
		etPrecio.setText("");
		textView[11].setText("");
		existe=false;
		etCodigo.requestFocus();
	
	}
	/**
	 * metodo que se ejectuta cuando una actividad que fue lanzada desde esta clase se cierra
	 * y ejecuta las acciones definidas ya sea la actividar para buscar un articulo por nombre
	 * o ver los articulos del pedido
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SUB_ACTIVITY_REQUEST_CODE)
			{
				String etapa="0";
			 try
				{
				  limpiarCampos();
					if (parametrosPos.getUsaSelecMultipleArt()==0) {
						Bundle b = data.getExtras();
						articulo.idArticulo = b.getLong("idArticulo");
						articulo.idCodigo = b.getString("idCodigo");
						articulo.nombre = b.getString("nombre");
						articulo.precio1 = b.getLong("precio1");
						articulo.precio2 = b.getLong("precio2");
						articulo.precio3 = b.getLong("precio3");
						articulo.precio4 = b.getLong("precio4");
						articulo.precio5 = b.getLong("precio5");
						articulo.precio6 = b.getLong("precio6");
						articulo.activo = b.getLong("activo");
						articulo.stock = b.getDouble("stock");

						etapa = "1";
						textView[2].setText(articulo.nombre);
						etPrecio.setText(Long.toString(getPrecioCliente()));
						btPrecio.setText(cliente.getTextoPrecioDefecto());
						etapa = "2";
						etCodigo.setText(articulo.getCodigo());

                        if(articulo.getStockint()>=0)
                        {
                            etapa = "3";
                            textView[11].setText(" Stock. "+articulo.getStockint());
                            textView[11].setVisibility(View.VISIBLE);
                        }

						agrega = true;
						etCantidad.requestFocus();
					}
					else
					{
						ArrayList<Articulo> listaSelec=data.getParcelableArrayListExtra("listaSelec");
						agregaListaArticulos(listaSelec);
					}
				}
				catch(Exception e)
				{
					mostrarMensaje("Etapa "+etapa+" No Selecciono ningun producto", "l");
				}
			}
		//   MODIFICAR PEDIDOS
		else if(requestCode == SUB_ACTIVITY_VER_PEDIDOS)
		{
		
			try
			{
				limpiarCampos();
				Bundle b = data.getExtras();
				boolean envio=b.getBoolean("Envio");
				
				if(envio)
				{
					finish();
				}
				else
				{
					  articulo.idArticulo = b.getLong("idArticulo");
	                  articulo.idCodigo = b.getString("idCodigo");
	                  articulo.nombre =  b.getString("nombre");
	                  articulo.precio1 = b.getLong("precio1");
	                  articulo.precio2 = b.getLong("precio2");
	                  articulo.precio3 = b.getLong("precio3");
	                  articulo.precio4 = b.getLong("precio4");
	                  articulo.precio5 = b.getLong("precio5");
	                  articulo.precio6 = b.getLong("precio6");           
	                  articulo.stock =b.getLong("stock");
					  articulo.iva =b.getLong("iva");

				  etCodigo.setText( articulo.idCodigo);
				  etPrecio.setText(b.getLong("valorUnitario")+"");
				  textView[2].setText(articulo.nombre);

					if(parametrosPos.getUsaCantDecimal()==1) {
						etCantidad.setText(Double.toString(b.getDouble("cantidad")));
					}
					else
					{
						etCantidad.setText(""+(long)b.getDouble("cantidad"));
					}


					if(articulo.getStockint()>=0)
					{

						textView[11].setText(" Stock. "+articulo.getStockint());
						textView[11].setVisibility(View.VISIBLE);
					}
              	  if(etCantidad.getText().length()>0) {
					  etCantidad.selectAll();
					  etCantidad.requestFocus();
				  }
				  else
				  {
					  etCodigo.selectAll();
					  etCodigo.requestFocus();
				  }
	          	  modificaCantidad=true;
			}         	  
			}
			catch(Exception e)
			{
//				mostrarMensaje("No Selecciono ningun producto", "l");
			}
		}
		
		else
			{
				finish();
			}
    }
	/**
	 * metodo que se encarga de dar estilo a las etiquetas de la actividad
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
//	 * metodo que se encarga de dar estilo a la letra de botones
//	 * @param tv
//	 */
//	public void getEstilo(Button tv)
//	{
//		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
//	}
	/**
	 * metodo que se ejecuta presionar alguna de las teclas fuera de la pantalla
	 * como por ejemplo  la tecla up-vol, down-vol, back, home, menu, cam, entre otros
	 */
	public boolean onKeyDown(int keyCode, KeyEvent  event) 
	{
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {        	
        	closeActivity();
        	return true;
        }
        else
        {
        	return false;
        }
        
        
	}
	public void closeActivity()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	String mensaje="el pedido ?";
    	if(operacion==FACTURA)
    	{
    		mensaje="la factura ?";
    	}
    	else if(operacion==REMISION)
		{
			mensaje="la cotización ?";
		}
    	else if(operacion==TRANSLADO)
    	{
    		mensaje="el traslado ?";
    	}
    		dialog.setMessage("� Esta seguro que desea Eliminar "+ mensaje);
    	
    	dialog.setCancelable(false);
    	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
    	
    	  public void onClick(DialogInterface dialog, int which) {
    		  CrearPedidoActivity.this.finish();
    	  }
    	});
    	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	 
    	
    	   public void onClick(DialogInterface dialog, int which) {
    	      dialog.cancel();
    	   }
    	});
    	dialog.show();
	}
	/**
	 * metodo que se ejecuta al presionar cualquier tecla en la pantalla
	 */
	public boolean onKey(View v, int keyCode, KeyEvent event) 
	{
		if(v.equals(etCodigo))
		{
			if(keyCode==KeyEvent.KEYCODE_ENTER)
	           {
				
					if(existe)
					{
					    codigo=etCodigo.getText().toString();
					   if(!codigo.equals(""))
					   {
						   if(parametrosPos.getConsultaArticuloEnLinea()==0)
						   {
						        	   if(buscarArticulo(codigo))
							             {

											 if(parametrosSys.isValue(parametrosSys.getModificaValorTotal()) && (operacion==PEDIDO||operacion==FACTURA))
										 	 {
											 	// modifica valor total y agrega articulo a la factura
												 if(operacion==PEDIDO)
												 {
													 modificarTotalArticuloPedido();
												 }
												 else if(operacion==FACTURA) {
													 modificarTotalArticuloFactura();
												 }
												 existe = false;
												 return true;

												 //-----------------------------------------------------------------------------------------

											 }
											 else
											 {
												 agrega=true;
												 etCantidad.requestFocus();
												 return true;
											 }

							             }
						        	   else
						        	   {
						        		   etCodigo.selectAll();
						        		   etCodigo.requestFocus();
						        		   existe = false;
						        		   mostrarMensaje("El Codigo del Articulo no Existe","l");		        		   
						        		   return true;
						        	   }
						   }
						   else
						   {
								new getStock().execute("");
								pdu=ProgressDialog.show(this,"Por Favor Espere", "Obteniendo datos", true,false);
							    etCodigo.selectAll();
			        		    etCodigo.requestFocus();
			        		    existe = false;
			        		    return true;
						   }
					   }
					   else
					   {
						   etCodigo.selectAll();
		        		   etCodigo.requestFocus();
		        		   existe = false;
		        		   mostrarMensaje("Debe Ingresar el Codigo del articulo","l");		        		   
		        		   return true;
					   }
					}
					else
					{
						existe = true;
						return true;
					}
	          
			}			
					
		}
		if(v.equals(etCantidad))
		{
			 if(keyCode == KeyEvent.KEYCODE_ENTER)				 
				 {
				 if(!textView[2].getText().toString().equals(""))
					{
					 if(!agrega)
					 {
						 addArticulo();
						 return true;
					 }
					 agrega=false;
					 }
					else
					{
						mostrarMensaje("Debe Seleccionar el articulo", "l");
						existe=false;
						etCodigo.requestFocus();
						return true;
					}
				 }
			 
		}
		return false;
	}

	/**
	 * En esta clase  se ejecuta un proceso en background al momento de realizar la
	 * actualizacion de los productos
	 * @author Javier
	 *
	 */
//	private class getActualizaProductos extends AsyncTask<String, Void, Object>
//	{
//		String  res ="";		
//		@Override
//			protected Integer doInBackground(String... params) 
//			{		
//					llamarArticulos=new LlamarArticulos(parametrosPos);
//					ArrayList larticulos=new ArrayList();
//				
////					larticulos=llamarArticulos.getArticulos("CIGARRILLOS", larticulos );
////					larticulos=llamarArticulos.getArticulos("BIG COLA", larticulos );
////					
//					if(larticulos!=null)
//					{
//						bd.openDB();
//						bd.insertArticulo(larticulos);
//						bd.closeDB();
//						res="true";	
//						String reportDate="";
//						LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
//						 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
//						 try {
//							Date dhora=new Date();
//							Date startDate=new Date();							 
//							startDate = df.parse(llamarFecha.getFecha());
//							SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
//							SimpleDateFormat df3 = new SimpleDateFormat("HHmm");
//
////							reportDate = df2.format(startDate)+df3.format(dhora);
//							reportDate = df2.format(startDate)+"0600";
//														
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//						
//						parametrosPos.setFecha(reportDate);
//						bd.openDB();
//						bd.ActualizarParametros(parametrosPos);
//						bd.closeDB();
//						
//					}					
//					return 1;	
//			}
//		
//		
//			protected void onPostExecute(Object result)
//			{
//				pdu.dismiss();
//				if(res.equals("true")||llamarArticulos.getResultado().equals("ok"))
//				{						
//						mostrarMensaje("Productos Actualizados Correctamente.","l");					
//				}
//			
//				else 
//				{						
//						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
//						mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
//				}		
//				
//			}
//			
//			
//	}
	
	/**
	 * En esta clase se un proceso en background que envia la localizacion del cliente
	 * al sistema de georeferenciaci�n
	 * @author Javier
	 *
	 */
	
	private class getLocalizarCliente extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{		
				bd.openDB();
				clientesys = bd.getBuscarClientesSys(cliente.getIdCliente(),clientesys);
				clientesys.setObservacionVisita("Ninguna");
				bd.closeDB();				
				//EnviarVisitasClientes enviarCliente=new EnviarVisitasClientes(parametrosSys.getIp());
				//res =enviarCliente.setUbicacionCliente(clientesys);
				return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("1"))
				{			
						bd.openDB();
						cliente.setUbicado("SI");
						bd.ActualizarClienteUbicacion(cliente);
						bd.closeDB();
						imUbicacionCliente.setImageResource(R.drawable.localgreenhd);
						mostrarMensaje("Ubicaci�n enviada Correctamente.","l");					
				}
				else 
				{						
						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");					
				}	
				
			}			
	}
	
	/**
	 * Atributo handler referencia de la clase Handler
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
			    		clientesys.setObservacionVisita("Ninguna");
			    		new getLocalizarCliente().execute("");
			        	pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Ubicaci�n del Cliente"), true,false);
			    	}
				}
		    	else
		    	{
		    		AlertDialog.Builder builder = new AlertDialog.Builder(CrearPedidoActivity.this);
  		    		builder.setMessage("Debe Activar el GPS del Telefono para poder Continuar.")
  		    		        .setTitle("Información!!")
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
		 * asigna la nueva localizacion obtenidia al atributo  currentLocation
		 * @param loc
		 */
	  private void setCurrentLocation(Location loc) {
	    	currentLocation = loc;
	    }
	 /**
	  * metodo que se ejecuta al inicializar el hilo de la clase
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
	 * Clase en la que se define la accion a realizar al momento de obtener la localizacion
	 * @author user
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
	 private class getArticulosCategoria extends AsyncTask<String, Void, Object>
		{
				
			@Override
			protected Integer doInBackground(String... params) {			
				
				if(actualizaFecha)
				{
					LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
					 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
					try
					{
				
						Date startDate=new Date();	
						String fecha =llamarFecha.getFecha();
						if(!fecha.equals("Error"))
						{
							startDate = df.parse(fecha);
							SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
//							SimpleDateFormat df3 = new SimpleDateFormat("HHmm");
//							reportDate = df2.format(startDate)+df3.format(dhora);
							
							parametrosPos.fecha = df2.format(startDate)+"0600";	
						}
					}
					catch (Exception e)
					{				
					}				
					actualizaFecha=false;
				}
				larticulos= new ArrayList<Articulo>();
				categoria=new Categoria();
				categoria.setNombre(" ");
				if(!parametrosPos.isValue(parametrosPos.getUsaTodasLasCategorias()))
				{
					categoria=listaCategorias.get(identificador);
				}		
				
				if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
					LlamarArticulos llamarArticulos=new LlamarArticulos(parametrosPos);						
					larticulos = llamarArticulos.getArticulos(isAll,categoria.getNombre(),  categoria.getFechaAct(),larticulos);
				}				
				if(larticulos!=null & larticulos.size()>0)
				{
					bd.openDB();
					bd.insertArticulo(larticulos);//					
					categoria.setFechaAct(parametrosPos.getFechaSys2System());									
					bd.ActualizarCategoria(categoria);
					bd.closeDB();			
				}					
				return 1;
			}
			
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(larticulos!=null)
				{
					identificador++;
					if(identificador < listaCategorias.size())
					{
						
						if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
						{
							new getArticulosCategoria().execute("");
							pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos 1 de "+listaCategorias.size()), true,false);
						}
						else
						{
							new getSizeConsultaArticulos().execute("");
							pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
						}		
					}
					else
					{
						mostrarMensaje("Productos Actualizados Correctamente.","l");	
					}
				}	
				else
				{
					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
					mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
							
				}
			

		}		
		}	
	 
	 private class getArticulosSys extends AsyncTask<String, Void, Object>
		{
					
			@Override
			protected Integer doInBackground(String... params) {			
					
				larticulos= new ArrayList<Articulo>();
				LlamarArticulosSys llamarArticulosSys=new LlamarArticulosSys(parametrosSys);						
				larticulos = llamarArticulosSys.getArticulos(isAll,rangIn,rangOut,categoria.getNombre(),  categoria.getFechaAct(),larticulos,""+parametrosSys.getBodegaPedidosOmision());
						
				if(larticulos!=null)
				{
					if(larticulos.size()>0)
					{			
						bd.openDB();
						bd.insertArticulo(larticulos);					
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
						pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+porcentaje+"%"), true,false);
							
					}
					else
					{
						categoria.setFechaAct(parametrosPos.getFechaSys2System());
						bd.ActualizarCategoria(categoria);
						identificador++;
						if(identificador < listaCategorias.size() & !isAll)
						{					
							new getSizeConsultaArticulos().execute("");
							pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
						}
						else
						{
							mostrarMensaje("Productos Actualizados Correctamente.","l");	
						}
					}
					
				}	
				else
				{
					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
					mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
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
					if(!isAll)
					{
						categoria=listaCategorias.get(identificador);
					}		
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
								pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
						}
		   				else 
		   				{		
		   					identificador++;
							if(identificador < listaCategorias.size() & !isAll)
							{					
								new getSizeConsultaArticulos().execute("");
								pdu=ProgressDialog.show(CrearPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo Datos "+(identificador+1)+" de "+listaCategorias.size()+" "+"0%"), true,false);
							}
							else
							{
								mostrarMensaje("Productos Actualizados Correctamente.","l");	
							}
		   					
		   				}
	   				}
	   				else
	   				{
	   					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
						mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
						
	   				}  	
	   				
	   			}
	   	}
		private class getStock extends AsyncTask<String, Void, Object>
		{
			String  res ="";
			private ArrayList<Articulo> listaArt;
			@Override
				protected Integer doInBackground(String... params) 
				{	
				if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
					llamarStock=new LlamarStock(parametrosPos.getIp());
					listaArt=llamarStock.getLlamarStock(codigo, "", bodegaOmision.getIdBodega());
					res=llamarStock.getRes();
				}
				else
				{
					llamarStockSys=new LlamarStockSys(parametrosSys);
					listaArt=llamarStockSys.getArticulos("cod", ""+bodegaOmision.getIdBodega(), codigo, listaArt);
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
							if(listaArt.size()>0)
							{
									articulo=listaArt.get(0);
									textView[2].setText(articulo.nombre);
									if(parametrosPos.getConsultaArticuloEnLinea()==1)
					              	  {
					              		textView[11].setText("Stock. "+articulo.getStockint());
					              		textView[11].setVisibility(View.VISIBLE);
					              	  }
					            	etPrecio.setText(Long.toString(getPrecioCliente())); 
					            	btPrecio.setText(cliente.getTextoPrecioDefecto());
					            	agrega=true;
					        		etCantidad.requestFocus();								
							}
							 else
				        	   {
				        		   etCodigo.selectAll();
				        		   etCodigo.requestFocus();
				        		   existe = false;
				        		   mostrarMensaje("El Codigo del Articulo no Existe","l");		        		   
				        		  
				        	   }							
						}
						else
			        	   {
			        		   etCodigo.selectAll();
			        		   etCodigo.requestFocus();
			        		   existe = false;
			        		   mostrarMensaje("El Codigo del Articulo no Existe","l");		        		   
			        		  
			        	   }
					}
					else if(res.equals("false"))
					{
						 etCodigo.selectAll();
		        		   etCodigo.requestFocus();
		        		   existe = false;
		        		   mostrarMensaje("El Codigo del Articulo no Existe","l");		        		   
			        }
					else
					{
						etCodigo.selectAll();
	        		    etCodigo.requestFocus();
	        		    existe = false;
						mostrarMensaje("No fue posible establecer la conexion con el servidor", "l");
					}
			 }
		}


	private final void modificarTotalArticuloFactura()
	{




		final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("Modificar Valor");
		builder2.setMessage("Ingrese el valor total para  \n "+articulo.getNombre() );

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder2.create();

		final LayoutInflater inflater = getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.activity_modificar_pedido, null);
		test.setView(dialogView);



		final  EditText  etAlertValor = (EditText) dialogView.findViewById(R.id.etModificaValor);
		etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
		etAlertValor.selectAll();
		etAlertValor.setTextSize(16);
		etAlertValor.setText("0");



		final Button btAlertCancelar = (Button) dialogView.findViewById(R.id.btMVCancelar);
		btAlertCancelar.setText("Cancelar");
		final Button btAlertOk = (Button) dialogView.findViewById(R.id.btMVAceptar);
		btAlertOk.setText("Ok");



		final Button btMV10000 = (Button) dialogView.findViewById(R.id.btMV10000);
		final Button btMV20000 = (Button) dialogView.findViewById(R.id.btMV20000);
		final Button btMV50000 = (Button) dialogView.findViewById(R.id.btMV50000);
		final Button btMV100000 = (Button) dialogView.findViewById(R.id.btMV100000);
		final Button btMV200000 = (Button) dialogView.findViewById(R.id.btMV200000);


		btMV10000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+10000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV20000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+20000;
					etAlertValor.setText(""+valor);

				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");

				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);
			}
		});
		btMV50000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+50000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV100000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+100000;
					etAlertValor.setText(""+valor);

				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV200000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+200000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});


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
						String value = etAlertValor.getText().toString();
						//if (value >= 0) {
						double valor = Double.parseDouble(value);
						if (valor>0) {

							test.cancel();
							long precio=Long.parseLong(etPrecio.getText().toString());
							double cant = (double) valor / (double) precio;


							if (operacion == FACTURA) {

								long orden=bd.obtenerUltimoFacturaArticulo(CrearPedidoActivity.this, factura.idCodigoInterno);
								double cantidadExistente=bd.getCantidadSiExisteFactura(CrearPedidoActivity.this, factura.idCodigoInterno, articulo.idArticulo);
								if(cantidadExistente > 0)
								{
									if(!modificaCantidad)
									{
										cant+=cantidadExistente;
									}
									if(bd.ActualizarFacturaArticulo(factura.idCodigoInterno, articulo.idArticulo, cant, precio, (long) (cant*precio)));
									{
										mostrarMensaje("Articulo Modificado Correctamente", "l");
									}
									modificaCantidad=false;
								}
								else
								{
									if(bd.insertFacturaArticulos(factura.idCodigoInterno, articulo.idArticulo, cant, precio, (long) (cant*precio),orden+1, articulo.idCodigo,articulo.getStockint(),tipoPrecio));
									{
										mostrarMensaje("Articulo Ingresado Correctamente", "l");
									}
								}
								limpiarCampos();
							}
						}
						else
						{
							mostrarMensaje("El nuevo valor debe ser mayor a 0", "l");
							etAlertValor.selectAll();
							etAlertValor.setFocusable(true);
						}



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
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etAlertValor, InputMethodManager.SHOW_IMPLICIT);
		etAlertValor.requestFocus();

		test.show();




	}
	private final void modificarTotalArticuloPedido()
	{




		final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("Modificar Valor");
		builder2.setMessage("Ingrese el valor total para  \n "+articulo.getNombre() );

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder2.create();

		final LayoutInflater inflater = getLayoutInflater();
		final View dialogView = inflater.inflate(R.layout.activity_modificar_pedido, null);
		test.setView(dialogView);



		final  EditText  etAlertValor = (EditText) dialogView.findViewById(R.id.etModificaValor);
		etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
		etAlertValor.selectAll();
		etAlertValor.setTextSize(16);
		etAlertValor.setText("0");



		final Button btAlertCancelar = (Button) dialogView.findViewById(R.id.btMVCancelar);
		btAlertCancelar.setText("Cancelar");
		final Button btAlertOk = (Button) dialogView.findViewById(R.id.btMVAceptar);
		btAlertOk.setText("Ok");



		final Button btMV10000 = (Button) dialogView.findViewById(R.id.btMV10000);
		final Button btMV20000 = (Button) dialogView.findViewById(R.id.btMV20000);
		final Button btMV50000 = (Button) dialogView.findViewById(R.id.btMV50000);
		final Button btMV100000 = (Button) dialogView.findViewById(R.id.btMV100000);
		final Button btMV200000 = (Button) dialogView.findViewById(R.id.btMV200000);


		btMV10000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+10000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV20000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+20000;
					etAlertValor.setText(""+valor);

				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");

				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);
			}
		});
		btMV50000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+50000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV100000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+100000;
					etAlertValor.setText(""+valor);

				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});
		btMV200000.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String value = etAlertValor.getText().toString();
					//if (value >= 0) {
					double valor = Double.parseDouble(value);
					valor=valor+200000;
					etAlertValor.setText(""+valor);
				} catch (Exception e )
				{
					mostrarMensaje("Formato numerico incorrecto", "l");
					etAlertValor.setText("0");
				}
				etAlertValor.selectAll();
				etAlertValor.setFocusable(true);

			}
		});


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
						String value = etAlertValor.getText().toString();
						//if (value >= 0) {
						double valor = Double.parseDouble(value);
						if (valor>0) {

							test.cancel();
							long precio=Long.parseLong(etPrecio.getText().toString());
							double cant = (double) valor / (double) precio;


							if (operacion == PEDIDO) {

								long orden=bd.obtenerUltimoPedidoArticulo(CrearPedidoActivity.this, pedido.idCodigoInterno);
								double cantidadExistente=bd.getCantidadSiExistePedido(CrearPedidoActivity.this, pedido.idCodigoInterno, articulo.idArticulo) ;
								if(cantidadExistente > 0)
								{
									if(!modificaCantidad)
									{
										cant+=cantidadExistente;
									}
									if(bd.ActualizarPedidoArticulo(pedido.idCodigoInterno, articulo.idArticulo, cant, precio, (long) (cant*precio),tipoPrecio));
									{
										mostrarMensaje("Articulo Modificado Correctamente", "l");
									}
									modificaCantidad=false;
								}
								else
								{
									if(bd.insertPedidoArticulos(pedido.idCodigoInterno, articulo.idArticulo, (double) cant, precio, (long) (cant*precio),orden+1, articulo.idCodigo,(double) articulo.getStockint(),"",tipoPrecio));
									{
										mostrarMensaje("Articulo Ingresado Correctamente", "l");
									}
								}
								limpiarCampos();
							}
						}
						else
						{
							mostrarMensaje("El nuevo valor debe ser mayor a 0", "l");
							etAlertValor.selectAll();
							etAlertValor.setFocusable(true);
						}



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
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etAlertValor, InputMethodManager.SHOW_IMPLICIT);
		etAlertValor.requestFocus();

		test.show();




	}




}
