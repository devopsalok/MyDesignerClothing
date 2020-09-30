package com.mydesignerclothing.mobile.android.basket;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.mydesignerclothing.mobile.android.MyDesignerClothingApplication;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://mydesignerclothing.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkhttpClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkhttpClient() {
        if(okHttpClient == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(40, TimeUnit.SECONDS)
                    .connectTimeout(40,TimeUnit.SECONDS)

                    .addNetworkInterceptor(httpLoggingInterceptor)
                    /*.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + "1ed6b7c1839e02bbf7a1b4a8dbca84d23127c68e").build();
                            return chain.proceed(request);
                        }})*/


                    .addInterceptor(new ChuckerInterceptor(MyDesignerClothingApplication.getInstance()))

                    .build();
        }
        return  okHttpClient;
    }




}
