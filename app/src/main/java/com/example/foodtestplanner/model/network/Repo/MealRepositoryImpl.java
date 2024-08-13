package com.example.foodtestplanner.model.network.Repo;

import android.util.Log;

import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.MealsItemResponse;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.example.foodtestplanner.model.network.db.MealDao;
import com.example.foodtestplanner.model.network.network.AreaCallBack;
import com.example.foodtestplanner.model.network.network.CategoryCallBack;
import com.example.foodtestplanner.model.network.network.IngridentCallBack;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.network.RandomMealCallBack;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.example.foodtestplanner.model.network.remotedatabase.RemoteDatabaseImp;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepositoryView {
    MealRemotDataSourceImp mealRemoteDataSource;
    MealLocalDataSourceImpl mealLocalDataSource;
    RemoteDatabaseImp remoteDatabaseImp;

    static MealRepositoryImpl mealRepository;

    public MealRepositoryImpl(MealRemotDataSourceImp mealRemoteDataSource, MealLocalDataSourceImpl mealLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        this.remoteDatabaseImp = remoteDatabaseImp;
    }
    public static MealRepositoryImpl getInstance(MealRemotDataSourceImp mealRemoteDataSource,MealLocalDataSourceImpl mealLocalDataSource){
        if(mealRepository == null)
            mealRepository = new MealRepositoryImpl(mealRemoteDataSource,mealLocalDataSource);

        return  mealRepository;
    }


    @Override
    public void RandomMealNetworkCall(RandomMealCallBack networkCallback) {
        mealRemoteDataSource.RandomMealNetworkCall(networkCallback);

    }

    @Override
    public void CategoryNetworkCall(CategoryCallBack categoryCallBack) {
        mealRemoteDataSource.CategoryNetworkCall(categoryCallBack);
    }

    @Override
    public void IngredientsNetworkCall(IngridentCallBack ingredientsCallback) {
        mealRemoteDataSource.IngredientsNetworkCall(ingredientsCallback);
    }

    @Override
    public void AreasNetworkCall(AreaCallBack areaMealCallback) {
        mealRemoteDataSource.AreasNetworkCall(areaMealCallback);
    }

    @Override
    public Single<ListDetailsResponse> CategoryDetailsNetworkCall(String category) {

        return mealRemoteDataSource.CategoryDetailsNetworkCall(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ListDetailsResponse> IngredientDetailsNetworkCall(String category) {
        return mealRemoteDataSource.IngredientDetailsNetworkCall(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ListDetailsResponse> AreaDetailsNetworkCall(String category) {

        return mealRemoteDataSource.AreaDetailsNetworkCall(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealsItemResponse> searchByNameNetworkCall(String name) {

        return mealRemoteDataSource.searchByNameNetworkCall(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealsDetailResponse> getMealDetailNetworkCall(String name) {

        return mealRemoteDataSource.getMealDetailNetworkCall(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable insertMealToFavoritDetails(MealsDetail mealsItem) {
        return mealLocalDataSource.insertMealDetailToFavorite(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    @Override
    public void insertWeekPlanMeal(WeekPlan weekPlan) {
        insertMealRemoteToWeekPlan(weekPlan);
        mealLocalDataSource.insertWeekPlanMealToCalender(weekPlan);
    }

    @Override
    public Completable insertMealToFavorite(MealsItem mealsItem) {
        Log.i("moneerinsert", mealsItem.getStrCategory());
        return mealLocalDataSource.insertMealToFavorite(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    @Override
    public void deleteMealFromFavorite(MealsItem mealsItem) {
        mealLocalDataSource.deleteMealFromFavorite(mealsItem);
    }

    @Override
    public Flowable<List<MealsItem>> getAllFavoriteStoredMeals() {

        return mealLocalDataSource.getAllFavoriteStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }




    @Override
    public void deleteMealDetailFromFavorite(MealsDetail mealsDetail) {
       mealLocalDataSource.deleteMealDetailFromFavorite(mealsDetail);
    }

    @Override
    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return mealLocalDataSource.getAllFavoriteStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail() {
        return mealLocalDataSource.getAllFavoriteStoredMealsDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Flowable<List<WeekPlan>> getWeekPlanMeals() {
        return mealLocalDataSource.getWeekPlanMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Completable insertWeekPlanMealToCalender(WeekPlan weekPlan) {
        return mealLocalDataSource.insertWeekPlanMealToCalender(weekPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteWeekPlanMealFromCalender(WeekPlan weekPlan) {
    mealLocalDataSource.deleteWeekPlanMealFromCalender(weekPlan);
    }

    @Override
    public Flowable<List<WeekPlan>> getMealsForDate(String date) {
        return mealLocalDataSource.getMealsForDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteAllTheCalenderList() {
mealLocalDataSource.deleteAllTheCalenderList();
    }

    @Override
    public void deleteAllTheFavoriteList() {
mealLocalDataSource.deleteAllTheFavoriteList();
    }

    @Override
    public void insertMealRemoteToFavorite(MealsItem mealsItem) {
mealLocalDataSource.deleteMealFromFavorite(mealsItem);
    }

    @Override
    public void insertMealRemoteToWeekPlan(WeekPlan weekPlan) {
mealLocalDataSource.insertWeekPlanMealToCalender(weekPlan);
    }
///////////////////////////////////////
    @Override
    public void deleteMealRemoteFromFavorite(MealsItem mealsItem) {

    }



    public void deleteMealRemoteFromFavorite(MealsDetail mealsDetail) {
        mealLocalDataSource.deleteMealDetailFromFavorite(mealsDetail);

    }


    public void deleteMealRemoteFromWeekPlan(WeekPlan weekPlan) {


    }
}
