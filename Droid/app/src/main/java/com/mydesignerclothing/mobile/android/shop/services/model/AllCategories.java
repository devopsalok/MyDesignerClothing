package com.mydesignerclothing.mobile.android.shop.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class AllCategories implements ProguardJsonMappable {
    @Expose
    @SerializedName("CategoryID")
    private String categoryId;
    @Expose
    @SerializedName("CategoryName")
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
