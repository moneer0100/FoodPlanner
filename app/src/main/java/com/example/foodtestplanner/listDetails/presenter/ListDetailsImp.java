package com.example.foodtestplanner.listDetails.presenter;

import com.example.foodtestplanner.listDetails.view.ListMealView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.example.foodtestplanner.model.network.network.MealDetailsCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ListDetailsImp implements ListDetailspresenter, MealDetailsCallBack {
    private final ListMealView listMealView;
    private final MealRepositoryView mealRepositoryView;

    public ListDetailsImp(ListMealView listMealView, MealRepositoryView mealRepositoryView) {
        this.listMealView = listMealView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public Single<MealsDetailResponse> LIST_DETAILS_RESPONSE_SINGLE(String category) {
        return mealRepositoryView.getMealDetailNetworkCall(category);
    }

    @Override
    public Completable addToFavDetails(MealsDetail mealsDetail) {
        return mealRepositoryView.insertMealToFavoritDetails(mealsDetail);
    }



    @Override
    public Completable SetClickedItemData(WeekPlan selectedDate) {
        return mealRepositoryView.insertWeekPlanMealToCalender(selectedDate);
    }

    @Override
    public Completable addToFavitem(MealsItem mealsItem) {
        return mealRepositoryView.insertMealToFavorite(mealsItem);
    }

    @Override
    public void onSuccessResult(List<MealsDetail> mealsDetailList) {
        listMealView.showListmealDitails(mealsDetailList);
    }

    @Override
    public void onFailureResult(String errormsg) {

        listMealView.showListitemError(errormsg);
    }
}
