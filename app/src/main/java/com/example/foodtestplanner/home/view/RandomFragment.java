package com.example.foodtestplanner.home.view;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.home.presenter.CategoryMealpresen;
import com.example.foodtestplanner.home.presenter.Categorymealpresent;
import com.example.foodtestplanner.home.presenter.RandomMealpresent;
import com.example.foodtestplanner.home.presenter.RandomMealpresntv;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.CategorisItem;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RandomFragment extends Fragment implements CategoryMealview, OnCategoryClickListner, RandomMealView {
    private Context context;
    private AdaptorCategory adaptorCategory;
    RecyclerView recyclerView ;
    List<MealsItem>mealsItems;
    private LinearLayoutManager linearLayoutManager;
    private ImageView image;
    private TextView meal;
    CardView randomCardView;
    private LottieAnimationView lottieAnimationView;
    private NestedScrollView nestedScrollView;
    private RandomMealpresent randomMealpresent;
    private Categorymealpresent categorymealpresent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_random, container, false);
        meal=view.findViewById(R.id.countryRandomid);
        image=view.findViewById(R.id.imageRandomid);
        randomMealpresent = new RandomMealpresntv(this,MealRepositoryImpl.getInstance(MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())));
        randomMealpresent.getMeal();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views by ID
        recyclerView = view.findViewById(R.id.CategoryRecRandoid);
        randomCardView = view.findViewById(R.id.cardView);

        // Set up the layout manager for the RecyclerView
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        // Initialize the adapter with an empty list initially
        adaptorCategory = new AdaptorCategory(getContext(), new ArrayList<>(), this);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adaptorCategory);

        // Initialize the presenter and load categories
        categorymealpresent = new CategoryMealpresen(this,
                MealRepositoryImpl.getInstance(
                        MealRemotDataSourceImp.getInstance(),
                        MealLocalDataSourceImpl.getInstance(getContext())
                )
        );

        // Set the click listener for the adapter
        adaptorCategory.setOnCategoryClickListner(this);

        // Fetch the categories
        categorymealpresent.getcategorymeal();
    }





    @Override
    public void showcategorydata(List<CategorisItem> categorisItemList) {
        Log.e("Sobaih", categorisItemList.size() + "siza");

        // Update the existing adapter with new data
        adaptorCategory.setCategorisItemList(categorisItemList);

        // notifyDataSetChanged is already called in setCategorisItemList, so no need to call it again.

        Log.d("TAG", "Success: " + categorisItemList.size());
    }

    @Override
    public void showErrorMassCategory(String error) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onclickCategory(CategorisItem categorisItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", (Serializable) categorisItem);
        Toast.makeText(requireActivity(), "category" + categorisItem.getCategory(), Toast.LENGTH_SHORT).show();

        Navigation.findNavController(requireView()).navigate(R.id.action_randomFragment_to_categoryFragment, bundle);

    }

    @Override
    public void showRandomMealView(List<MealsItem> mealsItems) {
        MealsItem item = mealsItems.get(0);
        meal.setText(item.getStrMeal());

        Glide.with(requireContext()).load(item.getStrMealThumb()).into(image);
        randomCardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            Navigation.findNavController(v).navigate(R.id.action_randomFragment_to_mealDetails, bundle);
        });
    }

    @Override
    public void showmassagrandomview(String erromsg) {

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