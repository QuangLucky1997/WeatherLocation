package com.example.weather01.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather01.R;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        edtSearch = findViewById(R.id.edtSearchCity);
        btnSearch = findViewById(R.id.btnsearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSearch.getText().toString().matches("") == false) {
                    Intent intent = new Intent(MainActivity.this, WeatherTodayActivity.class);
                    String city = edtSearch.getText().toString();
                    intent.putExtra("cityName", city);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter the box above", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}