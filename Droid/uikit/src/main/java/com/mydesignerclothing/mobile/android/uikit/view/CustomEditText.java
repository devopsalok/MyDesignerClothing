package com.mydesignerclothing.mobile.android.uikit.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.mydesignerclothing.mobile.android.uikit.R;


public class CustomEditText extends StyledEditText {

    private Drawable originalCompoundDrawable = null;
    private boolean hasText = false;

    private OnUserTextChanged userTextChangedListener;

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (hasText && originalCompoundDrawable == null) {
            originalCompoundDrawable = getCompoundDrawables() != null && getCompoundDrawables().length >= 3 ? getCompoundDrawables()[2] : null;
        }
        if (text.length() == 0) {
            hasText = false;
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else if (!hasText) {
            hasText = true;
            Drawable d = getResources().getDrawable(R.drawable.close);
            if (d != null) {
                d.setAlpha(100);
            }
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        if (this.userTextChangedListener != null) {
            userTextChangedListener.onUserTextChanged(text, start, lengthBefore, lengthAfter);
        }
    }

    public interface OnUserTextChanged {
        void onUserTextChanged(CharSequence text, int start, int before, int after);
    }

    public void setUserTextChangedListener(OnUserTextChanged userTextChangedListener) {
        this.userTextChangedListener = userTextChangedListener;
    }
}
