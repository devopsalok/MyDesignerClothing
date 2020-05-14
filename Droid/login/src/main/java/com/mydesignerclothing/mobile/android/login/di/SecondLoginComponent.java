package com.mydesignerclothing.mobile.android.login.di;


import com.mydesignerclothing.mobile.android.login.core.LoginService;

import dagger.Subcomponent;

@Subcomponent
public interface SecondLoginComponent {
  void inject(LoginService loginService);

  @Subcomponent.Builder
  interface Builder {
    SecondLoginComponent build();
  }
}

