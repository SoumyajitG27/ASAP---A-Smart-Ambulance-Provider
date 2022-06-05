package com.example.asap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asap.databinding.ActivityDriverRegistrationBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverRegistrationActivity extends AppCompatActivity {

    ActivityDriverRegistrationBinding binding;
    TextInputLayout emailLayout, pwdLayout;
    TextInputEditText emailInput, pwdInput;
    Button optionLogIn;
    Button btnSignup;
    String email, pass;
    FirebaseAuth auth;
    FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        emailLayout = binding.layoutEmail;
        pwdLayout = binding.layoutPassword;
        pwdInput = binding.inputPassword;
        emailInput = binding.inputEmail;
        btnSignup = binding.btnSubmit;
        optionLogIn = binding.signIn;

        // firebase authentication instance creation
        auth = FirebaseAuth.getInstance();

        // firestore instance
        fs = FirebaseFirestore.getInstance();

        optionLogIn.setOnClickListener(view -> {
            // TODO: route to the login or sign in screen that you have added to the project
            Intent intent = new Intent(this, DriverLogInActivity.class);
            startActivity(intent);
        });

        btnSignup.setOnClickListener(view -> {
            if(validateFields()){
                doRegister();
                //Toast.makeText(SignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doRegister() {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(task -> {
                    FirebaseUser fu=auth.getCurrentUser();
                    if(fu != null){
                        String usrID = auth.getUid();
                        // to verify email - send verification mail
                        auth.getCurrentUser().sendEmailVerification();
                        storeInfo(usrID);
                    }
                    else
                    {
                        Toast.makeText(this, "Try later, unable to Sign Up!", Toast.LENGTH_SHORT).show();
                    }
                }) // to detect the process completion status
                .addOnFailureListener(e -> Toast.makeText(this, "Sorry "+ e.getMessage(), Toast.LENGTH_SHORT).show()); // for any exception occured, the failure will be detected
    }

    private void storeInfo(String usrID) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        fs.collection("user_info")
                .document(usrID)
                .set(userMap)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(DriverRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private boolean validateFields() {
        // making the error part empty
        pwdLayout.setError("");
        emailLayout.setError("");
        // saving data provided by user
        email = String.valueOf(emailInput.getText());
        pass = String.valueOf(pwdInput.getText());
        // performing validations
        if(email.isEmpty())
            emailLayout.setError("Email missing!");
        else if(pass.isEmpty())
            pwdLayout.setError("Provide a password please!");
        else
            return true;
        return false;
    }
}
