package com.jayant.pocketlibrary.ebooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.jayant.pocketlibrary.R;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        String setSem = getIntent().getStringExtra("get_sem");
        String setSub = getIntent().getStringExtra("get_subject");

        Log.d("jayant", setSem);
        Log.d("jayant", setSub);

        pd = new ProgressDialog(this);
        pd.setTitle("loading...");
        pd.show();

        recyclerView = findViewById(R.id.book_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<PdfData> options = new FirebaseRecyclerOptions.Builder<PdfData>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("cse_books").child(setSem).child(setSub), PdfData.class).build();

        adapter = new BookAdapter(options);
        recyclerView.setAdapter(adapter);

        pd.dismiss();

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}