package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface CategortDetailsCallBack {
    public  void onSuccessResult(List<ListDetails>categListDetails);
    public void onFailureResult(String errormsg);

}
