package com.principal.factura;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
/**
 * En esta clase se encuentran las etiquetas que describen cada articulo en la lista del pedido
 * @author Javier
 *
 */
public class ItemArticuloPedidoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_articulo_pedido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_articulo_pedido, menu);
        return true;
    }
}
