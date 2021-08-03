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

//        String sem = getIntent().getStringExtra("sem_name");
        Log.d("jayant", sem);

        subjectListView = findViewById(R.id.list_subject);
        subList = new ArrayList<>();


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
            subList.add(".NET");
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