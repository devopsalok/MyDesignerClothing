package com.mydesignerclothing.mobile.android.commons.tracking;

import java.util.Map;

public class AppAnalytics {

  /*public static final int AUTHENTICATED = 0;
  static final int LOGGED_OUT = 1;

  @IntDef({AUTHENTICATED, LOGGED_OUT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface AuthenticationState {
  }

  private static final String TAG = AppAnalytics.class.getSimpleName();

  @VisibleForTesting
  public static final String MARKETING_TRAFFIC_API_KEY = "tracking.adobe.API_KEY";
  private Application application;
  private Context context;
  private AttributionManager attributionManager;

  @Inject
  public AppAnalytics() {
  }

  private AppAnalytics(@Nullable Application application, @Nullable Context context) {
    this.application = application;
    this.context = context;
  }

  public boolean initialize() {
    if (isMetaDataValid()) {

      attributionManager = new AttributionManager.Builder().withApplication(application).withContext(context).build();
      boolean didAttributionLoad = attributionManager.load();
      MobileCore.setApplication(application);
      MobileCore.setLogLevel(BuildConfig.DEBUG ? LoggingMode.DEBUG : LoggingMode.ERROR);

      try {
        Analytics.registerExtension();
        UserProfile.registerExtension();
        Identity.registerExtension();
        Lifecycle.registerExtension();
        Audience.registerExtension();
        setAdvertisingId();
        if (didAttributionLoad) {
          registerAttributionExtension();
        }
        MobileCore.start(object -> MobileCore.configureWithAppID(getTrackingAPIKeyFromMetaData(context)));
      } catch (InvalidInitException e) {
        DMLog.log(TAG, e, Log.ERROR);
        return false;
      }
      return true;
    }
    return false;
  }

  private void registerAttributionExtension() {
    ExtensionErrorCallback extensionErrorCallback = (ExtensionErrorCallback<ExtensionError>) extensionError -> {
      if (extensionError != null) {
        DMLog.e(TAG, String.format("An error occurred while registering the AttributionExtension %d %s", extensionError.getErrorCode(), extensionError.getErrorName()));
      }
    };

    if (!MobileCore.registerExtension(attributionManager.getExtensionClass(),extensionErrorCallback)) {
      DMLog.e(TAG, "DID NOT LOAD ATTRIBUTION EXTENSION");
    }
  }

  @Nullable
  public String getMarketingCloudVisitorId(@Nullable String defaultId) {
    SharedPref sharedPref = SharedPref.getSharedPreference(context);
    if (sharedPref.get(ANALYTICS_VISITOR_ID_META_DATA_KEY, DEFAULT_STRING) == DEFAULT_STRING) {
      new Thread(() -> Identity.getExperienceCloudId(id -> sharedPref.put(ANALYTICS_VISITOR_ID_META_DATA_KEY, id))).start();
    }
    return sharedPref.get(ANALYTICS_VISITOR_ID_META_DATA_KEY, defaultId);
  }

  private void setAdvertisingId() {
    AsyncTask.execute(new Thread(() -> {
      try {
        AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        MobileCore.setAdvertisingIdentifier(adInfo != null ? adInfo.getId() : null);
      } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
        DLog.log(TAG, exception, Log.ERROR);
      }
    }));
  }

  @VisibleForTesting
  boolean isMetaDataValid() {
    if (context != null && application != null) {
      String ignoreAppId = context.getString(R.string.adobe_market_ignore_value);
      return !ignoreAppId.equalsIgnoreCase(getTrackingAPIKeyFromMetaData(context));
    }
    return false;
  }

  public void lifeCycleStart() {
    MobileCore.lifecycleStart(null);
  }

  public void lifeCyclePause() {
    MobileCore.lifecyclePause();
  }

  public boolean trackState(@NonNull String state, @NonNull Map<String, String> data) {
    try {
      wrapMarketingAction(true, state, data);
      return true;
    } catch (NullPointerException nullPointerException) {
      CrashTracker.trackStackTrace(TAG, nullPointerException);
      return false;
    }
  }

  public boolean trackAction(@NonNull String action, @NonNull Map<String, String> data) {
    try {
      wrapMarketingAction(false, action, data);
      return true;
    } catch (NullPointerException nullPointerException) {
      CrashTracker.trackStackTrace(TAG, nullPointerException);
      return false;
    }
  }

  boolean syncIdentityIdentifier(@NonNull String identifier, String skyMilesNumber, @AuthenticationState int authenticationState) {
    VisitorID.AuthenticationState auth;
    switch (authenticationState) {
      case AUTHENTICATED:
        auth = VisitorID.AuthenticationState.AUTHENTICATED;
        break;
      case LOGGED_OUT:
        auth = VisitorID.AuthenticationState.LOGGED_OUT;
        break;
      default:
        auth = VisitorID.AuthenticationState.UNKNOWN;
    }
    Identity.syncIdentifier(identifier, skyMilesNumber, auth);
    return true;
  }

  *//**
   * Currently,
   * {@link com.adobe.marketing.mobile.MobileCore#trackAction(String, Map)} and
   * {@link com.adobe.marketing.mobile.MobileCore#trackState(String, Map)}
   * do not check if core == null. This can lead to a NPE
   * This method is simply a wrapper to allow for testing to validate that we are handling this correctly
   * We should raise this back to Adobe and remove this wrapper as it serves only a testing purpose
   *
   * @param isTrackState - true/false value of whether this is for tracking state versus tracking action
   * @param itemTracked  - this is the action or state that is being tracked
   * @param data         - this is the the Map<String, String> of data being tracked
   *//*
  @VisibleForTesting
  void wrapMarketingAction(boolean isTrackState, @NonNull String itemTracked, @NonNull Map<String, String> data) {
    if (isTrackState) {
      MobileCore.trackState(itemTracked, data);
    } else {
      MobileCore.trackAction(itemTracked, data);
    }
  }

  @Nullable
  private String getTrackingAPIKeyFromMetaData(@NonNull Context context) {
    return new MetaData.Builder().withMetaDataKey(MARKETING_TRAFFIC_API_KEY).create(context).getKeyValue();
  }

  public static class Builder {

    private Application application;
    private Context context;

    public Builder withApplication(@NonNull Application application) {
      this.application = application;
      return this;
    }

    public Builder withContext(@NonNull Context context) {
      this.context = context;
      return this;
    }

    public AppAnalytics build() {
      return new AppAnalytics(application, context);
    }
  }*/
}
