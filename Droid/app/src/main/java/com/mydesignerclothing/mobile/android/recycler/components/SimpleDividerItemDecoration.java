package com.mydesignerclothing.mobile.android.recycler.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.mydesignerclothing.mobile.android.R;

import androidx.recyclerview.widget.RecyclerView;

import static com.mydesignerclothing.mobile.android.util.MyDSCAndroidUIUtils.convertDpToPixels;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
  private final Rect paddingDimensions;
  private final Context context;
  private final Drawable mDivider;

  public SimpleDividerItemDecoration(Context context) {
    this.context = context;
    mDivider = context.getResources().getDrawable(R.drawable.list_divider);
    this.paddingDimensions = null;
  }

  public SimpleDividerItemDecoration(Context context, Rect paddingDimensions) {
    this.context = context;
    this.paddingDimensions = paddingDimensions;
    mDivider = context.getResources().getDrawable(R.drawable.list_divider);
  }

  public SimpleDividerItemDecoration(Context context, Rect paddingDimensions, Drawable drawable) {
    this.context = context;
    this.paddingDimensions = paddingDimensions;
    mDivider = drawable;
  }

  @Override
  public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    int left = getLeftBound(parent);
    int right = parent.getWidth() - getRightPadding(parent);

    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);

      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

      int top = child.getBottom() + params.bottomMargin + getTopBound();
      int bottom = top + getBottomPadding();

      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
    }
  }

  private int getTopBound() {
    int height = 0;
    if (paddingDimensions != null) {
      height = convertDpToPixels(context, paddingDimensions.top);
    }

    return height;
  }

  private int getBottomPadding() {
    return paddingDimensions == null ? mDivider.getIntrinsicHeight() :
      convertDpToPixels(context, paddingDimensions.bottom);
  }

  private int getRightPadding(RecyclerView parent) {
    int padding;
    if (paddingDimensions == null) {
      padding = parent.getPaddingRight();
    } else {
      padding = convertDpToPixels(context, paddingDimensions.right);
    }
    return padding;
  }

  private int getLeftBound(RecyclerView parent) {
    return paddingDimensions == null ? parent.getPaddingLeft() :
      convertDpToPixels(context, paddingDimensions.left);
  }
}
