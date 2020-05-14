package com.mydesignerclothing.mobile.android.login;

import android.content.Intent;
import android.os.Bundle;

import com.mydesignerclothing.mobile.android.login.databinding.ActivityLoginBinding;
import com.mydesignerclothing.mobile.android.login.di.contract.LoginOutwardNavigator;
import com.mydesignerclothing.mobile.android.login.loginmain.SecondLoginActivity;
import com.mydesignerclothing.mobile.android.login.view.LoginActivityListener;
import com.mydesignerclothing.mobile.android.uikit.BaseActivity;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class LoginActivity extends BaseActivity implements LoginActivityListener, HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> fragmentDispatchingAndroidInjector;
    @Inject
    LoginOutwardNavigator loginOutwardNavigator;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setLoginActivityListener(this);
    }

    @Override
    public void onCreateButtonClicked() {
        loginOutwardNavigator.onJoinMyDesignerClothingClicked(this);
    }

    @Override
    public void onSignInButtonClicked() {
        Intent intent = new Intent();
        intent.setClass(this, SecondLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipButtonClicked() {
        finish();
        loginOutwardNavigator.onSkipClicked(this);
    }

    @Override
    public void onLoginButtonClicked() {
        //NoSonar
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
