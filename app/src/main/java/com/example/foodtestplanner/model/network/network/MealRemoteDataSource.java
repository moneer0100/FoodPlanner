package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItemResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealRemoteDataSource {
    public void RandomMealNetworkCall(RandomMealCallBack networkCallback);
    public void IngredientsNetworkCall(IngridentCallBack ingredientsCallback);
    public void AreasNetworkCall(AreaCallBack areaMealCallback);
    public void CategoryNetworkCall(CategoryCallBack categoryCallBack);
    public Single<ListDetailsResponse> CategoryDetailsNetworkCall(String category);
    public Single<ListDetailsResponse> IngredientDetailsNetworkCall(String category);
    public Single<ListDetailsResponse> AreaDetailsNetworkCall(String category);
    public Single<MealsItemResponse> searchByNameNetworkCall(String name);
    public Single<MealsDetailResponse> getMealDetailNetworkCall(String name);

}
