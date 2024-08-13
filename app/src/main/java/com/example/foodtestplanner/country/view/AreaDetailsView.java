package com.example.foodtestplanner.country.view;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface AreaDetailsView {
    public void showAreaDetails(List<ListDetails>showAreaitem);
    public void showAreaError(String error);
}
