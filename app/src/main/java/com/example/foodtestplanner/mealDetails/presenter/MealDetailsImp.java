package com.example.foodtestplanner.mealDetails.presenter;

import android.util.Log;

import com.example.foodtestplanner.mealDetails.view.MealDetailsView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealDetailsImp implements MealDetailsPresenter{
    private MealDetailsView mealDetailView;
    private MealRepositoryView mealRepositoryView;

    public MealDetailsImp(MealDetailsView mealDetailView, MealRepositoryView mealRepositoryView) {
        this.mealDetailView = mealDetailView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public Completable SetClickedItemData(WeekPlan selectedDate) {
        return mealRepositoryView.insertWeekPlanMealToCalender(selectedDate);
    }

    @Override
    public Completable addToFav(MealsItem mealsItem) {
        Log.d("Samir", "addToFav: " + mealsItem.getStrCategory());
        Completable completable = mealRepositoryView.insertMealToFavorite(mealsItem);
      return   completable;

    }

    @Override
    public Single<MealsDetailResponse> MEALS_DETAIL_RESPONSE_SINGLE(String category) {
        return mealRepositoryView.getMealDetailNetworkCall(category);
    }

    @Override
    public Completable insertMealRemoteToFavorite(MealsItem mealsItem) {
       return mealRepositoryView.insertMealRemoteToFavorite(mealsItem);
    }

    @Override
    public Completable insertMealRemoteToWeekPlan(WeekPlan weekPlan) {
        return mealRepositoryView.insertMealRemoteToWeekPlan(weekPlan);
    }
}
