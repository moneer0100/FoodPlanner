package com.example.foodtestplanner.model.network.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Dao;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImpl implements  MealLocalDataSource{
    private MealDao mealDAO;
    private static MealLocalDataSourceImpl mealLocalDataSource = null;
    private Flowable<List<MealsItem>> storedFavoriteMeals;
    private Flowable<List<MealsDetail>> storedFavoriteMealsDetail;
    private Flowable<List<WeekPlan>> storedWeekPlanMeals;

    private MealLocalDataSourceImpl(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getMealsDAO();
        storedFavoriteMeals = mealDAO.getAllFavoriteMeals();
        storedWeekPlanMeals = mealDAO.getWeekPlanMeals();
        storedFavoriteMealsDetail = mealDAO.getAllMeals();
    }
    public static MealLocalDataSourceImpl getInstance(Context context) {
        if (mealLocalDataSource == null)
            mealLocalDataSource = new MealLocalDataSourceImpl(context);

        return mealLocalDataSource;
    }
    @Override
    public Completable insertMealToFavorite(MealsItem mealsItem) {
        Log.i("moneer","add to database"+ mealsItem.getStrCategory());
        return mealDAO.insertMealToFavorite(mealsItem);
    }

    @Override
    public void deleteMealFromFavorite(MealsItem mealsItem) {
        new Thread(() -> {
            mealDAO.deleteMealFromFavorite(mealsItem);
        }).start();
    }

    @Override
    public Flowable<List<MealsItem>> getAllFavoriteStoredMeals() {
        Log.d("moneer", "getAllFavoriteStoredMeals: ");
        return storedFavoriteMeals;
    }

    @Override
    public Completable insertMealDetailToFavorite(MealsDetail mealsDetail) {
        Log.i("details1", "inserting: Adding Item "+mealsDetail.getIdMeal());
        return mealDAO.insertMealDetailToFavorite(mealsDetail);

    }

    @Override
    public void deleteMealDetailFromFavorite(MealsDetail mealsDetail) {
        new Thread(() -> {
            mealDAO.deleteMealDetailFromFavorite(mealsDetail);
        }).start();
    }

    @Override
    public Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail() {

        return storedFavoriteMealsDetail;
    }

    @Override
    public Flowable<List<WeekPlan>> getWeekPlanMeals() {
        Log.d("back", "getWeekPlanMeals: ");
        return storedWeekPlanMeals;
    }

    @Override
    public Completable insertWeekPlanMealToCalender(WeekPlan weekPlan) {
        Log.i("calender", "inserting: Adding Item to calender ");
        return   mealDAO.insertWeekPlanMealToCalender(weekPlan);

    }

    @Override
    public void deleteWeekPlanMealFromCalender(WeekPlan weekPlan) {
        new Thread(() -> {
            mealDAO.deleteWeekPlanMealFromCalender(weekPlan);
            Log.i("TAG", "deleting: Removing item from calender ");
        }).start();
    }

    @Override
    public Flowable<List<WeekPlan>> getMealsForDate(String date) {
        return mealDAO.getMealsForDate(date);

    }

    @Override
    public void deleteAllTheCalenderList() {
        new Thread(() -> {
            mealDAO.deleteAllTheCalenderList();
            Log.i("TAG", "Deleting all meals from the calendar list");
        }).start();
    }

    @Override
    public void deleteAllTheFavoriteList() {
        new Thread(() -> {
            mealDAO.deleteAllTheFavoriteList();
            Log.i("TAG", "Deleting all meals from the favorite list");
        }).start();
    }
}
