package com.mydesignerclothing.mobile.android.registration.view;

import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationResponseModel;

import androidx.annotation.NonNull;

public interface RegistrationInfoView {

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorDialog(@NonNull NetworkError error);

    void onCreateUserSuccessResponse(RegistrationResponseModel registrationResponseModel);
}
