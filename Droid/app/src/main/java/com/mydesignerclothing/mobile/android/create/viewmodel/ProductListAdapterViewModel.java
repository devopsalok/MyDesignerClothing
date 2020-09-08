package com.mydesignerclothing.mobile.android.create.viewmodel;


import com.mydesignerclothing.mobile.android.create.services.model.ProductsListModel;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;

public class ProductListAdapterViewModel {

    private ProductsListModel productsListModel;
    private CreateInfoListener createInfoListener;

    public ProductListAdapterViewModel(ProductsListModel productsListModel, CreateInfoListener createInfoListener) {
        this.productsListModel = productsListModel;
        this.createInfoListener = createInfoListener;
    }

    public String getProductName() {
        return (productsListModel.getProductName());
    }

    public String getProductUrl() {
        return (productsListModel.getProductImage());
    }

    public String getProductPrice() {
        return ("Price : " + productsListModel.getProductPrice());
    }

    public void invokeProductDetailEvent() {
        createInfoListener.invokeProductDetailEvent(productsListModel.getProductId());
    }
}
