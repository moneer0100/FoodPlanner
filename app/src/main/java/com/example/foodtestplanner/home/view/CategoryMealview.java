package com.example.foodtestplanner.home.view;

import com.example.foodtestplanner.model.dto.CategorisItem;

import java.util.List;

public interface CategoryMealview {
    public void showcategorydata(List<CategorisItem> categorisItemList);
    public void showErrorMassCategory(String error);
}
