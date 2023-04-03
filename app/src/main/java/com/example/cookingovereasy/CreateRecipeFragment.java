package com.example.cookingovereasy;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Provides functionality for the create recipe fragment, which will allow users to input a number
 * of attributes that represents recipe, which will then be stored inside of their CookBook. This
 * will be implemented after the API is connected to further determine what values are necessary
 */
public class CreateRecipeFragment extends Fragment {

    /**
     * Initial onCreateView method that is called when the fragment view is created.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }
}