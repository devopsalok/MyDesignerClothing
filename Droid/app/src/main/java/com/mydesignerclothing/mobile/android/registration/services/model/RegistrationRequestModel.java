package com.mydesignerclothing.mobile.android.registration.services.model;

import com.google.gson.annotations.Expose;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class RegistrationRequestModel implements ProguardJsonMappable {
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String password_confirm;
    @Expose
    private String fname;
    @Expose
    private String lname;
    @Expose
    private String gender;
    @Expose
    private String phone;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
