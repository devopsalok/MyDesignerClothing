package com.mydesignerclothing.mobile.android.uikit.util;

import androidx.annotation.DrawableRes;

public class DrawableResource {
  private int resId;

  public DrawableResource(@DrawableRes int resId) {
    this.resId = resId;
  }

  public int resId() {
    return resId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DrawableResource that = (DrawableResource) o;

    return resId == that.resId;

  }

  @Override
  public int hashCode() {
    return resId;
  }
}
