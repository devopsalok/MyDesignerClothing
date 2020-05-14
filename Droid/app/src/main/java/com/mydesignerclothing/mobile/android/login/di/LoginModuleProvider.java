package com.mydesignerclothing.mobile.android.login.di;

import android.content.Context;

import com.mydesignerclothing.mobile.android.login.core.LoginService;
import com.mydesignerclothing.mobile.android.login.di.contract.LoginOutwardNavigator;
import com.mydesignerclothing.mobile.android.login.di.impl.LoginOutwardNavigatorImpl;

import dagger.Module;
import dagger.Provides;


@Module
public class LoginModuleProvider {
    @LoginScope
    @Provides
    public LoginOutwardNavigator getLoginOutwardNavigator() {
        return new LoginOutwardNavigatorImpl();
    }

  /*@Provides
  public LoginSharedPrefManager getLoginSharedPrefStorageManager(Context context) {
    return new LoginSharedPrefManager(SharedPref.getSharedPreference(context));
  }

  @Provides
  public LoginDatabaseStorageManager getLoginDatabaseStorageManager(Context context) {
    return new LoginDatabaseStorageManager(context);
  }*/


    @Provides
    public LoginService getLoginService(Context context) {
        return new LoginService(context);
    }
}
