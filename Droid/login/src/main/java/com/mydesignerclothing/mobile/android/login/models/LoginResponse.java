package com.mydesignerclothing.mobile.android.login.models;

import com.google.gson.annotations.Expose;

public class LoginResponse {

  @Expose
  private String firstName;
  @Expose
  private String lastName;
  @Expose
  private boolean isLoggedIn;
  @Expose
  private boolean hasEmail;
  @Expose
  private boolean isEmailValid;
  @Expose
  private String loginMethod;
  @Expose
  private boolean rememberMe;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public boolean isHasEmail() {
    return hasEmail;
  }

  public boolean isEmailValid() {
    return isEmailValid;
  }

  public String getLoginMethod() {
    return loginMethod;
  }

  public boolean isRememberMe() {
    return rememberMe;
  }
}
