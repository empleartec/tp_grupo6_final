package com.example.ferreteria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Activity_Pedidos extends Activity {


    int request_code = 1;
    private TextView tvTexto;
    private TextView art_sel;
    public Pedidos ped;
    public  Articulo  art_pedido;
    private TextView cantidad;
    public ImageButton bt_Agregar;
    public ImageButton bt_guardar;
    List<Pedidos> lista_pedido = new ArrayList<Pedidos>();
    private boolean guardar_pedido=true;

    int identificador=0;
    public View rowdel;
    public String artDesc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        art_sel = (TextView) findViewById(R.id.txt_codigo_select);
        ped=new Pedidos();
        cantidad=(TextView) findViewById(R.id.txt_cantt);
        bt_Agregar=(ImageButton)findViewById(R.id.bt_Agregar);
        bt_guardar=(ImageButton)findViewById(R.id.bt_Guardar);
        bt_guardar.setEnabled(false);
        bt_Agregar.setEnabled(false);
        cantidad.setEnabled(false);
        art_sel.setText("");
        seteaControles(false);


    }

    public void seteaControles (boolean opc)
    {
        bt_guardar.setEnabled(opc);
        bt_Agregar.setEnabled(opc);
        cantidad.setEnabled(opc);
        cantidad.setText("");
    }


    public void agregar_articulo(View v)
    {
        ManejadorFechas fecha=new ManejadorFechas();
        String hoy=fecha.getFechaActual().toString();
        TableLayout stk = (TableLayout) findViewById(R.id.table_main_pedidos);

        TextView[] tx = new TextView[10];
        TableRow[] tr = new TableRow[10];
        TableLayout tl = (TableLayout)findViewById(R.id.table_main_pedidos);








        //genero los texview
            tx[0] = new TextView(this);
            tx[1] = new TextView(this);
            tx[2] = new TextView(this);

            //lleno los texview
            //tx[i].setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tx[0].setText(art_pedido.getTxt_ARTICULO_SKU());
            tx[1].setText(art_pedido.getTxt_DESCRIPCION());
            //artDesc=art_pedido.getTxt_DESCRIPCION();
            tx[2].setText(cantidad.getText());

            tx[0].setEnabled(true);
            tr[0] = new TableRow(this);
            tr[0].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            // agrego los textos a la vista
            tr[0].addView(tx[0]);
            tr[0].addView(tx[1]);
            tr[0].addView(tx[2]);

        /* boton de borrar */
        final ImageButton btn_no = new ImageButton(this);
        btn_no.setTag(art_pedido.getTxt_DESCRIPCION());
        //btn_no.setImageResource(R.drawable.del);
       btn_no.setImageResource(android.R.drawable.ic_delete);//presence_busy
        //android:background="@android:color/transparent"
        btn_no.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        btn_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // la row seleccionada
                rowdel = (View) arg0.getParent();
                /********************/

                /**********************/

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Activity_Pedidos.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Desea eliminar este Articulo:\n" + btn_no.getTag().toString() + " ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        confirmar_borro(true);
                        /*row = (TableRow)findViewById(R.id.row);
                        table.removeView(row);*/

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //cancelar();
                    }
                });
                dialogo1.show();
            }

        });




        tr[0].addView(btn_no);
            tl.addView(tr[0]);
            // armo el pedido
             Pedidos p= new Pedidos();
             p.setCantidad(new Integer(cantidad.getText().toString()).intValue()) ;
        p.setCliente_id(1);
             p.setFecha(hoy);
             p.setFlete(0);
             p.setId(2);
             p.setSku(art_pedido.getTxt_ARTICULO_SKU());
             lista_pedido.add(p);
            //limpio los controles
            seteaControles(false);
            bt_guardar.setEnabled(true);
            art_sel.setText("");

    }


    public void confirmar_borro(boolean si) {
        Toast t=Toast.makeText(this,"borro el articulo", Toast.LENGTH_SHORT);
        t.show();
        ViewGroup container = ((ViewGroup)rowdel.getParent());
        // delete the row and invalidate your view so it gets redrawn
        container.removeView(rowdel);
        container.invalidate();


        guardar_pedido=si;
    }






    public void guardar_pedido(View v)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea gurdar el Pedido?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
               //en el curso
                     //String url = "http://10.54.11.133:8080/F2016/Jsp/Pedidos.jsp";
                // en casa
                String url ="http://10.54.11.133:8080/F2016/Jsp/Pedidos.jsp";
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    Gson gson = new Gson();
                    String cadena = gson.toJson(lista_pedido);
                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("userName", "User"));
                    pairs.add(new BasicNameValuePair("GsonPedidos", cadena));
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);
                    //veo si fue bien
                    String res = response.toString();
                    // res = res.trim();
                    res = res.replaceAll("\\s+", "");
                    //error.setText(res);

                    if (!res.equals("1")) {
                        //todo bien
                        Toast t=Toast.makeText(Activity_Pedidos.this,"Pedido Guardo con exito", Toast.LENGTH_SHORT);
                        t.show();
                        BaciaPedido();
                    } else{
                        //error de grabado
                        Toast t=Toast.makeText(Activity_Pedidos.this,"No se gusrdo el pedigo-> "+ res , Toast.LENGTH_SHORT);
                        t.show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar(false);
            }
        });
        dialogo1.show();



     }





    public void busca_articulo(View v)
    {
        /*Intent i = new Intent(Activity_Pedidos.this,
                Activity_Articulo.class);
        //startActivity(i);
        startActivityForResult(i,RESULT_OK);*/

        Intent intentGetMessage=new Intent(this,Activity_Articulo.class);
        startActivityForResult(intentGetMessage, 2);// Activity is started with requestCode 2



    }

    protected void onActivityResuldddt(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK))
        {
            art_sel.setText(data.getDataString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if(null!=data)
            {
                art_pedido = (Articulo)data.getSerializableExtra("MESSAGE");

                String detalle=art_pedido.getTxt_DESCRIPCION();


                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) // Configuration.ORIENTATION_LANDSCAPE
                {
                    detalle=detalle.substring(0,25);
                }

                art_sel.setText(detalle);
                seteaControles(true);
                //ped.setFecha(message);
            }
        }
    }

public boolean deceaGuardar ()
{
   boolean pasa=true;
    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
    dialogo1.setTitle("Importante");
    dialogo1.setMessage("¿ Desea gurdar el Pedido?");
    dialogo1.setCancelable(false);
    dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialogo1, int id) {

            aceptar(true);
        }
    });
    dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialogo1, int id) {
            cancelar(false);
        }
    });
    dialogo1.show();
    return guardar_pedido;

}

    public void aceptar(boolean si) {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        t.show();
        guardar_pedido=si;
    }

    public void cancelar(boolean no) {
        guardar_pedido=no;
        //finish();
    }


//************************************************


    public void guardar_pedidoOLD(View v) {
        if (deceaGuardar()) {

            String url = "http://10.54.11.133:8080/F2016/Jsp/Pedidos.jsp";
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);

                Gson gson = new Gson();
                String cadena = gson.toJson(lista_pedido);

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("userName", "hardik"));
                pairs.add(new BasicNameValuePair("password", cadena));

                post.setEntity(new UrlEncodedFormEntity(pairs));

                HttpResponse response = client.execute(post);
                //veo si fue bien

                String res = response.toString();
                // res = res.trim();
                res = res.replaceAll("\\s+", "");
                //error.setText(res);

                if (res.equals("0")) {
                    //todo bien
                    Toast t=Toast.makeText(this,"Pedido Guardo con exito, a limpiar el form", Toast.LENGTH_SHORT);
                    t.show();

                } else{
                    //error de grabado
                    Toast t=Toast.makeText(this,"No se gusrdo el pedigo ", Toast.LENGTH_SHORT);
                    t.show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

void BaciaPedido()
{

    Intent i = new Intent(this, Main_Activity.class );
    startActivity(i);
    finish();
  /*  lista_pedido.clear();
    TableLayout tl = (TableLayout)findViewById(R.id.table_main_pedidos);
    tl.destroyDrawingCache();
    tl.removeAllViews();*/


}

}
