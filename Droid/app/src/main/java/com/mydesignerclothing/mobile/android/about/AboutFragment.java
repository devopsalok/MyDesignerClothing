package com.mydesignerclothing.mobile.android.about;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.about.adapter.AboutInfoListAdapter;
import com.mydesignerclothing.mobile.android.about.presenter.AboutInfoPresenter;
import com.mydesignerclothing.mobile.android.about.presenter.DetailPresenter;
import com.mydesignerclothing.mobile.android.about.services.AboutInfoService;
import com.mydesignerclothing.mobile.android.about.services.apiclient.AboutInfoApiClient;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoList;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoListResponseModel;
import com.mydesignerclothing.mobile.android.about.services.model.DetailViewResponseModel;
import com.mydesignerclothing.mobile.android.about.view.AboutInfoView;
import com.mydesignerclothing.mobile.android.databinding.FragmentAboutBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.recycler.components.SimpleDividerItemDecoration;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class AboutFragment extends Fragment implements AboutInfoView, DetailPresenter {
    private AboutInfoPresenter aboutInfoPresenter;
    private AboutInfoListAdapter aboutInfoListAdapter;
    private AboutInfoListResponseModel aboutInfoListResponseModel;
    private FragmentAboutBinding fragmentAboutBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutInfoService aboutInfoService = new AboutInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(AboutInfoApiClient.class));
        aboutInfoPresenter = new AboutInfoPresenter(aboutInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentAboutBinding == null) {
            fragmentAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
            renderAboutInfoList();
        }
        return fragmentAboutBinding.getRoot();
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
        this.aboutInfoListResponseModel = aboutInfoListResponseModel;
        aboutInfoListAdapter = new AboutInfoListAdapter(aboutInfoListResponseModel.getAboutInfoList(), this);
        fragmentAboutBinding.aboutInfoRecyclerView.setAdapter(aboutInfoListAdapter);
    }

    @Override
    public void onDetailPageSuccessResponse(DetailViewResponseModel detailViewResponseModel) {

    }

    private void renderAboutInfoList() {
        fragmentAboutBinding.aboutInfoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentAboutBinding.aboutInfoRecyclerView.setHasFixedSize(true);
        fragmentAboutBinding.aboutInfoRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(fragmentAboutBinding.aboutInfoRecyclerView.getContext(),
                new Rect(0, 1, 0, 1)));
        aboutInfoPresenter.getAboutInfoList();
    }

    @Override
    public void showDetailView(AboutInfoList aboutInfoList) {
        Bundle bundle = new Bundle();
        aboutInfoList.setTitle(aboutInfoList.getAboutInfoTitle());
        bundle.putParcelable("ABOUT_INFO_PAGE_LIST", aboutInfoList);
        bundle.putString("title", aboutInfoList.getTitle());

        findNavController(requireView()).navigate(R.id.action_detail_item, bundle);
    }
}
