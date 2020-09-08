package com.mydesignerclothing.mobile.android.create.viewmodel;

import android.content.res.Resources;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailImages;
import com.mydesignerclothing.mobile.android.uikit.components.ViewModel;

public class ProductDetailImageViewModel implements ViewModel {

    private ProductDetailImages productDetailImages;
    private Resources resources;

    public ProductDetailImageViewModel(ProductDetailImages productDetailImages, Resources resources) {
        this.productDetailImages = productDetailImages;
        this.resources = resources;
    }

    @Override
    public int layoutResId() {
        return R.layout.product_detail_promo_view;
    }

    @Override
    public int dataBindingVariable() {
        return BR.productDetailViewModel;
    }

    public String getProductImages() {
        return productDetailImages.getImageUrl();
    }
}
