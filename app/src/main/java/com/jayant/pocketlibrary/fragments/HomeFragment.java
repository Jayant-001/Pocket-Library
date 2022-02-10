package com.jayant.pocketlibrary.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jayant.pocketlibrary.R;
import com.jayant.pocketlibrary.ebooks.SubjectActivity;
import com.jayant.pocketlibrary.quoteApi.ApiInterface;
import com.jayant.pocketlibrary.quoteApi.QuoteData;
import com.jayant.pocketlibrary.quoteApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView textSem1, textSem2, textSem3, textSem4, textSem5, textSem6, quoteText;

    ApiInterface apiInterface;
    List<QuoteData> list;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textSem1 = view.findViewById(R.id.text_sem_1);
        textSem2 = view.findViewById(R.id.text_sem_2);
        textSem3 = view.findViewById(R.id.text_sem_3);
        textSem4 = view.findViewById(R.id.text_sem_4);
        textSem5 = view.findViewById(R.id.text_sem_5);
        textSem6 = view.findViewById(R.id.text_sem_6);

        quoteText = view.findViewById(R.id.show_quote);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>();


        apiInterface.getQuote().enqueue(new Callback<List<QuoteData>>() {
            @Override
            public void onResponse(Call<List<QuoteData>> call, Response<List<QuoteData>> response) {

                if (response.body().size() > 0) {
                    list.addAll(response.body());

                    Random rand = new Random();
                    int i = rand.nextInt(list.size()-1);
                    String quote = list.get(i).getText();

                    quoteText.setText(quote);

                }
            }

            @Override
            public void onFailure(Call<List<QuoteData>> call, Throwable t) {

            }
        });




        SharedPreferences preferences = view.getContext().getSharedPreferences("app_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        textSem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "First sem");
                editor.putString("sem_name", "First sem");
                editor.apply();
                startActivity(intent);
            }
        });

        textSem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "Second sem");
                editor.putString("sem_name", "Second sem");
                editor.apply();
                startActivity(intent);
            }
        });

        textSem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "Third sem");
                editor.putString("sem_name", "Third sem");
                editor.apply();
                startActivity(intent);
            }
        });

        textSem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "Fourth sem");
                editor.putString("sem_name", "Fourth sem");
                editor.apply();
                startActivity(intent);
            }
        });

        textSem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "Fifth sem");
                editor.putString("sem_name", "Fifth sem");
                editor.apply();
                startActivity(intent);
            }
        });

        textSem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SubjectActivity.class);
//                intent.putExtra("sem_name", "Sixth sem");
                editor.putString("sem_name", "Sixth sem");
                editor.apply();
                startActivity(intent);
            }
        });


        return view;
    }
}