package com.mydesignerclothing.mobile.android.network.cache;

import org.apache.commons.lang3.StringUtils;

public class CacheConfig {
  public static final String CACHE_CONFIG = "Cache-Config";
  private static final long FORCE_CACHE = Long.MAX_VALUE;
  private static final long FORCE_NETWORK = 0;
  private long cacheDuration;
  private final String cacheKey;

  private CacheConfig(String cacheKey, long cacheDuration) {
    this.cacheKey = cacheKey;
    this.cacheDuration = cacheDuration;
  }

  public static CacheConfig forceNetwork(String cacheKey) {
    return CacheConfig.create(cacheKey, FORCE_NETWORK);
  }

  public static CacheConfig forceCache(String cacheKey) {
    return CacheConfig.create(cacheKey, FORCE_CACHE);
  }

  public static CacheConfig create(String cacheKey, long cacheDurationInMillis) {
    if (StringUtils.isEmpty(cacheKey))
      throw new IllegalArgumentException("cacheKey can not be null or empty!");
    return new CacheConfig(cacheKey, cacheDurationInMillis);
  }

  public String cacheKey() {
    return cacheKey;
  }

  public long duration() {
    return cacheDuration;
  }

  public boolean isForceNetwork() {
    return cacheDuration == FORCE_NETWORK;
  }

  public boolean isForceCache() {
    return cacheDuration == FORCE_CACHE;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(CACHE_CONFIG + ":KEY=" + cacheKey + ";");
    if (isForceCache()) {
      stringBuilder.append("DURATION=forceCache");
    } else if (isForceNetwork()) {
      stringBuilder.append("DURATION=forceNetwork");
    } else {
      stringBuilder.append("DURATION=").append(cacheDuration);
    }

    return stringBuilder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CacheConfig that = (CacheConfig) o;

    if (cacheDuration != that.cacheDuration) return false;
    return cacheKey != null ? cacheKey.equals(that.cacheKey) : that.cacheKey == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (cacheDuration ^ (cacheDuration >>> 32));
    result = 31 * result + (cacheKey != null ? cacheKey.hashCode() : 0);
    return result;
  }

  public static CacheConfig parse(String cacheConfigString) {
    String sanitizedConfigString = cacheConfigString.replaceAll("\\s", "");

    if (!sanitizedConfigString.matches("Cache-Config:KEY=.*;DURATION=.*$"))
      throw new IllegalArgumentException("Error while parsing: " + sanitizedConfigString);

    String[] keyValueList = sanitizedConfigString.split(";");
    String cacheKeyAndValue = keyValueList[0];
    String durationAndValue = keyValueList[1];

    String cacheKey = cacheKeyAndValue.split("=")[1];
    String duration = durationAndValue.split("=")[1];

    switch (duration) {
      case "forceCache":
        return CacheConfig.create(cacheKey, FORCE_CACHE);
      case "forceNetwork":
        return CacheConfig.create(cacheKey, FORCE_NETWORK);
      default:
        return CacheConfig.create(cacheKey, Long.valueOf(duration));
    }
  }
}
