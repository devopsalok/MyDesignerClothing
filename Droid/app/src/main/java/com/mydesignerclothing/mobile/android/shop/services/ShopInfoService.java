package com.mydesignerclothing.mobile.android.shop.services;

import com.mydesignerclothing.mobile.android.shop.services.apiclient.ShopInfoApiClient;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesRequestModel;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;

import io.reactivex.Observable;

public class ShopInfoService {

    private ShopInfoApiClient shopInfoApiClient;

    public ShopInfoService(ShopInfoApiClient shopInfoApiClient) {
        this.shopInfoApiClient = shopInfoApiClient;
    }

    public Observable<CategoriesResponseModel> getCategories(CategoriesRequestModel categoriesRequestModel) {
        return shopInfoApiClient.getAllCategories("");
    }

    public Observable<ProductsResponseModel> getProducts(int categoryId, String pType) {
        return shopInfoApiClient.getProductsByCategoryId(categoryId, pType);
    }

}
