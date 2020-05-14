package com.mydesignerclothing.mobile.android.commons.core.collections.sequence;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.core.collections.MapFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.ReduceFunction;
import com.mydesignerclothing.mobile.android.commons.core.collections.Seqs;

import java.util.ArrayList;
import java.util.List;


public class ListSeqLike<E> implements SeqLike<List, E> {

  protected final List<E> list;

  public ListSeqLike(List<E> list) {
    this.list = list;
  }

  @Override
  public <R> List<R> map(final MapFunction<E, R> function) {
    final List<R> mapped = new ArrayList<R>();
    this.each(new Function<E>() {
      @Override
      public void apply(E elem) {
        mapped.add(function.map(elem));
      }
    });
    return mapped;
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
    for (E object : list) {
      function.apply(object);
    }
  }

  @Override
  public SeqLike<List, E> empty() {
    return new ListSeqLike<>(new ArrayList<E>());
  }

  @Override
  public SeqLike<List, E> add(E element) {
    list.add(element);
    return this;
  }

  @Override
  public List unwrap() {
    return list;
  }
}
