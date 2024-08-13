package com.example.foodtestplanner.search.presenter;

import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.IngridentItem;
import com.example.foodtestplanner.model.network.network.IngridentCallBack;
import com.example.foodtestplanner.search.IngridentView;

import java.util.List;

public class IngridentImp implements IngridentPresenter, IngridentCallBack {
    private MealRepositoryView mealRepositoryView;
    private IngridentView ingridentView;

    public IngridentImp(MealRepositoryView mealRepositoryView, IngridentView ingridentView) {
        this.mealRepositoryView = mealRepositoryView;
        this.ingridentView = ingridentView;
    }

    @Override
    public void onSuccessResult(List<IngridentItem> ingridentItemList) {
        ingridentView.showIngredientsData(ingridentItemList);
    }

    @Override
    public void onFailureResult(String errormsg) {
        ingridentView.showIngredientsErrorMsg(errormsg);
    }

    @Override
    public void getIngredient() {
        mealRepositoryView.IngredientsNetworkCall(this);
    }
}
