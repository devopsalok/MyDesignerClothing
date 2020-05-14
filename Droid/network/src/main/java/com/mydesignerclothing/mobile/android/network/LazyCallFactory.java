package com.mydesignerclothing.mobile.android.network;

import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentsManager;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.exceptions.DuplicateRequestException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.Timeout;

public class LazyCallFactory implements Call.Factory {

  private static final String TAG = LazyCallFactory.class.getSimpleName();

  private OkHttpClient okHttpClient;
  private ServiceTicket serviceTicket;
  private EnvironmentConfig environmentConfig;
  private EnvironmentsManager environmentsManager;

  public LazyCallFactory(OkHttpClient okHttpClient, EnvironmentConfig environmentConfig, ServiceTicket serviceTicket, EnvironmentsManager environmentsManager) {
    if (serviceTicket == null || serviceTicket.getRequestType().isEmpty()) {
      throw new IllegalStateException("ServiceTicket must have a valid request type");
    }

    this.environmentConfig = environmentConfig;
    this.okHttpClient = okHttpClient;
    this.serviceTicket = serviceTicket;
    this.environmentsManager = environmentsManager;
  }

  @Override
  public Call newCall(@NonNull Request request) {
    HttpUrl url = request.url();
    String jvmSplitMappedEndPoint = environmentsManager.jvmSplitMappedEndPoint(url.encodedPath());
    HttpUrl actualUrl = HttpUrl.parse(url.toString().replace("http://" + url.host() + "/", mapUrlToEndpoint().toString()));
    if (jvmSplitMappedEndPoint != null) {
      actualUrl = actualUrl.newBuilder().encodedPath(jvmSplitMappedEndPoint).build();
    }
    Request taggedRequest = request.newBuilder().url(actualUrl).tag(serviceTicket).build();
    return new LazyCall(okHttpClient.newCall(taggedRequest));
  }

  @NonNull
  private HttpUrl mapUrlToEndpoint() {
    HttpUrl realUrl = HttpUrl.parse(environmentConfig.endpoint(serviceTicket.getRequestType()));
    if (!realUrl.pathSegments().get(realUrl.pathSize() - 1).isEmpty()) {
      realUrl = realUrl.newBuilder().addPathSegment("").build();
    }
    return realUrl;
  }

  private boolean isEqual(Request thisRequest, Request thatRequest) throws IOException {
    if (!thisRequest.url().equals(thatRequest.url())) {
      return false;
    }

    if (!thisRequest.method().equals(thatRequest.method())) {
      return false;
    }

    if (!thisRequest.headers().equals(thatRequest.headers())) {
      return false;
    }

    boolean requestBodiesExist = thisRequest.body() != null && thatRequest.body() != null;

    if (requestBodiesExist) {
      if (!thisRequest.body().contentType().equals(thatRequest.body().contentType())) {
        return false;
      }

      Buffer thisBuffer = new Buffer();
      Buffer thatBuffer = new Buffer();

      thisRequest.body().writeTo(thisBuffer);
      thatRequest.body().writeTo(thatBuffer);

      if (!thisBuffer.readUtf8().equals(thatBuffer.readUtf8())) {
        return false;
      }
    }

    return true;
  }

  private class LazyCall implements Call {
    private Call call;

    public LazyCall(Call call) {
      this.call = call;
    }

    @Override
    public Request request() {
      return call.request();
    }

    @Override
    public Response execute() throws IOException {
      checkPendingRequests();
      return call.execute();
    }

    @Override
    public void enqueue(Callback responseCallback) {
      checkPendingRequests();
      call.enqueue(responseCallback);
    }

    @Override
    public void cancel() {
      call.cancel();
    }

    @Override
    public boolean isExecuted() {
      return call.isExecuted();
    }

    @Override
    public boolean isCanceled() {
      return call.isCanceled();
    }

    @Override
    public Timeout timeout() {
      return new Timeout().timeout(180L, TimeUnit.SECONDS);
    }

    @Override
    public Call clone() {
      return new LazyCall(call.clone());
    }

    private boolean isCallQueuedOrRunning(Request request) throws IOException {
      return requestExists(request, okHttpClient.dispatcher().runningCalls()) ||
        requestExists(request, okHttpClient.dispatcher().queuedCalls());
    }

    private void checkPendingRequests() {
      try {
        if (isCallQueuedOrRunning(call.request())) {
          throw new DuplicateRequestException(call.request());
        }
      } catch (IOException e) {
        CrashTracker.trackStackTrace(TAG, e);
      }
    }

    private boolean requestExists(Request request, List<Call> calls) {
      for (Call call : calls) {
        try {
          if (isEqual(request, call.request()) && !call.isCanceled()) {
            return true;
          }
        } catch (IOException e) {
          DMLog.e(TAG, e.getMessage());
          CrashTracker.trackStackTrace(TAG, e);
        }
      }
      return false;
    }
  }
}
