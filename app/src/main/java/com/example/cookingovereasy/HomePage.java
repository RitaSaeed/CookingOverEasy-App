package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main page of the app, holds the fragments and the navigation bar at the bottom.
 */
public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CookBookFragment cookBookFragment = new CookBookFragment();
    CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
    GroceryListFragment groceryListFragment = new GroceryListFragment();
    SearchFragment searchFragment = new SearchFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    ArrayList<Category> categories;
    SavedRecipe currentRecipe;
    Map<String, ArrayList<SavedRecipe>> categoryMap;

    /**
     * Does the bulk of the work for this page, initializes the xml page objects and sets
     * their action listeners.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        categories = new ArrayList<>();
        loadData();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navbarcontainer,cookBookFragment).commit();
        getFragmentManager().beginTransaction();

        /**
         * Switch statement that determines which fragment to load onto the page by which button
         * in the navigation bar is selected by the user.
         */
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.cookbook:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,cookBookFragment).commit();
                        return true;
                    case R.id.addrecipe:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,createRecipeFragment).commit();
                        return true;
                    case R.id.grocerylist:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,groceryListFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction()
                                .remove(cookBookFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(searchFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(groceryListFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(createRecipeFragment).commit();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,settingsFragment).commit();
                        return true;
                    case R.id.search:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,searchFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void setCategories(ArrayList<Category> newCategories) {
        categories = newCategories;
    }

    public ArrayList<Category> retrieveCategories() {
        return this.categories;
    }

    public void addSavedRecipe(SavedRecipe recipe) {
        //currentRecipe = recipe;
        categoryMap.get(recipe.getCategory()).add(recipe);
        saveData();
        //cookBookFragment.addRecipeToCategory(currentRecipe);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addMapCategory(String categoryName) {
        categoryMap.put(categoryName, new ArrayList<>());
    }

    public ArrayList<SavedRecipe> retrieveCategoryItems(String categoryName) {
        return categoryMap.get(categoryName);
    }

    public void saveData(){
        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categoryMap);
        editor.putString("ListCategories", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("ListCategories", null);
        Type type = new TypeToken<Map<String, ArrayList<SavedRecipe>>>() {}.getType();
        categoryMap = gson.fromJson(json, type);

        if (categoryMap == null) {
            categoryMap = new HashMap<>();
        }
    }

}