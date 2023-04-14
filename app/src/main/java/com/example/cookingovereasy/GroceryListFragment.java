package com.example.cookingovereasy;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.FileReader;

public class GroceryListFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<String> ingredients;

    ImageView remove;
    private String[] ingredientName;


    private ImageView add;

    Activity context;
    IngredientAdapter adapter;


    /**
     * Creates the fragment that can be interacted with. Views created from onCreateView
     * are inflated here so the user can interact with the application.
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
        ingredientArrayList = new ArrayList<>();
//        ingredientArrayList.add(new Ingredient(getArguments().getString("newIngredient")));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grocery_list, container, false);
    }

    /**
     * Creates the view for the fragment. Constructs the design that will be generated with the
     * onCreateView method.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ingredientArrayList.add(new Ingredient(getArguments().getString("newIngredient")));
        add = view.findViewById(R.id.addIcon);

        ActivityResultLauncher<Intent> startForIngredient = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == -1) {
                    if (result.getData() != null) {
                        ingredientArrayList.add(new Ingredient(getArguments().getString("newIngredient")));
                        Toast.makeText(context, "current ingredient: " + getArguments().getString("newIngredient"), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddToGroceryList.class);
//                startForIngredient.launch(intent);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_grocery_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientAdapter(ingredientArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //dataInitialize();

        remove = view.findViewById(R.id.imageViewRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p = new PopupMenu(getActivity(), view);
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.rmSelectItems:
                                ArrayList<Ingredient> selectedItems = new ArrayList<>();
                                for(int i = 0; i < adapter.ingredientArrayList.size(); i++){
                                    if(adapter.ingredientArrayList.get(i).getSelected()){
                                        selectedItems.add(adapter.ingredientArrayList.get(i));
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                int index = 0;
                                for(Ingredient i : selectedItems){
                                    adapter.ingredientArrayList.remove(i);
                                    adapter.notifyItemRemoved(index);
                                    index++;
                                }
                               adapter.notifyDataSetChanged();

                                return true;
                            case R.id.rmAll:
                                adapter.ingredientArrayList.removeAll(adapter.ingredientArrayList);
                                adapter.notifyDataSetChanged();
                                return true;
                            case R.id.uncheckAll:
                                for(int i = 0; i < adapter.ingredientArrayList.size(); i++){
                                    if(adapter.ingredientArrayList.get(i).getSelected()){
                                        adapter.ingredientArrayList.get(i).setSelected(false);
                                        adapter.notifyItemChanged(i);
                                    }
                                }
                                return true;
                            default:
                                return false;
                        }

                    }
                });

                p.inflate(R.menu.rm_popup_menu);
                p.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        return false;
    }

    /**
     * Creates a list of cards containing ingredient names and checkboxes. When the
     * checkboxes are clicked, the user will have the option of deleting the ingredient.
     */
    private void dataInitialize() {

        ingredientArrayList = new ArrayList<>();
        ingredientName = new String[] {
                "Eggs", "Milk", "Chicken", "Steak", "Carrots", "Apples", "Broccoli", "Mushrooms",
                "Olive Oil", "Sugar", "Flour", "Paprika", "Italian Seasoning", "Bread Crumbs",
                "Salt", "Pasta", "Alfredo Sauce", "Orange", "Mango", "Avocado", "Banana", "Asaparagus"
        };


        for (int i = 0; i < ingredientName.length; i++) {
            Ingredient ingredient = new Ingredient(ingredientName[i]);
            ingredientArrayList.add(ingredient);
        }

        adapter = new IngredientAdapter(ingredientArrayList);
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}