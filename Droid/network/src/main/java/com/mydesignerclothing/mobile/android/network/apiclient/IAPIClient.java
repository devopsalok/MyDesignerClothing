package com.mydesignerclothing.mobile.android.network.apiclient;

public interface IAPIClient {
  <T> T get(Class<T> clazz);
}
