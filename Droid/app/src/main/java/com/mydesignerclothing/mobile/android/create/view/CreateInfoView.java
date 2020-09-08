package com.mydesignerclothing.mobile.android.create.view;

import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import androidx.annotation.NonNull;

public interface CreateInfoView {

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorDialog(@NonNull NetworkError error);

    void onProductsSuccessResponse(ProductsResponseModel productsResponseModel);

    void onProductDetailSuccessResponse(ProductDetailModel productDetailModel);

    void onColorDialogConfirmButtonClicked();

    void onColorCancelButtonClicked();

    void onSizeDialogConfirmButtonClicked();

    void onSizeCancelButtonClicked();

    void onSelectColorButtonClicked();

    void onSelectSizeButtonClicked();

    void invokeProductDetailEventForColorSelected(ProductColorsList productColorsList);

    void invokeProductDetailEventForSizeSelected(ProductColorsList productColorsList);

    void onStartDesignButtonClicked();


}
