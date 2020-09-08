package com.mydesignerclothing.mobile.android.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.adapter.ProductListAdapter;
import com.mydesignerclothing.mobile.android.create.presenter.CreateInfoPresenter;
import com.mydesignerclothing.mobile.android.create.services.CreateInfoService;
import com.mydesignerclothing.mobile.android.create.services.apiclient.CreateInfoApiClient;
import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.databinding.FragmentProductBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.recycler.components.SpacesItemDecoration;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class CreateProductsFragment extends Fragment implements CreateInfoView, CreateInfoListener {
    private CreateInfoPresenter createInfoPresenter;
    private ProductListAdapter productListAdapter;
    private ProductsResponseModel productsResponseModel;
    private static final String P_TYPE = "create";
    private int categoryId;
    private FragmentProductBinding fragmentProductBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getInt("CATEGORY_ID");
        }
        CreateInfoService createInfoService = new CreateInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(CreateInfoApiClient.class));
        createInfoPresenter = new CreateInfoPresenter(createInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentProductBinding == null) {
            fragmentProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
            renderProductsForCategoryId();
        }
        return fragmentProductBinding.getRoot();
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
        this.productsResponseModel = productsResponseModel;
        productListAdapter = new ProductListAdapter(productsResponseModel.getProductsListModel(), this);
        fragmentProductBinding.shopProductRecyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void onProductDetailSuccessResponse(ProductDetailModel productDetailModel) {

    }

    @Override
    public void onColorDialogConfirmButtonClicked() {

    }

    @Override
    public void onColorCancelButtonClicked() {

    }

    @Override
    public void onSizeDialogConfirmButtonClicked() {

    }

    @Override
    public void onSizeCancelButtonClicked() {

    }

    @Override
    public void onSelectColorButtonClicked() {

    }

    @Override
    public void onSelectSizeButtonClicked() {

    }

    @Override
    public void invokeProductDetailEventForColorSelected(ProductColorsList productColorsList) {

    }

    @Override
    public void invokeProductDetailEventForSizeSelected(ProductColorsList productColorsList) {

    }

    @Override
    public void onStartDesignButtonClicked() {

    }

    private void renderProductsForCategoryId() {
        fragmentProductBinding.shopProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentProductBinding.shopProductRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        fragmentProductBinding.shopProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentProductBinding.shopProductRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        createInfoPresenter.getListOfProducts(categoryId, P_TYPE);
    }

    @Override
    public void onProductImageClick(int index) {

    }

    @Override
    public void invokeProductDetailEvent(String productId) {
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", productId);
        findNavController(requireView()).navigate(R.id.action_product_detail_item, bundle);
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
