package com.mydesignerclothing.mobile.android.network.errors;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.exceptions.OfflineException;
import com.mydesignerclothing.mobile.android.network.exceptions.V2ErrorException;
import com.mydesignerclothing.mobile.android.network.exceptions.WrappedNetworkException;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.network.utils.OkHttpUtil;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V2;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.getRequestType;
import static com.mydesignerclothing.mobile.android.network.models.NetworkError.networkFailureError;
import static com.mydesignerclothing.mobile.android.network.models.NetworkError.offlineError;


public class ErrorConverter {

  private static final String TAG = ErrorConverter.class.getSimpleName();

  public static Optional<NetworkError> getNetworkError(Throwable t) {
    if (t instanceof HttpException) {
      try {
        Response<?> response = ((HttpException) t).response();
        Request request = response.raw().request();
        Object tag = request.tag();
        if (tag instanceof ServiceTicket) {
          ServiceTicket serviceTicket = (ServiceTicket) tag;
          ResponseBody errorBody = response.errorBody();
          return createNetworkError(serviceTicket, errorBody);
        }
      } catch (Exception e) {
        DMLog.e(TAG, e.getMessage());
        CrashTracker.trackStackTrace(TAG, e);
      }
    }

    return handleExceptions(t);
  }

  private static Optional<NetworkError> createNetworkError(ServiceTicket serviceTicket, ResponseBody errorBody) throws IOException, ErrorTransformingException {
    if (errorBody == null) {
      NetworkError networkError = new NetworkError();
      return Optional.of(networkError);
    } else {
      String stringify = new OkHttpUtil().stringify(errorBody);
      return ErrorTransformerFactory.create(getRequestType(serviceTicket.getRequestType())).transformError(stringify);
    }
  }

  private static Optional<NetworkError> handleExceptions(Throwable t) {
    if (t instanceof OfflineException) {
      return Optional.of(offlineError());
    } else if (t instanceof WrappedNetworkException) {
      return Optional.of(((WrappedNetworkException) t).getError());
    } else if (t instanceof V2ErrorException) {
      try {
        return ErrorTransformerFactory.create(V2).transformError(((V2ErrorException) t).getErrorResponse());
      } catch (ErrorTransformingException e) {
        DMLog.e(TAG, e.getMessage());
        CrashTracker.trackStackTrace(TAG, e);
      }
    } else if (t instanceof IOException) {
      return Optional.of(networkFailureError());
    } else {
      CrashTracker.trackStackTrace(TAG, t);
    }
    return Optional.absent();
  }
}
