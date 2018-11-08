package com.example.nikita.teplica5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    int automatika=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText t_otkr = (EditText) findViewById(R.id.t_otkr);
        final EditText t_zakr = (EditText) findViewById(R.id.t_zakr);
        final EditText tek_t = (EditText) findViewById(R.id.tek_t);
        final EditText tek_vl = (EditText) findViewById(R.id.tek_vl);
        final EditText timer_zakr = (EditText) findViewById(R.id.timer_zakr);
        final EditText timer_zakr2 = (EditText) findViewById(R.id.timer_zakr2);


        Button button5 = (Button) findViewById(R.id.button5);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Switch switch2 = (Switch) findViewById(R.id.switch2);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
               String otvet= zapros("poluch_all");
               String[] otvet_spl=otvet.split(";");
                t_otkr.setText(otvet_spl[0]);
                t_zakr.setText(otvet_spl[1]);
                tek_t.setText(otvet_spl[2]);
                tek_vl.setText(otvet_spl[3]);
                timer_zakr.setText(otvet_spl[4]);
                automatika=Integer.parseInt(otvet_spl[5]);
                if(automatika==1){
                    switch2.setChecked(true);
                }else{
                    switch2.setChecked(false);
                }
                timer_zakr2.setText(otvet_spl[6]);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                if(switch2.isChecked()){
                    automatika=1;
                }else{
                    automatika=0;
                }
                zapros("upload?"+t_otkr.getText()+";"+t_zakr.getText()+";"+tek_t.getText()+";"+tek_vl.getText()+";"+timer_zakr.getText()+";"+automatika+";"+timer_zakr2.getText()+"&");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                zapros("close");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View r) {
                zapros("open");
            }
        });
    }


    String zapros(final String command) {
        String sdfsf="";
        try {
             sdfsf = new AsyncTask<Void, String, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    String s = "";
                    try {
                        s = doGet("http://192.168.4.1/" + command);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return s;
                }

                @Override
                protected void onPostExecute(final String result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // tvRez.setText(result);
                        }
                    });
                }
            }.execute().get();
        } catch (Exception e) {

        }
        return sdfsf;
    }


    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d("asdasd", "Response string: " + response.toString());


        return response.toString();
    }


}
