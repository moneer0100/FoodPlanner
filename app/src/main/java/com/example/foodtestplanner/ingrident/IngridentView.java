package com.example.foodtestplanner.ingrident;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface IngridentView {
    public void showIngredientDetailsData(List<ListDetails> ingridentDetailsList);
    public void showIngredientDetailsErrorMsg(String error);
}
