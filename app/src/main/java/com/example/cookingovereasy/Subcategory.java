package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;

public class Subcategory extends AppCompatActivity {

    TextView categoryNameHeader;
    ImageView backToCategories;
    RecyclerView categoryItems;
    ArrayList<SavedRecipe> recipes;
    SavedRecipesAdapter adapter;
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

        categoryItems.setLayoutManager(new LinearLayoutManager(Subcategory.this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new SavedRecipesAdapter(Subcategory.this, recipes, recipeClickListener);
        categoryItems.setAdapter(adapter);
        categoryItems.setHasFixedSize(true);

    }

    private void getViews() {
        categoryNameHeader = findViewById(R.id.categoryNameHeader);
        backToCategories = findViewById(R.id.backToCategories);
        categoryItems = findViewById(R.id.categoryItems);
    }

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(Subcategory.this, SavedRecipeDetails.class)
                    .putExtra("id", id));
        }
    };
}