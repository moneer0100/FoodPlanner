package com.example.foodtestplanner.mealDetails.presenter;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealDetailsPresenter {
    public Completable SetClickedItemData(WeekPlan selectedDate);
//    public void addToFav(MealsItem mealsItem);
    public Completable addToFav(MealsItem mealsItem);
    public Single<MealsDetailResponse> MEALS_DETAIL_RESPONSE_SINGLE(String category);
    Completable insertMealRemoteToFavorite(MealsItem mealsItem);
    Completable insertMealRemoteToWeekPlan(WeekPlan weekPlan);
}
