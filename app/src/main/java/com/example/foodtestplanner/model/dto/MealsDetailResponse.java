package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsDetailResponse {
    @SerializedName("meals")
    private List<MealsDetail>meals;

    public List<MealsDetail> getMeals() {
        return meals;
    }
}
