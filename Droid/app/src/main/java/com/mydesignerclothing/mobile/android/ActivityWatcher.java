package com.mydesignerclothing.mobile.android;


import android.app.Activity;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;


public interface ActivityWatcher {
  Optional<Activity> getCurrentActivity();
}
