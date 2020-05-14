package com.mydesignerclothing.mobile.android.network.apiclient;

import androidx.annotation.NonNull;

public enum RequestType {
  V4("v4"),
  V3("v3"),
  V2("v2"),
  WEB_SERVER("web_server"),
  UNKNOWN("unknown");

  private String value;

  RequestType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static RequestType getRequestType(@NonNull String requestType) {
    RequestType returnRequestType;

    switch (requestType) {
      case "v4":
        returnRequestType = V4;
        break;
      case "v3":
        returnRequestType = V3;
        break;
      case "v2":
        returnRequestType = V2;
        break;
      case "web_server":
        returnRequestType = WEB_SERVER;
        break;
      default:
        returnRequestType = UNKNOWN;
        break;
    }

    return returnRequestType;
  }
}
