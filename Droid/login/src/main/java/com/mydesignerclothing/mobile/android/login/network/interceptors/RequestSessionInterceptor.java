package com.mydesignerclothing.mobile.android.login.network.interceptors;

import android.content.Context;
import android.util.Log;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.tracking.CrashTracker;
import com.mydesignerclothing.mobile.android.login.core.LoginService;
import com.mydesignerclothing.mobile.android.login.models.IUserSession;
import com.mydesignerclothing.mobile.android.network.apiclient.ServiceTicket;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTransformer;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTransformerFactory;
import com.mydesignerclothing.mobile.android.network.errors.ErrorTransformingException;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.network.utils.OkHttpUtil;
import com.mydesignerclothing.mobile.android.uikit.util.SimpleObserver;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.getRequestType;
import static com.mydesignerclothing.mobile.android.network.headers.NetworkCustomerHeaders.REQUIRES_SESSION_CHECK;
import static com.mydesignerclothing.mobile.android.network.headers.NetworkCustomerHeaders.TRUE_HEADER;
import static com.mydesignerclothing.mobile.android.network.headers.NetworkCustomerHeaders.hasHeader;


public class RequestSessionInterceptor implements Interceptor {

  private static final String TAG = RequestSessionInterceptor.class.getSimpleName();
  private static final String LOGIN_ENDPOINT = "login";

  private static final int LATCH_TIMEOUT = 1;
  public static final String INVALID_SESSION_ERROR = "mob010";

  private Context context;
  private boolean proceed = true;
  private Observer<IUserSession> mSessionRefreshObserver;

  public RequestSessionInterceptor(WeakReference<Context> context) {
    this.context = context.get();
  }

  @Override
  public Response intercept(final Chain chain) throws IOException {
    CountDownLatch latch = new CountDownLatch(1);
    boolean requiresSessionCheck = hasHeader(chain.request().headers(), REQUIRES_SESSION_CHECK, TRUE_HEADER);

    if (!requiresSessionCheck) {
      latch.countDown();
    } else {
      interceptRequest(latch);
    }

    try {
      latch.await(LATCH_TIMEOUT, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      DMLog.log(TAG, e, Log.ERROR);
      CrashTracker.trackStackTrace(TAG, e);
    }

    if (proceed) {
      boolean isLoginRequest = chain.request().url().pathSegments().contains(LOGIN_ENDPOINT);
      if (isLoginRequest) {
        return chain.proceed(chain.request());
      }
      Response response = chain.proceed(chain.request());

    /*  if (isSessionInvalid(response) && SessionManager.getInstance().isUserSessionAvailable()) {
        return retryOnSessionValidation(chain, response);
      }*/

      return response;
    }

    return null;
  }

  public void setSessionRefreshObserver(Observer<IUserSession> mSessionRefreshObserver) {
    this.mSessionRefreshObserver = mSessionRefreshObserver;
  }

  private void interceptRequest(CountDownLatch latch) {

   /* LoginSharedPrefManager loginSharedPrefStorageManager =
      new LoginSharedPrefManager(SharedPref.getSharedPreference(context));
    if (SessionManager.getInstance().isSessionExpired(loginSharedPrefStorageManager)) {
      doLogin(getSessionRefreshObserver(latch), loginSharedPrefStorageManager);
    } else {
      latch.countDown();
    }*/
  }

  private boolean isSessionInvalid(Response response) throws IOException {
    Object tag = response.request().tag();
    if (tag instanceof ServiceTicket) {
      ServiceTicket serviceTicket = (ServiceTicket) tag;
      ErrorTransformer errorTransformer = ErrorTransformerFactory.create(getRequestType(serviceTicket.getRequestType()));
      if (errorTransformer.hasError(response)) {
        try {
          Optional<NetworkError> error = errorTransformer.transformError(new OkHttpUtil().stringify(response));
          return error.isPresent() && error.get().getErrorCode().equalsIgnoreCase(INVALID_SESSION_ERROR);
        } catch (ErrorTransformingException e) {
          DMLog.log(TAG, e, Log.ERROR);
          CrashTracker.trackStackTrace(TAG, e);
        }
      }
    }

    return false;
  }

  private Response retryOnSessionValidation(Chain chain, Response response) throws IOException {

    /*LoginSharedPrefManager loginSharedPrefStorageManager =
      new LoginSharedPrefManager(SharedPref.getSharedPreference(context));*/

    CountDownLatch latch = new CountDownLatch(1);
    proceed = false;

    doLogin(getSessionRefreshObserver(latch));

    try {
      latch.await(LATCH_TIMEOUT, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      DMLog.log(TAG, e, Log.ERROR);
      CrashTracker.trackStackTrace(TAG, e);
    }

    if (proceed) {
      return chain.proceed(chain.request());
    }

    return response;
  }

  @NonNull
  private Observer<IUserSession> getSessionRefreshObserver(CountDownLatch latch) {
    return new SimpleObserver<IUserSession>() {
      @Override
      public void onNext(@NonNull IUserSession o) {
        RequestSessionInterceptor.this.proceed = true;
        latch.countDown();
      }

      @Override
      public void onError(@NonNull Throwable e) {
        RequestSessionInterceptor.this.proceed = false;
        latch.countDown();
      }
    };
  }

  private void doLogin(Observer<IUserSession> sessionRefershObserver) {
   /* SecretManager secretManager = new SecretManager(Password.KEYSTORE_ALIAS, context);
    Observable<IUserSession> userObservable = getLoginService(context)
      .executeLogin(createLoginRequestDTO(secretManager), SessionManager.getInstance().getUserSession().getKeepLoggedIn());

    userObservable.subscribe(sessionRefershObserver);
    if (mSessionRefreshObserver != null) {
      userObservable.subscribe(mSessionRefreshObserver);
    }*/
  }

  /*protected LoginRequestDTO createLoginRequestDTO(SecretManager secretManager) {
    IUserSession userSession = SessionManager.getInstance().getUserSession();
    EncryptedPassword encryptedPassword = new EncryptedPassword(userSession.getPassword(), secretManager);

    return new LoginRequestDTO(userSession.getUsername(), userSession.getLastName(), encryptedPassword);
  }*/

  protected LoginService getLoginService(Context context) {
    return new LoginService(context);
  }
}
