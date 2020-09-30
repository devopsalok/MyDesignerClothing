package com.mydesignerclothing.mobile.android.login.di.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.navigation.NavigationActivity;
import com.mydesignerclothing.mobile.android.login.di.contract.LoginOutwardNavigator;
import com.mydesignerclothing.mobile.android.registration.RegistrationActivity;

public class LoginOutwardNavigatorImpl implements LoginOutwardNavigator {

    @Override
    public void onForgotLoginClicked(Context context) {
    }

    @Override
    public void onForgotPasswordClicked(Context context) {
    }

    @Override
    public void onJoinMyDesignerClothingClicked(Context context) {
        context.startActivity(new Intent(context, RegistrationActivity.class));
    }

    @Override
    public void onSkipClicked(Context context) {
        context.startActivity(new Intent(context, NavigationActivity.class));
    }

    @Override
    public void onLoginSuccess(Context context) {

        navigateToNavigationDrawer(context, true);
    }

    @Override
    public boolean onLoginFailure(Context context, String errorCode) {
        return false;
    }

    private void navigateToNavigationDrawer(Context context, boolean shouldClearTop) {
        Intent intent = new Intent(context, NavigationActivity.class);
        if (shouldClearTop) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
