package com.devstream.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


/**
 * Created by pimpo on 11/02/16.
 */
public class MainActivity extends Activity {

    TextView capitalTextView;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        capitalTextView = (TextView) findViewById(R.id.capital_textview);

        this.retrieveCapitals();
    }

    void retrieveCapitals() {

        progressDialog = ProgressDialog.show(this,
                "Please wait...", "Retrieving data...", true, true);

        CapitalsRetrieverAsyncTask task = new CapitalsRetrieverAsyncTask();
        task.execute();
        progressDialog.setOnCancelListener(new CancelListener(task));
    }

    private class CapitalsRetrieverAsyncTask extends AsyncTask<Void, Void, Void> {

        Response response;

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://192.168.1.3:8080/Dochadzka/jsp/Android/GSON.jsp";

            HttpGet getRequest = new HttpGet(url);

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse getResponse = httpClient.execute(getRequest);
                final int statusCode = getResponse.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
                    return null;
                }

                HttpEntity getResponseEntity = getResponse.getEntity();
                InputStream httpResponseStream = getResponseEntity.getContent();
                Reader inputStreamReader = new InputStreamReader(httpResponseStream);

                Gson gson = new Gson();
                this.response = gson.fromJson(inputStreamReader, Response.class);
            }
            catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            StringBuilder builder = new StringBuilder();
            for (Country country : this.response.data) {

                builder.append(String.format("<br>Country: <b>%s</b><br>Capital: <b>%s</b><br><br>", country.getCountry(), country.getCapital()));

            }

            capitalTextView.setText(Html.fromHtml(builder.toString()));
            progressDialog.cancel();
        }

    }

    private class CancelListener implements OnCancelListener {

        AsyncTask<?, ?, ?> cancellableTask;

        public CancelListener(AsyncTask<?, ?, ?> task) {
            cancellableTask = task;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            cancellableTask.cancel(true);
        }

    }

}
