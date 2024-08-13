package com.example.foodtestplanner.search.presenter;


import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.AreaItem;
import com.example.foodtestplanner.model.network.network.AreaCallBack;
import com.example.foodtestplanner.search.AreaView;

import java.util.List;

public class AreaImp implements AreaPresenter, AreaCallBack {
    private AreaView areaView;
    private MealRepositoryView mealRepositoryView;

    public AreaImp(AreaView areaView, MealRepositoryView mealRepositoryView) {
        this.areaView = areaView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public void onSuccessResult(List<AreaItem> areaItemListlist) {
        areaView.showAreasData(areaItemListlist);
    }

    @Override
    public void onFailureResult(String errormsg) {
        areaView.showAreasErrorMsg(errormsg);
    }

    @Override
    public void getArea() {
        mealRepositoryView.AreasNetworkCall(this);
    }
}
