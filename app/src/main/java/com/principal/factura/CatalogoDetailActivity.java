package com.principal.factura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.principal.mundo.Articulo;
import com.principal.mundo.GetCatalogoArticulos;
import com.principal.mundo.Parametros;
import com.principal.persistencia.creaBD;

public class CatalogoDetailActivity extends Activity implements OnClickListener{

	
    private ViewFlipper viewFlipper; 
    private float lastX;
    private ImageView ivBack,ivNext;
    creaBD bd;
    private int pageView = 0;
    private int pageViewMenu = 0;
	Parametros parametrosPos;
	private ProgressDialog pdu;
	ArrayList<Articulo> listaArticulos;
	LetraEstilo letraEstilo;
	private String CatalogoNombre;
    // progress dialog
    private ProgressDialog progressDialog;
    private LinearLayout llDettalleCatalog;
    private HorizontalScrollView scrollView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalogo_detail);
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		llDettalleCatalog =(LinearLayout) findViewById(R.id.llDettalleCatalog);
		scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
		
		scrollView.setOnTouchListener(
				new OnTouchListener(){

		   
		    public boolean onTouch(View view, MotionEvent event) {
		        // TODO Auto-generated method stub

		    	  switch (event.getAction())
	                 {
	                        // when user first touches the screen to swap
	                         case MotionEvent.ACTION_DOWN: 
	                         {
	                             lastX = event.getX();
	                             break;
	                        }
	                         case MotionEvent.ACTION_UP: 
	                         {
	                             float currentX = event.getX();                             
	                             // if right to left swipe on screen
	                             if (lastX > currentX)
	                             {                            		
	                        	        cargarArticulosMenu();		                     			                      
	                             }
	                             break;
	                         }
	                 }
	                 return false;
		        }

		    });
		
		ivBack=(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ivNext=(ImageView)findViewById(R.id.ivNext);
		ivNext.setOnClickListener(this);
		bd=new creaBD(this);      
		letraEstilo=new LetraEstilo();
	    parametrosPos=bd.getParametros(this,"P");
	    Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        CatalogoNombre=obtenerDatos.getString("CatalogoNombre");
	    new getCatalogoArticulos().execute("");
		pdu=ProgressDialog.show(CatalogoDetailActivity.this,letraEstilo.getEstiloTitulo("Por Favor Espere"), letraEstilo.getEstiloTitulo("Cargando Articulos de Catalogo"), true,false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catalogo_detail, menu);
		return true;
	}
	// Web view client
	private class BasicWebViewCient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }

	    @Override
	    public void onLoadResource(WebView view, String url) {
	        if (progressDialog == null) {
	            progressDialog = new ProgressDialog(CatalogoDetailActivity.this);
	            progressDialog.setMessage("Locating");
	            progressDialog.show();
	        }
	    }

	    @Override
	    public void onPageFinished(WebView view, String url) {
	        if (progressDialog.isShowing()) {
	            progressDialog.dismiss();
	        }
	    }
	}
	private WebView _webviweSettings(WebView view) {
	    view.getSettings().setJavaScriptEnabled(true);
	    view.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    return view;
	}
	
    public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen to swap
                         case MotionEvent.ACTION_DOWN: 
                         {
                             lastX = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {
                             float currentX = touchevent.getX();                             
                             // if left to right swipe on screen
                             if (lastX < currentX) 
                             {
                            	viewFlipper.setInAnimation(this, R.anim.in_from_left);
                     	        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                     	        viewFlipper.showPrevious();  
                             }                             
                             // if right to left swipe on screen
                             if (lastX > currentX)
                             {                            		
                        	        cargarArticulos();
	                     			viewFlipper.setInAnimation(this, R.anim.in_from_right);
	                     	        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
	                     	        viewFlipper.showNext();	                             
                             }
                             break;
                         }
                 }
                 return false;
    }

	public void onClick(View view) {
		if(view.equals(ivBack))
		{
			viewFlipper.setInAnimation(this, R.anim.in_from_left);
	        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
	        viewFlipper.showPrevious();
		}
		else if(view.equals(ivNext))
		{			
			cargarArticulos();
			viewFlipper.setInAnimation(this, R.anim.in_from_right);
	        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
	        viewFlipper.showNext();		
		}
		
	}
	
	public void cargarArticulos()
	{
		if(pageView>=0 & pageView<listaArticulos.size())	
		{
			if(!listaArticulos.get(pageView).isview)
			{
				agregarArticulo(listaArticulos.get(pageView));
				pageView++;
			}
		}
	}
	
	public void cargarArticulosMenu()
	{
		for (int i = 0; i < 4; i++) {
			if(pageViewMenu>=0 & pageViewMenu<listaArticulos.size())	
			{ //hh
				if(!listaArticulos.get(pageViewMenu).isviewMenu)
				{
					agregarArticuloMenu(listaArticulos.get(pageViewMenu));
					pageViewMenu++;
				}
			}
		}
		
	}
	
	
	private void agregarArticulo(Articulo articulo)
	{		
		  LinearLayout.LayoutParams parmsFill = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		  LinearLayout.LayoutParams parmsMatch = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		  parmsMatch.setMargins(10, 10, 10, 20);
		  LinearLayout llVertical=new LinearLayout(CatalogoDetailActivity.this);
		  llVertical.setOrientation(LinearLayout.VERTICAL);
		  llVertical.setLayoutParams(parmsFill);
		  
		  LinearLayout llVerticalPage=new LinearLayout(CatalogoDetailActivity.this);
		  llVerticalPage.setOrientation(LinearLayout.VERTICAL);
		  llVerticalPage.setLayoutParams(parmsFill);
		  
		  LinearLayout llVerticalPageView=new LinearLayout(CatalogoDetailActivity.this);
		  llVerticalPageView.setOrientation(LinearLayout.VERTICAL);
		  llVerticalPageView.setLayoutParams(parmsFill);
		  llVerticalPageView.setGravity(Gravity.CENTER);
		  
		  TextView tvNombre=new TextView(CatalogoDetailActivity.this);
		  tvNombre.setLayoutParams(parmsMatch);
		  tvNombre.setTextSize(22);		  
		  tvNombre.setGravity(Gravity.CENTER);		  
//		  ImageView ivArticulo=new ImageView(CatalogoDetailActivity.this);
//		  ivArticulo.setLayoutParams(parmsMatch);		  
		  llVertical.addView(tvNombre);
//		  llVertical.addView(ivArticulo);
		  tvNombre.setText(articulo.getNombre());
		  articulo.setIsview(true);		
//		  UrlImageViewHelper.setUrlDrawable(ivArticulo, articulo.getUrlImagen(), null, 999999999);
		  WebView wb=new WebView(CatalogoDetailActivity.this);
		  wb = _webviweSettings(wb);
		  wb.loadUrl(articulo.getUrlImagen());
		  wb.setWebViewClient(new BasicWebViewCient());
		  wb.setOnTouchListener(new View.OnTouchListener() {
			     public boolean onTouch(View v, MotionEvent event) {
			    	CatalogoDetailActivity.this.onTouchEvent(event);
			        return true;
			    }
			});		
		  WebSettings settings = wb.getSettings();
		  settings.setJavaScriptEnabled(true);
		  settings.setLoadWithOverviewMode(true);
		  settings.setUseWideViewPort(true);
		 		
		  
//		  wb.setLayoutParams(parmsMatch);		  
		  llVerticalPageView.addView(wb);
		  llVerticalPage.addView(llVerticalPageView);
		  llVertical.addView(llVerticalPage);		  
		  viewFlipper.addView(llVertical,pageView);
	}
	
	private void agregarArticuloMenu(Articulo articulo)
	{				
			  LinearLayout.LayoutParams parmsFill = new LinearLayout.LayoutParams(130,180);
			  LinearLayout.LayoutParams parmsMatch = new LinearLayout.LayoutParams(130,160);
			  LinearLayout.LayoutParams parmsMatchWeb = new LinearLayout.LayoutParams(100,140);
			  LinearLayout.LayoutParams parmsWebPageView = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
			  parmsMatch.setMargins(2, 2, 2, 5);
			  parmsFill.setMargins(2, 2, 2, 5);
			
			  LinearLayout llVertical=new LinearLayout(CatalogoDetailActivity.this);
			  llVertical.setOrientation(LinearLayout.VERTICAL);
			  llVertical.setLayoutParams(parmsFill);			  
			  LinearLayout llVerticalPage=new LinearLayout(CatalogoDetailActivity.this);
			  llVerticalPage.setOrientation(LinearLayout.VERTICAL);
			  llVerticalPage.setLayoutParams(parmsMatch);			  
			  LinearLayout llVerticalPageView=new LinearLayout(CatalogoDetailActivity.this);
			  llVerticalPageView.setOrientation(LinearLayout.VERTICAL);
			  llVerticalPageView.setLayoutParams(parmsWebPageView);
			  llVerticalPageView.setGravity(Gravity.CENTER);			  
			  TextView tvNombre=new TextView(CatalogoDetailActivity.this);
//			  tvNombre.setLayoutParams(parmsMatch);
			  tvNombre.setTextSize(10);		  
			  tvNombre.setGravity(Gravity.CENTER);
			
			 
			  tvNombre.setText(articulo.getNombre());
			  articulo.setIsviewMenu(true);		
			  WebView wb=new WebView(CatalogoDetailActivity.this);
			  wb = _webviweSettings(wb);
			  wb.loadUrl(articulo.getUrlImagen());
			  wb.setWebViewClient(new BasicWebViewCient());
			  wb.setOnTouchListener(new View.OnTouchListener() {
				     public boolean onTouch(View v, MotionEvent event) {
				    	CatalogoDetailActivity.this.onTouchEvent(event);
				        return true;
				    }
				});		  
			  wb.setLayoutParams(parmsMatchWeb);
			  
			  WebSettings settings = wb.getSettings();
			  settings.setUseWideViewPort(true);
			  settings.setLoadWithOverviewMode(true);
			  
			  llVerticalPageView.addView(wb);
			  llVerticalPage.addView(llVerticalPageView);
			  llVertical.addView(tvNombre);
			  llVertical.addView(llVerticalPage);
			 
			  llDettalleCatalog.addView(llVertical);
		
		 
	}
	
	
	
	
	 private class getCatalogoArticulos extends AsyncTask<String, Void, Object>
	   	{
	   		String  res ="";		
	   		@Override
	   			protected Integer doInBackground(String... params) 
	   			{		
	   					GetCatalogoArticulos getCatalogosArticulos=new GetCatalogoArticulos(parametrosPos.getIp());
	   					getCatalogosArticulos.getCatalogoArticulos(CatalogoNombre);
	   					listaArticulos=getCatalogosArticulos.getListaArticulos();
	   					if(listaArticulos.size()>0)
	   					{
	   						res="true";
	   					}					
	   					return 1;	
	   			}
	   		
	   		
	   			protected void onPostExecute(Object result)
	   			{
	   				pdu.dismiss();
	   				if(res.equals("true"))
	   				{		
	   					List<Articulo> listArt=listaArticulos;		
	   					Collections.sort(listArt,Articulo.ArticuloNameComparator);
    					cargarArticulos();	
	   					cargarArticulosMenu();	   				   					
	   				}
	   				else 
	   				{						
	   						mostrarMensaje("No Fue Posible establecer la conexion con el servidor.","l");
	   				}		
	   				
	   			}
	   				
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
