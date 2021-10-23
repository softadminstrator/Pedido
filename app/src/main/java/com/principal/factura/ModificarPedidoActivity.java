package com.principal.factura;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ModificarPedidoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pedido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_modificar_pedido, menu);
        return true;
    }
}
