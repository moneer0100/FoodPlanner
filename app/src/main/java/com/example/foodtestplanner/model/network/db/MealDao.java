package com.example.foodtestplanner.model.network.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
@Dao
public interface MealDao {
    @Query("SELECT * FROM MEALS")
    Flowable<List<MealsItem>> getAllFavoriteMeals();
    @Query("SELECT * FROM mealdetail")
    Flowable<List<MealsDetail>> getAllMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealToFavorite(MealsItem mealsItem);
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    Completable insertMealDetailToFavorite(MealsDetail mealsDetail);
    @Delete
    void deleteMealFromFavorite(MealsItem mealsItem);
    @Delete
    void deleteMealDetailFromFavorite(MealsDetail mealsDetail);


    @Query("SELECT * FROM weekplan")
    Flowable<List<WeekPlan>> getWeekPlanMeals();

    @Query("SELECT * FROM weekplan WHERE date = :date")
    Flowable<List<WeekPlan>> getMealsForDate(String date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertWeekPlanMealToCalender(WeekPlan weekPlan);
    @Delete
    void deleteWeekPlanMealFromCalender(WeekPlan weekPlan);


    @Query("DELETE FROM weekplan")
    void deleteAllTheCalenderList();

    @Query("DELETE FROM meals")
    void deleteAllTheFavoriteList();



}
