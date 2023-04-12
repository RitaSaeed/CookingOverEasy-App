package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.IngredientResponse;

public interface IngredientResponseListener {
    void didFetch(IngredientResponse response, String message);
    void didError(String message);
}
