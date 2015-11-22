package com.example.cmbarnett.androidjokelib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_androidjoke);
        String strJoke = "Joke not found";
        TextView tvJokeAndroid;
        try {
            tvJokeAndroid = (TextView) findViewById(R.id.tvJokeAndroid);
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    strJoke = extras.getString("joke");
                }
            }
            tvJokeAndroid.setText(strJoke);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
