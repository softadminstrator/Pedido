package com.principal.factura;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/**
 * Clase que mostrara los datos del pedido en la lista
 * @author Javier
 *
 */
public class ItemPedidoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pedido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_pedido, menu);
        return true;
    }
}
