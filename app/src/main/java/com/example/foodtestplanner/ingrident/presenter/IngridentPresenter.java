package com.example.foodtestplanner.ingrident.presenter;


import com.example.foodtestplanner.model.dto.ListDetailsResponse;

import io.reactivex.rxjava3.core.Single;

public interface IngridentPresenter {
    public Single<ListDetailsResponse> getIngredientDetail(String category);

}
