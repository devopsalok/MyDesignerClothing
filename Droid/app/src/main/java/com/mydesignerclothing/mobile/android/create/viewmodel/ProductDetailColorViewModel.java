package com.mydesignerclothing.mobile.android.create.viewmodel;

import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;

public class ProductDetailColorViewModel {
    private ProductColorsList productColorsList;

    public ProductDetailColorViewModel(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
    }

    public String getColorName() {
        return productColorsList.getOptionName();
    }
}
