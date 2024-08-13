package com.example.foodtestplanner.model.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategorisItem implements Serializable {
    @SerializedName("strCategory")
    private String category;

    @SerializedName("idCategory")
    private String idCategory;

    @SerializedName("strCategoryDescription")
    private String categoryDescription;

    @SerializedName("strCategoryThumb")
    private String thumbnail;

    public void setCategory(String category) {
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
