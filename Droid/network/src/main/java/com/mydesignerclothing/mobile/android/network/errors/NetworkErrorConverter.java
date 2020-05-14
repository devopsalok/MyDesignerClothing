package com.mydesignerclothing.mobile.android.network.errors;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.network.utils.OkHttpUtil;

import retrofit2.Response;

public class NetworkErrorConverter {

  private static final String TAG = NetworkErrorConverter.class.getSimpleName();

  @SuppressWarnings("EmptyMethod")
  private NetworkErrorConverter() {
    //NOSONAR
  }

  public static Optional<NetworkError> getNetworkError(Throwable t) {
    if (t instanceof HttpException) {
      try {
        Response<?> response = ((HttpException) t).response();
        String stringify = new OkHttpUtil().stringify(response.errorBody());
        return new V2ErrorTransformer().transformError(stringify);
      } catch (Exception e) {
        DMLog.e(TAG, e.getMessage());
        CrashTracker.trackStackTrace(TAG, e);
      }
    }
    return ErrorConverter.getNetworkError(t);
  }

  public static NetworkError makeNetworkErrorUnrecoverable(Optional<NetworkError> networkErrorOptional) {
    NetworkError networkError = networkErrorOptional.isPresent() ? networkErrorOptional.get() : new NetworkError();
    networkError.setUnrecoverable(true);
    return networkError;
  }
}
