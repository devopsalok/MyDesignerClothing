package com.mydesignerclothing.mobile.android.network.errors;


import com.mydesignerclothing.mobile.android.commons.BuildConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.RequestBody.create;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class ErrorTracker {
  private static final String ERROR_TRACKING_ENDPOINT = "/popup";

  public void trackError(Request request) {
    String originalUrl = request.url().toString();
    Request errorTrackingRequest =
      request
        .newBuilder()
        .method("POST", create(MediaType.parse("application/json"), ""))
        .url(originalUrl + ERROR_TRACKING_ENDPOINT)
        .build();

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);

    new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build().newCall(errorTrackingRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        // NO-OP
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        // NO-OP
      }
    });
  }
}
