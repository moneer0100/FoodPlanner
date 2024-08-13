package com.example.foodtestplanner.mealDetails.view;

import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.MealsItem;

public interface MealDetailsView {
    public void showItemDetailData(MealsItem mealsItem);
    public void addToFav(MealsItem mealsItem);

    public void showItemDetailErrorMsg(String error);
}
