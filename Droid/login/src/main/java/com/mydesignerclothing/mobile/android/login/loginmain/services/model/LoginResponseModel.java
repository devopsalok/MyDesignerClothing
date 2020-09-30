package com.mydesignerclothing.mobile.android.login.loginmain.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class LoginResponseModel implements ProguardJsonMappable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<LoginResponseResult> loginResponseResult;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LoginResponseResult> getLoginResponseResult() {
        return loginResponseResult;
    }

    public void setLoginResponseResult(List<LoginResponseResult> loginResponseResult) {
        this.loginResponseResult = loginResponseResult;
    }
}
