package com.mydesignerclothing.mobile.android.create.presenter;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.create.services.CreateInfoService;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductRequestModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import io.reactivex.Observer;

import static com.mydesignerclothing.mobile.android.network.errors.NetworkErrorConverter.getNetworkError;

public class CreateInfoPresenter {

    private CreateInfoService createInfoService;
    private CreateInfoView createInfoView;

    public CreateInfoPresenter(CreateInfoService shopInfoService, CreateInfoView shopInfoView) {
        this.createInfoService = shopInfoService;
        this.createInfoView = shopInfoView;
    }

    public void getListOfProducts(int categoryId, String pType) {
        createInfoView.showProgressDialog();
        createInfoService.getProducts(categoryId, pType).subscribe(getProductsResponseModelObserver());
    }

    public void getProductDetail(String productId) {
        createInfoView.showProgressDialog();
        createInfoService.getProductDetail(Integer.parseInt(productId)).subscribe(getProductDetailResponseModelObserver());
    }

    @NonNull
    private Observer<ProductsResponseModel> getProductsResponseModelObserver() {
        return new SimpleObserver<ProductsResponseModel>() {

            @Override
            public void onNext(ProductsResponseModel productsResponseModel) {
                createInfoView.hideProgressDialog();
                createInfoView.onProductsSuccessResponse(productsResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                createInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    createInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    @NonNull
    private Observer<ProductDetailModel> getProductDetailResponseModelObserver() {
        return new SimpleObserver<ProductDetailModel>() {

            @Override
            public void onNext(ProductDetailModel productDetailModel) {
                createInfoView.hideProgressDialog();
                createInfoView.onProductDetailSuccessResponse(productDetailModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                createInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    createInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }
}
