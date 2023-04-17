package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
