package com.mydesignerclothing.mobile.android.login.network;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.util.Callback;
import com.mydesignerclothing.mobile.android.login.di.contract.LoginOutwardNavigator;
import com.mydesignerclothing.mobile.android.login.exceptions.LoginServiceCrashedUnexpectedly;
import com.mydesignerclothing.mobile.android.login.loginmain.SecondLoginActivity;
import com.mydesignerclothing.mobile.android.network.errors.ErrorConverter;
import com.mydesignerclothing.mobile.android.network.exceptions.DuplicateRequestException;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import io.reactivex.annotations.NonNull;

public class DefaultLoginResponseHandler {

  private static final String TAG = DefaultLoginResponseHandler.class.getSimpleName();
  private Activity activity;
  private LoginOutwardNavigator loginOutwardNavigator;

  public DefaultLoginResponseHandler(@NonNull Activity activity, LoginOutwardNavigator loginOutwardNavigator) {
    this.activity = activity;
    this.loginOutwardNavigator = loginOutwardNavigator;
  }



  public boolean handleLoginError(@NonNull Throwable e) {
    if (e instanceof DuplicateRequestException) {
      return true;
    }

    if(e instanceof LoginServiceCrashedUnexpectedly){
      DMLog.log(TAG, e, Log.ERROR);
      navigateToLoginCallback().invoke();
      return true;
    }
      Optional<NetworkError> networkErrorOptional = ErrorConverter.getNetworkError(e);

      if (networkErrorOptional.isPresent()) {
        NetworkError network = networkErrorOptional.get();
        String errorCode = network.getErrorCode();
        return loginOutwardNavigator.onLoginFailure(activity, errorCode);
    }
    return false;
  }

  @NonNull
  private Callback navigateToLoginCallback() {
    return () -> {
      Intent gotoLoginIntent = new Intent(activity, SecondLoginActivity.class);
        gotoLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(gotoLoginIntent);
        activity.finish();
    };
  }
}
