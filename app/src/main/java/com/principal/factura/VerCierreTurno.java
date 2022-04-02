package com.principal.factura;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bixolon.printer.BixolonPrinter;
import com.principal.mundo.Articulo;
import com.principal.mundo.CierreTurno;
import com.principal.mundo.Factura_in;
import com.principal.mundo.Parametros;
import com.principal.persistencia.creaBD;
import com.principal.print.PrintBixolon;
import com.principal.print.PrintDigitaPos;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.DataForSendToPrinterPos80;

import java.util.ArrayList;

public class VerCierreTurno extends Activity implements View.OnClickListener {

    private creaBD bd;
    private Parametros parametros;
    private String NCierre;
    private String NCaja;
    private CierreTurno cierreTurno;
    ArrayList<Factura_in> listaFacturas=new ArrayList<Factura_in>();
    ArrayList<Articulo> listaArticulos=new ArrayList<Articulo>();

    static BixolonPrinter mBixolonPrinter;
    private String operacionBixolon;


    private Button btImprimirCierreTurno , btVolverCierreTurno;
    private TextView tvTextNCierre, tvTextoNCaja, tvTextFecha, tvTextHoraCierre,tvTextFacturaInicial,tvTextFacturaFinal,
            tvTextNTransacciones,tvTextValorCierre;
    private ListView lvFacturasCierre;
    private boolean pirntArticulos;

    public static IMyBinder binder;
    public static boolean ISCONNECT;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cierre_turno);
        bd=new creaBD(this);
        parametros=bd.getParametros(this, "W");


        //variables para impresora digital pos
        //bind service?get ImyBinder object
        Intent intent=new Intent(this, PosprinterService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

        //inicializa vistas de actividad
        btImprimirCierreTurno=(Button)findViewById(R.id.btImprimirCierreTurno);
        btVolverCierreTurno=(Button)findViewById(R.id.btVolverCierreTurno);
        btImprimirCierreTurno.setOnClickListener(this);
        btVolverCierreTurno.setOnClickListener(this);

        tvTextNCierre=(TextView)findViewById(R.id.tvTextNCierre);
        tvTextoNCaja=(TextView)findViewById(R.id.tvTextoNCaja);
        tvTextFecha=(TextView)findViewById(R.id.tvTextFecha);
        tvTextHoraCierre=(TextView)findViewById(R.id.tvTextHoraCierre);
        tvTextFacturaInicial=(TextView)findViewById(R.id.tvTextFacturaInicial);
        tvTextFacturaFinal=(TextView)findViewById(R.id.tvTextFacturaFinal);
        tvTextNTransacciones=(TextView)findViewById(R.id.tvTextNTransacciones);
        tvTextValorCierre=(TextView)findViewById(R.id.tvTextValorCierre);

        lvFacturasCierre=(ListView)findViewById(R.id.lvFacturasCierre);


        //Obtiene paramentros del cierre a mostrar

        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();
        NCierre=obtenerDatos.getString("NCierre");
        NCaja=obtenerDatos.getString("NCaja");

        //Obtiene cierre turno a consultar
        cierreTurno=bd.obtenerCierreTurno(NCaja,NCierre);
        cargarDatosCierre();
    }

    public void onClick(View v) {
        if(v.equals(btVolverCierreTurno))
        {
            onBackPressed();
        }
        if(v.equals(btImprimirCierreTurno))
        {
            btImprimirCierreTurno.setEnabled(false);
            //Pregunta si desea imprimir las facturas o el detallado por articulo
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            String mensaje="Imprimir detalle :";

            dialog.setMessage(mensaje);
            dialog.setCancelable(false);
            dialog.setPositiveButton("Facturas", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //----------------------------------------------------------------------
                    pirntArticulos=false;
                     if(parametros.getUsaImpresoraZebra()==0 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==1& parametros.getUsaPrintDigitalPos()==0) {
                         printBixolonsppr310();
                     }
                    else if(parametros.getUsaImpresoraZebra()==0 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==0& parametros.getUsaPrintDigitalPos()==1) {
                        printDigitalPos810();
                    }
                    dialog.cancel();
                }
            });
            dialog.setNegativeButton("Articulos", new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int which) {
                    pirntArticulos=true;
                    if(parametros.getUsaImpresoraZebra()==0 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==1& parametros.getUsaPrintDigitalPos()==0) {
                        printBixolonsppr310();
                    }
                    else if(parametros.getUsaImpresoraZebra()==0 & parametros.getUsaPrintEpson()==0& parametros.getUsaPrintBixolon()==0& parametros.getUsaPrintDigitalPos()==1) {
                        printDigitalPos810();
                    }
                    dialog.cancel();
                }
            });
            dialog.show();



        }
    }

    private void cargarDatosCierre()
    {
        tvTextNCierre.setText(cierreTurno.getNCierre());
        tvTextoNCaja.setText(cierreTurno.getNCaja());
        tvTextFecha.setText(cierreTurno.getFecha());
        tvTextHoraCierre.setText(cierreTurno.getHora());
        tvTextFacturaInicial.setText(cierreTurno.getNFacturaInicial());
        tvTextFacturaFinal.setText(cierreTurno.getNFacturaFinal());
        tvTextNTransacciones.setText(cierreTurno.getTransacciones());
        tvTextValorCierre.setText(cierreTurno.getValorDecimal());

        listaArticulos=bd.getVentasCierreFacturasporArticulo(cierreTurno);
        listaFacturas=bd.getFacturasCierre(cierreTurno);
        lvFacturasCierre.setAdapter(new ListaDocumentosAdapterActivity(this,R.layout.activity_item_pedido,null,listaFacturas,null,null,null,null,null,null));


    }

    private void printBixolonsppr310()
    {
        try {
            mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
            mBixolonPrinter.connect(parametros.getMacAddBixolon());

        }
        catch (Exception e)
        {
            mostrarMensaje("e44"+e.toString(),"l");
            btImprimirCierreTurno.setEnabled(true);
        }

        //mBixolonPrinter.findBluetoothPrinters();
    }
    private final Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonPrinter.MESSAGE_STATE_CHANGE:
                    if (msg.arg1 == BixolonPrinter.STATE_CONNECTED) {
                        PrintBixolon printBixolon=new PrintBixolon();
                        printBixolon.printCierreTurno(mBixolonPrinter,listaFacturas,cierreTurno,pirntArticulos,listaArticulos);
                        btImprimirCierreTurno.setEnabled(true);
                    }
                    break;
                case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
                    mBixolonPrinter.disconnect();
                    break;
            }
            btImprimirCierreTurno.setEnabled(true);
            return true;
        }
    });

    private void printDigitalPos810(){
        String bleAdrress=parametros.getMacAddBixolon();


                binder.connectBtPort(bleAdrress, new UiExecute() {
                    public void onsucess() {
                        ISCONNECT = true;
                        PrintDigitaPos printDigitaPos = new PrintDigitaPos();
                        printDigitaPos.printCierreTurno(binder,listaFacturas,cierreTurno,pirntArticulos,listaArticulos);
                        btImprimirCierreTurno.setEnabled(true);



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

}


