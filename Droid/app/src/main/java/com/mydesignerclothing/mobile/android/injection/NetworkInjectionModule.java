package com.mydesignerclothing.mobile.android.injection;

import android.content.Context;

import com.mydesignerclothing.mobile.android.ActivityWatcher;
import com.mydesignerclothing.mobile.android.login.network.interceptors.RequestSessionInterceptor;
import com.mydesignerclothing.mobile.android.network.injection.NetworkComponent;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

@Module(subcomponents = NetworkComponent.class)
public class NetworkInjectionModule {

  private final Context context;

  public NetworkInjectionModule(Context context) {
    this.context = context;
  }

  @Provides
  public Set<Interceptor> getNetworkInterceptors() {
    HashSet<Interceptor> interceptors = new HashSet<>();
    interceptors.add(getSessionInterceptor());
    //interceptors.add(getAppUnavailableInterceptor());
    return interceptors;
  }

  private Interceptor getSessionInterceptor() {
    RequestSessionInterceptor requestSessionInterceptor = new RequestSessionInterceptor(new WeakReference<>(context));
//    requestSessionInterceptor.setSessionRefreshObserver(new SessionRefreshObserver(context));
    return requestSessionInterceptor;
  }

  /*private Interceptor getAppUnavailableInterceptor() {
    Context applicationContext = context.getApplicationContext();
    if (!(applicationContext instanceof ActivityWatcher)) {
      throw new IllegalArgumentException("Application must implement " + ActivityWatcher.class.getSimpleName());
    }
    return new AppUnavailableInterceptor((ActivityWatcher) applicationContext);
  }*/
}
