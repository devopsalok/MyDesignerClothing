package com.mydesignerclothing.mobile.android.login.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.mydesignerclothing.mobile.android.commons.core.AsynchronousModuleInitializer;
import com.mydesignerclothing.mobile.android.commons.util.StringUtils;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.mydesignerclothing.mobile.android.commons.network.AppNetworkStateManager.NETWORK_STATE_CHANGE_ACTION;

public class LoginModuleInitializer implements AsynchronousModuleInitializer {

  private Context context;

  @Inject
  public LoginModuleInitializer(Context context) {
    this.context = context;
  }

  @Override
  public Completable initialize() {

    return Completable.create(this::initialize);
  }

  private void initialize(CompletableEmitter observer) {
   /* LoginDatabaseStorageManager loginDatabaseStorageManager = new LoginDatabaseStorageManager(context);

    LoginSharedPrefManager loginSharedPreferences =
      new LoginSharedPrefManager(SharedPref.getSharedPreference(context));

    Observable<IUserSession> userSessionObservable = loginDatabaseStorageManager
      .getUser(loginSharedPreferences.getUserId());

    userSessionObservable.subscribe(new SimpleObserver<IUserSession>() {
      @Override
      public void onNext(IUserSession userSession) {
        initializeUserSession(userSession, observer);
      }

      @Override
      public void onError(Throwable e) {
        SessionManager.getInstance().clearUserSession();
        if (e instanceof DatabaseOperationException) {
          observer.onComplete();
        } else {
          observer.onError(e);
        }
      }
    });

    IntentFilter intentFilter = new IntentFilter(NETWORK_STATE_CHANGE_ACTION);
    BroadcastReceiver receiver = new RefreshLoginOnNetworkChangeReceiver(
      NetworkState.getInstance(), loginSharedPreferences);
    LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);*/
  }

  /*private void initializeUserSession(IUserSession userSession, CompletableEmitter observer) {
    if (!StringUtils.isNullOrEmpty(userSession.getPassword())) {
      if (userSession.getKeepLoggedIn()) {
        SessionManager.getInstance().createUserSession(userSession);
        observer.onComplete();
      } else {
        Completable completable = new SessionCleaner(context).clearLoginSession();
        completable.subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable disposable) {
            // NO-SONAR
          }

          @Override
          public void onComplete() {
            observer.onComplete();
          }

          @Override
          public void onError(Throwable throwable) {
            observer.onComplete();
          }
        });
      }
    } else {
      observer.onComplete();
    }
  }*/
}
