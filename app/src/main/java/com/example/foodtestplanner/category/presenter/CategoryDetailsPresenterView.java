package com.example.foodtestplanner.category.presenter;

import com.example.foodtestplanner.model.dto.ListDetailsResponse;

import io.reactivex.rxjava3.core.Single;

public interface CategoryDetailsPresenterView {
    public Single<ListDetailsResponse> getCategoryDetail(String category);
}
