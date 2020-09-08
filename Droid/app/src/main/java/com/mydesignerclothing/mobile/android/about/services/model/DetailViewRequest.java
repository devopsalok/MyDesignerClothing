package com.mydesignerclothing.mobile.android.about.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class DetailViewRequest implements ProguardJsonMappable {
    @Expose
    @SerializedName("pageId")
    private String pageId;

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
