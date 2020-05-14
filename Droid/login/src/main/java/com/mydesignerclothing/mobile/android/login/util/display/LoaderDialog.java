package com.mydesignerclothing.mobile.android.login.util.display;

public class LoaderDialog {
  private boolean visible;
  private boolean cancellable;
  private String text;

  public LoaderDialog(boolean visible, String text) {
    this.visible = visible;
    this.text = text;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isVisible() {
    return visible;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isCancellable() {
    return cancellable;
  }
}
