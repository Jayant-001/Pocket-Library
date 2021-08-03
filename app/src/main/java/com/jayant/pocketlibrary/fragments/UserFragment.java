package com.jayant.pocketlibrary.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jayant.pocketlibrary.FormActivity;
import com.jayant.pocketlibrary.MainActivity;
import com.jayant.pocketlibrary.R;

import static android.content.Context.MODE_PRIVATE;


public class UserFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View userLogoutBtn;
    private FirebaseAuth firebaseAuth;

    private TextView userName, userEnroll, userSem, userCollege, topUserName;
    private SharedPreferences sharedPreferences;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userLogoutBtn = view.findViewById(R.id.user_logout_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        topUserName = view.findViewById(R.id.top_user_name);
        userName = view.findViewById(R.id.user_name);
        userEnroll = view.findViewById(R.id.user_enroll);
        userSem = view.findViewById(R.id.user_sem);
        userCollege = view.findViewById(R.id.user_college);





        sharedPreferences = this.getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", "none");
        String enroll = sharedPreferences.getString("user_enroll", "none");
        String sem = sharedPreferences.getString("selected_sem", "none");
        String college = sharedPreferences.getString("user_college", "none");



        userLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                
                sharedPreferences.edit().clear().apply();

                getActivity().finish();
                startActivity(new Intent(getActivity(), FormActivity.class));

            }
        });


        topUserName.setText(name);
        userName.setText(name);
        userEnroll.setText(enroll);
        userSem.setText(sem);
        userCollege.setText(college);


        return view;
    }
}