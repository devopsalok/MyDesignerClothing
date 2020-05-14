package com.mydesignerclothing.mobile.android.login.di.contract;

import android.content.Context;

public interface LoginOutwardNavigator {
  void onForgotLoginClicked(Context context);
  void onForgotPasswordClicked(Context context);
  void onJoinMyDesignerClothingClicked(Context context);
  void onSkipClicked(Context context);
  void onLoginSuccess(Context context);
  boolean onLoginFailure(Context context, String errorCode);
}
