package com.mydesignerclothing.mobile.android.network.cookie;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CookieStore {
  public static final String TLTSID_COOKIE = "TLTSID";
  public static final String TLTHID_COOKIE = "TLTHID";

  private final org.apache.http.client.CookieStore internalCookieStore = new BasicCookieStore();

  private CookieStore() {
  }

  private static class InstanceHolder {
    static CookieStore instance = new CookieStore();
  }

  public static CookieStore getInstance()   {
    return InstanceHolder.instance;
  }

  public synchronized String getCookieValue(String cookieName) {
    List<Cookie> cookies = internalCookieStore.getCookies();
    for (int i = 0; i < cookies.size(); i++) {
      if (cookies.get(i).getName().equalsIgnoreCase(cookieName)) {
        return cookies.get(i).getValue();
      }
    }
    return null;
  }

  public synchronized void addCookie(Cookie cookie) {
    internalCookieStore.addCookie(cookie);
  }

  public synchronized List<Cookie> getCookies() {
    return Collections.unmodifiableList(new ArrayList<>(internalCookieStore.getCookies()));
  }

  public synchronized void removeCookies() {
    internalCookieStore.clear();
  }

  public synchronized void removeWebViewCookies(Context context) {
    CookieSyncManager.createInstance(context);
    CookieManager cookieManager = CookieManager.getInstance();
    if (cookieManager != null) {
      cookieManager.removeSessionCookie();
    }
  }
}
