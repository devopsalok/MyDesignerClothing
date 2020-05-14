package com.mydesignerclothing.mobile.android.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig;
import com.mydesignerclothing.mobile.android.commons.environment.EnvironmentsManager;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.network.adapters.RxCallAdapterFactory;
import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.convertor.XmlRequestJsonResponseConverterFactory;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTracker;
import com.mydesignerclothing.mobile.android.network.injection.NetworkComponent;
import com.mydesignerclothing.mobile.android.network.injection.NetworkInjector;
import com.mydesignerclothing.mobile.android.network.interceptor.CookieInterceptor;
import com.mydesignerclothing.mobile.android.network.interceptor.ErrorTrackerInterceptor;
import com.mydesignerclothing.mobile.android.network.interceptor.HeaderSanitizerInterceptor;
import com.mydesignerclothing.mobile.android.network.interceptor.NetworkSecurityHeaderInterceptor;
import com.mydesignerclothing.mobile.android.network.interceptor.OfflineModeInterceptor;
import com.mydesignerclothing.mobile.android.network.interceptor.TealeafInterceptor;
import com.prateekj.snooper.okhttp.SnooperInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class RetrofitBuilder {
    private static final String TAG = RetrofitBuilder.class.getSimpleName();
    private static String APP_SERVER_URL = BuildConfig.API_HOST;
    private String baseUrl;
    private List<Interceptor> interceptors = new ArrayList<>();
    private ContentType requestContentType = ContentType.XML;
    private ServiceTicket serviceTicket;

    @Inject
    Set<Interceptor> appInterceptors;
    @Inject
    EnvironmentConfig environmentConfig;
    @Inject
    EnvironmentsManager environmentsManager;


    public RetrofitBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitBuilder withInterceptors(List<Interceptor> interceptors) {
        this.interceptors.addAll(interceptors);
        return this;
    }

    public RetrofitBuilder withInterceptors(Interceptor interceptor) {
        List interceptorsList = new ArrayList<>();
        interceptorsList.add(interceptor);
        return withInterceptors(interceptorsList);
    }

    public RetrofitBuilder withContentType(ContentType contentType) {
        requestContentType = contentType;
        return this;
    }

    public RetrofitBuilder withRequestTag(ServiceTicket serviceTicket) {
        this.serviceTicket = serviceTicket;
        return this;
    }

    public Retrofit build(Context context) {
        if (serviceTicket == null) {
           // throw new IllegalStateException("Service ticket is mandatory for Retrofit instance");
        }

        //getNetworkInjector(context.getApplicationContext()).inject(this);
        OkHttpClient okHttpClient = getOkHttpClient(context);

        return new Retrofit.Builder()
                .baseUrl(APP_SERVER_URL)
                .addCallAdapterFactory(new RxCallAdapterFactory(getNetworkInjector(context.getApplicationContext())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                //.callFactory(new LazyCallFactory(okHttpClient, environmentConfig, serviceTicket, environmentsManager))
                .addConverterFactory(getConverterFactory())
                .build();
    }

    private NetworkComponent getNetworkInjector(Context context) {
        if (context instanceof NetworkInjector) {
            NetworkInjector injector = (NetworkInjector) context;
            return injector.getNetworkComponent();
        }
        throw new IllegalStateException("A network injector is expected to be implemented by App: " + context.getClass().getSimpleName());
    }

    @VisibleForTesting
    @NonNull
    OkHttpClient getOkHttpClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new CustomLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (appInterceptors != null) {
            interceptors.addAll(appInterceptors);
        }

        for (Interceptor interceptor : interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }

        CustomHeaders headers = new CustomHeaders(new AppInfo(context), new DeviceInfo(), context.getResources());
        TealeafInterceptor tealeafInterceptor = new TealeafInterceptor(headers, (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE));
        okHttpClientBuilder.addInterceptor(tealeafInterceptor);

        okHttpClientBuilder
                //.addInterceptor(new CookieInterceptor())
                .addInterceptor(new NetworkSecurityHeaderInterceptor())
                .addInterceptor(new HeaderSanitizerInterceptor())
                .addInterceptor(new OfflineModeInterceptor())
                .addInterceptor(new SnooperInterceptor())
                .addInterceptor(loggingInterceptor)
                // This interceptor should be the last to preserve the headers
                .addInterceptor(new ErrorTrackerInterceptor(new ErrorTracker()));

        return okHttpClientBuilder
                .callTimeout(180, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private Converter.Factory getConverterFactory() {
        if (requestContentType == ContentType.JSON) {
            return GsonConverterFactory.create();
        }

        return new XmlRequestJsonResponseConverterFactory();
    }

    public enum ContentType {
        JSON, XML
    }

    // It is important to use DMLog as the logs cannot be logged in production env.
    private class CustomLogger implements HttpLoggingInterceptor.Logger {

        @Override
        public void log(String message) {
            DMLog.d(TAG, message);
        }
    }
}
