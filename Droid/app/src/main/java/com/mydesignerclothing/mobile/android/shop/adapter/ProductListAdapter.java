package com.mydesignerclothing.mobile.android.shop.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;

import com.mydesignerclothing.mobile.android.shop.services.model.ProductsListModel;
import com.mydesignerclothing.mobile.android.shop.viewmodel.ProductListAdapterViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.navigation.Navigation.findNavController;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
Context context;
    private final List<ProductsListModel> productsListModelList;

    public ProductListAdapter(Context context,List<ProductsListModel> productsListModelList) {
        this.context=context;
        this.productsListModelList = productsListModelList;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list_row, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.productListViewModel, new ProductListAdapterViewModel(productsListModelList.get(position)));
        holder.viewDataBinding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Bundle bundle = new Bundle();

                bundle.putInt("PRODUCT_ID", Integer.parseInt(productsListModelList.get(position).getProductId()));
             //   Toast.makeText(context, ""+productsListModelList.get(position).getProductId(), Toast.LENGTH_SHORT).show();
                findNavController(v).navigate(R.id.create_products_detail_dest, bundle)*/;

                Bundle bundle = new Bundle();
                bundle.putString("PRODUCT_ID", productsListModelList.get(position).getProductId());
                bundle.putString("from","shop");
                findNavController(v).navigate(R.id.action_product_detail_item, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productsListModelList != null ? productsListModelList.size() : 0;
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        private ProductListViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }
    }

    public static void navigateFragment(Fragment fragment, String tag, Bundle bundle, FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (tag != null) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }
}
