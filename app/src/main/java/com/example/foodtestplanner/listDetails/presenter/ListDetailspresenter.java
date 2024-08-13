package com.example.foodtestplanner.listDetails.presenter;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ListDetailspresenter {
    public Single<MealsDetailResponse> LIST_DETAILS_RESPONSE_SINGLE(String category);
    public Completable addToFavDetails(MealsDetail mealsDetail);
    public Completable SetClickedItemData(WeekPlan selectedDate);

    public Completable addToFavitem(MealsItem mealsItem);

}
