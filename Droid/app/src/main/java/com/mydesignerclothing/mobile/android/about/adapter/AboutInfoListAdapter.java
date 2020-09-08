package com.mydesignerclothing.mobile.android.about.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.about.presenter.DetailPresenter;
import com.mydesignerclothing.mobile.android.about.services.model.AboutInfoList;
import com.mydesignerclothing.mobile.android.about.viewmodel.AboutInfoListAdapterViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class AboutInfoListAdapter extends RecyclerView.Adapter<AboutInfoListAdapter.AboutInfoListViewHolder> {

    private final List<AboutInfoList> aboutInfoList;
    private final DetailPresenter detailPresenter;

    public AboutInfoListAdapter(List<AboutInfoList> aboutInfoList, DetailPresenter detailPresenter) {
        this.aboutInfoList = aboutInfoList;
        this.detailPresenter = detailPresenter;

    }

    @NonNull
    @Override
    public AboutInfoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.about_info_list_row, parent, false);
        return new AboutInfoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutInfoListViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.aboutInfoListViewModel, new AboutInfoListAdapterViewModel(aboutInfoList.get(position),detailPresenter));
        holder.viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return aboutInfoList != null ? aboutInfoList.size() : 0;
    }

    class AboutInfoListViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        private AboutInfoListViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }
    }
}
