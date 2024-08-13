package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.AreaItem;
import com.example.foodtestplanner.model.dto.AreaitemResponse;
import com.example.foodtestplanner.model.dto.CategorisItem;
import com.example.foodtestplanner.model.dto.CategorisItemResponse;
import com.example.foodtestplanner.model.dto.IngridentItem;
import com.example.foodtestplanner.model.dto.IngridentItemResponse;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.MealsItemResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemotDataSourceImp implements MealRemoteDataSource {
    List<MealsItem>mealsItemList;
    List<CategorisItem>categoriesItemList;
    List<IngridentItem> ingredientsItemResponses;
    List<AreaItem> areaItemList;
    String error;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealRemotDataSourceImp mealRemotDataSourceImp = null;
    private MealService mealService;
public static Retrofit retrofit=null;
    public MealRemotDataSourceImp() {
        Gson gson= new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);


    }
    public static MealRemotDataSourceImp getInstance() {
        if (mealRemotDataSourceImp == null)
            mealRemotDataSourceImp = new MealRemotDataSourceImp();

        return mealRemotDataSourceImp;
    }
    public void CategoryNetworkCall(CategoryCallBack categoryCallBack){
        Single<CategorisItemResponse> categoriesItemResponseSingle = mealService.getCategory();

        categoriesItemResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategorisItemResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull CategorisItemResponse categorisItemResponse) {
                    categoryCallBack.onSuccessResult(categorisItemResponse.getCategories());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        categoryCallBack.onFailureResult(e.getMessage());
                    }
                });

    }

    @Override
    public void RandomMealNetworkCall(RandomMealCallBack networkCallback) {
        Single<MealsItemResponse> mealsItemResponseSingle = mealService.getRandomMeal();
        mealsItemResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item-> {
                    mealsItemList = item.getRandomMealList();
                    networkCallback.onSuccessResult(mealsItemList);
                },throwable -> {
                    error=throwable.getLocalizedMessage();
                    networkCallback.onFailureResult(error);


                });
    }

    @Override
    public void IngredientsNetworkCall(IngridentCallBack ingredientsCallback) {
        Single<IngridentItemResponse> ingredientsItemResponseSingle = (Single<IngridentItemResponse>) mealService.getIngredients();
        ingredientsItemResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item-> {
                    ingredientsItemResponses=item.getIngridentItems();
                    ingredientsCallback.onSuccessResult(ingredientsItemResponses);
                },throwable -> {
                    error=throwable.getLocalizedMessage();
                    ingredientsCallback.onFailureResult(error);
                });
    }

    @Override
    public void AreasNetworkCall(AreaCallBack areaMealCallback) {
        Single<AreaitemResponse> areaItemResponseSingle = mealService.getAreas();
        areaItemResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item-> {
                    areaItemList = item.getAreaList();
                    areaMealCallback.onSuccessResult(areaItemList);
                }, throwable -> {
                    error=throwable.getLocalizedMessage();
                    areaMealCallback.onFailureResult(error);

                });

    }

    @Override
    public Single<ListDetailsResponse> CategoryDetailsNetworkCall(String category) {
        return mealService.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ListDetailsResponse> IngredientDetailsNetworkCall(String category) {
        return mealService.getMealsByIngredient(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ListDetailsResponse> AreaDetailsNetworkCall(String category) {
        return mealService.getMealsByArea(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealsItemResponse> searchByNameNetworkCall(String name) {
        return mealService.searchByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<MealsDetailResponse> getMealDetailNetworkCall(String name) {
        return mealService.getMealById(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
