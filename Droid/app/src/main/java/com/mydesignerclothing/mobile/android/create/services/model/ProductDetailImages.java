package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductDetailImages implements ProguardJsonMappable {
    @Expose
    @SerializedName("id_gallery")
    private String idGallery;
    @Expose
    @SerializedName("deleted")
    private String deleted;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("image")
    private String imageUrl;
    @Expose
    @SerializedName("type")
    private String type;

    public String getIdGallery() {
        return idGallery;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
