package com.mydesignerclothing.mobile.android.registration.services.apiclient;

import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationRequestModel;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationInfoApiClient {

    @POST("api/users/actionCreateUser")
    Observable<RegistrationResponseModel> registerNewUser(@Body RegistrationRequestModel registrationRequestModel);
}
