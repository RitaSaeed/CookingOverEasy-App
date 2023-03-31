package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
        //holder.checkBox.setText("Checkbox " + position);
        holder.checkBox.setChecked(ingredientArrayList.get(position).getSelected());
        holder.ingredientName.setText(ingredient.name);

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.checkBox.getTag();
                //Toast.makeText(context, ingredientArrayList.get(pos).getName() + " clicked!",
                //       Toast.LENGTH_SHORT).show();

                if (ingredientArrayList.get(pos).getSelected()) {
                    ingredientArrayList.get(pos).setSelected(false);
                } else {
                    ingredientArrayList.get(pos).setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        protected CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxIngredient);
            ingredientName = itemView.findViewById(R.id.ingredientName);
        }
    }
}
