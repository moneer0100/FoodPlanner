package com.example.foodtestplanner.mealDetails.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
import com.example.foodtestplanner.home.view.Home;
import com.example.foodtestplanner.listDetails.presenter.ListDetailspresenter;
import com.example.foodtestplanner.mealDetails.presenter.MealDetailsImp;
import com.example.foodtestplanner.mealDetails.presenter.MealDetailsPresenter;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryView;
import com.example.foodtestplanner.model.dto.GeneratingIngridentsArrayLists;
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
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetails extends Fragment implements MealDetailsView  {
    private MealsItem mealsItem;

    private ImageView calender;
    private ImageView image;
    private TextView tvItemName;
    private TextView tvItemCountry;
    private TextView tvItemCategory;
    private TextView description;
    private ImageView addToFavImage;
    private ListDetailspresenter listDetailPresenterView ;
    private MealsDetail mealsDetail;
    private Home homeActivity;

    private RecyclerView recyclerView;
    private Single<MealsDetailResponse> mealsDetailList;

    private YouTubePlayerView youTubePlayerView;
    private MealRepositoryView mealRepositoryView;
    LinearLayoutManager linearLayoutManager;
    MealDetailsAdapter mealDetailsAdapter;
    MealDetailsPresenter mealDetailsPresenter;
    MealRepositoryImpl mealRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            homeActivity = (Home) getActivity();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Home) {
            homeActivity = (Home) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Home interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);
        image = view.findViewById(R.id.imag_meal_id);
        tvItemCountry = view.findViewById(R.id.Meal_id);
        tvItemCategory = view.findViewById(R.id.country_lid);
        description = view.findViewById(R.id.text_meal_description);
        calender = view.findViewById(R.id.calender);
        addToFavImage = view.findViewById(R.id.fac_list_imag_id);
        youTubePlayerView = view.findViewById(R.id.vedioId_meal);
        recyclerView = view.findViewById(R.id.recyviewmealidc);
        mealsItem = (MealsItem) getArguments().getSerializable("item");

        linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        mealDetailsAdapter = new MealDetailsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(mealDetailsAdapter);

        if (getArguments() != null) {
            mealsItem = (MealsItem) getArguments().getSerializable("item");
        }

        mealDetailsPresenter = new MealDetailsImp(this, MealRepositoryImpl.getInstance(
                MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())
        ));
        addToFavImage.setOnClickListener(v -> {
            if (homeActivity != null && homeActivity.isGuestMode) {
                homeActivity.showGuestModeAlert();
            } else {
                mealDetailsPresenter.addToFav(mealsItem)

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Log.d("fav", "Favorite added: " + mealsItem.getStrCategory());
                        }, throwable -> {
                            Log.e("fav", "Failed to add to favorites", throwable);
                            showItemDetailErrorMsg("Failed to add to favorites");
                        });
                mealDetailsPresenter.insertMealRemoteToFavorite(mealsItem);
                Log.d("fav", "Favorite remote: " + mealsItem.getStrCategory());
            }
        });
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isGuestMode = false;
                if (homeActivity != null && homeActivity.isGuestMode) {
                    homeActivity.showGuestModeAlert();

                }else{
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            String date = getDateString(year, month, dayOfMonth);
                            mealsItem = (MealsItem) getArguments().getSerializable("item");
                            WeekPlan weekPlan = new WeekPlan();
                            weekPlan.setDate(date);
                            weekPlan.setMealData(mealsItem);
                            addToCalendar(weekPlan);
                        }

                        private void addToCalendar(WeekPlan date) {
                            mealDetailsPresenter.SetClickedItemData(date);
//                        mealDetailsPresenter.insertMealRemoteToWeekPlan(date);
                            // mealDetailsPresenter.insertMealRemoteToWeekPlan(date);
                            Log.d("calender", "onClick: "+date.getStrCategory());
                            // date.setDate(selectedDate);
                        }
                    }, year, month, dayOfMonth);
                    datePickerDialog.show();
                }}
        });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                loadVideo(youTubePlayer);
            }
        });




        // Load YouTube video
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                loadVideo(youTubePlayer);
            }
        });


        showItemDetailData(mealsItem);



        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        image = view.findViewById(R.id.imag_meal_id);
//        tvItemCountry = view.findViewById(R.id.Meal_id);
//        tvItemCategory = view.findViewById(R.id.country_lid);
//        description = view.findViewById(R.id.text_meal_description);
//        calender = view.findViewById(R.id.calender);
//        addToFavImage = view.findViewById(R.id.fac_list_imag_id);
//        youTubePlayerView = view.findViewById(R.id.vedioId_meal);
//        recyclerView = view.findViewById(R.id.recyviewmealidc);
//        mealsItem = (MealsItem) getArguments().getSerializable("item");
//
//        linearLayoutManager = new LinearLayoutManager(getContext()
//         , LinearLayoutManager.HORIZONTAL,false);
//
//        recyclerView.setLayoutManager(linearLayoutManager);
//        mealDetailsAdapter = new MealDetailsAdapter(getContext(), new ArrayList<>());
//        recyclerView.setAdapter(mealDetailsAdapter);
//
//        if (getArguments() != null) {
//            mealsItem = (MealsItem) getArguments().getSerializable("item");
//        }
//
//        mealDetailsPresenter = new MealDetailsImp(this, MealRepositoryImpl.getInstance(
//                MealRemotDataSourceImp.getInstance(), MealLocalDataSourceImpl.getInstance(requireActivity())
//        ));
//        addToFavImage.setOnClickListener(v -> {
//            if (homeActivity != null && homeActivity.isGuestMode) {
//                homeActivity.showGuestModeAlert();
//            } else {
//                mealDetailsPresenter.addToFav(mealsItem)
//
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(() -> {
//                            Log.d("fav", "Favorite added: " + mealsItem.getStrCategory());
//                        }, throwable -> {
//                            Log.e("fav", "Failed to add to favorites", throwable);
//                            showItemDetailErrorMsg("Failed to add to favorites");
//                        });
//                mealDetailsPresenter.insertMealRemoteToFavorite(mealsItem);
//                Log.d("fav", "Favorite remote: " + mealsItem.getStrCategory());
//            }
//        });
//        calender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final boolean isGuestMode = false;
//                if (homeActivity != null && homeActivity.isGuestMode) {
//                    homeActivity.showGuestModeAlert();
//
//                }else{
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                        String date = getDateString(year, month, dayOfMonth);
//                        mealsItem = (MealsItem) getArguments().getSerializable("item");
//                        WeekPlan weekPlan = new WeekPlan();
//                        weekPlan.setDate(date);
//                        weekPlan.setMealData(mealsItem);
//                        addToCalendar(weekPlan);
//                    }
//
//                    private void addToCalendar(WeekPlan date) {
//                        mealDetailsPresenter.SetClickedItemData(date);
////                        mealDetailsPresenter.insertMealRemoteToWeekPlan(date);
//                       // mealDetailsPresenter.insertMealRemoteToWeekPlan(date);
//                        Log.d("calender", "onClick: "+date.getStrCategory());
//                        // date.setDate(selectedDate);
//                    }
//                }, year, month, dayOfMonth);
//                datePickerDialog.show();
//            }}
//        });
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
//                loadVideo(youTubePlayer);
//            }
//        });
//
//
//
//
//        // Load YouTube video
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                super.onReady(youTubePlayer);
//                loadVideo(youTubePlayer);
//            }
//        });
//
//
//        showItemDetailData(mealsItem);
//


    }
    @Override
    public void onPause() {
        super.onPause();
        youTubePlayerView.release();
    }


    @Override
    public void showItemDetailData(MealsItem mealsItem) {
//        tvItemName.setText(mealsItem.getStrMeal());
        tvItemCountry.setText(mealsItem.getStrArea());
        tvItemCategory.setText(mealsItem.getStrCategory());
        description.setText(mealsItem.getStrInstructions());
        Glide.with(requireActivity()).load(mealsItem.getStrMealThumb()).into(image);

        mealDetailsAdapter.setMealsItemList(GeneratingIngridentsArrayLists.getIngridentsArray(mealsItem));
    }

    @Override
    public void addToFav(MealsItem mealsItem) {
        mealRepositoryView.insertMealToFavorite(mealsItem);
        Log.i("moneer", "addToFav: " + mealRepositoryView);
    }


    @Override
    public void showItemDetailErrorMsg(String error) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
    }
    public static String getDateString(int year, int month, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return  format.format(calendar.getTime());
    }

    private void loadVideo(@NonNull YouTubePlayer youTubePlayer) {
        String videoUrl = mealsItem.getStrYoutube();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            youTubePlayer.loadVideo(getVideoId(videoUrl), 0);
        }
    }

    private String getVideoId(String videoUrl) {
        String videoId = null;
        String[] urlParts = videoUrl.split("v=");
        if (urlParts.length > 1) {
            videoId = urlParts[1];
        }
        return videoId;
    }

}