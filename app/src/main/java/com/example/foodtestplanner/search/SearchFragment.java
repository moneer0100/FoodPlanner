package com.example.foodtestplanner.search;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.AreaItem;
import com.example.foodtestplanner.model.dto.IngridentItem;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.example.foodtestplanner.search.presenter.AreaImp;
import com.example.foodtestplanner.search.presenter.AreaPresenter;
import com.example.foodtestplanner.search.presenter.IngridentImp;
import com.example.foodtestplanner.search.presenter.IngridentPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements AreaView,IngridentView,OnAreaClickListner,OnClickIngridentListner {
    private Context context;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView areasRecyclerView;
    private EditText searchByName;
    private List<IngridentItem> ingredientsItemList;
    private List <AreaItem>areaItemList;
    private ImageView image;
    private TextView mealName;
    private IngridentPresenter ingredientsPresenterView;
    private AreaPresenter areasPresenterView;
    private LinearLayoutManager ingredientsLinearLayoutManager;
    private LinearLayoutManager areasLinearLayoutManager;
    private IngridentSearchAdapter ingredientSearchAdapter;
    private AreaSearchAdapter areaSearchAdapter;
    private CardView ingredientCardView;
    private LottieAnimationView lottieAnimationView;
    private NestedScrollView nestedScrollView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_search, container, false);
//       ingredientsPresenterView=new IngridentImp(MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(),MealLocalDataSourceImpl.getInstance(getContext())), this);
//       ingredientsPresenterView.getIngredient();
//
//
//        areasPresenterView = new AreaImp(this, MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(),MealLocalDataSourceImpl.getInstance(requireActivity())));
//       areasPresenterView.getArea();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchByName = view.findViewById(R.id.txtSearch);
        searchByName.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_searchByNameFragment));

        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));

        areasRecyclerView = view.findViewById(R.id.countryRecyclerView);
        areasRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));

        ingredientSearchAdapter = new IngridentSearchAdapter(new ArrayList<>(), getContext(), this);
        ingredientsRecyclerView.setAdapter(ingredientSearchAdapter);

        ingredientsPresenterView = new IngridentImp(MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())), this);
        ingredientsPresenterView.getIngredient();

        areaSearchAdapter = new AreaSearchAdapter(getContext(),new ArrayList<>(), this);
        areasRecyclerView.setAdapter(areaSearchAdapter);

        areasPresenterView = new AreaImp(this, MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())));
        areasPresenterView.getArea();

    }

    @Override
    public void showAreasData(List<AreaItem> AreaItemList) {
        if (AreaItemList != null) {
            areaSearchAdapter.setAreaItems(AreaItemList);
            areaSearchAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(requireActivity(), "Ingredients list is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showAreasErrorMsg(String error) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIngredientsData(List<IngridentItem> IngredientsItemList) {
        if (IngredientsItemList != null) {
            ingredientSearchAdapter.setIngridentItemList(IngredientsItemList);
            ingredientSearchAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(requireActivity(), "Ingredients list is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showIngredientsErrorMsg(String error) {

    }

    @Override
    public void onAreaClick(AreaItem areaItem) {
        Bundle areaBundle = new Bundle();
        areaBundle.putSerializable("area", (Serializable) areaItem);
        Toast.makeText(requireActivity(), "ingredient"+areaItem, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_areaDetailsFragment, areaBundle);

    }

    @Override
    public void onIngredientClick(IngridentItem ingredientsItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ingredient", (Serializable) ingredientsItem);
        Toast.makeText(requireActivity(), "ingredient"+ingredientsItem, Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_ingridentDetailsFragment, bundle);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
}