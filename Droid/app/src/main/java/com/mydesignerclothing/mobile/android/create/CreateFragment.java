package com.mydesignerclothing.mobile.android.create;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentCreateBinding;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.recycler.components.SimpleDividerItemDecoration;
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

public class CreateFragment extends Fragment implements ShopInfoView, ClickCallback {

    private RecyclerViewAdapter shopAdapter;
    private CategoriesResponseModel categoriesResponseModel;
    private List<AllCategories> allCategoriesList = new ArrayList<>();
    private FragmentCreateBinding fragmentCreateBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentCreateBinding == null) {
            fragmentCreateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false);
            renderCategories();
        }
        return fragmentCreateBinding.getRoot();
    }

    private void renderCategories() {
        fragmentCreateBinding.createRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentCreateBinding.createRecyclerView.setHasFixedSize(true);
        fragmentCreateBinding.createRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(fragmentCreateBinding.createRecyclerView.getContext(),
                new Rect(0, 1, 0, 1)));
        getCategoriesList();
    }

    private void getCategoriesList() {
        AllCategories allCategories;
        allCategories = new AllCategories();
        allCategories.setCategoryId("63");
        allCategories.setCategoryName("Male");

        allCategoriesList.add(allCategories);

        allCategories = new AllCategories();
        allCategories.setCategoryId("64");
        allCategories.setCategoryName("Female");

        allCategoriesList.add(allCategories);

        allCategories = new AllCategories();
        allCategories.setCategoryId("84");
        allCategories.setCategoryName("Boy/Girl");

        allCategoriesList.add(allCategories);

        allCategories = new AllCategories();
        allCategories.setCategoryId("91");
        allCategories.setCategoryName("Baby");
        allCategoriesList.add(allCategories);

        onCategoriesSuccessResponse(new CategoriesResponseModel());
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
        shopAdapter = new RecyclerViewAdapter(this, getShopInfoViewModel());
        fragmentCreateBinding.createRecyclerView.setAdapter(shopAdapter);
    }

    private List<ShopInfoViewModel> getShopInfoViewModel() {
        List<ShopInfoViewModel> shopInfoViewModelList = new ArrayList<>();
        for (AllCategories allCategories : allCategoriesList) {
            shopInfoViewModelList.add(new ShopInfoViewModel(allCategories, getResources()));
        }
        return shopInfoViewModelList;
    }


    @Override
    public void onProductsSuccessResponse(ProductsResponseModel productsResponseModel) {

    }

    @Override
    public void performClickAction(ViewModel viewModel, View view) {
        ShopInfoViewModel shopInfoViewModel = (ShopInfoViewModel) viewModel;
        for (int index = 0; index < allCategoriesList.size(); index++) {
            AllCategories allCategories = allCategoriesList.get(index);
            if (allCategories.getCategoryName().equals(shopInfoViewModel.getCategoryName())) {
                Bundle bundle = new Bundle();
                bundle.putInt("CATEGORY_ID", Integer.parseInt(allCategories.getCategoryId()));
                findNavController(view).navigate(R.id.action_shop_item, bundle);
                break;
            }
        }
    }
}
