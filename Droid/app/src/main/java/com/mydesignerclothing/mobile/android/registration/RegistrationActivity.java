package com.mydesignerclothing.mobile.android.registration;

import android.os.Bundle;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.ActivityRegistrationBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.registration.presenter.RegistrationInfoPresenter;
import com.mydesignerclothing.mobile.android.registration.services.RegistrationInfoService;
import com.mydesignerclothing.mobile.android.registration.services.apiclient.RegistrationInfoApiClient;
import com.mydesignerclothing.mobile.android.registration.services.model.RegistrationResponseModel;
import com.mydesignerclothing.mobile.android.registration.view.RegistrationActivityListener;
import com.mydesignerclothing.mobile.android.registration.view.RegistrationInfoView;
import com.mydesignerclothing.mobile.android.registration.viewmodel.RegistrationViewModel;
import com.mydesignerclothing.mobile.android.uikit.BaseActivity;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class RegistrationActivity extends BaseActivity implements RegistrationActivityListener, RegistrationInfoView {
    private RegistrationInfoPresenter registrationInfoPresenter;
    private ActivityRegistrationBinding activityRegistrationBinding;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegistrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        activityRegistrationBinding.setRegistrationActivityListener(this);
        RegistrationInfoService registrationInfoService = new RegistrationInfoService(APIClientFactory.get(this, UNKNOWN).get(RegistrationInfoApiClient.class));
        registrationInfoPresenter = new RegistrationInfoPresenter(registrationInfoService, this);
        registrationViewModel = new RegistrationViewModel(getResources());
        activityRegistrationBinding.setCreateNewAccount(registrationViewModel);
    }

    @Override
    public void onCreateAccountButtonClicked() {
        if (registrationViewModel.validatePersonalAndContactInfo()) {
            registrationViewModel.setContactAndPersonalDetails();
            registrationInfoPresenter.createNewUser(registrationViewModel);
        }
    }

    @Override
    public void dismissPopup() {

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
    public void onCreateUserSuccessResponse(RegistrationResponseModel registrationResponseModel) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
