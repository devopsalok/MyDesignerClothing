package com.mydesignerclothing.mobile.android.network.headers;

import java.lang.annotation.Retention;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import okhttp3.Headers;

import static com.mydesignerclothing.mobile.android.commons.util.MyDesignerClothingUtilConstants.COLON_SPACE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class NetworkCustomerHeaders {

  @Retention(SOURCE)
  @StringDef({
    REQUIRES_SESSION_CHECK,
    X_ADAPTER,
    X_ADAPTER_CODE,
    X_APP_ROUTE,
    APP_ID,
    PAGE_ID,
    IS_MOBILE
  })
  @interface ValidHeaderName {
  }

  public static final String REQUIRES_SESSION_CHECK = "RequiresSessionCheck";
  public static final String X_ADAPTER = "X-Adapter";
  public static final String X_ADAPTER_CODE = "xAdapterCode";
  public static final String X_APP_ROUTE = "X-APP-ROUTE";
  public static final String APP_ID = "appId";
  public static final String PAGE_ID = "pageId";
  public static final String IS_MOBILE = "isMobile";

  @Retention(SOURCE)
  @StringDef({
    TRUE_HEADER,
    MOBILE_VALUE
  })
  @interface ValidHeaderValue {
  }

  public static final String TRUE_HEADER = "true";
  public static final String MOBILE_VALUE = "mobile";

  public static final String REQUIRES_SESSION_CHECK_HEADER = REQUIRES_SESSION_CHECK + COLON_SPACE + TRUE_HEADER;
  public static final String X_ADAPTER_HEADER = X_ADAPTER + COLON_SPACE + MOBILE_VALUE;
  public static final String X_ADAPTER_CODE_HEADER = X_ADAPTER_CODE + COLON_SPACE + MOBILE_VALUE;
  public static final String APP_ID_HEADER = APP_ID + COLON_SPACE + MOBILE_VALUE;
  public static final String IS_MOBILE_HEADER = IS_MOBILE + COLON_SPACE + TRUE_HEADER;

  public static boolean hasHeader(@NonNull Headers headers, @ValidHeaderName String headerName, @ValidHeaderValue String expectedValue) {
    return expectedValue.equalsIgnoreCase(headers.get(headerName));
  }
}
