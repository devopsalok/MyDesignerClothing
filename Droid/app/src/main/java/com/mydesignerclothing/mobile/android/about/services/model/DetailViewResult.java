package com.mydesignerclothing.mobile.android.about.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class DetailViewResult implements ProguardJsonMappable {
    @Expose
    @SerializedName("title_en")
    private String detailTitle;

    @Expose
    @SerializedName("content_en")
    private String detailContent;

    public String getDetailTitle() {
        return detailTitle;
    }

    public String getDetailContent() {
        return detailContent;
    }
}
