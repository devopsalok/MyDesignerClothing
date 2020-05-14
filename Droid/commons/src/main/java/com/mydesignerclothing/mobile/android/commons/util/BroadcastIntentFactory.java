package com.mydesignerclothing.mobile.android.commons.util;

import android.content.Context;
import android.content.Intent;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

public class BroadcastIntentFactory {

  @VisibleForTesting
  static final String BROADCAST_PERMISSION_FORMAT = "%s.BROADCAST";

  @NonNull
  public static Intent intent(@NonNull String action, @NonNull Context context) {
    return new Intent(action)
      .setPackage(context.getPackageName());
  }

  @NonNull
  public static Intent intentReceivedByClass(@NonNull String action, @NonNull Class cls, @NonNull Context context) {
    return new Intent(action)
      .setClass(context, cls);
  }

  @NonNull
  public static String getBroadcastPermission(@NonNull Context context) {
    return String.format(Locale.US, BROADCAST_PERMISSION_FORMAT, context.getPackageName());
  }
}
