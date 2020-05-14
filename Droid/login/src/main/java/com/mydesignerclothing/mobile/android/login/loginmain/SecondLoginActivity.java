package com.mydesignerclothing.mobile.android.login.loginmain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import android.os.Bundle;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.login.R;
import com.mydesignerclothing.mobile.android.login.databinding.ActivitySecondLoginBinding;
import com.mydesignerclothing.mobile.android.login.di.contract.LoginOutwardNavigator;
import com.mydesignerclothing.mobile.android.login.loginmain.presenter.LoginMainInfoPresenter;
import com.mydesignerclothing.mobile.android.login.loginmain.services.LoginInfoService;
import com.mydesignerclothing.mobile.android.login.loginmain.services.apiclient.LoginInfoApiClient;
import com.mydesignerclothing.mobile.android.login.loginmain.services.model.LoginResponseModel;
import com.mydesignerclothing.mobile.android.login.loginmain.view.LoginMainActivityListener;
import com.mydesignerclothing.mobile.android.login.loginmain.view.LoginMainInfoView;
import com.mydesignerclothing.mobile.android.login.loginmain.viewmodel.LoginMainViewModel;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.BaseActivity;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import javax.inject.Inject;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class SecondLoginActivity extends BaseActivity implements HasAndroidInjector, LoginMainActivityListener, LoginMainInfoView {
    @Inject
    DispatchingAndroidInjector<Object> fragmentDispatchingAndroidInjector;
    @Inject
    LoginOutwardNavigator loginOutwardNavigator;
    private LoginMainInfoPresenter loginMainInfoPresenter;
    private ActivitySecondLoginBinding activitySecondLoginBinding;
    private LoginMainViewModel loginMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activitySecondLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_second_login);
        activitySecondLoginBinding.setLoginMainActivityListener(this);
        LoginInfoService loginInfoService = new LoginInfoService(APIClientFactory.get(this, UNKNOWN).get(LoginInfoApiClient.class));
        loginMainInfoPresenter = new LoginMainInfoPresenter(loginInfoService, this);
        loginMainViewModel = new LoginMainViewModel(getResources());
        activitySecondLoginBinding.setLoginMainViewModel(loginMainViewModel);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onLoginButtonClicked() {
        if (loginMainViewModel.validateUserDetails()) {
            loginMainViewModel.setUserLoginDetails();
            loginMainInfoPresenter.loginUser(loginMainViewModel);
        }
    }

    @Override
    public void showProgressDialog() {
        CustomProgress.showProgressDialog(this, EMPTY_STRING, false);
    }

    @Override
    public void hideProgressDialog() {
        CustomProgress.removeProgressDialog();
    }

    @Override
    public void showErrorDialog(@NonNull NetworkError error) {
        Toast.makeText(this, error.getErrorMessage(getResources()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccessResponse(LoginResponseModel loginResponseModel) {
        loginOutwardNavigator.onLoginSuccess(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
