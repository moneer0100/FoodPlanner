package com.example.foodtestplanner.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.favourite.presenter.FavImp;
import com.example.foodtestplanner.favourite.presenter.FavPresenter;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavFragment extends Fragment implements FavView,OnVlickFav{

    private FavPresenter favouriteMealPresenterView;
    private RecyclerView recyclerView;
    private FavAdaptor favMealsAdapter;
    private Flowable<List<MealsItem>> favMealsList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.favRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext()
        ,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        favMealsAdapter=new FavAdaptor(getContext(),new ArrayList<>(),this);
        recyclerView.setAdapter(favMealsAdapter);

        favouriteMealPresenterView=new FavImp(MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance()
        , MealLocalDataSourceImpl.getInstance(getContext())),this);

    }

    @Override
    public void showFavItem(MealsItem mealsItem) {
        favMealsList = favouriteMealPresenterView.getFavMealList();
        favMealsList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsItemList -> {
                    //setFavMealsList(mealsItemList);
                    favMealsAdapter.setMealsItems(mealsItemList);
                    favMealsAdapter.notifyDataSetChanged();
                }, throwable -> {
                    Log.i("TAG", "Unable to show Meal because: "+throwable.getMessage());
                });
    }

    @Override
    public void showFavERROR(String error) {
        Toast.makeText(requireActivity(), "no data come ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemClick(MealsItem mealsItem) {
        favouriteMealPresenterView.deleteMeal(mealsItem);
        Toast.makeText(requireActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }
}