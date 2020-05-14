package com.mydesignerclothing.mobile.android.network.exceptions;


public class V2ErrorException extends RuntimeException {
  private final transient String errorResponse;

  public V2ErrorException(String errorResponse) {
    super(errorResponse);
    this.errorResponse = errorResponse;
  }

  public String getErrorResponse() {
    return errorResponse;
  }
}
