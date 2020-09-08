package com.mydesignerclothing.mobile.android.create.view;

public interface CreateInfoListener {
    void onProductImageClick(int index);

    void invokeProductDetailEvent(String productId);

    void onCancelButtonClicked();

    void onTakePictureButtonClicked();

    void onChooseGalleryButtonClicked();

    void onOkButtonClicked();

}
