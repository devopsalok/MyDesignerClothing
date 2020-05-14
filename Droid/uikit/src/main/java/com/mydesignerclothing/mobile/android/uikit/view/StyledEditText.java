package com.mydesignerclothing.mobile.android.uikit.view;

import android.content.Context;
import android.util.AttributeSet;

import com.mydesignerclothing.mobile.android.uikit.R;

import androidx.appcompat.widget.AppCompatEditText;

public class StyledEditText extends AppCompatEditText {
    public StyledEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        int backGroundResourceId = (text.length() == 0 && !isActivated() ? R.drawable.grey_nine_patch : R.drawable.blue_nine_patch);
        setBackground(getResources().getDrawable(backGroundResourceId));
    }
}
