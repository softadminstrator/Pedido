package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.Categoria;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.GetPedidoMesa;
import com.principal.mundo.sysws.ItemPedido;
import com.principal.mundo.sysws.Observacion;
import com.principal.mundo.sysws.Pedido;
import com.principal.mundo.sysws.PedidoMesaIn;
import com.principal.mundo.sysws.SetImpPrecuenta;
import com.principal.mundo.sysws.SetPedidoMesa;
import com.principal.persistencia.creaBD;

public class CrearPedioMesaActivity extends Activity implements OnClickListener {

	Pedido_in pedido;
	Pedido pedidoPos;
	PedidoMesaIn pedidoMesaIn;
	creaBD bd;
	Usuario usuario;
	private ProgressDialog pdu;
	LetraEstilo letraEstilo;
	Parametros parametrosPos, parametrosSys;
	private boolean isPedidoNuevo=false;;
	private ListView lvArtPedido;
	Context context;
	private TextView tvTotalPedido, tvTextoMesa ;
	private ArrayList<Categoria> listaCategorias;
	private LinearLayout llCategorias1 , llCategorias2, llArticulosCategoira, llObservaciones;
	private Button btVerMesas,btEnviarPM, btAlertOk, btAlertCancelar, btAlertaOkObservaciones,btImpPrecuenta;
	private ArrayList<Articulo> listaArtCategoria;
	Opciones [] opciones;
	private final static int PEDIDOMESA = 6;
	private boolean isEnviaPedido=false;
	private boolean isVerMesas=false;
	EditText etAlertValor,etObservacion;
	private String mensaje;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try
		{
		setContentView(R.layout.activity_crear_pedio_mesa);
		pedido=new Pedido_in();
		pedidoMesaIn=new PedidoMesaIn();
		usuario=new Usuario();
		letraEstilo=new LetraEstilo(); 
		pedidoPos=new Pedido();
		bd=new creaBD(this);		
		parametrosPos=bd.getParametros(this, "P");
		parametrosSys=bd.getParametros(this, "S");
		Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();       
		pedido.setMesa(obtenerDatos.getString("NMesa"));
        usuario.cedula=obtenerDatos.getString("cedula");

		//	opciones=new Opciones[1];
        opciones=new Opciones[2];
        opciones[0]=new Opciones("Modificar", getImg(R.drawable.modificar), "Modificar");
        opciones[1]=new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");
       
        
        tvTotalPedido=(TextView)findViewById(R.id.tvTotalPedido);
        tvTextoMesa=(TextView)findViewById(R.id.tvTextoMesa);
			etObservacion=(EditText)findViewById(R.id.etObservacion);


        
        
        tvTextoMesa.setText(pedido.getMesa());
        
        lvArtPedido=(ListView)findViewById(R.id.lvArtPedidoMesa);
        llCategorias1=(LinearLayout)findViewById(R.id.llCategorias1);
        llCategorias2=(LinearLayout)findViewById(R.id.llCategorias2);
        llArticulosCategoira=(LinearLayout)findViewById(R.id.llArticulosCategoira);
         
        btVerMesas=(Button)findViewById(R.id.btVerMesas);
        btVerMesas.setOnClickListener(this);
        btEnviarPM=(Button)findViewById(R.id.btEnviarPM);
        btEnviarPM.setOnClickListener(this);
			etObservacion.setOnClickListener(this);
			btImpPrecuenta=(Button)findViewById(R.id.btImpPrecuenta);
			btImpPrecuenta.setOnClickListener(this);
			//btImpPrecuenta.setVisibility(View.INVISIBLE);
        

        lvArtPedido.setOnItemLongClickListener (new OnItemLongClickListener() {
       	 /**
       	  * metodo que se ejecuta al realizar click sostenido sobre un elemento de la lista de articulos
       	  * del pedido
       	  */
	   	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
	   	  {
	   		  		ListAdapter listaOpciones = new OpcionesAdapter(CrearPedioMesaActivity.this, opciones);
	  	                AlertDialog.Builder builder = new AlertDialog.Builder(CrearPedioMesaActivity.this);	   	                
	   		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
		      			builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
		      			    public void onClick(DialogInterface dialog, int item) {
			      			  	if(item==0)
		      			    	{
				      			  	final ItemPedido itemPedido=pedidoMesaIn.getListaArticulos().get(position);
				      			  	if(itemPedido.getEnCocina().equals("NO"))
				      			  	{
				      			  		modificarArticulo(itemPedido); 
				      			  	}
				      			  	else
				      			  	{
				      			  		mostrarMensaje("No se puede modificar la cantidad del articulo, Ya fue Impreso", "l");
	      			    			}
			      			    			    		
		      			    	}	
			      			  	else if(item==1)
		      			    	{

									//final ItemPedido itemPedido=pedidoMesaIn.getListaArticulos().get(position);
									//if(itemPedido.getEnCocina().equals("NO"))
									//{
										if(borrarArticulo((int)position))
										{
											mostrarMensaje("Articulo Eliminado Satisfactoriamente", "l");
										}
										else
										{
											mostrarMensaje("Articulo No fue Eliminado", "l");
										}
									//}
									//else
									//{
									//	mostrarMensaje("No se puede eliminar el articulo, Ya fue Impreso", "l");
									//}
		      			    	}		      			    	
		      			    	dialog.cancel();			        
		      			    }
		      			});
		      			AlertDialog alert = builder.create();
		      			alert.show();
	   		  
	   		  return false;
	   		  }
	   		});
   
      

        cargaDatosPedido();
        cargarCategorias();
        new getPedidoMesa().execute("");								
		pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Consultando Mesa.."), true,false);
		}
		catch (Exception e) {
			mostrarMensaje("errf: "+e.toString(),"l");
			System.out.println("errf: "+e.toString());
		}
		etObservacion.setInputType(InputType.TYPE_NULL);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crear_pedio_mesa, menu);
		return true;
	}
	public void cargaDatosPedido()
	{		
		 if(pedido.idCodigoInterno==0)
	        {
		        
		        bd.openDB();
		        pedido.idCodigoInterno=bd.obtenerUltimoIdPedido(this);
		        if(pedido.idCodigoInterno==0)
		        {
		        	pedido.idCodigoInterno=1;
		        }
		        else
		        {
		        	pedido.idCodigoInterno++;
		        }
		        bd.eliminarArticulosDePedido(pedido.idCodigoInterno);		        
			    bd.closeDB();
	        }			
	}
	private void cargarPedioMesa()
	{	 
		 context = getApplicationContext();
		 lvArtPedido.setAdapter(new ItemPedidoMesaAdapter(context,R.layout.activity_item_pedido_mesa,pedidoMesaIn.getListaArticulos()));
		 DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		 tvTotalPedido.setText(decimalFormat.format(pedidoMesaIn.getTotalPedido()));
		 pedidoPos.setTotalPedido(""+pedidoMesaIn.getTotalPedido());
	}
	
	private void agregarArticuloPedido(Articulo articulo)
	{		
			ItemPedido itemPedido=new ItemPedido();
			itemPedido.setIdArticulo(articulo.getIdArticulo());
			itemPedido.setNombreArticulo(articulo.getNombre());
			itemPedido.setNPedido(pedidoPos.getNPedido());
			itemPedido.setNCaja(pedidoPos.getNCaja());
			itemPedido.setCantidad(1);
			itemPedido.setValorUnitario(articulo.getPrecio1());
			itemPedido.setTipoIva(0);
			itemPedido.setImpoConsumo(0);
			itemPedido.setTotal(articulo.getPrecio1());
			itemPedido.setCostoUnit(0);
			itemPedido.setEnCocina("NO");
			itemPedido.setMesero(""+usuario.getCedula());
			itemPedido.setIdCategoria(0);
			itemPedido.setListaObservaciones(articulo.getListaObservacionesSelec());


			pedidoMesaIn.getListaArticulos().add(itemPedido);			
			cargarPedioMesa();		
	}
	
	
	
	
	
	@SuppressLint("NewApi")
	private void cargarCategorias()
	{    	
		 bd.openDB();
		 listaCategorias=bd.getCategorias(parametrosSys.getUsaCategorias());  	        
		 bd.closeDB();
 
		 
		 int mit = listaCategorias.size()/2;
		 int col=0;
		 for (int i = 0; i < listaCategorias.size(); i++) {
			 final Categoria cat=listaCategorias.get(i);
			 final Animation anim_scale_mesa = AnimationUtils.loadAnimation(this, R.anim.anim_scale_mesa);
			 final Button btCategoria = new Button(this);
			 btCategoria.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {	
					v.startAnimation(anim_scale_mesa);	
					llArticulosCategoira.removeAllViews();
					cargarArticulosCategoria(cat);										
				}
			});			
			btCategoria.setText(listaCategorias.get(i).getNombre());
			btCategoria.setBackgroundResource(R.drawable.red_button);
			btCategoria.setTextColor(Color.WHITE);
			btCategoria.setTypeface(null, Typeface.BOLD);
             btCategoria.setTextSize(16);
			 col++;
			 btCategoria.setBackgroundColor(getNextColor(col));
			// btCategoria.setBackgroundColor(0xFFF00D21);
		     if(col==4)
			 {
				 col=0; 
			 }
		     LinearLayout ll = new LinearLayout(this);
		     ll.setOrientation(LinearLayout.VERTICAL);

		     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		          LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

		     layoutParams.setMargins(10, 5, 0, 0);
		     layoutParams.height=100-30;
		     layoutParams.width=140-30;
		     ll.addView(btCategoria, layoutParams);		    


			 if(i<=mit)
			 {
				 llCategorias1.addView(ll);
			 }
			 else
			 {
				 llCategorias2.addView(ll);
			 }		 
		}		
	}	
	private void cargarArticulosCategoria(Categoria categoria)
	{
		try
		{
		 bd.openDB();
		 listaArtCategoria=bd.getArticuloPorCategoria(this,categoria);  	        
		 bd.closeDB();
		 int col=0;
		 int numcolBt=0;
		 LinearLayout llinea =null;
		 if(listaArtCategoria!=null)
		 {
				 for (int i = 0; i < listaArtCategoria.size(); i++) {
					final Articulo articulo=listaArtCategoria.get(i);
					final Animation anim_scale_mesa = AnimationUtils.loadAnimation(this, R.anim.anim_scale_mesa);
					final Button btArticulo = new Button(this);
					 btArticulo.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							v.startAnimation(anim_scale_mesa);
							//valida si muestra observaciones del articulo
							if(articulo.getListaObservaciones().size()>0)
							{
								articulo.setListaObservacionesDesSelect();
								seleccionarObservacionesArticulo(articulo);
							}
							else
							{
								agregarArticuloPedido(articulo);
							}

						}
					});			
					 btArticulo.setText(articulo.getNombre());
					 btArticulo.setBackgroundResource(R.drawable.red_button);
					 btArticulo.setTextColor(Color.WHITE);
					 btArticulo.setTypeface(null, Typeface.BOLD);
					 btArticulo.setTextSize(12);
					 col++;
					 btArticulo.setBackgroundColor(getNextColorArticulo(col));
					 //btArticulo.setBackgroundColor(0xFF000000);
				     if(col==4)
					 {
						 col=0; 
					 }
				     LinearLayout ll = new LinearLayout(this);
				     ll.setOrientation(LinearLayout.VERTICAL);		
				     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				          LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);		
				     layoutParams.setMargins(10, 5, 0, 0);
				     layoutParams.height=90-20;
				     layoutParams.width=120-20;
				     ll.addView(btArticulo, layoutParams);
				     
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
				     
				     if(numcolBt==5)
				     {
				    	 llArticulosCategoira.addView(llinea);
				    	 numcolBt=0;
				     }
				     if((i+1) == listaArtCategoria.size() & numcolBt > 0 & numcolBt < 5)
				     {
				    	 llArticulosCategoira.addView(llinea); 
				     }
				}
		 }
		}
		catch (Exception e) {
			System.out.println("Err22 "+e.toString());
		}
	}
	
	
	
	
	private int getNextColor(int id)
	{
		switch (id) {
		case 1:return 0xFFF4EC00;
		case 2: return 0xFFF00D21;	
		case 3: return 0xFFF4DD33;	
		default: return 0xFFEF283C;	
		}
	}
	
	private int getNextColorArticulo(int id)
	{
		switch (id) {
		case 1:return 0xFF347BE5;
		case 2: return 0xFF019213;	
		case 3: return 0xFF5691EA;	
		case 4: return 0xFF22912F;	
		default: return 0xFF019213;	
		}
	}
	
	
	
	private class getPedidoMesa extends AsyncTask<String, Void, Object>
	{
		@Override
			protected Integer doInBackground(String... params) 
			{	
				GetPedidoMesa pedidoMesa=new GetPedidoMesa(parametrosSys.getIp(),parametrosSys.getWebidText());
				pedidoPos=pedidoMesa.GetPedidoM(""+pedido.getMesa());
				mensaje=pedidoMesa.getMensaje();
			return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				try
				{			
				pdu.dismiss();
				if(pedidoPos==null)
				{
					pedidoPos=new Pedido();
					pedidoPos.setNMesa(pedido.getMesa());
					isPedidoNuevo=true;
					mostrarMensaje("Nuevo Pedido!!!.","l");	
				
				
//					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
//					mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
//					mostrarMensaje(mensaje, "l");
				}
				else if(pedidoPos.getNPedido().equals("0"))
				{
					pedidoPos=new Pedido();
					pedidoPos.setNMesa(pedido.getMesa());
					isPedidoNuevo=true;
					mostrarMensaje("Nuevo Pedido!!.","l");							
				}
				else
				{
					pedidoMesaIn.getCargarArticulos(pedidoPos.getXmlArticulos());
					etObservacion.setText(pedidoPos.getObservaciones());
					cargarPedioMesa();
				}
//			
				}
				catch (Exception e) {
					System.out.println("jaja "+e.toString());
				}
				
			}
	}
	
	private class EnviarPedidoMesa extends AsyncTask<String, Void, Object>
	{
		String res="";
		@Override
			protected Integer doInBackground(String... params) 
			{	
				SetPedidoMesa pedidoMesa=new SetPedidoMesa(parametrosSys.getIp(),parametrosSys.getWebidText());
				res=pedidoMesa.setPedidoMesa(getXmlPedido(),pedidoMesaIn.getXmlArticulos());
			return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				try
				{						
				pdu.dismiss();
				isEnviaPedido=false;
				if(res.equals("OK"))
				{
					mostrarMensaje("PEDIDO ENVIADO CORRECTAMENTE!!!.","l");	
					guardarPedido();
				}
				else 
				{	
					mostrarMensaje(res,"l");
					
//					mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
//					mostrarMensaje("Puede realizar pedidos utilizando los datos existentes.","l");
				}				
				}
				catch (Exception e) {
					System.out.println("jaja "+e.toString());
				}
				
			}
	}

	private class EnviarImpPrecuenta extends AsyncTask<String, Void, Object>
	{
		String res="";
		@Override
		protected Integer doInBackground(String... params)
		{
			SetImpPrecuenta setImpPrecuenta=new SetImpPrecuenta(parametrosSys.getIp(),parametrosSys.getWebidText());
			res=setImpPrecuenta.SetImpPrecuenta(pedidoMesaIn.getNMesa());
			return 1;
		}


		protected void onPostExecute(Object result)
		{
			try
			{
				pdu.dismiss();

				if(res.equals("OK"))
				{
					mostrarMensaje("PRECUENTA IMPRESA CORRECTAMENTE!!!.","l");

				}
				else if(res.equals("NE"))
				{
					mostrarMensaje("DEBE ENVIAR PREVIAMENTE EL PEDIDO DE MESA /n PARA PODER IMPRIMIR LA PRECUENTA!!!.","l");

				}
				else
				{
					mostrarMensaje(res,"l");
				}
			}
			catch (Exception e) {
				System.out.println("jaja "+e.toString());
			}

		}
	}
	
	private void guardarPedido()
	{	
		bd.openDB();
		bd.insertPedido(pedido, null, false);
		bd.closeDB();
		Intent intent = new Intent(this, SelecMesaActivity.class );
		intent.putExtra("cedula", usuario.cedula);
		startActivity(intent);
		finish();
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
	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}

	public void onClick(View v) {
		if(v.equals(btVerMesas))
		{
			if(!isVerMesas)
			{
				closeActivity();				
			}
		}
		else if(v.equals(etObservacion))
		{
			etObservacion.setInputType(InputType.TYPE_CLASS_TEXT);
			etObservacion.requestFocus();
			InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.showSoftInput(etObservacion, InputMethodManager.SHOW_FORCED);
		}

		else if(v.equals(btEnviarPM))
		{
			if(!isEnviaPedido)
			{
				isEnviaPedido=true;
				if(pedidoMesaIn.getTotalPedido()>0)
				{
					pedidoPos.setNMesa(pedido.getMesa());
					pedidoPos.setObservaciones(etObservacion.getText().toString());
					pedidoPos.setXmlArticulos(" ");
					pedidoPos.setIdVendedor(usuario.getCedula());
					pedidoPos.setFecha(parametrosPos.getFecha());
					pedidoPos.setHora(parametrosPos.getHora());
					new EnviarPedidoMesa().execute("");					
					pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pedido.."), true,false);
				}
				else
				{
					mostrarMensaje("EL VALOR DEL PEDIDO DEBE SER MAYOR QUE 0!!!.","l");					
				}
			}
		}
		else if(v.equals(btImpPrecuenta))
		{
				if(pedidoMesaIn.getTotalPedido()>0)
				{
					pedidoMesaIn.setNMesa(tvTextoMesa.getText().toString());

					new EnviarImpPrecuenta().execute("");
					pdu=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Impresi�n.."), true,false);
				}
				else
				{
					mostrarMensaje("EL VALOR DEL PEDIDO DEBE SER MAYOR QUE 0!!!.","l");
				}

		}

	}
	
	 public String getXmlPedido()
		{
			String xml="";
			xml="<PedidoMesa>\n";		
			xml +="<Datos>\n";
			for (int j = 0; j < pedidoPos.getPropertyCount(); j++) {
				xml +="		<"+pedidoPos.getPropertyName(j)+">"+pedidoPos.getProperty(j)+"</"+pedidoPos.getPropertyName(j)+">\n";				
			}
			xml +="</Datos>\n";			
			
			xml +="</PedidoMesa>";
			return xml;
		}
	
	  private boolean borrarArticulo(int position)
      {
      	boolean res=false;
      	pedidoMesaIn.getListaArticulos().remove(position);
 		cargarPedioMesa(); 
 		res=true;
      	return res;      	
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
	  private void modificarArticulo(final ItemPedido itemPedido)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle("Modificar");
		  builder.setMessage("Ingrese la cantidad del articulo \n "+getTextSpace(itemPedido.getNombreArticulo()));	
		  
	      					        
//		         // Use an EditText view to get user input.
	        final AlertDialog test = builder.create(); 
	        LinearLayout llVertical=new LinearLayout(this);
	        llVertical.setOrientation(LinearLayout.VERTICAL);
	        etAlertValor = new EditText(this);
	        etAlertValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	         llVertical.addView(etAlertValor);
	         LinearLayout llHorizontal=new LinearLayout(this);
	         llHorizontal.setOrientation(LinearLayout.HORIZONTAL); 				         
	        etAlertValor.setText(""+itemPedido.getValorCantidad());
	        etAlertValor.selectAll();
	        etAlertValor.setTextSize(16);
	        btAlertCancelar=new Button(this);
	        btAlertCancelar.setText("Cancelar");
	        btAlertCancelar.setWidth(150);
	        btAlertCancelar.setHeight(50);
	        btAlertCancelar.setTextSize(15);
	        btAlertOk=new Button(this);
//		        btAlertOk=new Button(this,null,R.style.btAlertaOk);
	        btAlertOk.setText("Ok");
	        btAlertOk.setWidth(150);
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
					if(!etAlertValor.getText().toString().isEmpty())
		            	{long value =0;
							try {
								value = Long.parseLong(etAlertValor.getText().toString());
							}catch(Exception e )
							{
								value=1;
							}
		            		 if(value>=0)
		            		 {
		            			 	test.cancel();
		            			 	itemPedido.setCantidad(value);
		            			 	cargarPedioMesa();
		            		 }
		            		 else
		            		 {		            			 
		            			 mostrarMensaje("La cantidad del articulo debe ser mayor que 0","l" );
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
	        test.show();	 
		  
		  
//	  pedidoMesaIn.getListaArticulos().get(position)
	  }

	private void seleccionarObservacionesArticulo(final Articulo articulo)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(CrearPedioMesaActivity.this);
		builder.setTitle("Observaciónes");

		final AlertDialog test = builder.create();

		//VARIABLES LINEAR LAYOUT
		LinearLayout llVertical=new LinearLayout(CrearPedioMesaActivity.this);
		llVertical.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams layoutParamsllVertical = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsllVertical.setMargins(10, 5, 0, 0);
		layoutParamsllVertical.height=(700-200);
		layoutParamsllVertical.width=(540-50);
		llVertical.setLayoutParams(layoutParamsllVertical);

		//LINEAR LAYOUT PARA EL BOTON OK
		LinearLayout llHorizontal=new LinearLayout(CrearPedioMesaActivity.this);
		llHorizontal.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams layoutParamsllHorizontal = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsllHorizontal.setMargins(10, 5, 0, 0);
		layoutParamsllHorizontal.height=80-20;
		layoutParamsllHorizontal.width=500-50;
		llHorizontal.setLayoutParams(layoutParamsllHorizontal);

		//LIENAR LAYOUT PARA LAS DESCRIPCIIONES DE LAS OBSERVACIONES
		LinearLayout llHorizontal2=new LinearLayout(CrearPedioMesaActivity.this);


		LinearLayout.LayoutParams layoutParamsllHorizontal2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsllHorizontal2.setMargins(5, 5, 0, 0);
		layoutParamsllHorizontal2.height=700-200;
		layoutParamsllHorizontal2.width=500-50;
		llHorizontal2.setLayoutParams(layoutParamsllHorizontal2);

		ScrollView scView = new ScrollView(CrearPedioMesaActivity.this);


		LinearLayout.LayoutParams layoutParamsscView = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsscView.setMargins(5, 5, 0, 0);
		layoutParamsscView.height=700-200;
		layoutParamsscView.width=500-50;

		scView.setLayoutParams(layoutParamsscView);

		llObservaciones=new LinearLayout(CrearPedioMesaActivity.this);
		LinearLayout.LayoutParams layoutParamsllObservaciones = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		llObservaciones.setLayoutParams(layoutParamsllObservaciones);
		llObservaciones.setOrientation(LinearLayout.VERTICAL);

		scView.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams parms2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);

		LinearLayout.LayoutParams parms3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		btAlertaOkObservaciones=new Button(CrearPedioMesaActivity.this);
		btAlertaOkObservaciones.setLayoutParams(parms3);
		btAlertaOkObservaciones.setText("OK");
		btAlertaOkObservaciones.setTextColor(Color.WHITE);
		btAlertaOkObservaciones.setTextSize(16-2);
		btAlertaOkObservaciones.setWidth(80-20);
		btAlertaOkObservaciones.setHeight(90-20);
		btAlertaOkObservaciones.setBackgroundResource(R.drawable.red_button);


		llHorizontal.addView(btAlertaOkObservaciones);
		scView.addView(llObservaciones);
		llHorizontal2.addView(scView);
		llVertical.addView(llHorizontal);
		llVertical.addView(llHorizontal2);
		test.setView(llVertical);

		try
		{
			int col=0;
			int numcolBt=0;
			LinearLayout llinea =null;
			llObservaciones.removeAllViews();
			if(articulo.getListaObservaciones()!=null)
			{
				for (int i = 0; i < articulo.getListaObservaciones().size(); i++) {
					final Observacion observacion=articulo.getListaObservaciones().get(i);
					final Animation anim_scale_mesa = AnimationUtils.loadAnimation(this, R.anim.anim_scale_mesa);
					final Button btObservacion = new Button(this);
					final Button btObservacionMas = new Button(this);
					final Button btObservacionMenos = new Button(this);

					if(!parametrosSys.isValue(parametrosSys.getUsaObservMasMenos())) {

							btObservacion.setOnClickListener(new OnClickListener() {

								public void onClick(View v) {
									v.startAnimation(anim_scale_mesa);
									if(observacion.isSelected())
									{
										btObservacion.setBackgroundResource(R.drawable.selec_button);
										observacion.setSelected(false);
									}
									else
									{
										btObservacion.setBackgroundResource(R.drawable.select_button_1);
										observacion.setSelected(true);
									}
								}
							});

					}
					// boton detalle observacion
					btObservacion.setText(observacion.getDetalle());
					btObservacion.setBackgroundResource(R.drawable.selec_button);
					btObservacion.setTextColor(Color.BLACK);
					btObservacion.setTypeface(null, Typeface.BOLD);
					btObservacion.setTextSize(12);
					LinearLayout ll = new LinearLayout(this);
					ll.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(10, 5, 0, 0);
					if(parametrosSys.isValue(parametrosSys.getUsaObservMasMenos())) {
						layoutParams.height = 40;
						layoutParams.width = 250;
					}
					else
					{
						layoutParams.height = 60-10;
						layoutParams.width = 150-30;
					}
					ll.addView(btObservacion, layoutParams);


					//Boton mas
					btObservacionMas.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							v.startAnimation(anim_scale_mesa);
							if(observacion.isSelected())
							{
								if(observacion.isSelectedMas())
								{
									btObservacionMas.setBackgroundResource(R.drawable.selec_button);
									observacion.setSelected(false);
									observacion.setSelectedMas(false);
								}
								else if(observacion.isSelectedMenos())
								{
									btObservacionMas.setBackgroundResource(R.drawable.select_button_1);
									observacion.setSelected(true);
									observacion.setSelectedMas(true);
									observacion.setSelectedMenos(false);
									btObservacionMenos.setBackgroundResource(R.drawable.selec_button);
								}
							}
							else
							{
								btObservacionMas.setBackgroundResource(R.drawable.select_button_1);
								observacion.setSelected(true);
								observacion.setSelectedMas(true);
								observacion.setSelectedMenos(false);
								btObservacionMenos.setBackgroundResource(R.drawable.selec_button);
							}
						}
					});
					btObservacionMas.setText("+");
					btObservacionMas.setBackgroundResource(R.drawable.selec_button);
					btObservacionMas.setTextColor(Color.BLACK);
					btObservacionMas.setTypeface(null, Typeface.BOLD);
					btObservacionMas.setTextSize(22-4);
					LinearLayout ll2 = new LinearLayout(this);
					ll2.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams layoutParamsMas = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParamsMas.setMargins(10, 5, 0, 0);
					layoutParamsMas.height=40-10;
					layoutParamsMas.width=100-20;
					ll2.addView(btObservacionMas, layoutParamsMas);


					// Boton menos
					btObservacionMenos.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							v.startAnimation(anim_scale_mesa);
							if(observacion.isSelected())
							{
								if(observacion.isSelectedMenos()) {
									btObservacionMenos.setBackgroundResource(R.drawable.selec_button);
									observacion.setSelected(false);
									observacion.setSelectedMenos(false);
								}
								else if(observacion.isSelectedMas())
								{
									btObservacionMenos.setBackgroundResource(R.drawable.select_button_1);
									observacion.setSelected(true);
									observacion.setSelectedMenos(true);
									observacion.setSelectedMas(false);
									btObservacionMas.setBackgroundResource(R.drawable.selec_button);
								}
							}
							else
							{
								btObservacionMenos.setBackgroundResource(R.drawable.select_button_1);
								observacion.setSelected(true);
								observacion.setSelectedMenos(true);
								observacion.setSelectedMas(false);
								btObservacionMas.setBackgroundResource(R.drawable.selec_button);
							}
						}
					});
					btObservacionMenos.setText("-");
					btObservacionMenos.setBackgroundResource(R.drawable.selec_button);
					btObservacionMenos.setTextColor(Color.BLACK);
					btObservacionMenos.setTypeface(null, Typeface.BOLD);
					btObservacionMenos.setTextSize(22-4);
					LinearLayout ll3 = new LinearLayout(this);
					ll3.setOrientation(LinearLayout.VERTICAL);
					LinearLayout.LayoutParams layoutParamsMenos = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParamsMenos.setMargins(10, 5, 0, 0);
					layoutParamsMenos.height=40-10;
					layoutParamsMenos.width=100-20;
					ll3.addView(btObservacionMenos, layoutParamsMenos);


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

					if(parametrosSys.isValue(parametrosSys.getUsaObservMasMenos())) {
						numcolBt++;
						llinea.addView(ll2);
						numcolBt++;
						llinea.addView(ll3);
					}

					if(numcolBt==3)
					{
						llObservaciones.addView(llinea);
						numcolBt=0;
					}
					if((i+1) == articulo.getListaObservaciones().size() & numcolBt > 0 & numcolBt < 3)
					{
						llObservaciones.addView(llinea);
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Err22 "+e.toString());
		}

		btAlertaOkObservaciones.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				agregarArticuloPedido(articulo);
				test.cancel();
			}
		});
		test.show();
	}

	  
	  public void closeActivity()
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	dialog.setMessage("� Esta seguro que desea salir de la mesa sin guardar cambios ?");
	    	
	    	dialog.setCancelable(false);
	    	dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {  	 
	    	
	    	  public void onClick(DialogInterface dialog, int which) {
	    		  	isVerMesas=true;
					Intent intent = new Intent(CrearPedioMesaActivity.this, SelecMesaActivity.class );
					intent.putExtra("cedula", usuario.cedula);
					startActivity(intent);
					CrearPedioMesaActivity.this.finish();
	    	  }
	    	});
	    	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    	 
	    	
	    	   public void onClick(DialogInterface dialog, int which) {
	    	      dialog.cancel();
	    	   }
	    	});
	    	dialog.show();
		}
	  
	  @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
}
