package com.example.foodtestplanner.ingrident.presenter;

import com.example.foodtestplanner.ingrident.IngridentView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.IngeridentDetailsCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class IngridentImp implements IngridentPresenter, IngeridentDetailsCallback {
    private IngridentView ingridentView;
    private MealRepositoryView mealRepositoryView;

    public IngridentImp(IngridentView ingridentView, MealRepositoryView mealRepositoryView) {
        this.ingridentView = ingridentView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public Single<ListDetailsResponse> getIngredientDetail(String category) {
        return mealRepositoryView.IngredientDetailsNetworkCall(category);
    }




    @Override
    public void onSuccessResult(List<ListDetails> categoryDetailsList) {
        ingridentView.showIngredientDetailsData(categoryDetailsList);
    }

    @Override
    public void onFailureResult(String errormsg) {
        ingridentView.showIngredientDetailsErrorMsg(errormsg);
    }
}
