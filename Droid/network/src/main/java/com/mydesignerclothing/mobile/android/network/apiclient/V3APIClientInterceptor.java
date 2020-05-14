package com.mydesignerclothing.mobile.android.network.apiclient;


import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.network.utils.HeaderKeys;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class V3APIClientInterceptor implements Interceptor {

  private static final String MEDIA_TYPE = "application/json";

  private AppInfo appInfo;
  private DeviceInfo deviceInfo;

  V3APIClientInterceptor(AppInfo appInfo, DeviceInfo deviceInfo) {
    this.appInfo = appInfo;
    this.deviceInfo = deviceInfo;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    String appVersion = appInfo.getVersionName();
    Request.Builder requestBuilder = chain.request().newBuilder();

    requestBuilder.addHeader(HeaderKeys.ACCEPT, MEDIA_TYPE)
      .addHeader(HeaderKeys.RESPONSE_JSON, "true")
      .addHeader(HeaderKeys.APPLICATION_VERSION, appVersion)
      .addHeader(HeaderKeys.DEVICE_NAME, "Android")
      .addHeader(HeaderKeys.OS_VERSION, deviceInfo.getAndroidOsVersion())
      .addHeader(HeaderKeys.BUILD_NUMBER, appVersion);

    String url = chain.request().url().url().toString();
    url = URLDecoder.decode(url, "UTF-8");
    requestBuilder.url(url);

    return chain.proceed(requestBuilder.build());
  }
}
