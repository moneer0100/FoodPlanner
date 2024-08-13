package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

public interface SearchByNameCallBack {
    public void onSuccessResult (List<MealsItem>searchbynamelist);
    public void onFailureResult(String errorMsg);
}
