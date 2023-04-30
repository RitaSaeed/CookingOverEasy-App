package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingovereasy.Models.SavedRecipe;

import java.util.ArrayList;

public class Subcategory extends AppCompatActivity {

    TextView categoryNameHeader;
    ImageView backToCategories;
    RecyclerView categoryItems;
    ArrayList<SavedRecipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
        getViews();
        recipes = (ArrayList<SavedRecipe>) getIntent().getSerializableExtra("categoryItems");
        categoryNameHeader.setText(getIntent().getStringExtra("category"));

        backToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getViews() {
        categoryNameHeader = findViewById(R.id.categoryNameHeader);
        backToCategories = findViewById(R.id.backToCategories);
        categoryItems = findViewById(R.id.categoryItems);
    }
}