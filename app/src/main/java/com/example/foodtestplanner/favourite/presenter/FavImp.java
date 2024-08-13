package com.example.foodtestplanner.favourite.presenter;

import android.util.Log;

import com.example.foodtestplanner.favourite.view.FavView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavImp implements FavPresenter {
    private MealRepositoryView mealRepositoryView;
    FavView favView;

    public FavImp(MealRepositoryView mealRepositoryView, FavView favView) {
        this.mealRepositoryView = mealRepositoryView;
        this.favView = favView;
    }

    @Override
    public Flowable<List<MealsItem>> getFavMealList() {
        Log.i("TAG", "getFavMealList: Fav Presenter Live Data ");
        return mealRepositoryView.getAllFavoriteStoredMeals();
    }

    @Override
    public void deleteMeal(MealsItem mealsItem) {
        mealRepositoryView.deleteMealFromFavorite(mealsItem);
        Log.i("TAG", "deleteMeal: Presenter");
    }
}
