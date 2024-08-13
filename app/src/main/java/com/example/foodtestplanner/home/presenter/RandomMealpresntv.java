package com.example.foodtestplanner.home.presenter;

import com.example.foodtestplanner.home.view.RandomMealView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.network.network.RandomMealCallBack;

import java.util.List;

public class RandomMealpresntv implements RandomMealpresent, RandomMealCallBack {
    private final RandomMealView randomMealpresent;
    private final MealRepositoryView mealRepositoryView;

    public RandomMealpresntv(RandomMealView randomMealpresent, MealRepositoryView mealRepositoryView) {
        this.randomMealpresent = randomMealpresent;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public void getMeal() {
        mealRepositoryView.RandomMealNetworkCall(this);
    }

    @Override
    public void onSuccessResult(List<MealsItem> mealsItem) {
        randomMealpresent.showRandomMealView(mealsItem);

    }

    @Override
    public void onFailureResult(String error) {
        randomMealpresent.showmassagrandomview(error);
    }
}
