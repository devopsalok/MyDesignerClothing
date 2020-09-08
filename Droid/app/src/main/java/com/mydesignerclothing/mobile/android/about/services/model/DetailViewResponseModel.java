package com.mydesignerclothing.mobile.android.about.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class DetailViewResponseModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("result")
    private DetailViewResult result;

    public DetailViewResult getResult() {
        return result;
    }
}
