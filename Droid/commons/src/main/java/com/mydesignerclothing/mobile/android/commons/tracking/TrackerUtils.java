package com.mydesignerclothing.mobile.android.commons.tracking;

import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;

public class TrackerUtils {
  private final AppInfo appInfo;
  private Context context;
  private DeviceInfo deviceInfo;
  private String resolution;
  private String density;
  private String visitorId;

  public TrackerUtils(Context context, DeviceInfo deviceInfo, AppInfo appInfo) {
    this.context = context;
    this.deviceInfo = deviceInfo;
    this.appInfo = appInfo;
    setDeviceDisplayInfo();
    setVisitorId();
  }

  private void setVisitorId() {
    visitorId = Settings.Secure.getString(this.context.getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  private void setDeviceDisplayInfo() {
    android.util.DisplayMetrics displaymetrics = new android.util.DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

    resolution = Integer.toString(displaymetrics.widthPixels) + " x " + Integer.toString(displaymetrics.heightPixels);
    density = Integer.toString(displaymetrics.densityDpi) + " dpi";
  }

  private WindowManager getWindowManager() {
    return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }

  public String getVersion() {
    return appInfo.getVersionName();
  }

  public String getVisitorId() {
    return visitorId;
  }

  public int getVersionCode() {
    return appInfo.getVersionCode();
  }

  public String getResolution() {
    return resolution;
  }

  public String getDensity() {
    return density;
  }

  public String getMakeModel() {
    return deviceInfo.getPhoneMaker() + " " + deviceInfo.getPhoneModel();
  }

  public String getOs() {
    return deviceInfo.getAndroidOsVersion();
  }
}
