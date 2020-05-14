package com.mydesignerclothing.mobile.android.registration.presenter;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.registration.services.RegistrationInfoService;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationRequestModel;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationResponseModel;
import com.mydesignerclothing.mobile.android.registration.view.RegistrationInfoView;
import com.mydesignerclothing.mobile.android.registration.viewmodel.RegistrationViewModel;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import io.reactivex.Observer;

import static com.mydesignerclothing.mobile.android.network.errors.NetworkErrorConverter.getNetworkError;

public class RegistrationInfoPresenter {

    private RegistrationInfoService registrationInfoService;
    private RegistrationInfoView registrationInfoView;

    public RegistrationInfoPresenter(RegistrationInfoService registrationInfoService, RegistrationInfoView registrationInfoView) {
        this.registrationInfoService = registrationInfoService;
        this.registrationInfoView = registrationInfoView;
    }

    public void createNewUser(@NonNull RegistrationViewModel registrationViewModel) {
        registrationInfoView.showProgressDialog();
        RegistrationRequestModel registrationRequestModel = createNewAccountRequest(registrationViewModel);
        registrationInfoService.createNewUser(registrationRequestModel).subscribe(createAccountResponseModelObserver());
    }

    @NonNull
    private Observer<RegistrationResponseModel> createAccountResponseModelObserver() {
        return new SimpleObserver<RegistrationResponseModel>() {

            @Override
            public void onNext(RegistrationResponseModel registrationResponseModel) {
                registrationInfoView.hideProgressDialog();
                registrationInfoView.onCreateUserSuccessResponse(registrationResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                registrationInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    registrationInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    private RegistrationRequestModel createNewAccountRequest(RegistrationViewModel registrationViewModel) {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setEmail(registrationViewModel.getEmail());
        registrationRequestModel.setFname(registrationViewModel.getFirstName());
        registrationRequestModel.setLname(registrationViewModel.getLastName());
        registrationRequestModel.setPassword(registrationViewModel.getPassword());
        registrationRequestModel.setPassword_confirm(registrationViewModel.getConfirmPassword());
        registrationRequestModel.setGender("M");
        registrationRequestModel.setPhone(registrationViewModel.getPhoneNumber());

        return registrationRequestModel;
    }

}
