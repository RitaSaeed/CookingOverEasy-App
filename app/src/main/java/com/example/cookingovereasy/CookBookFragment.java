package com.example.cookingovereasy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class CookBookFragment extends Fragment {


    ImageButton myCookBook;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cook_book, container, false);
        myCookBook = view.findViewById(R.id.imageButton);



     //   MyCookBook = findViewById(R.id.imageButton);
       myCookBook.setOnClickListener(new View.OnClickListener() {
         @Override
       public void onClick(View view) {
             Toast.makeText(getActivity().getApplicationContext(), "Welcome to your CookBook!", Toast.LENGTH_SHORT).show();
         }

         // }
        });


        // Inflate the layout for this fragment
        return view;

    }
  // @Override
   // public void onViewCreate(@NonNull View view, @Nullable Bundle savedInstanceState){
    //    super.onViewCreated(view, savedInstanceState);

   }


