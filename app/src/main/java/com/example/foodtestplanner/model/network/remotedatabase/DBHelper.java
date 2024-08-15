package com.example.foodtestplanner.model.network.remotedatabase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.example.foodtestplanner.model.network.db.AppDataBase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private DatabaseReference databaseReference;
    public DBHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public Task<Void> addMealToFavorite(String email, MealsItem mealsItem) {
        if (mealsItem == null) {
            Log.e("DBHelper", "MealsItem is null, cannot add to Firebase");
            return null; // Or throw an exception
        }

        Log.i("DBHelper", "Adding meal to Firebase: " + mealsItem.getStrCategory());

        return databaseReference.child("favMeal").child(email).child(mealsItem.getIdMeal()).setValue(mealsItem)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("DBHelper", "Meal added successfully to Firebase");
                    } else {
                        Log.e("DBHelper", "Failed to add meal to Firebase", task.getException());
                    }
                });}

    public void getAllFavorite( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("favMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<MealsItem> favItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            favItemList.add(data.getValue(MealsItem.class));
                            Log.i("data", "onDataChange: "+favItemList.size());
                        }
                        new Thread(()->{  for (MealsItem item : favItemList){
                            AppDataBase.getInstance(context).getMealsDAO().insertMealToFavorite(item);
                        }}).start();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getAllFavoriteWeelPlan( Context context) {
        String encodeEmail = encodeEmailForFirebase(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.child("planMeal")
                .child(encodeEmail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<WeekPlan>weekPlanItemList = new ArrayList<>();

                        for(DataSnapshot data: snapshot.getChildren()){
                            weekPlanItemList.add(data.getValue(WeekPlan.class));
                        }
                        new Thread(()->{  for (WeekPlan weekPlan  : weekPlanItemList){
                            AppDataBase.getInstance(context).getMealsDAO().insertWeekPlanMealToCalender(weekPlan);
                        }}).start();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void deleteMealFromFavorite(String email, MealsItem mealsItem) {
        databaseReference.child("favMeal").child(email).child(mealsItem.getIdMeal()).removeValue();
    }

    public Task<Void> addMealToPlan(String email, WeekPlan weekPlan) {
     return    databaseReference.child("planMeal").child(email).child(weekPlan.getIdMeal()).setValue(weekPlan);
    }

    public void deleteMealPlan(String email, WeekPlan weekPlan) {
        databaseReference.child("planMeal").child(email).child(weekPlan.getIdMeal()).removeValue();
    }
    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }

}
