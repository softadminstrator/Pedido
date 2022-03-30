package com.principal.print;

import com.bixolon.printer.BixolonPrinter;
import com.epson.eposprint.Builder;
import com.principal.mundo.ArticulosPedido;
import com.principal.mundo.Parametros;
import com.principal.mundo.Pedido_in;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.UiExecute;
import net.posprinter.utils.DataForSendToPrinterPos80;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrintDigitaPos {

    public static IMyBinder binder;
    private static final int ALIGN_LEFT = 1;
    private static final int ALIGN_CENTER = 2;
    private static final int ALIGN_RIGHT = 3;


    public void printPedido(IMyBinder binder, Pedido_in pedido_in, ArrayList<ArticulosPedido> listaAPedido, Parametros parametrosPos)
    {
        this.binder=binder;
        asignaValor("Hola Mundo ");
    }


    private void asignaValor( final String  text)
    {
        binder.writeDataByYouself(

        new UiExecute() {
                    public void onsucess() {

                    }
                    public void onfailed() {

                    }
                }, new ProcessData() {

                    public List<byte[]> processDataBeforeSend() {

                        List<byte[]> list=new ArrayList<byte[]>();
                        //creat a text ,and make it to byte[],
                        String str=text;
                        if (str.equals(null)||str.equals("")){
                        }else {
                            //initialize the printer
//                            list.add( DataForSendToPrinterPos58.initializePrinter());
                            list.add(DataForSendToPrinterPos80.initializePrinter());
                            byte[] data1= str.getBytes();
                            list.add(data1);
                            //should add the command of print and feed line,because print only when one line is complete, not one line, no print
                            list.add(DataForSendToPrinterPos80.printAndFeedLine());
                            //cut pager
                            list.add(DataForSendToPrinterPos80.selectCutPagerModerAndCutPager(66,1));
                            return list;
                        }
                        return null;
                    }
                });
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
