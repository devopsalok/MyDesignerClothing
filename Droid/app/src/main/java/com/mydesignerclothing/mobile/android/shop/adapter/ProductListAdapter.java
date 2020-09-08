package com.mydesignerclothing.mobile.android.shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.shop.services.model.ProductsListModel;
import com.mydesignerclothing.mobile.android.shop.viewmodel.ProductListAdapterViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private final List<ProductsListModel> productsListModelList;

    public ProductListAdapter(List<ProductsListModel> productsListModelList) {
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
}
