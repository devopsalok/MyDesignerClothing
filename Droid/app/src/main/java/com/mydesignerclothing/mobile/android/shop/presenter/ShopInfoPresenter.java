package com.mydesignerclothing.mobile.android.shop.presenter;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationRequestModel;
import com.mydesignerclothing.mobile.android.registration.viewmodel.RegistrationViewModel;
import com.mydesignerclothing.mobile.android.shop.services.ShopInfoService;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesRequestModel;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.shop.view.ShopInfoView;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import io.reactivex.Observer;

import static com.mydesignerclothing.mobile.android.network.errors.NetworkErrorConverter.getNetworkError;

public class ShopInfoPresenter {

    private ShopInfoService shopInfoService;
    private ShopInfoView shopInfoView;

    public ShopInfoPresenter(ShopInfoService shopInfoService, ShopInfoView shopInfoView) {
        this.shopInfoService = shopInfoService;
        this.shopInfoView = shopInfoView;
    }

    public void getListOfCategories() {
        shopInfoView.showProgressDialog();
        shopInfoService.getCategories(new CategoriesRequestModel()).subscribe(getCategoriesResponseModelObserver());
    }

    @NonNull
    private Observer<CategoriesResponseModel> getCategoriesResponseModelObserver() {
        return new SimpleObserver<CategoriesResponseModel>() {

            @Override
            public void onNext(CategoriesResponseModel categoriesResponseModel) {
                shopInfoView.hideProgressDialog();
                shopInfoView.onCategoriesSuccessResponse(categoriesResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                shopInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    shopInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    public void getListOfProducts(int categoryId, String pType) {
        shopInfoView.showProgressDialog();
        shopInfoService.getProducts(categoryId, pType).subscribe(getProductsResponseModelObserver());
    }

    @NonNull
    private Observer<ProductsResponseModel> getProductsResponseModelObserver() {
        return new SimpleObserver<ProductsResponseModel>() {

            @Override
            public void onNext(ProductsResponseModel productsResponseModel) {
                shopInfoView.hideProgressDialog();
                shopInfoView.onProductsSuccessResponse(productsResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                shopInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    shopInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    private RegistrationRequestModel createNewAccountRequest(RegistrationViewModel registrationViewModel) {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setEmail(registrationViewModel.getEmail());
        registrationRequestModel.setFname(registrationViewModel.getFirstName());
        registrationRequestModel.setLname(registrationViewModel.getLastName());
        registrationRequestModel.setPassword(registrationViewModel.getPassword());
        registrationRequestModel.setPassword_confirm(registrationViewModel.getConfirmPassword());
        registrationRequestModel.setGender("M");
        registrationRequestModel.setPhone(registrationViewModel.getPhoneNumber());

        return registrationRequestModel;
    }

}
