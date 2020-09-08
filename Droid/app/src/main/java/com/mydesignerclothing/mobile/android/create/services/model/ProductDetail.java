package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductDetail implements ProguardJsonMappable {
    @Expose
    @SerializedName("productShown")
    private String productShown;
    @Expose
    @SerializedName("ProductID")
    private String productId;
    @Expose
    @SerializedName("ProductBrandID")
    private String productBrandId;
    @Expose
    @SerializedName("ProductSKU")
    private String productSKU;
    @Expose
    @SerializedName("ProductUrl")
    private String productUrl;
    @Expose
    @SerializedName("ProductName_en")
    private String productName;
    @Expose
    @SerializedName("ProductPrice")
    private String productPrice;
    @Expose
    @SerializedName("SalePrice")
    private String salePrice;
    @Expose
    @SerializedName("ProductWeight")
    private String productWeight;
    @Expose
    @SerializedName("ProductCartDesc")
    private String productCartDesc;
    @Expose
    @SerializedName("ProductShortDesc")
    private String productShortDesc;
    @Expose
    @SerializedName("ProductLongDesc")
    private String productLongDesc;
    @Expose
    @SerializedName("ProductThumb")
    private String productThumb;
    @Expose
    @SerializedName("ProductImage")
    private String productImage;
    @Expose
    @SerializedName("ProductCategoryID")
    private String productCategoryID;
    @Expose
    @SerializedName("ProductUpdateDate")
    private String productUpdateDate;
    @Expose
    @SerializedName("ProductStock")
    private String productStock;
    @Expose
    @SerializedName("ProductLive")
    private String productLive;
    @Expose
    @SerializedName("ProductUnlimited")
    private String productUnlimited;
    @Expose
    @SerializedName("ProductLocation")
    private String productLocation;
    @Expose
    @SerializedName("isSale")
    private String isSale;
    @Expose
    @SerializedName("isNew")
    private String isNew;
    @Expose
    @SerializedName("isHot")
    private String isHot;
    @Expose
    @SerializedName("isRecommend")
    private String isRecommend;
    @Expose
    @SerializedName("ProductAttributes")
    private String productAttributes;
    @Expose
    @SerializedName("deleted")
    private String deleted;
    @Expose
    @SerializedName("ProductType")
    private String productType;
    @Expose
    @SerializedName("producttypepostion")
    private String productTypePosition;

    public String getProductShown() {
        return productShown;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public String getProductCartDesc() {
        return productCartDesc;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public String getProductLongDesc() {
        return productLongDesc;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductCategoryID() {
        return productCategoryID;
    }

    public String getProductUpdateDate() {
        return productUpdateDate;
    }

    public String getProductStock() {
        return productStock;
    }

    public String getProductLive() {
        return productLive;
    }

    public String getProductUnlimited() {
        return productUnlimited;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public String getIsSale() {
        return isSale;
    }

    public String getIsNew() {
        return isNew;
    }

    public String getIsHot() {
        return isHot;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public String getProductAttributes() {
        return productAttributes;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductTypePosition() {
        return productTypePosition;
    }
}
