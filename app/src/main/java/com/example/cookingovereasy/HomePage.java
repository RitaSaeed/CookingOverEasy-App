package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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


}