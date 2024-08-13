package com.example.foodtestplanner.ingrident;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.ingrident.presenter.IngridentImp;
import com.example.foodtestplanner.ingrident.presenter.IngridentPresenter;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.IngridentItem;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class IngridentDetailsFragment extends Fragment implements IngridentView,OnClickIngrident{
    private Context context;
    private IngridentAdapter ingredientDetailsAdapter;
    private RecyclerView recyclerView;
    Single<ListDetailsResponse> ingredientDetailsList;
    private IngridentPresenter ingredientDetailsPresenterView;
    private LinearLayoutManager linearLayoutManager;
    CardView randomCardView;
    IngridentItem ingredientsItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleid1);
        ingredientDetailsAdapter = new IngridentAdapter(new ArrayList<>(),this,context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ingredientDetailsPresenterView = new IngridentImp(this, MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity()))) {
        };

        ingredientsItem = (IngridentItem) getArguments().getSerializable("ingredient");
        Toast.makeText(requireActivity(), "strmeal"+ingredientsItem.getIngreidant(), Toast.LENGTH_SHORT).show();

        if (ingredientsItem != null) {
            ingredientDetailsList = ingredientDetailsPresenterView.getIngredientDetail(ingredientsItem.getIngreidant());
            ingredientDetailsList.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                ingredientDetailsAdapter.setListDetails(item.getMeals());
                                recyclerView.setAdapter(ingredientDetailsAdapter);
                            },
                            throwable -> {
                                Log.i("TAG", "showIngredientDetail: unable to show meal ingredient because: " + throwable.getMessage());
                            });
        }
    }

    @Override
    public void showIngredientDetailsData(List<ListDetails> ingridentDetailsList) {

    }

    @Override
    public void showIngredientDetailsErrorMsg(String error) {

    }

    @Override
    public void onIngredientClick(ListDetails category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredientDetails", category);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_ingridentDetailsFragment_to_listDetailsFragment, bundle);
    }
}