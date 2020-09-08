package com.mydesignerclothing.mobile.android.about.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class AboutInfoListResponseModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("result")
    private List<AboutInfoList> aboutInfoList;

    public List<AboutInfoList> getAboutInfoList() {
        return aboutInfoList;
    }
}
