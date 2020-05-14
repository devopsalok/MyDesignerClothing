package com.mydesignerclothing.mobile.android.network.exceptions;


import com.mydesignerclothing.mobile.android.network.models.NetworkError;

public class WrappedNetworkException extends RuntimeException {
  private final transient NetworkError error;

  public WrappedNetworkException(NetworkError error) {
    super(error.getErrorMessage(null));
    this.error = error;
  }

  public NetworkError getError() {
    return error;
  }
}
