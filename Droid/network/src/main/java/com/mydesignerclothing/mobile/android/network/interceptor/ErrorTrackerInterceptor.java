package com.mydesignerclothing.mobile.android.network.interceptor;


import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTracker;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTransformer;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTransformerFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.getRequestType;

public class ErrorTrackerInterceptor implements Interceptor {

  private ErrorTracker errorTracker;

  public ErrorTrackerInterceptor(ErrorTracker errorTracker) {
    this.errorTracker = errorTracker;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response response = chain.proceed(chain.request());
    Object tag = chain.request().tag();
    if (tag instanceof ServiceTicket) {
      ServiceTicket serviceTicket = (ServiceTicket) tag;
      ErrorTransformer errorTransformer = ErrorTransformerFactory.create(getRequestType(serviceTicket.getRequestType()));

      if (errorTransformer.hasError(response)) {
        errorTracker.trackError(chain.request());
      }
    }
    return response;
  }
}
