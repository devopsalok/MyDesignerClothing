package com.mydesignerclothing.mobile.android.commons.tracking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;


import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.commons.network.NetworkStateIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;

public class BaseTracker {

  public static class Composer {

    private final Map<String, String> data = new HashMap<>();
    private final BaseTracker tracker;

    public Composer(BaseTracker tracker) {
      this.tracker = tracker;
    }

    public Composer setPageName(String pageName) {
      tracker.setPageName(pageName, data);
      return this;
    }

    public Composer setChannel(String channel) {
      tracker.setChannel(channel, data);
      return this;
    }

    public Composer setProperty(String name, String value) {
      data.put(name, value);
      return this;
    }

    public Composer setProperties(Map<String, String> properties) {
      tracker.setProperties(properties, data);
      return this;
    }

    public Composer setVariable(String name, String value) {
      data.put(name, value);
      return this;
    }

    public Composer setVariables(Map<String, String> variables) {
      tracker.setVariables(variables, data);
      return this;
    }

    public Composer setEvents(String ... events) {
      tracker.setEvents(events, data);
      return this;
    }

    public Composer setEventsAsString(String eventsCSV) {
      tracker.setEvents(eventsCSV, data);
      return this;
    }

    public Composer setProductsAsString(String products) {
      tracker.setProducts(products, data);
      return this;
    }

    public void track(String pageName) {
      tracker.doTrack(pageName, data);
    }

    public void trackAction(String pageName) {
      tracker.doTrackAction(pageName, data);
    }

    public void trackLink(String linkType, String linkName) {
      tracker.doLinkTrack(linkType, linkName, data);
    }
  }

  public static final String TAG = BaseTracker.class.getSimpleName();

  public static final String KEY_DEFAULT_LINK_TRACK_URL = "DEFAULT_LINK_TRACK_URL";
  public static final String KEY_LINK_TRACK_TYPE = "linkTrackType";
  public static final String KEY_CUSTOMLINK_LINKNAME = "customlink.linkname";
  public static final String KEY_LINK_NAME = "linkName";
  public static final String KEY_CHANNEL = "channel";
  public static final String KEY_PAGE_NAME = "pageName";
  public static final String KEY_PRODUCTS = "&&products";
  public static final String LINK_TRACK_TYPE_CUSTOM = "o";
  public static final String LINK_TRACK_TYPE_EXIT = "e";
  public static final String DEFAULT_LINK_TRACK_URL = "http://www.myDesignerClothing.com";
  public static final String KEY_ERROR_MESSAGE = "error.message";
  public static final String KEY_BUILD_VERSION = "build.version";
  public static final String KEY_APP_VERSIONNUMBER = "app.versionnumber";
  public static final String KEY_APP_OS_VERSION = "app.osversion";
  public static final String KEY_DEVICE_MANUFACTURERMODEL = "device.manufacturermodel";
  public static final String KEY_DEVICE_SCREENDENSITY = "device.screendensity";
  public static final String KEY_DEVICE_SCREENDIMENSIONS = "device.screendimensions";
  public static final String HOME_CHANNEL = "home";
  static final String ERROR_COUNT = "error.count";

  protected final TrackerUtils trackerUtils;
  protected Context context;
  //protected AppAnalytics appAnalytics;


  public BaseTracker(Context context) {
    this(context,
      new AppAnalytics(),
      new TrackerUtils(context, DeviceInfo.get(), AppInfo.get(context)));
  }

  public BaseTracker(Context context,
                     AppAnalytics appAnalytics, TrackerUtils trackerUtils) {
    this.context = context;
    //this.appAnalytics = appAnalytics;
    this.trackerUtils = trackerUtils;
  }

  public Composer createComposer() {
    return new Composer(this);
  }

  public void doTrack(String pageName, Map<String, String> data) {
    setAppVersion(data);
    setDeviceInfo(data);
    setDeviceDisplayInfo(data);
    setBuildVersion(data);
    if (isConnectedToInternet()) {
      trackCrashKeys(pageName);
      trackCrashLogs(data);
      //appAnalytics.trackState(pageName, data);
    }
  }

  public void doTrackAction(String page, Map<String, String> data) {
    if (isConnectedToInternet()) {
      //appAnalytics.trackAction(page, data);
    }
  }

  /**
   * Fire off a track link
   *
   * @param linkTrackType - use LINK_TRACK_TYPE_CUSTOM for custom, LINK_TRACK_TYPE_DOWNLOAD for download, or LINK_TRACK_TYPE_EXIT for exit
   * @param linkName
   * @param data
   */
  public void doLinkTrack(String linkTrackType, String linkName, Map<String, String> data) {
    if (isConnectedToInternet()) {
      data.put(KEY_DEFAULT_LINK_TRACK_URL, DEFAULT_LINK_TRACK_URL);
      data.put(KEY_LINK_TRACK_TYPE, linkTrackType);
      data.put(KEY_LINK_NAME, linkName);
      //appAnalytics.trackAction(linkName, data);
    }
  }

  /**
   * Set the page name
   *
   * @param pageName - name of the page that is being tracked
   * @param data - map of data to be tracked
   */
  public void setPageName(String pageName, Map<String, String> data) {
    data.put(KEY_PAGE_NAME, pageName);
  }

  /**
   * Set the channel
   *
   * @param channel - channel to associate with this tracking call
   * @param data - map of data to be tracked
   */
  public void setChannel(String channel, Map<String, String> data) {
    data.put(KEY_CHANNEL, channel);
  }

  /**
   * Set events
   *
   * @param events - Comma separated list of events to track with this tracking call
   * @param data - map of data to be tracked
   */
  public void setEvents(String events, Map<String, String> data) {
    if (events != null) {
      setEvents(events.split(","), data);
    }
  }

  /**
   * Set events
   *
   * @param events - array of events to track with this tracking call
   * @param data - map of data to be tracked
   */
  public void setEvents(String[] events, Map<String, String> data) {
    if(events != null) {
      for(String eventKey : events) {
        data.put(eventKey, "1");
      }
    }
  }

  /**
   * Set products
   *
   * @param products - Semicolon separated list of products to track with this tracking call
   * @param data
   */
  public void setProducts(String products, Map<String, String> data) {
    Matcher eventMatcher = Pattern.compile("event\\d+").matcher(products);
    ArrayList<String> eventList = new ArrayList<>();
    while (eventMatcher.find()) {
      eventList.add(eventMatcher.group(0));
    }
    HashSet<String> eventSet = new HashSet<>();
    eventSet.addAll(eventList);
    eventList.clear();
    eventList.addAll(eventSet);
    if (!eventList.isEmpty()) {
      String events = TextUtils.join(",", eventList);
      data.put("&&events", events);
    }
    data.put(KEY_PRODUCTS, products);
  }

  /**
   * Set the device display info.  prop11 = density, prop12 = resolution
   *
   * @param data - map of data to be tracked
   */
  public void setDeviceDisplayInfo(Map<String, String> data) {
    data.put(BaseTracker.KEY_DEVICE_SCREENDENSITY, trackerUtils.getDensity());
    data.put(BaseTracker.KEY_DEVICE_SCREENDIMENSIONS, trackerUtils.getResolution());
  }

  /**
   * Set the app version from the context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName.  This goes into eVar12 and prop9
   */
  public void setAppVersion(Map<String, String> data) {
    data.put(BaseTracker.KEY_APP_VERSIONNUMBER, trackerUtils.getVersion());
  }

  private void setBuildVersion(Map<String, String> data) {
    data.put(KEY_BUILD_VERSION, String.format(Locale.US, "%d", trackerUtils.getVersionCode()));
  }

  /**
   * Check if the user is connected to the internet.
   *
   * @return Returns true if there is internet connection.  Remember that this does not account for being on un-authenticated wi fi channels.
   */
  private boolean isConnectedToInternet() {
    return NetworkStateIdentifier.isConnectedToInternet((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE));
  }

  /**
   * Set variables
   *
   * @param variables - Map of omniture variable name to value
   * @param data - map of data to be tracked
   */
  public void setVariables(Map<String, String> variables, Map<String, String> data) {
    for (String key : variables.keySet()) {
      data.put(key, variables.get(key));
    }
  }

  /**
   * Set properties
   *
   * @param properties - Map of omniture property name to value
   * @param data - map of data to be tracked
   */
  public void setProperties(Map<String, String> properties, Map<String, String> data) {
    for (String prop : properties.keySet()) {
      data.put(prop, properties.get(prop));
    }
  }

  public void trackErrorMessage(String currentChannel, String message) {
    trackErrorMessage(currentChannel, message, null);
  }

  public void trackErrorMessage(String currentChannel, String message, String pageName) {
    HashMap<String, String> data = new HashMap<>();
    setChannel(currentChannel, data);
    setErrorMessage(message, data);
    setEvents(ERROR_COUNT, data);
    String pageNameToTrack = pageName == null ? EMPTY_STRING : pageName;
    doTrack(pageNameToTrack, data);
  }

/*  public void syncIdentityIdentifier(String identifier, String skyMilesNumber, @AppAnalytics.AuthenticationState int authenticationState) {
    appAnalytics.syncIdentityIdentifier(identifier, skyMilesNumber, authenticationState);
  }*/

  /**
   * Set the device info.  prop10 = os, prop13 = make and model
   *
   * @param data - map of data to be tracked
   */
  private void setDeviceInfo(Map<String, String> data) {
    data.put(BaseTracker.KEY_APP_OS_VERSION, trackerUtils.getOs());
    data.put(BaseTracker.KEY_DEVICE_MANUFACTURERMODEL, trackerUtils.getMakeModel());
  }

  /**
   * Set the error message to be associated with this tracking call.  This goes in as prop2
   *
   * @param message Error message
   * @param data - map of data to be tracked
   */
  protected void setErrorMessage(String message, Map<String, String> data) {
    data.put(KEY_ERROR_MESSAGE, message);
  }

  private void trackCrashKeys(String pageName) {
    if (pageName != null) {
      CrashTracker.trackCrashReportPage(pageName, context.getClass().getSimpleName());
    }
  }

  private void trackCrashLogs(Map<String, String> data) {
    if (data.containsKey(KEY_ERROR_MESSAGE)) {
      CrashTracker.trackAction(String.format("%s %s", CrashTracker.ACTION_ON_ERROR_MESSAGE_LOGGED, data.get(KEY_ERROR_MESSAGE)), context.getClass().getSimpleName());
    }
  }
}

