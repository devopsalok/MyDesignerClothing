package com.mydesignerclothing.mobile.android.network.exceptions;


public class OfflineException extends RuntimeException {
  public OfflineException() {
    super("Application is offline");
  }
}
