package com.example.cookingovereasy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientAdapter extends RecyclerView.Adapter<SearchIngredientAdapter.ViewHolder>{

    Context context;
    private List<SearchIngredient> searchIngredients;
    AddToGroceryList listener;

    public SearchIngredientAdapter(Context context, ArrayList<SearchIngredient> ingredientsList, AddToGroceryList listener) {
        this.context = context;
        this.searchIngredients = ingredientsList;
        this.listener = listener;
//        try {
//            adapterCallback = ((AdapterCallback) context);
//        } catch (ClassCastException e) {
//            throw new ClassCastException();
//        }
    }

    public void setFilteredList(List<SearchIngredient> filteredList) {
        this.searchIngredients = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_ingredient_search, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchIngredientAdapter.ViewHolder holder, int position) {
        SearchIngredient ingredient = searchIngredients.get(position);
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addIngredient(ingredient.getIngredientName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchIngredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientName;
        private final ImageView addButton;
        AddToGroceryList listener;
        public ViewHolder(@NonNull View itemView, AddToGroceryList listener) {
            super(itemView);
            this.listener = listener;
            ingredientName = itemView.findViewById(R.id.textView_title);
            addButton = itemView.findViewById(R.id.addToGroceryListIcon);
        }
    }

    public static interface AddToGroceryList {
        void addIngredient(String newIngredient);
    }

}
