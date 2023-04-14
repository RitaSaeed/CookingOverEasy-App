package com.example.cookingovereasy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CookBookFragment extends Fragment {

    ImageButton myCookBook; //declaring buttons
    Button myCategories;

    AlertDialog dialog;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cook_book, container, false);  // Inflate the layout for this fragment
        myCookBook = view.findViewById(R.id.imageButton);  //referencing cookbook icon button
        myCategories = view.findViewById(R.id.addCategory); //referencing 'new categories' button

        myCookBook.setOnClickListener(new View.OnClickListener() {  //adding a response when cookbook button is clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Welcome to your CookBook!", Toast.LENGTH_SHORT).show();
            }
        });

        layout = view.findViewById(R.id.container);
        createDialog();
        myCategories.setOnClickListener(new View.OnClickListener() {  //adding a response when the new category button is clicked
            @Override
            public void onClick(View view) {
                dialog.show();
                //Toast.makeText(getActivity().getApplicationContext(), "Create a Category!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        EditText nameCategory = view.findViewById(R.id.editName);
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
        Button addRecipes = view.findViewById(R.id.addRecipes);

        name.setText(categoryName);

        addRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Add Recipes!", Toast.LENGTH_SHORT).show();
            }
        });

        layout.addView(view);
    }
}



