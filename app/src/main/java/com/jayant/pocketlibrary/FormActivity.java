package com.jayant.pocketlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    private Spinner sem_sp;
    private ArrayList<String> list_sem;
    private ArrayAdapter<String> adapter_branch;
    private Button btnSave;
    private EditText inputName, inputEnroll, inputCollege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        sem_sp = findViewById(R.id.sp_branch);
        btnSave = findViewById(R.id.btn_save);
        inputName = findViewById(R.id.input_name);
        inputEnroll = findViewById(R.id.input_enroll);
        inputCollege = findViewById(R.id.input_college_name);

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
                String college = inputCollege.getText().toString();

                TextView sem_text = ((TextView)sem_sp.getSelectedView());
                String sem = sem_text.getText().toString();

                if (sem.equals("Select semester")) {
                    sem_text.setError("Plz select semester");
                    sem_text.requestFocus();
                    return;
                }

                Toast.makeText(FormActivity.this, ": " + sem, Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("my_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("selected_sem", sem);
                editor.apply();

//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();

            }
        });

    }
}