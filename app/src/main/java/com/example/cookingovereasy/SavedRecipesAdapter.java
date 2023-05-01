package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesViewHolder>{

    Context context;
    ArrayList<SavedRecipe> list;
    RecipeClickListener listener;

    public SavedRecipesAdapter(Context context, ArrayList<SavedRecipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedRecipesViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cookbook_recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedRecipesViewHolder holder, int position) {
        holder.savedRecipeName.setText(list.get(position).getName());
        holder.cookbook_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size(); // list is null here
    }
}

class SavedRecipesViewHolder extends RecyclerView.ViewHolder {

    TextView savedRecipeName;
    CardView cookbook_list_container;

    public SavedRecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        savedRecipeName = itemView.findViewById(R.id.savedRecipeName);
        cookbook_list_container = itemView.findViewById(R.id.cookbook_list_container);
    }
}
