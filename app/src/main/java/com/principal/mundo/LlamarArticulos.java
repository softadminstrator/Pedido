package com.principal.mundo;

import java.util.ArrayList;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Clase que se encarga de actualizar los articulos de la aplicacion, atraves de la llamada al 
 * servicio web del sistema PosStar
 * @author Javier
 *
 */
public class LlamarArticulos {
	 /**
     * nombre del dominio al que sera enviada la anulacion del pedido
     */
	    private static final String NAMESPACE = "http://www.elchispazo.com.co/";
	    /**
	     * url del servicio al que sera enviada la anulacion del pedido
	     */
	    private String URL="";	
	    /**
		 * nombre del metodo del servicio que sera llamado
		 */
		private static final String METHOD_NAME = "GetArtxCategoriaFecha";
		private static final String METHOD_NAME2 = "GetArticulos";
		
		
		private Parametros parametros;
		
		private String resultado;
		
		
	

	public LlamarArticulos(Parametros parametros)
	{	
		this.parametros=parametros;
		resultado="";
		URL="http://"+parametros.getIp()+"/servicioarticulos/srvarticulos.asmx";
		
	}
	
	/**
	 * metodo que se encarga de traer los articulos actualizados del servicio web del sistema PosStar 
	 * y almacenarlos en la base de datos del telefono
	 * @param categoria
	 * @param listaArticulos
	 * @return
	 */
	public ArrayList<Articulo> getArticulos(boolean isAll, String categoria,String fecha, ArrayList<Articulo> listaArticulos)
	{
		
		ArrayList<Articulo> l = listaArticulos;
		try
		 {
		SoapObject request=null;
		 if(!isAll)
		 {	 
			 request = new SoapObject(NAMESPACE, METHOD_NAME2);
			 request.addProperty("FechaA",fecha);			
		 }
		 else
		 {
			 request = new SoapObject(NAMESPACE, METHOD_NAME);	
			 request.addProperty("Categoria",categoria);
			 request.addProperty("FechaAct",fecha);
		 }
		 
//		 request.addProperty("Categoria","ABARROTES");
//		 request.addProperty("FechaAct","201309160600");	
		 
   	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.dotNet = true; //indicamos que utilizaremos servicios .NET
		 envelope.setOutputSoapObject(request); //añadimos a la conexión el objeto SoapObject anteriormente creado
		 
		 
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		 androidHttpTransport.debug = true; //nos aseguramos así de que funcione siempre		
		
	
		 	androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
		 	
	    	//SoapPrimitive res =(SoapPrimitive)envelope.getResponse();
		 	SoapObject res = (SoapObject) envelope.getResponse();
		 	resultado="ok";		 	
		 	if (res != null) 
		 	{//comprobamos que la respuesta no esté vacía
                //recorremos el objeto
		 		
		 		String [] temres= new String [res.getPropertyCount()];
		 		int ta = temres.length -1;
					for (int i = 0;i < ta ; i++) {

					Articulo articulo=new Articulo();				               
					SoapObject ic = (SoapObject)res.getProperty(i);
					
						if(ic!=null)
						{
								
											articulo.idArticulo=Long.parseLong(ic.getProperty("IdArticulo").toString());
											articulo.idCodigo=ic.getProperty("IdCategoria").toString();
											articulo.nombre=ic.getProperty("Nombre").toString();
											articulo.unidad=ic.getProperty("Unidad").toString();
											articulo.iva=Long.parseLong(ic.getProperty("Iva").toString());
											articulo.impoConsumo=Long.parseLong(ic.getProperty("ImpoConsumo").toString());
											articulo.precio1=Long.parseLong(ic.getProperty("Precio1").toString());
											articulo.precio2=Long.parseLong(ic.getProperty("Precio2").toString());
											articulo.precio3=Long.parseLong(ic.getProperty("Precio3").toString());
											articulo.precio4=Long.parseLong(ic.getProperty("Precio4").toString());
											articulo.precio5=Long.parseLong(ic.getProperty("Precio5").toString());
											articulo.precio6=Long.parseLong(ic.getProperty("Precio6").toString());	
											articulo.stock=Long.parseLong(ic.getProperty("Stock").toString());
											articulo.categoria=categoria;
											articulo.borrado=ic.getProperty("Borrado").toString();
											if(articulo.borrado.equals("NO"))
											{
												articulo.activo = 1;
											}
											else
											{
												articulo.activo = 0;
											}
											
											try
											{
													PropertyInfo pr=new PropertyInfo();
													ic.getPropertyInfo(30, pr);
													
															if(pr.getName().equals("CodBarra"))
															{
																SoapObject icod = (SoapObject)ic.getProperty(30);
																	ArrayList listacod=new ArrayList();
																	String [] icodres= new String [icod.getPropertyCount()];
															 		int cons = icodres.length-1 ;
																		for (int j = 0;j < cons ; j++) {
																			String ncodigo = (icod.getProperty(j).toString()).replace(" ", "");						
																			listacod.add(ncodigo);
																		}
																	articulo.codigos=listacod;
															}
															else
															{
																articulo.codigos=null;
															}
											}
											catch(Exception e3)
											{
												articulo.unidad="Und";
											}
											
										    l.add(articulo);
								}				
					}					
		 	}
		 	else
		 	{
		 		return l;
		 	}
	     }
	     catch (Exception e)
	     {
	    	 resultado=e.toString();
	     }	
		return l;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	

}
