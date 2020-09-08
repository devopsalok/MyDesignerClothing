package com.mydesignerclothing.mobile.android.create.services.apiclient;


import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CreateInfoApiClient {

    @GET("api/shop/getSearchResult")
    Observable<ProductsResponseModel> getProductsByCategoryId(@Query("CategoryID") int param1, @Query("Ptype") String param2);

    @FormUrlEncoded
    @POST("api/shop/productDetails")
    Observable<ProductDetailModel> getProductDetailByProductId(@Field("productId") int productId);
}
