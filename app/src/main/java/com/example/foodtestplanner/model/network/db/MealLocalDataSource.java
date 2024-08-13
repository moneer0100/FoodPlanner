package com.example.foodtestplanner.model.network.db;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealLocalDataSource {
    Completable insertMealToFavorite(MealsItem mealsItem);
    void deleteMealFromFavorite(MealsItem mealsItem);
    Flowable<List<MealsItem>> getAllFavoriteStoredMeals();


    Completable  insertMealDetailToFavorite(MealsDetail mealsDetail);
    void deleteMealDetailFromFavorite(MealsDetail mealsDetail);
    Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail();

    Flowable<List<WeekPlan>> getWeekPlanMeals();
    Completable insertWeekPlanMealToCalender(WeekPlan weekPlan);
    void deleteWeekPlanMealFromCalender(WeekPlan weekPlan);
    public Flowable<List<WeekPlan>> getMealsForDate(String date);

    public void deleteAllTheCalenderList();
    public void deleteAllTheFavoriteList();

}
