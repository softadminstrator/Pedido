package com.principal.factura;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.mundo.Articulo;
import com.principal.mundo.Bodega;
import com.principal.mundo.Categoria;
import com.principal.mundo.GetBodega;
import com.principal.mundo.GetBodegaSys;
import com.principal.mundo.GetCategorias;
import com.principal.mundo.GetCategoriasSys;
import com.principal.mundo.Opciones;
import com.principal.mundo.Parametros;
import com.principal.persistencia.creaBD;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;

public class ConfiguracionActivity extends Activity implements OnClickListener, DiscoveryHandler, AdapterView.OnItemSelectedListener
{
	//----------------------------------------------------------------
	//--------------------CONSTANTE-----------------------------------
	//----------------------------------------------------------------
	private static final int RES =10;
	//----------------------------------------------------------------
	//---------------------ATRIBUTOS----------------------------------
	//----------------------------------------------------------------
	/**
	/**
	 * Atributo bd referencia de la clase creaBD
	 */
	creaBD bd;
	Context context;
	private ProgressDialog pdu;
	private ArrayList<Categoria> listaCategorias;
	private ArrayList<Categoria> listaCategoriasXml;
	private ArrayList<Articulo> listaArticulos;
	private Categoria categoria;
	private ListView listView ;
	private LetraEstilo letraEstilo;
	private Articulo articulo;
	ExpandableListAdapter mAdapter;
	ArrayList<Bodega> listaBodegas;	
	GetBodega getBodega;
	GetBodegaSys getBodegaSys;
	Bodega bodegaPedidos, bodegaFactura, bodegaAdmInv,bodegaTranslados, bodegaRemision;
	Opciones [] opciones;
	private int mbodegaPedidos=0;
	ArrayList<View> views;
	private ArrayAdapter<String> dataAdapter;
	
	private LinearLayout llFechaActualizacion,llModificaStock,llBodegaInventario,llBodegaPedidos,
						llBodegaFactura,llNCaja,llUsaPrintZebra,llConnPrintZebra,llBodegaTranslados,
						llConsultaZ, llGeneraCierre,llIpCash,llIpPosstar,llStocken0,
						llFacturaOnline,llRazonSocial,llRepresentante,llRegimenNit, llDireccionTel, llResDian, llRango, llNombreVendedor,llPrefijo
						, llConnPrintEpson, llUsaPrintEpson,llCarteraOnline, llConnPrintBixolon, llUsaPrintBixolon, llBodegaRemision,  llConnPrintDigitalPos, llUsaPrintDigitalPos;
	
	/**
	 * Referencia de la clase Parametros para el envio de informacion
	 */
	Parametros parametrosPos, parametrosSys;
	

	/**
	 * Atributo btGuardar referente al boton para guardar la configuracion
	 */
	Button btGuardar,btCategorias,btAlertOk,btCargarXml,btConnPrint,btConnPrintEpson ,btConnPrintBixolon,btConnPrintDigitalPos;
	/**
	 * Atributo editTexts referente a la clase EditText que guarda las cajas de texto de la actividad
	 */
	EditText [] editTexts;
	EditText tvMacEpson, tvMacBixolon, tvMacDigitalPos,tvMac,etWebid;
	
	TextView tvBodegaFacturas,tvNCaja, tvBodegaAdmInvOmision,tvBodegaPedidos,  tvIpSys, tvIPInfo,  tvBodegaRemision;

	Spinner spPrecioMinimo;
	/**
	 * Atributo cbModifica que hace referencia a el CheckBox que define si la aplicacion permite 
	 * modificar precio del producto al momento de hacer el pedido
	 */
	CheckBox cbModifica,cbPrecioLibre,cbUsaPrecio123, cbAdministraInventario,cbConsultaArtOnline,cbFactura, cbRealizaPedidos,cbUsaCatalogo
			,cbModificaStock, cbUsaPrintCebra,cbRealizaTranslados,cbConsultaZ, cbGeneraCierre, cbWs, cbRealizaPedidosMesa, cbUsaTodasLasCategorias
			,cbStocken0,cbFacturaOnline,cbUsaObservMasMenos,cbDescuentoPedido,cbImprimePedido,cbConsultaCosto,cbUsaPrintEpson,cbUsaPrintBixolon,cbUsaPrintDigitalPos
			, cbUsaCantDecimal , cbUsaSelecMultipleArt,cbCarteraOnline ,cbControlaPrecioLibre, cbSelectDocumentoPedido,cbRealizaAlistamiento,cbSelectFormaPagoPedido,cbUsaPrestamos
			,cbRealizaRemision, cbModificaValorTotal , cbDescuentaStockEnPedido, cbUsaTipoPedido;
	String res ="";
	private ArrayList<DiscoveredPrinter> printerItems;
    private ArrayList<Map<String, String>> printerSettings;
	

	

	/**
	 * metodo en el que se asignan valores a los atributos de la clase
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
       
        	
        bd=new creaBD(this);    
        views=new ArrayList<View>();
        parametrosPos=bd.getParametros(this,"P");
        parametrosSys=bd.getParametros(this,"S");
        editTexts=new EditText[24];
        letraEstilo=new LetraEstilo();
        listaBodegas=bd.getBodegas(this);
        bodegaPedidos=bd.getBodega( parametrosPos.getBodegaPedidosOmision());
        bodegaFactura=bd.getBodega(parametrosPos.getBodegaFacturaOmision());
        bodegaAdmInv=bd.getBodega( parametrosPos.getBodegaAdmInvOmision());
        bodegaTranslados=bd.getBodega(parametrosPos.getBodegaTransladosOmision());
		bodegaRemision=bd.getBodega(parametrosPos.getBodegaRemisionOmision() );
     
      
	    
		opciones=new Opciones[listaBodegas.size()];
		for (int i = 0; i < listaBodegas.size(); i++) {
			Bodega b=listaBodegas.get(i);
			opciones[i]=new Opciones(b.getIdBodega(),b.getBodega() , getImg(R.drawable.pedidos), b.getBodega());
		}
		
		tvBodegaFacturas=(TextView)findViewById(R.id.tvBodegaFacturas);
		tvBodegaAdmInvOmision=(TextView)findViewById(R.id.tvBodegaAdmInvOmision);
		tvBodegaPedidos=(TextView)findViewById(R.id.tvBodegaPedidos);

		tvBodegaRemision=(TextView)findViewById(R.id.tvBodegaRemision);


		tvNCaja=(TextView)findViewById(R.id.tvNCaja);
		tvMac=(EditText) findViewById(R.id.tvMac);
		tvMac.setEnabled(true);

		tvMacEpson=(EditText) findViewById(R.id.tvMacEpson);
		tvMacEpson.setEnabled(true);

		tvMacBixolon=(EditText) findViewById(R.id.tvMacBixolon);
		tvMacBixolon.setEnabled(true);

		tvMacDigitalPos=(EditText) findViewById(R.id.tvMacDigitalPos);
		tvMacDigitalPos.setEnabled(true);

		etWebid=(EditText) findViewById(R.id.etWebid);
		etWebid.setEnabled(true);

		
		tvIpSys=(TextView)findViewById(R.id.tvIpSys);
		tvIPInfo=(TextView)findViewById(R.id.tvIPInfo);
               
        editTexts[0]=(EditText)findViewById(R.id.etIP1);
        editTexts[1]=(EditText)findViewById(R.id.etIP2);
        editTexts[2]=(EditText)findViewById(R.id.etIP3);
        editTexts[3]=(EditText)findViewById(R.id.etIP4);       
        editTexts[4]=(EditText)findViewById(R.id.etIpSys1);
        editTexts[5]=(EditText)findViewById(R.id.etIpSys2);
        editTexts[6]=(EditText)findViewById(R.id.etIpSys3);
        editTexts[7]=(EditText)findViewById(R.id.etIpSys4);
        editTexts[8]=(EditText)findViewById(R.id.etFecha);
        editTexts[9]=(EditText)findViewById(R.id.etNRutaN);  
       
        editTexts[10]=(EditText)findViewById(R.id.etBodegaPedidos);
        editTexts[11]=(EditText)findViewById(R.id.etBodegaFacturas);
        editTexts[12]=(EditText)findViewById(R.id.etNCajaN);
        editTexts[13]=(EditText)findViewById(R.id.etBodegaAdmInvOmision);
        editTexts[14]=(EditText)findViewById(R.id.etBodegaTranslados);
		editTexts[23]=(EditText)findViewById(R.id.etBodegaRemision);



        editTexts[10].setOnClickListener(this);
        editTexts[11].setOnClickListener(this);
        editTexts[13].setOnClickListener(this); 
        editTexts[14].setOnClickListener(this);
		editTexts[23].setOnClickListener(this);

		editTexts[10].setKeyListener(null);
    	editTexts[11].setKeyListener(null);
    	editTexts[13].setKeyListener(null); 
    	editTexts[14].setKeyListener(null);
		editTexts[23].setKeyListener(null);



		editTexts[15]=(EditText)findViewById(R.id.etRazonSocial);
    	editTexts[16]=(EditText)findViewById(R.id.etRepresentante);
    	editTexts[17]=(EditText)findViewById(R.id.etRegimenNit);
    	editTexts[18]=(EditText)findViewById(R.id.etDireccionTel);
    	editTexts[19]=(EditText)findViewById(R.id.etResDian);
    	editTexts[20]=(EditText)findViewById(R.id.etRango);
    	editTexts[21]=(EditText)findViewById(R.id.etNombreVendedor);
    	editTexts[22]=(EditText)findViewById(R.id.etPrefijo);











    	
    	
    	llFechaActualizacion=(LinearLayout)findViewById(R.id.llFechaActualizacion);
    	llModificaStock=(LinearLayout)findViewById(R.id.llModificaStock);
    	llBodegaInventario=(LinearLayout)findViewById(R.id.llBodegaInventario);
    	llBodegaPedidos=(LinearLayout)findViewById(R.id.llBodegaPedidos);

		llBodegaRemision =(LinearLayout)findViewById(R.id.llBodegaRemision);

    	llBodegaFactura=(LinearLayout)findViewById(R.id.llBodegaFactura);
    	llNCaja=(LinearLayout)findViewById(R.id.llNCaja);
    	llUsaPrintZebra=(LinearLayout)findViewById(R.id.llUsaPrintZebra);
    	llConnPrintZebra=(LinearLayout)findViewById(R.id.llConnPrintZebra);
    	llBodegaTranslados=(LinearLayout)findViewById(R.id.llBodegaTranslados);
    	llConsultaZ=(LinearLayout)findViewById(R.id.llConsultaZ);
    	llGeneraCierre=(LinearLayout)findViewById(R.id.llGeneraCierre);
    	llIpCash=(LinearLayout)findViewById(R.id.llIpCash);
    	llIpPosstar=(LinearLayout)findViewById(R.id.llIpPosstar);
    	llStocken0=(LinearLayout)findViewById(R.id.llStocken0);
    	llFacturaOnline=(LinearLayout)findViewById(R.id.llFacturaOnline);
    	llRazonSocial=(LinearLayout)findViewById(R.id.llRazonSocial);
    	llRepresentante=(LinearLayout)findViewById(R.id.llRepresentante);
    	llRegimenNit=(LinearLayout)findViewById(R.id.llRegimenNit);
    	llDireccionTel=(LinearLayout)findViewById(R.id.llDireccionTel);
    	llResDian=(LinearLayout)findViewById(R.id.llResDian);
    	llRango=(LinearLayout)findViewById(R.id.llRango);
    	llNombreVendedor=(LinearLayout)findViewById(R.id.llNombreVendedor);
    	llPrefijo=(LinearLayout)findViewById(R.id.llPrefijo);
		llConnPrintEpson=(LinearLayout)findViewById(R.id.llConnPrintEpson);
		llUsaPrintEpson=(LinearLayout)findViewById(R.id.llUsaPrintEpson);

		llConnPrintBixolon=(LinearLayout)findViewById(R.id.llConnPrintBixolon);
		llUsaPrintBixolon=(LinearLayout)findViewById(R.id.llUsaPrintBixolon);

		llConnPrintDigitalPos=(LinearLayout)findViewById(R.id.llConnPrintDigitalPos);
		llUsaPrintDigitalPos=(LinearLayout)findViewById(R.id.llUsaPrintDigitalPos);

		llCarteraOnline=(LinearLayout)findViewById(R.id.llCarteraOnline);




    	
        cbModifica=(CheckBox)findViewById(R.id.cbModifica);
        cbPrecioLibre=(CheckBox)findViewById(R.id.cbPrecioLibre);
		cbControlaPrecioLibre=(CheckBox)findViewById(R.id.cbControlaPrecioLibre);
		cbSelectDocumentoPedido=(CheckBox)findViewById(R.id.cbSelectDocumentoPedido);
		cbRealizaAlistamiento=(CheckBox)findViewById(R.id.cbRealizaAlistamiento);
		cbSelectFormaPagoPedido=(CheckBox)findViewById(R.id.cbSelectFormaPagoPedido);
		cbUsaPrestamos=(CheckBox)findViewById(R.id.cbUsaPrestamos);




        cbUsaPrecio123=(CheckBox)findViewById(R.id.cbUsaPrecio123);
        cbAdministraInventario=(CheckBox)findViewById(R.id.cbAdministraInventario);
        cbConsultaArtOnline=(CheckBox)findViewById(R.id.cbConsultaArtOnline);
        cbStocken0=(CheckBox)findViewById(R.id.cbStocken0);
        cbFactura=(CheckBox)findViewById(R.id.cbFactura);
        cbRealizaPedidos=(CheckBox)findViewById(R.id.cbRealizaPedidos);

		cbRealizaRemision=(CheckBox)findViewById(R.id.cbRealizaRemision);


        cbUsaCatalogo=(CheckBox)findViewById(R.id.cbUsaCatalogo);
        cbModificaStock=(CheckBox)findViewById(R.id.cbModificaStock);
        cbUsaPrintCebra=(CheckBox)findViewById(R.id.cbUsaPrintCebra);
        cbRealizaTranslados=(CheckBox)findViewById(R.id.cbRealizaTranslados);
        cbConsultaZ=(CheckBox)findViewById(R.id.cbConsultaZ);
        cbGeneraCierre=(CheckBox)findViewById(R.id.cbGeneraCierre);
        cbWs=(CheckBox)findViewById(R.id.cbWs);
        cbRealizaPedidosMesa=(CheckBox)findViewById(R.id.cbRealizaPedidosMesa);
        cbUsaTodasLasCategorias=(CheckBox)findViewById(R.id.cbUsaTodasLasCategorias);
        cbFacturaOnline=(CheckBox)findViewById(R.id.cbFacturaOnline);
		cbUsaObservMasMenos=(CheckBox)findViewById(R.id.cbUsaObservMasMenos);
		cbDescuentoPedido=(CheckBox)findViewById(R.id.cbDescuentoPedido);
		cbImprimePedido=(CheckBox)findViewById(R.id.cbImprimePedido);

		cbDescuentaStockEnPedido=(CheckBox)findViewById(R.id.cbDescuentaStockEnPedido);

		cbUsaTipoPedido=(CheckBox)findViewById(R.id.cbUsaTipoPedido);


		cbConsultaCosto=(CheckBox)findViewById(R.id.cbConsultaCosto);
		cbUsaPrintEpson=(CheckBox)findViewById(R.id.cbUsaPrintEpson);

		cbUsaPrintBixolon=(CheckBox)findViewById(R.id.cbUsaPrintBixolon);

		cbUsaPrintDigitalPos=(CheckBox)findViewById(R.id.cbUsaPrintDigitalPos);

		cbCarteraOnline=(CheckBox)findViewById(R.id.cbCarteraOnline);

		cbUsaCantDecimal=(CheckBox)findViewById(R.id.cbUsaCantDecimal);
		cbModificaValorTotal=(CheckBox)findViewById(R.id.cbModificaValorTotal);


		cbUsaSelecMultipleArt=(CheckBox)findViewById(R.id.cbUsaSelecMultipleArt);
		spPrecioMinimo=(Spinner)findViewById(R.id.spPrecioMinimo);


        
        btGuardar=(Button)findViewById(R.id.btGuardar);  
        btCategorias=(Button)findViewById(R.id.btCategorias); 
        btCargarXml=(Button)findViewById(R.id.btCargarXml); 
        btConnPrint=(Button)findViewById(R.id.btConnPrint);
		btConnPrintEpson=(Button)findViewById(R.id.btConnPrintEpson);
		btConnPrintBixolon=(Button)findViewById(R.id.btConnPrintBixolon);
		btConnPrintDigitalPos=(Button)findViewById(R.id.btConnPrintDigitalPos);


        
   
        cbModifica.setChecked(false);
        cbPrecioLibre.setChecked(false);
		cbControlaPrecioLibre.setChecked(false);
		cbSelectDocumentoPedido.setChecked(false);
		cbRealizaAlistamiento.setChecked(false);
		cbSelectFormaPagoPedido.setChecked(false);
		cbUsaPrestamos.setChecked(false);
        cbUsaPrecio123.setChecked(false);   
        cbAdministraInventario.setChecked(false);   
        cbConsultaArtOnline.setChecked(false);   
        cbFactura.setChecked(false);  
        cbUsaCatalogo.setChecked(false); 
        cbWs.setChecked(true);
        cbRealizaPedidosMesa.setChecked(false);
        cbUsaTodasLasCategorias.setChecked(false);
        cbStocken0.setChecked(false);
        cbFacturaOnline.setChecked(false);
		cbUsaObservMasMenos.setChecked(false);
		cbDescuentoPedido.setChecked(false);
		cbImprimePedido.setChecked(false);

		cbDescuentaStockEnPedido.setChecked(false);
		cbUsaTipoPedido.setChecked(false);

		cbConsultaCosto.setChecked(false);
		cbCarteraOnline.setChecked(false);

		cbUsaCantDecimal.setChecked(false);
		cbModificaValorTotal.setChecked(false);

		cbUsaSelecMultipleArt.setChecked(false);
		// Spinner click listener
		spPrecioMinimo.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		List<String> listaprecios = new ArrayList<String>();
		listaprecios.add("1");
		listaprecios.add("2");
		listaprecios.add("3");
		listaprecios.add("4");
		listaprecios.add("5");
		listaprecios.add("6");

		// Creating adapter for spinner
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaprecios);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spPrecioMinimo.setAdapter(dataAdapter);
        
        cargarDatos();  
        btConnPrint.setOnClickListener(new OnClickListener() {
        	/**
        	 * metodo que se ejecuta al hacer click en el boton guardar
        	 */
	        	public void onClick(View v) 
	        	{
	        		printerItems = new ArrayList<DiscoveredPrinter>();
	          	    printerSettings = new ArrayList<Map<String, String>>();	            	
	          	    pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Conectando.."), true,false);
 		   			getMac();
	        		
				}				
		});

		btConnPrintEpson.setOnClickListener(new OnClickListener() {
			/**
			 * metodo que se ejecuta al hacer click en el boton guardar
			 */
			public void onClick(View v)
			{
				printerItems = new ArrayList<DiscoveredPrinter>();
				printerSettings = new ArrayList<Map<String, String>>();
				pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Conectando.."), true,false);
				getMac();

			}
		});

		btConnPrintBixolon.setOnClickListener(new OnClickListener() {
			/**
			 * metodo que se ejecuta al hacer click en el boton guardar
			 */
			public void onClick(View v)
			{
				printerItems = new ArrayList<DiscoveredPrinter>();
				printerSettings = new ArrayList<Map<String, String>>();
				pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Conectando.."), true,false);
				getMac();

			}
		});

		btConnPrintDigitalPos.setOnClickListener(new OnClickListener() {
			/**
			 * metodo que se ejecuta al hacer click en el boton guardar
			 */
			public void onClick(View v)
			{
				printerItems = new ArrayList<DiscoveredPrinter>();
				printerSettings = new ArrayList<Map<String, String>>();
				pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Conectando.."), true,false);
				getMac();

			}
		});
      
        btGuardar.setOnClickListener(new OnClickListener() {
        	/**
        	 * metodo que se ejecuta al hacer click en el boton guardar
        	 */
	        	public void onClick(View v) 
	        	{
	        		getDatos();
	        		if(bd.ActualizarParametros(parametrosPos) & bd.ActualizarParametros(parametrosSys)&bd.ActualizarCategorias(listaCategorias))
	        		{
	        			mostrarMensaje("Parametros Actualizados Correctamente", "l");
	        			Intent i = new Intent();     							  
	  		            setResult( RES, i );              
	  		            finish();        		
	        		}	        		   			
				}				
		});
        btCargarXml.setOnClickListener(new OnClickListener() {
        	/**
        	 * metodo que se ejecuta al hacer click en el boton guardar
        	 */
	        	public void onClick(View v) 
	        	{
	        		     		
 	            
	        		 try {	  
//	        			 	pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Guardando Articulos"), true,false);
	     	        	    File file = new File(Environment.getExternalStorageDirectory()+"/possmovil/articulos.xml");
	     	        	   InputStream is = new FileInputStream(file.getPath());
	     		            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	     		            DocumentBuilder db = dbf.newDocumentBuilder();
	     		            Document doc = db.parse(new InputSource(is));
	     		            
//	     		            doc.getDocumentElement().normalize();

	     		            NodeList nodeList = doc.getElementsByTagName("categoria");
	     		            listaCategoriasXml=new ArrayList<Categoria>();
	     		            int cat=nodeList.getLength();
	     		            for (int i = 0; i < cat; i++) {
	     		            	
	     		 	           Element element = (Element) nodeList.item(i);
	     		 	           
	     		 	           Categoria categoria =new Categoria();
	     		 	           for (int j = 0; j < categoria.getPropertyCount(); j++) {
	     		 	        	   NodeList name = element.getElementsByTagName(categoria.getPropertyName(j));
	     		 		           Element line = (Element) name.item(0);
	     		 		           categoria.setProperty(j,getCharacterDataFromElement(line));		         		
	     		 	           }
	     		 	          listaCategoriasXml.add(categoria);
	     		 	        }
	     		           
	     		            NodeList nodeListArt = doc.getElementsByTagName("ClsArticulo");
	     		            listaArticulos=new ArrayList<Articulo>();
	     		            int total=nodeListArt.getLength()-1;
	     		            for (int i = 0; i < total ; i++) {
	     		            	
	     		            	
	     		 	           Element element = (Element) nodeListArt.item(i);
	     		 	         
	     		 	           articulo=new Articulo();
	     		 	           for (int j = 0; j < articulo.getPropertyCount(); j++) {
	     		 	        	   if(j==14)
	     		 	        	   {
	     		 	        		   	NodeList name = element.getElementsByTagName(articulo.getPropertyName(j));	 	        		
	     		 	 		          	Element line = (Element) name.item(0);
	     		 	 		          	if(line!=null)
	     		 	 		          	{	 	 	
	     		 	 		          	
	     				 	 		          	NodeList stringList = line.getElementsByTagName("string");
	     				 	 		         	int tam=stringList.getLength()-1;	
	     						 	 		       for (int k = 0; k < tam; ++k)
	     						 	 		        {
	     						 	 		    	 
	     						 	 		            Element condition = (Element) stringList.item(k);
	     						 	 		            articulo.getCodigos().add(condition.getFirstChild().getNodeValue());
	     						 	 		        }	
	     		 	 		          	}	 	 		       
	     		 	        	   }
	     		 	        	   else
	     		 	        	   {
	     		 	        		
	     		 	        	   NodeList name = element.getElementsByTagName(articulo.getPropertyName(j));
	     	 	 		           Element line = (Element) name.item(0); 	 		          
	     	 	 		           articulo.setProperty(j,getCharacterDataFromElement(line)); 	 		       
	     		 	        	   }
	     		 	        	   res=articulo.getNombre()+" "+articulo.getPropertyName(j);
	     		 	           }
	     		 	          listaArticulos.add(articulo);
	     		            }
	        	            bd.openDB();
	        	            bd.ActualizarCategorias(listaCategoriasXml);
	        	            bd.insertArticulo(listaArticulos);
	        	            bd.closeDB();
//	        	            pdu.dismiss();
	        	            mostrarMensaje("Articulos guardados correctamente", "l");
	        	            
	        	        } catch (Exception e) {
//	        	        	pdu.dismiss();
	        	        	mostrarMensaje("No se encontro el archivo xml","l");
	        	        	
//	        	            System.out.println("XML Pasing Excpetion = "+ e);
	        	        } 	
	        		
				}				
		});
        btCategorias.setOnClickListener(new OnClickListener() {
        	/**
        	 * metodo que se ejecuta al hacer click en el boton guardar
        	 */
	        	public void onClick(View v) 
	        	{
	        		new getCategorias().execute("");
	        		pdu=ProgressDialog.show(ConfiguracionActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Actualizando Categorias"), true,false);
	        	    		   			
				}				
		});
        
        cbConsultaArtOnline.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llFechaActualizacion);
		    	validCheck(!isChecked);
		    	views.add(llStocken0);
		    	validCheck(isChecked);
			}
		});
    	
        cbFactura.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llBodegaFactura);
		    	views.add(llNCaja);
		      	views.add(llUsaPrintZebra);
		    	views.add(llConnPrintZebra);
				views.add(llUsaPrintEpson);
				views.add(llConnPrintEpson);
				views.add(llUsaPrintBixolon);
				views.add(llConnPrintBixolon);

				views.add(llUsaPrintDigitalPos);
				views.add(llConnPrintDigitalPos);

		     	views.add(llConsultaZ);
		    	views.add(llGeneraCierre);
		    	
		    	
		    	views.add(llFacturaOnline);		    	
		    	views.add(llRazonSocial);
	    		views.add(llRepresentante);
	    		views.add(llRegimenNit);
	    		views.add(llDireccionTel);
	    		views.add(llResDian);
	    		views.add(llRango);
	    		views.add(llNombreVendedor);
	    		views.add(llPrefijo);


	    		
		    	
		      	validCheck(isChecked);
		    	if(isChecked)
		    	{
		    		views.add(llConnPrintZebra);
		    		validCheck(cbUsaPrintCebra.isChecked());
					views.add(llConnPrintEpson);
					validCheck(cbUsaPrintEpson.isChecked());
					views.add(llConnPrintBixolon);
					validCheck(cbUsaPrintBixolon.isChecked());

					views.add(llConnPrintDigitalPos);
					validCheck(cbUsaPrintDigitalPos.isChecked());

		    		views.add(llGeneraCierre);
		    		validCheck(cbConsultaZ.isChecked());
		    		
		    		
		    		views.add(llRazonSocial);
		    		views.add(llRepresentante);
		    		views.add(llRegimenNit);
		    		views.add(llDireccionTel);
		    		views.add(llResDian);
		    		views.add(llRango);
		    		views.add(llNombreVendedor);
		    		views.add(llPrefijo);
		    		validCheck(!cbFacturaOnline.isChecked());
		    	}  
			}
		});
		cbRealizaRemision.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llBodegaRemision);
				validCheck(cbRealizaRemision.isChecked());
			}
		});

        cbRealizaPedidos.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llBodegaPedidos);
		    	validCheck(cbRealizaPedidos.isChecked());				
			}
		});
        cbAdministraInventario.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llModificaStock);
		    	views.add(llBodegaInventario);
		    	validCheck(isChecked);
				
			
			}
		});
        cbRealizaTranslados.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llBodegaTranslados);
		    	validCheck(isChecked); 
			}
		});
        cbUsaPrintCebra.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llConnPrintZebra);
		    	validCheck(isChecked); 
			}
		});
		cbUsaPrintEpson.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llConnPrintEpson);
				validCheck(isChecked);
			}
		});
		cbUsaPrintBixolon.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llConnPrintBixolon);
				validCheck(isChecked);
			}
		});

		cbUsaPrintDigitalPos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				views.add(llConnPrintDigitalPos);
				validCheck(isChecked);
			}
		});

		cbConsultaZ.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
     			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
     				views.add(llGeneraCierre);
     		    	validCheck(isChecked); 
     			}
     		}); 
        cbFacturaOnline.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
 			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
 				views.add(llRazonSocial);
	    		views.add(llRepresentante);
	    		views.add(llRegimenNit);
	    		views.add(llDireccionTel);
	    		views.add(llResDian);
	    		views.add(llRango);
	    		views.add(llNombreVendedor);	
	    		views.add(llPrefijo);
 		    	validCheck(!isChecked); 
 			}
 		});
        
        
        cbWs.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
 			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
 					views.add(tvIPInfo);
 					views.add(llIpPosstar);
 					validCheck(isChecked);
 					
 					views.add(tvIpSys);
 					views.add(llIpCash);
 					validCheck(!isChecked); 						
 			}
 		});






	}
    public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?";
	  }

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	private class getCategorias extends AsyncTask<String, Void, Object>
   	{
   		String  res ="";		
   		@Override
   			protected Integer doInBackground(String... params) 
   			{		
   			if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
			{
					GetCategorias getCategorias=new GetCategorias(parametrosPos.getIp());
					getCategorias.getCategorias();
					listaCategorias=getCategorias.getListaCategorias();
			}
   			else
   			{
	   				GetCategoriasSys getCategoriasSys=new GetCategoriasSys(parametrosSys.getIp(),parametrosSys.getWebidText());
	   				getCategoriasSys.getCategorias();
					listaCategorias=getCategoriasSys.getListaCategorias();
		
   			}
			if(listaCategorias.size()>0)
			{
				bd.openDB();
				bd.insertCategorias(listaCategorias);									
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
//   					  Intent intent = new Intent(ConfiguracionActivity.this, CategoriaActivity.class );
//   	      			  startActivity(intent);
   					
   					   AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionActivity.this);
   				        builder.setTitle("CATEGORIAS");
   				        
   				        final AlertDialog test = builder.create(); 
   				        
   				        LinearLayout llVertical=new LinearLayout(ConfiguracionActivity.this);
   				        llVertical.setOrientation(LinearLayout.VERTICAL);
   				        LinearLayout llHorizontal2=new LinearLayout(ConfiguracionActivity.this);
   				       
   				        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
   				        llHorizontal2.setLayoutParams(parms);
   					    listView = new ListView(ConfiguracionActivity.this);
   					    listView.setBackgroundColor(Color.WHITE);   		
   					    LinearLayout.LayoutParams parms2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,50);
  				        LinearLayout llHorizontal=new LinearLayout(ConfiguracionActivity.this);
   				        llHorizontal.setOrientation(LinearLayout.VERTICAL);
   				        llHorizontal.setLayoutParams(parms2);
   				        LinearLayout.LayoutParams parms3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			            btAlertOk=new Button(ConfiguracionActivity.this);
			            btAlertOk.setLayoutParams(parms3);
   				        btAlertOk.setText("OK");
   				        btAlertOk.setTextColor(Color.WHITE);
   				        btAlertOk.setTextSize(16);
   				        btAlertOk.setWidth(50);
   				        btAlertOk.setBackgroundResource(R.drawable.red_button);
   				        llHorizontal.addView(btAlertOk);
   				        llHorizontal2.addView(listView); 
   				        llVertical.addView(llHorizontal);
   				        llVertical.addView(llHorizontal2);
   				       
   				        
   				        bd.openDB();
   						listaCategorias=bd.getCategorias(false);									
   						bd.closeDB();
   				        context = getApplicationContext();
   				        listView.setAdapter(new CategoriasAdapter(context,R.layout.activity_item_categoria,listaCategorias));
   						test.setView(llVertical);	
   						
   						
   					    listView.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
							     listaCategorias.get(position).setHabilidada();
								 bd.openDB();
								 bd.ActualizarCategoria(listaCategorias.get(position));
								 listaCategorias=bd.getCategorias(false);	
								 bd.closeDB();
								 listView.setAdapter(new CategoriasAdapter(context,R.layout.activity_item_categoria,listaCategorias));
																						  
							}
				        });
   				        
   				        btAlertOk.setOnClickListener(new OnClickListener() {
   							
   							public void onClick(View v) {
   								 test.cancel();
   							}
   						});
   				        test.show();						
   						
   				        
   				        
   						
   				}
   				else 
   				{						
   						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
   				}		
   				
   			}
   	}
    /**
     * metodo que se encarga de asignar valores a las cagas de texto de la actividad
     */
    private void cargarDatos() 
    {
    	editTexts[0].setText(parametrosPos.ip1);
    	editTexts[1].setText(parametrosPos.ip2);
    	editTexts[2].setText(parametrosPos.ip3);
    	editTexts[3].setText(parametrosPos.ip4);
    	editTexts[4].setText(parametrosSys.ip1);
    	editTexts[5].setText(parametrosSys.ip2);
    	editTexts[6].setText(parametrosSys.ip3);
    	editTexts[7].setText(parametrosSys.ip4);
    	cbModifica.setChecked(parametrosPos.isModificaPrecio());
    	cbPrecioLibre.setChecked(parametrosPos.isValue(parametrosPos.getPrecioLibre()));
		cbControlaPrecioLibre.setChecked(parametrosPos.isValue(parametrosPos.getControlaPrecioLibre()));
		cbSelectDocumentoPedido.setChecked(parametrosPos.isValue(parametrosPos.getSelectDocumentoPedido()));
		cbRealizaAlistamiento.setChecked(parametrosPos.isValue(parametrosPos.getRealizaAlistamiento()));
		cbSelectFormaPagoPedido.setChecked(parametrosPos.isValue(parametrosPos.getSelectFormaPagoPedido()));
		cbUsaPrestamos.setChecked(parametrosPos.isValue(parametrosPos.getUsaPrestamos()));


    	cbFacturaOnline.setChecked(parametrosPos.isValue(parametrosPos.getFacturaOnLine()));
    	cbUsaCatalogo.setChecked(parametrosPos.isValue(parametrosPos.getUsaCatalogo()));
    	cbUsaPrecio123.setChecked(parametrosPos.isPrecio123());
    	cbConsultaArtOnline.setChecked(parametrosPos.isValue(parametrosPos.getConsultaArticuloEnLinea()));
    	cbStocken0.setChecked(parametrosPos.isValue(parametrosPos.getPermiteStocken0()));

		cbUsaObservMasMenos.setChecked(parametrosPos.isValue(parametrosPos.getUsaObservMasMenos()));
		cbDescuentoPedido.setChecked(parametrosPos.isValue(parametrosPos.getDescuentoPedido()));
		cbImprimePedido.setChecked(parametrosPos.isValue(parametrosPos.getImprimePedido()));

		cbDescuentaStockEnPedido.setChecked(parametrosPos.isValue(parametrosPos.getDescuentaStockEnPedido()));

		cbUsaTipoPedido.setChecked(parametrosPos.isValue(parametrosPos.getUsaTipoPedido()));

		cbConsultaCosto.setChecked(parametrosPos.isValue(parametrosPos.getConsultaCosto()));

		cbUsaCantDecimal.setChecked(parametrosPos.isValue(parametrosPos.getUsaCantDecimal()));

		cbModificaValorTotal.setChecked(parametrosPos.isValue(parametrosPos.getModificaValorTotal()));


		cbUsaSelecMultipleArt.setChecked(parametrosPos.isValue(parametrosPos.getUsaSelecMultipleArt()));

		cbCarteraOnline.setChecked(parametrosPos.isValue(parametrosPos.getCarteraOnLine()));


		int spinnerPosition = dataAdapter.getPosition(""+parametrosPos.getPrecioMinimo());
		spPrecioMinimo.setSelection(spinnerPosition);

    	
    	editTexts[8].setText(parametrosPos.getFechaSys2());
    	views.add(llFechaActualizacion);
    	views.add(llStocken0);    	
    	validCheck(!cbConsultaArtOnline.isChecked());
    	views.add(llStocken0); 
    	validCheck(cbConsultaArtOnline.isChecked());
    	
    	cbAdministraInventario.setChecked(parametrosPos.isValue(parametrosPos.getAdministraInventario()));
    	cbModificaStock.setChecked(parametrosPos.isValue(parametrosPos.getModificaStock()));   
    	editTexts[13].setText(bodegaAdmInv.getBodega());
    	views.add(llModificaStock);
    	views.add(llBodegaInventario);
    	validCheck(cbAdministraInventario.isChecked());


		cbRealizaRemision.setChecked(parametrosPos.isValue(parametrosPos.getRealizaRemision() ));
		editTexts[23].setText(bodegaRemision.getBodega());
		views.add(llBodegaRemision);
		validCheck(cbRealizaRemision.isChecked());



    	
    	cbRealizaPedidos.setChecked(parametrosPos.isValue(parametrosPos.getRealizaPedidos()));
    	editTexts[10].setText(bodegaPedidos.getBodega());
    	views.add(llBodegaPedidos);
    	validCheck(cbRealizaPedidos.isChecked());
    	
    	cbFactura.setChecked(parametrosPos.isValue(parametrosPos.getRealizaFactura()));
    	editTexts[11].setText(bodegaFactura.getBodega()); 
    	editTexts[12].setText(""+parametrosPos.getCaja());  
    	
    	   	
    	
    	editTexts[15].setText(parametrosSys.getRazonSocial());
    	editTexts[16].setText(parametrosSys.getRepresentante());
    	editTexts[17].setText(parametrosSys.getRegimenNit());
    	editTexts[18].setText(parametrosSys.getDireccionTel());
    	editTexts[19].setText(parametrosSys.getResDian());
    	editTexts[20].setText(parametrosSys.getRango());
    	editTexts[21].setText(parametrosSys.getNombreVendedor());
    	editTexts[22].setText(parametrosSys.getPrefijo());
    	
    	
    	cbWs.setChecked(parametrosPos.isValue(parametrosPos.getUsaWSCash()));
    	
    	cbUsaPrintCebra.setChecked(parametrosPos.isValue(parametrosPos.getUsaImpresoraZebra()));
    	cbConsultaZ.setChecked(parametrosPos.isValue(parametrosPos.getConsultaZ()));
    	cbGeneraCierre.setChecked(parametrosPos.isValue(parametrosPos.getGeneraCierre()));
    	cbRealizaPedidosMesa.setChecked(parametrosPos.isValue(parametrosPos.getRealizaPedidosMesa()));
    	cbUsaTodasLasCategorias.setChecked(parametrosPos.isValue(parametrosPos.getUsaTodasLasCategorias()));
		cbUsaPrintEpson.setChecked(parametrosPos.isValue(parametrosPos.getUsaPrintEpson()));
		cbUsaPrintBixolon.setChecked(parametrosPos.isValue(parametrosPos.getUsaPrintBixolon()));

		cbUsaPrintDigitalPos.setChecked(parametrosPos.isValue(parametrosPos.getUsaPrintDigitalPos()));
    	
    	tvMac.setText(parametrosPos.getMacAdd());
		tvMacEpson.setText(parametrosPos.getMacAddEpson());
		tvMacBixolon.setText(parametrosPos.getMacAddBixolon());
		tvMacDigitalPos.setText(parametrosPos.getMacAddDigitalPos());

		etWebid.setText(""+parametrosPos.getWebid2());

    	views.add(llBodegaFactura);
    	views.add(llNCaja);
      	views.add(llUsaPrintZebra);
    	views.add(llConnPrintZebra);
		views.add(llUsaPrintEpson);
		views.add(llConnPrintEpson);
		views.add(llUsaPrintBixolon);
		views.add(llConnPrintBixolon);

		views.add(llUsaPrintDigitalPos);
		views.add(llConnPrintDigitalPos);

    	views.add(llConsultaZ);
    	views.add(llGeneraCierre);

    	views.add(llFacturaOnline);		    	
    	views.add(llRazonSocial);
		views.add(llRepresentante);
		views.add(llRegimenNit);
		views.add(llDireccionTel);
		views.add(llResDian);
		views.add(llRango);
		views.add(llNombreVendedor);
		views.add(llPrefijo);
    	validCheck(cbFactura.isChecked());
    	if(cbFactura.isChecked())
    	{
    		views.add(llConnPrintZebra);
    		validCheck(cbUsaPrintCebra.isChecked());
			views.add(llConnPrintEpson);
			validCheck(cbUsaPrintEpson.isChecked());
			views.add(llConnPrintBixolon);
			validCheck(cbUsaPrintBixolon.isChecked());

			views.add(llConnPrintDigitalPos);
			validCheck(cbUsaPrintDigitalPos.isChecked());

    		views.add(llGeneraCierre);
    		validCheck(cbConsultaZ.isChecked());
    		

    		views.add(llRazonSocial);
    		views.add(llRepresentante);
    		views.add(llRegimenNit);
    		views.add(llDireccionTel);
    		views.add(llResDian);
    		views.add(llRango);
    		views.add(llNombreVendedor);
    		views.add(llPrefijo);
    		validCheck(!cbFacturaOnline.isChecked());
    	}    	
    	cbRealizaTranslados.setChecked(parametrosPos.isValue(parametrosPos.getRealizaTranslados()));
    	editTexts[14].setText(bodegaTranslados.getBodega());
    	editTexts[9].setText(parametrosPos.ruta);
    	
    	views.add(llBodegaTranslados);
    	validCheck(cbRealizaTranslados.isChecked());   
    	
		views.add(tvIPInfo);
		views.add(llIpPosstar);
		validCheck(cbWs.isChecked());
		
		views.add(tvIpSys);
		views.add(llIpCash);
		validCheck(!cbWs.isChecked()); 
		
		    	
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_configuracion, menu);
        return true;
    }
	
	/**
	 * metodo que se encarga de asignar los valores de las cajas de texto a la clase Parametros
	 * para luego ser almacenados en la Base de datos del Telefono
	 */
	private void getDatos()
	{
		try
		{
			String fecha=editTexts[8].getText().toString();
			bd.openDB();
			listaCategorias=bd.getCategorias(true);
			
			for (int i = 0; i < listaCategorias.size(); i++) {
				categoria=listaCategorias.get(i);
				if(!categoria.getFechaAct().equals(parametrosPos.getFechaSys2System()))
				{
					categoria.setFechaAct(fecha);
				}
			}			
			bd.close();
				
			parametrosSys.ip1=editTexts[4].getText().toString();
			parametrosSys.ip2=editTexts[5].getText().toString();
			parametrosSys.ip3=editTexts[6].getText().toString();
			parametrosSys.ip4=editTexts[7].getText().toString();
			parametrosSys.fecha=editTexts[8].getText().toString();
			parametrosSys.ruta=editTexts[9].getText().toString();
			
			parametrosPos.ip1=editTexts[0].getText().toString();
			parametrosPos.ip2=editTexts[1].getText().toString();
			parametrosPos.ip3=editTexts[2].getText().toString();
			parametrosPos.ip4=editTexts[3].getText().toString();
			
			parametrosPos.ruta=editTexts[9].getText().toString();
		
		
			
			
			if(!cbModifica.isChecked()&!cbUsaPrecio123.isChecked())
			{
				parametrosPos.modificaPrecio=0;
				parametrosSys.modificaPrecio=0;
			}
			else if(cbModifica.isChecked()&!cbUsaPrecio123.isChecked())
			{
				parametrosPos.modificaPrecio=1;
				parametrosSys.modificaPrecio=1;
			}
			else if(cbModifica.isChecked()&cbUsaPrecio123.isChecked())
			{
				parametrosPos.modificaPrecio=2;
				parametrosSys.modificaPrecio=2;
			}
			else
			{
				parametrosPos.modificaPrecio=3;
				parametrosSys.modificaPrecio=3;
			}
			//DEFINE PARAMETROS WEB SERVICE POSSTAR
			parametrosPos.setFacturaOnLine(getValueCheck(cbFacturaOnline));

			parametrosPos.setCarteraOnLine(getValueCheck(cbCarteraOnline));
			
			parametrosPos.setUsaObservMasMenos(getValueCheck(cbUsaObservMasMenos));
			parametrosPos.setDescuentoPedido(getValueCheck(cbDescuentoPedido));
			parametrosPos.setImprimePedido(getValueCheck(cbImprimePedido));

			parametrosPos.setDescuentaStockEnPedido(getValueCheck(cbDescuentaStockEnPedido));
			parametrosPos.setUsaTipoPedido(getValueCheck(cbUsaTipoPedido));


			parametrosPos.setConsultaCosto(getValueCheck(cbConsultaCosto));


			parametrosPos.setPrecioLibre(getValueCheck(cbPrecioLibre));
			parametrosPos.setControlaPrecioLibre(getValueCheck(cbControlaPrecioLibre));
			parametrosPos.setSelectDocumentoPedido(getValueCheck(cbSelectDocumentoPedido));
			parametrosPos.setRealizaAlistamiento(getValueCheck(cbRealizaAlistamiento));
			parametrosPos.setSelectFormaPagoPedido(getValueCheck(cbSelectFormaPagoPedido));
			parametrosPos.setUsaPrestamos(getValueCheck(cbUsaPrestamos));




			parametrosPos.setUsaCatalogo(getValueCheck(cbUsaCatalogo));	
			parametrosPos.setConsultaArticuloEnLinea(getValueCheck(cbConsultaArtOnline));
			parametrosPos.setPermiteStocken0(getValueCheck(cbStocken0));		
			parametrosPos.setFechaSys2(fecha);			
			parametrosPos.setAdministraInventario(getValueCheck(cbAdministraInventario));   
			parametrosPos.setBodegaAdmInvOmision(bodegaAdmInv.getIdBodega());
			parametrosPos.setModificaStock(getValueCheck(cbModificaStock));	
			parametrosPos.setRealizaPedidos(getValueCheck(cbRealizaPedidos));
			parametrosPos.setBodegaPedidosOmision(bodegaPedidos.getIdBodega());

			parametrosPos.setRealizaRemision(getValueCheck(cbRealizaRemision)) ;
			parametrosPos.setBodegaRemisionOmision(bodegaRemision.getIdBodega());


			parametrosPos.setRealizaFactura(getValueCheck(cbFactura));	
			parametrosPos.setBodegaFacturaOmision(bodegaFactura.getIdBodega());
			parametrosPos.caja=Long.parseLong(editTexts[12].getText().toString());
			parametrosPos.setUsaImpresoraZebra(getValueCheck(cbUsaPrintCebra));
			parametrosPos.setMacAdd(tvMac.getText().toString());
			parametrosPos.setRealizaTranslados(getValueCheck(cbRealizaTranslados));
			parametrosPos.setBodegaTransladosOmision(bodegaTranslados.getIdBodega());
			parametrosPos.setConsultaZ(getValueCheck(cbConsultaZ));
			parametrosPos.setGeneraCierre(getValueCheck(cbGeneraCierre));
			parametrosPos.setUsaWSCash(getValueCheck(cbWs));
			parametrosPos.setRealizaPedidosMesa(getValueCheck(cbRealizaPedidosMesa));
			parametrosPos.setUsaTodasLasCategorias(getValueCheck(cbUsaTodasLasCategorias));

			parametrosPos.setUsaPrintEpson(getValueCheck(cbUsaPrintEpson));
			parametrosPos.setMacAddEpson(tvMacEpson.getText().toString());

			parametrosPos.setUsaPrintBixolon(getValueCheck(cbUsaPrintBixolon));
			parametrosPos.setMacAddBixolon(tvMacBixolon.getText().toString());

			parametrosPos.setUsaPrintDigitalPos(getValueCheck(cbUsaPrintDigitalPos));
			parametrosPos.setMacAddDigitalPos(tvMacDigitalPos.getText().toString());

			parametrosPos.setWebid(getWebId(etWebid.getText().toString()));

			
			parametrosPos.setRazonSocial(editTexts[15].getText().toString());
			parametrosPos.setRepresentante(editTexts[16].getText().toString());
			parametrosPos.setRegimenNit(editTexts[17].getText().toString());
			parametrosPos.setDireccionTel(editTexts[18].getText().toString());
			parametrosPos.setResDian(editTexts[19].getText().toString());
			parametrosPos.setRango(editTexts[20].getText().toString());
			parametrosPos.setNombreVendedor(editTexts[21].getText().toString());
			parametrosPos.setPrefijo(editTexts[22].getText().toString());

			parametrosPos.setUsaCantDecimal(getValueCheck(cbUsaCantDecimal)) ;

			parametrosPos.setModificaValorTotal(getValueCheck(cbModificaValorTotal)); ;



			parametrosPos.setUsaSelecMultipleArt(getValueCheck(cbUsaSelecMultipleArt));
			parametrosPos.setPrecioMinimo((Long.parseLong(spPrecioMinimo.getSelectedItem().toString())));


			
			
			//DEFINE PARAMETROS WEB SERVICE SYS
			parametrosSys.setUsaObservMasMenos(getValueCheck(cbUsaObservMasMenos));
			parametrosSys.setDescuentoPedido(getValueCheck(cbDescuentoPedido));
			parametrosSys.setImprimePedido(getValueCheck(cbImprimePedido));


			parametrosSys.setDescuentaStockEnPedido(getValueCheck(cbDescuentaStockEnPedido));
			parametrosSys.setUsaTipoPedido(getValueCheck(cbUsaTipoPedido));

			parametrosSys.setConsultaCosto(getValueCheck(cbConsultaCosto));

			parametrosSys.setPrecioLibre(getValueCheck(cbPrecioLibre));
			parametrosSys.setControlaPrecioLibre(getValueCheck(cbControlaPrecioLibre));
			parametrosSys.setSelectDocumentoPedido(getValueCheck(cbSelectDocumentoPedido));
			parametrosSys.setRealizaAlistamiento(getValueCheck(cbRealizaAlistamiento));
			parametrosSys.setSelectFormaPagoPedido(getValueCheck(cbSelectFormaPagoPedido));
			parametrosSys.setUsaPrestamos(getValueCheck(cbUsaPrestamos));



			parametrosSys.setFacturaOnLine(getValueCheck(cbFacturaOnline));
			parametrosSys.setCarteraOnLine(getValueCheck(cbCarteraOnline));
			parametrosSys.setUsaCatalogo(getValueCheck(cbUsaCatalogo));	
			parametrosSys.setConsultaArticuloEnLinea(getValueCheck(cbConsultaArtOnline));
			parametrosSys.setPermiteStocken0(getValueCheck(cbStocken0));			
			parametrosSys.setFechaSys2(fecha);			
			parametrosSys.setAdministraInventario(getValueCheck(cbAdministraInventario));   
			parametrosSys.setBodegaAdmInvOmision(bodegaAdmInv.getIdBodega());
			parametrosSys.setModificaStock(getValueCheck(cbModificaStock));	
			parametrosSys.setRealizaPedidos(getValueCheck(cbRealizaPedidos));
			parametrosSys.setBodegaPedidosOmision(bodegaPedidos.getIdBodega());

			parametrosSys.setRealizaRemision(getValueCheck(cbRealizaRemision));
			parametrosSys.setBodegaRemisionOmision(bodegaRemision.getIdBodega());


			parametrosSys.setRealizaFactura(getValueCheck(cbFactura));	
			parametrosSys.setBodegaFacturaOmision(bodegaFactura.getIdBodega());
			parametrosSys.caja=Long.parseLong(editTexts[12].getText().toString());
			parametrosSys.setUsaImpresoraZebra(getValueCheck(cbUsaPrintCebra));
			parametrosSys.setMacAdd(tvMac.getText().toString());
			parametrosSys.setRealizaTranslados(getValueCheck(cbRealizaTranslados));
			parametrosSys.setBodegaTransladosOmision(bodegaTranslados.getIdBodega());
			parametrosSys.setConsultaZ(getValueCheck(cbConsultaZ));
			parametrosSys.setGeneraCierre(getValueCheck(cbGeneraCierre));
			parametrosSys.setUsaWSCash(getValueCheck(cbWs));
			parametrosSys.setRealizaPedidosMesa(getValueCheck(cbRealizaPedidosMesa));
			parametrosSys.setUsaTodasLasCategorias(getValueCheck(cbUsaTodasLasCategorias));
			parametrosSys.setMacAddEpson(tvMacEpson.getText().toString());
			parametrosSys.setMacAddBixolon(tvMacBixolon.getText().toString());
			parametrosSys.setMacAddDigitalPos(tvMacDigitalPos.getText().toString());
			parametrosSys.setWebid(getWebId(etWebid.getText().toString()));
			
			parametrosSys.setRazonSocial(editTexts[15].getText().toString());
			parametrosSys.setRepresentante(editTexts[16].getText().toString());
			parametrosSys.setRegimenNit(editTexts[17].getText().toString());
			parametrosSys.setDireccionTel(editTexts[18].getText().toString());
			parametrosSys.setResDian(editTexts[19].getText().toString());
			parametrosSys.setRango(editTexts[20].getText().toString());
			parametrosSys.setNombreVendedor(editTexts[21].getText().toString());
			parametrosSys.setPrefijo(editTexts[22].getText().toString());

			parametrosSys.setUsaCantDecimal(getValueCheck(cbUsaCantDecimal)) ;
			parametrosSys.setModificaValorTotal(getValueCheck(cbModificaValorTotal)); ;


			parametrosSys.setUsaSelecMultipleArt(getValueCheck(cbUsaSelecMultipleArt));
			parametrosSys.setPrecioMinimo((Long.parseLong(spPrecioMinimo.getSelectedItem().toString())));
			
			
			
		}
		catch(Exception e)
		{
			mostrarMensaje("Debe Ingresar valores Requeridos", "l");
			mostrarMensaje("Verifique los datos", "s");
		}
		
	}
	private long getValueCheck(CheckBox value)
	{
		if(value.isChecked())
		{
			return 1;
		}
		return 0;
	}
	
	private long getValueCheckInverse(CheckBox value)
	{
		if(value.isChecked())
		{
			return 0;
		}
		return 1;
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
	 * metodo encargado de dar estilo de letra etiquetas

	 */
	private class getBodegas extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{	
				if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
				{
					getBodega=new GetBodega(parametrosPos.getIp());			
					res=getBodega.getBodega();
					
				}
				else
				{
					getBodegaSys=new GetBodegaSys(parametrosSys.getIp(),parametrosSys.getWebidText());
					res=getBodegaSys.getBodega();
				}
			return 1;
			}
		
		
			protected void onPostExecute(Object result)
			{
				pdu.dismiss();
				if(res.equals("true"))
				{
					if(parametrosPos.isValue(parametrosPos.getUsaWSCash()))
					{
						listaBodegas=getBodega.getListaBodegas();
					}
					else
					{
						listaBodegas=getBodegaSys.getListaBodegas();
					}
					bd.insertBodega(listaBodegas);
					listaBodegas=bd.getBodegas(ConfiguracionActivity.this);					
				}
				else if(res.equals("false"))
				{
					mostrarMensaje("No fue Posible actualizar las bodegas", "l");									
				}
				else
				{
					mostrarMensaje("No fue posible establecer la conexion con el servidor", "l");
					
				}
				seleccionarBodega();
			 }
			}
	public void onClick(View view) {
		  if(editTexts[10].equals(view))
		  {
			  mbodegaPedidos=1;
			  new getBodegas().execute("");
				pdu=ProgressDialog.show(this,"Por Favor Espere", "Actualizando Bodegas", true,false);
		  }
		  else if(editTexts[11].equals(view))
		  {
			  mbodegaPedidos=2;
			  new getBodegas().execute("");
				pdu=ProgressDialog.show(this,"Por Favor Espere", "Actualizando Bodegas", true,false);
		  }
		  if(editTexts[13].equals(view))
		  {
			  mbodegaPedidos=3;
			  new getBodegas().execute("");
				pdu=ProgressDialog.show(this,"Por Favor Espere", "Actualizando Bodegas", true,false);
		  }
		  if(editTexts[14].equals(view))
		  {
			  mbodegaPedidos=4;
			  //LIMPIA FACTURAS
			  bd.limpiaFacturas();
			  //new getBodegas().execute("");
				//pdu=ProgressDialog.show(this,"Por Favor Espere", "Actualizando Bodegas", true,false);
		  }
		if(editTexts[23].equals(view))
		{
			mbodegaPedidos=5;
			new getBodegas().execute("");
			pdu=ProgressDialog.show(this,"Por Favor Espere", "Actualizando Bodegas", true,false);
		}
		  
		
	}
	private void seleccionarBodega()
	{
		try {
			opciones = new Opciones[listaBodegas.size()];
			for (int i = 0; i < listaBodegas.size(); i++) {
				Bodega b = listaBodegas.get(i);
				opciones[i] = new Opciones(b.getIdBodega(), b.getBodega(), getImg(R.drawable.pedidos), b.getBodega());
			}
			ListAdapter listaMotivos = new OpcionesAdapter(ConfiguracionActivity.this, opciones);
			AlertDialog.Builder builderMotivo = new AlertDialog.Builder(ConfiguracionActivity.this);
			builderMotivo.setTitle("Seleccione la Bodega");
			builderMotivo.setSingleChoiceItems(listaMotivos, -1, new DialogInterface.OnClickListener() {
						//
						public void onClick(DialogInterface dialogMotivo, int itemMotivo) {
							if (mbodegaPedidos == 1) {
								bodegaPedidos = listaBodegas.get(itemMotivo);
								editTexts[10].setText(bodegaPedidos.getBodega());
							} else if (mbodegaPedidos == 2) {
								bodegaFactura = listaBodegas.get(itemMotivo);
								editTexts[11].setText(bodegaFactura.getBodega());
							} else if (mbodegaPedidos == 3) {
								bodegaAdmInv = listaBodegas.get(itemMotivo);
								editTexts[13].setText(bodegaAdmInv.getBodega());
							} else if (mbodegaPedidos == 4) {
								bodegaTranslados = listaBodegas.get(itemMotivo);
								editTexts[14].setText(bodegaTranslados.getBodega());
							} else if (mbodegaPedidos == 5) {
								bodegaRemision = listaBodegas.get(itemMotivo);
								editTexts[23].setText(bodegaRemision.getBodega());
							}
							mbodegaPedidos = 0;
							dialogMotivo.cancel();
						}
					}
			);
			AlertDialog alert = builderMotivo.create();
			alert.show();
		}
		catch (Exception e )
		{
			mostrarMensaje(e.toString(),"l");
		}
	}
	
	private Drawable getImg( int res )
	{
		Drawable img = getResources().getDrawable( res );
		img.setBounds( 0, 0, 45, 45 );
		return img;
	}
	private void validCheck(boolean isChecked)
	{
		int visibility=View.VISIBLE;
		if(!isChecked)
		{
			visibility=View.GONE;
		}
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setVisibility(visibility);
		}	
		views.clear();
	}
	public void discoveryError(String text) {
//		mostrarMensaje(text, "l");
		pdu.dismiss();
    	mostrarMensaje(text, "l");
	}



	public void discoveryFinished() {
		pdu.dismiss();	
    	selectDevice();	 
	}



	public void foundPrinter(final DiscoveredPrinter p) {
		 runOnUiThread(new Runnable() {
	            public void run() {	            	
	            	printerItems.add(p);
	                printerSettings.add(p.getDiscoveryDataMap());
	                            
	            }
	       });
		
		
	}
	
	private void selectDevice()
	{
		opciones=new Opciones[printerItems.size()];
		for (int i = 0; i < printerItems.size(); i++) {		
			   String macAdd=((DiscoveredPrinterBluetooth) printerItems.get(i)).address.trim();
			   String name=((DiscoveredPrinterBluetooth) printerItems.get(i)).friendlyName.trim();
               opciones[i]=new Opciones(i,name , getImg(R.drawable.pedidos), macAdd);
		}
		ListAdapter listaMotivos = new OpcionesAdapter(ConfiguracionActivity.this, opciones);  				        		  		
        AlertDialog.Builder builderMotivo = new AlertDialog.Builder(ConfiguracionActivity.this);
        builderMotivo.setTitle("Seleccione la Impresora");
        builderMotivo.setSingleChoiceItems(listaMotivos, -1, new DialogInterface.OnClickListener() {
//
  			    public void onClick(DialogInterface dialogMotivo, int itemMotivo) {	      			    
  			        String macAdd=opciones[itemMotivo].getVal().trim();
	                tvMac.setText(macAdd);
					tvMacEpson.setText(macAdd);
					tvMacBixolon.setText(macAdd);
					tvMacDigitalPos.setText(macAdd);
  			    	dialogMotivo.cancel();
  			    }
  		}
        );
  	AlertDialog alert = builderMotivo.create();
  	alert.show();
	}

	 private void getMac()
	    {
	        new Thread(new Runnable() {
	            public void run() {
	                Looper.prepare();
	                try {
	                    BluetoothDiscoverer.findPrinters(ConfiguracionActivity.this, ConfiguracionActivity.this);
	                } catch (ConnectionException e) {
	                	pdu.dismiss();
	                	 mostrarMensaje("Verifique que el Bluetooth del telefono este encendido 1", "l");
//	                    new UIHelper(PruebaZebraActivity.this).showErrorDialogOnGuiThread(e.getMessage());
	                } finally {
	                    Looper.myLooper().quit();
	                    mostrarMensaje("Verifique que el Bluetooth del telefono este encendido 2", "l");
	                }
	            }
	        }).start();
	    }

	    private Long getWebId (String text)
		{
			long res=0;
			try
			{
				res=Long.parseLong(text);
			}
			catch (Exception e )
			{
				res=0;
			}
			return res;
		}
}
