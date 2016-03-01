package com.example.ferreteria;

/**
 * Created by pimpo on 01/02/16.
 */
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
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


    private static final String BS_PACKAGE = "com.google.zxing.client.android";
    public static final int REQUEST_CODE = 0x0000c0de;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articulos_activity);
        activity=this;
        art_pedido=new Articulo();
        initData();
        initView();


        if (savedInstanceState != null) {
            String codigo = savedInstanceState.getString("key");

        }

       // conectarBDMySQL(usuarioMySQL, contrasenaMySQL, ipServidorMySQL, puertoMySQL, catalogoMySQL);

       buttonBuscarSKU = (ImageButton) findViewById(R.id.btn_art_SKU);

        buttonDescripcion = (ImageButton) findViewById(R.id.bto_busDesc);
        atrSKU = (TextView) findViewById(R.id.art_SKU);
        artDescL = (TextView) findViewById(R.id.desc_art);
        art_sel = (TextView) findViewById(R.id.txt_art_select);

                //codMuesta = (TextView) findViewById(R.id.cod_muestra);
        //desMuestra = (TextView) findViewById(R.id.desc_muestra);
        //marcaMuesta = (TextView) findViewById(R.id.marca_mues);

        //Creo los lisener de los botonas
         //buttonBuscarSKU.setOnClickListener(new View.OnClickListener() {

             buttonBuscarSKU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //atrSKU.setText("1000101005019");
                   // Toast.makeText(getApplicationContext(), "Buscando por codigo", Toast.LENGTH_SHORT).show();
                    String sqlEjscuar = "SELECT * FROM Ferreteria.T001_ARTICULOS where txt_ARTICULO_SKU='" + atrSKU.getText() + "'";
                    String rst = ejecutarConsultaSQL(sqlEjscuar);
                    desMuestra.setText(rst);

                } catch (Exception e) {
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", "value");
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
        setResult(2, intentMessage);
        // finish The activity
        finish();

    }

    /* tema de la camara lee el codigo de barra*/
    public void leer_codigoB(View v)
    {
        Intent intentScan = new Intent(BS_PACKAGE + ".SCAN");
        intentScan.putExtra("PROMPT_MESSAGE", "Enfoque entre 9 y 11 cm.viendo sólo el código de barras");
        String targetAppPackage = findTargetAppPackage(intentScan);
        if (targetAppPackage == null) {
            showDownloadDialog();
        } else startActivityForResult(intentScan, REQUEST_CODE);
    }



private String findTargetAppPackage(Intent intent) {

    PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> availableApps = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (availableApps != null) {
        for (ResolveInfo availableApp : availableApps) {
        String packageName = availableApp.activityInfo.packageName;
        if (BS_PACKAGE.contains(packageName)) {
        return packageName;
        }
        }
        }
        return null;
        }
private AlertDialog showDownloadDialog() {
final String DEFAULT_TITLE = "Instalar Barcode Scanner?";
final String DEFAULT_MESSAGE =
        "Esta aplicación necesita Barcode Scanner. Quiere instalarla?";
final String DEFAULT_YES = "Si";
final String DEFAULT_NO = "No";

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(DEFAULT_TITLE);
        downloadDialog.setMessage(DEFAULT_MESSAGE);
        downloadDialog.setPositiveButton(DEFAULT_YES, new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialogInterface, int i) {
        Uri uri = Uri.parse("market://details?id=" + BS_PACKAGE);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
        activity.startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
        // Hmm, market is not installed
        Toast.makeText(Activity_Articulo.this, "Android market no esta instalado,no puedo instalar Barcode Scanner", Toast.LENGTH_LONG).show();
        }
        }
        });
        downloadDialog.setNegativeButton(DEFAULT_NO, new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialogInterface, int i) {}
        });
        return downloadDialog.show();
        }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");
                byte[] rawBytes = intent.getByteArrayExtra("SCAN_RESULT_BYTES");
                int intentOrientation = intent.getIntExtra("SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
                Integer orientation = intentOrientation == Integer.MIN_VALUE ? null : intentOrientation;
                String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
                Toast.makeText(this, contents, Toast.LENGTH_LONG).show();

                atrSKU.setText(contents);

                //buttonBuscarSKU.setOnClickListener(dispatchKeyEvent( KeyEvent.KEYCODE_ENTER)  );

                String sqlEjscuar = "SELECT * FROM Ferreteria.T001_ARTICULOS where txt_ARTICULO_SKU='" + atrSKU.getText() + "'";
                String rst = ejecutarConsultaSQL(sqlEjscuar);
                desMuestra = (TextView) findViewById(R.id.desc_art);

                //desMuestra.setText(rst);
            }
        }
        return ;
    }


}//fin
