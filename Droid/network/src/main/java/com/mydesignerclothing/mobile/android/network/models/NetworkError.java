package com.mydesignerclothing.mobile.android.network.models;


import android.content.res.Resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.network.R;

import java.util.Objects;

import androidx.annotation.StringRes;

import static java.lang.String.valueOf;

public class NetworkError {

  private static final int CLIENT_ERROR_CODE = -99;
  private static final int OFFLINE_ERROR_CODE = -100;
  private static final int NETWORK_FAILURE_ERROR_CODE = -101;

  @Expose
  private String errorCode = String.valueOf(CLIENT_ERROR_CODE);
  @Expose
  @SerializedName("message")
  private String errorMessage;
  @Expose
  private String errorHeader;
  @Expose
  private String errorType;
  @Expose
  private long retryTimeout;
  @Expose
  private boolean unrecoverable = false;
  @Expose
  @SerializedName("status")
  private String errorStatus;

  @StringRes
  private int defaultError = R.string.tech_diff_error;
  @StringRes
  private int defaultErrorTitle = R.string.default_error_title;

  private Object tag;

  @SuppressWarnings("EmptyMethod")
  public NetworkError() {
    //NOSONAR
  }

  public NetworkError(String errorCode, String errorMessage, String errorHeader) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorHeader = errorHeader;
  }

  public NetworkError(String errorCode, String errorMessage, String errorType, long timeout, String errorHeader) {
    this(errorCode, errorMessage, errorHeader);
    this.errorType = errorType;
    this.retryTimeout = timeout;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage(Resources resources) {
    if ((errorMessage == null || errorMessage.isEmpty()) && resources != null) {
      return resources.getString(defaultError);
    }
    return errorMessage;
  }

  public String getErrorTitle(Resources resources) {
    if ((errorHeader == null || errorHeader.isEmpty()) && resources != null) {
      return resources.getString(defaultErrorTitle);
    }
    return errorHeader;
  }

  public String getErrorType() {
    return errorType;
  }

  public long getRetryTimeout() {
    return retryTimeout;
  }

  public boolean isRecoverable() {
    return !unrecoverable;
  }

  public void setUnrecoverable(boolean unrecoverable) {
    this.unrecoverable = unrecoverable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    NetworkError error = (NetworkError) o;

    if (defaultError != error.defaultError) {
      return false;
    }
    if (retryTimeout != error.retryTimeout) {
      return false;
    }
    if (!Objects.equals(errorCode, error.errorCode)) {
      return false;
    }
    if (!Objects.equals(errorMessage, error.errorMessage)) {
      return false;
    }
    if (!Objects.equals(errorHeader, error.errorHeader)) {
      return false;
    }
    if (unrecoverable != error.unrecoverable) {
      return false;
    }
    return Objects.equals(errorType, error.errorType);
  }

  @Override
  public int hashCode() {
    int result = errorCode != null ? errorCode.hashCode() : 0;
    result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
    result = 31 * result + (errorHeader != null ? errorHeader.hashCode() : 0);
    result = 31 * result + (errorType != null ? errorType.hashCode() : 0);
    result = 31 * result + defaultError;
    result = 31 * result + (int) (retryTimeout ^ (retryTimeout >>> 32));
    result = 31 * result + Boolean.valueOf(unrecoverable).hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "NetworkError{" +
      "errorCode='" + errorCode + '\'' +
      ", errorMessage='" + errorMessage + '\'' +
      ", errorHeader='" + errorHeader + '\'' +
      ", errorType='" + errorType + '\'' +
      ", defaultError=" + defaultError +
      ", retryTimeout=" + retryTimeout + '\'' +
      ", unrecoverable=" + unrecoverable +
      '}';
  }

  public static NetworkError offlineError() {
    return new NetworkError(valueOf(OFFLINE_ERROR_CODE), "Application is offline", "We're sorry...");
  }

  public static NetworkError networkFailureError() {
    return new NetworkError(valueOf(NETWORK_FAILURE_ERROR_CODE), null, null);
  }

  public boolean isOfflineError() {
    return valueOf(OFFLINE_ERROR_CODE).equals(errorCode);
  }

  public boolean isNetworkFailureError() {
    return valueOf(NETWORK_FAILURE_ERROR_CODE).equals(errorCode);
  }

  public Object getTag() {
    return tag;
  }

  public void setTag(Object tag) {
    this.tag = tag;
  }
}
