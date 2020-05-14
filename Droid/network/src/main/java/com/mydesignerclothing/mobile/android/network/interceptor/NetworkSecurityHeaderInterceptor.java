package com.mydesignerclothing.mobile.android.network.interceptor;

import com.mydesignerclothing.mobile.android.commons.tracking.networksecurity.NetworkSecurityConstants;
import com.mydesignerclothing.mobile.android.commons.tracking.networksecurity.NetworkSecurityTracker;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkSecurityHeaderInterceptor implements Interceptor {

  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {

    Request.Builder requestBuilder = chain.request().newBuilder();

    if (NetworkSecurityConstants.isNetworkSecurityEndPoint(chain.request().url().toString())) {
      if (NetworkSecurityTracker.shouldAddDummyHeader()) {
        requestBuilder.addHeader(NetworkSecurityTracker.getDummyHeaderKey(), NetworkSecurityTracker.getDummyHeaderValue());
      }
    }
    return chain.proceed(requestBuilder.build());
  }
}
