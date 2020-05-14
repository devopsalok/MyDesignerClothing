package com.mydesignerclothing.mobile.android.commons.tracking;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.mydesignerclothing.mobile.android.commons.BuildConfig;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.rxutils.RxExceptionUtil;

import androidx.annotation.NonNull;
import io.fabric.sdk.android.Fabric;

import static com.mydesignerclothing.mobile.android.commons.logger.DMLog.getBlackListedEnvironments;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

public final class CrashTracker {
  private static final String KEY_CURRENT_ACTION = "current_action";
  private static final String KEY_TEALEAF_TLTSID = "TLTSID";
  private static final String KEY_TEALEAF_TLTHID = "TLTHID";
  private static final String KEY_LOG_STACK_TRACE = "stack_trace";
  private static final String KEY_CURRENT_URL = "current_url";
  private static final String ACTION_NAME_NOT_SUPPLIED = "ACTION NAME NOT SUPPLIED!!!";
  private static final String EMPTY_STRING = "";
  private static final String LOG_MSG_SEPARATOR = " : ";
  private static final String PAGE_NAME_NOT_SUPPLIED = "PAGE NAME NOT SUPPLIED!!!";
  private static final String URL_NOT_SUPPLIED = "URL NOT SUPPLIED";
  private static final String KEY_CURRENT_PAGE = "current_page";

  public static final String ACTION_ON_BACK_PRESSED = "onBackPressed";
  public static final String ACTION_ON_ERROR_MESSAGE_LOGGED = "onErrorMessageLogged";
  public static final String ACTION_APP_UNAVAILABLE_BLOCKED = "onAppUnavailableBlocked";
  public static final String ACTION_APP_UNAVAILABLE_SHOWN = "onAppUnavailableShown";
  public static final String ACTION_SPICE_REQUEST_START_BLOCKED = "onSpiceRequestManagerStartBlocked";
  public static final String ACTION_SPICE_REQUEST_EXECUTE_BLOCKED = "onSpiceRequestManagerExecuteBlocked";
  public static final String ACTION_SPICE_REQUEST_QUEUED_ITEM_FOUND = "onSpiceRequestManagerQueuedItemFound";
  public static final String ACTION_SPICE_REQUEST_MANAGER_START_LOGGED = "onSpiceRequestManagerStartLogged";

  public static void initialize(Context applicationContext, boolean isDisabled) {
    if (!Fabric.isInitialized()) {
      CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(isDisabled).build();
      Fabric.with(applicationContext, new Crashlytics.Builder().core(core).build());
    }
  }

  public static void trackApplicationCalledUrlPath(@NonNull String urlPath, @NonNull String tag) {
    String loggedString = buildKeyMessage(urlPath, URL_NOT_SUPPLIED);
    logMessage(KEY_CURRENT_URL, loggedString, buildLogMessage(KEY_CURRENT_URL, loggedString), tag);
  }

  public static void trackCrashReportPage(String pageName, String tag) {
    String loggedString = buildKeyMessage(pageName, PAGE_NAME_NOT_SUPPLIED);
    logMessage(KEY_CURRENT_PAGE, loggedString, buildLogMessage(KEY_CURRENT_PAGE, loggedString), tag);
  }

  public static void trackAction(String actionName, String tag) {
    String loggedString = buildKeyMessage(actionName, ACTION_NAME_NOT_SUPPLIED);
    logMessage(KEY_CURRENT_ACTION, loggedString, buildLogMessage(KEY_CURRENT_ACTION, loggedString), tag);
  }

  public static void trackTeaLeafData(String tag, String tealeafHitCookieValue, String tealeafSessionCookieValue) {
    String loggedMessage;
    if (tealeafHitCookieValue != null) {
      loggedMessage = buildKeyMessage(tealeafHitCookieValue, EMPTY_STRING);
      logMessage(KEY_TEALEAF_TLTHID, loggedMessage, buildLogMessage(KEY_TEALEAF_TLTHID, tealeafHitCookieValue), tag);
    }
    if (tealeafSessionCookieValue != null) {
      loggedMessage = buildKeyMessage(tealeafSessionCookieValue, EMPTY_STRING);
      logMessage(KEY_TEALEAF_TLTSID, loggedMessage, buildLogMessage(KEY_TEALEAF_TLTSID, tealeafSessionCookieValue), tag);
    }
  }

  public static void trackStackTrace(String tag, Throwable exception) {
    if (!Fabric.isInitialized()) {
      return;
    }

    String exceptionMessage = String.format("%s - %s", getRootCauseMessage(exception), exception == null ? "" : getStackTrace(exception));
    String keyMessage = exceptionMessage;
    Throwable exceptionToTrack = exception;

    Optional<String> rxAssemblyStackTrace = RxExceptionUtil.getRxAssemblyStackTrace(exception);
    if (rxAssemblyStackTrace.isPresent()) {
      keyMessage = rxAssemblyStackTrace.get();
      exceptionToTrack = new Exception(keyMessage, exception);
    }

    Crashlytics.logException(exceptionToTrack);
    logMessage(KEY_LOG_STACK_TRACE, keyMessage, exceptionMessage, tag);
  }


  private static void logMessage(String key, String keyMessage, String logMessage, String tag) {
    if (!Fabric.isInitialized()) {
      return;
    }

    Crashlytics.getInstance().setString(key, keyMessage);
    if (!getBlackListedEnvironments().contains(BuildConfig.APP_ENV)) {
      Crashlytics.log(Log.DEBUG, tag, logMessage);
    } else {
      Crashlytics.log(logMessage);
    }
  }

  private static String buildLogMessage(String key, String message) {
    return key + LOG_MSG_SEPARATOR + message;
  }

  private static String buildKeyMessage(String message, String defaultValue) {
    return message != null && !EMPTY_STRING.equalsIgnoreCase(message) ? message : defaultValue;
  }
}
