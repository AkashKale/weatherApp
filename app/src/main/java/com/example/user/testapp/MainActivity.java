package com.example.user.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText etCountry;
    EditText etCity;
    TextView tvDisp,tvMore;
    JSONObject jsonObject;
    String readJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        etCountry=(EditText)findViewById(R.id.etCountry);
        etCity=(EditText)findViewById(R.id.etCity);
        tvDisp=(TextView)findViewById(R.id.tvDisp);
        tvMore=(TextView)findViewById(R.id.tvMore);
        tvDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country = etCountry.getText().toString();
                String city = etCity.getText().toString();
                String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=2de143494c0b295cca9337e1e96b00e0";
                try {
                    new getJSON().execute(url);
                } catch(Exception e){e.printStackTrace();}
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Details.class);
                myIntent.putExtra("data",readJSON ); //Optional parameters
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class getJSON extends AsyncTask<String, Void, String> {

        String temp;
        protected String doInBackground(String... address) {
            URL url= null;
            try {
                url = new URL(address[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                readJSON = result.toString();
                jsonObject = new JSONObject(readJSON);
                JSONObject main;
                main=jsonObject.getJSONObject("main");
                temp=main.getString("temp");
                //return result.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return temp;
        }
        protected void onPostExecute(String temp) {
            tvDisp.setText(temp+" F");
        }
    }
}
