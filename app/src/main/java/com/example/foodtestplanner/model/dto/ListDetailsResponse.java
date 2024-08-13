package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListDetailsResponse {
    @SerializedName("meals")
    private List<ListDetails>meals;

    public List<ListDetails> getMeals() {
        return meals;
    }
}
