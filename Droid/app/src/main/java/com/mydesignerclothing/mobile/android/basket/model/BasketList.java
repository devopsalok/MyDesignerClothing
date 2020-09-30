package com.mydesignerclothing.mobile.android.basket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasketList {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("printcount")
    @Expose
    private String printcount;

    @SerializedName("message")
    @Expose
    private List<BasketListData> basketListDataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrintcount() {
        return printcount;
    }

    public void setPrintcount(String printcount) {
        this.printcount = printcount;
    }

    public List<BasketListData> getBasketListDataList() {
        return basketListDataList;
    }

    public void setBasketListDataList(List<BasketListData> basketListDataList) {
        this.basketListDataList = basketListDataList;
    }
}
