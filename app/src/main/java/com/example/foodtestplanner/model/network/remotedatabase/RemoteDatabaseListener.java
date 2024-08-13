package com.example.foodtestplanner.model.network.remotedatabase;

import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import io.reactivex.rxjava3.core.Completable;

public interface RemoteDatabaseListener {
    Completable insertToFavorite(MealsItem mealsItem);
    Completable insertToWeekPlan(WeekPlan weekPlan);
    void deleteFromWeekPlane(WeekPlan weekPlan);
    void deleteFromFavorite(MealsItem mealsItem);
}
