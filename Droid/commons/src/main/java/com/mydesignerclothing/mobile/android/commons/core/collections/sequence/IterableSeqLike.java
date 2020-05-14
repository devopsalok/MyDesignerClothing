package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.Seqs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class IterableSeqLike<T extends Iterable, E> implements SeqLike<T, E> {

  private final Iterator<E> iterator;

  public IterableSeqLike(Iterable<E> iterable) {
    this.iterator = iterable.iterator();
  }

  @Override
  public <R> T map(final MapFunction<E, R> function) {
    final List<R> mapped = new ArrayList<R>();
    this.each(new Function<E>() {
      @Override
      public void apply(E elem) {
        mapped.add(function.map(elem));
      }
    });
    return (T) mapped;
  }

  @Override
  public <R> R reduce(final R initialValue, final ReduceFunction<R, E> function) {
    final Seqs.ReduceWrapper<R> result = new Seqs.ReduceWrapper<>(initialValue);
    this.each(new Function<E>() {
      @Override
      public void apply(E elem) {
        result.element = function.apply(elem, result.element);
      }
    });
    return result.element;
  }

  @Override
  public void each(final Function<E> function) {
    while (iterator.hasNext()) {
      function.apply(iterator.next());
    }
  }

}
