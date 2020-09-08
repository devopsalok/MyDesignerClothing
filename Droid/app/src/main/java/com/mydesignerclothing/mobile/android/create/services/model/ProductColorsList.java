package com.mydesignerclothing.mobile.android.create.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductColorsList implements ProguardJsonMappable, Parcelable {
    @Expose
    @SerializedName("OptionID")
    private String optionId;
    @Expose
    @SerializedName("OptionGroupID")
    private String optionGroupId;
    @Expose
    @SerializedName("OptionName")
    private String optionName;

    public static final Creator<ProductColorsList> CREATOR = new Creator<ProductColorsList>() {
        @Override
        public ProductColorsList createFromParcel(Parcel in) {
            return new ProductColorsList(in);
        }

        @Override
        public ProductColorsList[] newArray(int size) {
            return new ProductColorsList[size];
        }
    };
    protected ProductColorsList(Parcel in) {
        optionId = in.readString();
        optionGroupId = in.readString();
        optionName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(optionId);
        dest.writeString(optionGroupId);
        dest.writeString(optionName);
    }

    public String getOptionId() {
        return optionId;
    }

    public String getOptionGroupId() {
        return optionGroupId;
    }

    public String getOptionName() {
        return optionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
