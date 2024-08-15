package com.example.foodtestplanner.model.network.remotedatabase;

import android.util.Log;

import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;

public class RemoteDatabaseImp implements  RemoteDatabaseListener{
    private DBHelper dbHelper;
    private FirebaseAuth firebaseAuth;

    public RemoteDatabaseImp() {
        dbHelper = new DBHelper();
        firebaseAuth = FirebaseAuth.getInstance();
    }



    @Override
    public Completable insertToFavoriteremote(MealsItem mealsItem) {
        if (mealsItem == null) {
            Log.e("RemoteDatabaseImp", "insertToFavoriteremote: MealsItem is null");
            return Completable.error(new NullPointerException("MealsItem is null"));
        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e("RemoteDatabaseImp", "insertToFavoriteremote: User is not authenticated");
            return Completable.error(new IllegalStateException("User is not authenticated"));
        }

        String email = currentUser.getEmail();
        String encodeEmail = encodeEmailForFirebase(email);

        // Log the Firebase path
        String path = "favMeal/" + encodeEmail + "/" + mealsItem.getIdMeal();
        Log.i("RemoteDatabaseImp", "Firebase Path: " + path);

        // Return a Completable that wraps the Firebase operation
        return Completable.create(emitter -> {
            dbHelper.addMealToFavorite(encodeEmail, mealsItem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i("RemoteDatabaseImp", "Meal added to Firebase successfully");
                            emitter.onComplete();
                        } else {
                            Log.e("RemoteDatabaseImp", "Failed to add meal to Firebase", task.getException());
                            emitter.onError(task.getException());
                        }
                    });
        });

    }

    @Override
    public Completable insertToWeekPlan(WeekPlan weekPlan) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e("RemoteDatabaseImp", "insertToWeekPlan: User is not authenticated");
            return Completable.error(new IllegalStateException("User is not authenticated"));
        }

        String email = currentUser.getEmail();
        String encodeEmail = encodeEmailForFirebase(email);

        // Log the Firebase path
        String path = "planMeal/" + encodeEmail + "/" + weekPlan.getIdMeal();
        Log.i("RemoteDatabaseImp", "Firebase Path: " + path);

        // Return a Completable that wraps the Firebase operation
        return Completable.create(emitter -> {
            dbHelper.addMealToPlan(encodeEmail, weekPlan)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i("RemoteDatabaseImp", "Week plan added to Firebase successfully");
                            emitter.onComplete();
                        } else {
                            Log.e("RemoteDatabaseImp", "Failed to add week plan to Firebase", task.getException());
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public void deleteFromWeekPlane(WeekPlan weekPlan) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        String encodeEmail = encodeEmailForFirebase(email);
        dbHelper.deleteMealPlan(encodeEmail,weekPlan);
    }

    @Override
    public void deleteFromFavorite(MealsItem mealsItem) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        String encodeEmail = encodeEmailForFirebase(email);
        dbHelper.deleteMealFromFavorite(encodeEmail,mealsItem);
    }
    private String encodeEmailForFirebase(String email) {
        return email.replace(".", ",");
    }
}
