package com.mydesignerclothing.mobile.android.create.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductOptionsGroupList implements ProguardJsonMappable, Parcelable {
    @Expose
    @SerializedName("OptionID")
    private String optionId;
    @Expose
    @SerializedName("OptionGroupID")
    private String optionGroupId;
    @Expose
    @SerializedName("OptionName")
    private String optionName;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("productSKU")
    private String productSKU;
    @Expose
    @SerializedName("attribute_id")
    private String attributeId;
    @Expose
    @SerializedName("attribute_groupid")
    private String attributeGroupId;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("qty")
    private String quantity;

    public static final Parcelable.Creator<ProductOptionsGroupList> CREATOR = new Parcelable.Creator<ProductOptionsGroupList>() {
        @Override
        public ProductOptionsGroupList createFromParcel(Parcel in) {
            return new ProductOptionsGroupList(in);
        }

        @Override
        public ProductOptionsGroupList[] newArray(int size) {
            return new ProductOptionsGroupList[size];
        }
    };

    protected ProductOptionsGroupList(Parcel in) {
        optionId = in.readString();
        optionGroupId = in.readString();
        optionName = in.readString();

        id = in.readString();
        productSKU = in.readString();
        attributeId = in.readString();

        attributeGroupId = in.readString();
        optionGroupId = in.readString();
        image = in.readString();

        price = in.readString();
        quantity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(optionId);
        dest.writeString(optionGroupId);
        dest.writeString(optionName);

        dest.writeString(id);
        dest.writeString(productSKU);
        dest.writeString(attributeId);

        dest.writeString(attributeGroupId);
        dest.writeString(optionGroupId);
        dest.writeString(image);

        dest.writeString(price);
        dest.writeString(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getId() {
        return id;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public String getAttributeGroupId() {
        return attributeGroupId;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
