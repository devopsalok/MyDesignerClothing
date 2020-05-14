package com.mydesignerclothing.mobile.android.commons.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.util.Calendar;

import androidx.annotation.VisibleForTesting;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AppNetworkStateManager {
  public static final String NETWORK_STATE_CHANGE_ACTION = "com.mydesignerclothing.mobile.android.NetworkStateChangeAction";
  private final NetworkState networkState = NetworkState.getInstance();

  public void refresh(Context context) {
    boolean connectedToInternetBeforeRefresh = networkState.isOnline();
    refreshNetworkState(context);
    boolean connectedToInternetAfterRefresh = networkState.isOnline();

    if (isNetworkStateSame(connectedToInternetBeforeRefresh, connectedToInternetAfterRefresh) && !wentFirstTimeOffline()) {
      return;
    }
    updateOnlineModeState(connectedToInternetBeforeRefresh, connectedToInternetAfterRefresh);
    networkState.setCancelableOfflineNotificationDismissedByUser(false);
    broadcastNetworkChangeIntent(context);
  }

  private boolean isNetworkStateSame(boolean connectedToInternetBeforeRefresh, boolean connectedToInternetAfterRefresh) {
    return connectedToInternetAfterRefresh == connectedToInternetBeforeRefresh;
  }

  private void updateOnlineModeState(boolean connectedToInternetBeforeRefresh, boolean currentlyConnectedToInternet) {
    if (wentOfflineFromOnline(connectedToInternetBeforeRefresh, currentlyConnectedToInternet) || wentFirstTimeOffline()) {
      networkState.recordOfflineTimeStamp(Calendar.getInstance());
    }
  }

  private boolean wentFirstTimeOffline() {
    return networkState.getLastOfflineTimeStamp() == null;
  }

  private boolean wentOfflineFromOnline(boolean connectedToInternetBeforeRefresh, boolean currentlyConnectedToInternet) {
    return connectedToInternetBeforeRefresh && !currentlyConnectedToInternet;
  }

  private void broadcastNetworkChangeIntent(Context context) {
    Intent networkStateIntent = new Intent(NETWORK_STATE_CHANGE_ACTION);
    LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);
  }

  @VisibleForTesting
  void refreshNetworkState(Context context) {
    boolean connectedToInternet = NetworkStateIdentifier.isConnectedToInternet((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE));
    networkState.setIsConnectedToInternet(connectedToInternet);
  }

  public static final AppNetworkStateManager getInstance() {
    return AppNetworkStateManagerHolder.instance;
  }

  private static class AppNetworkStateManagerHolder {
    private static AppNetworkStateManager instance = new AppNetworkStateManager();
  }
}
