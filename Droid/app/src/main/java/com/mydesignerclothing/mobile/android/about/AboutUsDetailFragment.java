package com.mydesignerclothing.mobile.android.about;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.about.presenter.AboutInfoPresenter;
import com.mydesignerclothing.mobile.android.about.services.AboutInfoService;
import com.mydesignerclothing.mobile.android.about.services.apiclient.AboutInfoApiClient;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoList;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;
import com.mydesignerclothing.mobile.android.about.view.AboutInfoView;
import com.mydesignerclothing.mobile.android.databinding.FragmentAboutUsDetailBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class AboutUsDetailFragment extends Fragment implements AboutInfoView {
    private AboutInfoPresenter aboutInfoPresenter;
    private AboutInfoList aboutInfoList;
    private FragmentAboutUsDetailBinding fragmentAboutUsDetailBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            aboutInfoList = bundle.getParcelable("ABOUT_INFO_PAGE_LIST");
        }

        AboutInfoService aboutInfoService = new AboutInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(AboutInfoApiClient.class));
        aboutInfoPresenter = new AboutInfoPresenter(aboutInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentAboutUsDetailBinding == null) {
            fragmentAboutUsDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us_detail, container, false);
            renderDetailViewForAboutUsPageId();
            requireActivity().setTitle(aboutInfoList.getAboutInfoId());
        }
        return fragmentAboutUsDetailBinding.getRoot();
    }

    @Override
    public void showProgressDialog() {
        CustomProgress.showProgressDialog(requireActivity(), EMPTY_STRING, false);
    }

    @Override
    public void hideProgressDialog() {
        CustomProgress.removeProgressDialog();
    }

    @Override
    public void showErrorDialog(@NonNull NetworkError error) {
        Toast.makeText(requireActivity(), error.getErrorMessage(getResources()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAboutInfoListSuccessResponse(AboutInfoListResponseModel aboutInfoListResponseModel) {

    }

    @Override
    public void onDetailPageSuccessResponse(DetailViewResponseModel detailViewResponseModel) {
        Spanned formattedText = Html.fromHtml(detailViewResponseModel.getResult().getDetailContent());
        fragmentAboutUsDetailBinding.detailInfo.setText(formattedText);
    }

    private void renderDetailViewForAboutUsPageId() {
        aboutInfoPresenter.getDetailPage(aboutInfoList.getAboutInfoId());
    }
}
