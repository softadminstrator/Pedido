package com.principal.factura;

import java.io.InputStream;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.principal.mundo.Articulo;
import com.principal.mundo.LlamarImgUrl;
import com.principal.mundo.Parametros;
import com.principal.persistencia.creaBD;

public class ViewProductActivity extends Activity implements OnClickListener{
	private ImageView imArticulo;	
	private Articulo articulo;
	Parametros parametrosPos;
	private int  precioSelect;
	private TextView tvViewArticulo,tvViewPrecio,tvViewCodigo;
	private ProgressDialog pd;
	private Button btViewVolver;
	LetraEstilo letraEstilo;
	creaBD bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product);
		
		 bd=new creaBD(this);
		 parametrosPos=bd.getParametros(this, "P");
		 Bundle b=this.getIntent().getExtras(); 
		 letraEstilo=new LetraEstilo();
		 
		 tvViewArticulo=(TextView)findViewById(R.id.tvViewArticulo);
		 tvViewPrecio=(TextView)findViewById(R.id.tvViewPrecio);
		 tvViewCodigo=(TextView)findViewById(R.id.tvViewCodigo);
		 btViewVolver=(Button)findViewById(R.id.btViewVolver);
		 btViewVolver.setOnClickListener(this);
	
		 articulo=new Articulo();		 
		 articulo.idArticulo = b.getLong("idArticulo");
         articulo.idCodigo = b.getString("idCodigo");
         articulo.nombre =  b.getString("nombre");
         articulo.precio1 = b.getLong("precio1");
         articulo.precio2 = b.getLong("precio2");
         articulo.precio3 = b.getLong("precio3");
         articulo.precio4 = b.getLong("precio4");
         articulo.precio5 = b.getLong("precio5");
         articulo.precio6 = b.getLong("precio6");       
         precioSelect=b.getInt("precioSelect");
		
		imArticulo=(ImageView)findViewById(R.id.imArticulo);
	
//		imArticulo.setVisibility(View.INVISIBLE);
		tvViewArticulo.setText(articulo.getNombre());
		tvViewCodigo.setText(articulo.getIdCodigo());		
		DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
		tvViewPrecio.setText("$ "+decimalFormat.format(articulo.getPrecio(precioSelect)));
		
		
//		Resources resources = getResources();
//		imArticulo.setImageDrawable(resources.getDrawable(R.drawable.notfound));
//		imArticulo.setImageResource(R.drawable.notfound);	
//		tvViewCodigo.setText(articulo.getCodigos().get(0));
		new getImgUrl().execute("");
		pd=ProgressDialog.show(this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Cargando Imagen..."), true,false);
		
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	             e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	private class getImgUrl extends AsyncTask<String, Void, Object>
	{
		String  res ="";		
		@Override
			protected Integer doInBackground(String... params) 
			{	
						LlamarImgUrl llamarImgUrl=new LlamarImgUrl(parametrosPos.getIp());					
						res=llamarImgUrl.getImgUrl(articulo.getIdArticulo());
						return 1;	
			}
		
		
			protected void onPostExecute(Object result)
			{
				try
				{
					pd.dismiss();			
					if(res.equals("false"))
					{
					imArticulo.setImageResource(R.drawable.notfound);
					}
					else if (res.equals("desc"))
					{
					imArticulo.setImageResource(R.drawable.errorconexion);	
					}
					else
					{
						imArticulo.setVisibility(View.VISIBLE);						
						new DownloadImageTask(imArticulo)
			            .execute(res);
					}
					
				}
				catch(Exception e)
				{
					
				}
			}
	}

	public void onClick(View v) {
		if(v.equals(btViewVolver))
		{
			finish();
		}
	}
}
