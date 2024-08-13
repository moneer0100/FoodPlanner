package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IngridentItem implements Serializable {
    @SerializedName("idIngredient")
    private String idIngrideant;
    @SerializedName("strIngredient")
    private  String  ingreidant;
    @SerializedName("strDescription")
    private String ingreidantDescription;
    @SerializedName("strType")
    private String strType;

    public String getIdIngrideant() {
        return idIngrideant;
    }

    public String getIngreidant() {
        return ingreidant;
    }

    public String getIngreidantDescription() {
        return ingreidantDescription;
    }

    public String getStrType() {
        return strType;
    }
}
