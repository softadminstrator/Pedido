package com.principal.factura;

import java.util.ArrayList;

import com.principal.mundo.Catalogo;
import com.principal.persistencia.creaBD;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CatalogoActivity extends Activity {
	
	private ArrayList<Catalogo> listaCatalogos;
	private Catalogo catalogo;
	creaBD bd;
	Context context;
	private ListView listViewCatalogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalogo);
		
		context = getApplicationContext();
		bd=new creaBD(this);  		
	    bd.openDB();
	    listaCatalogos=bd.getCatalogos();									
		bd.closeDB();
		System.out.println("ssss : " +listaCatalogos.size());
		listViewCatalogo=(ListView)findViewById(R.id.listViewCatalogo);
		listViewCatalogo.setAdapter(new CatalogoAdapter(context,R.layout.activity_item_catalogo,listaCatalogos));
		
		listViewCatalogo.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
					catalogo= listaCatalogos.get((int)position);
					Intent intent = new Intent(CatalogoActivity.this, CatalogoDetailActivity.class );
					intent.putExtra("CatalogoNombre", catalogo.getNombre());
 	      			startActivity(intent); 
 	     }
	        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catalogo, menu);
		return true;
	}

}
