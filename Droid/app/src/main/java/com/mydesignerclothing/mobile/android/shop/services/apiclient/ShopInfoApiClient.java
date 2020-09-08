package com.mydesignerclothing.mobile.android.shop.services.apiclient;

import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesRequestModel;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopInfoApiClient {

    @FormUrlEncoded
    @POST("api/gallery/allcategory")
    Observable<CategoriesResponseModel> getAllCategories(@Field("") String java);

    @GET("api/shop/getSearchResult")
    Observable<ProductsResponseModel> getProductsByCategoryId(@Query("CategoryID") int param1, @Query("Ptype") String param2);
}
