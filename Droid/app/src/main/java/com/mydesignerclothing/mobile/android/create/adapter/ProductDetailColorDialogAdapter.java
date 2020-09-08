package com.mydesignerclothing.mobile.android.create.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailColorViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailColorDialogAdapter extends RecyclerView.Adapter<ProductDetailColorDialogAdapter.ProductDetailColorDialogAdapterViewHolder> {

    private final List<ProductColorsList> productColorsList;
    private int row_index;
    private CreateInfoView createInfoViewListener;

    public ProductDetailColorDialogAdapter(List<ProductColorsList> productColorsList, CreateInfoView createInfoViewListener) {
        this.productColorsList = productColorsList;
        this.createInfoViewListener = createInfoViewListener;
    }

    @NonNull
    @Override
    public ProductDetailColorDialogAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_detail_color_item, parent, false);
        return new ProductDetailColorDialogAdapter.ProductDetailColorDialogAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailColorDialogAdapterViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.productDetailColorViewModel, new ProductDetailColorViewModel(productColorsList.get(position)));
        holder.viewDataBinding.executePendingBindings();

        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            createInfoViewListener.invokeProductDetailEventForColorSelected(productColorsList.get(position));
        });

        holder.checkBox.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            createInfoViewListener.invokeProductDetailEventForColorSelected(productColorsList.get(position));
        });

        if (row_index == position) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return productColorsList != null ? productColorsList.size() : 0;
    }

    class ProductDetailColorDialogAdapterViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;
        CheckBox checkBox;

        private ProductDetailColorDialogAdapterViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
            checkBox = itemView.findViewById(R.id.color_check_box_selected);
        }
    }
}
