package com.example.foodtestplanner.favourite.presenter;

import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavPresenter {
    public Flowable<List<MealsItem>> getFavMealList();
    public void deleteMeal(MealsItem mealsItem);
}
