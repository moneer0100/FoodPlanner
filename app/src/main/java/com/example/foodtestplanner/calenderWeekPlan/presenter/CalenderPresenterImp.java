package com.example.foodtestplanner.calenderWeekPlan.presenter;

import android.util.Log;

import com.example.foodtestplanner.calenderWeekPlan.CalenderView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class CalenderPresenterImp implements CalenderPresenterView{

    private CalenderView weekPlanMealView;
    private MealRepositoryView mealRepositoryView;

    public CalenderPresenterImp(CalenderView weekPlanMealView, MealRepositoryView mealRepositoryView) {
        this.weekPlanMealView = weekPlanMealView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public Flowable<List<WeekPlan>> getWeekPlanMealList() {
        return mealRepositoryView.getWeekPlanMeals();
    }

    @Override
    public void deleteMeal(WeekPlan weekPlan) {
        mealRepositoryView.deleteWeekPlanMealFromCalender(weekPlan);
        Log.i("TAG", "deleteMeal: Presenter");
    }

    @Override
    public Flowable<List<WeekPlan>> getMealsForDate(String date) {
        return mealRepositoryView.getMealsForDate(date);

    }
}
