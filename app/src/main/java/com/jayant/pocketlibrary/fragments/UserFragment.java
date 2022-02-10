package com.jayant.pocketlibrary.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jayant.pocketlibrary.DeveloperActivity;
import com.jayant.pocketlibrary.FormActivity;
import com.jayant.pocketlibrary.R;
import com.jayant.pocketlibrary.adminPanel.LoginActivity;
import com.jayant.pocketlibrary.adminPanel.SignupActivity;
import com.jayant.pocketlibrary.user.AppInfo;

import static android.content.Context.MODE_PRIVATE;


public class UserFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout devInfo, addAdminLayout, appInfo;
    View userLogoutBtn;
    private FirebaseAuth firebaseAuth;

    private TextView userName, userEmail, userBranch, userCollege, topUserName, userAdminLogin;
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

        devInfo = view.findViewById(R.id.dev_info);
        addAdminLayout = view.findViewById(R.id.add_admin_layout);
        appInfo = view.findViewById(R.id.app_info_layout);

        topUserName = view.findViewById(R.id.top_user_name);
        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        userBranch = view.findViewById(R.id.user_branch);
        userCollege = view.findViewById(R.id.user_college);
        userAdminLogin = view.findViewById(R.id.user_admin_login);

        if(firebaseAuth.getCurrentUser() != null) {
            userAdminLogin.setText("Add Admin");
        }
        else {
            userAdminLogin.setText("Admin Login");
        }

        sharedPreferences = this.getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", "none");
        String email = sharedPreferences.getString("user_email", "none");
        String branch = sharedPreferences.getString("user_branch", "none");
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
        userEmail.setText(email);
        userBranch.setText(branch);
        userCollege.setText(college);

        addAdminLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(view.getContext(), SignupActivity.class));
                }
                else {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                }

            }
        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(view.getContext(), AppInfo.class));
            }
        });

        devInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), DeveloperActivity.class));

            }
        });

        return view;
    }
}