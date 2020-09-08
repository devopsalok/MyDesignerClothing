package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductAttributes implements ProguardJsonMappable {
    @Expose
    @SerializedName("productid")
    private String productId;
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

    public String getProductId() {
        return productId;
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
