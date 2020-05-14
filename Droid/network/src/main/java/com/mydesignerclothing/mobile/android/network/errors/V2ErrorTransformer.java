package com.mydesignerclothing.mobile.android.network.errors;


import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.network.utils.OkHttpUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class V2ErrorTransformer extends ErrorTransformer {

  private static final String TAG = V2ErrorTransformer.class.getSimpleName();
  @Override
  public Optional<NetworkError> transformError(String errorBody) throws ErrorTransformingException {
    Optional<JSONObject> response = new ErrorHandler().transformResponse(errorBody);
    if (response.isPresent()) {
        return super.transformError(response.get().toString());
    }
    return Optional.absent();
  }

  @Override
  public boolean hasError(Response response) {
    try {
      if (super.hasError(response) || new ErrorHandler().isMobileFacadeError(new OkHttpUtil().stringify(response))) {
        return true;
      }
    } catch (IOException e) {
      DMLog.e(TAG, e.getMessage());
      CrashTracker.trackStackTrace(TAG, e);
    }
    return false;
  }
}
