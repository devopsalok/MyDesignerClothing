package com.mydesignerclothing.mobile.android.commons.tracking;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.util.MetaData;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.annotation.VisibleForTesting;

public class AttributionManager {

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({
    CANONICAL_URL
  })
  public @interface AttributionType {
  }

  static final String CANONICAL_URL = "$canonical_url";
  @VisibleForTesting
  public static final String ATTRIBUTION_TOOL_META_DATA_KEY = "io.branch.sdk.BranchKey";
  private static final String TAG = AttributionManager.class.getSimpleName();
  private String canonicalUrl;
  private Context context;
  private Application application;
  private Activity activity;

  @SuppressWarnings("EmptyMethod")
  private AttributionManager() {
    //NO SONAR
  }

  private AttributionManager(@NonNull Context context, Application application, @Nullable Activity activity) {
    this.context = context;
    this.application = application;
    this.activity = activity;
  }

  /*public boolean load() {
    Branch branch = null;
    if (isMetaDataValid()) {
      if (isBranchTestModeEnabledFromMetaData()) {
        Branch.enableTestMode();
      }
      branch = AdobeBranch.getAutoInstance(application);
      AdobeBranch.registerAdobeBranchEvents(Collections.emptyList());
    }
    return branch != null;
  }

  public Class<AdobeBranchExtension> getExtensionClass() {
    return AdobeBranchExtension.class;
  }

  public void initializeListener(AttributionManagerListener listener, Uri uri) {
    if (isMetaDataValid()) {
      AdobeBranch.initSession((referringParams, error) -> {
        if (error == null) {
          extractData(referringParams);
        } else {
          DMLog.e(TAG, error.getMessage());
          canonicalUrl = null;
        }
        listener.attributedLinkAvailable(getCanonicalUrl());
      }, uri, activity);
    }
  }
*/
  private void extractData(JSONObject referringParams) {
    try {
      canonicalUrl = referringParams.getString(CANONICAL_URL);
    } catch (JSONException e) {
      DMLog.e(TAG, e);
      canonicalUrl = null;
    }
  }

  private Uri getCanonicalUrl() {
    return canonicalUrl == null ? null : Uri.parse(canonicalUrl);
  }

 /* @VisibleForTesting
  boolean isMetaDataValid() {
    String ignoreAppId = context.getString(R.string.branch_ignore_value);
    return !ignoreAppId.equalsIgnoreCase(getBranchAPIKeyFromMetaData());
  }

  private boolean isBranchTestModeEnabledFromMetaData() {
    return Boolean.parseBoolean(context.getString(R.string.branch_test_mode_enabled));
  }*/

  @Nullable
  private String getBranchAPIKeyFromMetaData() {
    return new MetaData.Builder().withMetaDataKey(ATTRIBUTION_TOOL_META_DATA_KEY).create(context).getKeyValue();
  }

  public interface AttributionManagerListener {
    void attributedLinkAvailable(Uri attributedLink);
  }

  public static class Builder {

    private Application application;
    private Context context;
    private Activity activity;

    public Builder withApplication(@NonNull Application application) {
      this.application = application;
      return this;
    }

    public Builder withContext(@NonNull Context context) {
      this.context = context;
      return this;
    }

    public Builder withActivity(@Nullable Activity activity) {
      this.activity = activity;
      return this;
    }

    public AttributionManager build() {
      return new AttributionManager(context, application, activity);
    }
  }
}
