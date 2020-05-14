package com.mydesignerclothing.mobile.android.commons.network;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateChangeReceiver extends BroadcastReceiver {
  private AppNetworkStateManager networkStateManager;

  public NetworkStateChangeReceiver(AppNetworkStateManager networkStateManager) {
    this.networkStateManager = networkStateManager;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    networkStateManager.refresh(context);
  }
}
