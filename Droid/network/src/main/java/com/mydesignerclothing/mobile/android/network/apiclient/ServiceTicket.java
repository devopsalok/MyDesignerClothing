package com.mydesignerclothing.mobile.android.network.apiclient;


public class ServiceTicket {

  private final String requestType;

  public ServiceTicket(String requestType) {
    this.requestType = requestType;
  }

  public String getRequestType() {
    return requestType;
  }

}
