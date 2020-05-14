package com.mydesignerclothing.mobile.android.network.injection;


import com.mydesignerclothing.mobile.android.network.RetrofitBuilder;
import com.mydesignerclothing.mobile.android.network.adapters.RxCallAdapterFactory;

import dagger.Subcomponent;

@Subcomponent
public interface NetworkComponent {

  void inject(RetrofitBuilder retrofitBuilder);
  void inject(RxCallAdapterFactory.RxCallAdapter callAdapter);

  @Subcomponent.Builder
  interface Builder {
    NetworkComponent build();
  }
}
