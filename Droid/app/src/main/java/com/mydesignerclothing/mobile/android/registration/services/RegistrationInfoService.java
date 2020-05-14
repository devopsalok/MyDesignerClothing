package com.mydesignerclothing.mobile.android.registration.services;

import com.mydesignerclothing.mobile.android.registration.services.apiclient.RegistrationInfoApiClient;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationRequestModel;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationResponseModel;

import io.reactivex.Observable;

public class RegistrationInfoService {

    private RegistrationInfoApiClient apiClient;

    public RegistrationInfoService(RegistrationInfoApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Observable<RegistrationResponseModel> createNewUser(RegistrationRequestModel registrationRequestModel) {
        return apiClient.registerNewUser(registrationRequestModel);
    }

}
