package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.Recipe;
import com.example.cookingovereasy.Models.RecipeDetailsResponse;
import com.example.cookingovereasy.listeners.RecipeDetailsListener;
import com.squareup.picasso.Picasso;

public class RecipeDetails extends AppCompatActivity {
    int id;
    TextView textView_recipe_name, textView_recipe_source, textView_recipe_summary;
    ImageView imageView_recipe_image;
    RecyclerView recycler_recipe_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    RecipeIngredientsAdapter recipeIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        // capture id from sent intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();

    }

    private void findViews() {
        textView_recipe_name = findViewById(R.id.textView_recipe_name);
        textView_recipe_source = findViewById(R.id.textView_recipe_source);
        textView_recipe_summary = findViewById(R.id.textView_recipe_summary);
        imageView_recipe_image = findViewById(R.id.imageView_recipe_image);
        recycler_recipe_ingredients = findViewById(R.id.recycler_recipe_ingredients);
    }

    private final RecipeDetailsListener listener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_recipe_name.setText(response.title);
            textView_recipe_source.setText(response.sourceName);
            textView_recipe_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_recipe_image);

            recycler_recipe_ingredients.setHasFixedSize(true);
            recycler_recipe_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            recipeIngredientsAdapter = new RecipeIngredientsAdapter(RecipeDetails.this, response.extendedIngredients);
            recycler_recipe_ingredients.setAdapter(recipeIngredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}