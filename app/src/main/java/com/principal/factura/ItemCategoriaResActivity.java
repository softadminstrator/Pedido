package com.principal.factura;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ItemCategoriaResActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_categoria_res);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_categoria_res, menu);
		return true;
	}

}
