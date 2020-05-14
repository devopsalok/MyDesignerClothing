package com.mydesignerclothing.mobile.android.login.models;


public class LoginRequestDTO {

  private String userName;
  private String lastName;
  private String password;

  public LoginRequestDTO() {
  }

  public LoginRequestDTO(String userName, String lastName, String password) {
    this.userName = userName;
    this.lastName = lastName;
    this.password = password;
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
  
}
