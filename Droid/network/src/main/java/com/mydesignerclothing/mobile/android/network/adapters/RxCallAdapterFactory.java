package com.mydesignerclothing.mobile.android.network.adapters;

import com.google.gson.reflect.TypeToken;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.network.cache.CacheConfig;
import com.mydesignerclothing.mobile.android.network.cache.CacheManager;
import com.mydesignerclothing.mobile.android.network.exceptions.ForceCacheMissException;
import com.mydesignerclothing.mobile.android.network.injection.NetworkComponent;
import com.mydesignerclothing.mobile.android.network.models.Cacheable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import static io.reactivex.Observable.concat;

public class RxCallAdapterFactory extends CallAdapter.Factory {

  private NetworkComponent networkInjector;

  public RxCallAdapterFactory(NetworkComponent networkInjector) {
    this.networkInjector = networkInjector;
  }

  @Nullable
  @Override
  public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    if (getRawType(returnType) != Observable.class) {
      return null;
    }

    final Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
    Type responseType = observableType;
    if (observableType instanceof ParameterizedType) {
      responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
    }

    TypeToken<?> bodyObservableType = TypeToken.getParameterized(Observable.class, responseType);
    Type modifiedReturnType = getRawType(observableType) == Cacheable.class ? bodyObservableType.getType() : returnType;

    final CallAdapter<Object, Observable<?>> delegate =
      (CallAdapter<Object, Observable<?>>) retrofit.nextCallAdapter(this, modifiedReturnType, annotations);

    return new RxCallAdapter(delegate, returnType, networkInjector);
  }

  public static class RxCallAdapter implements CallAdapter<Object, Observable<?>> {
    private static final String TAG = RxCallAdapter.class.getSimpleName();
    private static final String CUSTOM_CACHE_CONTROL_HEADER = "Cache-Config";
    @Inject
    CacheManager cacheManager;
    private CallAdapter<Object, Observable<?>> delegate;
    private Type returnType;

    public RxCallAdapter(CallAdapter<Object, Observable<?>> delegate, Type returnType, NetworkComponent networkInjector) {
      this.delegate = delegate;
      this.returnType = returnType;
      networkInjector.inject(this);
    }

    @Override
    public Type responseType() {
      return delegate.responseType();
    }

    @Override
    public Observable<?> adapt(Call<Object> call) {
      final Type observableType = RxCallAdapterFactory.getParameterUpperBound(0, (ParameterizedType) returnType);
      Type responseType = observableType;
      if (observableType instanceof ParameterizedType) {
        responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
      }

      Observable<?> resultedObservable = delegate.adapt(call);
      boolean adaptMemory = getRawType(observableType) == Cacheable.class;

      if (adaptMemory) {
        resultedObservable = concatCacheObservable(responseType, resultedObservable, call.request().headers());
      }

      return resultedObservable
        .doOnError(this::trackErrors)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    private void trackErrors(Throwable t) {
      DMLog.e(TAG, String.format("Caught Exception for n/w call %s", t.getMessage()));
      CrashTracker.trackStackTrace(TAG, t);
    }

    private Observable<? extends Cacheable<?>> concatCacheObservable(Type responseType, Observable<?> source, Headers headers) {
      validateArguments(headers);

      CacheConfig cacheConfig = CacheConfig.parse(headers.get(CUSTOM_CACHE_CONTROL_HEADER));

      boolean isLatest = cacheManager.isLatest(cacheConfig.cacheKey(), cacheConfig.duration());

      Observable<? extends Cacheable<?>> cacheObservable = Observable.create(e -> {
        Optional<Object> cachedResponse = cacheManager.get(cacheConfig.cacheKey(), responseType);
        if (cachedResponse.isPresent() && !e.isDisposed()) {
          e.onNext(new Cacheable<>(cachedResponse.get(), true));
        } else if (cacheConfig.isForceCache() && !e.isDisposed()) {
          e.onError(new ForceCacheMissException(cacheConfig.cacheKey()));
        }
        if (!e.isDisposed())
          e.onComplete();
      });

      Observable<? extends Cacheable<?>> networkObservable = source.map(Cacheable::new);

      Observable<? extends Cacheable<?>> resultedObservable = shouldExecuteNetworkCall(cacheConfig, isLatest) ? concat(cacheObservable, networkObservable) : cacheObservable;

      return resultedObservable
        .doOnNext(o -> {
          if (o != null && o.isPresent() && !cacheConfig.isForceCache()) {
            cacheManager.put(cacheConfig.cacheKey(), o.get());
          }
        });
    }

    private boolean shouldExecuteNetworkCall(CacheConfig cacheConfig, boolean isLatest) {
      return !cacheConfig.isForceCache() && (cacheConfig.isForceNetwork() || !isLatest);
    }

    private void validateArguments(Headers headers) {
      Set<String> headerKeys = headers.names();
      if (!headerKeys.contains(CUSTOM_CACHE_CONTROL_HEADER)) {
        throw new IllegalArgumentException("Cache-Config is not provided!");
      }
    }
  }
}
