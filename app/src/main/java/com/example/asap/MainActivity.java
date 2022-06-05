package com.example.asap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button driver, customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driver = (Button) findViewById(R.id.driverbtn);
        customer = (Button) findViewById(R.id.driverbtn);

        driver.setOnClickListener(view -> {
            Intent in = new Intent(this, DriverRegistrationActivity.class);
            startActivity(in);
        });
    }
}