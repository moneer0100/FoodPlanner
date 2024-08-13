package com.example.foodtestplanner.searchByName.presenter;

import com.example.foodtestplanner.model.dto.MealsItemResponse;

import io.reactivex.rxjava3.core.Single;

public interface SearchmealNamePresenter {
    public Single<MealsItemResponse> getMealName(String name);
}
