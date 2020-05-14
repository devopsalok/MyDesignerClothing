package com.mydesignerclothing.mobile.android.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.mydesignerclothing.mobile.android.network.cache.CacheConfig.CACHE_CONFIG;
import static com.mydesignerclothing.mobile.android.network.headers.NetworkCustomerHeaders.REQUIRES_SESSION_CHECK;

public class HeaderSanitizerInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    final Request request = chain.request();
    final Request.Builder requestBuilder = request.newBuilder();

    requestBuilder.removeHeader(REQUIRES_SESSION_CHECK);
    requestBuilder.removeHeader(CACHE_CONFIG);

    return chain.proceed(requestBuilder.build());
  }
}
