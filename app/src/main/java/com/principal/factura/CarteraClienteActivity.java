package com.principal.factura;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.BatteryStatusChangeEventListener;
import com.epson.eposprint.Builder;
import com.epson.eposprint.Print;
import com.epson.eposprint.StatusChangeEventListener;
import com.principal.mundo.Cliente;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Usuario;
import com.principal.mundo.sysws.GetDeudaClientexVendedor;
import com.principal.mundo.sysws.GetFacCliente;
import com.principal.mundo.sysws.PutPagosFacturaSys;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintBixolon;
import com.principal.print.PrintDigitaPos;
import com.principal.print.PrintEpson;
import com.principal.print.PrintFactura;
import com.principal.print.PrintZebra;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;

public class CarteraClienteActivity extends Activity implements OnClickListener ,StatusChangeEventListener, BatteryStatusChangeEventListener {

	protected static final int SUB_ACTIVITY_CONSULTA_CLIENTES = 400;
	protected static final int SUB_ACTIVITY_PAGO_PARCIAL = 500;
	private final static int CARTERA=7;
	
	private Usuario usuario;
	private Cliente cliente;
	private PagosFactura pagosFactura;
	private ArrayList<PagosFactura> listaPagos;
	private ItemPagoFac itemPagoFac;
	private Pago pago;
	private ProgressDialog pdu;
	static final int SEND_TIMEOUT = 10 * 1000;
	private Print printer;
	//variables para componentes graficos
	
	private TextView tvNombreCliente, tvDeuda, tvSubTotal, tvTotalPago;
	private RadioButton rbPagosParciales,rbPagosTotales;
	private Button btGenerarPago, btVolverCPT;
	private ListView lvFacturasPend;
	private LinearLayout llSubtotal,lltotal;
	private EditText etDescuento;
	LetraEstilo letraEstilo;
	private boolean keyback=false;

	static BixolonPrinter mBixolonPrinter;
	
	creaBD bd;
	Context context;
	private ArrayList<Factura_in> listaFacturas=new ArrayList<Factura_in>();

	long subtotal=0;
	long total=0;
	long descuento=0;
	
	Parametros parametrosPos, parametrosSys;
	private GetFacCliente getFacCliente;



	//Variables para impresora digital pos
	//IMyBinder interface?All methods that can be invoked to connect and send data are encapsulated within this interface
	public static IMyBinder binder;
	public static boolean ISCONNECT;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cartera_cliente);

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




		usuario=new Usuario();
		cliente=new Cliente();	
		bd=new creaBD(this);  
		letraEstilo=new LetraEstilo();
		parametrosPos=bd.getParametros(this,"P");
	    parametrosSys=bd.getParametros(this,"S");
		
		Bundle obtenerDatos=new Bundle();
	    obtenerDatos = this.getIntent().getExtras();	        
	    usuario.cedula=obtenerDatos.getString("cedula");
		cliente.setNombre(obtenerDatos.getString("nombreCliente"));
		cliente.setDeudaActual(obtenerDatos.getLong("deudaActual"));
		cliente.setIdCliente(obtenerDatos.getLong("idCliente"));

		//valida que el cliente este ingresado en la base de datos!!
		bd.insertClienteCartera(cliente);

		
		tvNombreCliente=(TextView)findViewById(R.id.tvNombreCliente);
		tvDeuda=(TextView)findViewById(R.id.tvDeuda);
		tvSubTotal=(TextView)findViewById(R.id.tvSubTotal);
		tvTotalPago=(TextView)findViewById(R.id.tvTotalPago);
		
		rbPagosParciales=(RadioButton)findViewById(R.id.rbPagosParciales);
		rbPagosTotales=(RadioButton)findViewById(R.id.rbPagosTotales);
		rbPagosParciales.setOnClickListener(this);
		rbPagosTotales.setOnClickListener(this);
		
		btGenerarPago=(Button)findViewById(R.id.btGenerarPago);
		btGenerarPago.setOnClickListener(this);
		
		btVolverCPT=(Button)findViewById(R.id.btVolverCPT);
		btVolverCPT.setOnClickListener(this);
		
//		etDescuento.setOnClickListener(this);
		
		lvFacturasPend=(ListView)findViewById(R.id.lvFacturasPend);
	
		lvFacturasPend.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Factura_in fac=listaFacturas.get(position);
				if(rbPagosTotales.isChecked())
				{								
					fac.isPagada=!fac.isPagada;
					context = getApplicationContext();
			        lvFacturasPend.setAdapter(new VerFacturasCarteraAdapter(context,R.layout.activity_item_facturas_cartera,listaFacturas,rbPagosTotales.isChecked()));
			        cargarValoresPagosTotales();
				}
				else
				{
					lvFacturasPend.setEnabled(false);
					Intent intent = new Intent(CarteraClienteActivity.this, CarteraPagosParcialesActivity.class );
					intent.putExtra("nombreCliente", cliente.nombre);
					intent.putExtra("idCliente", cliente.idCliente);
					intent.putExtra("cedula", usuario.cedula);
					intent.putExtra("NFactura", fac.NFactura);
					intent.putExtra("NCaja", fac.NCaja);
					intent.putExtra("Devolucion", fac.getDevolucion());
					intent.putExtra("ValorPagado", fac.ValorPagado);
					intent.putExtra("totalFactura", fac.getTotalFactura());
					intent.putExtra("TipoDoc", fac.getTipoDoc());
					startActivityForResult(intent,SUB_ACTIVITY_PAGO_PARCIAL );				
				}
				
			}
        });
		
		llSubtotal=(LinearLayout)findViewById(R.id.llSubtotal);
		lltotal=(LinearLayout)findViewById(R.id.lltotal);
		
		etDescuento=(EditText)findViewById(R.id.etDescuento);
		etDescuento.setOnKeyListener(
				new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				cargarValoresPagosTotales();
				return false;
			}
		});
		etDescuento.setVisibility(View.GONE);
		
		cargarDatosCliente();
	}
	
	public void cargarDatosCliente()
	{
		tvNombreCliente.setText(cliente.getNombre());
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		tvDeuda.setText(decimalFormat.format(cliente.getDeudaActual()));
		
		setPagosTotales(false);
		rbPagosTotales.setChecked(false);
		rbPagosParciales.setChecked(true);

		if(parametrosPos.getCarteraOnLine()==1)
		{
			new getDatos().execute("");
			pdu = ProgressDialog.show(this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Obteniendo datos"), true, false);

		}
		else
		{
			CargarFacturas(false);
		}

	
	}
	public void CargarFacturas(boolean isPagosTotales)
	{
		listaFacturas=bd.getFacturasCartera(this, cliente.getIdCliente());

	    context = getApplicationContext();
        lvFacturasPend.setAdapter(new VerFacturasCarteraAdapter(context,R.layout.activity_item_facturas_cartera,listaFacturas,isPagosTotales));
        cargarValoresPagosTotales();
//        clearCamposPagosTotales();
	}
	public void CargarFacturasOnline(boolean isPagosTotales)
	{
		context = getApplicationContext();
		lvFacturasPend.setAdapter(new VerFacturasCarteraAdapter(context,R.layout.activity_item_facturas_cartera,listaFacturas,isPagosTotales));
		cargarValoresPagosTotales();
//        clearCamposPagosTotales();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cartera_cliente, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.equals(rbPagosParciales))
		{
			setPagosTotales(!((RadioButton)v).isChecked());
			rbPagosTotales.setChecked(!((RadioButton)v).isChecked());

			if(parametrosPos.getCarteraOnLine()==1)
			{
				CargarFacturasOnline(!((RadioButton)v).isChecked());
			}
			else {
				CargarFacturas(!((RadioButton)v).isChecked());
			}


		}
		else if(v.equals(rbPagosTotales))
		{
			setPagosTotales(((RadioButton)v).isChecked());
			rbPagosParciales.setChecked(!((RadioButton)v).isChecked());
			if(parametrosPos.getCarteraOnLine()==1)
			{
				CargarFacturasOnline(((RadioButton)v).isChecked());
			}
			else {
				CargarFacturas(((RadioButton)v).isChecked());
			}
		}	
		else if(v.equals(lvFacturasPend))
		{
			if(rbPagosTotales.isChecked())
			{
				cargarValoresPagosTotales();
			}			
		}
		else if(v.equals(btGenerarPago))
		{
			btGenerarPago.setEnabled(false);
			if(validaDatos())
			{
				if(parametrosPos.getCarteraOnLine()==1)
				{
					guardarPagosOnline();
				}
				else
				{
					guardarPagos();
				}

			}
		}
		else if(v.equals(btVolverCPT))
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
				imm.hideSoftInputFromWindow(etDescuento.getWindowToken(), 0);
			    keyback=true;
			}	
		}
		else if(v.equals(etDescuento))
		{
		   keyback=false;
		}
	}
	
	private void guardarPagos()
	{
		pago=new Pago();
		pago.setIdPago(bd.obtenerUltimoIdPago(this)+1);
		pago.setValor(total);
		pago.setFecha(parametrosSys.getFecha());
		//pago.setFecha2(parametrosSys.getFechaSys2System());
		pago.setFecha2(parametrosSys.getFechaSysSystem());
		pago.setNombreCliente(" ");
		pago.setEnviado(0);
		pago.setDescripcion("Pago Factura(s) ");
		pago.setIdCliente(cliente.getIdCliente());
		
		for (int i = 0; i < listaFacturas.size(); i++) {
			if (listaFacturas.get(i).isPagada) {
				Factura_in fac= listaFacturas.get(i);
				
				//DATOS PARA PAGO DE FACTURA
				pagosFactura=new PagosFactura();
				pagosFactura.setIdPagosFactura(bd.obtenerUltimoIdPagosFactura(this)+1);
				pagosFactura.setNPago(pago.getIdPago());
				pagosFactura.setNPagoFac(0);
				pagosFactura.setNCajaQRecibe(fac.getNCaja());
				pagosFactura.setNFactura(fac.getNFactura());
				pagosFactura.setNCaja(fac.getNCaja());
				pagosFactura.setIdCliente(fac.getIdCliente());
				pagosFactura.setCuenta(1001);
				pagosFactura.setNComprobante(0);
				pagosFactura.setFecha(parametrosSys.getFecha());
				pagosFactura.setHora(fac.getHora());
				//pagosFactura.setFecha2(parametrosSys.getFechaSys2System());
				pagosFactura.setFecha2(parametrosSys.getFechaSysSystem());

				pagosFactura.setDescuento(getDescuento());
				pagosFactura.setRteFuente(0);
				pagosFactura.setTotal(fac.getSaldoFactura());
				pagosFactura.setSaldoAnterior(fac.getSaldoFactura());
				pagosFactura.setSaldo(0);
				pagosFactura.setObservaciones(" ");
				pagosFactura.setIdUsuario(0);
				pagosFactura.setFacturasQPaga(fac.getNFactura()+" Cj "+fac.getNCaja());
				pagosFactura.setRteIca(0);
				pagosFactura.setRteIva(0);
				pagosFactura.setDevolucion(0);
				
				bd.insertPagosFactura(pagosFactura,false);
				
				//DATOS PARA ITEM PAGOS FACTURA
				itemPagoFac=new ItemPagoFac();
				itemPagoFac.setIdItemPagoFac(bd.obtenerUltimoIdItemPagoFac(this)+1);
				itemPagoFac.setNPagoFac(pagosFactura.getIdPagosFactura());
				itemPagoFac.setValor(pagosFactura.getTotal());
				itemPagoFac.setFormaPago("EFECTIVO");
				itemPagoFac.setNCheque(" ");
				itemPagoFac.setTarjeta(" ");
				pagosFactura.getListaPagoFac().add(itemPagoFac);
				
				bd.insertItemPagosFactura(itemPagoFac);
				
				pago.getListaPagosFactura().add(pagosFactura);
				pago.setDescripcion(pago.getDescripcion()+pagosFactura.getNFactura()+" Cj "+pagosFactura.getNCaja()+" ");
				pago.setNPagosFacNoEnviados(pago.getNPagosFacNoEnviados()+1);
				
				fac.setPagada("SI");
				fac.setValorPagado(fac.totalFactura);

				if(fac.TipoDoc.equals("FAC"))
				{
					bd.ActualizarFactura(fac);
				}
				else
				{
					bd.ActualizarRemision(fac);
				}

			}
		}
		if(bd.insertPago(pago,false,0))
		{
			mostrarMensaje("Pago generado Correctamente", "l");
		}
		else
		{
			mostrarMensaje("Error al guardar pago", "l");
		}
		
		//----------------------------------------------------
		//PARAMETRIZAR RESULTADOS ACTIVIDAD CONSULTAR CLIENTES	
		//----------------------------------------------------
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putBoolean("save", true);
		i.putExtras(b);			  
	    setResult( SUB_ACTIVITY_CONSULTA_CLIENTES, i ); 	    
	    //----------------------------------------------------	 
	    Intent intent = new Intent(CarteraClienteActivity.this, ListaPedidosActivity.class );
		intent.putExtra("cedula", usuario.cedula);
		intent.putExtra("operacion", CARTERA);
		intent.putExtra("idPago", pago.getIdPago());
		intent.putExtra("print",true);
		startActivity(intent);
		finish();
		
	}

	private void guardarPagosOnline()
	{
		pago=new Pago();
		pago.setIdPago(bd.obtenerUltimoIdPago(this)+1);
		pago.setValor(total);
		pago.setFecha(parametrosSys.getFecha());
		//pago.setFecha2(parametrosSys.getFechaSys2System());
		pago.setFecha2(parametrosSys.getFechaSysSystem());
		pago.setNombreCliente(" ");
		pago.setEnviado(0);
		pago.setDescripcion("Pago Factura(s) ");
		pago.setIdCliente(cliente.getIdCliente());

		for (int i = 0; i < listaFacturas.size(); i++) {
			if (listaFacturas.get(i).isPagada) {
				Factura_in fac= listaFacturas.get(i);

				//DATOS PARA PAGO DE FACTURA
				pagosFactura=new PagosFactura();
				pagosFactura.setIdPagosFactura(bd.obtenerUltimoIdPagosFactura(this)+1);
				pagosFactura.setNPago(pago.getIdPago());
				pagosFactura.setNPagoFac(0);
				pagosFactura.setNCajaQRecibe(parametrosSys.getCaja());
				pagosFactura.setNFactura(fac.getNFactura());
				pagosFactura.setNCaja(fac.getNCaja());
				pagosFactura.setIdCliente(fac.getIdCliente());
				pagosFactura.setCuenta(1001);
				pagosFactura.setNComprobante(0);
				pagosFactura.setFecha(parametrosSys.getFecha());
				pagosFactura.setHora(parametrosSys.getHora());
				//pagosFactura.setFecha2(parametrosSys.getFechaSys2System());
				pagosFactura.setFecha2(parametrosSys.getFechaSysSystem());

				pagosFactura.setDescuento(getDescuento());
				pagosFactura.setRteFuente(0);
				pagosFactura.setTotal(fac.getSaldoFactura());
				pagosFactura.setSaldoAnterior(fac.getSaldoFactura());
				pagosFactura.setSaldo(0);
				pagosFactura.setObservaciones(" ");
				pagosFactura.setIdUsuario(0);
				pagosFactura.setFacturasQPaga(fac.getNFactura()+" Cj "+fac.getNCaja());
				pagosFactura.setRteIca(0);
				pagosFactura.setRteIva(0);
				pagosFactura.setDevolucion(0);
				pagosFactura.setIdVendedor(usuario.cedula);

				//bd.insertPagosFactura(pagosFactura);

				//DATOS PARA ITEM PAGOS FACTURA
				itemPagoFac=new ItemPagoFac();
				itemPagoFac.setIdItemPagoFac(bd.obtenerUltimoIdItemPagoFac(this)+1);
				itemPagoFac.setNPagoFac(pagosFactura.getIdPagosFactura());
				itemPagoFac.setValor(pagosFactura.getTotal());
				itemPagoFac.setFormaPago("EFECTIVO");
				itemPagoFac.setNCheque(" ");
				itemPagoFac.setTarjeta(" ");
				pagosFactura.getListaPagoFac().add(itemPagoFac);

				//bd.insertItemPagosFactura(itemPagoFac);

				pago.getListaPagosFactura().add(pagosFactura);
				pago.setDescripcion(pago.getDescripcion()+pagosFactura.getNFactura()+" Cj "+pagosFactura.getNCaja()+" ");
				pago.setNPagosFacNoEnviados(pago.getNPagosFacNoEnviados()+1);

				//fac.setPagada("SI");
				//fac.setValorPagado(fac.totalFactura);
				//bd.ActualizarFactura(fac);


			}
		}
		if(pago.getListaPagosFactura().size()>0) {
			new enviarPagoSys().execute("");
			pdu = ProgressDialog.show(CarteraClienteActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Enviando Pago"), true, false);
		}




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
				itemPagoFac.setNPagoFac(pagosFactura.getNPagoFac());
				bd.insertItemPagosFactura(itemPagoFac);
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
				i.putExtras(b);
				setResult( SUB_ACTIVITY_CONSULTA_CLIENTES, i );
				//----------------------------------------------------
				Intent intent = new Intent(CarteraClienteActivity.this, ListaPedidosActivity.class );
				intent.putExtra("cedula", usuario.cedula);
				intent.putExtra("operacion", CARTERA);
				intent.putExtra("idPago", pago.getIdPago());
				intent.putExtra("print",true);
				startActivity(intent);
				finish();
			}
			else
			{
				mostrarMensaje("No Fue Posible enviar El Pago ","l");
				btGenerarPago.setEnabled(true);
			}
		}
		catch (Exception e) {
			mostrarMensaje(e.toString(), "l");
		}
		btGenerarPago.setEnabled(false);
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
	private boolean validaDatos()
	{
		if(getFacturasACancelar()>0)
		{
			if(total>0)
			{
				return true;
			}
			else
			{
				mostrarMensaje("El valor del descuento no debe ser mayor al valor de las facturas a cancelar!!", "l");
				etDescuento.selectAll();				
				etDescuento.requestFocus();
				btGenerarPago.setEnabled(true);
			}
			
		}
		else
		{
			mostrarMensaje("Debe Seleccionar al menos Una factura a Pagar!!.", "l");
			btGenerarPago.setEnabled(true);
		}
		return false;			
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
	private void cargarValoresPagosTotales()
	{
		 subtotal=0;
		 total=0;
		 descuento=getDescuento();
		
		for (int i = 0; i < listaFacturas.size(); i++) {
			if(listaFacturas.get(i).isPagada)
			{
				//subtotal+=listaFacturas.get(i).getTotalFactura();	
				subtotal+=listaFacturas.get(i).getSaldoFactura();	
			}
		}
		total=subtotal-descuento;
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		tvSubTotal.setText(decimalFormat.format(subtotal));			
		tvTotalPago.setText(decimalFormat.format(total));		
	}
//	private void clearCamposPagosTotales()
//	{
//		tvSubTotal.setText("0");
//		tvTotalPago.setText("0");
//		etDescuento.setText("0");
//	} 
	private long getDescuento()
	{
		long descuento=0;
		try {
			if(etDescuento.getText().toString().length()>0)
			{
				descuento=Long.parseLong(etDescuento.getText().toString());
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
	private long getFacturasACancelar()
	{
		long res=0;
		for (int i = 0; i < listaFacturas.size(); i++) {
			if(listaFacturas.get(i).isPagada)
			{
				res++;
			}
		}
		return res;
	}
	private void setPagosTotales(boolean isChecked)
	{
		int visibility=View.VISIBLE;
		if(!isChecked)
		{
			visibility=View.GONE;
		}
		llSubtotal.setVisibility(visibility);
		lltotal.setVisibility(visibility);		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(requestCode == SUB_ACTIVITY_PAGO_PARCIAL)
		{
			lvFacturasPend.setEnabled(true);
		 try
			{

			  Bundle b = data.getExtras();	
			  boolean save=b.getBoolean("save");		  
			  if(save)
			  {
				 cargarDatosCliente();
				 DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
				 tvDeuda.setText(decimalFormat.format(getNuevaDeudaCliente()));
				 boolean print=b.getBoolean("print");
				 if (print) {
					 long idPago=b.getLong("idPago");
					 PrintDocumentIdPago(idPago);				
				 }
					
					
			  }
			 
			}
			catch(Exception e)
			{
				//mostrarMensaje("No Selecciono ningun producto", "l");
			}
		}
		
		
	}

	private long getNuevaDeudaCliente()
	{
		long deuda=0;
		for (int i = 0; i < listaFacturas.size(); i++) {
			deuda+=listaFacturas.get(i).getSaldoFactura();
		}
		return deuda;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent  event) 
	{
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {        	
        	Intent i = new Intent();
    		Bundle b = new Bundle();
    		b.putBoolean("save", false);
    		i.putExtras(b);			  
    	    setResult( SUB_ACTIVITY_CONSULTA_CLIENTES, i );   	   
    		finish();
        	return true;
        }
        else
        {
        	return false;
        }
        
        
	}
	 private void PrintDocumentIdPago(long IdPago)
	    {
		 boolean valid=false;
		 try
		 {	    	
			pago=bd.getPagosPorIdPago(this, IdPago);
			pago.setListaPagosFactura(bd.getPagosFacturaPorIdPago(this, pago.getIdPago()));
			pago.setDeudaCliente(bd.getDeudaCliente(this, pago.getIdCliente()));
			valid=true;
		 
		 }
		 catch (Exception e) {
			
		}
	    	
	    	if(valid)
	    	{
				if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)
				{
					//pdu=ProgressDialog.show(CarteraClienteActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true,false);
					PrintFactura printFactura=new PrintFactura();
					printFactura.printPago(pago, parametrosSys);
					//pdu.dismiss();
					mostrarMensaje(printFactura.getMensaje(), "l");
				}
				else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==1& parametrosPos.getUsaPrintBixolon()==0& parametrosPos.getUsaPrintDigitalPos()==0)
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
				else if(parametrosPos.getUsaImpresoraZebra()==0 & parametrosPos.getUsaPrintEpson()==0& parametrosPos.getUsaPrintBixolon()==1& parametrosPos.getUsaPrintDigitalPos()==0)
				{
					try
					{
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
						printDigitalPos810();
					}catch(Exception e){
						mostrarMensaje("No fue posible Enviar la impresion", "l");
						mostrarMensaje("Verifique que la impresora este encendida y el bluetooth del telefono este activo", "l");
					}
				}
				else {
					PrintZebra pz = new PrintZebra(parametrosPos.getMacAdd());
					try {
						//pdu = ProgressDialog.show(CarteraClienteActivity.this, letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Imprimiendo.."), true, false);
						boolean resPrint = false;
						resPrint = pz.printPago(pago, parametrosSys);
						if (resPrint) {
						//	pdu.dismiss();
							mostrarMensaje("Impresion enviada Correctamente..", "l");
						} else {
						//	pdu.dismiss();
							mostrarMensaje("No fue posible Enviar la impresion", "l");
						}
					} catch (Exception e) {
						//pdu.dismiss();
						mostrarMensaje("Imp22 " + e.toString(), "l");
					}//
				}
	    	}
	    }
	public void onStatusChangeEvent(final String deviceName, final int status) {
		;
	}
	public void onBatteryStatusChangeEvent(final String deviceName, final int battery) {
		;
	}

	private class getDatos extends AsyncTask<String, Void, Object>
	{

		@Override
		protected Integer doInBackground(String... params) {


			getFacCliente =new GetFacCliente(parametrosSys.getIp(),parametrosSys.getWebidText());
			listaFacturas=getFacCliente.getClientes(""+cliente.getIdCliente());
			return 1;
		}

		protected void onPostExecute(Object result)
		{
			pdu.dismiss();
			if(listaFacturas!=null)
			{
				if(listaFacturas.size()>0) {
					CargarFacturasOnline(false);
				}
				else {
					mostrarMensaje("No existen facturas pendientes del cliente","l");
				}
			}
			else
			{
				mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");

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
						printBixolon.printPago(mBixolonPrinter,pago, parametrosSys);


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
				printDigitaPos.printPago(binder,pago, parametrosSys);



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
