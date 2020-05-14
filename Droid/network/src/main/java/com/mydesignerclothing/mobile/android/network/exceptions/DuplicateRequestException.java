package com.mydesignerclothing.mobile.android.network.exceptions;


import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;

import java.io.IOException;

import okhttp3.Request;
import okio.Buffer;

public class DuplicateRequestException extends IllegalStateException {

  private static final String TAG = DuplicateRequestException.class.getSimpleName();

  public DuplicateRequestException(Request request) {
    super(getExceptionMessage(request));
  }

  private static String getExceptionMessage(Request request) {
    Buffer buffer = new Buffer();
    try {
      request.body().writeTo(buffer);
    } catch (IOException e) {
      DMLog.e(TAG, e.getMessage());
      CrashTracker.trackStackTrace(TAG, e);
    }

    StringBuilder exceptionBuilder = new StringBuilder();
    exceptionBuilder.append("Another call with the same request is already underway...\n");
    exceptionBuilder.append("Rejecting Dispatch...%n");
    exceptionBuilder.append(String.format("Url:: %s%n", request.url()));
    exceptionBuilder.append(String.format("Headers:: %s%n", request.headers()));
    exceptionBuilder.append(String.format("Body:: %s", buffer.readUtf8()));
    return exceptionBuilder.toString();
  }
}
