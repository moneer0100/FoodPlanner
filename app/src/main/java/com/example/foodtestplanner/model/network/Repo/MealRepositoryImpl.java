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
    static RemoteDatabaseImp remoteDatabaseImp;

    static MealRepositoryImpl mealRepository;

    public MealRepositoryImpl(MealRemotDataSourceImp mealRemoteDataSource, MealLocalDataSourceImpl mealLocalDataSource, RemoteDatabaseImp remoteDatabaseImp) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        this.remoteDatabaseImp = remoteDatabaseImp;
    }

    public static MealRepositoryImpl getInstance(MealRemotDataSourceImp mealRemoteDataSource, MealLocalDataSourceImpl mealLocalDataSource){
        if(mealRepository == null)
            mealRepository = new MealRepositoryImpl(mealRemoteDataSource,mealLocalDataSource,remoteDatabaseImp);

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
           ;
    }

    @Override
    public Single<ListDetailsResponse> IngredientDetailsNetworkCall(String category) {
        return mealRemoteDataSource.IngredientDetailsNetworkCall(category);

    }

    @Override
    public Single<ListDetailsResponse> AreaDetailsNetworkCall(String category) {

        return mealRemoteDataSource.AreaDetailsNetworkCall(category);

    }

    @Override
    public Single<MealsItemResponse> searchByNameNetworkCall(String name) {

        return mealRemoteDataSource.searchByNameNetworkCall(name);

    }

    @Override
    public Single<MealsDetailResponse> getMealDetailNetworkCall(String name) {

        return mealRemoteDataSource.getMealDetailNetworkCall(name);

    }

    @Override
    public Completable insertMealToFavoritDetails(MealsDetail mealsItem) {
        Log.d("details", "insertMealToFavoritDetails: "+mealsItem.getStrCategory());
        return mealLocalDataSource.insertMealDetailToFavorite(mealsItem);

    }


//
//    @Override
//    public Completable insertWeekPlanMeal(WeekPlan weekPlan) {
//        insertMealRemoteToWeekPlan(weekPlan);
//      return   mealLocalDataSource.insertWeekPlanMealToCalender(weekPlan);
//    }

    @Override
    public Completable insertMealToFavorite(MealsItem mealsItem) {
        Log.i("moneerinsert", mealsItem.getStrCategory());
        return mealLocalDataSource.insertMealToFavorite(mealsItem);

    }



    @Override
    public void deleteMealFromFavorite(MealsItem mealsItem) {
        mealLocalDataSource.deleteMealFromFavorite(mealsItem);
    }

    @Override
    public Flowable<List<MealsItem>> getAllFavoriteStoredMeals() {
        Log.i("tag", "getAllFavoriteStoredMeals: Method called");

        return mealLocalDataSource.getAllFavoriteStoredMeals()
                .doOnNext(meals -> {
                    // Log the data retrieved
                    if (meals.isEmpty()) {
                        Log.i("tag", "No favorite meals stored.");
                    } else {
                        Log.i("tag", "Data retrieved: " + meals.toString());
                    }
                })
                .doOnError(throwable -> {
                    // Log any errors
                    Log.e("tag", "Error retrieving data", throwable);
                });


    }




    @Override
    public void deleteMealDetailFromFavorite(MealsDetail mealsDetail) {
       mealLocalDataSource.deleteMealDetailFromFavorite(mealsDetail);
    }



    @Override
    public Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail() {
        return mealLocalDataSource.getAllFavoriteStoredMealsDetail()
                .doOnNext(mealsdetail -> {
                    // Log the data retrieved
                    if (mealsdetail.isEmpty()) {
                        Log.i("tag", "No favorite mealsdetails stored.");
                    } else {
                        Log.i("tag", "Datadetails retrieved: " + mealsdetail.toString());
                    }
                })
                .doOnError(throwable -> {
                    // Log any errors
                    Log.e("tag", "Error retrieving data", throwable);
                });
    }





    @Override
    public Flowable<List<WeekPlan>> getWeekPlanMeals() {
        Log.d("showdata", "getWeekPlanMeals: ");
        return mealLocalDataSource.getWeekPlanMeals();


    }

    @Override
    public Completable insertWeekPlanMealToCalender(WeekPlan weekPlan) {
        insertMealRemoteToWeekPlan(weekPlan);
        Log.d("calender", "insertWeekPlanMealToCalender: "+weekPlan.getStrCategory());
        return mealLocalDataSource.insertWeekPlanMealToCalender(weekPlan);

    }

    @Override
    public void deleteWeekPlanMealFromCalender(WeekPlan weekPlan) {
    mealLocalDataSource.deleteWeekPlanMealFromCalender(weekPlan);
    }

    @Override
    public Flowable<List<WeekPlan>> getMealsForDate(String date) {
        Log.d("showdata", "getMealsForDate: ");
        return mealLocalDataSource.getMealsForDate(date);

    }

    @Override
    public void deleteAllTheCalenderList() {

        mealLocalDataSource.deleteAllTheCalenderList();
    }

    @Override
    public void deleteAllTheFavoriteList() {

        mealLocalDataSource.deleteAllTheFavoriteList();
    }


    //remote

    @Override
    public Completable insertMealRemoteToFavorite(MealsItem mealsItem) {
        Log.i("moneer", "insertMealRemoteToFavorite: " + mealsItem.getStrCategory());

        return Completable.fromAction(() -> {

                    remoteDatabaseImp.insertToFavoriteremote(mealsItem);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());




    }

    @Override
    public Completable insertMealRemoteToWeekPlan(WeekPlan weekPlan) {

      return  Completable.fromAction(() -> {

                  remoteDatabaseImp.insertToWeekPlan(weekPlan);
              })
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());



    }
///////////////////////////////////////
    @Override
    public void deleteMealRemoteFromFavorite(MealsItem mealsItem) {
        remoteDatabaseImp.deleteFromFavorite(mealsItem);
    }






    public void deleteMealRemoteFromWeekPlan(WeekPlan weekPlan) {
        remoteDatabaseImp.deleteFromWeekPlane(weekPlan);

    }
}
