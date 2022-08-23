package com.principal.persistencia;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.ArticulosTraslado;
import com.principal.mundo.Bodega;
import com.principal.mundo.Catalogo;
import com.principal.mundo.Categoria;
import com.principal.mundo.CierreTurno;
import com.principal.mundo.Cliente;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.MediosDePago;
import com.principal.mundo.Municipio;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.ZFinanciera;
import com.principal.mundo.sysws.ClienteSucursal;
import com.principal.mundo.sysws.Libro;
import com.principal.mundo.sysws.Observacion;
import com.principal.mundo.sysws.PagoPrestamo;
import com.principal.mundo.sysws.Prestamo;

/**
 * Clase en la que se encuentran todos los metodos para la gestion de 
 * la base de datos del telefono Utilizando SQLite para ejecutar instrucciones como update, delete, insert y select
 * @author Javier
 *
 */
public class creaBD extends SQLiteOpenHelper {


	/**
	 * metodo que asigna valores a la super clase como
	 * el contexto,
	 * cursor y version de la base de datos
	 * nombre de base de datos,
	 *
	 * @param context
	 */
	public creaBD(Context context) {
		super(context, "BDPEDIDOSYS", null, 30);

	}


	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		String query = "CREATE TABLE usuarios " +
				"( cedula TEXT PRIMARY KEY, " +
				"  clave TEXT) ";
		db.execSQL(query);

		query = "CREATE TABLE parametro " +
				"(ws TEXT " +
				",ip1 TEXT " +
				",ip2 TEXT " +
				",ip3 TEXT " +
				",ip4 TEXT " +
				",modificaPrecio INGETER " +
				",usaCatalogo INGETER" +
				",consultaArticuloEnLinea INGETER" +
				",fecha TEXT" +
				",administraInventario INGETER" +
				",modificaStock INGETER" +
				",bodegaAdmInvOmision INGETER" +
				",realizaPedidos INGETER" +
				",bodegaPedidosOmision INGETER" +
				",realizaFactura INGETER" +
				",bodegaFacturaOmision INGETER" +
				",caja TEXT" +
				",usaPrintZebra INGETER" +
				",macAdd TEXT" +
				",realizaTranslados INGETER" +
				",bodegaTransladosOmision INGETER" +
				",ruta TEXT " +
				",generaCierre TEXT " +
				",consultaZ TEXT " +
				",usaWSCash TEXT " +
				",realizaPedidosMesa INGETER " +
				",usaTodasLasCategorias INGETER " +
				",permiteStocken0 INGETER " +
				",precioLibre INGETER " +
				",FacturaOnLine INGETER " +
				",RazonSocial TEXT " +
				",Representante TEXT " +
				",RegimenNit TEXT " +
				",DireccionTel TEXT " +
				",ResDian TEXT " +
				",Rango TEXT " +
				",NombreVendedor TEXT " +
				",Prefijo TEXT " +
				",UsaObservMasMenos TEXT " +
				",DescuentoPedido TEXT " +
				",ImprimePedido TEXT " +
				",ConsultaCosto TEXT " +
				",usaPrintEpson INGETER" +
				",macAddEpson TEXT" +
				",usaCantDecimal INGETER" +
				",usaSelecMultipleArt INGETER" +
				",precioMinimo INGETER" +
				",usaPrintBixolon INGETER" +
				",macAddBixolon TEXT" +
				",CarteraOnLine INGETER " +
				",ControlaPrecioLibre INGETER " +
				",SelectDocumentoPedido INGETER " +
				",RealizaAlistamiento INGETER " +
				",SelectFormaPagoPedido INGETER " +
				",UsaPrestamos INGETER " +
				",RealizaRemision INGETER " +
				",bodegaRemisionOmision INGETER" +
				",ModificaValorTotal INGETER" +
				",Webid INGETER" +
				",usaPrintDigitalPos INGETER" +
				",macAddDigitalPos TEXT" +
				",descuentaStockEnPedido INGETER" +
				",usaTipoPedido INGETER" +
				",permiteStocken0EnPedido INGETER " +
				" ) ";
		db.execSQL(query);


		query = "CREATE TABLE articulos " +
				"( idArticulo INGETER PRIMARY KEY, " +
				"  nombre TEXT, " +
				"  unidad TEXT, " +
				"  iva INGETER, " +
				"  impoconsumo INGETER, " +
				"  precio1 INGETER, " +
				"  precio2 INGETER, " +
				"  precio3 INGETER," +
				"  precio4 INGETER, " +
				"  precio5 INGETER, " +
				"  precio6 INGETER," +
				"  stock  REAL," +
				"  activo INTEGER," +
				"  categoria TEXT," +
				"  costo INGETER," +
				"  gramaje TEXT," +
				"  unidadDeMedida TEXT" +
				" ) ";
		db.execSQL(query);

		query = "CREATE TABLE clientes " +
				"( idCliente INGETER PRIMARY KEY, " +
				"  nombre TEXT," +
				"  representante TEXT," +
				"  nit TEXT," +
				"  direccion TEXT, " +
				"  telefono TEXT, " +
				"  municipio TEXT, " +
				"  limiteCredito INGETER, " +
				"  barrio TEXT, " +
				"  tipoCanal TEXT, " +
				"  lun TEXT, " +
				"  mar TEXT, " +
				"  mie TEXT, " +
				"  jue TEXT, " +
				"  vie TEXT, " +
				"  sab TEXT, " +
				"  dom TEXT, " +
				"  OrdenVisita INGETER," +
				"  OrdenVisitaLUN INGETER," +
				"  OrdenVisitaMAR INGETER," +
				"  OrdenVisitaMIE INGETER," +
				"  OrdenVisitaJUE INGETER," +
				"  OrdenVisitaVIE INGETER," +
				"  OrdenVisitaSAB INGETER," +
				"  OrdenVisitaDOM INGETER," +
				"  cedulaVendedor TEXT," +
				"  fechaUltimoPedido TEXT," +
				"  fechaUltimaVisita TEXT," +
				"  PrecioDefecto TEXT," +
				"  ubicado TEXT," +
				"  activo INTEGER," +
				"  fechaUltimaVenta TEXT," +
				"  fechaUltimoPago TEXT," +
				"  MotivoUltimaVisita TEXT," +
				"  deudaAntFac INTEGER," +
				"  DiasGracia INTEGER) ";
		db.execSQL(query);

		//Crea tabla para guardar sucursales del cliente 2020-09-29
		query = "CREATE TABLE ClienteSucursal " +
				"( IdClienteSucursal INGETER PRIMARY KEY, " +
				"  IdMpio INGETER," +
				"  IdDpto INGETER," +
				"  Direccion TEXT," +
				"  Telefono TEXT," +
				"  Establecimiento TEXT," +
				"  Borrado TEXT," +
				"  idCliente INTEGER," +
				"  Departamento TEXT," +
				"  Municipio TEXT" +
				") ";
		db.execSQL(query);


		//Crea tabla para guardar convenios del sistema 2020-09-29
		query = "CREATE TABLE Convenios " +
				"( IdConvenio INGETER PRIMARY KEY, " +
				"  Convenio TEXT," +
				"  Vigencia TEXT," +
				"  Vigencia2 TEXT," +
				"  Porcentaje TEXT," +
				"  AplicaDescuento TEXT," +
				"  ExigeClienteConv TEXT," +
				"  DescXArticulo TEXT," +
				"  AllCategories TEXT," +
				"  DescXProveedor TEXT" +
				") ";
		db.execSQL(query);


		//Crea tabla para guardar cierre entre turnos para versión para estaciones de servicio
		query = "CREATE TABLE CierreTurno" +
				"( IdCierreTurno INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"  NFacturaInicial TEXT," +
				"  NFacturaFinal TEXT," +
				"  NCaja TEXT," +
				"  NCierre TEXT," +
				"  Fecha2 TEXT," +
				"  Fecha TEXT," +
				"  Hora TEXT," +
				"  Valor TEXT," +
				"  Transacciones TEXT," +
				"  Vendedor TEXT" +
				") ";
		db.execSQL(query);

		//-se agrega tabla prestamo y pagos prestamo para la implementacion de prestamos a clientes
		//afectando el saldo inicial del cliente

		query = "CREATE TABLE Prestamo " +
				"( idPrestamo INGETER PRIMARY KEY, " +
				"  idCliente INGETER," +
				"  fecha TEXT," +
				"  fecha2 TEXT," +
				"  hora TEXT," +
				"  objeto TEXT," +
				"  valorPrestamo INTEGER," +
				"  saldoAnterior INGETER," +
				" Enviado INGETER," +
				"  nuevoSaldo INTEGER " +
				") ";
		db.execSQL(query);

		query = "CREATE TABLE PagoPrestamo " +
				"( idPagoPrestamo INGETER PRIMARY KEY, " +
				"  idCliente INGETER," +
				"  fecha TEXT," +
				"  fecha2 TEXT," +
				"  hora TEXT," +
				"  valor INTEGER," +
				" Enviado INGETER," +
				"  saldoAnterior INGETER," +
				"  nuevoSaldo INTEGER " +
				") ";
		db.execSQL(query);


		query = "CREATE TABLE pedidos " +
				"( idCodigoInterno INGETER PRIMARY KEY, " +
				"  idCodigoExterno INGETER," +
				"  idCliente INGETER," +
				"  fecha TEXT," +
				"  hora TEXT," +
				"  nombreVendedor TEXT," +
				"  valor INTEGER," +
				"  mesa INGETER," +
				"  envio INTEGER, " +
				"  observaciones TEXT," +
				"  DescuentoTotal INTEGER," +
				"  SubTotal INTEGER," +
				"  Documento TEXT " +
				"  ,FormaPago TEXT " +
				"  ,idClienteSucursal INGETER" +
				"  ,Estado TEXT " +
				") ";
		db.execSQL(query);

		query = "CREATE TABLE pedidos_articulos " +
				"( idPedido INGETER , " +
				"  idArticulo INGETER," +
				//			  "  cantidad INGETER," +
				"  cantidad REAL," +
				"  valorUnitario INTEGER," +
				"  valor INGETER, " +
				"  orden INTEGER," +
				"  codigo TEXT," +
				"  stock  REAL," +
				"  impreso  INGETER," +
				"  NObserArt  INGETER," +
				"  Observacion TEXT" +
				" ) ";
		db.execSQL(query);

		query = "CREATE TABLE factura " +
				"( idCodigoInterno INGETER PRIMARY KEY, " +
				"  idCodigoExterno INGETER," +
				"  idCliente INGETER," +
				"  fecha TEXT," +
				"  hora TEXT," +
				"  razonSocial TEXT," +
				"  representante TEXT," +
				"  regimenNit TEXT," +
				"  direccionTel TEXT," +
				"  NCaja INGETER," +
				"  prefijo TEXT," +
				"  base0 INGETER," +
				"  base5 INGETER," +
				"  base10 INGETER," +
				"  base14 INGETER," +
				"  base16 INGETER," +
				"  base19 INGETER," +
				"  iva5 INGETER," +
				"  iva10 INGETER," +
				"  iva14 INGETER," +
				"  iva16 INGETER," +
				"  iva19 INGETER," +
				"  impoCmo INGETER," +
				"  totalFactura INGETER," +
				"  resDian TEXT," +
				"  rango TEXT," +
				"  idBodega INGETER," +
				"  dineroRecibido INGETER," +
				"  NombreVendedor TEXT," +
				"  TelefonoVendedor TEXT," +
				"  valor INTEGER," +
				"  VentaCredito INTEGER," +
				"  NFactura INGETER," +
				"  Pagada TEXT," +
				"  ValorPagado INTEGER," +
				"  Observaciones TEXT," +
				"  idClienteSucursal INGETER," +
				"  Anulada TEXT" +
				") ";
		db.execSQL(query);

		query = "CREATE TABLE factura_articulos " +
				"( idFactura INGETER , " +
				"  idArticulo INGETER," +
				// "  cantidad INGETER," +
				"  cantidad REAL," +
				"  valorUnitario INTEGER," +
				"  valor INGETER, " +
				"  orden INTEGER," +
				"  codigo TEXT," +
				"  stock  REAL" +
				" ) ";
		db.execSQL(query);


		//---------------------------------------------------------------
		//Implementacion de remisiones
		//---------------------------------------------------------------
		query = "CREATE TABLE Remision " +
				"( idCodigoInterno INGETER PRIMARY KEY, " +
				"  idCodigoExterno INGETER," +
				"  idCliente INGETER," +
				"  fecha TEXT," +
				"  hora TEXT," +
				"  razonSocial TEXT," +
				"  representante TEXT," +
				"  regimenNit TEXT," +
				"  direccionTel TEXT," +
				"  NCaja INGETER," +
				"  prefijo TEXT," +
				"  base0 INGETER," +
				"  base5 INGETER," +
				"  base10 INGETER," +
				"  base14 INGETER," +
				"  base16 INGETER," +
				"  base19 INGETER," +
				"  iva5 INGETER," +
				"  iva10 INGETER," +
				"  iva14 INGETER," +
				"  iva16 INGETER," +
				"  iva19 INGETER," +
				"  impoCmo INGETER," +
				"  totalRemision INGETER," +
				"  resDian TEXT," +
				"  rango TEXT," +
				"  idBodega INGETER," +
				"  dineroRecibido INGETER," +
				"  NombreVendedor TEXT," +
				"  TelefonoVendedor TEXT," +
				"  valor INTEGER," +
				"  VentaCredito INTEGER," +
				"  NRemision INGETER," +
				"  Pagada TEXT," +
				"  ValorPagado INTEGER," +
				"  Observaciones TEXT" +
				" ,idClienteSucursal INGETER" +
				") ";
		db.execSQL(query);

		query = "CREATE TABLE Remision_articulos " +
				"( idRemision INGETER , " +
				"  idArticulo INGETER," +
				"  cantidad REAL," +
				"  valorUnitario INTEGER," +
				"  valor INGETER, " +
				"  orden INTEGER," +
				"  codigo TEXT," +
				"  stock  REAL" +
				" ) ";
		db.execSQL(query);
		//---------------------------------------------------------------
		//---------------------------------------------------------------


		query = "CREATE TABLE traslado " +
				"( idCodigoInterno INGETER PRIMARY KEY, " +
				"  idCodigoExterno INGETER," +
				"  fecha TEXT," +
				"  hora TEXT," +
				"  razonSocial TEXT," +
				"  representante TEXT," +
				"  regimenNit TEXT," +
				"  direccionTel TEXT," +
				"  NCaja INGETER," +
				"  base0 INGETER," +
				"  base5 INGETER," +
				"  base10 INGETER," +
				"  base14 INGETER," +
				"  base16 INGETER," +
				"  base19 INGETER," +
				"  iva5 INGETER," +
				"  iva10 INGETER," +
				"  iva14 INGETER," +
				"  iva16 INGETER," +
				"  iva19 INGETER," +
				"  impoCmo INGETER," +
				"  totaltraslado INGETER," +
				"  dineroRecibido INGETER," +
				"  IdBodegaOrigen INGETER," +
				"  IdBodegaDestino INGETER," +
				"  bodegaOrigen TEXT," +
				"  bodegaDestino TEXT" +
				"   ) ";
		db.execSQL(query);

		query = "CREATE TABLE traslado_articulos " +
				"( idTraslado INGETER , " +
				"  idArticulo INGETER," +
				"  cantidad REAL," +
				"  valorUnitario INTEGER," +
				"  valor INGETER, " +
				"  orden INTEGER," +
				"  codigo TEXT," +
				"  stock  REAL" +
				" ) ";
		db.execSQL(query);

		query = "CREATE TABLE articulo_codigo " +
				"( codigo TEXT PRIMARY KEY, " +
				"  idArticulo INGETER ) ";
		db.execSQL(query);

		query = "CREATE TABLE visita" +
				"( idVisita INTEGER PRIMARY KEY, " +
				"idCliente INTEGER, " +
				"fecha TEXT, " +
				"efectiva TEXT, " +
				"observacion TEXT, " +
				"idPedido INTEGER, " +
				"hora TEXT )";
		db.execSQL(query);
		query = "CREATE TABLE categoria " +
				"( idcategoria INGETER PRIMARY KEY, " +
				"  nombre TEXT, " +
				"  fechaact TEXT, " +
				"  activo INGETER, " +
				"  habilitada INGETER ) ";
		db.execSQL(query);

		query = "CREATE TABLE catalogo " +
				"( idCatalogo INGETER PRIMARY KEY, " +
				"  nombre TEXT," +
				"  activo INGETER ) ";
		db.execSQL(query);
		query = "CREATE TABLE catalogo_articulo " +
				"( idCatalogo INGETER PRIMARY KEY, " +
				"  idArticulo INGETER ) ";
		db.execSQL(query);

		query = "CREATE TABLE bodega " +
				"( IdBodega INGETER PRIMARY KEY, " +
				"  NBodega TEXT, " +
				"  Direccion TEXT, " +
				"  Telefono TEXT, " +
				"  Responsable TEXT," +
				"  Municipio  TEXT) ";
		db.execSQL(query);

		query = "CREATE TABLE zfinanciera " +
				"( NumZ INGETER PRIMARY KEY, " +
				"  NCaja INGETER, " +
				"  Fecha TEXT, " +
				"  Hora TEXT, " +
				"  FacturaInicial INGETER, " +
				"  FacturaFinal INGETER, " +
				"  VentasExentas INGETER, " +
				"  Ventas16 INGETER, " +
				"  Iva16 INGETER, " +
				"  Total16 INGETER, " +
				"  Ventas19 INGETER, " +
				"  Iva19 INGETER, " +
				"  Total19 INGETER, " +
				"  Ventas5 INGETER, " +
				"  Iva5 INGETER, " +
				"  Total5 INGETER, " +
				"  Ventas8 INGETER, " +
				"  Iva8 INGETER, " +
				"  Total8 INGETER, " +
				"  NTransacciones INGETER, " +
				"  Ventas INGETER, " +
				"  Iva INGETER, " +
				"  ImpoCmo INGETER," +
				"  Descuento INGETER," +
				"  Total INGETER," +
				"  consulta  INGETER) ";
		db.execSQL(query);
//	    
		query = "CREATE TABLE mediosdepago " +
				"( NumZ INGETER PRIMARY KEY, " +
				"  NCaja INGETER, " +
				"  MedioPago TEXT, " +
				"  Valor INGETER, " +
				"  Cuenta  INGETER) ";
		db.execSQL(query);


		//TABLAS PARA PAGOS DE FACTURAS
		query = "CREATE TABLE PagosFactura " +
				"( idPagosFactura INGETER PRIMARY KEY, " +
				"  NPago INGETER, " +
				"  NPagoFac INGETER, " +
				"  NCajaQRecibe INGETER, " +
				"  NFactura INGETER, " +
				"  NCaja INGETER, " +
				"  IdCliente INGETER, " +
				"  Cuenta INGETER, " +
				"  NComprobante INGETER, " +
				"  Fecha TEXT, " +
				"  Hora TEXT, " +
				"  Fecha2 TEXT, " +
				"  Descuento INGETER, " +
				"  RteFuente INGETER, " +
				"  Total INGETER, " +
				"  SaldoAnterior INGETER, " +
				"  Saldo INGETER, " +
				"  Observaciones TEXT, " +
				"  IdUsuario INGETER, " +
				"  FacturasQPaga TEXT, " +
				"  RteIca INGETER, " +
				"  RteIva INGETER, " +
				"  Devolucion  INGETER) ";
		db.execSQL(query);

		query = "CREATE TABLE ItemPagoFac " +
				"( IdItemPagoFac INGETER PRIMARY KEY, " +
				"  idPagosFactura INGETER, " +
				"  Valor INGETER, " +
				"  FormaPago TEXT, " +
				"  NCheque TEXT, " +
				"  Tarjeta  TEXT) ";
		db.execSQL(query);

		query = "CREATE TABLE Pago " +
				"( IdPago INGETER PRIMARY KEY, " +
				"  Valor INGETER, " +
				"  fecha TEXT, " +
				"  fecha2 TEXT, " +
				"  Descripcion TEXT, " +
				"  idCliente INGETER, " +
				"  Enviado INGETER, " +
				"  NPagosFacturaNoEnviados  TEXT) ";
		db.execSQL(query);

		query = "CREATE TABLE Compuestos " +
				"( IdArticulo INGETER, " +
				"  IdArtComponente INGETER, " +
				"  Cantidad INGETER ) ";
		db.execSQL(query);

		query = "CREATE TABLE ObservacionArticulo " +
				"( idArticulo INGETER, " +
				"  idObservacion INGETER," +
				"  Detalle TEXT ) ";
		db.execSQL(query);

		query = "CREATE TABLE pedidos_articulos_obser " +
				"( idPedido INGETER, " +
				"  NObserArt INGETER," +
				" idObservacion  INGETER," +
				" Detalle TEXT  ) ";
		db.execSQL(query);

		query = "CREATE TABLE Libro " +
				"( idLibro INGETER PRIMARY KEY, " +
				"  NLibro INGETER," +
				"  idCliente INGETER," +
				" MovCredito  INGETER," +
				" MovDedito  INGETER," +
				" Saldo  INGETER," +
				" Concepto  TEXT," +
				" Enviado INGETER," +
				" Fecha  TEXT," +
				" Fecha2 TEXT, " +
				" hora  TEXT," +
				" SaldoAnterior  INGETER" +
				" ) ";
		db.execSQL(query);

	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		//if (oldVersion == 14 && newVersion==15)
		//{
		String upgradeQuery = "";
		//Crea tabla para guardar sucursales del cliente 2020-09-29
		upgradeQuery = "CREATE TABLE ClienteSucursal " +
				"( IdClienteSucursal INGETER PRIMARY KEY, " +
				"  IdMpio INGETER," +
				"  IdDpto INGETER," +
				"  Direccion TEXT," +
				"  Telefono TEXT," +
				"  Establecimiento TEXT," +
				"  Borrado TEXT," +
				"  idCliente INTEGER," +
				"  Departamento TEXT," +
				"  Municipio TEXT" +
				") ";
		Actualiza(db, upgradeQuery);


		//Crea tabla para guardar convenios del sistema 2020-09-29
		upgradeQuery = "CREATE TABLE Convenios " +
				"( IdConvenio INGETER PRIMARY KEY, " +
				"  Convenio TEXT," +
				"  Vigencia TEXT," +
				"  Vigencia2 TEXT," +
				"  Porcentaje TEXT," +
				"  AplicaDescuento TEXT," +
				"  ExigeClienteConv TEXT," +
				"  DescXArticulo TEXT," +
				"  AllCategories TEXT," +
				"  DescXProveedor TEXT" +
				") ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE pedidos ADD COLUMN idClienteSucursal INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE factura ADD COLUMN idClienteSucursal INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE Remision ADD COLUMN idClienteSucursal INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);

		//upgradeQuery= "DROP TABLE CierreTurno";
		//Actualiza(db,upgradeQuery);
		upgradeQuery = "CREATE TABLE CierreTurno" +
				"( IdCierreTurno INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"  NFacturaInicial TEXT," +
				"  NFacturaFinal TEXT," +
				"  NCaja TEXT," +
				"  NCierre TEXT," +
				"  Fecha2 TEXT," +
				"  Fecha TEXT," +
				"  Hora TEXT," +
				"  Valor TEXT," +
				"  Transacciones TEXT," +
				"  Vendedor TEXT" +
				") ";
		Actualiza(db, upgradeQuery);


		upgradeQuery = "ALTER TABLE parametro ADD COLUMN RealizaRemision INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);
		upgradeQuery = "ALTER TABLE parametro ADD COLUMN bodegaRemisionOmision INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);
		upgradeQuery = "ALTER TABLE parametro ADD COLUMN ModificaValorTotal INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);
		upgradeQuery = "ALTER TABLE parametro ADD COLUMN Webid INTEGER DEFAULT 0";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE CierreTurno ADD COLUMN NCierre TEXT DEFAULT '0'";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE parametro ADD COLUMN usaPrintDigitalPos INGETER ";
		Actualiza(db, upgradeQuery);
		upgradeQuery = "ALTER TABLE parametro ADD COLUMN macAddDigitalPos TEXT ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE parametro ADD COLUMN descuentaStockEnPedido INGETER ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE Factura ADD COLUMN Anulada TEXT ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE pedidos ADD COLUMN Estado TEXT ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE parametro ADD COLUMN usaTipoPedido INGETER ";
		Actualiza(db, upgradeQuery);

		upgradeQuery = "ALTER TABLE parametro ADD COLUMN permiteStocken0EnPedido INGETER ";
		Actualiza(db, upgradeQuery);



		//}


	}

	private void Actualiza(SQLiteDatabase db, String query) {
		try {
			db.execSQL(query);
		} catch (Exception e) {

		}
	}


//------------------------------------------------------------------------------------------------------------	
//-----------------------------------------INSERT-------------------------------------------------------------	
//------------------------------------------------------------------------------------------------------------	


	public boolean insertParametro(Parametros parametros) {
		try {
			ContentValues valuesIn = new ContentValues();
			valuesIn.put("ws", parametros.ws);
			valuesIn.put("ip1", parametros.ip1);
			valuesIn.put("ip2", parametros.ip2);
			valuesIn.put("ip3", parametros.ip3);
			valuesIn.put("ip4", parametros.ip4);
			valuesIn.put("modificaPrecio", parametros.modificaPrecio);
			valuesIn.put("usaCatalogo", parametros.getUsaCatalogo());
			valuesIn.put("consultaArticuloEnLinea", parametros.getConsultaArticuloEnLinea());
			valuesIn.put("fecha", parametros.fecha);
			valuesIn.put("administraInventario", parametros.getAdministraInventario());
			valuesIn.put("modificaStock", parametros.getModificaStock());
			valuesIn.put("bodegaAdmInvOmision", parametros.getBodegaAdmInvOmision());
			valuesIn.put("realizaPedidos", parametros.getRealizaPedidos());
			valuesIn.put("bodegaPedidosOmision", parametros.getBodegaPedidosOmision());
			valuesIn.put("realizaFactura", parametros.getRealizaFactura());
			valuesIn.put("bodegaFacturaOmision", parametros.getBodegaFacturaOmision());
			valuesIn.put("caja", parametros.caja);
			valuesIn.put("usaPrintZebra", parametros.getUsaImpresoraZebra());
			valuesIn.put("macAdd", parametros.getMacAdd());
			valuesIn.put("realizaTranslados", parametros.getRealizaTranslados());
			valuesIn.put("bodegaTransladosOmision", parametros.getBodegaTransladosOmision());
			valuesIn.put("ruta", parametros.ruta);
			valuesIn.put("generaCierre", parametros.getGeneraCierre());
			valuesIn.put("consultaZ", parametros.getConsultaZ());
			valuesIn.put("usaWSCash", parametros.getUsaWSCash());
			valuesIn.put("realizaPedidosMesa", parametros.getRealizaPedidosMesa());
			valuesIn.put("usaTodasLasCategorias", parametros.getUsaTodasLasCategorias());
			valuesIn.put("permiteStocken0", parametros.getPermiteStocken0());
			valuesIn.put("precioLibre", parametros.getPrecioLibre());
			valuesIn.put("FacturaOnLine", parametros.getFacturaOnLine());
			valuesIn.put("RazonSocial", parametros.getRazonSocial());
			valuesIn.put("Representante", parametros.getRepresentante());
			valuesIn.put("RegimenNit", parametros.getRegimenNit());
			valuesIn.put("DireccionTel", parametros.getDireccionTel());
			valuesIn.put("ResDian", parametros.getResDian());
			valuesIn.put("Rango", parametros.getRango());
			valuesIn.put("NombreVendedor", parametros.getNombreVendedor());
			valuesIn.put("Prefijo", parametros.getPrefijo());
			valuesIn.put("UsaObservMasMenos", parametros.getUsaObservMasMenos());
			valuesIn.put("DescuentoPedido", parametros.getDescuentoPedido());
			valuesIn.put("ImprimePedido", parametros.getImprimePedido());
			valuesIn.put("ConsultaCosto", parametros.getConsultaCosto());
			valuesIn.put("usaPrintEpson", parametros.getUsaPrintEpson());
			valuesIn.put("macAddEpson", parametros.getMacAddEpson());
			valuesIn.put("usaCantDecimal", parametros.getUsaCantDecimal());
			valuesIn.put("usaSelecMultipleArt", parametros.getUsaSelecMultipleArt());
			valuesIn.put("precioMinimo", parametros.getPrecioMinimo());
			valuesIn.put("usaPrintBixolon", parametros.getUsaPrintBixolon());
			valuesIn.put("macAddBixolon", parametros.getMacAddBixolon());
			valuesIn.put("CarteraOnLine", parametros.getCarteraOnLine());
			valuesIn.put("ControlaPrecioLibre", parametros.getControlaPrecioLibre());
			valuesIn.put("SelectDocumentoPedido", parametros.getSelectDocumentoPedido());
			valuesIn.put("RealizaAlistamiento", parametros.getRealizaAlistamiento());
			valuesIn.put("SelectFormaPagoPedido", parametros.getSelectFormaPagoPedido());
			valuesIn.put("UsaPrestamos", parametros.getUsaPrestamos());
			valuesIn.put("RealizaRemision", parametros.getRealizaRemision());
			valuesIn.put("bodegaRemisionOmision", parametros.getBodegaRemisionOmision());
			valuesIn.put("ModificaValorTotal", parametros.getModificaValorTotal());
			valuesIn.put("Webid", parametros.getWebid2());
			valuesIn.put("usaPrintDigitalPos", parametros.getUsaPrintDigitalPos());
			valuesIn.put("macAddDigitalPos", parametros.getMacAddDigitalPos());
			valuesIn.put("descuentaStockEnPedido", parametros.getDescuentaStockEnPedido());
			valuesIn.put("usaTipoPedido", parametros.getUsaTipoPedido());
			valuesIn.put("permiteStocken0EnPedido", parametros.getPermiteStocken0EnPedido());



			this.getWritableDatabase().insert("parametro", null, valuesIn);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Inserta registro en la tabla Usuarios tan pornto realiza login
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 *
	 * @param cedula
	 * @param clave
	 * @return
	 */
	public boolean insertUsuario(String cedula, String clave) {
		try {
			ContentValues valuesIn = new ContentValues();
			valuesIn.put("cedula", cedula);
			valuesIn.put("clave", clave);
			this.getWritableDatabase().insert("usuarios", null, valuesIn);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean insertCategorias(ArrayList<Categoria> lista) {
		try {
			ContentValues valuesIn = new ContentValues();
			valuesIn.put("activo", 0);
			this.getWritableDatabase().update("categoria", valuesIn, null, null);

			for (int i = 0; i < lista.size(); i++) {
				Categoria categoria = (Categoria) lista.get(i);
				valuesIn = new ContentValues();
				valuesIn.put("idCategoria", categoria.getIdCategoria());
				valuesIn.put("nombre", categoria.getNombre());

				if (getValidaCategoria(categoria.getIdCategoria())) {
					this.getWritableDatabase().update("categoria", valuesIn, " idcategoria =" + categoria.getIdCategoria(), null);
				} else {
					valuesIn.put("fechaact", categoria.getFechaAct());
					valuesIn.put("activo", categoria.getActivo());
					valuesIn.put("habilitada", categoria.getHabilidada());
					this.getWritableDatabase().insert("categoria", null, valuesIn);
				}

			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public boolean insertCatalogos(ArrayList<Catalogo> lista) {
		try {
			ContentValues valuesIn = new ContentValues();
			valuesIn.put("activo", 0);
			this.getWritableDatabase().update("catalogo", valuesIn, null, null);

			for (int i = 0; i < lista.size(); i++) {
				Catalogo catalogo = (Catalogo) lista.get(i);
				valuesIn = new ContentValues();
				valuesIn.put("idCatalogo", catalogo.getIdCatalogo());
				valuesIn.put("nombre", catalogo.getNombre());
				valuesIn.put("activo", catalogo.getActivo());
				if (getValidaCatalogo(catalogo.getIdCatalogo())) {
					this.getWritableDatabase().update("catalogo", valuesIn, " idCatalogo =" + catalogo.getIdCatalogo(), null);
				} else {
					this.getWritableDatabase().insert("catalogo", null, valuesIn);
				}

			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Inserta o actualiza lista de clientes en la tabla cliente de la base de datos
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 *
	 * @param lista
	 * @return true o false
	 */
	public boolean insertClientes(ArrayList<Cliente> lista) {
		try {
			if (lista != null) {
				if (lista.size() > 0) {
					ContentValues valuesIn = new ContentValues();
					valuesIn.put("activo", 0);
					this.getWritableDatabase().update("clientes", valuesIn, null, null);

					for (int i = 0; i < lista.size(); i++) {
						Cliente cli = (Cliente) lista.get(i);
						valuesIn = new ContentValues();
						valuesIn.put("idCliente", cli.idCliente);
						valuesIn.put("nombre", cli.nombre);
						valuesIn.put("representante", cli.representante);
						valuesIn.put("nit", cli.nit);
						valuesIn.put("direccion", cli.getDireccion());
						valuesIn.put("telefono", cli.getTelefono());
						valuesIn.put("municipio", cli.municipio);
						valuesIn.put("limiteCredito", cli.limiteCredito);
						valuesIn.put("barrio", cli.barrio);
						valuesIn.put("tipoCanal", cli.tipoCanal);
						valuesIn.put("lun", cli.lun);
						valuesIn.put("mar", cli.mar);
						valuesIn.put("mie", cli.mie);
						valuesIn.put("jue", cli.jue);
						valuesIn.put("vie", cli.vie);
						valuesIn.put("sab", cli.sab);
						valuesIn.put("dom", cli.dom);
						valuesIn.put("OrdenVisitaLUN", cli.OrdenVisitaLUN);
						valuesIn.put("OrdenVisitaMAR", cli.OrdenVisitaMAR);
						valuesIn.put("OrdenVisitaMIE", cli.OrdenVisitaMIE);
						valuesIn.put("OrdenVisitaJUE", cli.OrdenVisitaJUE);
						valuesIn.put("OrdenVisitaVIE", cli.OrdenVisitaVIE);
						valuesIn.put("OrdenVisitaSAB", cli.OrdenVisitaSAB);
						valuesIn.put("OrdenVisitaDOM", cli.OrdenVisitaDOM);
						valuesIn.put("ordenVisita", cli.ordenVisita);
						valuesIn.put("cedulaVendedor", cli.cedulaVendedor);
						valuesIn.put("PrecioDefecto", cli.PrecioDefecto);
						valuesIn.put("activo", cli.activo);
						valuesIn.put("DiasGracia", cli.DiasGracia);


						if (getValidaCliente(cli.idCliente)) {
							this.getWritableDatabase().update("clientes", valuesIn, " idCliente =" + cli.idCliente, null);
						} else {
							valuesIn.put("fechaUltimoPedido", cli.fechaUltimoPedido);
							valuesIn.put("fechaUltimaVisita", cli.fechaUltimaVisita);
							valuesIn.put("fechaUltimaVenta", cli.fechaUltimaVenta);
							valuesIn.put("ubicado", cli.ubicado);
							//-- agrega saldo inicial del cliente si el cliente no esta registrado en la bd
							valuesIn.put("deudaAntFac", cli.deudaAntFac);
							this.getWritableDatabase().insert("clientes", null, valuesIn);
						}

						//actualiza sucursales en caso de que existam
						//-----------------------------------------------------------------------------------

						if (cli.getXmlClientesSucursales().length() > 0) {
							//carga sucursales asignadas al cliente
							cli.getCargarSucursales();

							if (cli.getListaClienteSucursal() != null) {
								//Elimina sucursales asignadas al cliente
								eliminaClienteSucursal(cli.getIdCliente());

								for (int j = 0; j < cli.getListaClienteSucursal().size(); j++) {
									ContentValues valuesCod = new ContentValues();
									ClienteSucursal cls = cli.getListaClienteSucursal().get(j);
									valuesCod.put("IdClienteSucursal", cls.getIdClienteSucursal());
									valuesCod.put("IdMpio", cls.getIdMpio());
									valuesCod.put("IdDpto", cls.getIdDpto());
									valuesCod.put("Direccion", cls.getDireccion());
									valuesCod.put("Telefono", cls.getTelefono());
									valuesCod.put("Establecimiento", cls.getEstablecimiento());
									valuesCod.put("Borrado", cls.getBorrado());
									valuesCod.put("idCliente", cls.getIdCliente());
									valuesCod.put("Departamento", cls.getDepartamento());
									valuesCod.put("Municipio", cls.getMunicipio());
									this.getWritableDatabase().insert("ClienteSucursal", null, valuesCod);
								}
							}

						}
						//---------------------------------------------------------------------------------------


					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


	public boolean insertClienteCartera(Cliente cliente) {
		try {
			if (cliente != null) {

				ContentValues valuesIn = new ContentValues();
				valuesIn = new ContentValues();
				valuesIn.put("idCliente", cliente.idCliente);
				valuesIn.put("nombre", cliente.nombre);
				if (getValidaCliente(cliente.idCliente)) {
					this.getWritableDatabase().update("clientes", valuesIn, " idCliente =" + cliente.idCliente, null);
				} else {
					this.getWritableDatabase().insert("clientes", null, valuesIn);
				}


				return true;

			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Inserta  o actualiza lista de articulos con sus respectivos codigos asingados, en la tabla cliente y codigo de la base de datos
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 *
	 * @param lista
	 * @return true o false
	 */

//	public boolean insertArticulo(Articulo articulo)
//	{
//		boolean res=false;
//		try
//		{
//			
//			ContentValues valuesIn=new ContentValues();
////			valuesIn.put("activo",0);			
////			this.getWritableDatabase().update("articulos", valuesIn,null,null);
//			
//		
//				valuesIn=new ContentValues();
//				valuesIn.put("IdArticulo", articulo.idArticulo);
//				valuesIn.put("nombre", articulo.nombre);
//				valuesIn.put("unidad", articulo.unidad);
//				valuesIn.put("iva", articulo.iva);
//				valuesIn.put("impoConsumo", articulo.impoConsumo);
//				valuesIn.put("precio1", articulo.precio1);
//				valuesIn.put("precio2", articulo.precio2);
//				valuesIn.put("precio3", articulo.precio3);
//				valuesIn.put("precio4", articulo.precio4);
//				valuesIn.put("precio5", articulo.precio5);
//				valuesIn.put("precio6", articulo.precio6);
//				valuesIn.put("stock" , articulo.stock);
//				valuesIn.put("activo", articulo.activo);			
//				valuesIn.put("categoria", articulo.categoria);				
//				if(getValidaArticulo(articulo.idArticulo))
//				{
//					this.getWritableDatabase().update("articulos", valuesIn," IdArticulo ="+articulo.idArticulo,null);
//				} 
//				else
//				{
//					this.getWritableDatabase().insert("articulos", null, valuesIn);
//				}
//				res=true;	
//		}
//		catch(Exception e)
//		{
//			
//		}
//		return res;
//	
//	}
	public String insertArticulo(ArrayList<Articulo> lista) {
		Articulo art = null;
		try {
			String res = "";
			ContentValues valuesIn = new ContentValues();
//			valuesIn.put("activo",0);			
//			this.getWritableDatabase().update("articulos", valuesIn,null,null);

			for (int i = 0; i < lista.size(); i++) {
				art = (Articulo) lista.get(i);
				valuesIn = new ContentValues();
				valuesIn.put("IdArticulo", art.idArticulo);
				valuesIn.put("nombre", art.nombre);
				valuesIn.put("unidad", art.unidad);
				valuesIn.put("iva", art.iva);
				valuesIn.put("impoConsumo", art.impoConsumo);
				valuesIn.put("precio1", art.precio1);
				valuesIn.put("precio2", art.precio2);
				valuesIn.put("precio3", art.precio3);
				valuesIn.put("precio4", art.precio4);
				valuesIn.put("precio5", art.precio5);
				valuesIn.put("precio6", art.precio6);
				valuesIn.put("stock", art.stock);
				valuesIn.put("activo", art.activo);
				valuesIn.put("categoria", art.idCodigo);
				valuesIn.put("costo", art.costo);
				//agregado 14-02-2020 ------------------------
				valuesIn.put("gramaje", art.gramaje);
				valuesIn.put("unidadDeMedida", art.unidadDeMedida);
				//-------------------------------------

				if (getValidaArticulo(art.idArticulo)) {
					this.getWritableDatabase().update("articulos", valuesIn, " IdArticulo =" + art.idArticulo, null);
				} else {
					this.getWritableDatabase().insert("articulos", null, valuesIn);
				}
				if (art.activo == 0) {
					eliminaCodigosArticulo(art.idArticulo);
					eliminaObservacionesArticulo(art.idArticulo);
				}
				if (art.codigos != null) {
					eliminaCodigosArticulo(art.idArticulo);

					for (int j = 0; j < art.codigos.size(); j++) {
						ContentValues valuesCod = new ContentValues();
						valuesCod.put("idArticulo", art.idArticulo);
						valuesCod.put("codigo", art.codigos.get(j).toString());
						this.getWritableDatabase().insert("articulo_codigo", null, valuesCod);
					}
				}
				if (art.getListaObservaciones() != null) {
					eliminaObservacionesArticulo(art.idArticulo);

					for (int j = 0; j < art.getListaObservaciones().size(); j++) {
						ContentValues valuesCod = new ContentValues();
						valuesCod.put("idArticulo", art.idArticulo);
						valuesCod.put("IdObservacion", art.getListaObservaciones().get(j).getIdObservacion().toString());
						valuesCod.put("Detalle", art.getListaObservaciones().get(j).getDetalle().toString());
						this.getWritableDatabase().insert("ObservacionArticulo", null, valuesCod);
					}
				}
			}

			return res = "ok";
		} catch (Exception e) {
			return lista.size() + " ar " + art.idArticulo + e.toString();
		}
	}


	public String UpdateStockArticulo(long idArticulo, double stock) {
		Articulo art = null;
		try {
			String res = "";
			ContentValues valuesIn = new ContentValues();

			valuesIn = new ContentValues();
			valuesIn.put("stock", stock);
			this.getWritableDatabase().update("articulos", valuesIn, " IdArticulo =" + idArticulo, null);


			return res = "ok";
		} catch (Exception e) {
			return "";
		}
	}




	public boolean insertPrestamo(Prestamo prestamo)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idPrestamo", prestamo.getIdPrestamo());
			valuesIn.put("idCliente", prestamo.getIdCliente());
			valuesIn.put("fecha", prestamo.getFecha());
			valuesIn.put("fecha2", prestamo.getFecha2());
			valuesIn.put("hora", prestamo.getHora());
			valuesIn.put("objeto", prestamo.getObjeto());
			valuesIn.put("valorPrestamo", prestamo.getValorPrestamo());
			valuesIn.put("saldoAnterior", prestamo.getSaldoAnterior());
			valuesIn.put("nuevoSaldo", prestamo.getNuevoSaldo());
			valuesIn.put("enviado", prestamo.getEnviado());
			this.getWritableDatabase().insert("Prestamo", null, valuesIn);

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean insertLibro(Libro libro)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idLibro", libro.getIdLibro());
			valuesIn.put("NLibro", libro.getNLibro());
			valuesIn.put("IdCliente", libro.getIdCliente());
			valuesIn.put("MovCredito", libro.getMovCredito());
			valuesIn.put("MovDedito", libro.getMovDedito());
			valuesIn.put("Saldo", libro.getSaldo());
			valuesIn.put("Enviado", libro.getEnviado());
			valuesIn.put("Concepto", libro.getConcepto());
			valuesIn.put("Fecha", libro.getFecha());
			valuesIn.put("Fecha2", libro.getFecha2());
			valuesIn.put("hora", libro.getHora());
			valuesIn.put("SaldoAnterior", libro.getSaldoAnterior());
			this.getWritableDatabase().insert("Libro", null, valuesIn);

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}




	
	/**
	 * Inserta pedido en la base de datos
	 * * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param pedidoin
	 * @return true o false
	 */
	public boolean insertPedido( Pedido_in pedidoin, ArrayList<ArticulosPedido> lista, boolean descuentaStock)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", pedidoin.idCodigoInterno);
			valuesIn.put("idCodigoExterno", pedidoin.idCodigoExterno);
			valuesIn.put("idCliente", pedidoin.idCliente);
			valuesIn.put("fecha", pedidoin.getFechaSqlite());
			valuesIn.put("hora", pedidoin.hora);
			valuesIn.put("valor", pedidoin.valor);
			//version bd 07/09/2015
			valuesIn.put("envio", pedidoin.getEnvio());
			valuesIn.put("observaciones", pedidoin.getObservaciones());
			valuesIn.put("SubTotal", pedidoin.getSubTotal());
			valuesIn.put("DescuentoTotal", pedidoin.getDescuentoTotal());
			valuesIn.put("Documento", pedidoin.getDocumento());
			valuesIn.put("FormaPago", pedidoin.getFormaPago());
			valuesIn.put("idClienteSucursal", pedidoin.idClienteSucursal);
			valuesIn.put("Estado", pedidoin.getEstado());
			
			if(getValidaPedido( pedidoin.idCodigoInterno, pedidoin.idCodigoExterno))
			{
				 this.getWritableDatabase().update("pedidos",valuesIn," idCodigoInterno = "+pedidoin.idCodigoInterno,null);
			}
			else
			{
				  this.getWritableDatabase().insert("pedidos", null, valuesIn);
			}
			if(descuentaStock)
			{
				ActualizaStockArtiulosPedido(lista);
			}


			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean insertCierreTurno(CierreTurno cierreTurno)
	{
		try
		{
			//elimina cierres registrados con el mismo numero antes de generarlo

			this.getWritableDatabase().delete("CierreTurno", " NCierre ="+cierreTurno.getNCierre(), null);



			ContentValues valuesIn=new ContentValues();
			valuesIn.put("NFacturaInicial", cierreTurno.getNFacturaInicial());
			valuesIn.put("NFacturaFinal", cierreTurno.getNFacturaFinal());
			valuesIn.put("NCaja", cierreTurno.getNCaja());
			valuesIn.put("NCierre", cierreTurno.getNCierre());
			valuesIn.put("Fecha2", cierreTurno.getFecha2());
			valuesIn.put("Fecha", cierreTurno.getFecha());
			valuesIn.put("Hora", cierreTurno.getHora());
			valuesIn.put("Valor", cierreTurno.getValor());
			valuesIn.put("Transacciones", cierreTurno.getTransacciones());
			valuesIn.put("Vendedor", cierreTurno.getVendedor());
			this.getWritableDatabase().insert("CierreTurno", null, valuesIn);

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	/**
	 * inserta registro de detalles de pedido  en la tabla pedido _artuculos
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idPedido
	 * @param idArticulo
	 * @param cantidad
	 * @param valorUnitario
	 * @param valor
	 * @param orden
	 * @param codigo
	 * @return true o false
	 */
	public boolean insertPedidoArticulos( long idPedido, long idArticulo, double cantidad, long valorUnitario, long valor,long orden, String codigo, double stock, String observacion )
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idPedido", idPedido);
			valuesIn.put("idArticulo", idArticulo);
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
			valuesIn.put("orden", orden);
			valuesIn.put("codigo", codigo);	
			valuesIn.put("stock", stock);
			valuesIn.put("Observacion", observacion);


		    this.getWritableDatabase().insert("pedidos_articulos", null, valuesIn);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean insertTrasladoArticulos( long idTraslado, long idArticulo, long cantidad, long valorUnitario, long valor,long orden, String codigo, double stock )
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idTraslado", idTraslado);
			valuesIn.put("idArticulo", idArticulo);
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
			valuesIn.put("orden", orden);
			valuesIn.put("codigo", codigo);
			valuesIn.put("stock", stock);	
		    this.getWritableDatabase().insert("traslado_articulos", null, valuesIn);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean insertFactura( Factura_in facturain, boolean online)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", facturain.idCodigoInterno);
			valuesIn.put("idCodigoExterno", facturain.idCodigoExterno);
			valuesIn.put("idCliente", facturain.idCliente);
			valuesIn.put("fecha", facturain.getFechaSqlite());
			valuesIn.put("hora", facturain.hora);
			valuesIn.put("valor", facturain.valor);
			valuesIn.put("razonSocial", facturain.razonSocial);
			valuesIn.put("representante", facturain.representante);
			valuesIn.put("regimenNit", facturain.regimenNit);
			valuesIn.put("direccionTel", facturain.direccionTel);
			valuesIn.put("NCaja", facturain.NCaja);
			valuesIn.put("prefijo", facturain.prefijo);
			valuesIn.put("base0", facturain.base0);
			valuesIn.put("base5", facturain.base5);
			valuesIn.put("base10", facturain.base10);
			valuesIn.put("base14", facturain.base14);
			valuesIn.put("base16", facturain.base16);
			valuesIn.put("base19", facturain.base19);
			valuesIn.put("iva5", facturain.iva5);
			valuesIn.put("iva10", facturain.iva10);
			valuesIn.put("iva14", facturain.iva14);
			valuesIn.put("iva16", facturain.iva16);
			valuesIn.put("iva19", facturain.iva19);
			valuesIn.put("impoCmo", facturain.impoCmo);
			valuesIn.put("totalFactura", facturain.totalFactura);
			valuesIn.put("resDian", facturain.resDian);
			valuesIn.put("rango", facturain.rango);
			valuesIn.put("idBodega", facturain.idBodega);
			valuesIn.put("dineroRecibido", facturain.dineroRecibido);
			valuesIn.put("nombrevendedor", facturain.NombreVendedor);
			valuesIn.put("telefonovendedor", facturain.TelefonoVendedor);
			valuesIn.put("VentaCredito", facturain.VentaCredito);
			valuesIn.put("NFactura", facturain.NFactura);
			valuesIn.put("Pagada", facturain.Pagada);
			valuesIn.put("ValorPagado", facturain.ValorPagado);
			valuesIn.put("Observaciones", facturain.observaciones);
			valuesIn.put("idClienteSucursal", facturain.idClienteSucursal);
			valuesIn.put("Anulada", facturain.Anulada);
						
			if(getValidaFactura(facturain.idCodigoInterno, facturain.idCodigoExterno))
			{
				 this.getWritableDatabase().update("factura",valuesIn," idCodigoInterno = "+facturain.idCodigoInterno,null);
			}
			else
			{
				  this.getWritableDatabase().insert("factura", null, valuesIn);
			}

			if (!online)
			{
				ActualizaStockArtiulosFactura(facturain);
			}

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean insertRemision(Remision_in remisionin, boolean online)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", remisionin.idCodigoInterno);
			valuesIn.put("idCodigoExterno", remisionin.idCodigoExterno);
			valuesIn.put("idCliente", remisionin.idCliente);
			valuesIn.put("fecha", remisionin.getFechaSqlite());
			valuesIn.put("hora", remisionin.hora);
			valuesIn.put("valor", remisionin.valor);
			valuesIn.put("razonSocial", remisionin.razonSocial);
			valuesIn.put("representante", remisionin.representante);
			valuesIn.put("regimenNit", remisionin.regimenNit);
			valuesIn.put("direccionTel", remisionin.direccionTel);
			valuesIn.put("NCaja", remisionin.NCaja);
			valuesIn.put("prefijo", remisionin.prefijo);
			valuesIn.put("base0", remisionin.base0);
			valuesIn.put("base5", remisionin.base5);
			valuesIn.put("base10", remisionin.base10);
			valuesIn.put("base14", remisionin.base14);
			valuesIn.put("base16", remisionin.base16);
			valuesIn.put("base19", remisionin.base19);
			valuesIn.put("iva5", remisionin.iva5);
			valuesIn.put("iva10", remisionin.iva10);
			valuesIn.put("iva14", remisionin.iva14);
			valuesIn.put("iva16", remisionin.iva16);
			valuesIn.put("iva19", remisionin.iva19);
			valuesIn.put("impoCmo", remisionin.impoCmo);
			valuesIn.put("totalRemision", remisionin.totalRemision);
			valuesIn.put("resDian", remisionin.resDian);
			valuesIn.put("rango", remisionin.rango);
			valuesIn.put("idBodega", remisionin.idBodega);
			valuesIn.put("dineroRecibido", remisionin.dineroRecibido);
			valuesIn.put("nombrevendedor", remisionin.NombreVendedor);
			valuesIn.put("telefonovendedor", remisionin.TelefonoVendedor);
			valuesIn.put("VentaCredito", remisionin.VentaCredito);
			valuesIn.put("NRemision", remisionin.NRemision);
			valuesIn.put("Pagada", remisionin.Pagada);
			valuesIn.put("ValorPagado", remisionin.ValorPagado);
			valuesIn.put("Observaciones", remisionin.observaciones);
			valuesIn.put("idClienteSucursal", remisionin.idClienteSucursal);

			if(getValidaFactura(remisionin.idCodigoInterno, remisionin.idCodigoExterno))
			{
				this.getWritableDatabase().update("remision",valuesIn," idCodigoInterno = "+remisionin.idCodigoInterno,null);
			}
			else
			{
				this.getWritableDatabase().insert("remision", null, valuesIn);
			}

			if (!online)
			{
				ActualizaStockArtiulosRemision(remisionin);
			}

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	private boolean ActualizaStockArtiulosPedido(ArrayList<ArticulosPedido> lista)
	{

		try
		{
			ContentValues valuesIn=new ContentValues();
			for (int i = 0; i < lista.size(); i++)
			{
				ArticulosPedido  articulosPedido= lista.get(i);

				double NStock= getStockArticulo(articulosPedido.getIdArticulo())-articulosPedido.cantidad ;
				valuesIn=new ContentValues();
				valuesIn.put("stock",NStock);

				this.getWritableDatabase().update("articulos", valuesIn," idArticulo ="+articulosPedido.getIdArticulo(),null);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}



	}


	private boolean ActualizaStockArtiulosFactura(Factura_in facturain)
	{

		try
		{
			ContentValues valuesIn=new ContentValues();
			for (int i = 0; i < facturain.getListaArticulos().size(); i++)
			{
				ArticulosFactura  articulosFactura= facturain.getListaArticulos().get(i);

				double NStock= getStockArticulo(articulosFactura.getIdArticulo())-articulosFactura.cantidad ;
				valuesIn=new ContentValues();
				valuesIn.put("stock",NStock);

				this.getWritableDatabase().update("articulos", valuesIn," idArticulo ="+articulosFactura.getIdArticulo(),null);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}



	}
	private boolean ActualizaStockArtiulosRemision(Remision_in remisionin)
	{

		try
		{
			ContentValues valuesIn=new ContentValues();
			for (int i = 0; i < remisionin.getListaArticulos().size(); i++)
			{
				ArticulosRemision  articulosRemision= remisionin.getListaArticulos().get(i);

				double NStock= getStockArticulo(articulosRemision.getIdArticulo())-articulosRemision.cantidad ;
				valuesIn=new ContentValues();
				valuesIn.put("stock",NStock);

				this.getWritableDatabase().update("articulos", valuesIn," idArticulo ="+articulosRemision.getIdArticulo(),null);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}



	}
	
	public boolean insertPagosFactura( PagosFactura pagosFactura, boolean isOnline)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			if(isOnline)
			{
				valuesIn.put("NPago", pagosFactura.getNPagoFac());
				valuesIn.put("idPagosFactura", pagosFactura.getNPagoFac());
			}
			else
			{
				valuesIn.put("NPago", pagosFactura.getNPago());
				valuesIn.put("idPagosFactura", pagosFactura.getIdPagosFactura());
			}


			valuesIn.put("NPagoFac", pagosFactura.getNPagoFac());
			valuesIn.put("NCajaQRecibe", pagosFactura.getNCajaQRecibe());
			valuesIn.put("NFactura", pagosFactura.getNFactura());
			valuesIn.put("NCaja", pagosFactura.getNCaja());
			valuesIn.put("IdCliente", pagosFactura.getIdCliente());
			valuesIn.put("Cuenta", pagosFactura.getCuenta());
			valuesIn.put("NComprobante", pagosFactura.getNComprobante());
			valuesIn.put("Fecha", pagosFactura.getFecha());
			valuesIn.put("Hora", pagosFactura.getHora());
			valuesIn.put("Fecha2", pagosFactura.getFecha2());
			valuesIn.put("Descuento", pagosFactura.getDescuento());
			valuesIn.put("RteFuente", pagosFactura.getRteFuente());
			valuesIn.put("Total", pagosFactura.getTotal());
			valuesIn.put("SaldoAnterior", pagosFactura.getSaldoAnterior());
			valuesIn.put("Saldo", pagosFactura.getSaldo());
			valuesIn.put("Observaciones", pagosFactura.getObservaciones());
			valuesIn.put("IdUsuario", pagosFactura.getIdUsuario());
			valuesIn.put("FacturasQPaga", pagosFactura.getFacturasQPaga());
			valuesIn.put("RteIca", pagosFactura.getRteIca());
			valuesIn.put("RteIva", pagosFactura.getRteIva());
			valuesIn.put("Devolucion", pagosFactura.getDevolucion());
			
			this.getWritableDatabase().insert("PagosFactura", null, valuesIn);
					  
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	public boolean insertItemPagosFactura( ItemPagoFac ipf)
	{
		try
		{
			if(ipf!=null)
			{				
				ContentValues valuesIn=new ContentValues();
				valuesIn.put("IdItemPagoFac", ipf.getIdItemPagoFac());
				valuesIn.put("idPagosFactura", ipf.getNPagoFac());
				valuesIn.put("Valor", ipf.getValor());
				valuesIn.put("FormaPago", ipf.getFormaPago());
				valuesIn.put("NCheque", ipf.getNCheque());
				valuesIn.put("Tarjeta", ipf.getTarjeta());						
				this.getWritableDatabase().insert("ItemPagoFac", null, valuesIn);
										
				
			}				  
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}


	public boolean insertPagoPrestamo( PagoPrestamo pagoPrestamo)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idPagoPrestamo", pagoPrestamo.getIdPagoPrestamo());
			valuesIn.put("idCliente", pagoPrestamo.getIdCliente());
			valuesIn.put("fecha", pagoPrestamo.getFecha());
			valuesIn.put("fecha2", pagoPrestamo.getFecha2());
			valuesIn.put("hora", pagoPrestamo.getHora());
			valuesIn.put("valor", pagoPrestamo.getValor());
			valuesIn.put("saldoAnterior", pagoPrestamo.getSaldoAnterior());
			valuesIn.put("nuevoSaldo", pagoPrestamo.getNuevoSaldo());
			valuesIn.put("Enviado", pagoPrestamo.getEnviado());
			this.getWritableDatabase().insert("PagoPrestamo", null, valuesIn);
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean insertPago( Pago pago, boolean isOnline, long NPagoFac)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			if(isOnline)
			{
				valuesIn.put("IdPago",NPagoFac);
			}
			else
			{
				valuesIn.put("IdPago", pago.getIdPago());
			}

			valuesIn.put("Valor", pago.getValor());
			valuesIn.put("Descripcion", pago.getDescripcion());
			valuesIn.put("idCliente", pago.getIdCliente());
			valuesIn.put("NPagosFacturaNoEnviados", pago.getNPagosFacNoEnviados());
			valuesIn.put("fecha", pago.getFecha());
			valuesIn.put("fecha2", pago.getFecha2());
			valuesIn.put("Enviado", pago.getEnviado());
			this.getWritableDatabase().insert("Pago", null, valuesIn);

			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean insertZFinancienra( ZFinanciera zFinanciera)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("NumZ", zFinanciera.NumZ);
			valuesIn.put("NCaja", zFinanciera.NCaja);
			valuesIn.put("Fecha", zFinanciera.Fecha);
			valuesIn.put("Hora", zFinanciera.Hora);
			valuesIn.put("FacturaInicial", zFinanciera.FacturaInicial);
			valuesIn.put("FacturaFinal", zFinanciera.FacturaFinal);
			valuesIn.put("VentasExentas", zFinanciera.VentasExentas);
			valuesIn.put("Ventas16", zFinanciera.Ventas16);
			valuesIn.put("Iva16", zFinanciera.Iva16);
			valuesIn.put("Total16", zFinanciera.Total16);
			valuesIn.put("Ventas5", zFinanciera.Ventas5);
			valuesIn.put("Iva5", zFinanciera.Iva5);
			valuesIn.put("Total5", zFinanciera.Total5);
			valuesIn.put("Ventas8", zFinanciera.Ventas8);
			valuesIn.put("Iva8", zFinanciera.Iva8);
			valuesIn.put("Total8", zFinanciera.Total8);
			valuesIn.put("NTransacciones", zFinanciera.NTransacciones);
			valuesIn.put("Ventas", zFinanciera.Ventas);
			valuesIn.put("Iva", zFinanciera.Iva);
			valuesIn.put("ImpoCmo", zFinanciera.ImpoCmo);
			valuesIn.put("Descuento", zFinanciera.Descuento);
			valuesIn.put("Total", zFinanciera.Total);	
			valuesIn.put("consulta", zFinanciera.consulta);
			valuesIn.put("Ventas19", zFinanciera.Ventas19);
			valuesIn.put("Iva19", zFinanciera.Iva19);
			valuesIn.put("Total19", zFinanciera.Total19);
			if(getValidaZFinanciera(zFinanciera.NumZ, zFinanciera.NCaja))
			{
				 this.getWritableDatabase().update("zfinanciera",valuesIn," NumZ = "+zFinanciera.NumZ+" AND NCaja = "+zFinanciera.NCaja,null);
			}
			else
			{
				  this.getWritableDatabase().insert("zfinanciera", null, valuesIn);
			}	
			
			
			if(zFinanciera.listaMendiosDePago!=null)
			{
				eliminaMediosDePago(zFinanciera.NumZ, zFinanciera.NCaja);
				
					for (int j = 0; j < zFinanciera.listaMendiosDePago.size(); j++) {
						ContentValues valuesMed=new ContentValues();
						valuesMed.put("NumZ", zFinanciera.NumZ);
						valuesMed.put("NCaja", zFinanciera.NCaja);
						valuesMed.put("MedioPago", zFinanciera.listaMendiosDePago.get(j).MedioPago);
						valuesMed.put("Valor", zFinanciera.listaMendiosDePago.get(j).Valor);
						valuesMed.put("Cuenta", zFinanciera.listaMendiosDePago.get(j).Cuenta);
						this.getWritableDatabase().insert("mediosdepago", null, valuesMed);
					}
			}	
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean insertTraslado( Traslado_in traslado_in)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", traslado_in.idCodigoInterno);
			valuesIn.put("idCodigoExterno", traslado_in.idCodigoExterno);
		    valuesIn.put("fecha", traslado_in.getFechaSqlite());
			valuesIn.put("hora", traslado_in.hora);
			valuesIn.put("razonSocial", traslado_in.razonSocial);
			valuesIn.put("representante", traslado_in.representante);
			valuesIn.put("regimenNit", traslado_in.regimenNit);
			valuesIn.put("direccionTel", traslado_in.direccionTel);
			valuesIn.put("NCaja", traslado_in.NCaja);		
			valuesIn.put("base0", traslado_in.base0);
			valuesIn.put("base5", traslado_in.base5);
			valuesIn.put("base10", traslado_in.base10);
			valuesIn.put("base14", traslado_in.base14);
			valuesIn.put("base16", traslado_in.base16);
			valuesIn.put("base19", traslado_in.base19);
			valuesIn.put("iva5", traslado_in.iva5);
			valuesIn.put("iva10", traslado_in.iva10);
			valuesIn.put("iva14", traslado_in.iva14);
			valuesIn.put("iva16", traslado_in.iva16);
			valuesIn.put("iva19", traslado_in.iva19);
			valuesIn.put("impoCmo", traslado_in.impoCmo);
			valuesIn.put("totaltraslado", traslado_in.totalTraslado);		
			valuesIn.put("IdBodegaOrigen", traslado_in.bodegaOrigen.getIdBodega());
			valuesIn.put("IdBodegaDestino", traslado_in.bodegaDestino.getIdBodega());
			valuesIn.put("bodegaOrigen", traslado_in.bodegaOrigen.getBodega());
			valuesIn.put("bodegaDestino", traslado_in.bodegaDestino.getBodega());
			valuesIn.put("dineroRecibido", traslado_in.dineroRecibido);		
			if(getValidaFactura(traslado_in.idCodigoInterno, traslado_in.idCodigoExterno))
			{
				 this.getWritableDatabase().update("traslado",valuesIn," idCodigoInterno = "+traslado_in.idCodigoInterno,null);
			}
			else
			{
				  this.getWritableDatabase().insert("traslado", null, valuesIn);
			}		  
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	/**
	 * inserta registro de detalles de pedido  en la tabla pedido _artuculos
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idFactura
	 * @param idArticulo
	 * @param cantidad
	 * @param valorUnitario
	 * @param valor
	 * @param orden
	 * @param codigo
	 * @return true o false
	 */
	public boolean insertFacturaArticulos( long idFactura, long idArticulo, double cantidad, long valorUnitario, long valor,long orden, String codigo, double stock )
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idFactura", idFactura);
			valuesIn.put("idArticulo", idArticulo);
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
			valuesIn.put("orden", orden);
			valuesIn.put("codigo", codigo);	
			valuesIn.put("stock", stock);	
		    this.getWritableDatabase().insert("factura_articulos", null, valuesIn);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean insertRemisionArticulos( long idRemision, long idArticulo, double cantidad, long valorUnitario, long valor,long orden, String codigo, double stock )
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idRemision", idRemision);
			valuesIn.put("idArticulo", idArticulo);
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
			valuesIn.put("orden", orden);
			valuesIn.put("codigo", codigo);
			valuesIn.put("stock", stock);
			this.getWritableDatabase().insert("remision_articulos", null, valuesIn);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean insertBodega( ArrayList<Bodega> listaBodegas)
	{
		try
		{		
			for (int i = 0; i < listaBodegas.size(); i++) {
			Bodega b=listaBodegas.get(i);
			ContentValues valuesIn=new ContentValues();		
			valuesIn.put("IdBodega", b.getIdBodega());
			valuesIn.put("NBodega", b.getBodega());
			valuesIn.put("Direccion", b.getDireccion());
			valuesIn.put("Telefono", b.getTelefono());
			valuesIn.put("Responsable", b.getResponsable());
			valuesIn.put("Municipio", b.getMunicipio());
			 if(this.getExisteBodega(b)==0)
		 		{	
				 this.getWritableDatabase().insert("bodega", null, valuesIn);
		 		}
			 else
			 {
				 this.getWritableDatabase().update("bodega", valuesIn," IdBodega ="+b.getIdBodega(),null);
			 }
			}
			
		   	return true;			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	
	
	
//------------------------------------------------------------------------------------------------------------	
//-----------------------------------------UPDATE-------------------------------------------------------------	
//------------------------------------------------------------------------------------------------------------	
		
	public boolean ActualizarCategorias(ArrayList<Categoria> lista)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			for (int i = 0; i < lista.size(); i++) 
			{
				Categoria categoria=(Categoria)lista.get(i);
				valuesIn=new ContentValues();
				valuesIn.put("habilitada",categoria.getHabilidada());
				valuesIn.put("fechaact", categoria.getFechaAct());	
				this.getWritableDatabase().update("categoria", valuesIn," idcategoria ="+categoria.getIdCategoria(),null);							
			}		
		return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public boolean ActualizarCategoria(Categoria categoria)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();		
				valuesIn=new ContentValues();
				valuesIn.put("habilitada",categoria.getHabilidada());
				valuesIn.put("fechaact", categoria.getFechaAct());	
				this.getWritableDatabase().update("categoria", valuesIn," idcategoria ="+categoria.getIdCategoria(),null);							
		    	return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 * metodo que actualiza el pedido en la tabla pedidos
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param pedido
	 * @return true o false
	 */
	public boolean ActualizarPedido( Pedido_in pedido)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", pedido.idCodigoInterno);
			valuesIn.put("idCodigoExterno", pedido.idCodigoExterno);
			valuesIn.put("idCliente", pedido.idCliente);
			valuesIn.put("fecha", pedido.getFechaSqlite());
			valuesIn.put("valor", pedido.valor);
			valuesIn.put("envio", pedido.getEnvio());
			valuesIn.put("observaciones", pedido.getObservaciones());
			valuesIn.put("SubTotal", pedido.getSubTotal());
			valuesIn.put("DescuentoTotal", pedido.getDescuentoTotal());
			valuesIn.put("Documento", pedido.getDocumento());
			//valuesIn.put("FormaPago", pedido.getFormaPago());
			valuesIn.put("idClienteSucursal", pedido.idClienteSucursal);
			valuesIn.put("Estado", pedido.getEstado());
		    this.getWritableDatabase().update("pedidos", valuesIn," idCodigoInterno ="+pedido.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarLibro( Libro libro)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Enviado", libro.getEnviado());
			this.getWritableDatabase().update("Libro", valuesIn," idLibro ="+libro.getIdLibro(),null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean ActualizarFactura( Factura_in facturain)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", facturain.idCodigoInterno);
			valuesIn.put("idCodigoExterno", facturain.idCodigoExterno);
			valuesIn.put("idCliente", facturain.idCliente);
			valuesIn.put("fecha", facturain.getFechaSqlite());
			valuesIn.put("hora", facturain.hora);
			valuesIn.put("valor", facturain.valor);
			valuesIn.put("razonSocial", facturain.razonSocial);
			valuesIn.put("representante", facturain.representante);
			valuesIn.put("regimenNit", facturain.regimenNit);
			valuesIn.put("direccionTel", facturain.direccionTel);
			valuesIn.put("NCaja", facturain.NCaja);
			valuesIn.put("prefijo", facturain.prefijo);
			valuesIn.put("base0", facturain.base0);
			valuesIn.put("base5", facturain.base5);
			valuesIn.put("base10", facturain.base10);
			valuesIn.put("base14", facturain.base14);
			valuesIn.put("base16", facturain.base16);
			valuesIn.put("base19", facturain.base19);
			valuesIn.put("iva5", facturain.iva5);
			valuesIn.put("iva10", facturain.iva10);
			valuesIn.put("iva14", facturain.iva14);
			valuesIn.put("iva16", facturain.iva16);
			valuesIn.put("iva19", facturain.iva19);
			valuesIn.put("impoCmo", facturain.impoCmo);
			valuesIn.put("totalFactura", facturain.totalFactura);
			valuesIn.put("resDian", facturain.resDian);
			valuesIn.put("rango", facturain.rango);
			valuesIn.put("idBodega", facturain.idBodega);
			valuesIn.put("dineroRecibido", facturain.dineroRecibido);
			valuesIn.put("nombrevendedor", facturain.NombreVendedor);
			valuesIn.put("telefonovendedor", facturain.TelefonoVendedor);
			valuesIn.put("VentaCredito", facturain.VentaCredito);
			valuesIn.put("NFactura", facturain.NFactura);
			valuesIn.put("Pagada", facturain.Pagada);
			valuesIn.put("ValorPagado", facturain.ValorPagado);
			valuesIn.put("Observaciones", facturain.observaciones);
			valuesIn.put("idClienteSucursal", facturain.idClienteSucursal);

		    this.getWritableDatabase().update("factura", valuesIn," idCodigoInterno ="+facturain.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarAnulacionFactura( Factura_in facturain)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Anulada", facturain.getAnulada());
			this.getWritableDatabase().update("factura", valuesIn," idCodigoInterno ="+facturain.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarAnulacionPedido( Pedido_in pedidoin)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Estado", pedidoin.getEstado());
			this.getWritableDatabase().update("pedidos", valuesIn," idCodigoInterno ="+pedidoin.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarRemision( Factura_in facturain)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", facturain.idCodigoInterno);
			valuesIn.put("idCodigoExterno", facturain.idCodigoExterno);
			valuesIn.put("idCliente", facturain.idCliente);
			valuesIn.put("fecha", facturain.getFechaSqlite());
			valuesIn.put("hora", facturain.hora);
			valuesIn.put("valor", facturain.valor);
			valuesIn.put("razonSocial", facturain.razonSocial);
			valuesIn.put("representante", facturain.representante);
			valuesIn.put("regimenNit", facturain.regimenNit);
			valuesIn.put("direccionTel", facturain.direccionTel);
			valuesIn.put("NCaja", facturain.NCaja);
			valuesIn.put("prefijo", facturain.prefijo);
			valuesIn.put("base0", facturain.base0);
			valuesIn.put("base5", facturain.base5);
			valuesIn.put("base10", facturain.base10);
			valuesIn.put("base14", facturain.base14);
			valuesIn.put("base16", facturain.base16);
			valuesIn.put("base19", facturain.base19);
			valuesIn.put("iva5", facturain.iva5);
			valuesIn.put("iva10", facturain.iva10);
			valuesIn.put("iva14", facturain.iva14);
			valuesIn.put("iva16", facturain.iva16);
			valuesIn.put("iva19", facturain.iva19);
			valuesIn.put("impoCmo", facturain.impoCmo);
			valuesIn.put("totalRemision", facturain.totalFactura);
			valuesIn.put("resDian", facturain.resDian);
			valuesIn.put("rango", facturain.rango);
			valuesIn.put("idBodega", facturain.idBodega);
			valuesIn.put("dineroRecibido", facturain.dineroRecibido);
			valuesIn.put("nombrevendedor", facturain.NombreVendedor);
			valuesIn.put("telefonovendedor", facturain.TelefonoVendedor);
			valuesIn.put("VentaCredito", facturain.VentaCredito);
			valuesIn.put("NRemision", facturain.NFactura);
			valuesIn.put("Pagada", facturain.Pagada);
			valuesIn.put("ValorPagado", facturain.ValorPagado);
			valuesIn.put("Observaciones", facturain.observaciones);
			valuesIn.put("idClienteSucursal", facturain.idClienteSucursal);
			this.getWritableDatabase().update("remision", valuesIn," idCodigoInterno ="+facturain.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarRemision( Remision_in remisionin)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", remisionin.idCodigoInterno);
			valuesIn.put("idCodigoExterno", remisionin.idCodigoExterno);
			valuesIn.put("idCliente", remisionin.idCliente);
			valuesIn.put("fecha", remisionin.getFechaSqlite());
			valuesIn.put("hora", remisionin.hora);
			valuesIn.put("valor", remisionin.valor);
			valuesIn.put("razonSocial", remisionin.razonSocial);
			valuesIn.put("representante", remisionin.representante);
			valuesIn.put("regimenNit", remisionin.regimenNit);
			valuesIn.put("direccionTel", remisionin.direccionTel);
			valuesIn.put("NCaja", remisionin.NCaja);
			valuesIn.put("prefijo", remisionin.prefijo);
			valuesIn.put("base0", remisionin.base0);
			valuesIn.put("base5", remisionin.base5);
			valuesIn.put("base10", remisionin.base10);
			valuesIn.put("base14", remisionin.base14);
			valuesIn.put("base16", remisionin.base16);
			valuesIn.put("base19", remisionin.base19);
			valuesIn.put("iva5", remisionin.iva5);
			valuesIn.put("iva10", remisionin.iva10);
			valuesIn.put("iva14", remisionin.iva14);
			valuesIn.put("iva16", remisionin.iva16);
			valuesIn.put("iva19", remisionin.iva19);
			valuesIn.put("impoCmo", remisionin.impoCmo);
			valuesIn.put("totalRemision", remisionin.totalRemision);
			valuesIn.put("resDian", remisionin.resDian);
			valuesIn.put("rango", remisionin.rango);
			valuesIn.put("idBodega", remisionin.idBodega);
			valuesIn.put("dineroRecibido", remisionin.dineroRecibido);
			valuesIn.put("nombrevendedor", remisionin.NombreVendedor);
			valuesIn.put("telefonovendedor", remisionin.TelefonoVendedor);
			valuesIn.put("VentaCredito", remisionin.VentaCredito);
			valuesIn.put("NRemision", remisionin.NRemision);
			valuesIn.put("Pagada", remisionin.Pagada);
			valuesIn.put("ValorPagado", remisionin.ValorPagado);
			valuesIn.put("Observaciones", remisionin.observaciones);
			valuesIn.put("idClienteSucursal", remisionin.idClienteSucursal);
			this.getWritableDatabase().update("remision", valuesIn," idCodigoInterno ="+remisionin.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean ActualizaDeudaAntFacCliente( String idCliente, long valor,String fechaAbono)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("deudaAntFac",valor);
			valuesIn.put("fechaUltimaVenta",fechaAbono);
			this.getWritableDatabase().update("Clientes", valuesIn," IdCliente ="+idCliente,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean ActualizarPagoFactura( PagosFactura pagosFactura)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("NPagoFac", pagosFactura.getNPagoFac());						
		    this.getWritableDatabase().update("PagosFactura", valuesIn," IdPagosFactura ="+pagosFactura.getIdPagosFactura(),null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarPago( Pago pago)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Enviado", pago.getEnviado());						
		    this.getWritableDatabase().update("Pago", valuesIn," IdPago ="+pago.getIdPago(),null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarFacturaPagoParcial( Factura_in facturain)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();			
			valuesIn.put("Pagada", facturain.Pagada);
			valuesIn.put("ValorPagado", facturain.ValorPagado);
		    this.getWritableDatabase().update("factura", valuesIn," NFactura ="+facturain.NFactura+" AND NCaja="+facturain.NCaja,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarRemisionPagoParcial( Factura_in facturain)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Pagada", facturain.Pagada);
			valuesIn.put("ValorPagado", facturain.ValorPagado);
			this.getWritableDatabase().update("remision", valuesIn," NRemision ="+facturain.NFactura+" AND NCaja="+facturain.NCaja,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	public boolean ActualizarTraslado( Traslado_in traslado_in)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("idCodigoInterno", traslado_in.idCodigoInterno);
			valuesIn.put("idCodigoExterno", traslado_in.idCodigoExterno);
		    valuesIn.put("fecha", traslado_in.getFechaSqlite());
			valuesIn.put("hora", traslado_in.hora);
			valuesIn.put("razonSocial", traslado_in.razonSocial);
			valuesIn.put("representante", traslado_in.representante);
			valuesIn.put("regimenNit", traslado_in.regimenNit);
			valuesIn.put("direccionTel", traslado_in.direccionTel);
			valuesIn.put("NCaja", traslado_in.NCaja);		
			valuesIn.put("base0", traslado_in.base0);
			valuesIn.put("base5", traslado_in.base5);
			valuesIn.put("base10", traslado_in.base10);
			valuesIn.put("base14", traslado_in.base14);
			valuesIn.put("base16", traslado_in.base16);
			valuesIn.put("base19", traslado_in.base19);
			valuesIn.put("iva5", traslado_in.iva5);
			valuesIn.put("iva10", traslado_in.iva10);
			valuesIn.put("iva14", traslado_in.iva14);
			valuesIn.put("iva16", traslado_in.iva16);
			valuesIn.put("iva19", traslado_in.iva19);
			valuesIn.put("impoCmo", traslado_in.impoCmo);
			valuesIn.put("totaltraslado", traslado_in.totalTraslado);		
			valuesIn.put("IdBodegaOrigen", traslado_in.bodegaOrigen.getIdBodega());
			valuesIn.put("IdBodegaDestino", traslado_in.bodegaDestino.getIdBodega());
			valuesIn.put("bodegaOrigen", traslado_in.bodegaOrigen.getBodega());
			valuesIn.put("bodegaDestino", traslado_in.bodegaDestino.getBodega());
			valuesIn.put("dineroRecibido", traslado_in.dineroRecibido);		
		    this.getWritableDatabase().update("traslado", valuesIn," idCodigoInterno ="+traslado_in.idCodigoInterno,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	/**
	 * metodo que actualiza los parametros de la aplicacion
	 * true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param parametros
	 * @return true o false
	 */
	public boolean ActualizarParametros( Parametros parametros)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("ws", parametros.ws);
			valuesIn.put("ip1", parametros.ip1);
			valuesIn.put("ip2", parametros.ip2);
			valuesIn.put("ip3", parametros.ip3);
			valuesIn.put("ip4", parametros.ip4);		
			valuesIn.put("modificaPrecio", parametros.modificaPrecio);
			valuesIn.put("usaCatalogo", parametros.getUsaCatalogo());
			valuesIn.put("consultaArticuloEnLinea", parametros.getConsultaArticuloEnLinea());
			valuesIn.put("fecha", parametros.getFechaSys2());
			valuesIn.put("administraInventario", parametros.getAdministraInventario());
			valuesIn.put("modificaStock", parametros.getModificaStock());
			valuesIn.put("bodegaAdmInvOmision", parametros.getBodegaAdmInvOmision());
			valuesIn.put("realizaPedidos", parametros.getRealizaPedidos());
			valuesIn.put("bodegaPedidosOmision", parametros.getBodegaPedidosOmision());
			valuesIn.put("realizaFactura", parametros.getRealizaFactura());
			valuesIn.put("bodegaFacturaOmision", parametros.getBodegaFacturaOmision());
			valuesIn.put("caja", parametros.caja);
			valuesIn.put("usaPrintZebra", parametros.getUsaImpresoraZebra());
			valuesIn.put("macAdd", parametros.getMacAdd());
			valuesIn.put("realizaTranslados", parametros.getRealizaTranslados());
			valuesIn.put("bodegaTransladosOmision", parametros.getBodegaTransladosOmision());
			valuesIn.put("ruta", parametros.ruta);
			valuesIn.put("generaCierre", parametros.getGeneraCierre());
			valuesIn.put("consultaZ", parametros.getConsultaZ());
			valuesIn.put("usaWSCash", parametros.getUsaWSCash());
			valuesIn.put("realizaPedidosMesa", parametros.getRealizaPedidosMesa());
			valuesIn.put("usaTodasLasCategorias", parametros.getUsaTodasLasCategorias());
			valuesIn.put("permiteStocken0", parametros.getPermiteStocken0());
			valuesIn.put("precioLibre", parametros.getPrecioLibre());
			valuesIn.put("FacturaOnLine", parametros.getFacturaOnLine());
			valuesIn.put("RazonSocial", parametros.getRazonSocial());
			valuesIn.put("Representante", parametros.getRepresentante());
			valuesIn.put("RegimenNit", parametros.getRegimenNit());
			valuesIn.put("DireccionTel", parametros.getDireccionTel());
			valuesIn.put("ResDian", parametros.getResDian());
			valuesIn.put("Rango", parametros.getRango());
			valuesIn.put("NombreVendedor", parametros.getNombreVendedor());	 
			valuesIn.put("Prefijo", parametros.getPrefijo());
			valuesIn.put("UsaObservMasMenos", parametros.getUsaObservMasMenos());
			valuesIn.put("DescuentoPedido", parametros.getDescuentoPedido());
            valuesIn.put("ImprimePedido", parametros.getImprimePedido());
			valuesIn.put("ConsultaCosto", parametros.getConsultaCosto());
            valuesIn.put("usaPrintEpson", parametros.getUsaPrintEpson());
            valuesIn.put("macAddEpson", parametros.getMacAddEpson());
			valuesIn.put("usaCantDecimal", parametros.getUsaCantDecimal() );
			valuesIn.put("usaSelecMultipleArt", parametros.getUsaSelecMultipleArt() );
			valuesIn.put("precioMinimo", parametros.getPrecioMinimo() );
			valuesIn.put("usaPrintBixolon", parametros.getUsaPrintBixolon() );
			valuesIn.put("macAddBixolon", parametros.getMacAddBixolon() );
			valuesIn.put("CarteraOnLine", parametros.getCarteraOnLine() );
			valuesIn.put("ControlaPrecioLibre", parametros.getControlaPrecioLibre() );
			valuesIn.put("SelectDocumentoPedido", parametros.getSelectDocumentoPedido() );
			valuesIn.put("RealizaAlistamiento", parametros.getRealizaAlistamiento() );
			valuesIn.put("SelectFormaPagoPedido", parametros.getSelectFormaPagoPedido() );
			valuesIn.put("UsaPrestamos", parametros.getUsaPrestamos() );
			valuesIn.put("RealizaRemision", parametros.getRealizaRemision());
			valuesIn.put("bodegaRemisionOmision", parametros.getBodegaRemisionOmision() );
			valuesIn.put("ModificaValorTotal", parametros.getModificaValorTotal() );
			valuesIn.put("Webid", parametros.getWebid2() );
			valuesIn.put("usaPrintDigitalPos", parametros.getUsaPrintDigitalPos() );
			valuesIn.put("macAddDigitalPos", parametros.getMacAddDigitalPos() );
			valuesIn.put("descuentaStockEnPedido", parametros.getDescuentaStockEnPedido());
			valuesIn.put("usaTipoPedido", parametros.getUsaTipoPedido());
			valuesIn.put("permiteStocken0EnPedido", parametros.getPermiteStocken0EnPedido());

            this.getWritableDatabase().update("parametro", valuesIn," ws ='"+parametros.ws+"'",null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	/**
	 * metodo que actualiza un articulo del pedido al momento de realizar cambios en la cantidad.
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idPedido
	 * @param idArticulo
	 * @param cantidad
	 * @param valorUnitario
	 * @param valor
	 * @return true o false
	 */
	public boolean ActualizarPedidoArticulo( long idPedido, long idArticulo, double cantidad, long valorUnitario, long valor)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);


		    this.getWritableDatabase().update("pedidos_articulos", valuesIn," idPedido ="+idPedido+" AND idArticulo ="+idArticulo,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean ActualizarObservacionPedidoArticulo(String observacion, long idPedido, long idArticulo)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("Observacion", observacion);


			this.getWritableDatabase().update("pedidos_articulos", valuesIn," idPedido ="+idPedido+" AND idArticulo ="+idArticulo,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarFacturaArticulo( long idFactura, long idArticulo, double cantidad, long valorUnitario, long valor)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
		    this.getWritableDatabase().update("factura_articulos", valuesIn," idFactura ="+idFactura+" AND idArticulo ="+idArticulo,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarRemisionArticulo( long idRemision, long idArticulo, double cantidad, long valorUnitario, long valor)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
			this.getWritableDatabase().update("remision_articulos", valuesIn," idRemision ="+idRemision+" AND idArticulo ="+idArticulo,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean ActualizarTrasladoArticulo( long idTraslado, long idArticulo, double cantidad, long valorUnitario, long valor)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("cantidad", cantidad);
			valuesIn.put("valorUnitario", valorUnitario);
			valuesIn.put("valor", valor);
		    this.getWritableDatabase().update("traslado_articulos", valuesIn," idTraslado ="+idTraslado+" AND idArticulo ="+idArticulo,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	/**
	 * metodo que actualiza un articulo del pedido al momento de realizar cambios en la cantidad.
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idPedido
	 * @param idArticulo
	 * @param cantidad
	 * @param valorUnitario
	 * @param valor
	 * @return true o false
	 */
	
	
	/**
	 * metodo que actualiza la fecha ultimo pedido y ultima visita del cliente, al realizar un pedido o registrar la visita
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param cliente
	 * @param registraVisita
	 * @return true o false
	 */
	public boolean ActualizarCliente( Cliente cliente, boolean registraVisita,boolean registraVenta)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			
			if(!registraVisita)
			{
				valuesIn.put("fechaUltimoPedido", cliente.fechaUltimoPedido);
			}	
			if(registraVenta)
			{
				valuesIn.put("fechaUltimaVenta", cliente.fechaUltimaVenta);
			}	
			valuesIn.put("fechaUltimaVisita", cliente.fechaUltimaVisita);
			valuesIn.put("MotivoUltimaVisita", cliente.MotivoUltimaVisita);
		    this.getWritableDatabase().update("clientes", valuesIn," idCliente ="+cliente.idCliente,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	/**
	 * metodo que actualiza el campo ubicado en la tabla cliente que determina si ya esta localizado en el sistema
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param cliente
	 * @return true o false
	 */
	public boolean ActualizarClienteUbicacion( Cliente cliente)
	{
		try
		{
			ContentValues valuesIn=new ContentValues();
			valuesIn.put("ubicado", cliente.ubicado);
		    this.getWritableDatabase().update("clientes", valuesIn," idCliente ="+cliente.idCliente,null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
//------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------SELECT-----------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------


	
	
	/**
	 * valida si el usuario se encuentra registrado en la base de datos para el control de acceso	
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param cont
	 * @param cedula
	 * @param clave
	 * @return true o false
	 */
	public boolean getValidaUsuario(Context cont, String cedula, String clave)
	{
		boolean res=false;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
					   "FROM usuarios "+
					   "WHERE cedula = '"+cedula+"' "+
					   "AND clave = '"+clave+"'";
		Cursor c= bds.rawQuery(query,null);	
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)==1)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		bd.close();
		return res;
	}
	
	public boolean getValidaCategoria(long idCategoria)
	{
		boolean res=false;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
					   "FROM categoria "+
					   "WHERE idCategoria = '"+idCategoria+"' ";
		Cursor c= bds.rawQuery(query,null);	
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)==1)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		this.close();
		return res;
	}
	
	public boolean getValidaCatalogo(long idCatalogo)
	{
		boolean res=false;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
					   "FROM catalogo "+
					   "WHERE idCatalogo = '"+idCatalogo+"' ";
		Cursor c= bds.rawQuery(query,null);	
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)==1)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		this.close();
		return res;
	}
	
	public boolean getValidaFechaZFinanciera(String fecha)
	{
		boolean res=false;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
					   "FROM zfinanciera "+
					   "WHERE fecha = '"+fecha+"' ";
		Cursor c= bds.rawQuery(query,null);	
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)==1)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		this.close();
		return res;
	}

	/**
	 * Metodo utilizado para validar si existe algun cierre realizado en el dia
	 * @param fecha recibe la fecha actual
	 * @return valor booleano q
	 * modificado 2020-10-13
	 */
	public boolean getValidaFechaCierreTurno(String fecha)
	{
		boolean res=false;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();

		String query = "SELECT COUNT(*) "+
				"FROM CierreTurno "+
				"WHERE fecha2 = '"+fecha+"' ";
		Cursor c= bds.rawQuery(query,null);
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)>0)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		this.close();
		return res;
	}
	/**
	 * metodo que valida si existen facturas pendientes por realizar
	 * @return valor booleano q
	 * modificado 2020-10-13
	 */
	public CierreTurno  obtenerUltimoCierreTurno( String NCaja)
	{
	try {
		// Inicializa ultimo CierreTurno
		CierreTurno cierreTurno = new CierreTurno();
		cierreTurno.setNCierre("0");
		cierreTurno.setNFacturaInicial("0");
		cierreTurno.setNFacturaFinal("0");


		SQLiteDatabase bds = this.getWritableDatabase();

		Cursor c = bds.rawQuery("SELECT IdCierreTurno, NFacturaInicial,NFacturaFinal, NCaja, NCierre, Fecha2, Fecha,  Hora, Valor ,Transacciones, Vendedor \n" +
				"    FROM    CierreTurno\n" +
				"    WHERE   NCaja=" + NCaja + " AND NCierre = (SELECT MAX(cast(NCierre as integer))  FROM CierreTurno WHERE   NCaja=" + NCaja + " )", null);
		if (c.moveToFirst()) {

			cierreTurno.setIdCierreTurno(c.getLong(0));
			cierreTurno.setNFacturaInicial(c.getString(1));
			cierreTurno.setNFacturaFinal(c.getString(2));
			cierreTurno.setNCaja(c.getString(3));
			cierreTurno.setNCierre(c.getString(4));
			cierreTurno.setFecha2(c.getString(5));
			cierreTurno.setFecha(c.getString(6));
			cierreTurno.setHora(c.getString(7));
			cierreTurno.setValor(c.getString(8));
			cierreTurno.setTransacciones(c.getString(9));
			cierreTurno.setVendedor(c.getString(10));
		}

		return cierreTurno;
	}
	catch (Exception e)
	{
		return null;
	}

	}

	public CierreTurno  obtenerCierreTurno( String NCaja, String NCierre)
	{
		try {
			// Inicializa ultimo CierreTurno
			CierreTurno cierreTurno = new CierreTurno();
			cierreTurno.setNCierre("0");
			cierreTurno.setNFacturaInicial("0");
			cierreTurno.setNFacturaFinal("0");


			SQLiteDatabase bds = this.getWritableDatabase();

			Cursor c = bds.rawQuery("SELECT IdCierreTurno, NFacturaInicial,NFacturaFinal, NCaja, NCierre, Fecha2, Fecha,  Hora, Valor ,Transacciones, Vendedor \n" +
					"    FROM    CierreTurno\n" +
					"    WHERE   NCaja=" + NCaja + " AND NCierre =" + NCierre, null);
			if (c.moveToFirst()) {

				cierreTurno.setIdCierreTurno(c.getLong(0));
				cierreTurno.setNFacturaInicial(c.getString(1));
				cierreTurno.setNFacturaFinal(c.getString(2));
				cierreTurno.setNCaja(c.getString(3));
				cierreTurno.setNCierre(c.getString(4));
				cierreTurno.setFecha2(c.getString(5));
				cierreTurno.setFecha(c.getString(6));
				cierreTurno.setHora(c.getString(7));
				cierreTurno.setValor(c.getString(8));
				cierreTurno.setTransacciones(c.getString(9));
				cierreTurno.setVendedor(c.getString(10));
			}

			return cierreTurno;
		}
		catch (Exception e)
		{
			return null;
		}

	}

	public CierreTurno  obtenerDatosNuevoCierreTurno( CierreTurno nuevoCierreTurno, CierreTurno ultimoCierreTurno)
	{
		long id=0;
		SQLiteDatabase bds=this.getWritableDatabase();

		//Asigna factura inicial
		nuevoCierreTurno.setNFacturaInicial(ultimoCierreTurno.getNFacturaInicialNuevoCierreTurno());

		//Obtiene ultimo numero de factura de la caja

		Cursor c= bds.rawQuery("SELECT MAX(NFactura)  FROM Factura WHERE  NCaja="+nuevoCierreTurno.getNCaja()+" ",null);
		if(c.moveToFirst())
		{
			nuevoCierreTurno.setNFacturaFinal(c.getString(0));
		}
		c.close();
		//Obtiene suma de facturas nuevo cierre
		c= bds.rawQuery("SELECT SUM(totalFactura)  FROM Factura WHERE  NCaja="+nuevoCierreTurno.getNCaja()+" AND NFactura > "+ultimoCierreTurno.getNFacturaFinal()+" AND NFactura<="+nuevoCierreTurno.getNFacturaFinal(),null);
		if(c.moveToFirst())
		{
			nuevoCierreTurno.setValor(c.getString(0));
		}
		c.close();
		return nuevoCierreTurno;
	}
	/**
	 * metodo que valida si existen facturas pendientes por realizar
	 * @return valor booleano q
	 * modificado 2020-10-13
	 */
	public long  obtenerNumeroFacturasCierreTurno( String NCaja, CierreTurno cierreTurno)
	{
		long id=0;
		String consulta ="";


		SQLiteDatabase bds=this.getWritableDatabase();

		consulta="select Count(NFactura) from Factura"+
				" Where NCaja="+ NCaja +
				" AND NFactura > "+cierreTurno.getNFacturaFinal();

		Cursor c= bds.rawQuery(consulta,null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}

		return id;
	}
		/**
		 *  Valida Si el cliente existe en la base de datos	
		 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
		 * @param idCliente
		 * @return true o false
		 */
		public boolean getValidaCliente(long idCliente)
		{
			boolean res=false;
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			
			String query = "SELECT COUNT(*) "+
						   "FROM clientes "+
						   "WHERE idCliente = '"+idCliente+"' ";
			Cursor c= bds.rawQuery(query,null);	
			try
			{
				if(c.moveToFirst())
				{
					if(c.getLong(0)==1)
					{
						res=true;
					}
				}
			}
			catch(Exception e)
			{
				res=false;
			}
			this.close();
			return res;
		}
		/**
		 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
		 * Valida Si el Articulo existe en la base de datos	

		 * @return true si existe de lo contrario retorna false
		 */
			public boolean getValidaPedido(long idCodigoInterno, long idCodigoExterno)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT COUNT(*) "+
							   "FROM pedidos "+
							   "WHERE idCodigoInterno = '"+idCodigoInterno+"' " 
							   //"AND idCodigoExterno = '"+idCodigoExterno+"' "
							   ;
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)>0)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}
			public boolean getValidaFactura(long idCodigoInterno, long idCodigoExterno)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT COUNT(*) "+
							   "FROM factura "+
							   "WHERE idCodigoInterno = '"+idCodigoInterno+"' " +
							   "AND idCodigoExterno = '"+idCodigoExterno+"' ";
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)>0)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}
			public boolean getValidaZFinanciera(long zFinanciera, long nCaja)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT COUNT(*) "+
							   "FROM zfinanciera "+
							   "WHERE NumZ = '"+zFinanciera+"' " +
							   "AND NCaja = '"+nCaja+"' ";
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)>0)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}

		/**
		 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
		 * Valida Si el Articulo existe en la base de datos	

		 * @return true si existe de lo contrario retorna false
		 */
			public boolean getValidaPagoFacturaEnviado(long idPagoFactura)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT NPagoFac "+
							   "FROM PagosFactura "+
							   "WHERE idPagosFactura ="+idPagoFactura ;
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)==1)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}
			
			public boolean getValidaFacturaEnviada(long NFactura, long NCaja)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT idCodigoExterno "+
							   " FROM Factura "+
							   " WHERE NFactura="+NFactura+
							   " AND NCaja="+NCaja;
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)>0)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}

	public boolean getValidaRemisionEnviada(long NFactura, long NCaja)
	{
		boolean res=false;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();

		String query = "SELECT idCodigoExterno "+
				" FROM Remision "+
				" WHERE NRemision="+NFactura+
				" AND NCaja="+NCaja;
		Cursor c= bds.rawQuery(query,null);
		try
		{
			if(c.moveToFirst())
			{
				if(c.getLong(0)>0)
				{
					res=true;
				}
			}
		}
		catch(Exception e)
		{
			res=false;
		}
		this.close();
		return res;
	}
			
			public boolean getValidaArticulo(long idArticulo)
			{
				boolean res=false;
				this.openDB();
				SQLiteDatabase bds=this.getWritableDatabase();
				
				String query = "SELECT COUNT(*) "+
							   "FROM articulos "+
							   "WHERE idArticulo = '"+idArticulo+"' ";
				Cursor c= bds.rawQuery(query,null);	
				try
				{
					if(c.moveToFirst())
					{
						if(c.getLong(0)==1)
						{
							res=true;
						}
					}
				}
				catch(Exception e)
				{
					res=false;
				}
				this.close();
				return res;
			}

			/**
			 * metodo que retorna el numero de pedidos represados en la base de datos de una fecha determinada

			 * @return res
			 */
				public int getNumeroPedidosRepresados(String fechaDesde,String fechaHasta)
				{
					int res=0;
					this.openDB();
					SQLiteDatabase bds=this.getWritableDatabase();
					
					String query = "SELECT count(*)"+
								   " FROM pedidos "+
								   " WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
								   //" AND idCodigoExterno = 0";
								   " AND envio = 0";
					Cursor c= bds.rawQuery(query,null);	
					try
					{
						if(c.moveToFirst())
						{						
							res=c.getInt(0);							
						}
					}
					catch(Exception e)
					{
						res=0;
					}
					this.close();
					return res;
				}

	public int getNumeroLibrosRepresados(String fechaDesde,String fechaHasta)
	{
		int res=0;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();

		String query = "SELECT count(*)"+
				" FROM Libro "+
				" WHERE fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
				" AND Enviado = 0";
		Cursor c= bds.rawQuery(query,null);
		try
		{
			if(c.moveToFirst())
			{
				res=c.getInt(0);
			}
		}
		catch(Exception e)
		{
			res=0;
		}
		this.close();
		return res;
	}
	/**
				 * metodo que retorna el numero de pedidos represados en la base de datos de una fecha determinada

				 * @return res
				 */
					public int getNumeroFacturasRepresados(String fechaDesde, String fechaHasta )
					{
						int res=0;
						this.openDB();
						SQLiteDatabase bds=this.getWritableDatabase();
						
						String query = "SELECT count(*)"+
									   " FROM factura "+
									   " WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
									   " AND idCodigoExterno = 0";
						Cursor c= bds.rawQuery(query,null);	
						try
						{
							if(c.moveToFirst())
							{						
								res=c.getInt(0);							
							}
						}
						catch(Exception e)
						{
							res=0;
						}
						this.close();
						return res;
					}

	public int getNumeroRemisionesRepresadas(String fechaDesde, String fechaHasta )
	{
		int res=0;
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();

		String query = "SELECT count(*)"+
				" FROM remision "+
				" WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
				" AND idCodigoExterno = 0";
		Cursor c= bds.rawQuery(query,null);
		try
		{
			if(c.moveToFirst())
			{
				res=c.getInt(0);
			}
		}
		catch(Exception e)
		{
			res=0;
		}
		this.close();
		return res;
	}
					public int getNumeroPagosRepresados(String fechaDesde, String fechaHasta )
					{
						int res=0;
						this.openDB();
						SQLiteDatabase bds=this.getWritableDatabase();
						
						String query = "SELECT count(*)"+
									   " FROM Pago "+
									   " WHERE fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
									   " AND Enviado = 0";
						Cursor c= bds.rawQuery(query,null);	
						try
						{
							if(c.moveToFirst())
							{						
								res=c.getInt(0);							
							}
						}
						catch(Exception e)
						{
							res=0;
						}
						this.close();
						return res;
					}
					
					public int getNumeroTrasladosRepresados(String fechaDesde,String fechaHasta)
					{
						int res=0;
						this.openDB();
						SQLiteDatabase bds=this.getWritableDatabase();
						
						String query = "SELECT count(*)"+
									   " FROM traslado "+
									   " WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
									   " AND idCodigoExterno = 0";
						Cursor c= bds.rawQuery(query,null);	
						try
						{
							if(c.moveToFirst())
							{						
								res=c.getInt(0);							
							}
						}
						catch(Exception e)
						{
							res=0;
						}
						this.close();
						return res;
					}
				
	
	/**
	 * metodo que retorna el ultimo identificador de pedido registrado en la base de datos
	 * @param cont
	 * @return id
	 */
	public int obtenerUltimoIdPedido(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(idCodigoInterno) from pedidos",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}

	public double getStockArticulo( long idArticulo)
	{
		double id=1;
		SQLiteDatabase bds=this.getWritableDatabase();
		Cursor c= bds.rawQuery("select stock from articulos where IdArticulo="+idArticulo,null);
		if(c.moveToFirst())
		{
			id= c.getDouble(0) ;
		}
		return id;
	}
	public int obtenerUltimoIdFactura(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(idCodigoInterno) from factura",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}

	public int obtenerUltimoIdRemision(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select MAX(idCodigoInterno) from Remision",null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		bd.close();
		return id;
	}
	public int obtenerUltimoIdPagosFactura(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(idPagosFactura) from PagosFactura",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}
	
	public int obtenerUltimoIdItemPagoFac(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(IdItemPagoFac) from ItemPagoFac",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}
	public int obtenerUltimoIdPago(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(IdPago) from Pago",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}
	public int obtenerUltimoNFactura(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(NFactura) from factura",null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		else
		{
			id=0;
		}
		bd.close();
		return id;
	}
	public int obtenerUltimoNRemision(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select MAX(NRemision) from remision",null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		else
		{
			id=0;
		}
		bd.close();
		return id;
	}
	public int obtenerUltimoIdTraslado(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();		
		
		Cursor c= bds.rawQuery("select MAX(idCodigoInterno) from traslado",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
		bd.close();
		return id;
	}
	public int obtenerUltimaNCierreTurno(long NCaja)
	{
		int id=1;
		SQLiteDatabase bds=this.getWritableDatabase();	
		try
		{	
		Cursor c= bds.rawQuery("select MAX(cast(NCierre as integer)) from CierreTurno WHERE NCaja="+NCaja+" ",null);
		if(c.moveToFirst())
			{
				id= c.getInt(0);
			}	
		}
		catch (Exception e) {
			id=0;
		}
		return id;
	}
	
	/**
	 * metodo que retorna el ultimo identificador de pedido registrado en la base de datos
	 * @param cont
	 * @return id
	 */

	
	/**
	 * metodo que retorna el identificador de orden del ultimo articulo registrado a un pedido determinado
	 * @param cont
	 * @param idPedido
	 * @return id
	 */
	public long obtenerUltimoPedidoArticulo(Context cont, long idPedido)
	{
		long id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT MAX(orden) "+
						       " FROM pedidos_articulos "+
						       " WHERE idPedido ="+idPedido;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getLong(0);
					}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}
	public long obtenerUltimoFacturaArticulo(Context cont, long idFactura)
	{
		long id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT MAX(orden) "+
						       " FROM factura_articulos "+
						       " WHERE idFactura ="+idFactura;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getLong(0);
					}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}

	public long obtenerUltimoRemisionArticulo(Context cont, long idRemision)
	{
		long id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
			SQLiteDatabase bds=bd.getWritableDatabase();

			String query = "SELECT MAX(orden) "+
					" FROM remision_articulos "+
					" WHERE idRemision ="+idRemision;

			Cursor c= bds.rawQuery(query,null);
			if(c.moveToFirst())
			{
				id= c.getLong(0);
			}
			bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}
	
	public long obtenerUltimoTrasladoArticulo(Context cont, long idTraslado)
	{
		long id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT MAX(orden) "+
						       " FROM traslado_articulos "+
						       " WHERE idTraslado ="+idTraslado;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getLong(0);
					}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}

	public int obtenerUltimoidPrestamo(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select MAX(idPrestamo) from Prestamo",null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		bd.close();
		return id;
	}

	public int obtenerUltimoidPagoPrestamo(Context cont)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select MAX(idPagoPrestamo) from PagoPrestamo",null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		bd.close();
		return id;
	}

	public long obtenerUltimoidLibro(Context cont)
	{
		long id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select MAX(IdLibro) from Libro",null);
		if(c.moveToFirst())
		{
			id= c.getLong(0);
		}
		bd.close();

		return id;
	}
	public int obtenerUltimoidNLibroCliente(Context cont, String idCliente)
	{
		int id=1;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		Cursor c= bds.rawQuery("select IFNULL(MAX(NLibro),0) from Libro where idcliente="+idCliente,null);
		if(c.moveToFirst())
		{
			id= c.getInt(0);
		}
		bd.close();
		return id;
	}


	/**
	 * metodo que retorna el identificador de orden del ultimo articulo registrado a un pedido determinado
	 * @param cont
	 * @param idPedido
	 * @return id
	 */
	
	
	/**
	 * metodo que retorna la catidad registrada de un articulo en un pedido determinado
	 * @param cont
	 * @param idPedido
	 * @param idArticulo
	 * @return
	 */
	public double getCantidadSiExistePedido(Context cont, long idPedido, long idArticulo)
	{
		double id=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT cantidad " +
							   " FROM pedidos_articulos " +
							   " WHERE idPedido=" +idPedido+
							   " AND idArticulo="+idArticulo;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getDouble(0) ;
					}
				else
				{
					id=0;
				}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}
	
	/**
	 * metodo que retorna la catidad registrada de un articulo en un pedido determinado
	 * @param cont
	 * @param idFactura
	 * @param idArticulo
	 * @return
	 */
	public double getCantidadSiExisteFactura(Context cont, long idFactura, long idArticulo)
	{
		double id=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT cantidad " +
							   " FROM factura_articulos " +
							   " WHERE idFactura=" +idFactura+
							   " AND idArticulo="+idArticulo;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getDouble(0);
					}
				else
				{
					id=0;
				}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}

	public int getCantidadSiExisteRemision(Context cont, long idRemision, long idArticulo)
	{
		int id=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
			SQLiteDatabase bds=bd.getWritableDatabase();

			String query = "SELECT cantidad " +
					" FROM remision_articulos " +
					" WHERE idRemision=" +idRemision+
					" AND idArticulo="+idArticulo;

			Cursor c= bds.rawQuery(query,null);
			if(c.moveToFirst())
			{
				id= c.getInt(0);
			}
			else
			{
				id=0;
			}
			bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}
	public int getCantidadSiExisteTraslado(Context cont, long idTraslado, long idArticulo)
	{
		int id=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		try
		{
				SQLiteDatabase bds=bd.getWritableDatabase();		
				
				String query = "SELECT cantidad " +
							   " FROM traslado_articulos " +
							   " WHERE idTraslado=" +idTraslado+
							   " AND idArticulo="+idArticulo;
						   	
				Cursor c= bds.rawQuery(query,null);
				if(c.moveToFirst())
					{
						id= c.getInt(0);
					}
				else
				{
					id=0;
				}
				bd.close();
		}
		catch(Exception e)
		{
			id=0;
		}
		return id;
	}
	
	/**
	 * metodo que retorna el numero de parametros que se encuentran registrados en la base de datos
	 * @param cont
	 * @return valor
	 */
	public long getExisteParametros(Context cont)
	{
		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
				       "FROM parametro ";				      
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	/**
	 * metodo que retorna los parametros  definidos para un servicio web determinado
	 * @param cont
	 * @param ws
	 * @return parametros
	 */
	public Parametros getParametros(Context cont, String ws)
	{
		
		Parametros parametros=new Parametros();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();	
		
		String query = "SELECT ws, ip1, ip2, ip3, ip4, modificaPrecio, usaCatalogo" +
					   ",consultaArticuloEnLinea , fecha, administraInventario, modificaStock" +
					   ",bodegaAdmInvOmision ,realizaPedidos ,bodegaPedidosOmision ,realizaFactura" +
					   ",bodegaFacturaOmision ,caja ,usaPrintZebra ,macAdd ,realizaTranslados" +
					   ",bodegaTransladosOmision ,ruta, generaCierre, consultaZ ,usaWSCash, realizaPedidosMesa, usaTodasLasCategorias, permiteStocken0, precioLibre" +
					   ",FacturaOnLine ,RazonSocial ,Representante ,RegimenNit ,DireccionTel ,ResDian ,Rango ,NombreVendedor, Prefijo, UsaObservMasMenos, DescuentoPedido, ImprimePedido, ConsultaCosto" +
                       ",usaPrintEpson, macAddEpson, usaCantDecimal, usaSelecMultipleArt, precioMinimo, usaPrintBixolon, macAddBixolon, CarteraOnLine ,ControlaPrecioLibre, SelectDocumentoPedido  , RealizaAlistamiento, SelectFormaPagoPedido, UsaPrestamos, RealizaRemision, bodegaRemisionOmision "+
				       ", ModificaValorTotal, Webid, usaPrintDigitalPos, macAddDigitalPos, descuentaStockEnPedido, usaTipoPedido, permiteStocken0EnPedido  "+
				       " FROM parametro " +
				       " WHERE ws ='"+ws+"' ";


		try
	    {


			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					parametros.ws=c.getString(0);
					parametros.ip1=c.getString(1);
					parametros.ip2=c.getString(2);
					parametros.ip3=c.getString(3);
					parametros.ip4=c.getString(4);
					parametros.setModificaPrecio(c.getLong(5));
					parametros.setUsaCatalogo(c.getLong(6));
					parametros.setConsultaArticuloEnLinea(c.getLong(7));
					parametros.setFechaSys2(c.getString(8));
					parametros.setAdministraInventario(c.getLong(9));
					parametros.setModificaStock(c.getLong(10));
					parametros.setBodegaAdmInvOmision(c.getLong(11));
					parametros.setRealizaPedidos(c.getLong(12));
					parametros.setBodegaPedidosOmision(c.getLong(13));
					parametros.setRealizaFactura(c.getLong(14));
					parametros.setBodegaFacturaOmision(c.getLong(15));
					parametros.setCaja(c.getLong(16));
					parametros.setUsaImpresoraZebra(c.getLong(17));
					parametros.setMacAdd(c.getString(18));
					parametros.setRealizaTranslados(c.getLong(19));
					parametros.setBodegaTransladosOmision(c.getLong(20));
					parametros.setRuta(c.getString(21));	
					parametros.setGeneraCierre(c.getLong(22));
					parametros.setConsultaZ(c.getLong(23));
					parametros.setUsaWSCash(c.getLong(24));
					parametros.setRealizaPedidosMesa(c.getLong(25));
					parametros.setUsaTodasLasCategorias(c.getLong(26));
					parametros.setPermiteStocken0(c.getLong(27));
					parametros.setPrecioLibre(c.getLong(28));
					parametros.setFacturaOnLine(c.getLong(29));
					parametros.setRazonSocial(c.getString(30));
					parametros.setRepresentante(c.getString(31));
					parametros.setRegimenNit(c.getString(32));
					parametros.setDireccionTel(c.getString(33));
					parametros.setResDian(c.getString(34));
					parametros.setRango(c.getString(35));
					parametros.setNombreVendedor(c.getString(36));
					parametros.setPrefijo(c.getString(37));
					parametros.setUsaObservMasMenos(c.getLong(38));
					parametros.setDescuentoPedido(c.getLong(39));
                    parametros.setImprimePedido(c.getLong(40));
					parametros.setConsultaCosto(c.getLong(41));
                    parametros.setUsaPrintEpson(c.getLong(42));
                    parametros.setMacAddEpson(c.getString(43));
					parametros.setUsaCantDecimal(c.getLong(44));
					parametros.setUsaSelecMultipleArt(c.getLong(45));
					parametros.setPrecioMinimo(c.getLong(46));
					parametros.setUsaPrintBixolon(c.getLong(47));
					parametros.setMacAddBixolon(c.getString(48));
					parametros.setCarteraOnLine(c.getLong(49)) ;
					parametros.setControlaPrecioLibre(c.getLong(50)) ;
					parametros.setSelectDocumentoPedido(c.getLong(51)) ;
					parametros.setRealizaAlistamiento(c.getLong(52)) ;
					parametros.setSelectFormaPagoPedido(c.getLong(53)) ;
					parametros.setUsaPrestamos(c.getLong(54)) ;
					parametros.setRealizaRemision(c.getLong(55)) ;
					parametros.setBodegaRemisionOmision(c.getLong(56)) ;
					parametros.setModificaValorTotal(c.getLong(57));
					parametros.setWebid(c.getLong(58));
					parametros.setUsaPrintDigitalPos(c.getLong(59));
					parametros.setMacAddDigitalPos(c.getString(60));
					parametros.setDescuentaStockEnPedido(c.getLong(61));
					parametros.setUsaTipoPedido(c.getLong(62));
					parametros.setPermiteStocken0EnPedido(c.getLong(63));




                }
				bd.close();
				return parametros;
	    }
	    catch(Exception e)
	    {
	    	return parametros;
	    }
	}
	
	public ArrayList <Categoria> getCategorias(boolean habilitadas)
	{
		ArrayList<Categoria> lista=new ArrayList<Categoria>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT idcategoria, nombre, fechaact, activo, habilitada "+
				   "FROM categoria "+
				   "ORDER BY nombre";
		if (habilitadas) {
	    query = "SELECT idcategoria, nombre, fechaact, activo, habilitada "+
					   "FROM categoria "+
					   "WHERE habilitada=1 "+
					   "ORDER BY nombre";
		}	
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Categoria categoria=new Categoria();
			categoria.setIdCategoria(Long.parseLong(c.getString(0)));
			categoria.setNombre(c.getString(1));
			categoria.setFechaAct(c.getString(2));
			categoria.setActivo(Long.parseLong(c.getString(3)));
			categoria.setHabilidada(Long.parseLong(c.getString(4)));
			lista.add(categoria);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public ArrayList <Categoria> getCategoriasVerResultados()
	{
		ArrayList<Categoria> lista=new ArrayList<Categoria>();
		try
		{
			Categoria categoria2=new Categoria();
			categoria2.setIdCategoria(0);
			categoria2.setNombre("TODAS");
			categoria2.setFechaAct("20200101");
			categoria2.setActivo(1);
			categoria2.setHabilidada(1);
			lista.add(categoria2);




			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT idcategoria, nombre, fechaact, activo, habilitada "+
					"FROM categoria "+
					"ORDER BY nombre";

			Cursor c= bds.rawQuery(query,null);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Categoria categoria=new Categoria();
				categoria.setIdCategoria(Long.parseLong(c.getString(0)));
				categoria.setNombre(c.getString(1));
				categoria.setFechaAct(c.getString(2));
				categoria.setActivo(Long.parseLong(c.getString(3)));
				categoria.setHabilidada(Long.parseLong(c.getString(4)));
				lista.add(categoria);
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public ArrayList <Catalogo> getCatalogos()
	{
		ArrayList<Catalogo> lista=new ArrayList<Catalogo>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT idCatalogo, nombre, activo "+
					   " FROM catalogo "+
					   " ORDER BY nombre";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Catalogo catalogo=new Catalogo();
			catalogo.setIdCatalogo(Long.parseLong(c.getString(0)));
			catalogo.setNombre(c.getString(1));
			catalogo.setActivo(Long.parseLong(c.getString(2)));
			lista.add(catalogo);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * metodo que retorna el numero total de clientes a visitar para un determinado dia de la semana
	 * y de un vendedor en particular
	 * @param cont
	 * @param dia
	 * @param cedulaVendedor
	 * @return valor
	 */
	public long getNumeroClientes(Context cont,String municipio,String cedulaVendedor)
	{
		String filtroMunicipio ="";
		if(!municipio.equals("TODOS"))
		{
			filtroMunicipio="AND municipio='"+municipio+"' ";
		}


		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
				       "FROM clientes "+
				       "WHERE activo = 1 "+
						filtroMunicipio+
				       "AND cedulaVendedor ='"+cedulaVendedor+"' ";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	/**
	 * metodo que retorna el numero total de clientes efectivos, es decir , que se les ha registrado pedido
	 * en un dia de la semana y una fecha en particular, correspondientes a un vendedor
	 * @param cont
	 * @param dia
	 * @param cedulaVendedor
	 * @param Fecha
	 * @return valor
	 */
	public long getNumeroClientesEfectivos(Context cont,String municipio,String cedulaVendedor, String Fecha)
	{

		String filtroMunicipio ="";
		if(!municipio.equals("TODOS"))
		{
			filtroMunicipio="AND municipio='"+municipio+"' ";
		}
		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
				       "FROM clientes "+
				       "WHERE activo = 1 "+
						filtroMunicipio+
				       "AND cedulaVendedor ='"+cedulaVendedor+"' "+
					   "AND (fechaUltimoPedido='"+Fecha+"' OR fechaUltimaVenta='"+Fecha+"')";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	
	
	
	
	
	
	/**
	 * metodo que retona un arreglo de clientes los cuales se deben visitar para el dia en particular
	 * @param cont
	 * @param dia
	 * @param cedulaVendedor
	 * @return lista
	 */
	public ArrayList <Cliente> getClientesPorDia(Context cont,String municipio,String cedulaVendedor)
	{

		String filtroMunicipio ="";
		if(!municipio.equals("TODOS"))
		{
			filtroMunicipio="AND municipio='"+municipio+"' ";
		}

		ArrayList<Cliente> lista=new ArrayList<Cliente>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT idCliente, nombre, representante, nit, direccion, telefono, municipio, limiteCredito, barrio, tipoCanal, 0, fechaUltimoPedido, fechaUltimaVisita, PrecioDefecto, ubicado, fechaUltimaVenta ,IFNULL(deudaAntFac,0) deudaAntFac ,IFNULL(DiasGracia,30) DiasGracia, IFNULL(MotivoUltimaVisita,'-') MotivoUltimaVisita "+
					   "FROM clientes "+
					   "WHERE activo = 1 "+
						filtroMunicipio+
					   "AND cedulaVendedor ='"+cedulaVendedor+"' "+
					   "ORDER BY nombre" ;
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Cliente cliente=new Cliente();
			cliente.idCliente=Long.parseLong(c.getString(0));
			cliente.nombre=c.getString(1);
			cliente.representante=c.getString(2);
			cliente.nit=c.getString(3);
			cliente.direccion=c.getString(4);
			cliente.telefono=c.getString(5);
			cliente.municipio=c.getString(6);
			cliente.limiteCredito=Long.parseLong(c.getString(7));
			cliente.barrio=c.getString(8);
			cliente.tipoCanal=c.getString(9);
			cliente.ordenVisita=Long.parseLong(c.getString(10));
			cliente.fechaUltimoPedido=c.getString(11);
			cliente.fechaUltimaVisita=c.getString(12);
			cliente.PrecioDefecto=c.getString(13);
			cliente.ubicado=c.getString(14);
			cliente.fechaUltimaVenta=c.getString(15);
			cliente.deudaAntFac=c.getString(16);
            cliente.DiasGracia=c.getString(17);
			cliente.MotivoUltimaVisita=c.getString(18);
			lista.add(cliente);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}

	public ArrayList <Cliente> getClientesPorMunicipio(Context cont,String municipio,String cedulaVendedor)
	{
		ArrayList<Cliente> lista=new ArrayList<Cliente>();
		try
		{
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT idCliente, nombre, representante, nit, direccion, telefono, municipio, limiteCredito, barrio, tipoCanal, 0, fechaUltimoPedido, fechaUltimaVisita, PrecioDefecto, ubicado, fechaUltimaVenta ,IFNULL(deudaAntFac,0) deudaAntFac ,IFNULL(DiasGracia,30) DiasGracia, IFNULL(MotivoUltimaVisita,'-') MotivoUltimaVisita "+
					"FROM clientes "+
					"WHERE Municipio='"+municipio+"' "+
					"AND activo = 1 "+
					"AND cedulaVendedor ='"+cedulaVendedor+"' "+
					"ORDER BY nombre" ;
			Cursor c= bds.rawQuery(query,null);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Cliente cliente=new Cliente();
				cliente.idCliente=Long.parseLong(c.getString(0));
				cliente.nombre=c.getString(1);
				cliente.representante=c.getString(2);
				cliente.nit=c.getString(3);
				cliente.direccion=c.getString(4);
				cliente.telefono=c.getString(5);
				cliente.municipio=c.getString(6);
				cliente.limiteCredito=Long.parseLong(c.getString(7));
				cliente.barrio=c.getString(8);
				cliente.tipoCanal=c.getString(9);
				cliente.ordenVisita=Long.parseLong(c.getString(10));
				cliente.fechaUltimoPedido=c.getString(11);
				cliente.fechaUltimaVisita=c.getString(12);
				cliente.PrecioDefecto=c.getString(13);
				cliente.ubicado=c.getString(14);
				cliente.fechaUltimaVenta=c.getString(15);
				cliente.deudaAntFac=c.getString(16);
				cliente.DiasGracia=c.getString(17);
				cliente.MotivoUltimaVisita=c.getString(18);
				lista.add(cliente);
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}

	public ArrayList <Municipio> getMunicipiosClientes(Context cont, String cedulaVendedor)
	{
		ArrayList<Municipio> lista=new ArrayList<Municipio>();
		lista.add(new Municipio("0","0","TODOS"));
		try
		{
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT Municipio "+
					"FROM clientes "+
					"WHERE activo = 1 "+
					"AND cedulaVendedor ='"+cedulaVendedor+"' "+
					"Group by municipio " +
					"Order BY municipio" ;
			Cursor c= bds.rawQuery(query,null);
			int i=1;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Municipio m=new Municipio();
				m.setMunicipio(c.getString(0));
				m.setIdMpio(""+i);
				m.setIdDpto(""+i);
				lista.add(m);
				i++;
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}

	/**
	 * obteine lista de sucursales del cliente
	 */
	public ArrayList <ClienteSucursal> getClienteSucursales(Context cont,Cliente clienteIn)
	{
		ArrayList<ClienteSucursal> lista=new ArrayList<ClienteSucursal>();

		//agrega sucursal principal --------------------------------------
		ClienteSucursal sucurcalPrincipal=new ClienteSucursal();
		sucurcalPrincipal.setIdClienteSucursal("0");
		sucurcalPrincipal.setMunicipio(clienteIn.municipio);
		sucurcalPrincipal.setDireccion(clienteIn.direccion);
		sucurcalPrincipal.setTelefono(clienteIn.telefono);
		sucurcalPrincipal.setEstablecimiento(clienteIn.representante);
		sucurcalPrincipal.setIdCliente(""+clienteIn.idCliente);
		sucurcalPrincipal.setIdDpto("0");
		sucurcalPrincipal.setIdMpio("0");
		lista.add(sucurcalPrincipal);
		//-----------------------------------------------------------------

		try
		{
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT IdClienteSucursal, IdMpio, IdDpto, Direccion, Telefono, Establecimiento, Borrado, idCliente, Departamento, Municipio "+
					"FROM ClienteSucursal "+
					"WHERE IdCliente="+clienteIn.idCliente ;

			Cursor c= bds.rawQuery(query,null);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				ClienteSucursal cliente=new ClienteSucursal();
				cliente.setIdClienteSucursal(c.getString(0));
				cliente.setIdMpio(c.getString(1));
				cliente.setIdDpto(c.getString(2));
				cliente.setDireccion(c.getString(3));
				cliente.setTelefono(c.getString(4));
				cliente.setEstablecimiento(c.getString(5));
				cliente.setBorrado(c.getString(6));
				cliente.setIdCliente(c.getString(7));
				cliente.setDepartamento(c.getString(8));
				cliente.setMunicipio(c.getString(9));

				lista.add(cliente);
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}




	/**
	 * metodo que retorna una lista de clientes resultado de la busqueda por parte del nombre, para un dia y vendedor en particular
	 * @param cont
	 * @param nombre
	 * @param dia
	 * @param cedulaVendedor
	 * @return lista
	 */
	public ArrayList <Cliente> getBuscarClientesPorDia(Context cont,String nombre, String municipio, String cedulaVendedor ,boolean buscaxnit)
	{

		String filtroMunicipio ="";
		if(!municipio.equals("TODOS"))
		{
			filtroMunicipio="AND municipio='"+municipio+"' ";
		}

		ArrayList<Cliente> lista=new ArrayList<Cliente>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query="";
		if (!buscaxnit ) {
			query = "SELECT idCliente, nombre, representante, nit, direccion, telefono, municipio, limiteCredito, barrio, tipoCanal, 0, fechaUltimoPedido, fechaUltimaVisita, PrecioDefecto, ubicado, fechaUltimaVenta ,IFNULL(deudaAntFac,0) deudaAntFac ,IFNULL(DiasGracia,30) DiasGracia, IFNULL(MotivoUltimaVisita,'-') MotivoUltimaVisita " +
					"FROM clientes " +
					"WHERE nombre LIKE '%" + nombre + "%' " +
					filtroMunicipio +
					"AND activo = 1 " +
					"AND cedulaVendedor ='"+cedulaVendedor+"' "+
					"ORDER BY Nombre";
		}
		else
			{
				query = "SELECT idCliente, nombre, representante, nit, direccion, telefono, municipio, limiteCredito, barrio, tipoCanal, 0, fechaUltimoPedido, fechaUltimaVisita, PrecioDefecto, ubicado, fechaUltimaVenta ,IFNULL(deudaAntFac,0) deudaAntFac ,IFNULL(DiasGracia,30) DiasGracia, IFNULL(MotivoUltimaVisita,'-') MotivoUltimaVisita " +
						"FROM clientes " +
						"WHERE nit LIKE '" + nombre + "%' " +
						filtroMunicipio +
						"AND activo = 1 " +
						"AND cedulaVendedor ='"+cedulaVendedor+"' " +
						"ORDER BY Nombre";
			}
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Cliente cliente=new Cliente();
			cliente.idCliente=Long.parseLong(c.getString(0));
			cliente.nombre=c.getString(1);
			cliente.representante=c.getString(2);
			cliente.nit=c.getString(3);
			cliente.direccion=c.getString(4);
			cliente.telefono=c.getString(5);
			cliente.municipio=c.getString(6);
			cliente.limiteCredito=Long.parseLong(c.getString(7));
			cliente.barrio=c.getString(8);
			cliente.tipoCanal=c.getString(9);
			cliente.ordenVisita=Long.parseLong(c.getString(10));
			cliente.fechaUltimoPedido=c.getString(11);
			cliente.fechaUltimaVisita=c.getString(12);
			cliente.PrecioDefecto=c.getString(13);
			cliente.ubicado=c.getString(14);
			cliente.fechaUltimaVenta=c.getString(15);
			cliente.deudaAntFac=c.getString(16);
            cliente.DiasGracia=c.getString(17);
			cliente.MotivoUltimaVisita=c.getString(18);

			lista.add(cliente);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}
	
	
	
	public ArrayList <Cliente> getBuscarClientesPorNombre(Context cont,String nombre, String cedulaVendedor)
	{
		ArrayList<Cliente> lista=new ArrayList<Cliente>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		
		String filtroNombre="AND nombre LIKE '%"+nombre+"%' ";
		if(nombre.equals(""))
		{
			filtroNombre="";
		}

		String query = "SELECT idCliente, nombre, representante, nit, direccion, telefono, municipio, limiteCredito, barrio, tipoCanal, fechaUltimoPedido, fechaUltimaVisita, PrecioDefecto, ubicado, fechaUltimaVenta, IFNULL(fechaUltimoPago,'--') fechaUltimoPago , IFNULL(deudaAntFac,0) deudaAntFac, IFNULL(DiasGracia,30) DiasGracia,IFNULL(MotivoUltimaVisita,'-') MotivoUltimaVisita "+
					   "FROM clientes "+
					   "WHERE activo = 1 " +
					   filtroNombre+
					   "AND cedulaVendedor ='"+cedulaVendedor+"' "+
					   "ORDER BY nombre";
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Cliente cliente=new Cliente();
			cliente.idCliente=Long.parseLong(c.getString(0));
			cliente.nombre=c.getString(1);
			cliente.representante=c.getString(2);
			cliente.nit=c.getString(3);
			cliente.direccion=c.getString(4);
			cliente.telefono=c.getString(5);
			cliente.municipio=c.getString(6);
			cliente.limiteCredito=Long.parseLong(c.getString(7));
			cliente.barrio=c.getString(8);
			cliente.tipoCanal=c.getString(9);			
			cliente.fechaUltimoPedido=c.getString(10);
			cliente.fechaUltimaVisita=c.getString(11);
			cliente.PrecioDefecto=c.getString(12);
			cliente.ubicado=c.getString(13);
			cliente.fechaUltimaVenta=c.getString(14);
			cliente.deudaActual=0;
			cliente.fechaUltimoPago=c.getString(15);
			cliente.deudaAntFac=c.getString(16);
            cliente.DiasGracia=c.getString(17);
			cliente.MotivoUltimaVisita=c.getString(18);
			lista.add(cliente);
		}
		//BUSCA DEUDA CLIENTE
		if(lista.size()>0)
		{
			//FACTURAS

			for (int i = 0; i < lista.size(); i++) {
				query = "SELECT  totalFactura, ValorPagado "+
						   "FROM factura "+
						   "WHERE Pagada='NO' " +
						   "AND idCliente = "+lista.get(i).getIdCliente();
			Cursor c2= bds.rawQuery(query,null);
			for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
			{
				long totalFactura=Long.parseLong(c2.getString(0));
				long valorPagado=Long.parseLong(c2.getString(1));
				long deudaFactura=totalFactura-valorPagado;
				lista.get(i).setDeudaActual(lista.get(i).getDeudaActual()+deudaFactura);
				
			}
			}
			//COTIZAICONES
			for (int i = 0; i < lista.size(); i++) {
				query = "SELECT  totalRemision, ValorPagado "+
						"FROM remision "+
						"WHERE Pagada='NO' " +
						"AND idCliente = "+lista.get(i).getIdCliente();
				Cursor c2= bds.rawQuery(query,null);
				for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
				{
					long totalFactura=Long.parseLong(c2.getString(0));
					long valorPagado=Long.parseLong(c2.getString(1));
					long deudaFactura=totalFactura-valorPagado;
					lista.get(i).setDeudaActual(lista.get(i).getDeudaActual()+deudaFactura);

				}
			}

		}	
		
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}
	public long getDeudaCliente(Context cont,long idCliente)
	{
		long deuda=0;
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();		
		
		//BUSCA DEUDA CLIENTE
		String query = "SELECT  totalFactura, ValorPagado "+
					   "FROM factura "+
					   "WHERE Pagada='NO' " +
					   "AND idCliente = "+idCliente;
		Cursor c2= bds.rawQuery(query,null);
		for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
		{
			long totalFactura=Long.parseLong(c2.getString(0));
			long valorPagado=Long.parseLong(c2.getString(1));
			long deudaFactura=totalFactura-valorPagado;
			deuda+=deudaFactura;			
		}	
		this.close();
		return deuda;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return deuda;
		}
	}
	

	/**
	 * metodo que retorna el cliente cuyo identificador sea igual al que le ingresa como paramentro
	 * @param idCliente
	 * @param cliente
	 * @return cliente
	 */
	public com.principal.mundo.sysws.Cliente getBuscarClientesSys(long idCliente, com.principal.mundo.sysws.Cliente cliente)
	{
		
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT IDCLIENTE, NOMBRE, CEDULAVENDEDOR, TELEFONO, DIRECCION, BARRIO, REPRESENTANTE, NIT, MUNICIPIO, LIMITECREDITO, TIPOCANAL, LUN, MAR, MIE, JUE, VIE, SAB, DOM, ORDENVISITALUN, ORDENVISITAMAR, ORDENVISITAMIE, ORDENVISITAJUE, ORDENVISITAVIE, ORDENVISITASAB, ORDENVISITADOM, FECHAULTIMOPEDIDO, ACTIVO, FECHAULTIMAVISITA, FECHAULTIMAVENTA "+
					   " FROM clientes "+
					   " WHERE idcliente= '"+idCliente+"'";					   
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{			
			cliente.setIdCliente(c.getString(0));
			cliente.setNombreCliente(c.getString(1));
			cliente.setIdRuta(c.getString(2));
			cliente.setTelefono(c.getString(3));
			cliente.setDireccion(c.getString(4));
			cliente.setBarrio(c.getString(5));
			cliente.setNombreNegocio(c.getString(6));
			cliente.setCedulaNit(c.getString(7));
			cliente.setMunicipio(c.getString(8));
			cliente.setLimiteCredito(c.getLong(9));
			cliente.setTipoCanal(c.getString(10));
			cliente.setLun(c.getString(11));
			cliente.setMar(c.getString(12));
			cliente.setMie(c.getString(13));
			cliente.setJue(c.getString(14));
			cliente.setVie(c.getString(15));
			cliente.setSab(c.getString(16));
			cliente.setDom(c.getString(17));
			cliente.setOrdenVisitaLUN(c.getLong(18));
			cliente.setOrdenVisitaMAR(c.getLong(19));
			cliente.setOrdenVisitaMIE(c.getLong(20));
			cliente.setOrdenVisitaJUE(c.getLong(21));
			cliente.setOrdenVisitaVIE(c.getLong(22));
			cliente.setOrdenVisitaSAB(c.getLong(23));
			cliente.setOrdenVisitaDOM(c.getLong(24));
			cliente.setFechaUltimoPedido(c.getString(25));
			cliente.setActivo(c.getLong(26));
			cliente.setFechaUltimaVisita(c.getString(27));
			cliente.setFechaUltimaVenta(c.getString(28));
		}
		this.close();
		return cliente;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}
	

	/**
	 * metodo que retorna el cliente cuyo identificador sea igual al que le ingresa como paramentro
	 * @param idCliente
	 * @param cliente
	 * @return cliente
	 */
	public com.principal.mundo.Cliente getBuscarClientesin(long idCliente, com.principal.mundo.Cliente cliente)
	{
		
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT IDCLIENTE, NOMBRE, ORDENVISITA, PRECIODEFECTO, UBICADO "+
					   " FROM clientes "+
					   " WHERE idcliente= '"+idCliente+"'";					   
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{			
			cliente.setIdCliente(c.getLong(0));
			cliente.setNombre(c.getString(1));
			cliente.setOrdenVisita(c.getLong(2));
			cliente.setPrecioDefecto(c.getString(3));
			cliente.setUbicado(c.getString(4));	
		}
		this.close();
		return cliente;
		}
		catch(Exception e)
		{
			String err=e.toString();
			return null;
		}
	}
	
	
	/**
	 * metodo que retorna los articulos que se encuentran registrados en la base de datos
	 * @param cont
	 * @return lista
	 */
	public ArrayList getArticulos(Context cont)
	{
		ArrayList lista=new ArrayList();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
	String query = "SELECT idArticulo, nombre, unidad, iva, impoconsumo, precio1, precio2, precio3, costo "+
				   "FROM articulos "+
				   "ORDER BY nombre " ;
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo articulo=new Articulo();
			articulo.idArticulo= Long.parseLong(c.getString(0));
			articulo.nombre= c.getString(0);
			articulo.unidad= c.getString(0);
			articulo.iva= Long.parseLong(c.getString(0));
			articulo.impoConsumo= Long.parseLong(c.getString(0));
			articulo.precio1= Long.parseLong(c.getString(0));
			articulo.precio2= Long.parseLong(c.getString(0));
			articulo.precio3= Long.parseLong(c.getString(0));
			int index=Integer.parseInt(Long.toString(articulo.idArticulo));
			articulo.costo= Long.parseLong(c.getString(0));
			lista.add(index,articulo);
		}
		bd.close();
		return lista;
	}
	
	/**
	 * metodo que retorna los articulos que se encuentren registrados en un pedido en particular

	 * @param idPedido
	 * @return lista
	 */
	public ArrayList <ArticulosPedido> getArticulosPedido( long idPedido)
	{
		ArrayList<ArticulosPedido> lista=new ArrayList<ArticulosPedido>();
		SQLiteDatabase bds=getWritableDatabase();
		
	    String query = "SELECT pa.idPedido, pa.idArticulo, pa.cantidad," +
	    			   " a.nombre, pa.valorUnitario, pa.valor, pa.codigo," +
	    			   " a.unidad, a.precio1, a.precio2, a.precio3, a.impoconsumo," +
	    			   " a.iva, pa.stock, a.activo, pa.orden, a.precio4, a.precio5," +
	    			   " a.precio6, pa.Observacion "+
					   " FROM pedidos_articulos pa, articulos a"+
					   " WHERE pa.idPedido ="+idPedido+
					   " AND pa.idArticulo = a.idArticulo"+
					   " ORDER BY pa.orden";
	
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ArticulosPedido articulosPedido=new ArticulosPedido();
			articulosPedido.idPedido= c.getLong(0);
			articulosPedido.idArticulo= c.getLong(1);
			articulosPedido.cantidad= c.getDouble(2);
			articulosPedido.nombre= c.getString(3);
			articulosPedido.valorUnitario= c.getLong(4);
			articulosPedido.valor= c.getLong(5);
			articulosPedido.codigo= c.getString(6);
			articulosPedido.unidad=c.getString(7);
			articulosPedido.precio1=c.getLong(8);
			articulosPedido.precio2=c.getLong(9);
			articulosPedido.precio3=c.getLong(10);
			articulosPedido.ipoConsumo=c.getLong(11);
			articulosPedido.iva=c.getLong(12);
			articulosPedido.stock=c.getDouble(13);
			articulosPedido.activo=c.getLong(14);
			articulosPedido.orden=c.getLong(15);
			articulosPedido.precio4=c.getLong(16);
			articulosPedido.precio5=c.getLong(17);
			articulosPedido.precio6=c.getLong(18);
			articulosPedido.Observacion=c.getString(19);

			lista.add(articulosPedido);
		}
	
		return lista;
	}
	
	public ArrayList <ArticulosFactura> getArticulosFactura( long idFactura)
	{
		ArrayList<ArticulosFactura> lista=new ArrayList<ArticulosFactura>();
		
		openDB();
		SQLiteDatabase bds=getWritableDatabase();
		
	    String query = "SELECT fa.idFactura, fa.idArticulo, fa.cantidad, a.nombre, fa.valorUnitario, fa.valor, fa.codigo, fa.cantidad, a.precio1, a.precio2, a.precio3, a.impoconsumo, a.iva, fa.stock, a.activo, fa.orden, a.precio4, a.precio5, a.precio6 "+
					   " FROM factura_articulos fa, articulos a"+
					   " WHERE fa.idFactura ="+idFactura+
					   " AND fa.idArticulo = a.idArticulo"+
					   " ORDER BY fa.orden";
	
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ArticulosFactura articulosFactura=new ArticulosFactura();
			articulosFactura.idFactura= c.getLong(0);
			articulosFactura.idArticulo= c.getLong(1);
			articulosFactura.cantidad= c.getDouble(2);
			articulosFactura.nombre= c.getString(3);
			articulosFactura.valorUnitario= c.getLong(4);
			articulosFactura.valor= c.getLong(5);
			articulosFactura.codigo= c.getString(6);
			articulosFactura.unidad="UND";
			articulosFactura.precio1=c.getLong(8);
			articulosFactura.precio2=c.getLong(9);
			articulosFactura.precio3=c.getLong(10);
			articulosFactura.ipoConsumo=c.getLong(11);
			articulosFactura.iva=c.getLong(12);
			articulosFactura.stock=c.getDouble(13);
			articulosFactura.activo=c.getLong(14);
			articulosFactura.orden=c.getLong(15);
			articulosFactura.precio4=c.getLong(16);
			articulosFactura.precio5=c.getLong(17);
			articulosFactura.precio6=c.getLong(18);
			lista.add(articulosFactura);
		}
		close();
		return lista;
	}
	public ArrayList <ArticulosFactura> getArticulosFacturaRemision( long idFactura)
	{
		ArrayList<ArticulosFactura> lista=new ArrayList<ArticulosFactura>();

		openDB();
		SQLiteDatabase bds=getWritableDatabase();

		String query = "SELECT fa.idRemision, fa.idArticulo, fa.cantidad, a.nombre, fa.valorUnitario, fa.valor, fa.codigo, fa.cantidad, a.precio1, a.precio2, a.precio3, a.impoconsumo, a.iva, fa.stock, a.activo, fa.orden, a.precio4, a.precio5, a.precio6 "+
				" FROM remision_articulos fa, articulos a"+
				" WHERE fa.idRemision ="+idFactura+
				" AND fa.idArticulo = a.idArticulo"+
				" ORDER BY fa.orden";

		Cursor c= bds.rawQuery(query,null);


		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ArticulosFactura articulosFactura=new ArticulosFactura();
			articulosFactura.idFactura= c.getLong(0);
			articulosFactura.idArticulo= c.getLong(1);
			articulosFactura.cantidad= c.getDouble(2);
			articulosFactura.nombre= c.getString(3);
			articulosFactura.valorUnitario= c.getLong(4);
			articulosFactura.valor= c.getLong(5);
			articulosFactura.codigo= c.getString(6);
			articulosFactura.unidad="UND";
			articulosFactura.precio1=c.getLong(8);
			articulosFactura.precio2=c.getLong(9);
			articulosFactura.precio3=c.getLong(10);
			articulosFactura.ipoConsumo=c.getLong(11);
			articulosFactura.iva=c.getLong(12);
			articulosFactura.stock=c.getDouble(13);
			articulosFactura.activo=c.getLong(14);
			articulosFactura.orden=c.getLong(15);
			articulosFactura.precio4=c.getLong(16);
			articulosFactura.precio5=c.getLong(17);
			articulosFactura.precio6=c.getLong(18);
			lista.add(articulosFactura);
		}
		close();
		return lista;
	}
	public ArrayList <ArticulosRemision> getArticulosRemision(long idRemision)
	{
		ArrayList<ArticulosRemision> lista=new ArrayList<ArticulosRemision>();

		openDB();
		SQLiteDatabase bds=getWritableDatabase();

		String query = "SELECT fa.idRemision, fa.idArticulo, fa.cantidad, a.nombre, fa.valorUnitario, fa.valor, fa.codigo, fa.cantidad, a.precio1, a.precio2, a.precio3, a.impoconsumo, a.iva, fa.stock, a.activo, fa.orden, a.precio4, a.precio5, a.precio6 "+
				" FROM remision_articulos fa, articulos a"+
				" WHERE fa.idRemision ="+idRemision+
				" AND fa.idArticulo = a.idArticulo"+
				" ORDER BY fa.orden";

		Cursor c= bds.rawQuery(query,null);


		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ArticulosRemision articulosRemision=new ArticulosRemision();
			articulosRemision.idRemision= c.getLong(0);
			articulosRemision.idArticulo= c.getLong(1);
			articulosRemision.cantidad= c.getDouble(2);
			articulosRemision.nombre= c.getString(3);
			articulosRemision.valorUnitario= c.getLong(4);
			articulosRemision.valor= c.getLong(5);
			articulosRemision.codigo= c.getString(6);
			articulosRemision.unidad="UND";
			articulosRemision.precio1=c.getLong(8);
			articulosRemision.precio2=c.getLong(9);
			articulosRemision.precio3=c.getLong(10);
			articulosRemision.ipoConsumo=c.getLong(11);
			articulosRemision.iva=c.getLong(12);
			articulosRemision.stock=c.getDouble(13);
			articulosRemision.activo=c.getLong(14);
			articulosRemision.orden=c.getLong(15);
			articulosRemision.precio4=c.getLong(16);
			articulosRemision.precio5=c.getLong(17);
			articulosRemision.precio6=c.getLong(18);
			lista.add(articulosRemision);
		}
		close();
		return lista;
	}
	
	public ArrayList <ArticulosTraslado> getArticulosTraslado(long idTraslado)
	{
		ArrayList<ArticulosTraslado> lista=new ArrayList<ArticulosTraslado>();
		openDB();
		SQLiteDatabase bds=getWritableDatabase();
		
	    String query = "SELECT ta.idTraslado, ta.idArticulo, ta.cantidad, a.nombre, ta.valorUnitario, ta.valor, ta.codigo, a.unidad, a.precio1, a.precio2, a.precio3, a.impoconsumo, a.iva, ta.stock, a.activo, ta.orden, a.precio4, a.precio5, a.precio6 "+
					   " FROM traslado_articulos ta, articulos a"+
					   " WHERE ta.idTraslado ="+idTraslado+
					   " AND ta.idArticulo = a.idArticulo"+
					   " ORDER BY ta.orden";
	
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ArticulosTraslado articulosTraslado=new ArticulosTraslado();
			articulosTraslado.idTraslado= c.getLong(0);
			articulosTraslado.idArticulo= c.getLong(1);
			articulosTraslado.cantidad= c.getDouble(2);
			articulosTraslado.nombre= c.getString(3);
			articulosTraslado.valorUnitario= c.getLong(4);
			articulosTraslado.valor= c.getLong(5);
			articulosTraslado.codigo= c.getString(6);
			articulosTraslado.unidad=c.getString(7);
			articulosTraslado.precio1=c.getLong(8);
			articulosTraslado.precio2=c.getLong(9);
			articulosTraslado.precio3=c.getLong(10);
			articulosTraslado.ipoConsumo=c.getLong(11);
			articulosTraslado.iva=c.getLong(12);
			articulosTraslado.stock=c.getDouble(13);
			articulosTraslado.activo=c.getLong(14);
			articulosTraslado.orden=c.getLong(15);
			articulosTraslado.precio4=c.getLong(16);
			articulosTraslado.precio5=c.getLong(17);
			articulosTraslado.precio6=c.getLong(18);
			lista.add(articulosTraslado);
		}
		close();
		return lista;
	}
	public ArrayList <MediosDePago> getMediosDePago(long NumZ, long NCaja)
	{
		ArrayList<MediosDePago> lista=new ArrayList<MediosDePago>();
		openDB();
		SQLiteDatabase bds=getWritableDatabase();
		
	    String query = "SELECT MedioPago, Valor, Cuenta "+
					   " FROM mediosdepago"+
					   " WHERE NumZ ="+NumZ+
					   " AND NCaja ="+NCaja;
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			MediosDePago mediosDePago=new MediosDePago();
			mediosDePago.MedioPago= c.getString(0);
			mediosDePago.Valor= c.getLong(1);
			mediosDePago.Cuenta= c.getLong(1);
			lista.add(mediosDePago);
		}
		close();
		return lista;
	}
	
	/**
	 * metodo que retorna los articulos que se encuentren registrados en un pedido en particular
	 * @param cont
	 * @param idPedido
	 * @return lista
	 */
	
	
	/**
	 * metodo que retorna el valor  total de un pedido en particular

	 * @param idPedido
	 * @return
	 */
	public long getValorPedido( long idPedido)
	{
		long valor=0;
		
		SQLiteDatabase bds=getWritableDatabase();
		
	    String query = "SELECT SUM(valor)"+
				   " FROM pedidos_articulos"+
				   " WHERE idPedido ="+idPedido;
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}

	/**
	 * metodo que retorna el valor  total de un pedido en particular

	 */
	public Pedido_in getObservacionesPedido( Pedido_in pedido_in)
	{
		String  text="";

		SQLiteDatabase bds=getWritableDatabase();

		String query = "SELECT observaciones, DescuentoTotal"+
				" FROM pedidos"+
				" WHERE idCodigoInterno="+pedido_in.idCodigoInterno;
		try
		{
			Cursor c= bds.rawQuery(query,null);

			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				pedido_in.setObservaciones(c.getString(0));
				pedido_in.setDescuentoTotal(c.getLong(1));

			}

			return pedido_in;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public String getObservacionesFactura( long idCodigoInterno)
	{
		String  text="";

		SQLiteDatabase bds=getWritableDatabase();

		String query = "SELECT observaciones"+
				" FROM factura"+
				" WHERE idCodigoInterno="+idCodigoInterno;
		try
		{
			Cursor c= bds.rawQuery(query,null);

			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				return c.getString(0);
			}

			return "";
		}
		catch(Exception e)
		{
			return "";
		}
	}
	public String getObservacionesRemision( long idCodigoInterno)
	{
		String  text="";

		SQLiteDatabase bds=getWritableDatabase();

		String query = "SELECT observaciones"+
				" FROM remision"+
				" WHERE idCodigoInterno="+idCodigoInterno;
		try
		{
			Cursor c= bds.rawQuery(query,null);

			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				return c.getString(0);
			}

			return "";
		}
		catch(Exception e)
		{
			return "";
		}
	}
	public long getValorFactura(Context cont, long idFactura)
	{
		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
	    String query = "SELECT SUM(valor)"+
				   " FROM factura_articulos"+
				   " WHERE idFactura ="+idFactura;
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	public long getValorRemision(Context cont, long idRemision)
	{
		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();

		String query = "SELECT SUM(valor)"+
				" FROM remision_articulos"+
				" WHERE idRemision ="+idRemision;
		try
		{
			Cursor c= bds.rawQuery(query,null);

			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				valor = c.getLong(0);

			}
			bd.close();
			return valor;
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	public long getValorTraslado(Context cont, long idTraslado)
	{
		long valor=0;
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
	    String query = "SELECT SUM(valor)"+
				   " FROM traslado_articulos"+
				   " WHERE idTraslado ="+idTraslado;
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	/**
	 * metodo que retorna el valor  total de un pedido en particular
	 * @param cont
	 * @param idPedido
	 * @return
	 */
	
	
	/**
	 * metodo que retorna el articulo cuyo codigo corresponda al que ingresa como parametro
	 * @param cont
	 * @param codigo
	 * @return Articulo
	 */
	public Articulo getArticuloPorCodigo(Context cont,String codigo)
	{
		Articulo articulo=new Articulo();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT a.idArticulo, a.nombre, a.unidad, a.iva, a.impoconsumo, a.precio1, a.precio2, a.precio3,a.precio4, a.precio5, a.precio6, a.stock, a.categoria "+
				   " FROM articulos a, articulo_codigo ac "+
				   " WHERE ac.codigo ='"+codigo+"'"+
				   " AND ac.idArticulo=a.idArticulo" +
				   " AND a.activo = 1 ";
		try
		{
	    Cursor c= bds.rawQuery(query,null);
		
		if(c!=null)
		{
				if(c.moveToFirst())
				{
				    articulo.idArticulo= Long.parseLong(c.getString(0));
					articulo.nombre= c.getString(1);
					articulo.unidad= c.getString(2);
					articulo.iva= Long.parseLong(c.getString(3));
					articulo.impoConsumo= Long.parseLong(c.getString(4));
					articulo.precio1= Long.parseLong(c.getString(5));
					articulo.precio2= Long.parseLong(c.getString(6));
					articulo.precio3= Long.parseLong(c.getString(7));
					articulo.precio4= Long.parseLong(c.getString(8));
					articulo.precio5= Long.parseLong(c.getString(9));
					articulo.precio6= Long.parseLong(c.getString(10));
					articulo.stock= c.getDouble(11);
					articulo.categoria=c.getString(12);
					articulo.idCodigo=codigo;
					closeDB();
					return articulo;					
				}
				else
				{
					closeDB();
					return null;					
				}			
		}
		else
		{
			closeDB();
			return null;
		}
		}
		
		catch(Exception e)
		{
			String error=e.toString();
			return null;
		}
		
	}
	
	
	
	public ArrayList<Articulo> getArticuloPorCategoria(Context cont, Categoria categoria)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT idArticulo, nombre, unidad, iva, impoconsumo, precio1, precio2, precio3,precio4, precio5, precio6, stock, categoria "+
				   " FROM articulos "+
				   " WHERE categoria ='"+categoria.getIdCategoria()+"'"+
				   " AND activo = 1" +
				   " ORDER BY nombre ";
	    
	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo articulo=new Articulo();
	        articulo.idArticulo= Long.parseLong(c.getString(0));
			articulo.nombre= c.getString(1);
			articulo.unidad= c.getString(2);
			articulo.iva= Long.parseLong(c.getString(3));
			articulo.impoConsumo= Long.parseLong(c.getString(4));
			articulo.precio1= Long.parseLong(c.getString(5));
			articulo.precio2= Long.parseLong(c.getString(6));
			articulo.precio3= Long.parseLong(c.getString(7));
			articulo.precio4= Long.parseLong(c.getString(8));
			articulo.precio5= Long.parseLong(c.getString(9));
			articulo.precio6= Long.parseLong(c.getString(10));
			try {
				articulo.stock = c.getDouble(11);
			} catch (Exception e )
			{
				articulo.stock =0;
			}
			articulo.categoria=c.getString(12);			
			lista.add(articulo);
		}

		//Busca Observaciones Articulo

        if(lista.size()>0)
        {
            for (int i = 0; i < lista.size(); i++) {
                query = "SELECT  idObservacion, Detalle "+
                        "FROM ObservacionArticulo "+
                        "WHERE idArticulo=" +lista.get(i).getIdArticulo();
                Cursor c2= bds.rawQuery(query,null);
                for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
                {
                    Observacion observacion=new Observacion();
                    observacion.setIdObservacion(c2.getString(0) );
                    observacion.setDetalle(c2.getString(1));
                    lista.get(i).getListaObservaciones().add(observacion);

                }
            }
        }


        bd.close();
		return lista;
	}

	/**
	 * metodo que retorna una lista de artiuclos  resultado de la busqueda por parte del nombre del articulo
	 * @param cont
	 * @param nombre
	 * @return lista
	 */
	
	public ArrayList<Articulo> getConsultaProducto(Context cont, String nombre)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";
		 query = " SELECT   MIN(IFNULL(articulo_codigo.codigo,'Sin codigo')), articulos.idArticulo, articulos.nombre, articulos.precio1, articulos.precio2, articulos.precio3, articulos.precio4, articulos.precio5, articulos.precio6, articulos.activo,  articulos.costo, articulos.stock " +
				" FROM articulos  LEFT OUTER JOIN articulo_codigo ON articulos.idArticulo=articulo_codigo.idArticulo  " +
				" WHERE articulos.nombre LIKE '%" + nombre + "%' " +
				" AND articulos.activo = 1 " +
				" GROUP BY  articulos.idArticulo, articulos.nombre, articulos.precio1, articulos.precio2, articulos.precio3, articulos.precio4, articulos.precio5, articulos.precio6, articulos.activo,  articulos.costo " +
				" ORDER BY  articulos.nombre";

	    Cursor c= bds.rawQuery(query,null);
		
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo art=new Articulo();
			art.idCodigo = c.getString(0);
			art.idArticulo=c.getLong(1);
			art.nombre=c.getString(2);
			art.precio1=c.getLong(3);
			art.precio2=c.getLong(4);
			art.precio3=c.getLong(5);
			art.precio4=c.getLong(6);
			art.precio5=c.getLong(7);
			art.precio6=c.getLong(8);
			art.activo=c.getLong(9);
			art.costo=c.getLong(10);
            art.stock=c.getDouble(11);
			art.codigo = c.getString(0);
			lista.add(art);
		}
		bd.close();
		return lista;
	}
	
	public ArrayList<Pago> getPagosPorFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Pago> lista=new ArrayList<Pago>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";
		String condicion= "";
		if(enviaRepresados)
		{
			condicion= " AND Enviado = 0 ";	 
		}
		query = " SELECT   p.IdPago, p.Valor, p.fecha, p.fecha2, p.Descripcion, p.idCliente, p.Enviado, p.NPagosFacturaNoEnviados, c.nombre  " +
    		       " FROM Pago p, clientes c " +
    		       " WHERE p.idCliente = c.idCliente " +
    		       " AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
    		       condicion+   		       
    		       " ORDER BY p.IdPago ASC ";
		
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					Pago pag=new Pago();

					pag.setIdPago(c.getLong(0));
					pag.setValor(c.getLong(1));
					pag.setFecha(c.getString(2));
					pag.setFecha2(c.getString(3));
					pag.setDescripcion(c.getString(4));
					pag.setIdCliente(c.getLong(5));
					pag.setEnviado(c.getLong(6));
					pag.setNPagosFacNoEnviados(c.getLong(7));
					pag.setNombreCliente(c.getString(8));
					lista.add(pag);
				}
				bd.close();
				return lista;
	    }
	    else
	    {
	    	return null;
	    }	
			
	}
	
	public Pago getPagosPorIdPago(Context cont, long idPago)
	{
		Pago pag=new Pago();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";
		
		query = " SELECT   p.IdPago, p.Valor, p.fecha, p.fecha2, p.Descripcion, p.idCliente, p.Enviado, p.NPagosFacturaNoEnviados, c.nombre  " +
    		       " FROM Pago p, clientes c " +
    		       " WHERE p.idCliente = c.idCliente "+
    		       " AND p.IdPago="+idPago;
		
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					
					pag.setIdPago(c.getLong(0));
					pag.setValor(c.getLong(1));
					pag.setFecha(c.getString(2));
					pag.setFecha2(c.getString(3));
					pag.setDescripcion(c.getString(4));
					pag.setIdCliente(c.getLong(5));
					pag.setEnviado(c.getLong(6));
					pag.setNPagosFacNoEnviados(c.getLong(7));
					pag.setNombreCliente(c.getString(8));
					
				}
				bd.close();
				return pag;
	    }
	    else
	    {
	    	return null;
	    }	
			
	}
	
	public ArrayList<PagosFactura> getPagosFacturaPorIdPago(Context cont, long IdPago)
	{
		ArrayList<PagosFactura> lista=new ArrayList<PagosFactura>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		
			
		 String query = " SELECT idPagosFactura, NFactura, NCaja, SaldoAnterior, " +
		 			"Total, Descuento, Saldo, IdCliente, Fecha , Hora, Fecha2 " +
    		       " FROM PagosFactura " +
    		       " WHERE NPago =" + IdPago;
    		      
		
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					PagosFactura pagosFactura=new PagosFactura();
					pagosFactura.setIdPagosFactura(c.getLong(0));
					pagosFactura.setNFactura(c.getLong(1));
					pagosFactura.setNCaja(c.getLong(2));
					pagosFactura.setSaldoAnterior(c.getLong(3));
					pagosFactura.setTotal(c.getLong(4));
					pagosFactura.setDescuento(c.getLong(5));
					pagosFactura.setSaldo(c.getLong(6));	
					pagosFactura.setIdCliente(c.getLong(7));
					pagosFactura.setFecha(c.getString(8));
					pagosFactura.setHora(c.getString(9));
					pagosFactura.setFecha2(c.getString(10));
					lista.add(pagosFactura);
				}
				
	    }
	    
	    if(lista.size()>0)
		{
			for (int i = 0; i < lista.size(); i++) {
				query = "SELECT  IdItemPagoFac, idPagosFactura, Valor, FormaPago, NCheque, Tarjeta "+
						   "FROM ItemPagoFac "+
						   "WHERE idPagosFactura=" +lista.get(i).getIdPagosFactura();
			Cursor c2= bds.rawQuery(query,null);
			for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
			{
				ItemPagoFac itemPagoFac=new ItemPagoFac();
				itemPagoFac.setIdItemPagoFac(c2.getLong(0));
				itemPagoFac.setNPagoFac(c2.getLong(1));
				itemPagoFac.setValor(c2.getLong(2));
				itemPagoFac.setFormaPago(c2.getString(3));
				itemPagoFac.setNCheque(c2.getString(4));
				itemPagoFac.setTarjeta(c2.getString(5));
				lista.get(i).getListaPagoFac().add(itemPagoFac);
				
			}
			}
		}	
	    bd.close();
		return lista;  
			
	}
	public ArrayList<ItemPagoFac> getAllItemPagosFacturaPorNFactura(Context cont, long idPagosFactura)
	{
		ArrayList<ItemPagoFac> lista=new ArrayList<ItemPagoFac>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
			
		String	query = "SELECT  it.IdItemPagoFac, it.idPagosFactura, it.Valor, it.FormaPago, it.NCheque, it.Tarjeta "+
						   " FROM ItemPagoFac it  "+
						   " WHERE it.idPagosFactura="+idPagosFactura;
			Cursor c2= bds.rawQuery(query,null);
			for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext())
			{
				ItemPagoFac itemPagoFac=new ItemPagoFac();
				itemPagoFac.setIdItemPagoFac(c2.getLong(0));
				itemPagoFac.setNPagoFac(c2.getLong(1));
				itemPagoFac.setValor(c2.getLong(2));
				itemPagoFac.setFormaPago(c2.getString(3));
				itemPagoFac.setNCheque(c2.getString(4));
				itemPagoFac.setTarjeta(c2.getString(5));
				lista.add(itemPagoFac);
				
			}		
	    bd.close();
		return lista;  
			
	}
	public ArrayList<PagoPrestamo> getPagosPrestamoXFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<PagoPrestamo> lista=new ArrayList<PagoPrestamo>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";

		if(enviaRepresados)
		{
			query = " SELECT   p.idPagoPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
					" p.valor,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre "+//, p.FormaPago  " +
					" FROM PagoPrestamo p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" AND p.enviado = 0 "+
					" ORDER BY p.idPagoPrestamo ASC ";
		}
		else
		{
			query = " SELECT   p.idPagoPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
					" p.valor,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre "+
					" FROM PagoPrestamo p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" ORDER BY p.idPagoPrestamo ASC ";;
		}
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				PagoPrestamo pagoPrestamo=new PagoPrestamo();
				pagoPrestamo.setIdPagoPrestamo(c.getString(0));
				pagoPrestamo.setFecha(c.getString(1));
				pagoPrestamo.setFecha2(c.getString(2));
		    	pagoPrestamo.setIdCliente(c.getString(3));
				pagoPrestamo.setHora(c.getString(4));
				pagoPrestamo.setValor(c.getLong(5));
				pagoPrestamo.setSaldoAnterior(c.getLong(6));
				pagoPrestamo.setNuevoSaldo(c.getLong(7));
				pagoPrestamo.setEnviado(c.getLong(8));
				pagoPrestamo.setNombreCliente(c.getString(9));
				lista.add(pagoPrestamo);
			}
			bd.close();
			return lista;
		}
		else
		{
			return null;
		}

	}

    public PagoPrestamo getPagoPrestamo(Context cont, long idPagoPrestamo)
    {
        creaBD bd= new creaBD(cont);
        bd.openDB();
        SQLiteDatabase bds=bd.getWritableDatabase();
        String query="";
        PagoPrestamo pagoPrestamo=new PagoPrestamo();


            query = " SELECT   p.idPagoPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
                    " p.valor,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre "+
                    " FROM PagoPrestamo p, clientes c " +
                    " WHERE p.idCliente = c.idCliente " +
                    " AND  p.idPagoPrestamo ="+idPagoPrestamo +
                    " ORDER BY p.idPagoPrestamo ASC ";;

        Cursor c= bds.rawQuery(query,null);

        if(c!=null)
        {
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            {

                pagoPrestamo.setIdPagoPrestamo(c.getString(0));
                pagoPrestamo.setFecha(c.getString(1));
                pagoPrestamo.setFecha2(c.getString(2));
                pagoPrestamo.setIdCliente(c.getString(3));
                pagoPrestamo.setHora(c.getString(4));
                pagoPrestamo.setValor(c.getLong(5));
                pagoPrestamo.setSaldoAnterior(c.getLong(6));
                pagoPrestamo.setNuevoSaldo(c.getLong(7));
                pagoPrestamo.setEnviado(c.getLong(8));
                pagoPrestamo.setNombreCliente(c.getString(9));

            }
            bd.close();
            return pagoPrestamo;
        }
        else
        {
            return null;
        }

    }

	public ArrayList<Prestamo> getPrestamosXFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Prestamo> lista=new ArrayList<Prestamo>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";

		if(enviaRepresados)
		{
			query = " SELECT   p.idPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
					" p.valorPrestamo,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre,p.objeto "+
					" FROM Prestamo p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" AND p.enviado = 0 "+
					" ORDER BY p.idPrestamo ASC ";
		}
		else
		{
			query = " SELECT   p.idPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
					" p.valorPrestamo,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre ,p.objeto "+
					" FROM Prestamo p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" ORDER BY p.idPrestamo ASC ";;
		}
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Prestamo prestamo=new Prestamo();
				prestamo.setIdPrestamo(c.getString(0));
				prestamo.setFecha(c.getString(1));
				prestamo.setFecha2(c.getString(2));
				prestamo.setIdCliente(c.getString(3));
				prestamo.setHora(c.getString(4));
				prestamo.setValorPrestamo(c.getLong(5));
				prestamo.setSaldoAnterior(c.getLong(6));
				prestamo.setNuevoSaldo(c.getLong(7));
				prestamo.setEnviado(c.getLong(8));
				prestamo.setNombreCliente(c.getString(9));
				prestamo.setObjeto(c.getString(10));
				lista.add(prestamo);
			}
			bd.close();
			return lista;
		}
		else
		{
			return null;
		}

	}

    public Prestamo getPrestamo(Context cont, long idPrestamo)
    {
        Prestamo prestamo=new Prestamo();
        creaBD bd= new creaBD(cont);
        bd.openDB();
        SQLiteDatabase bds=bd.getWritableDatabase();
        String query="";

            query = " SELECT   p.idPrestamo, p.fecha, p.fecha2, p.idCliente, p.hora," +
                    " p.valorPrestamo,p.saldoAnterior,p.nuevoSaldo,p.enviado, c.nombre ,p.objeto "+
                    " FROM Prestamo p, clientes c " +
                    " WHERE p.idCliente = c.idCliente " +
                    " AND p.idPrestamo ="+idPrestamo+
                    " ORDER BY p.idPrestamo ASC ";;

        Cursor c= bds.rawQuery(query,null);

        if(c!=null)
        {
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            {

                prestamo.setIdPrestamo(c.getString(0));
                prestamo.setFecha(c.getString(1));
                prestamo.setFecha2(c.getString(2));
                prestamo.setIdCliente(c.getString(3));
                prestamo.setHora(c.getString(4));
                prestamo.setValorPrestamo(c.getLong(5));
                prestamo.setSaldoAnterior(c.getLong(6));
                prestamo.setNuevoSaldo(c.getLong(7));
                prestamo.setEnviado(c.getLong(8));
                prestamo.setNombreCliente(c.getString(9));
                prestamo.setObjeto(c.getString(10));

            }
            bd.close();
            return prestamo;
        }
        else
        {
            return null;
        }

    }

    public ArrayList<Libro> getMovimientosXFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Libro> lista=new ArrayList<Libro>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";


		if(enviaRepresados)
		{
			query = " SELECT   p.idLibro, p.NLibro, p.IdCliente, p.MovCredito, p.MovDedito," +
					" p.Saldo,p.Enviado,p.Concepto,p.Fecha,p.Fecha2,p.hora,p.SaldoAnterior, c.nombre "+
					" FROM Libro p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" AND p.enviado = 0 "+
					" ORDER BY p.idLibro ASC ";
		}
		else
		{
			query = " SELECT   p.idLibro, p.NLibro, p.IdCliente, p.MovCredito, p.MovDedito," +
					" p.Saldo,p.Enviado,p.Concepto,p.Fecha,p.Fecha2,p.hora,p.SaldoAnterior, c.nombre "+
					" FROM Libro p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" ORDER BY p.idLibro ASC ";
		}
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Libro libro=new Libro();
				libro.setIdLibro(c.getString(0));
				libro.setNLibro(c.getLong(1));
				libro.setIdCliente(c.getString(2));
				libro.setMovCredito(c.getLong(3));
				libro.setMovDedito(c.getLong(4));
				libro.setSaldo(c.getLong(5));
				libro.setEnviado(c.getLong(6));
				libro.setConcepto(c.getString(7));
				libro.setFecha(c.getString(8));
				libro.setFecha2(c.getString(9));
				libro.setHora(c.getString(10));
				libro.setSaldoAnterior(c.getLong(11));
				libro.setNombreCliente(c.getString(12));
				lista.add(libro);
			}
			bd.close();
			return lista;
		}
		else
		{
			return null;
		}

	}
	public Libro getLibro(Context cont, long idLibro)
	{

		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";
		Libro libro=new Libro();


			query = " SELECT   p.idLibro, p.NLibro, p.IdCliente, p.MovCredito, p.MovDedito," +
					" p.Saldo,p.Enviado,p.Concepto,p.Fecha,p.Fecha2,p.hora,p.SaldoAnterior, c.nombre "+
					" FROM Libro p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.idLibro="+idLibro+
					" ORDER BY p.idLibro ASC ";
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{

				libro.setIdLibro(c.getString(0));
				libro.setNLibro(c.getLong(1));
				libro.setIdCliente(c.getString(2));
				libro.setMovCredito(c.getLong(3));
				libro.setMovDedito(c.getLong(4));
				libro.setSaldo(c.getLong(5));
				libro.setEnviado(c.getLong(6));
				libro.setConcepto(c.getString(7));
				libro.setFecha(c.getString(8));
				libro.setFecha2(c.getString(9));
				libro.setHora(c.getString(10));
				libro.setSaldoAnterior(c.getLong(11));
				libro.setNombreCliente(c.getString(12));

			}
			bd.close();
			return libro;
		}
		else
		{
			return null;
		}

	}
	public ArrayList<Libro> getMovimientosXFechaXCliente(Context cont, String fechaDesde, String fechaHasta, Cliente cliente )
	{
		ArrayList<Libro> lista=new ArrayList<Libro>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";



			query = " SELECT   p.idLibro, p.NLibro, p.IdCliente, p.MovCredito, p.MovDedito," +
					" p.Saldo,p.Enviado,p.Concepto,p.Fecha,p.Fecha2,p.hora,p.SaldoAnterior, c.nombre "+
					" FROM Libro p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" AND p.idCliente ="+cliente.getIdCliente()+
					" ORDER BY p.idLibro ASC ";
			Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Libro libro=new Libro();
				libro.setIdLibro(c.getString(0));
				libro.setNLibro(c.getLong(1));
				libro.setIdCliente(c.getString(2));
				libro.setMovCredito(c.getLong(3));
				libro.setMovDedito(c.getLong(4));
				libro.setSaldo(c.getLong(5));
				libro.setEnviado(c.getLong(6));
				libro.setConcepto(c.getString(7));
				libro.setFecha(c.getString(8));
				libro.setFecha2(c.getString(9));
				libro.setHora(c.getString(10));
				libro.setSaldoAnterior(c.getLong(11));
				libro.setNombreCliente(c.getString(12));
				lista.add(libro);
			}
			bd.close();
			return lista;
		}
		else
		{
			return null;
		}

	}

	public ArrayList<Libro> getUltimosMovimientosXFechaXCliente(Context cont, String fechaDesde, String fechaHasta, Cliente cliente )
	{
		ArrayList<Libro> lista=new ArrayList<Libro>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";



		query = " SELECT   p.idLibro, p.NLibro, p.IdCliente, p.MovCredito, p.MovDedito," +
				" p.Saldo,p.Enviado,p.Concepto,p.Fecha,p.Fecha2,p.hora,p.SaldoAnterior, c.nombre "+
				" FROM Libro p, clientes c " +
				" WHERE p.idCliente = c.idCliente " +
				" AND p.fecha2  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
				" AND p.idCliente ="+cliente.getIdCliente()+
				" ORDER BY p.NLibro DESC LIMIT 5  ";
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Libro libro=new Libro();
				libro.setIdLibro(c.getString(0));
				libro.setNLibro(c.getLong(1));
				libro.setIdCliente(c.getString(2));
				libro.setMovCredito(c.getLong(3));
				libro.setMovDedito(c.getLong(4));
				libro.setSaldo(c.getLong(5));
				libro.setEnviado(c.getLong(6));
				libro.setConcepto(c.getString(7));
				libro.setFecha(c.getString(8));
				libro.setFecha2(c.getString(9));
				libro.setHora(c.getString(10));
				libro.setSaldoAnterior(c.getLong(11));
				libro.setNombreCliente(c.getString(12));
				lista.add(libro);
			}
			bd.close();
			return lista;
		}
		else
		{
			return null;
		}

	}
	/**
	 * metodo que retorna una lista de pedidos registrados en una fecha en particular
	 * @param cont
	* @param enviaRepresados
	 * @return lista
	 */
	public ArrayList<Pedido_in> getPedidosPorFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Pedido_in> lista=new ArrayList<Pedido_in>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		 String query="";
		
		if(enviaRepresados)
		{
			 query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora," +
					 " p.valor, c.nombre, ifnull(p.envio,0) as envio, p.observaciones, p.DescuentoTotal, p.SubTotal, p.Documento , p.FormaPago, p.idClienteSucursal, p.Estado  " +
	    		       " FROM pedidos p, clientes c " +
	    		       " WHERE p.idCliente = c.idCliente " +
	    		       " AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
	    		       //" AND idCodigoExterno = 0 "+	
	    		       " AND p.envio = 0 "+
	    		       " ORDER BY p.idCodigoInterno ASC ";
		}
		else
		{
		    query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora," +
					" p.valor, c.nombre, ifnull(p.envio,0) as envio, p.observaciones , p.DescuentoTotal, p.SubTotal, p.Documento ,p.FormaPago, p.idClienteSucursal, p.Estado  " +
		    		       " FROM pedidos p, clientes c " +
		    		       " WHERE p.idCliente = c.idCliente " +
		    		       " AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
		    		       " ORDER BY p.idCodigoInterno DESC ";
		}
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					Pedido_in ped=new Pedido_in();
					ped.idCodigoInterno = c.getLong(0);
					ped.idCodigoExterno=c.getLong(1);
					ped.idCliente=c.getLong(2);
					ped.setFechaSqlite(c.getString(3));
					ped.hora=c.getString(4);
					ped.valor=c.getLong(5);
					ped.nombreCliente=c.getString(6);
					ped.setEnvio(c.getLong(7));
					ped.observaciones=c.getString(8);
					ped.setDescuentoTotal(c.getLong(9));
					ped.setSubTotal(c.getLong(10));
					ped.setDocumento(c.getString(11));
					ped.setFormaPago(c.getString(12));
					ped.idClienteSucursal = validaCampoNull(c,13);
					ped.setEstado( ""+validaCampoNull(c,14));
					lista.add(ped);
				}
				bd.close();
				return lista;
	    }
	    else
	    {
	    	return null;
	    }	
			
	}
	private long validaCampoNull(Cursor c, int ident)
	{
		long res=0;
		if (!c.isNull(ident))
		{
			res=c.getLong(ident);
		}
		return res;
	}

	public ArrayList<Factura_in> getFacturasPorFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Factura_in> lista=new ArrayList<Factura_in>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		 String query="";
		
		if(enviaRepresados)
		{
			 query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
			 		   ",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
			 		   ",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalFactura,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NFactura, c.nit , p.Pagada, p.ValorPagado,p.base19,p.iva19, p.Observaciones,  p.idClienteSucursal, p.Anulada " +
	    		       " FROM factura p, clientes c " +
	    		       " WHERE p.idCliente = c.idCliente " +
	    		       " AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
	    		       " AND idCodigoExterno = 0 "+	    		       
	    		       " ORDER BY p.idCodigoInterno ASC ";
		}
		else
		{
		    query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
			 		   ",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
			 		   ",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalFactura,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NFactura, c.nit, p.Pagada, p.ValorPagado,p.base19,p.iva19, p.Observaciones ,  p.idClienteSucursal, p.Anulada " +
	    		      " FROM factura p, clientes c " +
		    		       " WHERE p.idCliente = c.idCliente " +
		    		       " AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
		    		       " ORDER BY p.idCodigoExterno DESC ";
		}	
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					Factura_in ped=new Factura_in();
					ped.idCodigoInterno = c.getLong(0);
					ped.idCodigoExterno=c.getLong(1);
					ped.idCliente=c.getLong(2);
					ped.setFechaSqlite(c.getString(3));
					ped.hora=c.getString(4);
					ped.valor=c.getLong(5);
					ped.nombreCliente=c.getString(6);
					ped.razonSocial=c.getString(7);
					ped.representante=c.getString(8);
					ped.regimenNit=c.getString(9);
					ped.direccionTel=c.getString(10);
					ped.NCaja=c.getLong(11);
					ped.prefijo=c.getString(12);
					ped.base0=c.getLong(13);
					ped.base5=c.getLong(14);
					ped.base10=c.getLong(15);
					ped.base14=c.getLong(16);
					ped.base16=c.getLong(17);
					ped.iva5=c.getLong(18);
					ped.iva10=c.getLong(19);
					ped.iva14=c.getLong(20);
					ped.iva16=c.getLong(21);
					ped.impoCmo=c.getLong(22);
					ped.totalFactura=c.getLong(23);
					ped.resDian=c.getString(24);
					ped.rango=c.getString(25);
					ped.idBodega=c.getLong(26);
					ped.dineroRecibido=c.getLong(27);
					ped.NombreVendedor=c.getString(28);
					ped.TelefonoVendedor=c.getString(29);
					ped.VentaCredito=c.getLong(30);
					ped.NFactura=c.getLong(31);
					ped.nitCliente=c.getString(32);
					ped.Pagada=c.getString(33);
					ped.ValorPagado=c.getLong(34);
					ped.base19=c.getLong(35);
					ped.iva19=c.getLong(36);
					ped.observaciones=c.getString(37);
					//ped.idClienteSucursal=c.getLong(38);
					ped.idClienteSucursal = validaCampoNull(c,38);
					ped.setAnulada(""+validaCampoNull(c,39));
					lista.add(ped);					
				}
				bd.close();
				
				for (int i = 0; i < lista.size(); i++) {
					lista.get(i).setListaArticulos(getArticulosFactura( lista.get(i).idCodigoInterno));
				}
				
				return lista;
	    }
	    else
	    {
	    	return null;
	    }
	}
	public ArrayList<Factura_in> getFacturasCierre(CierreTurno cierreTurno)
	{
		ArrayList<Factura_in> lista=new ArrayList<Factura_in>();

		SQLiteDatabase bds=this.getWritableDatabase();
		String query="";


			query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
					",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
					",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalFactura,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NFactura, c.nit, p.Pagada, p.ValorPagado,p.base19,p.iva19, p.Observaciones ,  p.idClienteSucursal " +
					" FROM factura p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.NFactura>="+cierreTurno.getNFacturaInicial()+" AND p.NFactura<="+cierreTurno.getNFacturaFinal()+
					" ORDER BY p.idCodigoExterno DESC ";

		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Factura_in ped=new Factura_in();
				ped.idCodigoInterno = c.getLong(0);
				ped.idCodigoExterno=c.getLong(1);
				ped.idCliente=c.getLong(2);
				ped.setFechaSqlite(c.getString(3));
				ped.hora=c.getString(4);
				ped.valor=c.getLong(5);
				ped.nombreCliente=c.getString(6);
				ped.razonSocial=c.getString(7);
				ped.representante=c.getString(8);
				ped.regimenNit=c.getString(9);
				ped.direccionTel=c.getString(10);
				ped.NCaja=c.getLong(11);
				ped.prefijo=c.getString(12);
				ped.base0=c.getLong(13);
				ped.base5=c.getLong(14);
				ped.base10=c.getLong(15);
				ped.base14=c.getLong(16);
				ped.base16=c.getLong(17);
				ped.iva5=c.getLong(18);
				ped.iva10=c.getLong(19);
				ped.iva14=c.getLong(20);
				ped.iva16=c.getLong(21);
				ped.impoCmo=c.getLong(22);
				ped.totalFactura=c.getLong(23);
				ped.resDian=c.getString(24);
				ped.rango=c.getString(25);
				ped.idBodega=c.getLong(26);
				ped.dineroRecibido=c.getLong(27);
				ped.NombreVendedor=c.getString(28);
				ped.TelefonoVendedor=c.getString(29);
				ped.VentaCredito=c.getLong(30);
				ped.NFactura=c.getLong(31);
				ped.nitCliente=c.getString(32);
				ped.Pagada=c.getString(33);
				ped.ValorPagado=c.getLong(34);
				ped.base19=c.getLong(35);
				ped.iva19=c.getLong(36);
				ped.observaciones=c.getString(37);
				//ped.idClienteSucursal=c.getLong(38);
				ped.idClienteSucursal = validaCampoNull(c,38);
				lista.add(ped);
			}

			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).setListaArticulos(getArticulosFactura( lista.get(i).idCodigoInterno));
			}

			return lista;
		}
		else
		{
			return null;
		}
	}
	public ArrayList<Remision_in> getRemisionesPorFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados)
	{
		ArrayList<Remision_in> lista=new ArrayList<Remision_in>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String query="";

		if(enviaRepresados)
		{
			query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
					",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
					",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalRemision,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NRemision, c.nit , p.Pagada, p.ValorPagado,p.base19,p.iva19, p.Observaciones, p.idClienteSucursal " +
					" FROM remision p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" AND idCodigoExterno = 0 "+
					" ORDER BY p.idCodigoInterno ASC ";
		}
		else
		{
			query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
					",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
					",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalRemision,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NRemision, c.nit, p.Pagada, p.ValorPagado,p.base19,p.iva19, p.Observaciones , p.idClienteSucursal " +
					" FROM remision p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					" ORDER BY p.idCodigoExterno DESC ";
		}
		Cursor c= bds.rawQuery(query,null);

		if(c!=null)
		{
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Remision_in ped=new Remision_in();
				ped.idCodigoInterno = c.getLong(0);
				ped.idCodigoExterno=c.getLong(1);
				ped.idCliente=c.getLong(2);
				ped.setFechaSqlite(c.getString(3));
				ped.hora=c.getString(4);
				ped.valor=c.getLong(5);
				ped.nombreCliente=c.getString(6);
				ped.razonSocial=c.getString(7);
				ped.representante=c.getString(8);
				ped.regimenNit=c.getString(9);
				ped.direccionTel=c.getString(10);
				ped.NCaja=c.getLong(11);
				ped.prefijo=c.getString(12);
				ped.base0=c.getLong(13);
				ped.base5=c.getLong(14);
				ped.base10=c.getLong(15);
				ped.base14=c.getLong(16);
				ped.base16=c.getLong(17);
				ped.iva5=c.getLong(18);
				ped.iva10=c.getLong(19);
				ped.iva14=c.getLong(20);
				ped.iva16=c.getLong(21);
				ped.impoCmo=c.getLong(22);
				ped.totalRemision=c.getLong(23);
				ped.resDian=c.getString(24);
				ped.rango=c.getString(25);
				ped.idBodega=c.getLong(26);
				ped.dineroRecibido=c.getLong(27);
				ped.NombreVendedor=c.getString(28);
				ped.TelefonoVendedor=c.getString(29);
				ped.VentaCredito=c.getLong(30);
				ped.NRemision=c.getLong(31);
				ped.nitCliente=c.getString(32);
				ped.Pagada=c.getString(33);
				ped.ValorPagado=c.getLong(34);
				ped.base19=c.getLong(35);
				ped.iva19=c.getLong(36);
				ped.observaciones=c.getString(37);
				//ped.idClienteSucursal=c.getLong(38);
				ped.idClienteSucursal = validaCampoNull(c,38);
				lista.add(ped);
			}
			bd.close();

			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).setListaArticulos(getArticulosRemision( lista.get(i).idCodigoInterno) );
			}

			return lista;
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<Factura_in> getFacturasCartera(Context cont, long idCliente) {
		ArrayList<Factura_in> lista = new ArrayList<Factura_in>();
		ArrayList<Factura_in> listaF = new ArrayList<Factura_in>();
		ArrayList<Factura_in> listaR = new ArrayList<Factura_in>();
		creaBD bd = new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds = bd.getWritableDatabase();
		String query = "";

		query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
				",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
				",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalFactura,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NFactura, c.nit, p.Pagada, p.ValorPagado, p.base19, p.iva19, p.Observaciones " +
				" FROM factura p, clientes c " +
				" WHERE p.idCliente = c.idCliente " +
				" AND p.idCliente =" + idCliente +
				" AND p.Pagada ='NO'" +
				" ORDER BY p.NFactura ASC ";

		Cursor c = bds.rawQuery(query, null);

		if (c != null) {
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				Factura_in ped = new Factura_in();
				ped.idCodigoInterno = c.getLong(0);
				ped.idCodigoExterno = c.getLong(1);
				ped.idCliente = c.getLong(2);
				ped.setFechaSqlite(c.getString(3));
				ped.hora = c.getString(4);
				ped.valor = c.getLong(5);
				ped.nombreCliente = c.getString(6);
				ped.razonSocial = c.getString(7);
				ped.representante = c.getString(8);
				ped.regimenNit = c.getString(9);
				ped.direccionTel = c.getString(10);
				ped.NCaja = c.getLong(11);
				ped.prefijo = c.getString(12);
				ped.base0 = c.getLong(13);
				ped.base5 = c.getLong(14);
				ped.base10 = c.getLong(15);
				ped.base14 = c.getLong(16);
				ped.base16 = c.getLong(17);
				ped.iva5 = c.getLong(18);
				ped.iva10 = c.getLong(19);
				ped.iva14 = c.getLong(20);
				ped.iva16 = c.getLong(21);
				ped.impoCmo = c.getLong(22);
				ped.totalFactura = c.getLong(23);
				ped.resDian = c.getString(24);
				ped.rango = c.getString(25);
				ped.idBodega = c.getLong(26);
				ped.dineroRecibido = c.getLong(27);
				ped.NombreVendedor = c.getString(28);
				ped.TelefonoVendedor = c.getString(29);
				ped.VentaCredito = c.getLong(30);
				ped.NFactura = c.getLong(31);
				ped.nitCliente = c.getString(32);
				ped.Pagada = c.getString(33);
				ped.ValorPagado = c.getLong(34);
				ped.base19 = c.getLong(35);
				ped.iva19 = c.getLong(36);
				ped.observaciones = c.getString(37);
				ped.TipoDoc = "FAC";
				listaF.add(ped);
			}


		}

			for (int i = 0; i < listaF.size(); i++) {
				listaF.get(i).setListaArticulos(getArticulosFactura(listaF.get(i).idCodigoInterno));
			}

		c.close();
			//----------------------------------------------------------
			//REMISIONES
			//----------------------------------------------------------

			query = " SELECT   p.idCodigoInterno, p.idCodigoExterno, p.idCliente, p.fecha, p.hora, p.valor, c.nombre " +
					",p.razonSocial,p.representante ,p.regimenNit,p.direccionTel,p.NCaja,p.prefijo,p.base0,p.base5,p.base10,p.base14,p.base16" +
					",p.iva5,p.iva10,p.iva14,p.iva16,p.impoCmo,p.totalRemision,p.resDian,p.rango,p.idBodega, p.dineroRecibido, p.nombrevendedor, p.telefonovendedor, p.VentaCredito, p.NRemision, c.nit, p.Pagada, p.ValorPagado, p.base19, p.iva19, p.Observaciones " +
					" FROM remision p, clientes c " +
					" WHERE p.idCliente = c.idCliente " +
					" AND p.idCliente =" + idCliente +
					" AND p.Pagada ='NO'" +
					" ORDER BY p.NRemision ASC ";

		try {
			c = bds.rawQuery(query, null);
		}
		catch (Exception e)
		{
		String er=e.toString();
		}

			if (c != null) {
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					Factura_in ped = new Factura_in();
					ped.idCodigoInterno = c.getLong(0);
					ped.idCodigoExterno = c.getLong(1);
					ped.idCliente = c.getLong(2);
					ped.setFechaSqlite(c.getString(3));
					ped.hora = c.getString(4);
					ped.valor = c.getLong(5);
					ped.nombreCliente = c.getString(6);
					ped.razonSocial = c.getString(7);
					ped.representante = c.getString(8);
					ped.regimenNit = c.getString(9);
					ped.direccionTel = c.getString(10);
					ped.NCaja = c.getLong(11);
					ped.prefijo = c.getString(12);
					ped.base0 = c.getLong(13);
					ped.base5 = c.getLong(14);
					ped.base10 = c.getLong(15);
					ped.base14 = c.getLong(16);
					ped.base16 = c.getLong(17);
					ped.iva5 = c.getLong(18);
					ped.iva10 = c.getLong(19);
					ped.iva14 = c.getLong(20);
					ped.iva16 = c.getLong(21);
					ped.impoCmo = c.getLong(22);
					ped.totalFactura = c.getLong(23);
					ped.resDian = c.getString(24);
					ped.rango = c.getString(25);
					ped.idBodega = c.getLong(26);
					ped.dineroRecibido = c.getLong(27);
					ped.NombreVendedor = c.getString(28);
					ped.TelefonoVendedor = c.getString(29);
					ped.VentaCredito = c.getLong(30);
					ped.NFactura = c.getLong(31);
					ped.nitCliente = c.getString(32);
					ped.Pagada = c.getString(33);
					ped.ValorPagado = c.getLong(34);
					ped.base19 = c.getLong(35);
					ped.iva19 = c.getLong(36);
					ped.observaciones = c.getString(37);
					ped.TipoDoc = "REM";
					listaR.add(ped);
				}


			}
				for (int i = 0; i < listaR.size(); i++) {
					listaR.get(i).setListaArticulos(getArticulosFacturaRemision(listaR.get(i).idCodigoInterno));
				}
		c.close();
		bd.close();
		for (int i = 0; i < listaF.size(); i++) {
			lista.add(listaF.get(i));
		}
		for (int i = 0; i < listaR.size(); i++) {
			lista.add(listaR.get(i));
		}
		return lista;



	}

	
	public ArrayList<Traslado_in> getTrasladosPorFecha(Context cont, String fechaDesde, String fechaHasta, boolean enviaRepresados, long idBodegaOrigen, long idBodegaDestino)
	{
		ArrayList<Traslado_in> lista=new ArrayList<Traslado_in>();
		creaBD bd= new creaBD(cont);
		bd.openDB();
		SQLiteDatabase bds=bd.getWritableDatabase();
		String conBodegaOrigen=" AND idBodegaOrigen="+idBodegaOrigen;
		String conBodegaDestino=" AND idBodegaDestino="+idBodegaDestino;
		if(idBodegaOrigen==0)
		{
			conBodegaOrigen="";
		}
		if(idBodegaDestino==0)
		{
			conBodegaDestino="";
		}
		 String query="";
		
		if(enviaRepresados)
		{
			 query = " SELECT   idCodigoInterno, idCodigoExterno,  fecha, hora " +
			 		   ",razonSocial,representante ,regimenNit,direccionTel,NCaja,base0,base5,base10,base14,base16" +
			 		   ",iva5,iva10,iva14,iva16,impoCmo,totaltraslado, dineroRecibido, idBodegaOrigen, idBodegaDestino, bodegaOrigen, bodegaDestino, base19, iva19 " +
	    		       " FROM traslado  " +
	    		       " WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
	    		       " AND idCodigoExterno = 0 "+	 
	    		       conBodegaOrigen +conBodegaDestino+
	    		       " ORDER BY idCodigoInterno ASC ";
		}
		else
		{
		    query = " SELECT   idCodigoInterno, idCodigoExterno,  fecha, hora " +
			 		   ",razonSocial,representante ,regimenNit,direccionTel,NCaja," +
			 		   "base0,base5,base10,base14,base16" +
			 		   ",iva5,iva10,iva14,iva16,impoCmo,totaltraslado, " +
			 		   "dineroRecibido,  " +
			 		   "idBodegaOrigen, idBodegaDestino, bodegaOrigen, bodegaDestino, base19, iva19 " +
	    		       " FROM traslado  " +
	    		       " WHERE fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 	
	    		       conBodegaOrigen +conBodegaDestino+
	    		       " ORDER BY idCodigoInterno ASC ";
		}	
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					Traslado_in tra=new Traslado_in();
					tra.idCodigoInterno = c.getLong(0);
					tra.idCodigoExterno=c.getLong(1);
					tra.setFechaSqlite(c.getString(2));
					tra.hora=c.getString(3);
					tra.razonSocial=c.getString(4);
					tra.representante=c.getString(5);
					tra.regimenNit=c.getString(6);
					tra.direccionTel=c.getString(7);
					tra.NCaja=c.getLong(8);
					tra.base0=c.getLong(9);
					tra.base5=c.getLong(10);
					tra.base10=c.getLong(11);
					tra.base14=c.getLong(12);
					tra.base16=c.getLong(13);
					tra.iva5=c.getLong(14);
					tra.iva10=c.getLong(15);
					tra.iva14=c.getLong(16);
					tra.iva16=c.getLong(17);
					tra.impoCmo=c.getLong(18);
					tra.totalTraslado=c.getLong(19);
					tra.dineroRecibido=c.getLong(20);			
					tra.bodegaOrigen.setIdBodega(c.getInt(21));
					tra.bodegaDestino.setIdBodega(c.getInt(22));
					tra.bodegaOrigen.setBodega(c.getString(23));
					tra.bodegaDestino.setBodega(c.getString(24));
					tra.base19=c.getLong(25);
					tra.iva19=c.getLong(26);
					lista.add(tra);					
				}
				bd.close();
				
				for (int i = 0; i < lista.size(); i++) {
					lista.get(i).setListaArticulos(getArticulosTraslado(lista.get(i).idCodigoInterno));
				}
				
				return lista;
	    }
	    else
	    {
	    	return null;
	    }
	}
	public ZFinanciera getZFinanciera(long NumZ, long NCaja)
	{
		ZFinanciera zFinanciera =new ZFinanciera();
	
		SQLiteDatabase bds=this.getWritableDatabase();
		 String query="";	
	     query = " SELECT  NumZ, NCaja, Fecha, Hora, FacturaInicial, FacturaFinal," +
	     		 "	 	   VentasExentas, Ventas16, Iva16, Total16, Ventas5,Iva5, Total5, Ventas8," +
	     		 "         Iva8, Total8, NTransacciones, Ventas, Iva, ImpoCmo, Descuento, Total, Ventas19, Iva19, Total19 " +
	    		 " FROM zfinanciera " +
	    		 " WHERE NumZ ="+NumZ+
	    		 " AND NCaja="+NCaja;
	    		       		
	    Cursor c= bds.rawQuery(query,null);
		
	    if(c!=null)
	    {
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					zFinanciera.NumZ=c.getLong(0);
					zFinanciera.NCaja=c.getLong(1);
					zFinanciera.Fecha=c.getString(2);
					zFinanciera.Hora=c.getString(3);
					zFinanciera.FacturaInicial=c.getLong(4);
					zFinanciera.FacturaFinal=c.getLong(5);
					zFinanciera.VentasExentas=c.getLong(6);
					zFinanciera.Ventas16=c.getLong(7);
					zFinanciera.Iva16=c.getLong(8);
					zFinanciera.Total16=c.getLong(9);
					zFinanciera.Ventas5=c.getLong(10);
					zFinanciera.Iva5=c.getLong(11);
					zFinanciera.Total5=c.getLong(12);
					zFinanciera.Ventas8=c.getLong(13);
					zFinanciera.Iva8=c.getLong(14);
					zFinanciera.Total8=c.getLong(15);
					zFinanciera.NTransacciones=c.getLong(16);
					zFinanciera.Ventas=c.getLong(17);
					zFinanciera.Iva=c.getLong(18);
					zFinanciera.ImpoCmo=c.getLong(19);
					zFinanciera.Descuento=c.getLong(20);
					zFinanciera.Total=c.getLong(21);	
					zFinanciera.Ventas19=c.getLong(22);
					zFinanciera.Iva19=c.getLong(23);
					zFinanciera.Total19=c.getLong(24);
				}
			zFinanciera.setListaMendiosDePago(getMediosDePago(NumZ, NCaja));
				
			return zFinanciera;
	    }
	    else
	    {
	    	return null;
	    }	
			
	}
	
	public long getExisteBodegas(Context cont)
	{
		long valor=0;
		creaBD bd= new creaBD(cont);
		SQLiteDatabase bds=bd.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
				       "FROM bodega ";				      
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);
					
				}
				bd.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	public int getExisteBodega(Bodega b)
	{
		int valor=0;
		SQLiteDatabase bds=this.getWritableDatabase();
		
		String query = "SELECT COUNT(*) "+
				       "FROM bodega " +
				       "WHERE IdBodega="+b.getIdBodega();				      
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getInt(0);
					
				}
				this.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	
	public ArrayList<Bodega> getBodegas(Context cont)
	{
		
		ArrayList<Bodega> listaBodegas=new ArrayList<Bodega>();
		creaBD bd= new creaBD(cont);
		SQLiteDatabase bds=bd.getWritableDatabase();
	
		String query = "SELECT IdBodega, NBodega, Direccion, Telefono, Responsable, Municipio "+
				       "FROM bodega ";				      
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					Bodega bodega=new Bodega();
					bodega.setIdBodega(Integer.parseInt(c.getString(0)));
					bodega.setBodega(c.getString(1));
					bodega.setDireccion(c.getString(2));
					bodega.setTelefono(c.getString(3));
					bodega.setResponsable(c.getString(4));
					bodega.setMunicipio(c.getString(5));
					listaBodegas.add(bodega);
				
				}
				bd.close();
				return listaBodegas;
	    }
	    catch(Exception e)
	    {
	    	return null;
	    }
	}
	public Bodega getBodega( long idBodega)
	{
		
		
	
		SQLiteDatabase bds=getWritableDatabase();
		Bodega bodega=new Bodega();
		String query = "SELECT IdBodega, NBodega, Direccion, Telefono, Responsable, Municipio "+
				       "FROM bodega " +
				       "WHERE IdBodega="+idBodega;				      
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					
					bodega.setIdBodega(Integer.parseInt(c.getString(0)));
					bodega.setBodega(c.getString(1));
					bodega.setDireccion(c.getString(2));
					bodega.setTelefono(c.getString(3));
					bodega.setResponsable(c.getString(4));
					bodega.setMunicipio(c.getString(5));				
				}
				
				return bodega;
	    }
	    catch(Exception e)
	    {
	    	return null;
	    }
	}
	
	/**
	 * metodo que retorna la suma del valor de los pedidos registrados en una fecha en particular
	 * @param fecha
	 * @return valor
	 */
	public long getTotalPedidos(String fecha)
	{
		long valor=0;	
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();			
	    String query = "SELECT SUM(valor)"+
					   " FROM pedidos "+
					   " WHERE fecha='"+fecha+"'";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    if(c!=null)
			    {
					for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
					{
						valor = c.getLong(0);						
					}
			    }	
			    else
			    {
			    	
			    	valor=0;
			    }
			    this.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	public long getTotalFacturas(String fecha)
	{
		long valor=0;	
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();			
	    String query = "SELECT SUM(valor)"+
					   " FROM factura "+
					   " WHERE fecha='"+fecha+"'";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    if(c!=null)
			    {
					for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
					{
						valor = c.getLong(0);						
					}
			    }	
			    else
			    {
			    	
			    	valor=0;
			    }
			    this.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 0;
	    }
	}
	/**
	 * metodo que retorna la suma del valor de los pedidos registrados en una fecha en particular
	 * @param fecha
	 * @return valor
	 */
	
/**
 * metodo que retorna la suma del valor de los pedidos registrados en una fecha en particular
 * @param fecha
 * @return valor
 */
	
	public ArrayList <Categoria> getVentasCategoriasPedidos(String fecha)
	{
		ArrayList<Categoria> lista=new ArrayList<Categoria>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT a.categoria, SUM(pa.cantidad),SUM(pa.valor)  "+
				   "FROM pedidos p, pedidos_articulos pa, articulos a "+
				   "WHERE p.idCodigoInterno=pa.idPedido "+
    			   "AND pa.idArticulo = a.idArticulo "+    		
    			   "AND p.fecha='"+fecha+"' "+ 
    			   "GROUP BY a.categoria "+
				   "ORDER BY a.categoria";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Categoria categoria=new Categoria();
			categoria.setNombre(c.getString(0));
			categoria.setUnidades(Long.parseLong(c.getString(1)));
			categoria.setValor(Long.parseLong(c.getString(2)));
			lista.add(categoria);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}	
	
	public ArrayList <Articulo> getVentasPedidosporArticulo(String fechaDesde, String fechaHasta, String idCategoria)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		String filtroCat="";
		try
		{
		this.openDB();
			if(!idCategoria.equals("0"))
			{
				filtroCat=" AND a.categoria="+idCategoria;
			}


		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT a.nombre,a.gramaje,a.unidadDeMedida, SUM(pa.cantidad),SUM(pa.valor)   "+
				   "FROM pedidos p, pedidos_articulos pa, articulos a "+
				   "WHERE p.idCodigoInterno=pa.idPedido "+
    			   "AND pa.idArticulo = a.idArticulo "+    		
    			   "AND p.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					filtroCat+
    			   " GROUP BY a.idArticulo "+
				   " ORDER BY SUM(pa.cantidad) DESC";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo articulo=new Articulo();
			//articulo.setNombre(c.getString(0));
			//articulo.setCantidadVentas((long)Double.parseDouble(c.getString(1)));
			//articulo.setValorVentas(Long.parseLong(c.getString(2)));

			articulo.setNombre(c.getString(0));
			//articulo.setGramaje(c.getString(1));
			articulo.setGramaje("0");
			//articulo.setUnidadDeMedida(c.getString(2));
			articulo.setUnidadDeMedida("UND");

			articulo.setCantidadVentas((long)Double.parseDouble(c.getString(3)));
			articulo.setValorVentas(Long.parseLong(c.getString(4)));



			lista.add(articulo);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}	
	public ArrayList <Articulo> getVentasFacturasporArticulo(String fechaDesde, String fechaHasta, String idCategoria)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		String filtroCat="";
		if(!idCategoria.equals("0"))
		{
			filtroCat=" AND a.categoria="+idCategoria;
		}
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT a.nombre,a.gramaje,a.unidadDeMedida, SUM(fa.cantidad),SUM(fa.valor)  "+
				   "FROM factura f, factura_articulos fa, articulos a "+
				   "WHERE f.idCodigoInterno=fa.idFactura "+
    			   "AND fa.idArticulo = a.idArticulo "+    		
    			   "AND f.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' AND (f.Anulada='NO' OR f.Anulada is null) "+
				   filtroCat+
    			   " GROUP BY a.nombre,a.gramaje,a.unidadDeMedida "+
				   "ORDER BY SUM(fa.cantidad) DESC";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo articulo=new Articulo();
			articulo.setNombre(c.getString(0));
			articulo.setGramaje(c.getString(1));
			articulo.setUnidadDeMedida(c.getString(2));

			articulo.setCantidadVentas((long)Double.parseDouble(c.getString(3)));
			articulo.setValorVentas(Long.parseLong(c.getString(4)));

			lista.add(articulo);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return lista;
		}
	}
	public ArrayList <Articulo> getVentasCierreFacturasporArticulo(CierreTurno cierreTurno)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();

		try
		{
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT a.idArticulo, a.nombre, SUM(fa.cantidad),SUM(fa.valor)  "+
					" FROM factura f, factura_articulos fa, articulos a "+
					" WHERE f.idCodigoInterno=fa.idFactura "+
					" AND fa.idArticulo = a.idArticulo "+
					" AND f.NFactura>="+cierreTurno.getNFacturaInicial()+" AND f.NFactura<="+cierreTurno.getNFacturaFinal()+
					" GROUP BY a.idArticulo, a.nombre"+
					" ORDER BY SUM(fa.cantidad) DESC";



			Cursor c= bds.rawQuery(query,null);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Articulo articulo=new Articulo();
				articulo.idArticulo=c.getLong(0);
				articulo.setNombre(c.getString(1));
				articulo.setCantidadVentasDouble(Double.parseDouble(c.getString(2)));
				articulo.setValorVentas(Long.parseLong(c.getString(3)));

				lista.add(articulo);
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public ArrayList <Articulo> getVentasRemisionporArticulo(String fechaDesde, String fechaHasta, String idCategoria)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		String filtroCat="";

		if(!idCategoria.equals("0"))
		{
			filtroCat=" AND a.categoria="+idCategoria;
		}
		try
		{
			this.openDB();
			SQLiteDatabase bds=this.getWritableDatabase();
			String query = "SELECT a.nombre,a.gramaje,a.unidadDeMedida, SUM(fa.cantidad),SUM(fa.valor)  "+
					"FROM remision f, remision_articulos fa, articulos a "+
					"WHERE f.idCodigoInterno=fa.idRemision "+
					"AND fa.idArticulo = a.idArticulo "+
					"AND f.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+
					filtroCat+
					" GROUP BY a.idArticulo "+
					"ORDER BY SUM(fa.cantidad) DESC";

			Cursor c= bds.rawQuery(query,null);
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
			{
				Articulo articulo=new Articulo();
				articulo.setNombre(c.getString(0));

				articulo.setGramaje(c.getString(1));
				articulo.setUnidadDeMedida(c.getString(2));


				articulo.setCantidadVentas((long)Double.parseDouble(c.getString(3)));
				articulo.setValorVentas(Long.parseLong(c.getString(4)));
				lista.add(articulo);
			}
			this.close();
			return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public ArrayList <Articulo> getVentasTrasladosporArticulo(String fechaDesde, String fechaHasta)
	{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT a.nombre, SUM(ta.cantidad),SUM(ta.valor)  "+
				   "FROM traslado t, traslado_articulos ta, articulos a "+
				   "WHERE t.idCodigoInterno=ta.idTraslado "+
    			   "AND ta.idArticulo = a.idArticulo "+    		
    			   "AND t.fecha  BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"' "+ 
    			   "GROUP BY a.idArticulo "+
				   "ORDER BY SUM(ta.cantidad) DESC";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Articulo articulo=new Articulo();
			articulo.setNombre(c.getString(0));
			articulo.setCantidadVentas((long)Double.parseDouble(c.getString(1)));
			articulo.setValorVentas(Long.parseLong(c.getString(2)));
			lista.add(articulo);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}	
	
	public ArrayList <Categoria> getVentasCategoriasFacturas(String fecha)
	{
		ArrayList<Categoria> lista=new ArrayList<Categoria>();
		try
		{
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();
		String query = "SELECT a.categoria, SUM(fa.cantidad),SUM(fa.valor)  "+
				   "FROM factura f, factura_articulos fa, articulos a "+
				   "WHERE f.idCodigoInterno=fa.idFactura "+
    			   "AND fa.idArticulo = a.idArticulo "+    		
    			   "AND f.fecha='"+fecha+"' "+ 
    			   "GROUP BY a.categoria "+
				   "ORDER BY a.categoria";
		
		Cursor c= bds.rawQuery(query,null);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			Categoria categoria=new Categoria();
			categoria.setNombre(c.getString(0));
			categoria.setUnidades(Long.parseLong(c.getString(1)));
			categoria.setValor(Long.parseLong(c.getString(2)));
			lista.add(categoria);
		}
		this.close();
		return lista;
		}
		catch(Exception e)
		{
			return null;
		}
	}	
public long getTotalVentasPedidos(String Categoria,String fecha)
{
	long valor=0;	
	this.openDB();
	SQLiteDatabase bds=this.getWritableDatabase();			
    String query = "SELECT SUM(pa.valor) "+
    			   "FROM pedidos p, pedidos_articulos pa, articulos a "+
    			   "WHERE p.idCodigoInterno=pa.idPedido "+
    			   "AND pa.idArticulo = a.idArticulo "+
    			   "AND a.categoria='"+Categoria+"' "+
    			   "AND P.fecha='"+fecha+"' ";
    try
    {
		    Cursor c= bds.rawQuery(query,null);
		    if(c!=null)
		    {
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);						
				}
		    }	
		    else
		    {
		    	
		    	valor=0;
		    }
		    this.close();
			return valor;
    }
    catch(Exception e)
    {
    	return 0;
    }
}
public long getTotalVentasFacturas(String Categoria,String fecha)
{
	long valor=0;	
	this.openDB();
	SQLiteDatabase bds=this.getWritableDatabase();			
    String query = "SELECT SUM(fa.valor) "+
    			   "FROM factura f, factura_articulos fa, articulos a "+
    			   "WHERE f.idCodigoInterno=fa.idFactura "+
    			   "AND fa.idArticulo = a.idArticulo "+
    			   "AND a.categoria='"+Categoria+"' "+
    			   "AND f.fecha='"+fecha+"' ";
    try
    {
		    Cursor c= bds.rawQuery(query,null);
		    if(c!=null)
		    {
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);						
				}
		    }	
		    else
		    {
		    	
		    	valor=0;
		    }
		    this.close();
			return valor;
    }
    catch(Exception e)
    {
    	return 0;
    }
}

/**
 * metodo que retorna la suma del valor de los pedidos registrados en una fecha en particular
 * @param fecha
 * @return valor
 */
public long getUnidadesVendidasPedidosCategoriaFecha(String Categoria,String fecha)
{
	long valor=0;	
	this.openDB();
	SQLiteDatabase bds=this.getWritableDatabase();			
    String query = "SELECT SUM(pa.cantidad) "+
    			   "FROM pedidos p, pedidos_articulos pa, articulos a "+
    			   "WHERE p.idCodigoInterno=pa.idPedido "+
    			   "AND pa.idArticulo = a.idArticulo "+
    			   "AND a.categoria='"+Categoria+"' "+
    			   "AND P.fecha='"+fecha+"' ";
    try
    {
		    Cursor c= bds.rawQuery(query,null);
		    if(c!=null)
		    {
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);						
				}
		    }	
		    else
		    {
		    	
		    	valor=0;
		    }
		    this.close();
			return valor;
    }
    catch(Exception e)
    {
    	return 0;
    }
}
public long getUnidadesVendidasFacturasCategoriaFecha(String Categoria,String fecha)
{
	long valor=0;	
	this.openDB();
	SQLiteDatabase bds=this.getWritableDatabase();			
    String query = "SELECT SUM(fa.cantidad) "+
    			   "FROM pedidos f, pedidos_articulos fa, articulos a "+
    			   "WHERE f.idCodigoInterno=fa.idFactura "+
    			   "AND fa.idArticulo = a.idArticulo "+
    			   "AND a.categoria='"+Categoria+"' "+
    			   "AND f.fecha='"+fecha+"' ";
    try
    {
		    Cursor c= bds.rawQuery(query,null);
		    if(c!=null)
		    {
				for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
				{
					valor = c.getLong(0);						
				}
		    }	
		    else
		    {
		    	
		    	valor=0;
		    }
		    this.close();
			return valor;
    }
    catch(Exception e)
    {
    	return 0;
    }
}
	/**
	 * metodo que retorna el numero total de pacas registradas en los pedidos de una fecha en particular
	 * @param fecha
	 * @return valor
	 */
	public long getUnidadesVendidasPedidosFecha(String fecha)
	{
		long valor=0;	
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();			
	    String query = "SELECT SUM(pa.cantidad)"+
					   " FROM pedidos_articulos pa, pedidos p"+
					   " WHERE p.idCodigoInterno = pa.idPedido"+
					   " AND p.fecha='"+fecha+"'";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    if(c!=null)
			    {
					for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
					{
						valor = c.getLong(0);						
					}
			    }	
			    else
			    {
			    	
			    	valor=99999;
			    }
			    this.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 1099;
	    }
	}
	public long getUnidadesVendidasFacturasFecha(String fecha)
	{
		long valor=0;	
		this.openDB();
		SQLiteDatabase bds=this.getWritableDatabase();			
	    String query = "SELECT SUM(fa.cantidad)"+
					   " FROM factura_articulos fa, factura f"+
					   " WHERE f.idCodigoInterno = fa.idFactura"+
					   " AND f.fecha='"+fecha+"'";
	    try
	    {
			    Cursor c= bds.rawQuery(query,null);
			    if(c!=null)
			    {
					for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
					{
						valor = c.getLong(0);						
					}
			    }	
			    else
			    {
			    	
			    	valor=99999;
			    }
			    this.close();
				return valor;
	    }
	    catch(Exception e)
	    {
	    	return 1099;
	    }
	}
	
	/**
	 * metodo que retorna el numero total de pacas registradas en los pedidos de una fecha en particular
	 * @param fecha
	 * @return valor
	 */
	
	
//------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------DELETE-----------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * metodo que elimina un pedido registrado en la base de datos que corresponda a un codigo interno y externo en particular
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idCodigoInterno
	 * @param idCodigoExterno
	 * @return true o false
	 */
	public boolean eliminarPedido( long idCodigoInterno, long idCodigoExterno)
	{
		try
		{
			this.getWritableDatabase().delete("pedidos", " idCodigoInterno ="+idCodigoInterno+" AND idCodigoExterno ="+idCodigoExterno, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean limpiaFacturas()
	{
		try
		{

			this.getWritableDatabase().delete("factura_articulos", "", null);
			this.getWritableDatabase().delete("factura", "", null);
			this.getWritableDatabase().delete("pedidos_articulos", "", null);
			this.getWritableDatabase().delete("pedidos", "", null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}


	public boolean eliminarFactura( long idCodigoInterno, long idCodigoExterno)
	{
		try
		{
			this.getWritableDatabase().delete("factura", " idCodigoInterno ="+idCodigoInterno+" AND idCodigoExterno ="+idCodigoExterno, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	/**
	 * metodo que elimina un pedido registrado en la base de datos que corresponda a un codigo interno y externo en particular
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idCodigoInterno
	 * @param idCodigoExterno
	 * @return true o false
	 */
	
	
	/**
	 * metodo que elimina los codigos registrados en la base de datos para un articulo en particular
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idArticulo
	 * @return true o false
	 */
	public boolean eliminaCodigosArticulo( long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("articulo_codigo", " idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	public boolean eliminaClienteSucursal( long IdCliente)
	{
		try
		{
			this.getWritableDatabase().delete("ClienteSucursal", " IdCliente ="+IdCliente, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminaObservacionesArticulo( long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("ObservacionArticulo", " idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminaMediosDePago( long zfinanciera, long ncaja)
	{
		try
		{
			this.getWritableDatabase().delete("mediosdepago", " NumZ = "+zfinanciera+" AND NCaja = "+ncaja, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	/**
	 * metodo que elimina un articulo registrado en un pedido en particular
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idPedido
	 * @param idArticulo
	 * @return true o false
	 */
	public boolean eliminarPedidoArticulo( long idPedido,long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("pedidos_articulos", " idPedido ="+idPedido+ " AND idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarFacturaArticulo( long idFactura,long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("factura_articulos", " idFactura ="+idFactura+ " AND idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarRemisionArticulo( long idRemision,long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("remision_articulos", " idRemision ="+idRemision+ " AND idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarTrasladoArticulo( long idTraslado,long idArticulo)
	{
		try
		{
			this.getWritableDatabase().delete("traslado_articulos", " idTraslado ="+idTraslado+ " AND idArticulo ="+idArticulo, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}

	
	/**
	 * metodo que elimina todos los articulos regisrados a un pedido en particular
	 * retorna true si la transaccion fue ejecutada satisfactoriamente y false en caso contrario
	 * @param idPedido
	 * @return true o false
	 */
	public boolean eliminarArticulosDePedido( long idPedido)
	{
		try
		{
			this.getWritableDatabase().delete("pedidos_articulos", "idPedido ="+idPedido, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarArticulosDeFactura( long idFactura)
	{
		try
		{
			this.getWritableDatabase().delete("factura_articulos", "idFactura ="+idFactura, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarArticulosDeRemision( long idRemision)
	{
		try
		{
			this.getWritableDatabase().delete("remision_articulos", "idRemision ="+idRemision, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	public boolean eliminarArticulosDeTraslado( long idTraslado)
	{
		try
		{
			this.getWritableDatabase().delete("traslado_articulos", "idTraslado ="+idTraslado, null);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	/**
	 *  Metodo que Abre la conexion con la base de datos
	 */	
	public void openDB()
	{
		this.getWritableDatabase();
	}
	
	/**
	 * Metodo que Cierra la conexion con la base de datos
	 */
	public void closeDB()
	{
		this.close();
	}
	

}
