package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.IngredientResponse;
import com.example.cookingovereasy.Models.Result;
import com.example.cookingovereasy.listeners.IngredientResponseListener;

import java.util.ArrayList;
import java.util.List;

public class AddToGroceryList extends AppCompatActivity {

    private ImageView back;
    private ProgressDialog dialog;
    private RequestManager manager;
    private RecyclerView recyclerView;
    private View listenerView;
    private CardView cardView;
    private SearchView searchView;
    private List<Result> ingredientList;
    IngredientSearchAdapter ingredientSearchAdapter;
    List<String> tags = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_grocery_list);

        back = findViewById(R.id.backArrow);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager = new RequestManager(this);

        searchView = findViewById(R.id.searchViewSearchIngredient);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                tags.clear();
//                tags.add(query);
                manager.getIngredients(ingredientResponseListener, true, query);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //cardView = findViewById(R.id.ingredient_list_container);

    }

    private final IngredientResponseListener ingredientResponseListener = new IngredientResponseListener() {
        @Override
        public void didFetch(IngredientResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_view_searchIngredient);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(AddToGroceryList.this, 1));
            ingredientSearchAdapter = new IngredientSearchAdapter(AddToGroceryList.this, response.results);
            ingredientList = response.results;
            recyclerView.setAdapter(ingredientSearchAdapter);
            ingredientSearchAdapter.notifyDataSetChanged();

        }

        @Override
        public void didError(String message) {
            Toast.makeText(AddToGroceryList.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}