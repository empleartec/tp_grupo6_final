package com.example.ferreteria;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main_Activity extends Activity
{
	String ipServidorMySQL, contrasenaMySQL, usuarioMySQL, 
		puertoMySQL;
	static String SQLEjecutar;
	String catalogoMySQL;
	TextView textSQL, textResultadoSQL;
	private Button buttonEjecutar; 
	private Button buttonCatalogos; 
	private Spinner spnCatalogos;
	private CheckBox chbSQLModificacion;
	static Connection conexionMySQL;
	String[] listaCatalogos;

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	public TextView txt_codigoLeiodo;


	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        	
        //Asignamos cada objeto visual creado en el layout xml a su 
        //respectiva variable
        textSQL = (TextView)findViewById(R.id.txtSQL);
        spnCatalogos = (Spinner)findViewById(R.id.lsCatalogos);
        textResultadoSQL = (TextView)findViewById(R.id.txtResultadoSQL);
        buttonEjecutar = (Button) findViewById(R.id.btEjecutar);
        buttonCatalogos = (Button) findViewById(R.id.btCatalogos);
        chbSQLModificacion = (CheckBox) findViewById(R.id.opConsultaModificacion);

		//txt_codigoLeiodo= (TextView)findViewById(R.id.txt_codigoLeiodo);
        
        //Botón para mostrar lista de catálogos (bases de datos) de MySQL
        buttonCatalogos.setOnClickListener(new View.OnClickListener() 
        {			
			@Override
			public void onClick(View v) 
			{	
				  obtenerListaCatalogos();
				  try
				  {
					  ArrayAdapter<String> adaptador =
						  new ArrayAdapter<String>(Main_Activity.this,
								  android.R.layout.simple_list_item_1, listaCatalogos);        			
		  			
					  adaptador.setDropDownViewResource(
		  				  android.R.layout.simple_spinner_dropdown_item);
					  spnCatalogos.setAdapter(adaptador);
				  }
	              catch (Exception e) 
	              {  
	            	  Toast.makeText(getApplicationContext(),
			                    "Error: " + e.getMessage(),
			                    Toast.LENGTH_SHORT).show();
	              }					  
			}
		});
        
        //Botón para ejecutar consulta SQL en MySQL
        buttonEjecutar.setOnClickListener(new View.OnClickListener() 
        {
          public void onClick(View v) 
          {
		   	  cargarConfiguracion();
          	  SQLEjecutar = textSQL.getText().toString();
          	  catalogoMySQL = spnCatalogos.getSelectedItem().toString();
          	  conectarBDMySQL(usuarioMySQL, contrasenaMySQL, 
          			  ipServidorMySQL, puertoMySQL, catalogoMySQL);
          	  String resultadoSQL = 
          		  ejecutarConsultaSQL(chbSQLModificacion.isChecked(),
		       		  getApplication());
          	  textResultadoSQL.setText(resultadoSQL);          	  
          }
        });   
    }

	//crear el menú en el activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_ppal, menu);
        return true;
    }   
    
    //código para cada opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case R.id.menu_configuracion:
                menuConfiguracion();
                return true;
                
            case R.id.menu_acerca_de:
            	//visitarURL("http://www.ajpdsoft.com");
				Intent i = new Intent(Main_Activity.this, Activity_Acerca_De.class);
				startActivity(i);
                return true;               
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    
    //abrir ventana-activity Configuración
    public void menuConfiguracion()
    {
		Intent i = new Intent(Main_Activity.this,
				Activity_Configuracion.class);
		startActivity(i);    	
    }

	public void Trae_articulos()
	{
		Intent i = new Intent(Main_Activity.this,
				Activity_Articulo.class);
		startActivity(i);

	}


    //Abrir navegador con URL especificada
    public void visitarURL(String url)
    {
    	Intent browserIntent = 
    		new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    	startActivity(browserIntent);
    }    
    
    //guardar configuración aplicación Android usando SharedPreferences
    public void guardarConfiguracion()
    {
      SharedPreferences prefs = 
          getSharedPreferences("Main_Activity", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = prefs.edit();
      editor.putString("SQL", textSQL.getText().toString());
      editor.putString("Catálogo", spnCatalogos.getSelectedItem().toString());
      editor.commit();
    }
    
    //cargar configuración aplicación Android usando SharedPreferences
    public void cargarConfiguracion()
    {
    	//leemos los valores de conexión al servidor
    	//MySQL desde SharedPreferences
    	SharedPreferences prefs = 
            getSharedPreferences("Main_Activity", Context.MODE_PRIVATE);

    	SQLEjecutar = prefs.getString("SQL", "");
        catalogoMySQL =  prefs.getString("Catálogo", "");
        ipServidorMySQL = prefs.getString("Conexión", "192.168.1.100");
        contrasenaMySQL = prefs.getString("Contraseña", "");        
        puertoMySQL =  Integer.toString(prefs.getInt("Puerto", 3306)); 
        usuarioMySQL = prefs.getString("Usuario", "root");        
    }    
    
    //Obtener lista de catálogos de MySQL
    public void obtenerListaCatalogos ()
    {
        try
        {
      	  cargarConfiguracion();	            	  
      	  conectarBDMySQL(usuarioMySQL, contrasenaMySQL, 
      			  ipServidorMySQL, puertoMySQL, "");
      	  
          //ejecutamos consulta SQL
      	  Statement st = conexionMySQL.createStatement();  
      	  ResultSet rs = st.executeQuery("show databases"); 
      	  rs.last();
      	  Integer numFilas = 0;
      	  numFilas = rs.getRow();	            	  
      	  
      	  listaCatalogos = new String[numFilas];
       	  Integer j = 0;	              	  
      	  //mostramos el resultado	            	  
       	  for (int i = 1; i <= numFilas; i++)
       	  {
       		  listaCatalogos [j] = rs.getObject(1).toString();
       		  j++;
       		  rs.previous();
       	  }
  		  rs.close();
        }	              
        catch (Exception e) 
        {  
      	  Toast.makeText(getApplicationContext(),
	                    "Error: " + e.getMessage(),
	                    Toast.LENGTH_SHORT).show();
        }    	
    }
    
    //conectar al servidor de MySQL Server
    public void conectarBDMySQL (String usuario, String contrasena, 
    		String ip, String puerto, String catalogo)
    {
    	if (usuario == "" || puerto == "" || ip == "")
    	{
    		AlertDialog.Builder alertDialog = 
    			new AlertDialog.Builder(Main_Activity.this);
    		alertDialog.setMessage("Antes de establecer la conexión " +
    				"con el servidor " +
    				"MySQL debe indicar los datos de conexión " +
    				"(IP, puerto, usuario y contraseña).");
    		alertDialog.setTitle("Datos conexión MySQL");
    		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
    		alertDialog.setCancelable(false);
    		alertDialog.setPositiveButton("Aceptar", 
    				new DialogInterface.OnClickListener()
    		{
    			public void onClick(DialogInterface dialog, int which)
   		    	{
    				menuConfiguracion();
   		    	}
    		});
    		alertDialog.show();
    	}
    	else
    	{
    		String urlConexionMySQL = "";
    		if (catalogo != "")
    			urlConexionMySQL = "jdbc:mysql://" + ip + ":" +	
    			    puerto + "/" + catalogo;
    		else
    			urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
    		if (usuario != "" & contrasena != "" & ip != "" & puerto != "")
    		{
    			try 
    			{
					Class.forName("com.mysql.jdbc.Driver");
	    			conexionMySQL =	DriverManager.getConnection(urlConexionMySQL, 
	    					usuario, contrasena);					
				} 
    			catch (ClassNotFoundException e) 
    			{
    		      	  Toast.makeText(getApplicationContext(),
    		                    "Error: " + e.getMessage(),
    		                    Toast.LENGTH_SHORT).show();
    			} 
    			catch (SQLException e) 
    			{
			      	  Toast.makeText(getApplicationContext(),
			                    "Error: " + e.getMessage(),
			                    Toast.LENGTH_SHORT).show();
				}
    		}
    	}
    }
    
    public static String ejecutarConsultaSQL(Boolean SQLModificacion, Context context)
    {
        try
		{
          String resultadoSQL = "";
          //ejecutamos consulta SQL de selección (devuelve datos)
      	  if (!SQLModificacion)
      	  {	            	    
      		  Statement st = conexionMySQL.createStatement();
      		  ResultSet rs = st.executeQuery(SQLEjecutar); 

			  Integer numColumnas = 0;
				  
          	  //número de columnas (campos) de la consula SQL            	  
          	  numColumnas = rs.getMetaData().getColumnCount();          	  

      		  //obtenemos el título de las columnas
      		  for (int i = 1; i <= numColumnas; i++)
      		  {
              	  if (resultadoSQL != "")
              		  if (i < numColumnas)
              			  resultadoSQL = resultadoSQL + 
              			  		rs.getMetaData().getColumnName(i).toString() + ";";
              		  else
              			  resultadoSQL = resultadoSQL + 
              			  		rs.getMetaData().getColumnName(i).toString();
              	  else
              		  if (i < numColumnas)
              			  resultadoSQL = 
              				  rs.getMetaData().getColumnName(i).toString() + ";";
              		  else
              			  resultadoSQL = 
              				  rs.getMetaData().getColumnName(i).toString();                  	  
      		  }

          	  
          	  //mostramos el resultado de la consulta SQL
          	  while (rs.next()) 
          	  {  
          		  resultadoSQL = resultadoSQL + "\n";
          		  
          		  //obtenemos los datos de cada columna
          		  for (int i = 1; i <= numColumnas; i++)
                  {                          
                        if (rs.getObject(i) != null)
                        {
                      	  if (resultadoSQL != "")
                      		  if (i < numColumnas)
                      			  resultadoSQL = resultadoSQL + 
                      			  		rs.getObject(i).toString() + ";";
                      		  else
                      			  resultadoSQL = resultadoSQL + 
                      			  		rs.getObject(i).toString();
                      	  else
                      		  if (i < numColumnas)
                      			  resultadoSQL = rs.getObject(i).toString() + ";";
                      		  else
                      			  resultadoSQL = rs.getObject(i).toString();
                        }
                        else
                        {
                      	  if (resultadoSQL != "")
                      		  resultadoSQL = resultadoSQL + "null;";
                      	  else
                      		  resultadoSQL = "null;";
                        }                           
                    }
                    resultadoSQL = resultadoSQL + "\n";
          	  }
      		  st.close();
      		  rs.close();        		  
		  }
      	  // consulta SQL de modificación de 
      	  // datos (CREATE, DROP, INSERT, UPDATE)
      	  else 
      	  {
      		  int numAfectados = 0; 
      		  Statement st = conexionMySQL.createStatement();
      		  numAfectados = st.executeUpdate(SQLEjecutar);
      		  resultadoSQL = "Registros afectados: " + String.valueOf(numAfectados);
      		  st.close();
      	  }
      	  return resultadoSQL;
		}
        
        catch (Exception e) 
        {  
      	  Toast.makeText(context,
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
      	  return "";
        }
    }
    
    //en el evento "Cerrar aplicación" guardar los datos en fichero xml
    @Override
    public void onDestroy()
    {
      super.onDestroy();
      //guardarConfiguracion();
    }
    
    //en el evento "Abrir aplicación" leemos los datos de 
    //configuración del fichero xml
    @Override
    protected void onStart() 
    {
      super.onStart();
      cargarConfiguracion();
      try
      {
    	  textSQL.setText(SQLEjecutar);
    	  
    	  //seleccionamos en el Spinner (lista desplegable) 
    	  //el último catálogo MySQL usado
    	  if (catalogoMySQL != "")
    	  {
    		  listaCatalogos = new String[1];
    		  listaCatalogos [0] = catalogoMySQL;
    		  ArrayAdapter<String> adaptador =
    			  new ArrayAdapter<String>(Main_Activity.this,
    					  android.R.layout.simple_list_item_1, listaCatalogos);        			
    		  adaptador.setDropDownViewResource(
    				  android.R.layout.simple_spinner_dropdown_item);
    		  spnCatalogos.setAdapter(adaptador);      	  
    	  }
      }
      catch (Exception e) 
      {
    	  Toast.makeText(getApplicationContext(),
    			  "Error: " + e.getMessage(),
    			  Toast.LENGTH_SHORT).show();
      }      
    }

	public void Trae_articulos(View view) {
		Intent i = new Intent(Main_Activity.this,
				Conectar.class);
		startActivity(i);

	}

	public void Trae_pedidos(View vista)
	{
		Intent i = new Intent(Main_Activity.this,Activity_Pedidos.class);
		startActivity(i);
	}
	public void leer_codigoB(View vista)
	{
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(Main_Activity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}


	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {

		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}


	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				String codigo= contents.toString();
				txt_codigoLeiodo.setText(codigo);
				Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	public void lee_camara(View view) {
		Intent i = new Intent(Main_Activity.this,
				CodbarActivity.class);
		startActivity(i);

	}

}