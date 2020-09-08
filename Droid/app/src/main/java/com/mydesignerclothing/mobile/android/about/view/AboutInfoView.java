package com.mydesignerclothing.mobile.android.about.view;

import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import androidx.annotation.NonNull;

public interface AboutInfoView {

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorDialog(@NonNull NetworkError error);

    void onAboutInfoListSuccessResponse(AboutInfoListResponseModel aboutInfoListResponseModel);

    void onDetailPageSuccessResponse(DetailViewResponseModel detailViewResponseModel);
}
