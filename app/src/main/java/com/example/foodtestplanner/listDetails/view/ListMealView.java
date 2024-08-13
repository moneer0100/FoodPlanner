package com.example.foodtestplanner.listDetails.view;

import com.example.foodtestplanner.model.dto.MealsDetail;

import java.util.List;

public interface ListMealView {
    public void showListmealDitails(List<MealsDetail> mealsItems);
    public void showListitemError(String errormsg);
    public void addMealToFav(MealsDetail mealsItem);
}
