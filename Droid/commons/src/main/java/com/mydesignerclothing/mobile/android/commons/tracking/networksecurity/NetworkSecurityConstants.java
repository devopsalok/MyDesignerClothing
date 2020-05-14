package com.mydesignerclothing.mobile.android.commons.tracking.networksecurity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import static java.util.Collections.unmodifiableList;

public class NetworkSecurityConstants {

  @VisibleForTesting
  static final List<String> URL_ENDPOINTS = unmodifiableList(
    Arrays.asList("/user/login", "/mobile/login"));

  @SuppressWarnings("EmptyMethod")
  private NetworkSecurityConstants() {
    //NOSONAR
  }


  public static boolean isNetworkSecurityEndPoint(@NonNull String url) {
    String checkUrl = url.toLowerCase(Locale.US);
    for (String endpoint : URL_ENDPOINTS) {
      if (checkUrl.endsWith(endpoint.toLowerCase(Locale.US))) {
        return true;
      }
    }
    return false;
  }

}
