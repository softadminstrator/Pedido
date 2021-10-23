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
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
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
import com.principal.mundo.EnviarFactura;
import com.principal.mundo.EnviarPedido;
import com.principal.mundo.EnviarRemision;
import com.principal.mundo.EnviarTraslado;
import com.principal.mundo.Factura;
import com.principal.mundo.Factura_in;
import com.principal.mundo.LlamarFecha;
import com.principal.mundo.LlamarFechaSys;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.EnviarUbicacionYPedido;
import com.principal.mundo.sysws.FacturaEnviarSys;
import com.principal.mundo.sysws.PutFacturaSys;
import com.principal.mundo.sysws.PutPedidoSys;
import com.principal.mundo.sysws.PutRemisionSys;
import com.principal.mundo.sysws.RemisionEnviarSys;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintFactura;
import com.principal.print.PrintZebra;

public class ListaArticulosPedidoActivity extends Activity implements OnClickListener, OnItemLongClickListener, Runnable {

	//--------------------------------------------------------
	//--------------------CONSTANTES--------------------------
	//--------------------------------------------------------
	protected static final int SUB_ACTIVITY_VER_PEDIDOS = 200;
	protected static final int SUB_ACTIVITY_TERMINAR = 300;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int REMISION=12;
	/**
	 * Atributo pdu referente a laclase ProgressDialog utilizado para mostrar mensajes al usuario
	 */
	private ProgressDialog pdu;
	/**
	 * Atributo listaArticulosPedido referente a la clase ListView que mostrar la lista de articulos del pedido
	 */
	ListView listaArticulos;
	/**
	 * Atributo btVolver referente al boton volver utilizado para regresar a la actividad crearpedido
	 */
	Button btVolver;
	/**
	 * Atributo btEnviar referente al boton de enviar utilizado para enviar el pedido al servidor
	 */
	Button btEnviar, btCredito, btContado,btDescuento;


	/**
	 * Atributo ListAP arreglo en el cual se guardan los articulos que contiene el pedido
	 */
	ArrayList<ArticulosPedido> listaAPedido;
	ArrayList<ArticulosFactura> listaAFactura;
	ArrayList<ArticulosTraslado> listaATraslado;
	ArrayList<ArticulosRemision> listaARemision;
	
	private boolean isCredito=false;
	
	/**
	 * Atributo textView que se encarga de guardar las etiquetas de la actividad
	 */	
	TextView [] textView;
	/**
	 * Atributo opciones hace referencia la clase Opciones que se visualizaran en el dialogo
	 * al momento de hacer modificaciones a los articulos del pedido
	 */
	 Opciones [] opciones;
	/**
	 * Atributo etObservaciones hace referencia a la caja de texto para ingresar la observacion del pedido
	 */
    EditText etObservacion;
    /**
     * Atributo letraEstilo referencia de la clase LetraEstilo
     */
    LetraEstilo letraEstilo;
    /**
     * Atributo db referencia de la clase creaBD
     */
    creaBD bd;   
	/**
	 * Atributo cliente referencia de la clase Cliente
	 */
	Cliente cliente=new Cliente();
	/**
	 * Atributo pedidoin referencia de la clase Pedido_in
	 */
	Pedido_in pedido=new Pedido_in();
	
	Factura_in factura=new Factura_in();

	Traslado_in traslado=new Traslado_in();

	Remision_in remision=new Remision_in();

	
	/**
	 * Atributo usuario referencia de la clase Usuario
	 */
	Usuario usuario=new Usuario();
	/**
	 * Atributo Pedido referencia de la clase Pedido
	 */
	Pedido pedidoEnviar=new Pedido();
	
	Factura facturaEnviar=new Factura();

	Remision remisionEnviar=new Remision();

	private int intFocus=0;
	
	Traslado trasladoEnviar=new Traslado();


	/**
	 * Atributo Venta referencia de la clase Venta
	 */
	
	FacturaEnviarSys facturaEnviarsys=new FacturaEnviarSys();

	RemisionEnviarSys remisionEnviarsys=new RemisionEnviarSys();
	
	
	/**
	 * Atributo pedidoEnviarSys referencia de la clase Pedido utilizado para enviar el pedido 
	 * al sistema de georeferenciacion
	 */
	com.principal.mundo.sysws.Pedido pedidoEnviarSys=new com.principal.mundo.sysws.Pedido();
	/**
	 * Atributo clientesys referencia de la clase Cliente utilizado para enviar el cliente al 
	 * systema de georeferenciacion
	 */
	com.principal.mundo.sysws.Cliente clientesys=new com.principal.mundo.sysws.Cliente();
	/**
	 * Atributo consulta de tipo boolean utilizado para validar si la actividad es utilizada 
	 * para consultar el pedido enviado "true" o si enviar "false"
	 */
	Boolean consulta;
	/**
	 * Atributo parametrosPos y parametrosSys referencia de la clase Parametros
	 * utilizados en el momento de enviar datos a los servidores
	 */
	Parametros parametrosPos, parametrosSys;	
	/**
	 * Atributo locManager referencia de la clase LocationManager	
	 */
	private LocationManager locManager;
	/**
	 * Atributo locListener referencia de la clase LocationListener	
	 */
	private LocationListener locListener;
	/**
	 * Atributo locManagerSys referencia de la clase LocationManager	
	 */
	private LocationManager locManagerSys;
	/**
	 * Atributo locListenerSys referencia de la clase MyLocationListener	
	 */
	private MyLocationListener locListenerSys;	
	/**
	 * Atributo currentLocation referencia de la clase Location	
	 */
	Location currentLocation = null;
	
	private int operacion;
	
	private RelativeLayout rlListaArticulos;
	
	private PrintFactura printFactura;

	private TextView tvDatosCliente;




	
	
	/**
	 * metodo que se encarga de asignar valores a los atributos de la clase
	 */
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	try
    	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_articulos_pedido);        
        consulta=false;
        textView =new TextView [13];
        letraEstilo=new LetraEstilo();

        bd=new creaBD(this);
        parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");

        if(!parametrosSys.isValue(parametrosSys.getModificaValorTotal())) {
			opciones = new Opciones[3];
			opciones[0] = new Opciones("Modificar cantidad", getImg(R.drawable.modificar), "Modificar");
			opciones[1] = new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");
			opciones[2] = new Opciones("Agregar observacion", getImg(R.drawable.consultar), "Agregar Observacion");
		}
        else
		{
			opciones = new Opciones[4];
			opciones[0] = new Opciones("Modificar cantidad", getImg(R.drawable.modificar), "Modificar");
			opciones[1] = new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");
			opciones[2] = new Opciones("Agregar observacion", getImg(R.drawable.consultar), "Agregar Observacion");
			opciones[3] = new Opciones("Modificar total", getImg(R.drawable.pedidos), "Modifica Total");

		}



        textView[1]=(TextView)findViewById(R.id.tvClienteTAP);
        textView[0]=(TextView)findViewById(R.id.tvReferenciaTR);        
        textView[2]=(TextView)findViewById(R.id.tvValorLA);
        textView[3]=(TextView)findViewById(R.id.tvObservaciones);
        textView[4]=(TextView)findViewById(R.id.tvTotal);
        textView[5]=(TextView)findViewById(R.id.tvReferenciaLAP);
        textView[6]=(TextView)findViewById(R.id.tvClienteLAP);        
        textView[7]=(TextView)findViewById(R.id.tvRutaLP);
        textView[8]=(TextView)findViewById(R.id.tvRutaLPN);  
        textView[9]=(TextView)findViewById(R.id.tvOperacionListaArticulos);
		textView[10]=(TextView)findViewById(R.id.tvValSubTotal);
		textView[11]=(TextView)findViewById(R.id.tvSubTotal);
		textView[12]=(TextView)findViewById(R.id.tvDescuento);
		// se implementan datos del cliente en la lista de articulos de pedido
		tvDatosCliente=(TextView)findViewById(R.id.tvDatosCliente);


      
        etObservacion=(EditText)findViewById(R.id.etObservacion);
        listaArticulos=(ListView)findViewById(R.id.lvArticulosPedidoN);
        rlListaArticulos=(RelativeLayout)findViewById(R.id.rlListaArticulos);
        btVolver=(Button)findViewById(R.id.btVolver);
        btVolver.setOnClickListener(this);
        btEnviar=(Button)findViewById(R.id.btEnviar);
        btEnviar.setOnClickListener(this);
			btDescuento=(Button)findViewById(R.id.btDescuento);
			btDescuento.setOnClickListener(this);

			etObservacion.setOnClickListener(this);
//        getEstilo(btVolver);
//        getEstilo(btEnviar);  
       
        listaAPedido=new ArrayList<ArticulosPedido>(); 
        listaAFactura=new ArrayList<ArticulosFactura>();
		listaARemision=new ArrayList<ArticulosRemision>();
        listaATraslado=new ArrayList<ArticulosTraslado>();
       
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
      
        operacion=obtenerDatos.getInt("operacion");
        usuario.cedula=obtenerDatos.getString("cedula");
        consulta=obtenerDatos.getBoolean("consulta");   
        textView[8].setText( usuario.cedula);
       
        if(operacion==TRANSLADO)
        {   
        	textView[6].setText("Bodega");
       	    rlListaArticulos.setBackgroundColor(0xFFE0E0E0);
       	    textView[9].setText("TRANSLADO");
       	    traslado.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
       	    traslado.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
       	    traslado.bodegaOrigen=bd.getBodega(obtenerDatos.getInt("idBodegaOrigen"));
        	traslado.bodegaDestino=bd.getBodega(obtenerDatos.getInt("idBodegaDestino"));
        	textView[1].setText("Origen. " +traslado.bodegaOrigen.getBodega()+" Destino. "+traslado.bodegaDestino.getBodega());
			tvDatosCliente.setText("");
        	 cargarArticulosTraslado();       
        }
        else
        {
        	
       	    cliente.nombre=obtenerDatos.getString("nombre");
			cliente.direccion=obtenerDatos.getString("direccion");
            cliente.ordenVisita=obtenerDatos.getLong("ordenVisita");
            cliente.idCliente=obtenerDatos.getLong("idCliente");
            cliente.idClienteSucursal= obtenerDatos.getLong("idClienteSucursal");

            textView[1].setText(cliente.ordenVisita+". " +cliente.nombre);
			tvDatosCliente.setText(cliente.direccion);


	         if(operacion==FACTURA)
	         {
	        	 rlListaArticulos.setBackgroundColor(0xFFE0E0E0);
	        	 textView[9].setText("FACTURA");
	        	 factura.idCliente=cliente.idCliente;
	        	 factura.idClienteSucursal=cliente.idClienteSucursal;
	         	 factura.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
	             factura.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");	            
	             cargarArticulosFactura(); 
	         }
	         else if(operacion==PEDIDO)
	         {
	        	 textView[9].setText("PEDIDO");
	        	 pedido.idCliente=cliente.idCliente;
				 pedido.idClienteSucursal=cliente.idClienteSucursal;
	        	 pedido.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
	             pedido.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
	             cargarArticulosPedido(); 
	         }
			 else if(operacion==REMISION )
			 {
				 rlListaArticulos.setBackgroundColor(0xFF5D9ECC);
				 textView[9].setText("COTIZACION");
				 remision.idCliente=cliente.idCliente;
				 remision.idClienteSucursal=cliente.idClienteSucursal;
				 remision.idCodigoExterno=obtenerDatos.getLong("idCodigoExterno");
				 remision.idCodigoInterno=obtenerDatos.getLong("idCodigoInterno");
				 cargarArticulosRemision();
			 }
		     	
        }

        if(!parametrosSys.isValue(parametrosSys.getDescuentoPedido()))
		{
			textView[10].setVisibility(TextView.GONE);
			textView[11].setVisibility(TextView.GONE);
			textView[12].setVisibility(TextView.GONE);
			btDescuento.setVisibility(Button.GONE);
		}
       
                     
        if(consulta)
	       {
	           	btEnviar.setVisibility(Button.GONE);
	        	textView[3].setVisibility(TextView.GONE);
	        	etObservacion.setVisibility(EditText.GONE);
	        	
	       }        
        else
        {
	    	 listaArticulos.setOnItemLongClickListener (new OnItemLongClickListener() {
            	 /**
            	  * metodo que se ejecuta al realizar click sostenido sobre un elemento de la lista de articulos
            	  * del pedido
            	  */
        	  public boolean onItemLongClick(AdapterView parent, final View view,  final int position, long id)
        	  {
        		  		final ListAdapter listaOpciones = new OpcionesAdapter(ListaArticulosPedidoActivity.this, opciones);
				        final  AlertDialog.Builder builder = new AlertDialog.Builder(ListaArticulosPedidoActivity.this);
        		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
		      			builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
		      			    public void onClick(DialogInterface dialog, int item) {
		      			    	if(item==0)
		      			    	{
		      			    		 modificarArticulo((int)position);
		      			    	}

		      			    	else if (item==1)
		      			    	{
		      			    			if(borrarArticulo((int)position))
		      			    			{
		      			    				mostrarMensaje("Articulo Eliminado Satisfactoriamente", "l");
		      			    			}
		      			    			else
		      			    			{
		      			    				mostrarMensaje("Articulo No fue Eliminado", "l");
		      			    			}

		      			    	}
								else if (item==2) {
									modificarArticulo(listaAPedido.get((int) position), view);

								}
								else if (item==3) {
									if(operacion==PEDIDO)
									{
										modificarTotalArticuloPedido(listaAPedido.get((int) position), view);
									}
									else if(operacion==FACTURA) {
										modificarTotalArticuloFactura(listaAFactura.get((int) position), view);
									}
									else if(operacion==REMISION) {
										modificarTotalArticuloRemision(listaARemision.get((int) position), view);
									}


								}


		      			    	dialog.cancel();
		      			    }
		      			});
		      			final AlertDialog alert = builder.create();
		      			alert.show();
        		  
        		  return false;
        		  }
        		});
        }	
    }
       catch (Exception e) {
		
	}
		etObservacion.setInputType(InputType.TYPE_NULL);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_articulos_pedido, menu);
        return true;
    }
    private void modificarArticulo(int position)
    {
    	Intent i = new Intent();
		Bundle b = new Bundle();
    	if(operacion==PEDIDO)
    	{
    		 ArticulosPedido art = listaAPedido.get((int)position);
    		  b.putLong("idArticulo", art.idArticulo);
    		  b.putString("idCodigo", art.codigo);
    		  b.putString("nombre", art.nombre);
    		  b.putLong("precio1", art.precio1);
    		  b.putLong("precio2", art.precio2);
    		  b.putLong("precio3", art.precio3);
    		  b.putLong("precio4", art.precio4);
    		  b.putLong("precio5", art.precio5);
    		  b.putLong("precio6", art.precio6);
    		  b.putDouble("cantidad", art.cantidad);
    		  b.putDouble("stock", art.getStock());
    		  b.putLong("valorUnitario", art.valorUnitario);					   					  				      					
    		  b.putBoolean("Envio", false);  
    	}
    	else if(operacion==FACTURA)
    	{
    		 ArticulosFactura art = listaAFactura.get((int)position);
	   		  b.putLong("idArticulo", art.idArticulo);
	   		  b.putString("idCodigo", art.codigo);
	   		  b.putString("nombre", art.nombre);
	   		  b.putLong("precio1", art.precio1);
	   		  b.putLong("precio2", art.precio2);
	   		  b.putLong("precio3", art.precio3);
	   		  b.putLong("precio4", art.precio4);
	   		  b.putLong("precio5", art.precio5);
	   		  b.putLong("precio6", art.precio6);
	   		  b.putDouble("cantidad", art.cantidad);
	   		  b.putDouble("stock", art.getStock());
	   		  b.putLong("valorUnitario", art.valorUnitario);					   					  				      					
	   		  b.putBoolean("Envio", false);  
    	}
		else if(operacion==REMISION)
		{
			ArticulosRemision art = listaARemision.get((int)position);
			b.putLong("idArticulo", art.idArticulo);
			b.putString("idCodigo", art.codigo);
			b.putString("nombre", art.nombre);
			b.putLong("precio1", art.precio1);
			b.putLong("precio2", art.precio2);
			b.putLong("precio3", art.precio3);
			b.putLong("precio4", art.precio4);
			b.putLong("precio5", art.precio5);
			b.putLong("precio6", art.precio6);
			b.putDouble("cantidad", art.cantidad);
			b.putDouble("stock", art.getStock());
			b.putLong("valorUnitario", art.valorUnitario);
			b.putBoolean("Envio", false);
		}
    	else if(operacion==TRANSLADO)
    	{
    		 ArticulosTraslado art = listaATraslado.get((int)position);
	   		  b.putLong("idArticulo", art.idArticulo);
	   		  b.putString("idCodigo", art.codigo);
	   		  b.putString("nombre", art.nombre);
	   		  b.putLong("precio1", art.precio1);
	   		  b.putLong("precio2", art.precio2);
	   		  b.putLong("precio3", art.precio3);
	   		  b.putLong("precio4", art.precio4);
	   		  b.putLong("precio5", art.precio5);
	   		  b.putLong("precio6", art.precio6);
	   		  b.putDouble("cantidad", art.cantidad);
	   		  b.putDouble("stock", art.getStock());
	   		  b.putLong("valorUnitario", art.valorUnitario);					   					  				      					
	   		  b.putBoolean("Envio", false);  
    	}	
		i.putExtras(b);			  
        setResult( SUB_ACTIVITY_VER_PEDIDOS, i );              
        finish();
    }
    
    private boolean borrarArticulo(int position)
    {
    	boolean res=false;
    	if(operacion==TRANSLADO)
    	{
    		ArticulosTraslado art = listaATraslado.get(position);
      		res= bd.eliminarTrasladoArticulo(art.idTraslado, art.idArticulo);
      		cargarArticulosTraslado();
    	}
    	else if(operacion==FACTURA)
    	{
    		ArticulosFactura art = listaAFactura.get(position);
      		res= bd.eliminarFacturaArticulo(art.idFactura, art.idArticulo);
      		cargarArticulosFactura();
    	}
		else if(operacion==REMISION)
		{
			ArticulosRemision art = listaARemision.get(position);
			res= bd.eliminarRemisionArticulo(art.idRemision, art.idArticulo);
			cargarArticulosRemision();
		}
    	else if(operacion==PEDIDO)
    	{
    		ArticulosPedido art = listaAPedido.get(position);
      		res= bd.eliminarPedidoArticulo(art.idPedido, art.idArticulo);
      		cargarArticulosPedido();    		
    	}
    	return res;
    	
    }

	private boolean ModificaTotalArticulo(int position)
	{
		boolean res=false;
		if(operacion==TRANSLADO)
		{
			ArticulosTraslado art = listaATraslado.get(position);
			res= bd.eliminarTrasladoArticulo(art.idTraslado, art.idArticulo);
			cargarArticulosTraslado();
		}
		else if(operacion==FACTURA)
		{
			ArticulosFactura art = listaAFactura.get(position);
			res= bd.eliminarFacturaArticulo(art.idFactura, art.idArticulo);
			cargarArticulosFactura();
		}
		else if(operacion==REMISION)
		{
			ArticulosRemision art = listaARemision.get(position);
			res= bd.eliminarRemisionArticulo(art.idRemision, art.idArticulo);
			cargarArticulosRemision();
		}
		else if(operacion==PEDIDO)
		{
			ArticulosPedido art = listaAPedido.get(position);
			res= bd.eliminarPedidoArticulo(art.idPedido, art.idArticulo);
			cargarArticulosPedido();
		}
		return res;

	}
    

    /**
     * Metodo que se ejecuta al realizar click en los botones de la actividad
     */
	public void onClick(View v) 
	{
		if(v.equals(btVolver))
		{
			finish();
		}		
		else if(v.equals(btEnviar))
		{
			enviarDatos();	
		}
		else if(v.equals(etObservacion))
		{
			intFocus=0;
			etObservacion.setInputType(InputType.TYPE_CLASS_TEXT);
			etObservacion.requestFocus();
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.showSoftInput(etObservacion, InputMethodManager.SHOW_FORCED);
		}
		else if(v.equals(btDescuento))
		{
			selectDescuento();
		}
		
	}
	
	private void enviarDatos()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
//				    imm.hideSoftInputFromWindow(etNombreCliente.getWindowToken(), 0);
		if(intFocus==0)
		{
			imm.hideSoftInputFromWindow(etObservacion.getWindowToken(), 0);
		}
		if(validaCliente())
		 {
				if(operacion==PEDIDO)
				{
					try {


						if (pedido.valor > 0) {
							if(parametrosPos.getSelectFormaPagoPedido()==0&& parametrosPos.getSelectDocumentoPedido()==0) {
								new enviarPedido().execute("");
								pdu = ProgressDialog.show(ListaArticulosPedidoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true, false);
							}

							//PREGUNTA SI SELECCIONA FORMA DE PAGO PARA PEDIDO
							else if(parametrosPos.getSelectFormaPagoPedido()==1  )
							{
								selectFormaPagoPedido(parametrosPos.getSelectDocumentoPedido()==1);
							}
							else
							{
								selectTipoDocumento();
							}

							//Envia Pedido



						} else {
							mostrarMensaje("Debe ingresar al menos un producto al pedido", "l");
						}
					}
					catch (Exception e)
					{
						mostrarMensaje("D:"+e.toString(), "l");
					}
				}
				else if(operacion==FACTURA)
				{
					if(factura.valor>0)
						{
							if(parametrosPos.getUsaCatalogo()==0)
							{
								isCredito=false;
								EnviarFactura();
							}
							else
							{
								selectFormaPago();
							}
												
						}
					else
						{
							mostrarMensaje("Debe ingresar al menos un producto a la Factura","l" );
						}
				}
				else if(operacion==REMISION)
				{
					if(remision.valor>0)
					{
						if(parametrosPos.getUsaCatalogo()==0)
						{
							isCredito=false;
							EnviarRemision();
						}
						else
						{
							selectFormaPago();
						}

					}
					else
					{
						mostrarMensaje("Debe ingresar al menos un producto a la Cotizacion","l" );
					}
				}
				else if(operacion==TRANSLADO)
				{
					if(traslado.totalTraslado>0)
								{							
								  final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
							        builder.setTitle("Forma de Pago");
							        builder.setMessage("Valor a pagar "+traslado.getFormatoDecimal(traslado.totalTraslado)+"\n Ingrese el dinero recibido:");						        
					//		         // Use an EditText view to get user input.
							        final AlertDialog test = builder.create(); 
							        LinearLayout llVertical=new LinearLayout(this);
							        llVertical.setOrientation(LinearLayout.VERTICAL);
							        final EditText etAlertValor = new EditText(this);
							        etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
							         llVertical.addView(etAlertValor);
							         LinearLayout llHorizontal=new LinearLayout(this);
							         llHorizontal.setOrientation(LinearLayout.HORIZONTAL); 				         
							            
							        final Button btAlertCancelar=new Button(this);
							        btAlertCancelar.setText("Cancelar");
							        btAlertCancelar.setWidth(100);
									final Button btAlertOk=new Button(this);
					//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
							        btAlertOk.setText("Ok");
							        btAlertOk.setWidth(100);
							        llHorizontal.addView(btAlertOk);
							        llHorizontal.addView(btAlertCancelar);
							       
							        llVertical.addView(llHorizontal);
							        
							        test.setView(llVertical);						       
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
								            		 long value = Long.parseLong(etAlertValor.getText().toString());
								            		 if(value>=traslado.totalTraslado)
								            		 {
								            			 	test.cancel();
								            			 	traslado.setDineroRecibido(value);
															new enviarTraslado().execute("");
															pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Traslado"), true,false);
														
								            		 }
								            		 else
								            		 {
								            			 
								            			 mostrarMensaje("Pago insuficiente","l" );
								            			 etAlertValor.selectAll();
								            			 etAlertValor.setFocusable(true);
										            		
								            		 }
										         }
								            	else
								            	{
								            		mostrarMensaje("Debe ingresar el dinero recibido","l" );
								            		etAlertValor.selectAll();
								            		etAlertValor.setFocusable(true);
								            	
								            	}
										}
									});
							        test.show();						
							}
						else
							{
								mostrarMensaje("Debe ingresar al menos un producto al traslado","l" );
							}
				}
		}
		else
		{
			mostrarMensaje("Debe Seleccionar el cliente","l" );					
		}
	}
	private void selectFormaPago()
	{
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Forma de Pago");
		    builder.setMessage("Seleccione la forma de pago: ");     					        
//		         // Use an EditText view to get user input.
	        final AlertDialog test = builder.create(); 
	        LinearLayout llVertical=new LinearLayout(this);
	        llVertical.setOrientation(LinearLayout.VERTICAL);         
	            
	        btCredito=new Button(this);
	        btCredito.setText("CREDITO");
	        btCredito.setWidth(100);
	        btContado=new Button(this);
	        btContado.setText("CONTADO");
	        btContado.setWidth(100);
	        llVertical.addView(btCredito);
	        llVertical.addView(btContado);        
	        test.setView(llVertical);						       
//		        final AlertDialog test = builder.create();
	        btCredito.setOnClickListener(new OnClickListener() {									
				public void onClick(View v) {										
					test.cancel();
					isCredito=true;
					if(operacion==REMISION)
					{
						EnviarRemision();
					}
					else
					{
						EnviarFactura();
					}

				}
			});
	        btContado.setOnClickListener(new OnClickListener() {					
				@SuppressLint("NewApi")
				public void onClick(View v) {
					test.cancel();
					isCredito=false;
					if(operacion==REMISION)
					{
						EnviarRemision();
					}
					else
					{
						EnviarFactura();
					}
				}
			});
	        test.show();	
	}
	private void selectTipoDocumento()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Documento");
		builder.setMessage("Seleccione el tipo de documento: ");
//		         // Use an EditText view to get user input.
		final AlertDialog test = builder.create();
		LinearLayout llVertical=new LinearLayout(this);
		llVertical.setOrientation(LinearLayout.VERTICAL);

		btCredito=new Button(this);
		btCredito.setText("Rem (41)");
		btCredito.setWidth(100);
		btContado=new Button(this);
		btContado.setText("Factura");
		btContado.setWidth(100);
		llVertical.addView(btCredito);
		llVertical.addView(btContado);
		test.setView(llVertical);
//		        final AlertDialog test = builder.create();
		btCredito.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				test.cancel();
				pedido.setDocumento("REM");
				new enviarPedido().execute("");
				pdu = ProgressDialog.show(ListaArticulosPedidoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true, false);


			}
		});
		btContado.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				test.cancel();
				pedido.setDocumento("FAC");
				new enviarPedido().execute("");
				pdu = ProgressDialog.show(ListaArticulosPedidoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true, false);


			}
		});
		test.show();
	}

	private void selectFormaPagoPedido(final boolean selecTipoDocumento)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Forma de Pago");
		builder.setMessage("Seleccione la forma de pago: ");
//		         // Use an EditText view to get user input.
		final AlertDialog test = builder.create();
		LinearLayout llVertical=new LinearLayout(this);
		llVertical.setOrientation(LinearLayout.VERTICAL);

		btCredito=new Button(this);
		btCredito.setText("CREDITO");
		btCredito.setWidth(100);
		btContado=new Button(this);
		btContado.setText("CONTADO");
		btContado.setWidth(100);
		llVertical.addView(btCredito);
		llVertical.addView(btContado);
		test.setView(llVertical);
//		        final AlertDialog test = builder.create();
		btCredito.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				test.cancel();
				pedido.setFormaPago("1");
				if(selecTipoDocumento)
				{
					selectTipoDocumento();
				}
				else
				{
					new enviarPedido().execute("");
					pdu = ProgressDialog.show(ListaArticulosPedidoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true, false);

				}

			}
		});
		btContado.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				test.cancel();
				pedido.setFormaPago("0");
				if(selecTipoDocumento)
				{
				selectTipoDocumento();
				}
				else
				{
					new enviarPedido().execute("");
					pdu = ProgressDialog.show(ListaArticulosPedidoActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido"), true, false);

				}
			}
		});
		test.show();
	}

	private void selectDescuento()
	{

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Descuento");
			builder.setMessage("Subtotal actual: "+pedido.getFormatoDecimal(pedido.getValor())+"\n Ingrese el valor del descuento:");


//		         // Use an EditText view to get user input.
		final AlertDialog test = builder.create();

		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(300, 150);
		LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpButton.weight = 1.0f;

		LinearLayout llVertical=new LinearLayout(this);
		llVertical.setLayoutParams(lp);
		llVertical.setOrientation(LinearLayout.VERTICAL);
		final EditText etAlertValor = new EditText(this);
		etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
		etAlertValor.setText(pedido.DescuentoTotal+"");
		llVertical.addView(etAlertValor);
		LinearLayout llHorizontal=new LinearLayout(this);
		llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

		final Button btAlertCancelar=new Button(this);
		btAlertCancelar.setText("Cancelar");
//	        btAlertCancelar.setWidth(150);
		btAlertCancelar.setLayoutParams(lpButton);
//	        btAlertCancelar.setHeight(40);
		final Button btAlertOk=new Button(this);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
		btAlertOk.setText("Ok");
		btAlertOk.setLayoutParams(lpButton);
//	        btAlertOk.setWidth(150);
//	       i btAlertOk.setHeight(40);
		llHorizontal.addView(btAlertOk);
		llHorizontal.addView(btAlertCancelar);

		llVertical.addView(llHorizontal);

		test.setView(llVertical);
//		        final AlertDialog test = builder.create();
		btAlertCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pedido.setDescuentoTotal(0);
				pedido.setValor(pedido.getSubTotal()-pedido.getDescuentoTotal());
				pedido.setObservaciones(etObservacion.getText().toString());
				bd.openDB();
				bd.ActualizarPedido(pedido);
				bd.closeDB();
				cargarArticulosPedido();
				test.cancel();
			}
		});
		btAlertOk.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				if(etAlertValor.getText().length()>0)
				{
					long value = Long.parseLong(etAlertValor.getText().toString());

						if(value<pedido.valor)
						{
							pedido.setDescuentoTotal(value);
							pedido.setValor(pedido.getSubTotal()-pedido.getDescuentoTotal());
							pedido.setObservaciones(etObservacion.getText().toString());
							bd.openDB();
							bd.ActualizarPedido(pedido);
							bd.closeDB();
							cargarArticulosPedido();
							test.cancel();
						}
						else
						{
							mostrarMensaje("El valor del descuento debe ser menor al del pedido","l" );
							etAlertValor.selectAll();
							etAlertValor.setFocusable(true);

						}

				}
				else
				{
					mostrarMensaje("Debe ingresar el valor del descuento","l" );
					etAlertValor.selectAll();
					etAlertValor.setFocusable(true);

				}
			}
		});
		test.show();
	}

	
	private boolean validaCliente()
	{
		cliente.setOrdenVisita(0);
		if(cliente.getIdCliente()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void EnviarFactura()
	{
		
		  final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		   
		   if(isCredito)
		   {
			    builder.setTitle("Autorizar");
		        builder.setMessage("Ingrese el codigo de Autorizacion:");	
		   }
		   else
		   {
			   builder.setTitle("Forma de Pago");
		        builder.setMessage("Valor a pagar "+factura.getFormatoDecimal(factura.valor)+"\n Ingrese el dinero recibido:");	 
		   }
	      					        
//		         // Use an EditText view to get user input.
	        final AlertDialog test = builder.create(); 
	        
	        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(300, 150);
	        LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(
	        	    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	        lpButton.weight = 1.0f;
	        
	        LinearLayout llVertical=new LinearLayout(this);
	        llVertical.setLayoutParams(lp);
	        llVertical.setOrientation(LinearLayout.VERTICAL);
		final EditText  etAlertValor = new EditText(this);
	        etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	        llVertical.addView(etAlertValor);
	        //ingresa el valor que le pagan en el cuadro de texto
			etAlertValor.setText(""+factura.valor);
			//---------------------------------------------------


	        LinearLayout llHorizontal=new LinearLayout(this);
	        llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

		final Button btAlertCancelar=new Button(this);
	        btAlertCancelar.setText("Cancelar");
//	        btAlertCancelar.setWidth(150);
	        btAlertCancelar.setLayoutParams(lpButton);
//	        btAlertCancelar.setHeight(40);
		final Button btAlertOk=new Button(this);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
	        btAlertOk.setText("Ok");
	        btAlertOk.setLayoutParams(lpButton);
//	        btAlertOk.setWidth(150);
//	       i btAlertOk.setHeight(40);
	        llHorizontal.addView(btAlertOk);
	        llHorizontal.addView(btAlertCancelar);
	       
	        llVertical.addView(llHorizontal);
	        
	        test.setView(llVertical);						       
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
		            		 long value = Long.parseLong(etAlertValor.getText().toString());
		            		 if(isCredito)
		            		 {
		            			 if(value ==123)
			            		 {
		            			 	test.cancel();
		            			 	factura.setDineroRecibido(0);
		            			 	factura.setVentaCredito(1);
		            			 	factura.setPagada("NO");
		            			 	factura.setValorPagado(0);
		            			 	
		            			 	//valida servidor de envio y factura onlien
		            			 	if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
		            			 	{
										new enviarFactura().execute("");
										pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Factura"), true,false);
		            			 	}
		            			 	else
		            			 	{
		            			 		guardarFacturaOffLine();
		            			 	}
			            		 }
		            			 else
		            			 {
		            				 mostrarMensaje("Clave de autorizacion incorrecta","l" );
			            			 etAlertValor.selectAll();
			            			 etAlertValor.setFocusable(true);
		            			 }
								
		            		 }
		            		 else
		            		 {		            		 
			            		 if(value>=factura.valor)
			            		 {
			            			 	test.cancel();
			            			 	factura.setDineroRecibido(value);
			            			 	factura.setVentaCredito(0);
			            			 	factura.setPagada("SI");
			            			 	factura.setValorPagado(value);
			            			 	
			            			 	//valida servidor de envio y factura onlien
			            			 	if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
			            			 	{
											new enviarFactura().execute("");
											pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Factura"), true,false);
			            			 	}
			            			 	else
			            			 	{
			            			 		guardarFacturaOffLine();
			            			 	}
									
			            		 }
			            		 else
			            		 {		            			 
			            			 mostrarMensaje("Pago insuficiente","l" );
			            			 etAlertValor.selectAll();
			            			 etAlertValor.setFocusable(true);
					            		
			            		 }
		            		 }
				         }
		            	else
		            	{
		            		mostrarMensaje("Debe ingresar el dinero recibido","l" );
		            		etAlertValor.selectAll();
		            		etAlertValor.setFocusable(true);
		            	
		            	}
				}
			});
	        test.show();		
	}

	private void EnviarRemision()
	{

		final  AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if(isCredito)
		{
			builder.setTitle("Autorizar");
			builder.setMessage("Ingrese el codigo de Autorizacion:");
		}
		else
		{
			builder.setTitle("Forma de Pago");
			builder.setMessage("Valor a pagar "+remision.getFormatoDecimal(remision.valor)+"\n Ingrese el dinero recibido:");
		}

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder.create();

		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(300, 150);
		LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lpButton.weight = 1.0f;

		LinearLayout llVertical=new LinearLayout(this);
		llVertical.setLayoutParams(lp);
		llVertical.setOrientation(LinearLayout.VERTICAL);
		final EditText  etAlertValor = new EditText(this);
		etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
		llVertical.addView(etAlertValor);
		LinearLayout llHorizontal=new LinearLayout(this);
		llHorizontal.setOrientation(LinearLayout.HORIZONTAL);

		final Button btAlertCancelar=new Button(this);
		btAlertCancelar.setText("Cancelar");
//	        btAlertCancelar.setWidth(150);
		btAlertCancelar.setLayoutParams(lpButton);
//	        btAlertCancelar.setHeight(40);
		final Button btAlertOk=new Button(this);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
		btAlertOk.setText("Ok");
		btAlertOk.setLayoutParams(lpButton);
//	        btAlertOk.setWidth(150);
//	       i btAlertOk.setHeight(40);
		llHorizontal.addView(btAlertOk);
		llHorizontal.addView(btAlertCancelar);

		llVertical.addView(llHorizontal);

		test.setView(llVertical);
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
					long value = Long.parseLong(etAlertValor.getText().toString());
					if(isCredito)
					{
						if(value ==123)
						{
							test.cancel();
							remision.setDineroRecibido(0);
							remision.setVentaCredito(1);
							remision.setPagada("NO");
							remision.setValorPagado(0);

							//valida servidor de envio y factura onlien
							if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
							{
								new enviarRemision().execute("");
								pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizacion"), true,false);
							}
							else
							{
								guardarRemisionOffLine();
							}
						}
						else
						{
							mostrarMensaje("Clave de autorizacion incorrecta","l" );
							etAlertValor.selectAll();
							etAlertValor.setFocusable(true);
						}

					}
					else
					{
						if(value>=remision.valor)
						{
							test.cancel();
							remision.setDineroRecibido(value);
							remision.setVentaCredito(0);
							remision.setPagada("SI");
							remision.setValorPagado(value);

							//valida servidor de envio y factura onlien
							if(parametrosPos.isValue(parametrosPos.getFacturaOnLine()))
							{
								new enviarRemision().execute("");
								pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Cotizacion"), true,false);
							}
							else
							{
								guardarRemisionOffLine();
							}

						}
						else
						{
							mostrarMensaje("Pago insuficiente","l" );
							etAlertValor.selectAll();
							etAlertValor.setFocusable(true);

						}
					}
				}
				else
				{
					mostrarMensaje("Debe ingresar el dinero recibido","l" );
					etAlertValor.selectAll();
					etAlertValor.setFocusable(true);

				}
			}
		});
		test.show();
	}
	
	
	/**
	 * metodo que se encarga de cargar los articulos del pedido a la lista para visualizarlos en la actividad
	 * y demas valores del pedido, como el nombre del cliente, valor total del pedido
	 */
	public void cargarArticulosPedido()
	{
			bd.openDB();
		    pedido.valor=bd.getValorPedido( pedido.idCodigoInterno);
			pedido.SubTotal=pedido.valor;
			pedido=bd.getObservacionesPedido( pedido);
			pedido.valor=pedido.SubTotal-pedido.DescuentoTotal;

		    listaAPedido=bd.getArticulosPedido( pedido.idCodigoInterno);
		    bd.closeDB();
		    listaArticulos.setAdapter(new ArticulosListaAdapter(this, Integer.parseInt(Long.toString(pedido.idCodigoInterno)),listaAPedido,null,null,parametrosPos,null));
	        textView[1].setText(cliente.ordenVisita+". "+cliente.nombre);
			tvDatosCliente.setText(cliente.direccion);

			etObservacion.setText(pedido.observaciones);
	        if(pedido.idCodigoExterno==0)
	        {
	        	textView[0].setText(Long.toString(pedido.idCodigoInterno));
	        }
	        else
	        {
	        	textView[0].setText(Long.toString(pedido.idCodigoExterno));
	        }
	        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
	        textView[2].setText(decimalFormat.format(pedido.valor));
			textView[10].setText(decimalFormat.format(pedido.SubTotal));
			btDescuento.setText((decimalFormat.format(pedido.DescuentoTotal)));


			Date fecha=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
            pedido.setFecha(sdf.format(fecha));            
            SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
            pedido.hora = hora.format(fecha);		
	}
	
	public void cargarArticulosFactura()
	{
		    factura.valor=bd.getValorFactura(this, factura.idCodigoInterno);
		    listaAFactura=bd.getArticulosFactura(factura.idCodigoInterno);
			etObservacion.setText(bd.getObservacionesFactura(factura.idCodigoInterno));
		    listaArticulos.setAdapter(new ArticulosListaAdapter(this, Integer.parseInt(Long.toString(factura.idCodigoInterno)),null,listaAFactura,null,parametrosPos,null));
	        if(factura.idCodigoExterno==0)
	        {
	        	textView[0].setText(Long.toString(factura.idCodigoInterno));
	        }
	        else
	        {
	        	textView[0].setText(Long.toString(factura.idCodigoExterno));
	        }
			factura.observaciones=etObservacion.getText().toString();
	        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
	        textView[2].setText(decimalFormat.format(factura.valor));        
	        Date fecha=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
            factura.setFecha(sdf.format(fecha));            
            SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
            factura.hora = hora.format(fecha);		
	}
	public void cargarArticulosRemision()
	{
		remision.valor=bd.getValorRemision(this, remision.idCodigoInterno);
		listaARemision =bd.getArticulosRemision(remision.idCodigoInterno);
		etObservacion.setText(bd.getObservacionesRemision(remision.idCodigoInterno));
		listaArticulos.setAdapter(new ArticulosListaAdapter(this, Integer.parseInt(Long.toString(remision.idCodigoInterno)),null,null,null,parametrosPos,listaARemision));
		if(remision.idCodigoExterno==0)
		{
			textView[0].setText(Long.toString(remision.idCodigoInterno));
		}
		else
		{
			textView[0].setText(Long.toString(remision.idCodigoExterno));
		}
		remision.observaciones=etObservacion.getText().toString();
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		textView[2].setText(decimalFormat.format(remision.valor));
		Date fecha=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		remision.setFecha(sdf.format(fecha));
		SimpleDateFormat hora=new SimpleDateFormat("HH:mm");
		remision.hora = hora.format(fecha);
	}
	public void cargarArticulosTraslado()
	{
		    traslado.totalTraslado=bd.getValorTraslado(this, traslado.idCodigoInterno);
		    listaATraslado=bd.getArticulosTraslado(traslado.idCodigoInterno);
		    listaArticulos.setAdapter(new ArticulosListaAdapter(this, Integer.parseInt(Long.toString(traslado.idCodigoInterno)),null,null,listaATraslado,parametrosPos,null));
	        if(traslado.idCodigoExterno==0)
	        {
	        	textView[0].setText(Long.toString(traslado.idCodigoInterno));
	        }
	        else
	        {
	        	textView[0].setText(Long.toString(traslado.idCodigoExterno));
	        }
	        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
	        textView[2].setText(decimalFormat.format(traslado.totalTraslado));        
	        Date fecha=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
            traslado.setFecha(sdf.format(fecha));            
            SimpleDateFormat hora=new SimpleDateFormat("HH:mm"); 
            traslado.hora = hora.format(fecha);		
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
	 * metodo que se encarga de generear el pedido que sera posterior mente enviado al servidor
	 */
	public void generarPedido()
	{
		
		//pedidoEnviar.NPedido=pedido.idCodigoExterno;
		pedidoEnviar.NPedido=pedido.idCodigoInterno;
		//System.out.println(" Externo :" +pedido.idCodigoExterno);
		pedidoEnviar.IdCliente=cliente.idCliente;
		pedidoEnviar.IdClienteSucursal=cliente.idClienteSucursal;
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
		pedidoEnviar.Latitud="sin_datos";
		pedidoEnviar.Longitud="sin_datos";
		
		try
		{	pedidoEnviar.Observaciones=etObservacion.getText().toString();}
		catch(Exception e)
		{  pedidoEnviar.Observaciones="Ninguna"; }
		pedido.observaciones=pedidoEnviar.Observaciones;
	}
	
	public void generarPedidoSys()
	{
		
		//pedidoEnviarSys.setNPedido(""+pedido.getIdCodigoExterno());
		pedidoEnviarSys.setNPedido(""+pedido.getIdCodigoInterno());
		pedidoEnviarSys.setIdCliente(Long.toString(cliente.idCliente));
		pedidoEnviarSys.setIdClienteSucursal(Long.toString(cliente.idClienteSucursal));
		pedidoEnviarSys.setIdVendedor(usuario.cedula);
		pedidoEnviarSys.setNMesa("0");
		pedidoEnviarSys.setTotalPedido(""+pedido.getValor());
		pedidoEnviarSys.setListaArticulos(listaAPedido);
		pedidoEnviarSys.setNCaja(""+parametrosPos.getCaja());
		try
		{	pedidoEnviarSys.setObservaciones(etObservacion.getText().toString());}
		catch(Exception e)
		{  pedidoEnviarSys.setObservaciones("Ninguna"); }
		pedido.observaciones=pedidoEnviarSys.getObservaciones();
		pedidoEnviarSys.setSubTotal(pedido.SubTotal+"");
		pedidoEnviarSys.setDescuentoTotal(pedido.DescuentoTotal+"");
		pedidoEnviarSys.setDocumento(pedido.getDocumento());
		pedidoEnviarSys.setFormaPago(pedido.getFormaPago());
		pedidoEnviarSys.setFecha(parametrosPos.getFecha());
		pedidoEnviarSys.setHora(parametrosPos.getHora());

	}
	
	public void generarFacturaSys()
	{
		facturaEnviarsys.setNCaja(""+parametrosPos.getCaja());	
		facturaEnviarsys.setIdCliente(""+cliente.idCliente);
		facturaEnviarsys.setIdClienteSucursal(""+cliente.idClienteSucursal);
		facturaEnviarsys.setCedulaVendedor(usuario.cedula);
		facturaEnviarsys.setNFactura("0");
		facturaEnviarsys.setLatitud("sin_datos");
		facturaEnviarsys.setLongitud("sin_datos");
		facturaEnviarsys.setVentaCredito(""+factura.VentaCredito);
		facturaEnviarsys.setFecha(parametrosPos.getFecha());
		facturaEnviarsys.setFecha2(parametrosPos.getFechaSys2System());
		facturaEnviarsys.setHora(parametrosPos.getHora());
		facturaEnviarsys.setListaArticulos(listaAFactura);
		try 
		{	
			if(etObservacion.getText().toString().length()>0)
			{
				facturaEnviarsys.setObservaciones(etObservacion.getText().toString());
			}
			else
			{
				facturaEnviarsys.setObservaciones("Ninguna"); 
			}
		}
		catch(Exception e)
		{  	facturaEnviarsys.setObservaciones("Ninguna"); }	
	}

	public void generarRemisionSys()
	{
		remisionEnviarsys.setNCaja(""+parametrosPos.getCajaRem());
		remisionEnviarsys.setIdCliente(""+cliente.idCliente);
		remisionEnviarsys.setIdClienteSucursal(""+cliente.idClienteSucursal );
		remisionEnviarsys.setCedulaVendedor(usuario.cedula);
		remisionEnviarsys.setNRemision("0");
		remisionEnviarsys.setLatitud("sin_datos");
		remisionEnviarsys.setLongitud("sin_datos");
		remisionEnviarsys.setVentaCredito(""+remision.VentaCredito);
		remisionEnviarsys.setFecha(parametrosPos.getFecha());
		remisionEnviarsys.setFecha2(parametrosPos.getFechaSys2System());
		remisionEnviarsys.setHora(parametrosPos.getHora());
		remisionEnviarsys.setListaArticulos(listaARemision);
		try
		{
			if(etObservacion.getText().toString().length()>0)
			{
				remisionEnviarsys.setObservaciones(etObservacion.getText().toString());
			}
			else
			{
				remisionEnviarsys.setObservaciones("Ninguna");
			}
		}
		catch(Exception e)
		{  	remisionEnviarsys.setObservaciones("Ninguna"); }
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
	
	
	
	/**
	 * metodo que se encarga de guardar el pedido enviado en la base de datos del dispositivo movil
	 */
	public void guardarPedido()
	{	
		bd.openDB();
		bd.insertPedido(pedido);
		if(pedido.getEnvio()!=0)
		{					
			mostrarMensaje("Pedido Enviado Correctamente ","l");			
		}
		else
		{		
			mostrarMensaje("No Fue Posible enviar el pedido, Fue almacenado en el telefono temporalmente ","l");			
		}
		cliente.fechaUltimoPedido = pedido.getFecha();
		cliente.fechaUltimaVisita = pedido.getFecha();
		bd.ActualizarCliente(cliente,false,false);
		bd.closeDB();

		//imprimir

		try
		{
		/**	if(parametrosPos.getUsaImpresoraZebra()==0)
			{
				pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				printFactura=new PrintFactura();
				printFactura.printPedido(pedido,listaAPedido,parametrosPos);
				pdu.dismiss();
				mostrarMensaje(printFactura.getMensaje(), "l");
			}
		 **/

		/**	if(parametrosPos.getUsaImpresoraZebra()==0)
			{
				pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				printFactura=new PrintFactura();
				printFactura.printPedido(pedido,listaAPedido,parametrosPos);
				pdu.dismiss();
				mostrarMensaje(printFactura.getMensaje(), "l");

			}
		 **/
			/**else
			{
				pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo"), true,false);
				PrintZebra pz=new PrintZebra(parametrosPos.getMacAdd());
				if(pz.printPedido(pedido,listaAPedido,parametrosPos))
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
			 **/

		}
		catch (Exception e) {
			pdu.dismiss();
			mostrarMensaje("dos "+e.toString(), "l");
		}


		if(pedido.getEnvio()!=0)
		{
			Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", operacion);
			//if(parametrosPos.isValue(parametrosPos.getImprimePedido()))
			//{
				intent.putExtra("print",true);
				intent.putExtra("idCodigoExterno",pedido.idCodigoExterno );
				intent.putExtra("idCodigoInterno",pedido.idCodigoInterno );
			//}
			startActivity(intent);
			finish();
			
//			pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Validando Datos"), true,false);
//			Thread thread = new Thread(this);
//			thread.start();			
//			clientesys.setLatitud(String.valueOf(0));
//    		clientesys.setLongitud( String.valueOf(0));
//    		clientesys.setAltitud(String.valueOf(0));
//    		clientesys.setObservacionVisita("Visita Efectiva");
//    		generarPedidoSys();
//    		new setPedidoSys().execute("");
//        	pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Guardando Datos"), true,false);
		
		}
		else
		{
		    Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", operacion);
			//if(�parametrosPos.isValue(parametrosPos.getImprimePedido())
			//{
				intent.putExtra("print",true);
				intent.putExtra("idCodigoExterno",pedido.idCodigoExterno );
				intent.putExtra("idCodigoInterno",pedido.idCodigoInterno );
			//}
			startActivity(intent);
			finish();
		}
			
	
	}
	

	private class enviarPedido extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params) 
		{
			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				generarPedido();			
				EnviarPedido e= new EnviarPedido(parametrosPos.getIp());
				//activa parametro envio del pedido
				
//				pedido.idCodigoExterno=e.getEnviarPedido(pedidoEnviar);	
				
				long npedido=e.getEnviarPedido(pedidoEnviar);
				pedido.setEnvio(0);
				if(npedido>0)
				{
					pedido.idCodigoExterno=npedido;
					pedido.setEnvio(1);
				}
				
				LlamarFecha llamarFecha=new LlamarFecha(parametrosPos.getIp());
				String fechaSys=llamarFecha.getFecha();
				if(!fechaSys.equals("Error"))
				{
					pedido.setFecha(fechaSys);
				}
			}
			else
			{
				generarPedidoSys();
				PutPedidoSys putPedidoSys=new PutPedidoSys(parametrosSys.getIp(),parametrosSys.getWebidText());
				
				//pedido.idCodigoExterno=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
				//activa parametro envio del pedido
				long npedido=putPedidoSys.setPedido(getXmlPedido(), pedidoEnviarSys.getXmlArticulos());
				pedido.setEnvio(0);
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
			}
			
			
			
			return 1;
		}
		
		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			etObservacion.setInputType(InputType.TYPE_NULL);
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putBoolean("Envio", true);
			i.putExtras(b);			  
		    setResult( SUB_ACTIVITY_VER_PEDIDOS, i ); 
			guardarPedido();			
		}
		
	}
	
	private class enviarFactura extends AsyncTask<String, Void, Object>
	{

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
				
			}
			
			return 1;
		}
		
		protected void onPostExecute(Object result)
		{
			
			pdu.dismiss();
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putBoolean("Envio", true);
			i.putExtras(b);			  
		    setResult( SUB_ACTIVITY_VER_PEDIDOS, i );			
		    guardarFactura();			
		}
		
	}

	private class enviarRemision extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params)
		{

			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				generarRemision();
				EnviarRemision e= new EnviarRemision(parametrosPos.getIp());
				remision=e.getEnviarRemision(remisionEnviar ,remision);
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
				remision=putRemisionSys.setRemision(getXmlRemision(), remisionEnviarsys.getXmlArticulos(),remision);

			}

			return 1;
		}

		protected void onPostExecute(Object result)
		{

			pdu.dismiss();
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putBoolean("Envio", true);
			i.putExtras(b);
			setResult( SUB_ACTIVITY_VER_PEDIDOS, i );
			guardarRemision();
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
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putBoolean("Envio", true);
			i.putExtras(b);			  
		    setResult( SUB_ACTIVITY_VER_PEDIDOS, i ); 
		    guardarTraslado();			
		}
		
	}
	
	public void generarFactura()
	{
		
		facturaEnviar.NCaja=parametrosPos.getCaja();	
		facturaEnviar.IdCliente=cliente.idCliente;
		facturaEnviar.CedulaVendedor=usuario.cedula;
		facturaEnviar.IdClienteSucursal=cliente.idClienteSucursal;
		ArrayList<ArticulosFactura> listaAFTem=new ArrayList<ArticulosFactura>();
		listaAFTem.add(listaAFactura.get(0));
		for (int i = 0; i < listaAFactura.size(); i++) {
			listaAFTem.add(listaAFactura.get(i));
		}
		ClsArtsFactura ls=new ClsArtsFactura ();
		for (int i = 0; i < listaAFTem.size(); i++) 
		{
			 ArticulosFactura ap=listaAFTem.get(i);			 
			 ClsArtFactura cap=new ClsArtFactura();
			 cap.IdArticulo=ap.idArticulo;
			 cap.Cantidad=ap.cantidad;
			 cap.ValorUnitario=ap.valorUnitario;
			 ls.add(cap);
		}
		facturaEnviar.Articulo=ls;
		facturaEnviar.Latitud="sin_datos";
		facturaEnviar.Longitud="sin_datos";
		facturaEnviar.VentaCredito=factura.VentaCredito;
		
		try
		{	facturaEnviar.Observaciones=etObservacion.getText().toString();}
		catch(Exception e)
		{  facturaEnviar.Observaciones="Ninguna"; }
	}
	public void generarRemision()
	{

		remisionEnviar.NCaja=parametrosPos.getCajaRem();
		remisionEnviar.IdCliente=cliente.idCliente;
		remisionEnviar.CedulaVendedor=usuario.cedula;
		remisionEnviar.IdClienteSucursal =cliente.idClienteSucursal;

		ArrayList<ArticulosRemision> listaARTem=new ArrayList<ArticulosRemision>();
		listaARTem.add(listaARemision.get(0));
		for (int i = 0; i < listaARemision.size(); i++) {
			listaARTem.add(listaARemision.get(i));
		}
		ClsArtsRemision ls=new ClsArtsRemision ();
		for (int i = 0; i < listaARTem.size(); i++)
		{
			ArticulosRemision ap=listaARTem.get(i);
			ClsArtRemision  cap=new ClsArtRemision();
			cap.IdArticulo=ap.idArticulo;
			cap.Cantidad=ap.cantidad;
			cap.ValorUnitario=ap.valorUnitario;
			ls.add(cap);
		}
		remisionEnviar.Articulo=ls;
		remisionEnviar.Latitud="sin_datos";
		remisionEnviar.Longitud="sin_datos";
		remisionEnviar.VentaCredito=remision.VentaCredito;

		try
		{	remisionEnviar.Observaciones=etObservacion.getText().toString();}
		catch(Exception e)
		{  remisionEnviar.Observaciones="Ninguna"; }
	}
	public void generarTraslado()
	{
		
		trasladoEnviar.NCaja=parametrosPos.getCaja();	
		trasladoEnviar.IdCliente=13108;
		trasladoEnviar.CedulaVendedor=usuario.cedula;
		ArrayList<ArticulosTraslado> listaATTem=new ArrayList<ArticulosTraslado>();
		listaATTem.add(listaATraslado.get(0));
		for (int i = 0; i < listaATraslado.size(); i++) {
			listaATTem.add(listaATraslado.get(i));
		}	
		ClsArtsFactura ls=new ClsArtsFactura ();
		for (int i = 0; i < listaATTem.size(); i++) 
		{
			 ArticulosTraslado ap=listaATTem.get(i);
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
		{	trasladoEnviar.Observaciones=etObservacion.getText().toString();}
		catch(Exception e)
		{  trasladoEnviar.Observaciones="Ninguna"; }
	}
	
	public void guardarFactura()
	{
		try
		{
			//CARGA DATOS CLIENTE
			if(!parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				factura.setRazonSocial(parametrosPos.getRazonSocial());
				factura.setRepresentante(parametrosPos.getRepresentante());
				factura.setDireccionTel(parametrosPos.getDireccionTel());
				factura.setRegimenNit(parametrosPos.getRegimenNit());
				factura.setRango(parametrosPos.getRango());
				factura.setResDian(parametrosPos.getResDian());
				factura.setPrefijo(parametrosPos.getPrefijo());
				factura.setIdCodigoExterno(factura.getNFactura());
				try
				{	factura.observaciones=etObservacion.getText().toString();}
				catch(Exception e)
				{  factura.observaciones="Ninguna"; }
			}


			//Guarda los articulos en la factura
			factura.setListaArticulos(listaAFactura);

			//------------------------------
			bd.insertFactura(factura,true);
			if(factura.idCodigoExterno!=0)
			{
				mostrarMensaje("Factura enviada correctamente ","l");
				factura.setListaArticulos(listaAFactura);
				/**try
				 {
				 if(parametrosPos.getUsaImpresoraZebra()==0)
				 {
				 pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				 printFactura=new PrintFactura();
				 printFactura.printFactura(factura);
				 pdu.dismiss();
				 mostrarMensaje(printFactura.getMensaje(), "l");
				 }

				 }
				 catch (Exception e) {
				 pdu.dismiss();
				 mostrarMensaje("dos "+e.toString(), "l");
				 }
				 **/
			}
			else
			{
				mostrarMensaje("No Fue Posible registrar la factura, Fue almacenado en el telefono temporalmente ","l");
			}
			cliente.fechaUltimoPedido = factura.getFecha();
			cliente.fechaUltimaVisita = factura.getFecha();
			bd.ActualizarCliente(cliente,false,false);
			bd.closeDB();

			Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", operacion);
			//if(factura.idCodigoExterno!=0 & parametrosPos.getUsaImpresoraZebra()==1)
			//{

			intent.putExtra("print",true);
			intent.putExtra("idCodigoExterno",factura.idCodigoExterno );
			intent.putExtra("NFactura",factura.NFactura );
			//}
			//else
			//{
			//	intent.putExtra("print",false);
			//	intent.putExtra("idCodigoExterno",factura.idCodigoExterno );
			//	intent.putExtra("NFactura",factura.NFactura );
			//}
			startActivity(intent);
			finish();
		}
		catch (Exception e) {
			mostrarMensaje("error al guardar"+e.toString(), "l");
		}
	}
	public void guardarRemision()
	{
		try
		{
			//CARGA DATOS CLIENTE
			if(!parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
				remision.setRazonSocial(parametrosPos.getRazonSocial());
				remision.setRepresentante(parametrosPos.getRepresentante());
				remision.setDireccionTel(parametrosPos.getDireccionTel());
				remision.setRegimenNit(parametrosPos.getRegimenNit());
				remision.setRango(parametrosPos.getRango());
				remision.setResDian(parametrosPos.getResDian());
				remision.setPrefijo(parametrosPos.getPrefijo());
				remision.setIdCodigoExterno(remision.getNRemision());
				try
				{	remision.observaciones=etObservacion.getText().toString();}
				catch(Exception e)
				{  remision.observaciones="Ninguna"; }
			}


			//Guarda los articulos en la remision
			remision.setListaArticulos(listaARemision);

			//------------------------------
			bd.insertRemision(remision,true);
			if(remision.idCodigoExterno!=0)
			{
				mostrarMensaje("remision enviada correctamente ","l");
				remision.setListaArticulos(listaARemision);
				/**try
				 {
				 if(parametrosPos.getUsaImpresoraZebra()==0)
				 {
				 pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
				 printFactura=new PrintFactura();
				 printFactura.printFactura(factura);
				 pdu.dismiss();
				 mostrarMensaje(printFactura.getMensaje(), "l");
				 }

				 }
				 catch (Exception e) {
				 pdu.dismiss();
				 mostrarMensaje("dos "+e.toString(), "l");
				 }
				 **/
			}
			else
			{
				mostrarMensaje("No Fue Posible registrar la remision, Fue almacenado en el telefono temporalmente ","l");
			}
			cliente.fechaUltimoPedido = remision.getFecha();
			cliente.fechaUltimaVisita = remision.getFecha();
			bd.ActualizarCliente(cliente,false,false);
			bd.closeDB();

			Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", operacion);
			//if(factura.idCodigoExterno!=0 & parametrosPos.getUsaImpresoraZebra()==1)
			//{

			intent.putExtra("print",true);
			intent.putExtra("idCodigoExterno",remision.idCodigoExterno );
			intent.putExtra("NRemision",remision.NRemision );
			//}
			//else
			//{
			//	intent.putExtra("print",false);
			//	intent.putExtra("idCodigoExterno",factura.idCodigoExterno );
			//	intent.putExtra("NFactura",factura.NFactura );
			//}
			startActivity(intent);
			finish();
		}
		catch (Exception e) {
			mostrarMensaje("error al guardar"+e.toString(), "l");
		}
	}
	public void guardarFacturaOffLine()
	{
		try
		{		
			//INGRESA DATOS DEL CLIENTE
			

		
				factura.setRazonSocial(parametrosPos.getRazonSocial());
				factura.setRepresentante(parametrosPos.getRepresentante());
				factura.setDireccionTel(parametrosPos.getDireccionTel());
				factura.setRegimenNit(parametrosPos.getRegimenNit());
				factura.setRango(parametrosPos.getRango());
				factura.setResDian(parametrosPos.getResDian());
				factura.setPrefijo(parametrosPos.getPrefijo());
				factura.setNombreVendedor(parametrosPos.getNombreVendedor());
				factura.setTelefonoVendedor("-");				
				factura.setListaArticulos(listaAFactura);
				factura.CalcularBasesIvas();
				factura.setNCaja(parametrosPos.getCaja());
				factura.setTotalFactura(factura.valor);
				
				factura.idCodigoExterno=0;
				//Obtiene ultimo Numero de factura
				factura.NFactura=bd.obtenerUltimoNFactura(this);
		        if(factura.NFactura==0)
		        {
		        	factura.NFactura=1;
		        }
		        else
		        {
		        	factura.NFactura++;
		        }
				try
				{	factura.observaciones=etObservacion.getText().toString();}
				catch(Exception e)
				{  factura.observaciones="Ninguna"; }



				//Guarda los articulos en la factura
				factura.setListaArticulos(listaAFactura);


		        
		        bd.insertFactura(factura,false);
				mostrarMensaje("Factura guardada correctamente ","l");
				
				/**try
		    		{
						if(parametrosPos.getUsaImpresoraZebra()==0)
						{
						 	pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
							printFactura=new PrintFactura();	
						    printFactura.printFactura(factura);
							pdu.dismiss();	
							mostrarMensaje(printFactura.getMensaje(), "l");
						}
						
		    		}
		    		catch (Exception e) {						      			    			
		    			pdu.dismiss();
		    			mostrarMensaje("dos "+e.toString(), "l");
				}	**/
				
				cliente.fechaUltimoPedido = factura.getFecha();
				cliente.fechaUltimaVisita = factura.getFecha();
				bd.ActualizarCliente(cliente,false,false);
				bd.closeDB();
				
				
				//CIERRA ACTIVIDAD CREAR PEDIDO
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putBoolean("Envio", true);
				i.putExtras(b);			  
			    setResult( SUB_ACTIVITY_VER_PEDIDOS, i ); 
				
				
				Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("operacion", operacion);
				//if(parametrosPos.getUsaImpresoraZebra()==1)
				//{
					intent.putExtra("print",true);
					intent.putExtra("idCodigoExterno",factura.idCodigoExterno );
					intent.putExtra("NFactura",factura.NFactura );
				//}
				
				startActivity(intent);
				finish();	
		}
		catch (Exception e) {
			mostrarMensaje("error al guardar"+e.toString(), "l");
		}
	}

	public void guardarRemisionOffLine()
	{
		try
		{
			//INGRESA DATOS DEL CLIENTE



			remision.setRazonSocial(parametrosPos.getRazonSocial());
			remision.setRepresentante(parametrosPos.getRepresentante());
			remision.setDireccionTel(parametrosPos.getDireccionTel());
			remision.setRegimenNit(parametrosPos.getRegimenNit());
			remision.setRango(parametrosPos.getRango());
			remision.setResDian(parametrosPos.getResDian());
			remision.setPrefijo(parametrosPos.getPrefijo());
			remision.setNombreVendedor(parametrosPos.getNombreVendedor());
			remision.setTelefonoVendedor("-");
			remision.setListaArticulos(listaARemision);
			remision.CalcularBasesIvas();
			remision.setNCaja(parametrosPos.getCajaRem());
			remision.setTotalRemision(remision.valor);

			remision.idCodigoExterno=0;
			//Obtiene ultimo Numero de remision
			remision.NRemision=bd.obtenerUltimoNRemision(this);
			if(remision.NRemision==0)
			{
				remision.NRemision=1;
			}
			else
			{
				remision.NRemision++;
			}
			try
			{	remision.observaciones=etObservacion.getText().toString();}
			catch(Exception e)
			{  remision.observaciones="Ninguna"; }



			//Guarda los articulos en la remision
			remision.setListaArticulos(listaARemision);



			bd.insertRemision(remision,false);
			mostrarMensaje("Cotizacion guardada correctamente ","l");

			/**try
			 {
			 if(parametrosPos.getUsaImpresoraZebra()==0)
			 {
			 pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
			 printFactura=new PrintFactura();
			 printFactura.printFactura(factura);
			 pdu.dismiss();
			 mostrarMensaje(printFactura.getMensaje(), "l");
			 }

			 }
			 catch (Exception e) {
			 pdu.dismiss();
			 mostrarMensaje("dos "+e.toString(), "l");
			 }	**/

			cliente.fechaUltimoPedido = remision.getFecha();
			cliente.fechaUltimaVisita = remision.getFecha();
			bd.ActualizarCliente(cliente,false,false);
			bd.closeDB();


			//CIERRA ACTIVIDAD CREAR PEDIDO
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putBoolean("Envio", true);
			i.putExtras(b);
			setResult( SUB_ACTIVITY_VER_PEDIDOS, i );


			Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
			intent.putExtra("cedula", usuario.cedula);
			intent.putExtra("operacion", operacion);
			//if(parametrosPos.getUsaImpresoraZebra()==1)
			//{
			intent.putExtra("print",true);
			intent.putExtra("idCodigoExterno",remision.idCodigoExterno );
			intent.putExtra("NRemision",remision.NRemision );
			//}

			startActivity(intent);
			finish();
		}
		catch (Exception e) {
			mostrarMensaje("error al guardar"+e.toString(), "l");
		}
	}

	public void guardarTraslado()
	{
		try
		{		
				traslado.totalTraslado=bd.getValorTraslado(this, traslado.idCodigoInterno);
		    	bd.insertTraslado(traslado);
				if(traslado.idCodigoExterno!=0)
				{					
					mostrarMensaje("Traslado enviada correctamente ","l");
					traslado.setListaArticulos(listaATraslado);
					try
		    		{
						if(parametrosPos.getUsaImpresoraZebra()==0)
						{
						 	pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
							printFactura=new PrintFactura();	
						    printFactura.printTraslado(traslado);
							pdu.dismiss();	
							mostrarMensaje(printFactura.getMensaje(), "l");
						}
						
		    		}
		    		catch (Exception e) {						      			    			
		    			pdu.dismiss();
		    			mostrarMensaje(e.toString(), "l");
				}				
				}
				else
				{		
					mostrarMensaje("No Fue Posible registrar la factura, Fue almacenado en el telefono temporalmente ","l");			
				}
//				cliente.fechaUltimoPedido = traslado.fecha;
//				cliente.fechaUltimaVisita = traslado.fecha;
//				bd.ActualizarCliente(cliente,false,false);
//				bd.closeDB();
				
				Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("operacion", operacion);
				if(traslado.idCodigoExterno!=0 & parametrosPos.getUsaImpresoraZebra()==1)
				{
				
					intent.putExtra("print",true);
					intent.putExtra("idCodigoExterno",traslado.idCodigoExterno );
				}
				else
				{
					intent.putExtra("print",false);
					intent.putExtra("idCodigoExterno",traslado.idCodigoExterno );
				}
				startActivity(intent);
				finish();	
		}
		catch (Exception e) {
			mostrarMensaje("error al guardar"+e.toString(), "l");
		}
		
			
	
	}
	/**
	 * metodo que se encarga obtener la localizacion del telefono en terreno
	 */
	  private void comenzarLocalizacion()
	    {
	    	//Obtenemos una referencia al LocationManager
	    	locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	
	    	//Obtenemos la �ltima posici�n conocida
	    	//Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	
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
	    	
	    	//locManager.requestLocationUpdates(
	    	//		LocationManager.GPS_PROVIDER, 30000, 0, locListener);
	    }
	  
	  /**
	   * metodo que se encarga de asignar la localizacion del telefono al 
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
	  /**
	   * metodo que se encarga de retornar la imagen que sera asignada a cada una de las opciondes de dialogo
	   * al momento de modificar articulos del pedido
	   * @param res
	   * @return imagen
	   */
		private Drawable getImg( int res )
		{
			Drawable img = getResources().getDrawable( res );
			img.setBounds( 0, 0, 45, 45 );
			return img;
		}
		/**
		 * metodo que se encarga de generar el pedido que sera enviado al sistema de 
		 * georeferenciacion
		 */
	
		
	
		/**
		 * Atributo handler referencia de la clase Handler utilizado para asignar la ubicacion del cliente 
		 * que se enviara al sistema de georeferenciacion
		 */
		 private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					pdu.dismiss();
					if(msg.what == 0)
					{
						locManagerSys.removeUpdates(locListenerSys);
				    	if (currentLocation!=null) {
				    		clientesys.setLatitud(String.valueOf(currentLocation.getLatitude()));
				    		clientesys.setLongitud( String.valueOf(currentLocation.getLongitude()));
				    		clientesys.setAltitud(String.valueOf(currentLocation.getAltitude()));
				    		clientesys.setObservacionVisita("Visita Efectiva");
//				    		generarPedidoSys();
				    		new setPedidoSys().execute("");
				        	pdu=ProgressDialog.show(ListaArticulosPedidoActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Guardando Datos"), true,false);
						}
					}
					else
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(ListaArticulosPedidoActivity.this);
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
	  		    		final AlertDialog alert = builder.create();
	  		    		alert.show();		  
					}					
			    }
			};
			/**
			 * metodo que asigna la nueva localizacion obtenida al atributo currentLocation
			 * @param loc
			 */
		  private void setCurrentLocation(Location loc) {
		    	currentLocation = loc;
		    }
		  /**
		   * metodo que se ejecuta al inicializar el nuevo hilo de la clase
		   */
		public void run() {
			
			//Obtenemos una referencia al LocationManager
			locManagerSys = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    	
	    	//Obtenemos la �ltima posici�n conocida
	    	if(locManagerSys.isProviderEnabled(LocationManager.GPS_PROVIDER))
	    	{
	    		Looper.prepare();
	    		//Nos registramos para recibir actualizaciones de la posici�n
	    		locListenerSys = new MyLocationListener();        	
	        	//locManagerSys.requestLocationUpdates(
	        	//		LocationManager.GPS_PROVIDER,30000, 50, locListenerSys);
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
		 * Clase en la que se obtiene la nueva localizacion del telefono
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
		        	  handler.sendEmptyMessage(1);		        	
		        }
		        public void onProviderEnabled(String provider) {
		            // TODO Auto-generated method stub
		        }
		        public void onStatusChanged(String provider, int status, 
		            Bundle extras) {
		        	Log.i("", "Provider Status: " + status);
		        }
		    }
		 /**
		  * Clase en la que se envia el pedido al sistema de georeferenciacion en un proceso en segundo plano
		  * @author Javier
		  *
		  */
		 private class setPedidoSys extends AsyncTask<String, Void, Object>
			{
				String  res ="";		
				@Override
					protected Integer doInBackground(String... params) 
					{	
					
						bd.openDB();
						clientesys = bd.getBuscarClientesSys(cliente.getIdCliente(),clientesys);
						clientesys.setEfectivo("SI");						
						bd.closeDB();
//						generarPedidoSys();	
						EnviarUbicacionYPedido enviarPedido=new EnviarUbicacionYPedido(parametrosSys.getIp());
						res =enviarPedido.setUbicacionYPedido(pedidoEnviarSys,clientesys);
						return 1;	
					}
				
				
					protected void onPostExecute(Object result)
					{
						pdu.dismiss();
						if(res.equals("1"))
						{						
								mostrarMensaje("Datos Validados Correctamente.","l");					
						}
						else 
						{		
								mostrarMensaje(res,"l");
								mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");					
						}						
					  
						Intent intent = new Intent(ListaArticulosPedidoActivity.this, ListaPedidosActivity.class );
						intent.putExtra("cedula", usuario.cedula);
						startActivity(intent);
						finish();					
					}			
			}
		 
		 /**
		  * Clase en la que se envia el pedido al sistema de georeferenciacion en un proceso en segundo plano
		  * @author Javier
		  *
		  */
		
		 
		 /**
		  * metodo que se ejecuta al hacer click sostenido sobre un elemento de la lista de articulos del pedido
		  */
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			return false;
		}

	private final void modificarTotalArticuloPedido(final ArticulosPedido articulo, final View view2)
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

							articulo.setValor((long) valor);
							double cant = (double) articulo.getValor() / (double) articulo.getValorUnitario();
							articulo.setCantidad(cant);


							DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
							TextView tvPrecioT = (TextView) view2.findViewById(R.id.tvPrecioT);
							tvPrecioT.setText(decimalFormat.format(articulo.valor));

							DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###.###");
							TextView tvCantidad = (TextView) view2.findViewById(R.id.tvCantidad);
							tvCantidad.setText(decimalFormat2.format(articulo.cantidad));
							//-----------------------Valor Unitario----------------------------------------------


							if (operacion == PEDIDO ) {
								if (bd.ActualizarPedidoArticulo(pedido.idCodigoInterno, articulo.idArticulo, articulo.cantidad, articulo.valorUnitario, (long) (articulo.cantidad * articulo.valorUnitario)))
									;
								{
									mostrarMensaje("Articulo Modificado Correctamente", "l");
									cargarArticulosPedido() ;
								}
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

	private final void modificarTotalArticuloFactura(final ArticulosFactura articulo, final View view2)
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

							articulo.setValor((long) valor);
							double cant = (double) articulo.getValor() / (double) articulo.getValorUnitario();
							articulo.setCantidad(cant);


							DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
							TextView tvPrecioT = (TextView) view2.findViewById(R.id.tvPrecioT);
							tvPrecioT.setText(decimalFormat.format(articulo.valor));

							DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###.###");
							TextView tvCantidad = (TextView) view2.findViewById(R.id.tvCantidad);
							tvCantidad.setText(decimalFormat2.format(articulo.cantidad));
							//-----------------------Valor Unitario----------------------------------------------


							if (operacion == FACTURA) {
								if (bd.ActualizarFacturaArticulo(factura.idCodigoInterno, articulo.idArticulo, articulo.cantidad, articulo.valorUnitario, (long) (articulo.cantidad * articulo.valorUnitario)))
									;
								{
									mostrarMensaje("Articulo Modificado Correctamente", "l");
									cargarArticulosFactura();
								}
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
	private final void modificarTotalArticuloRemision(final ArticulosRemision articulo, final View view2)
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

							articulo.setValor((long) valor);
							double cant = (double) articulo.getValor() / (double) articulo.getValorUnitario();
							articulo.setCantidad(cant);


							DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
							TextView tvPrecioT = (TextView) view2.findViewById(R.id.tvPrecioT);
							tvPrecioT.setText(decimalFormat.format(articulo.valor));

							DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###.###");
							TextView tvCantidad = (TextView) view2.findViewById(R.id.tvCantidad);
							tvCantidad.setText(decimalFormat2.format(articulo.cantidad));
							//-----------------------Valor Unitario----------------------------------------------


							if (operacion == REMISION) {
								if (bd.ActualizarRemisionArticulo(remision.idCodigoInterno, articulo.idArticulo, articulo.cantidad, articulo.valorUnitario, (long) (articulo.cantidad * articulo.valorUnitario)))
									;
								{
									mostrarMensaje("Articulo Modificado Correctamente", "l");
									cargarArticulosRemision() ;
								}
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

	private final void modificarArticulo(final ArticulosPedido articulo, final View view2)
	{
		final AlertDialog.Builder  builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("NOTA");
		builder2.setMessage("Ingrese la observacion para  \n "+articulo.getNombre() );

//		         // Use an EditText view to get user input.
		final AlertDialog test = builder2.create();
		final LinearLayout llVertical=new LinearLayout(this);
		llVertical.setOrientation(LinearLayout.VERTICAL);
		final EditText  etAlertValor = new EditText(this);
		etAlertValor.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		llVertical.addView(etAlertValor);
		final LinearLayout llHorizontal=new LinearLayout(this);
		llHorizontal.setOrientation(LinearLayout.HORIZONTAL);
		etAlertValor.setText(""+articulo.getObservacion());
		etAlertValor.selectAll();
		etAlertValor.setTextSize(16);



		etAlertValor.setInputType(InputType.TYPE_CLASS_TEXT);


		final Button btAlertCancelar=new Button(this);
		//btAlertCancelar=(Button)findViewById(R.id.btAgregar);
		btAlertCancelar.setText("Cancelar");
		btAlertCancelar.setWidth(350);
		btAlertCancelar.setHeight(50);
		btAlertCancelar.setTextSize(15);
		final Button btAlertOk=new Button(this);
		//btAlertOk=(Button)findViewById(R.id.btVer);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
		btAlertOk.setText("Ok");
		btAlertOk.setWidth(350);
		btAlertOk.setHeight(50);
		btAlertOk.setTextSize(15);
		llHorizontal.addView(btAlertOk);
		llHorizontal.addView(btAlertCancelar);

		llVertical.addView(llHorizontal);

		test.setView(llVertical);
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
						test.cancel();
						articulo.setObservacion(value);
						TextView tvNombre = (TextView) view2.findViewById(R.id.tvNombreAP);
						tvNombre.setText(articulo.getNombre()+" -- "+articulo.getObservacion());
						bd.ActualizarObservacionPedidoArticulo(articulo.getObservacion(),pedido.idCodigoInterno,articulo.getIdArticulo());



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
