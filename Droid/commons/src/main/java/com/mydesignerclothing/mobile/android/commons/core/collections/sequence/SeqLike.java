package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;


import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;

public interface SeqLike<T, E> {

  <R> T map(final MapFunction<E, R> function);

  <R> R reduce(final R initialValue, final ReduceFunction<R, E> function);

  void each(Function<E> function);

  SeqLike<T, E> empty();

  SeqLike<T, E> add(E element);

  T unwrap();
}
