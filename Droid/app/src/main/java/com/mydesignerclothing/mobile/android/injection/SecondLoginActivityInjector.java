package com.mydesignerclothing.mobile.android.injection;

import com.mydesignerclothing.mobile.android.login.LoginActivity;
import com.mydesignerclothing.mobile.android.login.di.LoginModuleProvider;
import com.mydesignerclothing.mobile.android.login.di.LoginScope;
import com.mydesignerclothing.mobile.android.login.loginmain.SecondLoginActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@LoginScope
@Subcomponent(modules = {AndroidSupportInjectionModule.class, LoginModuleProvider.class})
public interface SecondLoginActivityInjector extends AndroidInjector<SecondLoginActivity> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SecondLoginActivity> {}
}
