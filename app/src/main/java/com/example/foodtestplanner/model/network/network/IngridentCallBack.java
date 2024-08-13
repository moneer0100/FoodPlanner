package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.IngridentItem;

import java.util.List;

public interface IngridentCallBack {
    public  void onSuccessResult(List<IngridentItem> ingridentItemList);
    public void onFailureResult(String errormsg);
}
