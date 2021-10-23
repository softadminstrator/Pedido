package com.principal.factura;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/**
 * Clase que mostrara la informacion del articulo en la lista
 * @author Javier
 *
 */
public class ItemVerProductosActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_ver_productos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_ver_productos, menu);
        return true;
    }
}
