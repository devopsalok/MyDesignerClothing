package com.mydesignerclothing.mobile.android.commons.util;

import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

import org.apache.http.client.utils.URIUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class URLUtil {

  public static final String DOUBLE_SLASH = "//";
  public static final String SINGLE_SLASH = "/";
  public static final String TAG = URLUtil.class.getSimpleName();

  private URLUtil() {

  }

  public static String getUrl(String href) {
    URI uri = URI.create(href);
    String path = uri.getPath().replace(DOUBLE_SLASH, SINGLE_SLASH);
    String url = "";
    try {
      url = URIUtils.createURI(uri.getScheme(), uri.getHost(), uri.getPort(), path, uri.getQuery(), uri.getFragment()).toString();
    } catch (URISyntaxException exception) {
      DMLog.e(TAG, exception);
    }
    return url;
  }

  public static String getFormattedPostData(LinkedHashMap<String, String> postData) {
    String formattedPostData;
    Iterator<String> postDataFields = postData.keySet().iterator();
    StringBuilder paramsBuilder = new StringBuilder();
    try {
      while (postDataFields.hasNext()) {
        String key = postDataFields.next();
        paramsBuilder.append(key);
        paramsBuilder.append("=");
        final String value = postData.get(key);
        paramsBuilder.append(URLEncoder.encode(value != null ? value : "", "UTF-8"));

        if (postDataFields.hasNext()) paramsBuilder.append("&");
      }
    } catch (UnsupportedEncodingException exception) {
      DMLog.e(TAG, exception);
    }
    formattedPostData = paramsBuilder.toString();
    return formattedPostData;
  }
}
