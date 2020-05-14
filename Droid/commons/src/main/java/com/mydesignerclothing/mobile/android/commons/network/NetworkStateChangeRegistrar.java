package com.mydesignerclothing.mobile.android.commons.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.NetworkCallback;

public class NetworkStateChangeRegistrar {

  private NetworkCallback networkCallback;

  public static NetworkStateChangeRegistrar getInstance() {
    return InstanceHolder.instance;
  }

  public void register(final Context context, AppNetworkStateManager networkStateManager) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

      networkCallback = new NetworkCallback() {
      @Override
      public void onAvailable(Network network) {
        networkStateManager.refresh(context);
      }

      @Override
      public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        networkStateManager.refresh(context);
      }

      @Override
      public void onLost(Network network) {
        networkStateManager.refresh(context);
      }
    };

    connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), networkCallback);
  }

  public void unregister(final Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
    connectivityManager.unregisterNetworkCallback(networkCallback);
  }

  private static class InstanceHolder {
    static NetworkStateChangeRegistrar instance = new NetworkStateChangeRegistrar();
  }

}
