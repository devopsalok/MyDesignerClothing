package com.mydesignerclothing.mobile.android.network;


import android.content.res.Resources;
import android.net.ConnectivityManager;


import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.commons.network.NetworkStateIdentifier;

import java.util.Map;

import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.entry;
import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.hash;
import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.merge;


public class CustomHeaders {

  private static final String TL_OS_PREFIX = "Android";
  private static final String USER_AGENT = "User-Agent";
  private static final String FORMATTED_USER_AGENT = "%s, Android %s, %s, %s";
  private static final String TL_AOS_ID = "Tlaosid";
  private static final String TL_AOS_CNX = "Tlaoscnx";

  private final AppInfo appInfo;
  private final DeviceInfo deviceInfo;
  private final Resources resources;

  public CustomHeaders(AppInfo appInfo, DeviceInfo deviceInfo, Resources resources) {
    this.appInfo = appInfo;
    this.deviceInfo = deviceInfo;
    this.resources = resources;
  }

  public Map<String, String> get(ConnectivityManager connectivityManager) {
    Map<String, String> headers = hash(
      entry(USER_AGENT, userAgent(appInfo, deviceInfo, resources)),
      entry(TL_AOS_ID, deviceId(appInfo, deviceInfo)));

    String connectionType = NetworkStateIdentifier.getConnectionType(connectivityManager);
    if (!connectionType.isEmpty()) {
      merge(headers, entry(TL_AOS_CNX, connectionType));
    }
    return headers;
  }

  private String deviceId(AppInfo appInfo, DeviceInfo deviceInfo) {
    return String.format(
      "%s %s, %s %s, %s",
      TL_OS_PREFIX,
      deviceInfo.getAndroidOsVersion(),
      deviceInfo.getPhoneMaker(),
      deviceInfo.getPhoneModel(),
      appInfo.getVersionName()
    );
  }

  private String userAgent(AppInfo appInfo, DeviceInfo deviceInfo, Resources resources) {
    return String.format(
      FORMATTED_USER_AGENT,
      appInfo.getApplicationName(),
      deviceInfo.getAndroidOsVersion(),
      appInfo.getVersionName(),
      deviceInfo.getDeviceType(resources));
  }
}
