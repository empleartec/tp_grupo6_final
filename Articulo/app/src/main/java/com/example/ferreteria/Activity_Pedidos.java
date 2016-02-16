package com.example.ferreteria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
                art_sel.setText("Message from second Activity: " + message);
            }
        }
    }




}
