package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductRequestModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("productId")
    private int productId;

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
