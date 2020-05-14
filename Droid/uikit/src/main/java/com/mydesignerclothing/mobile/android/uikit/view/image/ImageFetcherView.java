package com.mydesignerclothing.mobile.android.uikit.view.image;

import android.content.Context;
import android.util.AttributeSet;

import com.mydesignerclothing.mobile.android.uikit.view.image.client.ImageDownloader;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

public class ImageFetcherView extends AppCompatImageView {
  private Context context;
  private String url;
  @DrawableRes
  private int defaultResourceId;
  @DrawableRes
  private int errorResourceId;

  public ImageFetcherView(Context context) {
    super(context);
    this.context = context;
  }

  public ImageFetcherView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    initializeAttribute(attrs);
    displayImage(context);
  }

  private void displayImage(Context context) {
    ImageDownloader imageDownloader = new ImageDownloader(context.getApplicationContext());
    if (errorResourceId == 0) {
      imageDownloader.loadImage(url, this, defaultResourceId);
    } else {
      imageDownloader.loadImage(url, this, defaultResourceId, errorResourceId);
    }

  }

  private void initializeAttribute(AttributeSet attributeSet) {
    final String nameSpace = "http://schemas.android.com/apk/res-auto";
    url = attributeSet.getAttributeValue(nameSpace, "url");

    int defaultImageResourceId = 0;
    defaultResourceId = attributeSet.getAttributeResourceValue(
      nameSpace, "defaultResourceId", defaultImageResourceId);

    errorResourceId = attributeSet.getAttributeResourceValue(
      nameSpace, "errorResourceId", defaultImageResourceId);
  }

  public void setDefaultResourceId(@DrawableRes int defaultResourceId) {
    this.defaultResourceId = defaultResourceId;
  }

  public void setErrorResourceId(@DrawableRes int errorResourceId) {
    this.errorResourceId = errorResourceId;
  }

  /**
   * NOTE: If applicable, {@link ImageFetcherView#setDefaultResourceId(int)} and
   * {@link ImageFetcherView#setErrorResourceId(int)} should be called prior to calling
   * this function.
   */
  public void setUrl(String url) {
    this.url = url;
    displayImage(context);
  }
}
