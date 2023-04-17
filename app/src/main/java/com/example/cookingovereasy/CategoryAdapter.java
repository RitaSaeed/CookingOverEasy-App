package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<Category> createdCategories;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public CategoryAdapter(ArrayList<Category> createdCategories, Context context) {
        this.context = context;
        this.createdCategories = createdCategories;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View v = LayoutInflater.from(context).inflate(R.layout.grocery_item, parent, false);
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cookbook_category, parent, false);

        return new CategoryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Category currentItem = createdCategories.get(position);
        holder.categoryName.setText(currentItem.getName());
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, currentItem.getName() + " clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return createdCategories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        View rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rowView = itemView;
            categoryName = itemView.findViewById(R.id.categoryName);

        }
    }
}
