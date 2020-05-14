package com.mydesignerclothing.mobile.android.commons.tracking.performance;

import java.lang.annotation.Retention;

import androidx.annotation.StringDef;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class PerformanceMetricConstants {

  public static final String LOGIN = "Login";

  static final String DYNATRACE_APPLICATION_ID = "tracking.performance.PerformanceLogger.APPLICATION_ID";
  static final String DYNATRACE_AGENT_ENVIRONMENT = "tracking.performance.PerformanceLogger.AGENT_ENVIRONMENT";
  static final String DYNATRACE_CLUSTER_URL = "tracking.performance.PerformanceLogger.CLUSTER_URL";

  @Retention(SOURCE)
  @StringDef({
    LOGIN
  })
  public @interface ActionsTracked {
  }

}
