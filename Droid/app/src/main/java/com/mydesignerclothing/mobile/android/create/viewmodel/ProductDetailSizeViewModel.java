package com.mydesignerclothing.mobile.android.create.viewmodel;

import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;

public class ProductDetailSizeViewModel {
    private ProductColorsList productColorsList;

    public ProductDetailSizeViewModel(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
    }

    public String getSizeName() {
        return productColorsList.getOptionName();
    }
}
