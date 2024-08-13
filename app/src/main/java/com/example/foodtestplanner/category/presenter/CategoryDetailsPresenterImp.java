package com.example.foodtestplanner.category.presenter;

import com.example.foodtestplanner.category.view.CategoryDetailsView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.CategortDetailsCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CategoryDetailsPresenterImp implements CategoryDetailsPresenterView, CategortDetailsCallBack {
    private final CategoryDetailsView categoryDetailsView;
    private final MealRepositoryView mealRepositoryView;

    public CategoryDetailsPresenterImp(CategoryDetailsView categoryDetailsView, MealRepositoryView mealRepositoryView) {
        this.categoryDetailsView = categoryDetailsView;
        this.mealRepositoryView = mealRepositoryView;
    }

    @Override
    public Single<ListDetailsResponse> getCategoryDetail(String category) {
      return   mealRepositoryView.CategoryDetailsNetworkCall(category);
    }

    @Override
    public void onSuccessResult(List<ListDetails> categListDetails) {
        categoryDetailsView.showCategoryDetailsData(categListDetails);
    }

    @Override
    public void onFailureResult(String errormsg) {
        categoryDetailsView.showCategoryDetailsErrorMsg(errormsg);

    }
}
