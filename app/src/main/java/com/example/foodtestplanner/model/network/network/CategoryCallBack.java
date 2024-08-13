package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.CategorisItem;

import java.util.List;

public interface CategoryCallBack {
    public  void onSuccessResult(List<CategorisItem> categorisItemList);
    public void onFailureResult(String errormsg);
}
