package com.jayant.pocketlibrary.ebooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private EditText pdfTitle, pdfDesc, inputContributorName;
    private Spinner spSem, spSub, spLang, spBranch;
    private Button uploadPdf;

    private String title, desc, sem, sub, lang, branch, selectedBranch, selectedSem, selectedSub, contributorName;

    private ArrayList<String> semList, firstSemSub, secondSemSub, thirdSemSub, fourthSemSub, fifthSemSub, sixthSemSub, listLang, defaultSem, defaultSub, branchList;
    private ArrayAdapter<String> adapterLang, branchAdapter, semAdapter, subAdapter;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebooks);

        selectPdf = findViewById(R.id.pdf_select_text);

        pdfTitle = findViewById(R.id.input_pdf_title);
        pdfDesc = findViewById(R.id.input_pdf_desc);
        inputContributorName = findViewById(R.id.input_contributor_name);

        spSem = findViewById(R.id.sp_sem);
        spSub = findViewById(R.id.sp_subject);
        spLang = findViewById(R.id.sp_language);
        spBranch = findViewById(R.id.sp_branch);

        uploadPdf = findViewById(R.id.upload_pdf_btn);
        uploadPdf.setEnabled(false);

        firstSemSub = new ArrayList<>();
        secondSemSub = new ArrayList<>();
        thirdSemSub = new ArrayList<>();
        fourthSemSub = new ArrayList<>();
        fifthSemSub = new ArrayList<>();
        sixthSemSub = new ArrayList<>();

        defaultSem = new ArrayList<>();
        defaultSem.add("Select branch first");

        defaultSub = new ArrayList<>();
        defaultSub.add("Select sem first");

        listLang = new ArrayList<>();
        listLang.add("Select Language");
        listLang.add("English");
        listLang.add("Hindi");

        branchList = new ArrayList<>();
        branchList.add("Select branch");
        branchList.add("C S E");
        branchList.add("I C");
        branchList.add("E C");
        branchList.add("I T");

        semList = new ArrayList<>();
        semList.add("Select semester");
        semList.add("First sem");
        semList.add("Second sem");
        semList.add("Third sem");
        semList.add("Fourth sem");
        semList.add("Fifth sem");
        semList.add("Sixth sem");

        branchAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, branchList);
        spBranch.setAdapter(branchAdapter);

        semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, defaultSem);
        spSem.setAdapter(semAdapter);

        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, defaultSub);
        spSub.setAdapter(subAdapter);


        spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    selectedBranch = "none";
                    semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, defaultSem);
                    spSem.setAdapter(semAdapter);
                }
                else if(position == 1) {
                    selectedBranch = "C S E";
                    semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, semList);
                    spSem.setAdapter(semAdapter);
                }
                else if(position == 2) {
                    selectedBranch = "I C";
                    semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, semList);
                    spSem.setAdapter(semAdapter);
                }
                else if(position == 3) {
                    selectedBranch = "E C";
                    semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, semList);
                    spSem.setAdapter(semAdapter);
                }
                else if(position == 4) {
                    selectedBranch = "I T";
                    semAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, semList);
                    spSem.setAdapter(semAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, defaultSub);
                    spSub.setAdapter(subAdapter);
                }
                else if (position == 1) {

                    if(selectedBranch.equals("C S E")) {
                        firstSemSub.add("Select subject");
                        firstSemSub.add("Math-I");
                        firstSemSub.add("Communication-I");
                        firstSemSub.add("Physics-I");
                        firstSemSub.add("Chemistry");
                        firstSemSub.add("F C I T");
                        firstSemSub.add("Technical Drawing");
                        firstSemSub.add("Workshop Practice");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, firstSemSub);
                        spSub.setAdapter(subAdapter);

                    }
                    else if(selectedBranch.equals("I C")) {
                        firstSemSub.add("Select subject");
                        firstSemSub.add("Communication-I");
                        firstSemSub.add("Math-I");
                        firstSemSub.add("Physics-I");
                        firstSemSub.add("Chemistry");
                        firstSemSub.add("Engineering Drawing");
                        firstSemSub.add("E M M");
                        firstSemSub.add("Workshop-I");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, firstSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        firstSemSub.add("Select subject");
                        firstSemSub.add("Math-I");
                        firstSemSub.add("Communication-I");
                        firstSemSub.add("Physics-I");
                        firstSemSub.add("Chemistry");
                        firstSemSub.add("F C I T");
                        firstSemSub.add("Technical Drawing");
                        firstSemSub.add("Workshop Practice");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, firstSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        firstSemSub.add("Select subject");
                        firstSemSub.add("Math-I");
                        firstSemSub.add("Communication-I");
                        firstSemSub.add("Physics-I");
                        firstSemSub.add("Chemistry");
                        firstSemSub.add("Engineering Drawing");
                        firstSemSub.add("E M M");
                        firstSemSub.add("Workshop-I");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, firstSemSub);
                        spSub.setAdapter(subAdapter);
                    }

                }
                else if(position == 2) {

                    if(selectedBranch.equals("C S E")) {
                        secondSemSub.add("Select subject");
                        secondSemSub.add("Math-II");
                        secondSemSub.add("Physics-II");
                        secondSemSub.add("B E E E");
                        secondSemSub.add("Multimedia & Animation");
                        secondSemSub.add("Programming using C");
                        secondSemSub.add("O A T");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, secondSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I C")) {
                        secondSemSub.add("Select subject");
                        secondSemSub.add("Math-II");
                        secondSemSub.add("Physics-II");
                        secondSemSub.add("B I T");
                        secondSemSub.add("EE-I");
                        secondSemSub.add("E C D");
                        secondSemSub.add("Workshop-II");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, secondSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        secondSemSub.add("Select subject");
                        secondSemSub.add("Math-II");
                        secondSemSub.add("Physics-II");
                        secondSemSub.add("B E E E");
                        secondSemSub.add("Multimedia & Animation");
                        secondSemSub.add("Programming using C");
                        secondSemSub.add("O A T");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, secondSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        secondSemSub.add("Select subject");
                        secondSemSub.add("Math-II");
                        secondSemSub.add("Physics-II");
                        secondSemSub.add("B I T");
                        secondSemSub.add("EE-I");
                        secondSemSub.add("E C D");
                        secondSemSub.add("Workshop-II");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, secondSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                }
                else if(position == 3) {

                    if(selectedBranch.equals("C S E")) {
                        thirdSemSub.add("Select subject");
                        thirdSemSub.add("Math-III");
                        thirdSemSub.add("I & W T");
                        thirdSemSub.add("EVS");
                        thirdSemSub.add("D C C N");
                        thirdSemSub.add("DS using C");
                        thirdSemSub.add("Digital Electronic");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, thirdSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I C")) {
                        thirdSemSub.add("Select subject");
                        thirdSemSub.add("Math-III");
                        thirdSemSub.add("EE-II");
                        thirdSemSub.add("E V S");
                        thirdSemSub.add("E D C");
                        thirdSemSub.add("Electronic Workshop");
                        thirdSemSub.add("Transducer & Application");
                        thirdSemSub.add("U H V");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, thirdSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        thirdSemSub.add("Select subject");
                        thirdSemSub.add("Math-III");
                        thirdSemSub.add("I & W T");
                        thirdSemSub.add("EVS");
                        thirdSemSub.add("D C C N");
                        thirdSemSub.add("DS using C");
                        thirdSemSub.add("C A H M");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, thirdSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        thirdSemSub.add("Select subject");
                        thirdSemSub.add("Math-III");
                        thirdSemSub.add("EE-II");
                        thirdSemSub.add("E V S");
                        thirdSemSub.add("E D C");
                        thirdSemSub.add("Electronic Workshop");
                        thirdSemSub.add("Digital Electronic");
                        thirdSemSub.add("U H V");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, thirdSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                }
                else if(position == 4) {

                    if(selectedBranch.equals("C S E")) {
                        fourthSemSub.add("Select subject");
                        fourthSemSub.add("Communication-II");
                        fourthSemSub.add("D B M S");
                        fourthSemSub.add("OOP using Java");
                        fourthSemSub.add("O S");
                        fourthSemSub.add("E-com");
                        fourthSemSub.add("Energy Conservation");
                        fourthSemSub.add("U H V");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fourthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I C")) {
                        fourthSemSub.add("Select subject");
                        fourthSemSub.add("Communication-II");
                        fourthSemSub.add("Principles of DE");
                        fourthSemSub.add("N F T L");
                        fourthSemSub.add("E I M");
                        fourthSemSub.add("Process Instrumentation");
                        fourthSemSub.add("Energy Conservation");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fourthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        fourthSemSub.add("Select subject");
                        fourthSemSub.add("Communication-II");
                        fourthSemSub.add("D B M S");
                        fourthSemSub.add("OOP using Java");
                        fourthSemSub.add("O S");
                        fourthSemSub.add("E-com");
                        fourthSemSub.add("Energy Conservation");
                        fourthSemSub.add("U H V");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fourthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        fourthSemSub.add("Select subject");
                        fourthSemSub.add("Communication-II");
                        fourthSemSub.add("I E T");
                        fourthSemSub.add("N F T L");
                        fourthSemSub.add("E I M");
                        fourthSemSub.add("P C E");
                        fourthSemSub.add("Energy Conservation");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fourthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                }
                else if(position == 5) {

                    if(selectedBranch.equals("C S E")) {
                        fifthSemSub.add("Select subject");
                        fifthSemSub.add("Software Engineering");
                        fifthSemSub.add("Web Dev using PHP");
                        fifthSemSub.add("Python");
                        fifthSemSub.add("C A H M");
                        fifthSemSub.add("I O T");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fifthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I C")) {
                        fifthSemSub.add("Select subject");
                        fifthSemSub.add("I M E D");
                        fifthSemSub.add("Microprocessor");
                        fifthSemSub.add("Industrial Control");
                        fifthSemSub.add("S T R D");
                        fifthSemSub.add("Programming in C");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fifthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        fifthSemSub.add("Select subject");
                        fifthSemSub.add("Software Engineering");
                        fifthSemSub.add("Web Dev using PHP");
                        fifthSemSub.add("Python");
                        fifthSemSub.add("IS & IT Laws");
                        fifthSemSub.add("I O T");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fifthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        fifthSemSub.add("Select subject");
                        fifthSemSub.add("I M E D");
                        fifthSemSub.add("Microprocessor");
                        fifthSemSub.add("O F C");
                        fifthSemSub.add("Consumer Electronic");
                        fifthSemSub.add("Programming in C");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, fifthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                }
                else if(position == 6) {

                    if(selectedBranch.equals("C S E")) {
                        sixthSemSub.add("Select subject");
                        sixthSemSub.add("Android Development");
                        sixthSemSub.add("Cloud Computing");
                        sixthSemSub.add("I M E D");
                        sixthSemSub.add("Advance Java");
                        sixthSemSub.add("M L & D S");
                        sixthSemSub.add("DOT NET");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, sixthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I C")) {
                        sixthSemSub.add("Select subject");
                        sixthSemSub.add("Process Control");
                        sixthSemSub.add("Microcontrollers");
                        sixthSemSub.add("B M I");
                        sixthSemSub.add("Specialised Instruments");
                        sixthSemSub.add("Robotics");
                        sixthSemSub.add("Neural Networks");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, sixthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("I T")) {
                        sixthSemSub.add("Select subject");
                        sixthSemSub.add("Android Development");
                        sixthSemSub.add("Cloud Computing");
                        sixthSemSub.add("I M E D");
                        sixthSemSub.add("Advance Java");
                        sixthSemSub.add("Big Data");
                        sixthSemSub.add("M L & D S");
                        sixthSemSub.add("Digital Image Processing");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, sixthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                    else if(selectedBranch.equals("E C")) {
                        sixthSemSub.add("Select subject");
                        sixthSemSub.add("Microwave");
                        sixthSemSub.add("Microcontrollers");
                        sixthSemSub.add("W M C S");
                        sixthSemSub.add("Control System");
                        sixthSemSub.add("Medical Electronics");
                        sixthSemSub.add("Computer Networks");

                        subAdapter = new ArrayAdapter<>(UploadEbooks.this, android.R.layout.simple_spinner_dropdown_item, sixthSemSub);
                        spSub.setAdapter(subAdapter);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        adapterLang = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listLang);
        spLang.setAdapter(adapterLang);


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

                    if(pdfTitle.getText().toString().length() > 25) {
                        pdfTitle.setError("Title must be less than 25 character");
                        Toast.makeText(UploadEbooks.this, "Title must be less than 25 character", Toast.LENGTH_SHORT).show();
                        pdfTitle.requestFocus();
                        return;

                    }

                    if(pdfDesc.getText().toString().equals("")) {
                        pdfDesc.setError("This field is required");
                        Toast.makeText(UploadEbooks.this, "Description is required", Toast.LENGTH_SHORT).show();
                        pdfDesc.requestFocus();
                        return;
                    }

                    if(inputContributorName.getText().toString().isEmpty()) {
                        inputContributorName.setError("This field is required");
                        Toast.makeText(UploadEbooks.this, "Please fill contributor name", Toast.LENGTH_SHORT).show();
                        inputContributorName.requestFocus();
                        return;
                    }

                    if(inputContributorName.getText().toString().length() > 15) {
                        inputContributorName.setError("No more than 15 character");
                        Toast.makeText(UploadEbooks.this, "Contributor name must be less than 15 character", Toast.LENGTH_SHORT).show();
                        inputContributorName.requestFocus();
                        return;
                    }

                    TextView sem_text = ((TextView)spSem.getSelectedView());
                    TextView sub_text = ((TextView)spSub.getSelectedView());
                    TextView lang_text = ((TextView)spLang.getSelectedView());
                    TextView branch_text = ((TextView)spBranch.getSelectedView());

                    if(branch_text.getText().toString().equals("Select branch")) {
                        branch_text.setError("Plz select branch");
                        Toast.makeText(UploadEbooks.this, "Please select branch", Toast.LENGTH_SHORT).show();
                        spBranch.requestFocus();
                        return;
                    }

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
                    branch = branch_text.getText().toString();
                    contributorName = inputContributorName.getText().toString();


                    Log.d("jayant", title);
                    Log.d("jayant", desc);
                    Log.d("jayant", branch);
                    Log.d("jayant", sem);
                    Log.d("jayant", sub);
                    Log.d("jayant", lang);
                    Log.d("jayant", contributorName);


                    String brch = "default_branch";
                    if(branch.equals("C S E")) {
                        brch = "cse_books";
                    }
                    else if(branch.equals("I T")) {
                        brch = "it_books";
                    }
                    else if(branch.equals("E C")) {
                        brch = "ec_books";
                    }
                    else if(branch.equals("I C")) {
                        brch = "ic_books";
                    }

//                    Log.d("jayant", "branch =============" + brch);

                    databaseReference = FirebaseDatabase.getInstance().getReference(brch);
                    storageReference = FirebaseStorage.getInstance().getReference(brch);


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

                                PdfData pdfData = new PdfData(title, desc, contributorName, sem, sub, lang, uri.toString(), date, time);

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