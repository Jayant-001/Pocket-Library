package com.jayant.pocketlibrary.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jayant.pocketlibrary.*;
import com.jayant.pocketlibrary.ebooks.UploadEbooks;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {

    private TextView signupBtn, termCond;

    private EditText inputName, inputEmail, inputPassword, inputConfPassword;
    private ImageView googleSignup, fbSignup, twitterSignup;

    private FirebaseAuth fAuth;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = findViewById(R.id.signup_btn);
        fAuth = FirebaseAuth.getInstance();

        inputName = findViewById(R.id.signup_input_name);
        inputEmail = findViewById(R.id.signup_input_email);
        inputPassword = findViewById(R.id.signup_input_password);
        inputConfPassword = findViewById(R.id.signup_input_conf_password);

        googleSignup = findViewById(R.id.google_signup);
        fbSignup = findViewById(R.id.fb_signup);
        twitterSignup = findViewById(R.id.twitter_signup);
        termCond = findViewById(R.id.signup_term);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String pass = inputPassword.getText().toString();
                String confPass = inputConfPassword.getText().toString();


                if(name.isEmpty()) {
                    inputName.setError("Name is required");
                    Toast.makeText(SignupActivity.this, "Please input Name", Toast.LENGTH_SHORT).show();
                    inputName.requestFocus();
                    return;
                }

                if(email.isEmpty()) {
                    inputEmail.setError("Email is required");
                    Toast.makeText(SignupActivity.this, "Please input Email", Toast.LENGTH_SHORT).show();
                    inputEmail.requestFocus();
                    return;
                }

                if(pass.isEmpty()) {
                    inputPassword.setError("Password is required");
                    Toast.makeText(SignupActivity.this, "Please input Password", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }

                if(confPass.isEmpty()) {
                    inputConfPassword.setError("Confirm password is required");
                    Toast.makeText(SignupActivity.this, "Please input Confirm Password", Toast.LENGTH_SHORT).show();
                    inputConfPassword.requestFocus();
                    return;
                }

                if(!pass.equals(confPass)) {
                    inputConfPassword.setError("Confirm password is not same");
                    Toast.makeText(SignupActivity.this, "Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    inputConfPassword.requestFocus();
                    return;
                }

                Toast.makeText(SignupActivity.this, "Signing Up, Please wait...", Toast.LENGTH_SHORT).show();


                fAuth.createUserWithEmailAndPassword(email, pass).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(SignupActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                fAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();

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


        googleSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Currently this feature is not available.");
                builder.setCancelable(true);
                builder.show();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
            }
        });


        fbSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Currently this feature is not available.");
                builder.setCancelable(true);
                builder.show();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
            }
        });


        twitterSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Currently this feature is not available.");
                builder.setCancelable(true);
                builder.show();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

            }
        });

        termCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Our Terms & Conditions");
                builder.setMessage("Chill bro your data is save");
                builder.setCancelable(true);
                builder.show();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
            }
        });

    }
}