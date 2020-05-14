package com.mydesignerclothing.mobile.android.login.loginmain.services;

import android.content.res.Resources;

import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import static com.mydesignerclothing.mobile.android.login.network.interceptors.RequestSessionInterceptor.INVALID_SESSION_ERROR;

public class ErrorResponse extends BaseResponse implements Serializable{

  public static final String NO_RECEIPTS_ERROR_CODE = "mob071";
  public static final String FORCE_APP_UPDATE_ERROR_CODE = "MOB998";

  private final boolean hasIoError;
  public Exception exception;
  private int httpStatusCode = 1;
  private String responseBody = "{}";

  public ErrorResponse(boolean hasIoError) {
    this.hasIoError = hasIoError;
  }

  public ErrorResponse(String errorMessage) {
    this.setErrorMessage(errorMessage);
    this.hasIoError = false;
    this.exception = null;
  }

  public ErrorResponse(String errorMessage, String errorHeader) {
    this.setErrorMessage(errorMessage);
    this.setErrorHeader(errorHeader);
    this.hasIoError = false;
    this.exception = null;
  }

  public ErrorResponse(boolean hasIoError, Exception exception) {
    this.hasIoError = hasIoError;
    this.exception = exception;
  }

  public ErrorResponse(String errorCode, String errorMessage, String errorHeader) {
    this.setErrorMessage(errorMessage);
    this.setErrorHeader(errorHeader);
    this.setErrorCode(errorCode);
    this.hasIoError = false;
    this.exception = null;
  }

  public ErrorResponse(Map map) {
    setBaseResponseElements(map);
    hasIoError = false;
  }

  public ErrorResponse(boolean hasIoError, int statusCode) {
    this(hasIoError);
    setHttpStatusCode(statusCode);
  }

  public ErrorResponse(int httpStatusCode, String errorResponse) {
    this.setErrorMessage(errorResponse);
    setHttpStatusCode(httpStatusCode);
    this.hasIoError = false;
    this.exception = null;
  }

  public static ErrorResponse createErrorResponse(NetworkError networkError, Resources resources){
    return new ErrorResponse(networkError.getErrorCode(),networkError.getErrorMessage(resources), networkError.getErrorTitle(resources));
  }

  public boolean hasIOError() {
    return hasIoError;
  }

  public boolean hasIOException() {
    return exception instanceof IOException;
  }

  public boolean hasInterruptedException() {
    return exception instanceof InterruptedException;
  }

  public boolean isAppUnavailable() {
    return false;
  }

  public boolean hasErrorCode() {
    return getErrorCode() != null;
  }

  public boolean hasInvalidLoginSessionErrorCode() {
    return INVALID_SESSION_ERROR.equalsIgnoreCase(getErrorCode());
  }

  public void setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public String getHttpStatusCodeString() {
    return String.valueOf(httpStatusCode);
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }
}
