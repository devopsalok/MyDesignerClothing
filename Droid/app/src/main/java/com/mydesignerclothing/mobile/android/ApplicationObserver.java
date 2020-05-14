package com.mydesignerclothing.mobile.android;

import com.mydesignerclothing.mobile.android.commons.network.AppNetworkStateManager;
import com.mydesignerclothing.mobile.android.commons.network.NetworkStateChangeRegistrar;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class ApplicationObserver implements LifecycleObserver {

  @SuppressWarnings("EmptyMethod")
  public ApplicationObserver() {
    //NOSONAR
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  public void onApplicationEnterBackground() {
    NetworkStateChangeRegistrar.getInstance().unregister(MyDesignerClothingApplication.getAppContext());
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  public void onApplicationEnterForeground() {
    NetworkStateChangeRegistrar.getInstance().register(MyDesignerClothingApplication.getAppContext(), AppNetworkStateManager.getInstance());
  }
}
