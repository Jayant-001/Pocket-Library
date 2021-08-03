package com.jayant.pocketlibrary.ebooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.jayant.pocketlibrary.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class BookAdapter extends FirebaseRecyclerAdapter<PdfData, BookAdapter.PdfViewHolder> {

    FirebaseAuth fAuth;

    public BookAdapter(@NonNull @NotNull FirebaseRecyclerOptions<PdfData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull PdfViewHolder holder, int position, @NonNull @NotNull PdfData model) {

        holder.showTitle.setText(model.getTitle());
        holder.showDesc.setText(model.getDesc());
        holder.showLang.setText(model.getLang());
        fAuth = FirebaseAuth.getInstance();

        holder.showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jayant", model.getUrl());
                Intent intent = new Intent(holder.delBtn.getContext(), ShowBook.class);
                intent.putExtra("bookName", model.getTitle());
                intent.putExtra("bookUrl", model.getUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.downloadBtn.getContext().startActivity(intent);
            }
        });


        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager downloadManager = (DownloadManager) holder.downloadBtn.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(model.getUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(holder.downloadBtn.getContext(), DIRECTORY_DOWNLOADS ,model.getTitle() + ".pdf" );

                downloadManager.enqueue(request);
                Toast.makeText(holder.downloadBtn.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });

        if(fAuth.getCurrentUser() != null) {
            holder.delBtn.setVisibility(View.VISIBLE);
        }

        // delete pdf ------------------------------------------------------------------------------

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are your sure to delete this Book?");
                builder.setCancelable(true);

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // delete book from storage
                        FirebaseStorage.getInstance().getReference().child("cse_books").child(model.getTitle() + model.getDate() + "-" + model.getTime())
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                // delete from database
                                FirebaseDatabase.getInstance().getReference().child("cse_books").child(model.getSem()).child(model.getSub())
                                        .child(getRef(position).getKey())
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                        Toast.makeText(holder.delBtn.getContext(), "Book deleted.", Toast.LENGTH_SHORT).show();
                                        ((Activity)holder.delBtn.getContext()).finish();
                                        holder.delBtn.getContext().startActivity(new Intent(holder.delBtn.getContext(), BooksActivity.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                                Toast.makeText(v.getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                                Log.d("jayant", e.toString());
                            }
                        });

                        notifyItemRemoved(position);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(dialog != null)
                    dialog.show();


            }
        });

    }


    @NonNull
    @NotNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new PdfViewHolder(view);
    }



    class PdfViewHolder extends RecyclerView.ViewHolder {

        private TextView showTitle, showDesc, showLang;
        private ImageView showBtn, delBtn, downloadBtn;
//        private FirebaseAuth fAuth;

        public PdfViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            showTitle = itemView.findViewById(R.id.show_book_title);
            showDesc = itemView.findViewById(R.id.show_book_desc);
            showLang = itemView.findViewById(R.id.show_book_lang);

            showBtn = itemView.findViewById(R.id.view_book_btn);
            delBtn = itemView.findViewById(R.id.delete_book_btn);
            downloadBtn = itemView.findViewById(R.id.download_book_btn);

//            fAuth = FirebaseAuth.getInstance();

        }
    }
}


