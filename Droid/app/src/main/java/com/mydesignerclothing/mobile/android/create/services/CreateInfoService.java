package com.mydesignerclothing.mobile.android.create.services;

import com.mydesignerclothing.mobile.android.create.services.apiclient.CreateInfoApiClient;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductRequestModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;

import io.reactivex.Observable;

public class CreateInfoService {

    private CreateInfoApiClient createInfoApiClient;

    public CreateInfoService(CreateInfoApiClient createInfoApiClient) {
        this.createInfoApiClient = createInfoApiClient;
    }

    public Observable<ProductsResponseModel> getProducts(int categoryId, String pType) {
        return createInfoApiClient.getProductsByCategoryId(categoryId, pType);
    }

    public Observable<ProductDetailModel> getProductDetail(int productId) {
        return createInfoApiClient.getProductDetailByProductId(productId);
    }

}
