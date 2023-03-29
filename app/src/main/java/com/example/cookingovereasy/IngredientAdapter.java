package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder>{

    Context context;
    ArrayList<Ingredient> ingredientArrayList;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.grocery_item, parent, false);

        return new IngredientAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.MyViewHolder holder, int position) {

        Ingredient ingredient = ingredientArrayList.get(position);
        holder.ingredientName.setText(ingredient.name);

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
        }
    }
}
