package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.principal.mundo.Bodega;
import com.principal.mundo.Cliente;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.Opciones;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.PutPagosFacturaSys;
import com.principal.persistencia.creaBD;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class CarteraPagosParcialesActivity extends Activity implements OnClickListener{
	
	private final static int CARTERA=7;
	protected static final int SUB_ACTIVITY_PAGO_PARCIAL = 500;
	private Usuario usuario;
	private Factura_in factura;
	private PagosFactura pagosFactura;
	private Cliente cliente;
	private ItemPagoFac itemPagoFac;
	private ProgressDialog pdu;



	private TextView tvNombreClientePP, tvNFacturaC,tvNCajaC, tvValFacturaC, tvSaldoFacC,tvValorPagarC,tvNuevoSaldoC;
	private EditText etDescuentoPP, etValorAPagar, etFormaPago;	
	private Button btGenerarPagoParcial, btAgregarPago,btVolverCPP;
	private ListView lvPagosFactura;
	
	private ArrayList<String> listaFormasPago;
	Opciones [] opciones;
	Opciones [] opcionesAbonos;
	Context context;
	private Pago pago;
	creaBD bd;
	LetraEstilo letraEstilo;
	Parametros parametrosPos, parametrosSys;
	private long valorAPagar, descuento, nuevoSaldo;
	private boolean keyback=false;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try
		{
		setContentView(R.layout.activity_cartera_pagos_parciales);
		
		usuario=new Usuario();
		factura=new Factura_in();
		cliente=new Cliente();
		bd=new creaBD(this);  
		pagosFactura=new PagosFactura();
		letraEstilo=new LetraEstilo();
		parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");
		
		opcionesAbonos=new Opciones[1];              
		opcionesAbonos[0]=new Opciones("Eliminar", getImg(R.drawable.eliminar), "Eliminar");        
	     
		
		
		listaFormasPago=new ArrayList<String>();
		listaFormasPago.add("EFECTIVO");
		listaFormasPago.add("T CREDITO");
		listaFormasPago.add("T DEBITO");
		listaFormasPago.add("CHEQUE");
		listaFormasPago.add("BONO");
		
		tvNombreClientePP=(TextView)findViewById(R.id.tvNombreClientePP);
		tvNFacturaC=(TextView)findViewById(R.id.tvNFacturaC);
		tvNCajaC=(TextView)findViewById(R.id.tvNCajaC);
		tvValFacturaC=(TextView)findViewById(R.id.tvValFacturaC);
		tvSaldoFacC=(TextView)findViewById(R.id.tvSaldoFacC);
		tvNuevoSaldoC=(TextView)findViewById(R.id.tvNuevoSaldoC);
		tvValorPagarC=(TextView)findViewById(R.id.tvValorPagarC);
		
		etDescuentoPP=(EditText)findViewById(R.id.etDescuentoPP);
		etValorAPagar=(EditText)findViewById(R.id.etValorAPagar);
		etFormaPago=(EditText)findViewById(R.id.etFormaPago);		
		etFormaPago.setOnClickListener(this);
		etFormaPago.setFocusable(false);
		etFormaPago.setFocusableInTouchMode(false);
		
		etValorAPagar.setOnClickListener(this);

		etDescuentoPP.setVisibility(View.GONE);
		
		
		btGenerarPagoParcial=(Button)findViewById(R.id.btGenerarPagoParcial);
		btAgregarPago=(Button)findViewById(R.id.btAgregarPago);
		btVolverCPP=(Button)findViewById(R.id.btVolverCPP);
		
		btGenerarPagoParcial.setOnClickListener(this);
		btAgregarPago.setOnClickListener(this);
		btVolverCPP.setOnClickListener(this);
		
		
		lvPagosFactura=(ListView)findViewById(R.id.lvPagosFactura);
		
		Bundle obtenerDatos=new Bundle();
	    obtenerDatos = this.getIntent().getExtras();	        
	    usuario.cedula=obtenerDatos.getString("cedula");
	    cliente.setNombre(obtenerDatos.getString("nombreCliente"));		
		cliente.setIdCliente(obtenerDatos.getLong("idCliente"));
		factura.setNFactura(obtenerDatos.getLong("NFactura"));
		factura.setNCaja(obtenerDatos.getLong("NCaja"));
		factura.setDevolucion(obtenerDatos.getLong("Devolucion"));
		factura.setValorPagado(obtenerDatos.getLong("ValorPagado"));
		factura.setTotalFactura(obtenerDatos.getLong("totalFactura"));
		factura.setTipoDoc(obtenerDatos.getString("TipoDoc"));

		factura.setIdCliente(cliente.getIdCliente());
		
		etDescuentoPP.setOnKeyListener(
				new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				cargarValoresPagosParciales();
				return false;
			}
		});
		
		
		lvPagosFactura.setOnItemLongClickListener (new OnItemLongClickListener() {
        	 /**
        	  * metodo que se ejecuta al realizar click sostenido sobre un elemento de la lista de articulos
        	  * del pedido
        	  */
    	  public boolean onItemLongClick(AdapterView parent, View view,  final int position, long id) 
    	  {
    		  		ListAdapter listaOpciones = new OpcionesAdapter(CarteraPagosParcialesActivity.this, opcionesAbonos);
   	                AlertDialog.Builder builder = new AlertDialog.Builder(CarteraPagosParcialesActivity.this);	   	                
    		   	    builder.setTitle(letraEstilo.getEstiloTitulo("Opciones"));
	      			builder.setSingleChoiceItems(listaOpciones, -1, new DialogInterface.OnClickListener() {
	      			    public void onClick(DialogInterface dialog, int item) {
	      			    	pagosFactura.getListaPagoFac().remove(position);
	      			    	ClearCamposAbono();
	      			    	CargarAbonosFactura();
  			    			mostrarMensaje("Abono Eliminado Satisfactoriamente", "l");
  			    			dialog.cancel();			        
	      			    }
	      			});
	      			AlertDialog alert = builder.create();
	      			alert.show();
    		  
    		  return false;
    		  }
    		});
		
		
		cargarDatosFactura();
		}
		catch (Exception e) {
			mostrarMensaje(e.toString(), "L");
		}
	}
	
	private void cargarDatosFactura()
	{
		tvNombreClientePP.setText(cliente.getNombre());
		tvNFacturaC.setText(factura.getNFactura()+"");
		tvNCajaC.setText(factura.getNCaja()+"");
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		tvValFacturaC.setText(decimalFormat.format(factura.getTotalFactura()));			
		tvSaldoFacC.setText(decimalFormat.format(factura.getSaldoFactura()));			
		tvNuevoSaldoC.setText(decimalFormat.format(factura.getSaldoFactura()));
		nuevoSaldo=factura.getSaldoFactura();
		tvValorPagarC.setText("0");			
		etDescuentoPP.setText("0");
		etValorAPagar.setText("0");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cartera_pagos_parciales, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.equals(etFormaPago))
		{
			 keyback=false;
			seleccionarFormaPago();
		}
		else if(v.equals(btAgregarPago))
		{
			agregarAbonoFactura();	
		}
		else if(v.equals(btGenerarPagoParcial))
		{
			btGenerarPagoParcial.setEnabled(false);
			if( validaDatos())
			{
				if(parametrosPos.getCarteraOnLine()==1)
				{
					guardarPagosOnline();
				}
				else {
					guardarPagos();
				}
			}

		}
		else if(v.equals(btVolverCPP))
		{
			if(keyback)
			{
				onBackPressed();
			}
			else
			{
				InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(etValorAPagar.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etDescuentoPP.getWindowToken(), 0);				
			    keyback=true;
			}	
		}
		else if(v.equals(etDescuentoPP))
		{
			keyback=false;
		}
		else if(v.equals(etValorAPagar))
		{
		   keyback=false;
		}
	}
	private void agregarAbonoFactura()
	{
		if(getValorAbono()>0)
		{
			if(getValorAbono()<nuevoSaldo)
			{
				ItemPagoFac itemPagoFac=new ItemPagoFac();
				itemPagoFac.setValor(getValorAbono());
				itemPagoFac.setFormaPago(etFormaPago.getText().toString());
				pagosFactura.getListaPagoFac().add(itemPagoFac);
				CargarAbonosFactura();
				ClearCamposAbono();
			}
			else
			{
				mostrarMensaje("El valor del abono debe ser menor que el nuevo saldo de factura", "l");
				etValorAPagar.selectAll();
				etValorAPagar.requestFocus();
			}
		}
		else
		{
			mostrarMensaje("El valor del abono debe ser mayor que 0", "l");
			etValorAPagar.selectAll();
			etValorAPagar.requestFocus();
		}
	}
	private void ClearCamposAbono()
	{
		etFormaPago.setText("EFECTIVO");
		etValorAPagar.setText("");
		etValorAPagar.requestFocus();		
	}
	private long getValorAbono()
	{
		long valorAbono=0;
		try {
			if(etValorAPagar.getText().toString().length()>0)
			{
				valorAbono=Long.parseLong(etValorAPagar.getText().toString());
				return valorAbono;
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	private void CargarAbonosFactura()
	{
		 context = getApplicationContext();
		 lvPagosFactura.setAdapter(new VerFacturasCarteraPagosAdapter(context,R.layout.activity_item_facturas_cartera_pagos,pagosFactura.getListaPagoFac()));
	     cargarValoresPagosParciales();
	}
	private void cargarValoresPagosParciales() {
		descuento=getDescuento();
		nuevoSaldo=factura.getSaldoFactura()-pagosFactura.getValorAbonosFactura()-descuento;
		valorAPagar=pagosFactura.getValorAbonosFactura();
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		tvNuevoSaldoC.setText(decimalFormat.format(nuevoSaldo));
		tvValorPagarC.setText(decimalFormat.format(valorAPagar));
	}

	private void seleccionarFormaPago()
	{
		opciones=new Opciones[listaFormasPago.size()];
		for (int i = 0; i < listaFormasPago.size(); i++) {
		
			opciones[i]=new Opciones(i,listaFormasPago.get(i).toString() , getImg(R.drawable.pedidos), listaFormasPago.get(i).toString());
		}
		ListAdapter listaMotivos = new OpcionesAdapter(CarteraPagosParcialesActivity.this, opciones);  				        		  		
        AlertDialog.Builder builderMotivo = new AlertDialog.Builder(CarteraPagosParcialesActivity.this);
        builderMotivo.setTitle("Seleccione la Bodega");
        builderMotivo.setSingleChoiceItems(listaMotivos, -1, new DialogInterface.OnClickListener() {
//
  			    public void onClick(DialogInterface dialogMotivo, int itemMotivo) {	      			    
  			    
  			    	etFormaPago.setText(listaFormasPago.get(itemMotivo).toString());
  			    	dialogMotivo.cancel();
  			    }
  		}
        );
  	AlertDialog alert = builderMotivo.create();
  	alert.show();
	}
	
	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
	
	private long getDescuento()
	{
		long descuento=0;
		try {
			if(etDescuentoPP.getText().toString().length()>0)
			{
				descuento=Long.parseLong(etDescuentoPP.getText().toString());
				return descuento;
			}
			else
			{
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
		
	}
	private void guardarPagosOnline()
	{
		pago=new Pago();
		pago.setIdPago(bd.obtenerUltimoIdPago(this)+1);
		pago.setValor(valorAPagar+descuento);
		pago.setFecha(parametrosSys.getFecha());
		//pago.setFecha2(parametrosSys.getFechaSys2System());
		pago.setFecha2(parametrosSys.getFechaSysSystem());
		pago.setNombreCliente(" ");
		pago.setEnviado(0);
		pago.setDescripcion("Abono a Factura");
		pago.setIdCliente(cliente.getIdCliente());
		cargarValoresPagosParciales();

		//DATOS PARA PAGO DE FACTURA
		pagosFactura.setIdPagosFactura(bd.obtenerUltimoIdPagosFactura(this)+1);
		pagosFactura.setNPago(pago.getIdPago());
		pagosFactura.setNPagoFac(0);
		pagosFactura.setNCajaQRecibe(parametrosSys.getCaja());
		pagosFactura.setNFactura(factura.getNFactura());
		pagosFactura.setNCaja(factura.getNCaja());
		pagosFactura.setIdCliente(factura.getIdCliente());
		pagosFactura.setCuenta(1001);
		pagosFactura.setNComprobante(0);
		pagosFactura.setFecha(parametrosSys.getFecha());
		pagosFactura.setHora(parametrosSys.getHora());
		//pagosFactura.setFecha2(parametrosSys.getFechaSys2System());
		pagosFactura.setFecha2(parametrosSys.getFechaSysSystem());
		pagosFactura.setDescuento(getDescuento());
		pagosFactura.setRteFuente(0);
		pagosFactura.setTotal(valorAPagar);
		pagosFactura.setSaldoAnterior(factura.getSaldoFactura());
		pagosFactura.setSaldo(nuevoSaldo);
		pagosFactura.setObservaciones(" ");
		pagosFactura.setIdUsuario(0);
		pagosFactura.setFacturasQPaga(factura.getNFactura()+" Cj "+factura.getNCaja());
		pagosFactura.setRteIca(0);
		pagosFactura.setRteIva(0);
		pagosFactura.setDevolucion(0);
		pagosFactura.setIdVendedor(usuario.cedula);
				//bd.insertPagosFactura(pagosFactura);

				//DATOS PARA ITEM PAGOS FACTURA
		//DATOS PARA ITEM PAGOS FACTURA


				//bd.insertItemPagosFactura(itemPagoFac);

				pago.getListaPagosFactura().add(pagosFactura);
				pago.setDescripcion(pago.getDescripcion()+pagosFactura.getNFactura()+" Cj "+pagosFactura.getNCaja()+" ");
				pago.setNPagosFacNoEnviados(pago.getNPagosFacNoEnviados()+1);

				//fac.setPagada("SI");
				//fac.setValorPagado(fac.totalFactura);
				//bd.ActualizarFactura(fac);

				new enviarPagoSys().execute("");
				pdu=ProgressDialog.show(CarteraPagosParcialesActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pago"), true,false);


		}

	private class enviarPagoSys extends AsyncTask<String, Void, Object> {


		@Override
		protected Integer doInBackground(String... params) {

			PutPagosFacturaSys putPagosFacturaSys = new PutPagosFacturaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
			pagosFactura = putPagosFacturaSys.setPagosFactura(getXmlDatosFac(), pagosFactura);

			return 1;
		}

		protected void onPostExecute(Object result) {
			pdu.dismiss();
			guardarPagoFactura();
		}

	}

	public String getXmlDatosFac()
	{


		String xml="";

		xml="<Posstar7>\n";
		xml +="<DatosPagoFactura>\n";
		for (int i=0;i<pago.getListaPagosFactura().size();i++){
			pagosFactura=pago.getListaPagosFactura().get(i);
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
	public void guardarPagoFactura()
	{
		try
		{
			if(pagosFactura.getNPagoFac()!=0)
			{
				// ACTUALIZA PAGO FACTURA
				bd.insertPagosFactura(pagosFactura,true);
				//bd.ActualizarPagoFactura(pagosFactura);
				for (int i = 0; i < pagosFactura.getListaPagoFac().size(); i++) {
					itemPagoFac= pagosFactura.getListaPagoFac().get(i);
					itemPagoFac.setIdItemPagoFac(bd.obtenerUltimoIdItemPagoFac(this)+1);
					itemPagoFac.setNPagoFac(pagosFactura.getNPagoFac());
					itemPagoFac.setNCheque(" ");
					itemPagoFac.setTarjeta(" ");
					bd.insertItemPagosFactura(itemPagoFac);

				}

				pago.setEnviado(1);
				pago.setIdPago(pagosFactura.getNPagoFac());
				bd.insertPago(pago,true,pagosFactura.getNPagoFac());
				mostrarMensaje("Pago Enviado Correctamente", "l");

				//----------------------------------------------------
				//PARAMETRIZAR RESULTADOS ACTIVIDAD CONSULTAR CLIENTES
				//----------------------------------------------------
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putBoolean("save", true);
				b.putLong("idPago", pago.getIdPago());
				b.putBoolean("print",true);
				i.putExtras(b);
				setResult( SUB_ACTIVITY_PAGO_PARCIAL, i );
				//----------------------------------------------------
//	    Intent intent = new Intent(CarteraPagosParcialesActivity.this, CarteraClienteActivity.class );
//		intent.putExtra("cedula", usuario.cedula);
//		intent.putExtra("operacion", CARTERA);
//		startActivity(intent);
				finish();
			}
			else
			{
				btGenerarPagoParcial.setEnabled(true);
				mostrarMensaje("No Fue Posible enviar El Pago ","l");

			}
		}
		catch (Exception e) {
			mostrarMensaje(e.toString(), "l");
		}


	}
	private void guardarPagos()
	{
		pago=new Pago();
		pago.setIdPago(bd.obtenerUltimoIdPago(this)+1);
		pago.setValor(valorAPagar+descuento);
		pago.setFecha(parametrosSys.getFecha());
		//pago.setFecha2(parametrosSys.getFechaSys2System());
		pago.setFecha2(parametrosSys.getFechaSysSystem());
		pago.setNombreCliente(" ");
		pago.setEnviado(0);
		pago.setDescripcion("Abono a Factura");
		pago.setIdCliente(cliente.getIdCliente());
		cargarValoresPagosParciales();
		
			//DATOS PARA PAGO DE FACTURA		
		pagosFactura.setIdPagosFactura(bd.obtenerUltimoIdPagosFactura(this)+1);
		pagosFactura.setNPago(pago.getIdPago());
		pagosFactura.setNPagoFac(0);
		pagosFactura.setNCajaQRecibe(factura.getNCaja());
		pagosFactura.setNFactura(factura.getNFactura());
		pagosFactura.setNCaja(factura.getNCaja());
		pagosFactura.setIdCliente(factura.getIdCliente());
		pagosFactura.setCuenta(1001);
		pagosFactura.setNComprobante(0);
		pagosFactura.setFecha(parametrosSys.getFecha());
		pagosFactura.setHora(parametrosSys.getHora());
		//pagosFactura.setFecha2(parametrosSys.getFechaSys2System());
		pagosFactura.setFecha2(parametrosSys.getFechaSysSystem());
		pagosFactura.setDescuento(getDescuento());
		pagosFactura.setRteFuente(0);
		pagosFactura.setTotal(valorAPagar);
		pagosFactura.setSaldoAnterior(factura.getSaldoFactura());
		pagosFactura.setSaldo(nuevoSaldo);
		pagosFactura.setObservaciones(" ");
		pagosFactura.setIdUsuario(0);
		pagosFactura.setFacturasQPaga(factura.getNFactura()+" Cj "+factura.getNCaja());
		pagosFactura.setRteIca(0);
		pagosFactura.setRteIva(0);
		pagosFactura.setDevolucion(0);
		bd.insertPagosFactura(pagosFactura,false);
//				bd.insertPagosFactura(pagosFactura);
		
		//DATOS PARA ITEM PAGOS FACTURA
		for (int i = 0; i < pagosFactura.getListaPagoFac().size(); i++) {
			 itemPagoFac= pagosFactura.getListaPagoFac().get(i);
			itemPagoFac.setIdItemPagoFac(bd.obtenerUltimoIdItemPagoFac(this)+1);
			itemPagoFac.setNPagoFac(pagosFactura.getIdPagosFactura());			
			itemPagoFac.setNCheque(" ");
			itemPagoFac.setTarjeta(" ");
			bd.insertItemPagosFactura(itemPagoFac);
		}
		
		
		
		pago.getListaPagosFactura().add(pagosFactura);
		pago.setDescripcion(pago.getDescripcion()+pagosFactura.getNFactura()+" Cj "+pagosFactura.getNCaja()+" ");
		pago.setNPagosFacNoEnviados(pago.getNPagosFacNoEnviados()+1);
		
		factura.setPagada("NO");
		if(nuevoSaldo==0)
		{			
			factura.setPagada("SI");
		}
		factura.setValorPagado(factura.getValorPagado()+valorAPagar+getDescuento());

		if(factura.TipoDoc.equals("FAC"))
		{
			bd.ActualizarFacturaPagoParcial(factura);
		}
		else
		{
			bd.ActualizarRemisionPagoParcial(factura);
		}

		
		if(bd.insertPago(pago,false,0))
		{
			mostrarMensaje("Pago generado Correctamente"+pago.getValor(), "l");
		}
		else
		{
			mostrarMensaje("Error al guardar pago", "l");
		}
		btGenerarPagoParcial.setEnabled(true);
		
		//----------------------------------------------------
		//PARAMETRIZAR RESULTADOS ACTIVIDAD CONSULTAR CLIENTES	
		//----------------------------------------------------
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putBoolean("save", true);
		b.putLong("idPago", pago.getIdPago());
		b.putBoolean("print",true);
		i.putExtras(b);			  
	    setResult( SUB_ACTIVITY_PAGO_PARCIAL, i ); 	    
	    //----------------------------------------------------	 
//	    Intent intent = new Intent(CarteraPagosParcialesActivity.this, CarteraClienteActivity.class );
//		intent.putExtra("cedula", usuario.cedula);
//		intent.putExtra("operacion", CARTERA);
//		startActivity(intent);
		finish();
		
	}
	private boolean validaDatos()
	{
		if(pagosFactura.getListaPagoFac().size()>0)
		{
			if(valorAPagar>0)
			{
				return true;
			}
			else
			{
				mostrarMensaje("El valor del descuento no debe ser mayor al valor a pagar de la factura!!", "l");
				etDescuentoPP.selectAll();				
				etDescuentoPP.requestFocus();
			}
			
		}
		else
		{
			mostrarMensaje("Debe ingresar al menos un abono a la factura !!.", "l");
			etValorAPagar.selectAll();
			etValorAPagar.requestFocus();
		}
		btGenerarPagoParcial.setEnabled(false);
		return false;			
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if(keyCode == KeyEvent.KEYCODE_BACK)
	        {        	
	        	Intent i = new Intent();
	    		Bundle b = new Bundle();
	    		b.putBoolean("save", false);
	    		i.putExtras(b);			  
	    	    setResult( SUB_ACTIVITY_PAGO_PARCIAL, i );   	   
	    		finish();
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
	}
	

}
