package com.mydesignerclothing.mobile.android.network.errors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;

import okhttp3.Response;

public class ErrorTransformer {

  public Optional<NetworkError> transformError(String errorBody) throws ErrorTransformingException {
    try {
      return Optional.of(new Gson().fromJson(errorBody, NetworkError.class));
    } catch (JsonSyntaxException e) {
      throw new ErrorTransformingException(e);
    }
  }

  public boolean hasError(Response response) {
    return !response.isSuccessful();
  }
}
