package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

public class ProductsListModel implements ProguardJsonMappable {
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
    private String productSku;
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
    @SerializedName("image")
    private String productImage;
    @Expose
    @SerializedName("ProductCategoryID")
    private String productCategoryId;
    @Expose
    @SerializedName("ProductStock")
    private String productStock;
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

    public String getProductSku() {
        return productSku;
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

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public String getProductStock() {
        return productStock;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductTypePosition() {
        return productTypePosition;
    }
}
