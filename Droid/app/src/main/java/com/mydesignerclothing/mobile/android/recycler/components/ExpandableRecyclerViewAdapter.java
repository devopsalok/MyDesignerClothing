package com.mydesignerclothing.mobile.android.recycler.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.uikit.components.ClickCallback;
import com.mydesignerclothing.mobile.android.uikit.components.RecyclerViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpandableRecyclerViewAdapter extends RecyclerViewAdapter {
//  private final ClickCallback groupClickCallback;
//  private final ClickCallback childClickCallback;
//  private final List<? extends GroupViewModel> groupViewModelList;
//  private final SimpleDividerItemDecoration childItemDivider;
//
//  public ExpandableRecyclerViewAdapter(final ClickCallback groupClickCallback,
//                                       ClickCallback childClickCallback,
//                                       List<? extends GroupViewModel> groupViewModelList, SimpleDividerItemDecoration childItemDivider) {
//    super(getClickCallback(groupClickCallback), groupViewModelList);
//    this.groupClickCallback = groupClickCallback;
//    this.childClickCallback = childClickCallback;
//    this.groupViewModelList = groupViewModelList;
//    this.childItemDivider = childItemDivider;
//  }
//
//  @NonNull
//  @Override
//  public RecyclerGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
//    GroupViewModel groupViewModel = groupViewModelList.get(position);
//
//    ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), groupViewModel.layoutResId(), parent, false);
//    RecyclerGroupViewHolder recyclerGroupViewHolder = new RecyclerGroupViewHolder(binding, groupClickCallback);
//    View itemView = recyclerGroupViewHolder.itemView;
//    if (!groupViewModel.isExpanded()) {
//      collapseExpandableItem(itemView);
//    }
//    return recyclerGroupViewHolder;
//  }
//
//  private void collapseExpandableItem(View itemView) {
//    if (itemView instanceof ExpandableLinearLayout) {
//      ((ExpandableLinearLayout) itemView).changeState(false);
//      View viewWithTag = itemView.findViewWithTag(itemView.getContext().getString(R.string.body));
//      viewWithTag.setVisibility(View.GONE);
//    }
//  }
//
//  public class RecyclerGroupViewHolder extends RecyclerViewAdapter.RecyclerViewHolder {
//
//    private final ViewDataBinding binding;
//    private final ClickCallback groupClickCallback;
//
//    RecyclerGroupViewHolder(ViewDataBinding binding, ClickCallback groupClickCallback) {
//      super(binding);
//      this.binding = binding;
//      this.groupClickCallback = groupClickCallback;
//    }
//
//    @Override
//    public void bind(final ViewModel viewModel) {
//      GroupViewModel groupViewModel = (GroupViewModel) viewModel;
//
//      binding.setVariable(groupViewModel.dataBindingVariable(), groupViewModel);
//      binding.executePendingBindings();
//
//      RecyclerView recyclerView = itemView.findViewById(groupViewModel.getChildrenResourceId());
//      recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//      recyclerView.setAdapter(new RecyclerViewAdapter(getClickCallback(childClickCallback),
//        groupViewModel.getChildrenViewModels()));
//      if (childItemDivider != null) {
//        recyclerView.addItemDecoration(childItemDivider);
//      }
//      setItemClickListener(groupViewModel);
//    }
//
//    private void setItemClickListener(final GroupViewModel groupViewModel) {
//      itemView.setOnClickListener(v -> {
//        if (v instanceof ExpandableLinearLayout) {
//          ((ExpandableLinearLayout) v).toggleExpandedState();
//        }
//        if (groupClickCallback != null) {
//          groupClickCallback.performClickAction(groupViewModel);
//        }
//      });
//    }
//  }
//
//  private static ClickCallback getClickCallback(final ClickCallback groupClickCallback) {
//    return viewModel -> {
//      if (groupClickCallback != null) {
//        groupClickCallback.performClickAction(viewModel);
//      }
//    };
//  }
}
