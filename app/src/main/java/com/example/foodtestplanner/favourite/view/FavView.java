package com.example.foodtestplanner.favourite.view;

import com.example.foodtestplanner.model.dto.MealsItem;

public interface FavView {
    public void showFavItem(MealsItem mealsItem);
    public void showFavERROR(String error);

}
