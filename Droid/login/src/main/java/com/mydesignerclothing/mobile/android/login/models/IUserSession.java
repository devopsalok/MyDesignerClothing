package com.mydesignerclothing.mobile.android.login.models;

public interface IUserSession {
  long getId();

  String getUsername();

  String getFirstName();

  String getLastName();

  Boolean getKeepLoggedIn();

}
