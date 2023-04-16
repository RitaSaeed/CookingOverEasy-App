package com.example.cookingovereasy;

public class SearchIngredient {
    private String ingredientName;

    public SearchIngredient(String name) {
        this.ingredientName = name;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
