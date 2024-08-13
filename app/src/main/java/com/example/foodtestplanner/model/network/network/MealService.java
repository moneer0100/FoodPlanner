package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.AreaitemResponse;
import com.example.foodtestplanner.model.dto.CategorisItemResponse;
import com.example.foodtestplanner.model.dto.IngridentItemResponse;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItemResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
//call
    public Single<MealsItemResponse> getRandomMeal();
    @GET("search.php")
    public Single<MealsItemResponse>searchByName(@Query("s") String mealName);
    @GET("lookup.php")
    public Single<MealsDetailResponse> getMealById(@Query("i") String id);
    @GET("filter.php")
    public Single<ListDetailsResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    public Single<ListDetailsResponse> getMealsByIngredient(@Query("i") String category);
    @GET("filter.php")
    public Single<ListDetailsResponse> getMealsByArea(@Query("a") String category);

    @GET("categories.php")
    public Single<CategorisItemResponse>getCategory();
    @GET("list.php?i=list")
    public Single<IngridentItemResponse>getIngredients();
    @GET("list.php?a=list")
    public Single<AreaitemResponse>getAreas();

}
