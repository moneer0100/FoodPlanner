package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategorisItemResponse {
    @SerializedName("categories")
    private List<CategorisItem>categories;

    public List<CategorisItem> getCategories() {
        return categories;
    }
}
