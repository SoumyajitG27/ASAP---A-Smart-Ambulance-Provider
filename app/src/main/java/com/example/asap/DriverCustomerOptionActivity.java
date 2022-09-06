package com.example.asap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.asap.databinding.ActivityDriverCustomerOptionBinding;

public class DriverCustomerOptionActivity extends AppCompatActivity {

    private Button driver, customer;
    ActivityDriverCustomerOptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverCustomerOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        driver = (Button) findViewById(R.id.driverbtn);
        customer = (Button) findViewById(R.id.customerbtn);

        driver.setOnClickListener(view -> {
            Intent in = new Intent(DriverCustomerOptionActivity.this, DriverRegistrationActivity.class);
            startActivity(in);
        });

        customer.setOnClickListener(view -> {
            startActivity(new Intent(DriverCustomerOptionActivity.this, CustomerRegistrationActivity.class));
        });
    }
}