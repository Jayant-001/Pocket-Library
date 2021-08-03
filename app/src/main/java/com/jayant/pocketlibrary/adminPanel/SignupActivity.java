package com.jayant.pocketlibrary.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jayant.pocketlibrary.*;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {

    private Button signupBtn;

    private EditText inputName, inputEmail, inputPassword, inputConfPassword;

    private FirebaseAuth fAuth;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = findViewById(R.id.signup_btn);
        signupBtn.setBackgroundColor(R.color.custom_blue);
        fAuth = FirebaseAuth.getInstance();

        inputName = findViewById(R.id.signup_input_name);
        inputEmail = findViewById(R.id.signup_input_email);
        inputPassword = findViewById(R.id.signup_input_password);
        inputConfPassword = findViewById(R.id.signup_input_conf_password);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String pass = inputPassword.getText().toString();
                String confPass = inputConfPassword.getText().toString();

                fAuth.createUserWithEmailAndPassword(email, pass).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(SignupActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("jayant", e.toString());
                    }
                });

            }
        });


    }
}