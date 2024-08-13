package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.AreaItem;

import java.util.List;

public interface AreaCallBack {
    public  void onSuccessResult(List<AreaItem> areaItemListlist);
    public void onFailureResult(String errormsg);
}
