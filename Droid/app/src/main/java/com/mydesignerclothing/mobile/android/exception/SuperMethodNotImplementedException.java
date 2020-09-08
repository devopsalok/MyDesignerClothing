package com.mydesignerclothing.mobile.android.exception;

import java.util.Locale;

import androidx.annotation.NonNull;

import static java.lang.String.format;

public class SuperMethodNotImplementedException extends RuntimeException {

  public SuperMethodNotImplementedException(@NonNull String className, @NonNull String method) {
    super(format(Locale.US, "Failed to implement %s in %s - extension must be defined if you want to use it", method, className));
  }
}
