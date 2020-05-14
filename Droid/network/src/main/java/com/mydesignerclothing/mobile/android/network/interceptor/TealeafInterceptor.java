package com.mydesignerclothing.mobile.android.network.interceptor;


import android.net.ConnectivityManager;


import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.CustomHeaders;
import com.mydesignerclothing.mobile.android.network.cookie.CookieStore;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TealeafInterceptor implements Interceptor {

  public static final String TAG = TealeafInterceptor.class.getSimpleName();
  private CustomHeaders headers;
  private ConnectivityManager connectivityManager;

  public TealeafInterceptor(CustomHeaders headers, ConnectivityManager connectivityManager) {
    this.headers = headers;
    this.connectivityManager = connectivityManager;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = getRequestWithAdditionalHeaders(chain.request());
    CookieStore cookieStore = CookieStore.getInstance();

    // The issue arises because Crashlytics handles logging in an asynchronous manner
    // and when the window between the commit on a log message and the crash is too slim, the logs get flushed.
    String path = request.url().encodedPath();
    String tealeafHitCookieValue = cookieStore.getCookieValue(CookieStore.TLTHID_COOKIE);
    String tealeafSessionCookieValue = cookieStore.getCookieValue(CookieStore.TLTSID_COOKIE);
    CrashTracker.trackTeaLeafData(TAG, tealeafHitCookieValue, tealeafSessionCookieValue);
    CrashTracker.trackApplicationCalledUrlPath(path, TAG);
    return chain.proceed(request);
  }

  private Request getRequestWithAdditionalHeaders(Request request) {
    Request.Builder requestBuilder = request.newBuilder();
    for (Map.Entry<String, String> header : headers.get(connectivityManager).entrySet()) {
      requestBuilder.addHeader(header.getKey(), header.getValue());
    }
    return requestBuilder.build();
  }
}
