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

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Food> foodArrayList;
    private String[] foodHeading;
    private int[] imageResourceID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.setAdapter(new RandomNumListAdapter(1234));

//        return view;
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        SearchAdapter adapter = new SearchAdapter(getContext(), foodArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

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