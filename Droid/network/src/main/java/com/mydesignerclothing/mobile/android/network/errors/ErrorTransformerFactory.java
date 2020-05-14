package com.mydesignerclothing.mobile.android.network.errors;

import com.mydesignerclothing.mobile.android.network.apiclient.RequestType;
import com.mydesignerclothing.mobile.android.network.exceptions.UnknownRequestTypeException;

import static java.lang.String.format;

public class ErrorTransformerFactory {

  public static ErrorTransformer create(RequestType requestType) throws UnknownRequestTypeException {
    ErrorTransformer errorTransformer;
    switch (requestType) {
      case V2:
      case V3:
      case V4:
      case WEB_SERVER:
        errorTransformer = new ErrorTransformer();
        break;
      default:
        throw new UnknownRequestTypeException(format("Couldn't recognize the request type: %s for error transformation", requestType.getValue()));
    }
    return errorTransformer;
  }
}
