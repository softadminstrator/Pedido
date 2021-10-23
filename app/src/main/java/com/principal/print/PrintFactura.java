package com.principal.print;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.util.Log;

import com.bixolon.android.library.BxlService;
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
import com.principal.mundo.sysws.Prestamo;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.internal.VerbosePrinter;

public class PrintFactura {
	
	private static final int ALIGN_LEFT = 1;
	private static final int ALIGN_CENTER = 2;
	private static final int ALIGN_RIGHT = 3;
	
	private BxlService mBxlService = null;
	private boolean conn = false;

	private static final String TAG = "BXLEXAMPLE";
	private String mensaje;
	private Factura_in factura_in;
	private Remision_in remision_in;
	private Traslado_in traslado_in;
	private ZFinanciera zFinanciera;
	private Parametros parametrosPos;

	private Pedido_in pedido_in;
	private ArrayList<ArticulosPedido> listaAPedido;

	public PrintFactura() {		
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	private Pago pago;
	private Prestamo prestamo;



	public boolean printPrestamo(Prestamo prestamo, Parametros parametros)
	{
		this.prestamo=prestamo;
		this.parametrosPos=parametros;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");

			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				generateHeaderPrestamo(returnValue);
				generateFooterPrestamo(returnValue);

			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {
				mensaje="ERROR [" + returnValue + "]";
				//			mostrarMensaje(, "l");
			}
			CheckGC("PrintText_End");
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}


	private boolean generateHeaderPrestamo(int returnValue)
	{
		boolean res=false;

		returnValue = mBxlService.PrintText(parametrosPos.getRazonSocial()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getRepresentante()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("COMPROBANTE DE PAGO"+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		//NUMERO DE FACTURA Y NUMERO DE CAJA

		returnValue = mBxlService.PrintText("\n"+"No. Pago: "+prestamo.getIdPrestamo()+" ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("     Caja:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getCaja()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		returnValue = mBxlService.PrintText("\n"+"Fecha: "+prestamo.getFecha()+" ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("    Hora:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(prestamo.getHora()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		returnValue = mBxlService.PrintText("    Cliente:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(prestamo.getNombreCliente()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		return true;


	}

	private boolean generateFooterPrestamo(int returnValue)
	{
		returnValue = mBxlService.PrintText("______________________________________________"+"\n ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		returnValue = mBxlService.PrintText("SALDO ANTERIOR:"+getDecTxt(prestamo.getSaldoAnterior())+"\n ",
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("VALOR PRESTAMO:"+getDecTxt(prestamo.getValorPrestamo())+"\n ",
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		returnValue = mBxlService.PrintText("NUEVO SALDO:"+getDecTxt(prestamo.getNuevoSaldo())+"\n ",
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(" LO ATENDIO: "+parametrosPos.getNombreVendedor()+"\n ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("FIRMA AUTORIZADA:____________________________________________\n ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		return true;

	}
	public boolean printPago(Pago pago, Parametros parametros)
	{
		this.pago=pago;
		this.parametrosPos=parametros;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");

			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				generateHeaderPago(returnValue);
				generateBodyPago(returnValue);
				generateFooterPago(returnValue);

			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {
				mensaje="ERROR [" + returnValue + "]";
				//			mostrarMensaje(, "l");
			}
			CheckGC("PrintText_End");
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}


	private boolean generateHeaderPago(int returnValue)
	{
		boolean res=false;

		returnValue = mBxlService.PrintText(parametrosPos.getRazonSocial()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getRepresentante()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("COMPROBANTE DE PAGO"+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		//NUMERO DE FACTURA Y NUMERO DE CAJA

		returnValue = mBxlService.PrintText("\n"+"No. Pago: "+pago.getIdPago()+" ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("     Caja:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getCaja()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		returnValue = mBxlService.PrintText("\n"+"Fecha: "+pago.getFecha()+" ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("    Hora:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getHora()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		returnValue = mBxlService.PrintText("    Cliente:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(pago.getNombreCliente()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		return true;


	}
	private boolean generateBodyPago(int returnValue)
	{
		returnValue = mBxlService.PrintText("     DESCRIPCION: \n ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		if(pago.getListaPagosFactura()!=null)
		{
			for (int i = 0; i < pago.getListaPagosFactura().size(); i++) {
				PagosFactura pagosFactura=pago.getListaPagosFactura().get(i);

				returnValue = mBxlService.PrintText("______________________________________________\n ",
						mBxlService.BXL_ALIGNMENT_CENTER,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				returnValue = mBxlService.PrintText(" Factura: "+pagosFactura.getNFactura()+" Caja: "+pagosFactura.getNCaja()+"\n ",
						mBxlService.BXL_ALIGNMENT_CENTER,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

				returnValue = mBxlService.PrintText("               Saldo Anterior: "+getDecTxt(pagosFactura.getSaldoAnterior())+"\n ",
						mBxlService.BXL_ALIGNMENT_CENTER,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

				returnValue = mBxlService.PrintText(" FORMA DE PAGO                    VALOR        \n ",
						mBxlService.BXL_ALIGNMENT_CENTER,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				if(pagosFactura.getListaPagoFac()!=null)
				{
					for (int j = 0; j < pagosFactura.getListaPagoFac().size(); j++) {
						ItemPagoFac itemPagoFac=pagosFactura.getListaPagoFac().get(j);
						returnValue = mBxlService.PrintText(" "+itemPagoFac.getFormaPago()+"                 "+getDecTxt(itemPagoFac.getValor())+"\n ",
								mBxlService.BXL_ALIGNMENT_LEFT,
								mBxlService.BXL_FT_BOLD,
								mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
					}
				}
				returnValue = mBxlService.PrintText("Descuento: "+getDecTxt(pagosFactura.getDescuento())+"\n ",
						mBxlService.BXL_ALIGNMENT_RIGHT,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

				returnValue = mBxlService.PrintText("Nuevo Saldo: "+getDecTxt(pagosFactura.getSaldo())+"\n ",
						mBxlService.BXL_ALIGNMENT_RIGHT,
						mBxlService.BXL_FT_BOLD,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

			}
		}
		return true;
	}
	private boolean generateFooterPago(int returnValue)
	{
		returnValue = mBxlService.PrintText("______________________________________________"+"\n ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		returnValue = mBxlService.PrintText("TOTAL:"+getDecTxt(pago.getValor())+"\n ",
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		returnValue = mBxlService.PrintText("SALDO A LA FECHA: :"+getDecTxt(pago.getDeudaCliente())+"\n ",
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(" LO ATENDIO: "+parametrosPos.getNombreVendedor()+"\n ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("FIRMA AUTORIZADA:____________________________________________\n ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		return true;

	}



	public boolean printFactura(Factura_in factura_in)
	{
		this.factura_in=factura_in;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");		
	
			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				printHeader(returnValue);
				printBody(returnValue);
				printFooter(returnValue);
			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {		
				mensaje="ERROR [" + returnValue + "]";
	//			mostrarMensaje(, "l");
			}	
			CheckGC("PrintText_End");		
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean printRemision(Remision_in  remision_in)
	{
		this.remision_in=remision_in;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");

			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				printHeaderRemision(returnValue) ;
				printBodyRemision(returnValue) ;
				printFooterRemision(returnValue);
			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {
				mensaje="ERROR [" + returnValue + "]";
				//			mostrarMensaje(, "l");
			}
			CheckGC("PrintText_End");
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean printZFinanciera(ZFinanciera zFinanciera)
	{
		this.zFinanciera=zFinanciera;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");		
	
			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				printHeaderZFinanciera(returnValue);
				printBodyZFinanciera(returnValue);
				printFooterZFinanciera(returnValue);
			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {		
				mensaje="ERROR [" + returnValue + "]";
	//			mostrarMensaje(, "l");
			}	
			CheckGC("PrintText_End");		
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean printTraslado(Traslado_in traslado_in)
	{
		this.traslado_in=traslado_in;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");		
	
			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				printHeaderTraslado(returnValue);
				printBodyTraslado(returnValue);
				printFooterTraslado(returnValue);
			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {		
				mensaje="ERROR [" + returnValue + "]";
	//			mostrarMensaje(, "l");
			}	
			CheckGC("PrintText_End");		
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
//			mensaje="Impresión enviada correctamente";
		
	}

	public boolean printPedido(Pedido_in pedido_in,ArrayList<ArticulosPedido>  listaAPedido,Parametros parametrosPos)
	{
		this.pedido_in=pedido_in;
		this.listaAPedido=listaAPedido;
		this.parametrosPos=parametrosPos;
		if(conectar())
		{
			int returnValue = mBxlService.BXL_SUCCESS;
			CheckGC("PrintText_Start");

			returnValue = mBxlService.GetStatus();
			if (returnValue == mBxlService.BXL_SUCCESS) {
				printHeaderPedido(returnValue);
				printBodyPedido(returnValue);
				printFooterPedido(returnValue);
			}
			if (returnValue == mBxlService.BXL_SUCCESS) {
				returnValue = mBxlService.LineFeed(5);
			}
			returnValue = mBxlService.GetStatus();
			if (returnValue != mBxlService.BXL_SUCCESS) {
				mensaje="ERROR [" + returnValue + "]";
				//			mostrarMensaje(, "l");
			}
			CheckGC("PrintText_End");
			desconectar();
			mensaje="Impresión enviada correctamente";
			return true;
		}
		else
		{
			return false;
		}
	}


	private boolean printHeader(int returnValue)
	{
		boolean res=false;
	
			returnValue = mBxlService.PrintText(factura_in.getRazonSocial()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getRepresentante()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getRegimenNit()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getDireccionTel()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//NUMERO DE FACTURA Y NUMERO DE CAJA

			returnValue = mBxlService.PrintText("\n"+"Factura: "+factura_in.prefijo+" ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.NFactura+"",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("     Caja:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getNCaja()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//FECHA Y HORA

			returnValue = mBxlService.PrintText("Fecha:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getFecha()+"",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("     Hora:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getHora()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//Cliente

			returnValue = mBxlService.PrintText("Cliente:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getNombreCliente()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("Nit:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(factura_in.getNitCliente()+"\n\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		
				
		return true;
	}
	private boolean printBody(int returnValue)
	{
		boolean res=false;	
		    //Cliente
			returnValue = mBxlService.PrintText(" CANT        ARTICULO             VR UNIT  TOTAL\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			for (int i = 0; i < factura_in.getListaArticulos().size(); i++) {
				ArticulosFactura ap=factura_in.getListaArticulos().get(i);
				returnValue = mBxlService.PrintText(getEspCantidad(ap.getCantidad()+"")+"  "+getFillText(36,ap.getNombre())+getFillText(10,ap.getValorUnitarioText())+" "+getFillText(10,ap.getValorText())+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			}

			
		return true;
	}
	
	private boolean printFooter(int returnValue)
	{
		boolean res=false;	
		

	
		    //Cliente
			returnValue = mBxlService.PrintText("\n"+getEspValor("TOTAL: "+factura_in.getValorText()+"\n\n"),
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_1WIDTH| mBxlService.BXL_TS_1HEIGHT);	
			
			 //Medios de pago
			
			returnValue = mBxlService.PrintText("MEDIOS DE PAGO\n",
							mBxlService.BXL_ALIGNMENT_CENTER,
							mBxlService.BXL_FT_BOLD,
							mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			returnValue = mBxlService.PrintText(" EFECTIVO:  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(12,factura_in.getFormatoDecimal(factura_in.getDineroRecibido()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n CAMBIO:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(12,factura_in.getFormatoDecimal(factura_in.getCambio()))+"                        \n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
//			RELACION DE IVA
			
			returnValue = mBxlService.PrintText("RELACION DE IVA\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
	
			returnValue = mBxlService.PrintText(" EXCLUIDO:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,factura_in.getFormatoDecimal(factura_in.getBase0()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
	
			
			//RELACION IVA 5%
			
			returnValue = mBxlService.PrintText("\n BASE 5%:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,factura_in.getFormatoDecimal(factura_in.getBase5()))+"  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			returnValue = mBxlService.PrintText("IVA 5%:     ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(10,factura_in.getFormatoDecimal(factura_in.getIva5())),
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
//RELACION IVA 16%
			
			returnValue = mBxlService.PrintText("\n BASE 16%:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,factura_in.getFormatoDecimal(factura_in.getBase16()))+"  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			returnValue = mBxlService.PrintText("IVA 16%:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(10,factura_in.getFormatoDecimal(factura_in.getIva16())),
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
//RELACION IVA 19%
			
			returnValue = mBxlService.PrintText("\n BASE 19%:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,factura_in.getFormatoDecimal(factura_in.getBase19()))+"  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			returnValue = mBxlService.PrintText("IVA 19%:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(10,factura_in.getFormatoDecimal(factura_in.getIva19()))+"\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
//RELACION IMPOCONSUMO
			
			returnValue = mBxlService.PrintText(" IMPOCONSUMO:",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,factura_in.getFormatoDecimal(factura_in.getImpoCmo()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
//LO ATENDIO
			
			returnValue = mBxlService.PrintText("\n\nLO ATENDIO:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(34,factura_in.getNombreVendedor()+" Tel."+factura_in.getTelefonoVendedor())+"\n\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
//			RESOLUCION 
			returnValue = mBxlService.PrintText(getFillText(48,factura_in.getResDian())+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			//RANGO
			returnValue = mBxlService.PrintText(getFillText(48,factura_in.getRango()),
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		ArrayList<String> listaobserv=factura_in.getObservacion(factura_in.getObservaciones());
		for(int i=0;i<listaobserv.size();i++)
		{
			returnValue = mBxlService.PrintText(getFillText(48,listaobserv.get(i).toString()),
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		}

/**			returnValue = mBxlService.PrintText(getFillText(48,"SOMOS GRANDES CONTRIBUYENTES"),
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			
			returnValue = mBxlService.PrintText(getFillText(48,"RESOLUCION 000041 DE 30/01/2014 AGENTE RETENEDOR DE IVA"),
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	**/
			
			
			
		return true;
	}
	private boolean printHeaderRemision(int returnValue)
	{
		boolean res=false;

		returnValue = mBxlService.PrintText(remision_in.getRazonSocial()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getRepresentante()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getRegimenNit()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getDireccionTel()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		//NUMERO DE FACTURA Y NUMERO DE CAJA

		returnValue = mBxlService.PrintText("\n"+"Cotizacion: "+remision_in.prefijo+" ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.NRemision+"",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		//FECHA Y HORA

		returnValue = mBxlService.PrintText("Fecha:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getFecha()+"",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("     Hora:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getHora()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		//Cliente

		returnValue = mBxlService.PrintText("Cliente:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getNombreCliente()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("Nit:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(remision_in.getNitCliente()+"\n\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);


		return true;
	}
	private boolean printBodyRemision(int returnValue)
	{
		boolean res=false;
		//Cliente
		returnValue = mBxlService.PrintText(" CANT        ARTICULO             VR UNIT  TOTAL\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		for (int i = 0; i < remision_in.getListaArticulos().size(); i++) {
			ArticulosRemision ap=remision_in.getListaArticulos().get(i);
			returnValue = mBxlService.PrintText(getEspCantidad(ap.getCantidad()+"")+"  "+getFillText(36,ap.getNombre())+getFillText(10,ap.getValorUnitarioText())+" "+getFillText(10,ap.getValorText())+"\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		}


		return true;
	}

	private boolean printFooterRemision(int returnValue)
	{
		boolean res=false;



		//Cliente
		returnValue = mBxlService.PrintText("\n"+getEspValor("TOTAL: "+remision_in.getValorText()+"\n\n"),
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_1WIDTH| mBxlService.BXL_TS_1HEIGHT);

		//Medios de pago

		returnValue = mBxlService.PrintText("MEDIOS DE PAGO\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		returnValue = mBxlService.PrintText(" EFECTIVO:  ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(getFillText(12,remision_in.getFormatoDecimal(remision_in.getDineroRecibido()))+"                        ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("\n CAMBIO:    ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(getFillText(12,remision_in.getFormatoDecimal(remision_in.getCambio()))+"                        \n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

//LO ATENDIO

		returnValue = mBxlService.PrintText("\n\nLO ATENDIO:   ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(getFillText(34,remision_in.getNombreVendedor()+" Tel."+remision_in.getTelefonoVendedor())+"\n\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);



		ArrayList<String> listaobserv=remision_in.getObservacion(remision_in.getObservaciones());
		for(int i=0;i<listaobserv.size();i++)
		{
			returnValue = mBxlService.PrintText(getFillText(48,listaobserv.get(i).toString()),
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		}
		return true;
	}
	
	
	
	private boolean printHeaderTraslado(int returnValue)
	{
		boolean res=false;
	
			returnValue = mBxlService.PrintText(traslado_in.getRazonSocial()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getRepresentante()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getRegimenNit()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getDireccionTel()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("TRASLADO ENTRE BODEGAS"+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//NUMERO DE FACTURA Y NUMERO DE CAJA

			returnValue = mBxlService.PrintText("\n"+"Factura:   ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getIdCodigoExterno()+"",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("     Caja:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getNCaja()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//FECHA Y HORA

			returnValue = mBxlService.PrintText("Fecha:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getFecha()+"",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("     Hora:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.getHora()+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			//Cliente

			returnValue = mBxlService.PrintText("Origen:  ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.bodegaOrigen.getBodega()+" \n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("Destino: ",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(traslado_in.bodegaDestino.getBodega()+" \n\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		
				
		return true;
	}
	private boolean printBodyTraslado(int returnValue)
	{
		boolean res=false;	
		    //Cliente
			returnValue = mBxlService.PrintText(" CANT        ARTICULO             VR UNIT  TOTAL\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			for (int i = 0; i < traslado_in.getListaArticulos().size(); i++) {
				ArticulosTraslado ap=traslado_in.getListaArticulos().get(i);
				returnValue = mBxlService.PrintText(getEspCantidad(ap.getCantidad()+"")+"  "+getFillText(36,ap.getNombre())+getFillText(10,ap.getValorUnitarioText())+" "+getFillText(10,ap.getValorText())+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			}

			
		return true;
	}
	
	private boolean printFooterTraslado(int returnValue)
	{
		boolean res=false;	
		

	
		    //Cliente
			returnValue = mBxlService.PrintText("\n"+getEspValor("TOTAL: "+traslado_in.getValorText()+"\n\n"),
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_1WIDTH| mBxlService.BXL_TS_1HEIGHT);	
			
			 //Medios de pago
			
			returnValue = mBxlService.PrintText("MEDIOS DE PAGO\n",
							mBxlService.BXL_ALIGNMENT_CENTER,
							mBxlService.BXL_FT_BOLD,
							mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			returnValue = mBxlService.PrintText(" EFECTIVO:  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(12,traslado_in.getFormatoDecimal(traslado_in.getDineroRecibido()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n CAMBIO:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(12,traslado_in.getFormatoDecimal(traslado_in.getCambio()))+"                        \n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
//			RELACION DE IVA
			
			returnValue = mBxlService.PrintText("RELACION DE IVA\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
	
			returnValue = mBxlService.PrintText(" EXCLUIDO:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,traslado_in.getFormatoDecimal(traslado_in.getBase0()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
	
			
			//RELACION IVA 5%
			
			returnValue = mBxlService.PrintText("\n BASE 5%:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,traslado_in.getFormatoDecimal(traslado_in.getBase5()))+"  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			returnValue = mBxlService.PrintText("IVA 5%:     ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(10,traslado_in.getFormatoDecimal(traslado_in.getIva5())),
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
//RELACION IVA 16%
			
			returnValue = mBxlService.PrintText("\n BASE 16%:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,traslado_in.getFormatoDecimal(traslado_in.getBase16()))+"  ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
			returnValue = mBxlService.PrintText("IVA 16%:    ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(10,traslado_in.getFormatoDecimal(traslado_in.getIva16()))+"\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			

//RELACION IMPOCONSUMO
			
			returnValue = mBxlService.PrintText(" IMPOCONSUMO:",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText(getFillText(11,traslado_in.getFormatoDecimal(traslado_in.getImpoCmo()))+"                        ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
//LO ATENDIO
			
			returnValue = mBxlService.PrintText("\n\nLO ATENDIO:   ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				
		return true;
	}
	
	
	private boolean printHeaderZFinanciera(int returnValue)
	{
		boolean res=false;
	
			returnValue = mBxlService.PrintText("DISTRIBUIDORA EL CHISPAZO"+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("MARIO ERNESTO HERNANDEZ GALLO"+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("NIT 6761024-3 REGIMEN COMUN"+"\n",
					mBxlService.BXL_ALIGNMENT_CENTER,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			
			
			//NUMERO DE FACTURA Y NUMERO DE CAJA

			returnValue = mBxlService.PrintText("\n"+"INFORME DIARIO DE VENTAS: "+zFinanciera.NumZ+" ",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_BOLD,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n"+"CAJA:                     "+zFinanciera.NCaja+"",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n"+"FECHA DE CORTE:           "+zFinanciera.Fecha+"",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n"+"FECTURA INICIAL:          "+zFinanciera.FacturaInicial+"",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			returnValue = mBxlService.PrintText("\n"+"FACTURA FINAL:            "+zFinanciera.FacturaFinal+"",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_DEFAULT,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);						
		return true;
	}
	private boolean printBodyZFinanciera(int returnValue)
	{
		boolean res=false;
		
		//IVA DEL 16%
		returnValue = mBxlService.PrintText("\n"+"DEPARTAMENTO "+getFillText(ALIGN_RIGHT,50,"IVA DEL 16%")+"\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
		returnValue = mBxlService.PrintText("\n"+"VENTAS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Ventas16)+"")+"\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
		returnValue = mBxlService.PrintText("\n"+"IVA          "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Iva16)+"")+"\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);			
		returnValue = mBxlService.PrintText("\n"+"TOTAL DEPARTAMENTO"+getFillText(ALIGN_RIGHT,45,getDecTxt(zFinanciera.Total16)+"")+"\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		
		//IVA DEL 5%
				returnValue = mBxlService.PrintText("\n"+"DEPARTAMENTO "+getFillText(ALIGN_RIGHT,50,"IVA DEL 5%")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"VENTAS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Ventas5)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"IVA          "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Iva5)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);			
				returnValue = mBxlService.PrintText("\n"+"TOTAL DEPARTAMENTO"+getFillText(ALIGN_RIGHT,45,getDecTxt(zFinanciera.Total5)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				//EXENTO
				returnValue = mBxlService.PrintText("\n"+"DEPARTAMENTO "+getFillText(ALIGN_RIGHT,50,"EXENTO")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"VENTAS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.VentasExentas)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"IVA          "+getFillText(ALIGN_RIGHT,50,"0")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);			
				returnValue = mBxlService.PrintText("\n"+"TOTAL DEPARTAMENTO"+getFillText(ALIGN_RIGHT,45,getDecTxt(zFinanciera.VentasExentas)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				//IVA DEL 8%
				returnValue = mBxlService.PrintText("\n"+"DEPARTAMENTO "+getFillText(ALIGN_RIGHT,50,"IVA DEL 8%")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"VENTAS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Ventas8)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"IMPO CONSUMO "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Iva8)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);			
				returnValue = mBxlService.PrintText("\n"+"TOTAL DEPARTAMENTO"+getFillText(ALIGN_RIGHT,45,getDecTxt(zFinanciera.Total8)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				//TOTALES
				returnValue = mBxlService.PrintText("\n"+"# DE TRANSACCIONES EN EL DIA "+getFillText(ALIGN_RIGHT,39,zFinanciera.NTransacciones+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"VENTAS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Ventas)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);	
				returnValue = mBxlService.PrintText("\n"+"IVA          "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Iva)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);			
				returnValue = mBxlService.PrintText("\n"+"IMPO CONSUMO "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.ImpoCmo)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				returnValue = mBxlService.PrintText("\n"+"DESCUENTOS   "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Descuento)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				returnValue = mBxlService.PrintText("\n"+"TOTAL        "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.Total)+"")+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
				
				returnValue = mBxlService.PrintText("\n"+getFillText(ALIGN_CENTER,63,"MEDIOS DE PAGO"),
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		
		return true;
	}



	private boolean printFooterZFinanciera(int returnValue)
	{
		boolean res=false;		
		  for (int i = 0; i < zFinanciera.listaMendiosDePago.size(); i++) {
			  returnValue = mBxlService.PrintText("\n"+getFillText(ALIGN_CENTER,63,zFinanciera.listaMendiosDePago.get(i).Cuenta+""),
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
			  returnValue = mBxlService.PrintText("\n"+getFillText(ALIGN_LEFT,32,zFinanciera.listaMendiosDePago.get(i).MedioPago)+getFillText(ALIGN_RIGHT,32,getDecTxt(zFinanciera.listaMendiosDePago.get(i).Valor))+"\n",
						mBxlService.BXL_ALIGNMENT_LEFT,
						mBxlService.BXL_FT_FONTB,
						mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		}
		  returnValue = mBxlService.PrintText("\n"+"TOTAL MEDIOS       "+getFillText(ALIGN_RIGHT,50,getDecTxt(zFinanciera.getTotalMedios())+"")+"\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		return true;
	}
	private boolean printHeaderPedido(int returnValue)
	{
		boolean res=false;

		returnValue = mBxlService.PrintText(parametrosPos.getRazonSocial()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getRepresentante()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		//NUMERO DE PEDIDO Y NUMERO DE CAJA

		returnValue = mBxlService.PrintText("\n"+"Orden de Pedido: "+pedido_in.idCodigoInterno+" \n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		//FECHA Y HORA

		returnValue = mBxlService.PrintText("Fecha:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(pedido_in.getFecha()+"",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText("     Hora:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(pedido_in.getHora()+"\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);

		//Cliente

		returnValue = mBxlService.PrintText("Cliente:  ",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(pedido_in.getNombreCliente()+"\n\n",
				mBxlService.BXL_ALIGNMENT_CENTER,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		return true;
	}
	private boolean printBodyPedido(int returnValue)
	{
		boolean res=false;
		//Cliente
		returnValue = mBxlService.PrintText(" CANT        ARTICULO             VR UNIT  TOTAL\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		for (int i = 0; i < listaAPedido.size(); i++) {
			ArticulosPedido  ap=listaAPedido.get(i);
			returnValue = mBxlService.PrintText(getEspCantidad(ap.getCantidad()+"")+"  "+getFillText(36,ap.getNombre())+getFillText(10,ap.getValorUnitarioText())+" "+getFillText(10,ap.getValorText())+"\n",
					mBxlService.BXL_ALIGNMENT_LEFT,
					mBxlService.BXL_FT_FONTB,
					mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		}


		return true;
	}

	private boolean printFooterPedido(int returnValue)
	{
		boolean res=false;



		//Cliente
		returnValue = mBxlService.PrintText("\n"+"SUBTOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getSubTotal()),
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_1HEIGHT);

		//Cliente
		returnValue = mBxlService.PrintText("\n"+"DESCUENTO: "+pedido_in.getFormatoDecimal(pedido_in.getDescuentoTotal()),
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_1HEIGHT);

		//Cliente
		returnValue = mBxlService.PrintText("\n"+"TOTAL: "+pedido_in.getFormatoDecimal(pedido_in.getValor()),
				mBxlService.BXL_ALIGNMENT_RIGHT,
				mBxlService.BXL_FT_FONTB,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_1HEIGHT);


//LO ATENDIO

		returnValue = mBxlService.PrintText("\nLO ATENDIO:   ",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_BOLD,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		returnValue = mBxlService.PrintText(parametrosPos.getNombreVendedor()+"\n",
				mBxlService.BXL_ALIGNMENT_LEFT,
				mBxlService.BXL_FT_DEFAULT,
				mBxlService.BXL_TS_0WIDTH| mBxlService.BXL_TS_0HEIGHT);
		return true;
	}

	
	
	private String getEspCantidad(String cantidad)
	{
		String res="";
		int length=5;
		int esp=length-cantidad.length();
		for (int i = 0; i < esp; i++) {
			res+=" ";
		}
		res+=cantidad;
		return res;
	}
	private String getEspValor(String valor)
	{
		String res="";
		int length=34;
		int esp=length-valor.length();
		for (int i = 0; i < esp; i++) {
			res+=" ";
		}
		res+=valor;
		return res;
	}
	private String getFillText(int length,String text)
	{
		String res="";
	   if(text.length()>=length)
		{
			res=text.substring(0, length);
		}
		else
		{
			int esp=length-text.length();
			for (int i = 0; i < esp; i++) {
				res+=" ";							
			}
			if(length>13)
			{
				res=text+res;
			}
			else
			{
				res+=text;
			}
		}
		
		return res;
	}
	

	private boolean conectar()
	{
		CheckGC("Connect_Start");
		mBxlService = new BxlService();	
		if (mBxlService.Connect() == 0) {		
			conn = true;
		} else {
			mensaje="Verifique que la impresora este encendida y el bluetooth del telefono este activo";
//				mostrarMensaje(, "l");
			conn = false;
		}		
		CheckGC("Connect_End");
		return conn;
	}
	private void desconectar()
	{
		CheckGC("Disconnect_Start");		
		mBxlService.Disconnect();
		mBxlService = null;		
		conn = false;
		CheckGC("Disconnect_End");
	}
	
	void CheckGC(String FunctionName) {
		long VmfreeMemory = Runtime.getRuntime().freeMemory();
		long VmmaxMemory = Runtime.getRuntime().maxMemory();
		long VmtotalMemory = Runtime.getRuntime().totalMemory();
		long waittime = 53;
		long Memorypercentage = ((VmtotalMemory - VmfreeMemory) * 100)
				/ VmtotalMemory;

		Log.i(TAG, FunctionName + "Before Memorypercentage" + Memorypercentage
				+ "% VmtotalMemory[" + VmtotalMemory + "] " + "VmfreeMemory["
				+ VmfreeMemory + "] " + "VmmaxMemory[" + VmmaxMemory + "] ");

		// Runtime.getRuntime().gc();
		System.runFinalization();
		System.gc();
		VmfreeMemory = Runtime.getRuntime().freeMemory();
		VmmaxMemory = Runtime.getRuntime().maxMemory();
		VmtotalMemory = Runtime.getRuntime().totalMemory();
		Memorypercentage = ((VmtotalMemory - VmfreeMemory) * 100)
				/ VmtotalMemory;

		Log.i(TAG, FunctionName + "_After Memorypercentage" + Memorypercentage
				+ "% VmtotalMemory[" + VmtotalMemory + "] " + "VmfreeMemory["
				+ VmfreeMemory + "] " + "VmmaxMemory[" + VmmaxMemory + "] ");
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
