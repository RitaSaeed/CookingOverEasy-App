package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
