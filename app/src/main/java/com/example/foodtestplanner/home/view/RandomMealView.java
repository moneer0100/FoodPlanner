package com.example.foodtestplanner.home.view;

import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public interface RandomMealView {
    public void showRandomMealView(List<MealsItem>mealsItems);
    public void showmassagrandomview(String erromsg);
}
