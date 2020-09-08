package com.mydesignerclothing.mobile.android.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.recycler.components.ExpandableRecyclerViewAdapter;
import com.mydesignerclothing.mobile.android.recycler.components.GroupViewModel;
import com.mydesignerclothing.mobile.android.recycler.components.SimpleDividerItemDecoration;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.mydesignerclothing.mobile.android.util.MyDesignerClothingValidator.isNullOrEmpty;

public class ExpandableRecyclerView {

//  private final Context context;
//  private final SimpleDividerItemDecoration defaultDivider;
//
//  public ExpandableRecyclerView(Context context) {
//    this.context = context;
//    defaultDivider = new SimpleDividerItemDecoration(context, new Rect(0, 0, 0, 1),
//      new ColorDrawable(context.getResources().getColor(R.color.purchase_confirmation_divider)));
//  }
//
//  public void setUpExpandableRecyclerView(List<? extends GroupViewModel> groupViewModels, RecyclerView recyclerView) {
//    if (!isNullOrEmpty(groupViewModels)) {
//      groupViewModels.get(0).setExpanded(true);
//    }
//
//    ExpandableRecyclerViewAdapter adapter =
//      new ExpandableRecyclerViewAdapter(null, null, groupViewModels, getDivider());
//
//    recyclerView.setLayoutManager(new LinearLayoutManager(context));
//    recyclerView.addItemDecoration(getDivider());
//    recyclerView.setAdapter(adapter);
//  }
//
//  private SimpleDividerItemDecoration getDivider() {
//    return defaultDivider;
//  }
}
