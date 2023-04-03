package com.example.cookingovereasy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality for the search fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private String[] foodHeading;
    private int[] imageResourceID;
    private SearchView searchView;
    private SearchAdapter adapter;

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

        dataInitialize(); // adds test data to the recycler view

        searchView = view.findViewById(R.id.searchViewSearch);
        searchView.clearFocus();

        /**
         * QueryTextListener for the search bar.
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new SearchAdapter(getContext(), foodArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Filters the food arrayList based on the current text in the search field.
     * @param text
     */
    private void filterList(String text) {
        List<Food> filteredList = new ArrayList<>();
        for (Food food : foodArrayList) {
            if (food.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(food);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "Recipe not found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

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