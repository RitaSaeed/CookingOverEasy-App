package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.SavedRecipe;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesViewHolder>{

    Context context;
    ArrayList<SavedRecipe> list;

    public SavedRecipesAdapter(Context context, ArrayList<SavedRecipe> list) {
        this.context = context;
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size(); // list is null here
    }
}

class SavedRecipesViewHolder extends RecyclerView.ViewHolder {

    TextView savedRecipeName;

    public SavedRecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        savedRecipeName = itemView.findViewById(R.id.savedRecipeName);
    }
}
