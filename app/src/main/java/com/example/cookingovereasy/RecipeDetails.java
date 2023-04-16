package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecipeDetails extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // capture id from sent intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));


    }
}