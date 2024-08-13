package com.example.foodtestplanner.category.view;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface CategoryDetailsView {
    public void showCategoryDetailsData(List<ListDetails> categoryDetailsList);
    public void showCategoryDetailsErrorMsg(String error);
}
