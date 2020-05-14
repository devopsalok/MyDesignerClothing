package com.mydesignerclothing.mobile.android.commons.network;

import java.util.Calendar;

import androidx.annotation.VisibleForTesting;

public class NetworkState {
  private Calendar lastOfflineTimeStamp;
  private boolean dismissedByClickOnCross;
  private boolean isConnectedToInternet;

  @SuppressWarnings("EmptyMethod")
  private NetworkState() {
    //NOSONAR
  }

  public static NetworkState getInstance() {
    return NetworkStateHolder.instance;
  }

  public Calendar getLastOfflineTimeStamp() {
    return lastOfflineTimeStamp;
  }

  public boolean isCancelableNotificationDismissedByUser() {
    return dismissedByClickOnCross;
  }

  public void setCancelableOfflineNotificationDismissedByUser(boolean dismissedByClickOnCross) {
    this.dismissedByClickOnCross = dismissedByClickOnCross;
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
  public void recordOfflineTimeStamp(Calendar lastOfflineTimeStamp) {
    this.lastOfflineTimeStamp = lastOfflineTimeStamp;
  }

  public boolean isOnline() {
    return isConnectedToInternet;
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
  public void setIsConnectedToInternet(boolean isConnectedToInternet) {
    this.isConnectedToInternet = isConnectedToInternet;
  }

  public boolean isOffline() {
    return !isConnectedToInternet;
  }

  private static class NetworkStateHolder {
    private static NetworkState instance = new NetworkState();
  }

}
