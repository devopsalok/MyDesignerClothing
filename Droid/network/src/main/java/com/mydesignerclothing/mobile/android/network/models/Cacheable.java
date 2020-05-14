package com.mydesignerclothing.mobile.android.network.models;


public class Cacheable<T> {

  private final T value;
  private final boolean isCache;

  public Cacheable(T value) {
    this(value, false);
  }

  public Cacheable(T value, boolean isCache) {
    this.value = value;
    this.isCache = isCache;
  }

  public boolean isPresent() {
    return value != null;
  }

  public boolean isCacheHit() {
    return isCache;
  }

  public boolean isCacheMiss() {
    return !isCacheHit();
  }

  public T get() {
    return value;
  }
}
