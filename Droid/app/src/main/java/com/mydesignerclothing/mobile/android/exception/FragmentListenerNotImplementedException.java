package com.mydesignerclothing.mobile.android.exception;

import java.util.Locale;

import androidx.annotation.NonNull;

import static java.lang.String.format;

public class FragmentListenerNotImplementedException extends RuntimeException {

  public FragmentListenerNotImplementedException(@NonNull Class listener) {
    super(format(Locale.US, "Activity must implement %s", listener.getSimpleName()));
  }
}
