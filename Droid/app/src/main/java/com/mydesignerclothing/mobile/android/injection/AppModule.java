package com.mydesignerclothing.mobile.android.injection;

import android.app.Application;
import android.content.Context;

import com.mydesignerclothing.mobile.android.MyDesignerClothingApplication;
import com.mydesignerclothing.mobile.android.commons.core.AsynchronousModuleInitializer;
import com.mydesignerclothing.mobile.android.commons.core.SynchronousModuleInitializer;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentsManager;
import com.mydesignerclothing.mobile.android.login.core.LoginModuleInitializer;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.apiclient.IAPIClient;
import com.mydesignerclothing.mobile.android.network.cache.CacheManager;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V2;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V3;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.V4;

@Module
public class AppModule {

  private final MyDesignerClothingApplication application;

  public AppModule(MyDesignerClothingApplication application) {
    this.application = application;
  }

  @Provides
  public Application getApplication() {
    return application;
  }

  @ApplicationScope
  @Provides
  public Context getContext() {
    return application;
  }

  /*@ApplicationScope
  @Provides
  public ScheduleManager getScheduleManager() {
    return new ScheduleManager(application);
  }

  @ApplicationScope
  @Provides
  public SessionInfo sessionInfo() {
    return new SessionInfoImpl();
  }*/

  @ApplicationScope
  @Provides
  @Named("v4")
  public IAPIClient getV4ClientAPIClientFactory() {
    return APIClientFactory.get(application, V4);
  }

  @ApplicationScope
  @Provides
  @Named("v3")
  public IAPIClient getV3ClientAPIClientFactory() {
    return APIClientFactory.get(application, V3);
  }

  @ApplicationScope
  @Provides
  @Named("v2")
  public IAPIClient getV2ClientAPIClientFactory() {
    return APIClientFactory.get(application, V2);
  }

 /* @ApplicationScope
  @Provides
  public SharedPreferenceManager sharedPreferenceManager() {
    return new SharedPreferenceManager(application);
  }*/

  @ApplicationScope
  @Provides
  public EnvironmentsManager getEnvironmentsManager() {
    return MyDesignerClothingApplication.getEnvironmentsManager();
  }

  @ApplicationScope
  @Provides
  public EnvironmentConfig getEnvironmentConfig() {
    return MyDesignerClothingApplication.getEnvironmentConfig();
  }

  @ApplicationScope
  @Provides
  public CacheManager getCacheManager(Application application) {
    return CacheManager.create(application);
  }

  /*@ApplicationScope
  @Provides
  public Set<SynchronousModuleInitializer> synchronousModuleInitializers(AppModuleInitializer appModuleInitializer) {
    Set<SynchronousModuleInitializer> synchronousModuleInitializers = new HashSet<>();
    synchronousModuleInitializers.add(appModuleInitializer);
    return synchronousModuleInitializers;
  }

  @ApplicationScope
  @Provides
  public Set<AsynchronousModuleInitializer> asynchronousModuleInitializers(LoginModuleInitializer loginModuleInitializer) {
    Set<AsynchronousModuleInitializer> asynchronousModuleInitializers = new HashSet<>();
    asynchronousModuleInitializers.add(loginModuleInitializer);
    return asynchronousModuleInitializers;
  }*/
}
