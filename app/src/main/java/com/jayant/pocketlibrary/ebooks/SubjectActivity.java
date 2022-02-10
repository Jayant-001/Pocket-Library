package com.jayant.pocketlibrary.ebooks;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jayant.pocketlibrary.R;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    private ListView subjectListView;
    private ArrayList<String> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        SharedPreferences preferences = getSharedPreferences("app_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String sem = preferences.getString("sem_name", "none");


        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String branch = sharedPreferences.getString("user_branch", "none");

        Log.d("jayant", "branch :--- " + branch);


//        String sem = getIntent().getStringExtra("sem_name");
        Log.d("jayant", sem);

        subjectListView = findViewById(R.id.list_subject);
        subList = new ArrayList<>();


        if(branch.equals("C S E")) {

            if (sem.equals("First sem")) {
                subList.add("Math-I");
                subList.add("Communication-I");
                subList.add("Physics-I");
                subList.add("Chemistry");
                subList.add("F C I T");
                subList.add("Technical Drawing");
                subList.add("Workshop Practice");
            }

            if(sem.equals("Second sem")) {
                subList.add("Math-II");
                subList.add("Physics-II");
                subList.add("B E E E");
                subList.add("Multimedia & Animation");
                subList.add("Programming using C");
                subList.add("O A T");
            }

            if(sem.equals("Third sem")) {
                subList.add("Math-III");
                subList.add("I & W T");
                subList.add("EVS");
                subList.add("D C C N");
                subList.add("DS using C");
                subList.add("Digital Electronic");
            }

            if(sem.equals("Fourth sem")) {
                subList.add("Communication-II");
                subList.add("D B M S");
                subList.add("OOP using Java");
                subList.add("O S");
                subList.add("E-com");
                subList.add("Energy Conservation");
                subList.add("U H V");
            }

            if(sem.equals("Fifth sem")) {
                subList.add("Software Engineering");
                subList.add("Web Dev using PHP");
                subList.add("Python");
                subList.add("C A H M");
                subList.add("I O T");
            }

            if(sem.equals("Sixth sem")) {
                subList.add("Android Development");
                subList.add("Cloud Computing");
                subList.add("I M E D");
                subList.add("Advance Java");
                subList.add("M L & D S");
                subList.add("DOT NET");
            }


        }
        else if (branch.equals("I T")) {

            if (sem.equals("First sem")) {
                subList.add("Math-I");
                subList.add("Communication-I");
                subList.add("Physics-I");
                subList.add("Chemistry");
                subList.add("F C I T");
                subList.add("Technical Drawing");
                subList.add("Workshop Practice");
            }

            if(sem.equals("Second sem")) {
                subList.add("Math-II");
                subList.add("Physics-II");
                subList.add("B E E E");
                subList.add("Multimedia & Animation");
                subList.add("Programming using C");
                subList.add("O A T");
            }

            if(sem.equals("Third sem")) {
                subList.add("Math-III");
                subList.add("I & W T");
                subList.add("EVS");
                subList.add("D C C N");
                subList.add("DS using C");
                subList.add("C A H M");
            }

            if(sem.equals("Fourth sem")) {
                subList.add("Communication-II");
                subList.add("D B M S");
                subList.add("OOP using Java");
                subList.add("O S");
                subList.add("E-com");
                subList.add("Energy Conservation");
                subList.add("U H V");
            }

            if(sem.equals("Fifth sem")) {
                subList.add("Software Engineering");
                subList.add("Web Dev using PHP");
                subList.add("Python");
                subList.add("IS & IT Laws");
                subList.add("I O T");
            }

            if(sem.equals("Sixth sem")) {
                subList.add("Android Development");
                subList.add("Cloud Computing");
                subList.add("I M E D");
                subList.add("Advance Java");
                subList.add("Big Data");
                subList.add("M L & D S");
                subList.add("Digital Image Processing");
            }
            
        }
        else if(branch.equals("E C")) {

            if (sem.equals("First sem")) {
                subList.add("Math-I");
                subList.add("Communication-I");
                subList.add("Physics-I");
                subList.add("Chemistry");
                subList.add("Engineering Drawing");
                subList.add("E M M");
                subList.add("Workshop-I");
            }

            if(sem.equals("Second sem")) {
                subList.add("Math-II");
                subList.add("Physics-II");
                subList.add("B I T");
                subList.add("EE-I");
                subList.add("E C D");
                subList.add("Workshop-II");
            }

            if(sem.equals("Third sem")) {
                subList.add("Math-III");
                subList.add("EE-II");
                subList.add("E V S");
                subList.add("E D C");
                subList.add("Electronic Workshop");
                subList.add("Digital Electronic");
                subList.add("U H V");
            }

            if(sem.equals("Fourth sem")) {
                subList.add("Communication-II");
                subList.add("I E T");
                subList.add("N F T L");
                subList.add("E I M");
                subList.add("P C E");
                subList.add("Energy Conservation");
            }

            if(sem.equals("Fifth sem")) {
                subList.add("I M E D");
                subList.add("Microprocessor");
                subList.add("O F C");
                subList.add("Consumer Electronic");
                subList.add("Programming in C");
            }

            if(sem.equals("Sixth sem")) {
                subList.add("Microwave");
                subList.add("Microcontrollers");
                subList.add("W M C S");
                subList.add("Control System");
                subList.add("Medical Electronics");
                subList.add("Computer Networks");
            }
            
        }
        else if (branch.equals("I C")) {

            if (sem.equals("First sem")) {
                subList.add("Communication-I");
                subList.add("Math-I");
                subList.add("Physics-I");
                subList.add("Chemistry");
                subList.add("Engineering Drawing");
                subList.add("E M M");
                subList.add("Workshop-I");
            }

            if(sem.equals("Second sem")) {
                subList.add("Math-II");
                subList.add("Physics-II");
                subList.add("B I T");
                subList.add("EE-I");
                subList.add("E C D");
                subList.add("Workshop-II");
            }

            if(sem.equals("Third sem")) {
                subList.add("Math-III");
                subList.add("EE-II");
                subList.add("E V S");
                subList.add("E D C");
                subList.add("Electronic Workshop");
                subList.add("Transducer & Application");
                subList.add("U H V");
            }

            if(sem.equals("Fourth sem")) {
                subList.add("Communication-II");
                subList.add("Principles of DE");
                subList.add("N F T L");
                subList.add("E I M");
                subList.add("Process Instrumentation");
                subList.add("Energy Conservation");
            }

            if(sem.equals("Fifth sem")) {
                subList.add("I M E D");
                subList.add("Microprocessor");
                subList.add("Industrial Control");
                subList.add("S T R D");
                subList.add("Programming in C");
            }

            if(sem.equals("Sixth sem")) {
                subList.add("Process Control");
                subList.add("Microcontrollers");
                subList.add("B M I");
                subList.add("Specialised Instruments");
                subList.add("Robotics");
                subList.add("Neural Networks");
            }

        }


        SubjectAdapter adapter = new SubjectAdapter(this, R.layout.item_subject, subList);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.subName);
//                Log.d("jayant", "sem " + sem);
//                Log.d("jayant", "sub " + textView.getText().toString());

                Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
//                intent.putExtra("get_sem", sem);
//                intent.putExtra("get_subject", textView.getText().toString());
                editor.putString("get_subject", textView.getText().toString());
                editor.apply();
                startActivity(intent);

            }
        });




    }

}