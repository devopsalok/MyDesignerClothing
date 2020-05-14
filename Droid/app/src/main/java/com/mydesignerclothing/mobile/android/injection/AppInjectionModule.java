package com.mydesignerclothing.mobile.android.injection;

import com.mydesignerclothing.mobile.android.login.LoginActivity;
import com.mydesignerclothing.mobile.android.login.loginmain.SecondLoginActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  includes = {
    AppModule.class,
    AndroidSupportInjectionModule.class},

  subcomponents = {
    LoginActivityInjector.class,
    SecondLoginActivityInjector.class
  })
public abstract class AppInjectionModule {

  @Binds
  @IntoMap
  @ClassKey(LoginActivity.class)
  abstract AndroidInjector.Factory<?> bindLoginActivityInjectorFactory(LoginActivityInjector.Builder builder);

  @Binds
  @IntoMap
  @ClassKey(SecondLoginActivity.class)
  abstract AndroidInjector.Factory<?> bindSecondLoginActivityInjectorFactory(SecondLoginActivityInjector.Builder builder);
}
