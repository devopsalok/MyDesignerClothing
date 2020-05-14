package com.mydesignerclothing.mobile.android.network.injection;

import javax.inject.Inject;
import javax.inject.Provider;

public class NetworkBindingRouter {

  private Provider<NetworkComponent.Builder> networkComponentProvider;

  @Inject
  NetworkBindingRouter(Provider<NetworkComponent.Builder> networkComponentProvider) {
    this.networkComponentProvider = networkComponentProvider;
  }

  public NetworkComponent getNetworkComponent() {
    return networkComponentProvider.get().build();
  }
}
