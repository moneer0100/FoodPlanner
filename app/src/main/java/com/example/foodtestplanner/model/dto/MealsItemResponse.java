package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsItemResponse {

    @SerializedName("meals")
    private List<MealsItem> meals;

    public List<MealsItem> getRandomMealList(){
        return meals;
    }
}
