package com.mydesignerclothing.mobile.android.shop.viewmodel;

import android.content.res.Resources;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.shop.services.model.AllCategories;
import com.mydesignerclothing.mobile.android.uikit.components.ViewModel;

public class ShopInfoViewModel implements ViewModel {

    private AllCategories allCategories;
    private Resources resources;

    public ShopInfoViewModel(AllCategories allCategories, Resources resources) {
        this.allCategories = allCategories;
        this.resources = resources;
    }

    @Override
    public int layoutResId() {
        return R.layout.category_row_item;
    }

    @Override
    public int dataBindingVariable() {
        return BR.shopInfoViewModel;
    }

    public String getCategoryName() {
        return allCategories.getCategoryName();
    }
}
