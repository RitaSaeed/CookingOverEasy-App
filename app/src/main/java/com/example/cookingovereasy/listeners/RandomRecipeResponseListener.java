package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
