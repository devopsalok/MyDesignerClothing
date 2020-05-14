package com.mydesignerclothing.mobile.android.commons.core;

import android.content.res.Resources;
import android.os.Build;

import com.mydesignerclothing.mobile.android.commons.R;

public class DeviceInfo {
  public String getAndroidOsVersion() {
    return Build.VERSION.RELEASE;
  }

  public String getPhoneMaker() {
    return Build.MANUFACTURER;
  }

  public String getPhoneModel() {
    return Build.PRODUCT;
  }

  public static DeviceInfo get() {
    return new DeviceInfo();
  }

  public String getDeviceType(Resources resources) {
    return resources.getBoolean(R.bool.isTablet) ? "Tablet" : "Phone";
  }
}
