package com.mydesignerclothing.mobile.android.network.cache;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.disklrucache.DiskLruCache;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import androidx.annotation.VisibleForTesting;

public final class CacheManager {
  private static final String TAG = CacheManager.class.getSimpleName();
  private static final String CACHE_DIR = "network_cache_dir";
  private static final int MB = 1024 * 1024;
  private static final int CACHE_SIZE = 3 * MB;
  @VisibleForTesting
  final Optional<DiskLruCache> diskLruCache;

  private CacheManager(Context context) {
    File dir = new File(context.getCacheDir(), CACHE_DIR);
    DiskLruCache lruCache = null;
    try {
      lruCache = DiskLruCache.open(dir, 1, 1, CACHE_SIZE);
    } catch (IOException e) {
      CrashTracker.trackStackTrace(TAG, e);
    }
    this.diskLruCache = Optional.fromNullable(lruCache);
  }

  public static CacheManager create(Context context) {
    return new CacheManager(context);
  }

  public synchronized <T> Optional<T> get(String cacheKey, Type type) {
    if (!diskLruCache.isPresent()) {
      return Optional.absent();
    }

    try {
      DiskLruCache.Snapshot cacheSnapshot = diskLruCache.get().get(cacheKey);
      if (isCachePresent(cacheSnapshot)) {
        Optional<CacheResponse> cacheResponse = toCacheResponse(cacheSnapshot);
        if (cacheResponse.isPresent()) {
          return Optional.fromNullable(new Gson().fromJson(cacheResponse.get().response(), type));
        }
      }
    } catch (IOException e) {
      CrashTracker.trackStackTrace(TAG, e);
    }
    return Optional.absent();
  }

  public synchronized <T> Optional<T> get(String cacheKey, long cacheDurationInMillis, Type type) {
    return !isLatest(cacheKey, cacheDurationInMillis) ? Optional.absent() : get(cacheKey, type);
  }

  public synchronized <T> boolean put(String cacheKey, T response) {
    if (!diskLruCache.isPresent()) {
      return false;
    }

    try {
      DiskLruCache.Editor editor = diskLruCache.get().edit(cacheKey);
      Gson gson = new Gson();
      String stringResponse = gson.toJson(response);
      editor.set(0, gson.toJson(new CacheResponse(stringResponse)));
      editor.commit();
      return true;
    } catch (IOException e) {
      CrashTracker.trackStackTrace(TAG, e);
      return false;
    }
  }

  public synchronized boolean remove(String cacheKey) {
    if (!diskLruCache.isPresent()) {
      return false;
    }

    try {
      return diskLruCache.get().remove(cacheKey);
    } catch (IOException e) {
      CrashTracker.trackStackTrace(TAG, e);
      return false;
    }
  }

  public synchronized boolean isLatest(String cacheKey, long cacheDuration) {
    if (!diskLruCache.isPresent())
      return false;

    try {
      DiskLruCache.Snapshot cacheSnapshot = diskLruCache.get().get(cacheKey);
      if (isCachePresent(cacheSnapshot)) {
        Optional<CacheResponse> cacheResponse = toCacheResponse(cacheSnapshot);
        if (cacheResponse.isPresent()) {
          return cacheResponse.get().isLatest(cacheDuration);
        }
      }
    } catch (IOException e) {
      CrashTracker.trackStackTrace(TAG, e);
    }

    return false;
  }

  private boolean isCachePresent(DiskLruCache.Snapshot cacheSnapshot) {
    return cacheSnapshot != null;
  }

  private Optional<CacheResponse> toCacheResponse(DiskLruCache.Snapshot cacheSnapshot) throws IOException {
    String cacheResponseString = cacheSnapshot.getString(0);
    Gson gson = new Gson();
    try {
      return Optional.fromNullable(gson.fromJson(cacheResponseString, CacheResponse.class));
    } catch (JsonSyntaxException jsonSyntaxException) {
      CrashTracker.trackStackTrace(TAG, jsonSyntaxException);
    }
    return Optional.absent();
  }
}
