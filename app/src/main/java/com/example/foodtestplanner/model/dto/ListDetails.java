package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListDetails implements Serializable {

    @SerializedName("strMealThumb")
    private String MealThumb;

    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String Meal;

    public String getStrMealThumb(){
        return MealThumb;
    }

    public String getIdMeal(){
        return idMeal;
    }

    public String getStrMeal(){
        return Meal;
    }
}
