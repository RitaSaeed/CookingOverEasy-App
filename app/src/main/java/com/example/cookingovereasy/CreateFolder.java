package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class CreateFolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        CookBookFragment existingFragment = new CookBookFragment();

       // FrameLayout container = findViewById(R.id.container_view);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Begin a FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Create an instance of the existing fragment


        // Add the existing fragment to the container view
       fragmentTransaction.add(R.id.container_view, existingFragment);

        // Commit the transaction
        fragmentTransaction.commit();

    }
}

