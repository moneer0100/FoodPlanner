package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.MealsDetail;

import java.util.List;

public interface MealDetailsCallBack {
    public  void onSuccessResult(List<MealsDetail> mealsDetailList);
    public void onFailureResult(String errormsg);
}
