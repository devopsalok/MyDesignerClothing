package com.mydesignerclothing.mobile.android.network.interceptor;

import com.mydesignerclothing.mobile.android.network.apiclient.RequestType;
import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.errors.V2ErrorTransformer;
import com.mydesignerclothing.mobile.android.network.exceptions.V2ErrorException;
import com.mydesignerclothing.mobile.android.network.utils.OkHttpUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V2;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.getRequestType;

public class V2ErrorInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response response = chain.proceed(chain.request());

    Object tag = chain.request().tag();
    if (tag instanceof ServiceTicket) {
      ServiceTicket serviceTicket = (ServiceTicket) tag;
      RequestType requestType = getRequestType(serviceTicket.getRequestType());
      if (requestType == V2 ) {
        V2ErrorTransformer errorTransformer = new V2ErrorTransformer();
        if (errorTransformer.hasError(response)) {
          throw new V2ErrorException(new OkHttpUtil().stringify(response));
        }
      }
    }
    return response;
  }
}
