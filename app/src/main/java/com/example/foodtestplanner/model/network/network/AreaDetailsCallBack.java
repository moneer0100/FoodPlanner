package com.example.foodtestplanner.model.network.network;

import com.example.foodtestplanner.model.dto.ListDetails;

import java.util.List;

public interface AreaDetailsCallBack {
    public  void onSuccessResult(List<ListDetails> areaDetailslist);
    public void onFailureResult(String errormsg);
}
