package com.example.cookingovereasy.Models;

public class SavedRecipe {
    private String category;
    private String name;
    private int id;
    public SavedRecipe(String category, String name, int id) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
