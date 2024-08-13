package com.example.foodtestplanner.country.presenter;

import com.example.foodtestplanner.country.view.AreaDetailsView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.AreaDetailsCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AreaDetailsImp implements AreaDetailsPresenter , AreaDetailsCallBack {
    private MealRepositoryView mealRepositoryView;
    private AreaDetailsView areaDetailsView;

    public AreaDetailsImp(MealRepositoryView mealRepositoryView, AreaDetailsView areaDetailsView) {
        this.mealRepositoryView = mealRepositoryView;
        this.areaDetailsView = areaDetailsView;
    }

    @Override
    public Single<ListDetailsResponse> getAreaDetail(String category) {
        return mealRepositoryView.AreaDetailsNetworkCall(category);
    }

    @Override
    public void onSuccessResult(List<ListDetails> areaDetailslist) {
        areaDetailsView.showAreaDetails(areaDetailslist);
    }

    @Override
    public void onFailureResult(String errormsg) {
        areaDetailsView.showAreaError(errormsg);
    }
}
