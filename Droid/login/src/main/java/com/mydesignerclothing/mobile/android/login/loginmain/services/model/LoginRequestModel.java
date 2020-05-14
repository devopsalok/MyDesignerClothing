package com.mydesignerclothing.mobile.android.login.loginmain.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class LoginRequestModel implements ProguardJsonMappable {
    @Expose
    private String email;
    @Expose
    @SerializedName("pass")
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
