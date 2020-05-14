package com.mydesignerclothing.mobile.android.login.loginmain.view;

import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginResponseModel;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import androidx.annotation.NonNull;

public interface LoginMainInfoView {

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorDialog(@NonNull NetworkError error);

    void onLoginSuccessResponse(LoginResponseModel loginResponseModel);
}
