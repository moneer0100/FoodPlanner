package com.example.foodtestplanner.home.presenter;

import com.example.foodtestplanner.home.view.CategoryMealview;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.CategorisItem;
import com.example.foodtestplanner.model.network.network.CategoryCallBack;

import java.util.List;

public class CategoryMealpresen implements  Categorymealpresent, CategoryCallBack {
private final CategoryMealview categoryMealview;
private final MealRepositoryView mealRepositoryView;


    public CategoryMealpresen(CategoryMealview categoryMealview, MealRepositoryView mealRepositoryView) {
        this.categoryMealview = categoryMealview;
        this.mealRepositoryView = mealRepositoryView;

    }

    @Override
    public void getcategorymeal() {
        mealRepositoryView.CategoryNetworkCall(this);
    }

    @Override
    public void onSuccessResult(List<CategorisItem> categorisItemList) {
        categoryMealview.showcategorydata(categorisItemList);
    }

    @Override
    public void onFailureResult(String errormsg) {
        categoryMealview.showErrorMassCategory(errormsg);
    }

}
