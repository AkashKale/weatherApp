package com.example.user.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {
    TextView tvDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvDetails=(TextView)findViewById(R.id.tvDetails);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("data"));
            JSONObject main;
            main=jsonObject.getJSONObject("main");
            String text="Temp : "+main.getString("temp")+" F"+
                    "\nPressure : "+main.getString("pressure")+
                    "\nHumidity : "+main.getString("humidity");
            tvDetails.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
}
