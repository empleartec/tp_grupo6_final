package com.example.ferreteria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Activity_Pedidos extends Activity {


    int request_code = 1;
    private TextView tvTexto;
    private TextView art_sel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        art_sel = (TextView) findViewById(R.id.txt_codigo_select);
    }

    public void agregar_articulo(View v)
    {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main_pedidos);

        TextView[] tx = new TextView[10];
        TableRow[] tr = new TableRow[10];
        TableLayout tl = (TableLayout)findViewById(R.id.table_main_pedidos);
        for(int i = 0; i < 10; i++)
        {
            tx[i] = new TextView(this);
            //tx[i].setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tx[i].setText("Dates1");
            tx[i].setEnabled(true);
            tr[i] = new TableRow(this);
            tr[i].setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr[i].addView(tx[i]);
            tl.addView(tr[i]);
        }

        setContentView(tl);
        /*

        TableRow tbrow0 = new TableRow(Activity_Pedidos.this);
        tbrow0.setBackgroundColor(Color.WHITE);

        TextView tv0 = new TextView(Activity_Pedidos.this);

        tv0.setTextColor(Color.BLUE);
        tv0.setText("[X] 1234567891013");
        tv0.setEnabled(true);
        tv0.setCursorVisible(true);
        tv0.setFocusableInTouchMode(true);
        tv0.setInputType(InputType.TYPE_CLASS_TEXT);
        tv0.requestFocus();
        tv0.setSelected(true);


        tbrow0.addView(tv0);//pongo texto en row


        TextView tv1 = new TextView(this);
        tv1.setText(" Product ");

        tv1.setTextColor(Color.BLUE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" cantidad");
        tv2.setTextColor(Color.BLUE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Precio ");
        tv3.setTextColor(Color.BLUE);
        tbrow0.addView(tv3);


        stk.addView(tbrow0);// pongo la row en la table

        */

       /* for (int i = 0; i < 25; i++)
        {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("Rs." + i);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + i * 15 / 32 * 10);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }*/

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
                // fetch the message String
                String message=data.getStringExtra("MESSAGE");
                // Set the message string in textView
                art_sel.setText( message);
            }
        }
    }




}
