package com.principal.factura;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.principal.mundo.Cliente;
/**
 * Clase en la que se puede visualizar la imformacion completa del cliente
 * como el nombre, representante, direccion, relefono, barrio. 
 * @author Javier
 *
 */
public class DatosClienteActivity extends Activity 

{
	/**
	 * Atributo cliente hace referencia a la clase Cliente
	 */
	Cliente cliente;
	/**
	 * Atributo textViews arreglo que contendra las etiquetas de la actividad
	 */
	TextView [] textViews;
	/**
	 * Atributo letraEstilo referencia a la clase LetraEstilo
	 */
	LetraEstilo letraEstilo;
	/**
	 * Atributo btvolver referente al boton volver para regresar al rutero de clientes
	 */
	Button btvolver;
	
	/**
	 * metodo que se asigna valores a los atributos de la actividad
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);
        cliente=new Cliente();
        textViews=new TextView[10];
        letraEstilo=new LetraEstilo();
        
        Bundle obtenerDatos=new Bundle();
        obtenerDatos = this.getIntent().getExtras();     
        cliente.nombre=obtenerDatos.getString("nombre");
        cliente.ordenVisita=obtenerDatos.getLong("ordenVisita");
        cliente.idCliente=obtenerDatos.getLong("idCliente");
        cliente.representante=obtenerDatos.getString("representante");
        cliente.direccion=obtenerDatos.getString("direccion");
        cliente.telefono=obtenerDatos.getString("telefono");
        cliente.barrio=obtenerDatos.getString("barrio");
        
        
        textViews[0]=(TextView)findViewById(R.id.tvClienteInfoN);
        textViews[1]=(TextView)findViewById(R.id.tvNegocioInfoN);
        textViews[2]=(TextView)findViewById(R.id.tvDireccionInfoN);
        textViews[3]=(TextView)findViewById(R.id.tvTelefonoInfoN);
        textViews[4]=(TextView)findViewById(R.id.tvBarrioInfoN);
        
        textViews[5]=(TextView)findViewById(R.id.tvClienteInfo);
        textViews[6]=(TextView)findViewById(R.id.tvNegocioInfo);
        textViews[7]=(TextView)findViewById(R.id.tvDireccionInfo);
        textViews[8]=(TextView)findViewById(R.id.tvTelefonoInfo);
        textViews[9]=(TextView)findViewById(R.id.tvBarrioInfo);
        
        btvolver=(Button)findViewById(R.id.btVolverInfo);
        
        btvolver.setOnClickListener(new OnClickListener() {
    		
        	public void onClick(View v) {       	
    			finish(); 			
			}
			
		});
 
        cargarDatos();
        getEstilo(textViews);
        getEstilo(btvolver);       
    }
    /**
     * metodo encargado de asignar valores a las etiquetas de la actividad
     */
    public void cargarDatos()
    {
    	textViews[0].setText(cliente.nombre);
    	textViews[1].setText(cliente.getRepresentante());
    	textViews[2].setText(cliente.getDireccion());
    	textViews[3].setText(cliente.getTelefono());
    	textViews[4].setText(cliente.getBarrio());	
     
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_datos_cliente, menu);
        return true;
    }
    
    /**
     * metodo que se encarga de dar estilo en letra de etiquetas
     * @param tv
     */
    public void getEstilo(TextView [] tv)
	{
		for (int i = 0; i < tv.length; i++) {
			if(i<5)
			{
				tv[i].setText(letraEstilo.getEstiloSencillo(tv[i].getText().toString()));
			}
			else
			{
				tv[i].setText(letraEstilo.getEstiloNegrilla(tv[i].getText().toString()));
			}
		}
		
	}
    /**
     * metodo que se encarga de dar estilo de letra al texto del boton
     * @param tv
     */
    public void getEstilo(Button tv)
	{
		tv.setText(letraEstilo.getEstiloNegrilla(tv.getText().toString()));
	}
}
