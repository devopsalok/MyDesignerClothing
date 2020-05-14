package com.mydesignerclothing.mobile.android.commons.tracking.networksecurity;

import android.content.Context;

import com.mydesignerclothing.mobile.android.commons.util.MetaData;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

public class NetworkSecurityTracker {

  @VisibleForTesting
  public static final String NETWORK_SECURITY_HEADER_KEY = "tracking.networksecurity.NetworkSecurityTracker.HEADER_KEY";
  public static final String NETWORK_SECURITY_DUMMY_HEADER_KEY = "tracking.networksecurity.NetworkSecurityTracker.DUMMY_HEADER_KEY";
  public static final String NETWORK_SECURITY_DUMMY_VALUE = "tracking.networksecurity.NetworkSecurityTracker.DUMMY_VALUE";
  private static String HEADER_KEY;
  private static String DUMMY_HEADER_KEY;
  private static String DUMMY_VALUE;
  private static final String NO_HEADER_KEY = "NO-HEADER-KEY";

  @SuppressWarnings("EmptyMethod")
  private NetworkSecurityTracker() {
    //NO SONAR
  }

  private static class InstanceHolder {
    static final NetworkSecurityTracker instance = new NetworkSecurityTracker();
  }

  @NonNull
  public static NetworkSecurityTracker getInstance() {
    return InstanceHolder.instance;
  }

  /*@NonNull
  public static String getSensorData() {
    return NetworkSecurityTracker.getInstance().getCYFSensorData();
  }*/

  @NonNull
  public static String getHeaderKey() {
    return HEADER_KEY;
  }

  @NonNull
  public static String getDummyHeaderKey() {
    return DUMMY_HEADER_KEY;
  }

  @NonNull
  public static String getDummyHeaderValue() {
    return DUMMY_VALUE;
  }


  // BotManager Core Methods //

 /* public void initialize(@NonNull Application application) {
    NetworkSecurityTracker.getInstance().initializeNetworkSecurityTracker(application);
  }*/

  /*private String getCYFSensorData() {
    return CYFMonitor.getSensorData();
  }*/

  public void setMetaDataValues(Context context) {
    HEADER_KEY = getValueFromMetadata(NETWORK_SECURITY_HEADER_KEY, context);
    DUMMY_HEADER_KEY = getValueFromMetadata(NETWORK_SECURITY_DUMMY_HEADER_KEY, context);
    DUMMY_VALUE = getValueFromMetadata(NETWORK_SECURITY_DUMMY_VALUE, context);
  }

  /*private void initializeNetworkSecurityTracker(Application application) {
    CYFMonitor.initialize(application);
  }*/

  private String getValueFromMetadata(String metaData, Context context) {
    return new MetaData.Builder().withMetaDataKey(metaData).create(context).getKeyValue();
  }

  public static boolean shouldAddDummyHeader() {
    return !DUMMY_HEADER_KEY.equals(NO_HEADER_KEY);
  }
}
