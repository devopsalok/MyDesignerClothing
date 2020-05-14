package com.mydesignerclothing.mobile.android.network.interceptor;

import android.util.Log;


import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.network.cookie.CookieStore;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities.reduce;


public class CookieInterceptor implements Interceptor {
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE = "cookie";
    private static final String TAG = CookieInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = updateRequestWithCookies(chain.request());
        Response response = chain.proceed(request);

        Headers headers = response.headers();
        Map<String, List<String>> responseHeaders = headers.toMultimap();
        updateCookiesFromResponse(request, responseHeaders);
        return response;
    }

    private Request updateRequestWithCookies(Request request) {
        Headers headers = request.headers();
        List<String> cookies = headers.toMultimap().get(COOKIE);
        Request.Builder builder = request.newBuilder();

        if (cookies == null) {
            CookieStore cookieStore = CookieStore.getInstance();

            if (cookieStore != null) {
                String cookie = reduce(new ReduceFunction<String, Cookie>() {
                    @Override
                    public String apply(Cookie element, String currentResult) {
                        return String.format("%s=%s; %s", element.getName(), element.getValue(), currentResult);
                    }
                }, "", cookieStore.getCookies());

                builder.header(COOKIE, cookie);
            }
        }

        return builder.build();
    }

    private void updateCookiesFromResponse(Request request, Map<String, List<String>> headers) {
        if (headers.keySet().contains(SET_COOKIE)) {
            List<String> cookieHeaders = headers.get(SET_COOKIE);
            for (String cookieString : cookieHeaders) {
                try {
                    BasicHeader cookieHeader = new BasicHeader(SET_COOKIE, cookieString);

                    CookieOrigin cookieOrigin = new CookieOrigin(request.url().host(), 443,
                            request.url().encodedPath(), false);
                    List<Cookie> parsedCookies = new BestMatchSpec().parse(cookieHeader, cookieOrigin);
                    addCookies(parsedCookies);
                } catch (Exception e) {
                    DMLog.log(TAG, e, Log.ERROR);
                }
            }
        }
    }

    private void addCookies(List<Cookie> parsedCookies) {
        for (Cookie parsedCookie : parsedCookies) {
            CookieStore.getInstance().addCookie(parsedCookie);
        }
    }
}
