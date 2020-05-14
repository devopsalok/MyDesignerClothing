package com.mydesignerclothing.mobile.android.network.utils;


import java.io.IOException;
import java.nio.charset.Charset;

import androidx.annotation.NonNull;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class OkHttpUtil {

  @NonNull
  public String stringify(Response response) throws IOException {
    return stringify(response.body());
  }

  @NonNull
  public String stringify(ResponseBody responseBody) throws IOException {
    BufferedSource source = responseBody.source();
    // request the entire body
    source.request(Long.MAX_VALUE);
    Buffer buffer = source.buffer();
    // clone buffer before reading from it
    return buffer.clone().readString(Charset.forName("UTF-8"));
  }
}
