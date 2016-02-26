package com.example.ferreteria;

/**
 * Created by pimpo on 01/02/16.
 */
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Activity_Articulo extends Activity {
    static Connection conexionMySQL;
    public String usuarioMySQL="android13";
    public String contrasenaMySQL="android13";
    public String ipServidorMySQL="pimpo.mooo.com";
    public String puertoMySQL="1313";
    public String catalogoMySQL="Ferreteria";

    private ImageButton buttonBuscarSKU;
    private ImageButton buttonDescripcion;

    private TextView atrSKU, artDescL, codMuesta,desMuestra,marcaMuesta;

    private List<String> strList;
    private GridView gridView;
    private TextView art_sel;
    public Articulo art_pedido;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulos_activity);
        art_pedido=new Articulo();
        initData();
        initView();



       // conectarBDMySQL(usuarioMySQL, contrasenaMySQL, ipServidorMySQL, puertoMySQL, catalogoMySQL);

       buttonBuscarSKU = (ImageButton) findViewById(R.id.bto_busCod2);

        buttonDescripcion = (ImageButton) findViewById(R.id.bto_busDesc);
        atrSKU = (TextView) findViewById(R.id.art_SKU);
        artDescL = (TextView) findViewById(R.id.desc_art);
        art_sel = (TextView) findViewById(R.id.txt_art_select);

                //codMuesta = (TextView) findViewById(R.id.cod_muestra);
        //desMuestra = (TextView) findViewById(R.id.desc_muestra);
        //marcaMuesta = (TextView) findViewById(R.id.marca_mues);

        //Creo los lisener de los botonas
        // buttonBuscarSKU.setOnClickListener(new View.OnClickListener() {

        buttonBuscarSKU.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    atrSKU.setText("1000101005019");
                    Toast.makeText(getApplicationContext(),"Buscando por codigo" , Toast.LENGTH_SHORT).show();
                    String sqlEjscuar="SELECT * FROM Ferreteria.T001_ARTICULOS where txt_ARTICULO_SKU='"+atrSKU.getText() +"'";
                    String rst=ejecutarConsultaSQL(sqlEjscuar);
                    desMuestra.setText(rst);

                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonDescripcion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {

                    Toast.makeText(getApplicationContext(),"Buscando por descricion " , Toast.LENGTH_SHORT).show();
                    String sqlEjscuar="SELECT * FROM Ferreteria.T001_ARTICULOS where txt_ART_DESCRIPCION  LIKE '%"+artDescL.getText().toString().toUpperCase() + "%'";
                    String rst=ejecutarConsultaSQL(sqlEjscuar);
                    desMuestra.setText(rst);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //->la grilla

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                int pos = gridView.getSelectedItemPosition();
               // Toast.makeText(getBaseContext(), strList.get(position),
                 //       Toast.LENGTH_SHORT).show();
                String cadena = (String) strList.get(position);
                art_sel.setText(cadena);
                String sku = (String) strList.get(position-1);
                art_pedido.setTxt_DESCRIPCION(cadena);
                art_pedido.setTxt_ART_MARCA("AGME");
                art_pedido.setTxt_ARTICULO_SKU(sku);
            }

        });
        //- mostrar texto citado -





    }

    public void conectarBDMySQL (String usuario, String contrasena,
                                 String ip, String puerto, String catalogo)
    {


        String urlConexionMySQL = "";

            urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
            if (usuario != "" & contrasena != "" & ip != "" & puerto != "")
            {
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    conexionMySQL =	DriverManager.getConnection(urlConexionMySQL,
                            usuario, contrasena);
                    Toast.makeText(getApplicationContext(),
                            "Conectado Servidor MySQL",
                            Toast.LENGTH_LONG).show();
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


    public  String ejecutarConsultaSQL(String SQLEjecutar) {
        String resultadoSQL = "";
        initData();
        try {

            conectarBDMySQL(usuarioMySQL, contrasenaMySQL, ipServidorMySQL, puertoMySQL, catalogoMySQL);

            //ejecutamos consulta SQL de selección (devuelve datos)
            Statement st = conexionMySQL.createStatement();
            ResultSet rs = st.executeQuery(SQLEjecutar);
            strList.add(" Codigo  ");
            strList.add(" Descripcion  ");
            strList.add(" Marca  ");
            while (rs.next()) {
                resultadoSQL = rs.getString("txt_ARTICULO_SKU");
                resultadoSQL = resultadoSQL +rs.getString("txt_ART_DESCRIPCION");
                strList.add(rs.getString("txt_ARTICULO_SKU").trim());
                String detalle=rs.getString("txt_ART_DESCRIPCION").trim();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) // Configuration.ORIENTATION_LANDSCAPE
                {
                    detalle=detalle.substring(0,25);
                }

                strList.add(detalle);
                strList.add("AGME");

            }
            initView();
            conexionMySQL.close();
        } catch (SQLException e) {
            return "Err->" + e.toString();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return "";

        }
        return resultadoSQL;

    }

    public void initData(){
        strList = new ArrayList<String>();
        for(int i = 0; i < 3; i++){
            strList.add(" " );//strList.add("codigo " + i);
        }
    }

    private void initView() {
        gridView = (GridView)findViewById(R.id.gridViewArt);
        GridViewAdapter adapter = new GridViewAdapter(this, strList);

        int mCellWidth = gridView.getWidth()/3;

        gridView.setColumnWidth(mCellWidth);


        //gridView.setColumnWidth(200);
        gridView.setAdapter(adapter);
    }


    public void retorna_articulo(View v)
    {

        /***********************/
        String message=art_sel.getText().toString();
        Intent intentMessage=new Intent();
        intentMessage.putExtra("MESSAGE",art_pedido );
        // put the message in Intent
     //->ok   intentMessage.putExtra("MESSAGE", message);
        // Set The Result in Intent
        setResult(2,intentMessage);
        // finish The activity
        finish();

    }

    }//fin
