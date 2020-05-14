package com.mydesignerclothing.mobile.android.login;

import android.app.Activity;
import android.content.Intent;

import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class LoginNavigatorHelper {

    private static final String TAG = LoginNavigatorHelper.class.getSimpleName();

    @SuppressWarnings("EmptyMethod")
    private LoginNavigatorHelper() {
        //NOSONAR
    }

  /*public static void navigateToHomeForLogout(Context context) {
    Intent gotoLoginIntent = new Intent(context, NavigationActivity.class);

    *//*
    FLAG_ACTIVITY_CLEAR_TOP, FLAG_ACTIVITY_SINGLE_TOP can be used for Activity context.
    If we don't have activity context, then need to use FLAG_ACTIVITY_CLEAR_TASK, FLAG_ACTIVITY_NEW_TASK.

    Activity Context - If NavigationDrawerActivity is already in stack, then FLAG_ACTIVITY_CLEAR_TOP
    clears all the activities on top of it and NavigationDrawerActivity comes at top of stack.

    Application Context - FLAG_ACTIVITY_CLEAR_TASK clears existing task and FLAG_ACTIVITY_NEW_TASK
    create new task with NavigationDrawerActivity as root activity.
    *//*

    if (context instanceof Activity)
      gotoLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    else {
      gotoLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    gotoLoginIntent.setAction(LOGOUT_ACTION);
    context.startActivity(gotoLoginIntent);
  }

  private static void cancelPendingJobs(Context context) {
    ScheduleManager scheduleManager = new ScheduleManager(context);
    scheduleManager.cancelAllJobsNotInApplicationClass();
  }

  public static void navigateToLoginScreen(Activity activity) {
    cancelPendingJobs(activity);
    UserSession.getInstance().removeWebViewCookies(activity.getApplication());

    if (SessionManager.getInstance().isUserSessionAvailable()) {
      Completable clearLoginSessionCompletable = new SessionCleaner(activity).clearLoginSession();
      clearLoginSessionCompletable.subscribe(getLoginSessionCompletableObserver(activity));
    } else {
      launchLoginScreen(activity);
    }
  }

  public static void clearLoggedInUserProfileAndPnrData(Context context) {
    clearSession();
    final SharedPreferenceManager sharedPrefs = new SharedPreferenceManager(context.getApplicationContext());
    sharedPrefs.setLastTimeRefresh(ITIN_DETAIL, false);

    DatabaseUtil.clearLoginRelatedDatabaseData(context);
  }*/

    private static CompletableObserver getLoginSessionCompletableObserver(Activity activity) {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable disposable) {
                CustomProgress.showProgressDialog(activity, "", false);
            }

            @Override
            public void onComplete() {
                CustomProgress.removeProgressDialog();
                launchLoginScreen(activity);
            }

            @Override
            public void onError(Throwable throwable) {
                CustomProgress.removeProgressDialog();
                launchLoginScreen(activity);
            }
        };
    }

    private static void launchLoginScreen(Activity activity) {
        Intent gotoLoginIntent = new Intent(activity, LoginActivity.class);
        gotoLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(gotoLoginIntent);
        if (!(activity instanceof LoginActivity))
            activity.finish();
    }
}
