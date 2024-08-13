package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngridentItemResponse {
    @SerializedName("meals")
    private List<IngridentItem> meals;

    public List<IngridentItem> getIngridentItems() {
        return meals;
    }
}
