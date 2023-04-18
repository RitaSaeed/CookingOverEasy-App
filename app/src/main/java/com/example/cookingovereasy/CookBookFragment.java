package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class CookBookFragment extends Fragment {

    ImageButton myCookBook; //declaring buttons
    Button myCategories;

    AlertDialog dialog;

    ArrayList<Category> createdCategories;
    EditText nameCategory;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cook_book, container, false);  // Inflate the layout for this fragment


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Build recycler view:
        recyclerView = view.findViewById(R.id.recycler_cookbook_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CategoryAdapter(createdCategories, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        myCookBook = view.findViewById(R.id.imageButton);  //referencing cookbook icon button
        myCategories = view.findViewById(R.id.addCategory); //referencing 'new categories' button

        loadData();

        myCookBook.setOnClickListener(new View.OnClickListener() {  //adding a response when cookbook button is clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Welcome to your CookBook!", Toast.LENGTH_SHORT).show();
            }
        });


        createDialog();
        myCategories.setOnClickListener(new View.OnClickListener() {  //adding a response when the new category button is clicked
            @Override
            public void onClick(View view) {
                dialog.show();
                //Toast.makeText(getActivity().getApplicationContext(), "Create a Category!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        nameCategory = view.findViewById(R.id.editName);
        builder.setView(view);
        builder.setTitle("Create New Category")
                .setPositiveButton("Create Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCategory(nameCategory.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog = builder.create();
    }

    private void addCategory(String categoryName) {
        View view = getLayoutInflater().inflate(R.layout.cookbook_category, null);

        TextView name = view.findViewById(R.id.categoryName);
        //Button addRecipes = view.findViewById(R.id.addRecipes);

        name.setText(categoryName);

//        addRecipes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Add Recipes!", Toast.LENGTH_SHORT).show();
//            }
//        });

        //layout.addView(view);
        adapter.createdCategories.add(new Category(name.getText().toString()));
        adapter.notifyItemInserted(adapter.createdCategories.size());
        adapter.notifyDataSetChanged();
        saveData();
    }

    private void saveData(){
        SharedPreferences sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.createdCategories);
        editor.putString("Created Categories", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("Created Categories", null);
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        adapter.createdCategories = gson.fromJson(json, type);

        if(adapter.createdCategories == null) {
            adapter.createdCategories = new ArrayList<>();
        }

    }


}



