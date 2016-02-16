package com.example.login2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class login2 extends Activity {
    EditText un,pw;
	TextView error;
    Button ok;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.et_un);
        pw=(EditText)findViewById(R.id.et_pw);
        ok=(Button)findViewById(R.id.btn_login);
        error=(TextView)findViewById(R.id.tv_error);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            	postParameters.add(new BasicNameValuePair("username", un.getText().toString()));
            	postParameters.add(new BasicNameValuePair("password", pw.getText().toString()));
            	//String valid = "1";
            	String response = null;
            	try {
                    //en local mimaquina
            	    //response = CustomHttpClient.executeHttpPost("http://10.0.0.201:8080/F2016/androidres.do", postParameters);

                    //desde afuera
                    response = CustomHttpClient.executeHttpPost("http://pimpo.mooo.com/F2016/AndroidResponse", postParameters);
            	    String res=response.toString();
            	   // res = res.trim();
            	    res= res.replaceAll("\\s+","");         	              	 
            	    //error.setText(res);
            	   
            	   if(res.equals("1"))
            	    	error.setText("Bienvenido: "+un.getText());
            	    else
            	    	error.setText("Ususrio  o Clave incorrectas");
            	} catch (Exception e) {
            		un.setText(e.toString());
            	}

            }
        });
    }
}
