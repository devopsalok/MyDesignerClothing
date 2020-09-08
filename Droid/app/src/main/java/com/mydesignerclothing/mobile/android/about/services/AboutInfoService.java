package com.mydesignerclothing.mobile.android.about.services;

import com.mydesignerclothing.mobile.android.about.services.apiclient.AboutInfoApiClient;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListRequestModel;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewRequest;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.apiclient.ShopInfoApiClient;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesRequestModel;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;

import io.reactivex.Observable;

public class AboutInfoService {

    private AboutInfoApiClient aboutInfoApiClient;

    public AboutInfoService(AboutInfoApiClient aboutInfoApiClient) {
        this.aboutInfoApiClient = aboutInfoApiClient;
    }

    public Observable<AboutInfoListResponseModel> getAboutInfoList(AboutInfoListRequestModel aboutInfoListRequestModel) {
        return aboutInfoApiClient.getAboutInfoList(aboutInfoListRequestModel);
    }

    public Observable<DetailViewResponseModel> getDetailPage(DetailViewRequest detailViewRequest) {
        return aboutInfoApiClient.getPageByPageId(detailViewRequest);
    }

}
