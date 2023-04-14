package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroceryListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Ingredient> ingredientArrayList;
    ImageView remove;
    private String[] ingredientName;
    private ImageView add;
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

        add = view.findViewById(R.id.addIcon);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getActivity(), AddToGroceryList.class);
                startActivity(addIntent);

            }
        });

        recyclerView = view.findViewById(R.id.recycler_grocery_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientAdapter(ingredientArrayList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //dataInitialize();
        loadData();

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
                                saveData();
                                return true;
                            case R.id.rmAll:
                                adapter.ingredientArrayList.removeAll(adapter.ingredientArrayList);
                                adapter.notifyDataSetChanged();
                                saveData();
                                return true;
                            case R.id.uncheckAll:
                                for(int i = 0; i < adapter.ingredientArrayList.size(); i++){
                                    if(adapter.ingredientArrayList.get(i).getSelected()){
                                        adapter.ingredientArrayList.get(i).setSelected(false);
                                        adapter.notifyItemChanged(i);
                                    }
                                }
                                saveData();
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

    private void saveData(){
        SharedPreferences sp = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.ingredientArrayList);
        editor.putString("Ingredient list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sp = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("Ingredient list", null);
        Type type = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        adapter.ingredientArrayList = gson.fromJson(json, type);

        if(adapter.ingredientArrayList == null) adapter.ingredientArrayList = new ArrayList<Ingredient>();
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

        adapter = new IngredientAdapter(ingredientArrayList, getActivity());
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}