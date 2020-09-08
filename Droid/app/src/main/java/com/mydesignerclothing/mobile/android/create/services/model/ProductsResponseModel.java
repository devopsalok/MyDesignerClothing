package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class ProductsResponseModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("result")
    private List<ProductsListModel> productsListModel;

    public List<ProductsListModel> getProductsListModel() {
        return productsListModel;
    }
}
