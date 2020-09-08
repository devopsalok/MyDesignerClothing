package com.mydesignerclothing.mobile.android.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mydesignerclothing.mobile.android.R;

public class SegmentedRadioGroup extends LinearLayout {
    private int childCount;

    public SegmentedRadioGroup(Context context) {
        super(context);
    }

    public SegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupCarouselDots(int childCount) {
        setUpDots(childCount);
    }

    public void clearCheck() {
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setBackgroundResource(R.drawable.promo_switcher_indicator_off);
        }
    }

    private void check(int index) {
        clearCheck();
        getChildAt(index - 1).setBackgroundResource(R.drawable.promo_switcher_indicator_on);
    }

    private void setUpDots(int childCount) {
        this.childCount = childCount;
        int radioButtonIndex = 1;
        removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        do {
            LinearLayout radioButtonLayout = (LinearLayout) inflater.inflate(R.layout.promo_radio_layout, null);
            radioButtonLayout.setId(radioButtonIndex++);
            radioButtonLayout.setClickable(false);
            addView(radioButtonLayout);
        } while (--childCount > 0);
        check(1);
    }

    public void setChecked(int childAtIndex) {
        check(childAtIndex + 1);
    }
}
