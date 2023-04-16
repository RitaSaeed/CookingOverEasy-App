package com.example.cookingovereasy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookingovereasy.Models.RandomRecipeApiResponse;
import com.example.cookingovereasy.Models.Recipe;
import com.example.cookingovereasy.listeners.RandomRecipeResponseListener;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality for the search fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private List<Recipe> recipeList;
    private String[] foodHeading;
    private int[] imageResourceID;
    private SearchView searchView;
    private SearchAdapter adapter;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    ProgressDialog dialog;
    View listenerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();

    /**
     * Creates an inflated layout of the search fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return inflatedLayout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_search, container, false);
    }

    /**
     * Pulls the view from onCreateView and does the bulk of the logic with it.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // og sprint one stuff
//        dataInitialize(); // adds test data to the recycler view

        // new stuff
        dialog = new ProgressDialog(getActivity()); // might need to be getActivity
        dialog.setTitle("Loading...");

        manager = new RequestManager(getActivity()); // might need to be getActivity
        listenerView = view;
//        manager.getRandomRecipes(randomRecipeResponseListener);
//        dialog.show();

        // end new stuff

        spinner = view.findViewById(R.id.spinnerSearch);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.tags,
                R.layout.search_spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.search_spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        // og sprint one stuff
        searchView = view.findViewById(R.id.searchViewSearch);
        searchView.clearFocus();

        /**
         * QueryTextListener for the search bar.
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
                return false; // change to true when text is filtered and submit is disabled
            }
        });

            // og sprint one stuff
//        recyclerView = view.findViewById(R.id.recycler_view_search);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setHasFixedSize(true);
//        adapter = new SearchAdapter(getContext(), foodArrayList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = listenerView.findViewById(R.id.recycler_view_search);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            randomRecipeAdapter = new RandomRecipeAdapter(getActivity(), response.recipes, recipeClickListener);
            recipeList = response.recipes;
            recyclerView.setAdapter(randomRecipeAdapter);
            randomRecipeAdapter.notifyDataSetChanged();

            // following 2 lines just close the keyboard after hitting search
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Filters the food arrayList based on the current text in the search field.
     * @param text
     */
    private void filterList(String text) {
//        List<Food> filteredList = new ArrayList<>();
//        for (Food food : foodArrayList) {
//            if (food.getHeading().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(food);
//            }
//        }

        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "Recipe not found", Toast.LENGTH_SHORT).show();
        } else {
            randomRecipeAdapter.setFilteredList(filteredList);
        }
    }

    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), RecipeDetails.class)
                    .putExtra("id", id));
        }
    };

    /**
     * Adds initial test data to the recipe search recyclerview. This will eventually be
     * completely refactored to host the contents of the data returned from the API query.
     */
    private void dataInitialize() {

        foodArrayList = new ArrayList<>();
        foodHeading = new String[] {
                getString(R.string.head_1),
                getString(R.string.head_2),
                getString(R.string.head_3),
                getString(R.string.head_4),
                getString(R.string.head_5),
                getString(R.string.head_6),
                getString(R.string.head_7),
                getString(R.string.head_8),
                getString(R.string.head_9),
                getString(R.string.head_10),
                getString(R.string.head_11),
                getString(R.string.head_12)

        };

        imageResourceID = new int[]{
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e,
                R.drawable.f,
                R.drawable.g,
                R.drawable.h,
                R.drawable.i,
                R.drawable.j,
                R.drawable.k,
                R.drawable.l

        };

        for (int i = 0; i < foodHeading.length; i++) {
            Food food = new Food(foodHeading[i], imageResourceID[i]);
            foodArrayList.add(food);
        }
    }
}