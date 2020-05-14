package com.mydesignerclothing.mobile.android.login.apiclient;
import com.mydesignerclothing.mobile.android.login.models.LoginRequest;
import com.mydesignerclothing.mobile.android.login.models.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPIClient {

  @POST("user/login")
  Observable<LoginResponse> doLogin(@Body LoginRequest loginRequest);

}
