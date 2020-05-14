package com.mydesignerclothing.mobile.android.network.apiclient;

import android.content.Context;

import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.network.interceptor.V2ErrorInterceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;

import static com.mydesignerclothing.mobile.android.network.RetrofitBuilder.ContentType.JSON;
import static com.mydesignerclothing.mobile.android.network.RetrofitBuilder.ContentType.XML;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V2;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V3;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V4;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.WEB_SERVER;

public class APIClientFactory {

  public APIClientFactory() {
    //NOSONAR - do not listen to Sonar, this is used by TodayModeShellAppTestModule.getAPIClientFactory
  }

  public static IAPIClient get(Context context, RequestType requestType) {
    IAPIClient iApiClient;

    switch (requestType) {
      case V4:
        iApiClient = getV4ApiClient(context);
      break;

      case V3:
        iApiClient = getV3ApiClient(context);
      break;

      case V2:
        iApiClient = getV2ApiClient(context);
      break;

      case WEB_SERVER:
        iApiClient = getWebServerApiClient(context);
      break;

      default:
        iApiClient = getApiClient(context);
      break;
    }
    return iApiClient;
  }

  private static IAPIClient getV4ApiClient(@NonNull Context context) {
    return APIClient.create(context,
      V4,
      JSON,
      new V3APIClientInterceptor(AppInfo.get(context), DeviceInfo.get())
    );
  }

  private static IAPIClient getV3ApiClient(@NonNull Context context) {
    return APIClient.create(context,
      V3,
      JSON,
      new V3APIClientInterceptor(AppInfo.get(context), DeviceInfo.get())
    );
  }

  private static IAPIClient getV2ApiClient(@NonNull Context context) {
    List<Interceptor> interceptors = new ArrayList<>();
    interceptors.add(new V2APIClientHeaderInterceptor());
    interceptors.add(new V2ErrorInterceptor());
    return APIClient.create(context,
      V2,
      XML,
      interceptors
    );
  }

  private static IAPIClient getWebServerApiClient(@NonNull Context context) {
    return APIClient.create(context,
      WEB_SERVER,
      JSON,
      Collections.emptyList());
  }

  private static IAPIClient getApiClient(@NonNull Context context) {
    return APIClient.create(context,
            JSON);
  }
}
