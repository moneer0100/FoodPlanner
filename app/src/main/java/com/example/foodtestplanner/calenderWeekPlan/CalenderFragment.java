package com.example.foodtestplanner.calenderWeekPlan;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.calenderWeekPlan.presenter.CalenderPresenterView;
import com.example.foodtestplanner.model.dto.WeekPlan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CalenderFragment extends Fragment implements CalenderInClick,CalenderView {
    private CalenderInClick onWeekPlanMealClickListener ;
    private RecyclerView recyclerView;
    private CalenderAdapter weekPlanMealAdapter ;
    private CalenderPresenterView weekPlanMealPresenterView;
    private Flowable<List<WeekPlan>> weekPlanMealList;
    private Calendar calendar;
    private CalendarView calendarView;
    private WeekPlan date;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void getMealsForDate(String selectedDate) {

        weekPlanMealPresenterView.getMealsForDate(selectedDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planItemList -> {
                    updateUIWithMeals(planItemList);
                }, throwable -> {
                    Log.e("TAG", "Error fetching meals: " + throwable.getMessage());
                });
    }
    private void updateUIWithMeals(List<WeekPlan> mealsItemList) {
        weekPlanMealAdapter.setWeekPlanListMealList(mealsItemList);
        weekPlanMealAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItemClick(WeekPlan weekPlan) {
        weekPlanMealPresenterView.deleteMeal(weekPlan);
        Toast.makeText(requireActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeekPlanMealClick(WeekPlan weekPlan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("weekPlan", weekPlan);
        Navigation.findNavController(requireView()).navigate(R.id.action_calenderFragment_to_listDetailsFragment, bundle);
    }
    private String getStringFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(date);
    }

    private String getStringFromDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(calendar.getTime());
    }

    @Override
    public void deleteWeekPlan(WeekPlan weekPlan) {

    }

    @Override
    public void showList() {
        weekPlanMealList = weekPlanMealPresenterView.getWeekPlanMealList();
        Log.i("TAG", "showList: "+weekPlanMealList);
        weekPlanMealList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weekPlanItemList -> {
                    weekPlanMealAdapter.setWeekPlanListMealList(weekPlanItemList);
                    weekPlanMealAdapter.notifyDataSetChanged();
                }, throwable -> {
                    Log.i("TAG", "Unable to show Meal because: "+throwable.getMessage());
                });
    }
    }