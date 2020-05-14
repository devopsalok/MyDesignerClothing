package com.mydesignerclothing.mobile.android.login.loginmain.services.apiclient;

import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginRequestModel;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInfoApiClient {

    @POST("api/users/login")
    Observable<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequestModel);
}
