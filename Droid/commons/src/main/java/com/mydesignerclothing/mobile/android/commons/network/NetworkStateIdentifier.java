package com.mydesignerclothing.mobile.android.commons.network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;


import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static java.util.Arrays.asList;

public class NetworkStateIdentifier {

  private static final String TAG = NetworkStateIdentifier.class.getSimpleName();

  public static final String WIFI_CONNECTION = "Wi-Fi";
  public static final String WIRELESS_CONNECTION = "Wireless";

  @SuppressWarnings("EmptyMethod")
  private NetworkStateIdentifier() {
    //NOSONAR
  }

  public static boolean isConnectedToInternet(@Nullable ConnectivityManager connectivityManager) {
    try {
      if (connectivityManager != null) {
        return checkNetworksForConnectivity(connectivityManager);
      }
      return false;
    } catch (Exception e) {
      CrashTracker.trackStackTrace(NetworkStateIdentifier.class.getSimpleName(), e);
      return false;
    }
  }

  private static boolean checkNetworksForConnectivity(@NonNull final ConnectivityManager connectivityManager) {
    Network[] allNetworks = connectivityManager.getAllNetworks();
    for (Network network : allNetworks) {
      try {
        if (network != null && isNetworkConnected(connectivityManager, network)) {
          return true;
        }
      } catch (Exception e) {
        CrashTracker.trackStackTrace(NetworkStateIdentifier.class.getSimpleName(), e);
      }
    }
    return false;
  }

  private static boolean isNetworkConnected(@NonNull ConnectivityManager connectivityManager, @Nullable Network network) {
    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
    return networkInfo != null && isWifiOrMobile(networkInfo) && networkInfo.isConnected();
  }

  private static boolean isWifiOrMobile(@NonNull NetworkInfo networkInfo) {
    List<Integer> networks = asList(TYPE_MOBILE, TYPE_WIFI);
    return networks.contains(networkInfo.getType());
  }

  public static String getConnectionType(@NonNull ConnectivityManager connectivityManager) {
    try {
      NetworkInfo network = connectivityManager.getActiveNetworkInfo();
      if (network != null && network.isConnected()) {
        switch (network.getType()) {
          case ConnectivityManager.TYPE_MOBILE:
            return WIRELESS_CONNECTION;

          case ConnectivityManager.TYPE_WIFI:
            return WIFI_CONNECTION;

          default:
            break;
        }
      }
    } catch (Exception e) {
      DMLog.log(TAG, e, Log.ERROR);
    }

    return "";
  }
}
