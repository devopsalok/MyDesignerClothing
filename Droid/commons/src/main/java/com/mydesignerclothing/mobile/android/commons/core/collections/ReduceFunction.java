package com.mydesignerclothing.mobile.android.commons.core.collections;

public interface ReduceFunction<R, E> {
  R apply(E element, R currentResult);
}
