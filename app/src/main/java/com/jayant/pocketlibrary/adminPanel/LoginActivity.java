package com.jayant.pocketlibrary.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.jayant.pocketlibrary.*;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private TextView loginRegisterText, loginBtn;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRegisterText = findViewById(R.id.login_register_text);
        inputEmail = findViewById(R.id.signup_input_email);
        inputPassword = findViewById(R.id.signup_input_password);
        loginBtn = findViewById(R.id.login_btn);
        fAuth = FirebaseAuth.getInstance();

        loginRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SignupActivity.class));

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if(email.isEmpty()) {
                    inputEmail.setError("Email is required");
                    inputEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()) {
                    inputPassword.setError("Password is required");
                    inputPassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Log.d("jayant", e.toString());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}