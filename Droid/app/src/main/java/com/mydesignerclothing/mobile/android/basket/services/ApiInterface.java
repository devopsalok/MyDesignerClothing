package com.mydesignerclothing.mobile.android.basket.services;

import com.google.gson.JsonElement;
import com.mydesignerclothing.mobile.android.basket.model.BasketList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
/*    @Multipart
    @POST(APIEndPoints.ADDTOCART)
    @Headers(
            "Content-Type:application/x-www-form-urlencoded")

    Call<JsonElement> getAddtocart(@Part("productId") RequestBody productid,
                                   @Part("userId") RequestBody userId,
                                   @Part("quantity") RequestBody quantity,
                                   @Part MultipartBody.Part file


    );*/
@FormUrlEncoded
    @POST(APIEndPoints.ADDTOCART)
    @Headers(
            "Content-Type:application/x-www-form-urlencoded")

    Call<JsonElement> getAddtocart(@Field("productId") String productid,
                                   @Field("userId") String userId,
                                   @Field("quantity") String quantity,
                                   @Field ("file") String file


    );


    @FormUrlEncoded
    @POST(APIEndPoints.CARTLIST)
    Call<BasketList> getCart(@Field("userId")String userid);
}
