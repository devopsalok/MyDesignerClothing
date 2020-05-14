package com.mydesignerclothing.mobile.android.login.loginmain.services;

import com.mydesignerclothing.mobile.android.login.loginmain.services.apiclient.LoginInfoApiClient;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginRequestModel;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginResponseModel;

import io.reactivex.Observable;

public class LoginInfoService {

    private LoginInfoApiClient apiClient;

    public LoginInfoService(LoginInfoApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Observable<LoginResponseModel> userLogin(LoginRequestModel loginRequestModel) {
        return apiClient.loginUser(loginRequestModel);
    }

}
