package com.jayant.pocketlibrary.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jayant.pocketlibrary.*;

public class AppInfo extends AppCompatActivity {

    private TextView report, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        report = findViewById(R.id.report_text);
        userName = findViewById(R.id.app_info_user_name);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AppInfo.this, Feedback.class));


            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", "none");

        userName.setText(name);
    }
}