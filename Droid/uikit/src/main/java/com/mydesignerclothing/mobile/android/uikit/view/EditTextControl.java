package com.mydesignerclothing.mobile.android.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mydesignerclothing.mobile.android.commons.util.StringUtils;
import com.mydesignerclothing.mobile.android.uikit.R;
import com.mydesignerclothing.mobile.android.uikit.font.MyDscFont;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.content.res.ResourcesCompat;

public class EditTextControl extends LinearLayout {
    public static final int STATE_DEFAULT = 1;
    public static final int STATE_ERROR = 2;
    private Context ctx;
    private CustomEditText editTextView;
    private TextView errorTextView;
    private TextView titleView;
    private int state = -1;
    private String titleText;
    private String errorText;
    private int imeOptions = -1;
    private int inputType = -1;
    private int maxLength = -1;
    @StyleRes
    private int errorStyle = R.style.Widget_App_TextView_ErrorLabel;
    @DrawableRes
    private int errorUnderline = R.drawable.red_nine_patch;
    private boolean applyMaxLength = true;
    private String hint;
    private boolean password = false;
    private boolean singleLine = false;
    private int rightPadding;
    private OnEditTextListener onEditTextListener;

    public EditTextControl(Context context, String titleText, String errorText, int imeOptions, int inputType) {
        super(context);
        this.ctx = context;
        setIMEOptions(imeOptions);
        setInputType(inputType);
        this.titleText = titleText;
        this.errorText = errorText;
        initialize();
        attributeCheck();
        setState(STATE_DEFAULT, this.errorText);
    }

    public EditTextControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        initialize();
        editTextView = (CustomEditText) LayoutInflater.from(ctx).inflate(R.layout.custom_edit_text, this, false);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.default_font);
        editTextView.setTypeface(typeface);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditTextControl, 0, 0);
            int editState = a.getInt(R.styleable.EditTextControl_editstate, STATE_DEFAULT);
            titleText = a.getString(R.styleable.EditTextControl_titleText);
            errorText = a.getString(R.styleable.EditTextControl_errorText);
            maxLength = a.getInt(R.styleable.EditTextControl_maxLength, 30);
            applyMaxLength = a.getBoolean(R.styleable.EditTextControl_applyMaxLength, true);
            hint = a.getString(R.styleable.EditTextControl_hint);
            password = a.getBoolean(R.styleable.EditTextControl_password, false);
            singleLine = a.getBoolean(R.styleable.EditTextControl_singleLine, false);
            errorStyle = a.getResourceId(R.styleable.EditTextControl_errorStyle, R.style.Widget_App_TextView_ErrorLabel);
            errorUnderline = a.getResourceId(R.styleable.EditTextControl_errorUnderline, R.drawable.red_nine_patch);
            rightPadding = a.getDimensionPixelSize(R.styleable.EditTextControl_rightPadding, 0);
            setIMEOptions(a.getInt(R.styleable.EditTextControl_editIMEOptions, 6));
            setInputType(a.getInt(R.styleable.EditTextControl_editInputType, 1));
            attributeCheck();
            setState(editState, errorText);
            a.recycle();
        }

        editTextView.addTextChangedListener(textChangeListener());
        editTextView.setOnFocusChangeListener(focusChangeListener());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        editTextView.setPaddingRelative(editTextView.getPaddingStart(), editTextView.getPaddingTop(),
                editTextView.getPaddingEnd() + rightPadding, editTextView.getPaddingBottom());
    }

    private OnFocusChangeListener focusChangeListener() {
        return (v, hasFocus) -> {
            if (getState() == STATE_DEFAULT) {
                setDefaultStateBackground(hasFocus);
            }
        };
    }

    private void setDefaultStateBackground(boolean hasFocus) {
        if (!hasFocus && "".equals(getText())) {
            editTextView.setBackgroundResource(R.drawable.grey_nine_patch);
        } else {
            editTextView.setBackgroundResource(R.drawable.blue_nine_patch);
        }
    }

    private TextWatcher textChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getState() == STATE_ERROR) {
                    editTextView.setBackgroundResource(R.drawable.grey_nine_patch);
                } else {
                    editTextView.setBackgroundResource(R.drawable.blue_nine_patch);
                }
            }
        };
    }

    private void initialize() {
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public int getIMEOptions() {
        return this.imeOptions;
    }

    public void setIMEOptions(int imeOptions) {
        this.imeOptions = imeOptions;
    }

    public int getInputType() {
        return this.inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        attributeCheck();
        editTextView.setInputType(inputType);
    }

    public void setState(int state) {
        this.state = state;
        layoutView();
    }

    private void attributeCheck() {
        if (inputType == -1) {
            imeOptions = EditorInfo.TYPE_CLASS_TEXT;
        }
        if (imeOptions == -1) {
            imeOptions = EditorInfo.IME_ACTION_NEXT;
        }
        if (titleText == null) {
            titleText = "";
        }
        if (errorText == null) {
            errorText = "";
        }
    }

    public void setState(int state, String text, String errorText) {
        setState(state, errorText);
        setText(text);
    }

    public void setState(int state, String errorText) {
        this.state = state;
        this.errorText = errorText;
        layoutView();
    }

    public int getState() {
        return this.state;
    }

    private void layoutView() {
        refresh();
        switch (getState()) {
            case STATE_DEFAULT:
                drawDefaultState();
                break;
            case STATE_ERROR:
                drawErrorState();
                break;
            default:
                // Do nothing
        }
    }

    public String getText() {
        if (editTextView != null) {
            return editTextView.getText().toString();
        }
        return null;
    }

    public void setText(String text) {
        if (editTextView != null) {
            editTextView.setText(text);
        }
    }

    public String getHint() {
        if (editTextView != null) {
            return editTextView.getHint().toString();
        }
        return null;
    }

    public void setHint(String text) {
        if (editTextView != null) {
            editTextView.setHint(text);
        }
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
        checkAttributesForCustomEditText();
    }

    private void drawDefaultState() {
        LayoutParams titleParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (titleText != null && !StringUtils.EMPTY_STRING.equalsIgnoreCase(titleText)) {
            initializeTitleView();
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            addView(titleView, titleParams);
        }

        initializeEditTextView();
        setDefaultStateBackground(editTextView.isFocused());

        addView(editTextView, titleParams);

    }

    private void initializeTitleView() {
        titleView = new TextView(ctx);
        titleView.setTextAppearance(ctx, R.style.Widget_App_TextView_Label);

        titleView.setText(titleText != null ? titleText : "");
        MyDscFont.applyDefaultFont(titleView);
    }

    private void initializeEditTextView() {
        editTextView.setImeOptions(getIMEOptions());
        editTextView.setInputType(getInputType());
        editTextView.setUserTextChangedListener(onUserTextChanged);
        checkAttributesForCustomEditText();
    }

    private void checkAttributesForCustomEditText() {
        if (maxLength != -1) {
            updateMaxLength(maxLength);
        }
        if (hint != null) {
            editTextView.setHint(hint);
        }
        if (singleLine) {
            editTextView.setEllipsize(TextUtils.TruncateAt.END);
            editTextView.setSingleLine();
        }
        if (password) {
            TransformationMethod transformationMethod = PasswordTransformationMethod.getInstance();
            editTextView.setTransformationMethod(transformationMethod);
        }
    }

    private void drawErrorState() {
        // add the error text
        errorTextView = new TextView(ctx);
        errorTextView.setTextAppearance(ctx, errorStyle);
        MyDscFont.applyDefaultFont(errorTextView);

        errorTextView.setGravity(Gravity.CENTER_VERTICAL);
        errorTextView.setText(errorText != null ? errorText : "");
        MyDscFont.applyDefaultFont(errorTextView);
        this.addView(errorTextView);

        initializeEditTextView();
        editTextView.setBackgroundResource(errorUnderline);

        addView(editTextView, 0);

    }

    private void refresh() {
        this.removeAllViews();
    }

    private CustomEditText.OnUserTextChanged onUserTextChanged =
            (CharSequence text, int start, int before, int after) -> {
                if (onEditTextListener != null) {
                    onEditTextListener.onEditInput(text);
                }

            };

    public void updateMaxLength(int maxLength) {
        if (!applyMaxLength) {
            return;
        }
        InputFilter[] filterarray = new InputFilter[1];
        filterarray[0] = new InputFilter.LengthFilter(maxLength);
        editTextView.setFilters(filterarray);
    }

    public void updateInputType(int inputType) {
        this.inputType = inputType;
        editTextView.setInputType(inputType);
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
        layoutView();
    }

    public interface OnEditTextListener {
        void onEditInput(CharSequence text);

    }

    public void setOnEditTextListener(OnEditTextListener onEditTextListener) {
        this.onEditTextListener = onEditTextListener;
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener editorActionListener) {
        editTextView.setOnEditorActionListener(editorActionListener);
    }

    public void updateImeOptions(int imeOptions) {
        this.imeOptions = imeOptions;
        this.editTextView.setImeOptions(imeOptions);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        editTextView.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        editTextView.removeTextChangedListener(textWatcher);
    }
}
