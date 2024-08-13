package com.example.foodtestplanner.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.login.Login;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView navigationView;
    NavController navController;
    Toolbar toolbar;
    MealRepositoryView mealRepositoryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        navigationView=findViewById(R.id.navigation_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration=new AppBarConfiguration.Builder(R.id.randomFragment,R.id.searchFragment
         ,R.id.favFragment,R.id.calenderFragment).build();
      ActionBar actionBar= getSupportActionBar();
        if (actionBar != null) {
           actionBar.setDisplayHomeAsUpEnabled(true);
          actionBar.setDisplayShowHomeEnabled(true);
        }
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
        mealRepositoryView = MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance()
                ,MealLocalDataSourceImpl.getInstance(this));





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
}