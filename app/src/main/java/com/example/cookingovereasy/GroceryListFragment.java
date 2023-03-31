package com.example.cookingovereasy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class GroceryListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Ingredient> ingredientArrayList;
    private String[] ingredientName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grocery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_grocery_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        dataInitialize();
    }

    private void dataInitialize() {

        ingredientArrayList = new ArrayList<>();
        ingredientName = new String[] {
                "Eggs", "Milk", "Chicken", "Steak", "Carrots", "Apples", "Broccoli", "Mushrooms",
                "Olive Oil", "Sugar", "Flour", "Paprika", "Italian Seasoning", "Bread Crumbs",
                "Salt", "Pasta", "Alfredo Sauce", "Orange", "Mango", "Avocado", "Banana", "Asaparagus"
        };


        for (int i = 0; i < ingredientName.length; i++) {
            Ingredient ingredient = new Ingredient(ingredientName[i]);
            ingredientArrayList.add(ingredient);
        }

        IngredientAdapter adapter = new IngredientAdapter(ingredientArrayList);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}