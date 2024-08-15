package com.example.foodtestplanner.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.login.Login;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.example.foodtestplanner.model.network.remotedatabase.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView navigationView;
    NavController navController;
    Toolbar toolbar;
    MealRepositoryView mealRepositoryView;
    ImageView imageView;
    public boolean isGuestMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.logout1);
        imageView.setOnClickListener(view -> {

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("guestMode")) {
            isGuestMode = extras.getBoolean("guestMode", false);
        }

        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.randomFragment, R.id.searchFragment, R.id.favFragment, R.id.calenderFragment).build();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController
         , appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        mealRepositoryView = MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(),
                MealLocalDataSourceImpl.getInstance(this));

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            new DBHelper().getAllFavorite(this);
            new DBHelper().getAllFavoriteWeelPlan(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isGuestMode) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.top_bar, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main) {
            clearLocalFavoriteData();
            clearLocalCalendarData();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearLocalFavoriteData() {
        mealRepositoryView.deleteAllTheFavoriteList();
    }

    private void clearLocalCalendarData() {
        mealRepositoryView.deleteAllTheCalenderList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp();
    }

    public void onItemClickListener(MealsItem mealsItem) {
        if (isGuestMode) {
            showGuestModeAlert();
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", mealsItem);
            navController.navigate(R.id.favFragment, bundle);
        }
    }

    public void showGuestModeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guest Mode");
        builder.setMessage("You need to sign in to perform this action. Do you want to sign in?");
        builder.setPositiveButton("YES", (dialog, which) -> {
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
        });
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
    public boolean isGuestMode() {
        return isGuestMode;
    }
}