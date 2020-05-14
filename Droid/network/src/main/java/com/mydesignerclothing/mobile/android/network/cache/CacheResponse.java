package com.mydesignerclothing.mobile.android.network.cache;

final class CacheResponse {
  private final String response;
  private final long creationTime;

  public CacheResponse(String response) {
    this.response = response;
    this.creationTime = System.currentTimeMillis();
  }

  public String response() {
    return response;
  }

  public boolean isLatest(long cacheDurationInMillis) {
    long currentTime = System.currentTimeMillis();
    return currentTime - creationTime <= cacheDurationInMillis;
  }
}
