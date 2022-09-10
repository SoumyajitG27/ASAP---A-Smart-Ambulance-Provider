package com.example.asap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asap.databinding.ActivityDriverLogInBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverLogInActivity extends AppCompatActivity {

    Button btnSignIn;
    ActivityDriverLogInBinding binding;
    TextInputLayout emailLayout, pwdLayout;
    TextInputEditText emailInput, pwdInput;
    String email,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        emailLayout=binding.layoutEmail;
        pwdLayout=binding.layoutPassword;
        emailInput=binding.inputEmail;
        pwdInput=binding.inputPassword;
        btnSignIn=binding.signIn;

        auth=FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(view -> {
            if(validateFields())
            {
                doLogIn();
                //Toast.makeText(this,"Sign In successful!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validateFields(){
        emailLayout.setError("");
        pwdLayout.setError("");

        email=String.valueOf(emailInput.getText());
        password=String.valueOf(pwdInput.getText());
        if(email.isEmpty())
            emailLayout.setError("Username missing!");
        else if(password.isEmpty())
            pwdLayout.setError("Password missing!");
        else
            return true;
        return false;
    }

    private void doLogIn(){
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if(user != null)
                    {
                        //if mail is sent
                        //if(user.isEmailVerified())
                        //{
                            // storing session/user/app data locally
                            LocalSession session = new LocalSession(DriverLogInActivity.this);
                            session.storeLogin();;
                            session.storeCredential(user.getUid(), email);
                            startActivity(new Intent(DriverLogInActivity.this, DriverMapActivity.class));
                            //Toast.makeText(this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                            DriverLogInActivity.this.finish();
                        //}
//                        else
//                        {
//                            Toast.makeText(this, "Sorry, email verification failed!", Toast.LENGTH_SHORT).show();
//                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Sorry, "+ e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
