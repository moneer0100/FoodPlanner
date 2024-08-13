package com.example.foodtestplanner.calenderWeekPlan;

import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.dto.WeekPlan;

public interface CalenderView {
    public void deleteWeekPlan(WeekPlan weekPlan);

    public void showList();
}
