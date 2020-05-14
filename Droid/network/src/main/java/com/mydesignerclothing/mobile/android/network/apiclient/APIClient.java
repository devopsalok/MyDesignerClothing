package com.mydesignerclothing.mobile.android.network.apiclient;

import android.content.Context;

import com.mydesignerclothing.mobile.android.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

import static java.lang.String.format;

public class APIClient implements IAPIClient {

    private final Retrofit retrofit;

    private APIClient(Context context,
                      RequestType requestType,
                      RetrofitBuilder.ContentType contentType,
                      List<Interceptor> interceptors) {
        this.retrofit = new RetrofitBuilder()
                .withContentType(contentType)
                .withBaseUrl(format("http://%s/", requestType))
                .withInterceptors(interceptors)
                .withRequestTag(new ServiceTicket(requestType.getValue()))
                .build(context);
    }

    private APIClient(Context context,
                      RetrofitBuilder.ContentType contentType) {
        this.retrofit = new RetrofitBuilder()
                .withContentType(contentType)
                .build(context);
    }

    public static IAPIClient create(Context context,
                                    RequestType requestType,
                                    RetrofitBuilder.ContentType contentType,
                                    List<Interceptor> interceptors) {
        return new APIClient(
                context,
                requestType,
                contentType,
                interceptors
        );
    }

    public static IAPIClient createWithNoHeaders(Context context,
                                                 RetrofitBuilder.ContentType contentType) {
        return new APIClient(context, contentType);
    }

    public static IAPIClient create(Context context,
                                    RequestType requestType,
                                    RetrofitBuilder.ContentType contentType,
                                    Interceptor interceptor) {
        List interceptors = new ArrayList<>();
        interceptors.add(interceptor);
        return create(context, requestType, contentType, interceptors);
    }

    public static IAPIClient create(Context context,
                                    RetrofitBuilder.ContentType contentType) {
        return createWithNoHeaders(context, contentType);
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
