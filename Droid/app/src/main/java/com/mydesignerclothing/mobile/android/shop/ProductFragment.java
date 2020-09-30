package com.mydesignerclothing.mobile.android.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentProductBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.recycler.components.SpacesItemDecoration;
import com.mydesignerclothing.mobile.android.shop.adapter.ProductListAdapter;
import com.mydesignerclothing.mobile.android.shop.presenter.ShopInfoPresenter;
import com.mydesignerclothing.mobile.android.shop.services.ShopInfoService;
import com.mydesignerclothing.mobile.android.shop.services.apiclient.ShopInfoApiClient;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.shop.view.ShopInfoView;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class ProductFragment extends Fragment implements ShopInfoView {
    private ShopInfoPresenter shopInfoPresenter;
    private ProductListAdapter productListAdapter;
    private ProductsResponseModel productsResponseModel;
    private static final String P_TYPE = "shop";
    private int categoryId;
    private FragmentProductBinding fragmentProductBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getInt("CATEGORY_ID");
        }
        ShopInfoService shopInfoService = new ShopInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(ShopInfoApiClient.class));
        shopInfoPresenter = new ShopInfoPresenter(shopInfoService, this);
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
    public void onCategoriesSuccessResponse(CategoriesResponseModel categoriesResponseModel) {
        //NO SONAR
    }

    @Override
    public void onProductsSuccessResponse(ProductsResponseModel productsResponseModel) {
        this.productsResponseModel = productsResponseModel;
        productListAdapter = new ProductListAdapter(getActivity(),productsResponseModel.getProductsListModel());
        fragmentProductBinding.shopProductRecyclerView.setAdapter(productListAdapter);
    }

    private void renderProductsForCategoryId() {
        fragmentProductBinding.shopProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentProductBinding.shopProductRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        fragmentProductBinding.shopProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentProductBinding.shopProductRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        shopInfoPresenter.getListOfProducts(categoryId, P_TYPE);
    }
}
