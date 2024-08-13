package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public interface RandomMealCallBack {
    public void onSuccessResult(List<MealsItem>mealsItem);
    public void onFailureResult(String error);
}
