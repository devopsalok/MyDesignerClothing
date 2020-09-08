package com.mydesignerclothing.mobile.android.recycler.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderDecoration extends RecyclerView.ItemDecoration {

  private final View headerLayout;

  public HeaderDecoration(Context context, RecyclerView parent, @LayoutRes int headerLayoutId) {
    headerLayout = LayoutInflater.from(context)
      .inflate(headerLayoutId, parent, false);
    headerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
      View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    if (parent.getChildAdapterPosition(view) == 0) {
      outRect.set(0, headerLayout.getMeasuredHeight(), 0, 0);
    } else {
      outRect.setEmpty();
    }
  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    headerLayout.layout(parent.getLeft(), 0, parent.getRight(), headerLayout.getMeasuredHeight());
    for (int i = 0; i < parent.getChildCount(); i++) {
      View view = parent.getChildAt(i);
      if (parent.getChildAdapterPosition(view) == 0) {
        c.save();
        final int height = headerLayout.getMeasuredHeight();
        final int top = view.getTop() - height;
        c.translate(0, top);
        headerLayout.draw(c);
        c.restore();
        break;
      }
    }
  }
}
