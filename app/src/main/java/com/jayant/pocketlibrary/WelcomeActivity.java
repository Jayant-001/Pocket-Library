package com.jayant.pocketlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String value = sharedPreferences.getString("user_name", "none");


        Thread thread = new Thread() {

            public void run() {

                try {
                    sleep(5000);
                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                finally {

                    if(value.equals("none")) {
                        startActivity(new Intent(WelcomeActivity.this, FormActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }


                }
            }
        };

        thread.start();


    }
}