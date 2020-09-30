package com.mydesignerclothing.mobile.android.basket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketListData {
    @SerializedName("id")
    @Expose
    private String itemid;

    @SerializedName("qty")
    @Expose
    private String quantity;

    @SerializedName("price")
    @Expose
    private String priceval;

    @SerializedName("name")
    @Expose
    private String itemname;


    @SerializedName("options")
    @Expose
    private String options;


    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("merchant")
    @Expose
    private String merchant;

    @SerializedName("stock")
    @Expose
    private String stock;

    @SerializedName("rowid")
    @Expose
    private String rowid;


    @SerializedName("subtotal")
    @Expose
    private String subtotal;


    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceval() {
        return priceval;
    }

    public void setPriceval(String priceval) {
        this.priceval = priceval;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
