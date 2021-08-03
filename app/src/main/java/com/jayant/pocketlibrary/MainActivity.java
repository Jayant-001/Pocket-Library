package com.jayant.pocketlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.jayant.pocketlibrary.adminPanel.LoginActivity;
import com.jayant.pocketlibrary.ebooks.UploadEbooks;
import com.jayant.pocketlibrary.fragments.HomeFragment;
import com.jayant.pocketlibrary.fragments.UserFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fbBtn;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbBtn = findViewById(R.id.fab_icon);
        fAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), UploadEbooks.class));

                }
                else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch(item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new UserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;

                }
            };
}