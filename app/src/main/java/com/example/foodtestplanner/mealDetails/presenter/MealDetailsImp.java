package com.example.foodtestplanner.mealDetails.presenter;

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
    public void SetClickedItemData(WeekPlan selectedDate) {
        mealRepositoryView.insertWeekPlanMeal(selectedDate);
    }

    @Override
    public Completable addToFav(MealsItem mealsItem) {
      return   mealRepositoryView.insertMealToFavorite(mealsItem);
    }

    @Override
    public Single<MealsDetailResponse> MEALS_DETAIL_RESPONSE_SINGLE(String category) {
        return mealRepositoryView.getMealDetailNetworkCall(category);
    }
}
