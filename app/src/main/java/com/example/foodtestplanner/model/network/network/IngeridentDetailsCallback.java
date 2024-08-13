package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface IngeridentDetailsCallback {
    public  void onSuccessResult(List<ListDetails> categoryDetailsList);
    public void onFailureResult(String errormsg);
}

