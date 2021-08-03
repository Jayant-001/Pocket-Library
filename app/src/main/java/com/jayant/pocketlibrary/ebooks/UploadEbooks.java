package com.jayant.pocketlibrary.ebooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jayant.pocketlibrary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UploadEbooks extends AppCompatActivity {

    private TextView selectPdf;
    private EditText pdfTitle, pdfDesc;
    private Spinner spSem, spSub, spLang;
    private Button uploadPdf;

    private String title, desc, sem, sub, lang;

    private ArrayList<String> listSem, firstSemSub, secondSemSub, thirdSemSub, fourthSemSub, fifthSemSub, sixthSemSub, listLang, defaultSub;
    private ArrayAdapter<String> adapter_sem, adapter_sub, adapter_lang;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebooks);

        selectPdf = findViewById(R.id.pdf_select_text);

        pdfTitle = findViewById(R.id.input_pdf_title);
        pdfDesc = findViewById(R.id.input_pdf_desc);

        spSem = findViewById(R.id.sp_sem);
        spSub = findViewById(R.id.sp_subject);
        spLang = findViewById(R.id.sp_language);

        uploadPdf = findViewById(R.id.upload_pdf_btn);
        uploadPdf.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("cse_books");
        storageReference = FirebaseStorage.getInstance().getReference("cse_books");

        // Semester list----------------------------------------------------------------------------
        listSem = new ArrayList<>();
        listSem.add("Select semester");
        listSem.add("First sem");
        listSem.add("Second sem");
        listSem.add("Third sem");
        listSem.add("Fourth sem");
        listSem.add("Fifth sem");
        listSem.add("Sixth sem");

        defaultSub = new ArrayList<>();
        defaultSub.add("Select sem first");

        // First Semester list ---------------------------------------------------------------------
        firstSemSub = new ArrayList<>();
        firstSemSub.add("Select subject");
        firstSemSub.add("Math-I");
        firstSemSub.add("Communication-I");
        firstSemSub.add("Physics-I");
        firstSemSub.add("Chemistry");
        firstSemSub.add("F C I T");
        firstSemSub.add("Technical Drawing");
        firstSemSub.add("Workshop Practice");

        // Second Sem list -------------------------------------------------------------------------
        secondSemSub = new ArrayList<>();
        secondSemSub.add("Select subject");
        secondSemSub.add("Math-II");
        secondSemSub.add("Physics-II");
        secondSemSub.add("B E E E");
        secondSemSub.add("Multimedia & Animation");
        secondSemSub.add("Programming using C");
        secondSemSub.add("O A T");

        // Third sem list --------------------------------------------------------------------------
        thirdSemSub = new ArrayList<>();
        thirdSemSub.add("Select subject");
        thirdSemSub.add("Math-III");
        thirdSemSub.add("I & W T");
        thirdSemSub.add("EVS");
        thirdSemSub.add("D C C N");
        thirdSemSub.add("DS using C");
        thirdSemSub.add("Digital Electronic");

        // Fourth sem list -------------------------------------------------------------------------
        fourthSemSub = new ArrayList<>();
        fourthSemSub.add("Select subject");
        fourthSemSub.add("Communication-II");
        fourthSemSub.add("D B M S");
        fourthSemSub.add("OOP using Java");
        fourthSemSub.add("O S");
        fourthSemSub.add("E-com");
        fourthSemSub.add("Energy Conservation");
        fourthSemSub.add("U H V");

        // Fifth sem list --------------------------------------------------------------------------
        fifthSemSub = new ArrayList<>();
        fifthSemSub.add("Select subject");
        fifthSemSub.add("Software Engineering");
        fifthSemSub.add("Web Dev using PHP");
        fifthSemSub.add("Python");
        fifthSemSub.add("C A H M");
        fifthSemSub.add("I O T");

        // Sixth sem list --------------------------------------------------------------------------
        sixthSemSub = new ArrayList<>();
        sixthSemSub.add("Select subject");
        sixthSemSub.add("Android Development");
        sixthSemSub.add("Cloud Computing");
        sixthSemSub.add("I M E D");
        sixthSemSub.add("Advance Java");
        sixthSemSub.add("M L & D S");
        sixthSemSub.add(".NET");

        listLang = new ArrayList<>();
        listLang.add("Select Language");
        listLang.add("English");
        listLang.add("Hindi");

        adapter_sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listSem);
        spSem.setAdapter(adapter_sem);

        adapter_lang = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listLang);
        spLang.setAdapter(adapter_lang);

        spSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, defaultSub);
                    spSub.setAdapter(adapter_sub);
                }

                if (position == 1) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, firstSemSub);
                    spSub.setAdapter(adapter_sub);
                }
                if (position == 2) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, secondSemSub);
                    spSub.setAdapter(adapter_sub);
                }
                if (position == 3) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, thirdSemSub);
                    spSub.setAdapter(adapter_sub);
                }
                if (position == 4) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, fourthSemSub);
                    spSub.setAdapter(adapter_sub);
                }
                if (position == 5) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, fifthSemSub);
                    spSub.setAdapter(adapter_sub);
                }
                if (position == 6) {
                    adapter_sub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sixthSemSub);
                    spSub.setAdapter(adapter_sub);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdfData();
            }
        });

    }

    public void selectPdfData() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SELECT PDF FILE"), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uploadPdf.setEnabled(true);
            String fileName = getFileName(data.getData());
            selectPdf.setTextSize(18);
            selectPdf.setText(fileName);

            uploadPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(pdfTitle.getText().toString().equals("")) {
                        pdfTitle.setError("Title is required");
                        Toast.makeText(UploadEbooks.this, "Title is required", Toast.LENGTH_SHORT).show();
                        pdfTitle.requestFocus();
                        return;
                    }

                    if(pdfDesc.getText().toString().equals("")) {
                        pdfDesc.setError("This field is required");
                        Toast.makeText(UploadEbooks.this, "Description is required", Toast.LENGTH_SHORT).show();
                        pdfDesc.requestFocus();
                        return;
                    }

                    TextView sem_text = ((TextView)spSem.getSelectedView());
                    TextView sub_text = ((TextView)spSub.getSelectedView());
                    TextView lang_text = ((TextView)spLang.getSelectedView());

                    if(sem_text.getText().toString().equals("Select semester")) {
                        sem_text.setError("Plz select semester");
                        Toast.makeText(UploadEbooks.this, "Please select semester", Toast.LENGTH_SHORT).show();
                        spSem.requestFocus();
                        return;
                    }

                    if (sub_text.getText().toString().equals("Select subject")) {
                        sub_text.setError("Plz select subject");
                        Toast.makeText(UploadEbooks.this, "Please select subject", Toast.LENGTH_SHORT).show();
                        spSub.requestFocus();
                        return;
                    }

                    if (lang_text.getText().toString().equals("Select Language")) {
                        lang_text.setError("Plz select language");
                        Toast.makeText(UploadEbooks.this, "Please select language", Toast.LENGTH_SHORT).show();
                        spLang.requestFocus();
                        return;
                    }


                    title = pdfTitle.getText().toString();
                    desc = pdfDesc.getText().toString();
                    sem = sem_text.getText().toString();
                    sub = sub_text.getText().toString();
                    lang = lang_text.getText().toString();


                    Log.d("jayant", title);
                    Log.d("jayant", desc);
                    Log.d("jayant", sem);
                    Log.d("jayant", sub);
                    Log.d("jayant", lang);


                    uploadPdfToFirebase(data.getData());

                }
            });

        }
    }



    private void uploadPdfToFirebase(Uri data) {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File is uploading...");
        pd.setCancelable(false);
        pd.show();

        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calendarDate.getTime());

        Calendar calendarTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calendarTime.getTime());


        StorageReference reference = storageReference.child(pdfTitle.getText().toString() + date + "-" + time);
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseDatabase.getInstance().getReference().child("adminUser").child(fAuth.getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                String name = snapshot.child("name").getValue(String.class);

                                PdfData pdfData = new PdfData(title, desc, sem, sub, lang, uri.toString(), date, time);

                                databaseReference.child(sem).child(sub).push().setValue(pdfData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(UploadEbooks.this, "Ebook uploaded successfully", Toast.LENGTH_SHORT).show();
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Log.d("jayant", "database error: " + e.getMessage());
                                        Toast.makeText(UploadEbooks.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                pd.dismiss();
                                Log.d("jayant", error.toString());
                                Toast.makeText(UploadEbooks.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                pd.setMessage("Uploading Progress..." + (int) progress + "%");
            }
        });



    }



    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }

}