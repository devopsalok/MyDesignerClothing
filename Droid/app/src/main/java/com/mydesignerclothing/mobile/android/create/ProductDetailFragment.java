package com.mydesignerclothing.mobile.android.create;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.presenter.CreateInfoPresenter;
import com.mydesignerclothing.mobile.android.create.services.CreateInfoService;
import com.mydesignerclothing.mobile.android.create.services.apiclient.CreateInfoApiClient;
import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailImages;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductOptionsGroupList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductOptionsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.databinding.FragmentProductDetailBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.EXTRAS_COLORS_MODEL;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.EXTRAS_SIZE_MODEL;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.COLORS_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.SIZE_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.WHICH_DIALOG;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class ProductDetailFragment extends Fragment implements CreateInfoView, CreateInfoListener {
    private CreateInfoPresenter createInfoPresenter;
    private String productId;
    private FragmentProductDetailBinding fragmentProductDetailBinding;
    private CreateDialogFragment createDialogFragment;
    private ProductDetailModel productDetailModel;
    private List<ProductDetailImages> productDetailImagesList = new ArrayList<>();
    private ProductColorsList productColorsList;
    private int selectedItemIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            productId = bundle.getString("PRODUCT_ID");
        }
        CreateInfoService createInfoService = new CreateInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(CreateInfoApiClient.class));
        createInfoPresenter = new CreateInfoPresenter(createInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentProductDetailBinding == null) {
            fragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
            fragmentProductDetailBinding.setCreateFragmentListener(this);
            renderProductDetailByProductId();
        }
        return fragmentProductDetailBinding.getRoot();
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
    public void onProductsSuccessResponse(ProductsResponseModel productsResponseModel) {
        //NOSonar
    }

    @Override
    public void onProductDetailSuccessResponse(ProductDetailModel productDetailModel) {
        this.productDetailModel = productDetailModel;
        fragmentProductDetailBinding.descriptionText.setText(Html.fromHtml(productDetailModel.getProductDetail().getProductLongDesc()));
        fragmentProductDetailBinding.productDetailPromoViewFlipper.detailViewFlipperContainer.setupUI(productDetailModel.getProductDetailImagesList(), this);
    }

    @Override
    public void onColorDialogConfirmButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
        fragmentProductDetailBinding.colorBtn.setText(String.format("Color - %s", productColorsList.getOptionName()));
        fragmentProductDetailBinding.productDetailPromoViewFlipper.detailViewFlipperContainer.setupUI(productDetailImagesList, this);
    }

    @Override
    public void onColorCancelButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onSizeDialogConfirmButtonClicked() {
   //     Log.e("size",""+productColorsList.toString());
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
       // Log.e("OPTION NAME", "" + productColorsList.getOptionName());
        fragmentProductDetailBinding.sizeBtn.setText(String.format("Size - %s", productColorsList.getOptionName()));
    }

    @Override
    public void onSizeCancelButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onSelectColorButtonClicked() {
        createDialogFragment = new CreateDialogFragment();
        ProductOptionsList productOptionsList = productDetailModel.getProductOptionsList().get(1);
        createDialogFragment.setProductDetailFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, COLORS_DIALOG.ordinal());
        bundle.putParcelable(EXTRAS_COLORS_MODEL, productOptionsList);
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    @Override
    public void onSelectSizeButtonClicked() {

        createDialogFragment = new CreateDialogFragment();
        ProductOptionsList productOptionsList = productDetailModel.getProductOptionsList().get(0);
        createDialogFragment.setProductDetailFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, SIZE_DIALOG.ordinal());
        bundle.putParcelable(EXTRAS_SIZE_MODEL, productOptionsList);
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    @Override
    public void invokeProductDetailEventForColorSelected(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
        productDetailImagesList.clear();
        for (int i = 0; i < productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().size(); i++) {
            if (productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().get(i).getOptionId().equals(productColorsList.getOptionId())) {
                ProductDetailImages productDetailImages = new ProductDetailImages();
                productDetailImages.setImageUrl(productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().get(i).getImage());
                productDetailImagesList.add(productDetailImages);
            }
        }
    }

    @Override
    public void invokeProductDetailEventForSizeSelected(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
        productDetailImagesList.clear();
        for (int i = 0; i < productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().size(); i++) {
            if (productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().get(i).getOptionId().equals(productColorsList.getOptionId())) {
                ProductDetailImages productDetailImages = new ProductDetailImages();
                productDetailImages.setImageUrl(productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().get(i).getImage());
                productDetailImagesList.add(productDetailImages);
            }
        }
    }

    @Override
    public void onStartDesignButtonClicked() {
        List<ProductDetailImages> productDetailImagesList = productDetailModel.getProductDetailImagesList();
        Bundle bundle = new Bundle();
        bundle.putString("IMAGE_URL", productDetailImagesList.get(selectedItemIndex).getImageUrl());
        findNavController(requireView()).navigate(R.id.action_start_design, bundle);
    }

    @Override
    public void addToCart() {

    }

    private void renderProductDetailByProductId() {
        createInfoPresenter.getProductDetail(productId);
    }

    @Override
    public void onProductImageClick(int index) {
        selectedItemIndex = index;
    }

    @Override
    public void invokeProductDetailEvent(String productId) {

    }

    @Override
    public void onCancelButtonClicked() {

    }

    @Override
    public void onTakePictureButtonClicked() {

    }

    @Override
    public void onChooseGalleryButtonClicked() {

    }

    @Override
    public void onOkButtonClicked() {

    }
}
