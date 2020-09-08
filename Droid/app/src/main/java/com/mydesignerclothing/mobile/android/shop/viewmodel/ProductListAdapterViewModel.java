package com.mydesignerclothing.mobile.android.shop.viewmodel;

import com.mydesignerclothing.mobile.android.shop.services.model.ProductsListModel;

public class ProductListAdapterViewModel {

    private ProductsListModel productsListModel;

    public ProductListAdapterViewModel(ProductsListModel productsListModel) {
        this.productsListModel = productsListModel;
    }

    public String getProductName() {
        return (productsListModel.getProductName());
    }

    public String getProductUrl() {
        return (productsListModel.getProductImage());
    }

    public String getProductPrice() {
        return ("Price : " + productsListModel.getProductPrice());
    }
}
