package com.mydesignerclothing.mobile.android.login.di;

import javax.inject.Inject;
import javax.inject.Provider;

public class LoginComponentBindingInjection {

  private Provider<LoginComponent.Builder> loginComponentProvider;

  @Inject
  LoginComponentBindingInjection(Provider<LoginComponent.Builder> loginComponentProvider) {
    this.loginComponentProvider = loginComponentProvider;
  }

  public LoginComponent getLoginComponent() {
    return loginComponentProvider.get().build();
  }
}
