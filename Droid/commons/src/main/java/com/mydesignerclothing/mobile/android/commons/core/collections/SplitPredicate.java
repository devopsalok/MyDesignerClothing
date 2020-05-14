package com.mydesignerclothing.mobile.android.commons.core.collections;

public interface SplitPredicate<E> {
  boolean match(E previous, E current);
}
