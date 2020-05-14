package com.mydesignerclothing.mobile.android.login.util.display;

import android.content.DialogInterface;

public class ErrorDialog {
  private String error;
  private String errorTitle;
  private DialogInterface.OnClickListener listener;

  public static ErrorDialog newInstance(String error, DialogInterface.OnClickListener listener) {
    return new ErrorDialog(error, listener);
  }

  public ErrorDialog() {
    //NOSONAR
  }

  private ErrorDialog(String error, DialogInterface.OnClickListener listener) {
    this.error = error;
    this.listener = listener;
  }

  public boolean hasError() {
    return error != null;
  }

  public String getError() {
    return error;
  }

  public String getErrorTitle() {
    return errorTitle;
  }

  public DialogInterface.OnClickListener getListener() {
    return listener;
  }

  public void setListener(DialogInterface.OnClickListener listener) {
    this.listener = listener;
  }

  public void setError(String errorMessage) {
    this.error = errorMessage;
  }

  public void setErrorTitle(String errorTitle) {
    this.errorTitle = errorTitle;
  }

  public void flush() {
    error = null;
  }
}
