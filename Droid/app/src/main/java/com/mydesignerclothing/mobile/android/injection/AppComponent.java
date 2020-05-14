package com.mydesignerclothing.mobile.android.injection;


import com.mydesignerclothing.mobile.android.MyDesignerClothingApplication;
import com.mydesignerclothing.mobile.android.login.di.LoginComponentBindingInjection;
import com.mydesignerclothing.mobile.android.network.injection.NetworkBindingRouter;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@ApplicationScope
@Component(modules = {AndroidSupportInjectionModule.class, AppInjectionModule.class, NetworkInjectionModule.class, LoginComponentInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
  void inject(MyDesignerClothingApplication app);

  NetworkBindingRouter getBindingRouter();

  LoginComponentBindingInjection getLoginBindingInjection();
}
