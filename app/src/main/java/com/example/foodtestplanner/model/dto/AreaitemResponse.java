package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaitemResponse {
    @SerializedName("meals")
    private List<AreaItem>meals;

    public List<AreaItem> getAreaList() {
        return meals;
    }
}
