package com.mydesignerclothing.mobile.android.commons.environment;

public class InvalidConfigException extends RuntimeException {
  public InvalidConfigException(Throwable cause) {
    super("Please check your environment configuration!", cause);
  }
}
