package com.mydesignerclothing.mobile.android.create.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailColorViewModel;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailSizeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailSizeDialogAdapter extends RecyclerView.Adapter<ProductDetailSizeDialogAdapter.ProductDetailSizeDialogAdapterViewHolder> {

    private final List<ProductColorsList> productColorsList;
    private int row_index;
    private CreateInfoView createInfoViewListener;

    public ProductDetailSizeDialogAdapter(List<ProductColorsList> productColorsList, CreateInfoView createInfoViewListener) {
        this.productColorsList = productColorsList;
        this.createInfoViewListener = createInfoViewListener;
    }

    @NonNull
    @Override
    public ProductDetailSizeDialogAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_detail_size_item, parent, false);
        return new ProductDetailSizeDialogAdapter.ProductDetailSizeDialogAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailSizeDialogAdapterViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.productDetailSizeViewModel, new ProductDetailSizeViewModel(productColorsList.get(position)));
        holder.viewDataBinding.executePendingBindings();

        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            createInfoViewListener.invokeProductDetailEventForSizeSelected(productColorsList.get(position));
        });

        holder.checkBox.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            createInfoViewListener.invokeProductDetailEventForSizeSelected(productColorsList.get(position));
        });

        if (row_index == position) {
            holder.checkBox.setChecked(true);
            createInfoViewListener.invokeProductDetailEventForSizeSelected(productColorsList.get(position));
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return productColorsList != null ? productColorsList.size() : 0;
    }

    class ProductDetailSizeDialogAdapterViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;
        CheckBox checkBox;

        private ProductDetailSizeDialogAdapterViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
            checkBox = itemView.findViewById(R.id.size_check_box_selected);
        }
    }
}
