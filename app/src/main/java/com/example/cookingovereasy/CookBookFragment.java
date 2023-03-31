package com.example.cookingovereasy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class CookBookFragment extends Fragment {

    ImageButton myCookBook; //declaring buttons
    Button myCategories;
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



        myCategories.setOnClickListener(new View.OnClickListener() {  //adding a response when the new category button is clicked
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Create a Category!", Toast.LENGTH_SHORT).show();
            }
        });



//        public void onCreateCategoryButtonClicked(View view) {
//         Intent intent = new Intent(getActivity(), CreateFolder.class);
//           startActivity(intent);
//        }
//

        return view;
    }
}



