package com.example.foodtestplanner.category.view;

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
import com.example.foodtestplanner.category.presenter.CategoryDetailsPresenterImp;
import com.example.foodtestplanner.category.presenter.CategoryDetailsPresenterView;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.CategorisItem;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CategoryFragment extends Fragment implements CategoryDetailsView ,OnCategoryDetailsClickListener {
    private Context context;
    private CategoryDetailsAdapter categoryDetailsAdapter;
    private RecyclerView recyclerView;
    Single<ListDetailsResponse> categoryDetailsList;
    private CategoryDetailsPresenterView categoryDetailsPresenterView;
    private LinearLayoutManager linearLayoutManager;
    CardView randomCardView;
   CategorisItem category;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.categoryDetailsRecyclerView);
        categoryDetailsAdapter = new CategoryDetailsAdapter(requireActivity(),new ArrayList<>(),this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        categoryDetailsPresenterView = new CategoryDetailsPresenterImp(this, MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())));

        category = (CategorisItem) getArguments().getSerializable("category");
        Toast.makeText(requireActivity(), "strmeal"+category.getCategory(), Toast.LENGTH_SHORT).show();
        if (category != null) {
            categoryDetailsList = categoryDetailsPresenterView.getCategoryDetail(category.getCategory());
            Log.i("TAG", "s()categoryDetailsList " +categoryDetailsList);
            categoryDetailsList.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                categoryDetailsAdapter.setList(item.getMeals());
                                recyclerView.setAdapter(categoryDetailsAdapter);
                            },
                            throwable -> {
                                Log.i("TAG", "showCategoryDetail: unable to show products because: " + throwable.getMessage());
                            });
        }
    }

    @Override
    public void showCategoryDetailsData(List<ListDetails> categoryDetailsList) {
        categoryDetailsAdapter.setList(categoryDetailsList);



        Log.d("TAG", "Success: " + categoryDetailsList.size());
    }

    @Override
    public void showCategoryDetailsErrorMsg(String error) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCategoryClick(ListDetails listsDetails) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("categoryDetail", listsDetails);
        Navigation.findNavController(requireView()).navigate(R.id.action_categoryFragment_to_listDetailsFragment, bundle);
    }
    }

