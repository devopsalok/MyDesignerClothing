package com.mydesignerclothing.mobile.android.injection;


import com.mydesignerclothing.mobile.android.login.di.LoginComponent;

import dagger.Module;

@Module(subcomponents = LoginComponent.class)
public class LoginComponentInjectionModule {

  @SuppressWarnings("EmptyMethod")
  public LoginComponentInjectionModule() {
    //NOSONAR
  }

}
