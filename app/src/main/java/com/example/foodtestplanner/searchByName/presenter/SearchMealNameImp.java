package com.example.foodtestplanner.searchByName.presenter;

import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.MealsItemResponse;
import com.example.foodtestplanner.model.network.network.SearchByNameCallBack;
import com.example.foodtestplanner.searchByName.view.SearchMealView;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchMealNameImp implements SearchmealNamePresenter, SearchByNameCallBack {
    private MealRepositoryView mealRepositoryView;
    private SearchMealView searchMealView;

    public SearchMealNameImp(MealRepositoryView mealRepositoryView, SearchMealView searchMealView) {
        this.mealRepositoryView = mealRepositoryView;
        this.searchMealView = searchMealView;
    }

    @Override
    public void onSuccessResult(List<MealsItem> searchbynamelist) {
        searchMealView.searchMealName(searchbynamelist);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        searchMealView.searchMealError(errorMsg);
    }

    @Override
    public Single<MealsItemResponse> getMealName(String name) {
        return mealRepositoryView.searchByNameNetworkCall(name);
    }
}
