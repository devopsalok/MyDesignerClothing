package com.mydesignerclothing.mobile.android.about.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class AboutInfoList implements ProguardJsonMappable, Parcelable {
    @Expose
    @SerializedName("id")
    private String aboutInfoId;
    @Expose
    @SerializedName("title_en")
    private String aboutInfoTitle;

    private String title;

    public static final Parcelable.Creator<AboutInfoList> CREATOR = new Parcelable.Creator<AboutInfoList>() {
        @Override
        public AboutInfoList createFromParcel(Parcel source) {
            return new AboutInfoList(source);
        }

        @Override
        public AboutInfoList[] newArray(int size) {
            return new AboutInfoList[size];
        }
    };

    public String getAboutInfoId() {
        return aboutInfoId;
    }

    public String getAboutInfoTitle() {
        return aboutInfoTitle;
    }

    protected AboutInfoList(Parcel in) {
        this.aboutInfoId = in.readString();
        this.aboutInfoTitle = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.aboutInfoId);
        dest.writeString(this.aboutInfoTitle);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
