package com.principal.print;



import java.text.DecimalFormat;
import java.util.ArrayList;

import com.principal.factura.ArticulosListaAdapter;
import com.principal.mundo.Articulo;
import com.principal.mundo.ArticulosFactura;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.ArticulosRemision;
import com.principal.mundo.ArticulosTraslado;
import com.principal.mundo.DemoSleeper;
import com.principal.mundo.Factura_in;
import com.principal.mundo.ItemPagoFac;
import com.principal.mundo.Pago;
import com.principal.mundo.PagosFactura;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;
import com.principal.mundo.Remision_in;
import com.principal.mundo.Traslado_in;
import com.principal.mundo.ZFinanciera;
import com.principal.persistencia.creaBD;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

public class PrintZebra  {
	
	private static final int ALIGN_LEFT = 1;
	private static final int ALIGN_CENTER = 2;
	private static final int ALIGN_RIGHT = 3;
	private final static int FACTURA = 1;
	private final static int PEDIDO = 2;
	private final static int TRANSLADO = 3;
	private final static int REMISION=12;
	
	private Parametros parametrosPos, parametrosSys;
	  
	
	private Connection printerConnection;
	private ZebraPrinter printer;
	private Factura_in factura_in;
	private Remision_in remision_in;
	private Pedido_in pedido_in;
	private ArrayList<ArticulosPedido> listaAPedido;


	private Pago pago;
	private Traslado_in traslado_in;
	private ZFinanciera zFinanciera;
	ArrayList<Traslado_in> listaTraslados;
	ArrayList<Factura_in> listaFacturas;
	ArrayList<Pedido_in> listaPedidos;
	ArrayList<Articulo> listaArticulos;
	ArrayList<Remision_in> listaRemisiones;
	ArrayList<String> datos;
    private String macAdd;
    private long height=20;
    private String strConfInitial="";
    private String strHeader="";
    private String strBody="";
    private String strFooter="";
    private String strConfFinal="";
    private String strPrint="";
    private int operacion=0;
    private boolean printArticulos=false;  
  
    public PrintZebra(String macAdd) {
		
		this.macAdd=macAdd;
       
		
	}
      
    public boolean printFactura(Factura_in factura_in)
	{
    	this.factura_in=factura_in;
    	boolean res=false;
    	if(connect())
    	{
    		 try {
    			
    			 if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL) 
    			 {
    				 generateHeaderFactura();
    				 generateBodyFactura();
    				 generateFooterFactura();
    				 generateDocPrint();
	    			 byte[] configLabel = strPrint.getBytes();
    	             printerConnection.write(configLabel);    	        
    	             DemoSleeper.sleep(1000);
    	             res=true;
    			 }
    	           
    	        } catch (ConnectionException e) {
    	        	 disconnect();
    	        } finally {
    	            disconnect();
    	        }
    	}
    	return res;   	
	}
	public boolean printRemision(Remision_in remision_in)
	{
		this.remision_in=remision_in;
		boolean res=false;
		if(connect())
		{
			try {

				if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL)
				{
					generateHeaderRemision();
					generateBodyRemision();
					generateFooterRemision();
					generateDocPrint();
					byte[] configLabel = strPrint.getBytes();
					printerConnection.write(configLabel);
					DemoSleeper.sleep(1000);
					res=true;
				}

			} catch (ConnectionException e) {
				disconnect();
			} finally {
				disconnect();
			}
		}
		return res;
	}
	public boolean printPedido(Pedido_in pedido_in,ArrayList<ArticulosPedido> listaAPedido,Parametros parametrosPos)
	{
		this.listaAPedido=listaAPedido;
		this.pedido_in=pedido_in;
		this.parametrosPos=parametrosPos;
		boolean res=false;


		if(connect())
		{
			try {

				if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL)
				{
					generateHeaderPedido();
					generateBodyPedido();
					generateFooterPedido();
					generateDocPrint();
					byte[] configLabel = strPrint.getBytes();
					printerConnection.write(configLabel);
					DemoSleeper.sleep(1000);
					res=true;
				}

			} catch (ConnectionException e) {
				disconnect();
			} finally {
				disconnect();
			}
		}
		return res;
	}
    
    public boolean printTraslado(Traslado_in traslado_in)
   	{
    	this.traslado_in=traslado_in;
       	boolean res=false;
       	if(connect())
       	{
       		 try {
       			
       			 if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL) 
       			 {
   	    			 generateHeaderTraslado();
   	    			 generateBodyTraslado();
   	    			 generateFooterTraslado();
   	    			 generateDocPrint();
   	    			 byte[] configLabel = strPrint.getBytes();
       	             printerConnection.write(configLabel);    	        
       	             DemoSleeper.sleep(1500);
       	             res=true;
       			 }
       	           
       	        } catch (ConnectionException e) {
       	        	 disconnect();
       	        } finally {
       	            disconnect();
       	        }
       	}
       	return res;   	
   	}
    
    public boolean printPago(Pago pago, Parametros parametros)
   	{
       	this.pago=pago;
       	this.parametrosPos=parametros;
       	this.parametrosSys=parametros;
       	boolean res=false;
       	if(connect())
       	{
       		 try {
       			
       			 if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL) 
       			 {
       				 generateHeaderPago();
       				 generateBodyPago();
       				 generateFooterPago();
       				 generateDocPrint();
   	    			 byte[] configLabel = strPrint.getBytes();
       	             printerConnection.write(configLabel);    	        
       	             DemoSleeper.sleep(1000);
       	             res=true;
       	             
       			 }
       	           
       	        } catch (ConnectionException e) {
       	        	 disconnect();
       	        } finally {
       	            disconnect();
       	        }
       	}
       	return res;   	
   	}
    
    public boolean printDocumentosRealizados(int operacion, boolean printArticulos,ArrayList<String> datos,ArrayList<Pedido_in> listaPedidos,ArrayList<Factura_in> listaFacturas,ArrayList<Traslado_in> listaTraslados,ArrayList<Articulo> listaArticulos,ArrayList<Remision_in > listaRemisiones)
   	{
    	this.operacion=operacion;
    	this.printArticulos=printArticulos;
    	this.datos=datos;
    	this.listaFacturas=listaFacturas;
    	this.listaTraslados=listaTraslados;
    	this.listaArticulos=listaArticulos;
    	this.listaPedidos=listaPedidos;
		this.listaRemisiones=listaRemisiones;
       	boolean res=false;
       	if(connect())
       	{
       		 try {
       			
       			 if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL) 
       			 {
   	    			 generateHeaderDocument();
   	    			 generateBodyDocumentos();
   	    			 generateFooterDocumentos();
   	    			 generateDocPrint();
   	    			 byte[] configLabel = strPrint.getBytes();
       	             printerConnection.write(configLabel);    	        
       	             DemoSleeper.sleep(1500);
       	             res=true;
       			 }
       	           
       	        } catch (ConnectionException e) {
       	        	 disconnect();
       	        } finally {
       	            disconnect();
       	        }
       	}
       	return res;   	
   	}
    
    public boolean printZFinanciera(ZFinanciera zFinanciera)
	{
    	this.zFinanciera=zFinanciera;
    	boolean res=false;
    	if(connect())
    	{
    		 try {
    			
    			 if ( printer.getPrinterControlLanguage()== PrinterLanguage.CPCL) 
    			 {
    				 generateHeaderZFinanciera();
    				 generateBodyZFinanciera();
    				 generateFooterZFinanciera();
    				 generateDocPrint();
	    			 byte[] configLabel = strPrint.getBytes();
    	             printerConnection.write(configLabel);    	        
    	             DemoSleeper.sleep(1500);
    	             res=true;
    			 }
    	           
    	        } catch (ConnectionException e) {
    	        	 disconnect();
    	        } finally {
    	            disconnect();
    	        }
    	}
    	return res;   	
	}
    
    
    
    
    
    
	public boolean connect() {
	      
		boolean res=false;
		
        	printerConnection = null;    
            printerConnection = new BluetoothConnection(macAdd);
         try {
            printerConnection.open();
           
        } catch (ConnectionException e) {          
            DemoSleeper.sleep(1000);
            disconnect();
        }

     printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
              
               res=true;
            } catch (ConnectionException e) {
              
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
              
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            }
        }

        return res;
	}

    public void disconnect() {
        try {
           if (printerConnection != null) {
                printerConnection.close();
            }           
        } catch (ConnectionException e) {
            
        } finally {            
        }
    }
    private void generateHeaderPago()
    {
    	
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, parametrosSys.getRazonSocial())+"\r\n";
    	height+=52;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, parametrosSys.getRepresentante())+"\r\n";
    	height+=52;   
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, "COMPROBANTE DE PAGO")+"\r\n";
    	height+=52; 
    	strHeader +="T 7 0 65 "+height+"No. Pago: "+getFillText(ALIGN_CENTER, 11, pago.getIdPago()+"")+"    Caja: "+getFillText(ALIGN_LEFT, 3, ""+parametrosSys.getCaja())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"   Fecha: "+getFillText(ALIGN_LEFT, 10, pago.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, parametrosSys.getHora())+"\r\n";
    	height+=30; 
    	strHeader +="T 7 0 65 "+height+" Cliente: "+getFillText(ALIGN_LEFT, 35, pago.getNombreCliente())+"\r\n";
    	height+=80;
    	
    }
    private void generateBodyPago()
    {
    	strBody +="T 7 0 10 "+height+"DESCRIPCION \r\n";
    	height+=40;
    	if(pago.getListaPagosFactura()!=null)
    	{
	    	for (int i = 0; i < pago.getListaPagosFactura().size(); i++) {
				PagosFactura pagosFactura=pago.getListaPagosFactura().get(i);	
				strBody +="T 7 0 10 "+height+" ______________________________________________\r\n";
		    	height+=40;
		    	strBody +="T 7 0 65 "+height+" Factura: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNFactura()+"")+"     Caja: "+getFillText(ALIGN_LEFT, 10, pagosFactura.getNCaja()+"")+"\r\n";
				height+=40;
				strBody +="T 7 0 65 "+height+"               Saldo Anterior: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldoAnterior()))+"\r\n";
		    	height+=40;
		    	strBody +="T 7 0 10 "+height+" FORMA DE PAGO                    VALOR        \r\n";
		    	height+=40;
		    	if(pagosFactura.getListaPagoFac()!=null)
		    	{
			    	for (int j = 0; j < pagosFactura.getListaPagoFac().size(); j++) {
						ItemPagoFac itemPagoFac=pagosFactura.getListaPagoFac().get(j);
						strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 20, ""+itemPagoFac.getFormaPago())+" "+getFillText(ALIGN_RIGHT, 30, getDecTxt(itemPagoFac.getValor()))+"\r\n";
						height+=30;
					}
		    	}
		    	strBody +="T 7 0 65 "+height+"                    Descuento: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getDescuento()))+"\r\n";
		    	height+=40;
		    	strBody +="T 7 0 65 "+height+"                  Nuevo Saldo: "+getFillText(ALIGN_LEFT, 10, getDecTxt(pagosFactura.getSaldo()))+"\r\n";
		    	height+=40;
	    	}	
		}    	
    }
    private void generateFooterPago()
    {
    	strHeader +="T 7 0 10 "+height+" ______________________________________________\r\n";
    	height+=40;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(pago.getValor()))+"\r\n";
    	height+=52;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "SALDO A LA FECHA: "+getDecTxt(pago.getDeudaCliente()))+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 30 "+height+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosSys.getNombreVendedor())+"\r\n";
    	height+=50;    	
    	strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "FIRMA AUTORIZADA:____________________________________________")+"\r\n";
    	height+=30;
    	
    }
    
    
    
   /* private void generateHeaderFactura()
    {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, factura_in.getRazonSocial())+"\r\n";
    	height+=52;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, factura_in.getRepresentante())+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, factura_in.getRegimenNit())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, factura_in.getDireccionTel())+"\r\n";
    	height+=50;
    	strHeader +="T 7 0 65 "+height+" Factura: "+getFillText(ALIGN_CENTER, 3, factura_in.getPrefijo())+" "+getFillText(ALIGN_CENTER,8, ""+factura_in.NFactura)+"   Caja: "+getFillText(ALIGN_LEFT, 3, ""+factura_in.getNCaja())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"   Fecha: "+getFillText(ALIGN_LEFT, 10, factura_in.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, factura_in.getHora())+"\r\n";
    	height+=30; 
    	strHeader +="T 7 0 65 "+height+" Cliente: "+getFillText(ALIGN_LEFT, 35, factura_in.getNombreCliente())+"\r\n";
    	height+=30;    
    	strHeader +="T 7 0 65 "+height+"     Nit: "+getFillText(ALIGN_LEFT, 35, factura_in.getNitCliente())+"\r\n";
    	height+=80;
    	
    }
*/
	private void generateHeaderFactura()
	{
		try {
			strHeader += "T 7 1 16 " + height + " " + getFillText(ALIGN_CENTER, 46, factura_in.getRazonSocial()) + "\r\n";
			height += 52;
			strHeader += "T 7 1 16 " + height + " " + getFillText(ALIGN_CENTER, 46, factura_in.getRepresentante()) + "\r\n";
			height += 52;
			strHeader += "T 7 0 16 " + height + " " + getFillText(ALIGN_CENTER, 46, factura_in.getRegimenNit()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 16 " + height + " " + getFillText(ALIGN_CENTER, 46, factura_in.getDireccionTel()) + "\r\n";
			height += 50;
			strHeader += "T 7 0 65 " + height + " Factura: " + getFillText(ALIGN_CENTER, 3, factura_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + factura_in.NFactura) + "   Caja: " + getFillText(ALIGN_LEFT, 3, "" + factura_in.getNCaja()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + "   Fecha: " + getFillText(ALIGN_LEFT, 10, factura_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, factura_in.getHora()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + " Cliente: " + getFillText(ALIGN_LEFT, 35, factura_in.getNombreCliente()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + "     Nit: " + getFillText(ALIGN_LEFT, 35, factura_in.getNitCliente()) + "\r\n";
			height += 80;
		}
		catch (Exception e)
			{

			}
	}

	private void generateBodyFactura()
    {
		try
		{
    	strBody +="T 7 0 10 "+height+" CANT        ARTICULO       IVA VR-UNT TOTAL\r\n";
    	height+=40;
    	for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
			ArticulosFactura ap=factura_in.getListaArticulos().get(i);			
			strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 37, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText())+"\r\n";
			height+=30;
    	}
		}
		catch (Exception e)
		{

		}
    }
    private void generateFooterFactura()
    {
		try {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+factura_in.getValorText())+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 65 "+height+"    Paga: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getCambio()))+"\r\n";
    	height+=40;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO")+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 53 "+height+" Efectivo: "+getFillText(ALIGN_LEFT, 10, factura_in.getValorText())+"\r\n";
    	height+=40;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 42, "RELACION DE IMPUESTOS")+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 40 "+height+" Excludido: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase0()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+" Base 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase5()))+"   Iva 5%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva5()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 53 "+height+" Base 16%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase16()))+"  Iva 16%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva16()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 53 "+height+" Base 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getBase19()))+"  Iva 19%: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getIva19()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 53 "+height+" ImpoComo: "+getFillText(ALIGN_LEFT, 10, factura_in.getFormatoDecimal(factura_in.getImpoCmo()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 30 "+height+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, factura_in.getNombreVendedor())+"\r\n";
    	height+=40;
    	strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, factura_in.getResDian())+"\r\n";
    	height+=30;
    	strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, factura_in.getRango())+"\r\n";
    	height+=50;
    	strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "RECIBIDO:__________________________________________________________")+"\r\n";
    	height+=30;
		//strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "SOMOS GRANDES CONTRIBUYENTES")+"\r\n";
		//height+=30;
		}
		catch (Exception e)
		{

		}
		try {
			ArrayList<String> listaobser = factura_in.getObservacion(factura_in.getObservaciones());
			for (int i = 0; i < listaobser.size(); i++) {
				strHeader += "T 0 2 30 " + height + " " + getFillText(ALIGN_CENTER, 66, listaobser.get(i).toString()) + "\r\n";
				height += 30;
			}
		}
		catch (Exception e)
		{

		}


    	
    }
	private void generateHeaderRemision()
	{
		try {
			strHeader += "T 7 1 16 " + height + " " + getFillText(ALIGN_CENTER, 46, remision_in.getRazonSocial()) + "\r\n";
			height += 52;
			strHeader += "T 7 1 16 " + height + " " + getFillText(ALIGN_CENTER, 46, remision_in.getRepresentante()) + "\r\n";
			//height += 52;
			//strHeader += "T 7 0 16 " + height + " " + getFillText(ALIGN_CENTER, 46, remision_in.getRegimenNit()) + "\r\n";
			//height += 30;
			//strHeader += "T 7 0 16 " + height + " " + getFillText(ALIGN_CENTER, 46, remision_in.getDireccionTel()) + "\r\n";
			height += 50;
			strHeader += "T 7 0 65 " + height + " Cotizacion: " + getFillText(ALIGN_CENTER, 3, remision_in.getPrefijo()) + " " + getFillText(ALIGN_CENTER, 8, "" + remision_in.NRemision)  + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + "   Fecha: " + getFillText(ALIGN_LEFT, 10, remision_in.getFecha()) + "     Hora: " + getFillText(ALIGN_LEFT, 5, remision_in.getHora()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + " Cliente: " + getFillText(ALIGN_LEFT, 35, remision_in.getNombreCliente()) + "\r\n";
			height += 30;
			strHeader += "T 7 0 65 " + height + "     Nit: " + getFillText(ALIGN_LEFT, 35, remision_in.getNitCliente()) + "\r\n";
			height += 80;
		}
		catch (Exception e)
		{

		}
	}

	private void generateBodyRemision()
	{
		try
		{
			strBody +="T 7 0 10 "+height+" CANT        ARTICULO       IVA VR-UNT TOTAL\r\n";
			height+=40;
			for (int i = 0; i < remision_in.getListaArticulos().size(); i++) {
				ArticulosRemision ap=remision_in.getListaArticulos().get(i);
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 37, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 2, ap.getIva()+"")+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText())+"\r\n";
				height+=30;
			}
		}
		catch (Exception e)
		{

		}
	}
	private void generateFooterRemision()
	{
		try {
			strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+remision_in.getValorText())+"\r\n";
			height+=52;
			strHeader +="T 7 0 65 "+height+"    Paga: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, remision_in.getFormatoDecimal(remision_in.getCambio()))+"\r\n";
			height+=40;
			strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO")+"\r\n";
			height+=52;
			strHeader +="T 7 0 53 "+height+" Efectivo: "+getFillText(ALIGN_LEFT, 10, remision_in.getValorText())+"\r\n";
			height+=40;
			strHeader +="T 7 0 30 "+height+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, remision_in.getNombreVendedor())+"\r\n";
			height+=50;
			strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "RECIBIDO:__________________________________________________________")+"\r\n";
			height+=30;
			//strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "SOMOS GRANDES CONTRIBUYENTES")+"\r\n";
			//height+=30;
		}
		catch (Exception e)
		{

		}
		try {
			ArrayList<String> listaobser = remision_in.getObservacion(remision_in.getObservaciones());
			for (int i = 0; i < listaobser.size(); i++) {
				strHeader += "T 0 2 30 " + height + " " + getFillText(ALIGN_CENTER, 66, listaobser.get(i).toString()) + "\r\n";
				height += 30;
			}
		}
		catch (Exception e)
		{

		}



	}



	private void generateHeaderPedido()
	{
		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, parametrosPos.getRazonSocial())+"\r\n";
		height+=52;
		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, parametrosPos.getRepresentante())+"\r\n";
		height+=50;
		strHeader +="T 7 0 65 "+height+" Orden de pedido: "+getFillText(ALIGN_CENTER, 3, pedido_in.idCodigoInterno+"")+"\r\n";
		height+=30;
		strHeader +="T 7 0 65 "+height+"   Fecha: "+getFillText(ALIGN_LEFT, 10, pedido_in.getFecha())+"     Hora: "+getFillText(ALIGN_LEFT, 5, pedido_in.getHora())+"\r\n";
		height+=30;
		strHeader +="T 7 0 65 "+height+" Cliente: "+getFillText(ALIGN_LEFT, 35, pedido_in.getNombreCliente())+"\r\n";
		height+=80;

	}

	private void generateBodyPedido()
	{
		strBody +="T 7 0 10 "+height+" CANT        ARTICULO       VR-UNT   TOTAL\r\n";
		height+=40;
		for (int i = 0; i < listaAPedido.size(); i++) {
			ArticulosPedido ap=listaAPedido.get(i);
			strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 37, ""+ap.getNombre())+"   "+getFillText(ALIGN_RIGHT, 9, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 9, ap.getValorText())+"\r\n";
			height+=30;
		}
	}
	private void generateFooterPedido()
	{
		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 40, "SUBTOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getSubTotal()))+"\r\n";
		height+=50;
		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 40, "DESCUENTO: "+pedido_in.getFormatoDecimal(pedido_in.getDescuentoTotal()))+"\r\n";
		height+=50;
		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 40, "TOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getValor()))+"\r\n";
		height+=50;
		strHeader +="T 7 0 30 "+height+" LO ATENDIO: "+getFillText(ALIGN_LEFT, 30, parametrosPos.getNombreVendedor())+"\r\n";
		height+=50;
		strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "RECIBIDO:__________________________________________________________")+"\r\n";
		height+=30;
		//strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "SOMOS GRANDES CONTRIBUYENTES")+"\r\n";
		//height+=30;
		//strHeader +="T 0 2 16 "+height+" "+getFillText(ALIGN_CENTER, 66, "RESOLUCION 000041 DE 30/01/2014 AGENTE RETENEDOR DE IVA E ICA")+"\r\n";
		//height+=30;

	}
    
    private void generateHeaderTraslado()
    {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, traslado_in.getRazonSocial())+"\r\n";
    	height+=52;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, traslado_in.getRepresentante())+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, traslado_in.getRegimenNit())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, traslado_in.getDireccionTel())+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46,"TRASLADO ENTRE BODEGAS")+"\r\n";
      	height+=50;
    	strHeader +="T 7 0 65 "+height+" Traslado: "+getFillText(ALIGN_CENTER,11, ""+traslado_in.getIdCodigoExterno())+"      Caja: "+getFillText(ALIGN_LEFT, 3, ""+traslado_in.getNCaja())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"    Fecha: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFecha())+"    Hora: "+getFillText(ALIGN_LEFT, 5, traslado_in.getHora())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"   Origen: "+getFillText(ALIGN_LEFT, 36,traslado_in.bodegaOrigen.getBodega())+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"  Destino: "+getFillText(ALIGN_LEFT, 36,traslado_in.bodegaDestino.getBodega())+"\r\n";
    	height+=30;   	
    }

    private void generateBodyTraslado()
    {
    	strBody +="T 7 0 10 "+height+" CANT        ARTICULO             VR UNT  TOTAL\r\n";
    	height+=40;
    	for (int i = 0; i < traslado_in.getListaArticulos().size(); i++) {
			ArticulosTraslado ap=traslado_in.getListaArticulos().get(i);			
			strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 4, ""+ap.getCantidad())+" "+getFillText(ALIGN_LEFT, 40, ""+ap.getNombre())+" "+getFillText(ALIGN_RIGHT, 10, ap.getValorUnitarioText())+" "+getFillText(ALIGN_RIGHT, 10, ap.getValorText())+"\r\n";
			height+=30;
    	}
    }
    private void generateFooterTraslado()
    {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+traslado_in.getValorText())+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 65 "+height+"    Paga: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getDineroRecibido()))+"   Cambio: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getCambio()))+"\r\n";
    	height+=40;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 42, "MEDIOS DE PAGO")+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 53 "+height+" Efectivo: "+getFillText(ALIGN_LEFT, 10, traslado_in.getValorText())+"\r\n";
    	height+=40;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 42, "RELACION DE IMPUESTOS")+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 40 "+height+" Excludido: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getBase0()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+" Base 5%: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getBase5()))+"   Iva 5%: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getIva5()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 53 "+height+" Base 16%: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getBase16()))+"  Iva 16%: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getIva16()))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 53 "+height+" ImpoComo: "+getFillText(ALIGN_LEFT, 10, traslado_in.getFormatoDecimal(traslado_in.getImpoCmo()))+"\r\n";
    	height+=40;
    	strHeader +="BOX 30 "+height+" 550 "+(height+150)+" 2 \r\n";
    	height+=10;
    	strHeader +="T 7 0 50 "+height+" FIRMA DE ENTREGA"+"\r\n";
    	height+=100;
    	strHeader +="BOX 50 "+height+" 530 "+(height+2)+" 1 \r\n";
    	height+=7;
    	strHeader +="T 7 0 50 "+height+" cc"+"\r\n";    	
    	height+=70;
    	strHeader +="BOX 30 "+height+" 550 "+(height+150)+" 2 \r\n";
    	height+=10;
    	strHeader +="T 7 0 50 "+height+" FIRMA DE RECIBIDO"+"\r\n";
    	height+=100;
    	strHeader +="BOX 50 "+height+" 530 "+(height+2)+" 1 \r\n";
    	height+=7;
    	strHeader +="T 7 0 50 "+height+" cc"+"\r\n";
    	height+=40;
    }
    
    
    private void generateHeaderDocument()
    {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, datos.get(0))+"\r\n";
    	height+=52;    
    	strHeader +="T 7 0 65 "+height+" Generado: "+getFillText(ALIGN_LEFT, 10, datos.get(1))+"    Hora: "+getFillText(ALIGN_LEFT, 5, datos.get(2))+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 65 "+height+"    Desde: "+getFillText(ALIGN_LEFT, 10, datos.get(3))+"   Hasta: "+getFillText(ALIGN_LEFT, 10, datos.get(4))+"\r\n";
    	height+=30;
    	if(operacion==TRANSLADO)
    	{
	    	strHeader +="T 7 0 65 "+height+"   Origen: "+getFillText(ALIGN_LEFT, 36,datos.get(5))+"\r\n";
	    	height+=30;
	    	strHeader +="T 7 0 65 "+height+"  Destino: "+getFillText(ALIGN_LEFT, 36,datos.get(6))+"\r\n";
	    	height+=30;
    	}
    	height+=40;
    }

    private void generateBodyDocumentos()
    {
    	if(printArticulos)
    	{
	    	strBody +="T 7 0 10 "+height+" CANT        ARTICULO                     TOTAL\r\n";
	    	height+=40;
	    	for (int i = 0; i < listaArticulos.size(); i++) {
				Articulo a=listaArticulos.get(i);			
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 5, ""+a.getCantidadVentas())+" "+getFillText(ALIGN_LEFT, 49, ""+a.getNombre())+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(a.getValorVentas()))+"\r\n";
				height+=30;
	    	}	    	
    	}
    	else if(operacion==FACTURA &  !printArticulos)
    	{
    		strBody +="T 7 0 10 "+height+" No.         CLIENTE        FECHA Y HORA  TOTAL\r\n";
	    	height+=40;
	    	for (int i = 0; i < listaFacturas.size(); i++) {
				Factura_in f =listaFacturas.get(i);			
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 5, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 33, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor))+"\r\n";
				height+=30;
	    	}
    	}
		else if(operacion==REMISION &  !printArticulos)
		{
			strBody +="T 7 0 10 "+height+" No.         CLIENTE        FECHA Y HORA  TOTAL\r\n";
			height+=40;
			for (int i = 0; i < listaRemisiones.size(); i++) {
				Remision_in  f =listaRemisiones.get(i);
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 5, ""+f.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 33, ""+f.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, f.getFecha()+" "+f.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(f.valor))+"\r\n";
				height+=30;
			}
		}
    	else if(operacion==TRANSLADO& !printArticulos)
    	{
    		strBody +="T 7 0 10 "+height+" No.         BODEGAS        FECHA Y HORA  TOTAL\r\n";
	    	height+=40;
	    	for (int i = 0; i < listaTraslados.size(); i++) {
				Traslado_in t =listaTraslados.get(i);			
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 5, ""+t.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 33, ""+t.bodegaOrigen.getBodega()+" a "+t.bodegaDestino.getBodega())+" "+getFillText(ALIGN_RIGHT, 16, t.getFecha()+" "+t.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(t.totalTraslado))+"\r\n";
				height+=30;
	    	}
    	}
    	else if(operacion==PEDIDO & !printArticulos)
    	{
    		strBody +="T 7 0 10 "+height+" No.         CLIENTE        FECHA Y HORA  TOTAL\r\n";
	    	height+=40;
	    	for (int i = 0; i < listaPedidos.size(); i++) {
				Pedido_in p =listaPedidos.get(i);			
				strBody +="T 0 2 16 "+height+" "+getFillText(ALIGN_LEFT, 5, ""+p.idCodigoExterno)+" "+getFillText(ALIGN_LEFT, 33, ""+p.nombreCliente)+" "+getFillText(ALIGN_RIGHT, 16, p.getFecha()+" "+p.hora)+" "+getFillText(ALIGN_RIGHT, 10, getDecTxt(p.valor))+"\r\n";
				height+=30;
	    	}
    	}
    }
    private void generateFooterDocumentos()
    {
    	if(printArticulos)
    	{
    		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(getTotalArticulos()))+"\r\n";
        	height+=52;
    	}
    	else if(operacion==FACTURA & !printArticulos)
    	{
    		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(getTotalFacturas()))+"\r\n";
        	height+=52;
    	}
		else if(operacion==REMISION & !printArticulos)
		{
			strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(getTotalRemisiones()))+"\r\n";
			height+=52;
		}
    	else if(operacion==TRANSLADO & !printArticulos)
    	{
    		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(getTotalTraslados()))+"\r\n";
        	height+=52;
    	}  
    	else if(operacion==PEDIDO& !printArticulos)
    	{
    		strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_RIGHT, 45, "TOTAL: "+getDecTxt(getTotalPedidos()))+"\r\n";
        	height+=52;
    	}  
    }
    private long getTotalArticulos()
    {
    	long res=0;
    	 for (int i = 0; i < listaArticulos.size(); i++) {
    			Articulo a=listaArticulos.get(i);			
    			res+=a.getValorVentas();    			
    		}
    	return res;
    }
    private long getTotalTraslados()
    {
    	long res=0;
    	 for (int i = 0; i < listaTraslados.size(); i++) {
    			Traslado_in t=listaTraslados.get(i);			
    			res+=t.getTotalTraslado();    			
    		}
    	return res;
    }
    private long getTotalPedidos()
    {
    	long res=0;
    	 for (int i = 0; i < listaPedidos.size(); i++) {
    			Pedido_in t=listaPedidos.get(i);			
    			res+=t.getValor();    			
    		}
    	return res;
    }
    private long getTotalFacturas()
    {
    	long res=0;
    	 for (int i = 0; i < listaFacturas.size(); i++) {
    			Factura_in f=listaFacturas.get(i);			
    			res+=f.getValor();    			
    		}
    	return res;
    }
	private long getTotalRemisiones()
	{
		long res=0;
		for (int i = 0; i < listaRemisiones.size(); i++) {
			Remision_in  f=listaRemisiones.get(i);
			res+=f.getValor();
		}
		return res;
	}


	private void generateHeaderZFinanciera()
    {
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, "DISTRIBUIDORA EL CHISPAZO")+"\r\n";
    	height+=52;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, "MARIO ERNESTO HERNANDEZ GALLO")+"\r\n";
    	height+=52;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, "NIT 6761024-3 REGIMEN COMUN")+"\r\n";
    	height+=60;
    	strHeader +="T 7 0 16 "+height+" INFORME DIARIO DE VENTAS: "+getFillText(ALIGN_LEFT, 20, zFinanciera.getNumZ()+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" CAJA:                     "+getFillText(ALIGN_LEFT, 20, zFinanciera.getNCaja()+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" FECHA DE CORTE:           "+getFillText(ALIGN_LEFT, 20, zFinanciera.getFecha()+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" FECTURA INICIAL:          "+getFillText(ALIGN_LEFT, 20, zFinanciera.getFacturaInicial()+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" FACTURA FINAL:            "+getFillText(ALIGN_LEFT, 20, zFinanciera.getFacturaFinal()+"")+"\r\n";
    	height+=40;   	
    }
    private void generateBodyZFinanciera()
    {
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, "IVA DEL 16%")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"VENTAS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Ventas16)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"IVA")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Iva16)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Total16)+"")+"\r\n";
    	height+=40;
    	
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, "IVA DEL 5%")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"VENTAS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Ventas5)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"IVA")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Iva5)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Total5)+"")+"\r\n";
    	height+=40;
    	
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, "EXENTO")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"VENTAS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.VentasExentas)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"IVA")+getFillText(ALIGN_RIGHT, 23, "0")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.VentasExentas)+"")+"\r\n";
    	height+=40;
    	
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, "IVA DEL 8%")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"VENTAS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Ventas8)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"IMPO CONSUMO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Iva8)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL DEPARTAMENTO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Total8)+"")+"\r\n";
    	height+=40;
    	
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,30,"# DE TRANSACCIONES EN EL DIA ")+getFillText(ALIGN_RIGHT,16,zFinanciera.NTransacciones+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"VENTAS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Ventas)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"IMPO CONSUMO")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.ImpoCmo)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"DESCUENTOS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Descuento)+"")+"\r\n";
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.Total)+"")+"\r\n";
    	height+=40;
    	strHeader +="T 7 1 16 "+height+" "+getFillText(ALIGN_CENTER, 46, "MEDIOS DE PAGO")+"\r\n";
    	height+=40;
    	
    }
    private void generateFooterZFinanciera()
    {
    	for (int i = 0; i < zFinanciera.listaMendiosDePago.size(); i++) {
    		strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_CENTER, 46, zFinanciera.listaMendiosDePago.get(i).Cuenta+"")+"\r\n";
        	height+=30;
        	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,zFinanciera.listaMendiosDePago.get(i).MedioPago)+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.listaMendiosDePago.get(i).Valor)+"")+"\r\n";
        	height+=30;
    	}  
    	height+=30;
    	strHeader +="T 7 0 16 "+height+" "+getFillText(ALIGN_LEFT,23,"TOTAL MEDIOS")+getFillText(ALIGN_RIGHT, 23, getDecTxt(zFinanciera.getTotalMedios())+"")+"\r\n";
    	height+=40;
    	
    }
    
    private void generateDocPrint()
    {
    	
    	strConfInitial="! 0 200 200 "+(height+60)+" 1\r\n";
    	strConfFinal=  "PRINT\r\n";    	
    	strPrint=strConfInitial+strHeader+strBody+strFooter+strConfFinal;
    	
    }

    private String getFillText(int align, int length,String text)
	{
		String res="";
	   if(text.length()>=length)
		{
			res=text.substring(0, length);
		}
		else
		{
			if(align==ALIGN_CENTER)
			{
				int esp=length-text.length();
				for (int i = 0; i < (esp/2); i++) {
					res+=" ";							
				}
				res+=text+res;
			}
			else if(align==ALIGN_LEFT)
			{
				int esp=length-text.length();
				for (int i = 0; i < esp; i++) {
					res+=" ";							
				}
				res=text+res;
			}
			else if((align==ALIGN_RIGHT))
			{
				int esp=length-text.length();
				for (int i = 0; i < esp; i++) {
					res+=" ";							
				}
				res+=text;
			}	
		}
		
		return res;
	}  
	public String getDecTxt(long value) {
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		return decimalFormat.format(value);
	}
   
}