package com.example.ferreteria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;


/**
 * Created by pimpo on 18/02/16.
 */
public class Conectar extends Activity{

    //JSONParser jParser = new JSONParser();

  //  JSONObject json;

    List<Pedidos> lista = new ArrayList<Pedidos>();


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String url="http://10.0.0.21:8080/F2016/Jsp/Pedidos.jsp";
        try
        {
            setContentView(R.layout.activyty_conecta);
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            Pedidos p= new Pedidos();
            p.setCantidad(10);
            p.setCliente_id(1);
            p.setFecha("18-02-2016");
            p.setFlete(0);
            p.setId(2);
            p.setSku("1241106030013");

            lista.add(p);

            Pedidos p2= new Pedidos();
            p2.setCantidad(10);
            p2.setCliente_id(1);
            p2.setFecha("18-02-2016");
            p2.setFlete(0);
            p2.setId(2);
            p2.setSku("1250202070014");

            lista.add(p2);





            Gson gson= new Gson();
            String cadena=gson.toJson(lista);
           // String cadena= "{ \"Pedidos\":" + gson.toJson(lista)+ "}";
            //String cadena=  gson.toJson(p);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("userName", "hardik"));
            pairs.add(new BasicNameValuePair("password", cadena));

            post.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(post);


/*
            //******************************************************************
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("u","pimpo"));
            params.add(new BasicNameValuePair("p","pimpo"));
            json = jParser.makeHttpRequest(url, "GET", params);
            String s=null;

            try {
                s= json.getString("info");
               // Log.d("Msg", json.getString("info"));
                if(s.equals("success")){
                    Intent login = new Intent(getApplicationContext(), Activity_Acerca_De.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    finish();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

*/
            //******************************************************************
           // post.setParams(Pedidos, p);
            /*HttpResponse response = client.execute(post);
            HttpGet request = new HttpGet(params[0]);
            request.setAttribute("unEntero", new Integer(22));*/

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
