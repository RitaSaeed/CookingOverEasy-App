package com.example.cookingovereasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Cookbook extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CookBookFragment cookBookFragment = new CookBookFragment();
    CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
    GroceryListFragment groceryListFragment = new GroceryListFragment();
    SearchFragment searchFragment = new SearchFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,cookBookFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.cookbook:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,cookBookFragment).commit();
                        return true;
                    case R.id.addrecipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,createRecipeFragment).commit();
                        return true;
                    case R.id.grocerylist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,groceryListFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,settingsFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navbarcontainer,searchFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}