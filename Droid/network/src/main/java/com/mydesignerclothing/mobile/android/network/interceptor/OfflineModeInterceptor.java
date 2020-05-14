package com.mydesignerclothing.mobile.android.network.interceptor;

import com.mydesignerclothing.mobile.android.commons.network.NetworkState;
import com.mydesignerclothing.mobile.android.network.exceptions.OfflineException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OfflineModeInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    final Request request = chain.request();

    if (NetworkState.getInstance().isOffline()) {
      throw new OfflineException();
    }

    return chain.proceed(request);
  }
}
