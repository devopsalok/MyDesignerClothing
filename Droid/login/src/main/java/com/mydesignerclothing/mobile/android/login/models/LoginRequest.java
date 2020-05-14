package com.mydesignerclothing.mobile.android.login.models;

import android.content.Context;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class LoginRequest implements ProguardJsonMappable {
  //private RequestInfo requestInfo;
  private String userName;
  private String lastName;
  private String password;

  public LoginRequest(Context context) {
    //this.requestInfo = RequestInfo.create(AppInfo.get(context), DeviceInfo.get());
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /*public RequestInfo getRequestInfo() {
    return requestInfo;
  }

  public void setRequestInfo(RequestInfo requestInfo) {
    this.requestInfo = requestInfo;
  }*/
}
