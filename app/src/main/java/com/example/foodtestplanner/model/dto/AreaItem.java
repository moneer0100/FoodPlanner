package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AreaItem implements Serializable {
    @SerializedName("strArea")
    private String area;

    public String getArea() {
        return area;
    }
}
