package com.example.foodtestplanner.search;

import com.example.foodtestplanner.model.dto.IngridentItem;

import java.util.List;

public interface IngridentView {
    public void showIngredientsData(List<IngridentItem> IngredientsItemList);
    public void showIngredientsErrorMsg(String error);
}
