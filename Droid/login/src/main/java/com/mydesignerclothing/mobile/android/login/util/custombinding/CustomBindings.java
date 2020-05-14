package com.mydesignerclothing.mobile.android.login.util.custombinding;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mydesignerclothing.mobile.android.login.loginmain.services.ErrorResponse;
import com.mydesignerclothing.mobile.android.login.util.display.ErrorDialog;
import com.mydesignerclothing.mobile.android.login.util.display.LoaderDialog;
import com.mydesignerclothing.mobile.android.uikit.util.DrawableResource;
import com.mydesignerclothing.mobile.android.uikit.view.EditTextControl;
import com.mydesignerclothing.mobile.android.uikit.view.image.ImageFetcherView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import static com.mydesignerclothing.mobile.android.uikit.view.CustomProgress.removeProgressDialog;
import static com.mydesignerclothing.mobile.android.uikit.view.CustomProgress.showProgressDialog;

public class CustomBindings {
  public static final int COLOR_UNDEFINED = 0;

  private CustomBindings() {
  }

  @BindingAdapter({"textColor"})
  public static void setTextColor(TextView view, int color) {
    if (color != COLOR_UNDEFINED) {
      view.setTextColor(view.getResources().getColor(color));
    }
  }

  @BindingAdapter({"textSize"})
  public static void setTextSize(TextView view, int size) {
    if (size != 0) {
      view.setTextSize(size);
    }
  }

  @BindingAdapter({"loader"})
  public static void showLoader(View view, LoaderDialog loaderDialog) {
    if (loaderDialog.isVisible()) {
      showProgressDialog(view.getContext(), loaderDialog.getText(), loaderDialog.isCancellable());
    } else {
      removeProgressDialog();
    }
  }

  @BindingAdapter({"errorDialog"})
  public static void showErrorDialog(View view, ErrorDialog errorDialog) {
    if (errorDialog.hasError()) {
      Activity activity = (Activity) view.getContext();
      ErrorResponse errorResponse = new ErrorResponse(errorDialog.getError(), errorDialog.getErrorTitle());
//      createDialogForErrorWithCustomListener(activity, errorResponse, errorDialog.getListener()).show();
      errorDialog.flush();
    }
  }

  @BindingAdapter({"inputType"})
  public static void setInputType(EditTextControl editTextControl, int inputType) {
    editTextControl.updateInputType(inputType);
  }

  @BindingAdapter(value = {"text", "textAttrChanged"}, requireAll = false)
  public static void setText(EditTextControl view, String text, final InverseBindingListener bindingListener) {
    view.setText(text);
    view.setOnEditTextListener(inputText -> {
      if (bindingListener != null) {
        bindingListener.onChange();
      }
    });
  }

  @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
  public static String getText(EditTextControl view) {
    return view.getText();
  }

  @BindingAdapter(value = {"errorText"}, requireAll = false)
  public static void setErrorText(EditTextControl view, String text) {
    if (text != null) {
      view.setErrorText(text);
    }
  }

  @BindingAdapter({"isHtml"})
  public static void setTextAsHtml(TextView textView, boolean isHtml) {
    if (isHtml) {
      textView.setText(Html.fromHtml(textView.getText().toString()));
    }
  }

  @BindingAdapter({"focusable"})
  public static void setFocus(View view, boolean focus) {
    if (focus) {
      ScrollView scrollView = lookUpScrollView(view);
      scrollView.requestChildFocus(view, view);
    }
  }

  @BindingAdapter("defaultResourceId")
  public static void setDefaultResourceId(ImageFetcherView view, DrawableResource defaultDrawable) {
    view.setDefaultResourceId(defaultDrawable.resId());
  }

  @BindingAdapter("errorResourceId")
  public static void setErrorResourceId(ImageFetcherView view, DrawableResource errorDrawable) {
    view.setErrorResourceId(errorDrawable.resId());
  }

  private static ScrollView lookUpScrollView(View view) {
    View searchedView = view;
    while (!(searchedView instanceof ScrollView)) {
      searchedView = (View) searchedView.getParent();
    }

    return (ScrollView) searchedView;
  }

  @BindingAdapter({"imageBitmap"})
  public static void setImageBitmap(ImageView view, Bitmap bitmap) {
    BitmapDrawable drawable = new BitmapDrawable(view.getResources(), bitmap);
    view.setImageDrawable(drawable);
  }
}
