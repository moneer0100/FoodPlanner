package com.example.foodtestplanner.calenderWeekPlan.presenter;

import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface CalenderPresenterView {
    public Flowable<List<WeekPlan>> getWeekPlanMealList();
    public void deleteMeal(WeekPlan weekPlan);
    Flowable<List<WeekPlan>> getMealsForDate(String date);
}
