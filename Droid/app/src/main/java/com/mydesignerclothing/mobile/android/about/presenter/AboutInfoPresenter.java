package com.mydesignerclothing.mobile.android.about.presenter;

import com.mydesignerclothing.mobile.android.about.services.AboutInfoService;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListRequestModel;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewRequest;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;
import com.mydesignerclothing.mobile.android.about.view.AboutInfoView;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import androidx.annotation.NonNull;
import io.reactivex.Observer;

import static com.mydesignerclothing.mobile.android.network.errors.NetworkErrorConverter.getNetworkError;

public class AboutInfoPresenter {

    private AboutInfoService aboutInfoService;
    private AboutInfoView aboutInfoView;

    public AboutInfoPresenter(AboutInfoService aboutInfoService, AboutInfoView aboutInfoView) {
        this.aboutInfoService = aboutInfoService;
        this.aboutInfoView = aboutInfoView;
    }

    public void getAboutInfoList() {
        aboutInfoView.showProgressDialog();
        aboutInfoService.getAboutInfoList(new AboutInfoListRequestModel()).subscribe(getAboutInfoResponseModelObserver());
    }

    public void getDetailPage(String pageId) {
        aboutInfoView.showProgressDialog();
        DetailViewRequest detailViewRequest = new DetailViewRequest();
        detailViewRequest.setPageId(pageId);
        aboutInfoService.getDetailPage(detailViewRequest).subscribe(getAboutInfoDetailResponseModelObserver());
    }

    @NonNull
    private Observer<AboutInfoListResponseModel> getAboutInfoResponseModelObserver() {
        return new SimpleObserver<AboutInfoListResponseModel>() {

            @Override
            public void onNext(AboutInfoListResponseModel aboutInfoListResponseModel) {
                aboutInfoView.hideProgressDialog();
                aboutInfoView.onAboutInfoListSuccessResponse(aboutInfoListResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                aboutInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    aboutInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

    @NonNull
    private Observer<DetailViewResponseModel> getAboutInfoDetailResponseModelObserver() {
        return new SimpleObserver<DetailViewResponseModel>() {

            @Override
            public void onNext(DetailViewResponseModel detailViewResponseModel) {
                aboutInfoView.hideProgressDialog();
                aboutInfoView.onDetailPageSuccessResponse(detailViewResponseModel);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                aboutInfoView.hideProgressDialog();
                Optional<NetworkError> networkError = getNetworkError(throwable);
                if (networkError.isPresent()) {
                    aboutInfoView.showErrorDialog(networkError.get());
                }
            }
        };
    }

}
