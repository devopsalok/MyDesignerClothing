package com.mydesignerclothing.mobile.android.network.exceptions;


import static java.lang.String.format;

public class ForceCacheMissException extends RuntimeException {

  public ForceCacheMissException(String cacheKey) {
    super(format("[FORCE CACHE] Cache Miss against cache key %s", cacheKey));
  }
}
