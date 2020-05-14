package com.mydesignerclothing.mobile.android.commons.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

public class MetaData {

  private static final String TAG = MetaData.class.getSimpleName();

  @Nullable
  @VisibleForTesting
  public Bundle metaDataBundle;
  private String keyName;

  private MetaData(@NonNull Context context) {
    setBundle(context);
  }

  private void setBundle(@NonNull Context context) {
    ApplicationInfo applicationInfo;
    try {
      applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      this.metaDataBundle = applicationInfo.metaData;
    } catch (PackageManager.NameNotFoundException exception) {
      //NOSONAR
      // Cannot stub out the exception in tests
      CrashTracker.trackStackTrace(TAG, exception);
    }
  }

  private void setKeyName(@NonNull String keyName) {
    this.keyName = keyName;
  }

  @Nullable
  public String getKeyValue() {
    return metaDataBundle != null ? metaDataBundle.getString(keyName) : null;
  }

  public static class Builder {

    private String key;

    @SuppressWarnings("EmptyMethod")
    public Builder() {
      //NOSONAR
    }

    public Builder withMetaDataKey(@NonNull String key) {
      this.key = key;
      return this;
    }

    public MetaData create(@NonNull Context context) {
      MetaData metaData = new MetaData(context);
      metaData.setKeyName(key);
      return metaData;
    }
  }
}
