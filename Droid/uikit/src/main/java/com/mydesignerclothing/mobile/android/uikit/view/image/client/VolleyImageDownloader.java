package com.mydesignerclothing.mobile.android.uikit.view.image.client;


import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import androidx.collection.LruCache;

class VolleyImageDownloader {

  private static Context applicationContext;
  private RequestQueue requestQueue;
  private ImageLoader imageLoader;

  private VolleyImageDownloader() {
    this.requestQueue = getRequestQueue();

    imageLoader = new ImageLoader(requestQueue,
      new ImageLoader.ImageCache() {
        private final LruCache<String, Bitmap>
          cache = new LruCache<>(25);

        @Override
        public Bitmap getBitmap(String url) {
          return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
          cache.put(url, bitmap);
        }
      });
  }

  private RequestQueue getRequestQueue() {
    if (requestQueue == null) {
      Cache cache = new DiskBasedCache(applicationContext.getCacheDir(), 8 * 1024 * 1024);
      Network network = new BasicNetwork(new HurlStack());
      requestQueue = new RequestQueue(cache, network);
      requestQueue.start();
    }
    return requestQueue;
  }

  ImageLoader getImageLoader() {
    return imageLoader;
  }

  public static VolleyImageDownloader getInstance(Context applicationContext) {
    VolleyImageDownloader.applicationContext = applicationContext;
    return InstanceLoader.volleyImageDownloader;
  }

  private static final class InstanceLoader {
    private static VolleyImageDownloader volleyImageDownloader = new VolleyImageDownloader();
  }
}
