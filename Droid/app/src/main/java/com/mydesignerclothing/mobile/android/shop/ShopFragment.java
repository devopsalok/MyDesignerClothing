package com.mydesignerclothing.mobile.android.shop;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentShopBinding;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.recycler.components.SimpleDividerItemDecoration;
import com.mydesignerclothing.mobile.android.shop.presenter.ShopInfoPresenter;
import com.mydesignerclothing.mobile.android.shop.services.ShopInfoService;
import com.mydesignerclothing.mobile.android.shop.services.apiclient.ShopInfoApiClient;
import com.mydesignerclothing.mobile.android.shop.services.model.AllCategories;
import com.mydesignerclothing.mobile.android.shop.services.model.CategoriesResponseModel;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.shop.view.ShopInfoView;
import com.mydesignerclothing.mobile.android.shop.viewmodel.ShopInfoViewModel;
import com.mydesignerclothing.mobile.android.uikit.components.ClickCallback;
import com.mydesignerclothing.mobile.android.uikit.components.RecyclerViewAdapter;
import com.mydesignerclothing.mobile.android.uikit.components.ViewModel;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class ShopFragment extends Fragment implements ShopInfoView, ClickCallback {
    private ShopInfoPresenter shopInfoPresenter;
    private RecyclerViewAdapter shopAdapter;
    private CategoriesResponseModel categoriesResponseModel;
    private List<AllCategories> allCategoriesList;
    private FragmentShopBinding fragmentShopBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShopInfoService shopInfoService = new ShopInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(ShopInfoApiClient.class));
        shopInfoPresenter = new ShopInfoPresenter(shopInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentShopBinding == null) {
            fragmentShopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);
            renderCategories();
        }
        return fragmentShopBinding.getRoot();
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
        this.categoriesResponseModel = categoriesResponseModel;
        shopAdapter = new RecyclerViewAdapter(this, getShopInfoViewModel());
        fragmentShopBinding.shopCategoryRecyclerView.setAdapter(shopAdapter);
    }

    @Override
    public void onProductsSuccessResponse(ProductsResponseModel productsResponseModel) {
        //NO SONAR
    }

    private void renderCategories() {
        fragmentShopBinding.shopCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentShopBinding.shopCategoryRecyclerView.setHasFixedSize(true);
        fragmentShopBinding.shopCategoryRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(fragmentShopBinding.shopCategoryRecyclerView.getContext(),
                new Rect(0, 1, 0, 1)));
        shopInfoPresenter.getListOfCategories();
    }

    private List<ShopInfoViewModel> getShopInfoViewModel() {
        allCategoriesList = categoriesResponseModel.getAllCategoriesList();
        List<ShopInfoViewModel> shopInfoViewModelList = new ArrayList<>();
        for (AllCategories allCategories : allCategoriesList) {
            shopInfoViewModelList.add(new ShopInfoViewModel(allCategories, getResources()));
        }
        return shopInfoViewModelList;
    }

    @Override
    public void performClickAction(ViewModel viewModel, View view) {
        ShopInfoViewModel shopInfoViewModel = (ShopInfoViewModel) viewModel;
        for (int index = 0; index < allCategoriesList.size(); index++) {
            AllCategories allCategories = allCategoriesList.get(index);
            if (allCategories.getCategoryName().equals(shopInfoViewModel.getCategoryName())) {
                Bundle bundle = new Bundle();
                bundle.putInt("CATEGORY_ID", Integer.parseInt(allCategories.getCategoryId()));
//                ProductFragment productFragment = new ProductFragment();
//                productFragment.setArguments(bundle);
                findNavController(view).navigate(R.id.action_shop_item, bundle);
                break;
            }
        }
    }

}
