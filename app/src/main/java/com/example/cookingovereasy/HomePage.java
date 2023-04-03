package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CookBookFragment cookBookFragment = new CookBookFragment();
    CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
    GroceryListFragment groceryListFragment = new GroceryListFragment();
    SearchFragment searchFragment = new SearchFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navbarcontainer,cookBookFragment).commit();
        getFragmentManager().beginTransaction();
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