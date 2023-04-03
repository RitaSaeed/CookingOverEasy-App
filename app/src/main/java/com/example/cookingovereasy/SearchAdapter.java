package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    List<Food> foodArrayList;

    public SearchAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    public void setFilteredList(List<Food> filteredList) {
        this.foodArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

        Food food = foodArrayList.get(position);
        holder.tvHeading.setText(food.heading);
        holder.titleImage.setImageResource(food.titleImage);

        //holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.cardView.getContext(),
        //        R.anim.anim_search_one));

    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView titleImage;
        TextView tvHeading;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.recycler_view_search);
            titleImage = itemView.findViewById(R.id.title_image);
            tvHeading = itemView.findViewById(R.id.tvHeading);
        }
    }
}
