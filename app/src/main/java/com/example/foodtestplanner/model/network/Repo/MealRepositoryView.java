package com.example.foodtestplanner.model.network.Repo;

import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.MealsItemResponse;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.example.foodtestplanner.model.network.network.AreaCallBack;
import com.example.foodtestplanner.model.network.network.CategoryCallBack;
import com.example.foodtestplanner.model.network.network.IngridentCallBack;
import com.example.foodtestplanner.model.network.network.RandomMealCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepositoryView {
    public void RandomMealNetworkCall(RandomMealCallBack networkCallback);
    public void CategoryNetworkCall(CategoryCallBack categoryCallBack);
    public void IngredientsNetworkCall(IngridentCallBack ingredientsCallback);

    public void AreasNetworkCall(AreaCallBack areaMealCallback);

    public Single<ListDetailsResponse> CategoryDetailsNetworkCall(String category);

    public Single<ListDetailsResponse> IngredientDetailsNetworkCall(String category);

    public Single<ListDetailsResponse> AreaDetailsNetworkCall(String category);

    public Single<MealsItemResponse> searchByNameNetworkCall(String name);

    public Single<MealsDetailResponse> getMealDetailNetworkCall(String name);

    //Local
    Completable insertMealToFavoritDetails(MealsDetail mealsItem);
    public Completable insertMealToFavorite(MealsItem mealsItem);

    public void insertWeekPlanMeal(WeekPlan weekPlan);
    void deleteMealFromFavorite(MealsItem mealsItem);

    Flowable<List<MealsItem>> getAllFavoriteStoredMeals();

    void deleteMealDetailFromFavorite(MealsDetail mealsDetail);
    public Flowable<List<MealsItem>> getFavoriteMeals();

    Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail();

    Flowable<List<WeekPlan>> getWeekPlanMeals();

    Completable insertWeekPlanMealToCalender(WeekPlan weekPlan);

    void deleteWeekPlanMealFromCalender(WeekPlan weekPlan);

    public Flowable<List<WeekPlan>> getMealsForDate(String date);

    public void deleteAllTheCalenderList();

    public void deleteAllTheFavoriteList();



    //RemoteDB
    void insertMealRemoteToFavorite(MealsItem mealsItem);
   void insertMealRemoteToWeekPlan(WeekPlan weekPlan);
    void deleteMealRemoteFromFavorite(MealsItem mealsItem);
  // void deleteMealRemoteFromWeekPlan(WeekPlan weekPlan);


}