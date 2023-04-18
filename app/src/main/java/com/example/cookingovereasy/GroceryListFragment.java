package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;
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
import android.widget.SearchView;
import android.widget.Toast;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

/**
 * Provides functionality for the grocery list fragment.
 */
public class GroceryListFragment extends Fragment implements SearchIngredientAdapter.AddToGroceryList {

    private RecyclerView recyclerView;
    private ArrayList<Ingredient> ingredientArrayList;
    ImageView remove;
    private ImageView add;
    IngredientAdapter adapter;
    SearchIngredientAdapter searchIngredientAdapter;
    List<String> ingredientEntries;
    SearchView searchIngredients;
    RecyclerView ingredientRecyclerView;
    ArrayList<SearchIngredient> searchIngredientList;


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
        getViews(view);

        searchIngredients.clearFocus();
        // query listener for the search view
        searchIngredients.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addIngredient(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // on click listener for the "+" button on the grocery list fragment
        add.setOnClickListener(v -> {
            recyclerView.setVisibility(View.GONE);
            searchIngredients.setVisibility(View.VISIBLE);
            ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            ingredientRecyclerView.setAdapter(searchIngredientAdapter);
            searchIngredientAdapter.notifyDataSetChanged();
            ingredientRecyclerView.setVisibility(View.VISIBLE);

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientAdapter(ingredientArrayList, getActivity());

        // used in process that allows user to drag and drop the grocery list items
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);

        try {
            prepArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadData();

        searchIngredientAdapter = new SearchIngredientAdapter(getContext(),
                searchIngredientList, this);

        // on click listener for the popup menu that contains the remove/uncheck functions
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

    /**
     * Assigns the views from the components of the corresponding xml.
     * @param view the view to pull from
     */
    private void getViews(View view) {
        add = view.findViewById(R.id.addIcon);
        searchIngredients = view.findViewById(R.id.searchViewSearchIngredient);
        ingredientRecyclerView = view.findViewById(R.id.recycler_view_searchIngredient);
        recyclerView = view.findViewById(R.id.recycler_grocery_view);
        remove = view.findViewById(R.id.imageViewRemove);
    }

    /**
     * Saves data from ingredient array list to json file to be loaded later
     */
    private void saveData(){
        SharedPreferences sp = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.ingredientArrayList);
        editor.putString("Ingredient list", json);
        editor.apply();
    }

    /**
     * Loads data from json file to ingredient array list
     */
    private void loadData(){
        SharedPreferences sp = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("Ingredient list", null);
        Type type = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        adapter.ingredientArrayList = gson.fromJson(json, type);

        if(adapter.ingredientArrayList == null) {
            adapter.ingredientArrayList = new ArrayList<>();
            dataInitialize();
        }

    }

    /**
     * Uses subarrays to filter the list of searchable ingredients
     * @param text input text to use as filter
     */
    private void filterList(String text) {
        List<SearchIngredient> filteredList = new ArrayList<SearchIngredient>();
        for (SearchIngredient i : searchIngredientList) {
            if (i.getIngredientName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(i);
            }
        }

        if (filteredList.isEmpty()) {
//            Toast.makeText(getContext(), "Ingredient not found", Toast.LENGTH_SHORT).show();
        } else {
            searchIngredientAdapter.setFilteredList(filteredList);
        }
    }

    /**
     * Creates a list of cards containing ingredient names and checkboxes. When the
     * checkboxes are clicked, the user will have the option of deleting the ingredient.
     */
    private void dataInitialize() {

        // code below is to initialize grocery list with following string array values
//        ingredientArrayList = new ArrayList<>();
//        ingredientName = new String[] {
//                "Eggs", "Milk", "Chicken", "Steak", "Carrots", "Apples", "Broccoli", "Mushrooms",
//                "Olive Oil", "Sugar", "Flour", "Paprika", "Italian Seasoning", "Bread Crumbs",
//                "Salt", "Pasta", "Alfredo Sauce", "Orange", "Mango", "Avocado", "Banana", "Asaparagus"
//        };
//
//
//        for (int i = 0; i < ingredientName.length; i++) {
//            Ingredient ingredient = new Ingredient(ingredientName[i]);
//            ingredientArrayList.add(ingredient);
//        }

        // code below is used to add all items from csv
//        for (String s : ingredientEntries) {
//            ingredientArrayList.add(new Ingredient((s)));
//        }

        adapter = new IngredientAdapter(ingredientArrayList, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        saveData();
    }

    /**
     * Gets the ingredient names from a csv and loads them into an arraylist of ingredients.
     * @throws IOException
     */
    private void prepArray() throws IOException {
        InputStreamReader is = new InputStreamReader(getActivity().getAssets().open("ingredients.csv"));
        BufferedReader reader = new BufferedReader(is);
        reader.readLine();
        String line;
        ingredientEntries = new ArrayList<>();
        searchIngredientList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            ingredientEntries.add(line);
        }

        for (String i : ingredientEntries) {
            searchIngredientList.add(new SearchIngredient(i));
        }
    }

    /**
     * Takes a string and creates an ingredient based on that string, and adds it to the arraylist
     * @param newIngredient the name of the new ingredient
     */
    public void addIngredient(String newIngredient) {
        Toast.makeText(getActivity(), "Added " + newIngredient, Toast.LENGTH_SHORT).show();
        adapter.ingredientArrayList.add(new Ingredient(newIngredient));
        adapter.notifyDataSetChanged();
        searchIngredients.setVisibility(View.GONE);
        ingredientRecyclerView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        saveData();
    }
}