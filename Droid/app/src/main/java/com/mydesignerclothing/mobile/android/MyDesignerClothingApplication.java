package com.mydesignerclothing.mobile.android;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;

import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentsManager;
import com.mydesignerclothing.mobile.android.commons.network.AppNetworkStateManager;
import com.mydesignerclothing.mobile.android.injection.AppComponent;
import com.mydesignerclothing.mobile.android.injection.AppModule;
import com.mydesignerclothing.mobile.android.injection.DaggerAppComponent;
import com.mydesignerclothing.mobile.android.injection.NetworkInjectionModule;
import com.mydesignerclothing.mobile.android.login.di.LoginComponent;
import com.mydesignerclothing.mobile.android.login.di.LoginComponentInjector;
import com.mydesignerclothing.mobile.android.network.injection.NetworkComponent;
import com.mydesignerclothing.mobile.android.network.injection.NetworkInjector;
import com.prateekj.snooper.AndroidSnooper;

import java.lang.ref.WeakReference;

import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import hu.akarnokd.rxjava2.debug.RxJavaAssemblyTracking;

import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V1;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentsManager.DEVICE_LOCAL_KEY;
import static com.mydesignerclothing.mobile.android.login.constants.Constant.LOGIN_FAILURE_EVENT;
import static com.mydesignerclothing.mobile.android.login.constants.Constant.LOGIN_SUCCESS_EVENT;

public class MyDesignerClothingApplication extends DaggerApplication implements ActivityWatcher, NetworkInjector, LoginComponentInjector {

    private AppComponent appComponent;
    public static EnvironmentsManager environmentsManager;
    private static EnvironmentConfig environmentConfig;

    private static Context appContext;
    private static MyDesignerClothingApplication applicationInstance;
    private WeakReference<Activity> activityRef;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationContext();
        //initEnvManager();

        registerActivityLifecycleCallbacks(new MyDscActivityLifeCycleCallback());
        RxJavaAssemblyTracking.enable();

        applicationInstance = this;
//        CrashTracker.initialize(getApplicationContext(), BuildConfig.DEBUG);
        initAppInfo();
        AppNetworkStateManager.getInstance().refresh(this);
        registerNetworkChangeReceiverForLogin();
        registerLoginEventReceiver();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());

//        new AppAnalytics.Builder().withApplication(this).withContext(appContext).build().initialize();
    }

    protected void initEnvManager() {
        initEnvironmentsManager(false);
    }

    protected void initApplicationContext() {
        appContext = getApplicationContext();
    }

    public static MyDesignerClothingApplication getInstance() {
        return applicationInstance;
    }

    protected void initEnvironmentsManager(boolean forUnitTest) {
        environmentConfig = environmentConfig();
        environmentsManager = new EnvironmentsManager(appContext, environmentConfig);
    }

    private EnvironmentConfig environmentConfig() {
        return new EnvironmentConfig.Builder()
                .withMobileFacadeToPort(V1, "4568")
                .withCurrentEnv(environment())
                .build();
    }

    public static String environment() {
        if (appContext == null) {
            return DEVICE_LOCAL_KEY;
        }
        return "dev";
    }

    @Override
    public synchronized Optional<Activity> getCurrentActivity() {
        if (activityRef != null && activityRef.get() != null) {
            return Optional.of(activityRef.get());
        }
        return Optional.absent();
    }

    public void setCurrentActivityRef(WeakReference<Activity> currentActivity) {
        this.activityRef = currentActivity;
    }

    public void clearCurrentActivityRef(Activity activity) {
        if (getCurrentActivity().isPresent() && activityRef.get() != null && activityRef.get().equals(activity)) {
            activityRef.clear();
        }
    }

    private void initAppInfo() {
        AppInfo appInfo = AppInfo.get(getApplicationContext());
    }

    private void registerLoginEventReceiver() {
        IntentFilter intentFilter = new IntentFilter(LOGIN_SUCCESS_EVENT);
        intentFilter.addAction(LOGIN_FAILURE_EVENT);

//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    private void registerNetworkChangeReceiverForLogin() {
        AndroidSnooper.Companion.init(this);
    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .networkInjectionModule(new NetworkInjectionModule(this))
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
        return appComponent;
    }

    public static EnvironmentsManager getEnvironmentsManager() {
        return environmentsManager;
    }

    public static EnvironmentConfig getEnvironmentConfig() {
        return environmentConfig;
    }

    @Override
    public NetworkComponent getNetworkComponent() {
        return appComponent.getBindingRouter().getNetworkComponent();
    }

    @Override
    public LoginComponent getLoginComponent() {
        return appComponent.getLoginBindingInjection().getLoginComponent();
    }
}
