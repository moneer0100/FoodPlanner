package com.example.foodtestplanner.country.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.country.presenter.AreaDetailsImp;
import com.example.foodtestplanner.country.presenter.AreaDetailsPresenter;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.AreaItem;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.ListDetailsResponse;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AreaDetailsFragment extends Fragment implements AreaDetailsView, OnClickAreaListner {

    private Context context;
    private AreaAdaptor areaDetailsAdapter;
    private RecyclerView recyclerView;
    Single<ListDetailsResponse> areaDetailsList;
    private AreaDetailsPresenter areaDetailsPresenterView;

    CardView areaCardView;
    AreaItem areaItem;



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
        recyclerView=view.findViewById(R.id.recycleid1);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext(),
         LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        areaDetailsAdapter=new AreaAdaptor(new ArrayList<>(),this,context);
        recyclerView.setAdapter(areaDetailsAdapter);
        areaDetailsPresenterView=new AreaDetailsImp(MealRepositoryImpl
        .getInstance(MealRemotDataSourceImp.getInstance()
         , MealLocalDataSourceImpl.getInstance(getContext())),this);

        areaItem = (AreaItem) getArguments().getSerializable("area");
        Toast.makeText(requireActivity(), "strmeal"+areaItem.getArea(), Toast.LENGTH_SHORT).show();

        if (areaItem != null) {
            areaDetailsList = areaDetailsPresenterView.getAreaDetail(areaItem.getArea());
            areaDetailsList.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                areaDetailsAdapter.setListDetails(item.getMeals());
                                recyclerView.setAdapter(areaDetailsAdapter);
                            },
                            throwable -> {
                                Log.i("TAG", "showIngredientDetail: unable to show meal ingredient because: " + throwable.getMessage());
                            });
        }


    }

    @Override
    public void showAreaDetails(List<ListDetails> showAreaitem) {

    }

    @Override
    public void showAreaError(String error) {

    }

    @Override
    public void onclickDetailes(ListDetails listDetailsOnClick) {
    Bundle bundle=new Bundle();
    bundle.putSerializable("areaDetails" ,listDetailsOnClick);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_areaDetailsFragment_to_listDetailsFragment,bundle);
    }
}