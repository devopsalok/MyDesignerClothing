package com.mydesignerclothing.mobile.android.about.viewmodel;

import com.mydesignerclothing.mobile.android.about.presenter.DetailPresenter;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoList;

public class AboutInfoListAdapterViewModel {

    private AboutInfoList aboutInfoList;
    private DetailPresenter detailPresenter;

    public AboutInfoListAdapterViewModel(AboutInfoList aboutInfoList, DetailPresenter detailPresenter) {
        this.aboutInfoList = aboutInfoList;
        this.detailPresenter = detailPresenter;
    }

    public String getAboutInfoTitle() {
        return (aboutInfoList.getAboutInfoTitle());
    }

    public void navigateToDetailView() {
        detailPresenter.showDetailView(aboutInfoList);
    }
}
