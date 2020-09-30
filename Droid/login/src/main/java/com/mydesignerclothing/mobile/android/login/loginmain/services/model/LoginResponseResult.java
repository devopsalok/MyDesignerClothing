package com.mydesignerclothing.mobile.android.login.loginmain.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseResult {

    @SerializedName("id")
    @Expose
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
