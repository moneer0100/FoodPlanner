package com.example.foodtestplanner.model.network.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

@Database(entities = {MealsItem.class, WeekPlan.class , MealsDetail.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MealDao getMealsDAO();
    private static AppDataBase instance = null;
    private MealDao mealDAO;
    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , AppDataBase.class,"MY_ROOM_DATABASE").build();

        return instance;
    }

}
