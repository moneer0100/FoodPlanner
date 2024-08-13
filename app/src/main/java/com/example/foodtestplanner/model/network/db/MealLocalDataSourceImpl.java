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
        Log.i("moneer", mealsItem.getStrCategory());
        return mealDAO.insertMealToFavorite(mealsItem);
    }

    @Override
    public void deleteMealFromFavorite(MealsItem mealsItem) {
        mealDAO.deleteMealFromFavorite(mealsItem);
    }

    @Override
    public Flowable<List<MealsItem>> getAllFavoriteStoredMeals() {
        return storedFavoriteMeals;
    }

    @Override
    public Completable insertMealDetailToFavorite(MealsDetail mealsDetail) {
        Log.i("TAG", "inserting: Adding Item ");
        return mealDAO.insertMealDetailToFavorite(mealsDetail);

    }

    @Override
    public void deleteMealDetailFromFavorite(MealsDetail mealsDetail) {
    mealDAO.deleteMealDetailFromFavorite(mealsDetail);
    }

    @Override
    public Flowable<List<MealsDetail>> getAllFavoriteStoredMealsDetail() {
        return storedFavoriteMealsDetail;
    }

    @Override
    public Flowable<List<WeekPlan>> getWeekPlanMeals() {
        return storedWeekPlanMeals;
    }

    @Override
    public Completable insertWeekPlanMealToCalender(WeekPlan weekPlan) {
        Log.i("moneer", "inserting: Adding Item to calender ");
        return   mealDAO.insertWeekPlanMealToCalender(weekPlan);

    }

    @Override
    public void deleteWeekPlanMealFromCalender(WeekPlan weekPlan) {
        mealDAO.deleteWeekPlanMealFromCalender(weekPlan);
        Log.i("TAG", "deleting: Removing item from calender ");
    }

    @Override
    public Flowable<List<WeekPlan>> getMealsForDate(String date) {
        return  mealDAO.getMealsForDate(date);
    }

    @Override
    public void deleteAllTheCalenderList() {
        mealDAO.deleteAllTheCalenderList();
        Log.i("TAG", "Deleting all meals from the calendar list");
    }

    @Override
    public void deleteAllTheFavoriteList() {
        mealDAO.deleteAllTheFavoriteList();
        Log.i("TAG", "Deleting all meals from the favorite list");
    }
}
