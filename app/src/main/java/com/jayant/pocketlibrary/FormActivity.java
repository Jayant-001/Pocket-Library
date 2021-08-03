package com.jayant.pocketlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jayant.pocketlibrary.user.UserData;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private Spinner sem_sp;
    private ArrayList<String> list_sem;
    private ArrayAdapter<String> adapter_branch;
    private Button btnSave;
    private EditText inputName, inputEnroll, inputCourse, inputCollege;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        sem_sp = findViewById(R.id.user_input_sp_sem);
        btnSave = findViewById(R.id.btn_save);
        inputName = findViewById(R.id.input_name);
        inputEnroll = findViewById(R.id.input_enroll);
        inputCourse = findViewById(R.id.input_course_name);
        inputCollege = findViewById(R.id.input_college_name);

        reference = FirebaseDatabase.getInstance().getReference("user_data");

        list_sem = new ArrayList<>();

        list_sem.add("Select semester");
        list_sem.add("First Sem");
        list_sem.add("Second Sem");
        list_sem.add("Third Sem");
        list_sem.add("Fourth Sem");
        list_sem.add("Fifth Sem");
        list_sem.add("Sixth Sem");

        adapter_branch = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_sem);
        sem_sp.setAdapter(adapter_branch);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = inputName.getText().toString();
                String enrollNo = inputEnroll.getText().toString();
                String course = inputCourse.getText().toString();
                String college = inputCollege.getText().toString();

                TextView sem_text = ((TextView) sem_sp.getSelectedView());
                String sem = sem_text.getText().toString();

                if(name.isEmpty()) {
                    inputName.setError("Enter your name");
                    inputName.requestFocus();
                    return;
                }

                if(enrollNo.isEmpty()) {
                    inputEnroll.setError("Enter Enrollment No.");
                    inputEnroll.requestFocus();
                    return;
                }

                if(course.isEmpty()) {
                    inputCourse.setError("Enter Course");
                    inputCourse.requestFocus();
                    return;
                }

                if(college.isEmpty()) {
                    inputCollege.setError("Enter College Name");
                    inputCollege.requestFocus();
                    return;
                }

                if (sem.equals("Select semester")) {
                    sem_text.setError("Plz select semester");
                    sem_text.requestFocus();
                    return;
                }


                Calendar calendarDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
                String date = currentDate.format(calendarDate.getTime());


                UserData userData = new UserData(name, enrollNo, course, college, sem, date);

                reference.push().setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {


                        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("user_name", name);
                        editor.putString("user_enroll", enrollNo);
                        editor.putString("user_course", course);
                        editor.putString("user_college", college);
                        editor.putString("selected_sem", sem);
                        editor.apply();

                        Toast.makeText(FormActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(FormActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        Log.d("jayant", e.toString());
                    }
                });


//                Toast.makeText(FormActivity.this, ": " + sem, Toast.LENGTH_SHORT).show();


            }
        });

    }
}