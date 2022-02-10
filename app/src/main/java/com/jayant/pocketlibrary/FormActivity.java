package com.jayant.pocketlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private Spinner branch_sp;
    private ArrayList<String> list_branch;
    private ArrayAdapter<String> adapter_branch;
    private Button btnSave;
    private EditText inputName, inputEmail, inputSem, inputCollege, inputLoc;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Initialize local views....
        branch_sp = findViewById(R.id.user_input_sp_branch);
        btnSave = findViewById(R.id.btn_save);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputSem = findViewById(R.id.input_sem);
        inputCollege = findViewById(R.id.input_college_name);
        inputLoc = findViewById(R.id.input_loc);

        // Check Internet Connection......
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            Toast.makeText(this, "Welcome to Pocket Library", Toast.LENGTH_SHORT).show();

            btnSave.setClickable(true);
        }
        else {
            Toast.makeText(this, "Internet not access", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
            builder.setTitle("Internet Connection is required");
            builder.setMessage("Turn on Internet and relaunch this app.");
            builder.setCancelable(true);
            builder.show();

            btnSave.setEnabled(false);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
        }

        // reference of Firebase database....
        reference = FirebaseDatabase.getInstance().getReference("user_data");


        list_branch = new ArrayList<>();
        list_branch.add("Select branch");
        list_branch.add("C S E");
        list_branch.add("I T");
        list_branch.add("E C");
        list_branch.add("I C");

        // setting branch list to branch spinner....
        adapter_branch = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_branch);
        branch_sp.setAdapter(adapter_branch);

        // handling continue button.......
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String sem = inputSem.getText().toString();
                String college = inputCollege.getText().toString();
                String loc = inputLoc.getText().toString();

                TextView branch_text = ((TextView) branch_sp.getSelectedView());
                String branch = branch_text.getText().toString();

                // check all fields are filled or not.......
                if(name.isEmpty()) {
                    inputName.setError("Enter your name");
                    inputName.requestFocus();
                    return;
                }

                if(email.isEmpty()) {
                    inputEmail.setError("Enter Enrollment No.");
                    inputEmail.requestFocus();
                    return;
                }

                if(sem.isEmpty()) {
                    inputSem.setError("Enter Course");
                    inputSem.requestFocus();
                    return;
                }

                if(college.isEmpty()) {
                    inputCollege.setError("Enter College Name");
                    inputCollege.requestFocus();
                    return;
                }

                if (branch.equals("Select branch")) {
                    branch_text.setError("Plz select branch");
                    branch_text.requestFocus();
                    return;
                }

                if(loc.isEmpty()) {
                    inputLoc.setError("This field is required");
                    inputLoc.requestFocus();
                    return;
                }

                // getting current date from system.......
                Calendar calendarDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
                String date = currentDate.format(calendarDate.getTime());

                UserData userData = new UserData(name, email, sem, college, branch, date, loc);

                // uploading user data to Firebase database....
                reference.push().setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        // SharedPreferences to store user data offline....
                        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        if(branch.equals("C S E")) {
                            editor.putString("branch", "cse_books");
                        }
                        else if(branch.equals("E C")) {
                            editor.putString("branch", "ec_books");
                        }
                        else if(branch.equals("I T")) {
                            editor.putString("branch", "it_books");
                        }
                        else if(branch.equals("I C")) {
                            editor.putString("branch", "ic_books");
                        }


                        editor.putString("user_name", name);
                        editor.putString("user_email", email);
                        editor.putString("user_sem", sem);
                        editor.putString("user_college", college);
                        editor.putString("user_branch", branch);
                        editor.apply();

                        Toast.makeText(FormActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(FormActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                        Log.d("jayant", e.toString());
                    }
                });


//                Toast.makeText(FormActivity.this, ": " + sem, Toast.LENGTH_SHORT).show();


            }
        });

    }
}