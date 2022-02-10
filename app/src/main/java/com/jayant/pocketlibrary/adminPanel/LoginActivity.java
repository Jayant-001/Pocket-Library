package com.jayant.pocketlibrary.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.jayant.pocketlibrary.*;
import com.jayant.pocketlibrary.ebooks.UploadEbooks;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private TextView loginBtn, termAndCond;
    private EditText inputEmail, inputPassword;
    private ImageView googleLogin, fbLogin, twitterLogin;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleLogin = findViewById(R.id.google_login);
        fbLogin = findViewById(R.id.fb_login);
        twitterLogin = findViewById(R.id.twitter_login);

        inputEmail = findViewById(R.id.signup_input_email);
        inputPassword = findViewById(R.id.signup_input_password);
        loginBtn = findViewById(R.id.login_btn);
        fAuth = FirebaseAuth.getInstance();

        termAndCond = findViewById(R.id.termAndCond);


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

                Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

                fAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UploadEbooks.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Log.d("jayant", e.toString());

                        Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        termAndCond.setOnClickListener(new View.OnClickListener() {
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

        googleLogin.setOnClickListener(new View.OnClickListener() {
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

        fbLogin.setOnClickListener(new View.OnClickListener() {
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

        twitterLogin.setOnClickListener(new View.OnClickListener() {
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
    }
}