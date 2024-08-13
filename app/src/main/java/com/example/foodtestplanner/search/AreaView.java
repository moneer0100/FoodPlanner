package com.example.foodtestplanner.search;

import com.example.foodtestplanner.model.dto.AreaItem;

import java.util.List;

public interface AreaView {
    public void showAreasData(List<AreaItem> AreaItemList);
    public void showAreasErrorMsg(String error);
}
