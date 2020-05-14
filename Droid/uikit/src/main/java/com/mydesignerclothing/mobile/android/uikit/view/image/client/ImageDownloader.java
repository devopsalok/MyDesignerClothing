package com.mydesignerclothing.mobile.android.uikit.view.image.client;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

import org.apache.commons.lang3.StringUtils;

import androidx.annotation.DrawableRes;

import static android.graphics.Bitmap.createBitmap;

public class ImageDownloader {

  private ImageLoader imageLoader;

  public ImageDownloader(Application application) {
    this.imageLoader = getImageLoader(application.getApplicationContext());
  }

  public ImageDownloader(Context applicationContext) {
    this.imageLoader = getImageLoader(applicationContext);
  }

  public ImageDownloader(ImageLoader imageLoader) {
    this.imageLoader = imageLoader;
  }

  public void loadImage(String url, ImageView imageView) {
    loadImage(url, imageView, 0, 0);
  }

  public void loadImage(String url, ImageView imageView, int defaultImageResId) {
    loadImage(url, imageView, defaultImageResId, defaultImageResId);
  }

  public void loadImage(String url,
                        ImageView imageView,
                        int defaultImageResId,
                        int errorImageResId) {
    if (StringUtils.isEmpty(url)) {
      if (errorImageResId != 0)
        imageView.setImageResource(errorImageResId);
    } else {
      ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
      imageLoader.get(url, new UrlLoggingImageListener(url, imageListener));
    }
  }

  private ImageLoader getImageLoader(Context applicationContext) {
    return VolleyImageDownloader.getInstance(applicationContext).getImageLoader();
  }

  public void loadImage(String url, ScalableBitmapListener listener) {
    imageLoader.get(url, listener);
  }

  public void download(String url) {
    imageLoader.get(url, new ImageListener() {
      @Override
      public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
        DMLog.d(ImageDownloader.class.getSimpleName(), String.format("success, downloaded image from url: %s", url));
      }

      @Override
      public void onErrorResponse(VolleyError error) {
        DMLog.d(ImageDownloader.class.getSimpleName(), String.format("Error downloading image from url: %s", url));
        DMLog.d(ImageDownloader.class.getSimpleName(), error.getMessage());
      }
    });
  }

  public abstract static class ScalableBitmapListener implements ImageListener {

    private final int width;
    private final int height;
    private final ImageView imageView;
    private int errorImageResId;

    public ScalableBitmapListener(int width, int height, ImageView imageView, @DrawableRes int errorImageResId) {
      this.width = width;
      this.height = height;
      this.imageView = imageView;
      this.errorImageResId = errorImageResId;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
      onSuccessResponse();
      Bitmap bitmap = response.getBitmap();
      if (bitmap == null) {
        return;
      }
      Matrix matrix = new Matrix();
      RectF srcRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
      RectF dstRect = new RectF(0, 0, width, height);
      matrix.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER);
      imageView.setImageBitmap(createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
      if (errorImageResId != 0) {
        imageView.setImageResource(errorImageResId);
      }
    }

    public abstract void onSuccessResponse();
  }

  private class UrlLoggingImageListener implements ImageListener {

    private String url;
    private ImageListener imageListener;

    public UrlLoggingImageListener(String url, ImageListener imageListener) {
      this.url = url;
      this.imageListener = imageListener;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
      DMLog.d(ImageDownloader.class.getSimpleName(), String.format("success, displaying image from url: %s", url));
      this.imageListener.onResponse(response, isImmediate);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
      DMLog.d(ImageDownloader.class.getSimpleName(), String.format("error, displaying image from url: %s", url));
      imageListener.onErrorResponse(error);

    }
  }
}
