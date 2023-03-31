package com.example.cookingovereasy;

import android.content.Context;
import android.graphics.Color;
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
import java.util.Collections;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{

    Context context;
    ArrayList<Ingredient> ingredientArrayList;

    public IngredientAdapter(ArrayList<Ingredient> ingredientArrayList) {
        //this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View v = LayoutInflater.from(context).inflate(R.layout.grocery_item, parent, false);
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);

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

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(ingredientArrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(ingredientArrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        protected CheckBox checkBox;
        View rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rowView = itemView;
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxIngredient);
            ingredientName = itemView.findViewById(R.id.ingredientName);
        }
    }
}
