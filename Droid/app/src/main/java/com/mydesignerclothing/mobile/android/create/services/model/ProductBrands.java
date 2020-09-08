package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductBrands implements ProguardJsonMappable {
    @Expose
    @SerializedName("id_brands")
    private String brandId;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("logo_url")
    private String logoUrl;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id_user")
    private String userId;
    @Expose
    @SerializedName("category")
    private String category;

    public String getBrandId() {
        return brandId;
    }

    public String getStatus() {
        return status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }
}
