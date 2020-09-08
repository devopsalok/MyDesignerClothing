package com.mydesignerclothing.mobile.android.about.services.apiclient;

import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListRequestModel;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewRequest;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AboutInfoApiClient {

    @POST("api/page/pageList")
    Observable<AboutInfoListResponseModel> getAboutInfoList(@Body AboutInfoListRequestModel aboutInfoListRequestModel);

    @POST("api/page/getPage")
    Observable<DetailViewResponseModel> getPageByPageId(@Body DetailViewRequest detailViewRequest);
}
