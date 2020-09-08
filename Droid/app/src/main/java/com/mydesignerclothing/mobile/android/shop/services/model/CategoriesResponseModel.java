package com.mydesignerclothing.mobile.android.shop.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class CategoriesResponseModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("allcategory")
    private List<AllCategories> allCategoriesList;

    public List<AllCategories> getAllCategoriesList() {
        return allCategoriesList;
    }
}
