package com.mydesignerclothing.mobile.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class MyDscActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

  @Override
  @SuppressWarnings("EmptyMethod")
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    //NOSONAR
  }

  @Override
  @SuppressWarnings("EmptyMethod")
  public void onActivityStarted(Activity activity) {
    //NOSONAR
  }

  @Override
  public void onActivityResumed(Activity activity) {
    setCurrentActivity(activity);
  }

  @Override
  public void onActivityPaused(Activity activity) {
  }

  @Override
  @SuppressWarnings("EmptyMethod")
  public void onActivityStopped(Activity activity) {
    //NOSONAR
  }

  @Override
  @SuppressWarnings("EmptyMethod")
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    //NOSONAR
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    synchronized (MyDesignerClothingApplication.getInstance()) {
      MyDesignerClothingApplication.getInstance().clearCurrentActivityRef(activity);
    }
  }

  private void setCurrentActivity(Activity activity) {
    synchronized (MyDesignerClothingApplication.getInstance()) {
      WeakReference<Activity> activityRef = new WeakReference<>(activity);
      MyDesignerClothingApplication.getInstance().setCurrentActivityRef(activityRef);
    }
  }
}
