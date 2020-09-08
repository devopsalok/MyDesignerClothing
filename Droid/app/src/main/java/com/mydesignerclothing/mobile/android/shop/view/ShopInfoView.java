package com.mydesignerclothing.mobile.android.shop.view;

import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;

import androidx.annotation.NonNull;

public interface ShopInfoView {

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorDialog(@NonNull NetworkError error);

    void onCategoriesSuccessResponse(CategoriesResponseModel categoriesResponseModel);

    void onProductsSuccessResponse(ProductsResponseModel productsResponseModel);
}
