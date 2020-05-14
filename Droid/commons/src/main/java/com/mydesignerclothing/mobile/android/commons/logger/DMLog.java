package com.mydesignerclothing.mobile.android.commons.logger;

import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.BuildConfig;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.rxutils.RxExceptionUtil;

import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;

public final class DMLog {

  private static final List<String> blackListedEnvForLogs = Collections.singletonList("production");

  public static void d(String tag, String message) {
    log(tag, message, Log.DEBUG);
  }

  public static void w(String tag, String message) {
    log(tag, message, Log.WARN);
  }

  public static void e(String tag, String message) {
    log(tag, message, Log.ERROR);
  }

  public static void e(String tag, Throwable t) {
    log(tag, t, Log.ERROR);
  }

  public static void l(String tag, String longMessage) {
    int maxLogSize = 1000;
    for (int i = 0; i <= longMessage.length() / maxLogSize; i++) {
      int start = i * maxLogSize;
      int end = (i + 1) * maxLogSize;
      end = end > longMessage.length() ? longMessage.length() : end;
      log(tag, i + "=> " + longMessage.substring(start, end), Log.VERBOSE);
    }
  }

  public static void log(String tag, @Nullable String message, int level) {
    if (!blackListedEnvForLogs.contains(BuildConfig.APP_ENV)) {
      Log.println(level, tag, message == null ? "null" : message);
    }
  }

  public static void log(String tag, Throwable t, int level) {
    Optional<String> rxAssemblyStackTrace = RxExceptionUtil.getRxAssemblyStackTrace(t);
    if (rxAssemblyStackTrace.isPresent()) {
      String stackTrace = rxAssemblyStackTrace.get() + Log.getStackTraceString(t);
      log(tag, stackTrace, level);
    } else {
      log(tag, Log.getStackTraceString(t), level);
    }
  }

  public static void withInfo(String message, int traceIndex) {
    Throwable stack = new Throwable().fillInStackTrace();
    StackTraceElement trace = stack.getStackTrace()[traceIndex];
    StringBuilder tag = new StringBuilder();
    tag.append("(")
      .append("ClassName: " + trace.getClassName())
      .append(", Method: " + trace.getMethodName())
      .append(", Line: " + trace.getLineNumber())
      .append(")");
    d(tag.toString(), message);
  }

  public static List<String> getBlackListedEnvironments() {
    return blackListedEnvForLogs;
  }
}
