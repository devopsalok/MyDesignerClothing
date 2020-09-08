package com.mydesignerclothing.mobile.android.create.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class ProductOptionsList implements ProguardJsonMappable, Parcelable {
    @Expose
    @SerializedName("OptionGroupID")
    private String optionGroupId;
    @Expose
    @SerializedName("OptionGroupName")
    private String optionGroupName;
    @Expose
    @SerializedName("getProductOptions")
    private List<ProductOptionsGroupList> productOptionsGroupList;
    @Expose
    @SerializedName("getProductColor")
    private List<ProductColorsList> productColorsList;

    public static final Creator<ProductOptionsList> CREATOR = new Creator<ProductOptionsList>() {
        @Override
        public ProductOptionsList createFromParcel(Parcel in) {
            return new ProductOptionsList(in);
        }

        @Override
        public ProductOptionsList[] newArray(int size) {
            return new ProductOptionsList[size];
        }
    };

    protected ProductOptionsList(Parcel in) {
        optionGroupId = in.readString();
        optionGroupName = in.readString();
        productOptionsGroupList = in.createTypedArrayList(ProductOptionsGroupList.CREATOR);
        productColorsList = in.createTypedArrayList(ProductColorsList.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(optionGroupId);
        parcel.writeTypedList(productOptionsGroupList);
        parcel.writeTypedList(productColorsList);
    }

    public String getOptionGroupId() {
        return optionGroupId;
    }

    public String getOptionGroupName() {
        return optionGroupName;
    }

    public List<ProductOptionsGroupList> getProductOptionsGroupList() {
        return productOptionsGroupList;
    }

    public List<ProductColorsList> getProductColorsList() {
        return productColorsList;
    }
}
