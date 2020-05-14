package com.mydesignerclothing.mobile.android.commons.core;

  public interface BaseCallback<S,E> {

  void onSuccess(S response);
  void onFailure(E response);

}
