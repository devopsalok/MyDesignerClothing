package com.mydesignerclothing.mobile.android.login.loginmain.presenter;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.login.loginmain.services.LoginInfoService;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginRequestModel;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginResponseModel;
import com.mydesignerclothing.mobile.android.login.loginmain.view.LoginMainInfoView;
import com.mydesignerclothing.mobile.android.login.loginmain.viewmodel.LoginMainViewModel;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import io.reactivex.Observer;

import static com.mydesignerclothing.mobile.android.network.errors.NetworkErrorConverter.getNetworkError;

public class LoginMainInfoPresenter {

    private LoginInfoService loginInfoService;
    private LoginMainInfoView loginMainInfoView;

    public LoginMainInfoPresenter(LoginInfoService registrationInfoService, LoginMainInfoView loginMainInfoView) {
        this.loginInfoService = registrationInfoService;
        this.loginMainInfoView = loginMainInfoView;
    }

    public void loginUser(@NonNull LoginMainViewModel loginMainViewModel) {
        loginMainInfoView.showProgressDialog();
        LoginRequestModel loginRequestModel = userLoginRequest(loginMainViewModel);
        loginInfoService.userLogin(loginRequestModel).subscribe(createAccountResponseModelObserver());
    }

    @NonNull
    private Observer<LoginResponseModel> createAccountResponseModelObserver() {
        return new SimpleObserver<LoginResponseModel>() {

            @Override
            public void onNext(LoginResponseModel registrationResponseModel) {
                loginMainInfoView.hideProgressDialog();
                loginMainInfoView.onLoginSuccessResponse(registrationResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                loginMainInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    loginMainInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    private LoginRequestModel userLoginRequest(LoginMainViewModel loginMainViewModel) {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setEmail(loginMainViewModel.getEmail());
        loginRequestModel.setPassword(loginMainViewModel.getPassword());

        return loginRequestModel;
    }

}
