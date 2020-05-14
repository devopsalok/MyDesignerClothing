package com.mydesignerclothing.mobile.android.commons.core;


import android.content.Context;
import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.R;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

public class AppInfo {

  private static final String EMPTY_STRING = "";
  private static final String TAG = AppInfo.class.getSimpleName();
  private Context context;

  public AppInfo(Context context) {
    this.context = context;
  }

  public String getVersionName() {
    try {
      return context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName;
    } catch (Exception e) {
      DMLog.e(TAG, Log.getStackTraceString(e));
    }
    return EMPTY_STRING;
  }

  public int getVersionCode() {
    try {
      return context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionCode;
    } catch (Exception e) {
      DMLog.e(TAG, Log.getStackTraceString(e));
    }
    return 0;
  }

  public static AppInfo get(Context context) {
    return new AppInfo(context);
  }

  public String getApplicationName() {
    return context.getString(R.string.user_agent_app_name);
  }
}
