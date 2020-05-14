package com.mydesignerclothing.mobile.android.commons.core;

import io.reactivex.Completable;

public interface AsynchronousModuleInitializer {
  Completable initialize();
}
