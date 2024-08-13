package com.example.foodtestplanner.country.presenter;

import com.example.foodtestplanner.model.dto.ListDetailsResponse;

import io.reactivex.rxjava3.core.Single;

public interface AreaDetailsPresenter {
    public Single<ListDetailsResponse> getAreaDetail(String category);
}
