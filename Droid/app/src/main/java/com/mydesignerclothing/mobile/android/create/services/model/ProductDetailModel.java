package com.mydesignerclothing.mobile.android.create.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;

import java.util.List;

public class ProductDetailModel implements ProguardJsonMappable {
    @Expose
    @SerializedName("getProductImages")
    private List<ProductDetailImages> productDetailImagesList;
    @Expose
    @SerializedName("productid")
    private String productId;
    @Expose
    @SerializedName("ProductShortDesc")
    private String productShortDesc;
    @Expose
    @SerializedName("ProductLongDesc")
    private String productLongDesc;
    @Expose
    @SerializedName("getAttributes")
    private List<ProductAttributes> productAttributesList;
    @Expose
    @SerializedName("getOptions")
    private List<ProductOptionsList> productOptionsList;
    @Expose
    @SerializedName("ProductBrands")
    private ProductBrands productBrands;
    @Expose
    @SerializedName("product_detail")
    private ProductDetail productDetail;
    @Expose
    @SerializedName("producttypepostion")
    private String productTypePosition;

    public List<ProductDetailImages> getProductDetailImagesList() {
        return productDetailImagesList;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public String getProductLongDesc() {
        return productLongDesc;
    }

    public List<ProductAttributes> getProductAttributesList() {
        return productAttributesList;
    }

    public List<ProductOptionsList> getProductOptionsList() {
        return productOptionsList;
    }

    public ProductBrands getProductBrands() {
        return productBrands;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public String getProductTypePosition() {
        return productTypePosition;
    }
}
