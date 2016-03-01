package com.example.ferreteria;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Activity_Configuracion extends Activity {
	
	TextView textIP, textPuerto, textContrasena, textUsuario;
	private Button buttonProbarConexion; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        
        //Asignamos a cada objeto visual creado a su 
        //respectivo elemento de main.xml
        textIP = (TextView)findViewById(R.id.txtIP);      
        textPuerto = (TextView)findViewById(R.id.txtPuerto);
        textContrasena = (TextView)findViewById(R.id.txtContrasena);
        textUsuario = (TextView)findViewById(R.id.txtUsuario);
        buttonProbarConexion = (Button) findViewById(R.id.btProbarConexion);
    
        //Botón para ejecutar consulta SQL en MySQL
        buttonProbarConexion.setOnClickListener(new View.OnClickListener() 
        {
          public void onClick(View v) 
          {
        	  //Conectamos con el servidor de MySQL directamente
              try
			  {
            	  String conexionMySQLURL = "jdbc:mysql://" + 
            	  		textIP.getText().toString() + ":" + textPuerto.getText().toString();
            	  String usuario = textUsuario.getText().toString();
            	  String contrasena = textContrasena.getText().toString();
            	  
            	  Toast.makeText(getApplicationContext(),
		                    "Conectando a servidor MySQL", 
		                    Toast.LENGTH_SHORT).show();
            	  	
            	  Class.forName("com.mysql.jdbc.Driver");  
            	  Connection con = DriverManager.getConnection(conexionMySQLURL, 
            			  usuario, contrasena);
            	  
            	  Toast.makeText(getApplicationContext(),
		                    "Conectado Servidor MySQL", 
		                    Toast.LENGTH_LONG).show();
            	  con.close();            	  
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
              catch (Exception e) 
              {  
            	  Toast.makeText(getApplicationContext(),
		                    "Error: " + e.getMessage(),
		                    Toast.LENGTH_LONG).show();
              }
          }
        });    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu__configuracion, menu);
        return true;
    }   

    //código para cada opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case R.id.menu_guardar_configuracion:
            	guardarConfiguracion();
                return true;
                
            case R.id.menu_acerca_de:
            	visitarURL("http://www.pimpo.com");
                return true;               
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    
    //guardar configuración aplicación Android usando SharedPreferences
    public void guardarConfiguracion()
    {
    	try
    	{
    		SharedPreferences prefs = 
    			getSharedPreferences("Main_Activity", Context.MODE_PRIVATE);
    		SharedPreferences.Editor editor = prefs.edit();
    		editor.putString("Conexión", textIP.getText().toString());
    		editor.putString("Contraseña", textContrasena.getText().toString());
    		int puerto = 3306;
  			puerto = Integer.valueOf(textPuerto.getText().toString());    			
    		editor.putInt("Puerto", puerto);
    		editor.putString("Usuario", textUsuario.getText().toString());      
    		editor.commit();
    	}
        catch (Exception e)
        {  
      	  Toast.makeText(getApplicationContext(),
                  "Error: " + e.getMessage(),
                  Toast.LENGTH_LONG).show();
        }    	
    }
    
    //cargar configuración aplicación Android usando SharedPreferences
    public void cargarConfiguracion()
    {
    	try
    	{    	
    		SharedPreferences prefs = 
        		getSharedPreferences("Main_Activity", Context.MODE_PRIVATE);
        	textIP.setText(prefs.getString("Conexión", "192.168.1.100"));
        	textContrasena.setText(prefs.getString("Contraseña", ""));
    		int puerto = 3306;
   			puerto = prefs.getInt("Puerto", 3306);
        	textPuerto.setText(Integer.toString(puerto));        
        	textUsuario.setText(prefs.getString("Usuario", "root"));    
    	}
        catch (Exception e) 
        {  
      	  Toast.makeText(getApplicationContext(),
                  "Error: " + e.getMessage(),
                  Toast.LENGTH_LONG).show();
        }    
    }

    
    //Abrir navegador con URL determinada
    public void visitarURL(String url)
    {
    	Intent browserIntent = 
    		new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    	startActivity(browserIntent);
    }   
    
    
    //en el evento "Cerrar ventana" guardar los datos en fichero xml
    @Override
    public void onDestroy()
    {
      super.onDestroy();
      guardarConfiguracion();
    }
    
    //en el evento "Abrir ventana" leemos los datos de configuración del fichero xml
    @Override
    protected void onStart() 
    {
      super.onStart();
      cargarConfiguracion();
    }      
}
