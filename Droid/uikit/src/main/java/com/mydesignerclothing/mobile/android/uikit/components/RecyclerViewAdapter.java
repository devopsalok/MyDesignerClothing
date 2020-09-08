package com.mydesignerclothing.mobile.android.uikit.components;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
  protected List<? extends ViewModel> viewModelList;
  protected ClickCallback clickCallback;

  public RecyclerViewAdapter() {
    this(null);
  }

  public RecyclerViewAdapter(ClickCallback clickCallback) {
    this(clickCallback, new ArrayList<>());
  }

  public RecyclerViewAdapter(ClickCallback clickCallback, List<? extends ViewModel> viewModelList) {
    this.clickCallback = clickCallback;
    this.viewModelList = viewModelList;
  }

  public void updateData(List<? extends ViewModel> viewModels) {
    viewModelList = viewModels;
  }

  public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public RecyclerViewHolder(ViewDataBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(final ViewModel viewModel) {
      binding.setVariable(viewModel.dataBindingVariable(), viewModel);
      binding.executePendingBindings();
      if (viewModel instanceof OnBindListener) {
        ((OnBindListener) viewModel).bind(binding.getRoot());
      }
      setItemClickListener(viewModel);
    }

    public void setItemClickListener(final ViewModel viewModel) {
      itemView.setOnClickListener(v -> {
        if (clickCallback != null) {
          clickCallback.performClickAction(viewModel,v);
        }
      });
    }
  }

  @NonNull
  @Override
  public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
    ViewModel viewModel = viewModelList.get(position);
    ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewModel.layoutResId(), parent, false);
    return getViewHolder(binding);
  }

  public RecyclerViewHolder getViewHolder(ViewDataBinding binding) {
    return new RecyclerViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
    ViewModel viewModel = viewModelList.get(position);
    holder.bind(viewModel);
  }

  @Override
  public int getItemCount() {
    return viewModelList.size();
  }

  @Override
  public int getItemViewType(int position) {
    return position;
  }
}
