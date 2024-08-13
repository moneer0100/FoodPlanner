package com.example.foodtestplanner.listDetails.view;

import static com.example.foodtestplanner.mealDetails.view.MealDetails.getDateString;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodtestplanner.R;
import com.example.foodtestplanner.listDetails.presenter.ListDetailsImp;
import com.example.foodtestplanner.listDetails.presenter.ListDetailspresenter;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.GeneratingListIngridentsArrayLists;
import com.example.foodtestplanner.model.dto.ListDetails;
import com.example.foodtestplanner.model.dto.MealsDetail;
import com.example.foodtestplanner.model.dto.MealsDetailResponse;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ListDetailsFragment extends Fragment implements ListMealView, OnListMealClickListner {
    private ImageView calender;
    private ImageView image;
    private TextView tvItemName;
    private TextView tvItemCountry;
    private TextView tvItemCategory;
    private TextView description;
    private ImageView addToFavImage;
    private ListDetailspresenter listDetailspresenter; // Use this single presenter reference
    private ListDetails listsDetails;
    private ListDetails listAreaDetails;
    private ListDetails listingredientDetails;
    private MealsItem searchByName;
    private MealsItem favMeal;
    private WeekPlan weekPlanMeal;
    private Context context;
    private MealsItem mealsItem;

    private RecyclerView recyclerView;
    private Single<MealsDetailResponse> mealsDetailList;
    MealsDetail mealsItems;
    private YouTubePlayerView youTubePlayerView;
    private MealRepositoryView mealRepositoryView;
    LinearLayoutManager linearLayoutManager;
    ListDetailsAdapter listDetailsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_details, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        image = view.findViewById(R.id.imag_meal_id);
        tvItemCountry = view.findViewById(R.id.country_lid);
        tvItemCategory = view.findViewById(R.id.Meal_id);
        description = view.findViewById(R.id.text_meal_description);
        addToFavImage = view.findViewById(R.id.fac_list_imag_id);
        youTubePlayerView = view.findViewById(R.id.vedioId_meal);
        recyclerView = view.findViewById(R.id.recyviewmealidc);
        calender = view.findViewById(R.id.calender);

        // Initialize layout manager and adapter
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        listDetailsAdapter = new ListDetailsAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(listDetailsAdapter);

        // Initialize presenter
        listDetailspresenter = new ListDetailsImp(this, MealRepositoryImpl.getInstance(
                MealRemotDataSourceImp.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())
        ));

        // Get arguments
        listsDetails = (ListDetails) getArguments().getSerializable("categoryDetail");
        listAreaDetails = (ListDetails) getArguments().getSerializable("areaDetails");
        listingredientDetails = (ListDetails) getArguments().getSerializable("ingredientDetails");
        searchByName = (MealsItem) getArguments().getSerializable("SearchByName");
        favMeal = (MealsItem) getArguments().getSerializable("Favorite");
        weekPlanMeal = (WeekPlan) getArguments().getSerializable("weekPlan");
        mealsItem = (MealsItem) getArguments().getSerializable("item");

        // Check if mealsItem is null
        if (mealsItem == null) {
            Log.e("ListDetailsFragment", "mealsItem is null. Check how it's being passed.");
        }

        // Fetch meal details
        Single<MealsDetailResponse> mealsDetailSingle = listDetailspresenter
                .LIST_DETAILS_RESPONSE_SINGLE(listsDetails != null ? listsDetails.getIdMeal() :
                        listAreaDetails != null ? listAreaDetails.getIdMeal() :
                                listingredientDetails != null ? listingredientDetails.getIdMeal() :
                                        searchByName != null ? searchByName.getIdMeal() :
                                                favMeal != null ? favMeal.getIdMeal() :
                                                        weekPlanMeal != null ? weekPlanMeal.getIdMeal() : "");

        mealsDetailSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsDetailResponse -> {
                    MealsDetail mealsDetail = mealsDetailResponse.getMeals().get(0);

                    tvItemCountry.setText(mealsDetail.getStrArea());
                    tvItemCategory.setText(mealsDetail.getStrCategory());
                    description.setText(mealsDetail.getStrInstructions());
                    Glide.with(requireActivity()).load(mealsDetail.getStrMealThumb()).into(image);
                    listDetailsAdapter.setMealsItemList(GeneratingListIngridentsArrayLists.getIngridentsListArray(mealsDetail));

                    addToFavImage.setOnClickListener(v -> {
                        listDetailspresenter.addToFavDetails(mealsDetail);
                        Toast.makeText(requireContext(), "u are add to fav", Toast.LENGTH_SHORT).show();
                    });

                    calender.setOnClickListener(view1 -> {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                String date = getDateString(year, month, dayOfMonth);

                                if (mealsItem != null) {
                                    WeekPlan weekPlan = new WeekPlan();
                                    weekPlan.setDate(date);
                                    weekPlan.setMealData(mealsItem);
                                    addToCalendar(weekPlan);
                                } else {
                                    Log.e("ListDetailsFragment", "mealsItem is null when setting date");
                                    Toast.makeText(requireContext(), "Meal data is not available", Toast.LENGTH_SHORT).show();
                                }
                            }

                            private void addToCalendar(WeekPlan weekPlan) {
                                listDetailspresenter.SetClickedItemData(weekPlan);
                            }
                        }, year, month, dayOfMonth);
                        datePickerDialog.show();
                    });

                    String youtubeVideoId = mealsDetail.getStrYoutube();
                    if (youtubeVideoId != null && !youtubeVideoId.isEmpty()) {
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                loadVideo(youTubePlayer, youtubeVideoId);
                            }
                        });
                    } else {
                        // Hide YouTube player if there's no video URL
                        youTubePlayerView.setVisibility(View.GONE);
                    }
                }, throwable -> {
                    Log.e("TAG", "Error fetching meal details: " + throwable.getMessage());
                    Toast.makeText(requireContext(), "Error fetching meal details", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void showListmealDitails(List<MealsDetail> mealsItems) {
    }

    @Override
    public void showListitemError(String errormsg) {
        Toast.makeText(requireActivity(), "errormsg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMealToFav(MealsDetail mealsItem) {
        mealRepositoryView.insertMealToFavoritDetails(mealsItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        youTubePlayerView.release();
    }

    private void loadVideo(@NonNull YouTubePlayer youTubePlayer, String youtubeVideoId) {
        youTubePlayer.loadVideo(getVideoId(youtubeVideoId), 0);
    }

    private String getVideoId(String videoUrl) {
        String videoId = null;
        String[] urlParts = videoUrl.split("v=");
        if (urlParts.length > 1) {
            videoId = urlParts[1];
        }
        return videoId;
    }

    @Override
    public void onclickListmeal(MealsItem MealsItem) {
    }
}