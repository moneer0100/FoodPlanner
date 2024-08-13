package com.example.foodtestplanner.searchByName.view;

import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public interface SearchMealView {
public void searchMealName(List<MealsItem>searchMealsItemList);
public void searchMealError(String error);

}
