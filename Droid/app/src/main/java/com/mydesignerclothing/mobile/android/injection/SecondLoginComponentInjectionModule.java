package com.mydesignerclothing.mobile.android.injection;


import com.mydesignerclothing.mobile.android.login.di.LoginComponent;
import com.mydesignerclothing.mobile.android.login.di.SecondLoginComponent;

import dagger.Module;

@Module(subcomponents = SecondLoginComponent.class)
public class SecondLoginComponentInjectionModule {

  @SuppressWarnings("EmptyMethod")
  public SecondLoginComponentInjectionModule() {
    //NOSONAR
  }

}
