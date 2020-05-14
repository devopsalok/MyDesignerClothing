package com.mydesignerclothing.mobile.android.commons.rxutils;

import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;

import hu.akarnokd.rxjava2.debug.RxJavaAssemblyException;

import static com.mydesignerclothing.mobile.android.commons.core.optional.Optional.absent;
import static com.mydesignerclothing.mobile.android.commons.core.optional.Optional.fromNullable;

public class RxExceptionUtil {

  private RxExceptionUtil() {
    //Sonar enforces to create a private constructor when there are no instance methods
  }

  public static Optional<String> getRxAssemblyStackTrace(Throwable throwable) {
    RxJavaAssemblyException rxJavaAssemblyException = RxJavaAssemblyException.find(throwable);
    if (rxJavaAssemblyException == null) {
      return absent();
    } else {
      return fromNullable(rxJavaAssemblyException.stacktrace());
    }
  }
}
