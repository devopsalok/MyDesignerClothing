package com.mydesignerclothing.mobile.android.network.apiclient;


import com.mydesignerclothing.mobile.android.network.utils.HeaderKeys;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class V2APIClientHeaderInterceptor implements Interceptor {

  private static final String MEDIA_TYPE = "application/json";

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request.Builder requestBuilder = chain.request().newBuilder();

    requestBuilder.addHeader(HeaderKeys.ACCEPT, MEDIA_TYPE)
      .addHeader(HeaderKeys.RESPONSE_JSON, "true");

    return chain.proceed(requestBuilder.build());
  }
}
