package com.jayant.pocketlibrary.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jayant.pocketlibrary.*;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private EditText feedbackText;
    private Button feedbackBtn;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackText = findViewById(R.id.feedbackText);
        feedbackBtn = findViewById(R.id.sendFeedback);
        reference = FirebaseDatabase.getInstance().getReference("feedback");

        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calendarDate.getTime());


        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<Object, String> data = new HashMap<>();
                data.put("text", feedbackText.getText().toString());
                data.put("date", date);

                Toast.makeText(Feedback.this, "Sending Feedback", Toast.LENGTH_SHORT).show();

                reference.push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(Feedback.this, "Thank you for sending your feedback.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(Feedback.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        Log.d("jayant", e.toString());
                    }
                });

            }
        });
    }
}