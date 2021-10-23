package com.principal.mundo;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * Clase en la que se describen los articulos del pedido para enviar al sistema PossStar web
 * @author Javier
 *
 */
public class ClsArtRemision  implements KvmSerializable
{

    /**
     * identificador asignado al articulo del pedido
     */
    public long IdArticulo;
    /**
     * cantidad del articulo en el pedido
     */
    public double Cantidad;
    /**
     * valor unitario del articulo registrado en el pedido
     */
    public long ValorUnitario;

    /**
     * metodo constructor de la clase vacio
     */
    public ClsArtRemision()
    {

    }
    /**
     * metodo que se encarga de asignar valores a los atributos de la clase
     * @param IdArticulo
     * @param Cantidad
     * @param ValorUnitario
     */
    public ClsArtRemision(long IdArticulo, int Cantidad, long ValorUnitario)
    {
        this.IdArticulo=IdArticulo;
        this.Cantidad=Cantidad;
        this.ValorUnitario=ValorUnitario;
    }


    /* (non-Javadoc)
     * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
     */
    public Object getProperty(int i) {
        switch(i)
        {
            case 0: return IdArticulo;
            case 1: return Cantidad;
            case 2: return ValorUnitario;
        }
        return null;
    }
    /* (non-Javadoc)
     * @see org.ksoap2.serialization.KvmSerializable#getPropertyCount()
     */
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 3;
    }
    /* (non-Javadoc)
     * @see org.ksoap2.serialization.KvmSerializable#getPropertyInfo(int, java.util.Hashtable, org.ksoap2.serialization.PropertyInfo)
     */
    public void getPropertyInfo(int i, Hashtable arg1, PropertyInfo propertyInfo) {
        switch(i)
        {
            case 0:
                propertyInfo.name = "IdArticulo";
                propertyInfo.type = PropertyInfo.LONG_CLASS;
                break;
            case 1:
                propertyInfo.name = "Cantidad";
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                break;
            case 2:
                propertyInfo.name = "ValorUnitario";
                propertyInfo.type = PropertyInfo.LONG_CLASS;
                break;

        }
    }
    /* (non-Javadoc)
     * @see org.ksoap2.serialization.KvmSerializable#setProperty(int, java.lang.Object)
     */
    public void setProperty(int arg0, Object arg1) {

        switch(arg0)
        {
            case 0:
                IdArticulo=Long.parseLong(arg1.toString());
                break;
            case 1:
                Cantidad=Integer.parseInt(arg1.toString());
                break;
            case 2:
                ValorUnitario=Long.parseLong(arg1.toString());
                break;

        }
    }

}
